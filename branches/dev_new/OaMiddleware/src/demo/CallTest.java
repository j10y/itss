package demo;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class CallTest {

	/**
	 * @Methods Name main
	 * @Create In Aug 4, 2011 By Administrator
	 * @param args void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://127.0.0.1:8080/oamw/services/MenuWebServiceImpl";
		String methodName = "reloadMenu";
		Service service = new Service();
        Call call;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
	        call.setOperationName(methodName);
	        List<String> list = new ArrayList<String>();
	        list.add("中文");
	        list.add("2");
	        Object [] paramArr = {"文件",10000,list};
	        String backJsonString = (String) call.invoke(paramArr);
	        System.out.println(backJsonString);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
	
	}

}
