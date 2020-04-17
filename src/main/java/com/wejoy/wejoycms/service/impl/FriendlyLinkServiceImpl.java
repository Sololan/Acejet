package com.wejoy.wejoycms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wejoy.wejoycms.entity.TFriendlyLink;
import com.wejoy.wejoycms.entity.TSubjectLink;
import com.wejoy.wejoycms.mapper.FriendlyLinkMapper;
import com.wejoy.wejoycms.service.FriendlyLinkService;
import com.wejoyclass.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FriendlyLinkServiceImpl implements FriendlyLinkService {

    @Autowired
    FriendlyLinkMapper friendlyLinkMapper;

    @Override
    public Page<TSubjectLink> getAllLinkS(Map map) {
        List<TSubjectLink> allLinkS = friendlyLinkMapper.getAllLinkS();
        PageHelper.startPage((Integer) map.get("page"), (Integer) map.get("limit"));
        PageInfo<TSubjectLink> page = new PageInfo(allLinkS);
        Page<TSubjectLink> result = new Page(page.getPageSize(), true);
        result.setPageNo(page.getPageNum());
        result.setTotalCount((int) page.getTotal());
        result.setTotalRows((int) page.getTotal());
        result.setResult(new ArrayList(allLinkS));
        return result;
    }

    @Override
    public void saveLink(TFriendlyLink friendlyLink) {
        if (friendlyLink.getId() == null) {
            if (friendlyLinkMapper.insertFriendlyLink(friendlyLink) == 0) {
                throw new IllegalStateException("插入数据失败，请重新插入");
            }
        } else {
            if (friendlyLinkMapper.updateFriendlyLinkById(friendlyLink) == 0) {
                throw new IllegalStateException("更新数据失败，请重新插入");
            }
        }
    }

    @Override
    public void deleteLinkById(Long id) {
        friendlyLinkMapper.deleteLinkById(id);
    }

    @Override
    public TFriendlyLink getLinkById(Long id) {
        return friendlyLinkMapper.getLinkById(id);
    }

    @Override
    public List<TFriendlyLink> getLinkByCode(String code) {
        return friendlyLinkMapper.getLinkByCode(code);
    }
}
