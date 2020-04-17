package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TSubjectLink;
import com.wejoyclass.core.page.Page;

import java.util.List;
import java.util.Map;

public interface SubjectLinkService {

    //查询
    //根据链接id获取链接信息
    TSubjectLink getLinkById(Long id);

    //根据所属栏目查找栏目下所有的链接信息
    List<TSubjectLink> getLinksBySubjectId(Long subjectId);

    //添加/修改/删除链接--无法理解，没完成实现类
    String saveSubjectLink(TSubjectLink tSubjectLink);

    //删除
    //根据id删除链接
    String deleteLinkById(Long id);
    //根据id更新链接
    String updateLinkById(TSubjectLink tSubjectLink);
    //根据栏目id插入链接
    String insertLinkBySubjectId(TSubjectLink tSubjectLink);

    //给链接降序/升序排列
    List<TSubjectLink> getLinkSSortDESC();
    List<TSubjectLink> getLinkSSortASC();


}
