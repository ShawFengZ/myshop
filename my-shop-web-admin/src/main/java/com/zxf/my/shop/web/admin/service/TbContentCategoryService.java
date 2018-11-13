package com.zxf.my.shop.web.admin.service;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.persistence.BaseService;
import com.zxf.my.shop.domains.TbContentCategory;

import java.util.List;

/**
 * @author zxf
 * @date 2018/11/7 16:09
 */
public interface TbContentCategoryService {
    List<TbContentCategory> selectAll();

    /**
     * 根据父节点的Id查询所有子节点
     * */
    List<TbContentCategory> selectByPid(Long pid);

    /**
     * 根据id获取
     * */
    TbContentCategory getById(Long id);

    /**
     * 保存
     * */
    BaseResult save(TbContentCategory tbContentCategory);

    void delete(Long id);
}
