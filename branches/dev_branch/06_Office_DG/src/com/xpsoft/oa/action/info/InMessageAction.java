package com.xpsoft.oa.action.info;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.InMessage;
import com.xpsoft.oa.model.info.ShortMessage;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.info.InMessageService;
import com.xpsoft.oa.service.info.ShortMessageService;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class InMessageAction extends BaseAction {
	/* 34 */static short HAVE_DELETE = 1;
	private InMessage inMessage;
	private ShortMessage shortMessage;
	private Date from;
	private Date to;

	@Resource
	private InMessageService inMessageService;

	@Resource
	private ShortMessageService shortMessageService;

	public InMessage getInMessage() {
		/* 41 */return this.inMessage;
	}

	public void setInMessage(InMessage inMessage) {
		/* 45 */this.inMessage = inMessage;
	}

	public ShortMessage getShortMessage() {
		/* 48 */return this.shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		/* 52 */this.shortMessage = shortMessage;
	}

	public Date getFrom() {
		/* 56 */return this.from;
	}

	public void setFrom(Date from) {
		/* 60 */this.from = from;
	}

	public Date getTo() {
		/* 64 */return this.to;
	}

	public void setTo(Date to) {
		/* 68 */this.to = to;
	}

	public String list() {
		/* 80 */PagingBean pb = getInitPagingBean();
		/* 81 */AppUser appUser = ContextUtil.getCurrentUser();

		/* 83 */List list = this.inMessageService.searchInMessage(
				appUser.getUserId(), this.inMessage, this.shortMessage,
				this.from, this.to, pb);

		/* 87 */List<InMessage> inList = new ArrayList();
		/* 88 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':" + pb.getTotalItems()
						+ ",result:");
		/* 89 */for (int i = 0; i < list.size(); i++) {
			/* 90 */InMessage inMessage = (InMessage) ((Object[]) list.get(i))[0];
			/* 91 */inList.add(inMessage);
		}
		/* 93 */Gson gson = new Gson();
		/* 94 */Type type = new TypeToken<List<InMessage>>() {}.getType();
		/* 95 */buff.append(gson.toJson(inList, type));
		/* 96 */buff.append("}");
		/* 97 */setJsonString(buff.toString());
		/* 98 */return "success";
	}

	public String know() {
		/* 106 */String strReceiveId = getRequest().getParameter("receiveId");
		/* 107 */Long receiveId = null;
		/* 108 */if (StringUtils.isNotEmpty(strReceiveId)) {
			/* 109 */receiveId = Long.valueOf(Long.parseLong(strReceiveId));
		}
		/* 111 */InMessage in = (InMessage) this.inMessageService
				.get(receiveId);
		/* 112 */in.setReadFlag(Short.valueOf((short) 1));
		/* 113 */this.inMessageService.save(in);
		/* 114 */setJsonString("{success:true}");
		/* 115 */return "success";
	}

	public String multiRemove() {
		/* 123 */String[] ids = getRequest().getParameterValues("ids");
		/* 124 */if (ids != null) {
			/* 125 */for (String id : ids) {
				/* 126 */this.inMessage = ((InMessage) this.inMessageService
						.get(Long.valueOf(Long.parseLong(id))));
				/* 127 */this.inMessage.setDelFlag(Short.valueOf(HAVE_DELETE));
				/* 128 */this.inMessageService.save(this.inMessage);
			}
		}
		/* 131 */this.jsonString = "{success:true}";
		/* 132 */return "success";
	}

	public String reply() {
		/* 140 */String strReplyId = getRequest().getParameter("receiveId");
		/* 141 */if (StringUtils.isNotEmpty(strReplyId)) {
			/* 142 */Long replyId = Long.valueOf(Long.parseLong(strReplyId));
			/* 143 */this.inMessage = ((InMessage) this.inMessageService
					.get(replyId));
			/* 144 */StringBuffer buff = new StringBuffer(
					"{success:true,data:[");
			/* 145 */buff.append(
					"{'messageId':"
							+ this.inMessage.getShortMessage().getMessageId()
							+ ",'senderId':'"
							+ this.inMessage.getShortMessage().getSenderId()
							+ "','sender':'"
							+ this.inMessage.getShortMessage().getSender()
							+ "'}").append("]}");
			/* 146 */setJsonString(buff.toString());
		} else {
			/* 148 */setJsonString("{success:false}");
		}
		/* 150 */return "success";
	}

	public String read() {
		/* 158 */Long userId = ContextUtil.getCurrentUser().getUserId();
		/* 159 */boolean flag = false;
		/* 160 */if (userId != null) {
			/* 161 */this.inMessage = this.inMessageService.findByRead(userId);
			/* 162 */if (this.inMessage == null) {
				/* 163 */flag = true;
				/* 164 */this.inMessage = this.inMessageService
						.findLatest(userId);
			}
			/* 166 */if (this.inMessage != null) {
				/* 167 */this.inMessage.setReadFlag(InMessage.FLAG_READ);
				/* 168 */this.inMessageService.save(this.inMessage);
				/* 169 */this.shortMessage = this.inMessage.getShortMessage();
				/* 170 */SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				/* 171 */String date = sdf.format(this.shortMessage
						.getSendTime());
				/* 172 */StringBuffer buff = new StringBuffer(
						"{success:true,message:");
				/* 173 */buff.append("{'receiveId':"
						+ this.inMessage.getReceiveId() + ",'messageId':"
						+ this.shortMessage.getMessageId() + ",'senderId':"
						+ this.shortMessage.getSenderId() + ",'sender':'"
						+ this.shortMessage.getSender() + "','content':'"
						+ this.shortMessage.getContent().replace("\n", " ")
						+ "','sendTime':'" + date + "','msgType':"
						+ this.shortMessage.getMsgType());
				/* 174 */if (!flag) {
					/* 175 */InMessage in = this.inMessageService
							.findByRead(userId);
					/* 176 */if (in != null)
						/* 177 */buff.append(",haveNext:true");
					else
						/* 179 */buff.append(",haveNext:false");
				} else {
					/* 182 */buff.append(",haveNext:false");
				}
				/* 184 */buff.append("}}");
				/* 185 */setJsonString(buff.toString());
			} else {
				/* 187 */setJsonString("{success:false}");
			}
		} else {
			/* 190 */setJsonString("{success:true}");
		}
		/* 192 */return "success";
	}

	public String count() {
		/* 196 */Integer in = this.inMessageService.findByReadFlag(ContextUtil
				.getCurrentUser().getUserId());
		/* 197 */setJsonString("{success:true,count:'" + in + "'}");
		/* 198 */return "success";
	}

	public String detail() {
		/* 207 */String strReceiveId = getRequest().getParameter("receiveId");
		/* 208 */if (StringUtils.isNotEmpty(strReceiveId)) {
			/* 209 */Long receiveId = new Long(strReceiveId);
			/* 210 */this.inMessage = ((InMessage) this.inMessageService
					.get(receiveId));
			/* 211 */this.inMessage.setReadFlag(Short.valueOf((short) 1));
			/* 212 */this.inMessageService.save(this.inMessage);
		}
		/* 214 */return "detail";
	}

	public String display() {
		/* 222 */PagingBean pb = new PagingBean(0, 8);
		/* 223 */AppUser appUser = ContextUtil.getCurrentUser();
		/* 224 */List list = this.shortMessageService.searchShortMessage(
				appUser.getUserId(), null, null, null, pb);
		/* 225 */List inList = new ArrayList();
		/* 226 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':" + pb.getTotalItems()
						+ ",result:");
		/* 227 */for (int i = 0; i < list.size(); i++) {
			/* 228 */InMessage inMessage = (InMessage) ((Object[]) list.get(i))[0];
			/* 229 */inList.add(inMessage);
		}

		/* 232 */getRequest().setAttribute("messageList", inList);
		/* 233 */return "display";
	}

	public String multiRead() {
		/* 237 */String[] ids = getRequest().getParameterValues("ids");
		/* 238 */if (ids != null) {
			/* 239 */for (String id : ids) {
				/* 240 */this.inMessage = ((InMessage) this.inMessageService
						.get(Long.valueOf(Long.parseLong(id))));
				/* 241 */this.inMessage.setReadFlag(InMessage.FLAG_READ);
				/* 242 */this.inMessageService.save(this.inMessage);
			}
		}
		/* 245 */this.jsonString = "{success:true}";
		/* 246 */return "success";
	}
}
