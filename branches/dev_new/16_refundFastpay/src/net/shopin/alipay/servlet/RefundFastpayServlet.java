package net.shopin.alipay.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import net.shopin.alipay.entity.Alipay;
import net.shopin.alipay.service.RefundFastpayService;
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
		
		//ת������
		StringBuffer json = new StringBuffer();
		String methodCall = req.getParameter("methodCall");
		if(methodCall.equals("getResultDetailByBatchNo")){//ͨ��ԭ���������˿����κŻ�ȡ�˿�����
			if(req.getParameter("batchNo")==null){
				json.append("{success:false,msg:'����[ԭ�����˿����κ�]����Ϊ��'}");
			}else{
				Map resultDetails = refundFastpayService.getResultDetailByBatchNo(req.getParameter("batchNo"));
				if(CollectionUtils.isEmpty(resultDetails)){
					json.append("{success:false,msg:'ԭ�����˿����κ�Ϊ:" +req.getParameter("batchNo") + "�������˿���Ϣ������'}");
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
					json.append("},msg:'��ȡԭ�����˿����κ�Ϊ:" +req.getParameter("batchNo") + "�������˿���Ϣ�ɹ���'}");
				}
			}			
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().write(json.toString());
			resp.getWriter().close();
			return;
		}else if(methodCall.equals("batchRefundImport")){//�����˿��
			//�����ļ�
			
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
								
								if(!result.isSuccess()){//ʧ��
									RequestDispatcher rd = req.getRequestDispatcher("/refund/result.jsp");
									req.setAttribute("result", result);
									rd.forward(req, resp);
								}else{//�ɹ�
									req.getSession(true).setAttribute("result", result);
									resp.sendRedirect(req.getContextPath() + "/refund/result.jsp");
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
		}	
		
	}

	private Result processRefundFastpayExcel(String realPath) {
		// TODO Auto-generated method stub
		Set<String> repetFilter = new HashSet();//�����ظ�
		Workbook book = null;
		StringBuffer msg = new StringBuffer();
		Result result = new Result();
		//2011112421847473^0.01^NOT_THIS_PARTNERS_TRAD
		int batchNum = 0;
		StringBuffer batchData = new StringBuffer(); 
		StringBuffer relation = new StringBuffer(); 
		BigDecimal totalRefund = new BigDecimal(0);//�˿��ܽ��
		boolean hasError = false;
		try {
			try {
				book = Workbook.getWorkbook(new File(realPath));
			} catch (BiffException e) {
				e.printStackTrace();
				result.setMsg("�����ļ���ȡʧ�ܣ������µ��������ϵ������Ա!<br>");
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				result.setMsg("�����ļ���ȡʧ�ܣ������µ��������ϵ������Ա!<br>");
				return result;
			} 
			Sheet sheet = book.getSheet(0);
			int count = sheet.getRows();
			//���������˿�������˿����ݼ�
			if(count<=2){//û������
				result.setMsg("û������!");
				return result;
			}else if(count >= (Integer.valueOf(PropertiesUtil.getProperties("alipay.batchNumLimit", "1000")).intValue()+2)){//����������
				result.setMsg("֧������ʱ���������˿���֧��" + PropertiesUtil.getProperties("alipay.batchNumLimit", "1000") + "��!");
				return result;
			}else{//У��Ϸ���
				for(int i=1; i < count-1; i++){//���һ���Ǻϼ�					
					Cell[] cells = sheet.getRow(i);	
					String outTradeNo = cells[Integer.valueOf(PropertiesUtil.getProperties("outTradeNOIndex", "0"))].getContents().trim();
					if(!repetFilter.contains(outTradeNo)){
						repetFilter.add(outTradeNo);
					}else{
						msg.append("EXCEL��" +  i + "�У���������ͷ���֣�����-��Ʒ������[" + outTradeNo + "]�ڵ����ļ���ǰ�沿���Ѿ�������ϲ�Ϊһ���˿��¼��<br/>");
						hasError = true;
					}
					
					//У��money
					String totalFee = cells[Integer.valueOf(PropertiesUtil.getProperties("totalFeeIndex", "0"))].getContents().trim();
					totalRefund = totalRefund.add(new BigDecimal(totalFee));
					String tradeNo = getTradeNoByOutTradeNo(outTradeNo, new BigDecimal(totalFee));
					
					//У��tradeNo�߼�
					if(tradeNo==null){
						msg.append("EXCEL��" +  i + "�У���������ͷ���֣�����-��Ʒ������[" + outTradeNo + "]��ȡ֧�������׺�ʧ�ܣ�<br/>");						
						hasError = true;
					}else if(tradeNo.indexOf(PropertiesUtil.getProperties("alipay.interface.returncode.outoftotalfee", "OUTOFTOTALFEE")) == 0){
						msg.append("EXCEL��" +  i + "�У���������ͷ���֣�����-��Ʒ������[" + outTradeNo + "]�˿��ܽ����ڵ�ǰ���˿��<br/>");
						hasError = true;
					}else{
						//ƴ��relation ���̼Ҷ�����1^֧�������׺�1#�̼Ҷ�����2^֧�������׺�2��
						relation.append(outTradeNo).append("^").append(tradeNo);
						if(i<count-2){
							relation.append("#");
						}
					}
					
					String remark = PropertiesUtil.getProperties("refundRemark", "��Ʒ�ۿ�֧�����˿�");
					//�����߼�
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
			result.setMsg("������쳣�����µ����ļ���ȡʧ�ܣ�����ϵ������Ա!<br>" + e.getMessage());
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
        //����ǩ�����
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
				Schema schema = schemaFactory.newSchema(new File(PropertiesUtil.getProperties("alipay.schema.refundfastpay.path", "E:/��Ʒ/֧���������˻�/java/refund_fastpay_by_platform_nopwd_jsp_utf8/src/net/shopin/alipay/entity/singleTradeQuery.xsd")));
				unMarshaller.setSchema(schema);
				Alipay alipay = (Alipay) unMarshaller.unmarshal(U);

				if(alipay.getIsSuccess().equals("T")){
					//tradeNo = alipay.getResponse().get(0).getTrade().get(0).getTradeNo();
					tradeNo = alipay.getResponse().getTrade().getTradeNo();
					//У���Ƿ񳬹�������
					
					//У��Money
					BigDecimal totalFee = new BigDecimal(alipay.getResponse().getTrade().getTotalFee());//�ܽ��
					BigDecimal toBuyerFee = new BigDecimal(StringUtils.isNotEmpty(alipay.getResponse().getTrade().getToBuyerFee()) ? alipay.getResponse().getTrade().getToBuyerFee() : "0");//�˿���
					BigDecimal leftMoney = totalFee.subtract(toBuyerFee);
					if(leftMoney.compareTo(refundMoney)==-1){//ʣ�µ�ǮС���˿���
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
	
}
