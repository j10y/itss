package net.shopin.alipay.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.shopin.alipay.entity.Alipay;
import net.shopin.alipay.service.RefundFastpayService;
import net.shopin.alipay.util.HttpUtil;
import net.shopin.alipay.util.JDBCUtil;
import net.shopin.alipay.util.PropertiesUtil;
import net.shopin.alipay.util.Result;
import net.shopin.alipay.util.SpringContextUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import com.alipay.config.AlipayConfig;
import com.alipay.services.AlipayService;
import com.alipay.util.AlipayCore;

public class RefundFastpayServlet extends HttpServlet {
	
	RefundFastpayService refundFastpayService = (RefundFastpayService)SpringContextUtils.getBean("refundFastpayService");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//转发处理
		StringBuffer json = new StringBuffer();
		String methodCall = req.getParameter("methodCall");
		if(methodCall.equals("getResultDetailByBatchNo")){//通过原来的批量退款批次号获取退款详情
			if(req.getParameter("batchNo")==null){
				json.append("{success:false,msg:'参数[原批量退款批次号]不能为空'}");
			}else{
				Map resultDetails = refundFastpayService.getResultDetailByBatchNo(req.getParameter("batchNo"));
				if(CollectionUtils.isEmpty(resultDetails)){
					json.append("{success:false,msg:'原批量退款批次号为:" +req.getParameter("batchNo") + "的批量退款信息不存在'}");
				}else{
					json.append("{success:true,data:{");
					json.append("batchNo:'" + resultDetails.get("batchNo") + "',");
					json.append("batchNum:'" + resultDetails.get("batchNum") + "',");
					json.append("batchData:'" + resultDetails.get("batchData") + "',");
					json.append("refundDate:'" + DateUtil.formatDate((Date)resultDetails.get("refundDate"), "yyyy-MM-dd HH:mm:ss") + "',");
					json.append("isSuccess:'" + resultDetails.get("isSuccess") + "',");
					json.append("successNum:'" + (resultDetails.get("successNum") != null ? resultDetails.get("successNum") : "") + "',");
					json.append("resultDetails:'" + (resultDetails.get("resultDetails")!=null ? resultDetails.get("resultDetails") : "") + "',");
					json.append("relation:'" + (resultDetails.get("relation")!=null ? resultDetails.get("relation") : "") + "',");
					json.append("totalRefund:'" + (resultDetails.get("totalRefund")!=null ? resultDetails.get("totalRefund") : "") + "'");
					json.append("},msg:'获取原批量退款批次号为:" +req.getParameter("batchNo") + "的批量退款信息成功！'}");
				}
			}			
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().write(json.toString());
			resp.getWriter().close();
			return;
		}else if(methodCall.equals("batchRefundImport")){//批量退款导入
			//解析文件
			
			String realPath = null;
			String filePath = null;
			Result result = null;
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				boolean isMutipart = ServletFileUpload.isMultipartContent(req);
				if(isMutipart){
					ServletFileUpload upload = new ServletFileUpload(factory);
					List fileItems = upload.parseRequest(req);
					Iterator iterator = fileItems.iterator();
					while(iterator.hasNext()){
						FileItem fi = (FileItem)iterator.next();
						if (!fi.isFormField()) {
							String fileName = fi.getName();
							if(fileName==null || "".equals(fileName)) break;
							String suffix = fileName.substring(fileName.lastIndexOf("."));
				            String systemFileName = "upload-" + System.currentTimeMillis() + suffix;			            
				            
				            filePath = PropertiesUtil.getProperties("fileUploadPath", "D:/data/upload/")  + systemFileName;		            
				            //realPath = req.getSession().getServletContext().getRealPath("/") + filePath;
				            realPath = filePath;
				            try {
								File uploadedFile = new File(realPath);
								fi.write(uploadedFile);
								result = processRefundFastpayExcel(realPath);
								
								if(!result.isSuccess()){//失败
									RequestDispatcher rd = req.getRequestDispatcher("/refund/result.jsp");
									req.setAttribute("result", result);
									rd.forward(req, resp);
								}else{//成功
									req.getSession(true).setAttribute("result", result);
									resp.sendRedirect(req.getContextPath() + "/refund/success.jsp");
								}							
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
						}
					}

				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(methodCall.equals("batchRefundApply")){//批量退款申请
			//解析文件
			
			String realPath = null;
			String filePath = null;
			Result result = null;
			String applyUser = null;
			String applyRemark = null;
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				boolean isMutipart = ServletFileUpload.isMultipartContent(req);
				if(isMutipart){
					ServletFileUpload upload = new ServletFileUpload(factory);
					List fileItems = upload.parseRequest(req);
					Iterator iterator = fileItems.iterator();
					while(iterator.hasNext()){
						FileItem fi = (FileItem)iterator.next();
						if (!fi.isFormField()) {
							String fileName = fi.getName();
							if(fileName==null || "".equals(fileName)) break;
							String suffix = fileName.substring(fileName.lastIndexOf("."));
				            String systemFileName = "upload-" + System.currentTimeMillis() + suffix;			            
				            
				            filePath = PropertiesUtil.getProperties("fileUploadPath", "D:/data/upload/")  + systemFileName;		            
				            //realPath = req.getSession().getServletContext().getRealPath("/") + filePath;
				            realPath = filePath;
							File uploadedFile = new File(realPath);
							fi.write(uploadedFile);
						}else{
							String name = fi.getFieldName();
							String value = fi.getString();
							//在enctype="multipart/form-data") 情况下，form表单的数据通过流传递，且在Tomcat服务器下，默认使用ISO-8859-1编码
							value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
							if(name.equals("applyUser")){
								applyUser = value;
							}
							if(name.equals("applyRemark")){
								applyRemark = value;
							}
						}
					}
					try {
						//简单校验,提交申请applyId为null
						result = processRefundFastpayApplyExcel(realPath, true, null, 
								applyUser, 
								applyRemark);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			} catch (FileUploadException e) {
				result.setMsg("附件上传失败导致申请失败！");
			} catch (Exception e) {
				result.setMsg("服务器端异常导致申请失败！");
			}
			if(!result.isSuccess()){//失败
				RequestDispatcher rd = req.getRequestDispatcher("/refund/result.jsp");
				req.setAttribute("result", result);
				rd.forward(req, resp);
			}else{//成功
				req.getSession(true).setAttribute("result", result);
				resp.sendRedirect(req.getContextPath() + "/refund/success.jsp");
			}
		}else if(methodCall.equals("searchRefundFastpayApplyList")){//查询申请列表
			int currentPage = Integer.valueOf(req.getParameter("currentPage").toString());
			int startRecord = currentPage == 1 ? 0 : ((currentPage - 1) * Integer.valueOf(req.getParameter("pageSize").toString()));
			int totalPage = 0;
			if(req.getParameter("flag").toString().equals("0")){
				startRecord = 0;
			}
			Map resultDetails = refundFastpayService.searchRefundFastpayApplyList(
				req.getParameter("startDate").toString(),
				req.getParameter("endDate").toString(),
				Integer.valueOf(req.getParameter("applyStatus").toString()),
				req.getParameter("applyUser").toString(),
				Boolean.valueOf(req.getParameter("isSignUser")),
				startRecord,
				Integer.valueOf(req.getParameter("pageSize").toString()));
			
			for (Map map : ((List<Map>)resultDetails.get("data"))) {
				json.append("{'id':'");
				json.append(map.get("id")).append("','applyNo':'");
				json.append(map.get("applyNo")).append("','batchNo':'");
				json.append(map.get("batchNo")).append("','filepath':'");
				json.append(map.get("filepath")).append("','applyStatus':'");
				json.append(map.get("applyStatus")).append("','applyUser':'");
				json.append(map.get("applyUser")).append("','applyTime':'");
				json.append(map.get("applyTime")).append("','applyRemark':'");
				json.append(map.get("applyRemark")).append("','auditUser':'");
				json.append(map.get("auditUser")).append("','auditTime':'");
				json.append(map.get("auditTime")).append("','auditRemark':'");
				json.append(map.get("auditRemark")).append("','importUser':'");
				json.append(map.get("importUser")).append("','importTime':'");
				json.append(map.get("importTime")).append("','importRemark':'");
				json.append(map.get("importRemark")).append("'");
				json.append("},");
			}
			
			if (json.toString().endsWith(",")) {
				json = new StringBuffer(json.substring(0, json.length() - 1));
			}
			
			totalPage = (Integer.valueOf(resultDetails.get("rowCount").toString())==0? 1 : (Integer.valueOf(resultDetails.get("rowCount").toString()) + Integer.valueOf(req.getParameter("pageSize").toString()) -1)/Integer.valueOf(req.getParameter("pageSize").toString()));
			
			json = new StringBuffer("{success: true,rowCount:'" + resultDetails.get("rowCount") + "',data:[" + json + "],currentPage:'" + currentPage + "',totalPage:'" + totalPage + "'}");
			
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().write(json.toString());
			resp.getWriter().close();
			return;
			
		}else if(methodCall.equals("auditRefundFastpayApply")){//审批
			Result result = null;
			result = refundFastpayService.auditRefundFastpay(
					req.getParameter("applyId"), 
					req.getParameter("auditUser"),
					//new String(req.getParameter("auditRemark").getBytes("iso-8859-1"), "UTF-8"),
					//req.getParameter("auditRemark"),
					HttpUtil.ConverUnicode(req.getParameter("auditRemark")),//reuncode
					Integer.valueOf(req.getParameter("applyStatus").toLowerCase()));
			if(result.isSuccess()){
				json.append("{success:true}");
			}else{
				json.append("{success:false, msg:'" + result.getMsg() + "'}");
			}
			
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().write(json.toString());
			resp.getWriter().close();
			return;
		}else if(methodCall.equals("importRefundFastpayApply")){//导入
			Result result = null;//简单校验,提交申请applyId为null
			result = processRefundFastpayApplyExcel(null, false, 
					req.getParameter("applyId"), 
					req.getParameter("importUser"), 
					//req.getParameter("importRemark")
					HttpUtil.ConverUnicode(req.getParameter("importRemark"))
				);
			if(result.isSuccess()){
				json.append("{success:true}");
			}else{
				json.append("{success:false, msg:'" + result.getMsg() + "'}");
			}
			
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().write(json.toString());
			resp.getWriter().close();
			return;
		}else if(methodCall.equals("downloadFile")){//下载
			
			Map map = refundFastpayService.getApplyDetailByBatchNo(req.getParameter("applyId"));
			String filePath = map.get("filepath").toString();

			File dbFile = new File(filePath);
			FileInputStream fileIn = null;
			
			fileIn = new FileInputStream(dbFile);
			String contentType;
			contentType = "application/x-xls";
			resp.setContentType(contentType);
			resp.setHeader("Content-Disposition", "filename=" + filePath.substring(filePath.indexOf("upload-")));

			byte[] buffer = new byte[1024 * 512];
			while (true) {
				int bytes = fileIn.read(buffer);
				if (bytes == -1) {
	    	      break;
				}	
				resp.getOutputStream().write(buffer, 0, bytes);
			}
			resp.getOutputStream().flush();
			resp.getOutputStream().close();
			fileIn.close();
			return;
		}
		/*else if(methodCall.equals("test")){
			test();
		}*/
		
	}
	
	/**
	 * 校验批量退款申请excel的合法性<br>
	 * 如果合法，提交申请成功，生成批量退款号，申请状态为待审批
	 * @param realPath
	 * @param isApply true为申请，fasle为提交批量退款
	 * @param operationUser
	 * @param request
	 * @return
	 */
	private Result processRefundFastpayApplyExcel(String realPath, boolean isApply, String applyId, String operator, String operationRemark) {
		if(!isApply){//导入批量退款
			realPath = refundFastpayService.getApplyDetailByBatchNo(applyId).get("filepath").toString();
		}
		// TODO Auto-generated method stub
		Set<String> repetFilter = new HashSet();//过滤重复
		Workbook book = null;
		StringBuffer msg = new StringBuffer();
		Result result = new Result();
		//2011112421847473^0.01^NOT_THIS_PARTNERS_TRAD
		int batchNum = 0;
		StringBuffer batchData = new StringBuffer(); 
		StringBuffer relation = new StringBuffer(); 
		BigDecimal totalRefund = new BigDecimal(0);//退款总金额
		boolean hasError = false;
		try {
			book = Workbook.getWorkbook(new File(realPath));
			  
			Sheet sheet = book.getSheet(0);
			int count = sheet.getRows();
			//生成批量退款笔数及退款数据集
			if(count<=2){//没有数据
				result.setMsg("没有数据!");
				return result;
			}else if(count >= (Integer.valueOf(PropertiesUtil.getProperties("alipay.batchNumLimit", "1000")).intValue()+2)){//超出最大笔数
				result.setMsg("支付宝即时到账批量退款，最大支持" + PropertiesUtil.getProperties("alipay.batchNumLimit", "1000") + "笔!");
				return result;
			}else{//校验合法性
				for(int i=1; i < count-1; i++){//最后一行是合计					
					Cell[] cells = sheet.getRow(i);	
					String outTradeNo = cells[Integer.valueOf(PropertiesUtil.getProperties("outTradeNOIndex", "0"))].getContents().trim();
					if(!repetFilter.contains(outTradeNo)){
						repetFilter.add(outTradeNo);
					}else{
						msg.append("EXCEL第" +  i + "行（不包含表头部分）数据-单品订单号[" + outTradeNo + "]在导入文件的前面部分已经出现请合并为一条退款记录！<br/>");
						hasError = true;
					}
					
					//校验money
					String totalFee = cells[Integer.valueOf(PropertiesUtil.getProperties("totalFeeIndex", "0"))].getContents().trim();
					totalRefund = totalRefund.add(new BigDecimal(totalFee));
					String tradeNo = getTradeNoByOutTradeNo(outTradeNo, new BigDecimal(totalFee));
					
//					if(true){//测试
//						result.setSuccess(true);
//						tradeNo="1111111111111111111111111111111111111111111111111222222222222222222222222222222222222221";
//					}
					
					//校验tradeNo逻辑
					if(tradeNo==null){
						msg.append("EXCEL第" +  i + "行（不包含表头部分）数据-单品订单号[" + outTradeNo + "]获取支付宝交易号失败！<br/>");						
						hasError = true;
					}else if(tradeNo.indexOf(PropertiesUtil.getProperties("alipay.interface.returncode.outoftotalfee", "OUTOFTOTALFEE")) == 0){
						msg.append("EXCEL第" +  i + "行（不包含表头部分）数据-单品订单号[" + outTradeNo + "]退款总金额大于当前可退款金额！<br/>");
						hasError = true;
					}else{
						//拼接relation （商家订单号1^支付宝交易号1#商家订单号2^支付宝交易号2）
						relation.append(outTradeNo).append("^").append(tradeNo);
						if(i<count-2){
							relation.append("#");
						}
					}
					
					String remark = PropertiesUtil.getProperties("refundRemark", "上品折扣支付宝退款");
					//处理逻辑
					batchNum++;
					batchData.append(tradeNo + "^" + totalFee + "^" + remark);
					if(i<count-2){
						batchData.append("#");
					}
				}
			}
			
			if(hasError){
				result.setMsg(msg.toString());
				return result;
			}
			
			boolean validateResult = refundFastpayService.validateRefundFastpay(batchNum, batchData.toString(), relation.toString(), totalRefund);
//			validateResult = true;
			if(!validateResult){
				result.setMsg("未通过系统验证，可能近一周时间内导入过相同的批量退款数据，请核实！");
				result.setSuccess(false);
				return result;
			}
			if(isApply){//提交申请
				result = refundFastpayService.applyRefundFastpay(realPath, operator, operationRemark);
			}else{//审批通过，提交批量退款
				result = refundFastpayService.processRefundFastpayExcel(batchNum, batchData.toString(),
						realPath, relation.toString(), totalRefund, applyId, operator, operationRemark);
			}			
			
		} catch (BiffException e) {
			e.printStackTrace();
			result.setMsg((StringUtils.isNotEmpty(result.getMsg()) ? result.getMsg() : "") + "读取路径为：" + realPath + "的文件产生异常!<br>");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result.setMsg((StringUtils.isNotEmpty(result.getMsg()) ? result.getMsg() : "") + "路径为：" + realPath + "的文件不存在!<br>");
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setMsg((StringUtils.isNotEmpty(result.getMsg()) ? result.getMsg() : "") + "服务端异常，导致导入文件读取失败，请联系开发人员!<br>" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg((StringUtils.isNotEmpty(result.getMsg()) ? result.getMsg() : "") + "服务端异常，导致导入文件读取失败，请联系开发人员!<br>" + e.getMessage());
		}finally {
			try{
				book.close();
			}catch(Exception e){
				
			}
		}
		return result;
	}

	private Result processRefundFastpayExcel(String realPath) {
		// TODO Auto-generated method stub
		Set<String> repetFilter = new HashSet();//过滤重复
		Workbook book = null;
		StringBuffer msg = new StringBuffer();
		Result result = new Result();
		//2011112421847473^0.01^NOT_THIS_PARTNERS_TRAD
		int batchNum = 0;
		StringBuffer batchData = new StringBuffer(); 
		StringBuffer relation = new StringBuffer(); 
		BigDecimal totalRefund = new BigDecimal(0);//退款总金额
		boolean hasError = false;
		try {
			try {
				book = Workbook.getWorkbook(new File(realPath));
			} catch (BiffException e) {
				e.printStackTrace();
				result.setMsg("导入文件读取失败，请重新导入或者联系开发人员!<br>");
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				result.setMsg("导入文件读取失败，请重新导入或者联系开发人员!<br>");
				return result;
			} 
			Sheet sheet = book.getSheet(0);
			int count = sheet.getRows();
			//生成批量退款笔数及退款数据集
			if(count<=2){//没有数据
				result.setMsg("没有数据!");
				return result;
			}else if(count >= (Integer.valueOf(PropertiesUtil.getProperties("alipay.batchNumLimit", "1000")).intValue()+2)){//超出最大笔数
				result.setMsg("支付宝即时到账批量退款，最大支持" + PropertiesUtil.getProperties("alipay.batchNumLimit", "1000") + "笔!");
				return result;
			}else{//校验合法性
				for(int i=1; i < count-1; i++){//最后一行是合计					
					Cell[] cells = sheet.getRow(i);	
					String outTradeNo = cells[Integer.valueOf(PropertiesUtil.getProperties("outTradeNOIndex", "0"))].getContents().trim();
					if(!repetFilter.contains(outTradeNo)){
						repetFilter.add(outTradeNo);
					}else{
						msg.append("EXCEL第" +  i + "行（不包含表头部分）数据-单品订单号[" + outTradeNo + "]在导入文件的前面部分已经出现请合并为一条退款记录！<br/>");
						hasError = true;
					}
					
					//校验money
					String totalFee = cells[Integer.valueOf(PropertiesUtil.getProperties("totalFeeIndex", "0"))].getContents().trim();
					totalRefund = totalRefund.add(new BigDecimal(totalFee));
					String tradeNo = getTradeNoByOutTradeNo(outTradeNo, new BigDecimal(totalFee));
					
					//校验tradeNo逻辑
					if(tradeNo==null){
						msg.append("EXCEL第" +  i + "行（不包含表头部分）数据-单品订单号[" + outTradeNo + "]获取支付宝交易号失败！<br/>");						
						hasError = true;
					}else if(tradeNo.indexOf(PropertiesUtil.getProperties("alipay.interface.returncode.outoftotalfee", "OUTOFTOTALFEE")) == 0){
						msg.append("EXCEL第" +  i + "行（不包含表头部分）数据-单品订单号[" + outTradeNo + "]退款总金额大于当前可退款金额！<br/>");
						hasError = true;
					}else{
						//拼接relation （商家订单号1^支付宝交易号1#商家订单号2^支付宝交易号2）
						relation.append(outTradeNo).append("^").append(tradeNo);
						if(i<count-2){
							relation.append("#");
						}
					}
					
					String remark = PropertiesUtil.getProperties("refundRemark", "上品折扣支付宝退款");
					//处理逻辑
					batchNum++;
					batchData.append(tradeNo + "^" + totalFee + "^" + remark);
					if(i<count-2){
						batchData.append("#");
					}
				}
			}
			
			if(hasError){
				result.setMsg(msg.toString());
				return result;
			}
			
			result = refundFastpayService.processRefundFastpayExcel(batchNum, batchData.toString(), realPath, relation.toString(), totalRefund);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setMsg("服务端异常，导致导入文件读取失败，请联系开发人员!<br>" + e.getMessage());
		} finally {
			book.close();
		}
		return result;
	}
	
	private String getTradeNoByOutTradeNo(String outTradeNo, BigDecimal refundMoney){
		StringBuffer result = new StringBuffer();
    	URL U = null;
    	BufferedReader in = null;
    	String tradeNo = null;
    	String sign = null;
    	Map<String, String> sParaTemp = new HashMap();
    	sParaTemp.put("out_trade_no", outTradeNo);
        sParaTemp.put("trade_no", null);
    	sParaTemp.put("service", "single_trade_query");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        sign = AlipayCore.buildMysign(sPara);
        
    	try {
//			String url = "https://www.alipay.com/cooperate/gateway.do?" +
			String url = AlipayService.ALIPAY_GATEWAY_NEW +
					"_input_charset=utf-8" +
					"&sign=" + sign + 
					"&_input_charset=utf-8" +
					"&sign_type=MD5" +
					"&service=single_trade_query" +
					"&partner=" + AlipayConfig.partner + 
					"&out_trade_no=" + outTradeNo;
			U = new URL(url);
			URLConnection connection = U.openConnection();
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = in.readLine()) != null) {
				result.append(line);
				//System.out.println("-------------------\n" + new String(line.getBytes("gbk"), "utf-8"));
			}
			
			StringReader sr = new StringReader(new String(result.toString().getBytes("gbk"), "utf-8"));
			BufferedReader br = new BufferedReader(sr);
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance("net.shopin.alipay.entity");
				Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
				SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
				Schema schema = schemaFactory.newSchema(new File(PropertiesUtil.getProperties("alipay.schema.refundfastpay.path", "E:/上品/支付宝批量退货/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd")));
				unMarshaller.setSchema(schema);
				Alipay alipay = (Alipay) unMarshaller.unmarshal(U);

				if(alipay.getIsSuccess().equals("T")){
					//tradeNo = alipay.getResponse().get(0).getTrade().get(0).getTradeNo();
					tradeNo = alipay.getResponse().getTrade().getTradeNo();
					//校验是否超过三个月
					
					//校验Money
					BigDecimal totalFee = new BigDecimal(alipay.getResponse().getTrade().getTotalFee());//总金额
					BigDecimal toBuyerFee = new BigDecimal(StringUtils.isNotEmpty(alipay.getResponse().getTrade().getToBuyerFee()) ? alipay.getResponse().getTrade().getToBuyerFee() : "0");//退款金额
					BigDecimal leftMoney = totalFee.subtract(toBuyerFee);
					if(leftMoney.compareTo(refundMoney)==-1){//剩下的钱小于退款金额
						tradeNo = PropertiesUtil.getProperties("alipay.interface.returncode.outoftotalfee", "OUTOFTOTALFEE") + tradeNo;
					}
				}
				return tradeNo;
				
				
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//br.close();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private String test(){
		
		//读取excel文件
		String realPath = "C:/Users/wchao/Desktop/上品214快捷日获奖名单.xls";
		Workbook book = null;
		
		WritableWorkbook  book2 = null;
		WritableSheet sheet2 = null;
		Connection conn = null;
		conn = JDBCUtil.getConnection(JDBCUtil.getUrl("192.168.1.21", "1521", "danpin1", 0), 
				"dev_user", 
				"dev_user");
		try {
			try {
				book2 = Workbook.createWorkbook(new File("C:/Users/wchao/Desktop/上品214快捷日获奖名单111.xls"));
				sheet2 = book2.createSheet("第1页", 0);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			book = Workbook.getWorkbook(new File(realPath));
			  
			Sheet sheet = book.getSheet(0);
			int count = sheet.getRows();
			System.out.println("--------------------------------------");
			for(int i=1; i < count; i++){//最后一行是合计					
				Cell[] cells = sheet.getRow(i);	
				String tradeNo = cells[2].getContents().trim();
				
				StringBuffer result = new StringBuffer();
		    	URL U = null;
		    	BufferedReader in = null;
		    	String sign = null;
		    	Map<String, String> sParaTemp = new HashMap();
		    	sParaTemp.put("out_trade_no", null);
		    	sParaTemp.put("trade_no", tradeNo);
		    	sParaTemp.put("service", "single_trade_query");
		    	sParaTemp.put("partner", "2088002692486430");
		    	sParaTemp.put("_input_charset", "utf-8");
		        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		        //生成签名结果
		        sign = AlipayCore.buildMysign(sPara);//752cc62e72503d01c4cc93b654906918
		        
		    	try {
					String url = "https://www.alipay.com/cooperate/gateway.do?_input_charset=utf-8" +
						"&sign=" + sign + 
						"&_input_charset=utf-8" +
						"&sign_type=MD5" +
						"&service=single_trade_query" +
						"&partner=2088002692486430" + 
						"&trade_no=" + tradeNo;
					U = new URL(url);
					URLConnection connection = U.openConnection();
					connection.connect();
					in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String line;
					while((line = in.readLine()) != null) {
						result.append(line);
						//System.out.println("-------------------\n" + new String(line.getBytes("gbk"), "utf-8"));
					}
					
					StringReader sr = new StringReader(new String(result.toString().getBytes("gbk"), "utf-8"));
					BufferedReader br = new BufferedReader(sr);
					try {
						JAXBContext jaxbContext = JAXBContext.newInstance("net.shopin.alipay.entity");
						Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
						SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
						Schema schema = schemaFactory.newSchema(new File("E:/上品/支付宝批量退货/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd"));
						unMarshaller.setSchema(schema);
						Alipay alipay = (Alipay) unMarshaller.unmarshal(U);
						String outTradeNo = alipay.getResponse().getTrade().getOutTradeNo();
						//根据单品订单号查询收货人姓名	详细邮寄地址	联系电话	省份	城市	邮编
						System.out.println("SELECT d.RECEPT_NAME, d.RECEPT_ADDRESS, d.RECEPT_PHONE, d.INCEPT_PROVINCE, d.INCEPT_CITY, d.INCEPT_POSTCODE " +
"FROM DELIVERY d WHERE d.SID=(SELECT o.DELIVERY_SID FROM ORDERS o WHERE o.ORDER_NO='" + outTradeNo + "')");
						
						//循环结果集
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT d.RECEPT_NAME, d.RECEPT_ADDRESS, d.RECEPT_PHONE, d.INCEPT_PROVINCE, d.INCEPT_CITY, d.INCEPT_POSTCODE " +
"FROM DELIVERY d WHERE d.SID=(SELECT o.DELIVERY_SID FROM ORDERS o WHERE o.ORDER_NO='" + outTradeNo + "')");
						while(rs.next()){
							Label cell0 = new Label(0, i, tradeNo);
							Label cell1 = new Label(1, i, outTradeNo);
							Label cell2 = new Label(2, i, rs.getString("RECEPT_NAME"));
							Label cell3 = new Label(3, i, rs.getString("RECEPT_ADDRESS"));
							Label cell4 = new Label(4, i, rs.getString("RECEPT_PHONE"));
							Label cell5 = new Label(5, i, rs.getString("INCEPT_PROVINCE"));
							Label cell6 = new Label(6, i, rs.getString("INCEPT_CITY"));
							Label cell7 = new Label(7, i, rs.getString("INCEPT_POSTCODE"));
							
							sheet2.addCell(cell0);
							sheet2.addCell(cell1);
							sheet2.addCell(cell2);
							sheet2.addCell(cell3);
							sheet2.addCell(cell4);
							sheet2.addCell(cell5);
							sheet2.addCell(cell6);
							sheet2.addCell(cell7);
						}			
				    	JDBCUtil.close(rs, stmt, null);
				    	
						
						
					}catch(Exception e){
						e.printStackTrace();
					}
				
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}

		    }
			try {
				book2.write();
				book2.close();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
