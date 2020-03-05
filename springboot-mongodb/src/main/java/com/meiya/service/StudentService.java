package com.meiya.service;

import com.meiya.modul.Student;

/**
 * @Description
 * @ClassName StudentService
 * @Author Administrator
 * @date 2020.03.05 10:57
 */
public interface StudentService {
    void add(Student student);

    Student query();
}
