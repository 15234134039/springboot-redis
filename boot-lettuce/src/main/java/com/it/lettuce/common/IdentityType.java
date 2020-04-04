package com.it.lettuce.common;

/**
 * 代码中出现很多"user"字符串,解决方案1
 */
public enum IdentityType {
    /**
     * User
     */
    User(1,"user");


    private Integer id;
    private String name;

    IdentityType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
