package com.oak.service;

import com.oak.entity.UserInfo;

import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
public interface UserService {
    List<UserInfo> getAllUsers();
    UserInfo getUserById(long id);
    void addUser(UserInfo user);
    void updateUser(UserInfo user);
    void deleteUser(long userId);
    boolean isUserExist(UserInfo user);
}
