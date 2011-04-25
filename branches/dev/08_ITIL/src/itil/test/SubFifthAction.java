package test;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class SubFifthAction implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		System.out.println("task-node5"+executionContext.getNode().getName());

	}

}
