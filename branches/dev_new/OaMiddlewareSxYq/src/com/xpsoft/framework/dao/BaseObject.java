package com.xpsoft.framework.dao;

import java.io.Serializable;

/**
 * 实体基类，要求所有实体必须继承此类，
 * 所有实体的主键必须命名为Long 类型的id,并提供相应的getter和setter方法。<br>
 * @Class Name BaseObject
 * @Author likang
 * @Create In Jul 21, 2010
 */
public abstract class BaseObject implements Serializable{

	private Long id;
	
	
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
		return true;
	}

    @Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
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

}
