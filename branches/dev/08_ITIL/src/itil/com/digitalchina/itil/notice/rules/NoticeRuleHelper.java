package com.digitalchina.itil.notice.rules;

import java.util.Calendar;

import org.springframework.beans.BeanUtils;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.itil.notice.entity.NewNotice;
import com.digitalchina.itil.notice.entity.NewNoticeEvent;
import com.digitalchina.itil.notice.entity.NoticeAuditHis;

public class NoticeRuleHelper {
	
	Service service = (Service) ContextHolder.getBean("baseService");
	/**
	 * ������ʷ
	 * @Methods Name saveNoticeHis
	 * @Create In Mar 25, 2009 By Administrator
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param newNotice void
	 */
	public void saveNoticeHis(String nodeId,String nodeName,String processId,String result,String comment,NewNotice newNotice,String alterFlag){
		NoticeAuditHis  nah = new NoticeAuditHis();
		nah.setResultFlag(result);
		nah.setProcessId(Long.valueOf(processId));
		nah.setApprover(UserContext.getUserInfo());
		nah.setApproverDate(Calendar.getInstance().getTime());
		nah.setComment(comment);
		nah.setNewNotice(newNotice);
		nah.setNodeName(nodeName);
		nah.setNodeId(nodeId);
		nah.setAlterFlag(alterFlag);
		try{
			service.save(nah);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���濪ʼ��ʶ
	 * @Methods Name noticeStartFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId void
	 */
	public void noticeStartFlag(String dataId,String nodeId,String nodeName,String processId,String alterFlag){
		
		NewNotice ac=(NewNotice)service.find(NewNotice.class, dataId);
		ac.setAuditflag(NewNotice.STATUS_APPROVING);
		this.saveNoticeHis(nodeId, nodeName, processId, "Y", "", ac,alterFlag);
		service.save(ac);
		 
	}
	
	/**
	 * �Ƿ���A,B,C�๫��
	 * @Methods Name isABCFlag
	 * @Create In Aug 10, 2009 By guoxl void
	 */
	public String isABCFlag(String dataId){
		String result = "";
		NewNotice ac=(NewNotice)service.find(NewNotice.class, dataId);
		if(NewNotice.STATUS_D.equals(ac.getNewNoticeType().getFlag())){//D�๫��
			result = "N";
		}else{
			result = "Y";
		}
		return result;
	}
	/**
	 * �����ڵ����
	 * @Methods Name noticeAuditFlag
	 * @Create In Jul 7, 2010 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param alterFlag
	 * @return String
	 */
	public String noticeAuditFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){
		NewNotice ac=(NewNotice)service.find(NewNotice.class, dataId);
		this.saveNoticeHis(nodeId, nodeName, processId, result, comment, ac,alterFlag);
		return result; 
	}
	/**
	 * �Ƿ�BC�๫��
	 * @Methods Name isBCFlag
	 * @Create In Aug 10, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String isBCFlag(String dataId){
		String result = "";
		NewNotice ac=(NewNotice)service.find(NewNotice.class, dataId);
		if((NewNotice.STATUS_B.equals(ac.getNewNoticeType().getFlag())||(NewNotice.STATUS_C.equals(ac.getNewNoticeType().getFlag())))){
			result = "Y";
		}else{
			result = "N";
		}
		return result;
	}
	/**
	 * ����
	 * @Methods Name deployFlag
	 * @Create In Aug 10, 2009 By guoxl
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param alterFlag
	 * @return String
	 */
	public String deployFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){
		
		NewNotice ac=(NewNotice)service.find(NewNotice.class, dataId);
		
		if("Y".equals(result)){
			ac.setAuditflag(NewNotice.STATUS_FINISHED);
		}else{
			ac.setAuditflag(NewNotice.STATUS_APPROVING);
		}
		this.saveNoticeHis(nodeId, nodeName, processId, result, comment, ac,alterFlag);
		service.save(ac);
		return result;
	}
	
	
	/**
	 * �ܾ����ƻص��ύ��
	 * @Methods Name rollBackFlag
	 * @Create In Aug 10, 2009 By guoxl
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param alterFlag
	 * @return String
	 */
	public String rollBackFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){
		NewNotice ac=(NewNotice)service.find(NewNotice.class, dataId);
		
		if("Y".equals(result)){
			ac.setAuditflag(NewNotice.STATUS_APPROVING);
	
		}else{
			ac.setAuditflag(NewNotice.STATUS_DRAFT);
		}
		this.saveNoticeHis(nodeId, nodeName, processId, result, comment, ac,alterFlag);
		service.save(ac);
		return result;
	}

	/**
	 * �������
	 * @Methods Name deployAlterFlag
	 * @Create In Aug 19, 2009 By guoxl
	 * @param dataId
	 * @param oldDataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param alterFlag
	 * @return String
	 */
	public String deployAlterFlag(String dataId,String oldDataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){
		
		NewNotice newNotice=(NewNotice)service.find(NewNotice.class, dataId);//�µļ�¼
		NewNotice oldNotice=(NewNotice)service.find(NewNotice.class, oldDataId);//ԭ���ļ�¼������ͨ����Ҫ����
		newNotice.setAuditflag(NewNotice.STATUS_FINISHED);
		oldNotice.setAuditflag(NewNotice.STATUS_DELETE);
		service.save(newNotice);
		service.save(oldNotice);
		this.saveNoticeHis(nodeId, nodeName, processId, result, comment, newNotice,alterFlag);
		return result; 
	}
}
