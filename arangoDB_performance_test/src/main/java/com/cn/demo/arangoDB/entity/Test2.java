/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.demo.arangoDB.entity;


import com.arangodb.springframework.annotation.Document;

import lombok.Data;
@Data
@Document("test2")
public class Test2 {

	private String id;
	
	private String randName;
	
	private String randNum;
}
