package com.zsgj.itil.event.action;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName; 
import javax.xml.rpc.ServiceException; 
import org.apache.axis.client.Call; 
import org.apache.axis.client.Service;
public class FirstTestHello{
	@SuppressWarnings("unchecked")
	public String CcallCallback(Map infoMap) throws ServiceException, MalformedURLException, RemoteException{
		Map map = new HashMap();
		//注意了后面的那个是deploy中定义的webserive中的定义名称
		String endpoint =  "http://localhost:8080/services/CCLoginItilWs" ; 
		Service service = new Service();
		Call call =null;
		  call = (Call) service.createCall();
		call.setOperationName(new QName(endpoint, "login"));
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		String submitUserItcode="admin";
		String customerItcode=(String)infoMap.get("customerItcode");
		String callId=(String)infoMap.get("callId");
		String callPhone=(String)infoMap.get("callPhone");
		map = (Map) call.invoke(new Object[] { submitUserItcode ,customerItcode,callId,callPhone});
		return map.get("message").toString();
	}
	public static void main(String[] args) {
		try {
			Map map = new HashMap();
			String endpoint =  "http://localhost:8080/services/CCLoginItilWs" ; 
			Service service = new Service();
			Call call =null;
			  call = (Call) service.createCall();
			call.setOperationName(new QName(endpoint, "login"));
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			map = (Map) call.invoke(new Object[] { "xiehuia" ,"changyu","xiehuia","3","1591000"});
			System.out.println(map.get("message")); 
			Process process = Runtime.getRuntime().exec("cmd.exe /c start "+map.get("message")); 	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
