package com.zxf.my.shop.web.admin.service.impl;

import com.zxf.my.shop.domains.TbContent;
import com.zxf.my.shop.domains.User;
import com.zxf.my.shop.web.admin.dao.TbUserDao;
import com.zxf.my.shop.web.admin.service.TbUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TbUserServiceImplTest {

    @Autowired
    private TbUserService tbUserService;
    @Test
    public void selectAll() {
    }

    //测试md5
    @Test
    public void testMd5(){
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

    //测试通过id获取User
    @Test
    public void getById() {
        User byId = tbUserService.getById(1L);
        System.out.println(byId);
    }

    //测试插入
    @Test
    public void save() {

    }
}
