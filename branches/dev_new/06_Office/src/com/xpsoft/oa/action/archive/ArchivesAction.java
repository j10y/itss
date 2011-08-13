package com.xpsoft.oa.action.archive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.ArchHasten;
import com.xpsoft.oa.model.archive.ArchRecUser;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.archive.ArchivesDep;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import com.xpsoft.oa.model.archive.ArchivesType;
import com.xpsoft.oa.model.archive.DocHistory;
import com.xpsoft.oa.model.info.ShortMessage;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.archive.ArchHastenService;
import com.xpsoft.oa.service.archive.ArchRecUserService;
import com.xpsoft.oa.service.archive.ArchivesDepService;
import com.xpsoft.oa.service.archive.ArchivesDocService;
import com.xpsoft.oa.service.archive.ArchivesService;
import com.xpsoft.oa.service.archive.ArchivesTypeService;
import com.xpsoft.oa.service.archive.DocHistoryService;
import com.xpsoft.oa.service.flow.TaskService;
import com.xpsoft.oa.service.info.ShortMessageService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.DepartmentService;
import com.xpsoft.oa.service.system.FileAttachService;

import flexjson.JSONSerializer;

public class ArchivesAction extends BaseAction {

	@Resource
	private ArchivesService archivesService;

	@Resource
	private ArchivesDocService archivesDocService;

	@Resource
	private ArchivesTypeService archivesTypeService;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private ArchivesDepService archivesDepService;
	private Archives archives;

	@Resource
	private AppUserService appUserService;

	@Resource
	private FileAttachService fileAttachService;

	@Resource
	private DocHistoryService docHistoryService;

	@Resource
	private ArchRecUserService archRecUserService;

	@Resource
	private TaskService taskservice;

	@Resource
	private ShortMessageService messageService;

	@Resource
	private ArchHastenService archHastenService;
	private Long archivesId;

	public Long getArchivesId() {
		/* 91 */return this.archivesId;
	}

	public void setArchivesId(Long archivesId) {
		/* 95 */this.archivesId = archivesId;
	}

	public Archives getArchives() {
		/* 99 */return this.archives;
	}

	public void setArchives(Archives archives) {
		/* 103 */this.archives = archives;
	}

	public String list() {
		/* 111 */QueryFilter filter = new QueryFilter(getRequest());
		/* 112 */List<Archives> list = this.archivesService.getAll(filter);

		/* 114 */Type type = new TypeToken<List<Archives>>() {
			/* 115 */
		}.getType();
		/* 116 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 117 */.append(filter.getPagingBean().getTotalItems()).append(
		/* 118 */",result:");

		/* 120 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				/* 121 */.setDateFormat("yyyy-MM-dd").create();
		/* 122 */buff.append(gson.toJson(list, type));
		/* 123 */buff.append("}");

		/* 125 */this.jsonString = buff.toString();

		/* 127 */return "success";
	}

	public String cruList() {
		/* 134 */PagingBean pb = getInitPagingBean();
		/* 135 */AppUser appUser = ContextUtil.getCurrentUser();
		/* 136 */List<Archives> list = this.archivesService.findByUserOrRole(
				appUser
				/* 137 */.getUserId(), appUser.getRoles(), pb);
		/* 138 */Type type = new TypeToken<List<Archives>>() {
			/* 139 */
		}.getType();
		/* 140 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 141 */.append(pb.getTotalItems()).append(",result:");

		/* 143 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				/* 144 */.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		/* 145 */buff.append(gson.toJson(list, type));
		/* 146 */buff.append("}");

		/* 148 */this.jsonString = buff.toString();
		/* 149 */return "success";
	}

	public String multiDel() {
		/* 159 */String[] ids = getRequest().getParameterValues("ids");
		/* 160 */if (ids != null) {
			/* 161 */for (String id : ids) {
				/* 162 */this.archivesService.remove(new Long(id));
			}
		}

		/* 166 */this.jsonString = "{success:true}";

		/* 168 */return "success";
	}

