/**
 * @Probject Name: nginxStatus
 * @Path: net.shopin.monitorNginxStatusMonitor.java
 * @Create By Jack
 * @Create In 2012-5-17 下午09:43:35
 * TODO
 */
package net.shopin.monitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

import net.shopin.utils.AppUtil;
import net.shopin.utils.NginxSecondAlarmDataUtil;
import net.shopin.utils.AppUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
/**
 * @Class Name NginxStatusMonitor
 * @Author Jack
 * @Create In 2012-5-17
 */
public class NginxStatusMonitor extends QuartzJobBean{
	private final Log logger = LogFactory.getLog("servicelog");
	
	public void checkWebNginx(){
		//构造HttpClient的实例
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//创建GET方法的实例
		HttpGet httpget = new HttpGet("http://172.16.200.50/NginxStatus");
		try{
			HttpResponse resp =httpClient.execute(httpget);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity he = resp.getEntity();
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						he.getContent()));// 得到读取流
				StringBuffer sb = new StringBuffer();//接受者 
				String line; //临时存储器 
				while ((line = rd.readLine()) != null) {//读取 
					sb.append(line);              //写入 
				} 
				rd.close(); 
				String html=new String(sb.toString().getBytes(),"UTf-8");
				String[] activeNum = html.split(" ");
				
				Date cd = new Date();
				System.out.println("WebNginx连接数：" + activeNum[2] + " 采样时间: "
						+ cd.toString());
				
				/**********************添加报表内存数据逻辑**************************/
				
				try {
					NginxSecondAlarmDataUtil.addWebData(activeNum[2]);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				/**********************添加报表内存数据逻辑**************************/
				
				Integer an = (new Integer(AppUtil.getProperties(
						"activeWebNum", "150")));
				if(new Integer(activeNum[2]) >= an){
					String sms = "您好， WebNginx连接数超过规定阀值：" + an + "，目前为"
							+ activeNum[2] + " 。上品折扣监控系统";
					String[] smsNo = AppUtil.getProperties("smsNo",
							"18601031200").split(",");
					if(smsNo.length>1){
						for(String phone : smsNo){
							URL U = null;
					        try {
								String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message="
										+ URLEncoder.encode(sms, "GBK")
										+ "&phone="
										+ phone
										+ "&epid=6181&linkid=";
					            U = new URL(url);
					            URLConnection connection = U.openConnection();
					            connection.connect();
								BufferedReader in = new BufferedReader(
										new InputStreamReader(
												connection.getInputStream()));
					            in.close();
					            logger.info("Send Alert SMS To:" + phone);
					        } catch (Exception e) {
					        	logger.error(e.getMessage());
					        }
						}
					}else{
						URL U = null;
				        try {
							String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message="
									+ URLEncoder.encode(sms, "GBK")
									+ "&phone="
									+ AppUtil.getProperties("smsNo",
											"18601031200")
									+ "&epid=6181&linkid=";
				            U = new URL(url);
				            URLConnection connection = U.openConnection();
				            connection.connect();
							BufferedReader in = new BufferedReader(
									new InputStreamReader(
											connection.getInputStream()));
				            in.close();
							logger.info("Send Alert SMS To:"
									+ AppUtil.getProperties("smsNo",
											"18601031200"));
				        } catch (Exception e) {
				        	logger.error(e.getMessage());
				        }
					}
			        
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		
	}
	
	private void checkAppNginx(){
		//构造HttpClient的实例
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//创建GET方法的实例
		HttpGet httpget = new HttpGet("http://172.16.200.29/NginxStatus");
		try{
			HttpResponse resp =httpClient.execute(httpget);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity he = resp.getEntity();
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						he.getContent()));// 得到读取流
				StringBuffer sb = new StringBuffer();//接受者 
				String line; //临时存储器 
				while ((line = rd.readLine()) != null) {//读取 
					sb.append(line);              //写入 
				} 
				rd.close(); 
				String html=new String(sb.toString().getBytes(),"UTf-8");
				String[] activeNum = html.split(" ");
				Integer an = ((new Integer(AppUtil.getProperties(
						"activeAppNum", "300"))));
				
				Date cd = new Date();
				System.out.println("AppNginx连接数：" + activeNum[2] + " 采样时间: "
						+ cd.toString());
				
				/**********************添加报表内存数据逻辑**************************/
				
				try {
					NginxSecondAlarmDataUtil.addAppData(activeNum[2]);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				/**********************添加报表内存数据逻辑**************************/
				
				if(new Integer(activeNum[2]) >= an){
					String sms = "您好， AppNginx连接数超过规定阀值：" + an + "，目前为"
							+ activeNum[2] + " ，正在重启OrderServer。上品折扣监控系统";
					String[] smsNo = AppUtil.getProperties("smsNo",
							"18601031200").split(",");
					if(smsNo.length>1){
						for(String phone : smsNo){
							URL U = null;
					        try {
								String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message="
										+ URLEncoder.encode(sms, "GBK")
										+ "&phone="
										+ phone
										+ "&epid=6181&linkid=";
					            U = new URL(url);
					            URLConnection connection = U.openConnection();
					            connection.connect();
								BufferedReader in = new BufferedReader(
										new InputStreamReader(
												connection.getInputStream()));
					            in.close();
					            logger.info("Send Alert SMS To:" + phone);
					        } catch (Exception e) {
					        	logger.error(e.getMessage());
					        }
						}
						
					}else{
						URL U = null;
				        try {
							String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message="
									+ URLEncoder.encode(sms, "GBK")
									+ "&phone="
									+ AppUtil.getProperties("smsNo",
											"18601031200")
									+ "&epid=6181&linkid=";
				            U = new URL(url);
				            URLConnection connection = U.openConnection();
				            connection.connect();
							BufferedReader in = new BufferedReader(
									new InputStreamReader(
											connection.getInputStream()));
				            in.close();
							logger.info("Send Alert SMS To:"
									+ AppUtil.getProperties("smsNo",
											"18601031200"));
				        } catch (Exception e) {
				        	logger.error(e.getMessage());
				        }
					}
					//杀死OrderServer服务器，62及63
					//TestSSH.killAll();
					//重启OrderServer服务器，62及63
					//TestSSH.startAll();
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		checkWebNginx();
		checkAppNginx();
	}

}
