package com.zxf.my.shop.web.admin.web.controller;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.domains.TbContentCategory;
import com.zxf.my.shop.web.admin.service.TbContentCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.ws.RequestWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * 内容分类管理
 * @author zxf
 * @date 2018/11/7 16:10
 */
@Controller
@RequestMapping(value = "/content/category")
public class ContentCategoryController {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    @ModelAttribute
    public TbContentCategory getTbContentCategoryById(Long id){
        TbContentCategory tbContentCategory = null;
        //id不为空
        if (id != null) {
            tbContentCategory = tbContentCategoryService.getById(id);
        } else {
            tbContentCategory = new TbContentCategory();
        }
        return tbContentCategory;
    }



    /*@RequestMapping(value = "/list")
    public String list(){//Model model,  method = RequestMethod.GET
        *//*List<TbContentCategory> targetList = new ArrayList<>();
        List<TbContentCategory> sourceList = tbContentCategoryService.selectAll();

        //排序
        sortList(sourceList, targetList, 0L);

        model.addAttribute("tbContentCategories", targetList);*//*
        return "test";
        //return "content_category_list";
    }*/


    /**
     * 跳转内容管理
     * */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String goTest(Model model){
        List<TbContentCategory> targetList = new ArrayList<>();
        List<TbContentCategory> sourceList = tbContentCategoryService.selectAll();

        //排序
        sortList(sourceList, targetList, 0L);

        model.addAttribute("tbContentCategories", targetList);
        return "content_category_list";
    }

    /**
     * 跳转表单页
     * */
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(TbContentCategory tbContentCategory){
        return "content_category_form";
    }

    @ResponseBody
    @RequestMapping(value = "/tree/data", method = RequestMethod.POST)
    public List<TbContentCategory> treeData(Long id){
        if (id==null)
            id=0L;
        return tbContentCategoryService.selectByPid(id);
    }


    /**
     * 跳转新增页面
     * */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, Long id){
        //如果id不为空的话
        TbContentCategory tbContentCategory = new TbContentCategory();
        model.addAttribute("tbContentCategory",tbContentCategory);
        return "content_category_form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TbContentCategory tbContentCategory, Model model, RedirectAttributes redirectAttributes){
        BaseResult baseResult = tbContentCategoryService.save(tbContentCategory);

        if (baseResult.getStatus() == BaseResult.STATUS_SUCCESS) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            return "redirect:/content/category/test";
        } else {
            //保存失败
            model.addAttribute("baseResult", baseResult);
            return "content_category_form";
        }
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        BaseResult baseResult = null;
        if (StringUtils.isNotBlank(ids)) {
            tbContentCategoryService.delete(Long.parseLong(ids));
            baseResult = BaseResult.success("删除分类及其子类及全部内容成功");
        } else {
            baseResult = BaseResult.fail("删除分类失败");
        }
        return baseResult;
    }


    /**
     * 排序
     * @param sorceList 数据源集合
     * @param targetList 排序后的集合
     * @param parentId 父节点的Id
     * */
    private void sortList(List<TbContentCategory> sorceList, List<TbContentCategory> targetList, Long parentId){
        for (TbContentCategory tbContentCategory: sorceList) {
            if (tbContentCategory.getParent().getId().equals(parentId)) {
                targetList.add(tbContentCategory);

                //判断有没有子节点，如果有则继续添加
                if (tbContentCategory.getIsParent()) {
                    for (TbContentCategory contentCategory: sorceList) {
                        /*
                         * 包装类 引用类型 equals比较
                         * 基本数据类型 值类型 在-128~127之间可以用==比较
                         * 原因：系统启动时内存中开辟了有个缓存127以内的数字
                         * */
                        if (contentCategory.getParent().getId().equals(tbContentCategory.getId())) {
                            sortList(sorceList,targetList, tbContentCategory.getId());
                            break;
                        }
                    }
                }
            }
        }
    }

}
