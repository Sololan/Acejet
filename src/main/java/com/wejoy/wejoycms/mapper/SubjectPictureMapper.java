
package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.TSubjectPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface SubjectPictureMapper {

    String TABLE_NAME = "t_subject_picture";

    String INSERT_FIELDS = "SUBJECT_ID, PICTURE, PICTURE_TITLE, SORT_NUM, PICTURE_TYPE, WATERMARK, WATERMARK_TEXT";

    String SELECT_FIELDS = "ID, " + "SUBJECT_ID, PICTURE, PICTURE_TITLE, SORT_NUM, PICTURE_TYPE, WATERMARK, WATERMARK_TEXT";

    //查询
    // 1、根据图片id查询图片信息
    TSubjectPicture getPictureById(Long id);

    // 2、根据栏目id查询所有栏目图片信息
    List<TSubjectPicture> getPictureSBySubjectId(Long subjectId);

    // 3、根据图片类型PictureType查询图片信息
    List<TSubjectPicture> getPictureSByPictureType(Integer pictureType);

    //4、根据水印类型WaterMark查询图片信息
    List<TSubjectPicture> getPictureSByWatermark(Integer watermark);

    //排序
    //给图片对象排序[升序]
    List<TSubjectPicture> getPictureSSortASC();
    //给图片对象排序[降序]
    List<TSubjectPicture> getPictureSSortDESC();

    //删除
    // 根据图片id删除栏目图片
    Integer deletePictureById(Long id);

    //更新
    //根据图片id更新图片信息
    Integer updatePictureById(TSubjectPicture tSubjectPicture);

    //插入
    // 根据栏目id向栏目中增加图片对象
    Integer insertPictureBySubjectId(TSubjectPicture tSubjectPicture);

    @Insert({
            "<script>",
            "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values ",
            "<foreach collection='pictures' item='picture' index='index' separator=','>",
            "(#{picture.subjectId}, #{picture.picture},#{picture.pictureTitle}, #{picture.sortNum}, " +
                    "#{picture.pictureType},#{picture.watermark},#{picture.watermarkText})",
            "</foreach>",
            "</script>"
    })
    Integer addPictures(@Param("pictures") List<TSubjectPicture> pictures);

    @Select({"select count(id) from", TABLE_NAME, "where SUBJECT_ID = #{subjectId}"})
    Integer countSubjectImgs(long subjectId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where subject_id = #{subjectId} and picture_type = 1 limit 1"})
    TSubjectPicture getMainImg(long subjectId);

    @Update({"update", TABLE_NAME,
            "set PICTURE = #{picture}, PICTURE_TITLE = #{pictureTitle}",
            "where SUBJECT_ID = #{subjectId} and PICTURE_TYPE = 1"})
    Integer updateMainImg(TSubjectPicture tSubjectPicture);

    @Delete({"delete from", TABLE_NAME, "where SUBJECT_ID = #{subjectId} and PICTURE_TYPE = 1"})
    Integer deleteMainImg(long subjectId);

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values" +
            "(#{subjectId}, #{picture}, #{pictureTitle}, #{sortNum}, " +
            "#{pictureType}, #{watermark}, #{watermarkText})"})
    Integer addImg(TSubjectPicture tSubjectPicture);
}
