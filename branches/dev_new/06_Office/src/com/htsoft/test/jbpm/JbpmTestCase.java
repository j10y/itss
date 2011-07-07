package com.htsoft.test.jbpm;

import com.htsoft.core.util.XmlUtil;
import com.htsoft.oa.dao.flow.TaskDao;
import com.htsoft.oa.model.flow.ProDefinition;
import com.htsoft.oa.service.flow.JbpmService;
import com.htsoft.oa.service.flow.ProDefinitionService;
import com.htsoft.test.BaseTestCase;
import java.io.PrintStream;
import java.util.List;
import javax.annotation.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.junit.Test;

public class JbpmTestCase extends BaseTestCase {

	@Resource
	private ProcessEngine processEngine;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private ExecutionService executionService;

	@Resource
	private TaskService taskService;

	@Resource
	private ProDefinitionService proDefinitionService;

	@Resource
	private JbpmService jbpmService;

	@Resource
	private TaskDao taskDao;

	public void deploy() {
		/* 59 */String id = this.repositoryService.createDeployment()
				.addResourceFromClasspath("com/htsoft/test/jbpm/test.jpdl.xml")
				.deploy();
		/* 60 */System.out.println("deployId:" + id);
	}

	public void deploySignAll() {
		/* 64 */String id = this.repositoryService
				.createDeployment()
				.addResourceFromClasspath(
						"com/htsoft/test/jbpm/signAll2.jpdl.xml").deploy();
		/* 65 */System.out.println("deployId:" + id);
	}

	public void getByTaskId() {
		/* 70 */String taskId = "68";
		/* 71 */TaskImpl task = (TaskImpl) this.taskService.getTask(taskId);
		/* 72 */EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		/* 73 */Environment env = environmentFactory.openEnvironment();
		try {
			/* 75 */ProcessDefinitionImpl pd = task.getProcessInstance()
					.getProcessDefinition();
			/* 76 */ActivityImpl activityFind = pd.findActivity("任务1");

			/* 78 */if (activityFind != null) {
				/* 79 */List list = activityFind.getOutgoingTransitions();
				/* 80 */System.out.println("size:" + list.size());
			}
		} finally {
			/* 84 */env.close();
		}
	}

	public void getExecutionId() {
		/* 93 */String exeId2 = "pd6717864949166496642.43.连线2";

		/* 99 */TaskImpl task = (TaskImpl) this.taskService.getTask("58");
		/* 100 */String deployId = "";
		/* 101 */EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		/* 102 */Environment env = environmentFactory.openEnvironment();
		try {
			/* 105 */deployId = task.getProcessInstance()
					.getProcessDefinition().getDeploymentId();

			/* 107 */System.out.println("dpId:" + deployId);
		} finally {
			/* 111 */env.close();
		}

		/* 114 */ProDefinition pd = this.proDefinitionService
				.getByDeployId(deployId);

		/* 116 */System.out.println("taskName:" + task.getActivityName());

		/* 118 */System.out.println("pdxml:" + pd.getDefXml());
		/* 119 */Document doc = XmlUtil.stringToDocument(pd.getDefXml());

		/* 121 */Element rootEl = doc.getRootElement();

		/* 123 */Element taskEl = (Element) rootEl
				.selectSingleNode("/process/task[@name='"
						+ task.getActivityName() + "']");

		/* 125 */if (taskEl != null) {
			/* 126 */List trans = taskEl.selectNodes("./transition");
			/* 127 */for (int i = 0; i < trans.size(); i++) {
				/* 128 */Element el = (Element) trans.get(i);
				/* 129 */System.out.println("transiton name:"
						+ el.attributeValue("name"));
			}
		}
	}

	public void test() {
	}

	public void completTask() {
		/* 217 */String taskId = "122";

		/* 226 */System.out.println("before instance:");

		/* 228 */this.taskService.completeTask(taskId);
		/* 229 */System.out.println("complet task");
	}

	public void signAll() {
		/* 235 */String deployId = "69";

		/* 241 */String piId = "signAll2.74";

		/* 252 */List<Task> userTaskList = this.taskService
				.findPersonalTasks("3");

		/* 254 */for (Task tk : userTaskList) {
			/* 256 */System.out.println("userid 1:  task:" + tk.getName()
					+ " taskId:" + tk.getId());
		}

		/* 266 */List<Task> userTaskList6 = this.taskService
				.findPersonalTasks("6");

		/* 268 */for (Task tk : userTaskList6) {
			/* 270 */System.out.println("userid 6:  task:" + tk.getName()
					+ " taskId:" + tk.getId());

			/* 272 */TaskImpl tm = (TaskImpl) this.taskService.getTask(tk
					.getId());

			/* 274 */if (tm != null)
				/* 275 */System.out.println("sub task:" + tm.getName());
			else {
				/* 277 */System.out.println(" task is null");
			}
			/* 279 */List transitions = this.jbpmService
					.getTransitionsByTaskId(tm.getId());

			/* 281 */System.out.println("size:" + transitions.size());
		}
	}

	public void unDeploy() {
		/* 292 */System.out.println("delete---------------");
		/* 293 */this.processEngine.getRepositoryService()
				.deleteDeployment("2");
		/* 294 */System.out.println("delete- success--------------");
	}

	public void topAssign() {
	}

	@Test
	public void testGetValue() {
		/* 307 */List list = this.taskDao.getByActivityNameVarKeyLongVal(
				"发文分发", "archives.archivesId", Long.valueOf(12L));

		/* 309 */System.out.println("size:" + list.size());
	}
}