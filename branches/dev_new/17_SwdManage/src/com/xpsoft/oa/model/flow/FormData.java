package com.xpsoft.oa.model.flow;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;

import com.xpsoft.core.jbpm.pv.ParamField;
import com.xpsoft.core.model.BaseModel;

public class FormData extends BaseModel {
	/* 27 */public static final Short SHOWED = Short.valueOf((short) 1);

	/* 29 */public static final Short UNSHOWED = Short.valueOf((short) 0);
	protected Long dataId;
	protected String fieldLabel;
	protected String fieldName;
	protected Integer intValue;
	protected Long longValue;
	protected BigDecimal decValue;
	protected Date dateValue;
	protected String strValue;
	protected String blobValue;
	protected Short boolValue;
	protected String textValue;
	/* 43 */protected Short isShowed = Short.valueOf((short) 1);
	protected String fieldType;
	protected ProcessForm processForm;

	public FormData(ParamField pf) {
		/* 49 */copyValue(pf);
	}

	public void copyValue(ParamField pf) {
		/* 53 */this.fieldLabel = pf.getLabel();
		/* 54 */this.fieldName = pf.getName();
		/* 55 */this.fieldType = pf.getType();
		/* 56 */this.isShowed = pf.getIsShowed();

		/* 58 */setValue(pf.getValue(), pf.getType());
	}

	public Object getValue() {
		/* 63 */if (this.strValue != null)
			return this.strValue;
		/* 64 */if (this.intValue != null)
			return this.intValue;
		/* 65 */if (this.longValue != null)
			return this.longValue;
		/* 66 */if (this.decValue != null)
			return this.decValue;
		/* 67 */if (this.dateValue != null)
			return this.dateValue;
		/* 68 */if (this.boolValue != null)
			return this.boolValue;
		/* 69 */if (this.textValue != null)
			return this.textValue;
		/* 70 */return null;
	}

	public String getVal() {
		/* 79 */if ("varchar".equals(this.fieldType)) {
			/* 80 */return this.strValue;
		}

		/* 83 */if ("date".equals(this.fieldType)) {
			/* 84 */SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/* 85 */return this.dateValue == null ? null : sdf
					.format(this.dateValue);
		}

		/* 88 */if ("datetime".equals(this.fieldType)) {
			/* 89 */SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			/* 90 */return this.dateValue == null ? null : sdf
					.format(this.dateValue);
		}

		/* 93 */if ("int".equals(this.fieldType)) {
			/* 94 */return this.intValue == null ? null : this.intValue
					.toString();
		}

		/* 97 */if ("long".equals(this.fieldType)) {
			/* 98 */return this.longValue == null ? null : this.longValue
					.toString();
		}

		/* 101 */if ("decimal".equals(this.fieldType)) {
			/* 102 */return this.decValue == null ? null : this.decValue
					.toString();
		}

		/* 105 */if ("text".equals(this.fieldType)) {
			/* 106 */return this.textValue;
		}

		/* 109 */if ("file".equals(this.fieldType)) {
			/* 110 */return this.strValue;
		}

		/* 113 */if ("bool".equals(this.fieldType)) {
			/* 114 */return this.boolValue.shortValue() == 1 ? "是" : "否";
		}

		/* 117 */return null;
	}

