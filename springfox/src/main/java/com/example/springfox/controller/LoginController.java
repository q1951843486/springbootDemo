package com.example.springfox.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName LoginController
 * @Author Administrator
 * @date 2020.03.02 14:20
 */
@RestController
@Api(value = "用户登陆")
public class LoginController {
    @RequestMapping(value = "/login")
    @ApiOperation(value = "用户登陆")
    public String login(){
        return "login";
    }


}
