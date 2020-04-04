package com.it.lettuce.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;

    /**
     * 代码中出现很多"user"字符串,解决方案2
     */
    public static String getKeyName(){
        return "user";
    }
}
