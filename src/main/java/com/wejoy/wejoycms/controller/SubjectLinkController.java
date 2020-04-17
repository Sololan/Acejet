package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TFirendlyLinkType;
import com.wejoy.wejoycms.entity.TFriendlyLink;
import com.wejoy.wejoycms.entity.TSubjectLink;
import com.wejoy.wejoycms.service.FriendlyLinkService;
import com.wejoy.wejoycms.service.FriendlyLinkTypeService;
import com.wejoy.wejoycms.service.SubjectLinkService;
import com.wejoy.wejoycms.util.EntityUtil;
import com.wejoyclass.core.page.Page;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "栏目链接管理")
@RequestMapping("/subjectLink")
public class SubjectLinkController {

    @Autowired
    SubjectLinkService subjectLinkService;

    @Autowired
    FriendlyLinkService friendlyLinkService;

    @Autowired
    FriendlyLinkTypeService friendlyLinkTypeService;

    @ApiOperation("根据链接id查询链接信息")
    @GetMapping("/getLinkById/{id}")
    public RespEntity<TSubjectLink> getLinkById(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(subjectLinkService.getLinkById(id)));
    }

    @ApiOperation("查找栏目下所有链接信息")
    @GetMapping("/getLinksBySubjectId/{subjectId}")
    public RespEntity<List<TSubjectLink>> getLinksBySubjectId(@PathVariable Long subjectId) {
        return CtrlUtil.exe(r -> r.setVal(subjectLinkService.getLinksBySubjectId(subjectId)));
    }

    @ApiOperation("根据链接id更新链接信息")
    @PutMapping("/updateLinkById")
    public RespEntity<String> updateLinkById(@RequestBody TSubjectLink tSubjectLink) {
        return CtrlUtil.exe(r -> r.setVal(subjectLinkService.updateLinkById(tSubjectLink)));
    }

    @ApiOperation("根据栏目id插入链接信息")
    @PostMapping("/insertLinkBySubjectId")
    public RespEntity<String> insertLinkBySubjectId(@RequestBody TSubjectLink tSubjectLink) {
        return CtrlUtil.exe(r -> r.setVal(subjectLinkService.insertLinkBySubjectId(tSubjectLink)));
    }

    @ApiOperation("根据sortNum升序排序并显示")
    @GetMapping("/getLinkSSortASC")
    public RespEntity<List<TSubjectLink>> getLinkSSortASC() {
        return CtrlUtil.exe(r -> r.setVal(subjectLinkService.getLinkSSortASC()));
    }

    @ApiOperation("根据sortNum降序排序并显示")
    @GetMapping("/getLinkSSortDESC")
    public RespEntity<List<TSubjectLink>> getLinkSSortDESC() {
        return CtrlUtil.exe(r -> r.setVal(subjectLinkService.getLinkSSortDESC()));
    }

    @ApiOperation("查询所有链接信息")
    @GetMapping("/getAllLinkS")
    public RespEntity<Page<TSubjectLink>> getAllLinkS(@RequestParam Integer page, @RequestParam Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        return CtrlUtil.exe(r -> r.setVal(friendlyLinkService.getAllLinkS(map)));
    }

    @ApiOperation("添加/更新栏目链接")
    @PostMapping("/saveLink")
    public RespEntity<String> saveLink(@RequestBody TSubjectLink tSubjectLink) {

        if (tSubjectLink.getId() == null) {
            subjectLinkService.insertLinkBySubjectId(tSubjectLink);
        } else {
            subjectLinkService.updateLinkById(tSubjectLink);
        }

        return CtrlUtil.exe(r -> r.setVal("成功"));
    }

    @ApiOperation("获取所有链接分类")
    @GetMapping("/getAllLinkType")
    public RespEntity<List<TFirendlyLinkType>> getAllLinkType() {
        return CtrlUtil.exe(r -> r.setVal(friendlyLinkTypeService.getAllLinkType()));
    }

    @ApiOperation("根据id删除链接")
    @DeleteMapping("/deleteLinkById/{id}")
    public RespEntity<String> deleteLinkById(@PathVariable("id") Long id) {
        subjectLinkService.deleteLinkById(id);
        return CtrlUtil.exe(r -> r.setVal("成功"));
    }
}
