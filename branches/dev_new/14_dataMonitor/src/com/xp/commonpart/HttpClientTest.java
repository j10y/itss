package com.xp.commonpart;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		    try {
		    	HttpClient client = new HttpClient(); 
		    	client.setConnectionTimeout(3000);//设置为延时3秒
				client.setTimeout(3000);
			    HttpMethod method=new GetMethod("http://open.cb.qq.com/OpenAPI/openkey/get_user_address.php?version=1.0&merchant_id=shopin&openid=B4E5B0E63D830333D2A999552587D140&openkey=FD93C31A196C8378D42B9A04B701BED3&charset=utf-8&return_fmt=json&timestamp=20120222155336&sign=a6065c06ea5bf9510908fcf5a14dadf9");
				client.executeMethod(method);
				System.out.println(method.getStatusCode());
				//打印服务器返回的状态
			    System.out.println(method.getStatusLine());
			    //打印返回的信息
			    System.out.println(method.getResponseBodyAsString());
			    //释放连接
			    method.releaseConnection();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 System.out.println("asd1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 System.out.println("asd2");
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 System.out.println(e.getMessage());
			}


	}

}
