package com.xpsoft.core.command;

import com.xpsoft.core.util.ParamUtil;
import com.xpsoft.core.web.paging.PagingBean;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryFilter {
	public static final String ORDER_DESC = "desc";
	public static final String ORDER_ASC = "asc";
	/* 44 */public static final Log logger = LogFactory
			.getLog(QueryFilter.class);

	/* 46 */private HttpServletRequest request = null;

	/* 51 */private String filterName = null;

	/* 61 */private List<Object> paramValues = new ArrayList();

	/* 63 */private List<CriteriaCommand> commands = new ArrayList();

	/* 65 */private Set<String> aliasSet = new HashSet();

	/* 67 */private PagingBean pagingBean = null;

	public String getFilterName() {
		/* 54 */return this.filterName;
	}

	public void setFilterName(String filterName) {
		/* 58 */this.filterName = filterName;
	}

	public PagingBean getPagingBean() {
		/* 70 */return this.pagingBean;
	}

	public QueryFilter(PagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}

	public QueryFilter(HttpServletRequest request) {
		/* 87 */this.request = request;
		/* 88 */Enumeration paramEnu = request.getParameterNames();
		/* 89 */while (paramEnu.hasMoreElements()) {
			/* 90 */String paramName = (String) paramEnu.nextElement();

			/* 92 */if (paramName.startsWith("Q_")) {
				/* 93 */String paramValue = request.getParameter(paramName);
				/* 94 */addFilter(paramName, paramValue);
			}
		}

		/* 98 */Integer start = Integer.valueOf(0);
		/* 99 */Integer limit = PagingBean.DEFAULT_PAGE_SIZE;

		/* 101 */String s_start = request.getParameter("start");
		/* 102 */String s_limit = request.getParameter("limit");
		/* 103 */if (StringUtils.isNotEmpty(s_start)) {
			/* 104 */start = new Integer(s_start);
		}
		/* 106 */if (StringUtils.isNotEmpty(s_limit)) {
			/* 107 */limit = new Integer(s_limit);
		}

		/* 110 */String sort = request.getParameter("sort");
		/* 111 */String dir = request.getParameter("dir");

		/* 113 */if ((StringUtils.isNotEmpty(sort))
				&& (StringUtils.isNotEmpty(dir))) {
			/* 114 */addSorted(sort, dir);
		}

		/* 117 */this.pagingBean = new PagingBean(start.intValue(), limit
				.intValue());
	}

	public void addFilter(String paramName, String paramValue) {
		/* 139 */String[] fieldInfo = paramName.split("[_]");
		/* 140 */Object value = null;
		/* 141 */if ((fieldInfo != null) && (fieldInfo.length == 4)) {
			/* 142 */value = ParamUtil.convertObject(fieldInfo[2], paramValue);
			/* 143 */if (value != null) {
				/* 144 */FieldCommandImpl fieldCommand = new FieldCommandImpl(
						fieldInfo[1], value, fieldInfo[3], this);
				/* 145 */this.commands.add(fieldCommand);
			}
			/* 147 */} else if ((fieldInfo != null) && (fieldInfo.length == 3)) {
			/* 148 */FieldCommandImpl fieldCommand = new FieldCommandImpl(
					fieldInfo[1], value, fieldInfo[2], this);
			/* 149 */this.commands.add(fieldCommand);
		} else {
			/* 151 */logger.error("Query param name [" + paramName
					+ "] is not right format.");
		}
	}

	public void addParamValue(Object value) {
		/* 157 */this.paramValues.add(value);
	}

	public List getParamValueList() {
		/* 161 */return this.paramValues;
	}

	public void addSorted(String orderBy, String ascDesc) {
		/* 165 */this.commands
				.add(new SortCommandImpl(orderBy, ascDesc, this));
	}

	public void addExample(Object object) {
		/* 169 */this.commands.add(new ExampleCommandImpl(object));
	}

	public List<CriteriaCommand> getCommands() {
		/* 173 */return this.commands;
	}

	public Set<String> getAliasSet() {
		/* 177 */return this.aliasSet;
	}
}
