package com.galaxyzeta.dao;

import com.galaxyzeta.beans.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;

//这里采用注解开发，相比XML配置开发更加简单
//本接口旨在演示CRUD操作的实现
@Mapper
public interface TestMapper {
    //插入 原子操作
    @Insert(value = "INSERT INTO user(username, pswd) VALUES (#{username}, #{password});")
    void setUser(User user);

    //查找 原子操作
    @Select(value = "SELECT * FROM user WHERE username = #{username}")
    @Results(id = "returnUser",
            value = {
            @Result(property = "username", column = "username", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "pswd", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    User getUser(@Param(value = "username")String username);

    //删除 原子操作
    @Delete(value = "DELETE FROM user WHERE username = #{username};")
    void deleteUser(String username);

    //修改 原子操作
    @Update(value = "UPDATE user SET pswd = #{pswd} WHERE username = #{u};")
    void updatePassword(@Param(value = "u") String userName, @Param(value = "pswd") String password);

    //演示查找到很多条数据该怎么做
    @Select("SELECT * FROM user;")
    @ResultMap(value = "returnUser")
    ArrayList<User> queryAllUsers();
}
