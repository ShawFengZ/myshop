package com.zxf.my.shop.web.admin.service.impl;

import com.zxf.my.shop.domains.TbContent;
import com.zxf.my.shop.web.admin.service.TbContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TbContentServiceImplTest {

    @Autowired
    TbContentService tbContentService;
    @Test
    public void save() {

    }

    @Test
    public void update() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void page() {
    }

    @Test
    public void count() {
    }

    @Test
    public void deleteMulti() {
    }
}
