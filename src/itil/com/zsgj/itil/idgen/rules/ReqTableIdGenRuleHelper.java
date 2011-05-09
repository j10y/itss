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
 * �����ʺű������������
 * @Class Name ReqTableIdGenRuleHelper
 * @Author lee
 * @Create In Jan 26, 2010
 */
public class ReqTableIdGenRuleHelper {
	
	/**
	 * ��ȡ������
	 * @Methods Name getReqTableCurrentGeneratedId
	 * @Create In Jan 27, 2010 By lee
	 * @param prefix	ǰ׺
	 * @param length	����
	 * @param latestValue	�����
	 * @param dept	//����
	 * @return String	����ֵ
	 */
	public String getReqTableCurrentGeneratedId(String prefix, String length, String latestValue, Department dept){
		//�ܳ���
		Long idTotalLength = Long.valueOf(length);
		//�����ܳ���
		int numberLength = idTotalLength.intValue()-prefix.length()-8;	//8Ϊ�����ճ���
		Long latestNumber = 0L;
		if(latestValue!=null&& !latestValue.equals("")){ //������±��ֵ��null
			//ͨ�����±�Ž�ȡ���µ����ֱ�Ų���
			String latestNumberString = latestValue.substring(prefix.length()).substring(8);
			//���˱��ת�����ָ�ʽ
			latestNumber = Long.valueOf(latestNumberString);
		}
		
		latestNumber ++;
		//�õ���ǰʱ��
		Date curDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String curDateStr = df.format(curDate);
		
		//������ʼֵ�ĳ����������ָ�ʽ����
		StringBuffer formatStr = new StringBuffer("");
		//���ӳ��ȣ�Ҳ����00000001���ܳ���
		for(int ii=0; ii<numberLength; ii++){
			formatStr.append("0");
		}
		//��ʽ���ɹ̶�λ����ǰ�治����0�ĸ�ʽ
		DecimalFormat df1 = new DecimalFormat(formatStr.toString());
		
		//end
			
		String result = prefix + curDateStr + df1.format(latestNumber);
		return result;
	}
}
