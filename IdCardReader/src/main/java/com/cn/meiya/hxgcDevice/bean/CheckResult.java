/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.meiya.hxgcDevice.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class CheckResult implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 163015938338949032L;

	private String statusCode;
	
	private String errormsg;
	
	private String result = "您的证件已过有效期";
	
	//有效开始日期
	private String begindate;
	
    private String idCard;

    private String name;

    private String resultIdCard;

    private String resultName;

    private String photo;

    private String errorMesage;

    private String errorMesageCol;
    /**
     * 系统分析结果
     */
    private String resultFx;
    /**
     * 系统分析结果
     */
    private String resultXp;

    /**
     * 系统分析分数
     */
    private String resultFs;
    
   
}
