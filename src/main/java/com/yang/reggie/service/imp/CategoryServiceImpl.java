package com.yang.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.reggie.entity.Category;

import com.yang.reggie.entity.Dish;
import com.yang.reggie.entity.Setmeal;
import com.yang.reggie.com.CustomException;
import com.yang.reggie.mapper.CategoryMapper;
import com.yang.reggie.service.CategoryService;
import com.yang.reggie.service.DishService;
import com.yang.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category>
        implements CategoryService {
    //先注入菜品和套餐的service
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        //然后去除
        //1.构造查询器
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //2.添加查询条件，根据条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //1.构造查询器
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //2.添加查询条件，根据条件
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 =setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            //已经关联套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        super.removeById(id);
    }
}