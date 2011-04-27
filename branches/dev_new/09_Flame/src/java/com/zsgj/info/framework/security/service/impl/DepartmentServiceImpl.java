/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.ibmb2b.system.service.implDepartmentServiceImpl.java
 * @Create By zhangpeng
 * @Create In 2008-7-18 下午05:07:28
 * TODO
 */
package com.zsgj.info.framework.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;

/**
 * @Class Name DepartmentServiceImpl
 * @Author zhangpeng
 * @Create In 2008-7-18
 */
public class DepartmentServiceImpl extends BaseDao implements DepartmentService {

	private void initChilden(Department dept) {
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("childDepartments", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		Set childDepartments = result.getChildDepartments();
		Iterator iter = childDepartments.iterator();
		while(iter.hasNext()){
			Department childDept = (Department) iter.next();
			this.initChilden(childDept);
		}
		
	}
	
	private void initChilden(Department dept, List list) {
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("childDepartments", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		Set childDepartments = result.getChildDepartments();
		if(!childDepartments.isEmpty()){
			list.addAll(childDepartments);
			Iterator iter = childDepartments.iterator();
			while(iter.hasNext()){
				Department childDept = (Department) iter.next();
				this.initChilden(childDept, list);
			}
		}
		
		
	}
	
	/**
	 * 抓取指定深度的子部门
	 * @Methods Name initChilden
	 * @Create In 2009-2-11 By sa
	 * @param dept
	 * @param list
	 * @param fetchDepth void
	 */
	private void initChilden(Department dept, List list, int fetchDepth) {
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("childDepartments", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		
		Set childDepartments = result.getChildDepartments();
		if(!childDepartments.isEmpty()){
			list.addAll(childDepartments);
			fetchDepth = fetchDepth - 1;
			Iterator iter = childDepartments.iterator();
			while(iter.hasNext()){
				Department childDept = (Department) iter.next();
				if(fetchDepth>0){
					this.initChilden(childDept, list);
				}
				
			}
		}
		
		
	}
	
	/**
	 * 抓取指定深度的子部门, 同时传入根部门全名文本
	 * @Methods Name initChilden
	 * @Create In 2009-2-11 By sa
	 * @param dept
	 * @param list
	 * @param rootText
	 * @param fetchDepth void
	 */
	private void initChilden(Department dept, List list, String rootText, int fetchDepth) {
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("childDepartments", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		//给部门
		//result.setFullDepartName(rootText + dept.getDepartName());
		
		Set childDepartments = result.getChildDepartments();
		if(!childDepartments.isEmpty()){
			list.addAll(childDepartments);
			
			fetchDepth = fetchDepth - 1;
		
			Iterator iter = childDepartments.iterator();
			while(iter.hasNext()){
				Department childDept = (Department) iter.next();
				
				childDept.setFullDepartName(result.getFullDepartName() + "->" + childDept.getDepartName());
				
				if(fetchDepth>0){ 
					this.initChilden(childDept, list, rootText, fetchDepth);
				}
				
			}
		}
		
		
	}
	
	private void initChilden(Department dept, List list, String rootText) {
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("childDepartments", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		//给部门
		//result.setFullDepartName(rootText + dept.getDepartName());
		
		Set childDepartments = result.getChildDepartments();
		if(!childDepartments.isEmpty()){
			list.addAll(childDepartments);
			
			Iterator iter = childDepartments.iterator();
			while(iter.hasNext()){
				Department childDept = (Department) iter.next();
				
				childDept.setFullDepartName(result.getFullDepartName() + "->" + childDept.getDepartName());
				
				this.initChilden(childDept, list, rootText);
				
			}
		}
		
		
	}

	/**
	 * 递归向集合Set result里加入部门
	 * @Methods Name addChildByParentDept
	 * @Create In 2008-12-10 By sa
	 * @param result
	 * @param parent void
	 */
	private void addChildByParentDept(Set result, Department parent){
		Set childs = parent.getChildDepartments();
		Iterator iter = childs.iterator();
		while(iter.hasNext()){
			Department item = (Department) iter.next();
			result.add(item);
			this.addChildByParentDept(result, item);
		}
	}
	
	public Map<String, Map> findDepartmentChildren(Department parentDept) {
		//初始化部门的所有子部门
		this.initChilden(parentDept);
		Set result = new HashSet(); //存放所有的子部门对象
		this.addChildByParentDept(result, parentDept);
		Iterator iter = result.iterator();
		return null;
	}

	public List<UserInfo> findUserInfoByWorkflowRoleAndDepartment(WorkflowRole workFlowRole, Department dept) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WorkflowRole> findWorkflowRoleByDepartment(Department dept) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WorkflowRole> findWorkflowRoleByUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findChildDeptByParent(Department dept) {
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("parentDepartment", dept));
		List list = c.list();
		return list;
	}
	
	
	/**
	 * 按照根部门部门号和抓取部门深度获取所有部门, 本方法只从根开始取2层
	 */
	public List findDeptAll() {
		List list = new ArrayList();
		Criteria c3 = super.getCriteria(Department.class);
		String rootCode = PropertiesUtil.getProperties("system.dept.rootdeptcode", "50008953");
		String rootText = PropertiesUtil.getProperties("system.dept.rootdepttext", "神州数码->神州数码集团->总裁办公室");
		String fetchDepth = PropertiesUtil.getProperties("system.dept.deptfetchdepth", "5");
		int deptFetchDepth = Integer.valueOf(fetchDepth);
		
		c3.add(Restrictions.eq("departCode", Long.valueOf(rootCode)));//总裁办公室
		Department deptRoot = (Department) c3.uniqueResult(); //当前根部门
		//神州数码集团->神州数码科技公司->系统科技公司.网络应用及解决方案本部
		deptRoot.setFullDepartName(rootText);
		list.add(deptRoot);
		
		if(deptFetchDepth!=-1){
			this.initChilden(deptRoot, list, rootText, deptFetchDepth);
		}else{
			this.initChilden(deptRoot, list, rootText); //-1不做层级限制
		}
		
		return list;
		
		
	}

	public List findDeptByParentCode(String deptCode){
		List list = new ArrayList();
		if(StringUtils.isBlank(deptCode)){
			throw new ServiceException("系统默认的根部门不能未null");
		}
		
		String fetchDepth = PropertiesUtil.getProperties("system.dept.rootdepttext", "5");
		int deptFetchDepth = Integer.valueOf(fetchDepth);
		
		//取根部门节点
		Criteria criteria = super.getCriteria(Department.class);
		criteria.add(Restrictions.eq("departCode", Long.valueOf(deptCode)));//总裁办公室
		Department dept = (Department) criteria.uniqueResult();
		if(dept==null) return list; //如果父部门没有直接返回空数据
		list.add(dept); //把当前父部门放入集合
		
		//取第1层节点，方法内部递减深度值
		this.initChilden(dept, list, deptFetchDepth);
		return list;
		

	}

	public Department findDepartmentById(Long departCode) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(Department.class);
		c.add(Expression.eq("departCode", departCode));
		
		if(c.list().isEmpty()) {
			return null;
		}else{
			return (Department)c.list().get(0);
		}
	}
	
	public Department findDepartmentByName(String name) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(Department.class);
		c.add(Expression.eq("departName", name));
		
		if(c.list().isEmpty()) {
			return null;
		}else{
			return (Department)c.list().get(0);
		}
	}
	
	public Department findDepartmentByRealId(Long id) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(Department.class);
		c.add(Expression.eq("id", id));
		
		if(c.list().isEmpty()) {
			return null;
		}else{
			return (Department)c.list().get(0);
		}
	}
	
	public String findFull3To5DepartmentName(Department department) {

		String rootText = PropertiesUtil.getProperties("system.dept.rootdepttext", "神州数码->神州数码集团->总裁办公室->");
		int length = rootText.length();
		Criteria c = createCriteria(Department.class);
		c.add(Restrictions.eq("id", department.getId()));
		c.setFetchMode("parentDepartment", FetchMode.JOIN);
		Department current = (Department) c.uniqueResult();
		if(current==null) return "";
		String deptName = current.getDepartName();
		while(current.getParentDepartment()!=null){
			deptName = current.getParentDepartment().getDepartName()+"->"+deptName;
			current = current.getParentDepartment();
		}
		if(deptName.length()>=21){
			deptName = deptName.substring(length);
		}
		
		String[] splits = deptName.split("->");
		if(splits.length>=2){
			deptName = splits[0]+"->" + splits[1];
		}else if(splits.length==1){
			deptName = splits[0];
		}
		return deptName;
	}

	private Department getParentDept(Department dept){
		Criteria c = createCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("parentDepartment", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		return result.getParentDepartment();
	}

	public Department findDepartmentByUserInfo(UserInfo userInfo) {
		Criteria c = super.getCriteria(UserInfo.class);
		c.add(Restrictions.eq("id", userInfo.getId()));
		UserInfo result = (UserInfo) c.uniqueResult();
		return result.getDepartment();
	}

	private void findParentDept(List<Department> parentList, Department dept){
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("parentDepartment", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		Department parentDept = result.getParentDepartment();
		if(parentDept!=null){
			parentList.add(parentDept);
			this.findParentDept(parentList, parentDept);
		}
		
	}
	public List<Department> findDepartmentParents(Department dept) {
		List<Department> parentList = new ArrayList<Department>();
		
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("parentDepartment", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		Department parentDept = result.getParentDepartment();
		if(parentDept!=null){
			parentList.add(parentDept);
			this.findParentDept(parentList, parentDept);
		}
		
		Collections.reverse(parentList);
		
		return parentList;
		
	}

	public List<UserInfo> findUserInfoByDepartment(Department dept) {
		Criteria c = super.getCriteria(UserInfo.class);
		c.add(Restrictions.eq("department", dept));
		List list = c.list();
		return list;
	}
	
	public Map findDepartmentByDepName(String depName, String orderBy,
			boolean isAsc, int pageNo, int pageSize) {
		Map<String,Object> resultMap =  new HashMap();
		
		List<Department> list = new ArrayList();
		List depList= new ArrayList();
		Criteria criteria = super.getCriteria(Department.class);
		if(depName!=null&&!"".equals(depName)){
			criteria.add(Expression.like("departName", "%"+depName+"%"));
		}
		Criteria c1 = super.getCriteria(Department.class);
		String rootCode =PropertiesUtil.getProperties("system.dept.rootdeptcode", "50008953");
		c1.add(Restrictions.eq("departCode", Long.valueOf(rootCode)));//总裁办公室
		Department deptRoot = (Department) c1.uniqueResult(); //当前根部门
		//modify by tongjp 当部门号为下面几个号的时候，则不执行下面的查询，影响效率
		if(!rootCode.equals("50008953")&&!rootCode.equals("50008952")&&!rootCode.equals("50000075")){
			this.initChilden(deptRoot, list);
			for(Department dep:list){
				depList.add(dep.getDepartCode());
			}
			criteria.add(Restrictions.in("departCode", depList));
		}
		List queryList = criteria.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		
		criteria.setFirstResult(0);
		long total =  ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		resultMap.put("queryList", queryList);
		resultMap.put("total", total);
		return resultMap;
	}
   /**
    * 查找所有的根部门
    */
	public List findRootDepartments(String departmentCode) {
		String hql = "from Department d where d.parentDepartment.id =?";
		Query query = createQuery(hql, Long.parseLong(departmentCode));
		List list=query.list();
		return list;
	}

	public List findRootDept(String deptCode) {
		Department dept = null;
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("departCode", Long.valueOf(deptCode)));
		List list = c.list();
		return list;
	}

	/**
	 * 添加子部门
	 */
	public void addChildDepartment(Long parentDeptId, String childDeptName) {
		Department parentDept = null;
		Long parentDeptCode = null;
		Department childDept = new Department();
		
		if(parentDeptId.equals(new Long("0"))){//如果为根节点
			childDept.setParentDepartment(null);
		}else {//非根节点
			parentDept = super.get(Department.class, parentDeptId);
			childDept.setParentDepartment(parentDept);
		}
		childDept.setDepartName(childDeptName);
		Criteria criteria = super.createCriteria(Department.class);
		criteria.setProjection(Projections.max("departCode"));
		Long departCode = (Long) criteria.uniqueResult();
		departCode += 1;
		childDept.setId(departCode);
		childDept.setDepartCode(departCode);
		super.save(childDept);
	}

	/**
	 * 删除选中部门
	 */
	public void deleteCurrentDepartment(Long departmentId) {
		Department department = super.get(Department.class, departmentId);
		super.remove(department);
		
	}
	/**
	 * 修改选中部门信息
	 */
	public void modifyCurrentDepartment(Long departmentCode,String departmentName) {
		
		Department department = (Department) super.findUniqueBy(Department.class, "departCode", departmentCode);
		department.setDepartName(departmentName);
		super.save(department);
	}

	
}
