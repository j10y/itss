package com.zsgj.info.framework.workflow.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.workflow.WorkflowConstants;
/**
 * �����������࣬��������Ĳ�������
 * @Class Name JbpmConfig
 * @Author yang
 * @Create In 2008-6-10
 */
public class JbpmConfig implements WorkflowConstants{
	//���Ƶ�Action���ڰ�
	public static String ACTION_PACKAGE = null;
	
	//����ͼ���ڰ�
	public static String JPDL_PACKAGE = null;
	
	//SPRING�����е�SessionFactory����
	public static String SPRING_SESSION_FACTORY_NAME = null;
		
	//Action�仯��֪��ʱ
	public static String ACTION_TIMEOUT = "10";
	
	//�鿴�Ƿ��������ļ�����
	//����У��������ļ����ݸ���ԭ����
	static {
		init();
	}	
	
	//��ʼ�����������ⲿ���³�ʼ��
	public static void init() {
		ACTION_PACKAGE = getProperties("workflow.ACTION_PACKAGE",DEFAULT_ACTION_PACKAGE).trim();
		JPDL_PACKAGE = getProperties("workflow.JPDL_PACKAGE",DEFAULT_JPDL_PACKAGE).trim(); 
		ACTION_TIMEOUT = getProperties("workflow.ACTION_TIMEOUT","60000").trim(); 
		SPRING_SESSION_FACTORY_NAME = getProperties("workflow.SPRING_SESSION_FACTORY_NAME","pmcSessionFactory").trim(); 
	}
	
	/**
	 * ��ȡ��Դ�ļ���Ϣ
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            ��Դ�ļ�Key
	 * @param defaultValue
	 *            Ĭ����Ϣ
	 * @return String
	 */
	@SuppressWarnings("static-access")
	private static String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try{
			message = appContext.getMessage(Key, new Object[0],ContextHolder.getInstance().getLocal());
			return (message != null && !message.equals("") ? message : defaultValue);
		}catch(Exception e){
			return defaultValue;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
	}

}
