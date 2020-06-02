/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package entity;



import com.arangodb.springframework.annotation.Document;
import lombok.Data;

import java.io.Serializable;

@Data
@Document("test1")
public class Test2 implements Serializable {

	private static final long serialVersionUID = -8954859948242348887L;
	private String id;
	
	private String randName;
	
	private String randNum;
}
