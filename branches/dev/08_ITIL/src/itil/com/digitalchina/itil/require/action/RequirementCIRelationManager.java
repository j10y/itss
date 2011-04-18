package com.digitalchina.itil.require.action;

import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.digitalchina.itil.config.entity.CIRelationShip;

public class RequirementCIRelationManager extends BaseAction{
	/*
	 remove by duxh in 2010-02-22 -----begin-----注掉无用方法所产生的编译错误
	 
	 public String  getKernel(String id){
		CIRelationShip ciRelationShip = (CIRelationShip) super.getService().find(CIRelationShip.class,id,true);
		Integer typeFlag= ciRelationShip.getTypeFlag();
		if(Integer.valueOf(1).equals(typeFlag)){
			return "CONFIG";
		}
		return "SERVICE";
	}
	remove by duxh in 2010-02-22 -----end-----
	*/
		
}
