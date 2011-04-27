package com.zsgj.itil.idgen.rules;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.WorkingMemory;

import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.util.idgen.IdGenRuleHelper;

/**
 * 需求帐号编号申请器规则
 * @Class Name ReqTableIdGenRuleHelper
 * @Author lee
 * @Create In Jan 26, 2010
 */
public class ReqTableIdGenRuleHelper {
	
	/**
	 * 获取需求编号
	 * @Methods Name getReqTableCurrentGeneratedId
	 * @Create In Jan 27, 2010 By lee
	 * @param prefix	前缀
	 * @param length	长度
	 * @param latestValue	最后编号
	 * @param dept	//部门
	 * @return String	返回值
	 */
	public String getReqTableCurrentGeneratedId(String prefix, String length, String latestValue, Department dept){
		//总长度
		Long idTotalLength = Long.valueOf(length);
		//数字总长度
		int numberLength = idTotalLength.intValue()-prefix.length()-8;	//8为年月日长度
		Long latestNumber = 0L;
		if(latestValue!=null&& !latestValue.equals("")){ //如果最新编号值不null
			//通过最新编号截取最新的数字编号部分
			String latestNumberString = latestValue.substring(prefix.length()).substring(8);
			//将此编号转成数字格式
			latestNumber = Long.valueOf(latestNumberString);
		}
		
		latestNumber ++;
		//得到当前时间
		Date curDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String curDateStr = df.format(curDate);
		
		//根据起始值的长度设置数字格式化器
		StringBuffer formatStr = new StringBuffer("");
		//种子长度，也就是00000001的总长度
		for(int ii=0; ii<numberLength; ii++){
			formatStr.append("0");
		}
		//格式化成固定位数，前面不够补0的格式
		DecimalFormat df1 = new DecimalFormat(formatStr.toString());
		
		//end
			
		String result = prefix + curDateStr + df1.format(latestNumber);
		return result;
	}
}
