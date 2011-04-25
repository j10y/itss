package com.digitalchina.info.framework.util.idgen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.WorkingMemory;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.service.Service;
/**
 * 执行规则的方法
 * @Class Name ProcessRuleHelper
 * @Author YangTao
 * @Create In Mar 4, 2009
 */
public class IdGenRuleMethodHelper{
	Service service = (Service) ContextHolder.getBean("baseService");
	/**
	 * 执行规则
	 * @Methods Name executeRule
	 * @Create In Mar 4, 2009 By Administrator
	 * @param mapParams
	 * @param name
	 * @param node void
	 */
	public static synchronized String executeRule(String rulePath,Map<String, String> mapParams) {
		
		List<String> rulePaths = new ArrayList<String>();
		rulePaths.add(rulePath);
		
        RuleBase ruleBase = IdGenRuleHelper.readRule(rulePaths);

        WorkingMemory workingMemory = ruleBase.newStatefulSession();
       
        workingMemory.insert( mapParams );  
      
      
        workingMemory.fireAllRules(); 
        
     
        
       return mapParams.get("result");
	}
	
	public void test() {
	       
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("ruleName", "bid");
        executeRule("/com/digitalchina/itil/rules/OrderProductLine1.drl",mapParams);
        
	}
	
	
	
	public static final void main(String[] args) {       
		IdGenRuleMethodHelper orh = new IdGenRuleMethodHelper();
            orh.test();
    }
	
	/**
	 * 获取当前表的下一个编号
	 * @Methods Name getTableCurrentGeneratedId
	 * @Create In Apr 10, 2009 By sa
	 * @param prefix 编号规则前缀
	 * @param length 编号长度
	 * @param latestValue 最新的编号值
	 * @param latestNumber
	 * @return String
	 */
	public String getTableCurrentGeneratedId(String prefix, String length, String latestValue, Department dept){
		//总长度
		Long idTotalLength = Long.valueOf(length);
		//数字总长度
		int numberLength = idTotalLength.intValue()-prefix.length();
		Long latestNumber = 0L;
		if(latestValue!=null&& !latestValue.equals("")){ //如果最新编号值不null
			//通过最新编号截取最新的数字编号部分
			String latestNumberString = latestValue.substring(prefix.length());
			//将此编号转成数字格式
			latestNumber = Long.valueOf(latestNumberString);
		}
		
		latestNumber ++;
		
		//begin
		
		//根据起始值的长度设置数字格式化器
		StringBuffer formatStr = new StringBuffer("");
		//种子长度，也就是00000001的总长度
		for(int ii=0; ii<numberLength; ii++){
			formatStr.append("0");
		}
		//格式化成固定位数，前面不够补0的格式
		DecimalFormat df = new DecimalFormat(formatStr.toString());
		
		//end
			
		String result = prefix + df.format(latestNumber);
		return result;
	}

}
