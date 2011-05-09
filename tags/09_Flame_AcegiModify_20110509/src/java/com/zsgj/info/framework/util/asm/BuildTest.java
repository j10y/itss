package com.zsgj.info.framework.util.asm;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.zsgj.info.framework.util.asm.render.FreemarkerRender;
import com.zsgj.info.framework.util.asm.render.RenderClass;
import com.zsgj.info.framework.util.asm.render.RenderProperty;
import com.zsgj.info.framework.util.asm.render.Templates;
public class BuildTest extends TestCase {

	/*
	public void testStringUtils()
	{
		System.out.println(WordUtils.capitalize("name"));
	}
	*/
	
	/*
	public void testBuild()
	{
		BuildProperty p_name = new BuildProperty("name",String.class.getName());
		BuildProperty p_sex = new BuildProperty("sex",SimpleProperty.class.getName());
		ArrayList list=  new ArrayList();
		list.add(p_name);
		list.add(p_sex);
		POBuildUtil util = new POBuildUtil();
		Class cls = util.build("com.mit.test.BuildTest", "e:\\test.class", list);
		PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(cls);
		for (int i=0;i<properties.length;i++)
		{
			System.out.println(properties[i].getName()+":"+properties[i].getPropertyType().getName());
		}
	}
	*/
	
	public void testBuildHBM()
	{
		RenderClass rc = new RenderClass();
		ArrayList list = new ArrayList();
		
		RenderProperty property = new RenderProperty();
		property.setName("oid");
		property.setField("oid");
		property.setLength(new Integer(15));
		property.setType(Long.class.getName());
		property.setSequence("SEQ_PERSON");
		
		list.add(property);
		
		property = new RenderProperty();
		property.setName("name");
		property.setType(String.class.getName());
		property.setField("name");
		property.setLength(new Integer(20));
		
		list.add(property);
		
		rc.setProperties(list);
		rc.setClassName("com.mit.test.Person");
		rc.setTableName("person");

		FreemarkerRender render = new FreemarkerRender();
		render.render(rc, Templates.TEMPLATE_HIBERNATE3, "e:\\person.hbm.xml");
	}
}
