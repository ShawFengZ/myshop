package com.zxf.my.shop.web.admin.service.impl;

import com.zxf.my.shop.commons.dto.BaseResult;
import com.zxf.my.shop.commons.dto.PageInfo;
import com.zxf.my.shop.commons.validator.BeanValidator;
import com.zxf.my.shop.domains.User;
import com.zxf.my.shop.web.admin.dao.TbUserDao;
import com.zxf.my.shop.web.admin.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxf
 * @date 2018/10/29 8:34
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    TbUserDao tbUserDao;
    /**
     * 查询全部用户信息
     *
     * @return
     */
    @Override
    public List<User> selectAll() {
        return tbUserDao.selectAll();
    }

    /**
     * 用户登录方法
     *
     * @param email
     * @param password
     */
    @Override
    public User login(String email, String password) {
        User tbUser = tbUserDao.getByEmail(email);
        if (tbUser != null) {
            //明文密码加密
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            //判断加密后的密码和数据库中存放的密码是否匹配
            if (md5Password.endsWith(tbUser.getPassword()))
                return tbUser;
        }
        return null;
    }

    /**
     * 保存方法，新增+修改
     *
     * @param user
     */
    @Override
    public BaseResult save(User user) {
        String validator = BeanValidator.validator(user);
        if (validator != null) {
            return BaseResult.fail(validator);
        } else {
            user.setUpdated(new Date());
            int affectNum = 0;
            if (user.getId() == null) {//新增用户
                user.setCreated(new Date());
                //密码加密
                user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                affectNum = tbUserDao.insert(user);
            } else { //编辑用户
                affectNum = tbUserDao.update(user);
            }

        }

        return BaseResult.success("保存信息成功");
    }



    /**
     * 通过id获取user
     * */
    @Override
    public User getById(Long id) {
        User user = tbUserDao.getById(id);
        return user;
    }


    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteMulti(String[] ids) {
        tbUserDao.deleteMulti(ids);
    }

    /**
     * 分页查询
     * start:记录数开始的位置
     * length: 每次需要查询的条数
     *
     * @param start
     * @param length
     */
    @Override
    public PageInfo<User> page(int start, int length, int draw, User user) {
        int count = tbUserDao.count(user);

        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        params.put("tbUser", user);
        List<User> page = tbUserDao.page(params);
        pageInfo.setData(page);
        return pageInfo;
    }

    /**
     * 查询总记录数
     */
    @Override
    public int count(User tbUser) {
        return tbUserDao.count(tbUser);
    }


    //强行实现
    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
    }

    /**
     * 更新
     *
     * @param entity
     */
    @Override
    public int update(User entity) {
        return 0;
    }
}
