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
import com.xpsoft.oa.service.system.AppUserService;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class ShortMessageAction extends BaseAction {
	/* 31 */static short NOT_DELETE = 0;
	private ShortMessage shortMessage;
	private Date from;
	private Date to;
	/* 35 */private List<InMessage> inList = new ArrayList();

	@Resource
	private ShortMessageService shortMessageService;

	@Resource
	private InMessageService inMessageService;

	@Resource
	private AppUserService appUserService;

	/* 38 */public List<InMessage> getInList() {
		return this.inList;
	}

	public void setInList(List<InMessage> inList) {
		/* 42 */this.inList = inList;
	}

	public Date getFrom() {
		/* 46 */return this.from;
	}

	public void setFrom(Date from) {
		/* 50 */this.from = from;
	}

	public Date getTo() {
		/* 54 */return this.to;
	}

	public void setTo(Date to) {
		/* 58 */this.to = to;
	}

	public ShortMessage getShortMessage() {
		/* 62 */return this.shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		/* 66 */this.shortMessage = shortMessage;
	}

	public String list() {
		/* 81 */PagingBean pb = getInitPagingBean();
		/* 82 */AppUser appUser = ContextUtil.getCurrentUser();
		/* 83 */List list = this.shortMessageService.searchShortMessage(
				appUser.getUserId(), this.shortMessage, this.from, this.to, pb);
		/* 84 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':" + pb.getTotalItems()
						+ ",result:");
		/* 85 */List<InMessage> inList = new ArrayList();
		/* 86 */for (int i = 0; i < list.size(); i++) {
			/* 87 */InMessage inMessage = (InMessage) ((Object[]) list.get(i))[0];
			/* 88 */inList.add(inMessage);
		}
		/* 90 */Gson gson = new Gson();
		/* 91 */Type type = new TypeToken<List<InMessage>>() {
		}
		/* 91 */.getType();
		/* 92 */buff.append(gson.toJson(inList, type));
		/* 93 */buff.append("}");
		/* 94 */setJsonString(buff.toString());
		/* 95 */return "success";
	}

	public String send() {
		/* 103 */String reId = getRequest().getParameter("userId");
		/* 104 */String content = getRequest().getParameter("content");

		/* 106 */AppUser appUser = ContextUtil.getCurrentUser();
		/* 107 */if ((StringUtils.isNotEmpty(reId))
				&& (StringUtils.isNotEmpty(content))) {
			/* 108 */String[] st = reId.split(",");
			/* 109 */ShortMessage message = new ShortMessage();
			/* 110 */message.setContent(content);
			/* 111 */message.setMsgType(Short.valueOf((short) 1));
			/* 112 */message.setSenderId(appUser.getUserId());
			/* 113 */message.setSender(appUser.getFullname());
			/* 114 */message.setSendTime(new Date());
			/* 115 */this.shortMessageService.save(message);
			/* 116 */for (int i = 0; i < st.length; i++) {
				/* 117 */InMessage in = new InMessage();
				/* 118 */in.setUserId(Long.valueOf(Long.parseLong(st[i])));
				/* 119 */AppUser user = (AppUser) this.appUserService.get(Long
						.valueOf(Long.parseLong(st[i])));
				/* 120 */in.setUserFullname(user.getFullname());
				/* 121 */in.setDelFlag(Short.valueOf(NOT_DELETE));
				/* 122 */in.setReadFlag(Short.valueOf((short) 0));
				/* 123 */in.setShortMessage(message);
				/* 124 */this.inMessageService.save(in);
			}
			/* 126 */setJsonString("{success:true}");
		} else {
			/* 128 */setJsonString("{success:false}");
		}
		/* 130 */return "success";
	}
}
