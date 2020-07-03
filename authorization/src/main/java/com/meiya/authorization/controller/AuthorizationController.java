package com.meiya.authorization.controller;

import cn.hutool.core.date.DateUtil;
import com.meiya.authorization.common.Constants;
import com.meiya.authorization.entity.Authorizer;
import com.meiya.authorization.entity.dto.AuthorizationDto;
import com.meiya.authorization.entity.vo.RequestVo;
import com.meiya.authorization.entity.vo.ResultVo;
import com.meiya.authorization.service.AuthorizationService;
import com.meiya.authorization.service.IAuthorizationInfoService;
import com.meiya.authorization.service.IAuthorizerService;
import com.meiya.authorization.utils.IdCardUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName AuthorizationController
 * @Author Administrator
 * @date 2020.07.01 15:03
 */
@RestController
@RequestMapping(value = "/authorization")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private IAuthorizerService authorizerService;


    @Autowired
    private IAuthorizationInfoService authorizationInfoService;


    @PostMapping(value = "/add")
    public ResultVo addAuthorizer(@RequestBody RequestVo requestVo) {

        String name = requestVo.getName();
        String idCard = requestVo.getIdCard();
        String photo = requestVo.getPhoto();
        ResultVo resultVo = new ResultVo();
        if (StringUtils.isEmpty(name) || StringUtils.length(name) == 0 || StringUtils.isEmpty(idCard) || StringUtils.length(idCard) == 0) {
            resultVo.setCode(400);
            resultVo.setSuccess(false);
            resultVo.setMessage("姓名或者身份证不能为空");
        }
        //验证身份证正则
        System.out.println(name + idCard);
        System.out.println("--------------------------------------------");
        System.out.println(photo);
        if (!IdCardUtils.isIDNumber(idCard)) {
            resultVo.setCode(400);
            resultVo.setSuccess(false);
            resultVo.setMessage("身份证格式错误");
            return resultVo;
        }
        Authorizer authorizer = new Authorizer();
        authorizer.setCreationTime(new Date());
        authorizer.setAuthorizerName(name);
        authorizer.setIdCard(idCard);
        authorizer.setPhotoUrl(requestVo.getPhotoUrl());
        //authorizer.setPhone(phone);
      /*  byte[] decode = Base64.getDecoder().decode(photo);
        byte[] bytes = PhotoUtils.compressPicForScale(decode, 31L,"lllll");
        String base64 =Base64.getEncoder().encodeToString(bytes);*/
        AuthorizationDto authorizationDto = authorizationService.addAuthorization(Constants.LICENSE, authorizer, photo);
        if (!authorizationDto.getStatus()) {
            resultVo.setCode(400);
            resultVo.setSuccess(false);
            resultVo.setMessage(authorizationDto.getMsg());
            return resultVo;
        }
        resultVo.setCode(200);
        resultVo.setSuccess(true);
        resultVo.setMessage("成功");
        Map<String, Object> map = new HashMap<>(16);
        map.put("authorizationInfoId", authorizationDto.getAuthorizationInfo().getId());
        map.put("authorizationTime", DateUtil.format(authorizationDto.getAuthorizationInfo().getCreateTime(),"yyyy-MMM-dd HH:mm:ss"));
        map.put("photoUrl", authorizer.getPhotoUrl());
        resultVo.setData(map);
        return resultVo;

    }


}
