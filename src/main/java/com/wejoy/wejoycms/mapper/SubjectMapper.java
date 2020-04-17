package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.dto.SubjectDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubjectMapper {

    String TABLE_NAME = "t_subject";
    String INSERT_FILEDS = "SUBJECT_NAME, SUBJECT_SUB_NAME, SUBJECT_CODE, STATUS, TEMPLATE_ID, PID, SUBJECT_BRIEF, " +
            "SHOW_TYPE, IS_EDIT, IS_DELETE, IS_APPEND_CHILD, SORT_NUM, CREATE_TIME, CREATE_USER_NAME, CREATE_USER";
    String SELECT_FIELDS = "id, " + INSERT_FILEDS;

    //获得子栏目列表和本栏目信息
    List<TSubject> getSonSubjectSByCode(String code);

    TSubject getSubjectMsgByCode(String code);

    //获得子栏目列表和本栏目信息
    List<TSubject> getSonSubjectSById(Long id);

    TSubject getSubjectMsgById(Long id);

    //获得栏目列表
    List<TSubject> getAllSubjectS();

    String isCodeExists(String code);

    Integer insertSubject(TSubject subject);

    Integer updateSubject(TSubject subject);

    //根据id查询栏目详细信息
    TSubject getSubjectById(Long id);

    //根据code获取id
    Long getIdByCode(String code);

    @Update({"update ", TABLE_NAME, " set DELETE_FLAG = #{deleteFlag} where id = #{id}"})
    int updateDeleteFlag(@Param("id") Long id, @Param("deleteFlag") int deleteFlag);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where DELETE_FLAG = 0"})
    List<SubjectDto> getSubjectS();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where DELETE_FLAG = 0 and pid = -1"})
    List<TSubject> getParentSubjectS();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where DELETE_FLAG = 0 and pid = #{id} " +
            "order by SORT_NUM limit #{limit}, #{offset}"})
    List<SubjectDto> getSubSubjectSByIdPage(long id, int limit, int offset);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where DELETE_FLAG = 0 and pid = #{id} " +
            "order by SORT_NUM"})
    List<SubjectDto> getSubSubjectSById(Long id);

    @Select({"select count(id) from ", TABLE_NAME, " where DELETE_FLAG = 0 and pid = #{id} "})
    Integer countSubSubjectSById(Long id);

    //获取所有一级栏目
    List<TSubject> getAllParentSubjectS();
    //获取指定栏目信息

    List<TSubject> getSomeSubject(List<Long> idList);

    TSubject getArticleAndFile(String code);
}
