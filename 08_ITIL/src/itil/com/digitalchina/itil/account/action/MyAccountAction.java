package com.zsgj.itil.account.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.itil.account.entity.MobileTelephoneApply;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.account.service.AccountService;


/**
 * �ҵ��ʺ�Aciton
 * 
 * @Class Name MyAccountAction
 * @Author gaowen
 * @Create In 11 18, 2009
 */
public class MyAccountAction extends BaseDispatchAction {
	private AccountService accountService = (AccountService) getBean("accountService");
	private static Service baseBervice = (Service) ContextHolder.getBean("baseService");
	public ActionForward query(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk"); 
		UserInfo curUser = UserContext.getUserInfo();
		String userInfo=request.getParameter("userInfo");
		String flag=request.getParameter("flag");
		if(userInfo!=null){
			curUser=(UserInfo) baseBervice.find(UserInfo.class, userInfo);
		}
		
		List<PersonFormalAccount> myAccounts=accountService.findAllPersonAccount(curUser);
		List<SpecialAccount> specialAccounts=accountService.findAllSpecailAccount(curUser);
		//add by liuying at 20100115 for �ҵ��˺��嵥����ʾ�ֻ��ʺ�  start
		MobileTelephoneApply mobileTelephone=accountService.findMobileTelephone("�ֻ�", curUser);
		request.getSession().setAttribute("mobileTelephone", mobileTelephone);
		//add by liuying at 20100115 for �ҵ��˺��嵥����ʾ�ֻ��ʺ�  end
		
		int accountNumber=specialAccounts.size();
		
		request.getSession().setAttribute("myAccounts", myAccounts);
		request.getSession().setAttribute("userInfo", curUser);
		request.getSession().setAttribute("specialAccounts", specialAccounts);
		request.getSession().setAttribute("accountNumber", accountNumber);
		
		if(flag!=null&&!"".equals(flag)){
			return mapping.findForward("dcit");
		}
		return mapping.findForward("success");
	}
	
	

}