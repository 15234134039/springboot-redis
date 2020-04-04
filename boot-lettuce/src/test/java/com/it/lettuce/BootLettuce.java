package com.it.lettuce;

import com.it.lettuce.po.User;
import com.it.lettuce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BootLettuce {

    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        String result = userService.getString("redisStr");
        System.out.println(result);
    }

    @Test
    public void test2(){
        userService.expireStr("test", "测试数据有效期");
        System.out.println("操作成功");
    }

    @Test
    public void test3(){
        User user = userService.selectById("003");
        System.out.println(user);
    }

}
