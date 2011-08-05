package com.xpsoft.oa.action.archive;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.ArchDispatch;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.archive.ArchivesDep;
import com.xpsoft.oa.model.system.AppRole;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.archive.ArchDispatchService;
import com.xpsoft.oa.service.archive.ArchUnderTakesService;
import com.xpsoft.oa.service.archive.ArchivesDepService;
import com.xpsoft.oa.service.archive.ArchivesService;
import com.xpsoft.oa.service.system.AppRoleService;
import com.xpsoft.oa.service.system.AppUserService;
import flexjson.JSONSerializer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class ArchDispatchAction extends BaseAction {

	@Resource
	private ArchDispatchService archDispatchService;
	private ArchDispatch archDispatch;

	@Resource
	private ArchivesService archivesService;

	@Resource
	private AppUserService appUserService;

	@Resource
	private AppRoleService appRoleService;

	@Resource
	private ArchivesDepService archivesDepService;
	
	@Resource
	ArchUnderTakesService undertakesService;
	
	private Long dispatchId;
	private Long archivesId;
	private Short archUserType;
	private String readFeedback;

	public Short getArchUserType() {
		/* 68 */return this.archUserType;
	}

	public void setArchUserType(Short archUserType) {
		/* 72 */this.archUserType = archUserType;
	}

	public String getReadFeedback() {
		/* 76 */return this.readFeedback;
	}

	public void setReadFeedback(String readFeedback) {
		/* 80 */this.readFeedback = readFeedback;
	}

	public Long getArchivesId() {
		/* 84 */return this.archivesId;
	}

	public void setArchivesId(Long archivesId) {
		/* 88 */this.archivesId = archivesId;
	}

	public Long getDispatchId() {
		/* 92 */return this.dispatchId;
	}

	public void setDispatchId(Long dispatchId) {
		/* 96 */this.dispatchId = dispatchId;
	}

	public ArchDispatch getArchDispatch() {
		/* 100 */return this.archDispatch;
	}

	public void setArchDispatch(ArchDispatch archDispatch) {
		/* 104 */this.archDispatch = archDispatch;
	}

	public String list() {
		/* 112 */QueryFilter filter = new QueryFilter(getRequest());
		/* 113 */filter.addFilter("Q_userId_L_EQ", ContextUtil
				.getCurrentUserId()
				/* 114 */.toString());
		/* 115 */List list = this.archDispatchService.getAll(filter);

		/* 118 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 119 */.append(filter.getPagingBean().getTotalItems()).append(
		/* 120 */",result:");

		/* 124 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[] { "dispatchTime",
						"archives.issueDate", "archives.createtime" });
		/* 125 */buff.append(serializer.exclude(new String[] { "class" })
		/* 126 */.serialize(list));
		/* 127 */buff.append("}");

		/* 129 */this.jsonString = buff.toString();

		/* 131 */return "success";
	}

	public String disList() {
		/* 138 */PagingBean pb = getInitPagingBean();
		/* 139 */List list = this.archDispatchService.findByUser(
		/* 140 */ContextUtil.getCurrentUser(), pb);
		/* 141 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 142 */.append(pb.getTotalItems()).append(",result:");

		/* 144 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[] { "dispatchTime" });
		/* 145 */buff.append(serializer.exclude(new String[] { "class" })
		/* 146 */.serialize(list));
		/* 147 */buff.append("}");

		/* 149 */this.jsonString = buff.toString();
		/* 150 */return "success";
	}

	public String multiDel() {
		/* 160 */String[] ids = getRequest().getParameterValues("ids");
		/* 161 */if (ids != null) {
			/* 162 */for (String id : ids) {
				/* 163 */this.archDispatchService.remove(new Long(id));
			}
		}

		/* 167 */this.jsonString = "{success:true}";

		/* 169 */return "success";
	}

	public String get() {
		/* 178 */ArchDispatch archDispatch = (ArchDispatch) this.archDispatchService
				.get(this.dispatchId);

		/* 180 */Gson gson = new Gson();

		/* 182 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 183 */sb.append(gson.toJson(archDispatch));
		/* 184 */sb.append("}");
		/* 185 */setJsonString(sb.toString());

		/* 187 */return "success";
	}

	public String save() {
		/* 194 */Archives archives = (Archives) this.archivesService
				.get(this.archivesId);
		/* 195 */if (archives != null) {
			/* 196 */ArchDispatch archDispatch = new ArchDispatch();
			/* 197 */AppUser user = ContextUtil.getCurrentUser();
			/* 198 */archDispatch.setArchives(archives);
			/* 199 */archDispatch.setArchUserType(this.archUserType);
			/* 200 */archDispatch.setUserId(user.getUserId());
			/* 201 */archDispatch.setFullname(user.getFullname());
			/* 202 */archDispatch.setDispatchTime(new Date());
			/* 203 */archDispatch.setSubject(archives.getSubject());
			/* 204 */archDispatch.setIsRead(ArchDispatch.HAVE_READ);
			/* 205 */archDispatch.setReadFeedback(this.readFeedback);
			/* 206 */this.archDispatchService.save(archDispatch);

			/* 208 */if (this.archUserType.compareTo(ArchDispatch.IS_DISPATCH) == 0) {
				/* 209 */archives.setStatus(Archives.STATUS_READ);

				/* 210 */this.archivesService.save(archives);
			} else {
				/* 212 */String signUserIds = getRequest().getParameter(
						"undertakeUserIds");
				/* 213 */String cruArchDepId = getRequest().getParameter(
						"cruArchDepId");
				/* 214 */if (StringUtils.isNotEmpty(signUserIds)) {
					/* 215 */String[] signId = signUserIds.split(",");
					/* 216 */int size = signId.length;
					/* 217 */if (size < 2) {
						/* 218 */if ((this.archUserType
								.compareTo(ArchDispatch.IS_UNDERTAKE) == 0)
								&&
								/* 219 */(StringUtils.isNotEmpty(cruArchDepId))
								&& (cruArchDepId.indexOf("$") == -1)) {
							/* 220 */ArchivesDep archivesDep = (ArchivesDep) this.archivesDepService
									.get(new Long(cruArchDepId));
							/* 221 */StringBuffer sb = new StringBuffer("<div>"
									+ this.readFeedback);
							/* 222 */SimpleDateFormat sdf = new SimpleDateFormat();
							/* 223 */sdf.format(new Date());
							/* 224 */sb
									.append("--")
									.append(ContextUtil.getCurrentUser()
											.getFullname()).append("--")
									.append(sdf.format(new Date()))
									.append("</div>");
							/* 225 */archivesDep.setHandleFeedback(sb
									.toString());
							/* 226 */this.archivesDepService.save(archivesDep);
						}

						/* 229 */archives.setStatus(Archives.STATUS_END);
					} else {
						/* 231 */int recordSize = this.archDispatchService
								.countArchDispatch(archives.getArchivesId());
						/* 232 */if ((this.archUserType
								.compareTo(ArchDispatch.IS_UNDERTAKE) == 0)
								&&
								/* 233 */(StringUtils.isNotEmpty(cruArchDepId))
								&& (cruArchDepId.indexOf("$") == -1)) {
							/* 234 */ArchivesDep archivesDep = (ArchivesDep) this.archivesDepService
									.get(new Long(cruArchDepId));
							/* 235 */StringBuffer sb = new StringBuffer();
							/* 236 */if (archivesDep.getHandleFeedback() != null) {
								/* 237 */sb.append(archivesDep
										.getHandleFeedback());
							}
							/* 239 */sb.append("<div>" + this.readFeedback);
							/* 240 */SimpleDateFormat sdf = new SimpleDateFormat();
							/* 241 */sdf.format(new Date());
							/* 242 */sb
									.append("--")
									.append(ContextUtil.getCurrentUser()
											.getFullname()).append("--")
									.append(sdf.format(new Date()))
									.append("</div>");
							/* 243 */archivesDep.setHandleFeedback(sb
									.toString());
							/* 244 */this.archivesDepService.save(archivesDep);
						}

						/* 247 */if (size == recordSize)
							/* 248 */archives.setStatus(Archives.STATUS_END);
						else {
							/* 250 */archives
									.setStatus(Archives.STATUS_READING);
						}
					}
				}
				/* 254 */this.archivesService.save(archives);
			}
			/* 256 */setJsonString("{success:true}");
		} else {
			/* 258 */setJsonString("{success:false}");
		}
		/* 260 */return "success";
	}
	
	/**
	 * 为市局发文做的变动
	 * @return
	 */
	public String saveNew() {
			Archives archives = (Archives) this.archivesService
				.get(this.archivesId);
			String userList = null; 
			if (archives != null) {
				ArchDispatch archDispatch = new ArchDispatch();
				AppUser user = ContextUtil.getCurrentUser();
				archDispatch.setArchives(archives);
				archDispatch.setArchUserType(this.archUserType);
				archDispatch.setUserId(user.getUserId());
				archDispatch.setFullname(user.getFullname());
				archDispatch.setDispatchTime(new Date());
				archDispatch.setSubject(archives.getSubject());
				archDispatch.setIsRead(ArchDispatch.HAVE_READ);
				archDispatch.setReadFeedback(this.readFeedback);
				this.archDispatchService.save(archDispatch);

				if (this.archUserType.compareTo(ArchDispatch.IS_DISPATCH) == 0) {//
					archives.setStatus(Archives.STATUS_READ);
					this.archivesService.save(archives);
				} else {
					String signUserIds = getRequest().getParameter(
						"undertakeUserIds");//上一步审批人
					String cruArchDepId = getRequest().getParameter(
						"cruArchDepId");
					if (StringUtils.isNotEmpty(signUserIds)) {
						String[] signId = signUserIds.split(",");
						int size = signId.length;
						if (size < 2) {
							if ((this.archUserType.compareTo(ArchDispatch.IS_UNDERTAKE) == 0)
								&&(StringUtils.isNotEmpty(cruArchDepId))
								&&(cruArchDepId.indexOf("$") == -1)) {
								ArchivesDep archivesDep = (ArchivesDep) this.archivesDepService
									.get(new Long(cruArchDepId));
								StringBuffer sb = new StringBuffer("<div>"
									+ this.readFeedback);
								SimpleDateFormat sdf = new SimpleDateFormat();
								sdf.format(new Date());
								sb.append("--").append(ContextUtil.getCurrentUser()
											.getFullname()).append("--")
									.append(sdf.format(new Date()))
									.append("</div>");
								archivesDep.setHandleFeedback(sb
									.toString());
								this.archivesDepService.save(archivesDep);
							}
							if(this.archUserType.compareTo(Short.valueOf("0")) == 0){//传阅结束
								archives.setStatus(Archives.STATUS_READ);//进入承办归档环节
								userList = undertakesService.saveArchUnderTakesByArchId(archives.getArchivesId().toString(), getRequest().getParameter("signUserIds"));
							}else{//承办结束
								archives.setStatus(Archives.STATUS_END);
							}							
						} else {
							int recordSize = this.archDispatchService
									.countArchDispatch(archives.getArchivesId(), this.archUserType);
							if ((this.archUserType
									.compareTo(ArchDispatch.IS_UNDERTAKE) == 0)
									&&(StringUtils.isNotEmpty(cruArchDepId))
									&& (cruArchDepId.indexOf("$") == -1)) {
								ArchivesDep archivesDep = (ArchivesDep) this.archivesDepService
										.get(new Long(cruArchDepId));
								StringBuffer sb = new StringBuffer();
								if (archivesDep.getHandleFeedback() != null) {
									sb.append(archivesDep.getHandleFeedback());
								}
								sb.append("<div>" + this.readFeedback);
								SimpleDateFormat sdf = new SimpleDateFormat();
								sdf.format(new Date());
								sb.append("--").append(ContextUtil.getCurrentUser()
										.getFullname()).append("--")
										.append(sdf.format(new Date()))
										.append("</div>");
								archivesDep.setHandleFeedback(sb.toString());
								this.archivesDepService.save(archivesDep);
							}
							if(this.archUserType.compareTo(Short.valueOf("1")) == 0){//承办
//								if (size == recordSize)
								if (size <= recordSize){
									archives.setStatus(Archives.STATUS_END);
								}else {
								}
							}else{
								//传阅中	
								if (size > recordSize){
									userList = undertakesService.saveArchUnderTakesByArchId(archives.getArchivesId().toString(), getRequest().getParameter("signUserIds"));
									archives.setStatus(Archives.STATUS_READING);
								}else{
									userList = undertakesService.saveArchUnderTakesByArchId(archives.getArchivesId().toString(), getRequest().getParameter("signUserIds"));
									archives.setStatus(Archives.STATUS_READ);									
								}
									
							}
						}
					}
					this.archivesService.save(archives);
				}
			setJsonString("{success:true,signUserIds:'" + (userList!=null ? userList : "") + "',rowCount:'"+(userList!=null ? userList.split(",").length : "0")+"'}");
		} else {
			setJsonString("{success:false}");
		}
		return "success";
	}

	public String read() {
		/* 267 */ArchDispatch archDispatch = (ArchDispatch) this.archDispatchService
				.get(this.dispatchId);
		/* 268 */if (archDispatch != null) {
			/* 269 */archDispatch.setReadFeedback(this.readFeedback);
			/* 270 */archDispatch.setIsRead(ArchDispatch.HAVE_READ);
			/* 271 */archDispatch.setDispatchTime(new Date());
			/* 272 */this.archDispatchService.save(archDispatch);
			/* 273 */setJsonString("{success:true}");
		} else {
			/* 275 */setJsonString("{success:false}");
		}
		/* 277 */return "success";
	}

	public String dispatch() {
		/* 285 */String disUserIds = getRequest().getParameter("disUserIds");
		/* 286 */String disRoleIds = getRequest().getParameter("disRoleIds");
		/* 287 */Archives archives = (Archives) this.archivesService
				.get(this.archivesId);
		/* 288 */if (archives != null) {
			/* 289 */if (StringUtils.isNotEmpty(disUserIds)) {
				/* 290 */String[] ids = disUserIds.split(",");
				/* 291 */for (String id : ids) {
					/* 292 */AppUser appUser = (AppUser) this.appUserService
							.get(new Long(id));
					/* 293 */ArchDispatch archDispatch = new ArchDispatch();
					/* 294 */archDispatch.setArchives(archives);
					/* 295 */archDispatch.setUserId(appUser.getUserId());
					/* 296 */archDispatch.setFullname(appUser.getFullname());
					/* 297 */archDispatch.setDispatchTime(new Date());
					/* 298 */archDispatch.setSubject(archives.getSubject());
					/* 299 */archDispatch.setIsRead(ArchDispatch.NOT_READ);
					/* 300 */archDispatch
							.setArchUserType(ArchDispatch.IS_DISPATCH);
					/* 301 */this.archDispatchService.save(archDispatch);
				}
			}
			/* 304 */if (StringUtils.isNotEmpty(disRoleIds)) {
				/* 305 */String[] ids = disRoleIds.split(",");
				/* 306 */for (String id : ids) {
					/* 307 */AppRole role = (AppRole) this.appRoleService
							.get(new Long(id));
					/* 308 */ArchDispatch archDispatch = new ArchDispatch();
					/* 309 */archDispatch.setArchives(archives);
					/* 310 */archDispatch.setDisRoleId(role.getRoleId());
					/* 311 */archDispatch.setDisRoleName(role.getRoleName());
					/* 312 */archDispatch.setDispatchTime(new Date());
					/* 313 */archDispatch.setSubject(archives.getSubject());
					/* 314 */archDispatch.setIsRead(ArchDispatch.NOT_READ);
					/* 315 */archDispatch
							.setArchUserType(ArchDispatch.IS_DISPATCH);
					/* 316 */this.archDispatchService.save(archDispatch);
				}
			}
			/* 319 */setJsonString("{success:true}");
		} else {
			/* 321 */setJsonString("{success:false}");
		}
		/* 323 */return "success";
	}

	public String applicate() {
		/* 332 */ArchDispatch archDispatch = (ArchDispatch) this.archDispatchService
				.get(this.dispatchId);
		/* 333 */if (archDispatch.getUserId() == null) {
			/* 334 */AppUser user = ContextUtil.getCurrentUser();
			/* 335 */archDispatch.setUserId(user.getUserId());
			/* 336 */archDispatch.setFullname(user.getFullname());
			/* 337 */this.archDispatchService.save(archDispatch);
			/* 338 */setJsonString("{success:true}");
		} else {
			/* 340 */setJsonString("{success:false}");
		}
		/* 342 */return "success";
	}
}
