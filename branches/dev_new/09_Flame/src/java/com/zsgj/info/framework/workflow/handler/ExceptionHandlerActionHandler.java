package com.zsgj.info.framework.workflow.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
@SuppressWarnings("serial")
public class ExceptionHandlerActionHandler implements ActionHandler{

	private final Log logger = LogFactory.getLog("servicelog");
	public void execute(ExecutionContext executionContext) throws Exception {
		logger.error(executionContext.getException().getMessage());
	}

}
