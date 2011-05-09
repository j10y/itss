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
	 * ��ҳ��ѯ������ʽ��������������
	 * @throws IOException 
	 * @Methods Name findItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return String
	 */
	public String findItem() throws IOException{
		// ��ȡǰ̨����
		int start = HttpUtil.getInt(getRequest(), "start",0);  // ��ʼֵ
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10); // ҳ��С
		// ��ȡ����ֵ
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// �����,1��ʾ������;2��ʾ������
		String item = getRequest().getParameter("item");
		// ��ȡ����
		String json = costHandInputService.findItem(start,pageSize,item,propertyValue);
		// д������
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);		
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * ��ҳ��ѯ���з�������
	 * @Methods Name findCostType
	 * @Create In Oct 13, 2010 By Liaogs1
	 * @return String
	 */
	public String findCostType()throws IOException{
		// ��ȡҳ�����
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		// ��ȡ����ֵ
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// ��ȡ����
		String json = costHandInputService.findCostType(start, pageSize,propertyValue);
		// д������
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);	
		pw.flush();
		pw.close();
		return null;
	}
	/**
	 * ��ҳ��ѯ�����û�
	 * @Methods Name findReimbursement
	 * @Create In Oct 13, 2010 By Liaogs1
	 * @return String
	 */
	public String findReimbursement()throws IOException{
		// ��ȡҳ�����
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		// ��ȡ����ֵ
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// ��ȡ����
		String json = costHandInputService.findReimbursement(start, pageSize,propertyValue);
		// д������
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);	
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * ��ҳ��ѯ���гɱ���������
	 * @Methods Name findFinanceCostCenter
	 * @Create In Oct 13, 2010 By Liaogs1
	 * @return String
	 */
	public String findFinanceCostCenter()throws IOException{
		// ��ȡҳ�����
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		// ��ȡ����ֵ
		String propertyValue = super.getRequest().getParameter("propertyValue");
		// ��ȡ����
		String json = costHandInputService.findFinanceCostCenter(start, pageSize,propertyValue);
		// д������
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);		
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * ����ɱ��ֹ�¼����Ϣ
	 * @Methods Name save
	 * @Create In Oct 14, 2010 By liaogs1
	 * @return
	 * @throws Exception String
	 */
	public String save() throws Exception{
		// ��ȡҳ�����
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
	 * �ɱ���ϵ�б�
	 * @Methods Name getCostSchedulesList
	 * @Create In Oct 15, 2010 By liaogs1
	 * @return
	 * @throws Exception String
	 */
	public String getCostSchedulesList()throws Exception{
		// ��ȡҳ�����
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
	 * ����itcode��ȡ�û���Ϣ
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
