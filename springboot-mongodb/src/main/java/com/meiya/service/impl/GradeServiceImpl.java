package com.meiya.service.impl;

import com.meiya.modul.Grade;
import com.meiya.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @ClassName GradeServiceImpl
 * @Author Administrator
 * @date 2020.03.06 16:59
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Grade add(Grade grade) {
        return mongoTemplate.insert(grade);
    }

    @Override
    public List<Grade> queryGradeListByName(String name) {
        Query query = new Query();
        query.addCriteria(new Criteria("gradeName").regex(".*?"+name+".*")).limit(9).skip(0);
        return mongoTemplate.find(query,Grade.class);
    }
}
