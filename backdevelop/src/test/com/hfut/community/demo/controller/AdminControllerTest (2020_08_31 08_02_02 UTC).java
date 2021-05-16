package com.hfut.community.demo.controller;

import com.hfut.community.demo.domain.Post;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class AdminControllerTest {

    @Autowired
    private AdminController adminController;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Ignore
    public void getUTF8() {
    }

    @Ignore
    public void refinementPost() {
    }

    @Ignore
    public void forbidCommentPost() {
    }

    @Test
    public void searchPostAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Admin/searchPost"))
                .andExpect(model().attributeExists("searchPost","searchPostResult"))
                .andExpect(view().name("./admin/searchPost"))
                .andDo(print())
                .andReturn();
        Integer postNum = Integer.valueOf((String)result.getModelAndView().getModel().get("searchPostResult"));
        Assert.assertNotEquals(postNum,new Integer(0));
    }

    @Test
    public void searchPostByTitle() throws Exception {
        Post post = new Post();
        post.setTitle("title");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Admin/searchPost").param("title", "title"))
                .andExpect(model().attributeExists("searchPost","searchPostResult"))
                .andDo(print())
                .andReturn();
        Integer postNum = Integer.valueOf((String)result.getModelAndView().getModel().get("searchPostResult"));
        List<Post> searchPost = (List<Post>)result.getModelAndView().getModel().get("searchPost");
        Assert.assertEquals(postNum,new Integer(searchPost.size()));
        for(int index = 0; index < postNum; ++index) {
            Assert.assertNotEquals(-1,searchPost.get(index).getTitle().indexOf(post.getTitle()));
        }
    }
}