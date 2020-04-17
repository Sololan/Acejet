package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TSubjectPicture;

import java.util.List;

public interface SubjectPictureService {
    String TABLE_NAME = "t_subject";
    String INSERT_FILEDS = "user_id, content, created_date, entity_id, entity_type, status";
//    String SELECT_FIELDS = "id, " + INSERT_FILEDS;

    //查询
    // 1、根据图片id查询图片信息
    TSubjectPicture getPictureById(Long id);

    // 2、根据栏目id查询所有栏目图片信息
    List<TSubjectPicture> getPictureSBySubjectId(Long subjectId);

    // 3、根据图片类型PictureType查询图片信息
    List<TSubjectPicture> getPictureSByPictureType(Integer pictureType);

    // 4、根据水印类型WaterMark查询图片信息
    List<TSubjectPicture> getPictureSByWatermark(Integer watermark);

    //排序
    List<TSubjectPicture> getPictureSSortASC();
    List<TSubjectPicture> getPictureSSortDESC();

    //添加/修改/删除图片---未实现
    String saveSubjectPicture(TSubjectPicture tSubjectPicture);

    //删除
    // 根据图片id删除图片
    String deletePictureById(Long id);

    //更新
    //根据图片id更新图片信息
    String updatePictureById(TSubjectPicture tSubjectPicture);

    //插入
    // 根据栏目id向栏目中增加图片对象
    String insertPictureBySubjectId(TSubjectPicture tSubjectPicture);

    String addPictures(List<TSubjectPicture> pictures);

    TSubjectPicture getMainImg(long subjectId);


    String updateMainImg(TSubjectPicture tSubjectPicture);
}
