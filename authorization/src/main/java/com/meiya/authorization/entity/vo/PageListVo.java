package com.meiya.authorization.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @ClassName PageListVo
 * @Author Administrator
 * @date 2020.07.01 09:54
 */
@Data
public class PageListVo<T> {

    private Long total;
    private Integer pageCurrent;
    private Integer pageSize;
    private List<T> pageList;

}
