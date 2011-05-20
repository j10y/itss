package com.zsgj.itil.service.util;

import org.springframework.beans.BeanUtils;


import com.zsgj.itil.service.entity.SCIColumn;
import com.zsgj.itil.service.entity.SCIDColumn;

public final class CopySCIToSCID {
	public static SCIDColumn copySCIToSCID(SCIDColumn sCIDColumn,SCIColumn sCIColumn){
		sCIColumn.setId(null);
		BeanUtils.copyProperties(sCIColumn, sCIDColumn);
		//BeanUtils..setProperty(sCIDColumn, "id", null);
		return sCIDColumn;
	}
}
