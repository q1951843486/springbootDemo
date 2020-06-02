package com.example.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.naming.Name;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description
 * @ClassName InvokeUtils
 * @Author Administrator
 * @date 2020.05.25 15:50
 */
@Slf4j
public class InvokeUtils<T> {


    /**
     * 判断对象属性是否全部为空
     * @param t
     * @param <T>
     * @return
     */
    public static<T> Boolean notAllNull(T t){
        Boolean flag = false;
        try {
            Class<? extends Object> tClass = t.getClass();
            //拿到全部属性
            Field[] fields = tClass.getDeclaredFields();
            for (Field field:fields) {
                field.setAccessible(true);
                String name = field.getName();
                //将属性名字的首字母大写
                name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                Method m = tClass.getMethod("get"+name);
                Object invoke = m.invoke(t);
                if (ObjectUtils.allNotNull(invoke)){
                    flag = true;
                    break;
                }

            }

        }catch (Exception e){
            System.out.println(e);
            log.error("判断对象属性是否全部为空出错" + e.getMessage());
        }
        return flag;
    }



    public static <T> boolean isAnalyzer(Field field){


        org.springframework.data.elasticsearch.annotations.Field annotation = field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
        String analyzer = annotation.analyzer();

        if (!ObjectUtils.allNotNull(analyzer)){
            return false;
        }
        if (StringUtils.equals(analyzer,"ik_max_word")){
            return true;
        }

        return false;
    }


    public Object getAttributesByName(T t,String attributesName){

        try{
            Class<? extends Object> tClass = t.getClass();

            //得到所有属性
            Field[] field = tClass.getDeclaredFields();

            /**
             * 这里只需要 id 这个属性，所以直接取 field[0] 这
             * 一个，如果id不是排在第一位，自己取相应的位置，
             * 如果有需要，可以写成for循环，遍历全部属性
             */

            for (int i = 0; i < field.length; i++) {
                field[i].setAccessible(true);

                //获取属性的名字
                String name = field[0].getName();
                if (StringUtils.equals(name,attributesName)){

                    name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());

                    //整合出 getId() 属性这个方法
                    Method m = tClass.getMethod("get"+name);
                    Object invoke = m.invoke(t);
                    return invoke;

                }
            }
            System.out.println("类中没有该属性");
            return null;
        }catch(Exception e){
            System.out.println("获取id失败");
            return null;
        }
    }






}
