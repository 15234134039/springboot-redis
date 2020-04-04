package com.it.lettuce.service;


import com.it.lettuce.po.User;

public interface UserService {

    /**
     * lettuce string类型
     * @param key
     * @return
     */
    public String getString(String key);

    public void expireStr(String key, Object value);

    /**
     * lettuce hash类型
     * @param id
     * @return
     */
    public User selectById(String id);

}
