package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.dto.SubjectDto;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    //根据栏目code获取子栏目列表及栏目信息
    Map<String, Object> getSubjectSByCode(String code);

    //根据栏目id获取子栏目列表及栏目信息
    Map<String, Object> getSubjectSById(Long id);

    //获得所有栏目
    List<TSubject> getAllSubjectS();

    //判断code是否存在
    String isCodeExists(String code);

    //添加/修改/删除栏目
    String saveSubject(TSubject subject);

    //根据id查询栏目详细信息
    TSubject getSubjectById(Long id);

    //根据code查询id
    Long getIdByCode(String code);

    Integer addSubject(TSubject subject);

    Integer updateSubject(TSubject subject);

    Boolean deleteSubject(Long id);

    List<SubjectDto> getSubjectSAndPictures();

    List<TSubject> getParentSubjectS();

    Map<String, Object> getSubSubjectS(long id, int page, int limit);
    //获取所有一级栏目
    List<SubjectDto> getSubSubjectS(Long id);

    List<TSubject> getAllParentSubjectS();


    String updateSubjectAndMainImg(SubjectDto subjectDto);

    //根据code获取二级栏目及图片列表
    List<TSubject> getSubjectAndPic(String code);

    TSubject getArticleAndFile(String code);
}
