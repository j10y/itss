package com.digitalchina.itil.requireErp.synchronize;

import org.apache.log4j.Logger;

import com.digitalchina.info.framework.util.PropertiesUtil;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

/**
 * 建立SAP连接
 * @Class Name SapOption
 * @Create In 03 23, 2010 By zhangzy
 */
public class SapOption {
				
	private static IRepository repository= null;
	private static String SID = "00";
	
	synchronized public static IRepository GetRepository(){
					
		int max_connection_num = Integer.valueOf(PropertiesUtil.getProperties("sap.erp.max","100"));		//最大连接数
		String userName = PropertiesUtil.getProperties("sap.erp.username","ITSS");	//连接用户名
		String password = PropertiesUtil.getProperties("sap.erp.password","saperp");		//连接密码
		String language = PropertiesUtil.getProperties("sap.erp.language","ZH");			//语言
		String port = PropertiesUtil.getProperties("sap.erp.port","00");					//连接端口
		String serverip = PropertiesUtil.getProperties("sap.erp.serverip","10.1.188.8");	//连接服务器IP
		String client = PropertiesUtil.getProperties("sap.erp.client","101");				//连接服务器客户端
		if(repository != null){
			return repository;			
		}else{		
			try {
				JCO.addClientPool(SID,				// Alias for this pool
								  max_connection_num,          // Max. number of connections
								  client,       		// SAP client
			                      userName,   		// userid WSHX
			                      password,     	// password
			                      language,        	// language
			                      serverip, 		// host name
			                      port );
				repository = JCO.createRepository("MYRepository", SID);
				return repository;
			}
			catch (JCO.Exception ex) {
				Logger Log = Logger.getLogger("Log");
				System.out.println("链接R3失败！\n");
				System.out.println("Caught an exception: \n" + ex);
				Log.debug(ex.getMessage());
				return null;
			}
		}
	}
	
	public void cleanUp() {
	    JCO.removeClientPool(SID);
	}

	public static IRepository getRepository() {
		return repository;
	}

	public static void setRepository(IRepository repository) {
		SapOption.repository = repository;
	}

	public static String getSID() {
		return SID;
	}

	public static void setSID(String sid) {
		SID = sid;
	}
}
