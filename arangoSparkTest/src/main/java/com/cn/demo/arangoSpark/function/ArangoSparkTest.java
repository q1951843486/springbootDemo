/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.demo.arangoSpark.function;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

import com.arangodb.Protocol;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import com.arangodb.spark.WriteOptions;
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ArangoSparkTest {

	private static final String DB = "_system";
	private static JavaSparkContext sc;
	private static SparkSession spark;
	
	
	public void getJavaSparkContext() {
		SparkConf conf = new SparkConf(false).setMaster("local")
				.setAppName("test");
		sc = new JavaSparkContext(conf);
		// 设置日志输出等级 有一些启动信息的日志没必要看
		sc.setLogLevel("WARN");
	}
	
	public void getSparkSession() {
		spark = SparkSession
                .builder()
                .appName("test_JavaSparkSQL")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();
	}
	
	public void destructionSparkSession() {
		spark.stop();
	}
	
	public void destructionJavaSparkContext() {
		sc.stop();
	}
	
	/**
	 * 
	 * Description: 读取arangodb数据到spark，通过spark sql查询结果 再把结果写入新的集合
	 * Title: loadArangodbToSparkToArangodb1
	 * @param <T>
	 * @param queryCollection  查询集合 
	 * @param querycollectionClass
	 * @param writeCollection 写入的集合
	 * @param sqlConditions  sql语句
	 */
	@SuppressWarnings("unchecked")
	public <T> void loadArangodbToSparkToArangodb1(String queryCollection,Class<T> querycollectionClass,String writeCollection,String sqlConditions) {
		getJavaSparkContext();
		long start = System.currentTimeMillis();
		ArangoJavaRDD<T> rdd = ArangoSpark.load(sc, queryCollection, new ReadOptions().database(DB).protocol(Protocol.HTTP_JSON),
				querycollectionClass);
		long end = System.currentTimeMillis();
		log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",queryCollection,rdd.count(),(end - start));
		getSparkSession();
		Dataset<Row> dataset = spark.createDataFrame(rdd, querycollectionClass);
		dataset.createOrReplaceTempView(writeCollection);
		dataset = spark.sql("SELECT * FROM " + writeCollection + (StringUtils.isEmpty(sqlConditions) ? "" : " where " + sqlConditions));
		dataset.show();
		String[] columns = dataset.columns();
		List<Row> rows = dataset.collectAsList(); 
		List<T> jsonArray = new ArrayList<T>();
		/*for (Row row : rows) {
			JSONObject json = new JSONObject();
			for (int i = 0; i < columns.length; i++) {
				json.put(columns[i], row.get(i));
			}
			jsonArray.add((T) JSONObject.toBean(json, querycollectionClass));
		}*/
		for (Row r : rows) {

			JSONObject jsonObject = new JSONObject();
			for (int i = 0; i <columns.length ; i++) {

				jsonObject.put(columns[i], r.get(i));
			}
			jsonArray.add(jsonObject.toJavaObject(querycollectionClass));

		}
		System.out.println(jsonArray.get(0).toString());
		JavaRDD<T> documents = sc.parallelize(jsonArray);
		start = System.currentTimeMillis();
		ArangoSpark.save(documents, writeCollection, new WriteOptions().database(DB).protocol(Protocol.HTTP_JSON));
		end = System.currentTimeMillis();
		log.info("☆☆☆☆☆☆☆ spark写入arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",writeCollection,documents.count(),(end - start));
		destructionSparkSession();
		destructionJavaSparkContext();
	}
	
	
	public <T> void loadArangodbToSparkToArangodb(String queryCollection,Class<T> querycollectionClass,String writeCollection) {
		getJavaSparkContext();
		long start = System.currentTimeMillis();
		ArangoJavaRDD<T> rdd = ArangoSpark.load(sc, queryCollection, new ReadOptions().database(DB),
				querycollectionClass);
		long end = System.currentTimeMillis();
		log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",queryCollection,rdd.count(),(end - start));
		start = System.currentTimeMillis();
		ArangoSpark.save(rdd, writeCollection, new WriteOptions().database(DB).protocol(Protocol.HTTP_JSON));
		end = System.currentTimeMillis();
		log.info("☆☆☆☆☆☆☆ spark写入arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",writeCollection,rdd.count(),(end - start));
		destructionJavaSparkContext();
	}
	
	/**
	 * 
	 * Description: spark读取arangodb数据
	 * Title: loadAll 
	 * @param <T>
	 * @param collection  集合名
	 * @param collectionClass  集合对象
	 */
	public <T> void loadArangodbToSpark(String collection,Class<T> collectionClass) {
		getJavaSparkContext();
		long start = System.currentTimeMillis();
		ArangoJavaRDD<T> rdd = ArangoSpark.load(sc, collection, new ReadOptions().database(DB),
				collectionClass);
		long end = System.currentTimeMillis();
		log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",collection,rdd.count(),(end - start));
		destructionJavaSparkContext();
	}
	
	/**
	 * 
	 * Description: spark写入arangodb数据
	 * Title: loadSparkToArangodb
	 * @param <T>
	 * @param collection  集合名
	 * @param docs 集合数据
	 */
	public <T> void loadSparkToArangodb(String collection,List<T> docs) {
		getJavaSparkContext();
		long start = System.currentTimeMillis();
		JavaRDD<T> documents = sc.parallelize(docs);
		ArangoSpark.save(documents, collection, new WriteOptions().database(DB).protocol(Protocol.HTTP_JSON));
		long end = System.currentTimeMillis();
		log.info("☆☆☆☆☆☆☆ spark写入arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",collection,documents.count(),(end - start));
		destructionJavaSparkContext();
	}
}
