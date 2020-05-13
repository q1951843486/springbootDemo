/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.example.entity;


import com.arangodb.springframework.annotation.Document;
import lombok.Data;

import java.io.Serializable;

@Data
@Document("test1")
public class Test1 implements Serializable {

	private String id;
	
	private String randName;
	
	private String randNum;
}
