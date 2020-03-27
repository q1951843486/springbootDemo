package com.meiya.dao;

import com.meiya.modul.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @Description
 * @ClassName BaseDao
 * @Author Administrator
 * @date 2020.03.27 16:43
 */
@Component
public abstract class BaseDao<T> {


    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 保存
     * @param t
     * @return
     */
    public T save(T t){
        return this.mongoTemplate.save(t);
    }
    /**
     * 根据id查询单条记录
     * @param id
     * @return
     */
    public T queryEntityById(long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query,getTClass());
    }

    /**
     * 查询集合记录总条数
     * @return
     */
    public long queryEntityTotal() {

        return mongoTemplate.count(new Query(new Criteria()),getTClass());

    }


    /**
     * 新增单条记录
     * @param entity
     */
    public void add(T entity) {
        mongoTemplate.insert(entity);
    }

    /**
     * 根据id更新单条记录
     * @param entity
     * @return
     */
    public T updateEntityById(T entity) {
        return mongoTemplate.save(entity);
    }

    /**
     * 根据id删除记录
     * @param i
     * @return
     */
    public int deleteEntityById(Long i) {
        Query query = new Query(Criteria.where("id").is(i));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        return (int) result.getDeletedCount();
    }

    /**
     * 根据map查询列表
     * @param parmMap
     * @return
     */
    public List<T> queryPageList(Map<String, Object> parmMap) {
        Query query = new Query(new Criteria());
        Integer pageNo = (Integer) parmMap.get("pageNo");
        Integer pageSize = (Integer) parmMap.get("pageSize");
        query.skip((pageNo -1)* pageSize);
        query.limit(pageSize);
        List<T> userList =mongoTemplate.find(query,getTClass());
        return userList;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public T findById(Long id){
        return this.mongoTemplate.findById(id,getTClass());
    }

    /**
     * 获取类名
     * @return
     */
    protected Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


}
