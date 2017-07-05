package com.oak.dao.impl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.oak.dao.UserInfoDAO;
import com.oak.model.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Chennl on 6/23/2017.
 */
@Repository
@Transactional
public class UserInfoDAOImpl implements UserInfoDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public UserInfo getActiveUser(String userName) {
        UserInfo activeUserInfo = new UserInfo();
        short enabled = 1;
        List<?> list = entityManager.createQuery("SELECT u FROM UserInfo u WHERE userName=? and enabled=?")
                .setParameter(1, userName).setParameter(2, enabled).getResultList();
        if(!list.isEmpty()) {
            activeUserInfo = (UserInfo)list.get(0);
        }
        return activeUserInfo;
    }

    @Override
    public List<UserInfo> getAllUsers() {

        String hql = "FROM UserInfo as u ORDER BY u.id";
        return (List<UserInfo>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public UserInfo getUserById(long id) {
        UserInfo obj = entityManager.find(UserInfo.class,id);
        return obj;
    }

    @Override
    public void addUser(UserInfo user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(UserInfo user) {

    }

    @Override
    public void deleteUser(long id) {
        entityManager.detach(getUserById(id));
    }

    @Override
    public boolean isUserExist(String userName) {
        String hql = "FROM UserInfo as u WHERE u.userName=?";
        int count =   entityManager.createQuery(hql).setParameter(1,userName).getResultList().size();
        return count > 0 ? true : false;
    }
}

