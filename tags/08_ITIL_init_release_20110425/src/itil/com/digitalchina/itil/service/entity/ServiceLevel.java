package com.digitalchina.itil.service.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ���񼶱�SL
 * һ����������ˮƽĿ���Ԥ�����ҿɱ���ĳɹ���
 * �������ˮƽ��ʱҲ����ʽ����ָ����ˮƽĿ�ꡣ
 * ע�������ˮƽЭ��SLA�����֡�
 * @Class Name ServiceLevel
 * @Author sa
 * @Create In 2008-11-11
 */
public class ServiceLevel extends BaseObject {
	private Long id;
	//�����ˮƽ
	private String level;
	//���������Ҫ��Сʱ��
	private Integer solveHour; 
}
