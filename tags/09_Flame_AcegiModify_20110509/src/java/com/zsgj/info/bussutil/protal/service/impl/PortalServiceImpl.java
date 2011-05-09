package com.zsgj.info.bussutil.protal.service.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.zsgj.info.bussutil.protal.dao.PortalColumnDao;
import com.zsgj.info.bussutil.protal.dao.PortalDao;
import com.zsgj.info.bussutil.protal.dao.PortletDao;
import com.zsgj.info.bussutil.protal.entity.Portal;
import com.zsgj.info.bussutil.protal.entity.PortalColumn;
import com.zsgj.info.bussutil.protal.entity.PortalColumnTemplate;
import com.zsgj.info.bussutil.protal.entity.PortletSubscribe;
import com.zsgj.info.bussutil.protal.service.PortalService;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.info.framework.util.json.StringPool;

public class PortalServiceImpl extends BaseService implements PortalService {
	private PortalDao portalDao = null;
	private PortalColumnDao portalColumnDao = null;
	private PortletDao portletDao = null;

	public PortalDao getPortalDao() {
		return portalDao;
	}

	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}

	public Page getAllPortalsByPortalContainerId(
			Serializable portalContainerId) {
		// TODO Auto-generated method stub
		return this.getPortalDao().getAllPortalsByPortalContainerId(
				portalContainerId);
	}

	public void removePortal(Serializable id) {
		Portal portal = (Portal) this.getPortalDao()
				.getObject(Portal.class, id);
		if (null != portal) {
			Set portalColumns = portal.getPortalColumns();
			if (CollectionUtils.isNotEmpty(portalColumns)) {
				Iterator it = portalColumns.iterator();
				while (it.hasNext()) {
					PortalColumn portalColumn = (PortalColumn) it.next();
					Set portletSubscribes = portalColumn.getPortletSubscribes();
					if (CollectionUtils.isNotEmpty(portletSubscribes)) {
						Iterator inIt = portletSubscribes.iterator();
						while (inIt.hasNext()) {
							PortletSubscribe pitem = (PortletSubscribe) inIt
									.next();
							this.getPortletDao().remove(pitem);
						}
					}
					super.remove(portalColumn);
				}

			}
			this.getPortalDao().removePortal(portal);
		}
		// TODO Auto-generated method stub
		// this.getPortalDao().removePortal(id);
	}

	public void saveOrUpdatePortal(Portal portal) {
		// TODO Auto-generated method stub
		this.getPortalDao().saveOrUpdatePortal(portal);
	}

	public String getAllPortalsByPortalContainerIdJson(
			Serializable portalContainerId) {
		// TODO Auto-generated method stub
		return this.getAllPortalsByPortalContainerId(portalContainerId).json();
	}

	public void savePortalColumnTemplateChange(Serializable portalId,
			Serializable portalColumnTemplateId) {
		// TODO Auto-generated method stub
		/*Portal portal = (Portal) this.getPortalDao().loadObject(Portal.class,
				portalId);*/
		
		Portal portal = (Portal) super.find(Portal.class, String.valueOf(portalId));
		
		/*PortalColumnTemplate newPct = (PortalColumnTemplate) this
				.getPortalColumnDao().getObject(PortalColumnTemplate.class,
						portalColumnTemplateId);*/
		
		PortalColumnTemplate newPct = (PortalColumnTemplate) 
						super.find(PortalColumnTemplate.class, String.valueOf(portalColumnTemplateId));
		
		PortalColumnTemplate oldPct = portal.getPortalColumnTemplate();
		portal.setPortalColumnTemplate(newPct);
		String columnScale = newPct.getColumnScale();
		String oldColumnScale = oldPct.getColumnScale();
		String[] scales = this.getColumnScalesArray(columnScale);
		String[] oldScales = this.getColumnScalesArray(oldColumnScale);
		List portalColumns = this.getPortalColumnDao()
				.getPortalColumnByPortalId(portalId).getData();
		if (scales.length >= oldScales.length) {
			Object[] sub = ArrayUtils.subarray(scales, 0, oldScales.length);
			for (int i = 0; i < sub.length; i++) {
				PortalColumn item = (PortalColumn) portalColumns.get(i);
				item.setSingleColumnScale(sub[i].toString());
				this.getPortalColumnDao().saveOrUpdate(item);
			}
			for (int i = oldScales.length; i < scales.length; i++) {
				PortalColumn item = new PortalColumn();
				item.setPortal(portal);
				item.setName(StringPool.PORTAL_DEFAULT_PORTAL_NAME + "_" + i);
				item.setSingleColumnScale(scales[i]);
				this.getPortalColumnDao().saveOrUpdate(item);
			}

		} else {
			for (int i = 0; i < scales.length; i++) {
				PortalColumn item = (PortalColumn) portalColumns.get(i);
				item.setSingleColumnScale(scales[i]);
				this.getPortalColumnDao().saveOrUpdate(item);
			}
			// 取得最后一个没有被删除的portal column
			PortalColumn lastPortalColumn = (PortalColumn) portalColumns
					.get(scales.length - 1);
			for (int i = scales.length; i < portalColumns.size(); i++) {
				// 删除前,要将准备删除的portal column中的portlet转移到最后一个未被删除的portalColumn中去
				PortalColumn item = (PortalColumn) portalColumns.get(i);
				Set portletSubscribes = item.getPortletSubscribes();
				if (CollectionUtils.isNotEmpty(portletSubscribes)) {
					Iterator it = portletSubscribes.iterator();
					while (it.hasNext()) {
						PortletSubscribe pItem = (PortletSubscribe) it.next();
						Integer order = 0;
						if (CollectionUtils.isNotEmpty(lastPortalColumn
								.getPortletSubscribes())) {
							 order = lastPortalColumn
									.getPortletSubscribes().size();
						}
						pItem.setPortalColumn(lastPortalColumn);
                        pItem.setOrder(order);
                        this.save(pItem);
					}
				}
				this.remove(item);
			}
		}
		this.getPortalDao().saveOrUpdatePortal(portal);
		/**
		 * 取得属于本portal的所有portalColumn
		 */
		// List
		// portalColumns=this.getPortalColumnDao().getPortalColumnByPortalId(portalId).getItems();
		/**
		 * 取得本portal中所有的portlet
		 */
		// List
		// portlets=this.getPortletDao().getAllPortletsByPortalId(portalId).getItems();
	}

	private String[] getColumnScalesArray(String columnScale) {
		String[] scales;

		if (StringUtils.isEmpty(columnScale)) {
			columnScale = "33:33:33";
		}
		if (columnScale.contains(StringPool.CHARACTER_COLON)) {
			scales = columnScale.split(StringPool.CHARACTER_COLON);
		} else {
			scales = new String[] { columnScale };
		}
		return scales;
	}

	public PortalColumnDao getPortalColumnDao() {
		return portalColumnDao;
	}

	public void setPortalColumnDao(PortalColumnDao portalColumnDao) {
		this.portalColumnDao = portalColumnDao;
	}

	public PortletDao getPortletDao() {
		return portletDao;
	}

	public void setPortletDao(PortletDao portletDao) {
		this.portletDao = portletDao;
	}

	public Portal getLastPortalByUserId(Serializable userId) {
		// TODO Auto-generated method stub
		return this.getPortalDao().getLastPortalByUserId(userId);
	}

}
