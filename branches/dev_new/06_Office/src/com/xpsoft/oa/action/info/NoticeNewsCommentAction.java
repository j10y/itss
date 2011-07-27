package com.xpsoft.oa.action.info;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNews;
import com.xpsoft.oa.model.info.NoticeNewsComment;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.info.NoticeNewsCommentService;
import com.xpsoft.oa.service.info.NoticeNewsService;
import com.xpsoft.oa.service.system.AppUserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class NoticeNewsCommentAction extends BaseAction {

	@Resource
	private NoticeNewsCommentService noticeNewsCommentService;

	@Resource
	private AppUserService appUserService;

	@Resource
	private NoticeNewsService noticeNewsService;
	private NoticeNewsComment newsComment;
	private Long commentId;

	public Long getCommentId() {
		/* 43 */return this.commentId;
	}

	public void setCommentId(Long commentId) {
		/* 47 */this.commentId = commentId;
	}

	public NoticeNewsComment getNewsComment() {
		/* 51 */return this.newsComment;
	}

	public void setNewsComment(NoticeNewsComment newsComment) {
		/* 55 */this.newsComment = newsComment;
	}

	public String list() {
		/* 63 */QueryFilter filter = new QueryFilter(getRequest());
		/* 64 */String start = getRequest().getParameter("start");
		/* 65 */List<NoticeNewsComment> list = this.noticeNewsCommentService.getAll(filter);

		/* 67 */Gson gson = new Gson();
		/* 68 */SimpleDateFormat simpleDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		/* 69 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 70 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:[");
		/* 71 */for (NoticeNewsComment newsComment : list) {
			/* 72 */buff
					.append("{commentId:'")
					/* 73 */.append(newsComment.getCommentId())
					/* 74 */.append("',subject:")
					.append(gson.toJson(newsComment.getNews().getSubject()))
					/* 75 */.append(",content:")
					/* 76 */.append(gson.toJson(newsComment.getContent()))
					/* 77 */.append(",createtime:'")
					/* 78 */.append(
							simpleDate.format(newsComment.getCreatetime()))
					/* 79 */.append("',fullname:'")
					/* 80 */.append(newsComment.getFullname())
					/* 81 */.append("',start:'")
					/* 82 */.append(start)
					/* 83 */.append("'},");
		}
		/* 85 */if (list.size() > 0) {
			/* 86 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 88 */buff.append("]}");

		/* 90 */this.jsonString = buff.toString();

		/* 92 */return "success";
	}

	public String multiDel() {
		/* 100 */String[] ids = getRequest().getParameterValues("ids");
		/* 101 */if (ids != null) {
			/* 102 */for (String id : ids) {
				/* 103 */NoticeNewsComment delComment = (NoticeNewsComment) this.noticeNewsCommentService
						.get(new Long(id));
				/* 104 */NoticeNews news = delComment.getNews();
				/* 105 */if (news.getReplyCounts().intValue() > 1) {
					/* 106 */news.setReplyCounts(Integer.valueOf(news
							.getReplyCounts().intValue() - 1));
				}
				/* 108 */this.noticeNewsService.save(news);
				/* 109 */this.noticeNewsCommentService.remove(new Long(id));
			}
		}

		/* 113 */this.jsonString = "{success:true}";

		/* 115 */return "success";
	}

	public String get() {
		/* 123 */NoticeNewsComment newsComment = (NoticeNewsComment) this.noticeNewsCommentService
				.get(this.commentId);

		/* 125 */Gson gson = new Gson();

		/* 127 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 128 */sb.append(gson.toJson(newsComment));
		/* 129 */sb.append("}");
		/* 130 */setJsonString(sb.toString());

		/* 132 */return "success";
	}

	public String save() {
		/* 139 */NoticeNews news = (NoticeNews) this.noticeNewsService.get(this.newsComment
				.getNewsId());
		/* 140 */news.setReplyCounts(Integer.valueOf(news.getReplyCounts()
				.intValue() + 1));
		/* 141 */this.noticeNewsService.save(news);

		/* 143 */this.newsComment.setAppUser((AppUser) this.appUserService
				.get(this.newsComment.getUserId()));
		/* 144 */this.newsComment.setCreatetime(new Date());
		/* 145 */this.newsComment.setNews(news);
		/* 146 */this.noticeNewsCommentService.save(this.newsComment);
		/* 147 */setJsonString("{success:true}");
		/* 148 */return "success";
	}
}
