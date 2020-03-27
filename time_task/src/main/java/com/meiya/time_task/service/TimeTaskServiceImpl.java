package com.meiya.time_task.service;

import com.meiya.time_task.mapper.TimeTaskMapper;
import com.meiya.time_task.moduls.TimeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @ClassName TimeTaskServiceImpl
 * @Author Administrator
 * @date 2020.03.27 17:35
 */
@Service
public class TimeTaskServiceImpl {
    @Autowired
    private TimeTaskMapper timeTaskMapper;


    public TimeTask insert(){
        TimeTask timeTask = new TimeTask();
        timeTask.setCrom("a");
        timeTaskMapper.insertTimeTask(timeTask);
        System.out.println(timeTask);
        return timeTask;

    }



}
