package com.example.controller;

import com.example.modul.Population;
import com.example.service.IndexService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName IndexController
 * @Author Administrator
 * @date 2020.05.27 16:55
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @RequestMapping(value = "/es/deleteIndex")
    public String deleteIndex(String indexName){

        boolean deleteIndex = indexService.deleteIndex(indexName);
        if (deleteIndex){
            return "成功";
        }else {
            return "失败";
        }
    }

    @RequestMapping(value = "/es/createIndex")
    public String createIndex(String indexName){

        boolean b =indexService.createIndex(indexName);
        if (b){
            return "成功";
        }else {
            return "失败";
        }

    }

    @RequestMapping("/es/indexExists")
    public String getIndex(String indexName){


        boolean indexExists = elasticsearchTemplate.indexExists(indexName);
        if (indexExists){
            return "存在";
        }else {
            return "不存在";
        }
    }
    @RequestMapping(value = "/es/indexCount")
    public String getIndexCountByIndexName(String indexName){


        List list = new ArrayList();
        list.add(indexName);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).withIds(list).build();
        long count = elasticsearchTemplate.count(searchQuery,Population.class);
        return String.valueOf(count);
    }

}