	public String get() {
		/* 177 */Archives archives = (Archives) this.archivesService
				.get(this.archivesId);

		/* 179 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				/* 180 */.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

		/* 182 */StringBuffer sb = new StringBuffer("{success:true,data:[");
		/* 183 */sb.append(gson.toJson(archives));
		/* 184 */sb.append("]}");
		/* 185 */setJsonString(sb.toString());

		/* 187 */return "success";
	}

	public String save() {
		/* 194 */String arcRecfileIds = getRequest().getParameter(
				"archivesRecfileIds");
		/* 195 */String archDepId = getRequest().getParameter("archDepId");
		/* 196 */String handlerUids = getRequest().getParameter("signUserIds");
		/* 197 */AppUser appUser = ContextUtil.getCurrentUser();
		/* 198 */this.archives.setArchType(Archives.ARCHIVE_TYPE_RECEIVE);
		/* 199 */this.archives.setIssuerId(appUser.getUserId());
		/* 200 */this.archives.setIssuer(appUser.getFullname());
		/* 201 */this.archives.setHandlerUids(handlerUids);
		/* 202 */this.archives.setIssueDate(new Date());
		/* 203 */if (StringUtils.isNotEmpty(arcRecfileIds)) {
			/* 204 */this.archives.setFileCounts(Integer.valueOf(arcRecfileIds
					.split(",").length));
		}
		/* 206 */this.archivesService.save(this.archives);
		/* 207 */if (StringUtils.isNotEmpty(arcRecfileIds)) {
			/* 208 */List<ArchivesDoc> list = this.archivesDocService
					.findByAid(this.archives
					/* 209 */.getArchivesId());
			/* 210 */for (ArchivesDoc archivesDoc : list) {
				/* 211 */this.archivesDocService.remove(archivesDoc);
			}
			/* 213 */String[] fileIds = arcRecfileIds.split(",");
			/* 214 */for (String id : fileIds) {
				/* 215 */FileAttach fileAttach = (FileAttach) this.fileAttachService
						.get(new Long(id));
				/* 216 */ArchivesDoc archivesDoc = new ArchivesDoc();
				/* 217 */archivesDoc.setArchives(this.archives);
				/* 218 */archivesDoc.setFileAttach(fileAttach);
				/* 219 */archivesDoc.setDocName(fileAttach.getFileName());
				/* 220 */archivesDoc.setDocStatus((short) 1);
				/* 221 */archivesDoc.setCurVersion(Integer.valueOf(1));
				/* 222 */archivesDoc.setDocPath(fileAttach.getFilePath());
				/* 223 */archivesDoc.setCreatetime(new Date());
				/* 224 */archivesDoc.setUpdatetime(new Date());
				/* 225 */this.archivesDocService.save(archivesDoc);
			}

		}

		/* 230 */if (StringUtils.isNotEmpty(archDepId)) {
			/* 231 */ArchivesDep archivesDep = (ArchivesDep) this.archivesDepService
					.get(new Long(archDepId));
			/* 232 */archivesDep.setStatus(ArchivesDep.STATUS_SIGNED);
			/* 233 */this.archivesDepService.save(archivesDep);
		}

		/* 236 */setJsonString("{success:true,archivesId:"
				+ this.archives.getArchivesId() +
				/* 237 */"}");
		/* 238 */return "success";
	}

	public String docs() {
		/* 247 */StringBuffer sb = new StringBuffer(
				"{success:true,totalCounts:");

		/* 249 */if (this.archivesId != null) {
			/* 250 */this.archives = ((Archives) this.archivesService
					.get(this.archivesId));
			/* 251 */Gson gson = new Gson();
			/* 252 */Set docs = this.archives.getArchivesDocs();
			/* 253 */List<ArchivesDoc> docList = new ArrayList();
			/* 254 */docList.addAll(docs);
			/* 255 */Type type = new TypeToken<List<ArchivesDoc>>() {
			}.getType();
			/* 257 */sb.append(docs.size());
			/* 258 */sb.append(",results:").append(
					new Gson().toJson(docList, type));
		} else {
			/* 261 */sb.append("0,results:[]");
		}
		/* 263 */sb.append("}");

		/* 265 */setJsonString(sb.toString());

		/* 267 */return "success";
	}

