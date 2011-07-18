package com.xpsoft.framework.entity;


import com.xpsoft.framework.dao.BaseObject;

public class Sequence extends BaseObject{

	private String name;

	private long nextId;

	public Sequence() {
	}

	public Sequence(String name, long nextId) {
		this.name = name;
		this.nextId = nextId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNextId() {
		return nextId;
	}

	public void setNextId(long nextId) {
		this.nextId = nextId;
	}

}
