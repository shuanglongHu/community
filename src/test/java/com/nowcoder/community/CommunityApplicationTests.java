package com.nowcoder.community;

import com.nowcoder.community.dao.MyDao;
import com.nowcoder.community.service.MyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Test
    public void testApplicationContext() {
        System.out.println(applicationContext);
        MyDao dao = (MyDao)applicationContext.getBean("mybatsiDaoImpl");
        System.out.println(dao.select());
    }
    @Test
    public void testBeanManagement() {
        MyService service = applicationContext.getBean(MyService.class);
        System.out.println(service);
        service = applicationContext.getBean(MyService.class);
        System.out.println(service);
    }
    @Test
    public void testBeanConfig() {
        SimpleDateFormat bean = applicationContext.getBean(SimpleDateFormat.class);
        System.out.println(bean.format(new Date()));
    }
    @Autowired
    @Qualifier("alphaDaoImpl")
    private MyDao dao;
    @Autowired
    private MyService service;

    @Test
    public void testDI(){
        System.out.println(dao);
        System.out.println(service);
    }

}
