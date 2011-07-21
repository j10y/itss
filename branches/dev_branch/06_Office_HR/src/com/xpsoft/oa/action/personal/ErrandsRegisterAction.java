package com.xpsoft.oa.action.personal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.jbpm.pv.ParamField;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.action.flow.FlowRunInfo;
import com.xpsoft.oa.action.flow.ProcessActivityAssistant;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.personal.ErrandsRegister;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProcessRunService;
import com.xpsoft.oa.service.personal.ErrandsRegisterService;
import flexjson.JSONSerializer;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;

public class ErrandsRegisterAction extends BaseAction {

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private JbpmService jbpmService;

	@Resource
	private ErrandsRegisterService errandsRegisterService;
	private ErrandsRegister errandsRegister;
	private Long dateId;

	public Long getDateId() {
		/* 53 */return this.dateId;
	}

	public void setDateId(Long dateId) {
		/* 57 */this.dateId = dateId;
	}

	public ErrandsRegister getErrandsRegister() {
		/* 61 */return this.errandsRegister;
	}

	public void setErrandsRegister(ErrandsRegister errandsRegister) {
		/* 65 */this.errandsRegister = errandsRegister;
	}

	public String list() {
		/* 73 */QueryFilter filter = new QueryFilter(getRequest());
		/* 74 */filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 75 */List list = this.errandsRegisterService.getAll(filter);
		/* 76 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 77 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 79 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[] { "startTime", "endTime" });
		/* 80 */buff.append(serializer.serialize(list));

		/* 82 */buff.append("}");

		/* 84 */this.jsonString = buff.toString();

		/* 86 */return "success";
	}

	public String multiDel() {
		/* 94 */String[] ids = getRequest().getParameterValues("ids");
		/* 95 */if (ids != null) {
			/* 96 */for (String id : ids) {
				/* 97 */this.errandsRegisterService.remove(new Long(id));
			}
		}

		/* 101 */this.jsonString = "{success:true}";

		/* 103 */return "success";
	}

	public String get() {
		/* 111 */ErrandsRegister errandsRegister = (ErrandsRegister) this.errandsRegisterService
				.get(this.dateId);

		/* 113 */Gson gson = new GsonBuilder().setDateFormat(
				"yyyy-MM-dd HH:mm:ss").create();

		/* 115 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 116 */sb.append(gson.toJson(errandsRegister));
		/* 117 */sb.append("}");
		/* 118 */setJsonString(sb.toString());
		/* 119 */return "success";
	}

	public String save() {
		/* 126 */boolean isNew = this.errandsRegister.getDateId() == null;

		/* 128 */this.errandsRegister.setAppUser(ContextUtil.getCurrentUser());
		/* 129 */this.errandsRegister.setStatus(Short.valueOf((short) 0));
		/* 130 */this.errandsRegisterService.save(this.errandsRegister);

		/* 132 */if ((ErrandsRegister.FLAG_LEAVE.equals(this.errandsRegister.getFlag())||ErrandsRegister.FLAG_OUT.equals(this.errandsRegister.getFlag())) &&
		/* 133 */(isNew)) {
			/* 134 */Map fieldMap = constructStartFlowMap(this.errandsRegister);
			      
			/* 136 */ProDefinition proDefintion = this.jbpmService
					.getProDefinitionByKey("pd6212082814169152003");

			/* 138 */if (proDefintion != null) {
				/* 139 */ProcessRun processRun = this.processRunService
						.initNewProcessRun(proDefintion);

				/* 141 */ProcessForm processForm = new ProcessForm();
				/* 142 */processForm.setActivityName("开始");
				/* 143 */processForm.setProcessRun(processRun);

				//FlowRunInfo runInfo = new FlowRunInfo();
						 FlowRunInfo runInfo = new FlowRunInfo(getRequest());
						 runInfo.setParamFields(fieldMap);
				/* 147 */runInfo.setStartFlow(true);

				/* 149 */runInfo.setdAssignId(this.errandsRegister
						.getApprovalId().toString());

				/* 151 */this.processRunService.saveProcessRun(processRun,
						processForm, runInfo);
			} else {
				/* 154 */this.logger.error("请假流程没有定义！");
			}
		}

		/* 158 */setJsonString("{success:true}");
		/* 159 */return "success";
	}

	protected Map<String, ParamField> constructStartFlowMap(
			ErrandsRegister register) {
		/* 165 */String activityName = "开始";
		/* 166 */String processName = "请假";

		/* 168 */Map map = ProcessActivityAssistant.constructFieldMap(
				processName, activityName);

		/* 170 */ParamField pfDateId = (ParamField) map.get("dateId");

		/* 172 */if (pfDateId != null) {
			/* 173 */pfDateId.setValue(register.getDateId().toString());
		}

		/* 176 */SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		/* 177 */ParamField pfOption = (ParamField) map.get("reqDesc");
		/* 178 */if (pfOption != null) {
			/* 179 */pfOption.setValue(register.getDescp());
		}

		/* 182 */ParamField pfStartTime = (ParamField) map.get("startTime");
		/* 183 */if (pfStartTime != null) {
			/* 184 */pfStartTime.setValue(sdf.format(register.getStartTime()));
		}

		/* 187 */ParamField pfEndTime = (ParamField) map.get("endTime");
		/* 188 */if (pfEndTime != null) {
			/* 189 */pfEndTime.setValue(sdf.format(register.getEndTime()));
		}

		/* 192 */ParamField pfApprovalName = (ParamField) map
				.get("approvalName");
		/* 193 */if (pfApprovalName != null) {
			/* 194 */pfApprovalName.setValue(register.getApprovalName());
		}

		/* 197 */return map;
	}
}
