package com.zxf.my.shop.web.admin.web.controller;

import com.zxf.my.shop.commons.constant.ConstantUtils;
import com.zxf.my.shop.domains.User;
import com.zxf.my.shop.web.admin.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxf
 * @date 2018/10/26 14:50
 */
@Controller
public class LoginController {

    @Autowired
    private TbUserService tbUserService;
    //跳转页面
    @RequestMapping(value = {"","/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(required = true) String email, @RequestParam(required = true)String password, HttpServletRequest httpServletRequest, Model model) {
        User user = tbUserService.login(email, password);

        //登录失败
        if (user == null) {
            model.addAttribute("message","用户名或密码输入错粗，请重新输入");
            //跳转登陆页
            return login();
        }
        //登录跳转首页
        else {
            //将登录信息放入会话中，直接setAttribute是不行的，必须放到session中
            httpServletRequest.getSession().setAttribute(ConstantUtils.SESSION_USER, user);
            //重定向
            return "redirect:/main";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().invalidate();
        return "login";
    }
}
