package com.cn.demo.arangoSpark;

import java.util.ArrayList;
import java.util.List;

import com.cn.demo.arangoSpark.entity.Test3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cn.demo.arangoSpark.entity.Test1;
import com.cn.demo.arangoSpark.entity.TestJavaEntity;
import com.cn.demo.arangoSpark.function.ArangoSparkTest;

@SpringBootTest
class ArangoSparkTestApplicationTests {
	@Autowired
	private ArangoSparkTest  arangoSparkTest;
	
	@Test
	void loadArangodbToSparkToArangodb2() {
		arangoSparkTest.loadArangodbToSparkToArangodb1("test_col2", Test1.class,"test_coll","id == 1");
	}
	
	@Test
	void loadArangodbToSparkToArangodb() {
		arangoSparkTest.loadArangodbToSparkToArangodb1("spark_test_col", TestJavaEntity.class,"spark_test_coll","test == 11111111");
	}
	
	@Test
	void loadArangodbToSpark() {
		arangoSparkTest.loadArangodbToSpark("test_col3", Test3.class);
	}

	
	@Test
	void loadSparkToArangodb() {
		List<TestJavaEntity> docs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			docs.add(new TestJavaEntity());
		}
		arangoSparkTest.loadSparkToArangodb("spark_test_col", docs);
	}
}
