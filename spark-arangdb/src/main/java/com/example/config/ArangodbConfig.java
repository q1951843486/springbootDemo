package com.example.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @ClassName ArangodbConfig
 * @Author Administrator
 * @date 2020.05.06 17:21
 */
@Configuration
public class ArangodbConfig {


    @Value("arangodb.hosts")
    private String hosts;

    @Value("arangdob.user")
    private String user;

    @Value("arangodb.password")
    private String password;



    @Bean
    public SparkConf getSparkConf() {


        SparkConf SparkConf = new SparkConf()
                .set("arangodb.hosts", "10.200.1.183:8529").set("database","_system");

        return SparkConf;

    }




}
