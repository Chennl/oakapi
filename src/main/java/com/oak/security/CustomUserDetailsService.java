package com.oak.security;

import com.oak.dao.UserInfoDAO;
import com.oak.model.UserInfo;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements  UserDetailsService{

    private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    UserInfoDAO userInfoDAO;

//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        UserInfo activeUserInfo = userInfoDAO.getActiveUser(userName);
//
//        if(activeUserInfo == null){
//            throw new UsernameNotFoundException("Username not found");
//        }
//        logger.info("User found:" + activeUserInfo.getFullName());
//        GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
//        UserDetails userDetail =  new User(activeUserInfo.getUserName(),
//                activeUserInfo.getPassword(), Arrays.asList(authority));
//        return userDetail;
//    }

    /**
     * 根据用户名获取用户 - 用户的角色、权限等信息
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            UserInfo favUser = new UserInfo();
            favUser.setUserName("admin");
            favUser.setPassword("admin");
            Collection<GrantedAuthority> authList = getAuthorities();
            userDetails = new User(username, favUser.getPassword().toLowerCase(),true,true,true,true,authList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

    /**
     * 获取用户的角色权限,为了降低实验的难度，这里去掉了根据用户名获取角色的步骤
     * @param
     * @return
     */
    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authList;
    }
}
