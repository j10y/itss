package com.zsgj.itil.knowledge.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.ServiceItem;

/**
 * �������
 * @Class Name Knowledge
 * @Author sa
 * @Create In Mar 31, 2009
 */
public class Knowledge extends BaseObject {
	public static final Integer STATUS_DRAFT = 0;// �ݸ壺1.���Ǵ��¼����������������δͨ����;2.�ݴ�δ�ύ��
	public static final Integer STATUS_FINISHED = 1;// ����ͨ��������ʽ��
	public static final Integer STATUS_APPROVING = 2;// ������
	// 2010-5-11 add by huzh for ����״̬ begin
	public static final Integer STATUS_TIMEOUT = 3;// ���ڣ������ԭ�������
	public static final Integer STATUS_CHANGEDRAFT= 4;// ����ݸ壺1.���δͨ����; 2.����ݴ�
	public static final Integer STATUS_RENEW= 5;// �����䣺���¼����������������δͨ����
	// 2010-5-11 add by huzh for ����״̬ end
	
	private Long id;
	
	private ServiceItem serviceItem; //������
	private KnowProblemType knowProblemType; //��������
	
	private String summary; //����������ƣ���������
	
	private String reason; //ԭ��
	private String resolvent; //�������
	
	private Integer userViewFlag; //�Ƿ����пɼ�
	
	private Long useTime=0L; //ʹ�ô���
	
	private Long readTimes=0L; //�Ķ�����
	
	private String description; //����������
	
	private UserInfo createUser;
	private  Date createDate;
	
	private Integer status;
	
	private String knowledgeCisn;
	// 2010-5-11 add by huzh for ����֪ʶ��� begin
	private Knowledge oldKnowledge;//ԭ������������ڽ���������
	// 2010-5-11 add by huzh for ����֪ʶ��� end
	
	public String getKnowledgeCisn() {
		return knowledgeCisn;
	}
	public void setKnowledgeCisn(String knowledgeCisn) {
		this.knowledgeCisn = knowledgeCisn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @Return the UserInfo createUser
	 */
	public UserInfo getCreateUser() {
		return createUser;
	}
	/**
	 * @Param UserInfo createUser to set
	 */
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	/**
	 * @Return the Date createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @Param Date createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	//private String remark; //������Ϣ
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public KnowProblemType getKnowProblemType() {
		return knowProblemType;
	}
	public void setKnowProblemType(KnowProblemType knowProblemType) {
		this.knowProblemType = knowProblemType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResolvent() {
		return resolvent;
	}
	public void setResolvent(String resolvent) {
		this.resolvent = resolvent;
	}
	public Integer getUserViewFlag() {
		return userViewFlag;
	}
	public void setUserViewFlag(Integer userViewFlag) {
		this.userViewFlag = userViewFlag;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	public Long getUseTime() {
		return useTime;
	}
	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}
	public Long getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(Long readTimes) {
		this.readTimes = readTimes;
	}
	public Knowledge getOldKnowledge() {
		return oldKnowledge;
	}
	public void setOldKnowledge(Knowledge oldKnowledge) {
		this.oldKnowledge = oldKnowledge;
	}
	
	
	//֪ʶ����ʱֻ�����¼������������ѯ֪ʶ���������������ֻ����
	//���������Ҫ�����¼�������
	
	//��ͬ���ĵ��࣬�¼������۵ģ������ֹ�¼���ѡ������Ŀ��
	//һ����������ܼ��Ǻ�ͬ��
	
	//�������¼�������Ľ��������û������ֻ�и���
	//Ҳ�����Լ�ֱ����֪ʶ������¼���
	
	//����ֻѡ�������ͣ�û��ѡ��������������������֪ʶ
	
	
	//����֪ʶ����������Ŀ	
}
