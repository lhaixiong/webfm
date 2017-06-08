package com.lhx.webfm.controller;

import com.lhx.webfm.annotation.MyAction;
import com.lhx.webfm.annotation.MyController;
import com.lhx.webfm.annotation.MyInject;
import com.lhx.webfm.bean.MyData;
import com.lhx.webfm.bean.MyView;
import com.lhx.webfm.entity.User;
import com.lhx.webfm.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MyController
public class UserController {
    @MyInject
    private UserService service;
    @MyAction(value = "GET:/listUser")
    public MyView listUser(){
        String sql="select * from user";
        List<Map<String, Object>> list = service.getUsers(sql, User.class);
        List<User> userList=new ArrayList<>();
        for (Map<String, Object> map : list) {
            int id= (int) map.get("id");
            int age= (int) map.get("age");
            String name= (String) map.get("name");
            userList.add(new User(id,age,name));
        }
        MyView view=new MyView("user/list.jsp");
        view.addModel("userList",userList);
        return view;
    }
    @MyAction(value = "GET:/listUser2")
    public MyData listUser2(){
        String sql="select * from user";
        List<Map<String, Object>> list = service.getUsers(sql, User.class);
        List<User> userList=new ArrayList<>();
        for (Map<String, Object> map : list) {
            int id= (int) map.get("id");
            int age= (int) map.get("age");
            String name= (String) map.get("name");
            userList.add(new User(id,age,name));
        }
        MyData data=new MyData(userList);
        return data;
    }
}
