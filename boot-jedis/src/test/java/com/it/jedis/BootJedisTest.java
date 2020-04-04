package com.it.jedis;

import com.it.jedis.po.User;
import com.it.jedis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
public class BootJedisTest {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        System.out.println(jedisPool);
        Jedis jedis = jedisPool.getResource();  //在连接池中得到Jedis连接
        jedis.set("haha","你好");
        jedis.close();  //关闭当前连接
    }

    /**
     * 模拟jedis操作redis String类型
     */
    @Test
    public void test1(){
        String result = userService.getString("hello");
        System.out.println(result);
    }

    /**
     * 模拟jedis操作redis Hash类型
     */
    @Test
    public void test2(){
        User user = userService.selectById("001");
        System.out.println(user);
    }
}
