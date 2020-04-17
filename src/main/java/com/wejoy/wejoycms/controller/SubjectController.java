package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.dto.SubjectDto;
import com.wejoy.wejoycms.service.SubjectService;
import com.wejoy.wejoycms.service.impl.SubjectPictureServiceImpl;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "栏目管理")
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @Autowired
    private SubjectPictureServiceImpl subjectPictureService;

    @ApiOperation("查询栏目列表")
    @GetMapping("/getAllSubjectS")
    public RespEntity<List<TSubject>> getAllSubjectS() {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getAllSubjectS()));
    }

    @ApiOperation("验证栏目CODE是否重复")
    @GetMapping("/isCodeExists/{code}")
    public RespEntity<String> isCodeExists(@PathVariable("code") String code) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.isCodeExists(code)));
    }

    @ApiOperation("添加/修改/删除栏目")
    @PutMapping("/saveSubject")
    public RespEntity<String> saveSubject(@RequestBody TSubject subject) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.saveSubject(subject)));
    }

    @ApiOperation("根据栏目id查询栏目详细信息")
    @GetMapping("/getSubjectById/{id}")
    public RespEntity<TSubject> getSubjectById(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getSubjectById(id)));
    }

    @ApiOperation("添加栏目")
    @PostMapping("/addSubject")
    public RespEntity<Integer> addSubject(@RequestBody TSubject subject) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.addSubject(subject)));
    }

    @ApiOperation("添加栏目和图片")
    @PostMapping("/addSubjectAndPictures")
    public RespEntity<String> addSubjectAndPictures(@RequestBody SubjectDto subjectDTO) {
        String val1 = "add subject: " + subjectService.addSubject(subjectDTO) + "|";
        subjectDTO.getPictures().get(0).setSubjectId(subjectDTO.getId());   //todo get(0)
        String val2 = "add pictures: " + subjectPictureService.addPictures(subjectDTO.getPictures());
        return CtrlUtil.exe(r -> r.setVal(val1 + val2));
    }

    @ApiOperation("修改栏目")
    @PostMapping("/updateSubject")
    public RespEntity<Integer> updateSubject(@RequestBody TSubject subject) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.updateSubject(subject)));
    }

    @ApiOperation("修改栏目和主图")
    @PostMapping("/updateSubjectAndMainImg")
    public RespEntity<String> updateSubjectAndMainImg(@RequestBody SubjectDto subjectDto) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.updateSubjectAndMainImg(subjectDto)));
    }

    @ApiOperation("删除栏目")
    @PostMapping("/deleteSubject/{id}")
    public RespEntity<Boolean> deletSubject(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.deleteSubject(id)));
    }

    @ApiOperation("查询栏目列表和栏目图片")
    @GetMapping("/getSubjectSAndPictures")
    public RespEntity<List<SubjectDto>> getSubjectSAndPictures() {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getSubjectSAndPictures()));
    }

    @ApiOperation("查询一级栏目列表")
    @GetMapping("/getParentSubjectS")
    public RespEntity<List<TSubject>> getParentSubjectS() {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getParentSubjectS()));
    }

    @ApiOperation("分页查询子栏目列表")
    @GetMapping("/getSubSubjectSPage/{id}")
    public RespEntity<Map<String, Object>> getSubSubjectSPage(@PathVariable Long id, @RequestParam("page") int page,
                                                              @RequestParam("limit") int offset) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getSubSubjectS(id, page, offset)));
    }

    @ApiOperation("查询子栏目列表")
    @GetMapping("/getSubSubjectS/{id}")
    public RespEntity<List<SubjectDto>> getSubSubjectS(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(subjectService.getSubSubjectS(id)));
    }

}
