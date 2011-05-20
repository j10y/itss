package com.zsgj.info.framework.workflow.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.entity.UserRole;
import com.zsgj.info.framework.workflow.TaskAssignService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.DefinitionPreAssign;
import com.zsgj.info.framework.workflow.entity.TaskPreAssign;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;
import com.zsgj.info.framework.workflow.entity.WorkflowRoleMaping;
import com.zsgj.info.framework.workflow.info.NodeInfo;

public class TaskAssignServiceImpl extends BaseDao implements TaskAssignService {
//	private static Logger log = Logger.getLogger("workflowlog");
	
	//DefinitionPreAssign���ʵ�����൱�ڸ�ÿ������ָ������,������������þ��ǽ������̵�Ԥ֧�ɣ��ѵ�ǰ���̵��������������������ڵ���Ϣ��
	//һ�����̵�������Ϣ���ŵ�DefinitionPreAssign������
	public List<DefinitionPreAssign> addDefinitionPreAssign(String definitionName, String definitionDesc,String departmentCode,List<NodeInfo> nodes) {		

		List<Department> departments = findBy(Department.class, "departCode", new Long(departmentCode));
		List<DefinitionPreAssign> dpas = findBy(DefinitionPreAssign.class, "definitionName", definitionName);//�������̵õ�����
		List<DefinitionPreAssign> definitionPreAssigns = new ArrayList();
		//�ص���Ҫ�ѽڵ���Ϣ���ظ�����,��ǰ�Ľڵ���Ϣȥ�����еıȽ�
		if(departments!=null&&!departments.isEmpty()) {
			String departmentName = departments.get(0).getDepartName();
			for(NodeInfo node :nodes) {//�õ���ǰ�ڵ����Ϣ
				boolean exist = false;
				for(DefinitionPreAssign dpai: dpas) {//��ֹ�ظ����
					if(dpai.getNodeName().equals(node.getNodeName())) {
						exist = true;
						break;
					}
				}
				if(!exist) {
					DefinitionPreAssign dpa = new DefinitionPreAssign();
					dpa.setDefinitionName(definitionName);
					dpa.setDefinitionDesc(definitionDesc);
					dpa.setDepartmentCode(departmentCode);
					dpa.setDepartmentName(departmentName);
					dpa.setNodeName(node.getNodeName());
					save(dpa);
					definitionPreAssigns.add(dpa);
				}
			}
		}
		
		return definitionPreAssigns;
	}


