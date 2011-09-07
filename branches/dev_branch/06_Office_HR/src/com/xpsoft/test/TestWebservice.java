package com.xpsoft.test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.rpc.ServiceException;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.google.gson.Gson;

public class TestWebservice {
	public static void main(String[] args) {
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					"http://localhost:8080/commonpart/services/CountPfJob"));
			call.setOperationName("isKpiItemScoreForUser");
			String translateText = (String) call
					.invoke(new Object[] { "1","1"});
			JSONObject json = JSONObject.fromObject(translateText);   
			JSONArray ja=(JSONArray) json.get("data");
			for(Object ob:ja){
				JSONObject jb=(JSONObject) ob;
//				System.out.println(jb.get("value"));
			}
			System.out.println(translateText);
		} catch (ServiceException e) {
			e.printStackTrace();
			System.out.println("Service 获取 Call对象失败!");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("new java.net.URL(url)错误!");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("远程错误!");
		}
	}
}
