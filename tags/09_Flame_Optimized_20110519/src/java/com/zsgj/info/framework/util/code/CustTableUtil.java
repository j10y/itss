package com.zsgj.info.framework.util.code;
//package com.digitalchina.info.framework.util.code;
//
//import java.net.URL;
//import java.util.ArrayList;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.mapping.PersistentClass;
//
//import com.digitalchina.info.framework.util.asm.POBuildUtil;
//import com.digitalchina.info.framework.util.asm.render.FreemarkerRender;
//import com.digitalchina.info.framework.util.asm.render.RenderClass;
//import com.digitalchina.info.framework.util.asm.render.RenderProperty;
//import com.digitalchina.info.framework.util.asm.render.Templates;
//
//public class CustTableUtil {
//
//	public static void genNewTable(
//			SessionFactory sf, 
//			Configuration config,
//			String sourceClassPkg, 
//			String sourceClassName, 
//			String code, 
//			Class targetPkgClass){
//		
//		RuntimeCode c = new RuntimeCode();
//		
//		String newClass = c.genEntityAndClass(sourceClassPkg, sourceClassName, code, targetPkgClass);
//		
//
//		// 持久类对象描述
//		RenderClass rc = new RenderClass();
//		ArrayList list = new ArrayList();
//
//		RenderProperty property = new RenderProperty();
//		// 添加主键
//		property.setName("id");
//		property.setField("id");
//		property.setLength(new Integer(15));
//		property.setPrimary(true);
//		property.setType(Long.class.getName());
//		property.setSequence("SEQ_PERSON");
//
//		list.add(property);
//		// 添加一个name字段
//		property = new RenderProperty();
//		property.setName("userName");
//		property.setType(String.class.getName());
//		property.setField("userName");
//		property.setLength(new Integer(20));
//
//		list.add(property);
//
//		rc.setProperties(list);
//
//		String path = this.getClass().getResource("/").toString().replace("/",
//				"\\");
//		// 类名
//		String clazz = "com.digitalchina.itil.config.entity.Person";
//		rc.setClassName(clazz);
//		rc.setTableName("person");
//
//		// String classShortName = clazz.substring(clazz.lastIndexOf(".")+1);
//		String pkgdir = path + clazz.replace('.', '\\') + ".class";
//		int fileLength = "file:\\".length();
//		String fullClassDir = pkgdir.substring(fileLength);
//		// 开始生成class
//		POBuildUtil util = new POBuildUtil();
//		util.build(rc.getClassName(), fullClassDir, list);
//
//		// 开始生成hbm.xml
//		String hbnFile = "/" + clazz.replace('.', '/') + ".hbm.xml";
//		String hbmdir = fullClassDir.replace(".class", ".hbm.xml");
//		FreemarkerRender render = new FreemarkerRender();
//		render.render(rc, Templates.TEMPLATE_HIBERNATE3, hbmdir);
//		// URL url = this.getClass().getResource(hbmdir);
//
//		PersistentClass model = config.getClassMapping(clazz);
//		if (model == null) {
//			URL url = this.getClass().getResource(hbnFile);
//			config.addURL(url);
//			model = config.getClassMapping(clazz);
//		}
//		ssf.addPersistentClass(model, config.getMapping());
//
//	}
//}
