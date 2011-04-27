package com.zsgj.info.framework.exception;

import com.zsgj.info.framework.exception.base.BaseException;

/**
 * Ӧ�ü��쳣����action�и���service�ķ���ֵ����ҳ�����ʾ��Ϣ��ת��
 * �磺�û���½ʱ�����񷽷����ص�userInfΪnull�������׳����쳣��
 * <p><pre>
 * if(userInf==null){
 *     throw new ApplicationException("�Բ����û��������ܴ��󣬵�½ʧ��!");
 * }
 * </pre></p>
 * @Class Name ApplicationException
 * @author xiaofeng
 * @Create In 2007-10-30
 */
public class ApplicationException extends BaseException
{
    /**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = 2742819717404483313L;

	/**
	 * ���췽����������Ϣ��Ϣ��������쳣ʵ����
	 * @param message
	 */
	public ApplicationException(String message)
    {
        super(message);

		logger.error("application exception. \nMessage:" + message);
		logger.error("--------------------end application log----------------------------------------");
    }

    public ApplicationException()
    {
    }
    
    public ApplicationException(String message, long errorCode) {
		super(message);
		super.setErrorCode(errorCode);
		logger.error("application exception, errorCode: " + errorCode + "\nMessage:" + message);
		logger.error("--------------------end application log----------------------------------------");
	}
    
    /**
     * ���췽�����÷�������action�д��ݵĲ���errorCode����ȡ�����ļ�
     * applicationException.properties�еļ�ֵ��Ϣ����ȡ�ô����
     * ��Ӧ����Ϣ��
     * @param errorCode �����
     */
    public ApplicationException(long errorCode)
    {
        super(errorCode);
        super.setErrorCode(errorCode);
		logger.error("application exception, errorCode: " + errorCode);
		logger.error("--------------------end application log----------------------------------------");
    }

    public long getErrorCode()
    {
        return super.getErrorCode();
    }

    public String getMessageAndErrorCode()
    {
        return super.getMessageAndErrorCode();
    }

    public void setErrorCode(long errorCode)
    {
        super.setErrorCode(errorCode);
    }
}
