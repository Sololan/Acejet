package com.wejoy.wejoycms.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TArticlePicture extends BaseEntity {
    //文章id
    private Long articleId;
    //图片标题
    private String pictureTitle;
    //图片路径
    private String picture;
    //排序
    private Integer sortNum;
    //图片类型
    private Integer pictureType;
    //水印
    private Integer watermark;
    //水印文字
    private String watermarkText;
}
