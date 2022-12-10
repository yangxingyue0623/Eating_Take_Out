package com.yang.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.reggie.entity.Category;
import com.yang.reggie.entity.Dish;
import com.yang.reggie.entity.DishFlavor;
import com.yang.reggie.com.R;
import com.yang.reggie.dto.DishDto;
import com.yang.reggie.service.CategoryService;
import com.yang.reggie.service.DishFlavorService;
import com.yang.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save( @RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        return dishService.pagedish(page,pageSize,name);

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);

    }
    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update( @RequestBody DishDto dishDto){
        //设置dish和口味表
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
//        //清理所有的菜品缓存
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        //精确清理
        String key="dish_"+dishDto.getCategoryId()+"_";
        redisTemplate.delete(key);

        return R.success("新增菜品成功");
    }

    /**
     * 利用dish接收更通用
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        return dishService.listdish(dish);
    }

    /**
     * 对菜品批量或者是单个 进行停售或者是起售
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status,@RequestParam List<Long> ids) {
        return dishService.status(status, ids);

    }
    /**
     * 删除菜品
     */
    @DeleteMapping
    public R<String> deletedish(@RequestParam("ids") List<Long> ids){

        return dishService.deletedish(ids);
    }


}