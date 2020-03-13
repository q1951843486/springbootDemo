package com.example.vo;

import lombok.Data;

/**
 * @Description
 * @ClassName ResultVo
 * @Author Administrator
 * @date 2020.03.12 16:22
 */
@Data
public class ResultVo<T> {
    public Integer code;
    public String msg;
    public T result;
    public Boolean flag;
}
