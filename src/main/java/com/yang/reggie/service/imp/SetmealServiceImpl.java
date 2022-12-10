package com.yang.reggie.service.imp;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yang.reggie.com.R;
import com.yang.reggie.entity.Category;
import com.yang.reggie.entity.Setmeal;
import com.yang.reggie.entity.SetmealDish;
import com.yang.reggie.com.CustomException;
import com.yang.reggie.dto.SetmealDto;
import com.yang.reggie.mapper.SetmealMapper;
import com.yang.reggie.service.CategoryService;
import com.yang.reggie.service.SetmealDishService;
import com.yang.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,
        Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Resource
    @Lazy
    private CategoryService categoryService;

    /**
     * 新增套餐，同时需要保存套餐和菜品发关系
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto){
        //保存stteaml基本信息，操作steaml，执行inset操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //注意此时的setmealDishes的属性setMealId没有值，dishId存在值
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，执行sttmeal dish的inset;
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除菜单也需要从删除关系表
     * 还要先判断状态
     * @param ids
     */

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in (1,2,3) and status = 1
        //查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        //delete from setmeal_dish where setmeal_id in (1,2,3)
        //找到套餐关系表的id然后删除
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);

    }

    @Override
    public void statuschange(Integer status, List<Long> ids) {
        //1.根据ids找到对应的套餐
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids !=null,Setmeal::getId,ids);
        List<Setmeal> list = this.list(queryWrapper);
        for (Setmeal setmeal:list) {
            if (setmeal!=null){
                setmeal.setStatus(status);
                this.updateById(setmeal);
            }
        }

    }

    @Override
    public SetmealDto getDate(Long id) {
        //1.根据传给的套餐id进行数据的回显
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtil.copyProperties(setmeal,setmealDto);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    public R<Page> pagesetmeal(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage=new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage=new Page<>();

        //条件构造器，添加过滤条件，添加排序条件
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        //执行分页查询
        this.page(setmealPage,queryWrapper);

        //对象拷贝
        //页面数据只传了分类id，没显示分类名称，要显示还需以下操作
        //排除records对象，自己写records。records是page对象的属性，存放查询到的数据
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");

        List<Setmeal> setmealList=setmealPage.getRecords();
        List<SetmealDto> setmealDtoList=setmealList.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);

            //根据id查询分类对象
            long categoryId=item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }

            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(setmealDtoList);

        return R.success(setmealDtoPage);

    }


}
