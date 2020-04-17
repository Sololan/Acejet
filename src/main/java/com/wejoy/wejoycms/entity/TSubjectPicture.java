
package com.wejoy.wejoycms.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TSubjectPicture extends BaseEntity {

    //所属栏目
    private long subjectId;
    //图片（相对路径）除主图片之外的其它图片，按顺序存放
    private String picture;
    //图片标题
    private String pictureTitle;
    //排序
    private long sortNum;
    //图片类型（1：主图 2：其它图片）
    private Integer pictureType;
    //水印（0：不加水印 1：文字水印 2：logo图片水印）
    private Integer watermark;
    //水印文字
    private String watermarkText;

}
