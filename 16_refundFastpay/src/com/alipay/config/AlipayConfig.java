package com.alipay.config;

import net.shopin.alipay.util.PropertiesUtil;

/* *
 *������AlipayConfig
 *���ܣ�����������
 *��ϸ�������ʻ��й���Ϣ������·��
 *�汾��3.2
 *���ڣ�2011-03-17
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
	
 *��ʾ����λ�ȡ��ȫУ����ͺ��������ID
 *1.������ǩԼ֧�����˺ŵ�¼֧������վ(www.alipay.com)
 *2.������̼ҷ���(https://b.alipay.com/order/myOrder.htm)
 *3.�������ѯ���������(PID)��������ѯ��ȫУ����(Key)��

 *��ȫУ����鿴ʱ������֧�������ҳ��ʻ�ɫ��������ô�죿
 *���������
 *1�������������ã������������������������
 *2���������������ԣ����µ�¼��ѯ��
 */

public class AlipayConfig {
	
	//�����������������������������������Ļ�����Ϣ������������������������������
	// ���������ID����2088��ͷ��16λ��������ɵ��ַ���
	//public static String partner = "2088101568345155";
	public static String partner = PropertiesUtil.getProperties("alipay.partner");
	
	// ���װ�ȫ�����룬�����ֺ���ĸ��ɵ�32λ�ַ���
	//public static String key = "xu6xamwvgk5b51ahco9sgpbxy1e49ve9";
	public static String key = PropertiesUtil.getProperties("alipay.key");
	
	// ֧����������֪ͨ��ҳ�� Ҫ�� http://��ʽ������·�����������?id=123�����Զ������
	// ���뱣֤���ַ�ܹ��ڻ������з��ʵĵ�
	public static String notify_url = PropertiesUtil.getProperties("alipay.notifyUrl");
	//�����������������������������������Ļ�����Ϣ������������������������������
	

	// �����ã�����TXT��־·��
	public static String log_path = "D:\\alipay_log_" + System.currentTimeMillis()+".txt";

	// �ַ������ʽ Ŀǰ֧�� gbk �� utf-8
	public static String input_charset = "utf-8";
	
	// ǩ����ʽ �����޸�
	public static String sign_type = "MD5";
	
	//����ģʽ,�����Լ��ķ������Ƿ�֧��ssl���ʣ���֧����ѡ��https������֧����ѡ��http
	public static String transport = "http";

}
