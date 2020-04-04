package com.it.jedis.service.impl;

import com.it.jedis.config.JedisUtils;
import com.it.jedis.po.User;
import com.it.jedis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

//    @Autowired
//    private JedisPool jedisPool;

    @Autowired
    private JedisUtils jedisUtils;

//    @Override
//    public String getString(String key) {
//        String value = null;
//        Jedis jedis = jedisPool.getResource();
//        if(jedis.exists(key)){
//            log.info("查询redis中的数据");
//            value = jedis.get(key);
//        }else {
//            value = "hello";
//            log.info("查询的是mysql数据库:" + value);
//            jedis.set(key, value);
//        }
//        jedis.close();
//        return value;
//    }

    @Override
    public String getString(String key) {
        String value = null;
        Jedis jedis = jedisUtils.getJedis();
        if(jedis.exists(key)){
            log.info("查询redis中的数据");
            value = jedis.get(key);
        }else {
            value = "hello";
            log.info("查询的是mysql数据库:" + value);
            jedis.set(key, value);
        }
        jedisUtils.close(jedis);
        return value;
    }

    @Override
    public User selectById(String id) {
        String key = "user:"+id; //实体类名:id
        Jedis jedis = jedisUtils.getJedis();
        User user = new User();
        if(jedis.exists(key)){
            log.info("---------->查询的是redis数据");
            Map<String, String> map = jedis.hgetAll(key);
            user.setId(map.get("id"));
            user.setName(map.get("name"));
            user.setAge(Integer.parseInt(map.get("age")));
        }else {
            user.setId(id);
            user.setName("张三");
            user.setAge(20);
            log.info("---------->查询的是mysql数据库" + user);
            Map<String,String> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("name", user.getName());
            map.put("age", user.getAge().toString());
            jedis.hmset(key, map);
            log.info("---------->存入redis中");
        }
        jedis.close();
        return user;
    }
}
