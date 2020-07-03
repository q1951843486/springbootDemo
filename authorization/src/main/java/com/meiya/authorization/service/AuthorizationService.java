package com.meiya.authorization.service;

import com.meiya.authorization.entity.Authorizer;
import com.meiya.authorization.entity.dto.AuthorizationDto;

import java.util.Map;

/**
 * @Description
 * @ClassName AuthorizationService
 * @Author Administrator
 * @date 2020.07.01 15:05
 */
public interface AuthorizationService {
    AuthorizationDto addAuthorization(String license, Authorizer authorizer, String photo);
}
