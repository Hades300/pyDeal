package com.galaxyzeta.services;

//这里的内容全部废弃，仅作为替代方案参考！
import com.galaxyzeta.dao.TestMapper;
import com.galaxyzeta.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


//封装一个服务类，理论上简单的业务逻辑不需要服务类也能实现（只用编写的interface，只涉及单条SQL语句的操作）
//但如果要实现若干原子操作的组合，而不把他们放在Controller内实现，就需要拓展Service类来处理
//后来我觉得太麻烦了，还是全部给Controller来做吧
@Service
public class TestMapperImplement implements TestMapper {

    @Autowired
    TestMapperImplement(TestMapper testMapper){
        this.testMapper = testMapper;
    }
    private TestMapper testMapper;

    @Override
    public void setUser(User user) {
        testMapper.setUser(user);
    }

    @Override
    public User getUser(String s) {
        return testMapper.getUser(s);
    }

    @Override
    public void deleteUser(String username) {
        testMapper.deleteUser(username);
    }

    @Override
    public void updatePassword(String userName, String password) {
        testMapper.updatePassword(userName, password);
    }

    @Override
    public ArrayList<User> queryAllUsers() {
        return testMapper.queryAllUsers();
    }

    //此处新增一条拓展方法，因为需要用到查找和插入两个原子操作
    public boolean registerUser(User user){
        System.out.println(user.toString());
        User queryUser = testMapper.getUser(user.getUsername());
        if(queryUser == null){
            testMapper.setUser(user);
            System.out.println("[Service]Data Injected...");
            return true;
        } else {
            System.out.println("[Service]User already exist!");
            return false;
        }
    }
}