package com.xpsoft.oa.action.archive;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.archive.ArchivesAttend;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.archive.ArchivesAttendService;
import com.xpsoft.oa.service.archive.ArchivesDocService;
import com.xpsoft.oa.service.archive.ArchivesService;
import com.xpsoft.oa.service.archive.DocHistoryService;
import com.xpsoft.oa.service.archive.LeaderReadService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.FileAttachService;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class ArchivesAttendAction extends BaseAction {

	@Resource
	private ArchivesAttendService archivesAttendService;

	@Resource
	private ArchivesService archivesService;

	@Resource
	private AppUserService appUserService;

	@Resource
	private LeaderReadService leaderReadService;

	@Resource
	private ArchivesDocService archivesDocService;

	@Resource
	private FileAttachService fileAttachService;

	@Resource
	private DocHistoryService docHistoryService;
	private ArchivesAttend archivesAttend;
	private Long attendId;
	private Archives archives;

	public Archives getArchives() {
		/* 61 */return this.archives;
	}

	public void setArchives(Archives archives) {
		/* 65 */this.archives = archives;
	}

	public Long getAttendId() {
		/* 69 */return this.attendId;
	}

	public void setAttendId(Long attendId) {
		/* 73 */this.attendId = attendId;
	}

	public ArchivesAttend getArchivesAttend() {
		/* 77 */return this.archivesAttend;
	}

	public void setArchivesAttend(ArchivesAttend archivesAttend) {
		/* 81 */this.archivesAttend = archivesAttend;
	}

	public String list() {
		/* 89 */QueryFilter filter = new QueryFilter(getRequest());
		/* 90 */List<ArchivesAttend> list = this.archivesAttendService
				.getAll(filter);

		/* 92 */Type type = new TypeToken<List<ArchivesAttend>>() {
		}
		/* 92 */.getType();
		/* 93 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 94 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 96 */Gson gson = new Gson();
		/* 97 */buff.append(gson.toJson(list, type));
		/* 98 */buff.append("}");

		/* 100 */this.jsonString = buff.toString();

		/* 102 */return "success";
	}

	public String multiDel() {
		/* 110 */String[] ids = getRequest().getParameterValues("ids");
		/* 111 */if (ids != null) {
			/* 112 */for (String id : ids) {
				/* 113 */this.archivesAttendService.remove(new Long(id));
			}
		}

		/* 117 */this.jsonString = "{success:true}";

		/* 119 */return "success";
	}

	public String get() {
		/* 127 */ArchivesAttend archivesAttend = (ArchivesAttend) this.archivesAttendService
				.get(this.attendId);

		/* 129 */Gson gson = new Gson();

		/* 131 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 132 */sb.append(gson.toJson(archivesAttend));
		/* 133 */sb.append("}");
		/* 134 */setJsonString(sb.toString());

		/* 136 */return "success";
	}

	public String save() {
		/* 142 */String archivesStatus = getRequest().getParameter(
				"archivesStatus");
		/* 143 */this.archives = ((Archives) this.archivesService
				.get(this.archives.getArchivesId()));
		/* 144 */if (StringUtils.isNotEmpty(archivesStatus)) {
			/* 145 */this.archives.setStatus(Short.valueOf(Short
					.parseShort(archivesStatus)));
		}
		/* 147 */this.archivesService.save(this.archives);

		/* 149 */this.archivesAttend.setArchives(this.archives);
		/* 150 */this.archivesAttend.setUserID(ContextUtil.getCurrentUserId());
		/* 151 */this.archivesAttend.setFullname(ContextUtil.getCurrentUser()
				.getFullname());
		/* 152 */this.archivesAttend.setExecuteTime(new Date());
		/* 153 */this.archivesAttendService.save(this.archivesAttend);

		/* 155 */setJsonString("{success:true,attendId:"
				+ this.archivesAttend.getAttendId() + "}");
		/* 156 */return "success";
	}

	public String proof() {
		/* 163 */String archivesId = getRequest().getParameter("archivesId");
		/* 164 */String status = getRequest().getParameter("status");
		/* 165 */String docs = getRequest().getParameter("docs");
		/* 166 */Archives archives = (Archives) this.archivesService
				.get(new Long(archivesId));
		/* 167 */AppUser curUser = ContextUtil.getCurrentUser();

		/* 201 */archives.setStatus(Short.valueOf(Short.parseShort(status)));

		/* 203 */this.archivesService.save(archives);

		/* 205 */this.archivesAttend.setArchives(archives);
		/* 206 */this.archivesAttend.setExecuteTime(new Date());
		/* 207 */this.archivesAttend.setUserID(ContextUtil.getCurrentUserId());
		/* 208 */this.archivesAttend.setFullname(ContextUtil.getCurrentUser()
				.getFullname());
		/* 209 */this.archivesAttendService.save(this.archivesAttend);

		/* 211 */setJsonString("{success:true}");
		/* 212 */return "success";
	}
}
