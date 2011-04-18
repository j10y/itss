package com.digitalchina.info.framework.security.entity;

import java.io.Serializable;
import java.util.List;

import org.acegisecurity.GrantedAuthority;

/**
 * 资源授权实体
 * @Class Name ResourceDetail
 * @Author 张鹏
 * @Create In Sep 28, 2008
 */
public class ResourceDetail  implements Serializable{

	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 4717391566748179088L;

	private String resString;

	private String resType;

	private GrantedAuthority[] authorities;
	
	public ResourceDetail(){}

	public ResourceDetail(String resString, String resType, GrantedAuthority[] authorities) {
		if (resString == null || "".equals(resString)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to resource string");
		}
		if (resType == null || "".equals(resType)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to resource type");
		}
		this.resString = resString;
		this.resType = resType;
		setAuthorities(authorities);
	}

	public boolean equals(Object rhs) {
		if (!(rhs instanceof ResourceDetail))
			return false;
		ResourceDetail resauth = (ResourceDetail) rhs;
		if (resauth.getAuthorities().length != getAuthorities().length)
			return false;
		for (int i = 0; i < getAuthorities().length; i++) {
			if (!getAuthorities()[i].equals(resauth.getAuthorities()[i]))
				return false;
		}
		return getResString().equals(resauth.getResString()) && getResType().equals(resauth.getResType());
	}

	public int hashCode() {
		int code = 168;
		if (getAuthorities() != null) {
			for (int i = 0; i < getAuthorities().length; i++)
				code *= getAuthorities()[i].hashCode() % 7;
		}
		if (getResString() != null)
			code *= getResString().hashCode() % 7;
		return code;
	}

	public String getResString() {
		return resString;
	}

	public void setResString(String resString) {
		this.resString = resString;
	}

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		
		this.authorities = authorities;
	}
	
	public void setAuthorities(List roles) {
		
		if(authorities==null){
			authorities = new GrantedAuthority[roles.size()];
		}
		for(int i=0; i<roles.size(); i++){
			authorities[i] = (GrantedAuthority) roles.get(i);
		}
	}

}
