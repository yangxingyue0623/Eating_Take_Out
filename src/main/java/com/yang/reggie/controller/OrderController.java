package com.yang.reggie.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.Orders;
import com.yang.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 查询订单
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page>  orderPage(int page,int pageSize,String number,String beginTime,String endTime ){
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        queryWrapper.like(number!=null,Orders::getNumber,number)
                .gt( StringUtils.isNotBlank(beginTime),Orders::getOrderTime,beginTime)
                .lt( StringUtils.isNotBlank(endTime),Orders::getOrderTime,endTime);
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    /**
     * 订单派送状态的更改
     * @param orders
     * @return
     */
    @PutMapping
    public R<Orders> dispatch(@RequestBody Orders orders){
        //构造查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //构造查询条件
        queryWrapper.eq(orders.getId()!=null,Orders::getId,orders.getId());
        Orders orderone = orderService.getOne(queryWrapper);
        orderone.setStatus(orders.getStatus());
        orderService.updateById(orderone);

        return R.success(orderone);

    }

    @GetMapping("/userPage")
    public R<Page> userpage(int page, int pageSize){
        return orderService.userPage(page,pageSize);
    }
    //客户端点击再来一单
    /**
     * 前端点击再来一单是直接跳转到购物车的，所以为了避免数据有问题，再跳转之前我们需要把购物车的数据给清除
     * ①通过orderId获取订单明细
     * ②把订单明细的数据的数据塞到购物车表中，不过在此之前要先把购物车表中的数据给清除(清除的是当前登录用户的购物车表中的数据)，
     * 不然就会导致再来一单的数据有问题；
     * (这样可能会影响用户体验，但是对于外卖来说，用户体验的影响不是很大，电商项目就不能这么干了)
     */
    @PostMapping("/again")
    public R<String> againSubmit(@RequestBody Map<String,String> map){
        return orderService.again(map);
    }

}