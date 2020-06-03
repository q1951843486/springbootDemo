/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.meiya.hxgcDevice.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * Description: 
 * @author LiuHao
 * @date 2020年6月2日下午7:12:07
 * @version 1.0
 */
public class FileUtil {

	public String getFilePath1(String fileName) {
		String filePath = "";
		try {
			filePath = this.getClass().getResource(fileName).getPath();
			filePath = filePath.substring(filePath.indexOf("/") + 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return filePath;
	}
	/**
	 * 
	 * Description: 获取项目中文件路径
	 * @param fileName  classpath下的    /文件名
	 * @return
	 */
	public static String getFilePath(String fileName) {
		String filePath = "";
		try {
			filePath = FileUtil.class.getResource(fileName).getPath();
			filePath = filePath.substring(filePath.indexOf("/") + 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return filePath;
	}
	
	/**
	 * 
	 * Description: classpath下的    /文件名
	 * @param fileName
	 * @return
	 */
	public static InputStream getFileInputStream(String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = FileUtil.class.getResourceAsStream(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputStream;
	}
	
	 /**
	  *  =功能描述: byte数组转 InputStream
	  * @param bytes
	  * @return
	  */
    public static InputStream byte2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
 
 
    /**
     *   InputStream转byte数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] inputStream2byte(InputStream inputStream){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        try {
			while ((rc = inputStream.read(buff, 0, 100)) > 0) {
			    byteArrayOutputStream.write(buff, 0, rc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return byteArrayOutputStream.toByteArray();
    }

}
