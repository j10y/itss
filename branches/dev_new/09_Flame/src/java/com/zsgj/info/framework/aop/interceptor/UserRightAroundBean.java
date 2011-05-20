package com.zsgj.info.framework.aop.interceptor;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 用户权限Around Advice，实现对服务方法的权限拦截。
 * 注意：此类的userRights的类型为List， 用于存放与某一服务方法相关的权限配置。
 * 如查看合同审批信息时的要求有 合同审批写和决策只读只读2个权限。此时，权限配置文件
   applicationContext-security.xml的配置如下：<br>
 * <pre>
    &lt;bean id="contractWriteRightValidateAdvice" 
    	  class="com.digitalchina.info.common.base.aop.advice.UserRightAroundBean"&gt; 
        &lt;property name="userRights"&gt;
			&lt;list&gt;	
				&lt;value>616-2-合同管理读and202-1-决策只读&lt;/value&gt; 
			&lt;/list&gt;
		&lt;/property&gt;
	&lt;/bean&gt;
 * </pre>
 * 如果权限之间的关系为或，则给userRights这个list再加一个元素。权限之间的与关系使用and表示（xml中不允许使用&&符号）。
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

		System.out.println("●before invoke "+ result);
		
		// 获取与当前线程绑定的userInfo
//		UserInfo userInfo = UserContext.getUserInfo();

		// 权限验证
		
		//AccessChecker checker = new AccessChecker();
		for (int i = 0; i < userRights.size(); i++) {
			String line = (String) userRights.get(i);

			StringTokenizer token = new StringTokenizer(line, ",");
			while (token.hasMoreTokens()) {
				String item = token.nextToken(); // 616-1-查看项目&&702-2-合同写
				String[] splits = item.split("-"); // 616-1-查看项目

				int rightCode = Integer.valueOf(splits[0]).intValue(); // 616
				int flag = Integer.valueOf(splits[1]).intValue(); // 1
				String moduleName = splits[2]; // 查看项目
				
				logger.debug("right info: "+rightCode+"-"+flag+"-"+moduleName);
				/*logger.debug("checker.isPermit("+userId+", "+rightCode+", "+flag+"): "
						+checker.isPermit(userId, rightCode, flag));

				if (!checker.isPermit(userId, rightCode, flag)) { // 无其中任意权限，直接抛出异常
					logger.error(userInfo.getUserName()+"(userId:"+userInfo.getUserName()
							+"),没有" + moduleName + "权限,错误号:"+0x1d4c1);
					throw new ServiceException("对不起，没有" + moduleName + "权限", 0x1d4c1);
							
				}*/
			}

		}// for

		Object object = joinPoint.proceed();
		
		System.out.println("●after invoke "+ result);
		
		return object;
	}
}
