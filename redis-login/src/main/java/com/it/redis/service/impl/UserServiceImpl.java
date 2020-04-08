package com.it.redis.service.impl;

import com.it.redis.pojo.User;
import com.it.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    @Resource(name = "redisTemplate")
//    private ValueOperations<String, String> valueOperations;

    @Override
    public User login(String username, String password) {
        if("admin".equals(username) && "123456".equals(password)){
            return new User(username, password);
        }
        return null;
    }

    /**
     * 登录不成功操作
     * @param username
     * @return
     */
    @Override
    public String loginValidate(String username) {
        String loginCountFailKey = User.getLoginCountFailKey(username);
        int num = 5;
        if(!redisTemplate.hasKey(loginCountFailKey)){
            //第一次登录失败
            redisTemplate.opsForValue().set(loginCountFailKey, "1");
            redisTemplate.expire(loginCountFailKey, 2, TimeUnit.MINUTES);
            return "登录失败，在2分钟内还允许输入错误" + (num-1) + "次";
        }else {
            //不是首次登录失败，查询登录失败次数
            long loginFailCount = Long.parseLong(redisTemplate.opsForValue().get(loginCountFailKey));
            if(loginFailCount < (num-1)){
                //对指定key增加指定数量
                redisTemplate.opsForValue().increment(loginCountFailKey, 1);
                Long seconds = redisTemplate.getExpire(loginCountFailKey, TimeUnit.SECONDS);
                return username + "登录失败，在" + seconds + "秒内还允许输入错误" + (num-loginFailCount-1) + "次";
            }else {
                //超过指定登录次数,设置的值1可随意
                redisTemplate.opsForValue().set(User.getLoginTimeLockKey(username), "1");
                redisTemplate.expire(User.getLoginTimeLockKey(username), 1, TimeUnit.HOURS);
                return "登录失败次数超过" + num + "次，限制登录1小时";
            }
        }
    }

    /**
     * 判断当前登录用户是否被限制登录
     * 查询LoginTimeLockKey是否存在，存在则被限制，提示限制还剩多长时间，
     * 不存在则不限制
     * @param username
     * @return
     */
    @Override
    public Map<String, Object> loginUserLock(String username) {
        String loginTimeLockKey = User.getLoginTimeLockKey(username);
        Map<String, Object> map = new HashMap<>();
        if(redisTemplate.hasKey(loginTimeLockKey)){
            //以分钟为单位
            Long lockTime = redisTemplate.getExpire(loginTimeLockKey, TimeUnit.MINUTES);
            map.put("flag", true);
            map.put("lockTime", lockTime);
        }else {
            map.put("flag", false);
        }
        return map;
    }
}
