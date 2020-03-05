package com.meiya.service;

import com.meiya.modul.User;

/**
 * @Description
 * @ClassName UserServcie
 * @Author Administrator
 * @date 2020.03.04 17:02
 */
public interface UserService {
    User queryUserById(long id);

    long queryUserTotal();

    void add(User user);

    User updateUserById(User user);

    int deleteUserById(int i);
}
