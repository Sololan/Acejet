package com.wejoy.wejoycms.mapper;

import com.wejoy.wejoycms.entity.TFriendlyLink;
import com.wejoy.wejoycms.entity.TSubjectLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendlyLinkMapper {
    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/25 13:42
     * @description：查询所有链接信息
     * @param
     * @return
     */
    List<TSubjectLink> getAllLinkS();

    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/26 9:45
     * @description：插入友情链接
     * @param
     * @return
     */
    Integer insertFriendlyLink(TFriendlyLink friendlyLink);

    /**
     * @author     ：liuzt
     * @date       ：Created in 2019/12/26 9:45
     * @description：修改友情链接
     * @param
     * @return
     */
    Integer updateFriendlyLinkById(TFriendlyLink friendlyLink);

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
