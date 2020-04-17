package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TArticleFile;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleFileMapper extends CURDMapper<TArticleFile, Long> {
    List<TArticleFile> getFileSByArticleId(Long articleId);
    void saveFile(TArticleFile articleFile);
    void deleteById(Long id);
}
