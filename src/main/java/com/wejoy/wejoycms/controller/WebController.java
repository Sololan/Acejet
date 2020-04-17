package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.service.ArticleService;
import com.wejoy.wejoycms.service.BannerService;
import com.wejoy.wejoycms.service.SubjectService;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Api(tags = "Web数据测试接口")
@RequestMapping("/test")
@RestController
public class WebController {
    @Autowired
    BannerService bannerService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    ArticleService articleService;

    @ApiOperation("获取启用的轮播列表")
    @GetMapping("/getAllOpenBannerS")
    public RespEntity<List<TBanner>> getAllOpenBannerS() {
        return CtrlUtil.exe(r -> r.setVal(bannerService.getAllOpenBannerS()));
    }

    @ApiOperation("根据栏目code获取子栏目列表及栏目信息")
    @GetMapping("/getSubjectSByCode/{code}")
    public RespEntity<Map<String, Object>> getSubjectSByCode(@PathVariable("code") String code) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getSubjectSByCode(code)));
    }

    @ApiOperation("根据栏目code获取文章列表")
    @GetMapping("/getArticleSByCode/{code}/{startNo}/{num}")
    public RespEntity<List<TArticle>> getArticleSByCode(@PathVariable("code") String code, @PathVariable("startNo") Integer startNo, @PathVariable("num") Integer num) {
        return CtrlUtil.exe(r -> r.setVal(articleService.getArticleSByCode(code)));
    }

    @ApiOperation("根据栏目id获取子栏目列表及栏目信息")
    @GetMapping("/getSubjectSById/{id}")
    public RespEntity<Map<String, Object>> getSubjectSById(@PathVariable("id") Long id) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getSubjectSById(id)));
    }

    @ApiOperation("根据栏目id获取文章列表")
    @GetMapping("/getArticleSBySubjectId/{id}/{startNo}/{num}")
    public RespEntity<List<TArticle>> getArticleSBySubjectId(@PathVariable("id") Long id, @PathVariable("startNo") Integer startNo, @PathVariable("num") Integer num) {
        return CtrlUtil.exe(r -> r.setVal(articleService.getArticleSBySubjectId(id)));
    }

    @ApiOperation("根据文章id获取文章详细信息")
    @GetMapping("/getArticleById/{id}")
    public RespEntity<TArticle> getArticleSBySubjectId(@PathVariable("id") Long id) {
        return CtrlUtil.exe(r -> r.setVal(articleService.getArticleById(id)));
    }


    @ApiOperation("获取当前的年份")
    @GetMapping("/getYear")
    public String getYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

}
