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
	 * 删除时候改变序号（minIndex为本身的序号,nodeIndex为父节点下的子节点数目,ordParentId是parentId),重点在于得到唯一确定pagePanelRelation
	 * 删除分为集中情况，第一就是在第一层并且没有孩子节点的时候，只需要把序号大于它的减一即可;
	 * 第二种就是在第一层有孩子节点的时候，同第一种情况
	 * 第三种就是不再第一层没有孩子节点,
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public void changeOrderByDelete(String relationId , int minIndex, int nodeIndex) {
		
		PagePanelRelation pagePanelRelation = this.findPagePanelRelationById(relationId);
		PagePanel currentPagePanel =  pagePanelRelation.getParentPagePanel(); 
		//要更新哪个节点		
		
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
	 * 删除叶子节点,重点在于根结点是谁
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
	 * 当删根结点的时候，我会全部一个一个删除不用考虑任何顺序位
	 * @Methods Name removeRootNode
	 * @Create In Dec 19, 2008 By Administrator
	 * @param pageModelPanel void
	 */
	public void removeRootNode(PagePanelRelation pagePanelRelation){
		super.removeById(PagePanelRelation.class, pagePanelRelation.getId());
		super.flush();
	}
	
	/**
	 * 删除非叶子节点,重点在于判断是不是删除了叶子节点，如果其他节点的父节点不是该节点则说明是叶子节点
	 * ，否则为非叶子节点
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
	 * 面板移动之后保存(oldParentId为pagePanelRelation的实体id )
	 * 要分为新旧父节点为同一父节点和不同父节点的情况；
	 * 其中新旧父节点为同一父节点又分为都在第一层的时候；和新节点在第一层而旧父节点不再第一层；和新父节点不再第一层而旧父节点在第一层；
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	public void savePagePanelMove(PagePanel currentPanel,PagePanel pagePanel, String oldParentId, String newParentId, String nodeIndex) {
		//这个排序很简单，不用管新父节点的问题，也不用管分层的问题;但是要注意不能拖到下面去
		
		if(oldParentId.equals(newParentId) ){//&& oldParentId!=newParentId
			
			PagePanelRelation pagePanelRelation = this.findPagePanelRelation(pagePanel, currentPanel);
			Long nowId = pagePanelRelation.getId();
			int minIndex = pagePanelRelation.getOrder();
			int maxIndex = Integer.parseInt(nodeIndex);
			
			if(minIndex < maxIndex){
				// 当要移动的节点的序号小于要移动到的目标序号，则下移
				this.downNode(nowId, minIndex, maxIndex);
			}
			pagePanelRelation.setOrder(Integer.parseInt(nodeIndex));
			pagePanelRelation.setParentPagePanel(currentPanel);
			this.savePagePanelRelation(pagePanelRelation);
		}
		
	}
	/**
	 * 拖动的时候保存相应的panel
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
			throw new ServiceException("保存PageModel时发生异常");
		}		
		return result;
	}
	

	/**
	 * 下移(主要关心的是该节点的下面的节点序号，本身在移动之后处理),参数最好传递panelId
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
		public void downNode(Long currentPanelRelationId, Integer minIndex,
			Integer maxIndex) {
			
		//下移的重点在于它之后的节点的排序；首先必须确定当前节点			
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
		 * 上移
		 * @Methods Name findPagePanelRelation
		 * @Create In Dec 18, 2008 By Administrator
		 * @param parent
		 * @return List<PagePanelRelation>
		 */
		public void upNode(Long currentPanelRelationId, Integer minIndex,
				Integer maxIndex) {
			//下移的重点在于它之后的节点的排序；首先必须确定当前节点	
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
				throw new ServiceException("删除PagePanel发生异常");
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
			criteria.setFetchMode("pagePanel", FetchMode.JOIN); //防止延迟加载主动加载关联对象	
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
		 * 得到相应PartPagePanelRelation的属性
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
