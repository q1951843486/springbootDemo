package com.example.controller;


import com.example.modul.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName RandomController
 * @Author Administrator
 * @date 2020.04.08 17:20
 */
@RestController
public class RandomController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/random/query/zjh/{zjh}")
    public Random query(@PathVariable(value = "zjh")String zjh){
        Query query = new Query(new Criteria("zjh").is(zjh));
        return mongoTemplate.findOne(query,Random.class);
    }
    @RequestMapping(value = "/random/query/xm/{xm}")
    public Random queryPeopleByName(@PathVariable(value = "xm")String xm){
        Query query = new Query(new Criteria("xm").is(xm));
        return mongoTemplate.findOne(query,Random.class);
    }
    @RequestMapping(value = "/random/query/id/{id}")
    public Random queryPeopleById(@PathVariable(value = "id")String id){
        Query query = new Query(new Criteria("id").is(id));
        return mongoTemplate.findOne(query,Random.class);
    }


}
