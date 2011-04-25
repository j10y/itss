package com.digitalchina.itil.account.dao.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.entity.WorkSpace;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.itil.account.dao.AccountDao;
import com.digitalchina.itil.account.entity.AccountModifyDesc;
import com.digitalchina.itil.account.entity.AccountModifyRecord;
import com.digitalchina.itil.account.entity.AccountNewAppAdmin;
import com.digitalchina.itil.account.entity.AccountSBUOfficer;
import com.digitalchina.itil.account.entity.AccountType;
import com.digitalchina.itil.account.entity.DCContacts;
import com.digitalchina.itil.account.entity.DeviceType;
import com.digitalchina.itil.account.entity.MobileTelephoneApply;
import com.digitalchina.itil.account.entity.PersonFormalAccount;
import com.digitalchina.itil.account.entity.SpecialAccount;
import com.digitalchina.itil.account.entity.SystemAppAdmin;
import com.digitalchina.itil.account.entity.Win7PlatForm;
import com.digitalchina.itil.account.service.DataBaseConnection;
import com.digitalchina.itil.account.webservice.HrInfoService;
import com.digitalchina.itil.account.webservice.HrInfoServiceLocator;
import com.digitalchina.itil.account.webservice.HrInfoServiceSoap_PortType;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.extci.entity.AppAdministrator;
import com.digitalchina.itil.config.extlist.entity.AR_DrawSpace;
import com.digitalchina.itil.config.extlist.entity.MailForwardApply;
import com.digitalchina.itil.config.extlist.entity.MailGroup;
import com.digitalchina.itil.config.extlist.entity.MailVolume;
import com.digitalchina.itil.config.extlist.entity.WWWScanType;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;
import com.digitalchina.itil.require.entity.HRSAccountApply;
import com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis;
import com.digitalchina.itil.service.entity.ServiceItemProcess;

public class AccountDaoImpl extends BaseDao implements AccountDao{
	
	public List<AccountSBUOfficer> findOfficer(String processNameDescription,
			String personScope) {
		Criteria c = super.createCriteria(AccountSBUOfficer.class);
		c.add(Restrictions.eq("processNameDescription",processNameDescription));
		c.add(Restrictions.eq("personScope", personScope));
		return c.list();
	}

