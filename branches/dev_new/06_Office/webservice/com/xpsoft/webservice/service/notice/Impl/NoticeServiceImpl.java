package com.xpsoft.webservice.service.notice.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.google.gson.Gson;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNews;
import com.xpsoft.oa.model.info.NoticeNewsComment;
import com.xpsoft.oa.model.info.NoticeNewsDoc;
import com.xpsoft.oa.model.info.NoticeNewsType;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.info.NoticeNewsCommentService;
import com.xpsoft.oa.service.info.NoticeNewsService;
import com.xpsoft.oa.service.info.NoticeNewsTypeService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.webservice.service.notice.NoticeService;

public class NoticeServiceImpl implements NoticeService {
	private String getType(String type){
		if(type.equals("doc")){
			return "0";
		}else if(type.equals("xls")){
			return "1";
		}else if(type.equals("ppt")){
			return "2";
		}else if(type.equals("pdf")){
			return "3";
		}else if(type.equals("rar")){
			return "5";
		}else if(type.equals("txt")){
			return "6";
		}else if(type.equals("jpg")){
			return "7";
		}else{
			return "4";
		}
	}
	public String filter(String userId,String passwd){
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		AppUser user=userService.get(Long.parseLong(userId));
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				user.getUsername(), passwd);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		AuthenticationManager authenticationManager=(AuthenticationManager) AppUtil.getBean("authenticationManager");
		securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
		SecurityContextHolder.setContext(securityContext);
		return null;
	}
	public String getDyNotice(String userId,String passwd,String pageNum,String pageSize,String title,String department){
		filter( userId, passwd);
		NoticeNewsService noticeNewsService=(NoticeNewsService) AppUtil.getBean("noticeNewsService");
		Integer start=0;
		Integer limit=10;
		if(pageNum!=null&&pageSize!=null){
			start=(Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize);
			limit=start+Integer.parseInt(pageSize);
		}
		PagingBean pb=new PagingBean(start,limit);
		List<NoticeNews> list=noticeNewsService.findByForPadSearch(title, null, department,null, pb, false);
		String json="{success:true,totalCount:\""+pb.getTotalItems()+"\",data:[";
			for(NoticeNews n:list){
				json+="{id:\""+n.getId()+"\",title:\""+n.getSubject()+"\",department:\""+n.getDept().getDepName()+"\", author :\""+n.getAuthor()+"\", createDate :\""+n.getCreatetime()+"\"},";
			}
			if(list.size()>0){
				json=json.substring(0,json.length()-1);
			}
			json+="]}";
			
		return json;
	}
	public String getYyNotice(String userId,String passwd,String pageNum,String pageSize,String title,String department,String noticeTypeId){
		filter( userId, passwd);
		NoticeNewsService noticeNewsService=(NoticeNewsService) AppUtil.getBean("noticeNewsService");
		Integer start=0;
		Integer limit=10;
		if(pageNum!=null&&pageSize!=null){
			start=(Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize);
			limit=start+Integer.parseInt(pageSize);
		}
		PagingBean pb=new PagingBean(start,limit);
		Long ntypeid=noticeTypeId!=null?Long.parseLong(noticeTypeId):null;
		List<NoticeNews> list=noticeNewsService.findByForPadSearch(title, null, department,ntypeid, pb, true);
		String json="{success:true,totalCount:\""+pb.getTotalItems()+"\",data:[";
			for(NoticeNews n:list){
				json+="{id:\""+n.getId()+"\",title:\""+n.getSubject()+"\", department:\""+n.getDept().getDepName()+"\", author :\""+n.getAuthor()+"\", createDate :\""+n.getCreatetime()+"\"},";
			}
			if(list.size()>0){
				json=json.substring(0,json.length()-1);
			}
			json+="]}";
			
		return json;
	}
	
	public String getNoticeDetail(String noticeId,String userId,String passwd){
		filter( userId, passwd);
		NoticeNewsService newsService = (NoticeNewsService) AppUtil.getBean("noticeNewsService");
		NoticeNewsCommentService newsCommentService = (NoticeNewsCommentService) AppUtil.getBean("noticeNewsCommentService");
		NoticeNews news = null;
		Long newsId=null;
		AppUser appUser= ContextUtil.getCurrentUser();
		String json="";
		if (noticeId != null && !"".equals(noticeId)) {
			newsId = new Long(noticeId);
			news = newsService.get(newsId);
			//如果news为全部可见，那么需要在comment表中插入一条记录
			if(news.getIsAll().equals(Short.valueOf("1"))){
				Map<String, String> map = new HashMap();
				map.put("Q_flag_N_EQ", "2");//阅读人		
				map.put("Q_news.newsId_L_EQ", news.getNewsId().toString());
				map.put("Q_appUser.userId_L_EQ", appUser.getUserId().toString());//阅读人						
				com.xpsoft.core.command.QueryFilter paramQueryFilter = new com.xpsoft.core.command.QueryFilter(map);
				List<NoticeNewsComment> comments = newsCommentService.getAll(paramQueryFilter);
				if(comments.size()>0){
				}else{
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
				Map<String, String> map = new HashMap();
				map.put("Q_flag_N_EQ", "2");//阅读人		
				map.put("Q_news.newsId_L_EQ", news.getNewsId().toString());
				map.put("Q_appUser.userId_L_EQ", appUser.getUserId().toString());//阅读人						
				com.xpsoft.core.command.QueryFilter paramQueryFilter = new com.xpsoft.core.command.QueryFilter(map);
				List<NoticeNewsComment> comments = newsCommentService.getAll(paramQueryFilter);
				for (NoticeNewsComment comment : comments) {	
						comment.setContent("1");//已读
						newsCommentService.save(comment);
		        }
			}	
			NoticeNews news1 = (NoticeNews) newsService.get(Long.parseLong(noticeId));
			Map<String, String> map = new HashMap();
			map.put("Q_flag_N_EQ", "1");//阅读人		
			map.put("Q_news.newsId_L_EQ", news.getNewsId().toString());
			map.put("Q_appUser.userId_L_EQ", appUser.getUserId().toString());//阅读人						
			com.xpsoft.core.command.QueryFilter paramQueryFilter = new com.xpsoft.core.command.QueryFilter(map);
			List<NoticeNewsComment> listComment = newsCommentService.getAll(paramQueryFilter);
			NoticeNewsComment comment=new NoticeNewsComment();
			if(listComment.size()>0){
				comment=listComment.get(0);
			}
			Gson gson = new Gson();
			StringBuffer sb = new StringBuffer("{success:true,clickrate:\""+news1.getViewCounts()+"\", department:\""+news1.getDept().getDepName()+"\", desc:"+(news1.getContent()!=null?gson.toJson(news1.getContent()):"\"\"")+",commentDesc:"+(comment.getContent()!=null?gson.toJson(comment.getContent()):"\"\"")+",accessories:[");
			Iterator<NoticeNewsDoc> iterator = news1.getNoticeNewsDocs().iterator();
			NoticeNewsDoc doc = null;
			for(;iterator.hasNext();){
				doc = iterator.next();
				sb.append("{name:\""+doc.getDocName() + "\",\"url\":\"" + doc.getFileAttach().getFilePath() +"\",type:\""+getType(doc.getFileAttach().getExt())+"\"}");
				if(iterator.hasNext()){
					sb.append(",");
				}
			}
			sb.append("]}");
			json=sb.toString();
		}
		System.out.println(json);
		return json;
	}
	public String getNoticeType(String userId,String passwd){
		NoticeNewsTypeService noticeNewsTypeService=(NoticeNewsTypeService) AppUtil.getBean("noticeNewsTypeService");
		List<NoticeNewsType> list=noticeNewsTypeService.getAll();
		String json="{success:true,data:[";
		for(NoticeNewsType n:list){
			json+="{typeId:\""+n.getTypeId()+"\",typeName:\""+n.getTypeName()+"\"},";
		}
		if(list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		return json;
		
	}
	public String saveNoticeComment(String userId,String passwd,String noticeId,String commentDesc){
		try {
			filter( userId, passwd);
			NoticeNewsService newsService = (NoticeNewsService) AppUtil.getBean("noticeNewsService");
			NoticeNews news = (NoticeNews) newsService.get(Long.parseLong(noticeId));
			news.setReplyCounts(Integer.valueOf(news.getReplyCounts()
					.intValue() + 1));
			newsService.save(news);
			NoticeNewsCommentService newsCommentService = (NoticeNewsCommentService) AppUtil.getBean("noticeNewsCommentService");
			NoticeNewsComment comment=new NoticeNewsComment();
			comment.setAppUser( ContextUtil.getCurrentUser());
			comment.setCreatetime(new Date());
			comment.setNews(news);
			comment.setContent(commentDesc);
			comment.setFlag(1);
			comment.setFullname(ContextUtil.getCurrentUser().getFullname());
			newsCommentService.save(comment);
			return "{success：true}";
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return "{success：false}";
		}
	}
}
