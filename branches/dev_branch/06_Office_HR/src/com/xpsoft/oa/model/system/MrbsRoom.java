package com.xpsoft.oa.model.system;

import com.xpsoft.core.model.BaseModel;

public class MrbsRoom extends BaseModel{
	public MrbsRoom() {
		super();
	}
	public MrbsRoom(Long id) {
		super();
		this.id = id;
	}
	private Long id;
	private MrbsArea area;
	private String roomName;
	private String description;
	private Integer capacity;
	private String roomAdminEmail;
	private String status;
	private String sortIndex;
	private String virtualMap;
	private Integer flag;
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MrbsArea getArea() {
		return area;
	}
	public void setArea(MrbsArea area) {
		this.area = area;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public String getRoomAdminEmail() {
		return roomAdminEmail;
	}
	public void setRoomAdminEmail(String roomAdminEmail) {
		this.roomAdminEmail = roomAdminEmail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}
	public String getVirtualMap() {
		return virtualMap;
	}
	public void setVirtualMap(String virtualMap) {
		this.virtualMap = virtualMap;
	}
}
