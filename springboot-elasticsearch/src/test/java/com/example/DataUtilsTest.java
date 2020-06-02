package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @ClassName DataUtilsTest
 * @Author Administrator
 * @date 2020.06.01 11:07
 */
public class DataUtilsTest {
    public static void main(String[] args) {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time="2020-03-01 00:00:00";
        String time1="2020-04-01 00:00:00";
        String time2="2020-05-01 00:00:00";
        String time3="2020-06-01 00:00:00";
        Date date = null;
        try {
            date = format.parse(time3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //日期转时间戳（毫秒）
        long time4=date.getTime();
        System.out.print("Format To times:"+time4);
    }
}
