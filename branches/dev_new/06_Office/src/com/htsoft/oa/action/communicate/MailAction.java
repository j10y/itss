package com.htsoft.oa.action.communicate;

import com.google.gson.Gson;

import com.htsoft.core.command.QueryFilter;

import com.htsoft.core.util.BeanUtil;

import com.htsoft.core.util.ContextUtil;

import com.htsoft.core.util.StringUtil;

import com.htsoft.core.web.action.BaseAction;

import com.htsoft.core.web.paging.PagingBean;

import com.htsoft.oa.model.communicate.Mail;

import com.htsoft.oa.model.communicate.MailBox;

import com.htsoft.oa.model.communicate.MailFolder;

import com.htsoft.oa.model.info.ShortMessage;

import com.htsoft.oa.model.system.AppUser;

import com.htsoft.oa.model.system.FileAttach;

import com.htsoft.oa.service.communicate.MailBoxService;

import com.htsoft.oa.service.communicate.MailFolderService;

import com.htsoft.oa.service.communicate.MailService;

import com.htsoft.oa.service.info.InMessageService;

import com.htsoft.oa.service.info.ShortMessageService;

import com.htsoft.oa.service.system.AppUserService;

import com.htsoft.oa.service.system.FileAttachService;

import flexjson.JSONSerializer;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.HashSet;

import java.util.List;

import java.util.Set;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.logging.Log;

public class MailAction extends BaseAction {
	/* 47 */static long FOLDER_ID_RECEIVE = 1L;
	/* 48 */static long FOLDER_ID_SEND = 2L;
	/* 49 */static long FOLDER_ID_DRAFT = 3L;
	/* 50 */static long FOLDER_ID_DELETE = 4L;

	/* 52 */static short HAVE_DELETE = 1;
	/* 53 */static short NOT_DELETE = 0;
	/* 54 */static short HAVE_READ = 1;
	/* 55 */static short NOT_READ = 0;
	/* 56 */static Short HAVE_REPLY = Short.valueOf((short) 1);
	/* 57 */static short NOT_REPLY = 0;
	/* 58 */static short SYSTEM_MESSAGE = 4;
	/* 59 */static short COMMON = 1;

	/* 62 */static short IS_DRAFT = 0;
	/* 63 */static short IS_MAIL = 1;

	@Resource
	private MailService mailService;

	@Resource
	private FileAttachService fileAttachService;

	@Resource
	private AppUserService appUserService;

	@Resource
	private MailFolderService mailFolderService;

	@Resource
	private MailBoxService mailBoxService;

	@Resource
	private ShortMessageService shortMessageService;

	@Resource
	private InMessageService inMessageService;
	private Mail mail;
	private Long mailId;
	private AppUser appUser;
	private Long folderId;
	private Long boxId;
	private String sendMessage;
	private Long replyBoxId;
	private String boxIds;
	private Long fileId;

	/* 98 */public Long getMailId() {
		return this.mailId;
	}

	public void setMailId(Long mailId) {
		/* 102 */this.mailId = mailId;
	}

	public Mail getMail() {
		/* 106 */return this.mail;
	}

	public void setMail(Mail mail) {
		/* 110 */this.mail = mail;
	}

	public AppUser getAppUser() {
		/* 114 */return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		/* 118 */this.appUser = appUser;
	}

	public Long getFolderId() {
		/* 122 */if (this.folderId == null) {
			/* 123 */return Long.valueOf(1L);
		}
		/* 125 */return this.folderId;
	}

	public void setFolderId(Long folderId) {
		/* 130 */this.folderId = folderId;
	}

	public Long getBoxId() {
		/* 134 */return this.boxId;
	}

	public void setBoxId(Long boxId) {
		/* 138 */this.boxId = boxId;
	}

	public String getBoxIds() {
		/* 142 */return this.boxIds;
	}

	public void setBoxIds(String boxIds) {
		/* 146 */this.boxIds = boxIds;
	}

	public String getSendMessage() {
		/* 150 */return this.sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		/* 154 */this.sendMessage = sendMessage;
	}

	public Long getReplyBoxId() {
		/* 158 */return this.replyBoxId;
	}

