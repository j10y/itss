package com.zsgj.info.framework.security.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.appframework.menu.entity.DeptMenu;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * 角色实体
 * @Class Name Role
 * @Author peixf
 * @Create In 2008-3-5
 * 单独选择部门，且部门数据查看范围为全部，即当做部门经理看待
 * 单独选择平台，且平台数据查看范围为全部，即当做平台经理看待
 * 既选择部门又选择平台，即当做平台产品经理看待 
 * 
 */
public class Role extends BaseObject{
	private static final long serialVersionUID = -7616650355555597545L;
	/**
	 * 可以查看所有
	 */
	//modify by lee for change property scope in 20090818 begin
	//private static final Integer VIEW_FLAG_ALL=1;
	public static final Integer VIEW_FLAG_ALL=1;
	/**
	 * 只查看自己
	 */
	//private static final Integer VIEW_FLAG_SELF=0;
	public static final Integer VIEW_FLAG_SELF=0;
	//modify by lee for change property scope in 20090818 end
	private Long id;
	
	private String keyword;
	
	private String name;
	
	private String descn;
	
	private Set authorizations = new HashSet(0);//一个角色包含多个授权
	
	private Set userInfos = new HashSet(0);
	
	private Department department;

	private DeptMenu deptMenu;
	
	private Integer dataViewFlag; //数据查看范围（部门）
	
	private Integer dataViewPlatformFlag; //数据查看范围（平台）

	private Platform platform ;
	
	private Region region;
	
	private Integer dataViewRegionFlag; //数据查看范围（区域）
	
	private transient boolean isChecked = false;
	
	private Province province;
	
	private Integer dataViewProvinceFlag; //数据查看范围（省份）


	/**
	 * @Return the Region region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * @Param Region region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * @Return the Integer dataViewRegionFlag
	 */
	public Integer getDataViewRegionFlag() {
		return dataViewRegionFlag;
	}

	/**
	 * @Param Integer dataViewRegionFlag to set
	 */
	public void setDataViewRegionFlag(Integer dataViewRegionFlag) {
		this.dataViewRegionFlag = dataViewRegionFlag;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Set getUserInfos() {
		return userInfos;
	}
	
	public Set getUsers() {
		return userInfos;
	}

	public void setUserInfos(Set userInfos) {
		this.userInfos = userInfos;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Set getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set authorizations) {
		this.authorizations = authorizations;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getDataViewFlag() {
		return dataViewFlag;
	}

	public void setDataViewFlag(Integer dataViewFlag) {
		this.dataViewFlag = dataViewFlag;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Integer getDataViewPlatformFlag() {
		return dataViewPlatformFlag;
	}

	public void setDataViewPlatformFlag(Integer dataViewPlatformFlag) {
		this.dataViewPlatformFlag = dataViewPlatformFlag;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Integer getDataViewProvinceFlag() {
		return dataViewProvinceFlag;
	}

	public void setDataViewProvinceFlag(Integer dataViewProvinceFlag) {
		this.dataViewProvinceFlag = dataViewProvinceFlag;
	}

	public DeptMenu getDeptMenu() {
		return deptMenu;
	}

	public void setDeptMenu(DeptMenu deptMenu) {
		this.deptMenu = deptMenu;
	}

}