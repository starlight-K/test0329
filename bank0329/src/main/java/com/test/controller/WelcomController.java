package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller

public class WelcomController {
    // 访问localhost:8080 默认进login.html
    @RequestMapping("/")
    public String welcome() {
        return "login";
    }

   /* @RequestMapping("tohtml")
    public String tohtml(HttpServletRequest request) {
        return (String) request.getAttribute("pageName");
    }*/
}
