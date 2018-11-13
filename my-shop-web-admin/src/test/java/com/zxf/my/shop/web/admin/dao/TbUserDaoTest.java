package com.zxf.my.shop.web.admin.dao;

import com.zxf.my.shop.domains.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TbUserDaoTest {

    @Autowired
    private TbUserDao tbUserDao;
    @Test
    public void getByEmail() {
    }

    @Test
    public void insert() {
        User user = new User();
        user.setEmail("9789@qq.vom");
        user.setUsername("zxf1");
        user.setCreated(new Date());
        user.setPassword("12344565");
        user.setUpdated(new Date());
        int insert = tbUserDao.insert(user);
        Assert.assertEquals(1, insert);
    }

    @Test
    public void update() {
    }

    @Test
    public void count() {
        User user = new User();
        user.setUsername("zxf");
        int count = tbUserDao.count(user);
        System.out.println(count);
    }
}
