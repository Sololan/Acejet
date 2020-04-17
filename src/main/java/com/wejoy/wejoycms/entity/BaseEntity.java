package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public abstract class BaseEntity implements Serializable {
    private Long id;
    private Long createUser;
    private String createUserName;
    private Date createTime;
    private Long updateUser;
    private String updateUserName;
    private Date updateTime;
    private Long deleteUser;
    private String deleteUserName;
    private Date deleteTime;
    private Integer deleteFlag;
}
