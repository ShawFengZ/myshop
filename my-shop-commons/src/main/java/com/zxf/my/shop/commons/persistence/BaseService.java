package com.zxf.my.shop.commons.persistence;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;

import java.util.List;

/**
 * 所有service层的基类
 * @author zxf
 * @date 2018/11/9 14:50
 */
public interface BaseService<T extends BaseEntity> {
    /**
     * 查询全部用户信息
     * @return
     */
    List<T> selectAll();


    /**
     * 保存方法，新增+修改
     * */
    BaseResult save(T entity);

    /**
     * 通过id获取信息
     * */
    T getById(Long id);


    /**
     * 批量删除
     * */
    void deleteMulti(String[] ids);

    /**
     * 分页查询
     * start:记录数开始的位置
     * length: 每次需要查询的条数
     * */
    PageInfo<T> page(int start, int length, int draw, T entity);

    /**
     * 查询总记录数
     * */
    int count(T entity);

    /**
     * 删除
     * */
    void delete(Long id);

    /**
     * 更新
     * */
    int update(T entity);

}
