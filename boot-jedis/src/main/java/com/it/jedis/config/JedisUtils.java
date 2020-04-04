package com.it.jedis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtils {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取Jedis资源
     * @return
     */
    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 释放Jedis连接
     * @param jedis
     */
    public void close(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

}
