package com.cn.demo.arangoSpark;

import com.cn.demo.arangoSpark.commons.SparkOperationArangoDbUtils;
import com.cn.demo.arangoSpark.entity.Test1;
import com.cn.demo.arangoSpark.function.ArangoSparkTest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description
 * @ClassName SparkRunner
 * @Author Administrator
 * @date 2020.05.09 15:13
 */
@ComponentScan
public class SparkRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("spark启动");
        SparkOperationArangoDbUtils.loadArangodbToSparkToArangodb("_system","test_col2", Test1.class,"test_coll","id == 1");


    }
}
