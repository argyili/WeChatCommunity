package com.hfut.community.demo.dao;

import com.hfut.community.demo.domain.Post;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class AdminDaoTest {

    @Autowired
    private AdminDao adminDao;

    @Ignore
    public void upgradeRefinement() {
    }

    @Ignore
    public void isPost() {
    }

    @Ignore
    public void forbidCommentPost() {
    }

    @Test
    public void searchPost() {
        String title = "%title%";
        List<Post> posts= adminDao.searchPost(title);
        for (int index = 0; index < posts.size(); ++index) {
            Assert.assertNotEquals(-1, posts.get(index).getTitle().indexOf("title"));
        }
    }

    @Ignore
    public void searchAllPost() {
    }
}