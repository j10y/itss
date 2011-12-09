package net.shopin.alipay.service;

import java.util.Map;

import net.shopin.alipay.util.Result;


public abstract interface RefundFastpayService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @param batchNum �˿��ܱ���
	 * @param batchData �������ݼ�
	 * @param realPath �ļ�����·��
	 * @return
	 */
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath);
	
	/**
	 * ֧�����������첽֪ͨ���� 
	 * @param batchNo ԭ�����˿����κ�
	 * @param successNum �˽��׳ɹ��ı��� 
	 * @param resultDetails ����������
	 */
	void asynResultRefundFastpay(String batchNo, int successNum, String resultDetails);
	
	/**
	 * ͨ�������Ż�ȡ�˿�����
	 * @param batchNo
	 * @return
	 */
	Map getResultDetailByBatchNo(String batchNo);
}
