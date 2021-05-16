package com.hfut.community.demo.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class AdminServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AdminService adminService;

    @Before
    public void add(){
    }

    @Ignore
    public void upgradeRefinement() {
    }

    @Ignore
    public void post() {
    }

    @Ignore
    public void forbidComment() {
        adminService.forbidComment(1);
    }

    @Test
    public void searchPostTitle() {
        Assert.assertEquals(adminService.searchPost("title").get(0).getTitle(),"title");
    }

    @Test
    public void searchPostNull() {
        Assert.assertEquals(adminService.searchPost(null).size(),0);
    }

    @Ignore
    public void searchAllPost() {
    }
}