	public Page listDefinitionPreAssign(String definiName, int pageNo,int pageSize) {
		Criteria c = super.getCriteria(DefinitionPreAssign.class);
		//c.addOrder(Order.asc("definitionName"));
		if(StringUtils.isNotBlank(definiName)){
			c.add(Restrictions.eq("definitionName", definiName));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public void removeDefinitionPreAssign(String definitionName) {
		String hql = "delete from DefinitionPreAssign where definitionName=?";
		executeUpdate(hql, definitionName);	
		//����ɾ��
		//hql = "delete from DefinitionPreAssign where definitionName=?";
	}

	public int updateDefinitionPreAssignDepart(
			String definitionName, String departmentCode, String departmentName) {
		String hql = "update DefinitionPreAssign set departmentCode=? departmentName=? where definitionName=?";
		return executeUpdate(hql, departmentCode,departmentName,definitionName);	
	}

	//begin by peixf
	@SuppressWarnings("deprecation")
	public List<UserInfo> findUserInfoByWorkflowRoleAndDepartment(WorkflowRole workFlowRole, Department dept) {
		//ȡ��WorkflowRole��Ӧ������ϵͳ��ɫRole��id�����뼯��roleIds
		String hql = "select wfrm.role from WorkflowRoleMaping wfrm where wfrm.workflowRole=?";
		List<Role> roles = super.find(hql, workFlowRole);
		Set roleIds = new HashSet();
		for(Role role : roles){
			roleIds.add(role.getId());
		}
		//��ѯ����roleIds����Ա,һ��ϵͳ��ɫ�ֶ�Ӧ�Ŷ���û�
		Criteria c = super.getCriteria(UserRole.class);
		if(!roleIds.isEmpty()){ 
			c.add(Restrictions.in("role.id", roleIds));
		}
		c.createAlias("userInfo", "userInfo").setFetchMode("userInfo", FetchMode.JOIN);
		//�ò���ɸѡ��Ա
		c.add(Restrictions.eq("userInfo.department", dept));
		//������ͶӰ��ѯ
		c.setProjection(Projections.property("userInfo"));
		//��ֹ�ظ���¼��distinct
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<UserInfo> userInfos = c.list();
		return userInfos;
	}
	//ȡ���������������ϵͳ��ɫ��Ȼ�����ҵ����еĹ�������ɫ
	public List<WorkflowRole> findWorkflowRoleByDepartment(Department dept) {
		String hql = "select r.id from Role r where r.department=?";
		List<Long> roleIds = super.find(hql, dept);
		if(roleIds.isEmpty()) {
			return null;
		}
		Criteria c = super.getCriteria(WorkflowRoleMaping.class);
		c.add(Restrictions.in("role.id", roleIds));
		c.setProjection(Projections.property("workflowRole"));
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<WorkflowRole> wfrs = c.list();
		return wfrs;
	}

	public List<WorkflowRole> findWorkflowRoleByUserInfo(UserInfo userInfo) {
		String hql = "select ur.role.id from UserRole ur where ur.userInfo=?";
		List<Long> roleIds = super.find(hql, userInfo);
		
		Criteria c = super.getCriteria(WorkflowRoleMaping.class);
		c.add(Restrictions.in("role.id", roleIds));
		c.setProjection(Projections.property("workflowRole"));
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<WorkflowRole> wfrs = c.list();
		return wfrs;
	}

	public List<WorkflowRole> findAllWorkflowRoles() {
		List list = super.getAll(WorkflowRole.class);
		return list;
	}

	public WorkflowRoleMaping findWorkflowRoleMapingById(String wfrmId) {
		WorkflowRoleMaping result = null;
		result = super.get(WorkflowRoleMaping.class, Long.valueOf(wfrmId));
		return result;
	}

	public void removeWorkflowRoleMapingById(String[] wfrmIds) {
		for(String wfrmId : wfrmIds){
			WorkflowRoleMaping wfrm = super.get(WorkflowRoleMaping.class, Long.valueOf(wfrmId));
			super.remove(wfrm);
		}
		
	}

	public WorkflowRoleMaping saveWorkflowRoleMaping(WorkflowRoleMaping wfrm) {
		WorkflowRoleMaping result = null;
		result = (WorkflowRoleMaping) super.save(wfrm);
		return result;
	}
	
	//end
	
	
	//���²���
	public List<TaskPreAssign> listPreAssignTask(String definiName) {
		Criteria c = super.getCriteria(TaskPreAssign.class);
		c.addOrder(Order.asc("definitionName"));
		c.addOrder(Order.asc("taskName"));
		if(StringUtils.isNotBlank(definiName)){
			c.add(Restrictions.eq("definitionName", definiName));
		}
		List list = c.list();
		return list;
	}

	public Page listPreAssignTask(String definiName, int pageNo, int pageSize) {
		Criteria c = super.getCriteria(TaskPreAssign.class);
		c.addOrder(Order.asc("definitionName"));
		c.addOrder(Order.asc("taskName"));
		if(StringUtils.isNotBlank(definiName)){
			c.add(Restrictions.eq("definitionName", definiName));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	/**
	 * ͨ���ڵ����ͺ����������ҵ���Ӧ�����ý�ɫ
	 * @Methods Name findUnitRoleByNodeTypeAndProDesc
	 * @Create In Feb 25, 2009 By guangsa
	 * @param processName
	 * @param nodeDesc
	 * @return ConfigUnitRole
	 */
	public ConfigUnitRole findUnitRoleByNodeTypeAndProDesc(String processName ,String nodeDesc,String virtualDefinitionId,Long nodeId){
		//Criteria c = super.getCriteria(ConfigUnitRole.class);
		Criteria c=this.createCriteria(ConfigUnitRole.class);
		c.add(Restrictions.eq("nodeId", nodeId));
		//c.add(Restrictions.eq("definitionName", processName));
		c.add(Restrictions.eq("processId", Long.valueOf(virtualDefinitionId)));
		ConfigUnitRole unitRole = (ConfigUnitRole)c.uniqueResult();
		return unitRole;
	}
	/**
	 * ͨ�����õ�Ԫ��ɫ�ҵ���Ӧ�Ľ�ɫ
	 * @Methods Name findRoleTableByConfigUnitRole
	 * @Create In Feb 25, 2009 By guangsa
	 * @param unitRole
	 */
	public List<ConfigUnitRoleTable> findRoleTableByConfigUnitRole(ConfigUnitRole unitRole){
		Criteria c = super.getCriteria(ConfigUnitRoleTable.class);
		c.add(Restrictions.eq("configUnitRole", unitRole));
		List<ConfigUnitRoleTable> list = c.list();
		return list;
	}
	

}
