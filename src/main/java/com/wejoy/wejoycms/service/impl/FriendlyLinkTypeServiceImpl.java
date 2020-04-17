package com.wejoy.wejoycms.service.impl;

import com.wejoy.wejoycms.entity.TFirendlyLinkType;
import com.wejoy.wejoycms.mapper.FriendlyLinkTypeMapper;
import com.wejoy.wejoycms.service.FriendlyLinkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendlyLinkTypeServiceImpl implements FriendlyLinkTypeService {

    @Autowired
    private FriendlyLinkTypeMapper friendlyLinkTypeMapper;

    @Override
    public List<TFirendlyLinkType> getAllLinkType() {
        return friendlyLinkTypeMapper.getAllLinkType();
    }
}