	public void setReplyBoxId(Long replyBoxId) {
		/* 162 */this.replyBoxId = replyBoxId;
	}

	public Long getFileId() {
		/* 166 */return this.fileId;
	}

	public void setFileId(Long fileId) {
		/* 170 */this.fileId = fileId;
	}

	public String list() {
		/* 178 */QueryFilter filter = new QueryFilter(getRequest());
		/* 179 */if ((this.folderId == null)
				|| (this.folderId.longValue() == FOLDER_ID_RECEIVE)) {
			/* 180 */setFolderId(new Long(FOLDER_ID_RECEIVE));
		}
		/* 182 */filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 183 */filter.addFilter("Q_mailFolder.folderId_L_EQ",
				this.folderId.toString());
		/* 184 */if (this.folderId.longValue() != FOLDER_ID_DELETE) {
			/* 185 */filter.addFilter("Q_delFlag_SN_EQ", "0");
		}
		/* 187 */filter.addSorted("sendTime", "desc");
		/* 188 */List<MailBox> list = this.mailBoxService.getAll(filter);

		/* 190 */Gson gson = new Gson();
		/* 191 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 192 */.append(filter.getPagingBean().getTotalItems()).append(
		/* 193 */",result:[");
		/* 194 */for (MailBox mailBoxTemp : list) {
			/* 195 */Mail mailTemp = mailBoxTemp.getMail();
			/* 196 */buff
					.append("{boxId:'")
					/* 197 */.append(mailBoxTemp.getBoxId())
					/* 198 */.append("',sendTime:'")
					/* 199 */.append(mailBoxTemp.getSendTime())
					/* 200 */.append("',delFlag:'")
					/* 201 */.append(mailBoxTemp.getDelFlag())
					/* 202 */.append("',readFlag:'")
					/* 203 */.append(mailBoxTemp.getReadFlag())
					/* 204 */.append("',replyFlag:'")
					/* 205 */.append(mailBoxTemp.getReplyFlag())
					/* 206 */.append("',mailId:'")
					/* 207 */.append(mailTemp.getMailId())
					/* 208 */.append("',importantFlag:'")
					/* 209 */.append(mailTemp.getImportantFlag())
					/* 210 */.append("',mailStatus:'")
					/* 211 */.append(mailTemp.getMailStatus())
					/* 212 */.append("',fileIds:'")
					/* 213 */.append(mailTemp.getFileIds())
					/* 214 */.append("',subject:'")
					/* 215 */.append(
							gson.toJson(mailTemp.getSubject())
									.replace("\"", ""))
					/* 216 */.append("',recipientNames:'")
					/* 217 */.append(mailTemp.getRecipientNames())
					/* 218 */.append("',sender:'")
					/* 219 */.append(mailTemp.getSender())
					/* 220 */.append("',content:'");
			/* 221 */String content = StringUtil.html2Text(mailTemp
					.getContent());
			/* 222 */content = gson.toJson(content).replace("\"", "");

			/* 224 */if (content.length() > 100) {
				/* 225 */content = content.substring(0, 100) + "...";
			}
			/* 227 */buff.append(content);
			/* 228 */buff.append("'},");
		}
		/* 230 */if (list.size() > 0) {
			/* 231 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 233 */buff.append("]}");

		/* 235 */this.jsonString = buff.toString();

		/* 237 */return "success";
	}

	public String multiDel() {
		/* 247 */MailFolder deleteFolder = (MailFolder) this.mailFolderService
				.get(Long.valueOf(FOLDER_ID_DELETE));
		/* 248 */String[] ids = getRequest().getParameterValues("ids");
		/* 249 */if (ids != null) {
			/* 250 */if (getFolderId().longValue() == FOLDER_ID_DELETE) {
				/* 251 */for (String id : ids)
					/* 252 */this.mailBoxService.remove(new Long(id));
			} else {
				/* 255 */for (String id : ids) {
					/* 256 */MailBox mailBoxDelete = (MailBox) this.mailBoxService
							.get(new Long(id));
					/* 257 */mailBoxDelete.setDelFlag(Short
							.valueOf(HAVE_DELETE));
					/* 258 */mailBoxDelete.setMailFolder(deleteFolder);
					/* 259 */this.mailBoxService.save(mailBoxDelete);
				}
			}
		}
		/* 263 */this.jsonString = "{success:true}";
		/* 264 */return "success";
	}

	public String get() {
		/* 273 */String opt = getRequest().getParameter("opt");
		/* 274 */MailBox mailBox = null;
		/* 275 */if ((opt == null) || ("".equals(opt))) {
			/* 276 */mailBox = (MailBox) this.mailBoxService.get(this.boxId);
			/* 277 */getRequest().setAttribute("__haveNextMailFlag", "");
		} else {
			/* 280 */String folderId = getRequest().getParameter("folderId");
			/* 281 */if ((folderId == null) || ("".equals(folderId))) {
				/* 282 */folderId = "1";
			}

			/* 286 */QueryFilter filter = new QueryFilter(getRequest());
			/* 287 */filter.getPagingBean().setPageSize(1);
			/* 288 */List list = null;
			/* 289 */filter.addFilter("Q_appUser.userId_L_EQ",
			/* 290 */ContextUtil.getCurrentUserId().toString());
			/* 291 */filter.addFilter("Q_delFlag_SN_EQ", "0");
			/* 292 */filter.addFilter("Q_mailFolder.folderId_L_EQ", folderId);

			/* 294 */if (opt.equals("_next")) {
				/* 296 */filter
						.addFilter("Q_boxId_L_GT", this.boxId.toString());
				/* 297 */list = this.mailBoxService.getAll(filter);
				/* 298 */if (filter.getPagingBean().getStart().intValue() + 1 == filter
				/* 299 */.getPagingBean().getTotalItems())
					/* 300 */getRequest().setAttribute("__haveNextMailFlag",
							"endNext");
			}
			/* 302 */else if (opt.equals("_pre")) {
				/* 304 */filter
						.addFilter("Q_boxId_L_LT", this.boxId.toString());
				/* 305 */filter.addSorted("boxId", "desc");
				/* 306 */list = this.mailBoxService.getAll(filter);
				/* 307 */if (filter.getPagingBean().getStart().intValue() + 1 == filter
				/* 308 */.getPagingBean().getTotalItems()) {
					/* 309 */getRequest().setAttribute("__haveNextMailFlag",
							"endPre");
				}
			}
			/* 312 */if (list.size() > 0)
				/* 313 */mailBox = (MailBox) list.get(0);
			else {
				/* 315 */mailBox = (MailBox) this.mailBoxService
						.get(this.boxId);
			}
		}

		/* 319 */setMail(mailBox.getMail());
		/* 320 */mailBox.setReadFlag(Short.valueOf(HAVE_READ));
		/* 321 */this.mailBoxService.save(mailBox);

		/* 323 */if (this.mail.getMailStatus().shortValue() != 1) {
			/* 324 */JSONSerializer serializer = new JSONSerializer();

			/* 326 */StringBuffer sb = new StringBuffer(
			/* 327 */"{success:true,totalCounts:1,data:[");
			/* 328 */sb.append(serializer.exclude(
			/* 329 */new String[] { "class", "mail.appUser",
			/* 330 */"appUser.department", "mailFolder.appUser" })
			/* 331 */.serialize(this.mail));
			/* 332 */sb.append("]}");
			/* 333 */setJsonString(sb.toString());
			/* 334 */return "success";
		}
		/* 336 */getRequest().setAttribute("mail", this.mail);
		/* 337 */getRequest().setAttribute("boxId", mailBox.getBoxId());

		/* 339 */getRequest().setAttribute("mailAttachs",
				this.mail.getMailAttachs());
		/* 340 */return "detail";
	}

	public String save() {
		/* 349 */if (this.mail.getMailStatus().shortValue() == IS_MAIL) {
			/* 351 */if (StringUtils.isEmpty(this.mail.getRecipientIDs())) {
				/* 352 */setJsonString("{failure:true,msg:'收件人不能为空!'}");
				/* 353 */return "success";
			}

			/* 356 */if (StringUtils.isEmpty(this.mail.getSubject())) {
				/* 357 */setJsonString("{failure:true,msg:'邮件主题不能为空!'}");
				/* 358 */return "success";
			}

			/* 361 */if (StringUtils.isEmpty(this.mail.getContent())) {
				/* 362 */setJsonString("{failure:true,msg:'邮件内容不能为空!'}");
				/* 363 */return "success";
			}

			/* 366 */if ((this.replyBoxId != null)
					&& (!"".equals(this.replyBoxId))) {
				/* 367 */MailBox reply = (MailBox) this.mailBoxService
						.get(this.replyBoxId);
				/* 368 */reply.setReplyFlag(Short.valueOf(HAVE_READ));
				/* 369 */this.mailBoxService.save(reply);
			}

			/* 372 */MailFolder receiveFolder = (MailFolder) this.mailFolderService
					.get(Long.valueOf(FOLDER_ID_RECEIVE));
			/* 373 */MailFolder sendFolder = (MailFolder) this.mailFolderService
					.get(Long.valueOf(FOLDER_ID_SEND));
			/* 374 */String[] recipientIDs = this.mail.getRecipientIDs().split(
					",");
			/* 375 */String[] copyToIDs = this.mail.getCopyToIDs().split(",");

			/* 377 */if (this.mail.getMailId() == null) {
				/* 378 */SaveMail();

				/* 382 */MailBox mailBox = new MailBox();
				/* 383 */mailBox.setMail(this.mail);
				/* 384 */mailBox.setMailFolder(sendFolder);
				/* 385 */mailBox.setAppUser(ContextUtil.getCurrentUser());
				/* 386 */mailBox.setSendTime(this.mail.getSendTime());
				/* 387 */mailBox.setDelFlag(Short.valueOf(NOT_DELETE));
				/* 388 */mailBox.setReadFlag(Short.valueOf(NOT_READ));
				/* 389 */mailBox.setNote("已发送的邮件");
				/* 390 */mailBox.setReplyFlag(Short.valueOf(NOT_REPLY));
				/* 391 */this.mailBoxService.save(mailBox);
			} else {
				/* 393 */Mail old = (Mail) this.mailService.get(this.mail
						.getMailId());
				try {
					/* 395 */BeanUtil.copyNotNullProperties(old, this.mail);
					/* 396 */Set mailAttachs = new HashSet();
					/* 397 */old.setSendTime(new Date());
					/* 398 */String[] fileIds = this.mail.getFileIds().split(
							",");
					/* 399 */for (String id : fileIds) {
						/* 400 */if (id.equals(""))
							continue;
						/* 401 */mailAttachs
						/* 402 */.add((FileAttach) this.fileAttachService
								.get(new Long(id)));
					}

					/* 405 */old.setMailAttachs(mailAttachs);
					/* 406 */setMail(old);
					/* 407 */this.mailService.save(old);
				} catch (Exception ex) {
					/* 409 */this.logger.error(ex.getMessage());
				}
				/* 411 */MailBox drafted = (MailBox) this.mailBoxService
						.get(this.boxId);
				/* 412 */drafted.setMailFolder(sendFolder);
				/* 413 */drafted.setNote("已发送的邮件");
				/* 414 */this.mailBoxService.save(drafted);
			}

			/* 417 */if ((this.sendMessage != null)
					&& (this.sendMessage.equals("on"))) {
				/* 418 */StringBuffer msgContent = new StringBuffer(
						"<font color=\"green\">");
				/* 419 */SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd");
				/* 420 */msgContent.append(this.mail.getSender())
				/* 421 */.append("</font>")
				/* 422 */.append("在<font color=\"red\">")
				/* 423 */.append(sdf.format(this.mail.getSendTime()))
				/* 424 */.append("</font>")
				/* 425 */.append("给您发了一封邮件，请注意查收。");
				/* 426 */this.shortMessageService.save(AppUser.SYSTEM_USER,
						this.mail.getRecipientIDs(), msgContent.toString(),
						ShortMessage.MSG_TYPE_SYS);
			}

			
			for (int sdf = 0; sdf < recipientIDs.length; sdf++) {
				String id = recipientIDs[sdf];
				/* 430 */if (!"".equals(id)) {
					/* 431 */MailBox mailBoxSend = new MailBox();
					/* 432 */mailBoxSend.setMail(this.mail);
					/* 433 */mailBoxSend.setMailFolder(receiveFolder);
					/* 434 */mailBoxSend
							.setAppUser((AppUser) this.appUserService
									.get(new Long(id)));
					/* 435 */mailBoxSend.setSendTime(this.mail.getSendTime());
					/* 436 */mailBoxSend.setDelFlag(Short.valueOf(NOT_DELETE));
					/* 437 */mailBoxSend.setReadFlag(Short.valueOf(NOT_READ));
					/* 438 */mailBoxSend.setNote("发送出去的邮件");
					/* 439 */mailBoxSend.setReplyFlag(Short.valueOf(NOT_REPLY));
					/* 440 */this.mailBoxService.save(mailBoxSend);
				}

			}

			for (int sdf = 0; sdf < copyToIDs.length; sdf++) {
				String id = copyToIDs[sdf];
				/* 446 */if (!"".equals(id)) {
					/* 447 */MailBox mailBoxCopy = new MailBox();
					/* 448 */mailBoxCopy.setMail(this.mail);
					/* 449 */mailBoxCopy.setMailFolder(receiveFolder);
					/* 450 */mailBoxCopy
							.setAppUser((AppUser) this.appUserService
									.get(new Long(id)));
					/* 451 */mailBoxCopy.setSendTime(this.mail.getSendTime());
					/* 452 */mailBoxCopy.setDelFlag(Short.valueOf(NOT_DELETE));
					/* 453 */mailBoxCopy.setReadFlag(Short.valueOf(NOT_READ));
					/* 454 */mailBoxCopy.setNote("抄送出去的邮件");
					/* 455 */mailBoxCopy.setReplyFlag(Short.valueOf(NOT_REPLY));
					/* 456 */this.mailBoxService.save(mailBoxCopy);
				}
			}
		} else {
			/* 462 */if (StringUtils.isEmpty(this.mail.getSubject())) {
				/* 463 */setJsonString("{failure:true,msg:'邮件主题不能为空!'}");
				/* 464 */return "success";
			}
			/* 466 */SaveMail();

			/* 469 */MailFolder draftFolder = (MailFolder) this.mailFolderService
					.get(Long.valueOf(FOLDER_ID_DRAFT));
			/* 470 */MailBox mailBox = new MailBox();
			/* 471 */mailBox.setMail(this.mail);
			/* 472 */mailBox.setMailFolder(draftFolder);
			/* 473 */mailBox.setAppUser(ContextUtil.getCurrentUser());
			/* 474 */mailBox.setSendTime(this.mail.getSendTime());
			/* 475 */mailBox.setDelFlag(Short.valueOf(NOT_DELETE));
			/* 476 */mailBox.setReadFlag(Short.valueOf(NOT_READ));
			/* 477 */mailBox.setNote("存草稿");
			/* 478 */mailBox.setReplyFlag(Short.valueOf(NOT_REPLY));
			/* 479 */this.mailBoxService.save(mailBox);
		}

		/* 482 */setJsonString("{success:true}");
		/* 483 */return "success";
	}

	public void SaveMail() {
		/* 488 */Set mailAttachs = new HashSet();
		/* 489 */setAppUser(ContextUtil.getCurrentUser());
		/* 490 */this.mail.setAppSender(this.appUser);
		/* 491 */this.mail.setSendTime(new Date());
		/* 492 */this.mail.setSender(this.appUser.getFullname());
		/* 493 */String[] fileIds = this.mail.getFileIds().split(",");
		/* 494 */for (String id : fileIds) {
			/* 495 */if (!id.equals("")) {
				/* 496 */mailAttachs.add((FileAttach) this.fileAttachService
						.get(new Long(id)));
			}
		}
		/* 499 */this.mail.setMailAttachs(mailAttachs);
		/* 500 */this.mailService.save(this.mail);
	}

	public String opt() {
		/* 507 */setMail((Mail) this.mailService.get(this.mailId));
		/* 508 */String opt = getRequest().getParameter("opt");
		/* 509 */Mail reply = new Mail();
		/* 510 */StringBuffer newSubject = new StringBuffer(
		/* 511 */"<br><br><br><br><br><br><br><hr>");
		/* 512 */newSubject
				.append("<br>----<strong>" + opt + "邮件</strong>----");
		/* 513 */newSubject.append("<br><strong>发件人</strong>:"
				+ this.mail.getSender());
		/* 514 */newSubject.append("<br><strong>发送时间</strong>:"
				+ this.mail.getSendTime());
		/* 515 */newSubject.append("<br><strong>收件人</strong>:" +
		/* 516 */this.mail.getRecipientNames());
		/* 517 */String copyToNames = this.mail.getCopyToNames();
		/* 518 */if ((!"".equals(copyToNames)) && (copyToNames != null)) {
			/* 519 */newSubject.append("<br><strong>抄送人</strong>:"
					+ copyToNames);
		}
		/* 521 */newSubject.append("<br><strong>主题</strong>:"
				+ this.mail.getSubject());
		/* 522 */newSubject.append("<br><strong>内容</strong>:<br><br>" +
		/* 523 */this.mail.getContent());
		/* 524 */reply.setContent(newSubject.toString());
		/* 525 */reply.setSubject(opt + ":" + this.mail.getSubject());
		/* 526 */reply.setImportantFlag(Short.valueOf(COMMON));
		/* 527 */if (opt.equals("回复")) {
			/* 528 */MailBox replyBox = (MailBox) this.mailBoxService
					.get(this.boxId);
			/* 529 */replyBox.setReplyFlag(HAVE_REPLY);
			/* 530 */this.mailBoxService.save(replyBox);
			/* 531 */reply
					.setRecipientIDs(this.mail.getAppSender().getUserId().toString());
			/* 532 */reply.setRecipientNames(this.mail.getSender());
		}
		/* 534 */JSONSerializer serializer = new JSONSerializer();

		/* 536 */StringBuffer sb = new StringBuffer("{success:true,data:[");
		/* 537 */sb.append(serializer.exclude(
				new String[] { "class", "appUser" })
		/* 538 */.serialize(reply));
		/* 539 */sb.append("]}");
		/* 540 */setJsonString(sb.toString());
		/* 541 */return "success";
	}

	public String move() {
		/* 548 */MailFolder moveToFolder = (MailFolder) this.mailFolderService
				.get(new Long(this.folderId.longValue()));
		/* 549 */String[] ids = this.boxIds.split(",");
		/* 550 */StringBuffer msg = new StringBuffer("{");
		/* 551 */boolean moveSuccess = false;
		/* 552 */if ((ids[0] != null) && (!"".equals(ids[0]))) {
			/* 553 */Mail moveTest = ((MailBox) this.mailBoxService
					.get(new Long(ids[0]))).getMail();
			/* 554 */if (moveTest.getMailStatus().shortValue() == IS_DRAFT) {
				/* 556 */if ((this.folderId.longValue() == FOLDER_ID_DRAFT)
						|| (this.folderId.longValue() == FOLDER_ID_DELETE)) {
					/* 558 */moveSuccess = true;
				}
				/* 560 */else
					msg.append("msg:'草稿只能移至草稿箱或是垃圾箱(移至垃圾箱相当于删除,请注意!)'");
			}
			/* 561 */else if (moveTest.getMailStatus().shortValue() == IS_MAIL) {
				/* 563 */if (this.folderId.longValue() != FOLDER_ID_DRAFT) {
					/* 565 */moveSuccess = true;
				}
				/* 567 */else
					msg.append("msg:'正式邮件不能移至草稿箱'");
			}
		}
		/* 570 */if (moveSuccess) {
			/* 572 */for (String id : ids) {
				/* 573 */if (!"".equals(id)) {
					/* 574 */MailBox mailBoxMove = (MailBox) this.mailBoxService
							.get(new Long(id));
					/* 575 */mailBoxMove.setMailFolder(moveToFolder);
					/* 576 */if (this.folderId.longValue() != FOLDER_ID_DELETE)
						/* 577 */mailBoxMove.setDelFlag(Short
								.valueOf(NOT_DELETE));
					else {
						/* 579 */mailBoxMove.setDelFlag(Short
								.valueOf(HAVE_DELETE));
					}
					/* 581 */this.mailBoxService.save(mailBoxMove);
				}
			}
			/* 584 */msg.append("success:true}");
			/* 585 */setJsonString(msg.toString());
		} else {
			/* 588 */msg.append(",failure:true}");
			/* 589 */setJsonString(msg.toString());
		}
		/* 591 */return "success";
	}

	public String attach() {
		/* 600 */String fileIds = getRequest().getParameter("fileIds");
		/* 601 */String filenames = getRequest().getParameter("filenames");
		/* 602 */setMail((Mail) this.mailService.get(this.mailId));
		/* 603 */Set mailAttachs = this.mail.getMailAttachs();
		/* 604 */FileAttach remove = (FileAttach) this.fileAttachService
				.get(this.fileId);
		/* 605 */mailAttachs.remove(remove);
		/* 606 */this.mail.setMailAttachs(mailAttachs);
		/* 607 */this.mail.setFileIds(fileIds);
		/* 608 */this.mail.setFilenames(filenames);
		/* 609 */this.mailService.save(this.mail);
		/* 610 */this.fileAttachService.remove(this.fileId);
		/* 611 */return "success";
	}

	public String search() {
		/* 618 */PagingBean pb = getInitPagingBean();
		/* 619 */String searchContent = getRequest().getParameter(
				"searchContent");
		/* 620 */List<MailBox> list = this.mailBoxService
				.findBySearch(searchContent, pb);
		/* 621 */Gson gson = new Gson();
		/* 622 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 623 */.append(list.size()).append(",result:[");
		/* 624 */for (MailBox mailBoxTemp : list) {
			/* 625 */Mail mailTemp = mailBoxTemp.getMail();
			/* 626 */buff.append("{boxId:'")
			/* 627 */.append(mailBoxTemp.getBoxId())
			/* 628 */.append("',sendTime:'")
			/* 629 */.append(mailBoxTemp.getSendTime())
			/* 630 */.append("',delFlag:'")
			/* 631 */.append(mailBoxTemp.getDelFlag())
			/* 632 */.append("',readFlag:'")
			/* 633 */.append(mailBoxTemp.getReadFlag())
			/* 634 */.append("',replyFlag:'")
			/* 635 */.append(mailBoxTemp.getReplyFlag())
			/* 636 */.append("',mailId:'")
			/* 637 */.append(mailTemp.getMailId())
			/* 638 */.append("',importantFlag:'")
			/* 639 */.append(mailTemp.getImportantFlag())
			/* 640 */.append("',mailStatus:'").append(mailTemp.getMailStatus())
			/* 641 */.append("',fileIds:'").append(mailTemp.getFileIds())
			/* 642 */.append("',subject:'")
					.append(gson.toJson(mailTemp.getSubject()))
					/* 643 */.append("',recipientNames:'")
					/* 644 */.append(mailTemp.getRecipientNames())
					.append("',sender:'")
					/* 645 */.append(mailTemp.getSender())
					.append("',content:'");
			/* 646 */String content = StringUtil.html2Text(mailTemp
					.getContent());
			/* 647 */content = gson.toJson(content);
			/* 648 */if (content.length() > 100) {
				/* 649 */content = content.substring(0, 100) + "...";
			}
			/* 651 */buff.append(content);
			/* 652 */buff.append("'},");
		}
		/* 654 */if (list.size() > 0) {
			/* 655 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 657 */buff.append("]}");

		/* 659 */this.jsonString = buff.toString();

		/* 661 */return "success";
	}

	public String display() {
		/* 669 */QueryFilter filter = new QueryFilter(getRequest());
		/* 670 */filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		/* 671 */filter.addFilter("Q_mailFolder.folderId_L_EQ",
				Long.toString(FOLDER_ID_RECEIVE));
		/* 672 */filter
				.addFilter("Q_delFlag_SN_EQ", Short.toString(NOT_DELETE));
		/* 673 */filter.addSorted("sendTime", "desc");
		/* 674 */filter.addSorted("readFlag", "desc");
		/* 675 */List list = this.mailBoxService.getAll(filter);
		/* 676 */getRequest().setAttribute("mailBoxList", list);
		/* 677 */return "display";
	}

}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.action.communicate.MailAction JD-Core Version: 0.6.0
 */