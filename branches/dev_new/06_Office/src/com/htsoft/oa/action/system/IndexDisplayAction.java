package com.htsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.model.system.AppUser;
import com.htsoft.oa.model.system.IndexDisplay;
import com.htsoft.oa.model.system.PanelItem;
import com.htsoft.oa.service.system.IndexDisplayService;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class IndexDisplayAction extends BaseAction {

	@Resource
	private IndexDisplayService indexDisplayService;
	private IndexDisplay indexDisplay;
	private Long indexId;

	public Long getIndexId() {
		/* 35 */return this.indexId;
	}

	public void setIndexId(Long indexId) {
		/* 39 */this.indexId = indexId;
	}

	public IndexDisplay getIndexDisplay() {
		/* 43 */return this.indexDisplay;
	}

	public void setIndexDisplay(IndexDisplay indexDisplay) {
		/* 47 */this.indexDisplay = indexDisplay;
	}

	public String list() {
		/* 55 */QueryFilter filter = new QueryFilter(getRequest());
		/* 56 */List<IndexDisplay> list = this.indexDisplayService.getAll(filter);

		/* 58 */Type type = new TypeToken<List<IndexDisplay>>() {}.getType();
		/* 59 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 60 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 62 */Gson gson = new Gson();
		/* 63 */buff.append(gson.toJson(list, type));
		/* 64 */buff.append("}");

		/* 66 */this.jsonString = buff.toString();

		/* 68 */return "success";
	}

	public String multiDel() {
		/* 76 */String[] ids = getRequest().getParameterValues("ids");
		/* 77 */if (ids != null) {
			/* 78 */for (String id : ids) {
				/* 79 */this.indexDisplayService.remove(new Long(id));
			}
		}

		/* 83 */this.jsonString = "{success:true}";

		/* 85 */return "success";
	}

	public String get() {
		/* 93 */IndexDisplay indexDisplay = (IndexDisplay) this.indexDisplayService
				.get(this.indexId);

		/* 95 */Gson gson = new Gson();

		/* 97 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 98 */sb.append(gson.toJson(indexDisplay));
		/* 99 */sb.append("}");
		/* 100 */setJsonString(sb.toString());

		/* 102 */return "success";
	}

	public String save()
   {
/* 108 */     String items = getRequest().getParameter("items");
/* 109 */     Gson gson = new Gson();
/* 110 */     PanelItem[] panelItems = gson.fromJson(items, PanelItem[].class);
/* 112 */     AppUser user = ContextUtil.getCurrentUser();
/* 113 */     List<IndexDisplay> list = this.indexDisplayService.findByUser(user.getUserId());
/* 114 */     for (IndexDisplay id : list) {
/* 115 */       this.indexDisplayService.remove(id);
     }
/* 117 */     for (PanelItem item : panelItems) {
/* 118 */       IndexDisplay indexDisplay = new IndexDisplay();
/* 119 */       indexDisplay.setAppUser(user);
/* 120 */       indexDisplay.setPortalId(item.getPanelId());
/* 121 */       indexDisplay.setColNum(Integer.valueOf(item.getColumn()));
/* 122 */       indexDisplay.setRowNum(Integer.valueOf(item.getRow()));
/* 123 */       this.indexDisplayService.save(indexDisplay);
     }
/* 125 */     setJsonString("{success:true}");
/* 126 */     return "success";
   }
}
