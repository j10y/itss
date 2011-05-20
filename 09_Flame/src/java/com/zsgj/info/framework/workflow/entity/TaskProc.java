package com.zsgj.info.framework.workflow.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.workflow.info.NodeInfo;
/**
 * TaskProc��ʾ��ĳ���ڵ�ĳ���û�ί����Щ��ɫ�û����нڵ������
 * @Class Name TaskProc
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class TaskProc extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -4141307594802912726L;
	private Long id;
	private NodeInfo nodeInfo;
	private String actorId;
	private String proxc;//��ί����
	private Date begins;
	private Date ends;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}
	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	
	public String getProxc() {
		return proxc;
	}
	public void setProxc(String proxc) {
		this.proxc = proxc;
	}
	public Date getBegins() {
		return begins;
	}
	public void setBegins(Date begins) {
		this.begins = begins;
	}
	public Date getEnds() {
		return ends;
	}
	public void setEnds(Date ends) {
		this.ends = ends;
	}
	
}
