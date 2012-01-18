package net.shopin.ldap.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.Duty;
import net.shopin.ldap.entity.User;
import net.shopin.util.PropertiesUtil;
import net.shopin.util.SpringContextUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

/**
 * @see net.shopin.ldap.dao.UserDaoImpl
 * @author wchao
 *
 */
public class UserDaoImpl implements UserDao {

	private LdapTemplate ldapTemplate;	
	
	private DeptDao deptDao;
	
	private DutyDao dutyDao;

	/**
	 * @see net.shopin.ldap.dao.UserDao#create(User)
	 * @author wchao
	 *
	 */
	public void create(User user) {
		Name dn = buildDn(user);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(user, context);
		String filter="(&(objectClass=shopin-inetOrgPerson)(uid=" + user.getUid() + "))";
		List result = ldapTemplate.search("ou=orgnizations", filter, new UserContextMapper());
		if(result.size()==0){
			ldapTemplate.bind(dn, context, null);
		}else{
			throw new NameAlreadyBoundException(new javax.naming.NameAlreadyBoundException("uid=" + user.getUid() + "的用户已经存在!"));
		}
	}

	private void mapToContext(User user, DirContextAdapter context) {

		context.setAttributeValues("objectclass", new String[] { "top", "shopin-inetOrgPerson"});
		context.setAttributeValue("uid", user.getUid());
		context.setAttributeValue("userPassword", user.getPassword()!=null && !"".equals(user.getPassword()) ? user.getPassword() : null);
		context.setAttributeValue("cn", user.getCn());
		context.setAttributeValue("sn", user.getSn());
		context.setAttributeValue("displayName", user.getDisplayName());
		context.setAttributeValue("givenName", user.getGivenName());
		context.setAttributeValue("description", StringUtils.isNotEmpty(user.getDescription()) ?  user.getDescription():null);
		context.setAttributeValue("departmentNumber", StringUtils.isNotEmpty(user.getDepartmentNumber()) ? user.getDepartmentNumber() : null);
		context.setAttributeValue("belongTitleDN", StringUtils.isNotEmpty(user.getBelongTitleDN()) ? user.getBelongTitleDN() : null);
		context.setAttributeValue("telephoneNumber", StringUtils.isNotEmpty(user.getTelephoneNumber()) ? user.getTelephoneNumber() : null);
		context.setAttributeValue("mobile", StringUtils.isNotEmpty(user.getMobile()) ? user.getMobile() : null);
		context.setAttributeValue("mail", StringUtils.isNotEmpty(user.getMail()) ? user.getMail() : null);
		context.setAttributeValue("facsimileTelephoneNumber", StringUtils.isNotEmpty(user.getFacsimileTelephoneNumber()) ? user.getFacsimileTelephoneNumber() : null);
		context.setAttributeValue("jpegPhoto", user.getPhoto()!=null && user.getPhoto().length>0?user.getPhoto():null);
		if(user.getPhoto()!=null && user.getPhoto().length>0) context.setAttributeValue("jpegPhoto", user.getPhoto());
		context.setAttributeValue("o", user.getO());
		context.setAttributeValue("status", user.getStatus().toString());
		context.setAttributeValue("displayOrder", user.getDisplayOrder().toString());
		context.setAttributeValue("title", StringUtils.isNotEmpty(user.getTitle()) ? user.getTitle() : null);
		context.setAttributeValue("belongTitleDN", StringUtils.isNotEmpty(user.getBelongTitleDN()) ? user.getBelongTitleDN() : null);
		
	}

	public void remove(User user) {
		ldapTemplate.unbind(buildDn(user));
	}
	
