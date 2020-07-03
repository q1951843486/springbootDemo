package com.meiya.authorization.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meiya.authorization.entity.AuthorizationInfo;
import com.meiya.authorization.mapper.AuthorizationInfoMapper;
import com.meiya.authorization.service.IAuthorizationInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-30
 */
@Service
public class AuthorizationInfoServiceImpl extends ServiceImpl<AuthorizationInfoMapper, AuthorizationInfo> implements IAuthorizationInfoService {

    @Autowired
    private AuthorizationInfoMapper authorizationInfoMapper;

    @Override
    public IPage<AuthorizationInfo> queryPageList(Map map) {
        IPage<AuthorizationInfo> authorizationInfoIPage = new Page<>((Integer)map.get("pageCurrent"),(Integer)map.get("pageSize"));
        return authorizationInfoMapper.selectPage(authorizationInfoIPage, null);
    }

    @Override
    public Integer insertAuthorizationInfo(AuthorizationInfo authorizationInfo) {

        Integer id=authorizationInfoMapper.insertAuthorizationInfo(authorizationInfo);
        return null;
    }
}
