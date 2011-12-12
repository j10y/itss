package com.xpsoft.oa.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.DepartmentDao;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.system.DepartmentService;

public class DepartmentServiceImpl extends BaseServiceImpl<Department>
		implements DepartmentService {
	private DepartmentDao dao;

	public DepartmentServiceImpl(DepartmentDao dao) {
		/* 19 */super(dao);
		/* 20 */this.dao = dao;
	}

	public List<Department> findByParentId(Long parentId) {
		/* 24 */return this.dao.findByParentId(parentId);
	}

	public List<Department> findByDepName(String depName) {
		/* 28 */return this.dao.findByDepName(depName);
	}

	public List<Department> findByPath(String path) {
		/* 32 */return this.dao.findByPath(path);
	}

	public List<Department> findDistTreeById(Long paramLong) {
		Long parentId = null;
		List<Department> result = new ArrayList();
		Department department = dao.get(paramLong);
		if(department == null) return result;
		Integer isDist = department.getIsDist();
		if(isDist == null) return result;
		//获取树的根
		Department root = this.findDistTreeRootById(paramLong);
		if(root == null) 
			return result;
		result = this.findDistTreeByRootId(root.getDepId());
		return result;
	}

	public Department findDistTreeRootById(Long paramLong) {
		// TODO Auto-generated method stub
		Department department = dao.get(paramLong);
		if(department == null) return null;
		Integer isDist = department.getIsDist();
		if(isDist == null) return null;
		if(isDist.intValue()==2){
			return department;
		}else if(isDist.intValue()==1){//递归获取其父亲
			Department dept = null;
			dept = dao.get(department.getParentId());
			while(dept!=null){
				if(dept.getIsDist().intValue()==2){
					return dept;
				}else{
					dept = dao.get(dept.getParentId());
				}
			}
		}
		return null;
	}

	@Override
	public List<Department> findDistTreeByRootId(Long paramLong) {
		List<Department> resultList = new ArrayList();
		this.cascadeDistTreeByRootId(paramLong, resultList);
		return resultList;
	}
	
	private void cascadeDistTreeByRootId(Long paramLong, List resultList) {
		List<Department> levelList = dao.findByParentId(paramLong);
		if(levelList.size()>0){
			resultList.addAll(levelList);
			for(Department dept : levelList)
				this.cascadeDistTreeByRootId(dept.getDepId(), resultList);
		}
	}
	
}
