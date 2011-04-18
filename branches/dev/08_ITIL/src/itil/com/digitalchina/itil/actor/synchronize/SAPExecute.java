//package com.digitalchina.itil.actor.synchronize;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.sap.mw.jco.IFunctionTemplate;
//import com.sap.mw.jco.IRepository;
//import com.sap.mw.jco.JCO;
//import com.sap.mw.jco.JCO.Client;
//import com.sap.mw.jco.JCO.Function;
//import com.sap.mw.jco.JCO.ParameterList;
//import com.sap.mw.jco.JCO.Structure;
//import com.sap.mw.jco.JCO.Table;
//
//public class SAPExecute {
//	//SAP
//	private IRepository repository;
//	
//	public SAPExecute(){
//		repository = SapOption.GetRepository();
//	}
//	
//	/**
//	 * 获取新员工信息
//	 * @Methods Name getUserInfo
//	 * @Create In Dec 21, 2009 By lee
//	 * @param PERNR
//	 * @param ITCODE
//	 * @return Map
//	 */
//	@SuppressWarnings("unchecked")
//	synchronized public Map getUserInfo(Long PERNR,String ITCODE){
//		Map result = new HashMap();
//		Client client = null;
//		try{
//			IFunctionTemplate ftemplate = repository.getFunctionTemplate("Z_GET_SINGLE_PERSON".toUpperCase());
//			if (ftemplate != null){
//				Function function = ftemplate.getFunction();
//				client = JCO.getClient(SapOption.getSID());
//				//输入参数
//		        ParameterList inPut = function.getImportParameterList();
//		        inPut.setValue(ITCODE.toUpperCase(),"ITCODE");			//用户ITCODE
//		        //执行函数
//		        client.execute(function);	
//		        //获取返回数据
//		        ParameterList outPut = function.getExportParameterList();
//		        Structure USERINFO_DATA=outPut.getStructure("IMAT");
//		        String itcode = USERINFO_DATA.getString("ITCODE");
//		        if(StringUtils.isNotBlank(itcode)){
//	    			String userRealName = USERINFO_DATA.getString("PERXM");//获取用户姓名
//	    			String deptCode = USERINFO_DATA.getString("DEPID");//获取用户部门编号
//	    			String costCode = USERINFO_DATA.getString("FIID");//获取用户成本中心编号
//	    			String employeeCode = USERINFO_DATA.getString("PERID");//获取用户员工编号
//	    			result.put("userName", ITCODE.toLowerCase());
//	    			result.put("realName", userRealName);
//	    			result.put("itcode", ITCODE);
//	    			result.put("department", Long.valueOf(deptCode));
//	    			result.put("costCenterCode", costCode);
//	    			result.put("employeeCode", employeeCode);
//		        }
//			}
//		}catch (Exception ex) {
//			System.out.println("获取新员工信息失败！\n");
//		    System.out.println("Caught an exception: \n" + ex);
//	    }finally{
//	    	JCO.releaseClient(client);
//	    }
//		return result;
//	}	
//	
//		
//}
