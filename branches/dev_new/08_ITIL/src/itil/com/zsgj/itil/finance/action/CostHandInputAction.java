package com.zsgj.itil.finance.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.finance.service.CostHandInputService;

public class CostHandInputAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private CostHandInputService costHandInputService;
	private Service service = (Service) ContextHolder.getBean("baseService");

	/**
	 * 分页查询所有正式的配置项或服务项
	 * @throws IOException 
	 * @Methods Name findItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return String
	 */
	public String findItem() throws IOException{
		// 获取前台参数
		int start = HttpUtil.getInt(getRequest(), "start",0);  // 起始值
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10); // 页大小
		// 获取属性值
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// 类别标记,1表示配置项;2表示服务项
		String item = getRequest().getParameter("item");
		// 获取数据
		String json = costHandInputService.findItem(start,pageSize,item,propertyValue);
		// 写回数据
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);		
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 分页查询所有费用类型
	 * @Methods Name findCostType
	 * @Create In Oct 13, 2010 By Liaogs1
	 * @return String
	 */
	public String findCostType()throws IOException{
		// 获取页面参数
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		// 获取属性值
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// 获取数据
		String json = costHandInputService.findCostType(start, pageSize,propertyValue);
		// 写回数据
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);	
		pw.flush();
		pw.close();
		return null;
	}
	/**
	 * 分页查询所有用户
	 * @Methods Name findReimbursement
	 * @Create In Oct 13, 2010 By Liaogs1
	 * @return String
	 */
	public String findReimbursement()throws IOException{
		// 获取页面参数
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		// 获取属性值
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// 获取数据
		String json = costHandInputService.findReimbursement(start, pageSize,propertyValue);
		// 写回数据
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);	
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 分页查询所有成本中心数据
	 * @Methods Name findFinanceCostCenter
	 * @Create In Oct 13, 2010 By Liaogs1
	 * @return String
	 */
	public String findFinanceCostCenter()throws IOException{
		// 获取页面参数
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		// 获取属性值
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// 获取数据
		String json = costHandInputService.findFinanceCostCenter(start, pageSize,propertyValue);
		// 写回数据
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);		
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 保存成本手工录入信息
	 * @Methods Name save
	 * @Create In Oct 14, 2010 By liaogs1
	 * @return
	 * @throws Exception String
	 */
	public String save() throws Exception{
		// 获取页面参数
		String formParam = super.getRequest().getParameter("formParam");
		JSONObject jo = JSONObject.fromObject(formParam);
		boolean success = costHandInputService.saveFinanceCostSchedules(jo);
		if(success){
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write("{success: true}");
		}else{
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write("{success: false}");
		}
		return null;
	}
	
	/**
	 * 成本，系列表
	 * @Methods Name getCostSchedulesList
	 * @Create In Oct 15, 2010 By liaogs1
	 * @return
	 * @throws Exception String
	 */
	public String getCostSchedulesList()throws Exception{
		// 获取页面参数
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		String term =  super.getRequest().getParameter("costReduceType");
		Map map = new HashMap();
		map.put("costReduceType", term);
		String json = costHandInputService.findList(map, start, pageSize);
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);		
		return null;
	}
	
	/**
	 * 根据itcode获取用户信息
	 * @Methods Name getUserInfoByItcode
	 * @Create In Oct 15, 2010 By liaogs1
	 * @return
	 * @throws Exception String
	 */
	public String getUserInfoByItcode() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String itcode = request.getParameter("itcode");
		String json = "";
		if (itcode==null || itcode.equals("")) {
			return null;
		}
		List<UserInfo> list = service.find(UserInfo.class, "userName", itcode);
		if (list==null || list.size()==0) {
			json = "{success:false}";
		} else {
			UserInfo userInfo = list.get(0);
			json = "{success:true,auditPersonId:'"+userInfo.getId()+"',auditPersonRealName:'"+userInfo.getRealName()+"'}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
		return null;
	}

	public CostHandInputService getCostHandInputService() {
		return costHandInputService;
	}

	public void setCostHandInputService(CostHandInputService costHandInputService) {
		this.costHandInputService = costHandInputService;
	}
	
	
}
