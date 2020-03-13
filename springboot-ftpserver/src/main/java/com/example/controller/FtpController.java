package com.example.controller;

import com.example.service.FtpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName FtpController
 * @Author Administrator
 * @date 2020.03.13 11:04
 */
@Controller
public class FtpController {

    @Resource
    private FtpClientService ftpClientService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String downLoad(){
        String str = "2020031214crjrydk.csv";

        //ftpClientService.download(str,"E:/test");

        return null;



    }


}
