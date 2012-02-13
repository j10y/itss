 package com.xpsoft.oa.action.info;
 
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.google.gson.reflect.TypeToken;
 import com.xpsoft.core.command.QueryFilter;
 import com.xpsoft.core.util.StringUtil;
 import com.xpsoft.core.web.action.BaseAction;
 import com.xpsoft.core.web.paging.PagingBean;
 import com.xpsoft.oa.model.info.News;
 import com.xpsoft.oa.model.info.NewsType;
 import com.xpsoft.oa.service.info.NewsService;
 import com.xpsoft.oa.service.info.NewsTypeService;
 import flexjson.JSONSerializer;
 import java.lang.reflect.Type;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.StringUtils;
 
 public class NewsAction extends BaseAction
 {
 
   @Resource
   private NewsService newsService;
 
   @Resource
   private NewsTypeService newsTypeService;
   private News news;
   private List<News> list;
   private Long newsId;
   private Long typeId;
   private NewsType newsType;
 
   public List<News> getList()
   {
/*  43 */     return this.list;
   }
 
   public void setList(List<News> list) {
/*  47 */     this.list = list;
   }
 
   public Long getTypeId()
   {
/*  58 */     return this.typeId;
   }
 
   public void setTypeId(Long typeId) {
/*  62 */     this.typeId = typeId;
   }
 
   public Long getNewsId() {
/*  66 */     return this.newsId;
   }
 
   public void setNewsId(Long newsId) {
/*  70 */     this.newsId = newsId;
   }
 
   public News getNews() {
/*  74 */     return this.news;
   }
 
   public void setNews(News news) {
/*  78 */     this.news = news;
   }
 
   public NewsType getNewsType()
   {
/*  83 */     return this.newsType;
   }
 
   public void setNewsType(NewsType newsType) {
/*  87 */     this.newsType = newsType;
   }
 
   public String list()
   {
/*  95 */     QueryFilter filter = new QueryFilter(getRequest());
/*  96 */     List<News> list = this.newsService.getAll(filter);
 
/*  98 */     Type type = new TypeToken<List<News>>() {  }
/*  98 */     .getType();
/*  99 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 100 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/* 102 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/* 103 */     buff.append(gson.toJson(list, type));
/* 104 */     buff.append("}");
 
/* 106 */     this.jsonString = buff.toString();
 
/* 108 */     return "success";
   }
 
   public String multiDel()
   {
/* 116 */     String[] ids = getRequest().getParameterValues("ids");
/* 117 */     if (ids != null) {
/* 118 */       for (String id : ids) {
/* 119 */         this.newsService.remove(new Long(id));
       }
     }
 
/* 123 */     this.jsonString = "{success:true}";
 
/* 125 */     return "success";
   }
 
   public String get()
   {
/* 133 */     News news = (News)this.newsService.get(this.newsId);
 
/* 136 */     JSONSerializer serializer = new JSONSerializer();
 
/* 138 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 139 */     sb.append(serializer.exclude(new String[] { "class", "newsComments" }).serialize(news));
/* 140 */     sb.append("}");
/* 141 */     setJsonString(sb.toString());
 
/* 143 */     return "success";
   }
 
   public String save()
   {
/* 149 */     String isDeskNews = getRequest().getParameter("isDeskImage");
/* 150 */     if (StringUtils.isNotEmpty(isDeskNews))
/* 151 */       this.news.setIsDeskImage(News.ISDESKNEWS);
     else {
/* 153 */       this.news.setIsDeskImage(News.NOTDESKNEWS);
     }
/* 155 */     News old = new News();
/* 156 */     Boolean isNew = Boolean.valueOf(false);
/* 157 */     if (this.news.getNewsId() == null) {
/* 158 */       isNew = Boolean.valueOf(true);
     }
/* 160 */     if (this.news.getTypeId() != null) {
/* 161 */       setNewsType((NewsType)this.newsTypeService.get(this.news.getTypeId()));
/* 162 */       this.news.setNewsType(this.newsType);
     }
/* 164 */     if (isNew.booleanValue()) {
/* 165 */       this.news.setCreatetime(new Date());
/* 166 */       this.news.setUpdateTime(new Date());
/* 167 */       this.news.setReplyCounts(Integer.valueOf(0));
/* 168 */       this.news.setViewCounts(Integer.valueOf(0));
/* 169 */       this.newsService.save(this.news);
     } else {
/* 171 */       old = (News)this.newsService.get(this.news.getNewsId());
/* 172 */       this.news.setUpdateTime(new Date());
/* 173 */       this.news.setCreatetime(old.getCreatetime());
/* 174 */       this.news.setViewCounts(Integer.valueOf(old.getViewCounts().intValue() + 1));
/* 175 */       this.news.setReplyCounts(old.getReplyCounts());
/* 176 */       this.newsService.merge(this.news);
     }
/* 178 */     setJsonString("{success:true}");
/* 179 */     return "success";
   }
 
   public String category()
   {
/* 187 */     List<News> list = null;
/* 188 */     PagingBean pb = getInitPagingBean();
/* 189 */     if ((this.typeId != null) && (this.typeId.longValue() > 0L))
/* 190 */       list = this.newsService.findByTypeId(this.typeId, pb);
     else {
/* 192 */       list = this.newsService.getAll(pb);
     }
/* 194 */     Type type = new TypeToken<List<News>>() {  }
/* 194 */     .getType();
/* 195 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':" + pb.getTotalItems() + ",result:");
/* 196 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/* 197 */     buff.append(gson.toJson(list, type));
/* 198 */     buff.append("}");
/* 199 */     setJsonString(buff.toString());
/* 200 */     return "success";
   }
 
   public String icon()
   {
/* 206 */     setNews((News)this.newsService.get(getNewsId()));
/* 207 */     this.news.setSubjectIcon("");
/* 208 */     this.newsService.save(this.news);
/* 209 */     return "success";
   }
 
   public String search()
   {
/* 217 */     PagingBean pb = getInitPagingBean();
/* 218 */     String searchContent = getRequest().getParameter("searchContent");
/* 219 */     List<News>  list = this.newsService.findBySearch(searchContent, pb);
/* 220 */     Type type = new TypeToken<List<News>>() {  }
/* 220 */     .getType();
/* 221 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 222 */       .append(pb.getTotalItems()).append(",result:");
 
/* 224 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/* 225 */     buff.append(gson.toJson(list, type));
/* 226 */     buff.append("}");
 
/* 228 */     this.jsonString = buff.toString();
 
/* 230 */     return "success";
   }
 
   public String display()
   {
/* 237 */     PagingBean pb = new PagingBean(0, 8);
 
/* 239 */     List list = this.newsService.findBySearch(null, pb);
/* 240 */     getRequest().setAttribute("newsList", list);
/* 241 */     return "display";
   }
 
   public String image()
   {
/* 250 */     PagingBean pb = new PagingBean(0, 8);
 
/* 252 */     List<News> list = this.newsService.findImageNews(pb);
/* 253 */     List newList = new ArrayList();
/* 254 */     for (News news : list) {
/* 255 */       String content = StringUtil.html2Text(news.getContent());
/* 256 */       if (content.length() > 250) {
/* 257 */         content = content.substring(0, 250);
       }
/* 259 */       news.setContent(content);
/* 260 */       newList.add(news);
     }
/* 262 */     getRequest().setAttribute("imageNewsList", newList);
/* 263 */     return "image";
   }
 }
