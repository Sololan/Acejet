package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TSubjectPicture;
import com.wejoy.wejoycms.service.SubjectPictureService;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "图片管理")
@RequestMapping("/subjectPicture")
public class SubjectPictureController {

    @Autowired
    SubjectPictureService subjectPictureService;

    @ApiOperation("根据图片id查询图片信息")
    @GetMapping("/getPictureById/{id}")
    public RespEntity<TSubjectPicture> getPictureById(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.getPictureById(id)));
    }

    @ApiOperation("根据栏目id查询栏目下所有图片信息")
    @GetMapping("/getPictureSBySubjectId/{subjectId}")
    public RespEntity<List<TSubjectPicture>> getPictureSBySubjectId(@PathVariable Long subjectId) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.getPictureSBySubjectId(subjectId)));
    }

    @ApiOperation("根据图片类型查询图片信息")
    @GetMapping("/getPictureSByPictureType/{pictureType}")
    public RespEntity<List<TSubjectPicture>> getPictureSByPictureType(@PathVariable Integer pictureType) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.getPictureSByPictureType(pictureType)));
    }

    @ApiOperation("根据水印类型WaterMark查询图片信息")
    @GetMapping("/getPictureSByWatermark/{watermark}")
    public RespEntity<List<TSubjectPicture>> getPictureSByWatermark(@PathVariable Integer watermark) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.getPictureSByWatermark(watermark)));
    }

    @ApiOperation("图片升序排序")
    @GetMapping("/getPictureSSortASC")
    public RespEntity<List<TSubjectPicture>> getPictureSSortASC() {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.getPictureSSortASC()));
    }

    @ApiOperation("图片降序排序")
    @GetMapping("/getPictureSSortDESC")
    public RespEntity<List<TSubjectPicture>> getPictureSSortDESC() {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.getPictureSSortDESC()));
    }

    @ApiOperation("根据图片id删除图片")
    @PostMapping("/deletePictureById/{id}")
    public RespEntity<String> deletePictureById(@PathVariable Long id) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.deletePictureById(id)));
    }

    @ApiOperation("根据图片id更新图片")
    @PutMapping("/updatePictureById")
    public RespEntity<String> updatePictureById(@RequestBody TSubjectPicture tSubjectPicture) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.updatePictureById(tSubjectPicture)));
    }

    @ApiOperation("向栏目中增加图片信息")
    @PostMapping("/insertPictureBySubjectId")
    public RespEntity<String> insertPictureBySubjectId(@RequestBody TSubjectPicture tSubjectPicture) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.insertPictureBySubjectId(tSubjectPicture)));
    }

    @ApiOperation("向栏目中增加多张图片")
    @PostMapping("/addImgsBySubjectId")
    public RespEntity<String> insertPictureBySubjectId(@RequestBody List<TSubjectPicture> imgs) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.addPictures(imgs)));
    }

    @ApiOperation("获取栏目主图")
    @GetMapping("/getMainImg/{subjectId}")
    public RespEntity<TSubjectPicture> getMainImg(@PathVariable("subjectId") long subjectId) {
        return CtrlUtil.exe(r -> r.setVal(subjectPictureService.getMainImg(subjectId)));
    }
}
