package com.xpsoft.core.command;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class FieldCommandImpl implements CriteriaCommand {
	/* 17 */private static Log logger = LogFactory
			.getLog(CriteriaCommand.class);
	private String property;
	private Object value;
	private String operation;
	private QueryFilter filter;

	public FieldCommandImpl(String property, Object value, String operation,
			QueryFilter filter) {
		/* 34 */this.property = property;
		/* 35 */this.value = value;
		/* 36 */this.operation = operation;
		/* 37 */this.filter = filter;
	}

	public String getProperty() {
		/* 41 */return this.property;
	}

	public void setProperty(String property) {
		/* 45 */this.property = property;
	}

	public Object getValue() {
		/* 49 */return this.value;
	}

	public void setValue(Object value) {
		/* 53 */this.value = value;
	}

	public String getOperation() {
		/* 57 */return this.operation;
	}

	public void setOperation(String operation) {
		/* 61 */this.operation = operation;
	}

	public Criteria execute(Criteria criteria) {
		String[] propertys = this.property.split("[.]");
		if ((propertys != null) && (propertys.length > 1) &&
		(!"vo".equals(propertys[0]))) {
			for (int i = 0; i < propertys.length - 1; i++) {
				if (!this.filter.getAliasSet().contains(propertys[i])) {
					criteria.createAlias(propertys[i], propertys[i]);
					this.filter.getAliasSet().add(propertys[i]);
				}
			}
		}

		if ("LT".equals(this.operation))
			criteria.add(Restrictions.lt(this.property, this.value));
		else if ("GT".equals(this.operation))
			criteria.add(Restrictions.gt(this.property, this.value));
		else if ("LE".equals(this.operation))
			criteria.add(Restrictions.le(this.property, this.value));
		else if ("GE".equals(this.operation))
			criteria.add(Restrictions.ge(this.property, this.value));
		else if ("LK".equals(this.operation))
			criteria.add(Restrictions.like(this.property,
					"%" + this.value + "%").ignoreCase());
		else if ("LFK".equals(this.operation))
			criteria.add(Restrictions.like(this.property,
					this.value + "%").ignoreCase());
		else if ("RHK".equals(this.operation))
			criteria.add(Restrictions.like(this.property,
					"%" + this.value).ignoreCase());
		else if ("NULL".equals(this.operation))
			criteria.add(Restrictions.isNull(this.property));
		else if ("NOTNULL".equals(this.operation))
			criteria.add(Restrictions.isNotNull(this.property));
		else if ("EMP".equals(this.operation))
			criteria.add(Restrictions.isEmpty(this.property));
		else if ("NOTEMP".equals(this.operation))
			criteria.add(Restrictions.isNotEmpty(this.property));
		else if ("NEQ".equals(this.operation))
			criteria.add(Restrictions.ne(this.property, this.value));
		else if ("ORLIKE".equals(this.operation)){
			String[] propertys2 = this.property.split("[,]");
			Disjunction disjunction =Restrictions.disjunction();
			for(String pro:propertys2){
				Property prop = Property.forName(pro);
				disjunction.add(prop.like(value+"",  MatchMode.ANYWHERE));
			}
			criteria.add(disjunction);
		}
		else {
			criteria.add(Restrictions.eq(this.property, this.value));
		}

		/* 108 */return criteria;
	}

	public String getPartHql() {
		/* 113 */String[] propertys = this.property.split("[.]");
		/* 114 */if ((propertys != null) && (propertys.length > 1) &&
		/* 115 */(!"vo".equals(propertys[0]))) {
			/* 117 */if (!this.filter.getAliasSet().contains(propertys[0])) {
				/* 118 */this.filter.getAliasSet().add(propertys[0]);
			}
		}

		/* 122 */String partHql = "";
		/* 123 */if ("LT".equals(this.operation)) {
			/* 124 */partHql = this.property + " < ? ";
			/* 125 */this.filter.getParamValueList().add(this.value);
			/* 126 */} else if ("GT".equals(this.operation)) {
			/* 127 */partHql = this.property + " > ? ";
			/* 128 */this.filter.getParamValueList().add(this.value);
			/* 129 */} else if ("LE".equals(this.operation)) {
			/* 130 */partHql = this.property + " <= ? ";
			/* 131 */this.filter.getParamValueList().add(this.value);
			/* 132 */} else if ("GE".equals(this.operation)) {
			/* 133 */partHql = this.property + " >= ? ";
			/* 134 */this.filter.getParamValueList().add(this.value);
			/* 135 */} else if ("LK".equals(this.operation)) {
			/* 136 */partHql = this.property + " like ? ";
			/* 137 */this.filter.getParamValueList().add(
					"%" + this.value.toString() + "%");
			/* 138 */} else if ("LFK".equals(this.operation)) {
			/* 139 */partHql = this.property + " like ? ";
			/* 140 */this.filter.getParamValueList().add(
					this.value.toString() + "%");
			/* 141 */} else if ("RHK".equals(this.operation)) {
			/* 142 */partHql = this.property + " like ? ";
			/* 143 */this.filter.getParamValueList().add(
					"%" + this.value.toString());
			/* 144 */} else if ("NULL".equals(this.operation)) {
			/* 145 */partHql = this.property + " is null ";
			/* 146 */} else if ("NOTNULL".equals(this.operation)) {
			/* 147 */partHql = this.property + " is not null ";
			/* 148 */} else if (!"EMP".equals(this.operation)) {
			/* 150 */if (!"NOTEMP".equals(this.operation)) {
				/* 152 */if ("NEQ".equals(this.operation)) {
					/* 153 */partHql = this.property + " !=? ";
					/* 154 */this.filter.getParamValueList().add(this.value);
				} else {
					/* 156 */partHql = partHql + this.property + " =? ";
					/* 157 */this.filter.getParamValueList().add(this.value);
				}
			}
		}
		/* 160 */return partHql;
	}
}