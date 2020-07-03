package com.meiya.authorization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meiya.authorization.entity.AuthorizationInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-30
 */
public interface IAuthorizationInfoService extends IService<AuthorizationInfo> {

    IPage<AuthorizationInfo> queryPageList(Map map);

    Integer insertAuthorizationInfo(AuthorizationInfo authorizationInfo);
}
