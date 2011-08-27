package com.xpsoft.oa.action.archive;

import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.ArchivesDist;
import com.xpsoft.oa.service.archive.ArchivesDistService;

import flexjson.JSONSerializer;

public class ArchivesDistAction extends BaseAction {

	@Resource
	private ArchivesDistService ArchivesDistService;
	private ArchivesDist ArchivesDist;
	private Long archDistId;

	public Long getarchDistId() {
		/* 37 */return this.archDistId;
	}

	public void setarchDistId(Long archDistId) {
		/* 41 */this.archDistId = archDistId;
	}

	public ArchivesDist getArchivesDist() {
		/* 45 */return this.ArchivesDist;
	}

	public void setArchivesDist(ArchivesDist ArchivesDist) {
		/* 49 */this.ArchivesDist = ArchivesDist;
	}

	public String list() {
		/* 56 */QueryFilter filter = new QueryFilter(getRequest());
		/* 57 */filter.addFilter("Q_signUserID_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 58 */List list = this.ArchivesDistService.getAll(filter);

		/* 64 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 65 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		/* 66 */JSONSerializer json = JsonUtil
				.getJSONSerializer(new String[] { "archives.createtime" });
		/* 67 */buff.append(json.serialize(list));

		/* 70 */buff.append("}");

		/* 72 */this.jsonString = buff.toString();

		/* 74 */return "success";
	}

	public String multiDel() {
		/* 82 */String[] ids = getRequest().getParameterValues("ids");
		/* 83 */if (ids != null) {
			/* 84 */for (String id : ids) {
				/* 85 */this.ArchivesDistService.remove(new Long(id));
			}
		}

		/* 89 */this.jsonString = "{success:true}";

		/* 91 */return "success";
	}

	public String get() {
		/* 99 */ArchivesDist ArchivesDist = (ArchivesDist) this.ArchivesDistService
				.get(this.archDistId);

		/* 101 */Gson gson = new Gson();

		/* 103 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 104 */sb.append(gson.toJson(ArchivesDist));
		/* 105 */sb.append("}");
		/* 106 */setJsonString(sb.toString());

		/* 108 */return "success";
	}

	public String save() {
		/* 114 */this.ArchivesDistService.save(this.ArchivesDist);
		/* 115 */setJsonString("{success:true}");
		/* 116 */return "success";
	}
	
	public String view() {		
		ArchivesDist dist = this.ArchivesDistService.get(Long.valueOf(getRequest().getParameter("archDistId")));
		dist.setStatus(Short.valueOf("1"));
		this.ArchivesDistService.save(dist);
		setJsonString("{success:true}");
		return "success";
	}
}
