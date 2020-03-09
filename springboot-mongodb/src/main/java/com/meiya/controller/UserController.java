package com.meiya.controller;

import com.meiya.common.Constants;
import com.meiya.common.PageListVo;
import com.meiya.common.SequenceFactory;
import com.meiya.modul.User;
import com.meiya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public String del(@PathVariable("id")Long id){
        int count = userService.deleteUserById(id);
        if (count == 1){

            return "成功";

        }
        return "失败";
    }


    /**
     * 分页查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/getPageList")
    public PageListVo<User> getPageList(){

        int pageNo  = 1;
        int pageSize = 100;
        Map<String,Object> parmMap = new HashMap<>(16);
        parmMap.put("pageNo",pageNo);
        parmMap.put("pageSize",pageSize);
        List<User> userList = userService.queryUserPageList(parmMap);
        PageListVo<User> pageListVo = new PageListVo<>();
        pageListVo.setPageNo(pageNo);
        pageListVo.setPageSize(pageSize);
        pageListVo.setPageList(userList);
        if (ObjectUtils.isEmpty(userList)){
            pageListVo.setMsg("失败");
        }
        pageListVo.setMsg("成功");
        return pageListVo;

    }


    /**
     * 查询 name and age
     *
     * @param name
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/{name}/{age}")
    public List getUserByNameAndAge(@PathVariable(value = "name")String name,@PathVariable(value = "age")String age){
        Map parmMap = new HashMap(16);
        parmMap.put("name",name);
        parmMap.put("age",age);


        return userService.queryUserListByNameAndAge(parmMap);

    }

    /**
     * 查询name or age
     *
     *
     */
    @RequestMapping(value = "/user/a")
    @ResponseBody
    public List getUserByNameOrAge(){
        Map parmMap = new HashMap(16);
        parmMap.put("name","张三");
        parmMap.put("age",0);
        return userService.queryUserListByNameOrAge(parmMap);
    }


    /**
     * gte: 大于等于,lte小于等于…注意查询的时候各个字段的类型要和mongodb中数据类型一致
     * 查询 条件>=
     */
    @RequestMapping(value = "/users/{age}")
    @ResponseBody
    public PageListVo<User> getUserListByGteAge(@PathVariable(value = "age")Integer age){
        Map map = new HashMap(16);
        map.put("age",age);
        map.put("pageNo",1);
        map.put("pageSize",10);
        List list =userService.queryUserListByGteAge(map);
        PageListVo<User> userPageListVo = new PageListVo<>();
        userPageListVo.setPageNo(1);
        userPageListVo.setPageSize(10);
        userPageListVo.setPageList(list);
        if (ObjectUtils.isEmpty(list)){
            userPageListVo.setMsg("失败");

        }
        userPageListVo.setMsg("成功");
        return userPageListVo;
    }

    @ResponseBody
    @RequestMapping(value = "/user/sort")
    public List getUserListSortByAge(){

        return userService.queryUserListSortByAge();


    }
    @RequestMapping(value = "/user/{name}")
    @ResponseBody
    public List getUserListOrderByName(@PathVariable(value = "name")String name){

        return userService.queryUserListOrderByName(name);


    }




}
