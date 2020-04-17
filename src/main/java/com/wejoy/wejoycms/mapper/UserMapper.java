package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.userdetails.User;

@Mapper
public interface UserMapper {


    public TUser getUser();

    TUser findByUsername(String username);

    //spring security
    @Select("SELECT * FROM t_user where username=#{s}")
    TUser getUserByName(String s);

    void setPassword(String username, String password);
}
