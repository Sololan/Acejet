package com.wejoy.wejoycms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TemplateMapper {
    String getPathById(Long id);
    //根据模板code获取模板路径
    String getPathByCode(String code);
    //根据文章id获取模板路径
    String getPathByArticleId(Long id);

    @Select({"select ID from t_template where TEMPLATE_CODE = #{code}"})
    Long getTemplateIdByCode(String code);
}
