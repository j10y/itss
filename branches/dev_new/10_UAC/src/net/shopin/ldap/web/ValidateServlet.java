package net.shopin.ldap.web;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.ldap.dao.DutyDao;
import net.shopin.ldap.dao.GroupDao;
import net.shopin.ldap.dao.UserDao;
import net.shopin.ldap.entity.User;
import net.shopin.ldap.entity.UserGroup;
import net.shopin.util.PropertiesUtil;
import net.shopin.util.Result;
import net.shopin.util.SpringContextUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.samples.utils.LdapTreeBuilder;


public class ValidateServlet extends HttpServlet {
	
	UserDao userDao = (UserDao) SpringContextUtils.getBean("userDao");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String methodCall = req.getParameter("methodCall");
		StringBuffer json = new StringBuffer("{success:true}");
		try {
			if(methodCall.equalsIgnoreCase("validateUser")) {
				Result result = validateUser(req);
				if(!result.isSuccess()){
					json = new StringBuffer("false");
				}else{
					json = new StringBuffer("true");
				}
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'服务器端异常'}");
		}
		try {
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			PrintWriter pw = resp.getWriter();
			pw.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Result validateUser(HttpServletRequest req) {
		Result result = new Result();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = userDao.findByPrimaryKey(username);
		if(user==null){
			result.setMsg("用户不存在！");
		}else{
			if (!user.getPassword().equals(password)) {
				result.setMsg("密码不正确！");
			}else{
				result.setSuccess(true);
			}
		}
		return result;
	}

}
