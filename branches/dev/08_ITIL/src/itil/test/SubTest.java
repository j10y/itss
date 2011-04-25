package test;

import java.util.Iterator;
import java.util.Map;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

public class SubTest {
	
	public static void main(String[] args){
		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
		ProcessDefinition pd = jbpmContext.getGraphSession().findLatestProcessDefinition("forkProcessState");
		ProcessDefinition subPd = jbpmContext.getGraphSession().findLatestProcessDefinition("processTest1");
		ProcessInstance subPi = subPd.createProcessInstance();
		ProcessInstance pi = pd.createProcessInstance();
		Token rootToken = pi.getRootToken();
		System.out.println(rootToken.getNode()+"====="+rootToken.getFullName());
		
		
		
		System.out.println("新流程开始");
		//ProcessInstance subPi = rootToken.createSubProcessInstance(subPd);		
		//rootToken.setSubProcessInstance(subPi);
		//pi.signal();
		rootToken.signal();
		System.out.println(rootToken.getNode()+"====="+rootToken.getFullName());
//		Map tokenChild = rootToken.getChildren();
//		Iterator ite = tokenChild.keySet().iterator();
//		while(ite.hasNext()){
//			String key = (String)ite.next();
//			System.out.println(key+tokenChild.get(key));
//		}
		
		
//		ProcessInstance instance = rootToken.getSubProcessInstance();
//		Token subRootToken = instance.getRootToken();
//		System.out.println(subRootToken.getNode()+"===="+subRootToken.getFullName());
//		
		System.out.println("流程结束");
		jbpmContext.save(pi);
	}

}
