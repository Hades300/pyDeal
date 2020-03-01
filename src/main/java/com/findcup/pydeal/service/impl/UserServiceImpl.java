package com.findcup.pydeal.service.impl;

import com.findcup.pydeal.dao.UserDao;
import com.findcup.pydeal.entity.User;
import com.findcup.pydeal.service.UserService;
import com.findcup.pydeal.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired(required =false)
    private UserDao userDao;

    @Override
    public PageResult getUserPage(PageUtil pageUtil) {
        //当前页码中的数据列表
        List<User> users = userDao.findUsers(pageUtil);
        //数据总条数 用于计算分页数据
        int total = userDao.getTotalUser(pageUtil);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public User updateTokenAndLogin(String userName, String password) {
        User user = userDao.getUserByUserNameAndPassword(userName, MD5Util.MD5Encode(password, "UTF-8"));
        System.out.println(user);
        if (user != null) {
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", user.getUid());
            if (userDao.updateUserToken(user.getUid(), token) > 0) {
                //返回数据时带上token
                user.setToken(token);
                return user;
            }
        }
        return null;
    }

    /**
     * 获取token值
     *
     * @param sessionId
     * @param userId
     * @return
     */
    private String getNewToken(String sessionId, Long userId) {
        String src = sessionId + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }

    @Override
    public User selectById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User selectByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public int save(User user) {
        //密码加密
        user.setPassword(MD5Util.MD5Encode(user.getPassword(), "UTF-8"));
        return userDao.addUser(user);
    }

    @Override
    public int updatePassword(User user) {
        return userDao.updateUserPassword(user.getUid(), MD5Util.MD5Encode(user.getPassword(), "UTF-8"));
    }

    @Override
    public int deleteUser(long uid) {
        return userDao.deleteUser(uid);
    }

    @Override
    public User getUserByToken(String userToken) {
        return userDao.getUserByToken(userToken);
    }
}
