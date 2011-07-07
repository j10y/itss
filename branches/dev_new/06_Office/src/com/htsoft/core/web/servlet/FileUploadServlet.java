/*     */ package com.htsoft.core.web.servlet;
/*     */ 
/*     */ import com.htsoft.core.util.AppUtil;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.util.FileUtil;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import com.htsoft.oa.service.system.FileAttachService;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.fileupload.FileItem;
/*     */ import org.apache.commons.fileupload.disk.DiskFileItemFactory;
/*     */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class FileUploadServlet extends HttpServlet
/*     */ {
/*  40 */   private Log logger = LogFactory.getLog(FileUploadServlet.class);
/*     */ 
/*  42 */   private ServletConfig servletConfig = null;
/*     */ 
/*  44 */   private FileAttachService fileAttachService = (FileAttachService)AppUtil.getBean("fileAttachService");
/*     */ 
/*  46 */   private String uploadPath = "";
/*  47 */   private String tempPath = "";
/*     */ 
/*  49 */   private String fileCat = "others";
/*     */ 
/*  51 */   private String filePath = "";
/*     */ 
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  56 */     req.setCharacterEncoding("UTF-8");
/*  57 */     resp.setCharacterEncoding("UTF-8");
/*     */     try
/*     */     {
/*  60 */       DiskFileItemFactory factory = new DiskFileItemFactory();
/*     */ 
/*  62 */       factory.setSizeThreshold(4096);
/*  63 */       factory.setRepository(new File(this.tempPath));
/*  64 */       ServletFileUpload fu = new ServletFileUpload(factory);
/*     */ 
/*  66 */       List<FileItem> fileItems = fu.parseRequest(req);
/*     */ 
/*  68 */       for (FileItem fi : fileItems) {
/*  69 */         if ("file_cat".equals(fi.getFieldName())) {
/*  70 */           this.fileCat = fi.getString();
/*     */         }
/*     */ 
/*  73 */         if ("file_path".equals(fi.getFieldName())) {
/*  74 */           this.filePath = fi.getString();
/*     */         }
/*     */       }
/*     */ 
/*  78 */       Iterator i = fileItems.iterator();
/*     */ 
/*  80 */       while (i.hasNext())
/*     */       {
/*  82 */         FileItem fi = (FileItem)i.next();
/*     */ 
/*  84 */         if (fi.getContentType() == null)
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/*  89 */         String path = fi.getName();
/*     */ 
/*  91 */         int start = path.lastIndexOf("\\");
/*     */ 
/*  94 */         String fileName = path.substring(start + 1);
/*     */ 
/*  96 */         String relativeFullPath = null;
/*     */ 
/*  98 */         if (!"".equals(this.filePath))
/*  99 */           relativeFullPath = this.filePath;
/*     */         else {
/* 101 */           relativeFullPath = this.fileCat + "/" + FileUtil.generateFilename(fileName);
/*     */         }
/*     */ 
/* 104 */         int index = relativeFullPath.lastIndexOf("/");
/*     */ 
/* 106 */         File dirPath = new File(this.uploadPath + "/" + relativeFullPath.substring(0, index + 1));
/*     */ 
/* 108 */         if (!dirPath.exists()) {
/* 109 */           dirPath.mkdirs();
/*     */         }
/*     */ 
/* 112 */         fi.write(new File(this.uploadPath + "/" + relativeFullPath));
/* 113 */         FileAttach file = null;
/*     */ 
/* 115 */         if (!"".equals(this.filePath)) {
/* 116 */           file = this.fileAttachService.getByPath(this.filePath);
/*     */         }
/*     */ 
/* 119 */         if (file == null)
/*     */         {
/* 121 */           file = new FileAttach();
/* 122 */           file.setCreatetime(new Date());
/* 123 */           AppUser curUser = ContextUtil.getCurrentUser();
/* 124 */           if (curUser != null)
/* 125 */             file.setCreator(curUser.getFullname());
/*     */           else {
/* 127 */             file.setCreator("UNKown");
/*     */           }
/* 129 */           int dotIndex = fileName.lastIndexOf(".");
/* 130 */           file.setExt(fileName.substring(dotIndex + 1));
/* 131 */           file.setFileName(fileName);
/* 132 */           file.setFilePath(relativeFullPath);
/* 133 */           file.setFileType(this.fileCat);
/* 134 */           file.setNote(fi.getSize() + " bytes");
/* 135 */           this.fileAttachService.save(file);
/*     */         }
/*     */ 
/* 138 */         StringBuffer sb = new StringBuffer("{success:true");
/* 139 */         sb.append(",fileId:").append(file.getFileId())
/* 140 */           .append(",fileName:'").append(file.getFileName())
/* 141 */           .append("',filePath:'").append(file.getFilePath()).append("',message:'upload file success.(" + fi.getSize() + " bytes)'");
/* 142 */         sb.append("}");
/* 143 */         resp.setContentType("text/html;charset=UTF-8");
/* 144 */         PrintWriter writer = resp.getWriter();
/* 145 */         writer.println(sb.toString());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 150 */       resp.getWriter().write("{'success':false,'message':'error..." + e.getMessage() + "'}");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init(ServletConfig config) throws ServletException
/*     */   {
/* 156 */     super.init(config);
/* 157 */     this.servletConfig = config;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws ServletException
/*     */   {
/* 164 */     this.uploadPath = getServletContext().getRealPath("/attachFiles/");
/*     */ 
/* 166 */     File uploadPathFile = new File(this.uploadPath);
/* 167 */     if (!uploadPathFile.exists()) {
/* 168 */       uploadPathFile.mkdirs();
/*     */     }
/* 170 */     this.tempPath = (this.uploadPath + "/temp");
/*     */ 
/* 172 */     File tempPathFile = new File(this.tempPath);
/* 173 */     if (!tempPathFile.exists())
/* 174 */       tempPathFile.mkdirs();
/*     */   }
/*     */ 
/*     */   public boolean saveFileToDisk(String officefileNameDisk)
/*     */   {
/* 184 */     File officeFileUpload = null;
/* 185 */     FileItem officeFileItem = null;
/*     */ 
/* 187 */     boolean result = true;
/*     */     try
/*     */     {
/* 190 */       if ((!"".equalsIgnoreCase(officefileNameDisk)) && (officeFileItem != null))
/*     */       {
/* 192 */         officeFileUpload = new File(this.uploadPath + officefileNameDisk);
/* 193 */         officeFileItem.write(officeFileUpload);
/*     */       }
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/*     */     }
/*     */     catch (Exception e) {
/* 198 */       e.printStackTrace();
/* 199 */       result = false;
/*     */     }
/* 201 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.web.servlet.FileUploadServlet
 * JD-Core Version:    0.6.0
 */