	public String getIssue() {
		/* 276 */Archives archives = (Archives) this.archivesService
				.get(this.archivesId);

		/* 280 */JSONSerializer json = new JSONSerializer();

		/* 282 */StringBuffer sb = new StringBuffer("{success:true,data:[");

		/* 284 */sb.append(json.serialize(archives));
		/* 285 */sb.append("]}");
		/* 286 */setJsonString(sb.toString());

		/* 288 */return "success";
	}

	public String saveIssue() {
		/* 296 */String docs = getRequest().getParameter("docs");
		/* 297 */String status = getRequest().getParameter("status");
		/* 298 */AppUser curUser = ContextUtil.getCurrentUser();
		/* 299 */Set archivesDocSet = new HashSet();
		/* 300 */if (StringUtils.isNotEmpty(docs)) {
			/* 301 */Gson gson = new Gson();
			/* 302 */ArchivesDoc[] archivesDocs = gson.fromJson(docs,
					ArchivesDoc[].class);
			/* 304 */if (archivesDocs != null) {
				/* 305 */for (int i = 0; i < archivesDocs.length; i++) {
					/* 306 */if ((archivesDocs[i].getDocId() == null) ||
					/* 307 */(archivesDocs[i].getDocId().longValue() == 0L)) {
						/* 308 */archivesDocs[i].setDocId(null);
						/* 309 */archivesDocs[i].initUsers(curUser);
						/* 310 */archivesDocs[i].setDocStatus(Short
								.valueOf(ArchivesDoc.STATUS_MODIFY));
						/* 311 */archivesDocs[i].setUpdatetime(new Date());
						/* 312 */archivesDocs[i].setCreatetime(new Date());
						/* 313 */archivesDocs[i]
								.setFileAttach(this.fileAttachService
								/* 314 */.getByPath(archivesDocs[i]
										.getDocPath()));
						/* 315 */this.archivesDocService.save(archivesDocs[i]);

						/* 318 */DocHistory newHistory = new DocHistory();
						/* 319 */newHistory.setArchivesDoc(archivesDocs[i]);
						/* 320 */newHistory.setFileAttach(archivesDocs[i]
						/* 321 */.getFileAttach());
						/* 322 */newHistory.setDocName(archivesDocs[i]
								.getDocName());
						/* 323 */newHistory.setPath(archivesDocs[i]
								.getDocPath());
						/* 324 */newHistory.setVersion(Integer
								.valueOf(ArchivesDoc.ORI_VERSION));
						/* 325 */newHistory.setUpdatetime(new Date());
						/* 326 */newHistory.setMender(curUser.getFullname());
						/* 327 */this.docHistoryService.save(newHistory);
					} else {
						/* 329 */archivesDocs[i] =
						/* 330 */((ArchivesDoc) this.archivesDocService
						/* 330 */.get(archivesDocs[i].getDocId()));
					}
					/* 332 */archivesDocSet.add(archivesDocs[i]);
				}
			}
		}

		/* 337 */if (this.archives.getArchivesId() == null) {
			/* 339 */this.archives.setIssuer(curUser.getFullname());
			/* 340 */this.archives.setIssuerId(curUser.getUserId());

			/* 342 */ArchivesType archivesType = (ArchivesType) this.archivesTypeService
					.get(this.archives
					/* 343 */.getArchivesType().getTypeId());
			/* 344 */this.archives.setArchivesType(archivesType);

			/* 346 */this.archives.setArchType(Archives.ARCHIVE_TYPE_DISPATCH);

			/* 348 */if (StringUtils.isNotEmpty(status))
				/* 349 */this.archives.setStatus(Short.valueOf(Short
						.parseShort(status)));
			else {
				/* 351 */this.archives.setStatus(Archives.STATUS_DRAFT);
			}
			/* 353 */this.archives.setCreatetime(new Date());
			/* 354 */this.archives.setIssueDate(new Date());

			/* 357 */this.archives.setFileCounts(Integer
					.valueOf(archivesDocSet.size()));
			/* 358 */this.archives.setArchivesDocs(archivesDocSet);
			/* 359 */this.archivesService.save(this.archives);
		} else {
			/* 362 */Archives orgArchives =
			/* 363 */(Archives) this.archivesService
			/* 363 */.get(this.archives.getArchivesId());

			/* 365 */ArchivesType archivesType = (ArchivesType) this.archivesTypeService
					.get(this.archives
					/* 366 */.getArchivesType().getTypeId());
			/* 367 */this.archives.setArchivesType(archivesType);
			/* 368 */this.archives.setTypeName(archivesType.getTypeName());

			/* 370 */if (StringUtils.isNotEmpty(status))
				/* 371 */this.archives.setStatus(Short.valueOf(Short
						.parseShort(status)));
			else {
				/* 373 */this.archives.setStatus(orgArchives.getStatus());
			}

			/* 376 */this.archives.setCreatetime(orgArchives.getCreatetime());

			/* 378 */this.archives.setFileCounts(Integer
					.valueOf(archivesDocSet.size()));
			/* 379 */this.archives.setArchivesDocs(archivesDocSet);

			/* 381 */this.archives.setIssueDate(new Date());
			/* 382 */this.archives.setArchType(orgArchives.getArchType());

			/* 384 */this.archives.setIssuer(orgArchives.getIssuer());
			/* 385 */this.archives.setIssuerId(orgArchives.getIssuerId());
			/* 386 */this.archivesService.merge(this.archives);
		}

		/* 389 */setJsonString("{success:true,archivesId:'"
				+ this.archives.getArchivesId() +
				/* 390 */"'}");
		/* 391 */return "success";
	}

