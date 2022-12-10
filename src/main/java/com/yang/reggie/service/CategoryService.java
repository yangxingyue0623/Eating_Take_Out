package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);//扩展自己的方法

}
