package test;

import java.util.Iterator;
import java.util.Map;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

public class Controler implements ActionHandler {

	public void execute(ExecutionContext arg0) throws Exception {
		ProcessInstance pi=arg0.getProcessInstance();
		ContextInstance ci=pi.getContextInstance();
		String deparments=(String) ci.getVariable("departments");
		String[] deptArry=deparments.split(",");
		Token root=pi.getRootToken();	
		System.out.println(root.getNode());
		//默认的token
		Token degaultToken=root.getChild("2");
		//Token controlToken = root.getChild("control");
//		Map map = root.getChildren();
//		Iterator ite = map.keySet().iterator();
//		while(ite.hasNext()){
//			String key = (String)ite.next();
//			System.out.println(key);
//			System.out.println(map.get(key));
//		}
//		System.out.println(root.getChildren().size());
		System.out.println(degaultToken.getNode()+degaultToken.getFullName()+root.getChild("2").getNode());
		
		//System.out.println(controlToken.getNode()+controlToken.getFullName());
		ci.createVariable("dept", deptArry[0], degaultToken);
		//其他部门分别创建对应的token,并分别对变量dept赋值
		for(int i=1;i<deptArry.length;i++){
			Token newToken=new Token(root,deptArry[i]);
			
			ci.createVariable("dept", deptArry[i], newToken);
			newToken.signal();
			//newToken.signal("control");
			System.out.println(newToken.getNode());
			System.out.println(root.getChild("2").getNode());
		}
      	}

}
