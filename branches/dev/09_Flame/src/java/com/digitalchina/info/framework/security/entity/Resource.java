package com.digitalchina.info.framework.security.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * resource(资源)的概念,一个资源对应多个权限，
 * 资源分为ACL,URL,和FUNCTION三种。
 * 注意，URL和FUNTION的权限命名需要以AUTH_开头才会有资格参加投票, 
 * 同样的ACL权限命名需要ACL_开头。
 * @Class Name Resource
 * @Author peixf
 * @Create In 2008-3-5
 */
public class Resource extends BaseObject {
	private static final Long serialVersionUID = -7009425391300130055L;
	
	private static final String RES_TYPE_URL = "URL";
	private static final String RES_TYPE_FUNCTION = "FUNCTION";
	
	private Long id;
	private String name;
	private String type;
	private String className;
	private String methodName;
	private Module module;
	
	private Set authorizations = new HashSet(0);
	
	private String descn;

	public String getShortClassMethodName(){
		String result = "";
		String systemShortName = "knowledge";
		int dotIndex = this.className.indexOf(systemShortName);
		if(dotIndex!=-1){
			int from = dotIndex + systemShortName.length() + 1;
			String clazz = this.className.substring(from);
			String method = this.methodName;
			result = ".."+clazz + "." + method;
			return result;
		}
		return this.className+"/"+this.methodName;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Set getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set authorizations) {
		this.authorizations = authorizations;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((className == null) ? 0 : className.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Resource other = (Resource) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	
}
