package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TArticlePicture;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.service.service.CURDService;

import java.util.List;
import java.util.Map;

public interface ArticleService extends CURDService<TArticle, Long> {
    //文章的分页查询
    Page<TArticle> findPageByCondition(Map map);

    //根据栏目code获取下属所有文章
    List<TArticle> getArticleSByCode(String code);

    //根据栏目id获取下属所有文章
    List<TArticle> getArticleSBySubjectId(Long id);

    //根据文章id获取文章
    TArticle getArticleById(Long id);

    //添加/修改/删除文章
    String saveArticle(TArticle article);

    //根据文章id获取图片
    List<TArticlePicture> getPictureSByArticleId(Long id);

    List<TArticle> getAllSelectableArticle();

    //新增
    //根据输入字符串模糊匹配文章标题查询文章信息
    List<TArticle> getArticleSByArticleTitle(String articleTitle);
    //查询所有的栏目
    List<TSubject> getAllSubjectS();

    //根据文章id删除图片
    void deletePictureSByArticleId(Long articleId);

    //根据栏目id，增长系数，页数返回文章
    List<TArticle> getArticleForSubject(Long id, Integer coefficient, Integer page);
}
