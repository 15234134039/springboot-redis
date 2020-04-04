package com.it.jedis.service;

import com.it.jedis.po.User;

public interface UserService {

    /**
     * Redis String类型
     * 输入Key，存在则在redis查询，否则在mysql查询，将结果赋给redis
     * @param key
     * @return
     */
    public String getString(String key);

    /**
     * Redis Hash类型
     * @return
     */
    public User selectById(String id);

}
