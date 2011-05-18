package com.zsgj.itil.account.webservice.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import net.sf.json.JSONObject;


import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.MailServer;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.SameMailDept;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.entity.UserRole;
import com.zsgj.info.framework.security.entity.WorkSpace;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.itil.account.entity.AccountType;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.account.service.AccountService;
import com.zsgj.itil.account.webservice.ITILToHRJobData;
import com.zsgj.itil.account.webservice.SenseServicesUitl;
import com.zsgj.itil.config.extlist.entity.MailVolume;
import com.zsgj.itil.config.extlist.entity.TelephoneAudit;
import com.zsgj.itil.config.extlist.entity.WWWScanType;
import com.zsgj.itil.require.entity.AccountApplyMainTable;
import com.zsgj.itil.service.entity.Constants;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
//remove SenseServicesUitl webservice tongjp
//public class ITILToHRJobDataImpl extends BaseDao implements ITILToHRJobData{
//	 private static Service baseBervice = (Service) ContextHolder.getBean("baseService");
//	 private MetaDataManager metaDataManager = (MetaDataManager)ContextHolder.getBean("metaDataManager");
//	 private SystemMainTableService systemMainTableService = (SystemMainTableService)ContextHolder.getBean("systemMainTableService");
//	 private ProcessService ps = (ProcessService)ContextHolder.getBean("processService");
//	 private AccountService accountService = (AccountService) ContextHolder.getBean("accountService");
//	 public String getSameMailDept(int pageNo) {
//	    //Page sameMailDept=baseBervice.findByPagedQuery(SameMailDept.class, "id", true, pageNo, 10);
//	    Page sameMailDept=accountService.findByPageQueryAllSameMailDept(pageNo);
//	    Long total = sameMailDept.getTotalCount();// 这是查询出所有的记录
//	    List<SameMailDept> sameMD=sameMailDept.list();
//	    String json="";
//	    for(SameMailDept smd:sameMD){
//			Long id=smd.getId();
//			String name=smd.getName();
//			json += "{\"id\":\"" + id + "\",\"name\":\"" + name
//			+ "\" },";
//		}
//		if (json.length() == 0) {
//			json = "{success:true,rowCount:" + "1" + ",data:["
//					+ json.substring(0, json.length()) + "]}";
//		} else {
//			json = "{success:true,rowCount:" + total + ",data:["
//					+ json.substring(0, json.length() - 1) + "]}";
//		}
//		return json;
//	}
//
//	public String getWorkSpace() {
//		
//		List<WorkSpace> workSpaces=baseBervice.findAll(WorkSpace.class);
//		Integer total = workSpaces.size();// 这是查询出所有的记录
//		String json="";
//		for(WorkSpace ws:workSpaces){
//			Long id=ws.getId();
//			String workSpace=ws.getName()+"#"+ws.getMailServer();
//			json += "{\"id\":\"" + id + "\",\"workSpace\":\"" + workSpace
//			+ "\" },";
//		}
//		if (json.length() == 0) {
//			json = "{success:true,rowCount:" + "1" + ",data:["
//					+ json.substring(0, json.length()) + "]}";
//		} else {
//			json = "{success:true,rowCount:" + total + ",data:["
//					+ json.substring(0, json.length() - 1) + "]}";
//		}
//		return json;
//	}
//
//	public synchronized String hRJobStartItilAccount(String josnData) {
//		 Date currentDate = DateUtil.getCurrentDate();
//		 String json="";
//		 JSONObject jsonObj = JSONObject.fromObject(josnData);
//		 UserInfo user=(UserInfo) baseBervice.findUnique(UserInfo.class, "userName", "admin");
//	     String applyUserItCode=jsonObj.getString("applyUser");
//	     UserInfo applyUser=(UserInfo) baseBervice.findUnique(UserInfo.class, "userName", applyUserItCode);
//	   //add by lee for 如果本地无审批人信息，直接去R3去人员信息 in 20100119 begin
//		 if(applyUser==null){
////			 SAPExecute sap=new SAPExecute();
////				Map userMap=sap.getUserInfo(null, applyUserItCode.toUpperCase());
//			 SenseServicesUitl ssUtil = new SenseServicesUitl();
//			 Map userMap = ssUtil.getUserInfo(applyUserItCode.toUpperCase());
//				if(userMap.isEmpty())
//					json="{success:false}";
//				else{
//					userMap.put("password", "000000");
//					userMap.put("enabled", 1);
//					userMap.put("isLock", 0);
//					userMap.put("specialUser", 0);
//					applyUser=(UserInfo) metaDataManager.saveEntityData(UserInfo.class, userMap);
//				}
//		 }
//		//add by lee for 如果本地无审批人信息，直接去R3去人员信息 in 20100119 end
//	     ServiceItemProcess serviceItemProcess=(ServiceItemProcess) baseBervice.find(ServiceItemProcess.class, "195");
//	     AccountApplyMainTable aam=accountService.findUserApply(serviceItemProcess, applyUser, "0");
//	     if(aam!=null){
//	    	 json = "{success:false}";	
//	     }
//	     else{
//	    	 //add by liuying at 20100302 for 验证是否存在可用的邮件和域账号 start
//	    	 List<PersonFormalAccount> accs =accountService.findAllPersonAccount(applyUser);
//	    	 if(!accs.isEmpty()){
//	    		 for(PersonFormalAccount p:accs){
//	    			 if(p.getAccountType().getAccountType().equals("DomainAccount")||p.getAccountType().getAccountType().equals("MailAccount")){
//	    				 json = "{success:false}";
//	    			 }
//	    		 }
//	    	 }
//	    	 if(!json.equals("{success:false}")){
//	    		//add by liuying at 20100302 for 验证是否存在可用的邮件和域账号 end
//	    		 String delegateApplyUserItCode=jsonObj.getString("delegateApplyUser");//代申请人信息
//	    		 UserInfo delegateApplyUser=(UserInfo) baseBervice.findUnique(UserInfo.class, "itcode", delegateApplyUserItCode.toUpperCase());
//	    		 //add by lee for 如果本地无审批人信息，直接去R3去人员信息 in 20100119 begin
//	    		 if(delegateApplyUser==null){
////	    			 SAPExecute sap=new SAPExecute();
////	 				 Map userMap=sap.getUserInfo(null, applyUserItCode.toUpperCase());
//		 			 SenseServicesUitl ssUtil = new SenseServicesUitl();
//		 			 Map userMap = ssUtil.getUserInfo(applyUserItCode.toUpperCase());
//	    				if(userMap.isEmpty())
//	    					json="{success:false}";
//	    				else{
//	    					userMap.put("password", "000000");
//	    					userMap.put("enabled", 1);
//	    					userMap.put("isLock", 0);
//	    					userMap.put("specialUser", 0);
//	    					delegateApplyUser=(UserInfo) metaDataManager.saveEntityData(UserInfo.class, userMap);
//	    				}
//	    		 }
//	    		 //add by lee for 如果本地无审批人信息，直接去R3去人员信息 in 20100119 end
//	    		 String sameEmailDept=jsonObj.getString("sameEmailDept");
//	    		 SameMailDept dept=(SameMailDept) baseBervice.findUnique(SameMailDept.class, "id",Long.valueOf(sameEmailDept));
//	    		 String workSpace=jsonObj.getString("workSpace");
//	    		 WorkSpace workSpaces=(WorkSpace) baseBervice.findUnique(WorkSpace.class,"id",Long.valueOf(workSpace));
//	    		 String mailServer=workSpaces.getMailServer();
//	    		 MailServer ms=(MailServer) baseBervice.findUnique(MailServer.class, "name", mailServer);
//	    		 applyUser.setWorkSpace(workSpaces);
//	    		 applyUser.setSameMailDept(dept);
//	    		 applyUser.setMailServer(ms);
//	    		 Object us=baseBervice.save(applyUser);
//	    	     Map mainMap = new HashMap();
//	    			mainMap.put("createDate", currentDate);
//	    			mainMap.put("createUser", delegateApplyUser);
//	    			mainMap.put("applyUser", applyUser);
//	    			if(applyUser.getTelephone()!=null){
//	    				mainMap.put("applyUserTel", applyUser.getTelephone());
//	    			}
//	    			mainMap.put("applyDate", currentDate);
//	    			mainMap.put("delegateApplyUser", delegateApplyUser);
//	    			if(delegateApplyUser.getTelephone()!=null){
//	    			mainMap.put("delegateApplyTel", delegateApplyUser.getTelephone());
//	    			}
//	    			mainMap.put("processType", 0);
//	    			mainMap.put("status", Constants.STATUS_DRAFT);
//	    			mainMap.put("serviceItemProcess", 195);
//	    			mainMap.put("serviceItem", 301);
//	    			//mainMap.put("name", "AC-0000001884");
//	    			SystemMainTable msmts = systemMainTableService
//	    			.findSystemMainTableByClazz(AccountApplyMainTable.class); // 获取被关联表
//	    	        String msmtNames = msmts.getTableName(); // 获取被关联表英文表名
//	    	        Class accountApply = this.toClass(msmts.getClassName());
//	    			BaseObject mainObject = (BaseObject) metaDataManager.saveEntityDataForUser(
//	    					accountApply, mainMap,user);// 保存被关联主实体
//	    			
//	    			/** *******************************保存HR招聘单触发帐号实体START************************************************* */
//	    			SystemMainTable msmt = systemMainTableService
//	    			.findSystemMainTableByClazz(PersonFormalAccount.class); // 获取被关联表
//	    	        String msmtName = msmt.getTableName(); // 获取被关联表英文表名
//	    	        Class account = this.toClass(msmt.getClassName());
//	    			Map temp = new HashMap();
//	    			Map domain = new HashMap();
//	    			Map www = new HashMap();
//	    			Map telephone = new HashMap();
//	    			String mailValue=jsonObj.getString("mailValue");
//	    			MailVolume mv=(MailVolume) baseBervice.findUnique(MailVolume.class, "value",Integer.parseInt(mailValue));
//	    			String mailRemark=jsonObj.getString("mailRemark");
//	    			
//	    			temp.put("accountState", "0");
//	    			temp.put("applyId", mainObject);
//	    			temp.put("accountType", "2");
//	    			temp.put("mailValue", mv.getId());
//	    			temp.put("department", applyUser.getDepartment());
//	    			temp.put("remarkDesc", mailRemark);
//	    			temp.put("accountowner", applyUser);
//	    			temp.put("mailServer", mailServer);
//	    			temp.put("workSpace", workSpace);
//	    			temp.put("costCenterCode", applyUser.getCostCenterCode());
//	    			temp.put("sameMailDept", sameEmailDept);
//	    			
//	    			domain.put("accountState", "0");
//	    			domain.put("applyId", mainObject);
//	    			domain.put("department", applyUser.getDepartment());
//	    			domain.put("accountType", "1");
//	    			domain.put("accountowner", applyUser);
//	    			domain.put("mailServer", mailServer);
//	    			domain.put("sameMailDept", sameEmailDept);
//	    			domain.put("costCenterCode", applyUser.getCostCenterCode());
//	    			domain.put("workSpace", workSpace);
//
//	    			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
//	    					account, temp);// 保存关联实体
//	    			BaseObject domianObject = (BaseObject) metaDataManager.saveEntityData(
//	    					account, domain);// 保存关联实体
//	                String wwwFlag=jsonObj.getString("wwwFlag");
//	    			if(wwwFlag.equals("1")){
//	    				String wwwValue=jsonObj.getString("wwwValue");
//	    				WWWScanType wwws= (WWWScanType) baseBervice.findUnique(WWWScanType.class, "value", Integer.parseInt(wwwValue));
//	    				String wwwRemark=jsonObj.getString("wwwRemark");
//	    				www.put("accountState", "0");
//	    				www.put("applyId", mainObject);
//	    				www.put("accountType", "3");
//	    				www.put("department", applyUser.getDepartment());
//	    				www.put("accountowner", applyUser);
//	    				www.put("wwwAccountValue", wwws.getId());
//	    				www.put("applyReason", wwwRemark);
//	    				www.put("mailServer", mailServer);
//	    				www.put("sameMailDept", sameEmailDept);
//	    				www.put("costCenterCode", applyUser.getCostCenterCode());
//	    				www.put("workSpace", workSpace);
//	    				BaseObject wwwObject = (BaseObject) metaDataManager.saveEntityData(
//	    						account, www);// 保存关联实体
//	    			}
//	    			String telephoneFlag=jsonObj.getString("telephoneFlag");
//	                 if(telephoneFlag.equals("1")){
//	                	 String telephoneType=jsonObj.getString("telephoneType");
//	                	 String stationNumber=jsonObj.getString("stationNumber");
//	                	 Double yearMoney=jsonObj.getDouble("yearMoney");
//	                	 telephone.put("accountState", "0");
//	         			 telephone.put("applyId", mainObject);
//	         			 telephone.put("accountType", "15");
//	         			 telephone.put("accountowner", applyUser);
//	         			 telephone.put("department", applyUser.getDepartment());
//	         			 telephone.put("yearMoney", yearMoney);
//	         			 telephone.put("telephoneType", telephoneType);
//	         			 telephone.put("stationNumber", stationNumber);
//	         			 telephone.put("mailServer", mailServer);
//	         			 telephone.put("sameMailDept", sameEmailDept);
//	         			 telephone.put("workSpace", workSpace);
//	         			 telephone.put("costCenterCode", applyUser.getCostCenterCode());
//	         			 BaseObject telephoneObject = (BaseObject) metaDataManager
//	         					.saveEntityData(account, telephone);// 保存关联实体
//	    				
//	    			}
//	                 
//	                 
//	                 String definitionName="AR_NewITAccountApply1255086207953";
//	                 List<TelephoneAudit> audit= baseBervice.find(TelephoneAudit.class, "workSpace", workSpaces.getName());
//	                 String accountManger="";
//	                 if(audit!=null){
//	                 for(TelephoneAudit te:audit){
//	         		 accountManger=te.getAuditManger();
//	                  }
//	                 }
//	                 Map<String,String> mapBizz = new HashMap<String,String>();
//	                 String buzzParameters= "{dataId :'" + mainObject.getId()
//	    				+ "',applyId : '" +  mainObject.getId()
//	    				+ "',accountName:'" +  mainObject.getName()
//	    				+ "',applyType: 'acproject'," 
//	    				+ "applyTypeName: '新员工IT帐号申请',"
//	    				+ "customer:'',serviceItemId:'"
//	    				+ "301" + "'}";
//	         		if(buzzParameters!=null&&!buzzParameters.equals("")) {
//	         			JSONObject jo = JSONObject.fromObject(buzzParameters);
//	         			Iterator it = jo.keys();
//	         			while(it.hasNext()) {
//	         				String key = (String)it.next();
//	         				String value = (String)jo.get(key);					
//	         				mapBizz.put(key, value);
//	         			}
//	         		}
//	         		mapBizz.put("workflowHistory", "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis");
//	         		String serviceItemId = mapBizz.get("serviceItemId");
//	        		ServiceItem servcieItem = (ServiceItem) baseBervice.find(ServiceItem.class, serviceItemId);
//	        		ServiceItemUserTable siut = (ServiceItemUserTable) baseBervice.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
//	        		String className = siut.getClassName();
//	        		Object obj = baseBervice.find(this.toClass(className), mainObject.getId().toString());
//	        		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
//	        		mapBizz.put("applyNum",mainObject.getName());
//	        		mapBizz.put("applyName",mainObject.getName());
//	        		mapBizz.put("hrJob","true");
//	        		String dynCounterSignStr="confirmByAM:";
//	        		AccountApplyMainTable mainObj = (AccountApplyMainTable) baseBervice.find(AccountApplyMainTable.class,  mainObject.getId().toString(), true);			//得到面板主实体
//	        		List<PersonFormalAccount> accounts=  baseBervice.find(PersonFormalAccount.class, "applyId", mainObj);
//	        		for (PersonFormalAccount acc : accounts) {
//        			Long at=acc.getAccountType().getId();
//	        		AccountType ac=(AccountType) baseBervice.findUnique(AccountType.class,"id",at);
//	        		//AccountType ac=acc.getAccountType();
//	        		if(ac.getAccountType().equals("Telephone")){
//	        			dynCounterSignStr+="1"+"+";
//	        			dynCounterSignStr+=accountManger;
//	        			String type="&"+ac.getName()+"管理员处理";
//	        			dynCounterSignStr+=type;
//	        			dynCounterSignStr+=";";
//	        		}else{
////	        		Role role=ac.getRole();
////	        		Set<UserInfo> userinfos=role.getUserInfos();
//	        		Long roles = ac.getRole().getId();
//	        		Role role=(Role) baseBervice.findUnique(Role.class,"id",roles);
//	        		List<UserRole> userRole=baseBervice.find(UserRole.class, "role", role);
//	        		List<UserInfo> userinfos=new ArrayList();
//	        		for(UserRole ur:userRole){
//	        			userinfos.add(ur.getUserInfo());
//	        		}
//	        		if(userinfos.size()>1){
//	        		dynCounterSignStr+="1"+"+";
//	        		for(UserInfo userinfo:userinfos){
//	        			dynCounterSignStr+=userinfo.getUserName()+",";
//	        		}
//	        		dynCounterSignStr=dynCounterSignStr.substring(0, dynCounterSignStr.length()-1);
//	        		String type="&"+ac.getName()+"管理员处理";
//	        		dynCounterSignStr+=type;
//	        		dynCounterSignStr+=";";
//	        		}else{
//	        			for(UserInfo userinfo:userinfos){
//	        				dynCounterSignStr+="1"+"+"+userinfo.getUserName();
//	        			}
//	        			String type="&"+ac.getName()+"管理员处理";
//	        			dynCounterSignStr+=type;
//	        			dynCounterSignStr+=";";
//	        			
//	        		}
//	        		}
//	        		}
//	        		
//	        		if(dynCounterSignStr.endsWith(";")) {
//	        			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
//	        		}
//	        		
//	        		
//	        		mapBizz.put("dynCounterSign", dynCounterSignStr);//放入流程参数中
//	        		String creator = applyUser.getUserName();
//	        		Long instanceId = null;
//	        		String meg = "";
//	        		try{
//	        			instanceId = ps.createProcess(definitionName,creator,mapBizz);
//	        			json = "{success:true}";	
//	        		}catch(Exception e){
//	        			meg = e.getMessage();
//	        			json = "{success:true,Exception:'"+meg+"'}";
//	        		}
//	    	 }
//		 	
//	     }
//    		
//		return json;
//	}
//	
//	/**
//	 * 通过类名获取类
//	 * 
//	 * @Methods Name toClass
//	 * @Create In Mar 4, 2009 By gaowen
//	 * @param className
//	 * @return Class
//	 */
//	private Class toClass(String className) {
//		Class clazz = null;
//		try {
//			clazz = Class.forName(className);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return clazz;
//	}
//
//	public String getSameMailDept(String sameMailDeptName,int pageNo) {
//		//Page sameMailDept=accountService.findByPageQuerySameMailDept(sameMailDeptName, pageNo);
//		Page sameMailDept=accountService.findByPageQuerySameMailDeptByName(sameMailDeptName, pageNo);
//		Long total = sameMailDept.getTotalCount();// 这是查询出所有的记录
//	    List<SameMailDept> sameMD=sameMailDept.list();
//	    String json="";
//	    for(SameMailDept smd:sameMD){
//			Long id=smd.getId();
//			String name=smd.getName();
//			json += "{\"id\":\"" + id + "\",\"name\":\"" + name
//			+ "\"},";
//		}
//		if (json.length() == 0) {
//			json = "{success:true,rowCount:" + "1" + ",data:["
//					+ json.substring(0, json.length()) + "]}";
//		} else {
//			json = "{success:true,rowCount:" + total + ",data:["
//					+ json.substring(0, json.length() - 1) + "]}";
//		}
//		return json;
//	}

//}
