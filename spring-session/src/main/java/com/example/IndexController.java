package com.example;

import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @ClassName IndexController
 * @Author Administrator
 * @date 2020.03.10 14:56
 */
@Controller
public class IndexController {


    @RequestMapping(value = "/session")
    @ResponseBody
    public String session(HttpServletRequest request){
        return "sessionId" + request.getSession().getId() + "port " + request.getServletPath();
    }


}
