package com.example.controller;

import com.example.modul.Random;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description
 * @ClassName Test
 * @Author Administrator
 * @date 2020.05.25 15:38
 */
public class Test {
    public static void main(String[] args) {
        Random random = new Random();
        boolean empty = StringUtils.isEmpty(random);
        System.out.println(empty);


        try {
            Class tClass = random.getClass();

            //拿到全部属性
            Field[] fields = tClass.getDeclaredFields();
            for (Field field:fields) {
                String name = field.getName();
                //将属性名字的首字母大写
                name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                Method m = tClass.getMethod("get"+name);
                Object invoke = m.invoke(random);
                if (ObjectUtils.allNotNull(invoke)){
                    System.out.println(invoke);
                    //QueryBuilders.termQuery(name,invoke);
                }

            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
