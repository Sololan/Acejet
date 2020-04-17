package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.entity.TSubjectPicture;
import com.wejoy.wejoycms.mapper.SubjectPictureMapper;
import com.wejoy.wejoycms.service.SubjectPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectPictureServiceImpl implements SubjectPictureService {

    @Autowired
    SubjectPictureMapper subjectPictureMapper;


    @Override
    public TSubjectPicture getPictureById(Long id) {
        return subjectPictureMapper.getPictureById(id);
    }

    @Override
    public List<TSubjectPicture> getPictureSBySubjectId(Long subjectId) {
        return subjectPictureMapper.getPictureSBySubjectId(subjectId);
    }

    @Override
    public List<TSubjectPicture> getPictureSByPictureType(Integer pictureType) {
        return subjectPictureMapper.getPictureSByPictureType(pictureType);
    }

    @Override
    public List<TSubjectPicture> getPictureSByWatermark(Integer watermark) {
        return subjectPictureMapper.getPictureSByWatermark(watermark);
    }

    @Override
    public List<TSubjectPicture> getPictureSSortASC() {
        return subjectPictureMapper.getPictureSSortASC();
    }

    @Override
    public List<TSubjectPicture> getPictureSSortDESC() {
        return subjectPictureMapper.getPictureSSortDESC();
    }


    //关于增删改的总和
    @Override
    public String saveSubjectPicture(TSubjectPicture tSubjectPicture) {
        return null;
    }

    @Override
    public String deletePictureById(Long id) {
        Integer num = subjectPictureMapper.deletePictureById(id);
        if (num != null) return "删除成功";
        return "删除失败";
    }

    @Override
    public String updatePictureById(TSubjectPicture tSubjectPicture) {
        Integer num = subjectPictureMapper.updatePictureById(tSubjectPicture);
        if (num != null) return "更新成功";
        return "更新失败";
    }

    @Override
    public String insertPictureBySubjectId(TSubjectPicture tSubjectPicture) {
        Integer num = subjectPictureMapper.insertPictureBySubjectId(tSubjectPicture);
        if (num != null) return "插入成功";
        return "插入失败";
    }

    @Override
    public String addPictures(List<TSubjectPicture> pictures) {
        int addSize = pictures.size();
        if (addSize > 6) {
            return "图片个数超过限制";
        }
        int curSize = subjectPictureMapper.countSubjectImgs(pictures.get(0).getSubjectId());
        if (addSize + curSize > 10) {
            return "图片个数超过限制";
        }
        Integer count = subjectPictureMapper.addPictures(pictures);
        return "图片添加成功";
    }

    @Override
    public TSubjectPicture getMainImg(long subjectId) {
        return subjectPictureMapper.getMainImg(subjectId);
    }

    @Override
    public String updateMainImg(TSubjectPicture tSubjectPicture) {
        long subjectId = tSubjectPicture.getSubjectId();
        Integer deleteCount = subjectPictureMapper.deleteMainImg(subjectId);
        Integer addCount = subjectPictureMapper.addImg(tSubjectPicture);
        return "delete: " + deleteCount + "|add: " + addCount;
    }
}
