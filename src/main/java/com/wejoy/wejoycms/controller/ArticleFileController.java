package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TArticleFile;
import com.wejoy.wejoycms.service.ArticleFileService;
import com.wejoy.wejoycms.service.ArticleService;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "文章附件管理")
@RequestMapping("/article/file")
public class ArticleFileController {
    @Autowired
    ArticleFileService articleFileService;

    @ApiOperation("根据文章id获取文章附件")
    @GetMapping("/{id}/articleId")
    public RespEntity<List<TArticleFile>> getFileSByArticleId(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(articleFileService.getFileSByArticleId(id)));
    }

    @ApiOperation("保存文章附件")
    @PostMapping()
    public RespEntity saveFile(@RequestBody TArticleFile tArticleFile) {
        return CtrlUtil.exe(r -> {articleFileService.saveFile(tArticleFile);});
    }

    @ApiOperation("删除文章附件")
    @GetMapping("/{id}/delete")
    public RespEntity<List<TArticleFile>> getArticleById(@PathVariable Long id) {
        return CtrlUtil.exe(r -> {articleFileService.deleteById(id);});
    }
}
