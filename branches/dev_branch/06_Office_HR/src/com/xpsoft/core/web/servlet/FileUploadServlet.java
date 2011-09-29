package com.xpsoft.core.web.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpsoft.core.model.Ftp;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.FileUtil;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.system.FileAttachService;

public class FileUploadServlet extends HttpServlet {
	private Log logger = LogFactory.getLog(FileUploadServlet.class);

	private ServletConfig servletConfig = null;

	private FileAttachService fileAttachService = (FileAttachService) AppUtil
			.getBean("fileAttachService");

	private String uploadPath = "";
	private String tempPath = "";

	/**
	 * 所属栏目建立的目录名称
	 */
	private String fileCat = "others";

	private String filePath = "";

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		boolean isFtp = new Boolean(String.valueOf(AppUtil.getSysConfig().get(
				"isFtp")));

		try {

			DiskFileItemFactory factory = new DiskFileItemFactory();

			factory.setSizeThreshold(4096);
			factory.setRepository(new File(this.tempPath));
			ServletFileUpload fu = new ServletFileUpload(factory);

			List<FileItem> fileItems = fu.parseRequest(req);

			for (FileItem fi : fileItems) {
				if ("file_cat".equals(fi.getFieldName())) {
					this.fileCat = fi.getString();
				}

				if ("file_path".equals(fi.getFieldName())) {
					this.filePath = fi.getString();
				}
			}

			Iterator i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();

				if (fi.getContentType() == null) {
					continue;
				}

				String path = fi.getName();
				int start = path.lastIndexOf("\\");
				String fileName = path.substring(start + 1);
				String relativeFullPath = null;
				FileAttach file = null;
				
				//modify by jack for FTP support at 2011-9-21 begin
				if (!"".equals(this.filePath)) {
					relativeFullPath = this.filePath;
				} else {
					if (isFtp) {

						Ftp ftp = new Ftp(1, "fileUpload", String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.host")),
								new Integer(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.port"))), "", "");
						ftp.setUsername(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.user")));
						ftp.setPassword(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.passwd")));
						ftp.setPath("");
						
						String savePath = String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.sysprofix"));
						String viewPath = String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.pathpifx"));
						
						relativeFullPath =  savePath + "/" + this.fileCat + "/"
								+ FileUtil.generateFilename(fileName);

						String desFile = ftp.storeByFilename(relativeFullPath, fi.getInputStream());
						relativeFullPath = "ftp://" + String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.host"))
											+ ":"
											+ String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.port"))
											+ viewPath + "/"
											+ desFile.substring(desFile.indexOf("/") + 1);
					} else {
						relativeFullPath = this.fileCat + "/" + FileUtil.generateFilename(fileName);
						// 建立上传目录
						int index = relativeFullPath.lastIndexOf("/");
						File dirPath = new File(this.uploadPath + "/"
								+ relativeFullPath.substring(0, index + 1));
						if (!dirPath.exists()) {
							dirPath.mkdirs();
						}
						// 写入附件到上传目录
						fi.write(new File(this.uploadPath + "/" + relativeFullPath));
						
						String profix = this.uploadPath.substring(this.uploadPath.lastIndexOf("\\")+1);
						relativeFullPath = "http://" + req.getServerName() + ":" + req.getServerPort() + 
											(req.getContextPath() != null && !"".equals(req.getContextPath()) ? req.getContextPath() : "/") + 
											profix + "/" + relativeFullPath;
					}
				}
				//modify by jack for FTP support at 2011-9-21 end
				if (!"".equals(this.filePath)) {
					file = this.fileAttachService.getByPath(this.filePath);
				}

				if (file == null) {
					file = new FileAttach();
					file.setCreatetime(new Date());
					AppUser curUser = ContextUtil.getCurrentUser();
					if (curUser != null)
						file.setCreator(curUser.getFullname());
					else {
						file.setCreator("UNKown");
					}
					int dotIndex = fileName.lastIndexOf(".");
					file.setExt(fileName.substring(dotIndex + 1));
					file.setFileName(fileName);
					file.setFilePath(relativeFullPath);
					file.setFileType(this.fileCat);
					file.setNote(fi.getSize() + " bytes");
					file = this.fileAttachService.save(file);
				}

				StringBuffer sb = new StringBuffer("{success:true");
				sb.append(",fileId:")
						.append(file.getFileId())
						.append(",fileName:'")
						.append(file.getFileName())
						.append("',filePath:'")
						.append(file.getFilePath())
						.append("',message:'upload file success.("
								+ fi.getSize() + " bytes)'");
				sb.append("}");
				resp.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = resp.getWriter();
				writer.println(sb.toString());
			}
		} catch (Exception e) {
			resp.getWriter().write(
					"{'success':false,'message':'error..." + e.getMessage()
							+ "'}");
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.servletConfig = config;
	}

	public void init() throws ServletException {
		//modify by jack for FTP support at 2011-9-21 begin
		this.uploadPath = getServletContext().getRealPath(String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix")));
		//modify by jack for FTP support at 2011-9-21 end
		File uploadPathFile = new File(this.uploadPath);
		if (!uploadPathFile.exists()) {
			uploadPathFile.mkdirs();
		}
		this.tempPath = (this.uploadPath + "/temp");

		File tempPathFile = new File(this.tempPath);
		if (!tempPathFile.exists())
			tempPathFile.mkdirs();
	}

	public boolean saveFileToDisk(String officefileNameDisk) {
		File officeFileUpload = null;
		FileItem officeFileItem = null;

		boolean result = true;
		try {
			if ((!"".equalsIgnoreCase(officefileNameDisk))
					&& (officeFileItem != null)) {
				officeFileUpload = new File(this.uploadPath
						+ officefileNameDisk);
				officeFileItem.write(officeFileUpload);
			}
		} catch (FileNotFoundException localFileNotFoundException) {
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}
