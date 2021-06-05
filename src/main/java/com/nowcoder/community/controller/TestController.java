package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello!";
    }

    @RequestMapping("/find")
    @ResponseBody
    public User findUserById(int id){
        User user = userService.findUserById(id);
        return user;
    }

}
