package com.zsgj.itil.event.webservice;

import java.util.Map;

/**
 * CALLCENTER����ITIL��WS�ӿ�
 * @Class Name CCLoginItilWs
 * @Author sa
 * @Create In Mar 31, 2009
 */
public interface CCLoginItilWs {
	
	/**
	 * CCԶ�̵��õ�¼itil�ķ���
	 * @Methods Name login
	 * @Create In Mar 31, 2009 By sa
	 * @param loginItcode ��½ITILϵͳ�˺�, 
	 * @param submitUserItcode ��ϯԱ��ITCODE, Ҳ�����¼��ύ��
	 * @param customerItcode �ͻ�Ա��ITCODE�� Ҳ���������Ա��
	 * @param callId ����ID
	 * @param callPhone �������
	 * @return String
	 */
	Map login(String loginItcode, String submitUserItcode, String customerItcode,String callId,String callPhone);
	
}
