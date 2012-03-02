package net.shopin.alipay.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.shopin.alipay.util.Result;


public abstract interface RefundFastpayService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @param batchNum �˿��ܱ���
	 * @param batchData �������ݼ�
	 * @param realPath �ļ�����·��
	 * @return
	 */
	@Deprecated
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath);
	
	/**
	 * 
	 * @param batchNum �˿��ܱ���
	 * @param batchData �������ݼ�
	 * @param realPath �ļ�����·��
	 * @param relation �̼Ҷ����š�֧�������׺ŵĹ�ϵ ���̼Ҷ�����1^֧�������׺�1#�̼Ҷ�����2^֧�������׺�2��
	 * @param totalRefund �˿��ܽ��
	 * @return
	 */
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath, String relation, BigDecimal totalRefund);
	
	/**
	 * 
	 * @param batchNum �˿��ܱ���
	 * @param batchData �������ݼ�
	 * @param realPath �ļ�����·��
	 * @param relation �̼Ҷ����š�֧�������׺ŵĹ�ϵ ���̼Ҷ�����1^֧�������׺�1#�̼Ҷ�����2^֧�������׺�2��
	 * @param totalRefund �˿��ܽ��
	 * @param applyId  �����˿�����ID
	 * @return
	 */
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath, 
			String relation, BigDecimal totalRefund, String applyId, String importUser, String importRemark);
	
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
	
	/**
	 * ͨ������ID��ȡ�����˿���������
	 * @param applyId
	 * @return
	 */
	Map getApplyDetailByBatchNo(String applyId);
	
	/**
	 * �ύ�����˿�����
	 * @param realPath
	 * @param applyUser  ������
	 * @param applyRemark ���뱸ע
	 * @return
	 */
	Result applyRefundFastpay(String realPath, String operationUser, String applyRemark);
	
	/**
	 * ���������˿�����
	 * @param applyId ����id
	 * @param auditUser ������
	 * @param auditRemak �������
	 * @param applyStatus ����״̬ 1������ 2����ͨ����δ���� 3�ܾ� 4����ɹ� 5����ʧ��
	 * @return
	 */
	Result auditRefundFastpay(String applyId, String auditUser, String auditRemak, int applyStatus);
	
	/**
	 * ��ѯָ��������ĳ��ʱ�䷶Χ�ڵ�ָ��״̬�ļ�ʱ�����˿�����
	 * @param StartDate
	 * @param endDate
	 * @param applyStatus
	 * @param applyUser
	 * @param isSignUser �Ƿ�ָ����
	 * @param startRecord ��ʼ��¼����0��ʼ
	 * @param pageSize
	 * @return
	 */
	Map searchRefundFastpayApplyList(String StartDate, String endDate, int applyStatus, String applyUser, boolean isSignUser, int startRecord, int pageSize);
	
	/**
	 * �����˿��ܱ����������˿����ݼ�����ϵ���˿��ܽ��У��
	 * @param batchNum
	 * @param batchData
	 * @param relation
	 * @param totalRefund
	 * @return
	 */
	boolean validateRefundFastpay(int batchNum, String batchData, String relation, BigDecimal totalRefund);
}
