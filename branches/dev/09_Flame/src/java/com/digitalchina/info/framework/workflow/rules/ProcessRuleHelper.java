package com.digitalchina.info.framework.workflow.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.RuleBase;
import org.drools.WorkingMemory;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.RuleFileException;
import com.digitalchina.info.framework.service.Service;
/**
 * 
 * @Class Name ProcessRuleHelper
 * @Author YangTao
 * @Create In Mar 4, 2009
 */
public class ProcessRuleHelper{
	Service service = (Service) ContextHolder.getBean("baseService");
	private static Logger log;
	static 
	{
		log = Logger.getLogger("workflowlog");
	}
	/**
	 * 执行规则
	 * @Methods Name executeRule
	 * @Create In Mar 4, 2009 By Administrator
	 * @param mapParams
	 * @param name
	 * @param node void
	 */
	public static String executeRule(String rulePath,Map<String, String> mapParams){
		
		List<String> rulePaths = new ArrayList<String>();
		rulePaths.add(rulePath);
		try{
			RuleBase ruleBase = BaseRuleHelper.readRule(rulePaths);
	        
	        WorkingMemory workingMemory = ruleBase.newStatefulSession();
	       
	        workingMemory.insert( mapParams );  
	      
	        workingMemory.fireAllRules(); 
		}catch(Exception e){
			if(e.getCause() instanceof com.digitalchina.info.framework.exception.RuleFileException){
				log.error("读取"+rulePath+"发生异常");
				throw new RuleFileException(e.getMessage());
			}else{
				log.error("读取"+rulePath+"发生异常");
				throw new RuntimeException("read ruleFile Exception !");
			}
		}
       return mapParams.get("transitionName");
	}
	
	public void test() {
	       
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("ruleName", "bid");
        executeRule("/com/digitalchina/itil/rules/OrderProductLine1.drl",mapParams);
        
	}
	
	public static final void main(String[] args) {       
		ProcessRuleHelper orh = new ProcessRuleHelper();
            orh.test();
    }
	/*************************************************************************************************************************************/
	
}
