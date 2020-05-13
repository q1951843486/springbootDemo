package com.cn.demo.arangoSpark;

import com.cn.demo.arangoSpark.commons.SparkOperationArangoDbUtils;
import com.cn.demo.arangoSpark.entity.Test1;

/**
 * @Description
 * @ClassName SparkTest
 * @Author Administrator
 * @date 2020.05.09 16:06
 */
public class SparkTest {
    public static void main(String[] args) {
        SparkOperationArangoDbUtils.loadArangodbToSparkToArangodb("_system","test_col2", Test1.class,"test_coll","id == 1");

    }
}
