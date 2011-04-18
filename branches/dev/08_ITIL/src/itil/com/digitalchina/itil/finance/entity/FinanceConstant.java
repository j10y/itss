package com.digitalchina.itil.finance.entity;

public class FinanceConstant {

	
	public static final Integer COSTREDUCECONFIG =1;//成本归结类型配置项为1
	public static final Integer COSTREDUCESERVER =2;//成本归结类型服务项为2
	public static final Integer BORROWTYPEEXPENSE =1;//借款类型1为直接报销
	public static final Integer BORROWTYPELOAN =2;//借款类型2为借款
	public static final Integer BORROWTYPESQUARE =3;//借款类型3为借款后清帐
	public static final Integer COSTDATARESOURECEHAND =1;//费用数据来源1为手工录入
	public static final Integer COSTDATARESOURECEERP =2;//费用数据来源2为ERP导入
	public static final Integer COSTDATARESOURECESYSTEM =3;//费用数据来源3为系统计算
	public static final Integer ERPTYPE=1;//是ERP科目
	public static final Integer NOTERPTYPE=0;//非ERP科目
	public static final String NOTERPTYPEUSERTOTALAMOUNT="A004050100";//非常用科目
	public static final Integer USERDAPPORTION=1;//用于分摊
	public static final Integer NOTUSERDAPPORTION=0;//不用于分摊
	public static final Integer USUALSUBJECTS=1;//常用科目
	public static final Integer NOTUSUALSUBJECTS=0;//非常用科目
	
}
