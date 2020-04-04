package com.it.lettuce.service.impl;

import com.it.lettuce.common.IdentityType;
import com.it.lettuce.po.User;
import com.it.lettuce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;    //redisTemplate.opsForValue()

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, User> hashOperations;    //redisTemplate.opsForHash()

    /**
     * 普通缓存放入
     * @param key 键
     * @return
     */
    @Override
    public String getString(String key) {
        if(redisTemplate.hasKey(key)) {
            log.info("Redis中查询");
            //return (String) redisTemplate.opsForValue().get(key);
            return valueOperations.get(key);
        }else{
            log.info("mysql中查询");
            String val="hello";
            log.info("存入redis");
            //redisTemplate.opsForValue().set(key, val);
            valueOperations.set(key, val);
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
        //redisTemplate.opsForHash().hasKey(User.getKeyName(), id)
        if(redisTemplate.opsForHash().hasKey(IdentityType.User.getName(), id)){
            log.info("------------>查询redis数据库");
            //return (User) redisTemplate.opsForHash().get(IdentityType.User.getName(), id);
            return hashOperations.get(IdentityType.User.getName(), id);
        }else {
            log.info("------------>查询mysql数据库");
            User user = new User();
            user.setId(id);
            user.setName("赵六");
            user.setAge(18);
            //redisTemplate.opsForHash().put(IdentityType.User.getName(), id, user);
            hashOperations.put(IdentityType.User.getName(), id, user);
            return user;
        }

    }
}
