package com.yunboot.common.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.yunboot.common.BaseTests;

/**
 * [简要描述]:controller层单元测试
 * [详细描述]:<br/>
 *
 * @author yqzhang
 * @version 1.0, 2018年11月14日
 * @since JDK1.8
 */
public class CommonController extends BaseTests
{

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void sayHi() throws Exception
    {
        RequestBuilder request = MockMvcRequestBuilders.get("/common/test/say").param("name", "joy");
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int code = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确", code == 200);
        System.out.println("返回结果:" + content);
    }

}
