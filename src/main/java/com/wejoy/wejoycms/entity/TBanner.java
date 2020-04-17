package com.wejoy.wejoycms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBanner extends BaseEntity {
    //banner标题
    private String bannerTitle;
    //图片（相对路径）
    private String picture;
    //状态（1：有效 0：无效）
    private Integer status;
    //链接的文章ID
    private Long articleId;
    //排序
    private Long sortNum;


    @Override
    public String toString() {
        return "TBanner{" +
                "bannerTitle='" + bannerTitle + '\'' +
                ", picture='" + picture + '\'' +
                ", status=" + status +
                ", articleId=" + articleId +
                ", sortNum=" + sortNum +
                '}';
    }
}
