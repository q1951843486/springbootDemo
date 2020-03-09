package com.meiya.service;

import com.meiya.modul.Grade;

import java.util.List;

/**
 * @Description
 * @ClassName GradeService
 * @Author Administrator
 * @date 2020.03.06 16:59
 */
public interface GradeService {
    Grade add(Grade grade);

    List<Grade> queryGradeListByName(String name);
}
