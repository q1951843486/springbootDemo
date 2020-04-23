package com.example.controller;

import com.example.common.Constants;
import com.example.modul.Population;
import com.example.modul.Random;
import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description
 * @ClassName DateImputController
 * @Author Administrator
 * @date 2020.04.15 13:59
 */
@RestController(value = "/es")
public class DateInputController {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping(value = "/input")
    public void input(){
        Integer pageSize = new Integer(1000);
        int pageIndex = 1;
        Pageable pageRequest = new PageRequest(pageIndex,pageSize);
        Query query = new Query();
        query.with(pageRequest);
        List<Random> list = mongoTemplate.find(query, Random.class);
        list.forEach(random -> {
            System.out.println(random);
        });

    }

    /**
     * 往es插入单条数据
     * @return
     */
    @RequestMapping(value = "/insert")
    public List insert(){

        Population population = new Population();
        population.setId(1);
        population.setIdCard("111111111111");
        population.setName("张三");
        population.setSourceTableName(Constants.TABLE_RANDOM);
        population.setSourceTableId("1");
        IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(population.getId())).withObject(population).build();
        elasticsearchTemplate.index(indexQuery);
        return null;

    }
    /**
     *
     */
    @RequestMapping(value = "/insertList")
    public List insertList(){

        int pageIndex = 0;
        for (int i = 0; i <3000 ; i++) {
            Long startTime = System.currentTimeMillis();
            Integer pageSize = new Integer(10000);
            Pageable pageRequest = new PageRequest(pageIndex,pageSize);
            Query query = new Query();
            query.with(pageRequest);

            List<Random> list = mongoTemplate.find(query, Random.class);
            System.out.println(System.currentTimeMillis()-startTime);
            List<IndexQuery> queries = new ArrayList<>();
            list.forEach(random -> {
                Population population = new Population();
                population.setId(System.currentTimeMillis());
                population.setIdCard(random.getZjh());
                population.setName(random.getXm());
                population.setSourceTableName(Constants.TABLE_RANDOM);
                population.setSourceTableId(random.getId());
                IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(population.getId())).withObject(population).build();
                queries.add(indexQuery);

            });

            elasticsearchTemplate.bulkIndex(queries);
            Long endTime = System.currentTimeMillis();
            pageIndex++;
            System.out.println(pageIndex);
            System.out.println(endTime-startTime);
            System.out.println("///////////////////////");

        }

        return null;

    }

    /**
     * 查询姓名  不分词
     * @param name
     * @return
     */
    @RequestMapping(value = "/population")
    public List<Population> getPopulationByName(String name){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("name",name)).build();
        List list = elasticsearchTemplate.queryForList(searchQuery,Population.class);
        System.out.println(list.size());
        if (ObjectUtils.allNotNull(list)){
            list.forEach(population -> System.out.println(population));
        }
        return list;
    }




}
