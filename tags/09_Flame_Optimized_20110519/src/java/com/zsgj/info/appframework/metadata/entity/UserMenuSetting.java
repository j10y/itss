package com.zsgj.info.appframework.metadata.entity;
//package com.digitalchina.info.appframework.metadata.entity;
//
//import com.digitalchina.info.appframework.template.entity.Menu;
//import com.digitalchina.info.appframework.template.entity.MenuTemplate;
//import com.digitalchina.info.framework.dao.BaseObject;
//import com.digitalchina.info.framework.security.entity.UserInfo;
//
///**
// * 用户菜单设定实体
// * @Class Name UserMenuSetting
// * @Author peixf
// * @Create In 2008-4-3
// */
//public class UserMenuSetting2 extends BaseObject{
//	private static final long serialVersionUID = 5859792694474090434L;
//	
//	private Long id;
//	private UserInfo userInfo;
//	
//	private MenuTemplate template;
//	private Menu menu;
//	private Integer menuOrder;
//	
//	private Integer enabled;
//
//	public Integer getEnabled() {
//		return enabled;
//	}
//	public void setEnabled(Integer enabled) {
//		this.enabled = enabled;
//	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
//	public UserInfo getUserInfo() {
//		return userInfo;
//	}
//	public void setUserInfo(UserInfo userInfo) {
//		this.userInfo = userInfo;
//	}
//	public Menu getMenu() {
//		return menu;
//	}
//	public void setMenu(Menu menu) {
//		this.menu = menu;
//	}
//	public Integer getMenuOrder() {
//		return menuOrder;
//	}
//	public void setMenuOrder(Integer menuOrder) {
//		this.menuOrder = menuOrder;
//	}
//	public MenuTemplate getTemplate() {
//		return template;
//	}
//	public void setTemplate(MenuTemplate template) {
//		this.template = template;
//	}
//	@Override
//	public int hashCode() {
//		final int PRIME = 31;
//		int result = 1;
//		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
//		result = PRIME * result + ((menu == null) ? 0 : menu.hashCode());
//		result = PRIME * result + ((template == null) ? 0 : template.hashCode());
//		result = PRIME * result + ((userInfo == null) ? 0 : userInfo.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		final UserMenuSetting other = (UserMenuSetting) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (menu == null) {
//			if (other.menu != null)
//				return false;
//		} else if (!menu.equals(other.menu))
//			return false;
//		if (template == null) {
//			if (other.template != null)
//				return false;
//		} else if (!template.equals(other.template))
//			return false;
//		if (userInfo == null) {
//			if (other.userInfo != null)
//				return false;
//		} else if (!userInfo.equals(other.userInfo))
//			return false;
//		return true;
//	}
//	
//}
