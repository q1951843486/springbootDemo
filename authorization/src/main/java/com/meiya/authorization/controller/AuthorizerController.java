package com.meiya.authorization.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meiya.authorization.entity.Authorizer;
import com.meiya.authorization.entity.vo.ResultVo;
import com.meiya.authorization.service.IAuthorizerService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/authorizer")
public class AuthorizerController {

    @Autowired
    private IAuthorizerService authorizerService;
    @RequestMapping(value = "/query")
    public ResultVo query(@RequestBody String openId){

        ResultVo resultVo = new ResultVo();
        Authorizer one = authorizerService.getOne(new LambdaQueryWrapper<Authorizer>().eq(StringUtils.isNotBlank(openId),Authorizer::getOpenId,openId));

        if (ObjectUtils.allNotNull(one)){
            resultVo.setCode(200);
            resultVo.setSuccess(true);
            resultVo.setMessage("查询成功");
            resultVo.setData(one);
        }else{

            resultVo.setCode(400);
            resultVo.setSuccess(false);
            resultVo.setMessage("失败");
        }
        return resultVo;

    }

}
