/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.example.entity;


import com.arangodb.springframework.annotation.Document;
import lombok.Data;

@Data
@Document("test6")
public class Test6 {

	private String id;
	
	private String randName;
	
	private String randNum;
	private String num;
}
