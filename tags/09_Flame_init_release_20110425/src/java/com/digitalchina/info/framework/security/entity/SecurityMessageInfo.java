/**
 * @Probject Name: 10_InfoFramework
 * @Path: com.digitalchina.info.framework.security.entitySecurityMessageInfo.java
 * @Create By 张鹏
 * @Create In Jan 4, 2009 2:43:59 PM
 * TODO
 */
package com.digitalchina.info.framework.security.entity;

/**
 * @Class Name SecurityMessageInfo
 * @Author 张鹏
 * @Create In Jan 4, 2009
 */
public class SecurityMessageInfo {
	String title = "安全验证错误";
	String message = "";
	/**
	 * @Return the String title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @Param String title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @Return the String message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @Param String message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SecurityMessageInfo other = (SecurityMessageInfo) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}
