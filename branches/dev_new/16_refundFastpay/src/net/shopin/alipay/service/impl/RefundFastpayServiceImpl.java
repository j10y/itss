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
	
	public Result processRefundFastpayExcel(int batchNum, String batchData,
			String realPath, String relation, BigDecimal totalRefund, String applyId, String importUser, String importRemark) {//生成批量退款笔数及退款数据集
		// TODO Auto-generated method stub
		Result result = new Result();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sHtmlText = null;
		StringBuffer batchNo = new StringBuffer();
		Date refundDate = Calendar.getInstance().getTime();
		String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//退款日期（8位当天日期）
		 
		try {
			
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
				sHtmlText = AlipayService
						.refund_fastpay_by_platform_nopwd(sParaTemp);
			} catch (Exception e) {
				logger.info("调用[AlipayService]-[即时到账批量退款无密接口(refund_fastpay_by_platform_nopwd)]异常！");
				result.setException(e);
				result.setMsg("支付宝接口调用发生异常，请停止一切操作尽快联系开发人员，并核实支付宝账户资金！");
				return result;//调用支付宝接口发生异常，批量导入失败
			}
			
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
			String updateSql = null;
			updateSql = "INSERT INTO alipay_refundfastpay(batchNo, batchNum, batchData, filepath, refundDate, isSuccess, relation, totalRefund, applyId) " +
					"VALUES('" + batchNo + "', " + batchNum + ", '" + batchData + "', '" + realPath + "', str_to_date(\""+sParaTemp.get("refund_date")+"\",'%Y-%m-%d %H:%i:%s'), '" + alipay.getIsSuccess() + "'" +
							", '" + relation + "', " + totalRefund.doubleValue() + "," + applyId + ")";
			dao.updatebySql(updateSql);
			
			//更新状态,导入成功为4，导入失败为5
			updateSql = "UPDATE alipay_refundfastpayapply rfa"
				+ " SET rfa.applyStatus=" + (result.isSuccess() ? "4" : "5") + ", rfa.importUser='" 
				+ importUser + "', rfa.importTime=SYSDATE(), rfa.importRemark='" + importRemark + "'"
				+ ", rfa.batchNo=(select rf.batchNo from alipay_refundfastpay rf WHERE rf.applyId=" + applyId + ") "
				+ " WHERE rfa.id=" + applyId;
			dao.updatebySql(updateSql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("调用[AlipayService]-[即时到账批量退款无密接口(refund_fastpay_by_platform_nopwd)]异常！");
			result.setException(e);
			result.setMsg("支付宝接口调用发生异常，请停止一切操作尽快联系开发人员！");
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
	

	public Map getApplyDetailByBatchNo(String applyId) {
		// TODO Auto-generated method stub
		String querySql = "SELECT * FROM alipay_refundfastpayapply rfa WHERE rfa.id='" + applyId + "'";
		List list = dao.findDataList(querySql);
		return CollectionUtils.isEmpty(list) ? null : (Map)list.get(0);
	}

	public Result applyRefundFastpay(String realPath, String applyUser, String applyRemark) {
		Result result = new Result();
		try {
			StringBuffer applyNo = new StringBuffer();
			
			//生成applyNo
			Date refundDate = Calendar.getInstance().getTime();
			String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//退款日期（8位当天日期）
			//生成batchNo
			String batchNoSql = "SELECT MAX(substring(rfa.applyNo, " + (PropertiesUtil.getProperties("rfaApplyNoSuffix", "RFA-").length()+1) + ")) maxApplyNo FROM alipay_refundfastpayApply rfa WHERE rfa.applyNo like '" + PropertiesUtil.getProperties("rfaApplyNoSuffix", "RFA-") + refundDateSuffix + "%'";
			List list = dao.findDataList(batchNoSql);
			if(!org.springframework.util.CollectionUtils.isEmpty(list)){
				Map map = (Map) list.get(0);
				if(map.get("maxApplyNo")!=null){
					applyNo.append(Long.valueOf(map.get("maxApplyNo").toString()).longValue()+1);
				}else{
					int suffixLen = Integer.valueOf(PropertiesUtil.getProperties("refundDateSuffixLen", "3"));
					applyNo.append(refundDateSuffix);
					for(int i=0; i<suffixLen-1; i++){
						applyNo.append("0");
					}
					applyNo.append("1");
				}
			}else{
				int suffixLen = Integer.valueOf(PropertiesUtil.getProperties("refundDateSuffixLen", "3"));
				applyNo.append(refundDateSuffix);
				for(int i=0; i<suffixLen-1; i++){
					applyNo.append("0");
				}
				applyNo.append("1");
			}
			
			
			String updateSql = "INSERT INTO alipay_refundfastpayapply(applyNo, filepath, applyStatus, applyUser, applyTime, applyRemark) " +
			"VALUES('" + PropertiesUtil.getProperties("rfaApplyNoSuffix", "RFA-")+applyNo + "', '" + realPath + "', 1, '" + applyUser + "', SYSDATE(), '" + applyRemark + "')";
			dao.updatebySql(updateSql);
			result.setSuccess(true);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.info("服务器端异常，提交批量退款申请失败！");
			result.setException(e);
			result.setMsg("服务器端异常，提交批量退款申请失败！");
		}
		
		return result;
	}

	public Result auditRefundFastpay(String applyId, String auditUser, String auditRemark,
			int applyStatus) {
		Result result = new Result();
		String updateSql = null;
		try {
			updateSql = "UPDATE alipay_refundfastpayapply rfa"
				+ " SET rfa.applyStatus=" + applyStatus + ", rfa.auditUser='" + auditUser 
				+ "', rfa.auditTime=SYSDATE(), rfa.auditRemark='" + auditRemark + "'" 
				+ " WHERE rfa.id=" + applyId;
			dao.updatebySql(updateSql);
			result.setSuccess(true);
			logger.info("id为" + applyId + "批量退款申请" + (applyStatus==2 ? "审批通过！" : "拒绝成功！"));
			result.setMsg("批量退款申请" + (applyStatus==2  ? "审批通过！" : "拒绝成功！"));
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("服务器端异常，审批批量退款申请失败！");
			result.setException(e);
			result.setMsg("服务器端异常，审批批量退款申请失败！");
		}
		return result;
	}

	public Map searchRefundFastpayApplyList(String startDate, String endDate,
			int applyStatus, String applyUser, boolean isSignUser, int startRecord, int pageSize) {
		Map resultMap = new HashMap();
		Integer rowCount = 0;
		String rowCountSql = "SELECT COUNT(rfa.id) COU FROM alipay_refundfastpayapply rfa " +
				" WHERE rfa.applyTime BETWEEN str_to_date(\""+startDate+"\",'%Y-%m-%d') AND str_to_date(\""+endDate+"\",'%Y-%m-%d')" +
				(isSignUser ? " AND rfa.applyUser='" + applyUser + "'" : " ") + (applyStatus!=-1 ? (" AND applyStatus=" + applyStatus) : "");
		List list = dao.findDataList(rowCountSql);
		if(!org.springframework.util.CollectionUtils.isEmpty(list)){
			rowCount = Integer.valueOf(((Map) list.get(0)).get("COU").toString());
		}
		resultMap.put("rowCount", rowCount.toString());
		
		String resultSql  = "SELECT rfa.* FROM alipay_refundfastpayapply rfa " +
		" WHERE rfa.applyTime BETWEEN str_to_date(\""+startDate+"\",'%Y-%m-%d') AND str_to_date(\""+endDate+"\",'%Y-%m-%d')" +
		(isSignUser ? " AND rfa.applyUser='" + applyUser + "'" : " ") + (applyStatus!=-1 ? (" AND applyStatus=" + applyStatus) : "") +
		" LIMIT " + startRecord + ", " + pageSize;
		resultMap.put("data", dao.findDataList(resultSql));
		return resultMap;
	}

	/**
	 * 鉴于数据量太大，及字符串匹配等，默认查询一周内
	 */
	public boolean validateRefundFastpay(int batchNum, String batchData,
			String relation, BigDecimal totalRefund) {
		String countSql = "SELECT COUNT(rf.id) COUN FROM alipay_refundfastpay rf " +
				"WHERE rf.batchNum=" + batchNum + " AND rf.batchData='" +
				batchData + "' AND rf.relation='" + relation + "' AND  rf.totalRefund=" + totalRefund + "" +
				" AND (rf.refundDate BETWEEN DATE_ADD(rf.refundDate, INTERVAL -7 day) AND SYSDATE())";
		List list = dao.findDataList(countSql);
		
		if(Integer.valueOf(((Map) list.get(0)).get("COUN").toString()) > 0){
			return false;
		}else{
			return true;
		}
	}
	
}