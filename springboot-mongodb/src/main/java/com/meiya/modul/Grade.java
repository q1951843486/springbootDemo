package com.meiya.modul;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @ClassName Grade
 * @Author Administrator
 * @date 2020.03.06 16:56
 */
@Data
public class Grade {
    private Long id;
    private String gradeName;
    List<Student> students;
    List<Teacher> teachers;
    List<User> users;
}
