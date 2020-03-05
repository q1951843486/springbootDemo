package com.meiya.time_task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(value = "com.meiya.time_task.mapper")
public class TimeTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeTaskApplication.class, args);
    }

}
