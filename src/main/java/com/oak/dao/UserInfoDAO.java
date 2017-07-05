package com.oak.dao;


import com.oak.model.UserInfo;

import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
public interface UserInfoDAO {
    UserInfo getActiveUser(String userName);
    List<UserInfo> getAllUsers();
    UserInfo getUserById(long id);
    void addUser(UserInfo user);
    void updateUser(UserInfo user);
    void deleteUser(long id);
    boolean isUserExist(String username);
}
