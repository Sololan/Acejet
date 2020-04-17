package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TUser;
import com.wejoy.wejoycms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private UserMapper userDao;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //从数据库根据用户名获取用户信息
        System.out.println(s);
        TUser userByName = userDao.getUserByName(s);
        //创建一个新的UserDetails对象，最后验证登陆的需要
        UserDetails userDetails = null;
        if (userByName != null) {
            System.out.println(userByName.getPassword());
            //创建一个集合来存放权限
            Collection<GrantedAuthority> authorities = getAuthorities(userByName);
            //实例化UserDetails对象
            userDetails = new User(s, userByName.getPassword(), true, true, true, true, authorities);
        }
        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(TUser user) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        //注意：这里每个权限前面都要加ROLE_。否在最后验证不会通过
        authList.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authList;
    }
}