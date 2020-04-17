package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.entity.view.VBanner;
import com.wejoyclass.core.service.service.CURDService;

import java.util.List;

public interface BannerService extends CURDService<TBanner, Long> {
    //获取所有处于开启状态的轮播
    List<TBanner> getAllOpenBannerS();

    //获取所有轮播视图
    List<VBanner> getAllBannerSView();

    //获取所有轮播
    List<TBanner> getAllBannerS();

    //增加，修改，逻辑删除轮播
    String saveBanner(TBanner banner);

    //根据id查找轮播
    TBanner getBannerById(Long id);

    //根据id查找轮播视图
    VBanner getBannerByIdView(Long id);

    //修改轮播启用状态
    String taggleBanner(Integer id, Integer status);
}
