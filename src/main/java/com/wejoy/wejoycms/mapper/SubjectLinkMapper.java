package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.TSubjectLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubjectLinkMapper {


    //查询
    //1、根据所属栏目id查看所有的链接信息
    List<TSubjectLink> getLinkSBySubjectId(Long subjectId);

    //2、根据链接id查询链接信息
    TSubjectLink getLinkById(Long id);

    //排序
    //5、将所有链接升序排序
    List<TSubjectLink> getLinkSSortASC();
    //6、将所有链接降序排序
    List<TSubjectLink> getLinkSSortDESC();

    //删除
    //3、根据链接id删除链接
    Integer deleteLinkById(Long id);

    //更新
    //4、根据链接id更新链接
    Integer updateLinkById(TSubjectLink tSubjectLink);

    //增加
    //根据栏目id插入链接
    Integer insertLinkBySubjectId(TSubjectLink tSubjectLink);


}
