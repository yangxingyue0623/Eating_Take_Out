package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.entity.OrderDetail;

import java.util.List;


public interface OrderDetailService extends IService<OrderDetail> {
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId);
}
