package com.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserMapper userMapper;


    @RequestMapping("login")
    public String  login(String username,String password , HttpSession session) {
        User user = userMapper.login(username, password);
        System.out.println(user);
        if(user == null) {
            return "用户名或密码错误";
        }
        session.setAttribute("user",user);
        return "bank";
    }

}
