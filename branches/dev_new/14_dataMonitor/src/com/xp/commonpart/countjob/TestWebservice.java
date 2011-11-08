package com.xp.commonpart.countjob;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;


import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

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
