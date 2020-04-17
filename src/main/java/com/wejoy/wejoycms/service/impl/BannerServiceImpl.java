package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.entity.view.VBanner;
import com.wejoy.wejoycms.mapper.ArticleMapper;
import com.wejoy.wejoycms.mapper.BannerMapper;
import com.wejoy.wejoycms.service.BannerService;
import com.wejoyclass.service.impl.BaseCURDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl extends BaseCURDServiceImpl<TBanner, Long> implements BannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<TBanner> getAllOpenBannerS() {
        return bannerMapper.getAllOpenBannerS();
    }

    @Override
    public List<VBanner> getAllBannerSView() {
        List<VBanner> bannerView = new ArrayList<>();
        List<TBanner> allBannerS = bannerMapper.getAllBannerS();
        for (TBanner tbanner :
                allBannerS) {
            VBanner vBanner = new VBanner();
            vBanner.setBannerTitle(tbanner.getBannerTitle());
            vBanner.setStatus(tbanner.getStatus());
            vBanner.setPicture(tbanner.getPicture());
            //先获取文章标题，如果没有获取到（文章处于未启用状态）即返回指定字符串
            //否则正常返回
            TArticle articleflag = articleMapper.getArticleById(tbanner.getArticleId());
            if (articleflag == null) {
                vBanner.setArticleTitle("暂未指定文章，或文章未启用");
            } else {
                vBanner.setArticleTitle(articleflag.getArticleTitle());
            }
            vBanner.setArticleId(tbanner.getArticleId());
            bannerView.add(vBanner);
        }
        return bannerView;
    }

    @Override
    public List<TBanner> getAllBannerS() {
        return bannerMapper.getAllBannerS();
    }

    @Override
    public String saveBanner(TBanner banner) {
        bannerMapper.update(banner);
        return "修改完成";
    }

    @Override
    public TBanner getBannerById(Long id) {
        return bannerMapper.getBannerById(id);
    }

    @Override
    public VBanner getBannerByIdView(Long id) {
        VBanner vBanner = new VBanner();
        TBanner tBanner = bannerMapper.getBannerById(id);
        vBanner.setBannerTitle(tBanner.getBannerTitle());
        vBanner.setStatus(tBanner.getStatus());
        vBanner.setPicture(tBanner.getPicture());
        vBanner.setArticleTitle(articleMapper.getArticleById(tBanner.getArticleId()).getArticleTitle());
        return vBanner;
    }

    @Override
    public String taggleBanner(Integer id, Integer status) {
        Integer flag = bannerMapper.taggleBanner(id, status);
        if (flag == 0) {
            return "变更失败";
        }
        return "变更成功";
    }

}
