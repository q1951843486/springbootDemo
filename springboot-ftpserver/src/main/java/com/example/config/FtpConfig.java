package com.example.config;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.soap.Addressing;
import java.io.IOException;
import java.net.SocketException;

/**
 * @Description
 * @ClassName FtpConfig
 * @Author Administrator
 * @date 2020.03.13 10:58
 */
@Configuration
public class FtpConfig {

    private static  final  Logger logger = LoggerFactory.getLogger(FtpConfig.class);

    @Autowired
    private ApplicationEntity applicationEntity;
    @Bean
    public FTPClient ftpClient(){
//        BeansEntity beansEntity =new BeansEntity();
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(1000*30);//设置连接超时时间
        ftpClient.setControlEncoding("utf-8");//设置ftp字符集
//        ftpClient.enterLocalPassiveMode();//设置被动模式，文件传输端口设置
        try {
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//设置文件传输模式为二进制，可以保证传输的内容不会被改变
//            ftpClient.connect(beansEntity.getIp()+":"+beansEntity.getPort());
            ftpClient.setDefaultPort(applicationEntity.getFtpPort());
            ftpClient.connect(applicationEntity.getFtpHost(),applicationEntity.getFtpPort());
            ftpClient.login(applicationEntity.getFtpUsername(),applicationEntity.getFtpPassword());
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)){
                ftpClient.disconnect();
                logger.error("未连接到FTP，用户名或密码错误!");
                return null;
            }else {
                logger.info("FTP连接成功!");
                return ftpClient;
            }
        } catch (SocketException socketException) {
            logger.error("FTP的IP地址可能错误，请正确配置!");
            return null;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            logger.error("FTP的端口错误,请正确配置!");
            return null;
        }
    }


}
