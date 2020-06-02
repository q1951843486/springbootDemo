/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.demo.arangoDB.config;

//import org.springframework.context.annotation.Configuration;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB.Builder;
import com.arangodb.Protocol;
//import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.springframework.config.AbstractArangoConfiguration;

@SuppressWarnings("deprecation")
//@Configuration
//@EnableArangoRepositories(basePackages = { "com.cn.demo.arangoDB" })
public class ArangoDBConfiguration extends AbstractArangoConfiguration  {

	@Override
	public Builder arango() {
		ArangoDB.Builder arango = new ArangoDB.Builder()
                .useProtocol(Protocol.VST)
				.acquireHostList(true)
				.host("10.200.1.184",8529)
                .user("root")
                .password("root")
				.loadBalancingStrategy(LoadBalancingStrategy.ROUND_ROBIN);
        return arango;
	}

	@Override
	public String database() {
		// TODO Auto-generated method stub
		return "_system";
	}

}