package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ���⴦����̱�
 * @Class Name EventHandleLog
 * @Author sa
 * @Create In 2009-3-4
 */
@SuppressWarnings("serial")
public class ProblemHandleLog extends BaseObject{
	private Long id;
	private Problem problem;//����
	private UserInfo handleUser;//������
	private String handleLog;//�����¼
	private Date handleDate;//����ʱ��
	private String files; //���и���
	private ProblemProgress progress;//�������
	
	public Date getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}
	public String getHandleLog() {
		return handleLog;
	}
	public void setHandleLog(String handleLog) {
		this.handleLog = handleLog;
	}
	public UserInfo getHandleUser() {
		return handleUser;
	}
	public void setHandleUser(UserInfo handleUser) {
		this.handleUser = handleUser;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public ProblemProgress getProgress() {
		return progress;
	}
	public void setProgress(ProblemProgress progress) {
		this.progress = progress;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	
}
