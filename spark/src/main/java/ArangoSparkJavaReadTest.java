/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.arangodb.entity.BaseDocument;
import entity.Test2;
import entity.TestJavaEntity;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.Protocol;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD;

public class ArangoSparkJavaReadTest {

	private static final String DB = "_system";
	private static final String COLLECTION = "spark_test_col";
	private static ArangoDB arangoDB;
	private static JavaSparkContext sc;

	@BeforeClass
	public static void setup() throws IOException {

		SparkConf conf = new SparkConf(false).setMaster("local")// .setMaster("spark://10.200.1.193:7077")
				.setAppName("test").set("spark.executor.memory", "2147483648")// 2G*1024*1024*1024
				.set("spark.scheduler.mode", "FAIR").set("spark.cores.max", "12").set("spark.deploy.defaultCores", "6");
//		SparkConf conf = new SparkConf(false).setMaster("local").setAppName("test");
		// Set values from arangodb.properties to spark context
		sc = new JavaSparkContext(conf);
	}

	@AfterClass
	public static void teardown() {
		sc.stop();
////		arangoDB.db(DB).drop();
//		arangoDB.shutdown();
	}



	@Test
	public void loadAll() {
		//ArangoJavaRDD<String> rdd = ArangoSpark.load(sc, "test2", new ReadOptions().database(DB),String.class);


		//rdd.foreach(s -> System.out.println(s));
		//System.out.println("=======================" + rdd.count());

		SparkSession sparkSession = SparkSession.builder().getOrCreate();

		//Dataset<Row> rowDataset = sparkSession.createDataFrame(rdd, Object.class);
		ArangoJavaRDD<Test2> rdd2 = ArangoSpark.load(sc, "test2", new ReadOptions().database(DB).protocol(Protocol.HTTP_JSON),
				Test2.class);
		System.out.println("*********************" + rdd2.count());
		rdd2.foreach(s -> System.out.println(s));
		Dataset<Row> rowDataset1 = sparkSession.createDataFrame(rdd2, Test2.class);
		//rowDataset.createOrReplaceTempView("test2");
		rowDataset1.createOrReplaceTempView("test2");

		Dataset<Row> sql = sparkSession.sql("select * from test2");
		System.out.println(sql.count());
		//Dataset<Row> sql = sparkSession.sql("select t1.randName as t1name,t2.randName as t2name,t1.randNum as t1num,t2.randNum as t2num from test2 t2 join test1 t1 on t1.randNum = t2.randNum");

		//System.out.println(sql.count());
		//assertThat(rdd.count(), is(100L));
	}

	@Test
	public void loadWithFilterStatement() {
		ArangoJavaRDD<TestJavaEntity> rdd = ArangoSpark.load(sc, COLLECTION, new ReadOptions().database(DB),
				TestJavaEntity.class);
		ArangoJavaRDD<TestJavaEntity> rdd2 = rdd.filter("doc.test <= 2147483647");
		System.out.println("=======================" + rdd.count());
		System.out.println("=======================" + rdd2.count());
		assertThat(rdd2.count(), is(100L));
	}

	@Test
	public void loadAllWithHTTP() {
		ArangoJavaRDD<TestJavaEntity> rdd = ArangoSpark.load(sc, COLLECTION,
				new ReadOptions().database(DB).protocol(Protocol.HTTP_JSON), TestJavaEntity.class);
		assertThat(rdd.count(), is(100L));
	}

	@Test
	public void loadAllWithLoadBalancing() {
		// set acquireHostList to false, due our tests are running inside a nested
		// docker container. Settings this option to true will result in wrong ports
		// beeing used.
		// So we need to set those settings explicitly inside:
		// 'src/test/resources/arangodb.properties' file
		ArangoJavaRDD<TestJavaEntity> rdd = ArangoSpark.load(sc, COLLECTION,
				new ReadOptions().user("root").password("test").database(DB).acquireHostList(false)
						.loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN),
				TestJavaEntity.class);

		assertThat(rdd.count(), is(100L));
	}
}
