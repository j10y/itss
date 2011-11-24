package net.shopin.ldap.dao;


import java.util.List;

import net.shopin.ldap.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoImplTest {
	private static ApplicationContext context;
	private static UserDao userDao;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userDao = (UserDao)context.getBean("userDao");
	}
	
	@Test
	public void testFindByRDN(){
		User user = userDao.findByRDN("uid=admin,ou=employees,ou=users");
		System.out.println("---------------------------\n" + user.getDisplayName());
	}
	
	@Test
	public void testfindUserList3(){
		List<User> list = userDao.findUserList("", "h", 2);
		int i = 0;
		for(User user : list){
			
			System.out.println("--------------"+ i++ +"-------------\n" + user.getDisplayName());
		}
		
	}
	
	@Test
	public void testfindUserList1(){
		List<User> list = userDao.findUserList("h");
		for(User user : list)
		System.out.println("---------------------------\n" + user.getDisplayName());
	}

	@After
	public void tearDown() throws Exception {
		
	}

}
