package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TFirendlyLinkType;

import java.util.List;

public interface FriendlyLinkTypeService {
    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/26 11:08
     * @description：获取所有链接分类
     * @param
     * @return
     */
    List<TFirendlyLinkType> getAllLinkType();
}
