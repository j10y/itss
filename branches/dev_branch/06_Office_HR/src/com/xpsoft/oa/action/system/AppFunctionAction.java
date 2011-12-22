package com.xpsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppFunction;
import com.xpsoft.oa.service.system.AppFunctionService;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class AppFunctionAction extends BaseAction {

	@Resource
	private AppFunctionService appFunctionService;
	private AppFunction appFunction;
	private Long functionId;

	public Long getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public AppFunction getAppFunction() {
		return this.appFunction;
	}

	public void setAppFunction(AppFunction appFunction) {
		this.appFunction = appFunction;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<AppFunction> list = this.appFunctionService.getAll(filter);

		Type type = new TypeToken<List<AppFunction>>() {
		}
		.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		Gson gson = new Gson();
		toJson(list,buff);
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public static void  toJson(List<AppFunction> list,StringBuffer sb){
		sb.append("[");
		for(AppFunction f:list){
			sb.append("{")
					.append("'functionId':'" + f.getFunctionId() + "',")
					.append("'funKey':'" + f.getFunKey() + "',")
					.append("'funName':'" + f.getFunName() + "'},");
		}
		if(list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		
	}
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		  if (ids != null) {
			 for (String id : ids) {
				this.appFunctionService.remove(new Long(id));
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		AppFunction appFunction = (AppFunction) this.appFunctionService
				.get(this.functionId);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		       sb.append("{'functionId':'" + appFunction.getFunctionId() + "',")
		       			.append("'funKey':'" + appFunction.getFunKey() + "',")
		       			.append("'funName':'" + appFunction.getFunName() + "'}");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	public String save() {
		String data = getRequest().getParameter("funUrls");
		String[] funUrls = data.split(",");
		this.appFunctionService.save(this.appFunction);
		if(this.appFunction.getFunctionId()!=null){
			this.appFunctionService.updateFunUrl(funUrls,this.appFunction.getFunctionId());
		}
		setJsonString("{success:true}");
		return "success";
	}
}