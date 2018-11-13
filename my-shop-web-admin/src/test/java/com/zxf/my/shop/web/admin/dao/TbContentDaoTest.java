package com.zxf.my.shop.web.admin.dao;

import com.zxf.my.shop.domains.TbContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TbContentDaoTest {

    @Autowired
    TbContentDao tbContentDao;
    @Test
    public void insert() {
    }

    @Test
    public void getById() {
        TbContent tbContent = tbContentDao.getById(28L);
        System.out.println(tbContent);
    }
}