	public void delete(String userDN) {
		if(userDN.contains(PropertiesUtil.getProperties("base"))){
			userDN = userDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(userDN);
		context.setAttributeValue("status", "3");
		ldapTemplate.modifyAttributes(userDN, context.getModificationItems());
	}

	private Name buildDn(User user) {
		DistinguishedName path = new DistinguishedName(user.getBelongDeptDN().contains(PropertiesUtil.getProperties("base")) ? (user.getBelongDeptDN().replace(("," + PropertiesUtil.getProperties("base")), "")) : user.getBelongDeptDN());
		DistinguishedName dn = new DistinguishedName("uid=" + user.getUid());
		dn.prepend(path);
		return dn;
	}
	
	/**
	 * @see net.shopin.ldap.dao.UserDao#findByPrimaryKey(String, String)
	 * @author wchao
	 *
	 */
	public User findByPrimaryKey(String uid, String userType) {
		User user = new User();
		user.setUid(uid);
		user.setUserType(userType);
		User result = (User) ldapTemplate.lookup(buildDn(user), getContextMapper());
		return result;
	}
	
	/**
	 * @see net.shopin.ldap.dao.UserDao#findByPrimaryKey(String)
	 * @author wchao
	 *
	 */
	public User findByPrimaryKey(String uid) {
		String filter="(uid=" + uid + ")";
		List users = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter, new UserContextMapper());
		if(users.size()<=0){
			return null;
		}else{
			User result = (User) users.get(0);
			return result;
		}
	}

	private ContextMapper getContextMapper() {
		return new UserContextMapper();
	}
	
