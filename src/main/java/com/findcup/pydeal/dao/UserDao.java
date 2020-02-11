package com.findcup.pydeal.dao;

import com.findcup.pydeal.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface UserDao {

    /**
     * 根据参数查询用户列表
     *
     * @param param
     * @return
     */
    List<User> findUsers(Map param);

    /**
     * 查询用户总数
     *
     * @param param
     * @return
     */
    int getTotalUser(Map param);

    /**
     * 根据登录名和密码获取用户记录
     *
     * @return
     */
    User getUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据userToken获取用户记录
     *
     * @return
     */
    User getUserByToken(String token);

    /**
     * 根据id获取用户记录
     *
     * @return
     */
    User getUserById(Long uid);

    /**
     * 根据用户名获取用户记录
     *
     * @return
     */
    User getUserByUserName(String username);

    /**
     * 新增用户记录
     *
     * @return
     */
    int addUser(User user);

    /**
     * 修改密码
     *
     * @return
     */
    int updateUserPassword(@Param("uid") Long uid, @Param("password") String password);

    /**
     * 更新用户token值
     *
     * @param uid
     * @param token
     * @return
     */
    int updateUserToken(@Param("uid") Long uid, @Param("token") String token);

    /**
     * 批量删除
     *
     * @param uid
     * @return
     */
    int deleteUser(long uid);

    /**
     * 查询所有用户列表
     *
     * @return
     */
    List<User> getAllUsers();
}

