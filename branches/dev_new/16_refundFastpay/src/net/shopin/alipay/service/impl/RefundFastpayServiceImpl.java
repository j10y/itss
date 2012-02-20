package net.shopin.alipay.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.shopin.alipay.dao.RefundFastpayDao;
import net.shopin.alipay.entity.Alipay;
import net.shopin.alipay.service.RefundFastpayService;
import net.shopin.alipay.util.PropertiesUtil;
import net.shopin.alipay.util.Result;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.alipay.services.AlipayService;

public class RefundFastpayServiceImpl<T> extends BaseServiceImpl<T> implements
	RefundFastpayService<T> {
	
	private static final Log logger = LogFactory.getLog(RefundFastpayServiceImpl.class);
	
	private RefundFastpayDao dao;

	public RefundFastpayServiceImpl(RefundFastpayDao dao) {
		/* 15 */super(dao);
		/* 16 */this.dao = dao;
	}

	public Result processRefundFastpayExcel(int batchNum, String batchData, String realPath) {//生成批量退款笔数及退款数据集
		// TODO Auto-generated method stub
//		Result result = new Result();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String sHtmlText = null;
//		StringBuffer batchNo = new StringBuffer();
//		Date refundDate = Calendar.getInstance().getTime();
//		String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//退款日期（8位当天日期）
//		//生成batchNo
//		String batchNoSql = "SELECT MAX(rf.batchNo) maxbatchNo FROM alipay_refundfastpay rf WHERE rf.batchNo like '" + refundDateSuffix + "%'";
//		List list = dao.findDataList(batchNoSql);
//		if(!org.springframework.util.CollectionUtils.isEmpty(list)){
//			Map map = (Map) list.get(0);
//			if(map.get("maxbatchNo")!=null){
//				batchNo.append(Long.valueOf(map.get("maxbatchNo").toString()).longValue()+1);
//			}else{
//				int suffixLen = Integer.valueOf(PropertiesUtil.getProperties("refundDateSuffixLen", "3"));
//				batchNo.append(refundDateSuffix);
//				for(int i=0; i<suffixLen-1; i++){
//					batchNo.append("0");
//				}
//				batchNo.append("1");
//			}
//		}else{
//			int suffixLen = Integer.valueOf(PropertiesUtil.getProperties("refundDateSuffixLen", "3"));
//			batchNo.append(refundDateSuffix);
//			for(int i=0; i<suffixLen-1; i++){
//				batchNo.append("0");
//			}
//			batchNo.append("1");
//		}
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//        sParaTemp.put("batch_no", batchNo.toString());//格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）。
//        sParaTemp.put("refund_date", sdf.format(refundDate));
//        sParaTemp.put("batch_num", batchNum + "");
//        sParaTemp.put("detail_data", batchData);
//		//构造函数，生成请求URL  
//		try {
//			
//			sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
//			StringReader sr = new StringReader(sHtmlText.toString());
//			BufferedReader br = new BufferedReader(sr);
//			
//			JAXBContext jaxbContext = JAXBContext.newInstance("net.shopin.alipay.entity");
//			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
//			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//			Schema schema = schemaFactory.newSchema(new File(PropertiesUtil.getProperties("alipay.schema.refundfastpay.path", "E:/上品/支付宝批量退货/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd")));
//			unMarshaller.setSchema(schema);
//			Alipay alipay = (Alipay) unMarshaller.unmarshal(br);
//			if(alipay.getIsSuccess().equals("T")){
//				result.setSuccess(true);
//				result.setMsg("成功!退款批次号:" + batchNo);
//				
//			}else if(alipay.getIsSuccess().equals("F")){
//				result.setSuccess(false);
//				result.setErrorCode(alipay.getError());
//				result.setMsg("失败!退款批次号:" + batchNo);
//			}else if(alipay.getIsSuccess().equals("P")){
//				result.setSuccess(true);
//				result.setMsg("处理中或银行卡充退中!退款批次号:" + batchNo);
//			}
//			String updateSql = "INSERT INTO alipay_refundfastpay(batchNo, batchNum, batchData, filepath, refundDate, isSuccess) " +
//					"VALUES('" + batchNo + "', " + batchNum + ", '" + batchData + "', '" + realPath + "', str_to_date(\""+sParaTemp.get("refund_date")+"\",'%Y-%m-%d %H:%i:%s'), '" + alipay.getIsSuccess() + "')";
//			dao.updatebySql(updateSql);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			logger.info("调用[AlipayService]-[即时到账批量退款无密接口(refund_fastpay_by_platform_nopwd)]异常！");
//			result.setException(e);
//			result.setMsg("支付宝接口调用失败，请重新联系开发人员！");
//		}
//		return result;
		return processRefundFastpayExcel(batchNum, batchData, realPath, null, null);
	}
	
	public Result processRefundFastpayExcel(int batchNum, String batchData,
			String realPath, String relation, BigDecimal totalRefund) {//生成批量退款笔数及退款数据集
		// TODO Auto-generated method stub
		Result result = new Result();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sHtmlText = null;
		StringBuffer batchNo = new StringBuffer();
		Date refundDate = Calendar.getInstance().getTime();
		String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//退款日期（8位当天日期）
		//生成batchNo
		String batchNoSql = "SELECT MAX(rf.batchNo) maxbatchNo FROM alipay_refundfastpay rf WHERE rf.batchNo like '" + refundDateSuffix + "%'";
		List list = dao.findDataList(batchNoSql);
		if(!org.springframework.util.CollectionUtils.isEmpty(list)){
			Map map = (Map) list.get(0);
			if(map.get("maxbatchNo")!=null){
				batchNo.append(Long.valueOf(map.get("maxbatchNo").toString()).longValue()+1);
			}else{
				int suffixLen = Integer.valueOf(PropertiesUtil.getProperties("refundDateSuffixLen", "3"));
				batchNo.append(refundDateSuffix);
				for(int i=0; i<suffixLen-1; i++){
					batchNo.append("0");
				}
				batchNo.append("1");
			}
		}else{
			int suffixLen = Integer.valueOf(PropertiesUtil.getProperties("refundDateSuffixLen", "3"));
			batchNo.append(refundDateSuffix);
			for(int i=0; i<suffixLen-1; i++){
				batchNo.append("0");
			}
			batchNo.append("1");
		}
		Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("batch_no", batchNo.toString());//格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）。
        sParaTemp.put("refund_date", sdf.format(refundDate));
        sParaTemp.put("batch_num", batchNum + "");
        sParaTemp.put("detail_data", batchData);
		//构造函数，生成请求URL  
		try {
			
			sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
			StringReader sr = new StringReader(sHtmlText.toString());
			BufferedReader br = new BufferedReader(sr);
			
			JAXBContext jaxbContext = JAXBContext.newInstance("net.shopin.alipay.entity");
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File(PropertiesUtil.getProperties("alipay.schema.refundfastpay.path", "E:/上品/支付宝批量退货/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd")));
			unMarshaller.setSchema(schema);
			Alipay alipay = (Alipay) unMarshaller.unmarshal(br);
			if(alipay.getIsSuccess().equals("T")){
				result.setSuccess(true);
				result.setMsg("成功!退款批次号:" + batchNo);
				
			}else if(alipay.getIsSuccess().equals("F")){
				result.setSuccess(false);
				result.setErrorCode(alipay.getError());
				result.setMsg("失败!退款批次号:" + batchNo);
			}else if(alipay.getIsSuccess().equals("P")){
				result.setSuccess(true);
				result.setMsg("处理中或银行卡充退中!退款批次号:" + batchNo);
			}
			String updateSql = "INSERT INTO alipay_refundfastpay(batchNo, batchNum, batchData, filepath, refundDate, isSuccess, relation, totalRefund) " +
					"VALUES('" + batchNo + "', " + batchNum + ", '" + batchData + "', '" + realPath + "', str_to_date(\""+sParaTemp.get("refund_date")+"\",'%Y-%m-%d %H:%i:%s'), '" + alipay.getIsSuccess() + "'" +
							", '" + relation + "', " + totalRefund.doubleValue() + ")";
			dao.updatebySql(updateSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("调用[AlipayService]-[即时到账批量退款无密接口(refund_fastpay_by_platform_nopwd)]异常！");
			result.setException(e);
			result.setMsg("支付宝接口调用失败，请重新联系开发人员！");
		}
		return result;
	}

	public void asynResultRefundFastpay(String batchNo, int successNum,
			String resultDetails) {
		//由于异步通知在25小时内分多次重复提醒，所以在修改记录前，需要首先查询已经修改完毕
		String countSql = "SELECT COUNT(rf.id) COUN FROM alipay_refundfastpay rf WHERE rf.batchNo='" + batchNo + "'" +
				" AND rf.successNum IS NOT NULL";
		List list = dao.findDataList(countSql);
		
		if(((Map) list.get(0)).get("COUN").toString().equals("0")){//未插入
			String updateSql = "UPDATE alipay_refundfastpay rf SET rf.successNum=" + successNum + ", rf.resultDetails='" + resultDetails + "' WHERE rf.batchNo='" + batchNo + "'";
			dao.updatebySql(updateSql);
			logger.info("[AlipayService]-[即时到账批量退款无密接口(refund_fastpay_by_platform_nopwd)]-异步通知更新原批量退款批次号为：" + batchNo + "的信息");
		}

		
	}

	public Map getResultDetailByBatchNo(String batchNo) {
		// TODO Auto-generated method stub
		String querySql = "SELECT * FROM alipay_refundfastpay rf WHERE rf.batchNo='" + batchNo + "'";
		List list = dao.findDataList(querySql);
		return CollectionUtils.isEmpty(list) ? null : (Map)list.get(0);
	}
	
	
	
}