package com.zsgj.itil.account.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.jspsmart.upload.SmartUpload;
import com.zsgj.itil.account.entity.MobileTelephoneApply;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.account.service.AccountService;

/**
 * 批量导入帐号数据
 * @Class Name ImportAccountDataAction
 * @Author lee
 * @Create In Feb 1, 2010
 */
public class ImportAccountDataAction extends BaseDispatchAction{
	private AccountService as = (AccountService) ContextHolder.getBean("accountService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	/**
	 * 将EXCEL数据导入数据库
	 * @Methods Name importAccountDataExcel
	 * @Create In Feb 1, 2010 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward importAccountDataExcel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk"); 
		SmartUpload su = new SmartUpload();
		su.initialize(super.getServlet(), request,  response);
		String errors="";
		try {
			su.upload();			
			String uploadUrl =  "user"+ FSP + "account" + FSP + "report"+ FSP+ "tempfileforimport"+ FSP; //FSP+
			String accountType=su.getRequest().getParameter("ScImportType1");
			String isnew=su.getRequest().getParameter("isnew1");
			for (int i=0;i<su.getFiles().getCount();i++){
				//文件上传后的文件对象
				com.jspsmart.upload.File myFile = su.getFiles().getFile(i);
				
				String filePathName = myFile.getFilePathName();
				
				if(StringUtils.isNotBlank(filePathName)){
					
					String extFileName = myFile.getFileExt();
					String sSaveFileName = "account_" + System.currentTimeMillis()+"."+extFileName;
					//String folder = uploadUrl; //"upload" + FSP + "NewsFiles"+ FSP;
					//实际上传的文件路径：应用的根路径 + 文件夹路径 + 实际的文件名
					String sPathFileName =request.getRealPath(FSP)+ uploadUrl + sSaveFileName;
                    myFile.saveAs(sPathFileName);
			
					AccountService accountService = (AccountService) ContextHolder.getBean("accountService");			
					errors=errors+accountService.importAccountDateExcel(sPathFileName, accountType, isnew)+",";
				}else{
					errors="没有选择模板！";
				}
			
			
		  }
		
			
		} catch (java.lang.SecurityException e) {
			e.printStackTrace();
			request.setAttribute("success", Boolean.FALSE);
			request.getSession().setAttribute("success", Boolean.FALSE);
			throw new ApplicationException("上传文件发生异常，请联系管理员");
		}catch (ServiceException e) {
			e.printStackTrace();
			throw new ApplicationException("-------------");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("success", Boolean.FALSE);
			request.getSession().setAttribute("success", Boolean.FALSE);
			throw new ApplicationException("上传文件发生异常，请联系管理员");
		}
		String[] er=errors.split(",");
		request.setAttribute("errors", er);	
	//	return HttpUtil.redirect("userMainTable.do?methodCall=toForm&id="+smt.getId()); 
		return mapping.findForward("importAccount");
	}
	public ActionForward findMyAccount(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo curUser = UserContext.getUserInfo();
		List<PersonFormalAccount> myAccounts=as.findAllPersonAccount(curUser);
		List<SpecialAccount> specialAccounts=as.findAllSpecailAccount(curUser);
		int accountNumber=specialAccounts.size();
		
		request.setAttribute("myAccounts", myAccounts);
		request.setAttribute("userInfo", curUser);
		request.setAttribute("specialAccounts", specialAccounts);
		request.setAttribute("accountNumber", accountNumber);

		return mapping.findForward("myAccount");
	}
	
}
