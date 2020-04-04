package com.it.lettuce.service.impl;

import com.it.lettuce.po.User;
import com.it.lettuce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 普通缓存放入
     * @param key 键
     * @return
     */
    @Override
    public String getString(String key) {
        if(redisTemplate.hasKey(key)) {
            log.info("Redis中查询");
            return (String) redisTemplate.opsForValue().get(key);
        }else{
            log.info("mysql中查询");
            String val="hello";
            log.info("存入redis");
            redisTemplate.opsForValue().set(key, val);
            return val;
        }
    }

    /**
     * 设置存活时间
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    @Override
    public void expireStr(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 2, TimeUnit.HOURS);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public User selectById(String id) {
        if(redisTemplate.opsForHash().hasKey("user", id)){
            log.info("------------>查询redis数据库");
            return (User) redisTemplate.opsForHash().get("user", id);
        }else {
            log.info("------------>查询mysql数据库");
            User user = new User();
            user.setId(id);
            user.setName("王五");
            user.setAge(18);
            redisTemplate.opsForHash().put("user", id, user);
            return user;
        }

    }
}
