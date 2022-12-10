package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.Orders;

import java.util.Map;


public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);

    R<Page> userPage(int page, int pageSize);

    R<String> again(Map<String, String> map);
}
