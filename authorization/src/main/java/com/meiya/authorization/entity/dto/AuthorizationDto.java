package com.meiya.authorization.entity.dto;

import com.meiya.authorization.entity.AuthorizationInfo;
import lombok.Data;

/**
 * @Description
 * @ClassName AuthorizationDto
 * @Author Administrator
 * @date 2020.07.02 14:44
 */
@Data
public class AuthorizationDto {
    private AuthorizationInfo AuthorizationInfo;
    private Boolean status;
    private String msg;
}
