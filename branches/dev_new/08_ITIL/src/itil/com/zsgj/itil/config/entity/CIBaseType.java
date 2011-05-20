package com.zsgj.itil.config.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.event.entity.Event;

public class CIBaseType extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -3128612763297343854L;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	public String getName() {
		return super.getName();
	}
	
	public int getFlag(){
		if(this instanceof ConfigItemType){
			return Event.TYPE_CID;
		}else{
			return Event.TYPE_SCID;
		}
	}
	
}
