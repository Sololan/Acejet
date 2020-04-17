package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

@Table(name = "t_article")
@Getter
@Setter
public class TArticle extends BaseEntity {
    //文章标题
    private String articleTitle;
    //文章副标题
    private String articleSubtitle;
    //文章简介
    private String articleBrief;
    //所属栏目ID
    private Long subjectId;
    //模板ID
    private Long templateId;
    //图片路径
    private String picture;
    //状态（1：有效 0：无效）
    private Integer status;
    //文章内容
    private String content;

    //数据库内没有
    //文章图片
    private List<Map<String, Object>> picList;
    //文章图片的id
    private Long apid;

    //新增字段
    //排序
    private long sortNum;

    //文章图片
    private List<TArticlePicture> articlePictureList;

    //文章附件
    @Transient
    private List<TArticleFile> articleFileList;
}
