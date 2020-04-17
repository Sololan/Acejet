package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TArticleFile {
    //文章id
    private Long id;
    //所属文章id
    private Long articleId;
    //文件名
    private String fileTitle;
    //文件路径
    private String filePath;
    //排序号
    private Long sortNum;
}
