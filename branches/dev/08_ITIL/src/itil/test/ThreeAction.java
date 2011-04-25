package test;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class ThreeAction implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		System.out.println("task-node3"+executionContext.getNode().getName());

	}

}
