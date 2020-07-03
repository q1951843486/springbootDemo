package com.meiya.authorization.service;

import com.meiya.authorization.entity.Authorizer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-30
 */
public interface IAuthorizerService extends IService<Authorizer> {



    Integer insertAuthorizer(Authorizer authorizer);

    boolean findAuthorizerByNameAndIdCard(Authorizer authorizer);

    int addAuthorizer(Authorizer authorizer);
}
