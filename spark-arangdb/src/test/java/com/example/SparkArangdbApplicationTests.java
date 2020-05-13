package com.example;

import com.arangodb.springframework.core.template.ArangoTemplate;
import com.example.entity.Test1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SparkArangdbApplicationTests {

    @Autowired
    private ArangoTemplate arangoTemplate;


    @Test
    void contextLoads() {

        List<Test1> arangoTemplateAll = (List<Test1>) arangoTemplate.findAll(Test1.class);

        System.out.println(arangoTemplateAll.size());


    }

}
