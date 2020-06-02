package com.example.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @ClassName LoginController
 * @Author Administrator
 * @date 2020.04.27 14:45
 */
@Controller
public class LoginController {

    private final  String userName = "1";
    private final  String userPassWord= "1";
    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(String name, String password, HttpServletRequest request, HttpServletResponse response){
        //判断逻辑当name 与password 没有值时去尝试去cookie中取账号密码 根据自己的业务逻辑更改

        if (!ObjectUtils.allNotNull(name,password) || "".equals(name)||"".equals(password)){
            Cookie[] cookies = request.getCookies();
            if (ObjectUtils.allNotNull(cookies)){
                System.out.println(cookies);
                for (Cookie cookie :cookies) {
                    if ("name".equals(cookie.getName())){
                        name = cookie.getValue();
                    }
                    if ("password".equals(cookie.getName())){
                        password = cookie.getValue();
                    }
                }
            }
        }
        System.out.println(name + "......." +password);
        //登陆成功后保存登陆信息进cookie 实际上用户名与密码需要加密
        if (userName.equals(name)&&userPassWord.equals(password)){
            Cookie cookie = new Cookie("name",name);
            Cookie cookie1 = new Cookie("password",name);
            cookie.setMaxAge(7*3600*24);
            cookie1.setMaxAge(7*3600*24);
            response.addCookie(cookie);
            response.addCookie(cookie1);
            return "成功";
        }
        return "失败";

    }
}
