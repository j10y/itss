package com.zsgj.info.bussutil.protal.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zsgj.info.bussutil.protal.dao.PortalColumnDao;
import com.zsgj.info.bussutil.protal.dao.PortalContainerDao;
import com.zsgj.info.bussutil.protal.dao.PortalDao;
import com.zsgj.info.bussutil.protal.dao.PortetSubscribeDao;
import com.zsgj.info.bussutil.protal.dao.PortletDao;
import com.zsgj.info.bussutil.protal.entity.Portal;
import com.zsgj.info.bussutil.protal.entity.PortalColumn;
import com.zsgj.info.bussutil.protal.entity.PortalColumnTemplate;
import com.zsgj.info.bussutil.protal.entity.PortalContainer;
import com.zsgj.info.bussutil.protal.entity.PortalStyle;
import com.zsgj.info.bussutil.protal.entity.Portlet;
import com.zsgj.info.bussutil.protal.entity.PortletSubscribe;
import com.zsgj.info.bussutil.protal.service.PortalContainerService;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.util.json.StringPool;

public class PortalContainerServiceImpl extends BaseService implements
		PortalContainerService {
	private PortalContainerDao portalContainerDao = null;
	private PortalColumnDao portalColumnDao = null;
	private PortalDao portalDao = null;
	private PortletDao portletDao = null;
	private PortetSubscribeDao portetSubscribeDao;

	public PortletDao getPortletDao() {
		return portletDao;
	}

	public void setPortletDao(PortletDao portletDao) {
		this.portletDao = portletDao;
	}

	public PortalColumnDao getPortalColumnDao() {
		return portalColumnDao;
	}

	public void setPortalColumnDao(PortalColumnDao portalColumnDao) {
		this.portalColumnDao = portalColumnDao;
	}

	public PortalContainer getPortalContainerByUserId(Serializable userId) {
		// TODO Auto-generated method stub
		return this.getPortalContainerDao().getPortalContainerByUserId(userId);
	}

	public void removePortalContainer(Serializable portalContainerId) {
		// TODO Auto-generated method stub
		// this.getPortalContainerDao().removeObject(entity)
	}

	/** 
	 * �û���һ��portal�Ķ���.��portlet 
	 **/ 
	public void saveDefaultUserPortalContainer(Serializable userId) {
		// TODO Auto-generated method stub
		UserInfo user = (UserInfo) this.find(UserInfo.class, String.valueOf(userId));
		PortalColumnTemplate pct = (PortalColumnTemplate) this
				.getPortalColumnDao().getSystemDefaultPortalColumnTemplate();
		
		/*PortalStyle ps = (PortalStyle) this.getPortalContainerDao().get
				.loadAllObjects(PortalStyle.class).get(0);*/ //���Ļ���DAO����loadAllObjects����������Dao����Ҳ����

		//�˾�ԭ��Ӧ���ǣ���һ�θ��û�portal�����ȥһ����ʽ���û� peixf
		PortalStyle ps = (PortalStyle) super.findAll(PortalStyle.class).get(0);
		
		
		PortalContainer pc = new PortalContainer();
		pc.setName(user.getUserName() + "-" + StringPool.PORTAL_DEFAULT_PORTAL_NAME);  //user.getName() 
		pc.setUserInfo(user);
		pc.setPortalStyle(ps);
		Portal portal = new Portal();
		portal.setPortalContainer(pc);
		portal.setName(StringPool.PORTAL_DEFAULT_PORTAL_NAME);
		portal.setPortalColumnTemplate(pct);
		String columnScale = pct.getColumnScale();
		String[] scales;
		if (StringUtils.isEmpty(columnScale)) {
			columnScale = "33:33:33";
		}
		if (columnScale.contains(StringPool.CHARACTER_COLON)) {
			scales = columnScale.split(StringPool.CHARACTER_COLON);
		} else {
			scales = new String[] { columnScale };
		}
		for (int i = 0; i < scales.length; i++) {
			PortalColumn portalColumn = new PortalColumn();
			portalColumn.setPortal(portal);
			portalColumn.setName(StringPool.PORTAL_DEFAULT_PORTAL_NAME + "_"
					+ i);
			portalColumn.setSingleColumnScale(scales[i]);
			this.getPortalColumnDao().saveOrUpdate(portalColumn);
		}
		/**
		 * ��portal �ĵ�һ��portalColumn�зֲ�portlet,����ж��,���ǽ������ֲܷ�
		 */
		this.getPortalDao().saveOrUpdatePortal(portal);
		this.getPortalContainerDao().saveOrUpdate(pc);
	}

	public void saveOrUpdatePortalContainer(PortalContainer portalContainer) {
		// TODO Auto-generated method stub
		this.getPortalContainerDao().saveOrUpdate(portalContainer);
	}

	public PortalContainerDao getPortalContainerDao() {
		return portalContainerDao;
	}

	public void setPortalContainerDao(PortalContainerDao portalContainerDao) {
		this.portalContainerDao = portalContainerDao;
	}

	public Page getPortalAllStyles() {
		// TODO Auto-generated method stub
		return this.getPortalContainerDao().getPortalAllStyles();
	}

	public String getPortalAllStylesJson() {
		// TODO Auto-generated method stub
		return this.getPortalAllStyles().json();
	}

	public boolean isExistsUserPortalContainer(Serializable userId) {
		// TODO Auto-generated method stub
		boolean result = false;
		PortalContainer pc = this.getPortalContainerByUserId(userId);
		if (null != pc) {
			result = true;
		}
		return result;
	}

	public PortalDao getPortalDao() {
		return portalDao;
	}

	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}
	/**
	 * ��ʼ���û�protal��Ϣ
	 */
	public void saveDefaultUserDefinePortalContainer(Serializable userId) {
		// TODO Auto-generated method stub
		UserInfo user = (UserInfo) super.find(UserInfo.class, String.valueOf(userId)); //BaseSevice�еķ���
		//User user = (User) this.loadObject(User.class, userId);
		//��ȡprotalĬ�ϲ���
		PortalColumnTemplate pct = (PortalColumnTemplate) this
				.getPortalColumnDao().getSystemDefaultPortalColumnTemplate();
		PortalStyle ps = null; //(PortalStyle) this.getPortalContainerDao()
								//.loadAllObjects(PortalStyle.class).get(0);
		
		List portalStyles = super.findAll(PortalStyle.class);
		if(!portalStyles.isEmpty()){
			ps = (PortalStyle) portalStyles.iterator().next();
		}
		//����protal����
		PortalContainer pc = new PortalContainer();
		pc.setName(user.getUserName() + "-"
				+ StringPool.PORTAL_DEFAULT_PORTAL_NAME);
				
		pc.setUserInfo(user);
		pc.setPortalStyle(ps);
		pc = this.getPortalContainerDao().saveOrUpdate(pc);
		//����protal
		Portal portal = new Portal();
		portal.setPortalContainer(pc);
		portal.setName(StringPool.PORTAL_DEFAULT_PORTAL_NAME);
		portal.setPortalColumnTemplate(pct);
		portal = this.getPortalDao().saveOrUpdatePortal(portal);
		String columnScale = pct.getColumnScale();
		String[] scales;
		if (StringUtils.isEmpty(columnScale)) {
			columnScale = "33:33:33";
		}
		if (columnScale.contains(StringPool.CHARACTER_COLON)) {
			scales = columnScale.split(StringPool.CHARACTER_COLON);
		} else {
			scales = new String[] { columnScale };
		}
		List<PortalColumn> pcs = new ArrayList();
		for (int i = 0; i < scales.length; i++) {
			PortalColumn portalColumn = new PortalColumn();
			portalColumn.setPortal(portal);
			portalColumn.setName(StringPool.PORTAL_DEFAULT_PORTAL_NAME + "_" + i);
			portalColumn.setSingleColumnScale(scales[i]);
			portalColumn = this.getPortalColumnDao().saveOrUpdate(portalColumn);
			pcs.add(portalColumn);
		}
		
		//add by lee for ����Ĭ���û�portal����
		String defaultproletStr = PropertiesUtil.getProperties("system.portal.default.prolet");
		String[] defaultprolets = null;
		if(StringUtils.isNotBlank(defaultproletStr)&&defaultproletStr.contains(StringPool.ENTITY_IDS_SPLIT_WITH)){
			defaultprolets = defaultproletStr.split(StringPool.ENTITY_IDS_SPLIT_WITH);
		}
		PortalColumn firstportalColumn = pcs.get(0);
		if(defaultprolets!=null){
			for (int i = 0; i < defaultprolets.length; i++) {
				Portlet portlet = (Portlet) super.find(Portlet.class, defaultprolets[i]);
				PortletSubscribe pss = new PortletSubscribe();
				pss.setPortalColumn(firstportalColumn);
				pss.setName(portlet.getName());
				pss.setProtlet(portlet);
				pss.setOrder(i);
				super.save(pss);
			}
		}
		//add by lee for ����Ĭ���û�portal����
	}

	public void saveUserPortletSubscribe(Serializable portalId,
			Serializable portletId) {
		// TODO Auto-generated method stub
		PortalColumn portalColumn = (PortalColumn) this.getPortalColumnDao()
				.getPortalColumnByPortalId(portalId).getData().get(0); //getItems
		Portlet portlet = (Portlet) super.find(Portlet.class, String.valueOf(portletId)); //this.loadObject
		PortletSubscribe ps = new PortletSubscribe();
		int order = this.getPortletDao().getNextPortletSubscribeOrder(
				portalColumn.getId());
		ps.setPortalColumn(portalColumn);
		ps.setProtlet(portlet);
		ps.setName(portlet.getName());
		ps.setOrder(order);
		this.save(ps);
	}

	public void saveChangePortletPosition(Serializable portletId,
			Serializable portalColumnId, Serializable portalId, int index) {
		List portletSubscribes = this.getPortletDao()
				.getPortletSubscribeByPortalColumnId(portalColumnId).getData();
		PortalColumn portalColumn = (PortalColumn) super.find(
				PortalColumn.class, String.valueOf(portalColumnId)); //this.getObject
//		 ȡ�ý�Ҫ�ƶ�����Ŀ��column��ԭ�е�portlets
		List oldPortlets = this.getPortletDao().getPortletsByPortalColumnId(
				portalColumnId).getData();
		Portlet portlet = null;
		PortletSubscribe oldPortletSubscribe = null;
		// ������ڱ�column���ƶ�,�ӱ�column��portlets��ȡ�õ�ǰ�ƶ���portlet
		for (int i = 0; i < portletSubscribes.size(); i++) {
			PortletSubscribe item = (PortletSubscribe) portletSubscribes.get(i);
			if (item.getProtlet().getId().equals(portletId)) {
				portlet = item.getProtlet();
				oldPortletSubscribe = item;
				break;
			}
		}
		
		
		// ���û���ڵ�ǰcolumn��ȡ��portlet,˵���ǿ�column�ƶ�
		if (null == portlet) {
			portlet = (Portlet) this.getPortletDao().getObject(Portlet.class,
					new Long(portletId.toString()));
			// ȡ��ԭcolumn�жԵ�ǰ�ƶ�portlet�Ķ���.
			oldPortletSubscribe = this.getPortletDao()
					.getPortletSubscribeByPortalAndPortalColumnAndPortlet(
							portletId, portalColumnId, portalId);
			// ��ԭ����colum�е�portlet��������
			// ȡ��ԭcolumn�е�����portlet
			PortalColumn oldPortalColumn = oldPortletSubscribe
					.getPortalColumn();
			List oldColumnPortlets = this.getPortletDao()
					.getPortletsByPortalColumnId(oldPortalColumn.getId())
					.getData();
			// ȡ��ԭportlet����ʱ������
			int oldPortletSubscribeIndex = oldPortletSubscribe.getOrder();
			//��ԭcolumnʣ��porlet����������.
			if (oldPortletSubscribeIndex < oldColumnPortlets.size() - 1) {
				for (int i = oldPortletSubscribeIndex + 1; i < oldColumnPortlets
						.size(); i++) {
					Portlet pItem = (Portlet) oldColumnPortlets.get(i);
					PortletSubscribe cops = this.portletDao
							.getPortletSubscribeByPortletAndPortleColumn(
									oldPortalColumn.getId(), pItem.getId());
					cops.setOrder(i - 1);
					this.save(cops);
				}
			}
			Long refersh = oldPortletSubscribe.getRefresh();
			if (null != oldPortletSubscribe) {
				this.remove(oldPortletSubscribe);
			}
			PortletSubscribe ps = new PortletSubscribe();
			ps.setName(portlet.getName());
			ps.setPortalColumn(portalColumn);
			ps.setProtlet(portlet);
			ps.setOrder(index);
			ps.setRefresh(refersh);
			
			//�����porlet�����뵽�Ѵ��ڶ��portlet��column�м�
			if (index < oldPortlets.size()) {
				for (int i = index; i < oldPortlets.size(); i++) {
					Portlet item = (Portlet) oldPortlets.get(i);
					PortletSubscribe ops = this.portletDao
							.getPortletSubscribeByPortletAndPortleColumn(
									portalColumnId, item.getId());
					if (null != ops) {
						ops.setOrder(i + 1);
						this.save(ops);
					}
				}
			}
			this.save(ps);
		} else {
			// �ڱ�column���ƶ�
			int oldIndex = oldPortletSubscribe.getOrder();
			// ����������ƶ�
			if (oldIndex > index) {
				for (int i = index; i < oldIndex; i++) {
					Portlet item = (Portlet) oldPortlets.get(i);
					PortletSubscribe ops = this.portletDao
							.getPortletSubscribeByPortletAndPortleColumn(
									portalColumnId, item.getId());
					if (null != ops) {
						ops.setOrder(i + 1);
						this.save(ops);
					}
				}
			} else {
				// ����������ƶ�
				for (int i = oldIndex + 1; i <= index; i++) {
					Portlet item = (Portlet) oldPortlets.get(i);
					PortletSubscribe ops = this.portletDao
							.getPortletSubscribeByPortletAndPortleColumn(
									portalColumnId, item.getId());
					if (null != ops) {
						ops.setOrder(i - 1);
						this.save(ops);
					}
				}
			}
			oldPortletSubscribe.setOrder(index);
			this.save(oldPortletSubscribe);
		}

	}
	
	public void saveChangePortletRefersh(Serializable portletId,
			Serializable portalColumnId, Long refershData){
		PortletSubscribe porlets = this.portletDao
		.getPortletSubscribeByPortletAndPortleColumn(portalColumnId, portletId);
		if(null != porlets){
			porlets.setRefresh(refershData);
			this.save(porlets);
		}
		
	}
	
	public PortletSubscribe getPortletSubscribeByPortletAndPortleColumn(Serializable portletId,
			Serializable portalColumnId){
		PortletSubscribe porlets = this.portletDao
		.getPortletSubscribeByPortletAndPortleColumn(portalColumnId, portletId);
		
		return porlets;
	}
	public void removeUserPorletSubscribe(Serializable portletId,
			Serializable portalId) {
		
		/*String hql = "from "
				+ PortletSubscribe.class.getName()
				+ " p where p.protlet.id=:portletId and p.portalColumn.portal.id=:portalId";
		List items = this.getAllByHQL(hql, new String[] { "portletId",
				"portalId" }, new Object[] { portletId, portalId }); 
		
		if (CollectionUtils.isNotEmpty(items)) {
			PortletSubscribe portletSubscribe=(PortletSubscribe) items.get(0);
			String sql="from "+PortletSubscribe.class.getName()+" p where p.portalColumn.id=:portalColumnId order by p.order asc";
			List list=this.getAllByHQL(sql, "portalColumnId", portletSubscribe.getPortalColumn().getId());
			if(portletSubscribe.getOrder()<list.size()){
				for(int i=portletSubscribe.getOrder()+1;i<list.size();i++){
					PortletSubscribe item=(PortletSubscribe) list.get(i);
					item.setOrder(i-1);
					this.save(item);
				}
			}
			this.remove(items);
		}������дDAO����*/
		
		portetSubscribeDao.removeUserPorletSubscribe(portletId, portalId);
	}

	public Page getNewerPortalContainers(int startIndex,
			int pageSize) {
		return this.getPortalContainerDao().getNewerPortalContainers(startIndex, pageSize);
	}

	public PortetSubscribeDao getPortetSubscribeDao() {
		return portetSubscribeDao;
	}

	public void setPortetSubscribeDao(PortetSubscribeDao portetSubscribeDao) {
		this.portetSubscribeDao = portetSubscribeDao;
	}
}
