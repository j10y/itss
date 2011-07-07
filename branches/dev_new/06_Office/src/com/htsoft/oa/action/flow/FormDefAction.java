package com.htsoft.oa.action.flow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.Constants;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.jbpm.jpdl.Node;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.FileUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.model.archive.ArchivesDoc;
import com.htsoft.oa.model.flow.ExtFormItem;
import com.htsoft.oa.model.flow.FormDef;
import com.htsoft.oa.model.flow.ProDefinition;
import com.htsoft.oa.service.flow.FormDefService;
import com.htsoft.oa.service.flow.JbpmService;
import com.htsoft.oa.service.flow.ProDefinitionService;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;

public class FormDefAction extends BaseAction {

	@Resource
	private ProDefinitionService proDefinitionService;

	@Resource
	private JbpmService jbpmService;

	@Resource
	private FormDefService formDefService;
	private FormDef formDef;
	private Long formDefId;
	private String defId;

	public String getDefId() {
		/* 53 */return this.defId;
	}

	public void setDefId(String defId) {
		/* 57 */this.defId = defId;
	}

	public Long getFormDefId() {
		/* 61 */return this.formDefId;
	}

	public void setFormDefId(Long formDefId) {
		/* 65 */this.formDefId = formDefId;
	}

	public FormDef getFormDef() {
		/* 69 */return this.formDef;
	}

	public void setFormDef(FormDef formDef) {
		/* 73 */this.formDef = formDef;
	}

	public String nodes() {
		/* 78 */List<Node> nodes = this.jbpmService
				.getTaskNodesByDefId(new Long(this.defId));

		/* 80 */StringBuffer sb = new StringBuffer("{data:['");

		/* 82 */for (Node node : nodes) {
			/* 83 */sb.append("'").append(node.getName()).append("',");
		}
		/* 85 */sb.append("]}");
		/* 86 */setJsonString(sb.toString());
		/* 87 */return "success";
	}

	public String addAll() {
		/* 96 */List<Node> nodes = this.jbpmService.getFormNodes(new Long(
				this.defId));
		/* 97 */ProDefinition proDefinition = (ProDefinition) this.proDefinitionService
				.get(new Long(this.defId));

		/* 99 */for (Node node : nodes) {
			/* 100 */FormDef formDef = this.formDefService
					.getByDeployIdActivityName(proDefinition.getDeployId(),
							node.getName());
			/* 101 */if (formDef == null) {
				/* 102 */formDef = new FormDef();
				/* 103 */formDef.setActivityName(node.getName());
				/* 104 */formDef.setColumns(FormDef.DEFAULT_COLUMNS);
				/* 105 */formDef.setFormName(node.getName() + "-表单");
				/* 106 */formDef.setIsEnabled(Constants.ENABLED);
				/* 107 */formDef.setDeployId(proDefinition.getDeployId());
				/* 108 */this.formDefService.save(formDef);
			}
		}
		/* 111 */setJsonString("{success:true}");
		/* 112 */return "success";
	}

	public String select() {
		/* 120 */QueryFilter filter = new QueryFilter(getRequest());
		/* 121 */List<FormDef> list = this.formDefService.getAll(filter);

		/* 123 */Type type = new TypeToken<List<FormDef>>() {}.getType();
		/* 124 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 125 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 127 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();
		/* 128 */buff.append(gson.toJson(list, type));
		/* 129 */buff.append("}");

		/* 131 */this.jsonString = buff.toString();
		/* 132 */return "success";
	}

	public String list() {
		/* 140 */ProDefinition proDefinition = (ProDefinition) this.proDefinitionService
				.get(new Long(this.defId));

		/* 142 */List nodes = this.jbpmService
				.getFormNodes(new Long(this.defId));

		/* 144 */List<FormDef> formDefs = this.formDefService
				.getByDeployId(proDefinition.getDeployId());

		/* 146 */StringBuffer buff = new StringBuffer("{result:[");

		/* 148 */for (int i = 0; i < nodes.size(); i++) {
			/* 149 */String nodeName = ((Node) nodes.get(i)).getName();
			/* 150 */buff.append("{activityName:'").append(nodeName)
					.append("',deployId:'" + proDefinition.getDeployId())
					.append("'");

			/* 152 */for (FormDef def : formDefs) {
				/* 153 */if (nodeName.equals(def.getActivityName())) {
					/* 154 */buff.append(",formDefId:'")
							.append(def.getFormDefId()).append("',formName:'")
							.append(def.getFormName()).append("'");
					/* 155 */break;
				}
			}
			/* 158 */buff.append("},");
		}

		/* 161 */if (nodes.size() > 0) {
			/* 162 */buff.deleteCharAt(buff.length() - 1);
		}

		/* 165 */buff.append("]}");

		/* 168 */this.jsonString = buff.toString();

		/* 170 */return "success";
	}

	public String multiDel() {
		/* 178 */String[] ids = getRequest().getParameterValues("ids");
		/* 179 */if (ids != null) {
			/* 180 */for (String id : ids) {
				/* 181 */this.formDefService.remove(new Long(id));
			}
		}

		/* 185 */this.jsonString = "{success:true}";

		/* 187 */return "success";
	}

