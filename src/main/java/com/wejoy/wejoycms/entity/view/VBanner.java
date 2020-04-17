package com.wejoy.wejoycms.entity.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VBanner {
    //banner标题
    private String bannerTitle;
    //图片（相对路径）
    private String picture;
    //状态（1：有效 0：无效）
    private Integer status;
    //链接的文章题目
    private String articleTitle;
    //连接的文章id
    private Long articleId;

    @Override
    public String toString() {
        return "VBanner{" +
                "bannerTitle='" + bannerTitle + '\'' +
                ", picture='" + picture + '\'' +
                ", status=" + status +
                ", articleTitle='" + articleTitle + '\'' +
                '}';
    }
}
