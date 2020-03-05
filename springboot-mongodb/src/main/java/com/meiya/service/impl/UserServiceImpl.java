package com.meiya.service.impl;

import com.meiya.modul.User;
import com.meiya.service.UserService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @ClassName UserServcieImpl
 * @Author Administrator
 * @date 2020.03.04 17:02
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public User queryUserById(long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query,User.class);
    }

    @Override
    public long queryUserTotal() {

        return mongoTemplate.count(new Query(new Criteria()),"user");

    }

    @Override
    public void add(User user) {
        mongoTemplate.insert(user);
    }

    @Override
    public User updateUserById(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public int deleteUserById(int i) {
        Query query = new Query(Criteria.where("id").is(i));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        return (int) result.getDeletedCount();
    }


}
