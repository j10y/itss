package com.xpsoft.core.jbpm.pv;

import java.io.Serializable;
import java.util.LinkedList;

public class ProcessForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String activityName;
	/* 26 */private LinkedList<ParamInfo> params = new LinkedList();

	public String getActivityName() {
		/* 33 */return this.activityName;
	}

	public void setActivityName(String activityName) {
		/* 37 */this.activityName = activityName;
	}

	public LinkedList<ParamInfo> getParams() {
		/* 41 */return this.params;
	}

	public void setParams(LinkedList<ParamInfo> params) {
		/* 45 */this.params = params;
	}
}
