package com.wejoy.wejoycms.service;

public interface TemplateService {
    //根据模板id获取模板路径
    String getPathById(Long id);
    //根据栏目code获取模板路径
    String getPathByCode(String code);
    //根据文章id获取模板路径
    String getPathByArticleId(Long id);
}
