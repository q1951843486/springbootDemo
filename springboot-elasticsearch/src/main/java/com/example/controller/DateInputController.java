package com.example.controller;

import com.example.common.Constants;
import com.example.common.SequenceFactory;
import com.example.modul.Population;
import com.example.modul.Random;
import com.example.service.PopulationService;
import com.mongodb.client.MongoCollection;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName DateImputController
 * @Author Administrator
 * @date 2020.04.15 13:59
 */
@RestController
public class DateInputController {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    private SequenceFactory sequenceFactory;

    @Autowired
    private PopulationService populationService;

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
     * 往es插入单条数据 返回id
     * @return
     */
    @RequestMapping(value = "/insert")
    public List insert(){

        Population population = new Population();
        population.setId("1111");
        population.setIdCard("22222222222222222222");
        population.setName("张三111111111");
        population.setSourceTableName(Constants.TABLE_RANDOM);
        population.setSourceTableId("1111111111");

        String insert = populationService.insert(population);
        System.out.println(insert);
        return null;

    }


    /**
     * 根据id 查询单条
     * @param id
     * @return
     */

    @RequestMapping(value = "/es/population/queryById")
    public Population queryPopulationById(Long id){
        return populationService.queryPopulationById(id);
    }
    /**
     * 根据对象查询单条数据
     */
    @RequestMapping(value = "/es/population/queryByObject")
    public List queryPopulationByObject( Population population){


        System.out.println(population);

        return populationService.queryPopulationByObject(population);
    }


    /**
     * 插入多条数据
     */
    @RequestMapping(value = "/insertList")
    public List insertList(){

        int pageIndex = 0;
        for (int i = 0; i <3000 ; i++) {
            Long startTime = System.currentTimeMillis();
            Integer pageSize = new Integer(100);
            Pageable pageRequest = new PageRequest(pageIndex,pageSize);
            Query query = new Query();
            query.with(pageRequest);

            List<Random> list = mongoTemplate.find(query, Random.class);
            System.out.println(System.currentTimeMillis()-startTime);
            List<IndexQuery> queries = new ArrayList<>();
            list.stream().forEach(random -> {
                Population population = new Population();
                population.setId(sequenceFactory.incrementHash(Constants.TABLE,Constants.TABLE_RANDOM,1L).toString());
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

    /**
     * 根据name 与 idCard 精确查询es
     * @param name
     * @param idCard
     * @return
     */

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public List queryEsByNameAndIdCard(String name ,String idCard){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("name",name)).withQuery(QueryBuilders.matchQuery("idCard",idCard)).build();
        List list = elasticsearchTemplate.queryForList(searchQuery,Population.class);
        System.out.println(list.size());
        list.forEach(population -> System.out.println(population));
        return list;
    }


    @RequestMapping(value = "/count")
    public long coount(){

        MongoCollection<Document> test1 = mongoTemplate.getCollection("test1");
        System.out.println(test1.count());
        return test1.count();

    }


    @RequestMapping(value = "/es/population",method = RequestMethod.POST)
    public String insertIntoPopulation(@RequestBody Population population){

        IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(population.getId())).withObject(population).build();
        String index = elasticsearchTemplate.index(indexQuery);
        return index;
    }


    /**
     * 根据对象查询 单条 取身份证号查询
     * @param population
     * @return
     */
    @RequestMapping(value = "/es/population",method = RequestMethod.GET)
    public Population queryPopulation(@RequestBody Population population){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("idCard",population.getIdCard())).build();
        List<Population> list = elasticsearchTemplate.queryForList(searchQuery, Population.class);






        return null;
    }








}
