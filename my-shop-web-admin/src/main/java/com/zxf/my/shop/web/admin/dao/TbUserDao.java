package com.zxf.my.shop.web.admin.dao;

import com.zxf.my.shop.commons.persistence.BaseDao;
import com.zxf.my.shop.domains.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zxf
 * @date 2018/10/29 8:32
 */
public interface TbUserDao extends BaseDao<User> {

    User getByEmail(String email);

}
