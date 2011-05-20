package com.zsgj.info.framework.aop.interceptor;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * �û�Ȩ��Around Advice��ʵ�ֶԷ��񷽷���Ȩ�����ء�
 * ע�⣺�����userRights������ΪList�� ���ڴ����ĳһ���񷽷���ص�Ȩ�����á�
 * ��鿴��ͬ������Ϣʱ��Ҫ���� ��ͬ����д�;���ֻ��ֻ��2��Ȩ�ޡ���ʱ��Ȩ�������ļ�
   applicationContext-security.xml���������£�<br>
 * <pre>
    &lt;bean id="contractWriteRightValidateAdvice" 
    	  class="com.digitalchina.info.common.base.aop.advice.UserRightAroundBean"&gt; 
        &lt;property name="userRights"&gt;
			&lt;list&gt;	
				&lt;value>616-2-��ͬ�����and202-1-����ֻ��&lt;/value&gt; 
			&lt;/list&gt;
		&lt;/property&gt;
	&lt;/bean&gt;
 * </pre>
 * ���Ȩ��֮��Ĺ�ϵΪ�����userRights���list�ټ�һ��Ԫ�ء�Ȩ��֮������ϵʹ��and��ʾ��xml�в�����ʹ��&&���ţ���
 * @Class Name UserRightAroundBean
 * @author xiaofeng
 * @Create In 2007-10-26
 * 
 */
public class UserRightAroundBean {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private List userRights;
	
	public List getUserRights() {
		return userRights;
	}

	public void setUserRights(List userRights) {
		this.userRights = userRights;
	}

	public Object aroundRightValidateCalls(ProceedingJoinPoint joinPoint)
			throws Throwable {

		String methodName = joinPoint.getSignature().getName();
		String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
//		String targetClassName = joinPoint.getTarget().getClass().getName();
		String argsString = "";
		Object[] args = joinPoint.getArgs();
		for(int i=0; i<args.length; i++){
			if(args[i]!=null){
				argsString+=args[i]+",";
				//argsString+=args[i].getClass().getName()+"("+args[i]+"),";
			}else{
				argsString+="null,";
			}
			
		}
		argsString=argsString.substring(0, argsString.length()-1);
		argsString=argsString.substring(argsString.lastIndexOf(".")+1);
		declaringTypeName=declaringTypeName.substring(declaringTypeName.lastIndexOf(".")+1);
		
		String result = declaringTypeName+"."+methodName+"("+argsString+")";

		System.out.println("��before invoke "+ result);
		
		// ��ȡ�뵱ǰ�̰߳󶨵�userInfo
//		UserInfo userInfo = UserContext.getUserInfo();

		// Ȩ����֤
		
		//AccessChecker checker = new AccessChecker();
		for (int i = 0; i < userRights.size(); i++) {
			String line = (String) userRights.get(i);

			StringTokenizer token = new StringTokenizer(line, ",");
			while (token.hasMoreTokens()) {
				String item = token.nextToken(); // 616-1-�鿴��Ŀ&&702-2-��ͬд
				String[] splits = item.split("-"); // 616-1-�鿴��Ŀ

				int rightCode = Integer.valueOf(splits[0]).intValue(); // 616
				int flag = Integer.valueOf(splits[1]).intValue(); // 1
				String moduleName = splits[2]; // �鿴��Ŀ
				
				logger.debug("right info: "+rightCode+"-"+flag+"-"+moduleName);
				/*logger.debug("checker.isPermit("+userId+", "+rightCode+", "+flag+"): "
						+checker.isPermit(userId, rightCode, flag));

				if (!checker.isPermit(userId, rightCode, flag)) { // ����������Ȩ�ޣ�ֱ���׳��쳣
					logger.error(userInfo.getUserName()+"(userId:"+userInfo.getUserName()
							+"),û��" + moduleName + "Ȩ��,�����:"+0x1d4c1);
					throw new ServiceException("�Բ���û��" + moduleName + "Ȩ��", 0x1d4c1);
							
				}*/
			}

		}// for

		Object object = joinPoint.proceed();
		
		System.out.println("��after invoke "+ result);
		
		return object;
	}
}
