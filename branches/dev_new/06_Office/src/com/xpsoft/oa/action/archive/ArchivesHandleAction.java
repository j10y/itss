package com.xpsoft.oa.action.archive;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.archive.ArchivesHandle;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.archive.ArchivesHandleService;
import com.xpsoft.oa.service.archive.ArchivesService;
import flexjson.JSONSerializer;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class ArchivesHandleAction extends BaseAction {

	@Resource
	private ArchivesHandleService archivesHandleService;
	private ArchivesHandle archivesHandle;

	@Resource
	private ArchivesService archivesService;
	private Long handleId;
	private String handleOpinion;
	private Short isPass;
	private Long archiveId;
	
	//归档部门
	protected Long filedDeptId;

	protected String filedDeptName;
	
	//归档类型
	protected Long recFiledTypeId;

	protected String recFiledTypeName;

	public Long getArchiveId() {
		/* 47 */return this.archiveId;
	}

	public void setArchiveId(Long archiveId) {
		/* 51 */this.archiveId = archiveId;
	}

	public String getHandleOpinion() {
		/* 55 */return this.handleOpinion;
	}

	public void setHandleOpinion(String handleOpinion) {
		/* 59 */this.handleOpinion = handleOpinion;
	}

	public Short getIsPass() {
		/* 63 */return this.isPass;
	}

	public void setIsPass(Short isPass) {
		/* 67 */this.isPass = isPass;
	}

	public Long getHandleId() {
		/* 71 */return this.handleId;
	}

	public void setHandleId(Long handleId) {
		/* 75 */this.handleId = handleId;
	}

	public ArchivesHandle getArchivesHandle() {
		/* 79 */return this.archivesHandle;
	}

	public void setArchivesHandle(ArchivesHandle archivesHandle) {
		/* 83 */this.archivesHandle = archivesHandle;
	}

	public String list() {
		/* 91 */QueryFilter filter = new QueryFilter(getRequest());
		/* 92 */filter.addFilter("Q_userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());

		/* 95 */List list = this.archivesHandleService.getAll(filter);

		/* 97 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 98 */.append(filter.getPagingBean().getTotalItems()).append(
		/* 99 */",result:");
		/* 100 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[] { "createtime",
						"archives.issueDate", "archives.createtime" });
		/* 101 */buff.append(serializer.exclude(
				new String[] { "archives.archRecType", "class" }).serialize(
				list));
		/* 102 */buff.append("}");

		/* 104 */this.jsonString = buff.toString();

		/* 106 */return "success";
	}

	public String multiDel() {
		/* 116 */String[] ids = getRequest().getParameterValues("ids");
		/* 117 */if (ids != null) {
			/* 118 */for (String id : ids) {
				/* 119 */this.archivesHandleService.remove(new Long(id));
			}
		}

		/* 123 */this.jsonString = "{success:true}";

		/* 125 */return "success";
	}

	public String get() {
		/* 134 */ArchivesHandle archivesHandle = (ArchivesHandle) this.archivesHandleService
				.get(this.handleId);

		/* 136 */Gson gson = new Gson();

		/* 138 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 139 */sb.append(gson.toJson(archivesHandle));
		/* 140 */sb.append("}");
		/* 141 */setJsonString(sb.toString());

		/* 143 */return "success";
	}

	public String save() {
		Short status = getRequest().getParameter("status")!=null&&!getRequest().getParameter("status").equals("") ? Short.valueOf(getRequest().getParameter("status")): (short)-1;
		ArchivesHandle arh = new ArchivesHandle();
		AppUser user = ContextUtil.getCurrentUser();
		Archives archives = (Archives) this.archivesService
				.get(this.archiveId);
			arh.setArchives(archives);
			arh.setCreatetime(new Date());
			arh.setFillTime(new Date());
			arh.setHandleOpinion(this.handleOpinion);
			arh.setIsPass(this.isPass);
			arh.setUserId(user.getUserId());
			arh.setUserFullname(user.getFullname());
			arh.setFiledDeptId(this.filedDeptId);
			arh.setFiledDeptName(this.filedDeptName);
			arh.setRecFiledTypeId(this.recFiledTypeId);
			arh.setRecFiledTypeName(this.recFiledTypeName);
			this.archivesHandleService.save(arh);
			String signIds = getRequest().getParameter("handlerUserIds");
			if (StringUtils.isNotEmpty(signIds)) {
				String[] signId = signIds.split(",");
				int size = signId.length;
				if (size < 2) {
				archives.setStatus(Archives.STATUS_LEADERREAD);
			} else {
				int handlerSize = this.archivesHandleService
						.countHandler(archives.getArchivesId());
				if (size == handlerSize)
					archives.setStatus(Archives.STATUS_LEADERREAD);
				else {
					archives.setStatus(Archives.STATUS_HANDLEING);
				}
			}
			if(!status.equals(Short.valueOf("-1"))){
				archives.setStatus(Archives.STATUS_HANDLEING);
			}else{
				archives.setStatus(status);
			}
			this.archivesService.save(archives);
			
		}
		setJsonString("{success:true}");
		return "success";
	}

	public Long getFiledDeptId() {
		return filedDeptId;
	}

	public void setFiledDeptId(Long filedDeptId) {
		this.filedDeptId = filedDeptId;
	}

	public String getFiledDeptName() {
		return filedDeptName;
	}

	public void setFiledDeptName(String filedDeptName) {
		this.filedDeptName = filedDeptName;
	}

	public Long getRecFiledTypeId() {
		return recFiledTypeId;
	}

	public void setRecFiledTypeId(Long recFiledTypeId) {
		this.recFiledTypeId = recFiledTypeId;
	}

	public String getRecFiledTypeName() {
		return recFiledTypeName;
	}

	public void setRecFiledTypeName(String recFiledTypeName) {
		this.recFiledTypeName = recFiledTypeName;
	}
}
