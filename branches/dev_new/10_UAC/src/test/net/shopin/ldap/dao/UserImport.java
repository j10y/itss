package net.shopin.ldap.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.User;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserImport {
	private static ApplicationContext context;
	private static DeptDao deptDao;
	private static UserDao userDao;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		deptDao = (DeptDao) context.getBean("deptDao");
		userDao = (UserDao)context.getBean("userDao");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private Object getXSSFCellString(XSSFCell cell) {
		Object result = null;
		if (cell != null) {

			int cellType = cell.getCellType();

			switch (cellType) {

			case XSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().getString();
				break;
            //注意poi底层将CELL_TYPE_NUMERIC:数据自动转换为double类型返回
			case XSSFCell.CELL_TYPE_NUMERIC:
				result = new Double(cell.getNumericCellValue()).longValue();
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				result = cell.getNumericCellValue();
				break;
			case XSSFCell.CELL_TYPE_ERROR:
				result = null;
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 获取单元格中的内容
	 * @param cell
	 * @return
	 */
	private  Object getCellString(HSSFCell cell) {
		Object result = null;
		if (cell != null) {

			int cellType = cell.getCellType();

			switch (cellType) {

			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().getString();
				break;
            //注意poi底层将CELL_TYPE_NUMERIC:数据自动转换为double类型返回
			case HSSFCell.CELL_TYPE_NUMERIC:
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				result = null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			}
		}
		return result;
	}

	@Test
	public void testImportFromExcel() {
		String filePath = "C:/Users/wchao/Desktop/shopin_165.xlsx";
		processExcel(filePath);
	
		
	}

	private void processExcel(String filePath) {
		List<User> users = new ArrayList();
		String msg="";
		try {
			FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Integer rowCount = sheet.getLastRowNum();/* 获取一共存在多少行，poi中的API */
			Integer successCount = 0;
			Integer falsCount = 0;

			XSSFRow titleRow = sheet.getRow(0);
			if (titleRow!=null) {
				
			}else{
			    throw new RuntimeException("请检查导入的文件是否正确！");

			}

			if (rowCount < 1) {//第一行为标题
				throw new RuntimeException("请检查导入的文件是否包含数据！");
			}

			//简单的正则
			java.util.regex.Pattern simplePattern = java.util.regex.Pattern.compile("\\d*[-]*\\d*[-]*\\d*");
			
			java.util.regex.Pattern emailPattern = java.util.regex.Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			
			//部门正则表达式
			java.util.regex.Pattern deptPattern = java.util.regex.Pattern.compile("\\d{4,}?");
			for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
			    String rowMs = "";
				//HSSFRow row = sheet.getRow(rowIndex);
			    XSSFRow row = sheet.getRow(rowIndex);
			    if (row != null) {
			  		        
			        String emailCell = null;
			        String uidCell = null;
			        String gnCell = null;
			        String snCell = null;
			        String displayNameCell = null;
			        String deptCell = null;
			        String telCell = null;
			        String mobileCell = null;
			        String faxCell = null;
			        String titleCell = null;
			        String userTypeCell = null;
			        String passwordCell = null;
			        String descriptionCell = null;
			        
			        Object emailObj = getXSSFCellString(row.getCell((short)0));
			        emailCell = emailObj !=null ? emailObj.toString().trim() : "";
			        
			        uidCell = emailCell.substring(0, emailCell.indexOf("@"));
			        
			        Object snObj = getXSSFCellString(row.getCell((short)1));
			        snCell = snObj !=null ? snObj.toString().trim() : "";
			        
			        Object gnObj = getXSSFCellString(row.getCell((short)2));
			        gnCell = gnObj !=null ? gnObj.toString().trim() : "";
			        
			        Object displayNameObj = getXSSFCellString(row.getCell((short)3));
			        displayNameCell = displayNameObj !=null ? displayNameObj.toString().trim() : "";
			        
			        Object deptObj = getXSSFCellString(row.getCell((short)4));
			        deptCell = deptObj !=null ? deptObj.toString().trim() : "";
			        
			        String belongDeptDN = "";
			        String departmentName = "";
			        String departmentNumber = "";
			        
		            List<Department> deptList = deptDao.findDeptsByParam(deptCell);
		            if(deptList!=null && deptList.size()==1){
		            	departmentNumber = deptCell;
		            	belongDeptDN = deptList.get(0).getDn();
		            	departmentName = deptList.get(0).getDeptName();
		            }
			        
			        if(emailCell.length()>0 && !emailPattern.matcher(emailCell).matches()){
			        	rowMs += "Line: "+(rowIndex+1)+", email is incorrect";
			        	msg += rowMs+"<br>";
			        	continue ;
			        }
			        Object pwdObj = getXSSFCellString(row.getCell((short)5));
			        passwordCell = pwdObj !=null ? pwdObj.toString().trim() : "";
			        
			        Object telObj = getXSSFCellString(row.getCell((short)6));
			        telCell = telObj !=null ? telObj.toString().trim() : "";
			        if(telCell.length()>0 && !simplePattern.matcher(telCell).matches()){
			        	rowMs += "Line: "+(rowIndex+1)+", telephone is incorrect";
			        	msg += rowMs+"<br>";
			        	continue ;
			        }
			        
			        Object mobileObj = getXSSFCellString(row.getCell((short)7));
			        mobileCell = mobileObj !=null ? mobileObj.toString().trim() : "";
		        	if(mobileCell.length()>0 && !simplePattern.matcher(mobileCell).matches()){
		        		rowMs += "Line: "+(rowIndex+1)+", mobile is incorrect";
			        	msg += rowMs+"<br>";
			        	continue ;
			        }
			        
			        Object faxObj = getXSSFCellString(row.getCell((short)8));
			        faxCell = faxObj !=null ? faxObj.toString().trim() : "";
			        if(faxCell.length()>0 && !simplePattern.matcher(faxCell).matches()){
			        	rowMs += "Line: "+(rowIndex+1)+", fax is incorrect";
			        	msg += rowMs+"<br>";
			        	continue ;
			        }
			        
			        Object titleObj = getXSSFCellString(row.getCell((short)9));
			        titleCell = titleObj !=null ? titleObj.toString().trim() : "";
			        
			        Object photoObj = getXSSFCellString(row.getCell((short)10));
			        
			        
			        if("".equals(rowMs)){				        
			            User user = new User();
			            user.setUid(uidCell);
			            user.setPassword(passwordCell);
			            user.setCn(uidCell);
			            user.setSn(snCell);
			            user.setGivenName(gnCell);
			            user.setDisplayName(displayNameCell);
			            user.setDescription(descriptionCell);
			            user.setDepartmentNumber(deptCell);
			            user.setTitle(titleCell);
			            user.setMail(emailCell.trim());			            
			            user.setTelephoneNumber(telCell);
			            user.setMobile(mobileCell);
			            user.setFacsimileTelephoneNumber(faxCell);
			            user.setPhoto(null);
			            user.setDisplayOrder(0);
			            user.setStatus(0);
			            user.setO(departmentName);
			            user.setEmployeeNumber(null);
			            user.setEmployeeType("01");
			            user.setBelongDeptDN(belongDeptDN);
			            user.setBelongTitleDN(null);
			            user.setPhoto(photoObj != null ? photoObj.toString().getBytes() : null);
			            users.add(user);
			        }else{
			            msg += rowMs+"<br>";
			            //throw new RuntimeException(msg);

			        }
			    }
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(msg + "\n\n=================================================\n\n");
		for(int i=0; i<users.size(); i++){
			if(i==244){
				System.out.println("");
			}
			try {
				User user = users.get(i);
				User u = userDao.findByPrimaryKey(user.getUid());
				if(u!=null){
					userDao.update(user);
				}else{
					userDao.create(user);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("-----------" + (i+2) + "-----------\n" + e.getMessage() + "\n---------------------------");
			}
//			Name dn = buildDn(user);
//			DirContextAdapter context = new DirContextAdapter(dn);
//			mapToContext(user, context);
//			String filter="(uid=" + user.getUid() + ")";
//			List result = ldapTemplate.search("ou=users", filter, new UserContextMapper());
//			if(result.size()==0){
//				ldapTemplate.bind(dn, context, null);
//			}else{
//				//update
//				//update(user);
//				updateUserButPwd(user,false);
//			}
		}
	}

}
