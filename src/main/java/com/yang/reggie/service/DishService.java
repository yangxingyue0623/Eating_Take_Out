package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.Dish;
import com.yang.reggie.dto.DishDto;

import java.util.List;


public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id,查询菜品基本信息，同时插入菜品对应的口味数据需要操作两张表：dish、dish_flavor
    public DishDto getByIdWithFlavor(Long id);
    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    R<Page> pagedish(int page, int pageSize, String name);

    R<String> status(Integer status, List<Long> ids);

    R<String> deletedish(List<Long> ids);

    R<List<DishDto>> listdish(Dish dish);
}
