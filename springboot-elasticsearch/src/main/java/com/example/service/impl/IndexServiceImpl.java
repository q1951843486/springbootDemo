package com.example.service.impl;

import com.example.modul.Population;
import com.example.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @ClassName IndexServiceImpl
 * @Author Administrator
 * @date 2020.05.27 16:57
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public boolean deleteIndex(String indexName) {
        return elasticsearchTemplate.deleteIndex(indexName);
    }

    @Override
    public boolean createIndex(String indexName) {
        return elasticsearchTemplate.createIndex(Population.class);
    }
}
