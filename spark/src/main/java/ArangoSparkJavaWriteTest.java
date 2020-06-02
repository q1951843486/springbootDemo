/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import entity.TestJavaEntity;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.Protocol;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.WriteOptions;

public class ArangoSparkJavaWriteTest {

	private static final String DB = "_system";
	private static final String COLLECTION = "spark_test_col";
	private static ArangoDB arangoDB;
	private static JavaSparkContext sc;

	@BeforeClass
	public static void setup() {
		InputStream configFileStream = ClassLoader.getSystemClassLoader().getResourceAsStream("arangodb.properties");
		arangoDB = new ArangoDB.Builder().loadProperties(configFileStream).build();
		SparkConf conf = new SparkConf(false).setMaster("local")
				.setAppName("test");
		sc = new JavaSparkContext(conf);
//		try {
//			arangoDB.db(DB).drop();
//		} catch (ArangoDBException e) {
//		}
//		arangoDB.createDatabase(DB);
		try {
			arangoDB.db(DB).collection(COLLECTION).drop();
		} catch (ArangoDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arangoDB.db(DB).createCollection(COLLECTION);
		
	}

	@AfterClass
	public static void teardown() {
		sc.stop();
//		arangoDB.db(DB).drop();
		arangoDB.shutdown();
	}

	private void checkDocumentCount(Long count) {
		assertThat(arangoDB.db(DB).collection(COLLECTION).count().getCount(),
				is(count));
	}

	@Test
	public void saveJavaRDD() {
		checkDocumentCount(0L);
		List<TestJavaEntity> docs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			docs.add(new TestJavaEntity());
		}
		JavaRDD<TestJavaEntity> documents = sc.parallelize(docs);
		ArangoSpark.save(documents, COLLECTION, new WriteOptions().database(DB).protocol(Protocol.HTTP_JSON));
		checkDocumentCount(100L);
	}
}
