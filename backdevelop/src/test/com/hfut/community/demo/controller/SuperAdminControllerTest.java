package com.hfut.community.demo.controller;

import com.hfut.community.demo.domain.Advertisement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.testng.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class SuperAdminControllerTest {

    @Autowired
    private SuperAdminController superAdminController;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSearchAdvertisementSuccess() throws Exception {
        Advertisement advertisement = new Advertisement();
        advertisement.setAid("1");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/superadmin/advertisementctrl")
                .param("viewId", advertisement.getAid()))
                .andExpect(model().attributeExists("aid", "img", "advContent"))
                .andExpect(view().name("admin/superAdmin/viewAdvertisement"))
                .andReturn();
        Integer aid = Integer.valueOf((String)result.getModelAndView().getModel().get("aid"));
        Assert.assertEquals(aid, Integer.valueOf(advertisement.getAid()));
    }

    @Test
    public void testSearchAdvertisementFailed() throws Exception {
        Advertisement advertisement = new Advertisement();
        advertisement.setAid("1000");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/superadmin/advertisementctrl")
                .param("viewId", advertisement.getAid()))
                .andExpect(model().attributeExists("Result"))
                .andExpect(view().name("./admin/superAdmin/advertisementManage"))
                .andReturn();
        String msg = (String)result.getModelAndView().getModel().get("Result");
        Assert.assertNotEquals(-1, msg.indexOf(advertisement.getAid()));
    }

    @Ignore
    public void testAdvModification() {
    }

    @Ignore
    public void testAdvAdd() {
    }

    @Ignore
    public void testTest1() {
    }

    @Ignore
    public void testUploadFile() {
    }
}