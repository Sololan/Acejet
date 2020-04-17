package com.wejoy.wejoycms.service;

import java.util.List;
import java.util.Map;

public interface UniquePageService {
    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/28 15:38
     * @description：获取特殊的栏目，返回栏目信息，栏目链接，栏目图片，栏目下属文章
     * @param
     * @return
     */
    Map<String, Object> getSomeSubject(List<Long> idList);
}
