package com.meiya.common;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @ClassName PageListVo
 * @Author Administrator
 * @date 2020.03.06 10:39
 */
@Data
public class  PageListVo <T> {
    private Integer pageNo;
    private Integer pageSize;
    private List<T> pageList;
    private String msg;

}
