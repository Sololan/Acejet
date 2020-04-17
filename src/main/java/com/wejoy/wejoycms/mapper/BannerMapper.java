package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TBanner;
import com.wejoyclass.service.mapper.CURDMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper extends CURDMapper<TBanner, Long> {
    List<TBanner> getAllOpenBannerS();
    List<TBanner> getAllBannerS();
    void update(TBanner banner);
    TBanner getBannerById(Long id);
    Integer taggleBanner(Integer id, Integer status);
}
