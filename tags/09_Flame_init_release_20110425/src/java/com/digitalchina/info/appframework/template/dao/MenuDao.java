/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.info.framework.security.daoMenuService.java
 * @Create By zhangpeng
 * @Create In 2008-7-17 ����02:52:14
 * TODO
 */
package com.digitalchina.info.appframework.template.dao;

import java.io.Serializable;
import java.util.List;

import com.digitalchina.info.appframework.template.entity.Menu;

/**
 * �ṩ�����Ĳ˵���ά������
 * @Class Name MenuService
 * @Author zhangpeng
 * @Create In 2008-7-17
 */
public interface MenuDao {
	
	/**
	 * ����˵���
	 * @Methods Name save
	 * @Create In 2008-7-17 By zhangpeng
	 * @param menu
	 * @return Menu
	 */
	public Menu save(Menu menu);
	
	/**
	 * �������в˵���
	 * @Methods Name findAll
	 * @Create In 2008-7-17 By zhangpeng
	 * @return List
	 */
	public List<Menu> selectAll();
	
	/**
	 * ����Id�����˵���
	 * @Methods Name findMenuByID
	 * @Create In 2008-7-17 By zhangpeng
	 * @param id
	 * @return Menu
	 */
	public Menu selectMenuByID(Long id);
	
	/**
	 * �������Ƽ����˵���
	 * @Methods Name selectMenusByName
	 * @Create In 2008-7-17 By zhangpeng
	 * @param name
	 * @return List<Menu>
	 */
	public List<Menu> selectMenusByName(String name);
	
	/**
	 * ɾ���˵�
	 * @Methods Name removeObject
	 * @Create In 2008-7-17 By zhangpeng
	 * @param clazz
	 * @param id void
	 */
	public void removeObject(Class clazz, Serializable id);
	
	/**
	 * ���ݼ����ѯ�˵���
	 * @Methods Name selectMenuByLevel
	 * @Create In 2008-7-22 By zhangpeng
	 * @param level
	 * @return List<Menu>
	 */
	public List<Menu> selectMenuByLevel(Integer level);
	
	/**
	 * ���ݸ��˵���ѯ�����Ӳ˵�
	 * @Methods Name selectMenuByPrentMenu
	 * @Create In 2008-7-22 By zhangpeng
	 * @param parent
	 * @return List<Menu>
	 */
	public List<Menu> selectMenuByPrentMenu(Menu parent);
	
}
