package com.meiya.service;

import com.meiya.modul.User;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @ClassName UserServcie
 * @Author Administrator
 * @date 2020.03.04 17:02
 */
public interface UserService {
    /**
     * 根据id查询单条数据
     * @param id
     * @return
     */
    User queryUserById(long id);

    /**
     * 查询总条数
     * @return
     */
    long queryUserTotal();

    /**
     * 添加一条数据到集合中
     * @param user
     */
    void add(User user);

    /**
     * 根据id更新单条数据
     * @param user
     * @return
     */
    User updateUserById(User user);

    /**
     * 根据id删除单条数据
     * @param i
     * @return
     */
    int deleteUserById(Long i);

    /**
     * 分页查询
     * @param parmMap
     * @return
     */
    List<User> queryUserPageList(Map<String, Object> parmMap);

    /**
     * 查询 name and age
     * @param parmMap
     * @return
     */
    List queryUserListByNameAndAge(Map parmMap);

    /**
     * 查询 name or age
     * @param parmMap
     * @return
     */
    List queryUserListByNameOrAge(Map parmMap);
}
