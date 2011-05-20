package com.zsgj.itil.config.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 每套关系只存储一份关系数据的关系图节点实体
 * @Class Name CIRelationShipPicItem
 * @Author sa
 * @Create In 2009-2-8
 * @deprecated deprecated by duxh in 09-11-19
 */
public class CIRelationShipPicNode extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 7475895537175045288L;

	private Long id;

	private CIRelationShipPic ciRelationShipPic;
	 
	private CIRelationShip ciRelationShip;
	
	private Integer deleteFlag;

	public Double getAttachQuotiety() {
		return ciRelationShip.getAttachQuotiety();
	}


	public void setId(Long id) {
		this.id = id;
	}


	

	

	

	public CIRelationShipPic getCiRelationShipPic() {
		return ciRelationShipPic;
	}

	public void setCiRelationShipPic(CIRelationShipPic ciRelationShipPic) {
		this.ciRelationShipPic = ciRelationShipPic;
	}

	public CIRelationShip getCiRelationShip() {
		return ciRelationShip;
	}

	public void setCiRelationShip(CIRelationShip ciRelationShip) {
		this.ciRelationShip = ciRelationShip;
	}

	public CIRelationShipGrade getRelationShipGrade() {
		return ciRelationShip.getRelationShipGrade();
	}

	

	public CIRelationShipType getRelationShipType() {
		return ciRelationShip.getRelationShipType();
	}


	public Long getId() {
		return id;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


	
}
