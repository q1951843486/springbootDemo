package com.cn.demo.arangoSpark;

import com.cn.demo.arangoSpark.entity.TestJavaEntity;
import com.cn.demo.arangoSpark.function.ArangoSparkTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ArangoSparkTestApplication {


	public static void main(String[] args) {
		final Class<?>[] runner = new Class<?>[] { SparkRunner.class};
		System.exit(SpringApplication.exit(SpringApplication.run(runner, args)));

		//SpringApplication.run(ArangoSparkTestApplication.class, args);

	}

}
