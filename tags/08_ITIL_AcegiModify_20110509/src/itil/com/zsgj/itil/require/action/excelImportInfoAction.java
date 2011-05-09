package com.zsgj.itil.require.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.itil.require.entity.ERP_NormalNeed;
import com.zsgj.itil.require.entity.RequireFactoryInfo;

public class excelImportInfoAction extends BaseDispatchAction  {
	
	private Service service = (Service) ContextHolder.getBean("baseService");
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	
	/**
	 * Excel导入工厂信息Action 
	 * 
	 * @Class Name upload
	 * @author zhangzy
	 * @Create In 04 05, 2010 TODO
	 */
	@SuppressWarnings("unchecked")
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		request.setCharacterEncoding("utf-8");
		Map<String,Object> resultMap = null;
		String json = "";
		String realPath = null;
		try {
			String msg = "";
			while (iter.hasNext()) {
				// 把实体换成map，然后保存
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					String systemFileName = "uploadExcelInfo-" + System.currentTimeMillis() + ".xls";
					String filePath = FSP + "tempUpload" + FSP + systemFileName;
					realPath = request.getRealPath(FSP) + filePath;
					
					File uploadedFile = new File(realPath);
					item.write(uploadedFile);
					int p1 = fileName.lastIndexOf("\\");
					int p2 = fileName.lastIndexOf("/");
					int p = p1>p2?(p1>0?p1:-1):(p2>0?p2:-1);
					fileName = fileName.substring(p+1);
					resultMap = parseXLSX(realPath);
				}
			}
			File file = new File(realPath);
			file.delete();					//读取内容后删除服务上传的文件
			msg = (String) resultMap.get("msg");
			if("".equals(msg)){
				json = "{success:true,successFlg:'allSuccess',resultJson:"+resultMap.get("returnString")+"}";
			}else if("记录数少于一条".equals(msg)){
				json = "{success:true,successFlg:'recodeNull',msg:\""+resultMap.get("msg")+"\"}";
			}else{
				int successCount = (Integer) resultMap.get("successCount");
				int falsCount = (Integer) resultMap.get("falsCount");
				json = "{success:true,successFlg:'partSuccess',successCount:'"+successCount+"',falsCount:'"+falsCount+"',msg:'"+msg+"'}";
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			json = "{success:true,successFlg:'error',msg:'错误提示："+e.getMessage()+"<br/>'}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}
	/**
	 * 解析Excel的工厂信息放入数据库 
	 * 
	 * @Class Name parseXLSX
	 * @author zhangzy
	 * @Create In 04 05, 2010 TODO
	 */
	@SuppressWarnings("unchecked")
	private Map parseXLSX(String filePath,String id)throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String msg = "";
		FileInputStream fis = new FileInputStream(filePath);
		
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		Integer rowCount = sheet.getLastRowNum();
		int successCount = 0;
		if(rowCount<1){
			msg = "记录数少于一条";
			resultMap.put("msg", msg);
			return resultMap;
		}
		for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
			String rowMs = "";
			HSSFRow row = sheet.getRow(rowIndex);
			 if (row != null) {
			      //工厂编号校验
			      HSSFCell factoryIdCell = row.getCell((short)0);
			      Object factoryIdCellObj = this.getCellString(factoryIdCell);
			      String factoryIdCellStr = null;
			      if(factoryIdCellObj==null){
			    	  rowMs += "第"+(rowIndex+1)+"行:工厂编号为空! ";
			    	  msg += rowMs+"<br>";
			    	  continue ;
			      }else{
			    	  factoryIdCellStr = factoryIdCellObj.toString();
				      
				      if(factoryIdCellStr.length()!=4){
				    	  rowMs += "第"+(rowIndex+1)+"行:工厂编号\""+factoryIdCellStr+"\"长度不为4个字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;
				      }else if(this.containCnCode(factoryIdCellStr)){
				    	  rowMs += "第"+(rowIndex+1)+"行:工厂编号\""+factoryIdCellStr+"\"包含中文字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;				    	  
				      }
			      }
			      
			      //库存地编号校验
			      HSSFCell stockAddressIdCell = row.getCell((short)1);
			      Object stockAddressIdCellObject = this.getCellString(stockAddressIdCell);
			      String stockAddressIdCellStr = null;
			      if(stockAddressIdCellObject==null){
			    	  if("".equals(rowMs)){
			    		  rowMs += "第"+(rowIndex+1)+"行:";
			    	  }
			    	  rowMs += "库存地编号为空! ";
			    	  msg += rowMs+"<br>";
			    	  continue ;
			      }else{
				      stockAddressIdCellStr = stockAddressIdCellObject.toString();
				      if(stockAddressIdCellStr.length()!=4){
				    	  rowMs += "第"+(rowIndex+1)+"行:库存地编号\""+stockAddressIdCellStr+"\"长度不为4个字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;
				      }else if(this.containCnCode(stockAddressIdCellStr)){
				    	  rowMs += "第"+(rowIndex+1)+"行:库存地编号\""+stockAddressIdCellStr+"\"不能为中文字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;				    	  
				      }
			      }			      
			      if("".equals(rowMs)){//数据库保存数据
			    	  String product = "{'newRecord':true,'itil_req_RequireFactoryInfo$id':null,'itil_req_RequireFactoryInfo$factoryId':'"+factoryIdCellStr+"','itil_req_RequireFactoryInfo$stockAddressId':'"+stockAddressIdCellStr+"'}";
			    	  JSONArray ja = JSONArray.fromObject("[" + product + "]");
			    	  RequireFactoryInfo rfi = null;
			    	  ERP_NormalNeed erp = (ERP_NormalNeed) getService().find(ERP_NormalNeed.class, id);			    	  
			    	  for (int i = 0; i < ja.size(); i++) {
						HashMap productMap = new HashMap();
						JSONObject opl = (JSONObject) ja.get(i);
						Iterator itProduct = opl.keys();
						while (itProduct.hasNext()) {
							String key = (String) itProduct.next();
							String value = opl.getString(key);

							if(("flm_ProjectTestReport$id").equals(key)&&value!="null"){
								rfi = (RequireFactoryInfo) super.getService().find(RequireFactoryInfo.class, value);
							}
							key = StringUtils.substringAfter(key, "$");
							value = value.trim();
							productMap.put(key, value);
						}
						productMap.put("requireData",erp);
						rfi =(RequireFactoryInfo) metaDataManager.saveEntityData(RequireFactoryInfo.class, productMap);
					}					    	  
			    	  successCount++;
			      }else{
			    	  msg += rowMs+"<br>";
			      }
			 }
		}
		
