package com.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @ClassName ApplicationEntity
 * @Author Administrator
 * @date 2020.03.13 10:57
 */
@Component
@Data
public class ApplicationEntity {

    /**
     * ftp站点
     */
    @Value("${ftp.host}")
    private String ftpHost;
    /**
     * ftp端口号
     */
    @Value("${ftp.port}")
    private int ftpPort;
    /**
     * ftp访问用户名
     */
    @Value("${ftp.username}")
    private String ftpUsername;
    /**
     * ftp访问密码
     */
    @Value("${ftp.password}")
    private String ftpPassword;
    /**
     * ftp访问文件路径
     */
    @Value("${ftp.filepath}")
    private String ftpFilepath;
    /**
     * ftp提供的http方式访问地址
     */
    @Value("${ftp.web.host}")
    private String ftpWebHost;
    /**
     * ftp提供的http方式访问的端口号
     */
    @Value("${ftp.web.port}")
    private String ftpWebPort;

}
