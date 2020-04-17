package com.example.springbootweixin.controller;

import com.example.springbootweixin.common.Constants;
import com.example.springbootweixin.common.HttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName WxacodeController
 * @Author Administrator
 * @date 2020.03.18 17:03
 */
@Controller
public class WxCodeController {

    public static void main(String[] args) {

        System.out.println(Constants.ACCESS_TOKEN_URL);
        String token = "31_0-YkGYyuisoDYqQpIbmSkRMeXZZG8DMYe2Gq1Hba6YQ9LI-SJPgq0zwx3kq_CXZac4E8fP5Ky0ZL3DG47Fnye0BcVKwKqpMzlEs_djhmbkNd2sjT63BhVFn2cZyCmQ7aAv2RFzsQISnY8KbbZOFiAAAUJA";


        String str = "?access_token=" + token + "&scene=" + "a&" + "page=" + Constants.URL;
        System.out.println(str);

        String s  =HttpUtils.sendPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit",str);

        System.out.println(s);
    }


}
