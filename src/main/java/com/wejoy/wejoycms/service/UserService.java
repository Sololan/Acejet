package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TUser;

public interface UserService {

    TUser getUser();

    void setPassword(String username, String password);

    TUser getUserByUsername(String username);
}
