package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelRelation;
import com.zsgj.info.appframework.pagemodel.service.PagePanelRelationService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.ServiceException;

public class PagePanelRelationServiceImpl extends BaseDao implements PagePanelRelationService {

	public List<PagePanelRelation> findPagePanelRelation(PagePanelRelation parentPagePanel) {
		Criteria c = super.getCriteria(PagePanelRelation.class);
		c.add(Restrictions.eq("parentPagePanel", parentPagePanel.getPagePanel()));
		c.addOrder(Order.asc("order"));
		List list = c.list();
		return list;
	}
	/**
	 * ɾ��ʱ��ı���ţ�minIndexΪ��������,nodeIndexΪ���ڵ��µ��ӽڵ���Ŀ,ordParentId��parentId),�ص����ڵõ�Ψһȷ��pagePanelRelation
	 * ɾ����Ϊ�����������һ�����ڵ�һ�㲢��û�к��ӽڵ��ʱ��ֻ��Ҫ����Ŵ������ļ�һ����;
	 * �ڶ��־����ڵ�һ���к��ӽڵ��ʱ��ͬ��һ�����
	 * �����־��ǲ��ٵ�һ��û�к��ӽڵ�,
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public void changeOrderByDelete(String relationId , int minIndex, int nodeIndex) {
		
		PagePanelRelation pagePanelRelation = this.findPagePanelRelationById(relationId);
		PagePanel currentPagePanel =  pagePanelRelation.getParentPagePanel(); 
		//Ҫ�����ĸ��ڵ�		
		
			StringBuffer hql = new StringBuffer(
					"update PagePanelRelation p set p.order=p.order-1 where p.parentPagePanel = ?");
			List paramsList = new ArrayList();
			paramsList.add(currentPagePanel);
			if(nodeIndex != -1){
				hql.append("and p.order<=?");
				paramsList.add(nodeIndex);
			}
			if(minIndex != -1){
				hql.append(" and p.order > ? ");
				paramsList.add(minIndex);
			}				
			Object[] obj = paramsList.toArray();
			super.executeUpdate(hql.toString(), obj);
		
		
			
		
	}

	
	private void initChildPanels(PagePanel parent){
		Criteria c = super.getCriteria(PagePanel.class);
		c.add(Restrictions.eq("id", parent.getId()));
		c.setFetchMode("childPagePanels", FetchMode.JOIN);
		parent = (PagePanel) c.uniqueResult();
		Set<PagePanelRelation> childens = parent.getChildPagePanels();
		for(PagePanelRelation item : childens){
			PagePanel childPanel = item.getPagePanel();
			this.initChildPanels(childPanel);
		}
	}
	
	public List<PagePanel> findPagePanelByParent(PagePanel parentPagePanel) {
		Criteria c = super.getCriteria(PagePanelRelation.class);
		c.add(Restrictions.eq("parentPagePanel", parentPagePanel));
		c.addOrder(Order.asc("order"));
		c.setProjection(Projections.property("pagePanel"));
		List<PagePanel> childs = c.list();
		for(PagePanel childItem : childs){
			this.initChildPanels(childItem);
		}
		return childs;
	}
	/**
	 * 
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public PagePanelRelation findPagePanelRelationById(String id) {
		PagePanelRelation result = null;
		result = super.get(PagePanelRelation.class, Long.valueOf(id));
		return result;
	}
	/**
	 * ɾ��Ҷ�ӽڵ�,�ص����ڸ������˭
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public void removeLeafPagePanelRelation(PagePanelRelation PagePanelRelation, int nodeIndex ,String pagePanelId) {
		
		String parentId = "-100";
		int minIndex = PagePanelRelation.getOrder();
		if(PagePanelRelation.getParentPagePanel()!=null){
			parentId = PagePanelRelation.getParentPagePanel().getId()+"";
		}
		if(!parentId.equals("-100") && parentId!= null){
			//this.changeOrderByDelete(parentId, minIndex, nodeIndex, pagePanelId);
			super.removeById(PagePanelRelation.getClass(), PagePanelRelation.getId());
			super.flush();
		}else{
			//this.changeOrderByDelete(parentId, minIndex, nodeIndex, pagePanelId);
			super.removeById(PagePanelRelation.getClass(), PagePanelRelation.getId());
			super.flush();
		}	
		
	}
	/**
	 * ��ɾ������ʱ���һ�ȫ��һ��һ��ɾ�����ÿ����κ�˳��λ
	 * @Methods Name removeRootNode
	 * @Create In Dec 19, 2008 By Administrator
	 * @param pageModelPanel void
	 */
	public void removeRootNode(PagePanelRelation pagePanelRelation){
		super.removeById(PagePanelRelation.class, pagePanelRelation.getId());
		super.flush();
	}
	
