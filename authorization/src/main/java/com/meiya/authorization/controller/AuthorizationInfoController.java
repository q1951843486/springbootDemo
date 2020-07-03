package com.meiya.authorization.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meiya.authorization.entity.AuthorizationInfo;
import com.meiya.authorization.entity.Authorizer;
import com.meiya.authorization.entity.vo.ResultVo;
import com.meiya.authorization.service.IAuthorizationInfoService;
import com.meiya.authorization.service.IAuthorizerService;
import com.meiya.authorization.utils.PDFPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/authorization-info")
public class AuthorizationInfoController {
    @Autowired
    private IAuthorizationInfoService authorizationInfoService;
    @Autowired
    private IAuthorizerService authorizerService;

/*

    @RequestMapping(value = "/page")
    public ResultVo findPageList(Integer pageCurrent,Integer pageSize){

        Map map = new HashMap();
        map.put("pageCurrent",pageCurrent);
        map.put("pageSize",pageSize);
        IPage<AuthorizationInfo> authorizationInfoPage = authorizationInfoService.queryPageList(map);
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setData(authorizationInfoPage);
        resultVo.setSuccess(true);
        resultVo.setMessage("成功");
        return  resultVo;
    }
*/



    @RequestMapping("/page")
    public ResultVo findPageListByCondition(Integer pageCurrent,Integer pageSize,String name,String idCard,String startTime,String endTime){


        //查询姓名或者身份证号对应AuthorizarId
        List<Authorizer> list = authorizerService.list(new LambdaQueryWrapper<Authorizer>()
                .like(StringUtils.isNotBlank(name), Authorizer::getAuthorizerName, name)
                .eq(StringUtils.isNotBlank(idCard), Authorizer::getIdCard, idCard));
        List<Integer> idList = new ArrayList<Integer>();
        if (org.apache.commons.lang3.ObjectUtils.allNotNull(list)){
            list.stream().forEach(l->{
                idList.add(l.getId());
            });
        }

        Page<AuthorizationInfo> page = new Page<>(pageCurrent, pageSize);
        IPage<AuthorizationInfo> result = authorizationInfoService.page(page,
                new LambdaQueryWrapper<AuthorizationInfo>()
                        .apply(StringUtils.isNotBlank(startTime),
                                "date_format (create_time,'%Y-%m-%d') >= date_format('" + startTime + "','%Y-%m-%d')")
                        .apply(StringUtils.isNotBlank(endTime),
                                "date_format (create_time,'%Y-%m-%d') <= date_format('" + endTime + "','%Y-%m-%d')")
                        .in(ObjectUtils.isEmpty(idList),AuthorizationInfo::getAttorneysIdCard,idList)
                        .orderByDesc(AuthorizationInfo::getCreateTime));

        ResultVo resultVo = new ResultVo();
        if(ObjectUtils.isEmpty(result)){
            resultVo.setCode(400);
            resultVo.setSuccess(true);
            resultVo.setMessage("无数据");
        }
        resultVo.setCode(200);
        resultVo.setSuccess(true);
        resultVo.setMessage("成功");
        resultVo.setData(result);

        return resultVo;
    }


    @PostMapping("/update")
    public ResultVo addAuthorization(@RequestBody AuthorizationInfo authorizationInfo){

        ResultVo resultVo = new ResultVo();
        authorizationInfo.setUpdateTime(new Date());
        boolean b = authorizationInfoService.updateById(authorizationInfo);


        AuthorizationInfo authorizationInfoServiceById = authorizationInfoService.getById(authorizationInfo.getId());
        //生成pdf
        PDFPrint pdfPrint = new PDFPrint();



        if (b){
            resultVo.setCode(200);
            resultVo.setSuccess(true);
            resultVo.setMessage("授权成功");
            resultVo.setData(authorizationInfo);
        }else{

            resultVo.setCode(400);
            resultVo.setSuccess(false);
            resultVo.setMessage("授权失败");
        }
        return resultVo;
    }



}
