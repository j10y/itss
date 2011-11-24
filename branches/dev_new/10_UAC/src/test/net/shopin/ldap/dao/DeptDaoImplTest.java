package net.shopin.ldap.dao;

import static org.junit.Assert.*;

import java.util.List;

import net.shopin.ldap.entity.Department;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeptDaoImplTest {
	private static ApplicationContext context;
	private static DeptDao deptDao;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		deptDao = (DeptDao) context.getBean("deptDao");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdate() {
		
		Department department;
		department = new Department();
		department.setDeptName("上品公司");
		department.setDeptDesc("上品公司");
		department.setDisplayOrder(1);
		department.setStatus(2);
		department.setErpId("1101");
		department.setDeptNo("o=1101,ou=orgnizations");
		deptDao.update(department);
	}
	
	@Test
	public void testFindSubDeptsByParentRDN(){
		List list = null;
		list = deptDao.findSubDeptsByParentRDN("o=1101,ou=orgnizations");
		System.out.println("SIZE : " + list.size());
	}

}
