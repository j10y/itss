package com.zsgj.info.appframework.extjs.servlet;


public class Validator {


	static public String get(String type) {
		String validatorName = "";
		if(type.equalsIgnoreCase("Number")) {//����
			validatorName = "validate_number";
		}
		else if(type.equalsIgnoreCase("Integer")) {//����Ϊ����
			validatorName = "validate_integer";
		}
		else if(type.equalsIgnoreCase("Double")) {//����
			validatorName = "validate_double";
		}
		else if(type.equalsIgnoreCase("Currency")) {//����
			validatorName = "validate_currency";
		}
		else if(type.equalsIgnoreCase("Phone")) {//�绰 ����
			validatorName = "validate_phone";
		}
		else if(type.equalsIgnoreCase("Mobile")) {//�ƶ��绰 ����
			validatorName = "validate_mobile";
		}
		else if(type.equalsIgnoreCase("Email")) {//Email
			validatorName = "validate_email";
		}
		else if(type.equalsIgnoreCase("Zip")) {//����
			validatorName = "validate_zip";
		}
		else if(type.equalsIgnoreCase("Date")) {//��������
			validatorName = "validate_date";
		}
		else if(type.equalsIgnoreCase("IdCard")) {//����
			validatorName = "validate_idcard";
		}
		else if(type.equalsIgnoreCase("English")) {//Ӣ��
			validatorName = "validate_english";
		}
		else if(type.equalsIgnoreCase("Chinese")) {//����
			validatorName = "validate_chinese";
		}
		else {
			validatorName = "''";
		}
		return validatorName;
	}
}
