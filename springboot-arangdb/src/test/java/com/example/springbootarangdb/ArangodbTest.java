package com.example.springbootarangdb;

import com.arangodb.springframework.core.template.ArangoTemplate;
import com.example.springbootarangdb.entity.Test1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description
 * @ClassName ArangodbTest
 * @Author Administrator
 * @date 2020.05.07 14:32
 */
@SpringBootTest
public class ArangodbTest {

    @Autowired
    private ArangoTemplate arangoTemplate;

    @Test
    void test1(){
        Iterable<Test1> all = arangoTemplate.findAll(Test1.class);
        all.forEach(test1-> System.out.println(test1));


    }
}
