package com.yang.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.reggie.com.CustomException;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.*;
import com.yang.reggie.dto.DishDto;
import com.yang.reggie.mapper.DishMapper;
import com.yang.reggie.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品，同时保存对应的口味（两张表）
     * @param dishDto
     */
    @Transactional //两张表事务控制
    public void saveWithFlavor (DishDto dishDto){
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);
        //保存菜品口味数据到菜品口味表，引入口味service
        //批量保存口味（但是存在问题）
        //因为保存的时候 private List<DishFlavor> flavors = new ArrayList<>();
        //封装的是.getFlavors含只有name和value(口味名称和数据），然而无菜品id)
        //1.所以在前面获得dishId
        Long dishId = dishDto.getId();//对应的菜品id
        //2.先对菜品口味进行处理
        List<DishFlavor> flavors = dishDto.getFlavors();
        //3.遍历集合，将dishId给flavor赋值
        flavors=flavors.stream().map((item) -> {
                    item.setDishId(dishId);
                    return item;
                }
        ).collect(Collectors.toList());
        //item是遍历出来的值，收集起来变成list ,赋值给原来的

        dishFlavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //1.先查询基本信息
        Dish dish = this.getById(id);
        //2.查询口味信息，从dish_flavor
        //3.返回的sdto，所以需要拷贝
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //条件构造器
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        //条件查询,根据菜品的id与口味表的id对应
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {

        //更新dish表基本信息，清理当前菜品对应的口味，再添加当前的口味数据
        this.updateById(dishDto);
        //构造条件
        LambdaQueryWrapper<DishFlavor> queryWrapper = new
                LambdaQueryWrapper();
        //查询条件
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //插入
        List<DishFlavor> flavors = dishDto.getFlavors();
        dishFlavorService.saveBatch(flavors);
        //注意此时disFlaor的dishid没有值，所以需要传入
        //3.遍历集合，将dishId给flavor赋值
        flavors=flavors.stream().map((item) -> {
                    item.setDishId(dishDto.getId());
                    return item;
                }
        ).collect(Collectors.toList());


    }

    @Override
    public R<String> status(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(ids !=null,Dish::getId,ids);
        //根据数据进行批量查询
        List<Dish> list = this.list(queryWrapper);

        for (Dish dish : list) {
            if (dish != null){
                dish.setStatus(status);
                this.updateById(dish);
            }
        }
        return R.success("售卖状态修改成功");
    }

    /**
     *套餐批量删除和单个删除
     * @param ids
     */
//    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {

        //构造条件查询器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //先查询该菜品是否在售卖，如果是则抛出业务异常
        queryWrapper.in(ids!=null,Dish::getId,ids);
        List<Dish> list = this.list(queryWrapper);
        for (Dish dish : list) {
            Integer status = dish.getStatus();
            //如果不是在售卖,则可以删除
            if (status == 0){
                this.removeById(dish.getId());
            }else {
                //此时应该回滚,因为可能前面的删除了，但是后面的是正在售卖
                throw new CustomException("删除菜品中有正在售卖菜品,无法全部删除");
            }
        }

    }

    /**
     * 删除菜品
     *  1.判断要删除的菜品在不在售卖的套餐中，如果在那不能删除
     *  * 2.要先判断要删除的菜品是否在售卖，如果在售卖也不能删除
     * @param ids
     * @return
     */

    @Override
    public R<String> deletedish(List<Long> ids) {

        //1.找到菜品是否关联套餐

        //1.1不关联的直接删除菜品，删除口味
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getDishId,ids);
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        if (setmealDishes.size()==0){
            //不关联菜品，直接删除
            for (Long id:ids) {
                this.deleteByIds(ids);
            }
            //删除关联口味
            LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
            dishFlavorWrapper.in(DishFlavor::getDishId,ids);
            dishFlavorService.remove(dishFlavorWrapper);
            return R.success("菜品删除成功");

        }
        //2.关联套餐
        //2.1套餐售
        //如果菜品有关联套餐，并且该套餐正在售卖，那么不能删除
        //得到与删除菜品关联的套餐id
        ArrayList<Long> Setmeal_idList = new ArrayList<>();
        for (SetmealDish setmealDish :setmealDishes ) {
            Long setmealId = setmealDish.getSetmealId();
            Setmeal_idList.add(setmealId);
        }

        //查询出与删除菜品相关联的套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,Setmeal_idList);
        List<Setmeal> setmealList = setmealService.list(setmealLambdaQueryWrapper);
        //对拿到的所有套餐进行遍历，然后拿到套餐的售卖状态，如果有套餐正在售卖那么删除失败
        for (Setmeal setmeal : setmealList) {
            Integer status = setmeal.getStatus();
            if (status == 1){
                return R.error("删除的菜品中有关联在售套餐,删除失败！");
            }
        }
        //2.2套餐不售

        //要删除的菜品关联的套餐没有在售，可以删除
        //这下面的代码并不一定会执行,因为如果前面的for循环中出现status == 1,那么下面的代码就不会再执行
        this.deletedish(ids);
        LambdaQueryWrapper<DishFlavor> queryWrapperdishf = new LambdaQueryWrapper<>();
        queryWrapperdishf.in(DishFlavor::getDishId,ids);
        dishFlavorService.remove(queryWrapperdishf);
        return R.success("菜品删除成功");

    }

    @Override
    public R<List<DishDto>> listdish(Dish dish) {
        List<DishDto> dishDtoList=null;
        String key="dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        //1.从redis中获得缓存数据
        dishDtoList = (List<DishDto>)  redisTemplate.opsForValue().get(key);
        //2.判断是否存在缓存
        if (dishDtoList !=null){
            return R.success(dishDtoList);
        }

        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = this.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
        //3.保存查询信息到缓存
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }

    @Override
    public R<Page> pagedish(int page, int pageSize, String name) {

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //此时也不能直接保存dishdto，因为没有值
        //所以将dish执行qw的值后拷贝到DishDto之中

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        this.page(pageInfo,queryWrapper);

        //对象拷贝
        //copyProperties 拷贝属性，元，不拷集合（集合是显示在页面的，我们要处理）
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        //以下是处理list
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list= records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            String categoryname = category.getName();
            dishDto.setCategoryName(categoryname);
            return dishDto;

        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

}