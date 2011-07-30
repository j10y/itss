<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.xpsoft.core.util.AppUtil"%>
<%@page import="com.xpsoft.oa.model.system.AppUser"%>
<%@page import="com.xpsoft.oa.model.info.NoticeNewsComment"%>
<%@page import="com.xpsoft.core.util.ContextUtil"%>
<%@page import="com.xpsoft.oa.service.info.NoticeNewsService"%>
<%@page import="com.xpsoft.oa.service.info.impl.NoticeNewsServiceImpl"%>
<%@page import="com.xpsoft.core.web.paging.PagingBean"%>
<%@page import="com.xpsoft.oa.model.info.NoticeNews"%>
<%@page import="com.xpsoft.oa.model.info.NoticeNewsType"%>
<%@page import="com.xpsoft.oa.service.info.NoticeNewsTypeService"%>
<%@page import="com.xpsoft.oa.service.info.NoticeNewsCommentService"%>
<%@page import="com.xpsoft.core.command.QueryFilter"%>
<%@page import="java.util.*"%>
<%
	Long newsId = null;
	String strId = request.getParameter("newsId");
	String opt = request.getParameter("opt");
	String currentUserId = request.getParameter("userId");
	AppUser appUser  = ((com.xpsoft.oa.service.system.AppUserService)AppUtil.getBean("appUserService")).get(Long.valueOf(currentUserId));
	NoticeNewsService newsService = (NoticeNewsService) AppUtil.getBean("noticeNewsService");
	NoticeNewsCommentService newsCommentService = (NoticeNewsCommentService) AppUtil.getBean("noticeNewsCommentService");
	//通过id得到NoticeNews
	NoticeNews news = null;
	if (strId != null && !"".equals(strId)) {
		newsId = new Long(strId);
	}
	
	//使用页面的方法实现获取上一条,下一条的记录
	
	if(opt != null && !"".equals(opt)){
		QueryFilter filter=new QueryFilter(request);
		filter.getPagingBean().setPageSize(1);//只取一条记录
		List<NoticeNews> list = null;
		filter.addFilter("Q_status_SN_EQ","1");//只取生效的新闻
		if(opt.equals("_next")){
			//点击下一条按钮,则取比当前ID大的下一条记录
			filter.addFilter("Q_newsId_L_GT", newsId.toString());
			list= newsService.getAll(filter);
			if(filter.getPagingBean().getStart()+1==filter.getPagingBean().getTotalItems()){
				request.setAttribute("__haveNextNoticeNewFlag","endNext");
			}
		}else if(opt.equals("_pre")){
			//点击上一条按钮,则取比当前ID小的下一条记录
			filter.addFilter("Q_newsId_L_LT", newsId.toString());
			filter.addSorted("newsId","desc");
			list= newsService.getAll(filter);
			if(filter.getPagingBean().getStart()+1==filter.getPagingBean().getTotalItems()){
				request.setAttribute("__haveNextNoticeNewFlag","endPre");
			}
		}
		if(list.size()>0){
			news = list.get(0);
		}else{
			news = newsService.get(newsId);
		}
	}else{
		news = newsService.get(newsId);
		request.setAttribute("__haveNextNoticeNewFlag","");
	}
	
	request.setAttribute("news",news);
	request.setAttribute("ctxPath",request.getContextPath());
	//浏览后的新闻浏览次数加1
	news.setViewCounts(news.getViewCounts()+1);
	
	//如果news为全部可见，那么需要在comment表中插入一条记录
	Map<String, String> map = new HashMap();
	map.put("Q_flag_N_EQ", "2");//阅读人		
	map.put("Q_news.newsId_L_EQ", news.getNewsId().toString());
	map.put("Q_appUser.userId_L_EQ", appUser.getUserId().toString());
	com.xpsoft.core.command.QueryFilter paramQueryFilter = new com.xpsoft.core.command.QueryFilter(map);
	List<NoticeNewsComment> comments = newsCommentService.getAll(paramQueryFilter);
	if(news.getIsAll().equals(Short.valueOf("1"))){
		if(comments.size()<=0){
			NoticeNewsComment comment = new NoticeNewsComment();
			comment.setAppUser(appUser);
			comment.setFullname(appUser.getFullname());
			comment.setFlag(Integer.valueOf("2"));//阅读
			comment.setContent("1");//已读
			comment.setCreatetime(new Date());
			comment.setNews(news);
			newsCommentService.save(comment);
		}
	}else{//如果news为非全部可见，那么需在comment表中修改一条记录		
		for (NoticeNewsComment comment : comments) {	
			if(comment.getAppUser().getUserId().equals(appUser.getUserId())){
				comment.setContent("1");//已读
				newsCommentService.save(comment);
				break;
			}
			
        }
	}	
	newsService.save(news);
%>

<table width="98%" cellpadding="0" cellspacing="1" style="border: 5px 5px 5px 5px;">
	<tr>
		<td align="center" style="font:2.0em 宋体  ;color:black;font-weight: bold;padding:10px 0px 10px 0px; ">
			${news.subject }
			<input type="hidden" value="${news.newsId }" id="__curNoticeNewId"/>
			<input type="hidden" value="${__haveNextNoticeNewFlag}" id="__haveNextNoticeNewFlag"/>
		</td>
	</tr>
	<tr>
		<td align="center" style="padding:0px 0px 10px 0px;">
			<font color="red">
				<fmt:formatDate value="${news.createtime}" pattern="yyyy-MM-dd HH:mm"/>
			</font>
			&nbsp;&nbsp;通知类型:
			<font color="green">
				${news.newsType.typeName}
			</font>
			&nbsp;&nbsp;浏览次数:
			<font color="red">
				${news.viewCounts }
			</font>
			&nbsp;&nbsp;回复次数:
			<font color="red">
				${news.replyCounts}
			</font>
		</td>
	</tr>
	
	<tr>
		<td style="border-top:dashed 1px #ccc;" height="28">
			
		</td>
	</tr>
	
	
	<c:if test="${news.isDeskImage==1}">
	<tr>
		<td align="center">
			<img src="${ctxPath}/attachFiles/${news.subjectIcon}"/>
		</td>
	</tr>
	</c:if>
	<tr>
	<div>
		<span style="font-weight: bolder">通知附件：</span> 
		<c:forEach
		var="doc" items="${news.noticeNewsDocs}">
		<a href="<%=request.getContextPath()%>/attachFiles/${doc.fileAttach.filePath}"
		target="_blank">${doc.fileAttach.fileName}</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</c:forEach>
	</div>
	</tr>
	<tr >
		<td style="font:13px 宋体;color: black;line-height:24px;">
			${news.content}
		</td>
	</tr>
	<tr>
		<td align="right">
				(作者:
			<font color="green">
				${news.author}
			</font>
				&nbsp;&nbsp;发布人:
			<font color="green">
				${news.issuer}
			</font>
				 )
		</td>
	</tr>
	
</table>
