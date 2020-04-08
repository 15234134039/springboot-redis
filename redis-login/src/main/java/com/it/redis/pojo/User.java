package com.it.redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String username;
    private String password;

    public static String getKeyName(){
        return "user:";
    }

    /**
     * 锁定限制登录key：  user:loginTime:lock:用户名
     * @param username
     * @return
     */
    public static String getLoginTimeLockKey(String username){
        return "user:loginTime:lock:" + username;
    }

    /**
     * 登录失败次数key：   user:loginCount:fail:用户名
     * @param username
     * @return
     */
    public static String getLoginCountFailKey(String username){
        return "user:loginCount:fail:" + username;
    }
}
