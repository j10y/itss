package com.zsgj.itil.actor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.zsgj.info.appframework.extjs.servlet.CoderForList;
import com.zsgj.info.appframework.metadata.entity.ExtOptionData;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.actor.service.ActorUtilService;

/**
 * 人员信息工具ACTION
 * @Class Name ActorUtilAction
 * @Author lee
 * @Create In Dec 15, 2009
 */
@SuppressWarnings("serial")
public class ActorUtilAction extends BaseAction{
	
	private ActorUtilService actorUtilService = (ActorUtilService) getBean("actorUtilService");
	
	/**
	 * 获取所有用户，包括已经离职的用户
	 * @Methods Name getAllUserForCombo
	 * @Create In Dec 15, 2009 By lee
	 * @return String
	 */
	public String getAllUserForCombo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String json = "";
		int pageSize = 10;
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
//		String orderBy = HttpUtil.getString(request, "orderBy", "id");
//		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", true);
		String userName = HttpUtil.getString(request, "userName", "");
		String curId = HttpUtil.getString(request, "id", "");
		if(curId!=null&&curId!=""){
			UserInfo curUser = (UserInfo) this.getService().findUnique(UserInfo.class, "id", Long.valueOf(curId));
			json+="{id:'"+curUser.getId()+"',userName:'"+curUser.getUserName()+"/"+curUser.getRealName()+"/"+curUser.getDepartment().getDepartName()+"'}";//modify by liuying at 20100224 for 显示部门信息
			//json+="{id:'"+curUser.getId()+"',userName:'"+curUser.getUserName()+"/"+curUser.getRealName()+"'}";
			json = "{success: true, rowCount:'1',data:["+json+"]}";
		}else{
			Page page = actorUtilService.getAllUser(userName, pageNo, pageSize);
			Long total = page.getTotalCount();
			List<UserInfo> queryList = page.list();
			for(UserInfo user : queryList){
				json+="{id:'"+user.getId()+"',userName:'"+user.getUserName()+"/"+user.getRealName()+"/"+user.getDepartment().getDepartName()+"'},";//modify by liuying at 20100224 for 显示部门信息
				//json+="{id:'"+user.getId()+"',userName:'"+user.getUserName()+"/"+user.getRealName()+"'},";
			}
			if (json.length() == 0) {
				json = "{success:true,rowCount:" + "1" + ",data:["
						+ json.substring(0, json.length()) + "]}";
			} else {
				json = "{success:true,rowCount:" + total + ",data:["
						+ json.substring(0, json.length() - 1) + "]}";
			}
		}
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
