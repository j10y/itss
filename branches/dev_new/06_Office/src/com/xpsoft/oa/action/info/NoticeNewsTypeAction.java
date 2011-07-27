package com.xpsoft.oa.action.info;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNewsType;
import com.xpsoft.oa.service.info.NoticeNewsTypeService;

public class NoticeNewsTypeAction extends BaseAction {
	private NoticeNewsType newsType;

	@Resource
	private NoticeNewsTypeService noticeNewsTypeService;

	public void setNewsType(NoticeNewsType newsType) {
		/* 26 */this.newsType = newsType;
	}

	public NoticeNewsType getNewsType() {
		/* 30 */return this.newsType;
	}

	public String list() {
		/* 39 */QueryFilter filter = new QueryFilter(getRequest());
		/* 40 */filter.addSorted("sn", "asc");
		/* 41 */List<NoticeNewsType> list = this.noticeNewsTypeService.getAll(filter);
		/* 42 */Type type = new TypeToken<List<NoticeNewsType>>() {
		}
		/* 42 */.getType();
		/* 43 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':'" +
				/* 44 */filter.getPagingBean().getTotalItems() + "',result:");
		/* 45 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();
		/* 46 */buff.append(gson.toJson(list, type));
		/* 47 */buff.append("}");
		/* 48 */setJsonString(buff.toString());
		/* 49 */return "success";
	}

	public String tree() {
		/* 56 */List<NoticeNewsType> list = this.noticeNewsTypeService.getAllBySn();
		/* 57 */StringBuffer sb = new StringBuffer("[");
		/* 58 */String opt = getRequest().getParameter("opt");
		/* 59 */if ((opt == null) || (!opt.equals("treeSelector"))) {
			/* 60 */sb
					.append("{id:'0',text:'全部新闻',leaf:true,iconCls:'menu-news'},");
		}
		/* 62 */for (NoticeNewsType newsType : list) {
			/* 63 */sb.append("{id:'").append(newsType.getTypeId())
					.append("',text:'").append(newsType.getTypeName())
					.append("',leaf:true,iconCls:'menu-news'},");
		}
		/* 65 */if (!list.isEmpty()) {
			/* 66 */sb.deleteCharAt(sb.length() - 1);
		}
		/* 68 */sb.append("]");
		/* 69 */setJsonString(sb.toString());
		/* 70 */return "success";
	}

	public String add() {
		/* 77 */Short top = this.noticeNewsTypeService.getTop();
		/* 78 */if (top == null)
			top = Short.valueOf((short) 0);

		/* 80 */if (this.newsType.getTypeId() == null) {
			/* 81 */this.newsType
					.setSn(Short.valueOf((short) (top.shortValue() + 1)));
		}
		/* 83 */this.noticeNewsTypeService.save(this.newsType);
		/* 84 */setJsonString("{success:true}");
		/* 85 */return "success";
	}

	public String remove() {
		/* 92 */Long typeId = Long.valueOf(getRequest().getParameter("typeId"));
		/* 93 */setNewsType((NoticeNewsType) this.noticeNewsTypeService.get(typeId));
		/* 94 */if (this.newsType != null) {
			/* 95 */Short sn = this.newsType.getSn();
			/* 96 */this.noticeNewsTypeService.remove(typeId);

			/* 99 */Short top = this.noticeNewsTypeService.getTop();
			/* 100 */if ((top != null) && (top.shortValue() > sn.shortValue())) {
				/* 101 */for (int i = 0; i < top.shortValue() - sn.shortValue(); i++) {
					/* 102 */setNewsType(this.noticeNewsTypeService.findBySn(Short
							.valueOf((short) (sn.shortValue() + i + 1))));
					/* 103 */this.newsType.setSn(Short.valueOf((short) (sn
							.shortValue() + i)));
					/* 104 */this.noticeNewsTypeService.save(this.newsType);
				}
			}
		}
		/* 108 */return "success";
	}

	public String detail() {
		/* 115 */Long typeId = Long
				.valueOf(getRequest().getParameter("typeId"));
		/* 116 */setNewsType((NoticeNewsType) this.noticeNewsTypeService.get(typeId));
		/* 117 */Gson gson = new Gson();
		/* 118 */StringBuffer sb = new StringBuffer("{success:true,data:[");
		/* 119 */sb.append(gson.toJson(this.newsType));
		/* 120 */sb.append("]}");
		/* 121 */setJsonString(sb.toString());
		/* 122 */return "success";
	}

	public String search() {
		/* 129 */PagingBean pb = getInitPagingBean();
		/* 130 */List<NoticeNewsType> list = this.noticeNewsTypeService.findBySearch(this.newsType,
				pb);
		/* 131 */Type type = new TypeToken<List<NoticeNewsType>>() {
		}
		/* 131 */.getType();
		/* 132 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':'" + pb.getTotalItems()
						+ "',result:");
		/* 133 */Gson gson = new Gson();
		/* 134 */buff.append(gson.toJson(list, type));
		/* 135 */buff.append("}");
		/* 136 */setJsonString(buff.toString());
		/* 137 */return "success";
	}

	public String sort() {
		/* 144 */Integer opt = Integer.valueOf(Integer.parseInt(getRequest()
				.getParameter("opt")));
		/* 145 */Long typeId = Long
				.valueOf(getRequest().getParameter("typeId"));
		/* 146 */setNewsType((NoticeNewsType) this.noticeNewsTypeService.get(typeId));
		/* 147 */Short top = this.noticeNewsTypeService.getTop();
		/* 148 */Short sn = this.newsType.getSn();
		/* 149 */NoticeNewsType change = new NoticeNewsType();
		/* 150 */if ((opt.intValue() == 1) &&
		/* 151 */(sn.shortValue() != 1)) {
			/* 152 */for (int i = 0; i < sn.shortValue() - 1; i++) {
				/* 153 */change = this.noticeNewsTypeService.findBySn(Short
						.valueOf((short) (sn.shortValue() - i - 1)));
				/* 154 */change
						.setSn(Short.valueOf((short) (sn.shortValue() - i)));
				/* 155 */this.noticeNewsTypeService.save(change);
			}
			/* 157 */this.newsType.setSn((short) 1);
			/* 158 */this.noticeNewsTypeService.save(this.newsType);
		}

		/* 161 */if ((opt.intValue() == 2) &&
		/* 162 */(sn.shortValue() != 1)) {
			/* 163 */change = this.noticeNewsTypeService.findBySn(Short
					.valueOf((short) (sn.shortValue() - 1)));
			/* 164 */change.setSn(sn);
			/* 165 */this.noticeNewsTypeService.save(change);
			/* 166 */this.newsType
					.setSn(Short.valueOf((short) (sn.shortValue() - 1)));
			/* 167 */this.noticeNewsTypeService.save(this.newsType);
		}

		/* 170 */if ((opt.intValue() == 3) &&
		/* 171 */(sn.shortValue() < top.shortValue())) {
			/* 172 */change = this.noticeNewsTypeService.findBySn(Short
					.valueOf((short) (sn.shortValue() + 1)));
			/* 173 */change.setSn(sn);
			/* 174 */this.noticeNewsTypeService.save(change);
			/* 175 */this.newsType
					.setSn(Short.valueOf((short) (sn.shortValue() + 1)));
			/* 176 */this.noticeNewsTypeService.save(this.newsType);
		}

		/* 179 */if ((opt.intValue() == 4) &&
		/* 180 */(sn.shortValue() < top.shortValue())) {
			/* 181 */for (int i = 0; i < top.shortValue() - sn.shortValue(); i++) {
				/* 182 */change = this.noticeNewsTypeService.findBySn(Short
						.valueOf((short) (sn.shortValue() + i + 1)));
				/* 183 */change
						.setSn(Short.valueOf((short) (sn.shortValue() + i)));
				/* 184 */this.noticeNewsTypeService.save(change);
			}
			/* 186 */this.newsType.setSn(top);
			/* 187 */this.noticeNewsTypeService.save(this.newsType);
		}

		/* 190 */return "success";
	}
}
