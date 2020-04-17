package com.wejoy.wejoycms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TArticlePicture;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.TSubjectPicture;
import com.wejoy.wejoycms.mapper.ArticleMapper;
import com.wejoy.wejoycms.mapper.ArticlePictureMapper;
import com.wejoy.wejoycms.mapper.TemplateMapper;
import com.wejoy.wejoycms.service.ArticleService;
import com.wejoy.wejoycms.util.EntityUtil;
import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.params.Request;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl extends BaseCURDServiceImpl<TArticle, Long> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticlePictureMapper articlePictureMapper;

    @Autowired
    TemplateMapper templateMapper;

    @Override
    public Page<TArticle> findPageByCondition(Map map) {
        List<TArticle> articleList = articleMapper.findPageByCondition(map);
        PageHelper.startPage((Integer) map.get("page"), (Integer) map.get("limit"));
        PageInfo<TArticle> page = new PageInfo(articleList);
        Page<TArticle> result = new Page(page.getPageSize(), true);
        result.setPageNo(page.getPageNum());
        result.setTotalCount((int) page.getTotal());
        result.setTotalRows((int) page.getTotal());
        result.setResult(new ArrayList(articleList));
        return result;
    }

    @Override
    public List<TArticle> getArticleSByCode(String code) {
        return articleMapper.getArticleSByCode(code);
    }

    @Override
    public List<TArticle> getArticleSBySubjectId(Long id) {
        return articleMapper.getArticleSBySubjectId(id);
    }

    @Override
    public TArticle getArticleById(Long id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public String saveArticle(TArticle article) {
        List<Map<String, Object>> picList = article.getPicList();

        Long articleTemplateId = templateMapper.getTemplateIdByCode("Article");
        article.setTemplateId(articleTemplateId);

        //插入文章
        if (article.getId() == null) {
            EntityUtil.setCreate(article);
            Integer num = articleMapper.insertArticle(article);
            for (Map map : picList) {
                if ((Integer) map.get("pictureType") == 1) {
                    article.setPicture((String) map.get("picture"));
                    //待删除;
                    articleMapper.setMainArticle(article.getId(), (String) map.get("picture"));
                }
                TArticlePicture tArticlePicture = new TArticlePicture();
                tArticlePicture.setSortNum(1);
                tArticlePicture.setArticleId(article.getId());
                tArticlePicture.setPicture((String) map.get("picture"));
                tArticlePicture.setPictureType((Integer) map.get("pictureType"));
                tArticlePicture.setWatermark((Integer) map.get("watermark"));
                tArticlePicture.setWatermarkText((String) map.get("watermarkText"));
                articlePictureMapper.insertPictureByArticle(tArticlePicture);
            }

            //获取主键，插入其他表中
            if (num == null) return "插入失败";
            else {
                return "插入成功";
            }

        }
        System.out.println(article.getDeleteFlag());

        //若标记删除，则为可删除
        if (article.getDeleteFlag() == 1) {
            Integer num = articleMapper.updateArticle(article);
            articlePictureMapper.deletePictureByArticleId(article.getId());
            EntityUtil.setDelete(article);
            if (num == 0) return "删除失败";
            return "删除成功";
        }
        article.setPicture("null");

        //更新文章信息
        Integer num = articleMapper.updateArticle(article);
        //根据文章id删除原图片
        articlePictureMapper.deletePictureByArticleId(article.getId());
        for (Map map : picList) {
            System.out.println(map);
            if ((Integer) map.get("pictureType") == 1) {
                article.setPicture((String) map.get("picture"));
                //待删除;
                articleMapper.setMainArticle(article.getId(), (String) map.get("picture"));
            }
            TArticlePicture tArticlePicture = new TArticlePicture();
            tArticlePicture.setArticleId(article.getId());
            tArticlePicture.setSortNum(1);
            tArticlePicture.setPicture((String) map.get("picture"));
            tArticlePicture.setPictureType((Integer) map.get("pictureType"));
            tArticlePicture.setWatermark((Integer) map.get("watermark"));
            tArticlePicture.setWatermarkText((String) map.get("watermarkText"));
            articlePictureMapper.insertPictureByArticle(tArticlePicture);
        }
        System.out.println(article);
        EntityUtil.setUpdate(article);
        if (num == 0) return "更新失败";
        return "更新成功";
    }

    @Override
    public List<TArticlePicture> getPictureSByArticleId(Long id) {
        return articlePictureMapper.getPictureSByArticleId(id);
    }

    @Override
    public List<TArticle> getAllSelectableArticle() {
        return articleMapper.getAllSelectableArticle();
    }

    @Override
    public List<TArticle> getArticleSByArticleTitle(String articleTitle) {
        return articleMapper.getArtcleSByArtcleTitle(articleTitle);
    }

    //获取所有的栏目
    @Override
    public List<TSubject> getAllSubjectS() {
        return articleMapper.getAllSubjectS();
    }

    //根据文章id删除图片信息
    @Override
    public void deletePictureSByArticleId(Long articleId) {
        articlePictureMapper.deletePictureByArticleId(articleId);
    }

    @Override
    public List<TArticle> getArticleForSubject(Long id, Integer coefficient, Integer page) {
        Integer offset = (page - 1) * coefficient;
        return articleMapper.getArticleForSubject(id, offset, coefficient);
    }

}
