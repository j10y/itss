package net.shopin.alipay.service;

import java.util.Map;

import net.shopin.alipay.util.Result;


public abstract interface RefundFastpayService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @param batchNum 退款总笔数
	 * @param batchData 单笔数据集
	 * @param realPath 文件绝对路径
	 * @return
	 */
	Result processRefundFastpayExcel(int batchNum, String batchData, String realPath);
	
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
}
