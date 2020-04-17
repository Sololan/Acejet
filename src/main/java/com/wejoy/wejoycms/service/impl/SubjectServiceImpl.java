package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.TSubjectPicture;
import com.wejoy.wejoycms.entity.dto.SubjectDto;
import com.wejoy.wejoycms.mapper.SubjectMapper;
import com.wejoy.wejoycms.mapper.SubjectPictureMapper;
import com.wejoy.wejoycms.service.SubjectPictureService;
import com.wejoy.wejoycms.service.SubjectService;
import com.wejoy.wejoycms.util.EntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = false)
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    SubjectPictureMapper subjectPictureMapper;

    @Autowired
    private SubjectPictureService subjectPictureService;

    @Override
    public Map<String, Object> getSubjectSByCode(String code) {
        List<TSubject> subjectList = subjectMapper.getSonSubjectSByCode(code);
        TSubject subjectMsg = subjectMapper.getSubjectMsgByCode(code);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectList", subjectList);
        map.put("subjectMsg", subjectMsg);
        return map;
    }

    //2.
    @Override
    public Map<String, Object> getSubjectSById(Long id) {
        List<TSubject> subjectList = subjectMapper.getSonSubjectSById(id);
        TSubject subjectMsg = subjectMapper.getSubjectMsgById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectList", subjectList);
        map.put("subjectMsg", subjectMsg);
        return map;
    }

    //1.
    @Override
    public List<TSubject> getAllSubjectS() {
        return subjectMapper.getAllSubjectS();
    }

    @Override
    public String isCodeExists(String code) {
        return subjectMapper.isCodeExists(code);
    }

    //4.
    //添加/修改/删除栏目
    @Override
    public String saveSubject(TSubject subject) {
        if (subject.getId() == null) {
            EntityUtil.setCreate(subject);
            Integer num = subjectMapper.insertSubject(subject);
            if (num == 0) return "插入失败";
            return "插入成功";
        }
        if (subject.getDeleteFlag() == 1) {
            Integer num = subjectMapper.updateSubject(subject);
            if (num == 0) return "删除失败";
            return "删除成功";
        }
        Integer num = subjectMapper.updateSubject(subject);
        if (num == 0) return "更新失败";
        return "更新成功";
    }

    //3.
    @Override
    public TSubject getSubjectById(Long id) {
        return subjectMapper.getSubjectById(id);
    }

    @Override
    public Long getIdByCode(String code) {
        return subjectMapper.getIdByCode(code);
    }

    @Override
    public Integer addSubject(TSubject subject) {
        Long pid = subject.getPId();
        //ctrl参数校验 pid非空, not null in db
        if (pid == -1) {
            subject.setIsEdit(0);
            subject.setIsDelete(0);
            subject.setIsAppendChild(1);
        } else {
            subject.setIsEdit(1);
            subject.setIsDelete(1);
            subject.setIsAppendChild(0);
        }
        subject.setTemplateId((long) 8);
        subject.setSubjectCode("default");
        EntityUtil.setCreate(subject);
        return subjectMapper.insertSubject(subject);
    }

    @Override
    public Integer updateSubject(TSubject subject) {
        return subjectMapper.updateSubject(subject);
    }

    @Override
    public String updateSubjectAndMainImg(SubjectDto subjectDto) {
        String val1 = "updateSubject: " + subjectMapper.updateSubject(subjectDto);
        String val2 = "|updateMainImg: ";
        List<TSubjectPicture> pictures = subjectDto.getPictures();
        log.info("imgs, {}", pictures);
        log.info("size, {}", pictures.size());
        if (pictures.size() != 0) {
            val2 += subjectPictureService.updateMainImg(pictures.get(0));    //todo
        } else {
            val2 += 0;
        }
        return val1 + val2;
    }

    @Override
    public Boolean deleteSubject(Long id) {
        return subjectMapper.updateDeleteFlag(id, 1) > 0;
    }

    @Override
    public List<SubjectDto> getSubjectSAndPictures() {

        List<SubjectDto> subjects = subjectMapper.getSubjectS();
        for (SubjectDto subject : subjects) {
            List<TSubjectPicture> pictures = subjectPictureService.getPictureSBySubjectId(subject.getId());
            subject.setPictures(pictures);
        }
        return subjects;
    }

    @Override
    public List<TSubject> getParentSubjectS() {
        return subjectMapper.getParentSubjectS();
    }

    @Override
    public Map<String, Object> getSubSubjectS(long id, int page, int offset) {
        int limit = (page - 1) * 10;
        Map<String, Object> map = new HashMap<>();
        map.put("subSubjects", subjectMapper.getSubSubjectSByIdPage(id, limit, offset));
        map.put("count", subjectMapper.countSubSubjectSById(id));
        return map;
    }

    @Override
    public List<SubjectDto> getSubSubjectS(Long id) {
        return subjectMapper.getSubSubjectSById(id);
    }

    @Override
    public List<TSubject> getAllParentSubjectS() {
        return subjectMapper.getAllParentSubjectS();
    }

    @Override
    public List<TSubject> getSubjectAndPic(String code) {
        return subjectMapper.getSonSubjectSByCode(code);
    }

    @Override
    public TSubject getArticleAndFile(String code) {
        return subjectMapper.getArticleAndFile(code);
    }
}
