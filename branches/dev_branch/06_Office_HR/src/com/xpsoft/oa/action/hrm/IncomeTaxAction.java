package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.IncomeTax;
import com.xpsoft.oa.service.hrm.IncomeTaxService;

public class IncomeTaxAction extends BaseAction {
	private IncomeTax incomeTax;

	@Resource
	private IncomeTaxService incomeTaxService;

	public IncomeTax getIncomeTax() {
		/* 28 */return this.incomeTax;
	}

	public void setIncomeTax(IncomeTax incomeTax) {
		/* 32 */this.incomeTax = incomeTax;
	}

	public String check() {
		/* 40 */List list = this.incomeTaxService.findIncomeTax();
		/* 41 */if (list.size() > 0)
			/* 42 */setJsonString("{success:true}");
		else {
			/* 44 */setJsonString("{success:false}");
		}
		/* 46 */return "success";
	}

	public String list() {
		/* 50 */List list = this.incomeTaxService.findIncomeTax();
		/* 51 */if (list.size() > 0) {
			/* 52 */this.incomeTax = ((IncomeTax) list.get(0));
			/* 53 */Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
					.create();
			/* 54 */StringBuffer cf = new StringBuffer("{success:true,result:[");
			/* 55 */cf.append(gson.toJson(this.incomeTax));
			/* 56 */cf.append("]}");
			/* 57 */setJsonString(cf.toString());
		} else {
			/* 59 */setJsonString("{success:false,message:'还没有填写个人所得税参数信息'}");
			/* 60 */return "success";
		}
		/* 62 */return "success";
	}

	public String add() {
		this.incomeTax.setPublishDate(new Date());
		this.incomeTax.setPublishPerson(ContextUtil.getCurrentUser());
		this.incomeTaxService.save(this.incomeTax);
		setJsonString("{success:true,itId:'"+this.incomeTax.getItId()+"'}");
		return "success";
	}
}