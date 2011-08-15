package com.xpsoft.webservice.service.login;

public interface LoginServie {
	public String Login(String userName,String passwd);
	public String getInfoCount(String userId,String passwd);
	public String findUserByUserName(String userName,String userId,String passwd);
	public String findUserByUserName(String userName,String userId,String passwd,String departId);
}
