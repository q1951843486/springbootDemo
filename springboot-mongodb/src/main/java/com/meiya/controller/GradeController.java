package com.meiya.controller;

import com.meiya.common.Constants;
import com.meiya.common.SequenceFactory;
import com.meiya.modul.Grade;
import com.meiya.service.GradeService;
import com.meiya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName GradeController
 * @Author Administrator
 * @date 2020.03.06 16:58
 */
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private SequenceFactory sequenceFactory;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/grade")
    @ResponseBody
    public Grade add(){

        Grade grade = new Grade();
        grade.setGradeName("三年级");
        grade.setId(sequenceFactory.incrementHash(Constants.TABLE,Constants.TABLE_GRADE,1L));
        Map map = new HashMap(16);
        map.put("pageNo",1);
        map.put("pageSize",100);
        grade.setUsers(userService.queryUserPageList(map));
        return gradeService.add(grade);
    }



}
