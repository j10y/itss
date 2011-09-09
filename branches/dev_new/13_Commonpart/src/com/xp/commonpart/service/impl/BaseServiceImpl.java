package com.xp.commonpart.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.xp.commonpart.Page;
import com.xp.commonpart.bean.SysTemUserLog;
import com.xp.commonpart.dao.BaseDao;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.util.DateUtil;
import com.xp.commonpart.util.PropertiesUtil;
import com.xp.commonpart.util.WebUtils;
/**
 * 基于hibernate的基础方法实现,包括简单的增删查改
 * @author tongjp
 *
 */
public class BaseServiceImpl implements BaseService {
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public List findObjectByLikeParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String[] orderNames, String[] ascoOrdescs) {
		// TODO Auto-generated method stub
		return baseDao.findObjectByLikeParsOrder(clazz, propNames, propValues, orderNames, ascoOrdescs);
	}

	
	public List findObjectByPar(Class clazz, String propName, Object propValue) {
		// TODO Auto-generated method stub
		return baseDao.findObjectByPar(clazz, propName, propValue);
	}

	
	public List findObjectByParOrder(Class clazz, String propName,
			Object propValue, String orderName, String ascoOrdesc) {
		// TODO Auto-generated method stub
		return baseDao.findObjectByParOrder(clazz, propName, propValue, orderName, ascoOrdesc);
	}

	
	public List findObjectByPars(Class clazz, String[] propNames,
			Object[] propValues) {
		// TODO Auto-generated method stub
		return baseDao.findObjectByPars(clazz, propNames, propValues);
	}

	
	public List findObjectByParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String orderName, String ascoOrdesc) {
		// TODO Auto-generated method stub
		return baseDao.findObjectByParsOrder(clazz, propNames, propValues, orderName, ascoOrdesc);
	}

	
	public List findObjectListByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap) {
		// TODO Auto-generated method stub
		return baseDao.findObjectListByParamAndOrder(clazz, proMap, orderMap);
	}

	
	public void remove(Object obj) {
		// TODO Auto-generated method stub
		baseDao.remove(obj);
	}

	
	public Object save(Object obj, Class clazz, String key) {
		// TODO Auto-generated method stub
		return baseDao.save(obj, clazz, key);
	}
	
	public Long getUniqueId(){
		return baseDao.getUniqueId();
	}
	public Page findObjectPageByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap, HttpServletRequest request) {
		// TODO Auto-generated method stub
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		int pageSize=Integer.parseInt(PropertiesUtil.getProperties("system.pagesize", "12"));
		if(request.getParameter("pageSize")!=null){
			pageSize=Integer.parseInt(request.getParameter("pageSize"));
		}else if(request.getParameter("rows")!=null){
			pageSize=Integer.parseInt(request.getParameter("rows"));
		}
		if(request.getParameter("order")!=null&&request.getParameter("sort")!=null){
			orderMap.put(request.getParameter("sort"), request.getParameter("order"));
		}
		return baseDao.findObjectPageByParamAndOrder(clazz, proMap, orderMap, page, pageSize);
	}
	public Page findObjectPageByMultParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap proMultMap,
			LinkedHashMap orderMap, HttpServletRequest request) {
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		int pageSize=Integer.parseInt(PropertiesUtil.getProperties("system.pagesize", "12"));
		if(request.getParameter("pageSize")!=null){
			pageSize=Integer.parseInt(request.getParameter("pageSize"));
		}else if(request.getParameter("rows")!=null){
			pageSize=Integer.parseInt(request.getParameter("rows"));
		}
		if(request.getParameter("order")!=null&&request.getParameter("sort")!=null){
			orderMap.put(request.getParameter("sort"), request.getParameter("order"));
		}
		// TODO Auto-generated method stub
		return baseDao.findObjectPageByMultParamAndOrder(clazz, proMap, proMultMap, orderMap, page, pageSize);
	}
	public void saveSysTemUserLog(HttpServletRequest request,String operateDetail) {
		// TODO Auto-generated method stub
		SysTemUserLog sysTemUserLog=new SysTemUserLog();
		sysTemUserLog.setOperateDate(new Date());
		sysTemUserLog.setOperateIP(request.getRemoteAddr());
		String userName = "anonymity";
		if(request.getSession().getAttribute("userMap")!=null){
			Map userMap=(Map) request.getSession().getAttribute("userMap");
			userName=userMap.get("USERISN").toString();
		}
		sysTemUserLog.setOperateUserName(userName);
		sysTemUserLog.setOperateDetail(operateDetail);
		baseDao.save(sysTemUserLog, SysTemUserLog.class, "id");
	}
	public Page findObjectPageByMultParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap proMultMap,
			LinkedHashMap orderMap, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return baseDao.findObjectPageByMultParamAndOrder(clazz, proMap, proMultMap, orderMap, pageNo, pageSize);
	}
	
	public String exportData(String fileRootPath, String sheetName,
			String filePrefix,String[] titles, String[] proNames, List dataList,
			String exportType) {
		// TODO Auto-generated method stub
		HSSFWorkbook wb=new HSSFWorkbook();
      	HSSFCellStyle cellStyle=wb.createCellStyle();
      	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置标题上下居中
      	int totalCount =dataList.size();
     	int pageSize = 5000;
     	int sheetSum = 0;
     	if (totalCount % pageSize == 0){
     		sheetSum = totalCount / pageSize;
     	}else{
     		sheetSum = totalCount / pageSize + 1;
     	}
     	for(int ii=0; ii<sheetSum; ii++){
     		HSSFSheet sheet = wb.createSheet();
	     	wb.setSheetName(ii,sheetName+(ii+1));
	     	
//			建立标题行，0行
	     	HSSFRow row=sheet.createRow(0);
	      	row.setHeight((short)400);		
	      	for(int iii=0;iii<titles.length;iii++){
	      		HSSFCell cell=row.createCell((short)iii); //建立标题记录行，从第1行开始 *******
	     		cell.setCellStyle(cellStyle);
	         	cell.setCellValue(titles[iii]);
	      	}
	      
	      	for(int rows=0;rows<dataList.size();rows++){
	      		//System.out.println(rows);
	      		if(exportType.equals("maplist")){
	      			Map npl=(Map) dataList.get(rows);
		      		HSSFRow rowj = null; //建立当前记录的行 *******
					try {
						rowj = sheet.createRow((short) (rows + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(int iii=0;iii<proNames.length;iii++){
						HSSFCell cellk=rowj.createCell((short)iii);
		      			Object ob=npl.get(proNames[iii]);
		      			cellk.setCellValue(ob==null?"":ob.toString());
					}
	      		}else if(exportType.equals("objlist")){
	      			Object npl=(Object) dataList.get(rows);
	      			BeanWrapper bw= new BeanWrapperImpl(npl); 
		      		HSSFRow rowj = null; //建立当前记录的行 *******
					try {
						rowj = sheet.createRow((short) (rows + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(int iii=0;iii<proNames.length;iii++){
						HSSFCell cellk=rowj.createCell((short)iii);
						Object ob=bw.getPropertyValue(proNames[iii]);
		      			cellk.setCellValue(ob==null?"":ob.toString());
					}
	      		}
	      	}
     	}
      	
     	String excelFileName = null;
		final String FSP = System.getProperty("file.separator");
		try{
//			获得Excel文件的真实路径
			deleteFile(fileRootPath);
      		excelFileName = filePrefix+"_"+System.currentTimeMillis() + ".xls";
      		String excelFullFileName = fileRootPath + FSP + excelFileName;
      		//deleteFile(excelFullFileName);//先删除原文件
      	 	FileOutputStream fileout=new FileOutputStream(excelFullFileName);
          	wb.write(fileout);
          	fileout.close();
       	}
      	catch(Exception e){
      		e.printStackTrace();
      	}
      	return excelFileName;
	}
	private String deleteFile(String path){
		File file=new File(path);
		if(!file.isDirectory()){
			file.delete();
		}else if(file.isDirectory()){
			String[] filelist=file.list();
			Date currDate=new Date();
			Long currDates=currDate.getTime();
			for(int i=0;i<filelist.length;i++){
				File delfile=new File(path+"\\"+filelist[i]);
				Long lastModified=delfile.lastModified();
				String times=PropertiesUtil.getProperties("system.removefiletime","5");
				Integer time=Integer.parseInt(times);
				if((currDates-lastModified)>time*60*1000){
					delfile.delete();
				}
			}
		}
		return null;
	}
	public String exportDataForlist(String fileRootPath, String sheetName,
			String filePrefix,String[] titles, String[] proNames, List dataList,String[] propertytype,Map listValueMap,
			String exportType,Map imagepath) {
		// TODO Auto-generated method stub
		HSSFWorkbook wb=new HSSFWorkbook();
      	HSSFCellStyle cellStyle=wb.createCellStyle();
      	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置标题上下居中
      	HSSFCellStyle cellStyledate=wb.createCellStyle();
      	int totalCount =dataList.size();
     	int pageSize = 5000;
     	int sheetSum = 0;
     	if (totalCount % pageSize == 0){
     		sheetSum = totalCount / pageSize;
     	}else{
     		sheetSum = totalCount / pageSize + 1;
     	}
     	for(int ii=0; ii<sheetSum; ii++){
     		HSSFSheet sheet = wb.createSheet();
	     	wb.setSheetName(ii,sheetName+(ii+1));
	     	
//			建立标题行，0行
	     	HSSFRow row=sheet.createRow(0);
	      	row.setHeight((short)400);		
	      	for(int iii=0;iii<titles.length;iii++){
	      		HSSFCell cell=row.createCell((short)iii); //建立标题记录行，从第1行开始 *******
	     		cell.setCellStyle(cellStyle);
	         	cell.setCellValue(titles[iii]);
	      	}
	      
	      	for(int rows=0;rows<dataList.size();rows++){
	      		//System.out.println(rows);
	      		if(exportType.equals("maplist")){
	      			Map npl=(Map) dataList.get(rows);
		      		HSSFRow rowj = null; //建立当前记录的行 *******
					try {
						rowj = sheet.createRow((short) (rows + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(int iii=0;iii<proNames.length;iii++){
						HSSFCell cellk=rowj.createCell((short)iii);
		      			Object ob=npl.get(proNames[iii]);
		      			if(propertytype[iii].equals("2")||propertytype[iii].equals("4")){
		      				Map valueMap=(Map) listValueMap.get(proNames[iii]);
							String columnvalue="";
							String va="";
							if(ob instanceof BigDecimal){
								BigDecimal bd=(BigDecimal) ob;
								if(bd!=null){
									va= bd.toString();
								}
							}else{
								if(ob!=null){
									va=ob.toString();
								}
							}
							if(valueMap.get(va)!=null){
								ob=valueMap.get(va).toString();
							}
		      			}else if(propertytype[iii].equals("8")){
		      				Map valueMap=(Map) listValueMap.get(proNames[iii]);
							String columnvalue="";
							if(ob!=null&&ob.toString().length()>0){
								String value=ob.toString();
								String[] values=value.split(",");
								for(String val:values){
									columnvalue+=valueMap.get(val)+",";
								}
								if(columnvalue.length()>0){
									columnvalue=columnvalue.substring(0,columnvalue.length()-1);
								}
								ob=columnvalue;
							}
		      			}
		      			if(ob!=null){
		      				if(ob instanceof BigDecimal){
			      				BigDecimal bd=(BigDecimal) ob;
			      				cellk.setCellValue(Double.parseDouble(bd.toString()));
			      			}else if(ob instanceof BigInteger){
			      				BigInteger bd=(BigInteger) ob;
			      				cellk.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			      				cellk.setCellValue(Double.parseDouble(bd.toString()));
			      			}else if(ob instanceof Integer){
			      				cellk.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			      				cellk.setCellValue(Integer.parseInt(ob.toString()));
			      			}else if(ob instanceof Double){
			      				cellk.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			      				cellk.setCellValue(Double.parseDouble(ob.toString()));
			      			}else if(ob instanceof Float){
			      				cellk.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			      				cellk.setCellValue(Float.parseFloat(ob.toString()));
			      			}else if(ob instanceof Date){
			      				sheet.setColumnWidth((short) iii,5000);
			      				cellStyledate.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
			      				cellk.setCellStyle(cellStyledate);
			      				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			      		        Date d=null;
								try {
									d = df.parse(ob.toString());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			      		        cellk.setCellValue(d);
			      			}else{
			      				cellk.setCellType(HSSFCell.CELL_TYPE_STRING);
			      				cellk.setCellValue(ob.toString());
			      			}
		      			}else{
		      				cellk.setCellValue("");
		      			}
					}
	      		}else if(exportType.equals("objlist")){
	      			Object npl=(Object) dataList.get(rows);
	      			BeanWrapper bw= new BeanWrapperImpl(npl); 
		      		HSSFRow rowj = null; //建立当前记录的行 *******
					try {
						rowj = sheet.createRow((short) (rows + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(int iii=0;iii<proNames.length;iii++){
						HSSFCell cellk=rowj.createCell((short)iii);
						Object ob=bw.getPropertyValue(proNames[iii]);
		      			cellk.setCellValue(ob==null?"":ob.toString());
					}
	      		}
	      	}
     	}
     	Set set=imagepath.keySet();
     	Iterator it=set.iterator();
     	int sheetSumi=0;
     	while(it.hasNext()){
     		Object ob=it.next();
     		Object url=imagepath.get(ob);
     		HSSFSheet sheet = wb.createSheet();
	     	wb.setSheetName(sheetSum+sheetSumi,ob.toString());
	     	ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
	     	BufferedImage bufferImg =null;
			try {
				bufferImg = ImageIO.read(new File(url.toString()));
				ImageIO.write(bufferImg,"jpg",byteArrayOut);    
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			} 
			sheetSumi++;
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
			HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,0,0,(short) 0,0,(short)10,20); //从0行开始
			patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG)); 
     	}
     	String excelFileName = null;
		final String FSP = System.getProperty("file.separator");
		try{
//			获得Excel文件的真实路径
			deleteFile(fileRootPath);
      		excelFileName = filePrefix+"_"+System.currentTimeMillis() + ".xls";
      		String excelFullFileName = fileRootPath + FSP + excelFileName;
      		//deleteFile(excelFullFileName);//先删除原文件
      	 	FileOutputStream fileout=new FileOutputStream(excelFullFileName);
          	wb.write(fileout);
          	fileout.close();
       	}
      	catch(Exception e){
      		e.printStackTrace();
      	}
      	return excelFileName;
	}
	public Object findObjectById(Class clazz, String key, Object id) {
		// TODO Auto-generated method stub
		return baseDao.findObjectById(clazz, key, id);
	}
}