	public void setValue(String val, String type) {
		/* 122 */if (val == null)
			return;
		try {
			/* 125 */if ("varchar".equals(type))
				/* 126 */this.strValue = val;
			/* 127 */else if ("bool".equals(type))
				/* 128 */this.boolValue = Short
						.valueOf((short) ("1".equals(val) ? 1 : 0));
			/* 129 */else if (("date".equals(type))
					|| ("datetime".equals(type)))
				/* 130 */this.dateValue = DateUtils.parseDate(val,
						new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" });
			/* 131 */else if ("decimal".equals(type))
				/* 132 */this.decValue = new BigDecimal(val);
			/* 133 */else if ("int".equals(type))
				/* 134 */this.intValue = new Integer(val);
			/* 135 */else if ("long".equals(type))
				/* 136 */this.longValue = new Long(val);
			/* 137 */else if ("text".equals(type))
				/* 138 */this.textValue = val;
			/* 139 */else if ("file".equals(type))
				/* 140 */this.strValue = val;
		} catch (Exception ex) {
			/* 143 */this.logger.warn("setValue error:" + ex.getMessage());
		}
	}

	public FormData() {
	}

	public FormData(Long in_dataId) {
		/* 161 */setDataId(in_dataId);
	}

	public ProcessForm getProcessForm() {
		/* 166 */return this.processForm;
	}

	public void setProcessForm(ProcessForm in_processForm) {
		/* 170 */this.processForm = in_processForm;
	}

	public Long getDataId() {
		/* 179 */return this.dataId;
	}

	public void setDataId(Long aValue) {
		/* 186 */this.dataId = aValue;
	}

	public Long getFormId() {
		/* 193 */return getProcessForm() == null ? null : getProcessForm()
				.getFormId();
	}

	public Short getBoolValue() {
		/* 197 */return this.boolValue;
	}

	public void setBoolValue(Short boolValue) {
		/* 201 */this.boolValue = boolValue;
	}

	public void setFormId(Long aValue) {
		/* 208 */if (aValue == null) {
			/* 209 */this.processForm = null;
			/* 210 */} else if (this.processForm == null) {
			/* 211 */this.processForm = new ProcessForm(aValue);
			/* 212 */this.processForm.setVersion(new Integer(0));
		} else {
			/* 214 */this.processForm.setFormId(aValue);
		}
	}

	public String getFieldLabel() {
		/* 223 */return this.fieldLabel;
	}

	public void setFieldLabel(String aValue) {
		/* 231 */this.fieldLabel = aValue;
	}

	public String getFieldName() {
		/* 239 */return this.fieldName;
	}

	public void setFieldName(String aValue) {
		/* 247 */this.fieldName = aValue;
	}

	public Integer getIntValue() {
		/* 255 */return this.intValue;
	}

	public void setIntValue(Integer aValue) {
		/* 262 */this.intValue = aValue;
	}

	public BigDecimal getDecValue() {
		/* 270 */return this.decValue;
	}

	public void setDecValue(BigDecimal aValue) {
		/* 277 */this.decValue = aValue;
	}

	public Date getDateValue() {
		/* 285 */return this.dateValue;
	}

	public void setDateValue(Date aValue) {
		/* 292 */this.dateValue = aValue;
	}

	public String getStrValue() {
		/* 300 */return this.strValue;
	}

	public void setStrValue(String aValue) {
		/* 307 */this.strValue = aValue;
	}

	public String getBlobValue() {
		/* 315 */return this.blobValue;
	}

	public void setBlobValue(String aValue) {
		/* 322 */this.blobValue = aValue;
	}

	public Short getIsShowed() {
		/* 332 */return this.isShowed;
	}

	public void setIsShowed(Short aValue) {
		/* 340 */this.isShowed = aValue;
	}

	public boolean equals(Object object) {
		/* 347 */if (!(object instanceof FormData)) {
			/* 348 */return false;
		}
		/* 350 */FormData rhs = (FormData) object;
		/* 351 */return new EqualsBuilder()
		/* 352 */.append(this.dataId, rhs.dataId)
		/* 353 */.append(this.fieldLabel, rhs.fieldLabel)
		/* 354 */.append(this.fieldName, rhs.fieldName)
		/* 355 */.append(this.intValue, rhs.intValue)
		/* 356 */.append(this.decValue, rhs.decValue)
		/* 357 */.append(this.dateValue, rhs.dateValue)
		/* 358 */.append(this.strValue, rhs.strValue)
		/* 359 */.append(this.blobValue, rhs.blobValue)
		/* 360 */.append(this.isShowed, rhs.isShowed)
		/* 361 */.isEquals();
	}

	public int hashCode() {
		/* 368 */return new HashCodeBuilder(-82280557, -700257973)
		/* 369 */.append(this.dataId)
		/* 370 */.append(this.fieldLabel)
		/* 371 */.append(this.fieldName)
		/* 372 */.append(this.intValue)
		/* 373 */.append(this.decValue)
		/* 374 */.append(this.dateValue)
		/* 375 */.append(this.strValue)
		/* 376 */.append(this.blobValue)
		/* 377 */.append(this.isShowed)
		/* 378 */.toHashCode();
	}

	public String toString() {
		/* 385 */return new ToStringBuilder(this)
		/* 386 */.append("dataId", this.dataId)
		/* 387 */.append("fieldLabel", this.fieldLabel)
		/* 388 */.append("fieldName", this.fieldName)
		/* 389 */.append("intValue", this.intValue)
		/* 390 */.append("decValue", this.decValue)
		/* 391 */.append("dateValue", this.dateValue)
		/* 392 */.append("strValue", this.strValue)
		/* 393 */.append("blobValue", this.blobValue)
		/* 394 */.append("isShowed", this.isShowed)
		/* 395 */.toString();
	}

	public Long getLongValue() {
		/* 399 */return this.longValue;
	}

	public void setLongValue(Long longValue) {
		/* 403 */this.longValue = longValue;
	}

	public String getTextValue() {
		/* 407 */return this.textValue;
	}

	public void setTextValue(String textValue) {
		/* 411 */this.textValue = textValue;
	}

	public String getFieldType() {
		/* 415 */return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		/* 419 */this.fieldType = fieldType;
	}

}
