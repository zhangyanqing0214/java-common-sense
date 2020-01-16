/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunboot.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-8 下午9:37
 * <p>Version: 1.0
 */
@Data
public class UserDto implements Serializable
{
    private String username;
    private String password;

}
