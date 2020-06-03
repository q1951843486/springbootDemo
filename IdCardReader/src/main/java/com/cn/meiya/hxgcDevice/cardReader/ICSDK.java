/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.meiya.hxgcDevice.cardReader;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.cn.meiya.hxgcDevice.service.ICDll;
import com.sun.jna.ptr.IntByReference;

/**
 * 
 * Description: 读取身份证信息 (流程:寻卡--选卡--读卡)
 * 
 * @author LiuHao
 * @date 2020年6月1日上午10:39:03
 * @version 1.0
 */
public class ICSDK {

	// 存储基本文字信息
	private byte[] byCHMsg = new byte[256 + 1];
	// 存储照片信息
	private byte[] byPHMsg = new byte[1024 + 1];

	public ICSDK() {
		byte[] byManaID = new byte[8];
		// 寻卡
		Integer result = ICDll.instance.SDT_StartFindIDCard(1001, byManaID, 1);
		System.out.println(Integer.toHexString(result));
		byManaID = new byte[8];
		// 选卡
		result = ICDll.instance.SDT_SelectIDCard(1001, byManaID, 1);
		System.out.println(Integer.toHexString(result));
		// 读卡
		result = ICDll.instance.SDT_ReadBaseMsg(1001, byCHMsg, new IntByReference(0), byPHMsg, new IntByReference(0),
				1);
		System.out.println(Integer.toHexString(result));
	}

	public static ICSDK getInstance() {
		return new ICSDK();
	}

	/**
	 * 获取姓名
	 * 
	 * @return
	 */
	public String getName() {
		return new String(Arrays.copyOf(byCHMsg, 30), StandardCharsets.UTF_16LE);
	}

	/***
	 * 获取性别
	 * 
	 * @return
	 */
	public String getGener() {
		return new String(Arrays.copyOfRange(byCHMsg, 30, 32), StandardCharsets.UTF_16LE);
	}

	/***
	 * 获取民族
	 * 
	 * @return
	 */
	public String getNation() {
		return new String(Arrays.copyOfRange(byCHMsg, 32, 36), StandardCharsets.UTF_16LE);
	}

	/***
	 * 获取出生
	 * 
	 * @return
	 */
	public String getBirth() {

		return new String(Arrays.copyOfRange(byCHMsg, 36, 52), StandardCharsets.UTF_16LE);
	}

	/***
	 * 获取住址
	 * 
	 * @return
	 */
	public String getAddress() {

		return new String(Arrays.copyOfRange(byCHMsg, 52, 122), StandardCharsets.UTF_16LE);
	}

	/***
	 * 获取身份证号码
	 * 
	 * @return
	 */
	public String getIc() {

		return new String(Arrays.copyOfRange(byCHMsg, 122, 158), StandardCharsets.UTF_16LE);
	}

	/***
	 * 签发机关
	 * 
	 * @return
	 */
	public String getStation() {

		return new String(Arrays.copyOfRange(byCHMsg, 158, 188), StandardCharsets.UTF_16LE);
	}

	/***
	 * 有效开始日期
	 * 
	 * @return
	 */
	public String getBegindate() {

		return new String(Arrays.copyOfRange(byCHMsg, 188, 204), StandardCharsets.UTF_16LE);
	}

	/***
	 * 有效结束日期
	 * 
	 * @return
	 */
	public String getEnddate() {

		return new String(Arrays.copyOfRange(byCHMsg, 204, 220), StandardCharsets.UTF_16LE);
	}

	/***
	 * 最新地址
	 * 
	 * @return
	 */
	public String getLateAddress() {

		return new String(Arrays.copyOfRange(byCHMsg, 220, 290), StandardCharsets.UTF_16LE);
	}

	public String getBaseAll() {
		return new String(byCHMsg, StandardCharsets.UTF_16LE);
	}
}