	private static class UserContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			User user = new User();
			user.setUid(context.getStringAttribute("uid"));
			byte [] bytes = (byte[]) context.getObjectAttribute("userPassword");
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<bytes.length; i++){
				sb.append("" + (char)bytes[i]);
			}
			user.setPassword(sb.toString());
			user.setDn(context.getDn().toString() + (StringUtils.isNotEmpty(PropertiesUtil.getProperties("base")) ? ',' + PropertiesUtil.getProperties("base") : ""));
			user.setCn(context.getStringAttribute("cn"));
			user.setSn(context.getStringAttribute("sn"));
			user.setDepartmentNumber(context.getStringAttribute("departmentNumber"));
			user.setBelongTitleDN(context.getStringAttribute("belongTitleDN"));
			user.setMail(context.getStringAttribute("mail"));
			user.setTelephoneNumber(context.getStringAttribute("telephoneNumber"));
			user.setMobile(context.getStringAttribute("mobile"));
			user.setFacsimileTelephoneNumber(context.getStringAttribute("facsimileTelephoneNumber"));
			user.setDisplayName(context.getStringAttribute("displayName"));
			user.setGivenName(context.getStringAttribute("givenName"));
			user.setDescription(context.getStringAttribute("description"));
			user.setPhoto((byte[])context.getObjectAttribute("jpegPhoto"));
			user.setO(context.getStringAttribute("o"));
			user.setStatus(StringUtils.isNotEmpty(context.getStringAttribute("status")) ? Integer.valueOf(Integer.valueOf(context.getStringAttribute("status"))) : 0);
			user.setDisplayOrder(StringUtils.isNotEmpty(context.getStringAttribute("displayOrder")) ? Integer.valueOf(Integer.valueOf(context.getStringAttribute("displayOrder"))) : 0);
			user.setEmployeeNumber(context.getStringAttribute("employeeNumber"));
			user.setEmployeeType(context.getStringAttribute("employeeType"));
			user.setTitle(context.getStringAttribute("title"));
			user.setBelongDeptDN(context.getDn().toString().substring(context.getDn().toString().indexOf(",")+1));
			return user;
		}
	}

	/**
	 * @see net.shopin.ldap.dao.UserDao#update(User)
	 * @author wchao
	 *
	 */
	public void update(User user) {
		// TODO 如果用户DN改变，即调整部门了
		User old = this.findByPrimaryKey(user.getUid());
		Name dn = buildDn(user);
		
		if(StringUtils.isNotEmpty(user.getDn())&& !user.getDn().equals(dn.toString() + (StringUtils.isNotEmpty(PropertiesUtil.getProperties("base")) ? ',' + PropertiesUtil.getProperties("base") : ""))){//DN改变，需要移动目录
			this.remove(old);
			if(user.getPhoto()==null||user.getPhoto().length == 0){
				user.setPhoto(old.getPhoto());//图片
			}
			this.create(user);
			return;
		}
		
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(user, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}
	
	/**
	 * @see net.shopin.ldap.dao.UserDao#updateUserButPwd(User, boolean)
	 * @author wchao
	 *
	 */
	public void updateUserButPwd(User user, boolean modPwd) {
		// TODO 如果修改用户的用户类型，需要unbind老数据，新bind新数据，图片信息需要保存
		//首先判断userType是否修改
		User old = this.findByPrimaryKey(user.getUid());
		if(!modPwd){
			user.setPassword(old.getPassword());
		}
		if(old.getUserType()==null || !old.getUserType().equals(user.getUserType())){//用户类型改变
			this.remove(old);
			if(user.getPhoto()==null||user.getPhoto().length == 0){
				user.setPhoto(old.getPhoto());//图片
			}
			this.create(user);
			return;
		}		
		Name dn = buildDn(user);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(user, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}

	public String importUsersFromFile(String filePath) {
		List<User> users = new ArrayList();
		String msg="";
		try {
			FileInputStream fis = new FileInputStream(filePath);
//			HSSFWorkbook workbook = new HSSFWorkbook(fis);
//			HSSFSheet sheet = workbook.getSheetAt(0);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Integer rowCount = sheet.getLastRowNum();/* 获取一共存在多少行，poi中的API */
			Integer successCount = 0;
			Integer falsCount = 0;

			//HSSFRow titleRow = sheet.getRow(0);
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
			        
			        Object emailObj = getXSSFCellString(row.getCell((short)0));
			        emailCell = emailObj !=null ? emailObj.toString().trim() : "";
			        
			        uidCell = emailCell.substring(0, emailCell.indexOf("@"));
			        
			        Object snObj = getXSSFCellString(row.getCell((short)1));
			        snCell = snObj !=null ? snObj.toString().trim() : "";
			        
			        Object gnObj = getXSSFCellString(row.getCell((short)2));
			        gnCell = gnObj !=null ? gnObj.toString().trim() : "";
			        
			        Object displayNameObj = getXSSFCellString(row.getCell((short)3));
			        displayNameCell = displayNameObj !=null ? displayNameObj.toString().trim() : "";
			        
			        Object deptObj = getXSSFCellString(row.getCell((short)5));
			        deptCell = deptObj !=null ? deptObj.toString().trim() : "";
			        
		            //add by awen for add a switch deptCell not a NUM as deptName to select form LDAP on 2011-06-23 begin
		            
		            if(!deptPattern.matcher(deptCell).matches()){
		            	DirContextAdapter context = new DirContextAdapter(DistinguishedName.EMPTY_PATH);
		        		String filter=null;
		        		filter="(&(objectClass=organizationalUnit)(description=" + deptCell +"))";
		        		List<Department> depts = ldapTemplate.search("ou=orgnizations", filter, deptDao.getContextMapper());
		        		if(depts.size() != 1){//如果指定名称的部门不惟一，那么不倒入数据库，记录日志，手动添加
		        			rowMs += "Line: "+(rowIndex+1)+", deptName not found or more than one";
				        	msg += rowMs+"<br>";
				        	continue ;
		        		}else{
			        		deptCell = depts.get(0).getDeptNo();
		        		}
		            }
		            
		            //add by awen for add a switch deptCell not a NUM as deptName to select form LDAP on 2011-06-23 end
			        
			        if(emailCell.length()>0 && !emailPattern.matcher(emailCell).matches()){
			        	rowMs += "Line: "+(rowIndex+1)+", email is incorrect";
			        	msg += rowMs+"<br>";
			        	continue ;
			        }
			        
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
			        
			        Object userTypeObj = getXSSFCellString(row.getCell((short)10));
			        userTypeCell = userTypeObj !=null ? userTypeObj.toString() : "";
			        userTypeCell = this.userTypeTrans(userTypeCell).trim();
			        
			        if("".equals(rowMs)){				        
			            User user = new User();
			            user.setUid(uidCell);
			            user.setPassword("000000");
			            user.setCn(uidCell);
			            user.setSn(snCell);
			            user.setGivenName(gnCell);
			            user.setDisplayName(displayNameCell);
			            user.setMail(emailCell.trim());			            
			            user.setDepartmentNumber(deptCell);
			            user.setTelephoneNumber(telCell);
			            user.setMobile(mobileCell);
			            user.setFacsimileTelephoneNumber(faxCell);
			            user.setTitle(titleCell);
			            user.setUserType(userTypeCell);			            
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
		
		for(int i=0; i<users.size(); i++){
			User user = users.get(i);
			Name dn = buildDn(user);
			DirContextAdapter context = new DirContextAdapter(dn);
			mapToContext(user, context);
			String filter="(uid=" + user.getUid() + ")";
			List result = ldapTemplate.search("ou=orgnizations", filter, new UserContextMapper());
			if(result.size()==0){
				ldapTemplate.bind(dn, context, null);
			}else{
				//update
				//update(user);
				updateUserButPwd(user,false);
			}
		}
		
		
		return msg;
	}
	
	/**
	 * 解释excel中的userType
	 * //1员工，2客户，3供应商
	 * @param userTypeDesc
	 * @return
	 */
	private String userTypeTrans(String userTypeDesc){
		if(userTypeDesc==null) return "1";
		
		if(userTypeDesc.equals("employees")){
			return "1";
		}else if(userTypeDesc.equals("customers")){
			return "2";
		}else if(userTypeDesc.equals("suppliers")){
			return "3";
		}else if(userTypeDesc.equals("specialuser")){
			return "4";
		}
		return "1";//默认为员工
	}
	
	/**
	 * 获取单元格中的内容
	 * 2007版本以上的
	 * @param cell
	 * @return
	 */
	private static Object getXSSFCellString(XSSFCell cell) {
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
	private static Object getCellString(HSSFCell cell) {
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

	
	
	/* 获取用户的uid或cn模糊匹配uidOrName的用户列表
	 * @see net.shopin.ldap.dao.UserDao#findUserList(java.lang.String)
	 */
	public List<User> findUserList(String uidORName) {
		String filters = null;
		DirContextAdapter context = new DirContextAdapter(DistinguishedName.EMPTY_PATH);
		String filter=null;
		if(uidORName != null && !uidORName.equals("")){
			filter="(&(objectClass=shopin-inetOrgPerson)(|(uid=*" + uidORName + "*)(cn=*"+ uidORName + "*)(title=*"+ uidORName + "*)(displayName=*"+ uidORName + "*)))";
		}else{
			filter="(&(objectClass=shopin-inetOrgPerson)(|(uid=*)(cn=*)(title=*)(displayName=*)))";
		}
		List<User> users = ldapTemplate.search("ou=orgnizations", filter, getContextMapper());

		return users;
	
	}
	
	public List<User> findUserList(String deptDN, String uidORName) {
		return this.findUserList(deptDN, uidORName, Integer.MAX_VALUE);
	}
	
	public List<User> findUserList(String deptDN, String uidORName, long limit) {
		if(StringUtils.isNotEmpty(deptDN) && deptDN.contains(PropertiesUtil.getProperties("base"))){
			deptDN = deptDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		String filters = null;
		List<User> users = new ArrayList();
		DirContextAdapter context = new DirContextAdapter(DistinguishedName.EMPTY_PATH);
		String filter=null;
		if(StringUtils.isNotEmpty(uidORName)){
			filter="(&(objectClass=shopin-inetOrgPerson)(|(uid=*" + uidORName + "*)(cn=*"+ uidORName + "*)(title=*"+ uidORName + "*)(displayName=*"+ uidORName + "*)))";
			
		}else{
			filter="(&(objectClass=shopin-inetOrgPerson)(|(uid=*)(cn=*)(title=*)(displayName=*)))";
		}
		SearchControls controls  = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setCountLimit(limit);
		controls.setReturningObjFlag(true);
		DirContext dirContext = ldapTemplate.getContextSource().getReadOnlyContext();
		try {
			NamingEnumeration results = dirContext.search((StringUtils.isNotEmpty(deptDN) ? deptDN : "ou=orgnizations"), filter, controls);
			while (results != null && results.hasMoreElements()) {

                SearchResult sr = (SearchResult)results.next();
                Attributes attributes = sr.getAttributes();
                User user = convertAttributesToUser((DirContextAdapter)sr.getObject(), attributes);
                if(user != null)users.add(user);
                
			} 
		} catch (org.springframework.ldap.NamingException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally{
			try {
				dirContext.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}		
		return users;
	}
	
	

	private User convertAttributesToUser(DirContextAdapter dirContextAdapter,Attributes attributes) {
		try {
			User user = new User();
			

			user.setUid(attributes.get("uid").get().toString());
			byte [] bytes = attributes.get("userPassword")!= null ? (byte[]) (attributes.get("userPassword").get()) : new byte [] {};
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<bytes.length; i++){
				sb.append("" + (char)bytes[i]);
			}
			String dnStr = dirContextAdapter.getDn().toString();
			
			user.setDn(dnStr + (StringUtils.isNotEmpty(PropertiesUtil.getProperties("base")) ? ',' + PropertiesUtil.getProperties("base") : ""));
			
			user.setPassword(sb.toString());
			user.setCn(attributes.get("cn").get().toString());
			user.setSn(attributes.get("sn").get().toString());
			if(attributes.get("departmentNumber")!=null)user.setDepartmentNumber(attributes.get("departmentNumber").get().toString());
			if(attributes.get("title")!=null)user.setTitle(attributes.get("title").get().toString());
			if(attributes.get("mail")!=null)user.setMail(attributes.get("mail").get().toString());
			if(attributes.get("telephoneNumber")!=null)user.setTelephoneNumber(attributes.get("telephoneNumber").get().toString());
			if(attributes.get("mobile")!=null)user.setMobile(attributes.get("mobile").get().toString());
			if(attributes.get("facsimileTelephoneNumber")!=null)user.setFacsimileTelephoneNumber(attributes.get("facsimileTelephoneNumber").get().toString());
			if(attributes.get("displayName")!=null)user.setDisplayName(attributes.get("displayName").get().toString());
			if(attributes.get("givenName")!=null)user.setGivenName(attributes.get("givenName").get().toString());
			if(attributes.get("description")!=null)user.setDescription(attributes.get("description").get().toString());
			if(attributes.get("o")!=null)user.setO(attributes.get("o").get().toString());//部门名称
			if(attributes.get("status")!=null)user.setStatus(Integer.valueOf(attributes.get("status").get().toString()));
			if(attributes.get("displayOrder")!=null)user.setDisplayOrder(Integer.valueOf(attributes.get("displayOrder").get().toString()));
			if(attributes.get("employeeNumber")!=null)user.setEmployeeNumber(attributes.get("employeeNumber").get().toString());
			if(attributes.get("employeeType")!=null)user.setEmployeeType(attributes.get("employeeType").get().toString());
			if(attributes.get("jpegPhoto")!=null)user.setPhoto((byte[])attributes.get("jpegPhoto").get());
			if(attributes.get("belongTitleDN")!=null)user.setBelongTitleDN(attributes.get("belongTitleDN").get().toString());
			user.setBelongDeptDN(dnStr.substring(dnStr.indexOf(",")+1));

			return user;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public DeptDao getDeptDao() {
		return deptDao;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public User findByDN(String userDN) {
		if(userDN.contains(PropertiesUtil.getProperties("base"))){
			userDN = userDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		return (User)ldapTemplate.lookup(userDN, new UserContextMapper());
	}

	public DutyDao getDutyDao() {
		return dutyDao;
	}

	public void setDutyDao(DutyDao dutyDao) {
		this.dutyDao = dutyDao;
	}
	
	
	
}
