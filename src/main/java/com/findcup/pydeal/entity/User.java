package com.findcup.pydeal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    /**
     * 主键
     */
    private long uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码哈希值
     */
    private String password;

    /**
     * 性别
     */
    private int gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 口令
     */
    private String token;

    /**
     * 头像地址
     */
    private String avatar;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
