package com.xp.commonpart.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xp.commonpart.Page;
/**
 * 通过sql语句进行的基础接口
 * @author tongjp
 *
 */
public interface SelectDataService {
	/**
	 * 通过sql语句进行查询
	 * @param sql
	 * @author tongjp
	 * @return
	 */
	public List getData(String sql);
	/**
	 * 通过sql语句进行删除
	 * @param sql
	 * @author tongjp
	 * @return
	 */
	public boolean remove(String sql);
	/**
	 * 通过sql语句获取查询的字段名称
	 * @param sql
	 * @author tongjp
	 * @return
	 */
	public String[] getColumnName(String sql);
	/**
	 * 通过sql语句获取记录的总数
	 * @param sql
	 * @author tongjp
	 * @return
	 */
	public int getDataRowNum(String sql);
	/**
	 * 通过sql语句进行数据保存和更新
	 * @param sql
	 * @author tongjp
	 * @return
	 */
	public void saveRealTable(String sql);
	/**
	 * 设置权限方法
	 * @param request
	 */
	public void setManage(HttpServletRequest request);
	/**
	 * 通过sql语句进行分页查寻
	 * @param sql
	 * @author tongjp
	 * @return
	 */
	public Page getListForPage(HttpServletRequest request,String sql); 
}
