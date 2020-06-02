/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.demo.arangoDB.entity;


import com.arangodb.springframework.annotation.Document;

import lombok.Data;
@Data
@Document("test1")
public class Test1 {

	private String id;
	
	private String randName;
	
	private String randNum;
}
