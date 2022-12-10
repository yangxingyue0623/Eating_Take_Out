package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.Setmeal;
import com.yang.reggie.dto.SetmealDto;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);

    void statuschange(Integer status, List<Long> ids);

    SetmealDto getDate(Long id);

    R<Page> pagesetmeal(int page, int pageSize, String name);
}
