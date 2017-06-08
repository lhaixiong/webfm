package com.lhx.webfm.service;

import com.lhx.webfm.annotation.MyInject;
import com.lhx.webfm.annotation.MyService;
import com.lhx.webfm.dao.UserDao;

import java.util.List;
import java.util.Map;

@MyService
public class UserService {
    @MyInject
    private UserDao dao;
    public List<Map<String, Object>> getUsers(String sql,Class cls){
        return dao.getUsers(sql,cls);
    }

}
