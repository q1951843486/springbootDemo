package com.example.modul.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.PipedReader;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @ClassName Student
 * @Author Administrator
 * @date 2020.03.12 16:11
 */
@Data
public class Student extends BaseRowModel implements Serializable {
    public static final long serialVersionUID = 11L;
    @ExcelProperty(value = {"学号"}, index = 0)
    private Long studentId;

    @ExcelProperty(value = {"姓名"}, index = 1)
    private String studentName;

    @ExcelProperty(value = {"准考证号"}, index = 2)
    private long examId;

    @ExcelProperty(value = {"身份证号"}, index = 3)
    private String idNumber;

    @ExcelProperty(value = {"生源地"}, index = 4)
    private String cradle;

    @ExcelProperty(value = {"家庭住址"}, index = 5)
    private String address;

    @ExcelProperty(value = {"入学年份"}, index = 6)
    private Integer enroll_year;

    @ExcelProperty(value = {"班级Id"}, index = 7)
    private Integer classId;

    @ExcelProperty(value = {"房间Id"}, index = 8)
    private Integer roomId;

    @ExcelProperty(value = {"性别"}, index = 9)
    private String gender;

    @ExcelProperty(value = {"出生日期"}, index = 10)
    private Date birthday;

    @ExcelProperty(value = {"手机号"}, index = 11)
    private String phone;

    @ExcelProperty(value = {"邮箱"}, index = 12)
    private String mail;

    @ExcelProperty(value = {"激活状态"}, index = 13)
    private Integer infoStatus;

    @ExcelProperty(value = {"报到状态"}, index = 14)
    private Integer payStatus;

    @ExcelProperty(value = {"注册状态"}, index = 15)
    private Integer registerStatus;

    @ExcelProperty(value = {"交通工具"}, index = 16)
    private String transportation;

    @ExcelProperty(value = {"到达日期"}, index = 16)
    private Date date;
}
