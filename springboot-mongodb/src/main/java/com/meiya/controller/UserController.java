package com.meiya.controller;

import com.meiya.common.Constants;
import com.meiya.common.SequenceFactory;
import com.meiya.modul.User;
import com.meiya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName UserController
 * @Author Administrator
 * @date 2020.03.04 17:02
 */
@Controller
public class UserController {


    @Resource
    private UserService userService;
    @Autowired
    private SequenceFactory sequenceFactory;

    @ResponseBody
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") long id){
        return userService.queryUserById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public User add(){
        User user = new User();
        user.setName("李四");
        user.setAge(1);
        user.setSex("男");
        user.setId(sequenceFactory.incrementHash(Constants.TABLE,Constants.TABLE_USER,1L));
        userService.add(user);
        return user;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public User update(){
        User user = userService.queryUserById(1);
        user.setName("张三");
        user.setAge(22);
        User user1 =userService.updateUserById(user);
        return user1;
    }
    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(){
        int count = userService.deleteUserById(1);
        if (count == 1){

            return "成功";

        }
        return "失败";

    }



}
