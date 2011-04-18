package com.digitalchina.info.appframework.metadata.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ÔËËã·ûÀàÐÍ
 * 
 * @Class Name Operator
 * @Author sa
 * @Create In 2009-2-12
 */
public class Operator extends BaseObject {

	public final static String OPERATOR_EQ = "=";

	public final static String OPERATOR_NE = "!=";

	public final static String OPERATOR_NE_ANSINULL_OFF = "!=%";

	public final static String OPERATOR_GE = ">=";

	public final static String OPERATOR_GT = ">";

	public final static String OPERATOR_NGE = "!>=";

	public final static String OPERATOR_NGT = "!>";

	public final static String OPERATOR_LE = "<=";

	public final static String OPERATOR_LT = "<";

	public final static String OPERATOR_NLE = "!<=";

	public final static String OPERATOR_NLT = "!<";

	public final static String OPERATOR_LIKE = "like";

	public final static String OPERATOR_NLIKE = "!like";

	public final static String OPERATOR_INCLUDE = "include";

	public final static String OPERATOR_NINCLUDE = "!include";

	public final static String OPERATOR_ILIKE = "ilike";

	public final static String OPERATOR_NILIKE = "!ilike";

	public final static String OPERATOR_IINCLUDE = "iinclude";

	public final static String OPERATOR_NIINCLUDE = "!iinclude";

	public final static String OPERATOR_IS = "is";

	public final static String OPERATOR_NIS = "!is";

	public final static String OPERATOR_IN = "in";

	public final static String OPERATOR_NIN = "!in";

	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
