package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
public class TSubject extends BaseEntity {
    //栏目名称
    private String subjectName;
    //栏目CODE
    private String subjectCode;
    //是否启用(0:未启用 1：启用)
    private Integer status;
    //模板ID
    private Long templateId;
    //父栏目ID
    private Long pId;
    //栏目简介
    private String subjectBrief;

    //新增字段

    //栏目副名称
    private String subjectSubName;
    //在父栏目列表页面上的展现方式（1：xxx 2:xxx 3:xxx 4：）1、2、3具体表示什么方式，由各项目自行定义，在此项目中，可分为A、B、C、D四类
    private Integer showType;
    //是否可编辑(0：不可编辑  1:可编辑)
    private Integer isEdit;
    //是否可删除(0：不可删除  1:可删除 )
    private Integer isDelete;
    //是否可添加子栏目(0：不可添加  1:可添加 )
    private Integer isAppendChild;
    //排序
    private Long sortNum;

    @Transient
    private List<TSubjectPicture> subjectPictureList;
    @Transient
    private List<TSubjectLink> subjectLinkList;
    @Transient
    private List<TArticle> subjectArticleList;
}
