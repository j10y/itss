package com.xpsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppFunction;
import com.xpsoft.oa.model.system.FunUrl;
import com.xpsoft.oa.service.system.FunUrlService;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class FunUrlAction extends BaseAction {

	@Resource
	private FunUrlService funUrlService;
	private FunUrl funUrl;
	private Long urlId;

	public Long getUrlId() {
		return this.urlId;
	}

	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}

	public FunUrl getFunUrl() {
		return this.funUrl;
	}

	public void setFunUrl(FunUrl funUrl) {
		this.funUrl = funUrl;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<FunUrl> list = this.funUrlService.getAll(filter);

		Type type = new TypeToken<List<FunUrl>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		toJson(list, buff);
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public static void toJson(List<FunUrl> list, StringBuffer sb) {
		sb.append("[");
		for (FunUrl f : list) {
			sb.append("{").append("'urlId':'" + f.getUrlId() + "',").append(
					"'functionId':'" + f.getFunctionId() + "',").append(
					"'urlPath':'" + f.getUrlPath() + "'},");
		}
		if (list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");

	}

	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				this.funUrlService.remove(new Long(id));
			}
		}

		this.jsonString = "{success:true}";

		return "success";
	}

	public String get() {
		FunUrl funUrl = (FunUrl) this.funUrlService.get(this.urlId);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(funUrl));
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	public String save() {
		this.funUrlService.save(this.funUrl);
		setJsonString("{success:true}");
		return "success";
	}
}