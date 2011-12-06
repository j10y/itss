package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.AppFunction;
import com.xpsoft.oa.service.system.AppFunctionService;

public class AppFunctionAction extends BaseAction {

	@Resource
	private AppFunctionService appFunctionService;
	private AppFunction appFunction;
	private Long functionId;

	public Long getFunctionId() {
		/* 33 */return this.functionId;
	}

	public void setFunctionId(Long functionId) {
		/* 37 */this.functionId = functionId;
	}

	public AppFunction getAppFunction() {
		/* 41 */return this.appFunction;
	}

	public void setAppFunction(AppFunction appFunction) {
		/* 45 */this.appFunction = appFunction;
	}

	public String list() {
		/* 53 */QueryFilter filter = new QueryFilter(getRequest());
		/* 54 */List<AppFunction> list = this.appFunctionService.getAll(filter);

		/* 56 */Type type = new TypeToken<List<AppFunction>>() {
		}
		/* 56 */.getType();
		/* 57 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 58 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 60 */Gson gson = new Gson();
		/* 61 *///buff.append(gson.toJson(list, type));
		toJson(list,buff);
		/* 62 */buff.append("}");

		/* 64 */this.jsonString = buff.toString();

		/* 66 */return "success";
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
		/* 74 */String[] ids = getRequest().getParameterValues("ids");
		/* 75 */if (ids != null) {
			/* 76 */for (String id : ids) {
				/* 77 */this.appFunctionService.remove(new Long(id));
			}
		}

		/* 81 */this.jsonString = "{success:true}";

		/* 83 */return "success";
	}

	public String get() {
		/* 91 */AppFunction appFunction = (AppFunction) this.appFunctionService
				.get(this.functionId);

		/* 93 */Gson gson = new Gson();

		/* 95 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 96 *///sb.append(gson.toJson(appFunction));
		       sb.append("{'functionId':'" + appFunction.getFunctionId() + "',")
		       			.append("'funKey':'" + appFunction.getFunKey() + "',")
		       			.append("'funName':'" + appFunction.getFunName() + "'}");
		       
		/* 97 */sb.append("}");
		/* 98 */setJsonString(sb.toString());

		/* 100 */return "success";
	}

	public String save() {
		String data = getRequest().getParameter("funUrls");
		String cccc = getRequest().getParameter("cccc");
		System.out.println("======="+data);
		/* 106 */this.appFunctionService.save(this.appFunction);
		/* 107 */setJsonString("{success:true}");
		/* 108 */return "success";
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.oa.action.system.AppFunctionAction JD-Core Version: 0.6.0
 */