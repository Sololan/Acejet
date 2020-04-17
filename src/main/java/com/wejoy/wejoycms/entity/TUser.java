package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TUser extends BaseEntity {
    //用户名1
    private String username;
    //登陆密码
    private String password;
    //用户姓名
    private String fullName;
    //用户角色（超级管理员 superAdmin）
    private String role;

    //多余的
    //用户类型(1:系统管理员 2：平台用户)
    private Integer userType;
}
