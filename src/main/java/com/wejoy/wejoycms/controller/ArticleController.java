package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TArticlePicture;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.mapper.ArticlePictureMapper;
import com.wejoy.wejoycms.service.ArticleService;
import com.wejoy.wejoycms.util.EntityUtil;
import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.page.QueryParameter;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.net.nntp.Article;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@Api(tags = "文章管理")
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    ArticlePictureMapper articlePictureMapper;

    @Value("${picturepath.upload-url}")
    private String uploadUrl;

    @Value("${picturepath.loading-url}")
    private String loadingUrl;


    @ApiOperation("文章分页的条件查询")
    @GetMapping("/getArticleSPageByCondition")
    public RespEntity<Page<TArticle>> getArticleSPageByCondition(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page, @RequestParam("articleTitle") String articleTitle
            , @RequestParam("articleSubtitle") String articleSubtitle, @RequestParam("articleStatus") Integer articleStatus, @RequestParam("sortNum") Long sortNum, @RequestParam("subjectId") Long subjectId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("limit", limit);
        Integer offset = (page - 1) * limit;
        map.put("page", page);
        map.put("offset", offset);
        map.put("articleTitle", articleTitle);
        map.put("articleSubtitle", articleSubtitle);
        map.put("articleStatus", articleStatus);
        map.put("subjectId", subjectId);
        map.put("sortNum", sortNum);
        Page<TArticle> pageByCondition = articleService.findPageByCondition(map);
        return CtrlUtil.exe(r -> r.setVal(articleService.findPageByCondition(map)));
    }


    @ApiOperation("根据文章id查询文章详细信息")
    @GetMapping("/getArticleById/{id}")
    public RespEntity<TArticle> getArticleById(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(articleService.getArticleById(id)));
    }

    @ApiOperation("修改/删除/添加文章信息")
    @PutMapping("/saveArticle")
    public RespEntity<String> saveArticle(@RequestBody TArticle article) {
        return CtrlUtil.exe(r -> r.setVal(articleService.saveArticle(article)));
    }

    @ApiOperation("根据文章ID获取文章图片")
    @GetMapping("/getPictureSByArticleId/{id}")
    public RespEntity<List<TArticlePicture>> getPictureSByArticleId(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(articlePictureMapper.getPictureSByArticleId(id)));
    }

    //新增
    //获取所有的栏目
    @ApiOperation("获取所有的栏目")
    @GetMapping("/getAllSubjectS")
    public List<TSubject> getAllSubjectS() {
        return articleService.getAllSubjectS();
    }

    //根据文章id删除图片
    @ApiOperation("根据文章id删除图片")
    @GetMapping("/deletePictureSByArticleId/{articleId}")
    public void deletePictureSByArticleId(@PathVariable Long articleId) {
        articleService.deletePictureSByArticleId(articleId);
    }


}
