package com.example.controller;

import com.example.modul.excel.Student;
import com.example.service.StudentExcelService;
import com.example.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @ClassName ExcelController
 * @Author Administrator
 * @date 2020.03.12 16:21
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private StudentExcelService studentExcelService;

    @RequestMapping("generate")
    public ResultVo generate(){

        Student student = new Student();
        student.setStudentId(1L);
        student.setStudentName("李四");
        String path =studentExcelService.generate(student);
        ResultVo resultVo = new ResultVo();
        if(ObjectUtils.isEmpty(path)){
           resultVo.setCode(400);
           resultVo.setMsg("错误");
        }
        resultVo.setCode(200);
        resultVo.setMsg("成功 ");
        resultVo.setResult(path);
        return  resultVo;


    }



}
