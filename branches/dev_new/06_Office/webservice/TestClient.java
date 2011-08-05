import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;


public class TestClient {
//	public static void main(String[] args) {
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(
//					"http://localhost:8080/Office/services/NoticeServiceImpl"));
//			call.setOperationName("getDyNotice");
//			//call.setOperationName("getInfoCount");
//			/*
//			 * invoke方法的参数是Object数组,该数组元素个数与方法参数一致
//			 */
//			String translateText = (String) call
//					.invoke(new Object[] { "1","1",null,null,null,null });
//			System.out.println(translateText);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//			System.out.println("Service 获取 Call对象失败!");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			System.out.println("new java.net.URL(url)错误!");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//			System.out.println("远程错误!");
//		}
//	}
	
//	public static void main(String[] args) {
//		try {
//			Service service = new Service();
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(new java.net.URL(
//					"http://localhost:8080/services/NoticeServiceImpl"));
//			call.setOperationName("getNoticeDetail");
//			//call.setOperationName("getInfoCount");
//			/*
//			 * invoke方法的参数是Object数组,该数组元素个数与方法参数一致
//			 */
//			String translateText = (String) call
//					.invoke(new Object[] { "1","1","1" });
//			System.out.println(translateText);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//			System.out.println("Service 获取 Call对象失败!");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			System.out.println("new java.net.URL(url)错误!");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//			System.out.println("远程错误!");
//		}
//	}
	
	public static void main(String[] args) {
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					"http://localhost:8080/services/FlowServiceImpl"));
			call.setOperationName("saveProcessAndToNext");
//			call.setOperationName("getDbsxList");
//			call.setOperationName("getDbsxDetail");
//			call.setOperationName("getDycyList");
//			call.setOperationName("getYycyList");
//			call.setOperationName("getYycyDetail");
//			call.setOperationName("getGdlx");
			
			/*
			 * invoke方法的参数是Object数组,该数组元素个数与方法参数一致
//			 */
//			String translateText = (String) call
//					.invoke(new Object[] { "1","1","部门负责人审批","70"});
//			String translateText = (String) call
//			.invoke(new Object[] { "1","1"});
			String translateText = (String) call
			.invoke(new Object[] { "1","1","3","70","部门负责人审批","审批","审批不同意","",null,"false",null});
//			String translateText = (String) call
//			.invoke(new Object[] { "1","1","0",null,null,null});
//			String translateText = (String) call
//			.invoke(new Object[] { "1","1","18"});
//			String translateText = (String) call
//			.invoke(new Object[] {});
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
