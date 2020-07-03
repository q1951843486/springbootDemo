package com.meiya.authorization.service.impl;

import com.meiya.authorization.entity.AuthorizationInfo;
import com.meiya.authorization.entity.Authorizer;
import com.meiya.authorization.entity.dto.AuthorizationDto;
import com.meiya.authorization.entity.dto.CheckResult;
import com.meiya.authorization.mapper.AuthorizationInfoMapper;
import com.meiya.authorization.mapper.AuthorizerMapper;
import com.meiya.authorization.service.AuthorizationService;
import com.meiya.authorization.utils.HttpsClientUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
/**
 * @Description
 * @ClassName AuthorizationServiceImpl
 * @Author Administrator
 * @date 2020.07.01 15:05
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private AuthorizerMapper authorizerMapper;
    @Autowired
    private AuthorizationInfoMapper authorizationInfoMapper;



    @Override
    @Transactional
    public AuthorizationDto addAuthorization(String license, Authorizer authorizer, String photo) {

        CheckResult checkResult = null;
        AuthorizationDto authorizationDto = new AuthorizationDto();
        authorizationDto.setStatus(false);

        //http请求调用人脸识别接口验证身份
        try {
            checkResult = HttpsClientUtils.doCompare(license, authorizer.getAuthorizerName(), authorizer.getIdCard(), photo);
            // System.out.println(checkResult);
        } catch (Exception e) {
            e.printStackTrace();
            authorizationDto.setMsg(e.getMessage());
            return authorizationDto;
            //map.put("msg",e.getMessage());
        }


        if (!ObjectUtils.allNotNull(checkResult)) {

            System.out.println("人脸认证接口调用错误");
            authorizationDto.setMsg("人脸认证接口调用错误");
            //map.put("httpError","人脸认证接口调用错误");
            return authorizationDto;

        }
        System.out.println(checkResult);

        if (!StringUtils.equals("系统判断为同一人", checkResult.getResultFx())) {
            System.out.println("人脸识别不一致");
            authorizationDto.setMsg("人脸识别不一致");
            return authorizationDto;
            //map.put("checkError","人脸识别不一致");
            //return map;

        }
        //判断授权人是否也在库中
        Authorizer b = authorizerMapper.selectAuthorizerByNameAndIdCard(authorizer);
        //添加授权人信息
        if (!ObjectUtils.allNotNull(b)) {
            Integer count = authorizerMapper.insertAuthorizer(authorizer);
            if (count != 1) {
                //map.put("addAuthorizer","添加授权人错误");
                System.out.println("添加授权人错误");
                authorizationDto.setMsg("添加授权人错误");
                return authorizationDto;
            }
        }
        //添加授权信息
        AuthorizationInfo authorizationInfo = new AuthorizationInfo();
        if (ObjectUtils.allNotNull(b)) {
            authorizationInfo.setAuthorizarId(b.getId());
        }else {
            authorizationInfo.setAuthorizarId(authorizer.getId());
        }

        authorizationInfo.setAuthorizerPhoto(photo);
        authorizationInfo.setCreateTime(new Date());

        Integer num = authorizationInfoMapper.insertAuthorizationInfo(authorizationInfo);
        if (num != 1) {
            System.out.println("授权信息添加失败");
            authorizationDto.setMsg("授权信息添加失败");
            return authorizationDto;
        }
        authorizationDto.setStatus(true);
        authorizationDto.setAuthorizationInfo(authorizationInfo);
        return authorizationDto;
    }
}
