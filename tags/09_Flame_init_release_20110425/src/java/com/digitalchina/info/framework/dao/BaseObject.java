package com.digitalchina.info.framework.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体基类，要求所有实体必须继承此类，
 * 所有实体的主键必须命名为Long 类型的id，并提供相应的getter和setter方法。<br>
 * @Class Name BaseObject
 * @Author peixf
 * @Create In 2008-3-3
 */
public abstract class BaseObject implements Serializable{

	private Long id;

	private String uuid;
	
	private String name;
	
//	private Date createDate;
//	
//	private Date submitDate;
//	
//	private Date modifyDate;
//	
//	private UserInfo createUser;
//	
//	private UserInfo modifyUser;
	
	/**
	 * 获取区分的类名称
	 * @Methods Name getDiscTableClass
	 * @Create In 2008-12-8 By sa
	 * @return String
	 */
	public String getDiscTableClass(){
		return this.getClass().getName();
	}
	
	public String getUniquePropName(){
		return null;
	}
	
	private Map<String, Object> extendProps = new HashMap<String, Object>(); 
	
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BaseObject other = (BaseObject) obj;
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

    @Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
    
    public BaseObject(){}
    
    public BaseObject(Long id){
    	this.id = new Long(id);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, Object> getExtendProps() {
		return extendProps;
	}

	public void setExtendProps(Map<String, Object> extendProps) {
		this.extendProps = extendProps;
	}

	public String getName() {
		return name;
	}
	
	public void setName(){
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setName(String name) {
		this.name = name;
	}
}
