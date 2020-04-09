package com.meiya.controller;

import com.meiya.common.Constants;
import com.meiya.common.SequenceFactory;
import com.meiya.modul.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName RedisController
 * @Author Administrator
 * @date 2020.04.01 17:57
 */
@RestController
public class RedisController {

    @Resource
    public SequenceFactory sequenceFactory;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/redis/add")
    public Student add(){

        Student student = new Student();
        student.setId(sequenceFactory.incrementHash(Constants.TABLE,Constants.TABLE_STUDENT,1L));
        student.setName("李四");
        student.setAge(11);
        student.setPhone("1111111");
        redisTemplate.opsForValue().set("a",student);
        return student;
    }


}
