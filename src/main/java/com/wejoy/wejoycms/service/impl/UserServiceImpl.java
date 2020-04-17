package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.entity.TUser;
import com.wejoy.wejoycms.mapper.UserMapper;
import com.wejoy.wejoycms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public TUser getUser() {
        return userMapper.getUser();
    }

    @Override
    public void setPassword(String username, String password) {
        String encode = BCrypt.hashpw(password, BCrypt.gensalt());
        userMapper.setPassword(username, encode);
    }

    @Override
    public TUser getUserByUsername(String username) {
        return userMapper.getUserByName(username);
    }

}
