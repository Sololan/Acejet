package com.wejoy.wejoycms.service;

import com.wejoy.wejoycms.entity.TFriendlyLink;
import com.wejoy.wejoycms.entity.TSubjectLink;
import com.wejoyclass.core.page.Page;

import java.util.List;
import java.util.Map;

public interface FriendlyLinkService {
    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/25 13:37
     * @description：查询所有链接
     * @param
     * @return
     */
    Page<TSubjectLink> getAllLinkS(Map map);

    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/25 15:40
     * @description：保存/更新接口
     * @param
     * @return
     */
    void saveLink(TFriendlyLink friendlyLink);

    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/26 11:54
     * @description：根据id删除链接
     * @param
     * @return
     */
    void deleteLinkById(Long id);

    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/26 12:39
     * @description：根据id获取友情链接信息
     * @param
     * @return
     */
    TFriendlyLink getLinkById(Long id);

    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/27 16:46
     * @description：根据分类id获取所有链接
     * @param
     * @return
     */
    List<TFriendlyLink> getLinkByCode(String code);
}
