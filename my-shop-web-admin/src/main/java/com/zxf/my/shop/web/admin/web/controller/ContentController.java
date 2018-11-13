package com.zxf.my.shop.web.admin.web.controller;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;
import com.zxf.my.shop.domains.TbContent;
import com.zxf.my.shop.web.admin.service.TbContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zxf
 * @date 2018/11/8 10:13
 */
@Controller
@RequestMapping(value = "/content")
public class ContentController {
    @Autowired
    private TbContentService tbContentService;

    @ModelAttribute
    public TbContent getTbContentById(Long id){
        TbContent tbContent = null;

        //id不为空，则从数据库中查找
        if (id != null) {
            tbContent = tbContentService.getById(id);
        } else {
            tbContent = new TbContent();
        }
        return tbContent;
    }

    /**
     * 跳转内容列表页, 同时查询所有的内容
     * */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "content_list";
    }

    /**
     * 跳转表单页
     * */
    @RequestMapping(value = "/form")
    public String form(Model model){
        return "content_form";
    }

    /**
     * 保存
     * */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TbContent tbContent, Model model, RedirectAttributes redirectAttributes){
        BaseResult baseResult = tbContentService.save(tbContent);

        if (baseResult.getStatus() == BaseResult.STATUS_SUCCESS) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            //这里是post方法，因此需要的是重定向
            return "redirect:/content/list";
        } else {
            //保存失败
            model.addAttribute("baseResult", baseResult);
            return "content_form";
        }
    }

    /**
     * 删除信息
     * */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult delete(String ids){
        BaseResult baseResult = null;
        if (!StringUtils.isBlank(ids)) {
            String[] idArray = ids.split(",");
            tbContentService.deleteMulti(idArray);
            baseResult = BaseResult.success("删除用户成功");
        } else {
            baseResult = BaseResult.fail("删除用户失败");
        }
        return baseResult;
    }

    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<TbContent> page(HttpServletRequest request, TbContent tbContent){

        String strDraw = request.getParameter("draw");
        String strStart = request.getParameter("start");
        String strLength = request.getParameter("length");

        int draw = strDraw==null?0:Integer.parseInt(strDraw);
        int start = strStart==null?0:Integer.parseInt(strStart);
        int length = strLength==null?10:Integer.parseInt(strLength);

        PageInfo<TbContent> pageInfo = tbContentService.page(start, length, draw, tbContent);
        return pageInfo;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(TbContent tbContent){
        return "content_detail";
    }
}
