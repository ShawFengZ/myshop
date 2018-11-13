package com.zxf.my.shop.commons.persistence;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 所有数据访问层的基类
 * @author zxf
 * @date 2018/11/9 14:45
 */
public interface BaseDao<T extends BaseEntity> {
    /**
     * 查询全部信息
     * */
    List<T> selectAll();


    /**
     * 新增
     * */
    int insert(T entity);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 更新
     * */
    int update(T entity);

    /**
     * 通过id获取数据
     * */
    T getById(Long id);


    /**
     * 分页查询
     * start:记录数开始的位置
     * length: 每次需要查询的条数
     * */
    List<T> page(Map<String, Object> params);

    /**
     * 查询总记录数
     * */
    int count(T entity);

    /**
     * 批量删除
     * */
    void deleteMulti(String[] ids);
}
