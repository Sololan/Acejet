package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TFirendlyLinkType;
import com.wejoy.wejoycms.entity.TSubjectLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendlyLinkTypeMapper {
    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/26 11:08
     * @description：获取所有链接分类
     * @param
     * @return
     */
    List<TFirendlyLinkType> getAllLinkType();
}
