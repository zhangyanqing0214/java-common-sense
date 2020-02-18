package com.yunboot.common.controller;

import com.yunboot.common.limit.annotation.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yunboot.common.annotation.Log;
import com.yunboot.common.enums.CrmServiceEnum;
import com.yunboot.common.exception.CommonException;
import com.yunboot.common.exception.ResponseJson;
import com.yunboot.common.service.RedisService;

@RestController
@RequestMapping("/common/test")
public class HomeController
{
    @Autowired
    private RedisService redisService;

    @AccessLimit(times = 4)
    @GetMapping("/say")
    @Log(value = "测试接口")
    public ResponseJson sayHi(@RequestParam("name") String name)
    {
        if (name.equals("123"))
        {
            throw new CommonException(CrmServiceEnum.ID_IS_NULL);
        }
        else if (name.equals("456"))
        {
            throw new IndexOutOfBoundsException();
        }
        return ResponseJson.success(name);
    }
}
