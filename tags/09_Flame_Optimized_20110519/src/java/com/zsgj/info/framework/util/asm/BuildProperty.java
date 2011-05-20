package com.zsgj.info.framework.util.asm;

public class BuildProperty {
	
	/*×Ö¶ÎÃû³Æ*/
	private String name;
	/*×Ö¶ÎÀàÐÍ*/
	private String type;
	
	public BuildProperty()
	{
		
	}
		
	public BuildProperty(String name,String type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
