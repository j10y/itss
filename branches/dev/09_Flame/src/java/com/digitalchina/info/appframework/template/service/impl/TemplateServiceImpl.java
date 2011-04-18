package com.digitalchina.info.appframework.template.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.appframework.template.entity.Template;
import com.digitalchina.info.appframework.template.entity.TemplateItem;
import com.digitalchina.info.appframework.template.service.TemplateService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class TemplateServiceImpl extends BaseDao implements TemplateService {
	public Template findTemplateByClass(Class clazz) {
		SystemMainTableService smts = (SystemMainTableService) ContextHolder.getBean("systemMainTableService");
		SystemMainTable smt = smts.findSystemMainTableByClazz(clazz);
		return smt.getTemplate();
		
	}
	public String findTemplateItemWithChild(Template template) {
		Criteria c = super.getCriteria(TemplateItem.class);
		c.add(Restrictions.eq("template", template));
		c.add(Restrictions.isNull("parentTemplateItem"));
		c.addOrder(Order.asc("orderFlag"));
		c.setFetchMode("childTemplateItems", FetchMode.JOIN);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = c.list();
		Iterator iterC = list.iterator();
		String result = "[";
		while(iterC.hasNext()){
			TemplateItem item = (TemplateItem) iterC.next();
			Set childen =  item.getChildTemplateItems();
			Long id = item.getId();
			String name = item.getName();
			String value = "";
			String uiProvider = "col";
			String cls = "x-btn-text-icon";
			String iconCls = "task";
			result += "{id:'"+id+"',";
			result += "name:'"+name+"',";
			result += "value:'"+value+"',";
			result += "uiProvider:'"+uiProvider+"',";
			result += "cls:'"+cls+"',";
			result += "iconCls:'"+iconCls+"',";
			if(childen != null){
				result += "children:[";			
				result += initChild(item);
				result +="]},";
			}else{
				result = result.substring(0, result.length()-1);
				result += "leaf:"+true+"},"; 
			}	
		}
		result = result.substring(0, result.length()-1);
		result += "]";
		
		return result;
	}

	private String initChild(TemplateItem templateItem){
		String temp = "";
		Criteria c = super.getCriteria(TemplateItem.class);
		c.add(Restrictions.eq("id", templateItem.getId()));
		c.setFetchMode("childTemplateItems", FetchMode.JOIN);
	/*	c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);*/
		TemplateItem item = (TemplateItem) c.uniqueResult();
		Set childen =  item.getChildTemplateItems();
		if(childen.size()==0){
			return temp;
		}else{
			Iterator iter = childen.iterator();
			while(iter.hasNext()){
				TemplateItem dmtItem = (TemplateItem) iter.next();
				Set dmtchilden =  dmtItem.getChildTemplateItems();
				Long id = dmtItem.getId();
				String name = dmtItem.getName();
				String value = "";
				String uiProvider = "col";
				String cls = "x-btn-text-icon";
				String iconCls = "task";
				temp += "{id:'"+id+"',";
				temp += "name:'"+name+"',";
				temp += "value:'"+value+"',";
				temp += "uiProvider:'"+uiProvider+"',";
				temp += "cls:'"+cls+"',";
				temp += "iconCls:'"+iconCls+"',";
				if(dmtchilden != null){
					temp += "children:[";			
					temp += initChild(dmtItem);
					temp +="]},";
				}else{
					temp = temp.substring(0, temp.length()-1);
					temp += "leaf:"+true+"},"; 
				}
			}
			temp = temp.substring(0, temp.length()-1);
			return temp;
		}
	}
	
	public List<Column> findColumnByTemplateTable(SystemMainTable smt) {
		if(UserContext.getUserInfo()==null){
			throw new ServiceException("为了获取用户可见字段，请先登录");
		}
		List<Column> columns = new ArrayList<Column>();
		Criteria c = super.getCriteria(UserTableSetting.class);
		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
		c.add(Restrictions.eq("isDisplay", 1));
		List list = c.list();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			UserTableSetting uts = (UserTableSetting) iter.next();
			Column column = uts.getColumn();
			columns.add(column);
		}
		return columns;
	}

	public List<SystemMainTable> findTemplateTable() {
		String hql="from SystemMainTable smt where smt.templateFlag=1";
		List<SystemMainTable> list = super.find(hql, null);
		return list;
	}

	public List<TemplateItem> findAllTemplateItem() {
		return this.getObjects(TemplateItem.class);
	}

	public List<Template> findAllTemplates() {
		return this.getObjects(Template.class);
	}

	public List<TemplateItem> findChildenByParent(String parentTemplateItemId) {
		TemplateItem parent = this.get(TemplateItem.class, Long.valueOf(parentTemplateItemId));
		String hql = "select m from TemplateItem m where m.parentTemplateItem=? order by m.orderFlag  ";
		List list = super.find(hql, parent);
		return list;
	}

	public TemplateItem modifyTemplateItemName(String TemplateItemId, String TemplateItemName) {
		return null;
	}

	public void removeTemplate(String[] dmtIds) {
		if(dmtIds==null|| dmtIds.length==0){
			throw new ServiceException("请选择要删除的模板");
		}
		for(int i=0; i<dmtIds.length; i++){
			Template current = (Template) super.get(Template.class, Long.valueOf(dmtIds[i]));
			super.executeUpdate("delete TemplateItem smti where smti.template=?", new Object[]{current});
			super.remove(current);
		}
		
	}

	public TemplateItem saveTemplateItem(TemplateItem TemplateItem) {
		TemplateItem result = null;
		result = (TemplateItem) super.save(TemplateItem);
		return result;
	}

	public Template saveTemplate(Template template) {
		Template result = null;
		result = (Template) super.save(template);
		return result;
	}

	public TemplateItem findTemplateItemById(String id) {
		TemplateItem TemplateItem = null;
		TemplateItem = this.get(TemplateItem.class, Long.valueOf(id));
		return TemplateItem;
	}

	public List<TemplateItem> findTemplateItemsByName(String name) {
		List list = null;
		list = super.findBy(TemplateItem.class, "TemplateItemName", name);
		return list;
	}

	public void removeTemplateItem(String TemplateItemId) {
		TemplateItem TemplateItem = this.get(TemplateItem.class, Long.valueOf(TemplateItemId));
		this.executeUpdate("delete from TemplateItem m where m.parentTemplateItem=?", TemplateItem);
		super.removeById(TemplateItem.class, Long.valueOf(TemplateItemId));
		
	}

	public void removeNode(String TemplateItemId) {
		TemplateItem templateItem = this.get(TemplateItem.class, Long.valueOf(TemplateItemId));
		if(templateItem.getParentTemplateItem() == null){
			this.downNode(new Long(0), templateItem.getOrderFlag(), -1);
		}else{
			this.downNode(templateItem.getParentTemplateItem().getId(), templateItem.getOrderFlag(), -1);
		}
		this.removeTemplateItem(TemplateItemId);
	}

	public void saveNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		Long templateItemId = Long.valueOf(mId);
		Long oldParentId = Long.valueOf(oldPid);
		Long newParentId = Long.valueOf(newPid);
		int nodeIndex = Integer.parseInt(nodeIndx);
		TemplateItem obj = this.get(TemplateItem.class, templateItemId);
		TemplateItem newParent = super.get(TemplateItem.class, newParentId);
		int minIndex = obj.getOrderFlag().intValue();
		int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
		if(oldParentId == newParentId && minIndex != maxIndex){
			// 在同一个父节点下发生移动
			if(minIndex < maxIndex){
				// 当要移动的节点的序号小于要移动到的目标序号，则下移
				this.downNode(oldParentId, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// 当要移动的节点的序号大于要移动到的目标序号，则上移
				maxIndex = minIndex;
				minIndex = nodeIndex;
				this.upNode(oldParentId, minIndex, maxIndex);
			}
			// 节点本身的序号设置成要移动到的目标序号
			obj.setOrderFlag(nodeIndex);
			this.saveTemplateItem(obj);
		}
		if(oldParentId != newParentId){
			// 在不同父节点下发生移动
			//1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置
			this.downNode(oldParentId, minIndex, -1);
			//2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置
			this.upNode(newParentId, maxIndex, -1);
			// 节点本身的序号设置成要移动到的目标序号
			obj.setOrderFlag(nodeIndex);
			obj.setParentTemplateItem(newParent);
			this.saveTemplateItem(obj);
		}
		
	}

	/**
	 * 指定的节点下移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点移动发生时所在的位置
	 * @param maxIndex	指定节点要移动到的目标位置
	 */
	public void downNode(Long parentId, Integer minIndex, Integer maxIndex){

		TemplateItem parent= this.get(TemplateItem.class, parentId);
		// 指定的节点下移，意味着其范围内的节点各自减1
		StringBuffer hql = new StringBuffer("update TemplateItem m set m.orderFlag=m.orderFlag-1 where m.parentTemplateItem = ?");
		List paramsList = new ArrayList();
		paramsList.add(parent);
		
		if(maxIndex != -1){
			hql.append(" and m.orderFlag <= ? ");
			paramsList.add(maxIndex);
		}
		if(minIndex != -1){
			hql.append(" and m.orderFlag > ? ");
			paramsList.add(minIndex);
		}		
		Object[] params = paramsList.toArray();
		super.executeUpdate(hql.toString(), params/*new Object[]{parent, maxIndex, minIndex}*/);
	}
	
	/**
	 * 指定的节点上移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点要移动到的目标位置
	 * @param maxIndex	指定节点移动发生时所在的位置
	 */
	@SuppressWarnings("unchecked")
	public void upNode(Long parentId, Integer minIndex, Integer maxIndex){
		TemplateItem parent = this.get(TemplateItem.class, parentId);
		// 指定的节点下移，意味着其范围内的节点各自减1
		StringBuffer hql = new StringBuffer("update TemplateItem m set m.orderFlag=m.orderFlag+1 where m.parentTemplateItem = ?");
		List paramsList = new ArrayList();
		paramsList.add(parent);
		
		if(maxIndex != -1){
			hql.append(" and m.orderFlag < ? ");
			paramsList.add(maxIndex);
		}
		if(minIndex != -1){
			hql.append(" and m.orderFlag >= ? ");
			paramsList.add(minIndex);
		}		
		Object[] params = paramsList.toArray();
		super.executeUpdate(hql.toString(), params);
	}
	
	public List<TemplateItem> findChildenByParentAndTemplate(String parentId, String templateId) {
		
		TemplateItem tmItem = super.get(TemplateItem.class, Long.valueOf(parentId));
		Template template = super.get(Template.class, Long.valueOf(templateId));
		String hql="select ti from TemplateItem ti where ti.parentTemplateItem =? and ti.template=? order by ti.orderFlag";
		List<TemplateItem> itemList = super.find(hql, new Object[]{tmItem,template});
		return itemList;
	}

	public List<TemplateItem> findTemplateItemNoParent(String templateId) {
		
		Template template = super.get(Template.class, Long.valueOf(templateId));
		String hql="select ti from TemplateItem ti where ti.parentTemplateItem is null and ti.template=?  order by ti.orderFlag";
		List<TemplateItem> itemList = super.find(hql, template);
		List<TemplateItem> returnList = new ArrayList<TemplateItem>();
		
		for(TemplateItem item:itemList){
			TemplateItem parentTemplateItemItem = new TemplateItem();
			parentTemplateItemItem.setId(new Long(0));
			item.setParentTemplateItem(parentTemplateItemItem);
			returnList.add(item);
		}
		
		return returnList;
	}
	
	public List<UserInfo> findAllUsers(){
		return this.getObjects(UserInfo.class);
		
	}
	
	public Template findTemplateById(String templateId){
		Criteria c = super.getCriteria(Template.class);
		c.add(Restrictions.eq("id", new Long(templateId)));
		Template res = (Template) c.uniqueResult();
		return res;
	}

	public List<TemplateItem> findTemplateItemsByParentAndTemplate(String parentId, String templateId) {
		TemplateItem tmti = super.get(TemplateItem.class, Long.valueOf(parentId));
		Template tmt = super.get(Template.class, Long.valueOf(templateId));
		String hql="select tmti from TemplateItem tmti where tmti.parentTemplateItem =? and tmti.template=? order by tmti.orderFlag";
		List<TemplateItem> itemList = super.find(hql, new Object[]{tmti,tmt});
		return itemList;
	}

	public List<TemplateItem> findTemplateItemsNoParent(String templateId) {
		Template tmt = super.get(Template.class, Long.valueOf(templateId));
		String hql="select tmti from TemplateItem tmti where tmti.parentTemplateItem is null and tmti.template=? order by tmti.orderFlag";
		List<TemplateItem> itemList = super.find(hql, tmt);
		List<TemplateItem> returnList = new ArrayList<TemplateItem>();
		
		for(TemplateItem item:itemList){
			TemplateItem parentTemplateItem = new TemplateItem();
			parentTemplateItem.setId(new Long(0));
			item.setParentTemplateItem(parentTemplateItem);
			returnList.add(item);
		}
		
		return returnList;
	}
}
