package com.digitalchina.info.framework.workflow.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.entity.UserRole;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.workflow.TaskPageModelService;
import com.digitalchina.info.framework.workflow.entity.PageModelConfigUnit;
/**
 * 作用：根据任务节点找到配置的PageModel
 * @Class Name TaskPageModelServiceImpl
 * @Author Administrator
 * @Create In Feb 25, 2009
 */
public class TaskPageModelServiceImpl extends BaseDao implements TaskPageModelService{
	
	private static Log log;
	static 
	{
		log = LogFactory.getLog(TaskPageModelServiceImpl.class);
	}
	
	public PageModel getPageModel(Task task,Map mapVar) {
		// TODO Auto-generated method stub
		
		TaskNode tn = task.getTaskNode();
		Long virtulDefinitionId=(Long)mapVar.get("VIRTUALDEFINITIONINFO_ID");
		ProcessDefinition p = tn.getProcessDefinition();
		//List<VirtualDefinitionInfo> vd=this.getObjects(VirtualDefinitionInfo.class, "realDefinitionName", p.getName());
		List<PageModelConfigUnit> list =this.getAll(PageModelConfigUnit.class);
		PageModel pageModel=null;
		UserInfo u = UserContext.getUserInfo();
		//根据登陆人得到他对应的角色
		Set set=new HashSet();
		set.addAll(u.getRoles());
//		List<UserRole> li=this.getAll(UserRole.class);
//		for(UserRole ur : li){
//			if(u.getId().equals(ur.getUserInfo().getId())){
//				set.add(ur.getRole());
//			}
//		}
		//找到某个角色对应的pageModel
		Iterator it=set.iterator();
		while(it.hasNext()){
			Role role=(Role)it.next();
			for (PageModelConfigUnit pm : list) {
				if(pm.getNodeId().equals(tn.getId())&&virtulDefinitionId.equals(pm.getProcessId())){
					if(pm.getRoleId()!=null&&pm.getRoleId()!=0&&pm.getRoleId().equals(role.getId())){
						
						pageModel=(PageModel)this.getObject(PageModel.class, pm.getPageModelId());
					}
				}
			}
		}
		//如果角色为空的话,执行默认界面
		if(pageModel==null){
			for (PageModelConfigUnit pm : list) {
				if(pm.getNodeId().equals(tn.getId())&&virtulDefinitionId.equals(pm.getProcessId())){
					if(pm.getRoleId()==null||pm.getRoleId()==0){
						if(pm.getPageModelId()!=null&&pm.getPageModelId()!=0){
							pageModel=(PageModel)this.getObject(PageModel.class, pm.getPageModelId());
						}
					}
				}
			}
		}
		
		return pageModel;
	}
	public PageModel findPageModelByVritualProcessIdAndNodeId(Long processId,
			Long nodeId) {
		PageModel pageModel = null;
		Criteria criteria = super.getCriteria(PageModelConfigUnit.class);
		criteria.add(Restrictions.eq("processId",processId));
		criteria.add(Restrictions.eq("nodeId",nodeId));
		List<PageModelConfigUnit> pageUnit = criteria.list();
		if(pageUnit.size()!=0){
			//思路就是先找没有角色的页面，即默认页面；随后再找相应角色所对应的界面
			for(PageModelConfigUnit configUnit : pageUnit){
				System.out.println(configUnit.getRoleName());
				if(configUnit.getRoleName()==null||"".equals(configUnit)||"null".equals(configUnit.getRoleName())){
					Long pageModeId = configUnit.getPageModelId();
					pageModel=(PageModel)this.getObject(PageModel.class, pageModeId);
					break;
				}
			}
			if(pageModel==null||"".equals(pageModel)){
				for(PageModelConfigUnit configUnit : pageUnit){
					Long pageModeId = configUnit.getPageModelId();
					pageModel=(PageModel)this.getObject(PageModel.class, pageModeId);
				}
			}
		}
		return pageModel;
	}
	
	

}
