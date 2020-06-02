package com.cn.demo.arangoDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.arangodb.springframework.core.template.ArangoTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.arangodb.ArangoCursor;
import com.arangodb.springframework.core.ArangoOperations;
import com.arangodb.util.MapBuilder;
import com.cn.demo.arangoDB.entity.Test1;
import com.cn.demo.arangoDB.repository.Test1Repository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArangoDbPerformanceTestApplicationTests {

	@Autowired
	private Test1Repository repository;
	
	@Autowired
	private ArangoOperations operations;

	/**
	 * 
	 * Description: 根据字段randNum，内连查询test2（30万）和test1（1200万）
	 * Title: test_Test2InnerJoinTest1
	 */
	@Test
	public void test_Test2InnerJoinTest1() {
		long startTime = System.currentTimeMillis();
		List<Map<String, Object>> list = repository.getTest2InnerJoinTest1();
		//System.out.println(repository.getTest2InnerJoinTest1());
        System.out.println(list.size());
		long endTime = System.currentTimeMillis();
		System.out.println("Test2InnerJoinTest1_第一次查询执行时间： " + (endTime - startTime) + "毫秒");
		
//		startTime = System.currentTimeMillis();
//		list = repository.getTest2InnerJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test2InnerJoinTest1_第二次查询执行时间： " + (endTime - startTime) + "毫秒");
//		
//		startTime = System.currentTimeMillis();
//		list = repository.getTest2InnerJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test2InnerJoinTest1_第三次查询执行时间： " + (endTime - startTime) + "毫秒");
		
		//System.out.println("Test2InnerJoinTest1_查询记录数： " + list.size());
	}
	
	/**
	 * 
	 * Description: 根据字段randNum，内连查询test3（300万）和test1（1200万）
	 * Title: test_Test3InnerJoinTest1
	 */
	@Test
	public void test_Test3InnerJoinTest1() {
		long startTime = System.currentTimeMillis();
		List<Map<String, Object>> list = repository.getTest3InnerJoinTest1();
		long endTime = System.currentTimeMillis();
		System.out.println("Test3InnerJoinTest1_第一次查询执行时间： " + (endTime - startTime) + "毫秒");
		
//		startTime = System.currentTimeMillis();
//		list = repository.getTest3InnerJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test3InnerJoinTest1_第二次查询执行时间： " + (endTime - startTime) + "毫秒");
//
//		startTime = System.currentTimeMillis();
//		list = repository.getTest3InnerJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test3InnerJoinTest1_第三次查询执行时间： " + (endTime - startTime) + "毫秒");
		
		System.out.println("Test3InnerJoinTest1_查询记录数： " + list.size());
	}
	/**
	 * 
	 * Description: 根据字段randNum，左连查询test2（30万）和test1（1200万）
	 * Title: test_Test2LeftJoinTest1
	 */
	@Test
	public void test_Test2LeftJoinTest1() {
		long startTime = System.currentTimeMillis();
		List<Map<String, Object>> list = repository.getTest2LeftJoinTest1();
		long endTime = System.currentTimeMillis();
		System.out.println("Test2LeftJoinTest1_第一次查询执行时间： " + (endTime - startTime) + "毫秒");
		
//		startTime = System.currentTimeMillis();
//		list = repository.getTest2LeftJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test2LeftJoinTest1_第二次查询执行时间： " + (endTime - startTime) + "毫秒");
//		
//		startTime = System.currentTimeMillis();
//		list = repository.getTest2LeftJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test2LeftJoinTest1_第三次查询执行时间： " + (endTime - startTime) + "毫秒");
		
		System.out.println("Test2LeftJoinTest1_查询记录数： " + list.size());
	}
	/**
	 * 
	 * Description: 根据字段randNum，左连查询test3（300万）和test1（1200万）
	 * Title: test_Test3LeftJoinTest1
	 */
//	@Test
	public void test_Test3LeftJoinTest1() {
		long startTime = System.currentTimeMillis();
		List<Map<String, Object>> list = repository.getTest3LeftJoinTest1();
		long endTime = System.currentTimeMillis();
		System.out.println("Test3LeftJoinTest1_第一次查询执行时间： " + (endTime - startTime) + "毫秒");
		
//		startTime = System.currentTimeMillis();
//		list = repository.getTest3LeftJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test3LeftJoinTest1_第二次查询执行时间： " + (endTime - startTime) + "毫秒");
//		
//		startTime = System.currentTimeMillis();
//		list = repository.getTest2LeftJoinTest1();
//		endTime = System.currentTimeMillis();
//		System.out.println("Test3LeftJoinTest1_第三次查询执行时间： " + (endTime - startTime) + "毫秒");
		
		System.out.println("Test3LeftJoinTest1_查询记录数： " + list.size());
	}
	
	
	@Test
	public void testArangoOperations() {
		Long startTime = System.currentTimeMillis();
		String query = "FOR t1 IN test1 FILTER t1.randNum == @randNum SORT t1.randName DESC RETURN t1";
		Map<String, Object> params = new MapBuilder()
				.put("randNum", "13121920080421335X")
//				.put("age", 25)
				.get();
		ArangoCursor<Test1> cursor = operations.query(query, params, null, Test1.class);
		System.out.println(System.currentTimeMillis()-startTime);
		cursor.forEachRemaining(test -> {
			System.out.println("test: " + test);
		});
	}
	
	
	@Test
	public void contextLoads() {

	}
}
