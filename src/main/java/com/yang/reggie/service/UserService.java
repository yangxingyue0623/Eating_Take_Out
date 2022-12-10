package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;


public interface UserService extends IService<User> {
    R<String> sendMsg(User user, HttpSession session);

    R<User> login(Map map, HttpSession session);
}
