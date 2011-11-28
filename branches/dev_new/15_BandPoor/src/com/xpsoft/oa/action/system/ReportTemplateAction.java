package com.xpsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.ReportParam;
import com.xpsoft.oa.model.system.ReportTemplate;
import com.xpsoft.oa.service.system.ReportParamService;
import com.xpsoft.oa.service.system.ReportTemplateService;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class ReportTemplateAction extends BaseAction {

	@Resource
	private ReportTemplateService reportTemplateService;
	private ReportTemplate reportTemplate;

	@Resource
	private ReportParamService reportParamService;
	private Long reportId;

	public Long getReportId() {
		/* 43 */return this.reportId;
	}

	public void setReportId(Long reportId) {
		/* 47 */this.reportId = reportId;
	}

	public ReportTemplate getReportTemplate() {
		/* 51 */return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		/* 55 */this.reportTemplate = reportTemplate;
	}

	public String list() {
		/* 63 */QueryFilter filter = new QueryFilter(getRequest());
		/* 64 */List<ReportTemplate> list = this.reportTemplateService.getAll(filter);

		/* 66 */Type type = new TypeToken<List<ReportTemplate>>() {
		}
		/* 66 */.getType();
		/* 67 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 68 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 70 */Gson gson = new Gson();
		/* 71 */buff.append(gson.toJson(list, type));
		/* 72 */buff.append("}");

		/* 74 */this.jsonString = buff.toString();

		/* 76 */return "success";
	}

	public String multiDel() {
		/* 84 */String[] ids = getRequest().getParameterValues("ids");
		/* 85 */if (ids != null) {
			/* 86 */for (String id : ids) {
				/* 87 */List<ReportParam> list = this.reportParamService
						.findByRepTemp(new Long(id));
				/* 88 */for (ReportParam rp : list) {
					/* 89 */this.reportParamService.remove(rp);
				}
				/* 91 */this.reportTemplateService.remove(new Long(id));
			}
		}

		/* 95 */this.jsonString = "{success:true}";

		/* 97 */return "success";
	}

	public String get() {
		/* 105 */ReportTemplate reportTemplate = (ReportTemplate) this.reportTemplateService
				.get(this.reportId);

		/* 107 */Gson gson = new Gson();

		/* 109 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 110 */sb.append(gson.toJson(reportTemplate));
		/* 111 */sb.append("}");
		/* 112 */setJsonString(sb.toString());

		/* 114 */return "success";
	}

	public String save() {
		/* 121 */if (this.reportTemplate.getReportId() == null) {
			/* 122 */this.reportTemplate.setCreatetime(new Date());
			/* 123 */this.reportTemplate.setUpdatetime(new Date());
		} else {
			/* 125 */this.reportTemplate.setUpdatetime(new Date());
		}
		/* 127 */this.reportTemplateService.save(this.reportTemplate);
		/* 128 */setJsonString("{success:true}");
		/* 129 */return "success";
	}

	public String load() {
		/* 136 */String strReportId = getRequest().getParameter("reportId");
		/* 137 */if (StringUtils.isNotEmpty(strReportId)) {
			/* 138 */List<ReportParam> list = this.reportParamService
					.findByRepTemp(new Long(strReportId));
			/* 139 */StringBuffer sb = new StringBuffer("[");
			/* 140 */for (ReportParam rp : list) {
				/* 141 */sb.append("{xtype:'label',text:'" + rp.getParamName()
						+ "'},{");
				/* 142 */if ("date".equals(rp.getParamType()))
					/* 143 */sb.append("xtype:'datefield',format:'Y-m-d'");
				/* 144 */else if ("datetime".equals(rp.getParamType()))
					/* 145 */sb
							.append("xtype:'datetimefield',format:'Y-m-d H:i:s'");
				else {
					/* 147 */sb.append("xtype:'textfield'");
				}
				/* 149 */sb.append(",name:'" + rp.getParamKey() + "',value:'"
						+ rp.getDefaultVal() + "'},");
			}
			/* 151 */if (list.size() > 0) {
				/* 152 */sb.deleteCharAt(sb.length() - 1);
			}
			/* 154 */sb.append("]");
			/* 155 */setJsonString("{success:true,data:" + sb.toString() + "}");
		} else {
			/* 157 */setJsonString("{success:false}");
		}
		/* 159 */return "success";
	}

	public String submit() {
		/* 165 */Map map = getRequest().getParameterMap();
		/* 166 */Iterator it = map.entrySet().iterator();
		/* 167 */StringBuffer sb = new StringBuffer();
		/* 168 */while (it.hasNext()) {
			/* 169 */Map.Entry entry = (Map.Entry) it.next();
			/* 170 */String key = (String) entry.getKey();
			/* 171 */String[] value = (String[]) entry.getValue();
			/* 172 */sb.append("&" + key + "=" + value[0]);
		}
		/* 174 */setJsonString("{success:true,data:'" + sb.toString() + "'}");
		/* 175 */return "success";
	}
}
