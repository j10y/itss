package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * timer实体，用来标识一个节点的有效期
 * @Class Name ConfigUnitTimer
 * @Author guangsa
 * @Create In Mar 4, 2009
 */
public class ConfigUnitTimer extends BaseObject{
	private Long id;
	private String processName;
	private String nodeName;
	private int effectTime;
	private String inverseNodeName;
	private String timerPath;
	private int flag;//标识是否有timer
	private Long virtualProcessId;
	private Long nodeId;
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + effectTime;
		result = prime * result + flag;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((inverseNodeName == null) ? 0 : inverseNodeName.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result
				+ ((processName == null) ? 0 : processName.hashCode());
		result = prime * result
				+ ((timerPath == null) ? 0 : timerPath.hashCode());
		result = prime
				* result
				+ ((virtualProcessId == null) ? 0 : virtualProcessId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ConfigUnitTimer other = (ConfigUnitTimer) obj;
		if (effectTime != other.effectTime)
			return false;
		if (flag != other.flag)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inverseNodeName == null) {
			if (other.inverseNodeName != null)
				return false;
		} else if (!inverseNodeName.equals(other.inverseNodeName))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (processName == null) {
			if (other.processName != null)
				return false;
		} else if (!processName.equals(other.processName))
			return false;
		if (timerPath == null) {
			if (other.timerPath != null)
				return false;
		} else if (!timerPath.equals(other.timerPath))
			return false;
		if (virtualProcessId == null) {
			if (other.virtualProcessId != null)
				return false;
		} else if (!virtualProcessId.equals(other.virtualProcessId))
			return false;
		return true;
	}
	public Long getVirtualProcessId() {
		return virtualProcessId;
	}
	public void setVirtualProcessId(Long virtualProcessId) {
		this.virtualProcessId = virtualProcessId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(int effectTime) {
		this.effectTime = effectTime;
	}
	public String getInverseNodeName() {
		return inverseNodeName;
	}
	public void setInverseNodeName(String inverseNodeName) {
		this.inverseNodeName = inverseNodeName;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getTimerPath() {
		return timerPath;
	}
	public void setTimerPath(String timerPath) {
		this.timerPath = timerPath;
	}

}
