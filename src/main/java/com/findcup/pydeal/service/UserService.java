package com.findcup.pydeal.service;

import com.findcup.pydeal.entity.User;
import com.findcup.pydeal.utils.PageResult;
import com.findcup.pydeal.utils.PageUtil;

public interface UserService {

    /**
     * 分页功能
     *
     * @param pageUtil
     * @return
     */
    PageResult getUserPage(PageUtil pageUtil);

    /**
     * 登陆功能
     *
     * @return
     */
    User updateTokenAndLogin(String userName, String password);

    /**
     * 根据id获取用户记录
     *
     * @return
     */
    User selectById(Long id);

    /**
     * 根据用户名获取用户记录
     *
     * @return
     */
    User selectByUserName(String userName);

    /**
     * 新增用户记录
     *
     * @return
     */
    int save(User user);

    /**
     * 修改密码
     *
     * @return
     */
    int updatePassword(User user);

    /**
     * 删除功能
     *
     * @param uid
     * @return
     */
    int deleteUser(long uid);

    /**
     * 根据userToken获取用户记录
     *
     * @return
     */
    User getUserByToken(String userToken);
}
