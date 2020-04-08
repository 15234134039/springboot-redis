package com.it.redis.controller;

import com.it.redis.pojo.User;
import com.it.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *
     * @param username
     * @param password
     * @param valcode   验证码
     * @return
     */
    @RequestMapping(produces = {"application/json;charset=UTF-8"}, value = "login")
    @ResponseBody
    public String login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "valcode", required = false) String valcode) {

        /**
         * 1.验证码比较
         */

        /**
         * 2.执行登录功能
         * 2.1先判断当前用户是否被限制登录
         */
        Map<String, Object> map = userService.loginUserLock(username);
        if ((boolean) map.get("flag")) {
            //限制登录
            return "登录失败，因" + username + "用户超过了限制登录次数，已被禁止登录，还剩"
                    + map.get("lockTime") + "分钟";
        } else {
            //没有被限制登录，执行登录功能
            User user = userService.login(username, password);

            if (user != null) {
                //登录成功，清空对应的key，跳转到index页面
                //TODO
                redisTemplate.delete(User.getLoginCountFailKey(username));
                return "";
            } else {
                //登录不成功
                String result = userService.loginValidate(username);
                return result;
            }
        }


    }

}
