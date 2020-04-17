package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

@Setter
@Getter
public class TSubjectLink {
    //id
    private Long id;
    //所属栏目
    private Long subjectId;
    //链接名称
    private String linkName;
    //跳转链接URL
    private String linkUrl;
    //排序
    private Long sortNum;

    private Integer linkType;
    //栏目名称
    @Transient
    private String subjectName;
}