	public String handOut() {
		if (this.archivesId == null) {
			this.archivesId = this.archives.getArchivesId();
		}
		this.archives = ((Archives) this.archivesService.get(this.archivesId));
		//this.archives.setArchivesNo(getRequest().getParameter("archivesNo"));
		String depIds = this.archives.getRecDepIds();
		StringBuffer msg = new StringBuffer("");
		//获取不需要进入收文签收的发文类型类型
		String archivesTypeName= AppUtil.getPropertity("app.unrefArchivesTypeName");
		if (StringUtils.isNotEmpty(depIds)
				&& !this.archives.getArchivesType().getTypeName()
						.equals(archivesTypeName)) {
			String[] depIdArr = depIds.split("[,]");
			if (depIdArr != null) {
				StringBuffer recIds = new StringBuffer("");

				for (int i = 0; i < depIdArr.length; i++) {
					Long depId = new Long(depIdArr[i]);
					Department department = (Department) this.departmentService
							.get(depId);
					ArchRecUser archRecUser = this.archRecUserService
							.getByDepId(depId);

					ArchivesDep archivesDep = new ArchivesDep();
					archivesDep.setSubject(this.archives.getSubject());
					archivesDep.setDepartment(department);
					archivesDep.setArchives(this.archives);
					archivesDep.setIsMain(ArchivesDep.RECEIVE_MAIN);
					archivesDep.setStatus(ArchivesDep.STATUS_UNSIGNED);
					if ((archRecUser != null)
							&& (archRecUser.getUserId() != null)) {
						archivesDep.setSignUserID(archRecUser.getUserId());
						archivesDep.setSignFullname(archRecUser.getFullname());

						recIds.append(archRecUser.getUserId()).append(",");
					} else {
						msg.append(department.getDepName()).append(
								" 部门还未添加收文负责人");
					}

					this.archivesDepService.save(archivesDep);
				}

				if (StringUtils.isNotEmpty(recIds.toString())) {
					String content = "您有新的公文,请及时签收.";
					this.messageService.save(AppUser.SYSTEM_USER, recIds
							.toString(), content, ShortMessage.MSG_TYPE_TASK);
				}
			}
		}

		String archivesStatus = getRequest().getParameter(
				"archivesStatus");
		if (StringUtils.isNotEmpty(archivesStatus)) {
			this.archives.setStatus(Short.valueOf(Short
					.parseShort(archivesStatus)));
		}
		this.archivesService.save(this.archives);
		return "success";
	}
	
