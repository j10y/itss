package com.digitalchina.info.appframework.pagemodel.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.appframework.pagemodel.entity.PageBtnType;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelBtnService;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.exception.ServiceException;

public class PagePanelBtnServiceImpl extends BaseDao implements PagePanelBtnService{

	public PagePanelBtn findPanelBtnById(String panelBtnId) {
		PagePanelBtn result = null;
		try {
			result = super.get(PagePanelBtn.class, Long.valueOf(panelBtnId));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("��ȡPagePanelBtn�����쳣");
		}		
		return result;
	}
	
	public List<PagePanelBtn> findPanelBtnByPanel(PagePanel panel) {
		Criteria c = super.getCriteria(PagePanelBtn.class);
		c.add(Restrictions.eq("pagePanel", panel));
		//c.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
		List<PagePanelBtn> btns=c.list();
		return btns;
	}
	
	public void initPagePanelBtn(PagePanel pagePanel) {	
		//PagePanelType xtype = super.get(PagePanelType.class, pagePanel.getXtype().getId());
		PagePanelType xtype = pagePanel.getXtype();
		xtype = super.get(PagePanelType.class, xtype.getId());
		String xtypeName=xtype.getName();
		if(xtypeName.equals("editorgrid")){//�ɱ༭��

			PagePanelBtn addButton = new PagePanelBtn();
			addButton.setBtnName("���");
			addButton.setMethod("addByColumn");
			addButton.setImageUrl("add");
			addButton.setPagePanel(pagePanel);
			super.save(addButton);

			PagePanelBtn delButton = new PagePanelBtn();
			delButton.setBtnName("ɾ��");
			delButton.setMethod("removeByColumn");
			delButton.setImageUrl("remove");
			delButton.setPagePanel(pagePanel);
			super.save(delButton);
			
			PagePanelBtn saveButton = new PagePanelBtn();
			saveButton.setBtnName("����");
			saveButton.setMethod("saveForGridPanel");
			saveButton.setImageUrl("save");
			saveButton.setPagePanel(pagePanel);
			super.save(saveButton);
			
//			PagePanelBtn upLoadButton = new PagePanelBtn();
//			upLoadButton.setBtnName("�ϴ�");
//			upLoadButton.setMethod("upload");
//			upLoadButton.setImageUrl("upload");
//			upLoadButton.setPagePanel(pagePanel);
//			super.save(upLoadButton);
//			
//			PagePanelBtn exportButton = new PagePanelBtn();
//			exportButton.setBtnName("����");
//			exportButton.setMethod("export");
//			exportButton.setImageUrl("export");
//			exportButton.setPagePanel(pagePanel);
//			super.save(exportButton);
		}else if(xtypeName.equals("form")){
			PagePanelBtn saveButton = new PagePanelBtn();
			saveButton.setBtnName("����");
			saveButton.setMethod("saveForFormPanel");
			saveButton.setImageUrl("save");
			saveButton.setPagePanel(pagePanel);
			super.save(saveButton);
			
			PagePanelBtn resetButton = new PagePanelBtn();
			resetButton.setBtnName("����");
			resetButton.setMethod("resetPanel");
			resetButton.setImageUrl("reset");
			resetButton.setPagePanel(pagePanel);
			super.save(resetButton);
		}
	}
	
	public PageBtnType findPageBtnTypeByName(String keyName) {
		PageBtnType pageBtnType = null;
		Criteria c = super.getCriteria(PageBtnType.class);
		c.add(Restrictions.eq("name", keyName));
		List list = c.list(); //��ֹ���ظ���¼
		if(!list.isEmpty()){
			pageBtnType = (PageBtnType) list.iterator().next();
		}
		return pageBtnType;
	}

	public PagePanelBtn savePanelBtn(Map buttonMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
