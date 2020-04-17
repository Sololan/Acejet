package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.mapper.TemplateMapper;
import com.wejoy.wejoycms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    TemplateMapper templateMapper;

    @Override
    public String getPathById(Long id) {
        return templateMapper.getPathById(id);
    }

    @Override
    public String getPathByCode(String code) {
        return templateMapper.getPathByCode(code);
    }

    @Override
    public String getPathByArticleId(Long id) {
        return templateMapper.getPathByArticleId(id);
    }
}
