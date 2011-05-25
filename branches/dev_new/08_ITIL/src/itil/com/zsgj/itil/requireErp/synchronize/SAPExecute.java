package com.zsgj.itil.requireErp.synchronize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;
import com.zsgj.itil.require.entity.RequireFactoryInfo;

public class SAPExecute {
	//SAP
	@SuppressWarnings("unused")
	private IRepository repository;
	@SuppressWarnings("unused")
	private Service service = (Service) ContextHolder.getBean("baseService");
	
	public SAPExecute(){
		//repository = SapOption.GetRepository();
	}
	
	/**
	 * ��ȡERP���ڱ��---------------������ʱ����������ʱ�޸ĵ����з���
	 * @Methods Name getErpAccountId
	 * @Create In Aug 20, 2010 By lee
	 * @param dataId	����ID
	 * @param weightAccount	��Ȩ����
	 * @param otherInfo		ʵʩ����
	 * @param transferRequestNumber	���������
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	synchronized public List getErpAccountId(String dataId,String weightAccount,String otherInfo,String transferRequestNumber){
		List resultList = new ArrayList();		
//		Client client = null;
		try{
//			IFunctionTemplate ftemplate = repository.getFunctionTemplate("Z_FKTK".toUpperCase());
//			if (ftemplate != null){
//				Function function = ftemplate.getFunction();
//				client = JCO.getClient(SapOption.getSID());
//				//�������
//		        ParameterList inPut = function.getImportParameterList();	
//		        ParameterList tables= function.getTableParameterList();
//		        Table ZFKTK_FQ = tables.getTable("ZFKTK_FQ");
//				if(otherInfo.length()>0&&!otherInfo.equals(null)){					
//					String[] strArray = otherInfo.split(",");
//					for(int i = 0 ; i<strArray.length;i++){
//						ZFKTK_FQ.appendRow();
//						String[] strArray2= strArray[i].split("%");						
//						int id = i+1;
//						String strId = "";
//						if(i<10){
//							strId= "0"+id;
//						}
//						int dayNum = Integer.parseInt(strArray2[1].replaceAll("��", ""));
//						String strDayNum = strArray2[1].replaceAll("��", "");
//						if(dayNum<10&&dayNum>0){
//							strDayNum="000"+strDayNum;
//						}else if(dayNum<100&&dayNum>=10){
//							strDayNum="00"+strDayNum;
//						}else if(dayNum<1000&&dayNum>=100){
//							strDayNum="0"+strDayNum;
//						}
//						ZFKTK_FQ.setValue(strId,"RATNR");
//						ZFKTK_FQ.setValue(strArray2[0],"RATPZ");
//						ZFKTK_FQ.setValue(strDayNum,"RATZT");
//					}					
//				}   		        
//		        inPut.setValue(weightAccount.toUpperCase(),"VTEXT");     //��Ȩ����
//		        inPut.setValue(otherInfo.toUpperCase(), "TEXT1");//��������
//		        inPut.setValue(transferRequestNumber.toUpperCase(), "CHANGEREQUEST");//���������
//		        //ִ�к���
//		        client.execute(function);	
//		        //��ȡ��������
//		        ParameterList outPut = function.getExportParameterList();
//		        int isSuccess = outPut.getInt("SUBRC");
//		        String erpAccountId = outPut.getString("ZTERMOUT");
//		        Table MESSTAB=tables.getTable("MESSTAB");
//		    	Map map = new HashMap();
//		    	map.put("SUBRC", outPut.getInt("SUBRC"));
//		    	map.put("erpAccountId", erpAccountId);
//		    	resultList.add(map);
//		    	for(int i = 0;i<MESSTAB.getNumRows(); i++){
//		    	        Map result = new HashMap();
//			    		result.put("TEXT", MESSTAB.getString("TEXT"));
//			    		result.put("ARBGB", MESSTAB.getString("ARBGB"));
//			    		result.put("MSGNR", MESSTAB.getString("MSGNR"));
//		    			if(i<MESSTAB.getNumRows()){
//		    				MESSTAB.nextRow();
//		    			}
//		    	    	resultList.add(result);	
//		    	}	
//		       
//			}
			 Map result = new HashMap();
			 result.put("TEXT", "��");
			 result.put("ARBGB", "0");
			 result.put("MSGNR", "0");
			 resultList.add(result);	
		}catch (Exception ex) {
			System.out.println("��ȡERP���ڱ����Ϣʧ�ܣ�\n");
		    System.out.println("Caught an exception: \n" + ex);
	    }finally{
//	    	JCO.releaseClient(client);
	    }
		return resultList;
	}	

	/**
	 * �������빤����ϸ��Ϣ
	 * @Methods Name saveRequireFactoryInfo
	 * @Create In 03 23, 2010 By zhangzy
	 * @param 
	 * @param 
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	synchronized public List<Map> saveRequireFactoryInfo(String dataId,String[] productIds,String transferRequestNumber){		
		List resultList = new ArrayList();
//		Client client = null;
		try{
//			IFunctionTemplate ftemplate = repository.getFunctionTemplate("Z_KCD".toUpperCase());
//			Function function = ftemplate.getFunction();
//			client = JCO.getClient(SapOption.getSID());
//			//�������
//	        ParameterList inPut = function.getImportParameterList();	
//	        ParameterList tables= function.getTableParameterList();
//	        Table Z_KCD = tables.getTable("Z_KCD");
//
//			if (ftemplate != null){				
//				for (int i = 0; i < productIds.length; i++) {						
//						RequireFactoryInfo requireFactoryInfo = null;
//						if(!"".equals(productIds[i])){
//							requireFactoryInfo =(RequireFactoryInfo) service.find(RequireFactoryInfo.class, productIds[i]);
//						
//				        Z_KCD.appendRow();				        
//				        Z_KCD.setValue(requireFactoryInfo.getFactoryId().toUpperCase(), "WERKS");
//				        Z_KCD.setValue(requireFactoryInfo.getStockAddressId().toUpperCase(), "LGORT");
//				        Z_KCD.setValue(requireFactoryInfo.getStockAddressDesc(), "LGOBE");
//				        Z_KCD.setValue(requireFactoryInfo.getMrpFlag(), "DISKZ");
//				        Z_KCD.setValue(requireFactoryInfo.getShipCondition(), "VSBED");
//				        Z_KCD.setValue(requireFactoryInfo.getShipPoint(), "VSTEL");
//				        Z_KCD.setValue(requireFactoryInfo.getNoOrdersTransportShipPoint(), "VSTE1");
//				        Z_KCD.setValue(requireFactoryInfo.getWareHouseId(), "LGNUM");				        
//				        inPut.setValue(transferRequestNumber.toUpperCase(), "CHANGEREQUEST");//���������
//						}
//				}
//		        //ִ�к���
//		        client.execute(function);	
//		        //��ȡ��������
//		        ParameterList outPut = function.getExportParameterList();
//		        Table MESSTAB=tables.getTable("MESSTAB");
//	    		Map map = new HashMap();
//	    		map.put("SUBRC", outPut.getInt("SUBRC"));	    			
//	    		resultList.add(map);
//	    		for(int i = 0;i<MESSTAB.getNumRows(); i++){
//	    	        	Map result = new HashMap();
//		    			result.put("TEXT", MESSTAB.getString("TEXT"));
//		    			result.put("ARBGB", MESSTAB.getString("ARBGB"));
//		    			result.put("MSGNR", MESSTAB.getString("MSGNR"));
//	    				if(i<MESSTAB.getNumRows()){
//	    					MESSTAB.nextRow();
//	    				}
//	    	    		resultList.add(result);	
//	    		}
//			}
			
			Map result = new HashMap();
			result.put("TEXT", "��");
			result.put("ARBGB", "0");
			result.put("MSGNR", "0");
			
    		resultList.add(result);	
		}catch (Exception ex) {
			System.out.println("�������빤����ϸ��Ϣʧ�ܣ�\n");
		    System.out.println("Caught an exception: \n" + ex);
	    }finally{
//	    	JCO.releaseClient(client);
	    }
		return resultList;
	}		
		
}
