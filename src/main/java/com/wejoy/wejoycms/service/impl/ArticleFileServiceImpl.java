package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.entity.TArticleFile;
import com.wejoy.wejoycms.mapper.ArticleFileMapper;
import com.wejoy.wejoycms.service.ArticleFileService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleFileServiceImpl extends BaseCURDServiceImpl<TArticleFile, Long> implements ArticleFileService {
    @Autowired
    ArticleFileMapper articleFileMapper;


    @Override
    public List<TArticleFile> getFileSByArticleId(Long articleId) {
        return articleFileMapper.getFileSByArticleId(articleId);
    }

    @Override
    public void saveFile(TArticleFile articleFile) {
        articleFileMapper.saveFile(articleFile);
    }

    @Override
    public void deleteById(Long id) {
        articleFileMapper.deleteById(id);
    }
}
