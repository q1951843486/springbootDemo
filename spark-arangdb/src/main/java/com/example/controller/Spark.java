package com.example.controller;

import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD;
import com.arangodb.springframework.core.template.ArangoTemplate;
import com.example.config.ArangodbConfig;
import com.example.entity.Test1;
import com.example.entity.Test2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StringType;
import org.apache.spark.sql.types.StructType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName Spark
 * @Author Administrator
 * @date 2020.05.06 17:04
 */
@Component
public class Spark {
    public static void main(String[] args) {

       /* StructType structType = new StructType();
        structType.add("name", StringType.productPrefix());
        structType.add("randNum",StringType.productPrefix());*/
        ArangodbConfig arangodbConfig = new ArangodbConfig();
        SparkConf sparkConf = arangodbConfig.getSparkConf();
        SparkSession sparkSession = SparkSession.builder().master("spark://10.200.1.195:7077").appName("ArangoSparkConnectorLk").config(sparkConf).getOrCreate();
        //JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        //List<Test1> docs = (List<Test1>) arangoTemplate.findAll(Test1.class);
        //JavaRDD<Test1> test1JavaRDD = javaSparkContext.parallelize(docs);
        //ArangoSpark.save(test1JavaRDD,"Test1",new WriteOptions().database("test"));
        //ArangoSpark.load(javaSparkContext, "test1", Test1.class);

        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());

        ReadOptions readOptions =  new ReadOptions().database("_system").collection("test2");
        ArangoJavaRDD<Test2> dataset = ArangoSpark.load(javaSparkContext, "test2",readOptions,Test2.class);
        //ArangoJavaRDD<Test2> dataset2 = ArangoSpark.load(javaSparkContext, "test2", Test2.class);

        //Dataset<Row> dataset1 =sparkSession.sql("select * from test1");
        //System.out.println(dataset1);
        //Dataset<Row> dataset2 =sparkSession.sql("select * from test2");


        //ClassTag<Test1> test1ClassTag = ArangoSpark.load(javaSparkContext, "test1", Test1.class).classTag();

        Dataset<Row> test1Dataset = sparkSession.createDataFrame(dataset,Test1.class);
        //test1Dataset.foreach(test1 -> System.out.println(test1));
        System.out.println();






    }






}