    public PersonFormalAccount findPersonAccount(String accountType,
			UserInfo applyUser) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		if(accountType.equals("座机")){
			c.add(Restrictions.eq("departTelephone","0"));
		}
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType ));
		//c.add(Restrictions.eq("accountType",at));
		c.add(Restrictions.eq("accountowner", applyUser));
		c.add(Restrictions.eq("accountState","1"));
		return (PersonFormalAccount) c.uniqueResult();
		
	}
	public PersonFormalAccount findTelephoneAccount(String accountType,
			String telephoneNumber) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType));
		c.add(Restrictions.eq("telephoneNumber", telephoneNumber));
		c.add(Restrictions.eq("accountState","1"));
		return (PersonFormalAccount) c.uniqueResult();
	}

	public List findSpecailAccount(String accountType,
			UserInfo applyUser) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType ));
		//c.add(Restrictions.eq("accountType",at));
		c.add(Restrictions.eq("accountNowUser", applyUser));
		c.add(Restrictions.eq("accountState","1"));
		//c.list();
		return c.list();
	}

	public List findSpecailAccountByApplyId(String applyId) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		AccountApplyMainTable apply=this.get(AccountApplyMainTable.class,Long.valueOf(applyId));
		c.add(Restrictions.eq("applyId", apply));
		return c.list();
	}

	public List findAllPersonAccount(UserInfo userInfo) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.add(Restrictions.eq("accountowner", userInfo));
		c.add(Restrictions.eq("accountState","1"));
		c.setFetchMode("mailValue", FetchMode.JOIN);
		c.setFetchMode("wwwAccountValue", FetchMode.JOIN);
		c.setFetchMode("accountType", FetchMode.JOIN);
		//c.setFetchMode(arg0, arg1);
		return c.list();
	}

	

	public List findMailForwardAccount(UserInfo userInfo) {
		Criteria c = super.createCriteria(MailForwardApply.class);
		c.add(Restrictions.eq("accountOwner", userInfo));
		c.add(Restrictions.eq("accountState","1"));
		return c.list();
	}

	public List findAllSpecailAccount(UserInfo userInfo) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		c.add(Restrictions.eq("accountNowUser", userInfo));
		c.add(Restrictions.eq("accountState","1"));
		c.setFetchMode("mailValue", FetchMode.JOIN);
		c.setFetchMode("wwwAccountValue", FetchMode.JOIN);
		c.setFetchMode("accountType", FetchMode.JOIN);
		return c.list();
	}

	public HRSAccountApply findHRSAccountByName(String accountName) {
		Criteria c = super.createCriteria(HRSAccountApply.class);
		c.add(Restrictions.eq("accountName", accountName));
		c.add(Restrictions.eq("accountState","1"));
		return (HRSAccountApply) c.uniqueResult();
	}

	public MailGroup findMailGroupByName(String accountName) {
		Criteria c = super.createCriteria(MailGroup.class);
		c.add(Restrictions.eq("groupNewName", accountName));
		c.add(Restrictions.eq("accountState","1"));
		return (MailGroup) c.uniqueResult();
	}

	public MobileTelephoneApply findMobileTelephone(String accountType,
			UserInfo applyUser) {
		Criteria c = super.createCriteria(MobileTelephoneApply.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType ));
		//c.add(Restrictions.eq("accountType",at));
		c.add(Restrictions.eq("accountowner", applyUser));
		c.add(Restrictions.eq("accountState","1"));
		//c.list();
		return (MobileTelephoneApply) c.uniqueResult();
	}

	public String importAccountDateExcel(String realPath,String accountType,String isnew) {
		// TODO Auto-generated method stub
		String errors="";
		try {
			Integer at=null;
			if(accountType!=null){
				at=Integer.parseInt(accountType);
			}
			Integer isn=null;
			if(isnew!=null&&isnew.length()>0){
				isn=Integer.parseInt(isnew);
			}
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
						realPath));
			
			HSSFSheet sheet = workbook.getSheetAt(0);
			if(at==1){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforDomain(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
			}else if(at==2){//邮件帐号 
				if(isn==0){
					for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
						System.out.println("=================导入到第 " + i+ " 行=================================");
						HSSFRow row = sheet.getRow(i);
							try {
								errors=errors+this.readRowforaddemail(row)+",";
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}else{
					for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
						System.out.println("=================导入到第 " + i+ " 行=================================");
						HSSFRow row = sheet.getRow(i);
							try {
								errors=errors+this.readRowforupdataemail(row)+",";
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			}else if(at==13){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforBi(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==9){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforELog(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==10){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforSCM(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==11){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforPushMail(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==8){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforEB(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==7){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforERP(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==4){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforMSN(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==3){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforWWW(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==15){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforTelephone(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==16){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforMobileTelephone(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else if(at==5){
				
				if(isnew.equals("0")){
				for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
					System.out.println("=================导入到第 " + i+ " 行=================================");
					HSSFRow row = sheet.getRow(i);
						try {
							errors=errors+this.readRowforRemote(row)+",";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				}else{
					for (int i =1; i <=sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()
						System.out.println("=================导入到第 " + i+ " 行=================================");
						HSSFRow row = sheet.getRow(i);
							try {
								errors=errors+this.readRowforUpdateRemote(row)+",";
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errors;
	}
	
	private String readRowforaddemail(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		String value2="";//员工编号
		String value3="";//成本中心编号
		Long value4=null;//部门名称对应的部门号
		Long value5=null;//邮件等价名部门名称
		Long value6=null;//工作地点
		String value7="";//邮件服务器
		Long value8=null;//帐号类型
		String value9="";//帐号名
		String value10="";//特殊需求
		String value11="";//备注
		Integer value12=1;//零一标志位
		//Integer orderFlag = null;
		String value13="";//操作说明
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		WorkSpace ws=null;
		SameMailDept smd=null;
		Department dp=null;
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo us=(UserInfo) list.get(0);
				user=us;
				userId=us.getId();
			}
		}
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 value2 = this.getCellStringValue(cell2);
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4= row.getCell((short) 4);
		if(cell4!=null){
			String value=this.getCellStringValue(cell4);
			Criteria c = super.getCriteria(Department.class);
			c.add(Restrictions.eq("departName", value));
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				dp=(Department) list.get(0);
				value4=dp.getDepartCode();
			}
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			String value = this.getCellStringValue(cell5);
			Criteria c = super.getCriteria(SameMailDept.class);
			c.add(Restrictions.eq("name", value));
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				smd=(SameMailDept) list.get(0);
				value5=smd.getId();
			}
		}
//		HSSFCell cell6 = row.getCell((short) 6);
//		if(cell6!=null){
//			String value = this.getCellStringValue(cell6);
//			Criteria c = super.getCriteria(WorkSpace.class);
//			c.add(Restrictions.like("space", value, MatchMode.ANYWHERE));
//			List list=c.list();
//			if(list!=null&&!list.isEmpty()){
//				ws=(WorkSpace) list.get(0);
//				value6=ws.getId();
//			}
//		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){
			 value7 = this.getCellStringValue(cell7);
		}
		HSSFCell cell8 = row.getCell((short) 8);
		if(cell8!=null){
			 String value = this.getCellStringValue(cell8);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value8=at.getId();
					actype=at;
				}
		}
		HSSFCell cell9 = row.getCell((short) 9);
		if(cell9!=null){
			 value9 = this.getCellStringValue(cell9);
		}
		HSSFCell cell10 = row.getCell((short) 10);
		if(cell10!=null){
			 value10 = this.getCellStringValue(cell10);
		}
		HSSFCell cell11 = row.getCell((short) 11);
		if(cell11!=null){
			 value11 = this.getCellStringValue(cell11);
		}
		HSSFCell cell12 = row.getCell((short) 12);
		if(cell12!=null){//1表示有效，0表示无效
			 value12 = Integer.parseInt(this.getCellStringValue(cell12));
		}
		HSSFCell cell13 = row.getCell((short) 13);
		if(cell13!=null){
			 value13 = this.getCellStringValue(cell13);
		}
		//String hql=null;
		if(value8==2){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			 //c.add(Restrictions.eq("accountName", value9));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list!=null&&list.size()>0){
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value9+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value9+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次");
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value9);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setAccountState(value12.toString());
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value10);
				 pfa.setRemarkDesc(value11);
				 //pfa.setWorkSpace(ws);
				 pfa.setMailServer(value7);
				 pfa.setDepartment(dp);
				 pfa.setCostCenterCode(value3);
				 pfa.setSameMailDept(smd);
				 //pfa.setComments(value13);
				 this.save(pfa);
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value13);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
//				hql = "insert into itil_ac_PersonFormalAccount (accountName,accountType,accountowner,accountState,createDate,rightsDesc,remarkDesc,workSpace,mailServer,department,costCenterCode,sameMailDept) " +
//				"Values('"+value9+"',"+value8+","+userId+","+value12+",'"+applyDate+"','"+value10+"','"+value11+"',"+value6+",'"+value7+"',"+value4+",'"+value3+"',"+value5+")";
//				 errors="插入"+errors+user.getRealName()+user.getItcode()+"的帐号名为"+value9+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次!";
//				 System.out.println("插入"+user.getRealName()+user.getItcode()+"的帐号名为"+value9+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次");
			 }
		}else if(value8==14||value8==17 ){
			Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value9));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 List list=c.list();
			 if(list!=null&&list.size()>0){
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value9+"的"+actype.getName()+"已存在！帐号名重复!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value9+"的"+actype.getName()+"已存在！帐号名重复");
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！"); 
			 }else{
				 SpecialAccount pfa=new SpecialAccount();
				 pfa.setAccountName(value9);
				 pfa.setAccountType(actype);
				 pfa.setAccountNowUser(user);
				 pfa.setAccountState(value12.toString());
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value10);
				 pfa.setRemarkDesc(value11);
				 //pfa.setWorkSpace(ws);
				 pfa.setMailServer(value7);
				 pfa.setDepartment(dp);
				 pfa.setCostCenterCode(value3);
				 pfa.setSameMailDept(smd);
				 //pfa.setComments(value13);
				 this.save(pfa);
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value13);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
//			hql = "insert into itil_ac_SpecialAccount (accountName,accountType,accountNowUser,accountState,createDate,rightsDesc,remarkDesc,workSpace,mailServer,department,costCenterCode,sameMailDept) " +
//			"Values('"+value9+"',"+value8+","+userId+","+value12+",'"+applyDate+"','"+value10+"','"+value11+"',"+value6+",'"+value7+"',"+value4+",'"+value3+"',"+value5+")";
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
//	        try {
//				Connection conn = super.getSession().connection();
//				Statement stmt = conn.createStatement();
//				stmt.executeUpdate(hql);
//				conn.commit();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			return errors;
		}	
	private String readRowforupdataemail(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		Long value4=null;//邮箱大小
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//操作说明
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		MailVolume mv=null;
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			String value= this.getCellStringValue(cell4);
			Criteria c = super.getCriteria(MailVolume.class);
			c.add(Restrictions.eq("volume", value).ignoreCase());
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					mv=(MailVolume) list.get(0);
					value4=mv.getId();
				}
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){
			 value7 = this.getCellStringValue(cell7);
		}
		//String hql=null;
		if(value2==2){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个");
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setMailValue(mv);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				// pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors="修改"+errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else if(value2==14||value2==17 ){
			Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个");
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 SpecialAccount pfa=(SpecialAccount) list.get(0);
				 pfa.setMailValue(mv);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors="修改"+errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	
	private String readRowforBi(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//是否涉及薪酬
		String value8="";//操作说明
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
			 if(value7.equals("是")){
				 value7="1";
			 }else{
				 value7="0";
			 }
		}
		HSSFCell cell8 = row.getCell((short) 8); 
		if(cell8!=null){
			 value8 = this.getCellStringValue(cell8);
		}
		if(value2==13){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setReferSalary(value7);
				// pfa.setComments(value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setReferSalary(value7);
				// pfa.setComments(pfa.getComments()+value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	
	private String readRowforELog(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//操作说明
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){
			 value7 = this.getCellStringValue(cell7);
		}
		
		if(value2==9){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				// pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	private String readRowforSCM(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//操作说明
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		if(value2==10){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				// pfa.setComments(value7);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 //pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	
	private String readRowforPushMail(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//操作说明
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		String hql=null;
		if(value2==11){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				// pfa.setComments(value7);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				// pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	private String readRowforEB(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//手机号码
		String value8="";//操作说明
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		HSSFCell cell8 = row.getCell((short) 8);
		if(cell8!=null){//
			 value8 = this.getCellStringValue(cell8);
		}
		if(value2==8){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setTelephone(value7);
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				// pfa.setComments(value8);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setTelephone(value7);
				// pfa.setComments(pfa.getComments()+value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"(帐号名不能修改)";
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else if(value2==21){
			 Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 SpecialAccount pfa=new SpecialAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountNowUser(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setTelephone(value7);
				// pfa.setComments(value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 SpecialAccount pfa=(SpecialAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setTelephone(value7);
				// pfa.setComments(pfa.getComments()+value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	private String readRowforERP(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//ERP名
		String value8="";//操作说明
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		HSSFCell cell8 = row.getCell((short) 8);
		if(cell8!=null){//
			 value8 = this.getCellStringValue(cell8);
		}
		String hql=null;
		if(value2==7){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setErpUserName(value7);
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				 //pfa.setComments(value8);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setErpUserName(value7);
				 //pfa.setComments(pfa.getComments()+value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"(帐号名不能修改)";
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else if(value2==12){
			 Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 SpecialAccount pfa=new SpecialAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountNowUser(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setErpUserName(value7);
				 //pfa.setComments(value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 SpecialAccount pfa=(SpecialAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setErpUserName(value7);
				// pfa.setComments(pfa.getComments()+value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	
	private String readRowforMSN(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		String hql=null;
		if(value2==4){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				 //pfa.setComments(value7);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 //pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"(帐号名不能修改)";
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else if(value2==18){
			 Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 SpecialAccount pfa=new SpecialAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountNowUser(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 //pfa.setComments(value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 SpecialAccount pfa=(SpecialAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				// pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	
	private String readRowforWWW(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		Long value4=null;//额度
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		WWWScanType wwwType=null;
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			String value= this.getCellStringValue(cell4);
			Criteria c=super.getCriteria(WWWScanType.class);
			c.add(Restrictions.eq("value", Integer.parseInt(value)));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					WWWScanType at=(WWWScanType) list.get(0);
					value4=at.getId();
					wwwType=at;
				}
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		String hql=null;
		if(value2==3){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setWwwAccountValue(wwwType);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				// pfa.setComments(value7);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setWwwAccountValue(wwwType);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				// pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"(帐号名不能修改)";
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else if(value2==19){
			 Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 SpecialAccount pfa=new SpecialAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountNowUser(user);
				 pfa.setCreateDate(da);
				 pfa.setWwwAccountValue(wwwType);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 //pfa.setComments(value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 SpecialAccount pfa=(SpecialAccount) list.get(0);
				 pfa.setWwwAccountValue(wwwType);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				// pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	private String readRowforTelephone(HSSFRow row) throws Exception{
		String errors="";
		String itcode="";// itcode
		String telnumber="";//telnumber
		String voip="";//voip
		String remarkDesc="";//备注
		String deptTel="";//是否部门电话
		String workSpace="";//工作地点
		String acctype="";//帐号类型
		String state="";//状态位
		Date da=DateUtil.getCurrentDate();
		UserInfo user=null;
		WorkSpace ws=null;
		AccountType actype=null;
		
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){
			state=this.getCellStringValue(cell7);
			if(state.equals("0")){
				//
				HSSFCell cell0 = row.getCell((short) 0);
				if(cell0!=null){
				 	itcode=this.getCellStringValue(cell0);
					Criteria c = super.getCriteria(UserInfo.class);
					c.add(Restrictions.eq("itcode", itcode).ignoreCase());
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						user=(UserInfo) list.get(0);
					}else{
						errors="第"+row.getRowNum()+"行导入失败，系统中没有这个ITCODE。请检查导入数据。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，ITCODE不能为空。请检查导入数据。";
					return errors;
				}
				
				
				
				HSSFCell cell4 = row.getCell((short) 4);
				if(cell4!=null){
					String isdeptTel= this.getCellStringValue(cell4);
					if(isdeptTel.equals("是")){
						deptTel="1";
						
						HSSFCell cell1 = row.getCell((short) 1);
						if(cell1!=null){
							telnumber=this.getCellStringValue(cell1);
							if(telnumber.trim().equals("")){
								errors="第"+row.getRowNum()+"行导入失败，电话号码不能为空。";
								return errors;
							}
						}else{
							errors="第"+row.getRowNum()+"行导入失败，电话号码不能为空。";
							return errors;
						}
						
					}else if(isdeptTel.equals("否")){
						deptTel="0";
					}else{
						errors="第"+row.getRowNum()+"行导入失败，是否为部门电话请填写‘是’或‘否’。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，是否为部门电话不能为空。";
					return errors;
				}
				
				HSSFCell cell6 = row.getCell((short) 6);
				if(cell6!=null){
					acctype= this.getCellStringValue(cell6);
					if(!acctype.equals("座机")){
						errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘座机’。";
						return errors;
					}
					Criteria c = super.getCriteria(AccountType.class);
					c.add(Restrictions.eq("name", acctype));
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						actype=(AccountType) list.get(0);
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘座机’。";
					return errors;
				}
				
				
				if(acctype.equals("座机")){
					 Criteria c = super.getCriteria(PersonFormalAccount.class);
					 c.add(Restrictions.eq("accountType", actype));
					 c.add(Restrictions.eq("accountowner", user));
					 c.add(Restrictions.eq("accountState", "1"));
					 c.add(Restrictions.eq("departTelephone",deptTel));
					 if(deptTel.equals("1")){
						 c.add(Restrictions.eq("telephoneNumber", telnumber));
					 }
					 List list=c.list();
					 if(list==null||list.size()==0){
						
						 if(deptTel.equals("0")){
							 DCContacts dc=findUniqueBy(DCContacts.class, "userNames", itcode.toLowerCase());
							 if(dc==null){
								 dc= saveUserToContacts(itcode);
							 }
							 dc.setTelephone("");
							 dc.setVoipPhone("");
							 save(dc);
							 errors=this.updateDCContacts(user.getEmployeeCode(), "", dc.getMobilePhone(), "", false);
							 Criteria c1=this.createCriteria(AccountModifyRecord.class);
							 c1.add(Restrictions.eq("itCode", user.getItcode()));
							 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
							 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
							 if(amr==null){
								 amr=new AccountModifyRecord();
							 }
							 amr.setItCode(user.getItcode());
							 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >");
							 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
							 amr.setModifyDate(new Date());
							 amr.setAccountManger(UserContext.getUserInfo().getItcode());
							 this.save(amr);
							 errors=errors+"删除一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName();
							 System.out.println("删除一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName());
						 }else{
							 errors=errors+"系统中没有"+telnumber+"这个部门电话";
							 System.out.println("系统中没有"+telnumber+"这个部门电话");
						 }
						 
						 
					 }else{
						 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
						 pfa.setAccountState("0");
						 this.save(pfa);
						 
						 if(deptTel.equals("0")){
							 DCContacts dc=findUniqueBy(DCContacts.class, "userNames", itcode.toLowerCase());
							 if(dc==null){
								 dc= saveUserToContacts(itcode);
							 }
							 dc.setTelephone("");
							 dc.setVoipPhone("");
							 save(dc);
							 errors=this.updateDCContacts(user.getEmployeeCode(), "", dc.getMobilePhone(), "", false);
							 Criteria c1=this.createCriteria(AccountModifyRecord.class);
							 c1.add(Restrictions.eq("itCode", user.getItcode()));
							 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
							 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
							 if(amr==null){
								 amr=new AccountModifyRecord();
							 }
							 amr.setItCode(user.getItcode());
							 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >");
							 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
							 amr.setModifyDate(new Date());
							 amr.setAccountManger(UserContext.getUserInfo().getItcode());
							 this.save(amr);
						 }
						 
						 errors=errors+"删除一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName()+"(帐号名不能修改)";
						 System.out.println("删除一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName());
					 }
				}else{
					errors=errors+"模板出错，与你选择的导入类型不匹配";
				}
				
				
			}else if(state.equals("1")){
						
				
				HSSFCell cell0 = row.getCell((short) 0);
				if(cell0!=null){
				 	itcode=this.getCellStringValue(cell0);
					Criteria c = super.getCriteria(UserInfo.class);
					c.add(Restrictions.eq("itcode", itcode).ignoreCase());
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						user=(UserInfo) list.get(0);
					}else{
						errors="第"+row.getRowNum()+"行导入失败，系统中没有这个ITCODE。请检查导入数据。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，ITCODE不能为空。请检查导入数据。";
					return errors;
				}
				HSSFCell cell1 = row.getCell((short) 1);
				if(cell1!=null){
					telnumber=this.getCellStringValue(cell1);
					if(telnumber.trim().equals("")){
						errors="第"+row.getRowNum()+"行导入失败，电话号码不能为空。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，电话号码不能为空。";
					return errors;
				}
				HSSFCell cell2 = row.getCell((short) 2);
				if(cell2!=null){
					 voip = this.getCellStringValue(cell2);
				}
				HSSFCell cell3 = row.getCell((short) 3);
				if(cell3!=null){
					remarkDesc = this.getCellStringValue(cell3);
				}
				HSSFCell cell4 = row.getCell((short) 4);
				if(cell4!=null){
					String isdeptTel= this.getCellStringValue(cell4);
					if(isdeptTel.equals("是")){
						deptTel="1";
					}else if(isdeptTel.equals("否")){
						deptTel="0";
					}else{
						errors="第"+row.getRowNum()+"行导入失败，是否为部门电话请填写‘是’或‘否’。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，是否为部门电话不能为空。";
					return errors;
				}
				HSSFCell cell5 = row.getCell((short) 5);
				if(cell5!=null){
					workSpace = this.getCellStringValue(cell5);
					Criteria c=super.getCriteria(WorkSpace.class);
					c.add(Restrictions.eq("name", workSpace));
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						 ws=(WorkSpace) list.get(0);
					}else{
						errors="第"+row.getRowNum()+"行导入失败，没有这个工作地点，请检查工作地点是否填写正确。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，工作地点不能为空。";
					return errors;
				}
				HSSFCell cell6 = row.getCell((short) 6);
				if(cell6!=null){
					acctype= this.getCellStringValue(cell6);
					if(!acctype.equals("座机")){
						errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘座机’。";
						return errors;
					}
					Criteria c = super.getCriteria(AccountType.class);
					c.add(Restrictions.eq("name", acctype));
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						actype=(AccountType) list.get(0);
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘座机’。";
					return errors;
				}
				if(acctype.equals("座机")){
					 Criteria c = super.getCriteria(PersonFormalAccount.class);
					 c.add(Restrictions.eq("accountType", actype));
					 c.add(Restrictions.eq("accountowner", user));
					 c.add(Restrictions.eq("accountState", "1"));
					 c.add(Restrictions.eq("departTelephone",deptTel));
					 if(deptTel.equals("1")){
						 c.add(Restrictions.eq("telephoneNumber", telnumber));
					 }
					 List list=c.list();
					 if(list==null||list.size()==0){
						 PersonFormalAccount pfa=new PersonFormalAccount();
						 pfa.setAccountName(itcode.toLowerCase());
						 pfa.setAccountType(actype);
						 pfa.setAccountowner(user);
						 pfa.setCreateDate(da);
						 pfa.setRemarkDesc(remarkDesc);
						 pfa.setAccountState("1");
						 pfa.setTelephoneNumber(telnumber);
						 pfa.setVoip(voip);
						 pfa.setWorkSpace(ws);
						 pfa.setDepartTelephone(deptTel);
						 
						 this.save(pfa);
						 if(deptTel.equals("0")){
							 DCContacts dc=findUniqueBy(DCContacts.class, "userNames", itcode.toLowerCase());
							 if(dc==null){
								 dc= saveUserToContacts(itcode);
							 }
							 dc.setTelephone(telnumber);
							 dc.setVoipPhone(voip);
							 save(dc);
							 errors=this.updateDCContacts(user.getEmployeeCode(), telnumber, dc.getMobilePhone(), voip, false);
						 }
						 Criteria c1=this.createCriteria(AccountModifyRecord.class);
						 c1.add(Restrictions.eq("itCode", user.getItcode()));
						 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
						 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
						 if(amr==null){
							 amr=new AccountModifyRecord();
						 }
						 amr.setItCode(user.getItcode());
						 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >");
						 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
						 amr.setModifyDate(new Date());
						 amr.setAccountManger(UserContext.getUserInfo().getItcode());
						 this.save(amr);
						 
						 
						 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName();
						 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName());
					 }else{
						 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
						 pfa.setAccountName(itcode.toLowerCase());
						 pfa.setRemarkDesc(remarkDesc);
						 pfa.setTelephoneNumber(telnumber);
						 pfa.setVoip(voip);
						 pfa.setWorkSpace(ws);
						 this.save(pfa);
						 
						 if(deptTel.equals("0")){
							 DCContacts dc=findUniqueBy(DCContacts.class, "userNames", itcode.toLowerCase());
							 if(dc==null){
								 dc= saveUserToContacts(itcode);
							 }
							 dc.setTelephone(telnumber);
							 dc.setVoipPhone(voip);
							 save(dc);
							 errors=this.updateDCContacts(user.getEmployeeCode(), telnumber, dc.getMobilePhone(), voip, false);
						 }
						 
						 Criteria c1=this.createCriteria(AccountModifyRecord.class);
						 c1.add(Restrictions.eq("itCode", user.getItcode()));
						 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
						 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
						 if(amr==null){
							 amr=new AccountModifyRecord();
						 }
						 amr.setItCode(user.getItcode());
						 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >");
						 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
						 amr.setModifyDate(new Date());
						 amr.setAccountManger(UserContext.getUserInfo().getItcode());
						 this.save(amr);
						 
						 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName()+"(帐号名不能修改)";
						 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+itcode.toLowerCase()+"的"+actype.getName());
					 }
				}else{
					errors=errors+"模板出错，与你选择的导入类型不匹配";
				}
				
				
			}else{
				errors="第"+row.getRowNum()+"行导入失败，状态位请填写‘0’或‘1’。";
				return errors;
			}
		}else{
			errors="第"+row.getRowNum()+"行导入失败，状态位不能为空。";
			return errors;
		}
		
		
			return errors;
		}
	
	private String readRowforMobileTelephone(HSSFRow row) throws Exception{
		String errors="";
		String itcode="";// itcode
		String telnumber="";//telnumber
		String startDate="";//补贴开始月份
		String remarkDesc="";//备注
		String payType="";//是否代缴
		String allowance="";//补贴标准
		String acctype="";//帐号类型
		String onlyforupdateDC="";//是否之为了更新通讯录
		Date da=DateUtil.getCurrentDate();
		UserInfo user=null;
		AccountType actype=null;
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){
			onlyforupdateDC= this.getCellStringValue(cell7);
			if(onlyforupdateDC.equals("是")){
				HSSFCell cell0 = row.getCell((short) 0);
				if(cell0!=null){
				 	itcode=this.getCellStringValue(cell0);
					Criteria c = super.getCriteria(UserInfo.class);
					c.add(Restrictions.eq("itcode", itcode).ignoreCase());
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						user=(UserInfo) list.get(0);
					}else{
						errors="第"+row.getRowNum()+"行导入失败，系统中没有这个ITCODE。请检查导入数据。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，ITCODE不能为空。请检查导入数据。";
					return errors;
				}
				HSSFCell cell1 = row.getCell((short) 1);
				if(cell1!=null){
					telnumber=this.getCellStringValue(cell1);
					if(telnumber.trim().equals("")){
						errors="第"+row.getRowNum()+"行导入失败，手机号码不能为空。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，手机号码不能为空。";
					return errors;
				}
				HSSFCell cell6 = row.getCell((short) 6);
				if(cell6!=null){
					acctype= this.getCellStringValue(cell6);
					if(!acctype.equals("手机")){
						errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘手机’。";
						return errors;
					}
					Criteria c = super.getCriteria(AccountType.class);
					c.add(Restrictions.eq("name", acctype));
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						actype=(AccountType) list.get(0);
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘手机’。";
					return errors;
				}
				if(acctype.equals("手机")){
					
					 DCContacts dc=findUniqueBy(DCContacts.class, "userNames", itcode.toLowerCase());
					 if(dc==null){
						dc= saveUserToContacts(itcode);
					 }
					 dc.setMobilePhone(telnumber);
					 save(dc);
					 errors=this.updateDCContacts(user.getEmployeeCode(), dc.getTelephone(), telnumber, dc.getVoipPhone(), false);
					
					 errors=errors+"修改"+user.getRealName()+user.getItcode()+"的通讯录成功";
					 System.out.println("修改"+user.getRealName()+user.getItcode()+"的通讯录成功");
				
				}else{
					errors=errors+"模板出错，与你选择的导入类型不匹配";
				}
				
			}else if(onlyforupdateDC.equals("否")){
				HSSFCell cell0 = row.getCell((short) 0);
				if(cell0!=null){
				 	itcode=this.getCellStringValue(cell0);
					Criteria c = super.getCriteria(UserInfo.class);
					c.add(Restrictions.eq("itcode", itcode).ignoreCase());
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						user=(UserInfo) list.get(0);
					}else{
						errors="第"+row.getRowNum()+"行导入失败，系统中没有这个ITCODE。请检查导入数据。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，ITCODE不能为空。请检查导入数据。";
					return errors;
				}
				HSSFCell cell1 = row.getCell((short) 1);
				if(cell1!=null){
					telnumber=this.getCellStringValue(cell1);
					if(telnumber.trim().equals("")){
						errors="第"+row.getRowNum()+"行导入失败，手机号码不能为空。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，手机号码不能为空。";
					return errors;
				}
				HSSFCell cell2 = row.getCell((short) 2);
				if(cell2!=null){
					startDate = this.getCellStringValue(cell2);
					
				}else{
					errors="第"+row.getRowNum()+"行导入失败，补贴开始月份不能为空。";
					return errors;
				}
				HSSFCell cell3 = row.getCell((short) 3);
				if(cell3!=null){
					remarkDesc = this.getCellStringValue(cell3);
				}
				HSSFCell cell4 = row.getCell((short) 4);
				if(cell4!=null){
					String isdeptTel= this.getCellStringValue(cell4);
					if(isdeptTel.equals("是")){
						payType="1";
					}else if(isdeptTel.equals("否")){
						payType="0";
					}else{
						errors="第"+row.getRowNum()+"行导入失败，是否代缴请填写‘是’或‘否’。";
						return errors;
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，是否代缴不能为空。";
					return errors;
				}
				HSSFCell cell5 = row.getCell((short) 5);
				if(cell5!=null){
					allowance = this.getCellStringValue(cell5);
					
				}else{
					errors="第"+row.getRowNum()+"行导入失败，补贴标准不能为空。";
					return errors;
				}
				HSSFCell cell6 = row.getCell((short) 6);
				if(cell6!=null){
					acctype= this.getCellStringValue(cell6);
					if(!acctype.equals("手机")){
						errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘手机’。";
						return errors;
					}
					Criteria c = super.getCriteria(AccountType.class);
					c.add(Restrictions.eq("name", acctype));
					List list=c.list();
					if(list!=null&&!list.isEmpty()){
						actype=(AccountType) list.get(0);
					}
				}else{
					errors="第"+row.getRowNum()+"行导入失败，帐号类型请填写‘手机’。";
					return errors;
				}
				if(acctype.equals("手机")){
					 Criteria c = super.getCriteria(MobileTelephoneApply.class);
					 c.add(Restrictions.eq("accountowner", user));
					 c.add(Restrictions.eq("accountState", "1"));
					 List list=c.list();
					 if(list==null||list.size()==0){
						 MobileTelephoneApply pfa=new MobileTelephoneApply();
						 pfa.setAccountType(actype);
						 pfa.setAccountowner(user);
						 pfa.setCreateDate(da);
						 pfa.setRemarkDesc(remarkDesc);
						 pfa.setAccountState("1");
						 pfa.setTelephone(telnumber);
						 pfa.setPayType(Integer.parseInt(payType));
						 pfa.setStartDate(startDate);
						 pfa.setAllowance(Double.parseDouble(allowance));
						 
						 this.save(pfa);
						 DCContacts dc=findUniqueBy(DCContacts.class, "userNames", itcode.toLowerCase());
						 if(dc==null){
							dc= saveUserToContacts(itcode);
						 }
						 dc.setMobilePhone(telnumber);
						 save(dc);
						 errors=this.updateDCContacts(user.getEmployeeCode(), dc.getTelephone(), telnumber, dc.getVoipPhone(), false);
						 
						 Criteria c1=this.createCriteria(AccountModifyRecord.class);
						 c1.add(Restrictions.eq("itCode", user.getItcode()));
						 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
						 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
						 if(amr==null){
							 amr=new AccountModifyRecord();
						 }
						 amr.setItCode(user.getItcode());
						 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >");
						 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
						 amr.setModifyDate(new Date());
						 amr.setAccountManger(UserContext.getUserInfo().getItcode());
						 this.save(amr);
						 
						 
						 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的"+actype.getName()+"帐号";
						 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的"+actype.getName()+"帐号");
					 }else{
						 MobileTelephoneApply pfa=(MobileTelephoneApply) list.get(0);
						 pfa.setRemarkDesc(remarkDesc);
						 pfa.setTelephone(telnumber);
						 pfa.setPayType(Integer.parseInt(payType));
						 pfa.setStartDate(startDate);
						 pfa.setAllowance(Double.parseDouble(allowance));
						 
						 this.save(pfa);
						 
						 DCContacts dc=findUniqueBy(DCContacts.class, "userNames", itcode.toLowerCase());
						 if(dc==null){
							dc= saveUserToContacts(itcode);
						 }
						 dc.setMobilePhone(telnumber);
						 save(dc);
						 errors=this.updateDCContacts(user.getEmployeeCode(), dc.getTelephone(), telnumber, dc.getVoipPhone(), false);
						 
						 Criteria c1=this.createCriteria(AccountModifyRecord.class);
						 c1.add(Restrictions.eq("itCode", user.getItcode()));
						 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
						 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
						 if(amr==null){
							 amr=new AccountModifyRecord();
						 }
						 amr.setItCode(user.getItcode());
						 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >");
						 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
						 amr.setModifyDate(new Date());
						 amr.setAccountManger(UserContext.getUserInfo().getItcode());
						 this.save(amr);
						 
						 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的"+actype.getName()+"帐号";
						 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的"+actype.getName()+"帐号");
					 }
				}else{
					errors=errors+"模板出错，与你选择的导入类型不匹配";
				}
			}else{
				errors="第"+row.getRowNum()+"行导入失败，是否只更新通讯录请填写‘是’或‘否’。";
				return errors;
			}
		}else{
			errors="第"+row.getRowNum()+"行导入失败，是否只更新通讯录不能为空。";
			return errors;
		}
		
			return errors;
		}
	
	private String readRowforRemote(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//令牌卡号
		String value8="";//操作说明
		String value9="";//到期日期
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		Date endDate=null;
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		HSSFCell cell8 = row.getCell((short) 8);
		if(cell8!=null){//
			 value8 = this.getCellStringValue(cell8);
		}
		HSSFCell cell9 = row.getCell((short) 9);
		if(cell9!=null){//
			 value9 = this.getCellStringValue(cell9);
			 endDate=DateUtil.convertStringToDate(value9);
		}
		if(value2==5){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 c.add(Restrictions.eq("accountState", "1"));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCardNumber(value7);
				 pfa.setEndDate(endDate);
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				// pfa.setComments(value8);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次");
			 }
		}else if(value2==20){
			 Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 c.add(Restrictions.eq("accountState", "1"));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 SpecialAccount pfa=new SpecialAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountNowUser(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCardNumber(value7);
				 pfa.setEndDate(endDate);
				 //pfa.setComments(value8);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.SPECAILACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value8);
				 amr.setAccountFlag(AccountModifyRecord.SPECAILACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"已存在！同一类型帐号只能申请一次");
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	
	private String readRowforUpdateRemote(HSSFRow row) throws Exception{	
		String errors="";
		
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		Long value4=null;//领卡地点
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";//令牌卡号
		
		String value8="";//到期日期
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		
		
		UserInfo user=null; 
		AccountType actype=null;
		Date endDate=null;
		AR_DrawSpace drawSpace=null;
	
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			 String space = this.getCellStringValue(cell4);
			 Criteria c = super.getCriteria(AR_DrawSpace.class);
			 c.add(Restrictions.eq("name", space));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AR_DrawSpace ds=(AR_DrawSpace) list.get(0);
					value4=ds.getId();
					drawSpace=ds;
				}
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		
		HSSFCell cell8 = row.getCell((short) 8);
		if(cell8!=null){//
			 value8 = this.getCellStringValue(cell8);
			 endDate=DateUtil.convertStringToDate(value8);
		}
		if(value2==5){
			Criteria c = super.getCriteria(PersonFormalAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 c.add(Restrictions.eq("accountState", "1"));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个");
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
		
				 pfa.setDrawSpace(drawSpace);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCardNumber(value7);
				 pfa.setEndDate(endDate);
				
				 this.save(pfa);
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors="修改"+errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				
				 
				
				
			 }
		}else if(value2==20){
			 Criteria c = super.getCriteria(SpecialAccount.class);
			 c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountNowUser", user));
			 c.add(Restrictions.eq("accountState", "1"));
			 List list=c.list();
			 
			 
			 if(list==null||list.size()==0){
				 errors=errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个!";
				 System.out.println(user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"不存在！需要新建一个");
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 SpecialAccount pfa=(SpecialAccount) list.get(0);
		
				 pfa.setDrawSpace(drawSpace);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCardNumber(value7);
				 pfa.setEndDate(endDate);
				
				 this.save(pfa);
				 
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors="修改"+errors+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("修改"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				
			 }
			 
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}
	private String readRowforDomain(HSSFRow row) throws Exception{
		String errors="";
		String value0="";// 姓名
		String value1="";//itcode
		Long value2=null;//帐号类型
		String value3="";//帐号名
		String value4=null;//特殊需求
		String value5="";//备注
		Integer value6=1;//零一标志位
		String value7="";
		//Integer orderFlag = null;
		Long userId=null;//用户Id
		Date da=DateUtil.getCurrentDate();
		String applyDate=DateUtil.convertDateToString(da);
		HSSFCell cell0 = row.getCell((short) 0);
		UserInfo user=null; 
		AccountType actype=null;
		
		if(cell0!=null){
			 value0 = this.getCellStringValue(cell0);
		}
		HSSFCell cell1 = row.getCell((short) 1);
		if(cell1!=null){
			value1=this.getCellStringValue(cell1);
			String valueid=this.getCellStringValue(cell1);
			Criteria c = super.getCriteria(UserInfo.class);
			c.add(Restrictions.eq("itcode", value1).ignoreCase());
			List list=c.list();
			if(list!=null&&!list.isEmpty()){
				UserInfo dp=(UserInfo) list.get(0);
				user=dp;
				userId=dp.getId();
			}
		}
		
		
		HSSFCell cell2 = row.getCell((short) 2);
		if(cell2!=null){
			 String value = this.getCellStringValue(cell2);
			 Criteria c = super.getCriteria(AccountType.class);
			 c.add(Restrictions.eq("name", value));
			 List list=c.list();
				if(list!=null&&!list.isEmpty()){
					AccountType at=(AccountType) list.get(0);
					value2=at.getId();
					actype=at;
				}
		}
		HSSFCell cell3 = row.getCell((short) 3);
		if(cell3!=null){
			 value3 = this.getCellStringValue(cell3);
		}
		HSSFCell cell4 = row.getCell((short) 4);
		if(cell4!=null){
			value4= this.getCellStringValue(cell4);
		}
		HSSFCell cell5 = row.getCell((short) 5);
		if(cell5!=null){
			 value5 = this.getCellStringValue(cell5);
		}
		HSSFCell cell6 = row.getCell((short) 6);
		if(cell6!=null){//1表示有效，0表示无效
			 value6 = Integer.parseInt(this.getCellStringValue(cell6));
		}
		HSSFCell cell7 = row.getCell((short) 7);
		if(cell7!=null){//
			 value7 = this.getCellStringValue(cell7);
		}
		
		if(value2==1){
			 Criteria c = super.getCriteria(PersonFormalAccount.class);
			// c.add(Restrictions.eq("accountName", value3));
			 c.add(Restrictions.eq("accountType", actype));
			 c.add(Restrictions.eq("accountowner", user));
			 List list=c.list();
			 if(list==null||list.size()==0){
				 PersonFormalAccount pfa=new PersonFormalAccount();
				 pfa.setAccountName(value3);
				 pfa.setAccountType(actype);
				 pfa.setAccountowner(user);
				 pfa.setCreateDate(da);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 pfa.setCostCenterCode(user.getCostCenterCode());
				 pfa.setSameMailDept(user.getSameMailDept());
				 pfa.setDepartment(user.getDepartment());
				// pfa.setComments(value7);
				 if(user.getMailServer()!=null){
					 pfa.setMailServer(user.getMailServer().getName()); 
				 }
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName();
				 System.out.println("新建一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
				 //throw new ServiceException(user.getRealName()+user.getItcode()+"的"+value9+"的"+actype.getName()+"已存在！");
			 }else{
				 PersonFormalAccount pfa=(PersonFormalAccount) list.get(0);
				 pfa.setRightsDesc(value4);
				 pfa.setRemarkDesc(value5);
				 pfa.setAccountState(value6.toString());
				 //pfa.setComments(pfa.getComments()+value7);
				 this.save(pfa);
				 
				 Criteria c1=this.createCriteria(AccountModifyRecord.class);
				 c1.add(Restrictions.eq("itCode", user.getItcode()));
				 c1.add(Restrictions.eq("accountFlag", AccountModifyRecord.PERSONACCOUNT));
				 AccountModifyRecord amr= (AccountModifyRecord) c1.uniqueResult();
				 if(amr==null){
					 amr=new AccountModifyRecord();
				 }
				 amr.setItCode(user.getItcode());
				 amr.setComment(amr.getComment()+"&nbsp;&nbsp;&nbsp;"+amr.getAccountManger()+"&nbsp;&nbsp;&nbsp;"+amr.getModifyDate()+"&nbsp;<BR >"+value7);
				 amr.setAccountFlag(AccountModifyRecord.PERSONACCOUNT);
				 amr.setModifyDate(new Date());
				 amr.setAccountManger(UserContext.getUserInfo().getItcode());
				 this.save(amr);
				 
				 errors=errors+"修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName()+"(帐号名不能修改)";
				 System.out.println("修改一个"+user.getRealName()+user.getItcode()+"的帐号名为"+value3+"的"+actype.getName());
			 }
		}else{
			errors=errors+"模板出错，与你选择的导入类型不匹配";
		}
			return errors;
		}

	
	public String getCellStringValue(HSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
				cellValue = " ";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date aDate = cell.getDateCellValue();
				cellValue = DateUtil.convertDateToString(aDate);
			} else {
				double doubleValue = cell.getNumericCellValue();
				DecimalFormat df = new DecimalFormat("###");
				String dfValue = df.format(Double.valueOf(doubleValue));
				cellValue = dfValue; // String.valueOf(cell.getNumericCellValue());
			}

			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = " ";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			boolean boolValue= cell.getBooleanCellValue();
			cellValue = String.valueOf(boolValue);
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}
	@SuppressWarnings("unchecked")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}

	public List findSpecailAccountByType(String accountType) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.accountType",accountType ));
		c.add(Restrictions.eq("accountState","1"));
		return c.list();
	}

	public AccountModifyRecord findModifyRecord(String itCode,
			Integer accountFlag) {
		Criteria c = super.createCriteria(AccountModifyRecord.class);
		c.add(Restrictions.eq("itCode",itCode));
		c.add(Restrictions.eq("accountFlag",accountFlag));
		return (AccountModifyRecord) c.uniqueResult();
	}

	public List findAllPersonAccountByAccountType(String accountType) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.accountType",accountType ));
		c.add(Restrictions.eq("accountState","1"));
		return c.list();
	}

	public SpecialAccount findSpecailAccountByAccountName(String accountType,
			String accountName) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.accountType",accountType ));
		c.add(Restrictions.eq("accountName", accountName));
		c.add(Restrictions.eq("accountState","1"));
		return (SpecialAccount) c.uniqueResult();
	}

	public AccountApplyMainTable findUserApply(ServiceItemProcess serviceItemProcess,
			UserInfo userInfo, String processType) {
		Criteria c = super.createCriteria(AccountApplyMainTable.class);
		c.add(Restrictions.eq("serviceItemProcess",serviceItemProcess));
		c.add(Restrictions.eq("applyUser",userInfo));
		c.add(Restrictions.eq("processType",Integer.parseInt(processType)));
		c.add(Restrictions.eq("status",1));
		return (AccountApplyMainTable) c.uniqueResult();
	}

	public List<AppAdministrator> findAppAdministratorByUser(UserInfo userInfo) {
		Criteria c = super.createCriteria(AppAdministrator.class);
		c.add(Restrictions.eq("userInfo",userInfo));
		List<AppAdministrator> list = c.list();
		for(AppAdministrator appAdmin : list){
			Criteria cc = super.createCriteria(ConfigItem.class);
			c.add(Restrictions.eq("csin",appAdmin.getCisn()));
			c.add(Restrictions.eq("status", ConfigItem.VALID_STATUS));
			if(cc.list().isEmpty()){
				list.remove(appAdmin);
			}
		}
		return list;
	}

	public Page findByPageQuerySameMailDept(
			String sameMailDeptName, int pageNo) {
		Criteria c = super.createCriteria(SameMailDept.class);
		c.add(Restrictions.like("name", "%"+sameMailDeptName+"%"));
		Page page=super.pagedQuery(c, pageNo, 10);
		return page;
	}

	public List<SystemAppAdmin> findAllAppAdministratorByUser(
			String itCode) {
		Criteria c = super.createCriteria(SystemAppAdmin.class);
		c.add(Restrictions.eq("itCode",itCode));
		return c.list();
	}

	public void runNewAppAdmin(AccountNewAppAdmin accountNewAppAdmin) {
/*      remove by duxh in 2010-02-22 -----begin-----注掉无用方法所产生的编译错误
  	
  		AccountApplyMainTable amt = accountNewAppAdmin.getAmt();
		ConfigItem newCi = accountNewAppAdmin.getConfigItem();
		UserInfo oldUser = amt.getApplyUser();//申请人？？
		List<AppAdministrator> administrators = this.findAppAdministratorByUser(oldUser);
		List<ConfigItem> cilist = new ArrayList();
		List<CIRelationShip> cirlist = new ArrayList();//原配置项关联的配置项关系
		for (AppAdministrator admin : administrators) {
			String cisn=admin.getCisn();
			ConfigItem ci=(ConfigItem) super.findUniqueBy(ConfigItem.class, "cisn", cisn);
			cirlist.addAll(super.findBy(CIRelationShip.class, "configItem", ci));
		}
		for(CIRelationShip cir : cirlist){
			CIRelationShip newCir = (CIRelationShip) BeanUtils.instantiateClass(CIRelationShip.class);
			BeanUtils.copyProperties(cir, newCir);
			newCir.setId(null);
			newCir.setConfigItem(newCi);
			newCir = (CIRelationShip) super.save(newCir);//生成对应的新配置项关系
			List<CIRelationShip> childCRs = super.findBy(CIRelationShip.class, "parentRelationShip", cir);
			for(CIRelationShip childCR : childCRs){
				childCR.setParentRelationShip(newCir);
				super.save(childCR);
			}
		}  
		
		remove by duxh in 2010-02-22 -----end-----*/
	}

	public String importAccountDateExcel(String realPath) {
		// TODO Auto-generated method stub
		return null;
	}

	public MailForwardApply findMailForwardByName(String accountName) {
		Criteria c = super.createCriteria(MailForwardApply.class);
		c.add(Restrictions.eq("accountName", accountName));
		c.add(Restrictions.eq("accountState","1"));
		return (MailForwardApply) c.uniqueResult();
	}

	public Page findSystemAppAdminByPage(String itCode, int pageNo, int pageSize) {
		Criteria c =super.createCriteria(SystemAppAdmin.class);
		if(itCode != null&&itCode!=""){
			c.add(Restrictions.like("itCode", itCode, MatchMode.ANYWHERE));//修改为模糊查询
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}
	
	
	public Page findSpecailAccountByUser(String accountType,
			UserInfo applyUser,int pageNo,int pageSize) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType ));
		//c.add(Restrictions.eq("accountType",at));
		c.add(Restrictions.eq("accountNowUser", applyUser));
		c.add(Restrictions.eq("accountState","1"));
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}
	public Page findWin7AccountByUser(Long accountId,
			UserInfo applyUser,int pageNo,int pageSize) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		if(accountId!=null){
			c.add(Restrictions.eq("id", accountId));
		}else{
			c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
			c.add(Restrictions.eq("accountType.id",24L));
			c.add(Restrictions.eq("accountNowUser", applyUser));
			c.add(Restrictions.eq("accountState","1"));
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}

	public Page findByPageQueryHRSAccount(UserInfo userInfo, int pageNo,int pageSize) {
		
		Criteria c = super.createCriteria(HRSAccountApply.class);
		c.add(Restrictions.eq("applyUser", userInfo));
		c.add(Restrictions.eq("accountState","1"));
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}

    public Page findByPageQueryMailGroup(String name, int pageNo, int pageSize) {
		Criteria c =super.createCriteria(MailGroup.class);
		if(name!= null){
			c.add(Restrictions.like("groupNewName", name,MatchMode.ANYWHERE));
		}
		c.add(Restrictions.eq("accountState", "1"));
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}
	public DCContacts saveUserToContacts(String itcode) {
		DCContacts employee = null;
		if(itcode!= null&&itcode!= ""){
			UserInfo curUser = this.findUniqueBy(UserInfo.class, "itcode", itcode.toUpperCase());
			employee = new DCContacts();
			employee.setEmployeeCode(curUser.getEmployeeCode());
			employee.setUserNames(curUser.getUserName());
			employee.setRealName(curUser.getRealName());
			employee.setItcode(curUser.getItcode());
			employee.setDepartCode(curUser.getDepartCode());
			employee.setDepartment(curUser.getDepartment());
			employee.setPlatform(curUser.getPlatform());
			employee.setEmail(curUser.getEmail());
			employee.setTelephone(curUser.getTelephone());
			employee.setMobilePhone(curUser.getMobilePhone());
			employee.setCostCenterCode(curUser.getCostCenterCode());
			employee.setWorkSpace(curUser.getWorkSpace());
			employee.setUserType(curUser.getUserType());
			employee.setPersonnelScope(curUser.getPersonnelScope());
			employee.setSameMailDept(curUser.getSameMailDept());
			//modify liuying for 修改通讯录邮件服务器信息的跟新 at 20100514 start
			//employee.setMailServer(curUser.getMailServer()==null?null:curUser.getMailServer().getId().toString());
			employee.setMailServer(curUser.getMailServer()==null?null:curUser.getMailServer().getName());
			//modify liuying for 修改通讯录邮件服务器信息的跟新 at 20100514 end
			try{
				super.save(employee);
			}catch(Exception e){
				System.out.println("保存通讯录信息失败！");
				e.printStackTrace();
			}
		}
		return employee;
	}

	public Page getAllTelephone(String number, int pageNo, int pageSize) {

		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.add(Restrictions.eq("accountType.id",Long.valueOf(15) ));
		c.add(Restrictions.eq("accountState","1"));
		if(number!=null){
			//c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
			c.add(Restrictions.like("telephoneNumber", number, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	
	}
	public List<PersonFormalAccount> findAllTelephoneAccount(String accountType,
			String telephoneNumber) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType));
		c.add(Restrictions.eq("telephoneNumber", telephoneNumber));
		return  c.list();
	}

	public List<PersonFormalAccount> getAllTelephoneAccount(String accountType,
			String telephoneNumber) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType));
		c.add(Restrictions.eq("telephoneNumber", telephoneNumber));
		c.add(Restrictions.eq("accountState","1"));
		return  c.list();
	}

	public List<SpecialAccount> findAllSpecailAccountByAccountName(
			String accountType, String accountName) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.accountType",accountType ));
		c.add(Restrictions.eq("accountName", accountName));
		return  c.list();
	}
	
	public Page findSpecailAccountByUsername(String accountType,String ownerName,String confirmUserName,int pageNo,int pageSize) {
		Criteria c = super.createCriteria(SpecialAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.accountType",accountType ));
		c.add(Restrictions.eq("accountState","1"));
		if(ownerName!=null&&!"".equals(ownerName)){
			c.createAlias("this.accountNowUser", "accountNowUser").setFetchMode("accountNowUser", FetchMode.JOIN);
			c.add(Restrictions.like("accountNowUser.userName", ownerName, MatchMode.ANYWHERE));
		}
		if(confirmUserName!=null&&!"".equals(confirmUserName)){
			c.createAlias("this.confirmUser", "confirmUser").setFetchMode("confirmUser", FetchMode.JOIN);
			c.add(Restrictions.like("confirmUser.userName", confirmUserName, MatchMode.ANYWHERE));
		}	
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public List<PersonFormalAccount> getAllDeptTelephoneAccount(
			String accountType, String telephoneNumber) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType));
		c.add(Restrictions.eq("departTelephone", "1"));
		c.add(Restrictions.eq("telephoneNumber", telephoneNumber));
		c.add(Restrictions.eq("accountState","1"));
		return  c.list();
	}

	public Page getAllDeptTelephone(String number, int pageNo, int pageSize) {

		Criteria c = super.createCriteria(PersonFormalAccount.class);
		c.add(Restrictions.eq("accountType.id",Long.valueOf(15) ));
		c.add(Restrictions.eq("accountState","1"));
		c.add(Restrictions.eq("departTelephone", "1"));
		if(number!=null){
			//c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
			c.add(Restrictions.like("telephoneNumber", number, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	
	}

	public Page findByPageQueryWin7PlatForm(String name, int pageNo,
			int pageSize) {
		Criteria c =super.createCriteria(Win7PlatForm.class);
		if(name!= null){
			c.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}
	public Page findByPageQueryDeviceType(String name, int pageNo,
			int pageSize) {
		Criteria c =super.createCriteria(DeviceType.class);
		if(name!= null){
			c.add(Restrictions.like("deviceName", name,MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}

	public List<AccountModifyDesc> findModifyDescByAccountId(String accountId,
			String string) {
		Criteria c = super.createCriteria(AccountModifyDesc.class);
		c.add(Restrictions.eq("accountId",accountId));
		c.add(Restrictions.eq("accountFlag",Integer.parseInt(string)));
		return  c.list();
	
	}

	public Page findByPageQueryAllSameMailDept(int pageNo) {
		Criteria c =super.createCriteria(SameMailDept.class);
		c.add(Restrictions.isNull("endDate"));
		Page page = super.pagedQuery(c, pageNo,10);
		return page;
	}
	
	public Page findByPageQuerySameMailDeptByName(String sameMailDeptName,
			int pageNo) {
		Criteria c = super.createCriteria(SameMailDept.class);
		c.add(Restrictions.like("name", "%"+sameMailDeptName+"%"));
		c.add(Restrictions.isNull("endDate"));
		Page page=super.pagedQuery(c, pageNo, 10);
		return page;
	
	}
	private String updateDCContacts(String employeeCode,String telephone,String mobilePhone,String voipPhone,boolean isDelete){
		UserInfo user=(UserInfo) findUniqueBy(UserInfo.class, "employeeCode",employeeCode);
		if(user!=null){
			user.setTelephone(telephone);
			user.setMobilePhone(mobilePhone);
			save(user);
		}
		String error="";
		HrInfoService hs = new HrInfoServiceLocator();
		try {
			HrInfoServiceSoap_PortType hp = hs.getHrInfoServiceSoap();
			int reslut = hp.updatePhone(employeeCode,telephone,mobilePhone,voipPhone,isDelete);
			if(reslut==1){
				System.out.println("更新HR更新通讯录[员工编号："+employeeCode+" ][手机:"+mobilePhone+"][ 座机:"+telephone+" ][voip:"+voipPhone+"]信息成功！");
				error="更新HR更新通讯录[员工编号："+employeeCode+" ][手机:"+mobilePhone+"][ 座机:"+telephone+" ][voip:"+voipPhone+"]信息成功！";
			}else{
				System.out.println("更新HR更新通讯录"+employeeCode+"信息失败！");
				error="更新通讯录失败！";
			}
		} catch (ServiceException e) {
			System.out.println("创建HR更新通讯录webservice调用失败！");
			error="更新通讯录失败！";
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("运行HR更新通讯录webservice调用失败！");
			error="更新通讯录失败！";
			e.printStackTrace();
		}
		return error;
	}

	public Page getAllMailForward(String name, int pageNo, int pageSize) {

		Criteria c = super.createCriteria(MailForwardApply.class);
		c.add(Restrictions.eq("accountState","1"));
		if(name!=null){
			c.add(Restrictions.like("accountName", name, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	
	}

	public List<ServiceItemApplyAuditHis> findServiceItemApplyAuditHis(
			String dataId, String serviceItemId) {
		Criteria c = super.createCriteria(ServiceItemApplyAuditHis.class);
		c.add(Restrictions.eq("requirementId", Long.parseLong(dataId)));
		c.add(Restrictions.eq("serviceItem.id", Long.parseLong(serviceItemId)));
		
		return c.list();
	}
	public Page findAccountApply(String applyUser, String delegateApplyUser,
			String serviceItemProcess,String applyname, int pageNo, int pageSize) {
		Criteria c = super.createCriteria(AccountApplyMainTable.class);
		if(applyname!=null&&!"".equals(applyname)){
			c.add(Restrictions.like("name", applyname,MatchMode.ANYWHERE));
		}	
		if(applyUser!=null&&!"".equals(applyUser)){
			c.createAlias("this.applyUser", "applyUser").setFetchMode("applyUser", FetchMode.JOIN);
			c.add(Restrictions.eq("applyUser.userName", applyUser));
		}
		if(delegateApplyUser!=null&&!"".equals(delegateApplyUser)){
			c.createAlias("this.delegateApplyUser", "delegateApplyUser").setFetchMode("delegateApplyUser", FetchMode.JOIN);
			c.add(Restrictions.eq("delegateApplyUser.userName", delegateApplyUser));
		}	
		if(serviceItemProcess!=null&&!"".equals(serviceItemProcess)){
			c.add(Restrictions.eq("serviceItemProcess.id", Long.parseLong(serviceItemProcess)));
		}	
		c.add(Restrictions.ne("status", 0));
		c.addOrder(Order.desc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	public Page selectWWWDayDetail(String calendar,int start,int size) {
		List list=new ArrayList();
		try {
			Connection con=DataBaseConnection.getMySqlConnection();
			String dataSql=" select * from squidlog_"+calendar+
						   " where itcode = ? "+
						   " order by bytes desc limit ? , ? ";
			String countSql=" select count(*) from squidlog_"+calendar+
							" where itcode = ? ";
			PreparedStatement countps=con.prepareStatement(countSql);
			countps.setString(1, UserContext.getUserInfo().getUserName());
			ResultSet countrs=countps.executeQuery();
			int totalCount=0;
			if(countrs.next()){
				totalCount=countrs.getInt(1);
			}
			DataBaseConnection.closeConnection(countrs, countps, null);
			if(totalCount>0){
				PreparedStatement ps=con.prepareStatement(dataSql);
				ps.setString(1, UserContext.getUserInfo().getUserName());
				ps.setInt(2, Page.getStartOfPage(start,size));
				ps.setInt(3, size);
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					Object[] obj=new Object[4];
					String remotehost=rs.getString("remotehost");
					obj[0]=remotehost;
					String unixtime=rs.getString("unixtime");
					String time = unixtime.substring(0,unixtime.indexOf("."));
					//obj[1]=unixtime;
					Long timestamp = Long.parseLong(time)*1000;
					obj[1] = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date(timestamp));
					String url=rs.getString("URL");
					obj[2]=url;
					long bytes=rs.getLong("bytes");
					obj[3]=bytes;
					list.add(obj);
				}
				DataBaseConnection.closeConnection(rs, ps, null);
			}
			DataBaseConnection.closeConnection(null, null, con);
			return new Page(Page.getStartOfPage(start,size),totalCount,size,list);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	public long selectWWWMonth(String yearAndMonth){
		try {
			long count=0;
			Connection con=DataBaseConnection.getMySqlConnection();
			String dataSql=" select sum(bytes) from visitstat_"+yearAndMonth+
						   " where itcode = ? ";
			PreparedStatement ps=con.prepareStatement(dataSql);
			ps.setString(1, UserContext.getUserInfo().getUserName());
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				count=rs.getLong(1);
			}
			DataBaseConnection.closeConnection(rs, ps, con);
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	
	}
	public List<Object[]> selectWWWMonthDetail(String yearAndMonth){
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Connection con=DataBaseConnection.getMySqlConnection();
			String dataSql=" select sum(bytes),max(visitdate) from visitstat_"+yearAndMonth+
			" where itcode = ? " +
			" group by visitdate";
			PreparedStatement ps=con.prepareStatement(dataSql);
			ps.setString(1, UserContext.getUserInfo().getUserName());
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Object[] obj=new Object[2];
				//obj[0]=rs.getDouble(1)/1024/1024;
				BigDecimal bd = new BigDecimal(rs.getDouble(1)/1024/1024);   
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP); 
				obj[0]=bd.toString();
				obj[1]=rs.getString(2);
				list.add(obj);
			}
			DataBaseConnection.closeConnection(rs, ps, con);
		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DaoException(e.getMessage());
		} finally{
			return list;
		}
		
	}
	public PersonFormalAccount findPersonAccount(String accountType,
			String accountName) {
		Criteria c = super.createCriteria(PersonFormalAccount.class);
		if(accountType.equals("座机")){
			c.add(Restrictions.eq("departTelephone","0"));
		}
		c.createAlias("this.accountType", "accountType").setFetchMode("accountType", FetchMode.JOIN);
		c.add(Restrictions.eq("accountType.name",accountType ));
		//c.add(Restrictions.eq("accountType",at));
		c.add(Restrictions.eq("accountName", accountName));
		c.add(Restrictions.eq("accountState","1"));
		return (PersonFormalAccount) c.uniqueResult();
	}
}
