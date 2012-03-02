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

	public Result processRefundFastpayExcel(int batchNum, String batchData, String realPath) {//���������˿�������˿����ݼ�
		// TODO Auto-generated method stub
//		Result result = new Result();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String sHtmlText = null;
//		StringBuffer batchNo = new StringBuffer();
//		Date refundDate = Calendar.getInstance().getTime();
//		String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//�˿����ڣ�8λ�������ڣ�
//		//����batchNo
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
//        sParaTemp.put("batch_no", batchNo.toString());//��ʽΪ���˿����ڣ�8λ�������ڣ�+��ˮ�ţ�3��24λ�����ܽ��ܡ�000�������ǿ��Խ���Ӣ���ַ�����
//        sParaTemp.put("refund_date", sdf.format(refundDate));
//        sParaTemp.put("batch_num", batchNum + "");
//        sParaTemp.put("detail_data", batchData);
//		//���캯������������URL  
//		try {
//			
//			sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
//			StringReader sr = new StringReader(sHtmlText.toString());
//			BufferedReader br = new BufferedReader(sr);
//			
//			JAXBContext jaxbContext = JAXBContext.newInstance("net.shopin.alipay.entity");
//			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
//			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//			Schema schema = schemaFactory.newSchema(new File(PropertiesUtil.getProperties("alipay.schema.refundfastpay.path", "E:/��Ʒ/֧���������˻�/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd")));
//			unMarshaller.setSchema(schema);
//			Alipay alipay = (Alipay) unMarshaller.unmarshal(br);
//			if(alipay.getIsSuccess().equals("T")){
//				result.setSuccess(true);
//				result.setMsg("�ɹ�!�˿����κ�:" + batchNo);
//				
//			}else if(alipay.getIsSuccess().equals("F")){
//				result.setSuccess(false);
//				result.setErrorCode(alipay.getError());
//				result.setMsg("ʧ��!�˿����κ�:" + batchNo);
//			}else if(alipay.getIsSuccess().equals("P")){
//				result.setSuccess(true);
//				result.setMsg("�����л����п�������!�˿����κ�:" + batchNo);
//			}
//			String updateSql = "INSERT INTO alipay_refundfastpay(batchNo, batchNum, batchData, filepath, refundDate, isSuccess) " +
//					"VALUES('" + batchNo + "', " + batchNum + ", '" + batchData + "', '" + realPath + "', str_to_date(\""+sParaTemp.get("refund_date")+"\",'%Y-%m-%d %H:%i:%s'), '" + alipay.getIsSuccess() + "')";
//			dao.updatebySql(updateSql);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			logger.info("����[AlipayService]-[��ʱ���������˿����ܽӿ�(refund_fastpay_by_platform_nopwd)]�쳣��");
//			result.setException(e);
//			result.setMsg("֧�����ӿڵ���ʧ�ܣ���������ϵ������Ա��");
//		}
//		return result;
		return processRefundFastpayExcel(batchNum, batchData, realPath, null, null);
	}
	
	public Result processRefundFastpayExcel(int batchNum, String batchData,
			String realPath, String relation, BigDecimal totalRefund) {//���������˿�������˿����ݼ�
		// TODO Auto-generated method stub
		Result result = new Result();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sHtmlText = null;
		StringBuffer batchNo = new StringBuffer();
		Date refundDate = Calendar.getInstance().getTime();
		String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//�˿����ڣ�8λ�������ڣ�
		//����batchNo
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
        sParaTemp.put("batch_no", batchNo.toString());//��ʽΪ���˿����ڣ�8λ�������ڣ�+��ˮ�ţ�3��24λ�����ܽ��ܡ�000�������ǿ��Խ���Ӣ���ַ�����
        sParaTemp.put("refund_date", sdf.format(refundDate));
        sParaTemp.put("batch_num", batchNum + "");
        sParaTemp.put("detail_data", batchData);
		//���캯������������URL  
		try {
			
			sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
			StringReader sr = new StringReader(sHtmlText.toString());
			BufferedReader br = new BufferedReader(sr);
			
			JAXBContext jaxbContext = JAXBContext.newInstance("net.shopin.alipay.entity");
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File(PropertiesUtil.getProperties("alipay.schema.refundfastpay.path", "E:/��Ʒ/֧���������˻�/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd")));
			unMarshaller.setSchema(schema);
			Alipay alipay = (Alipay) unMarshaller.unmarshal(br);
			if(alipay.getIsSuccess().equals("T")){
				result.setSuccess(true);
				result.setMsg("�ɹ�!�˿����κ�:" + batchNo);
				
			}else if(alipay.getIsSuccess().equals("F")){
				result.setSuccess(false);
				result.setErrorCode(alipay.getError());
				result.setMsg("ʧ��!�˿����κ�:" + batchNo);
			}else if(alipay.getIsSuccess().equals("P")){
				result.setSuccess(true);
				result.setMsg("�����л����п�������!�˿����κ�:" + batchNo);
			}
			String updateSql = "INSERT INTO alipay_refundfastpay(batchNo, batchNum, batchData, filepath, refundDate, isSuccess, relation, totalRefund) " +
					"VALUES('" + batchNo + "', " + batchNum + ", '" + batchData + "', '" + realPath + "', str_to_date(\""+sParaTemp.get("refund_date")+"\",'%Y-%m-%d %H:%i:%s'), '" + alipay.getIsSuccess() + "'" +
							", '" + relation + "', " + totalRefund.doubleValue() + ")";
			dao.updatebySql(updateSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("����[AlipayService]-[��ʱ���������˿����ܽӿ�(refund_fastpay_by_platform_nopwd)]�쳣��");
			result.setException(e);
			result.setMsg("֧�����ӿڵ���ʧ�ܣ���������ϵ������Ա��");
		}
		return result;
	}
	
	public Result processRefundFastpayExcel(int batchNum, String batchData,
			String realPath, String relation, BigDecimal totalRefund, String applyId, String importUser, String importRemark) {//���������˿�������˿����ݼ�
		// TODO Auto-generated method stub
		Result result = new Result();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sHtmlText = null;
		StringBuffer batchNo = new StringBuffer();
		Date refundDate = Calendar.getInstance().getTime();
		String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//�˿����ڣ�8λ�������ڣ�
		 
		try {
			
			//����batchNo
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
	        sParaTemp.put("batch_no", batchNo.toString());//��ʽΪ���˿����ڣ�8λ�������ڣ�+��ˮ�ţ�3��24λ�����ܽ��ܡ�000�������ǿ��Խ���Ӣ���ַ�����
	        sParaTemp.put("refund_date", sdf.format(refundDate));
	        sParaTemp.put("batch_num", batchNum + "");
	        sParaTemp.put("detail_data", batchData);
			//���캯������������URL 
			
			try {
				sHtmlText = AlipayService
						.refund_fastpay_by_platform_nopwd(sParaTemp);
			} catch (Exception e) {
				logger.info("����[AlipayService]-[��ʱ���������˿����ܽӿ�(refund_fastpay_by_platform_nopwd)]�쳣��");
				result.setException(e);
				result.setMsg("֧�����ӿڵ��÷����쳣����ֹͣһ�в���������ϵ������Ա������ʵ֧�����˻��ʽ�");
				return result;//����֧�����ӿڷ����쳣����������ʧ��
			}
			
			StringReader sr = new StringReader(sHtmlText.toString());
			BufferedReader br = new BufferedReader(sr);
			
			JAXBContext jaxbContext = JAXBContext.newInstance("net.shopin.alipay.entity");
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File(PropertiesUtil.getProperties("alipay.schema.refundfastpay.path", "E:/��Ʒ/֧���������˻�/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd")));
			unMarshaller.setSchema(schema);
			Alipay alipay = (Alipay) unMarshaller.unmarshal(br);
			if(alipay.getIsSuccess().equals("T")){
				result.setSuccess(true);
				result.setMsg("�ɹ�!�˿����κ�:" + batchNo);
				
			}else if(alipay.getIsSuccess().equals("F")){
				result.setSuccess(false);
				result.setErrorCode(alipay.getError());
				result.setMsg("ʧ��!�˿����κ�:" + batchNo);
			}else if(alipay.getIsSuccess().equals("P")){
				result.setSuccess(true);
				result.setMsg("�����л����п�������!�˿����κ�:" + batchNo);
			}
			String updateSql = null;
			updateSql = "INSERT INTO alipay_refundfastpay(batchNo, batchNum, batchData, filepath, refundDate, isSuccess, relation, totalRefund, applyId) " +
					"VALUES('" + batchNo + "', " + batchNum + ", '" + batchData + "', '" + realPath + "', str_to_date(\""+sParaTemp.get("refund_date")+"\",'%Y-%m-%d %H:%i:%s'), '" + alipay.getIsSuccess() + "'" +
							", '" + relation + "', " + totalRefund.doubleValue() + "," + applyId + ")";
			dao.updatebySql(updateSql);
			
			//����״̬,����ɹ�Ϊ4������ʧ��Ϊ5
			updateSql = "UPDATE alipay_refundfastpayapply rfa"
				+ " SET rfa.applyStatus=" + (result.isSuccess() ? "4" : "5") + ", rfa.importUser='" 
				+ importUser + "', rfa.importTime=SYSDATE(), rfa.importRemark='" + importRemark + "'"
				+ ", rfa.batchNo=(select rf.batchNo from alipay_refundfastpay rf WHERE rf.applyId=" + applyId + ") "
				+ " WHERE rfa.id=" + applyId;
			dao.updatebySql(updateSql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("����[AlipayService]-[��ʱ���������˿����ܽӿ�(refund_fastpay_by_platform_nopwd)]�쳣��");
			result.setException(e);
			result.setMsg("֧�����ӿڵ��÷����쳣����ֹͣһ�в���������ϵ������Ա��");
		}
		return result;
	}

	public void asynResultRefundFastpay(String batchNo, int successNum,
			String resultDetails) {
		//�����첽֪ͨ��25Сʱ�ڷֶ���ظ����ѣ��������޸ļ�¼ǰ����Ҫ���Ȳ�ѯ�Ѿ��޸����
		String countSql = "SELECT COUNT(rf.id) COUN FROM alipay_refundfastpay rf WHERE rf.batchNo='" + batchNo + "'" +
				" AND rf.successNum IS NOT NULL";
		List list = dao.findDataList(countSql);
		
		if(((Map) list.get(0)).get("COUN").toString().equals("0")){//δ����
			String updateSql = "UPDATE alipay_refundfastpay rf SET rf.successNum=" + successNum + ", rf.resultDetails='" + resultDetails + "' WHERE rf.batchNo='" + batchNo + "'";
			dao.updatebySql(updateSql);
			logger.info("[AlipayService]-[��ʱ���������˿����ܽӿ�(refund_fastpay_by_platform_nopwd)]-�첽֪ͨ����ԭ�����˿����κ�Ϊ��" + batchNo + "����Ϣ");
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
			
			//����applyNo
			Date refundDate = Calendar.getInstance().getTime();
			String refundDateSuffix = DateUtil.formatDate(refundDate, "yyyyMMdd");//�˿����ڣ�8λ�������ڣ�
			//����batchNo
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
			logger.info("���������쳣���ύ�����˿�����ʧ�ܣ�");
			result.setException(e);
			result.setMsg("���������쳣���ύ�����˿�����ʧ�ܣ�");
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
			logger.info("idΪ" + applyId + "�����˿�����" + (applyStatus==2 ? "����ͨ����" : "�ܾ��ɹ���"));
			result.setMsg("�����˿�����" + (applyStatus==2  ? "����ͨ����" : "�ܾ��ɹ���"));
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("���������쳣�����������˿�����ʧ�ܣ�");
			result.setException(e);
			result.setMsg("���������쳣�����������˿�����ʧ�ܣ�");
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
	 * ����������̫�󣬼��ַ���ƥ��ȣ�Ĭ�ϲ�ѯһ����
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