package com.meiya.controller;

import com.meiya.common.Constants;
import com.meiya.common.SequenceFactory;
import com.meiya.modul.Student;
import com.meiya.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @Description
 * @ClassName StudentController
 * @Author Administrator
 * @date 2020.03.05 10:56
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SequenceFactory sequenceFactory;

    @RequestMapping(value = "/stu/add")
    @ResponseBody
    public Student add() throws Exception {

        Student student = new Student();
        student.setId(sequenceFactory.incrementHash(Constants.TABLE,Constants.TABLE_STUDENT,1L));
        student.setName("李四");
        student.setAge(11);
        student.setPhone("1111111");
        studentService.add(student);
        return student;

    }
    @RequestMapping(value = "/query")
    @ResponseBody
    public Student query(){
        return studentService.query();
    }



}
