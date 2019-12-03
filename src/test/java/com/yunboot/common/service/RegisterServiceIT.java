/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunboot.common.service;

import com.yunboot.common.BaseTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-8 下午9:43
 * <p>Version: 1.0
 */

public class RegisterServiceIT extends BaseTests
{
    @Autowired
    private RegisterService registerService;

    @Test
    public void testRegister()
    {
        registerService.register("long", "123");
    }
}
