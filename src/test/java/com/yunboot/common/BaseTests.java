package com.yunboot.common;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTests
{

    @Test
    public void contextLoads()
    {
    }

    public static void main(String[] args)
    {
        String msg = "hello world!";
        System.out.println(DigestUtil.md5Hex(msg));
        System.out.println(DigestUtil.md5Hex(msg));
        System.out.println(DigestUtil.sha1Hex(msg));
        System.out.println(DigestUtil.sha256Hex(msg));
        String hashpwd = DigestUtil.bcrypt("123456");
        System.out.println(DigestUtil.bcryptCheck("123456", hashpwd));
    }
}
