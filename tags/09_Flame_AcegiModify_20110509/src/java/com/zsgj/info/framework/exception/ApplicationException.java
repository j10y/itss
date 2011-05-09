package com.zsgj.info.framework.exception;

import com.zsgj.info.framework.exception.base.BaseException;

/**
 * 应用级异常，即action中根据service的返回值决定页面的提示信息和转向。
 * 如：用户登陆时，服务方法返回的userInf为null，可以抛出该异常。
 * <p><pre>
 * if(userInf==null){
 *     throw new ApplicationException("对不起，用户名或秘密错误，登陆失败!");
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
	 * 构造方法，根据消息信息，构造此异常实例。
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
     * 构造方法，该方法根据action中传递的参数errorCode，读取属性文件
     * applicationException.properties中的键值信息，获取该错误号
     * 对应的消息。
     * @param errorCode 错误号
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
