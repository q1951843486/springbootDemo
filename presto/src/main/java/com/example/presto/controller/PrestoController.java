package com.example.presto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;


/**
 * @Description
 * @ClassName PrestoController
 * @Author Administrator
 * @date 2020.06.11 11:27
 */
@Controller
public class PrestoController {

    @ResponseBody
    @RequestMapping("/test")
    public int  tset() {


/*
        Class.forName("com.facebook.presto.jdbc.PrestoDriver");

        //url的填写中使用 jdbc:presto://ip地址:端口号/system/runtime 其中system是指默认的catalog内所有的源数据连接，runtime是数据源中默认的schema，这样写后面的SQL语句

        //需要指定具体的数据源连接名和schema名，实现跨库混合查询

        Connection connection = DriverManager.getConnection("jdbc:presto://192.168.227.3:8001/mongodb/test", "root", null);

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("show tables");

        while (rs.next()) {

            System.out.println(rs.getString(1));

        }

        rs.close();

        connection.close();*/


        long start = System.currentTimeMillis();
        int row = 0;

        Connection connection = null;
        ResultSet rs = null;
        try {
            Class.forName("com.facebook.presto.jdbc.PrestoDriver");
            connection = DriverManager.getConnection("jdbc:presto://10.200.1.194:8080/mongodb/test","root",null);
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select t1.randName,t2.randNum from test4 t2 left join test1 t1 on t1.randNum = t2.randNum");
            //rs = stmt.executeQuery("select * from test2");
            while (rs.next()){
                //System.out.println(rs.getString(1));
                row ++;
            }
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        System.out.println(System.currentTimeMillis() -start);
        return row;

    }


}
