package test;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class Test implements ActionHandler {

	public void execute(ExecutionContext ec) throws Exception {
		// TODO Auto-generated method stub
		ContextInstance ci = ec.getContextInstance();
		String nodeName = ec.getToken().getNode().getName();
		String goBack = (String)ci.getVariable("goBack");
		if(goBack!=null&&!"".equals(goBack)){
			goBack+=";"+nodeName;
			ci.setVariable("goBack", goBack);
		}else{
			ci.setVariable("goBack", nodeName);
		}
		
		
	}
	
	
	
//	public static void main(String[] args) {
//
//		JbpmContext jc = JbpmConfiguration.getInstance().createJbpmContext();
//		//JbpmContext jc = JbpmContextFactory.getJbpmContext();
////		ProcessDefinition pd = jc.getGraphSession()
////				.findLatestProcessDefinition("forktest");
//		ProcessDefinition pd = jc.getGraphSession().loadProcessDefinition(117);
//		ProcessInstance pi = pd.createProcessInstance();
//		System.out.println("新流程开始成功！！");
//		Token root = pi.getRootToken();		
//		ContextInstance ci = pi.getContextInstance();
//		//把部门参数加入流程当中
//		ci.createVariable("departments", "dept1,dept2,dept3");
//		//System.out.println("rootToken:" + root.getChild("2").getNode());
//		//System.out.println("rootToken:" + root.getChild("control").getNode());
//		root.signal();
//		System.out.println("rootToken:" + root.getChild("control").getNode());
//		TaskMgmtInstance tmi = pi.getTaskMgmtInstance();
//		System.out.println("rootToken:" +  root.getChild("2").getNode());
//		Collection c = tmi.getTaskInstances();
//		System.out.println(c.size());
//		Iterator iter = c.iterator();
//		System.out.println("rootToken:" + root.getNode());
//		priintTokenChhilds(root);
//		while (iter.hasNext()) {
//			TaskInstance ti = (TaskInstance) iter.next();
//			Token token = ti.getToken();
//			System.out.println(token.getName());
//			String dept = (String) ci.getVariable("dept", token);
//			ti.end();
//			System.out.println("部门" + dept + "的task'" + ti.getName() + "'结束");
//			System.out.println("rootToken:" + root.getNode());
//			priintTokenChhilds(root);
//		}
//		jc.close();
//	}
//
//	public static void priintTokenChhilds(Token token) {
//		System.out.println(token.getNode());
//		System.out.println(token.getChild("2").getNode());
//		Map childs = token.getChildren();
//		Iterator it = childs.keySet().iterator();
//		while (it.hasNext()) {
//			Token child = (Token) childs.get(it.next());
//			System.out.println(child.getFullName() + ":" + child.getNode());
//		}
//	}

}
