package com.zxf.my.shop.web.admin.service;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;
import com.zxf.my.shop.commons.persistence.BaseService;
import com.zxf.my.shop.domains.User;

import java.util.List;

/**
 * @author zxf
 * @date 2018/10/29 8:34
 */
public interface TbUserService extends BaseService<User> {

    /**
     * 用户登录方法
     * */
    User login(String email, String password);

}
