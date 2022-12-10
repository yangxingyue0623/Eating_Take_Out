package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.ShoppingCart;


public interface ShoppingCartService extends IService<ShoppingCart> {

    R<ShoppingCart> addcart(ShoppingCart shoppingCart);

    void clean();

    R<ShoppingCart> sub(ShoppingCart shoppingCart);
}
