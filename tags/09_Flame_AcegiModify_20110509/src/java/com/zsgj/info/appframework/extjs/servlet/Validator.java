package com.zsgj.info.appframework.extjs.servlet;


public class Validator {


	static public String get(String type) {
		String validatorName = "";
		if(type.equalsIgnoreCase("Number")) {//数字
			validatorName = "validate_number";
		}
		else if(type.equalsIgnoreCase("Integer")) {//必须为整数
			validatorName = "validate_integer";
		}
		else if(type.equalsIgnoreCase("Double")) {//数字
			validatorName = "validate_double";
		}
		else if(type.equalsIgnoreCase("Currency")) {//数字
			validatorName = "validate_currency";
		}
		else if(type.equalsIgnoreCase("Phone")) {//电话 数字
			validatorName = "validate_phone";
		}
		else if(type.equalsIgnoreCase("Mobile")) {//移动电话 数字
			validatorName = "validate_mobile";
		}
		else if(type.equalsIgnoreCase("Email")) {//Email
			validatorName = "validate_email";
		}
		else if(type.equalsIgnoreCase("Zip")) {//数字
			validatorName = "validate_zip";
		}
		else if(type.equalsIgnoreCase("Date")) {//日期类型
			validatorName = "validate_date";
		}
		else if(type.equalsIgnoreCase("IdCard")) {//数字
			validatorName = "validate_idcard";
		}
		else if(type.equalsIgnoreCase("English")) {//英语
			validatorName = "validate_english";
		}
		else if(type.equalsIgnoreCase("Chinese")) {//中文
			validatorName = "validate_chinese";
		}
		else {
			validatorName = "''";
		}
		return validatorName;
	}
}