	/**
	 * ɾ����Ҷ�ӽڵ�,�ص������ж��ǲ���ɾ����Ҷ�ӽڵ㣬��������ڵ�ĸ��ڵ㲻�Ǹýڵ���˵����Ҷ�ӽڵ�
	 * ������Ϊ��Ҷ�ӽڵ�
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public void removePanelRelation(PagePanelRelation pagePanelRelation) {
		StringBuffer hql = new StringBuffer("from PagePanelRelation p " +
							"where p.parentPagePanel.id = ?");
		List paramList = new ArrayList();
		paramList.add(pagePanelRelation.getId());
		
		Object[] params = paramList.toArray();		
		Object result = super.createQuery(hql.toString(), params).uniqueResult();
		List<PagePanelRelation> list = super.createQuery(hql.toString(), params).list();
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				removeRootNode(list.get(i));
			}			
		}		
	}
	/**
	 * ����ƶ�֮�󱣴�(oldParentIdΪpagePanelRelation��ʵ��id )
	 * Ҫ��Ϊ�¾ɸ��ڵ�Ϊͬһ���ڵ�Ͳ�ͬ���ڵ�������
	 * �����¾ɸ��ڵ�Ϊͬһ���ڵ��ַ�Ϊ���ڵ�һ���ʱ�򣻺��½ڵ��ڵ�һ����ɸ��ڵ㲻�ٵ�һ�㣻���¸��ڵ㲻�ٵ�һ����ɸ��ڵ��ڵ�һ�㣻
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public void savePagePanelMove(PagePanel currentPanel,PagePanel pagePanel, String oldParentId, String newParentId, String nodeIndex) {
		//�������ܼ򵥣����ù��¸��ڵ�����⣬Ҳ���ùֲܷ������;����Ҫע�ⲻ���ϵ�����ȥ
		
		if(oldParentId.equals(newParentId) ){//&& oldParentId!=newParentId
			
			PagePanelRelation pagePanelRelation = this.findPagePanelRelation(pagePanel, currentPanel);
			Long nowId = pagePanelRelation.getId();
			int minIndex = pagePanelRelation.getOrder();
			int maxIndex = Integer.parseInt(nodeIndex);
			
			if(minIndex < maxIndex){
				// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
				this.downNode(nowId, minIndex, maxIndex);
			}
			pagePanelRelation.setOrder(Integer.parseInt(nodeIndex));
			pagePanelRelation.setParentPagePanel(currentPanel);
			this.savePagePanelRelation(pagePanelRelation);
		}
		
	}
	/**
	 * �϶���ʱ�򱣴���Ӧ��panel
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public PagePanelRelation savePagePanelRelation(PagePanelRelation ppr) {
		
		PagePanelRelation result = null;
		try {
			result = (PagePanelRelation) super.save(ppr);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("����PageModelʱ�����쳣");
		}		
		return result;
	}
	

	/**
	 * ����(��Ҫ���ĵ��Ǹýڵ������Ľڵ���ţ��������ƶ�֮����),������ô���panelId
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
		public void downNode(Long currentPanelRelationId, Integer minIndex,
			Integer maxIndex) {
			
		//���Ƶ��ص�������֮��Ľڵ���������ȱ���ȷ����ǰ�ڵ�			
			PagePanelRelation currentPanelRelation = super.get(PagePanelRelation.class, currentPanelRelationId);
			PagePanel pagePanel = currentPanelRelation.getParentPagePanel();
			StringBuffer hql = new StringBuffer(
					"update PagePanelRelation ppr set ppr.order= ppr.order-1 where ppr.parentPagePanel=?");
			List paramsList = new ArrayList();
			paramsList.add(pagePanel);
			
			if(maxIndex != -1){
				hql.append(" and ppr.order <= ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and ppr.order > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
			
		
		}
		/**
		 * ����
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public void upNode(Long currentPanelRelationId, Integer minIndex,
				Integer maxIndex) {
			//���Ƶ��ص�������֮��Ľڵ���������ȱ���ȷ����ǰ�ڵ�	
			PagePanelRelation currentPanelRelation = super.get(PagePanelRelation.class, currentPanelRelationId);
			PagePanel pagePanel = currentPanelRelation.getParentPagePanel();
			StringBuffer hql = new StringBuffer(
					"update PagePanelRelation ppr set ppr.order= ppr.order-1 where ppr.parentPagePanel=?");
			List paramsList = new ArrayList();
			paramsList.add(pagePanel);
			
			if(maxIndex != -1){
				hql.append(" and ppr.order <= ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and ppr.order > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
			
		}
		/**
		 * 
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public PagePanelRelation findPagePanelRelation(PagePanel pagePanel,
				PagePanel parentPagePanel) {
			
			Criteria criteria = super.getCriteria(PagePanelRelation.class);
			criteria.add(Restrictions.eq("pagePanel",pagePanel));
			criteria.add(Restrictions.eq("parentPagePanel", parentPagePanel));
			PagePanelRelation pagePanelRelation = (PagePanelRelation)criteria.setMaxResults(1).uniqueResult();
			
			return pagePanelRelation;
		}
		/**
		 * 
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public void removePagePanelRelation(String pagePanelId) {
			PagePanel panel = super.get(PagePanel.class, Long.valueOf(pagePanelId));
			super.executeUpdate("delete from PagePanelRelation ppr " +
					"where ppr.parentPagePanel.id=?", Long.valueOf(pagePanelId));
					
			super.removeObject(PagePanel.class, Long.valueOf(pagePanelId));
		}
		/**
		 * 
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public void removePagePanelRelation(String[] pagePanelIds) {
			if(pagePanelIds==null||pagePanelIds.length==0){
				throw new ServiceException("ɾ��PagePanel�����쳣");
			}
			for(String pagePanelId: pagePanelIds){
				this.removePagePanelRelation(pagePanelId);
			}
		}
		/**
		 * 
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public List findPagePanelRelationByParentPagePanel(PagePanel parentPagePanel){
			Criteria criteria = super.getCriteria(PagePanelRelation.class);
			criteria.add(Restrictions.eq("parentPagePanel", parentPagePanel));
			criteria.setFetchMode("pagePanel", FetchMode.JOIN); //��ֹ�ӳټ����������ع�������	
			List list = criteria.list();
			return list;
		}
		/**
		 * 
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public void deletePagePanelRelation(PagePanelRelation relation){
			
			super.removeById(PagePanelRelation.class, relation.getId());
			super.flush();
		}
		/**
		 * 
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public List findPagePanelRelationByPageAddOrder(
				PagePanel relation) {
			
			Criteria criteria = super.getCriteria(PagePanelRelation.class);
			criteria.add(Restrictions.eq("parentPagePanel", relation));
			criteria.addOrder(Order.asc("order"));
			List list = criteria.list();
			return list;
		}
		/**
		 * �õ���ӦPartPagePanelRelation������
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public List findPartPagePanelRelationByRelationObject(
				PagePanelRelation pagePanelRelation) {
			List listProperty = new ArrayList();
//			listProperty.add(0, pagePanelRelation.getIsDisplay());
//			listProperty.add(1, pagePanelRelation.getReadonly());
//			listProperty.add(2, pagePanelRelation.getTitleDisplayFlag());
//			listProperty.add(3, pagePanelRelation.getPagePanel().getXtype());
			
			HashMap<String , Object> relation = new HashMap<String , Object>();
			relation.put("display", pagePanelRelation.getIsDisplay()!=null ? pagePanelRelation.getIsDisplay():"");
			relation.put("readonly", pagePanelRelation.getReadonly()!=null ? pagePanelRelation.getReadonly():"");
			relation.put("titleDisplay", pagePanelRelation.getTitleDisplayFlag()!=null ? pagePanelRelation.getTitleDisplayFlag():"");
			relation.put("typeId", pagePanelRelation.getPagePanel().getXtype().getId()!=null ? pagePanelRelation.getPagePanel().getXtype().getId():"");
			
			listProperty.add(relation);
			return listProperty;
		}
		
	
}
