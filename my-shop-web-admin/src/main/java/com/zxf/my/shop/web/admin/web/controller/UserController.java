package com.zxf.my.shop.web.admin.web.controller;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;
import com.zxf.my.shop.domains.User;
import com.zxf.my.shop.web.admin.service.TbUserService;
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

/**
 * 用户管理
 * @author zxf
 * @date 2018/10/29 13:38
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TbUserService tbUserService;

    /**
     * 将返回的对象自动反倒Model中,统一的初始化功能
     * */
    @ModelAttribute
    public User getUser(Long id){
        User user = null;
        //id不为空则
        if (id != null) {
            user = tbUserService.getById(id);
        } else {
            user = new User();
        }
        return user;
    }

    /**
     * 跳转用户列表页, 同时查询所有的用户
     * */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "user_list";
    }

    /**
     * 跳转表单页
     * */
    @RequestMapping(value = "/form")
    public String form(Model model){
        return "user_form";
    }

    /**
     * 保存
     * */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(User user,Model model,  RedirectAttributes redirectAttributes){
        BaseResult baseResult = tbUserService.save(user);

        if (baseResult.getStatus() == 200) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            //这里是post方法，因此需要的是重定向
            return "redirect:/user/list";
        } else {
            //保存失败
            model.addAttribute("baseResult", baseResult);
            return "user_form";
        }
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult delete(String ids){
        BaseResult baseResult = null;
        if (!StringUtils.isBlank(ids)) {
            String[] idArray = ids.split(",");
            tbUserService.deleteMulti(idArray);
            baseResult = BaseResult.success("删除用户成功");
        } else {
            baseResult = BaseResult.fail("删除用户失败");
        }
        return baseResult;
    }

    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<User> page(HttpServletRequest request, User tbUser){

        String strDraw = request.getParameter("draw");
        String strStart = request.getParameter("start");
        String strLength = request.getParameter("length");

        int draw = strDraw==null?0:Integer.parseInt(strDraw);
        int start = strStart==null?0:Integer.parseInt(strStart);
        int length = strLength==null?10:Integer.parseInt(strLength);

        PageInfo<User> pageInfo = tbUserService.page(start, length, draw, tbUser);

        return pageInfo;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(User user){
        return "user_detail";
    }

}
