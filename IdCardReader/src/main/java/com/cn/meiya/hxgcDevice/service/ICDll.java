/*
 * Company: 
 * Copyright (c) 2012-2032 
 * All Rights Reserved.
 */
package com.cn.meiya.hxgcDevice.service;

import com.cn.meiya.hxgcDevice.util.FileUtil;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

/**
 * 
 * Description: 身份证识别器类库(采用JNA开发)
 * 
 * @author LiuHao
 * @date 2020年6月1日上午10:37:14
 * @version 1.0
 */
public interface ICDll extends Library {

	ICDll instance = (ICDll) Native.loadLibrary(FileUtil.getFilePath("/sdtapi.dll"),ICDll.class);

	/***
	 * 打开端口
	 * 
	 * @param port 1001～1016(十进制)为 USB 口。 例如:1001： USB1,1002,USB2
	 * @return
	 */
	public int SDT_OpenPort(int port);

	/***
	 * 寻找居民身份证
	 * 
	 * @param port     端口
	 * @param byManaID 存储导卡数据
	 * @param iIfOpen  自动打开设备标志。如果设置为1，则在接口内部自动实现打开设备和关闭设备，无需调用者再添加
	 * @return 0x9f 找卡成功。0x80 找卡失败。
	 */
	public int SDT_StartFindIDCard(int port, byte[] byManaID, int iIfOpen);

	/****
	 * 选卡
	 * 
	 * @param port     端口
	 * @param byManaID 存储选卡数据
	 * @param iIfOpen  自动打开设备标志。如果设置为1，则在接口内部自动实现打开设备和关闭设备，无需调用者再添加
	 * @return
	 */
	public int SDT_SelectIDCard(int port, byte[] byManaID, int iIfOpen);

	/***
	 * 读取身份证基本信息(文字信息采用GB 13000的UCS-2进行存储对应java的 StandardCharsets.UTF_16LE编码)
	 * 
	 * @param iPort       端口
	 * @param pucCHMsg    存储基本文字信息
	 * @param puiCHMsgLen 读取文字长度 0为全部 最大256.
	 * @param pucPHMsg    存储照片信息
	 * @param puiPHMsgLen 读取照片长度 0为全部 最大256.
	 * @param iIfOpen     自动打开设备标志。如果设置为1，则在接口内部自动实现打开设备和关闭设备，无需调用者再添加
	 * @return
	 */
	public int SDT_ReadBaseMsg(int iPort, byte[] pucCHMsg, IntByReference puiCHMsgLen, byte[] pucPHMsg,
			IntByReference puiPHMsgLen, int iIfOpen);
}
