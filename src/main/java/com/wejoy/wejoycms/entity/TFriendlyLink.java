package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

@Setter
@Getter
public class TFriendlyLink extends BaseEntity {

    //友情链接分类ID
    private Long linkTypeId;    //新增
    //链接名称
    private String linkName;
    //图片（相对路径）
    private String picture;
    //链接URL
    private String linkUrl;
    //排序
    private Long sortNum;
    //友情链接分类名称
    @Transient
    private String linkTypeName;

}
