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
 * ִ�й���ķ���
 * @Class Name ProcessRuleHelper
 * @Author YangTao
 * @Create In Mar 4, 2009
 */
public class IdGenRuleMethodHelper{
	Service service = (Service) ContextHolder.getBean("baseService");
	/**
	 * ִ�й���
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
	 * ��ȡ��ǰ�����һ�����
	 * @Methods Name getTableCurrentGeneratedId
	 * @Create In Apr 10, 2009 By sa
	 * @param prefix ��Ź���ǰ׺
	 * @param length ��ų���
	 * @param latestValue ���µı��ֵ
	 * @param latestNumber
	 * @return String
	 */
	public String getTableCurrentGeneratedId(String prefix, String length, String latestValue, Department dept){
		//�ܳ���
		Long idTotalLength = Long.valueOf(length);
		//�����ܳ���
		int numberLength = idTotalLength.intValue()-prefix.length();
		Long latestNumber = 0L;
		if(latestValue!=null&& !latestValue.equals("")){ //������±��ֵ��null
			//ͨ�����±�Ž�ȡ���µ����ֱ�Ų���
			String latestNumberString = latestValue.substring(prefix.length());
			//���˱��ת�����ָ�ʽ
			latestNumber = Long.valueOf(latestNumberString);
		}
		
		latestNumber ++;
		
		//begin
		
		//������ʼֵ�ĳ����������ָ�ʽ����
		StringBuffer formatStr = new StringBuffer("");
		//���ӳ��ȣ�Ҳ����00000001���ܳ���
		for(int ii=0; ii<numberLength; ii++){
			formatStr.append("0");
		}
		//��ʽ���ɹ̶�λ����ǰ�治����0�ĸ�ʽ
		DecimalFormat df = new DecimalFormat(formatStr.toString());
		
		//end
			
		String result = prefix + df.format(latestNumber);
		return result;
	}

}
