package com.digitalchina.info.framework.aop.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import com.digitalchina.info.framework.exception.DaoException;

/**
 * Dao异常自动日志记录拦截器
 * 所有异常Dao 结尾的Dao类的所有方法将被拦截，即环绕实际的Dao方法前后加上异常捕获，
 * 该拦截器的aroundDaoMethodCalls方法捕获Dao方法的DataAccessException，首先记录
 * DataAccessException的异常信息到日志文件， 然后重新抛出为我们自定义的DaoException,
 * 该DaoException是DataAccessException的子类。
 * <p>异常服务层必须捕获DaoException，并重新抛出为服务层异常ServiceException。
 * @class Name DaoExceptionAroundBean
 * @author xiaofeng
 * @create In 2007-10-30
 */
public class DaoExceptionAroundBean {
	
	private final Log logger = LogFactory.getLog("daolog");

	public Object aroundDaoMethodCalls(ProceedingJoinPoint joinPoint)
			throws Throwable {
		
		logger.debug("before invoke dao method:"+ joinPoint.getSignature().getName());

		Object object = null;
		try {
			object = joinPoint.proceed(); //具体的Dao方法
		} catch (Throwable e) { //暂且改为Throwable
			e.printStackTrace();
			//记录DataAccessException
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			logger.error("--------------------begin exception log--------------------------------------");
			logger.error(className+"."+methodName+ " method occur dataAccessException: "+e.getMessage(), e); 
			logger.error("----------------------------------------------------------------");
			throw new DaoException(e.getMessage(), e); //转为DaoException重新抛出给服务层去catch
		}
		
		logger.debug("after invoke dao method:"+ joinPoint.getSignature().getName());
		return object;
	}
}
