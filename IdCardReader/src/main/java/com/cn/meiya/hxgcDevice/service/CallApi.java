/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.meiya.hxgcDevice.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cn.meiya.hxgcDevice.bean.CheckResult;
import com.cn.meiya.hxgcDevice.util.ApiHttpClientUtils;
import com.cn.meiya.hxgcDevice.util.RSAUtils;

/**
 * 
 * Description: 调用美亚接口
 * @author LiuHao
 * @date 2020年6月2日下午4:46:25
 * @version 1.0
 */

public class CallApi {

	private static final String LICENSE = "L9GHlwuQZI8fVHxx+E+lTX63AlO7yLSF6K2/zUs06RjJw45AiGt+uWNVRgtKMKcjdLoBX25v2euR0pT/AA6fQgC+tR1WZnXgZRxLZQRP0hlFzOjup/Ax8qnI70GWrSS3l+9YuZ7onbr74sSHSQqcHUyesY3QvzSs5Rdmpr+e+Gti//9fG4Jth5uiBkmXa8BbCIqWLGXZ84nduYrq5foKv8Ve5tRnXzp1kVDgUOzgguEn36X5wi79Ng==";
	
	private static final String  HOST="https://www.httech-bj.com";
	
	private static final String CHECKPATH = "/honest/service/honestCheckSington";
	
	
	


    /**
     * 进行调用身份核查接口
     *
     * @return
     * @throws Exception
     */
    public static CheckResult doCheck(String name, String idCardNum)
            throws Exception {
    	CheckResult checkResult = new CheckResult();
        Map<String, String> querys = new HashMap<String, String>(3);
        querys.put("license", LICENSE);
        querys.put("name", name);
        querys.put("idCardNum", idCardNum);
        querys = getRSAQuerys(querys);
        JSONObject jsonObject = ApiHttpClientUtils.doGet(HOST, CHECKPATH, querys);
        String statusCode = jsonObject.getString("statusCode");
        String errormsg = jsonObject.getString("errormsg");
        String resultData = jsonObject.getString("resultData");
        if(StringUtils.equals(jsonObject.getString("statusCode"), "200")) {
        	checkResult = JSONObject.parseObject(resultData, CheckResult.class);
        }
        checkResult.setStatusCode(statusCode);
        checkResult.setErrormsg(errormsg);
        return getDecryptCheckResult(checkResult);
    }
    
    /**
     * 对返回的内容 身份证号 姓名 进行解密 得到真实的数据
     *
     * @param checkResult
     * @return
     * @throws IOException
     */
    private static CheckResult getDecryptCheckResult(CheckResult checkResult) throws IOException {

        if (checkResult != null) {
            try {
                if (checkResult.getIdCard() != null) {
                    checkResult.setIdCard(RSAUtils.publicDecrypt(checkResult.getIdCard()));
                }
                if (checkResult.getName() != null) {
                    checkResult.setName(RSAUtils.publicDecrypt(checkResult.getName()));
                }
            } catch (Exception e) {
                return null;
            }
        }
        return checkResult;
    }
	
    
	/**
     * 将请求参数的 身份证号 姓名  进行加密
     *
     * @param querys
     * @return
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private static Map<String, String> getRSAQuerys(Map<String, String> querys) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

        for (Map.Entry<String, String> entry : querys.entrySet()) {
            if (("idCardNum".equals(entry.getKey()) && entry.getValue() != null) || ("name".equals(entry.getKey()) && entry.getValue() != null)) {
                querys.put(entry.getKey(), RSAUtils.publicEncrypt(entry.getValue()));
            }
        }
        return querys;
    }
}
