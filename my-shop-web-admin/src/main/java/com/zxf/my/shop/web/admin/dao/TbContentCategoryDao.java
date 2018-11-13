package com.zxf.my.shop.web.admin.dao;

import com.zxf.my.shop.commons.persistence.BaseDao;
import com.zxf.my.shop.domains.TbContentCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zxf
 * @date 2018/11/7 16:09
 */
@Repository
public interface TbContentCategoryDao extends BaseDao<TbContentCategory> {

    /**
     * 根据父节点的Id查询所有子节点
     * */
    List<TbContentCategory> selectByParentId(Long pid);


    TbContentCategory getTbContentCategoryById(Long id);

    /**
     * 批量删除
     * */
    void delete(String[] ids);

    //根据分类id删除
    void deleteByCategoryId(Long id);
}
