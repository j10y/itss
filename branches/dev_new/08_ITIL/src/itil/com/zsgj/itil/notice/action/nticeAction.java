package com.zsgj.itil.notice.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.config.extlist.entity.NewNoticeType;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.notice.entity.NewNotice;

public class nticeAction extends BaseAction{
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	/**
	 * 保存公告草稿
	 * @Methods Name save
	 * @Create In Oct 12, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	public String save() throws Exception {
		UserInfo userInfo = UserContext.getUserInfo();
		String json="";
		String info = super.getRequest().getParameter("info");
		Map map = getMapFormPanelJson(info);

		if(map.get("id")!=null){
			Date date = new Date();  
			map.put("auditflag", 0);
			map.put("createDate", date);
			map.put("createUser", userInfo.getId());
		}
		NewNotice newNotice = (NewNotice) metaDataManager.saveEntityData(NewNotice.class, map);
		NewNoticeType noticeType = (NewNoticeType) this.getService().find(NewNoticeType.class, newNotice.getNewNoticeType().getId().toString());
		json ="{success:true,newNoticeId:"+newNotice.getId()+",newNoticeName:'"+newNotice.getName()+"',newNoticeType:'"+noticeType.getName()+"'}";
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
		
	}
	/**
	 * 保存公告变更信息
	 * @Methods Name saveAlter
	 * @Create In May 6, 2009 By Administrator
	 * @return
	 * @throws Exception String
	 */
	public String saveAlter() throws Exception {
//		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo userInfo = UserContext.getUserInfo();
		String json="";
		String info = super.getRequest().getParameter("info");
		Map map = getMapFormPanelJson(info);
		if(map.get("id")!=null){
//			Date date = new Date();
			map.put("alterNoticeId", map.get("id"));
			map.put("id", null);
			map.put("auditflag", 0);
			map.put("createUser", userInfo.getId());
		}
		NewNotice newNotice = (NewNotice) metaDataManager.saveEntityData(NewNotice.class, map);
		NewNoticeType noticeType = (NewNoticeType) this.getService().find(NewNoticeType.class, newNotice.getNewNoticeType().getId().toString());
		json ="{success:true,newNoticeId:"+newNotice.getId()+",newNoticeName:'"+newNotice.getName()+"',newNoticeType:'"+noticeType.getName()+"'}";
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
		
	}
	public String findKnowledge() throws Exception {
		String dataId = super.getRequest().getParameter("dataId");
		Knowledge knowledge = (Knowledge) super.getService().find(Knowledge.class, dataId);
		String content = knowledge.getResolvent();
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(content);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
		
	}
	/**
	 * 删除 彻底删除还是改变标志位？
	 * @Methods Name remove
	 * @Create In Mar 26, 2009 By Administrator
	 * @return
	 * @throws Exception String
	 */
	public String remove() throws Exception {
		String dataId = super.getRequest().getParameter("dataId");
		NewNotice newNotice = (NewNotice) super.getService().find(NewNotice.class, dataId);
		super.getService().remove(newNotice);
		return null;
		
	}
	/**
	 * 查找标志位
	 * @Methods Name findNoticeFlag
	 * @Create In Mar 26, 2009 By Administrator
	 * @return
	 * @throws Exception String
	 */
	public String findNoticeFlag() throws Exception{
		String dataId = super.getRequest().getParameter("dataId"); 
		NewNotice newNotice = (NewNotice) super.getService().find(NewNotice.class, dataId);
		int noticeFlag = newNotice.getAuditflag();
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(noticeFlag+"");		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
	}
	public String isExistAltering(){
		String dataId = super.getRequest().getParameter("dataId"); 
		NewNotice newNotice = (NewNotice) super.getService().findUnique(NewNotice.class, "alterNoticeId", Long.valueOf(dataId));
		String json ="";
		if(newNotice==null){//没有变更审批中的
			json="{success:true,flag:'no',noticeId:"+dataId+"}";
			
		}else{
			json ="{success:true,flag:'yes',noticeId:"+newNotice.getId()+"}";
			
		}
		
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
		
	}
	private Map getMapFormPanelJson(String param){
		JSONObject basicJson=JSONObject.fromObject(param);
		Set<String> basicSet=basicJson.keySet();
		Map map = new HashMap();
		for (String key:basicSet) {
			String keyString = StringUtils.substringAfter(key, "$");
			String value = basicJson.getString(key);
			map.put(keyString, value);
		}
		return map;
	}
}
