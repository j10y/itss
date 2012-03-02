package net.shopin.alipay.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.shopin.alipay.util.Result;


public abstract interface RefundFastpayService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @param batchNum 退款总笔数
	 * @param batchData 单笔数据集
	 * @param realPath 文件绝对路径
	 * @return
	 */
	@Deprecated
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath);
	
	/**
	 * 
	 * @param batchNum 退款总笔数
	 * @param batchData 单笔数据集
	 * @param realPath 文件绝对路径
	 * @param relation 商家订单号、支付宝交易号的关系 （商家订单号1^支付宝交易号1#商家订单号2^支付宝交易号2）
	 * @param totalRefund 退款总金额
	 * @return
	 */
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath, String relation, BigDecimal totalRefund);
	
	/**
	 * 
	 * @param batchNum 退款总笔数
	 * @param batchData 单笔数据集
	 * @param realPath 文件绝对路径
	 * @param relation 商家订单号、支付宝交易号的关系 （商家订单号1^支付宝交易号1#商家订单号2^支付宝交易号2）
	 * @param totalRefund 退款总金额
	 * @param applyId  批量退款申请ID
	 * @return
	 */
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath, 
			String relation, BigDecimal totalRefund, String applyId, String importUser, String importRemark);
	
	/**
	 * 支付宝服务器异步通知处理 
	 * @param batchNo 原请求退款批次号
	 * @param successNum 退交易成功的笔数 
	 * @param resultDetails 处理结果详情
	 */
	void asynResultRefundFastpay(String batchNo, int successNum, String resultDetails);
	
	/**
	 * 通过订单号获取退款详情
	 * @param batchNo
	 * @return
	 */
	Map getResultDetailByBatchNo(String batchNo);
	
	/**
	 * 通过申请ID获取批量退款申请详情
	 * @param applyId
	 * @return
	 */
	Map getApplyDetailByBatchNo(String applyId);
	
	/**
	 * 提交批量退款申请
	 * @param realPath
	 * @param applyUser  申请人
	 * @param applyRemark 申请备注
	 * @return
	 */
	Result applyRefundFastpay(String realPath, String operationUser, String applyRemark);
	
	/**
	 * 审批批量退款申请
	 * @param applyId 申请id
	 * @param auditUser 审批人
	 * @param auditRemak 审批意见
	 * @param applyStatus 申请状态 1待审批 2审批通过，未导入 3拒绝 4导入成功 5导入失败
	 * @return
	 */
	Result auditRefundFastpay(String applyId, String auditUser, String auditRemak, int applyStatus);
	
	/**
	 * 查询指定申请人某段时间范围内的指定状态的即时批量退款申请
	 * @param StartDate
	 * @param endDate
	 * @param applyStatus
	 * @param applyUser
	 * @param isSignUser 是否指定人
	 * @param startRecord 起始记录，从0开始
	 * @param pageSize
	 * @return
	 */
	Map searchRefundFastpayApplyList(String StartDate, String endDate, int applyStatus, String applyUser, boolean isSignUser, int startRecord, int pageSize);
	
	/**
	 * 根据退款总笔数，单笔退款数据集，关系和退款总金额校验
	 * @param batchNum
	 * @param batchData
	 * @param relation
	 * @param totalRefund
	 * @return
	 */
	boolean validateRefundFastpay(int batchNum, String batchData, String relation, BigDecimal totalRefund);
}
