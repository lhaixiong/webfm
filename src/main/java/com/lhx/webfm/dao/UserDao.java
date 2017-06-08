package com.lhx.webfm.dao;

import com.lhx.webfm.annotation.MyDao;
import com.lhx.webfm.helper.DBHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@MyDao
public class UserDao {

    public List<Map<String, Object>> getUsers(String sql,Class cls){
        List<Map<String, Object>> mapList=null;
        try {
            Connection conn=DBHelper.getConnection();
            mapList= DBHelper.queryForListMap(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.closeConnection();
        }
        return mapList;
    }

}
