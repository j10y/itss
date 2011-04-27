package com.zsgj.itil.workflow.rules;

import java.util.Calendar;
import java.util.List;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.itil.config.extlist.entity.SRExpendPlan;
import com.zsgj.itil.config.extlist.entity.SRIncomePlan;
import com.zsgj.itil.require.entity.BusinessAccount;
import com.zsgj.itil.require.entity.BusinessAccountApplyHis;
import com.zsgj.itil.require.entity.RealIncome;
import com.zsgj.itil.require.entity.RealPayment;
import com.zsgj.itil.require.entity.UpDatePlan;
import com.zsgj.itil.require.service.BusinessAccountService;
import com.zsgj.itil.require.service.RequireService;

/**
 * 商务结算规则文件
 * @Class Name BusinessAccountRuleHelper
 * @Author lee
 * @Create In Aug 25, 2009
 */
public class BusinessAccountRuleHelper {
	Service service = (Service) ContextHolder.getBean("baseService");
	MailSenderService ms = (MailSenderService) ContextHolder.getBean("mailSenderService");
	RequireService rs = (RequireService) ContextHolder.getBean("requireService");
	BusinessAccountService baService = (BusinessAccountService) ContextHolder.getBean("businessAccountService");
	/**
	 * 保存审批历史
	 * @Methods Name saveHis
	 * @Create In Aug 25, 2009 By lee
	 * @param dataId
	 * @param nodeId
	 * @param processId
	 * @param nodeName
	 * @param result
	 * @param comment void
	 */
	public void saveHis(String dataId, String nodeId, String processId,String nodeName,String result,UserInfo applyUser, String comment){
		BusinessAccountApplyHis bash = new BusinessAccountApplyHis();
		bash.setDataId(Long.valueOf(dataId));
		bash.setResultFlag(result);
		bash.setProcessId(Long.valueOf(processId));
		bash.setApprover(applyUser);
		bash.setApproverDate(Calendar.getInstance().getTime());
		bash.setNodeName(nodeName);
		bash.setComment(comment);
		bash.setNodeId(nodeId);
		service.save(bash);
	}
	/**
	 * 开始节点审批方法
	 * @Methods Name start
	 * @Create In Aug 25, 2009 By lee void
	 */
	public void start(String dataId, String nodeId, String nodeName, String processId){
		BusinessAccount ba = (BusinessAccount) service.find(BusinessAccount.class, dataId);
		ba.setStatus(BusinessAccount.STATUS_APPROVING);
		service.save(ba);
		UserInfo applyUser = UserContext.getUserInfo();
		this.saveHis(dataId, nodeId, processId, nodeName, "Y", applyUser, "开始");
	}
	
	/**
	 * 审批
	 * @Methods Name audit
	 * @Create In Aug 25, 2009 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public void audit(String dataId, String nodeId, String nodeName, String processId,String result,String comment){
		UserInfo applyUser = UserContext.getUserInfo();
		this.saveHis(dataId, nodeId, processId, nodeName, result, applyUser, comment);
	}
	
	/**
	 * 打回
	 * @Methods Name back
	 * @Create In Aug 25, 2009 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId void
	 */
	public void back(String dataId, String nodeId, String nodeName, String processId){
		BusinessAccount ba = (BusinessAccount) service.find(BusinessAccount.class, dataId);
		ba.setStatus(BusinessAccount.STATUS_DRAFT);
		service.save(ba);
		this.saveHis(dataId, nodeId, processId, nodeName, "Y", null, "放弃");
	}
	
	/**
	 * 结束
	 * @Methods Name end
	 * @Create In Aug 25, 2009 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId void
	 */
	public void end(String dataId, String nodeId, String nodeName, String processId){
		BusinessAccount ba = (BusinessAccount) service.find(BusinessAccount.class, dataId);
		ba.setStatus(BusinessAccount.STATUS_FINISHED);
		service.save(ba);
		List<RealIncome> incomeMoneys = baService.getRealIncomeByBaId(dataId);
		for(RealIncome money : incomeMoneys){
			SRIncomePlan plan = money.getIncomePlan();
			if(0.0==baService.getIncomeBalanceByPlanId(plan.getId().toString())){
				plan.setIsFinish(Integer.valueOf(1));
				service.save(plan);
			}
		}
		List<RealPayment> explandMoneys = baService.getRealPaymentByBaId(dataId);
		for(RealPayment money : explandMoneys){
			SRExpendPlan plan = money.getExpendPlan();
			if(0.0==baService.getExpendBalanceByPlanId(plan.getId().toString())){
				plan.setIsFinish(Integer.valueOf(1));
				service.save(plan);
			}
		}
		this.saveHis(dataId, nodeId, processId, nodeName, "Y", null, "结束");
	}
}