	/**
	 * 编号
	 * @return
	 */
	public String number() {
		if (this.archivesId == null) {
			this.archivesId = this.archives.getArchivesId();
		}
		this.archives = ((Archives) this.archivesService.get(this.archivesId));
		this.archives.setArchivesNo(getRequest().getParameter("archivesNo"));
		
		String archivesStatus = getRequest().getParameter(
		"archivesStatus");
		if (StringUtils.isNotEmpty(archivesStatus)) {
			this.archives.setStatus(Short.valueOf(Short
					.parseShort(archivesStatus)));
		}
		
		this.archivesService.save(this.archives);
		return "success";
	}

	public String hasten() {
		/* 455 */String activityName = getRequest().getParameter(
				"activityName");
		/* 456 */String archivesId = getRequest().getParameter("archivesId");
		/* 457 */String content = getRequest().getParameter("content");
		/* 458 */if ((StringUtils.isNotEmpty(activityName))
				&& (StringUtils.isNotEmpty(archivesId))) {
			/* 459 */Long arcId = new Long(archivesId);
			/* 460 */Date lastCruTime = this.archHastenService
					.getLeastRecordByUser(arcId);
			/* 461 */if (lastCruTime != null) {
				/* 462 */Date now = new Date();
				/* 463 */long time = now.getTime() - lastCruTime.getTime();
				/* 464 */if (time / 60000L < 30L) {
					/* 465 */this.jsonString = "{success:false,message:'催办过于频繁！'}";
					/* 466 */return "success";
				}
			}
			/* 469 */Archives archives = (Archives) this.archivesService
					.get(arcId);
			/* 470 */Set userIds = this.taskservice
					.getHastenByActivityNameVarKeyLongVal(activityName,
							"archives.archivesId", new Long(archivesId));
			/* 471 */StringBuffer strUsrIds = new StringBuffer();
			/* 472 */Iterator it = userIds.iterator();
			/* 473 */while (it.hasNext()) {
				/* 474 */ArchHasten ah = new ArchHasten();
				/* 475 */Long userId = (Long) it.next();
				/* 476 */AppUser appUser = (AppUser) this.appUserService
						.get(userId);
				/* 477 */ah.setContent(content);
				/* 478 */ah.setCreatetime(new Date());
				/* 479 */ah.setArchives(archives);
				/* 480 */ah.setHastenFullname(ContextUtil.getCurrentUser()
						.getFullname());
				/* 481 */ah.setHandlerUserId(appUser.getUserId());
				/* 482 */ah.setHandlerFullname(appUser.getFullname());
				/* 483 */this.archHastenService.save(ah);
				/* 484 */strUsrIds.append(userId.toString()).append(",");
			}
			/* 486 */if (userIds.size() > 0) {
				/* 487 */strUsrIds.deleteCharAt(strUsrIds.length() - 1);
			}
			/* 489 */this.messageService.save(AppUser.SYSTEM_USER, strUsrIds
					.toString(), content, ShortMessage.MSG_TYPE_TASK);
		}
		/* 491 */this.jsonString = "{success:true}";
		/* 492 */return "success";
	}
	
	public String modStatus() {
		String archivesId = getRequest().getParameter("archivesId");
		Short status = Short.valueOf(getRequest().getParameter("status"));
		this.archives = ((Archives) this.archivesService.get(this.archivesId));
		this.archives.setStatus(status);
		this.archivesService.save(this.archives);
		this.jsonString = "{success:true}";
		return "success";
	}
}
