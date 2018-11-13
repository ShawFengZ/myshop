package com.zxf.my.shop.web.admin.service.impl;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.validator.BeanValidator;
import com.zxf.my.shop.domains.TbContentCategory;
import com.zxf.my.shop.web.admin.dao.TbContentCategoryDao;
import com.zxf.my.shop.web.admin.service.TbContentCategoryService;
import com.zxf.my.shop.web.admin.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zxf
 * @date 2018/11/7 16:09
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Autowired
    private TbContentCategoryDao tbContentCategoryDao;

    @Autowired
    private TbContentService tbContentService;

    @Override
    public List<TbContentCategory> selectAll() {
        return tbContentCategoryDao.selectAll();
    }

    /**
     * 根据父节点的Id查询所有子节点
     *
     * @param pid
     */
    @Override
    public List<TbContentCategory> selectByPid(Long pid) {
        return tbContentCategoryDao.selectByParentId(pid);
    }

    /**
     * 根据id获取
     *
     * @param id
     */
    @Override
    public TbContentCategory getById(Long id) {
        return tbContentCategoryDao.getById(id);
    }

    /**
     * 保存
     *
     * @param tbContentCategory
     */
    @Override
    public BaseResult save(TbContentCategory tbContentCategory) {
        String validator = BeanValidator.validator(tbContentCategory);
        if (validator != null) {
            return BaseResult.fail(validator);
        } else {
            TbContentCategory parent = tbContentCategory.getParent();
            if (parent == null || parent.getId() == null) {
                //如果没有选择父节点，默认设置为根目录
                parent.setId(0L);
                //根目录一定是父级目录
                //parent.setIsParent(true);
            }
            tbContentCategory.setUpdated(new Date());
            if (tbContentCategory.getId() == null) { //新增
                tbContentCategory.setCreated(new Date());
                tbContentCategory.setIsParent(false);
                //判断当前新增节点有没有父节点
                if (parent.getId() != 0L) {
                    TbContentCategory currentCategoryParent = getById(parent.getId());
                    if (currentCategoryParent != null) {
                        //为父节点设置isParent为true
                        currentCategoryParent.setIsParent(true);
                        tbContentCategoryDao.update(currentCategoryParent);
                    }
                    //父级节点为0，表示为根目录
                } else {
                    //根目录一定是父级目录
                    tbContentCategory.setIsParent(true);
                }
                tbContentCategory.setStatus(1);
                tbContentCategoryDao.insert(tbContentCategory);
            }else {
                tbContentCategoryDao.update(tbContentCategory);
            }
            return BaseResult.success("保存分类信息成功");
        }

    }

    /**
     * 删除分类
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        List<String> targetArray = new ArrayList<>();
        findAllChild(targetArray, id);

        String[] categoryIds = targetArray.toArray(new String[targetArray.size()]);
        tbContentCategoryDao.delete(categoryIds);

        //删除类目下的类容
        tbContentService.deleteMulti(categoryIds);
    }

    /**
     * 查找出所有子节点
     *
     * @param targetList
     * @param parentId
     */
    private void findAllChild(List<String> targetList, Long parentId) {
        targetList.add(String.valueOf(parentId));

        List<TbContentCategory> tbContentCategories = selectByPid(parentId);
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            findAllChild(targetList, tbContentCategory.getId());
        }
    }
}
