package com.digitalchina.info.appframework.metadata.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 系统扩展表action
 * @Class Name ResourceAction
 * @Author peixf
 * @Create In 2008-3-14
 */
public class SystemMainColumnTypeAction extends BaseDispatchAction{
	
	private Service bs = getService();
	private boolean writable = false;
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List mainColumnTypes = bs.findAll(SystemMainTableColumnType.class);
		JSONArray jsonObject = JSONArray.fromObject(mainColumnTypes);
		//System.out.println(jsonObject.toString());

		try {
			response.setCharacterEncoding("gbk");
			response.getWriter().write(
					"{success:" + true + ",rowCount:" + mainColumnTypes.size()+ ",data:" + jsonObject.toString() + "}");
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward toEdit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(writable==false) throw new ApplicationException("当前列表不可以修改");
		
		this.list(mapping, actionForm, request, response);
		
		String sysExtTableId = request.getParameter("id");
		SystemMainTableColumnType smtct = (SystemMainTableColumnType) bs.find(SystemMainTableColumnType.class, sysExtTableId);
		request.setAttribute("detail", smtct);
		
		return mapping.findForward("list");
	}

	public ActionForward toAdd(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		this.list(mapping, actionForm, request, httpServletResponse);
				
		return mapping.findForward("list"); 
	}

	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String data = request.getParameter("data");
		//从页面传来的数据格式为：
		//[{"id":null,"name":"1223","serviceKeyName":"234","url":"/test/*","descn":"test1234","pModuleId":1},{},{}]
		JSONArray jsonArray = JSONArray.fromObject(data);
		//System.out.println(jsonArray.toString());

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
			//判断是不是新的记录
			String newRecord = jsonObject.getString("newRecord");
			
			if (!"".equals(newRecord) && newRecord.equals("true")) {
				String columnTypeName = jsonObject.getString("columnTypeName");
				String columnTypeCnName = HttpUtil.ConverUnicode(jsonObject.getString("columnTypeCnName"));				
				SystemMainTableColumnType smtct = new SystemMainTableColumnType();
				smtct.setColumnTypeName(columnTypeName);
				smtct.setColumnTypeCnName(columnTypeCnName);
				bs.save(smtct);
			}else{
				String id = jsonObject.getString("id");
				SystemMainTableColumnType smtct =(SystemMainTableColumnType) bs.find(SystemMainTableColumnType.class, id);
				String columnTypeName = jsonObject.getString("columnTypeName");
				String columnTypeCnName = HttpUtil.ConverUnicode(jsonObject.getString("columnTypeCnName"));
				smtct.setColumnTypeName(columnTypeName);
				smtct.setColumnTypeCnName(columnTypeCnName);
				bs.save(smtct);
			}
		}
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		
		
		String[] ids = request.getParameterValues("id");
		for(int i=0; i<ids.length; i++){
			String id = ids[i];
			bs.remove(SystemMainTableColumnType.class, id);
		}
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}

} 
