package com.example.service;

/**
 * @Description
 * @ClassName IndexService
 * @Author Administrator
 * @date 2020.05.27 16:57
 */
public interface IndexService {
    boolean deleteIndex(String indexName);

    boolean createIndex(String indexName);
}
