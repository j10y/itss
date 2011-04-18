package com.digitalchina.info.framework.workflow.base;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.workflow.WorkflowConstants;

public class JbpmUtils {

	/**
	 * @Methods Name main
	 * @Create In Jun 20, 2008 By yang
	 * @param args 
	 * @ReturnType void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 获得当前登录用户的ActorId
	 * @Methods Name getLoginActorId
	 * @Create In Jun 20, 2008 By yang
	 * @return 
	 * @ReturnType String
	 * 不用
	 */	
	public static String getLoginActorId1() {
		return "defaultUser";
//		return UserContext.getUserInfo().getItcode();
	}
	/**
	 * 获得流程创建者名称
	 * @Methods Name getCreator
	 * @Create In Jul 30, 2008 By yang
	 * @return 
	 * @ReturnType String
	 */
	public static String getCreatorId() {
		return WorkflowConstants.PROCESS_CREATOR_ID;
	}
}
