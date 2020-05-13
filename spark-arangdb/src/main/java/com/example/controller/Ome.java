package com.example.controller;

import org.apache.spark.sql.types.StringType;
import org.apache.spark.sql.types.StructType;

/**
 * @Description
 * @ClassName Ome
 * @Author Administrator
 * @date 2020.05.07 14:00
 */
public class Ome {
    public static void main(String[] args) {
        StructType structType = new StructType();
        structType.add("name", "StringType");
        //structType.add("randNum",StringType.productPrefix());
        System.out.println(structType);
    }
}
