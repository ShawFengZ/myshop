package com.zxf.my.shop.web.admin.service;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;
import com.zxf.my.shop.commons.persistence.BaseService;
import com.zxf.my.shop.domains.TbContent;
import com.zxf.my.shop.domains.User;

import java.util.List;
import java.util.Map;

/**
 * @author zxf
 * @date 2018/11/8 9:34
 */
public interface TbContentService extends BaseService<TbContent> {
    /**
     * 查询全部用户信息
     * */
    //public List<TbContent> selectAll();


    /**
     * 新增
     * */
    //BaseResult save(TbContent tbContent);

    /**
     * 删除
     */
    //void delete(Long id);

    /**
     * 更新
     * */
    //int update(TbContent tbContent);

    /**
     * 通过id获取数据
     * */
    //TbContent getById(Long id);


    /**
     * 分页查询
     * start:记录数开始的位置
     * length: 每次需要查询的条数
     * */
    //PageInfo<TbContent> page(int start, int length, int draw, TbContent tbContent);

    /**
     * 查询总记录数
     * */
    //int count(TbContent tbContent);

    /**
     * 批量删除
     * */
    //public void deleteMulti(String[] ids);

}
