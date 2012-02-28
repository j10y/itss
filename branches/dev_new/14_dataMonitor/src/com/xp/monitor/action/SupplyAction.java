package com.xp.monitor.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.monitor.bean.SupplyApply;
import com.xp.monitor.bean.UpLoadFile;

public class SupplyAction {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private File upload;
	
	private String uploadContentType;
	private String uploadFileName;
	private String allowTypes;
	private SupplyApply supplyApply;
	
	public SupplyApply getSupplyApply() {
		return supplyApply;
	}

	public void setSupplyApply(SupplyApply supplyApply) {
		this.supplyApply = supplyApply;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	private BaseService baseService=(BaseService) ContextHolder.getBean("baseService");
	
	public String toSupply() throws Exception {
		
		return "toSupply";
	}
	public String submitSupply() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
			"yyyymmddhhmmss");
			String result = format.format(new java.util.Date());
			if(getUpload()!=null){
				String fileName=upload.getName();
				String uploadFileName=getUploadFileName();
				String fname=uploadFileName.substring(0, uploadFileName.lastIndexOf("."));
				String fsuffix=uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
				String path = req.getRealPath("/") + "upload/";
				// 以服务器的文件保存地址和原文件名建立上传文件输出流
				 FileOutputStream fos = new FileOutputStream(path + "/"
				    + fname+result+fsuffix);
				  FileInputStream fis = new FileInputStream(getUpload());
				  byte[] buffer = new byte[1024];
				  int len = 0;
				  while ((len = fis.read(buffer)) > 0) {
				   fos.write(buffer, 0, len);
				  }
				  fis.close();
				  fos.close();
				  UpLoadFile upLoadFile=new UpLoadFile();
				  upLoadFile.setCreateDate(new Date());
				  upLoadFile.setFilePath("upload/"+fname+result+fsuffix);
				  upLoadFile.setRealName(fname+result+fsuffix);
				  upLoadFile =(UpLoadFile) baseService.save(upLoadFile, UpLoadFile.class, "id");
				  supplyApply.setUploadFile(upLoadFile);
			}
			supplyApply.setCreateDate(new Date());
		    baseService.save(supplyApply,SupplyApply.class ,"id");
		    req.setAttribute("result", "1");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("result", "2");
		}
		  
		return "result";
	}
	/**
	 * 导出功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception {
		final String FSP = System.getProperty("file.separator");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List list = new ArrayList();
		// 生成随机文件名
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyymmddhhmmss");
		String result = format.format(new java.util.Date());

		String path = request.getRealPath("/") + "upload"+FSP;
		String id=request.getParameter("id");
		supplyApply=(SupplyApply) baseService.findObjectById(SupplyApply.class, "id", Long.parseLong(id));
		String excelFileName=this.getExcel(path, supplyApply, result);
		list.add(excelFileName);
		if(supplyApply.getUploadFile()!=null){
			list.add(supplyApply.getUploadFile().getRealName());
		}
		String zipname=supplyApply.getSupplyName()+"_"+supplyApply.getBrandName()+result;
		zipname=zipname.replace("/", "");
		getZip(list, path, zipname);
		downLoad(zipname, path, response);
		// 删除zip文件
		deleteFile(new File(path + zipname + ".zip"));
		deleteFile(new File(path + excelFileName));
		return null;
	}
	public String getExcel(String path,SupplyApply supplyApply,String result){
		HSSFWorkbook wb = new HSSFWorkbook();
		Font headerFont = wb.createFont(); // 字体
		headerFont.setFontHeightInPoints((short)18);
		headerFont.setFontName("宋体");
		headerFont.setBoldweight((short)2);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置标题上下居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		cellStyle.setFont(headerFont);
		HSSFCellStyle cellStyle1 = wb.createCellStyle();
		cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置标题上下居中
		
		HSSFSheet sheet = wb.createSheet();
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5)); 
		
		sheet.addMergedRegion(new Region(1, (short) 0, 2, (short) 0)); 
		sheet.addMergedRegion(new Region(1, (short) 1, 2, (short) 2));      
		sheet.addMergedRegion(new Region(1, (short) 4, 1, (short) 5));
		sheet.addMergedRegion(new Region(2, (short) 4, 2, (short) 5));
		
		sheet.addMergedRegion(new Region(3, (short) 1, 3, (short) 2));
		sheet.addMergedRegion(new Region(4, (short) 1, 4, (short) 2));
		sheet.addMergedRegion(new Region(5, (short) 1, 5, (short) 2));
		sheet.addMergedRegion(new Region(6, (short) 1, 6, (short) 2));
		
		sheet.addMergedRegion(new Region(3, (short) 4, 3, (short) 5));
		sheet.addMergedRegion(new Region(4, (short) 4, 4, (short) 5));
		sheet.addMergedRegion(new Region(5, (short) 4, 5, (short) 5));
		sheet.addMergedRegion(new Region(6, (short) 4, 6, (short) 5));
		
		sheet.addMergedRegion(new Region(7, (short) 0, 7, (short) 5));
		sheet.setColumnWidth((short)0,   (short)24*256); 
		sheet.setColumnWidth((short)1,   (short)20*256);
		sheet.setColumnWidth((short)2,   (short)15*256);
		sheet.setColumnWidth((short)3,   (short)24*256);
		sheet.setColumnWidth((short)4,   (short)15*256);
		sheet.setColumnWidth((short)5,   (short)20*256);
		
		wb.setSheetName(0, supplyApply.getSupplyName());
		HSSFRow row0 = sheet.createRow(0);//第一个行
		row0.setHeight((short)500);
		HSSFCell cell00  =row0.createCell((short) 0);
		cell00.setCellStyle(cellStyle);
		cell00.setCellValue("合作资料表");
		HSSFRow row1 = sheet.createRow(1);//第一个行
		row1.setHeight((short)500);
		HSSFCell cell10 = row1.createCell((short) 0); 
		cell10.setCellStyle(cellStyle1);
		cell10.setCellValue("供应商名称");
		HSSFCell cell11 = row1.createCell((short) 1); 
		cell11.setCellStyle(cellStyle1);
		cell11.setCellValue(supplyApply.getSupplyName());
		
		HSSFCell cell13 = row1.createCell((short) 3); 
		cell13.setCellStyle(cellStyle1);
		cell13.setCellValue("品牌注册地");
		
		HSSFCell cell14 = row1.createCell((short) 4);
		cell14.setCellStyle(cellStyle1);
		cell14.setCellValue(supplyApply.getBrandRegistArea());
		
		HSSFRow row2 = sheet.createRow(2);//第二个行
		row2.setHeight((short)500);
		HSSFCell cell23 = row2.createCell((short) 3); 
		cell23.setCellStyle(cellStyle1);
		cell23.setCellValue("品牌持有");
		
		HSSFCell cell24 = row2.createCell((short) 4); 
		cell24.setCellStyle(cellStyle1);
		cell24.setCellValue(supplyApply.getBrandBelong());
		
		HSSFRow row3 = sheet.createRow(3);//第二个行
		row3.setHeight((short)500);
		HSSFCell cell30 = row3.createCell((short) 0); 
		cell30.setCellStyle(cellStyle1);
		cell30.setCellValue("经营品牌名称（中/英）");
		
		HSSFCell cell31 = row3.createCell((short) 1); 
		cell31.setCellStyle(cellStyle1);
		cell31.setCellValue(supplyApply.getBrandName());
		
		HSSFCell cell33 = row3.createCell((short) 3); 
		cell33.setCellStyle(cellStyle1);
		cell33.setCellValue("品牌价格带 ");
		
		HSSFCell cell34 = row3.createCell((short) 4); 
		cell34.setCellStyle(cellStyle1);
		cell34.setCellValue(supplyApply.getBrandPrice());
		
		HSSFRow row4 = sheet.createRow(4);//第二个行
		row4.setHeight((short)500);
		HSSFCell cell40 = row4.createCell((short) 0); 
		cell40.setCellStyle(cellStyle1);
		cell40.setCellValue("品牌所属类别 ");
		
		HSSFCell cell41 = row4.createCell((short) 1); 
		cell41.setCellStyle(cellStyle1);
		cell41.setCellValue(supplyApply.getBrandClass());
		
		HSSFCell cell43 = row4.createCell((short) 3); 
		cell43.setCellStyle(cellStyle1);
		cell43.setCellValue("品牌风格（适合人群）");
		
		HSSFCell cell44 = row4.createCell((short) 4); 
		cell44.setCellStyle(cellStyle1);
		cell44.setCellValue(supplyApply.getBrandStyle());
		
		HSSFRow row5 = sheet.createRow(5);//第二个行
		row5.setHeight((short)500);
		HSSFCell cell50 = row5.createCell((short) 0);
		cell50.setCellStyle(cellStyle1);
		cell50.setCellValue("拥有实体店/专柜数量");
		
		HSSFCell cell51 = row5.createCell((short) 1); 
		cell51.setCellStyle(cellStyle1);
		cell51.setCellValue(supplyApply.getStoreNum());
		
		HSSFCell cell53 = row5.createCell((short) 3); 
		cell53.setCellStyle(cellStyle1);
		cell53.setCellValue("实体店区域分布（省市级）");
		
		HSSFCell cell54 = row5.createCell((short) 4); 
		cell54.setCellStyle(cellStyle1);
		cell54.setCellValue(supplyApply.getStoreAddress());
		
		HSSFRow row6 = sheet.createRow(6);//第二个行
		row6.setHeight((short)500);
		HSSFCell cell60 = row6.createCell((short) 0); 
		cell60.setCellStyle(cellStyle1);
		cell60.setCellValue("品牌官方网站（网址）");
		
		HSSFCell cell61 = row6.createCell((short) 1); 
		cell61.setCellStyle(cellStyle1);
		cell61.setCellValue(supplyApply.getWebsite());
		
		HSSFCell cell63 = row6.createCell((short) 3); 
		cell63.setCellStyle(cellStyle1);
		cell63.setCellValue("合作网站（5家以内）");
		
		HSSFCell cell64 = row6.createCell((short) 4); 
		cell64.setCellStyle(cellStyle1);
		cell64.setCellValue(supplyApply.getCooperateWebSite());
		
		HSSFRow row7 = sheet.createRow(7);//第二个行
		row7.setHeight((short)500);
		HSSFCell cell70 = row7.createCell((short) 0); 
		cell70.setCellStyle(cellStyle);
		cell70.setCellValue("联系方式");
		
		HSSFRow row8 = sheet.createRow(8);//第二个行
		row8.setHeight((short)500);
		HSSFCell cell80 = row8.createCell((short) 0); 
		cell80.setCellStyle(cellStyle1);
		cell80.setCellValue("公司地址");
		HSSFCell cell81 = row8.createCell((short) 1); 
		cell81.setCellStyle(cellStyle1);
		cell81.setCellValue(supplyApply.getCompanyAddress());
		HSSFCell cell82 = row8.createCell((short) 2);
		cell82.setCellStyle(cellStyle1);
		cell82.setCellValue("公司电话");
		HSSFCell cell83 = row8.createCell((short) 3); 
		cell83.setCellStyle(cellStyle1);
		cell83.setCellValue(supplyApply.getCompanyTel());
		HSSFCell cell84 = row8.createCell((short) 4); 
		cell84.setCellStyle(cellStyle1);
		cell84.setCellValue("E-mail");
		HSSFCell cell85 = row8.createCell((short) 5);
		cell85.setCellStyle(cellStyle1);
		cell85.setCellValue(supplyApply.getCompanyEmail());
		
		HSSFRow row9 = sheet.createRow(9);//第二个行
		row9.setHeight((short)500);
		HSSFCell cell90 = row9.createCell((short) 0); 
		cell90.setCellStyle(cellStyle1);
		cell90.setCellValue("业务负责人/职位");
		HSSFCell cell91 = row9.createCell((short) 1); 
		cell91.setCellStyle(cellStyle1);
		cell91.setCellValue(supplyApply.getBusinessUser());
		HSSFCell cell92 = row9.createCell((short) 2); 
		cell92.setCellStyle(cellStyle1);
		cell92.setCellValue("手机");
		HSSFCell cell93 = row9.createCell((short) 3); 
		cell93.setCellStyle(cellStyle1);
		cell93.setCellValue(supplyApply.getBusinessTel());
		HSSFCell cell94 = row9.createCell((short) 4); 
		cell94.setCellStyle(cellStyle1);
		cell94.setCellValue("E-mail");
		HSSFCell cell95 = row9.createCell((short) 5); 
		cell95.setCellStyle(cellStyle1);
		cell95.setCellValue(supplyApply.getBusinessEmail());
		
		HSSFRow row10 = sheet.createRow(10);//第二个行
		row10.setHeight((short)500);
		HSSFCell cell100 = row10.createCell((short) 0); 
		cell100.setCellStyle(cellStyle1);
		cell100.setCellValue("电商负责人/职位");
		HSSFCell cell101 = row10.createCell((short) 1); 
		cell101.setCellStyle(cellStyle1);
		cell101.setCellValue(supplyApply.getECommerceUser());
		HSSFCell cell102 = row10.createCell((short) 2); 
		cell102.setCellStyle(cellStyle1);
		cell102.setCellValue("手机");
		HSSFCell cell103 = row10.createCell((short) 3);
		cell103.setCellStyle(cellStyle1);
		cell103.setCellValue(supplyApply.getECommerceTel());
		HSSFCell cell104 = row10.createCell((short) 4);
		cell104.setCellStyle(cellStyle1);
		cell104.setCellValue("E-mail");
		HSSFCell cell105 = row10.createCell((short) 5); 
		cell105.setCellStyle(cellStyle1);
		cell105.setCellValue(supplyApply.getECommerceEmail());
		
		String excelFileName = null;
		final String FSP = System.getProperty("file.separator");
		try {
			// 获得Excel文件的真实路径
			//deleteFile(fileRootPath);
			excelFileName = supplyApply.getSupplyName() + "_" + result
					+ ".xls";
			String excelFullFileName = path + FSP + excelFileName;
			// deleteFile(excelFullFileName);//先删除原文件
			FileOutputStream fileout = new FileOutputStream(excelFullFileName);
			wb.write(fileout);
			fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelFileName;
	}
	/**
	 * 压缩文件zip
	 * 
	 * @功能信息 :
	 * @参数信息 :
	 * @返回值信息 :
	 * @异常信息 :
	 */
	public void getZip(List list, String path, String fileName){
		try {
			byte[] buffer = new byte[1024];
			String strZipName = fileName + ".zip";
			strZipName=strZipName.replace("/", "").replace("\\", "");
			for (int j = 0; j < list.size(); j++) {
				String name = list.get(j).toString();
			}
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path
					+ strZipName));
			for (int j = 0; j < list.size(); j++) {
				String name = list.get(j).toString();
				FileInputStream fis = new FileInputStream(path + name);
				out.putNextEntry(new ZipEntry(name));
				out.setEncoding("GBK");
				int len;
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("生成Demo.zip成功");
	}

	/**
	 * 文件下载
	 * 
	 * @功能信息 :
	 * @参数信息 :
	 * @返回值信息 :
	 * @异常信息 :
	 */
	public void downLoad(String fileName, String path,
			HttpServletResponse response) throws IOException {
		String strZipName = fileName + ".zip";
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			File file = new File(path + strZipName);
			if (!file.exists()) {
				System.out.println("文件不存在");
			}

			is = new FileInputStream(file);
			bis = new BufferedInputStream(is);
			os = response.getOutputStream();
			bos = new BufferedOutputStream(os);

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/x-msdownload;charset=utf-8");
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(strZipName, "utf-8"));
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			is.close();
			bis.close();
			os.close();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件信息 根据boolean类型判断文件是否存在
	 * 
	 * @param file
	 */
	private boolean deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					this.deleteFile(files[i]);
				}
			}
			file.delete();
			return true;
		} else {
			return false;
		}
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getAllowTypes() {
		return allowTypes;
	}

	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}
}
