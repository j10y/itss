package com.xpsoft.padoa.test.entity;

/**
 * 
 * @Class Name User
 * @Author likang
 * @Create In Jul 20, 2010
 */
public class User {
	public Integer id;

	private Children children;
	
	public User() {
		children = new Children();
	}
	public Children getChildren() {
		return children;
	}

	public void setChildren(Children children) {
		this.children = children;
	}

	

	/**
	 * @hibernate.id column="id" type="java.lang.Integer"
	 *               generator-class="native"
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @hibernate.property column="password" length="10"
	 */
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	/**
//	 * @hibernate.property column="username" length="10"
//	 */
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
}
