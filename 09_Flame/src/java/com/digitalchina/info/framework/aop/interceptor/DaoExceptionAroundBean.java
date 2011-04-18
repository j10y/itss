package com.digitalchina.info.framework.aop.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import com.digitalchina.info.framework.exception.DaoException;

/**
 * Dao�쳣�Զ���־��¼������
 * �����쳣Dao ��β��Dao������з����������أ�������ʵ�ʵ�Dao����ǰ������쳣����
 * ����������aroundDaoMethodCalls��������Dao������DataAccessException�����ȼ�¼
 * DataAccessException���쳣��Ϣ����־�ļ��� Ȼ�������׳�Ϊ�����Զ����DaoException,
 * ��DaoException��DataAccessException�����ࡣ
 * <p>�쳣�������벶��DaoException���������׳�Ϊ������쳣ServiceException��
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
			object = joinPoint.proceed(); //�����Dao����
		} catch (Throwable e) { //���Ҹ�ΪThrowable
			e.printStackTrace();
			//��¼DataAccessException
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			logger.error("--------------------begin exception log--------------------------------------");
			logger.error(className+"."+methodName+ " method occur dataAccessException: "+e.getMessage(), e); 
			logger.error("----------------------------------------------------------------");
			throw new DaoException(e.getMessage(), e); //תΪDaoException�����׳��������ȥcatch
		}
		
		logger.debug("after invoke dao method:"+ joinPoint.getSignature().getName());
		return object;
	}
}
