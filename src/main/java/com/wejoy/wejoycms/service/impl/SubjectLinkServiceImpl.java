package com.wejoy.wejoycms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wejoy.wejoycms.entity.TSubjectLink;
import com.wejoy.wejoycms.mapper.SubjectLinkMapper;
import com.wejoy.wejoycms.service.SubjectLinkService;
import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.util.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SubjectLinkServiceImpl implements SubjectLinkService {

    @Autowired
    private SubjectLinkMapper subjectLinkMapper;

    @Override
    public TSubjectLink getLinkById(Long id) {
        return subjectLinkMapper.getLinkById(id);
    }

    @Override
    public List<TSubjectLink> getLinksBySubjectId(Long subjectId) {
        return subjectLinkMapper.getLinkSBySubjectId(subjectId);
    }

    //增删改的总和--未实现
    @Override
    public String saveSubjectLink(TSubjectLink tSubjectLink) {
        return null;
    }

    @Override
    public String deleteLinkById(Long id) {
        Integer num = subjectLinkMapper.deleteLinkById(id);
        if (num != null) return "删除成功!";
        return "删除失败!";
    }

    @Override
    public String updateLinkById(TSubjectLink tSubjectLink) {
        Integer num = subjectLinkMapper.updateLinkById(tSubjectLink);
        if (num != null) return "更新成功";
        return "更新失败";
    }

    @Override
    public String insertLinkBySubjectId(TSubjectLink tSubjectLink) {
        Integer num = subjectLinkMapper.insertLinkBySubjectId(tSubjectLink);
        if (num != null) return "插入成功";
        return "插入失败";
    }

    //排序
    @Override
    public List<TSubjectLink> getLinkSSortDESC() {
        return subjectLinkMapper.getLinkSSortDESC();
    }

    @Override
    public List<TSubjectLink> getLinkSSortASC() {
        return subjectLinkMapper.getLinkSSortASC();
    }


}
