package com.example.service;

import java.io.InputStream;

/**
 * @Description
 * @ClassName FtpClientService
 * @Author Administrator
 * @date 2020.03.13 11:05
 */
public interface FtpClientService {
    public String readFileToBase64(String remoteFileName,String remoteDir);

    public void download(String remoteFileName,String localFileName,String remoteDir);

    public boolean uploadFile(InputStream inputStream, String originName, String remoteDir);
}
