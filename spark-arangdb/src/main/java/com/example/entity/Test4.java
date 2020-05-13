/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.example.entity;


import com.arangodb.springframework.annotation.Document;
import lombok.Data;

@Data
@Document("test3")
public class Test4 {

	private String id;
	
	private String randName;
	
	private String randNum;
}
