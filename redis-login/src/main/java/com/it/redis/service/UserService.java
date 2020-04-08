package com.it.redis.service;

import com.it.redis.pojo.User;

import java.util.Map;

public interface UserService {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password);

    /**
     * 登录不成功的操作
     * @param username
     * @return
     */
    public String loginValidate(String username);

    /**
     * 判断当前登录用户是否被限制登录
     * @param username
     * @return
     */
    public Map<String, Object> loginUserLock(String username);
}
