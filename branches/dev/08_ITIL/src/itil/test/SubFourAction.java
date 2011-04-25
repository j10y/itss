package test;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class SubFourAction implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		System.out.println("task-node4"+executionContext.getNode().getName());

	}

}
