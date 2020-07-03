package com.meiya.authorization.entity.vo;

import lombok.Data;

/**
 * @Description
 * @ClassName ResultVo
 * @Author Administrator
 * @date 2020.06.28 10:52
 */
@Data
public class ResultVo<T> {

    private boolean success;
    private Integer code;
    private String message;
    private T data;

}
