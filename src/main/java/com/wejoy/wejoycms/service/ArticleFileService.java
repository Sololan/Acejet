package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TArticleFile;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleFileService {
    List<TArticleFile> getFileSByArticleId(Long articleId);
    void saveFile(TArticleFile articleFile);
    void deleteById(Long Id);
}
