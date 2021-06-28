package com.nowcoder.community.controller;
import com.nowcoder.community.service.MyService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping(value = "/alpha")
public class MyController {
    @RequestMapping("/say")
    @ResponseBody
    public String say() {
        return "Hello Spring!";
    }

    @Autowired
    private MyService service;

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        String s = service.find();
        return s;
    }
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        response.setContentType("text/html;charset=utf-8");

        try(
                PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //处理GET请求
    //http://localhost:8080/community/alpha/students?current=3&limit=50
    @RequestMapping(value = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit",required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);;
        return "some students";
    }

    //http://localhost:8080/community/alpha/student/101
    @RequestMapping(value = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student!";
    }

    //处理POST请求
    @RequestMapping(value = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTML数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mv= new ModelAndView();
        mv.addObject("name","王老师");
        mv.addObject("age",39);
        mv.setViewName("/demo/view");
        return mv;
    }
    @RequestMapping(value = "/school",method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name","西电");
        model.addAttribute("age",80);
        return"/demo/view";
    }
    //返回json数据
    @RequestMapping(value = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",13);
        emp.put("salary",25000);
        return emp;
    }
    @RequestMapping(value = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",13);
        emp.put("salary",25000);
        list.add(emp);

        emp.put("name","lisi");
        emp.put("age",14);
        emp.put("salary",20000);
        list.add(emp);

        emp.put("name","wanger");
        emp.put("age",15);
        emp.put("salary",35000);
        list.add(emp);

        emp.put("name","mazi");
        emp.put("age",16);
        emp.put("salary",75000);
        list.add(emp);
        return list;
    }

    @RequestMapping(value = "/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        //创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie生效的范围
        cookie.setPath("/community/alpha");
        //设置cookie生存时间
        cookie.setMaxAge(60 * 10);//单位为秒
        //发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }
    @RequestMapping(value = "/cookie/get",method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return "get cookie";
    }

    @RequestMapping(value = "/session/set",method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("name","Test");

        return "set session";
    }
    @RequestMapping(value = "/session/get",method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }


}
