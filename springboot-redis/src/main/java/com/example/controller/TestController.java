package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.modul.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName TestController
 * @Author Administrator
 * @date 2020.03.23 11:36
 */
@Controller
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate ;


    /**
     * 第一种方案  将对象声明为可序列话的 然后直接存储对象list
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/add")
    public List addList(){



        redisTemplate.opsForList().rightPushAll("students",getList());
        return getList();

    }
    @RequestMapping(value = "/get")
    @ResponseBody
    public List get(){

        List<Student> students =redisTemplate.opsForList().range("students",0,-1);
        return students;

    }


    /**
     * 第二种方案
     * 现将list集合中的元素序列化成json 后存储list<String>
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addJson")
    public List addJsonList(){

        List<Student> students =getList();
        List<String> jsonList =JsonUtils.setList(students);
        redisTemplate.opsForList().rightPushAll("student",jsonList);
        return jsonList;
    }


    @ResponseBody
    @RequestMapping(value = "/getJson")
    public List<Student> getJsonList(){

        return JsonUtils.getList(redisTemplate.opsForList().range("student",0,-1));

    }


    /**
     * 第三种方案  直接将list转换成json  存入redis 类型String
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addList")
    public List addListJson(){

        List<Student> students =getList();

        redisTemplate.opsForValue().set("studentList",JSON.toJSONString(students));
        return students;
    }


    @ResponseBody
    @RequestMapping(value = "/getList")
    public List get1(){


        String s  = (String) redisTemplate.opsForValue().get("studentList");
        List<Student> list = JSON.parseArray(s,Student.class);
        return list;
    }






    public static List getList(){
        List<Student> students = new ArrayList<>();

        Student student = new Student();
        student.setName("lisi");
        student.setAge(11);

        Student student1 = new Student();
        student1.setName("lisi1");
        student1.setAge(111);


        Student student2 = new Student();
        student2.setName("lisi2");
        student2.setAge(112);

        students.add(student1);
        students.add(student2);
        students.add(student);
        System.out.println(students);
        return students;


    }


}
