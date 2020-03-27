package com.meiya.time_task.controller;

import com.meiya.time_task.moduls.TimeTask;
import com.meiya.time_task.service.TimeTaskServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName TimeTaskController
 * @Author Administrator
 * @date 2020.03.27 17:40
 */
@Controller
public class TimeTaskController {

    @Resource
    private TimeTaskServiceImpl timeTaskService;


    @RequestMapping(value = "/a")
    @ResponseBody
    public TimeTask insert(){

        TimeTask timeTask =timeTaskService.insert();
        return timeTask;
    }

}
