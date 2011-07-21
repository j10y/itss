package com.xpsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.Dictionary;
import com.xpsoft.oa.service.system.DictionaryService;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class DictionaryAction extends BaseAction {

	@Resource
	private DictionaryService dictionaryService;
	private Dictionary dictionary;
	private Long dicId;
	private String itemName;

	public String getItemName() {
		/* 30 */return this.itemName;
	}

	public void setItemName(String itemName) {
		/* 34 */this.itemName = itemName;
	}

	public Long getDicId() {
		/* 38 */return this.dicId;
	}

	public void setDicId(Long dicId) {
		/* 42 */this.dicId = dicId;
	}

	public Dictionary getDictionary() {
		/* 46 */return this.dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		/* 50 */this.dictionary = dictionary;
	}

	public String list() {
		/* 58 */QueryFilter filter = new QueryFilter(getRequest());
		/* 59 */List<Dictionary> list = this.dictionaryService.getAll(filter);

		/* 61 */Type type = new TypeToken<List<Dictionary>>() {
		}
		/* 61 */.getType();
		/* 62 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 63 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 65 */Gson gson = new Gson();
		/* 66 */buff.append(gson.toJson(list, type));
		/* 67 */buff.append("}");

		/* 69 */this.jsonString = buff.toString();

		/* 71 */return "success";
	}

	public String load() {
		/* 78 */List<String> list = this.dictionaryService
				.getAllByItemName(this.itemName);
		/* 79 */StringBuffer buff = new StringBuffer("[");
		/* 80 */for (String itemName : list) {
			/* 81 */buff.append("'").append(itemName).append("',");
		}
		/* 83 */if (list.size() > 0) {
			/* 84 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 86 */buff.append("]");
		/* 87 */setJsonString(buff.toString());
		/* 88 */return "success";
	}

	public String multiDel() {
		/* 96 */String[] ids = getRequest().getParameterValues("ids");
		/* 97 */if (ids != null) {
			/* 98 */for (String id : ids) {
				/* 99 */this.dictionaryService.remove(new Long(id));
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		/* 113 */Dictionary dictionary = (Dictionary) this.dictionaryService
				.get(this.dicId);

		/* 115 */Gson gson = new Gson();

		/* 117 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 118 */sb.append(gson.toJson(dictionary));
		/* 119 */sb.append("}");
		/* 120 */setJsonString(sb.toString());

		/* 122 */return "success";
	}

	public String save() {
		/* 128 */this.dictionaryService.save(this.dictionary);
		/* 129 */setJsonString("{success:true}");
		/* 130 */return "success";
	}

	public String items() {
		/* 138 */List<String> list = this.dictionaryService.getAllItems();
		/* 139 */StringBuffer buff = new StringBuffer("[");
		/* 140 */for (String str : list) {
			/* 141 */buff.append("'").append(str).append("',");
		}
		/* 143 */if (list.size() > 0) {
			/* 144 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 146 */buff.append("]");
		/* 147 */setJsonString(buff.toString());
		/* 148 */return "success";
	}
}
