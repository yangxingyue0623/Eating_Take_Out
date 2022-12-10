package com.yang.reggie.dto;

import com.yang.reggie.entity.OrderDetail;
import com.yang.reggie.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author yangxingyue0623
 * @date 2022/12/7 18:14
 */
@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails;
}
