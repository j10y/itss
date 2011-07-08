/*     */package com.xpsoft.oa.action.personal;

/*     */
/*     */import com.xpsoft.core.command.QueryFilter;
/*     */
import com.xpsoft.core.util.JsonUtil;
/*     */
import com.xpsoft.core.web.action.BaseAction;
/*     */
import com.xpsoft.core.web.paging.PagingBean;
/*     */
import com.xpsoft.oa.model.personal.DutySection;
/*     */
import com.xpsoft.oa.service.personal.DutySectionService;
/*     */
import flexjson.JSONSerializer;
/*     */
import java.util.List;
/*     */
import javax.annotation.Resource;
/*     */
import javax.servlet.http.HttpServletRequest;

/*     */
/*     */public class DutySectionAction extends BaseAction
/*     */{
	/*     */
	/*     */@Resource
	/*     */private DutySectionService dutySectionService;
	/*     */private DutySection dutySection;
	/*     */private Long sectionId;

	/*     */
	/*     */public Long getSectionId()
	/*     */{
		/* 29 */return this.sectionId;
		/*     */}

	/*     */
	/*     */public void setSectionId(Long sectionId) {
		/* 33 */this.sectionId = sectionId;
		/*     */}

	/*     */
	/*     */public DutySection getDutySection() {
		/* 37 */return this.dutySection;
		/*     */}

	/*     */
	/*     */public void setDutySection(DutySection dutySection) {
		/* 41 */this.dutySection = dutySection;
		/*     */}

	/*     */
	/*     */public String list()
	/*     */{
		/* 49 */QueryFilter filter = new QueryFilter(getRequest());
		/* 50 */List list = this.dutySectionService.getAll(filter);
		/*     */
		/* 52 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 53 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		/*     */
		/* 55 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[0]);
		/* 56 */buff.append(serializer.serialize(list));
		/* 57 */buff.append("}");
		/*     */
		/* 59 */this.jsonString = buff.toString();
		/*     */
		/* 61 */return "success";
		/*     */}

	/*     */
	/*     */public String multiDel()
	/*     */{
		/* 69 */String[] ids = getRequest().getParameterValues("ids");
		/* 70 */if (ids != null) {
			/* 71 */for (String id : ids) {
				/* 72 */this.dutySectionService.remove(new Long(id));
				/*     */}
			/*     */}
		/* 75 */this.jsonString = "{success:true}";
		/* 76 */return "success";
		/*     */}

	/*     */
	/*     */public String combo() {
		/* 80 */StringBuffer sb = new StringBuffer();
		/*     */
		/* 82 */List<DutySection> dutySectionList = this.dutySectionService
				.getAll();
		/* 83 */sb.append("[");
		/* 84 */for (DutySection dutySection : dutySectionList) {
			/* 85 */sb.append("['").append(dutySection.getSectionId())
					.append("','").append(dutySection.getSectionName())
					.append("'],");
			/*     */}
		/* 87 */if (dutySectionList.size() > 0) {
			/* 88 */sb.deleteCharAt(sb.length() - 1);
			/*     */}
		/* 90 */sb.append("]");
		/* 91 */setJsonString(sb.toString());
		/* 92 */return "success";
		/*     */}

	/*     */
	/*     */public String get()
	/*     */{
		/* 101 */DutySection dutySection = (DutySection) this.dutySectionService
				.get(this.sectionId);
		/*     */
		/* 103 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[0]);
		/*     */
		/* 105 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 106 */sb.append(serializer.serialize(dutySection));
		/* 107 */sb.append("}");
		/* 108 */setJsonString(sb.toString());
		/*     */
		/* 110 */return "success";
		/*     */}

	/*     */
	/*     */public String save()
	/*     */{
		/* 117 */this.dutySectionService.save(this.dutySection);
		/* 118 */setJsonString("{success:true}");
		/* 119 */return "success";
		/*     */}
	/*     */
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.oa.action.personal.DutySectionAction JD-Core Version: 0.6.0
 */