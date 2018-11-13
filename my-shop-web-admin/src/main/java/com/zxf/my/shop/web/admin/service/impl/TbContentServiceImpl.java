package com.zxf.my.shop.web.admin.service.impl;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;
import com.zxf.my.shop.commons.validator.BeanValidator;
import com.zxf.my.shop.domains.TbContent;
import com.zxf.my.shop.web.admin.dao.TbContentDao;
import com.zxf.my.shop.web.admin.service.TbContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxf
 * @date 2018/11/8 9:34
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Autowired
    private TbContentDao tbContentDao;

    /**
     * 查询全部用户信息
     */
    @Override
    public List<TbContent> selectAll() {
        return tbContentDao.selectAll();
    }

    /**
     * 新增
     *
     * @param tbContent
     */
    @Override
    public BaseResult save(TbContent tbContent) {
        String validator = BeanValidator.validator(tbContent);
        if (validator != null) {
            //没通过验证
            return BaseResult.fail();
        } else {
            tbContent.setUpdated(new Date());
            int affectNum = 0;
            if (tbContent.getId() == null) {//新增用户
                tbContent.setCreated(new Date());
                affectNum = tbContentDao.insert(tbContent);
            } else { //编辑用户
                affectNum = tbContentDao.update(tbContent);
            }
            return BaseResult.success("保存内容信息成功");
        }

    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        tbContentDao.delete(id);
    }

    /**
     * 更新
     *
     * @param tbContent
     */
    @Override
    public int update(TbContent tbContent) {
        return tbContentDao.update(tbContent);
    }

    /**
     * 通过id获取数据
     *
     * @param id
     */
    @Override
    public TbContent getById(Long id) {
        return tbContentDao.getById(id);
    }

    /**
     * 分页查询
     * start:记录数开始的位置
     * length: 每次需要查询的条数
     */
    @Override
    public PageInfo<TbContent> page(int start, int length, int draw, TbContent tbContent) {
        int count = tbContentDao.count(tbContent);

        PageInfo<TbContent> pageInfo = new PageInfo<>();
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        params.put("tbContent", tbContent);
        List<TbContent> page = tbContentDao.page(params);
        pageInfo.setData(page);
        return pageInfo;
    }

    /**
     * 查询总记录数
     *
     * @param tbContent
     */
    @Override
    public int count(TbContent tbContent) {
        return tbContentDao.count(tbContent);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteMulti(String[] ids) {
        tbContentDao.deleteMulti(ids);
    }


}
