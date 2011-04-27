package com.zsgj.info.framework.workflow.entity;

import java.util.Date;

import com.zsgj.info.framework.workflow.info.NodeInfo;
/**
 * TaskProxy��ʾ��ĳ���ڵ�ĳ���û�ί����Щ��ɫ�û����нڵ������
 * @Class Name TaskProxy
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class TaskProxy {
	private Long id;
	private NodeInfo nodeInfo;
	private String actorId;
	private String proxy;//��ί����
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
	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
}
