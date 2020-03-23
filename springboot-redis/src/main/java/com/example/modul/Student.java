package com.example.modul;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @ClassName Student
 * @Author Administrator
 * @date 2020.03.23 11:35
 */
@Data
public class Student implements Serializable {

    private String name;
    private Integer age;



}
