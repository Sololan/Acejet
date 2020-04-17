package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper extends CURDMapper<TArticle, Long> {
    List<TArticle> getArticleSByCode(String code);

    List<TArticle> getArticleSBySubjectId(Long id);
    TArticle getArticleById(Long id);

    Integer insertArticle(TArticle article);

    Integer updateArticle(TArticle article);

    List<TArticle> findPageByCondition(Map map);

    //待删除
    void setMainArticle(Long id, String path);

    List<TArticle> getAllSelectableArticle();

    //根据文章标题(模糊匹配)查找文章信息
    List<TArticle> getArtcleSByArtcleTitle(String articleTitle);

    //查询所有的栏目列表
    List<TSubject> getAllSubjectS();

    //根据栏目id，增长系数，页数返回文章
    List<TArticle> getArticleForSubject(Long id, Integer offset, Integer coefficient);

}
