package com.digitalchina.itil.config.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.event.entity.Event;

public class CIBaseType extends BaseObject {
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
