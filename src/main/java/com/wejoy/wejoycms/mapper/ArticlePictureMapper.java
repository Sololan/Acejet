package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TArticlePicture;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticlePictureMapper {
    List<TArticlePicture> getPictureSByArticleId(Long id);

    void insertPictureByArticle(TArticlePicture articlePicture);

    void deletePictureByArticleId(Long id);

    //根据文章id将所有图片pictureType更新为0
    void updatePictureType(Long articleId);
}
