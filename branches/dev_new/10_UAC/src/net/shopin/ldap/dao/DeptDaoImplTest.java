package net.shopin.ldap.dao;

import static org.junit.Assert.*;

import net.shopin.ldap.entity.Department;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeptDaoImplTest {
	private static ApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdate() {
		DeptDao deptDao = (DeptDao) context.getBean("deptDao");
		Department department;
		department = new Department();
		department.setDeptName("上品公司");
		department.setDeptDesc("上品公司");
		department.setDisplayOrder(11);
		department.setStatus(2);
		department.setErpId("1101");
		department.setDeptNo("o=1101,ou=orgnizations");
		deptDao.update(department);
	}

}
