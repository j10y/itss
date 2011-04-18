//package com.digitalchina.itil.actor.synchronize;
//
//import org.apache.log4j.Logger;
//
//import com.digitalchina.info.framework.util.PropertiesUtil;
//import com.sap.mw.jco.IRepository;
//import com.sap.mw.jco.JCO;
//
///**
// * ����SAP����
// * @Class Name SapOption
// * @Author lee
// * @Create In Dec 18, 2009
// */
//public class SapOption {
//				
//	private static IRepository repository= null;
//	private static String SID = "HRQ";
//	
//	synchronized public static IRepository GetRepository(){
//					
//		int max_connection_num = Integer.valueOf(PropertiesUtil.getProperties("sap.hrq.max","100"));		//���������
//		String userName = PropertiesUtil.getProperties("sap.hrq.username","HRREADPERSON");	//�����û���
//		String password = PropertiesUtil.getProperties("sap.hrq.password","030624");		//��������
//		String language = PropertiesUtil.getProperties("sap.hrq.language","ZH");			//����
//		String port = PropertiesUtil.getProperties("sap.hrq.port","00");					//���Ӷ˿�
//		String serverip = PropertiesUtil.getProperties("sap.hrq.serverip","10.1.188.40");	//���ӷ�����IP
//		String client = PropertiesUtil.getProperties("sap.hrq.client","401");				//���ӷ������ͻ���
//		//System.out.println("�����û���"+userName+"\n�������룺"+password+"\n���ԣ�"+language+"\n���ӵ�ַ��"+serverip+":"+port);
//		if(repository != null){
//			return repository;
//			
//		}else{		
//			try {
//				JCO.addClientPool(SID,				// Alias for this pool
//								  max_connection_num,          // Max. number of connections
//								  client,       		// SAP client
//			                      userName,   		// userid WSHX
//			                      password,     	// password
//			                      language,        	// language
//			                      serverip, 		// host name
//			                      port );
//				repository = JCO.createRepository("MYRepository", SID);
//				return repository;
//			}
//			catch (JCO.Exception ex) {
//				Logger Log = Logger.getLogger("Log");
//				System.out.println("����R3ʧ�ܣ�\n");
//				System.out.println("Caught an exception: \n" + ex);
//				Log.debug(ex.getMessage());
//				return null;
//			}
//		}
//	}
//	
//	public void cleanUp() {
//	    JCO.removeClientPool(SID);
//	}
//
//	public static IRepository getRepository() {
//		return repository;
//	}
//
//	public static void setRepository(IRepository repository) {
//		SapOption.repository = repository;
//	}
//
//	public static String getSID() {
//		return SID;
//	}
//
//	public static void setSID(String sid) {
//		SID = sid;
//	}
//}
