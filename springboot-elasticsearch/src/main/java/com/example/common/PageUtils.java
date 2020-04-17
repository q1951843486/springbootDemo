package com.example.common;

/**
 * @Description
 * @ClassName PageUtils
 * @Author Administrator
 * @date 2020.04.16 14:27
 */
public class PageUtils {
    public static Long getPages(Long count,Integer pageSize){
        return count%pageSize == 0?count/pageSize:count/pageSize +1;
    }
}
