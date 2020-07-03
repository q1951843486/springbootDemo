package com.meiya.authorization.service.impl;

import com.meiya.authorization.entity.Authorizer;
import com.meiya.authorization.mapper.AuthorizerMapper;
import com.meiya.authorization.service.IAuthorizerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-30
 */
@Service
public class AuthorizerServiceImpl extends ServiceImpl<AuthorizerMapper, Authorizer> implements IAuthorizerService {

    @Autowired
    private AuthorizerMapper authorizerMapper;


    @Override
    public Integer insertAuthorizer(Authorizer authorizer) {


        int count  = authorizerMapper.insertAuthorizer(authorizer);
        return count;
    }

    @Override
    public boolean findAuthorizerByNameAndIdCard(Authorizer authorizer) {
        Authorizer authorizer1 =authorizerMapper.selectAuthorizerByNameAndIdCard(authorizer);
        if (ObjectUtils.allNotNull(authorizer1)){
            return true;
        }
        return false;

    }

    @Override
    public int addAuthorizer(Authorizer authorizer) {
        return 0;
    }
}
