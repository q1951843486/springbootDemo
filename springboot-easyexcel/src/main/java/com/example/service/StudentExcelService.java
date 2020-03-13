package com.example.service;

import com.example.modul.excel.Student;

/**
 * @Description
 * @ClassName StudentExcelService
 * @Author Administrator
 * @date 2020.03.12 16:27
 */
public interface StudentExcelService {
    /**
     * 生成excel
     * @param student
     */
    String generate(Student student);
}
