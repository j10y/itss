package com.zsgj.itil.config.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.event.entity.Event;

public class CIBaseData extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -23408000769556754L;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	public String getName() {
		return super.getName();
	}
	
	public int getFlag(){
		if(this instanceof ConfigItem){
			return Event.TYPE_CID;
		}else{
			return Event.TYPE_SCID;
		}
	}
}
