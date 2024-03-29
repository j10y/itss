package com.xpsoft.oa.action.customer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.gson.Gson;
import com.xpsoft.core.engine.MailEngine;
import com.xpsoft.core.model.Ftp;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.customer.Customer;
import com.xpsoft.oa.model.customer.CustomerMail;
import com.xpsoft.oa.model.customer.Provider;
import com.xpsoft.oa.model.system.Company;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.customer.CustomerService;
import com.xpsoft.oa.service.customer.ProviderService;
import com.xpsoft.oa.service.system.CompanyService;
import com.xpsoft.oa.service.system.FileAttachService;

public class MutilMailAction extends BaseAction {

	@Resource
	private MailEngine mailEngine;

	@Resource
	private ProviderService providerService;

	@Resource
	private FileAttachService fileAttachService;

	@Resource
	private CustomerService customerService;

	@Resource
	private CompanyService companyService;
	private CustomerMail customerMail;

	public CustomerMail getCustomerMail() {
		return this.customerMail;
	}

	public void setCustomerMail(CustomerMail customerMail) {
		this.customerMail = customerMail;
	}

	public String send() throws IOException {
		Short type = this.customerMail.getType();
		String ids = this.customerMail.getIds();
		String files = this.customerMail.getFiles();
		List atFiles = new ArrayList();
		List fileName = new ArrayList();
		//add by jack for FTP support at 2011-9-21 begin
		boolean isFtp = new Boolean(String.valueOf(AppUtil.getSysConfig().get("isFtp")));
		//add by jack for FTP support at 2011-9-21 emd
		if (StringUtils.isNotEmpty(files)) {
			String[] fIds = files.split(",");
			for (int i = 0; i < fIds.length; i++) {
				FileAttach fileAttach = (FileAttach) this.fileAttachService.get(new Long(fIds[i]));
				//modify by jack for FTP support at 2011-9-21 begin
				if(!isFtp){
					String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix"));
					
					String filePath = fileAttach.getFilePath().substring(fileAttach.getFilePath().indexOf(defaultProfix));
					File file = new File(getSession().getServletContext().getRealPath(filePath));
					fileName.add(fileAttach.getFileName());
					atFiles.add(file);
				}else{
					Ftp ftp = new Ftp(1, "fileUpload", String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.host")),
							new Integer(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.port"))), "", "");
					ftp.setUsername(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.user")));
					ftp.setPassword(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.passwd")));
					ftp.setPath("");
					
					String fileP = fileAttach.getFilePath();
					fileP = fileP.substring(fileP.indexOf(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.sysprofix"))));
					File file = ftp.retrieve(fileP);
					
					fileName.add(fileAttach.getFileName());
					atFiles.add(file);
				}
				//modify by jack for FTP support at 2011-9-21 end
			}
		}
		String[] id = ids.split(",");
		List toss = new ArrayList();
		if (type.shortValue() == 0) {
			for (int i = 0; i < id.length; i++) {
				Customer customer = (Customer) this.customerService
						.get(new Long(id[i]));
				toss.add(customer.getEmail());
			}
		}
		if (type.shortValue() == 1) {
			for (int i = 0; i < id.length; i++) {
				Provider provider = (Provider) this.providerService.get(new Long(id[i]));
				toss.add(provider.getEmail());
			}
		}
		String from = null;
		String cc = null;
		String htmlMsgContent = this.customerMail.getMailContent();
		String subject = this.customerMail.getSubject();
		String[] st = new String[0];
		String[] attachedFileNames = (String[]) fileName.toArray(st);
		File[] f = new File[0];
		File[] attachedFiles = (File[]) atFiles.toArray(f);
		String replyTo = null;
		String[] tos = (String[]) toss.toArray(st);
		if (tos.length > 0) {
			Map configs = AppUtil.getSysConfig();
			if ((StringUtils.isNotEmpty((String) configs.get("host")))
					&& (StringUtils
							.isNotEmpty((String) configs.get("username")))
					&& (StringUtils
							.isNotEmpty((String) configs.get("password")))
					&& (StringUtils.isNotEmpty((String) configs.get("from")))) {
				this.mailEngine.setFrom((String) configs.get("from"));
				this.mailEngine.sendMimeMessage(from, tos, cc, replyTo,
						subject, htmlMsgContent, attachedFileNames,
						attachedFiles, false);
				setJsonString("{success:true}");
			} else {
				setJsonString("{success:false,message:'未配置好邮箱配置!'}");
			}
		} else {
			setJsonString("{success:false}");
		}
		return "success";
	}

	public String loadVm() {
		VelocityEngine velocityEngine = (VelocityEngine) AppUtil.getBean("velocityEngine");
		String templateLocation = "mail/sendMsg.vm";
		Map model = new HashMap();
		model.put("appUser", ContextUtil.getCurrentUser());
		List list = this.companyService.findCompany();
		if (list.size() > 0) {
			Company company = (Company) list.get(0);
			if (company != null) {
				model.put("company", company);
			}
			String pageHtml = VelocityEngineUtils
					.mergeTemplateIntoString(velocityEngine, templateLocation,
							model);
			Gson gson = new Gson();
			setJsonString("{success:true,data:"
					+ gson.toJson(pageHtml) + "}");
		} else {
			setJsonString("{success:false,message:'你的公司信息还不完整！请填写好公司信息!'}");
		}
		return "success";
	}
}