		resultMap.put("msg", msg);
		resultMap.put("successCount", successCount);
		resultMap.put("falsCount", rowCount - successCount);
		return resultMap;
	}
	/**
	 * 解析Excel的工厂信息返回json，不保存到数据库 
	 * 
	 * @Class Name parseXLSX
	 * @author zhangzy
	 * @Create In 04 05, 2010 TODO
	 */
	@SuppressWarnings("unchecked")
	private Map parseXLSX(String filePath)throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String msg = "";
		FileInputStream fis = new FileInputStream(filePath);
		
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		Integer rowCount = sheet.getLastRowNum();
		int successCount = 0;
		if(rowCount<1){
			msg = "记录数少于一条";
			resultMap.put("msg", msg);
			return resultMap;
		}
		StringBuilder sb = new StringBuilder("[");
		
		for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
			String rowMs = "";
			HSSFRow row = sheet.getRow(rowIndex);
			if(rowIndex==0){
				if(row != null){
				      HSSFCell factoryIdTitle = row.getCell((short)0);
				      Object factoryIdCellObj = this.getCellString(factoryIdTitle);
				      String factoryIdTitleStr = null;
				      HSSFCell stockAddressIdTitle = row.getCell((short)1);
				      Object stockAddressIdTitleObj = this.getCellString(stockAddressIdTitle);
				      String stockAddressIdTitleStr = null;		      
				      if(factoryIdCellObj != null){
				    	  factoryIdTitleStr = factoryIdCellObj.toString();
				    	  stockAddressIdTitleStr = stockAddressIdTitleObj.toString();
				    	  if(!factoryIdTitleStr.equals("工厂编号")||!stockAddressIdTitleStr.equals("库存地编号")){
				    		  msg = "此模板非标准上传模板，请下载标准模板";  
				    		  break;
				    	  }
				      }else{
				    	  msg = "此模板非标准上传模板，请下载标准模板"; 
				    	  break;
				      }
				}
			}else{
			 if (row != null) {
			      //工厂编号校验
			      HSSFCell factoryIdCell = row.getCell((short)0);
			      Object factoryIdCellObj = this.getCellString(factoryIdCell);
			      String factoryIdCellStr = null;
			      if(factoryIdCellObj==null){
			    	  rowMs += "第"+(rowIndex+1)+"行:工厂编号为空! ";
			    	  msg += rowMs+"<br>";
			    	  continue ;
			      }else{
			    	  factoryIdCellStr = factoryIdCellObj.toString();
			    	  if(factoryIdCellStr.contains(".")){//去掉小数点后面的字符
			    		  factoryIdCellStr = factoryIdCellStr.substring(0,factoryIdCellStr.indexOf("."));
			    	  }
				      
				      if(factoryIdCellStr.length()!=4){//校验长度
				    	  rowMs += "第"+(rowIndex+1)+"行:工厂编号\""+factoryIdCellStr+"\"长度不为4个字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;
				      }else if(this.containCnCode(factoryIdCellStr)){//校验中文
				    	  rowMs += "第"+(rowIndex+1)+"行:工厂编号\""+factoryIdCellStr+"\"包含中文字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;				    	  
				      }
			      }
			      
			      //库存地编号校验
			      HSSFCell stockAddressIdCell = row.getCell((short)1);
			      Object stockAddressIdCellObject = this.getCellString(stockAddressIdCell);
			      String stockAddressIdCellStr = null;
			      if(stockAddressIdCellObject==null){
			    	  if("".equals(rowMs)){
			    		  rowMs += "第"+(rowIndex+1)+"行:";
			    	  }
			    	  rowMs += "库存地编号为空! ";
			    	  msg += rowMs+"<br>";
			    	  continue ;
			      }else{
				      stockAddressIdCellStr = stockAddressIdCellObject.toString();
			    	  if(stockAddressIdCellStr.contains(".")){//去掉小数点后面的字符
			    		  stockAddressIdCellStr = stockAddressIdCellStr.substring(0,stockAddressIdCellStr.indexOf("."));
			    	  }				      
				      if(stockAddressIdCellStr.length()!=4){//校验长度
				    	  rowMs += "第"+(rowIndex+1)+"行:库存地编号\""+stockAddressIdCellStr+"\"长度不为4个字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;
				      }else if(this.containCnCode(stockAddressIdCellStr)){//校验中文
				    	  rowMs += "第"+(rowIndex+1)+"行:库存地编号\""+stockAddressIdCellStr+"\"不能为中文字符! ";
				    	  msg += rowMs+"<br>";
				    	  continue ;				    	  
				      }
			      }
//			      //其他列如果有数据，说明不是合法模板，excel最大255列
//			      for(int i = 2;i<256;i++){
//				      HSSFCell otherCell = row.getCell((short)i);
//				      Object otherCellObject = this.getCellString(otherCell);
//				      if(otherCellObject!=null){
//				    	  msg = "此模板非标准上传模板，请下载标准模板"; 
//				    	  break;
//				      }
//			      }			      
			      if("".equals(rowMs)){//拼接json
			    	  if(rowIndex !=1){
			    		  sb.append(",");
			    	  }
//			    	  String product = "{'newRecord':true,'itil_req_RequireFactoryInfo$id':null,'itil_req_RequireFactoryInfo$factoryId':'"+factoryIdCellStr+"','itil_req_RequireFactoryInfo$stockAddressId':'"+stockAddressIdCellStr+"'}";
			    	  sb.append("{newRecord:true,itil_req_RequireFactoryInfo$id:null,itil_req_RequireFactoryInfo$factoryId:'");
			    	  sb.append(factoryIdCellStr);
			    	  sb.append("',itil_req_RequireFactoryInfo$stockAddressId:'");
			    	  sb.append(stockAddressIdCellStr);
			    	  sb.append("'}");
			    	  successCount++;
			      }else{
			    	  msg += rowMs+"<br>";
			      }
			 }
			}
		}
		sb.append("]");
		resultMap.put("returnString", sb.toString());
		resultMap.put("msg", msg);
		resultMap.put("successCount", successCount);
		resultMap.put("falsCount", rowCount - successCount);
		return resultMap;
	}	
	/**
	* 获得单元格中的内容
	* @param cell
	* @return
	*/
	private Object getCellString(HSSFCell cell){
	   Object result = null;
	   if (cell != null) {

	    int cellType = cell.getCellType();
	   
	    switch(cellType){
	   
	    case HSSFCell.CELL_TYPE_STRING :
	     result = cell.getRichStringCellValue().getString();
	     break;
	    case HSSFCell.CELL_TYPE_NUMERIC:
	     result=cell.getNumericCellValue();
	     break;
	    case HSSFCell.CELL_TYPE_FORMULA:
	     result = cell.getNumericCellValue();
	     break;
	    case HSSFCell.CELL_TYPE_ERROR:
	     result=null;
	     break;
	    case HSSFCell.CELL_TYPE_BOOLEAN:
	     result=cell.getBooleanCellValue();
	     break;
	    case HSSFCell.CELL_TYPE_BLANK:
	     result=null;
	     break;
	    }
	   }
	   return result;
	}
	/**
	 * 取消科学计数法
	 * @Methods Name chansforFlodToCode
	 * @return String
	 */
	private String chansforFlodToCode(String str)throws Exception{
		if(null != str && str.indexOf(".") != -1 && str.indexOf("E") != -1) {
            DecimalFormat df = new DecimalFormat();
            str = df.parse(str).toString();
		}
		int index=str.indexOf(".");
		if(index!=-1)	str=str.substring(0, index);
		return str;
	}
	/**
	 * 包含中文字符校验
	 * @Methods Name chansforFlodToCode
	 * @return String
	 */
	private boolean containCnCode(String str){        
		for (int i = 0; i < str.length(); i++) {   
			String bb = str.substring(i, i+1);         
			//生成一个Pattern,同时编译一个正则表达式.         
			boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);       
			if(cc==true)  
				return true;			
		}
		return false;
	}
		

}
