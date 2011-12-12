package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.Department;
import java.util.List;

public abstract interface DepartmentService extends BaseService<Department> {
	public abstract List<Department> findByParentId(Long paramLong);

	public abstract List<Department> findByDepName(String paramString);

	public abstract List<Department> findByPath(String paramString);
	
	/**
	 * 通过某个部门的ID获取分局的部门树
	 * @param paramLong
	 * @return
	 */
	public abstract List<Department> findDistTreeById(Long paramLong);
	
	/**
	 * 获取某个分局部门的根部门
	 * @param paramLong
	 * @return
	 */
	public abstract Department findDistTreeRootById(Long paramLong);
	
	/**
	 * 根据某个部门树节点获取其整棵树
	 * @param paramLong
	 * @return
	 */
	public abstract List<Department> findDistTreeByRootId(Long paramLong);
}