	public String get() {
		/* 195 */FormDef formDef = (FormDef) this.formDefService
				.get(this.formDefId);

		/* 197 */Gson gson = new Gson();

		/* 199 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 200 */sb.append(gson.toJson(formDef));
		/* 201 */sb.append("}");
		/* 202 */setJsonString(sb.toString());

		/* 204 */return "success";
	}

	public String save() {
		/* 211 */String activityName = getRequest()
				.getParameter("activityName");
		/* 212 */FormDef formDef = (FormDef) this.formDefService
				.get(this.formDefId);

		/* 214 */String extDef = getRequest().getParameter("extDef");
		/* 215 */formDef.setExtDef(extDef);
		/* 216 */this.formDefService.save(formDef);

		/* 220 */String extFormDef = getRequest().getParameter("extFormDef");
		/* 221 */String formItemDef = getRequest().getParameter("formItemDef");

		/* 223 */this.logger.info("extFormDef:" + extFormDef);
		/* 224 */this.logger.info("formItemDef:" + formItemDef);

		/* 226 */ProDefinition proDefinition = this.proDefinitionService
				.getByDeployId(formDef.getDeployId());
		/* 227 */String formPath = AppUtil.getAppAbsolutePath()
				+ "/WEB-INF/FlowForm/" + proDefinition.getName();

		/* 229 */File flowDirPath = new File(formPath);
		/* 230 */if (!flowDirPath.exists()) {
			/* 231 */flowDirPath.mkdirs();
		}
		/* 233 */Gson gson = new Gson();

		/* 235 */ExtFormItem[] formItems = gson.fromJson("[" + formItemDef
				+ "]", ExtFormItem[].class);
		/* 236 */StringBuffer xmlBuf = new StringBuffer();

		/* 238 */if (formItems != null) {
			/* 239 */xmlBuf
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			/* 240 */xmlBuf.append("<fields>\n");
			/* 241 */for (ExtFormItem item : formItems) {
				/* 242 */xmlBuf.append("\t<field name=\"" + item.getName()
						+ "\" label=\"" + item.getFieldLabel() +
						/* 243 */"\" type=\"" + item.getType() + "\" length=\""
						+ item.getMaxLength() + "\" isShowed=\""
						+ item.getIsShowed() + "\"/>\n");
			}
			/* 245 */xmlBuf.append("</fields>\n");
		}

		/* 248 */if (xmlBuf.length() > 0) {
			/* 249 */String fieldFilePath = formPath + "/" + activityName
					+ "-fields.xml";
			/* 250 */FileUtil.writeFile(fieldFilePath, xmlBuf.toString());
		}

		/* 253 */if (proDefinition != null) {
			/* 255 */String extFilePath = formPath + "/" + activityName + ".vm";
			/* 256 */FileUtil.writeFile(extFilePath, extFormDef);
		}
		/* 258 */return "success";
	}

	public String saveVmXml() {
		/* 267 */String deployId = getRequest().getParameter("deployId");
		/* 268 */String activityName = getRequest()
				.getParameter("activityName");

		/* 270 */String vmSources = getRequest().getParameter("vmSources");

		/* 272 */String xmlSources = getRequest().getParameter("xmlSources");

		/* 274 */ProDefinition proDefinition = this.proDefinitionService
				.getByDeployId(deployId);
		/* 275 */String filePath = AppUtil.getAppAbsolutePath()
				+ "/WEB-INF/FlowForm/" + proDefinition.getName() + "/"
				+ activityName;

		/* 277 */String vmFilePath = filePath + ".vm";
		/* 278 */String xmlFilePath = filePath + "-fields.xml";

		/* 280 */FileUtil.writeFile(vmFilePath, vmSources);

		/* 282 */FileUtil.writeFile(xmlFilePath, xmlSources);

		/* 284 */setJsonString("{success:true}");

		/* 286 */return "success";
	}

	public String getVmXml() {
		/* 294 */String deployId = getRequest().getParameter("deployId");
		/* 295 */String activityName = getRequest()
				.getParameter("activityName");

		/* 297 */ProDefinition proDefinition = this.proDefinitionService
				.getByDeployId(deployId);
		/* 298 */String filePath = AppUtil.getAppAbsolutePath()
				+ "/WEB-INF/FlowForm/" + proDefinition.getName() + "/"
				+ activityName;

		/* 300 */String vmFilePath = filePath + ".vm";
		/* 301 */String xmlFilePath = filePath + "-fields.xml";

		/* 303 */String vmSources = FileUtil.readFile(vmFilePath);
		/* 304 */String xmlSources = FileUtil.readFile(xmlFilePath);
		/* 305 */Gson gson = new Gson();

		/* 307 */setJsonString("{success:true,vmSources:"
				+ gson.toJson(vmSources) + ",xmlSources:"
				+ gson.toJson(xmlSources) + "}");

		/* 309 */return "success";
	}
}