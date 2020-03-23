package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.modul.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName JsonUtils
 * @Author Administrator
 * @date 2020.03.23 11:37
 */
public class JsonUtils {


    public static <T> List<T> getList(List<String> parmList){
        List<T> list = new ArrayList<>();

        for (String str:parmList) {
            list.add((T)JSON.parse(str));
        }


        return list;
    }
    public static <T> List<String> setList(List<T> list){
        List<String> jsonList = new ArrayList<>();
        for (Object o :list) {
            jsonList.add(JSON.toJSONString(o));
        }
        System.out.println(jsonList);
        return jsonList;

    }

    public static void main(String[] args) {
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

        List<String> jsonList= setList(students);
        System.out.println(jsonList);


    }



}
