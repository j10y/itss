package com.digitalchina.info.appframework.template.web.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.template.entity.UserMenuItem;
import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * DWR��ʽ�������˵�
 * @Class Name NavigateMenuManager
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
public class NavigateMenuManager {
	
	private UserTemplateMenuService userMenuService = (UserTemplateMenuService) getBean("userTemplateMenuService");
	
	/**
	 * �첽���²˵�����
	 * @param id
	 * @param title
	 * @return true-�޸ĳɹ� false-�޸�ʧ��
	 */
	public Boolean ajaxUpdateTitle(String id,String menuName){
		userMenuService.modifyMenuName(id, menuName);
		return true;
	}
	/**
	 * �첽ɾ�����ݣ�����������ڵ�
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id){
		userMenuService.removeNode(id);
	}
	/**
	 * �첽�ƶ�ָ���ڵ�
	 * @param id	ָ���Ľڵ��id
	 * @param oldParentId	�ڵ��ƶ�ǰ���ڵĸ��ڵ�
	 * @param newParentId	�ڵ��ƶ����Ŀ�길�ڵ�
	 * @param nodeIndex		�ڵ��ƶ����Ŀ��λ��
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex){
		userMenuService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
	}
	
	/**
	 * ���ý���Ƿ�ɼ�
	 * TODO
	 * Sep 4, 2008 By hp
	 * @param id
	 * @param enabled TODO
	 */
	public void ajaxEnableNode(String nodeId, String enabled){
		userMenuService.saveNodeEnabled(nodeId, enabled);
		
	}
	
	/**
	 * ����ָ���û��ĵ������ı���
	 * TODO
	 * Sep 8, 2008 By hp
	 * @param userId
	 * @return JSON �ַ�������ʽ:{["id":1,"name":"name1"],["id":2,"name":"name2"]}
	 */
	public String ajaxFindNavigateTitles(String userId){
		
		List<UserMenuItem> itemList = userMenuService.findAllMenuTitleByUserId(userId);
		String result = "";
		for(int i = 0; i< itemList.size(); i++){
			UserMenuItem item = (UserMenuItem)itemList.get(i);
			Long id = item.getId();
			String text = item.getMenuName();
			result += "[id :"+id+",text:\""+text+"\"],";
		}
		result = "["+result.substring(0, result.length()-1)+"]";
		
		return  result;
	}
	
	public List ajaxFindMenuNodes(String nodeId,String userId){
		List returnList = new ArrayList();
			List<UserMenuItem> itemList = userMenuService.findChildenByParentAndUserId(nodeId, userId);
			if (itemList != null && itemList.size() > 0) {
				for (int i = 0, len = itemList.size(); i < len; i++) {
					UserMenuItem item = (UserMenuItem) itemList.get(i);
					Map map = new HashMap();
					map.put("id", item.getId());
					map.put("text", item.getMenuName());
//					map.put("parentId", item.getParentId());
					map.put("leaf", item.getLeafFlag());
					map.put("expanded", true);
					returnList.add(map);
				}
			}	
			return  returnList;
		}
	
	
	/**
	 * ����spring����ķ���service
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
