package com.galaxyzeta;

import com.galaxyzeta.dao.TestMapper;
import com.galaxyzeta.beans.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

//此处进行单元测试
@SpringBootTest
class SpringboottestApplicationTests {
	@Autowired
	SpringboottestApplicationTests(TestMapper testMapper){
		this.testMapper = testMapper;
	}
	private TestMapper testMapper;

	@Test
	void contextLoads() {
		System.out.println("Context Loading...");
	}

	@Test
	void iBatisTestQueryAllUsers(){
		//测试成功
		ArrayList<User> li = testMapper.queryAllUsers();
		for(User user: li){
			System.out.println(user.toString());
		}
	}

	@Test
	//测试条件：保证数据库中存在username = xxx
	void iBatisTestDeleteUser(){
		//测试成功
		testMapper.deleteUser("galaxyzeta");
	}

	@Test
	//测试条件：保证数据库中存在username = xxx
	void iBatisTestUpdatePassword(){
		//测试成功
		testMapper.updatePassword("asd", "overwatchTracer");
	}
}
