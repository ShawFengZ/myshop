package com.zxf.my.shop.web.admin.web.interceptor;

import com.zxf.my.shop.commons.constant.ConstantUtils;
import com.zxf.my.shop.domains.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zxf
 * @date 2018/10/26 15:01
 */
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User user = (User) httpServletRequest.getSession().getAttribute(ConstantUtils.SESSION_USER);

        //未登录
        if (user == null) {
            httpServletResponse.sendRedirect("/login");
            return false;
        }

        //放行
        return true;
    }

    /**
     * 请求完成之后要做的事情，已经登录后再访问login，踢回首页
     * */
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
