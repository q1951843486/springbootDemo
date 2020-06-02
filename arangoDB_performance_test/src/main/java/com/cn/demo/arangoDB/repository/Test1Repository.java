/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.demo.arangoDB.repository;


import com.arangodb.springframework.annotation.Query;
import com.cn.demo.arangoDB.entity.Test1;

public interface Test1Repository extends BaseRepository<Test1, String> {

	Iterable<Test1> findByRandName(String randName);

    @Query("FOR t1 IN test1 FILTER ti.randNum > @randNum SORT ti.randName DESC RETURN t1")
    Iterable<Test1> getName(String randNum);
    
}
