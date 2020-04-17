package com.wejoy.wejoycms.util;

import com.wejoy.wejoycms.entity.BaseEntity;
import com.wejoy.wejoycms.entity.TArticle;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class EntityUtil {


    public static void setCreate(BaseEntity entity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        entity.setCreateUser(new Long(1));
        entity.setCreateUserName(username);
        entity.setCreateTime(new Date());
    }

    public static void setUpdate(BaseEntity entity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        entity.setUpdateUser(new Long(1));
        entity.setUpdateUserName(username);
        entity.setUpdateTime(new Date());
    }

    public static void setDelete(BaseEntity entity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        entity.setDeleteUser(new Long(1));
        entity.setDeleteUserName(username);
        entity.setDeleteTime(new Date());
        entity.setDeleteFlag(1);
    }
}
