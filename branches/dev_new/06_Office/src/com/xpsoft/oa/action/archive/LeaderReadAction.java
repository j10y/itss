package com.xpsoft.oa.action.archive;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.archive.LeaderRead;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.archive.ArchUnderTakesService;
import com.xpsoft.oa.service.archive.ArchivesService;
import com.xpsoft.oa.service.archive.LeaderReadService;
import com.xpsoft.oa.service.system.AppUserService;
import flexjson.JSONSerializer;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class LeaderReadAction extends BaseAction {

	@Resource
	private LeaderReadService leaderReadService;
	private LeaderRead leaderRead;

	@Resource
	private ArchivesService archivesService;

	@Resource
	private AppUserService appUserService;
	private Long readId;
	private String leaderOpinion;
	private Short isPass;
	private Archives archives;

	public Archives getArchives() {
		/* 55 */return this.archives;
	}

	public void setArchives(Archives archives) {
		/* 59 */this.archives = archives;
	}

	public Short getIsPass() {
		/* 63 */return this.isPass;
	}

	public void setIsPass(Short isPass) {
		/* 67 */this.isPass = isPass;
	}

	public String getLeaderOpinion() {
		/* 71 */return this.leaderOpinion;
	}

	public void setLeaderOpinion(String leaderOpinion) {
		/* 75 */this.leaderOpinion = leaderOpinion;
	}

	public Long getReadId() {
		/* 79 */return this.readId;
	}

	public void setReadId(Long readId) {
		/* 83 */this.readId = readId;
	}

	public LeaderRead getLeaderRead() {
		/* 87 */return this.leaderRead;
	}

	public void setLeaderRead(LeaderRead leaderRead) {
		/* 91 */this.leaderRead = leaderRead;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		filter.addFilter("Q_archives.archType_SN_EQ", Archives.ARCHIVE_TYPE_RECEIVE.toString());
		List<LeaderRead> list = this.leaderReadService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems())
			.append(",result:");
		
		JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "createtime",
						"archives.issueDate", "archives.createtime" });
		buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				this.leaderReadService.remove(new Long(id));
			}
		}

		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		LeaderRead leaderRead = (LeaderRead) this.leaderReadService
				.get(this.readId);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(leaderRead));
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	public String save() {
		String strArchivesId = getRequest().getParameter("archivesId");
		Short status = getRequest().getParameter("status")!=null && !getRequest().getParameter("status").equals("")? Short.valueOf(getRequest().getParameter("status")) : (short)-1;
		if (StringUtils.isNotEmpty(strArchivesId)) {
			LeaderRead leader = new LeaderRead();
			Archives archives = (Archives) this.archivesService.get(new Long(strArchivesId));
			AppUser user = ContextUtil.getCurrentUser();
			leader.setArchives(archives);
			leader.setLeaderOpinion(this.leaderOpinion);
			leader.setIsPass(this.isPass);
			leader.setUserId(user.getUserId());
			leader.setLeaderName(user.getFullname());
			leader.setCreatetime(new Date());
			this.leaderReadService.save(leader);
			archives.setStatus(Archives.STATUS_DISPATCH);
			if(!status.equals(Short.valueOf("-1"))){
				archives.setStatus(status);
			}
			this.archivesService.save(archives);
		}
		setJsonString("{success:true}");
		return "success";
	}

	public String saveDep() {
		this.archives = ((Archives) this.archivesService.get(this.archives.getArchivesId()));
		String archivesStatus = getRequest().getParameter("archivesStatus");
		if (StringUtils.isNotEmpty(archivesStatus)) {
			this.archives.setStatus(Short.valueOf(Short.parseShort(archivesStatus)));
		}
		this.archivesService.save(this.archives);

		this.leaderRead.setLeaderName(ContextUtil.getCurrentUser().getFullname());
		this.leaderRead.setUserId(ContextUtil.getCurrentUserId());
		this.leaderRead.setArchives(this.archives);
		this.leaderRead.setCreatetime(new Date());
		this.leaderRead.setIsPass(LeaderRead.IS_PASS);
		this.leaderReadService.save(this.leaderRead);

		setJsonString("{success:true,readId:" + this.leaderRead.getReadId() + "}");
		return "success";
	}
}
