package com.nowcoder.community.service;

import com.nowcoder.community.dao.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Scope(value = "prototype")
public class MyService {
    @Autowired
    private MyDao dao;

    public MyService(){
        System.out.println("实例化MyService");
    }
    @PostConstruct
    public void init() {
        System.out.println("初始化");
    }
    @PreDestroy
    public void destroy() {
        System.out.println("销毁");
    }

    public String find() {
        return dao.select();
    }
}
