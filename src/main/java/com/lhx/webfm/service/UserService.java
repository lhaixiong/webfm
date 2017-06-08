package com.lhx.webfm.service;

import com.lhx.webfm.dao.UserDao;
import com.lhx.webfm.entity.User;

import java.util.List;
import java.util.Map;

public class UserService {
    private UserDao dao=new UserDao();
    public List<Map<String, Object>> getUsers(String sql,Class cls){
        return dao.getUsers(sql,cls);
    }

}
