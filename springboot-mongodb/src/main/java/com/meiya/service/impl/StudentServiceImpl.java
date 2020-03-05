package com.meiya.service.impl;

import com.meiya.modul.Student;
import com.meiya.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @ClassName StudentServiceImpl
 * @Author Administrator
 * @date 2020.03.05 10:58
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void add(Student student) {
       redisTemplate.opsForValue().set("student",student);
    }

    @Override
    public Student query() {
        return (Student) redisTemplate.opsForValue().get("student");
    }
}
