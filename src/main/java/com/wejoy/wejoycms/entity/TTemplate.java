package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TTemplate extends BaseEntity {
    //模板名称
    private String templateName;
    //模板CODE
    private String templateCode;
    //模板分类（1：栏目 2：文章）
    private Integer templateType;
    //模板文件名（相对路径/文件名）
    private Integer templatePath;
}
