/*     */package com.xpsoft.oa.action.flow;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.jbpm.jpdl.JpdlConverter;
import com.xpsoft.core.util.BeanUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProType;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProDefinitionService;
import com.xpsoft.oa.service.flow.ProTypeService;
import flexjson.JSONSerializer;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

public class ProDefinitionAction extends BaseAction {

	@Resource
	private ProDefinitionService proDefinitionService;

	@Resource
	private ProTypeService proTypeService;

	@Resource
	private JbpmService jbpmService;
	private ProDefinition proDefinition;
	private Long defId;

	public Long getDefId() {
		/* 43 */return this.defId;
	}

	public void setDefId(Long defId) {
		/* 47 */this.defId = defId;
	}

	public ProDefinition getProDefinition() {
		/* 51 */return this.proDefinition;
	}

	public void setProDefinition(ProDefinition proDefinition) {
		/* 55 */this.proDefinition = proDefinition;
	}

	public String list() {
		/* 63 */QueryFilter filter = new QueryFilter(getRequest());

		/* 65 */String typeId = getRequest().getParameter("typeId");

		/* 67 */if ((StringUtils.isNotEmpty(typeId)) && (!"0".equals(typeId))) {
			/* 68 */filter.addFilter("Q_proType.typeId_L_EQ", typeId);
		}

		/* 71 */List list = this.proDefinitionService.getAll(filter);

		/* 73 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 74 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 76 */JSONSerializer serializer = JsonUtil.getJSONSerializer(
				new String[] { "createtime" }).exclude(
				new String[] { "defXml" });

		/* 78 */buff.append(serializer.serialize(list));
		/* 79 */buff.append("}");

		/* 81 */this.jsonString = buff.toString();

		/* 83 */return "success";
	}

	public String multiDel() {
		/* 91 */String[] ids = getRequest().getParameterValues("ids");
		/* 92 */if (ids != null) {
			/* 93 */for (String id : ids) {
				/* 96 */this.jbpmService.doUnDeployProDefinition(new Long(id));
			}
		}

		/* 100 */this.jsonString = "{success:true}";

		/* 102 */return "success";
	}

	public String get() {
		/* 111 */if (this.defId != null) {
			/* 112 */this.proDefinition = ((ProDefinition) this.proDefinitionService
					.get(this.defId));
		} else {
			/* 114 */this.proDefinition = new ProDefinition();
			/* 115 */String proTypeId = getRequest().getParameter("proTypeId");
			/* 116 */if (StringUtils.isNotEmpty(proTypeId)) {
				/* 117 */ProType proType = (ProType) this.proTypeService
						.get(new Long(proTypeId));
				/* 118 */this.proDefinition.setProType(proType);
			}

		}

		/* 123 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[] { "createtime" });

		/* 125 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 126 */sb.append(serializer.serialize(this.proDefinition));
		/* 127 */sb.append("}");
		/* 128 */setJsonString(sb.toString());

		/* 130 */return "success";
	}

	public String defSave() {
		/* 134 */this.logger.info("...eneter defSave......");

		/* 136 */if (StringUtils.isNotEmpty(this.proDefinition.getDrawDefXml())) {
			/* 138 */Long uuid = Long.valueOf(Math.abs(UUID.randomUUID()
					.getLeastSignificantBits()));

			/* 140 */String defXml = JpdlConverter.JpdlGen(
					this.proDefinition.getDrawDefXml(), "pd" + uuid);

			/* 142 */defXml = defXml.replace("<process",
					"<process xmlns=\"http://jbpm.org/4.0/jpdl\"");

			/* 144 */if (this.logger.isDebugEnabled()) {
				/* 145 */this.logger.debug("jbpmXml:" + defXml);
			}

			/* 148 */this.proDefinition.setDefXml(defXml);

			/* 150 */save();
		}

		/* 153 */return "success";
	}

	public String save() {
		/* 161 */Long proTypeId = this.proDefinition.getProTypeId();
		/* 162 */if (proTypeId != null) {
			/* 163 */ProType proType = (ProType) this.proTypeService
					.get(proTypeId);
			/* 164 */this.proDefinition.setProType(proType);
		}
		/* 166 */if (this.proDefinition.getDefId() != null) {
			/* 167 */ProDefinition proDef = (ProDefinition) this.proDefinitionService
					.get(this.proDefinition.getDefId());
			try {
				/* 169 */BeanUtil.copyNotNullProperties(proDef,
						this.proDefinition);
				/* 170 */this.jbpmService.saveOrUpdateDeploy(proDef);
			} catch (Exception ex) {
				/* 172 */this.logger.error(ex.getMessage());
			}
		} else {
			/* 175 */this.proDefinition.setCreatetime(new Date());

			/* 177 */if (this.logger.isDebugEnabled()) {
				/* 178 */this.logger.info("---start deploy---");
			}

			/* 181 */this.jbpmService.saveOrUpdateDeploy(this.proDefinition);
		}
		/* 183 */setJsonString("{success:true}");
		/* 184 */return "success";
	}

	/**
	 * 提供给simpleStore使用
	 * 
	 * @return
	 */
	public String select() {
		QueryFilter filter = new QueryFilter(getRequest());

		String typeId = getRequest().getParameter("typeId");

		if ((StringUtils.isNotEmpty(typeId)) && (!"0".equals(typeId))) {
			filter.addFilter("Q_proType.typeId_L_EQ", typeId);
		}

		List<ProDefinition> list = this.proDefinitionService.getAll(filter);

		StringBuffer sb = new StringBuffer("[");

		for(ProDefinition proDef : list){
			sb.append("['").append(proDef.getDefId()).append("','").append(proDef.getName()).append("'],");
		}
		
		sb.append("]");

		this.jsonString = sb.toString();

		return "success";
	}
}