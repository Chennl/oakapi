package com.oak.service.impl;

import com.oak.dao.UserInfoDAO;
import com.oak.entity.UserInfo;
import com.oak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chennl on 6/23/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDAO userInfoDAO;

    @Override
    public List<UserInfo> getAllUsers() {
        return userInfoDAO.getAllUsers();
    }

    @Override
    public UserInfo getUserById(long id) {
        UserInfo obj = userInfoDAO.getUserById(id);
        return obj;
    }

    @Override
    public synchronized void addUser(UserInfo user) {
        userInfoDAO.addUser(user);
    }

    @Override
    public void updateUser(UserInfo user) {
        userInfoDAO.updateUser(user);
    }

    @Override
    public void deleteUser(long userId) {
        userInfoDAO.deleteUser(userId);
    }

    @Override
    public boolean isUserExist(UserInfo user) {
        return userInfoDAO.isUserExist(user.getUserName());
    }
}
