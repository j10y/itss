package com.xpsoft.framework.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ibatis.common.resources.Resources;

import com.xpsoft.framework.dao.BaseObject;

/**
 * 实体工具类，提供生成实体的sqlMap和自动建表的功能
 * 如sqlMap功能不满足需要，则自己按情况添加
 * 建表的列长度，如不能满足需求，请自己在数据库中修改
 * @Class Name BuildBeanUtilForOracle
 * @Author likang
 * @Create In Aug 1, 2010
 */
public class BuildBeanUtilForOracleOneToOne {
	
	//配置文件的存放路径可以修改 
	//不需要文件名
	private static String targetFile = "";
	private static String DBDRIVER = null;
	private static String DBURL = null;
	private static String DBUSER = null;
	private static String DBPASSWORD = null;
	private static List waiJianTypeAliasAndResultMapList = new ArrayList();
	private static List waiJianSelectStringList = new ArrayList();
	
	public static void main(String[] args) throws Exception {
		//生成XML文件的方法 生成的文件会保存到targetFile目录下
//		buildSqlXmlForOracle(SignContInfo.class);
		//生成实体对应的表 和 Sequenece 表名为实体名的大写
//		excuteCreatTableForOracle(Test.class);
		//如果实体对应的表名不一样可以用该方法 一般情况下不使用该方法
//		excuteCreatTableAndSequenceByTableName(Test.class,"likang");
	}
	
	/**
	 * 可以指定本实体对应数据库连接的配置
	 */
	static{
		Properties props;
		try {
			props = Resources.getResourceAsProperties("ApplicationResources.properties");
			DBDRIVER = (String) props.get("jdbc.driverClassName");
			DBURL = (String) props.get("jdbc.url");
			DBUSER = (String) props.get("jdbc.username");
			DBPASSWORD = (String) props.get("jdbc.password");
			targetFile = (String) props.get("system.sqlMapXmlOutputTarget");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据类名生成 
	 * 实体名.sqlMap.xml
	 * 包含 BaseService中需要的所有数据库访问
	 * 列表如下：
	 * insert
	 * update
	 * delete
	 * deleteByIds
	 * deleteByLogic
	 * deleteObjectsByLogic
	 * selectAll
	 * selectById
	 * selectTotal
	 * selectPage
	 * selectTotalByParam
	 * selectPageByParam
	 * selectObjectsByParamNoPage
	 * @Methods Name xmlReadForOracle
	 * @Create In Aug 2, 2010 By likang
	 * @param clazz void
	 */
	public static void buildSqlXmlForOracle(Class clazz) { 
		  //全类名
		  String className = clazz.getName();
		  //命名空间 再建一个变量 便于阅读
		  String nameSpace = clazz.getName();
		  //对象名
		  String objectName = className.substring(className.lastIndexOf(".")+1,className.length());
		  //表名
		  String tableName = PropertiesUtil.getProperties("tablepre", "cs").toUpperCase()+ "_" +objectName.toUpperCase(); 
		  //新建文件
		  Document document =  DocumentHelper.createDocument(); 
		  //加入DocType
		  document.addDocType("sqlMap", "-//ibatis.apache.org//DTD SQL Map 2.0//EN", "http://ibatis.apache.org/dtd/sql-map-2.dtd");
		  //该实体中是否有外键
		  boolean hasWaiJian = false;
	      //root节点
	      Element root =  (Element) document.addElement("sqlMap");
	      root.addAttribute("namespace",className);
	      //typeAlias节点
	      Element element = root.addElement("typeAlias");
	      element.addAttribute("alias",objectName);
	      element.addAttribute("type",className);
	      //resultMap节点
	      element = root.addElement("resultMap");
	      element.addAttribute("id",objectName+"Map");
	      element.addAttribute("class",objectName);
	      String whereSqlColumn = "";
	      
	      String insertSqlColumn = "";
	      String insertSqlProperty = "";
	      //类的所有属性
	      Field[] fields = clazz.getDeclaredFields();
	      String insertItemString = "";
	      String updateSqlColumn = "";
	      for (Field field : fields) {
	    	    //该实体中有其他 BaseObject类型的外键
			    boolean isFromBaseObejct = false;
	    	  	//插入
	    	  	insertItemString = field.getName();
				if ("java.lang.Long".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					ele.addAttribute("column", field.getName().toUpperCase());
					ele.addAttribute("jdbcType", "Long");
					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">("+field.getName().toUpperCase()+"=#"+field.getName()+"#)</isNotNull>";
				}
				if ("java.lang.String".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					ele.addAttribute("column", field.getName().toUpperCase());
					ele.addAttribute("jdbcType", "String");
					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">("+field.getName().toUpperCase()+" like  '%$"+field.getName()+"$%')</isNotNull>";

				}
				if ("java.util.Date".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					ele.addAttribute("column", field.getName().toUpperCase());
					ele.addAttribute("jdbcType", "java.util.Date");
					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">( to_char("+field.getName().toUpperCase()+",'yyyy-MM-dd HH:mm:ss') like '%$"+field.getName()+"$%')</isNotNull>";
				}
				if ("java.lang.Integer".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					ele.addAttribute("column", field.getName().toUpperCase());
					ele.addAttribute("jdbcType", "java.lang.Integer");
					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">("+field.getName().toUpperCase()+"=#"+field.getName()+"#)</isNotNull>";
				}
				if ("java.lang.Float".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					ele.addAttribute("column", field.getName().toUpperCase());
					ele.addAttribute("jdbcType", "java.lang.Float");
					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">("+field.getName().toUpperCase()+"=#"+field.getName()+"#)</isNotNull>";
				}
				if ("java.lang.Double".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					ele.addAttribute("column", field.getName().toUpperCase());
					ele.addAttribute("jdbcType", "java.lang.Double");
					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">("+field.getName().toUpperCase()+"=#"+field.getName()+"#)</isNotNull>";
				}
				//如果是BaseObject类型 用于一对一
				String superClassName = "";
				if (field.getType().getGenericSuperclass() != null ) {
					superClassName = field.getType().getGenericSuperclass().toString();
					if (!superClassName.equals("")) {
						isFromBaseObejct = isFromBaseObejct(superClassName);
					}
				}
				if (isFromBaseObejct ) {
					//有外键
					hasWaiJian = true;
					//get大写第一个字母+小写其他（属性名）+ By 大写第一个字母+小写其他（表名） + Id
					String classNameString = field.getType().getName().substring(field.getType().getName().lastIndexOf(".")+1);
					String selectNameString = 
						"get"  + classNameString + "By" + classNameString + "Id";
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					ele.addAttribute("column", field.getName().toUpperCase());
					ele.addAttribute("select", nameSpace + "." + selectNameString);
					saveAllTypeAliasResultMapSelectProperties(field,classNameString,selectNameString,nameSpace);
//					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">("+field.getName().toUpperCase()+"=#"+field.getName()+"#)</isNotNull>";
					//如果是BaseObject类型的 insert 为 属性.id
					insertItemString += ".id";
				}
				//如果是java.util.List 用于一对多
				if ("java.util.List".equals(field.getType().getName())) {
					String selectNameString = field.getName();
					selectNameString = "get" + selectNameString.substring(0,1).toUpperCase() + selectNameString.substring(1, selectNameString.length());
					
//					String selectNameString = "get"  + field.getName().substring(0,field.getName().length())+field.getName().substring(1,field.getName().length());
					Element ele = element.addElement("result");
					ele.addAttribute("property", field.getName());
					//根据this的ID 对应的多个 引用  无法预知主键名 新表全部规定主键为id
					ele.addAttribute("column", "ID");
					ele.addAttribute("select", nameSpace + "." + selectNameString);
					//拼写该List对应BaseObject的 selectSql 和 泛型中 BaseObject 的ResultMap
					//该field应该是泛型的 Object类型 带包名的全类名
					String listFanXingTypeString = getListFanXingType(field);
					//不带包名的类名
					String classNameString = listFanXingTypeString.substring(listFanXingTypeString.lastIndexOf(".")+1);
					listTypeSaveAllTypeAliasResultMapSelectProperties(listFanXingTypeString,classNameString,selectNameString,nameSpace,className);
//					field.get
//					String selectString =  "<select id=\""+nameSpace+"."+selectNameString+"\" parameterClass=\"Long\" resultMap=\""+resultMapNameString+"\">" + selectString + "</select>"; 
				}
				if (!"java.util.List".equals(field.getType().getName())) {
					  insertSqlColumn += ","+field.getName().toUpperCase();
			    	    insertSqlProperty += ",#"+insertItemString+"#";
				}
	    	 	//如果不是id 可以加入到update序列
	    	  	if(!"id".equals(field.getName()) && !"java.util.List".equals(field.getType().getName())){
		    		 updateSqlColumn += "<isNotNull prepend=\",\" property=\"" + field.getName()+ "\">"+field.getName().toUpperCase()+"=#"+insertItemString+"#</isNotNull>";
		    	}
	      }
	      //
	      for(String string : (List<String>)waiJianTypeAliasAndResultMapList) {
	    	  root.addText(string);
	      }
	     
	      
	      //insert sql
	      Element insertElement = root.addElement("insert");
	      insertElement.addAttribute("id", "insert");
	      insertElement.addAttribute("parameterClass", objectName);
	 
	      String insertSql = "INSERT INTO " + tableName + "("+insertSqlColumn.substring(1)+") VALUES ("+insertSqlProperty.substring(1)+")";
	      //生成主键的sequence
	      Element selectKey = insertElement.addElement("selectKey");
	      selectKey.addAttribute("resultClass", "Long");
	      selectKey.addAttribute("type", "pre");
	      selectKey.addAttribute("keyProperty", "id");
	      selectKey.setText("SELECT " + tableName + "_SEQUENCE.NEXTVAL AS VALUE FROM DUAL");
	      insertElement.addText(insertSql);
	      
	      //动态修改 update sql
	      Element updateElement = root.addElement("update");
	      updateElement.addAttribute("id", "update");
	      updateElement.addAttribute("parameterClass", objectName);
	      String updateWhereId = " where ID=#id#";
	      updateSqlColumn = "<dynamic prepend=\" SET \">" + updateSqlColumn + "</dynamic>";
	      String updateSql = "UPDATE "+ tableName+ " " + updateSqlColumn + " " +updateWhereId;
	      updateElement.setText(updateSql);
	      //物理删除 delete sql
	      Element deleteElement = root.addElement("delete");
	      deleteElement.addAttribute("id", "delete");
	      //不用加参数 这样可以用 Integer Long String
//	      deleteElement.addAttribute("parameterClass", "java.lang.Long");
	      deleteElement.setText("DELETE FROM "+tableName+" WHERE ID=#id#");
	      //物理删除多个 deleteByIds sql
	      Element deleteByIdsElement = root.addElement("delete");
	      deleteByIdsElement.addAttribute("id", "deleteByIds");
	      deleteByIdsElement.addAttribute("parameterClass", "java.lang.String");
	      deleteByIdsElement.setText("DELETE FROM "+tableName+" WHERE ID IN ($ids$)");
	      //逻辑删除 deleteFlag sql
	      Element deleteByLogic = root.addElement("update");
	      deleteByLogic.addAttribute("id", "deleteByLogic");
	      //不用加参数 这样可以用 Integer Long String
//	      deleteByLogic.addAttribute("parameterClass", "java.lang.Long");
	      deleteByLogic.setText("UPDATE "+ tableName +" SET DELETEFLAG=1 WHERE ID=#id#");
	      //批量逻辑删除持久化对象 deleteObjectsByLogic sql
	      Element deleteObjectsByLogic = root.addElement("update");
	      deleteObjectsByLogic.addAttribute("id", "deleteObjectsByLogic");
	      deleteObjectsByLogic.addAttribute("parameterClass", "java.lang.String");
	      deleteObjectsByLogic.setText("UPDATE "+ tableName +" SET DELETEFLAG=1 WHERE ID IN ($ids$)");
	      //无条件查询所有 selectAll sql
	      Element selectAllElement = root.addElement("select");
	      selectAllElement.addAttribute("id", "selectAll");
	      selectAllElement.addAttribute("resultMap", objectName+"Map");
	      selectAllElement.setText("SELECT "+insertSqlColumn.substring(1)+" FROM "+tableName);
	      //根据id查询单个实体 selectById sql
	      Element selectByIdElement = root.addElement("select");
	      selectByIdElement.addAttribute("id", "selectById");
	      //如果有外键则 用resultMap
	      if (hasWaiJian) {
		      selectByIdElement.addAttribute("resultMap", objectName+"Map");
	      } else {
	    	  selectByIdElement.addAttribute("resultClass", objectName);
		  }
	      selectByIdElement.setText("SELECT " + insertSqlColumn.substring(1)+ " FROM "+tableName+" WHERE ID=#id#");
	      String dynamicOrderBy = "<dynamic>"+  
	      "<isNotNull property=\"orderBy\">"+  
	      " order by $orderBy$ " +
	      " <isNotNull property=\"asc\"> $asc$  </isNotNull>"+   
	      "</isNotNull>"+ 
	      "</dynamic> ";
	      //无条件查询总数 selectTotal sql
	      Element selectTotalElement = root.addElement("select");
	      selectTotalElement.addAttribute("id", "selectTotal");
	      selectTotalElement.addAttribute("resultClass", "java.lang.Integer");
	      selectTotalElement.setText("SELECT COUNT(*) FROM "+tableName);
	      //无条件分页查询 selectPage sql
	      Element selectPageElement = root.addElement("select");
	      selectPageElement.addAttribute("id", "selectPage");
	      selectPageElement.addAttribute("parameterClass", "java.util.Map");
	      selectPageElement.addAttribute("resultMap", objectName+"Map");
	      //两层分页查询 提高效率 适用于不分页 String dynamicSqlNoPage = "SELECT "+insertSqlColumn.substring(1)+" FROM ( SELECT "+insertSqlColumn.substring(1)+", ROWNUM rownum_ FROM "+tableName+" WHERE ROWNUM &lt;= #end#  " + dynamicOrderBy + " ) WHERE rownum_ &gt;#start# ";
	      //三层分页查询 解决 order by 的问题
	      String dynamicSqlNoPage = "select "+insertSqlColumn.substring(1)+",r from (select "+insertSqlColumn.substring(1)+", rownum r from (select "+insertSqlColumn.substring(1)+" from "+tableName+" "+ dynamicOrderBy + ")) where r &gt;#start# and r &lt;= #end#";
	      
	      
	      selectPageElement.setText(dynamicSqlNoPage);

	      //动态有条件查询总数 selectTotalByParam sql
	      String dynamicWhere = "<dynamic prepend=\"where\">" + whereSqlColumn + "</dynamic>";

	      Element selectTotalByParamElement = root.addElement("select");
	      selectTotalByParamElement.addAttribute("id", "selectTotalByParam");
	      selectTotalByParamElement.addAttribute("resultClass", "java.lang.Integer");
	      selectTotalByParamElement.setText("SELECT COUNT(*) FROM "+tableName + " " + dynamicWhere);
	      
	      //动态有条件分页查询 selectPageByParam sql
	      Element selectPageByParamElement = root.addElement("select");
	      selectPageByParamElement.addAttribute("id", "selectPageByParam");
	      selectPageByParamElement.addAttribute("parameterClass", "java.util.Map");
	      selectPageByParamElement.addAttribute("resultMap", objectName+"Map");
	      //两层分页查询 提高效率 适用于不分页String dynamicSql = "SELECT "+insertSqlColumn.substring(1)+" FROM ( SELECT "+insertSqlColumn.substring(1)+", ROWNUM rownum_ FROM "+tableName+" WHERE ROWNUM &lt;= #end# "+whereSqlColumn+ " " + dynamicOrderBy + " ) WHERE rownum_ &gt;#start# ";
	      //三层分页查询 解决 order by 的问题
	      String dynamicSql = "select r"+insertSqlColumn+" from (select "+insertSqlColumn.substring(1)+", rownum r from (select "+insertSqlColumn.substring(1)+" from " + tableName + " " + dynamicWhere + " " + dynamicOrderBy +" ))  where r &gt;#start#  and r &lt;=#end#";

	      selectPageByParamElement.setText(dynamicSql);
	      //按条件不分页查询所有 selectObjectsByParamNoPage sql
	      Element selectObjectsByParamNoPage = root.addElement("select");
	      selectObjectsByParamNoPage.addAttribute("id", "selectObjectsByParamNoPage");
	      selectObjectsByParamNoPage.addAttribute("parameterClass", "java.util.Map");
	      selectObjectsByParamNoPage.addAttribute("resultMap", objectName+"Map");
	      String dynamicSqlAll = "select "+insertSqlColumn.substring(1)+" from " + tableName + " " + dynamicWhere + " " + dynamicOrderBy ;
	      selectObjectsByParamNoPage.setText(dynamicSqlAll);
	      //写外键的select语句
	      for(String string : (List<String>)waiJianSelectStringList) {
	    	  root.addText(string);
	      }
	      //生成到项目下该类的map包下
	      String filePathString = targetFile+"\\"+ clazz.getPackage().getName().replace(".", "\\")+"\\map\\"+objectName+".sqlMap.xml";
	      //生成XML文件
	      doc2XmlFile(document, filePathString);
	   }

	
	/**
	 * 
	 * @Methods Name listTypeSaveAllTypeAliasResultMapSelectProperties
	 * @Create In Aug 5, 2010 By likang
	 * @param listFanXingTypeString list泛型类型的名字 全包名
	 * @param classNameString       只是类名
	 * @param selectNameString		定义好的select Id名称
	 * @param nameSpace				命名空间
	 * @param thisClassName			该一对多 一方的全类名（带包名）用于在 多方的实体中反射该类型 然后取出该类型的Coulum 拼出sql
	 * @return String
	 */
	private static String listTypeSaveAllTypeAliasResultMapSelectProperties(String listFanXingTypeString,String classNameString,String selectNameString,String nameSpace,String thisClassName) {
		Class clazz = null;
		Object object = null;
		//一对多 多方中对应的 一方的引用的属性名称 的 大写（对应表的列名）
		String keyNameString = "";
		try {
			 clazz = Class.forName(listFanXingTypeString);
			 object = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//先拼出typeAlias
		String typeString = listFanXingTypeString;
		String typeAlias = " <typeAlias alias=\"" + classNameString + "\" type=\"" + typeString + "\" />";
		//同时拼出该引用实体的resultMap
		String resultMapNameString = classNameString + "Map";
		String selectPro = "";
//		 <result property="userinfoid" column="userinfoid" />  
		String resultMap = "<resultMap id=\""+resultMapNameString+"\" class=\""+classNameString+"\">  ";
		Field [] fields = clazz.getDeclaredFields();
		for (Field fieldTemp : fields) {
			//如果不是BaseObject类型
			String superClassName = fieldTemp.getType().getGenericSuperclass().toString();
			boolean isFromBaseObejct = isFromBaseObejct(superClassName);
			if (!isFromBaseObejct) {
				 selectPro += ","+fieldTemp.getName().toUpperCase();
				 resultMap += "<result property=\""+fieldTemp.getName()+"\" column=\""+fieldTemp.getName().toUpperCase()+"\" jdbcType=\""+fieldTemp.getType().getName()+"\"/>";
			} else if (thisClassName.equals(fieldTemp.getType().getName())) {
				keyNameString = fieldTemp.getName().toUpperCase();
			}
	    }
		resultMap += "</resultMap> ";
		//外键的<typeAlias>和<resultMap>
	   String back = typeAlias + resultMap;
	   waiJianTypeAliasAndResultMapList.add(back);
       String selectString = "SELECT " + selectPro.substring(1) + " FROM " + PropertiesUtil.getProperties("tablepre", "cs").toUpperCase()+ "_" + classNameString.toUpperCase() + " WHERE "+keyNameString+"=#value#";
       selectString =  "<select id=\""+nameSpace+"."+selectNameString+"\" parameterClass=\"Long\" resultMap=\""+resultMapNameString+"\">" + selectString + "</select>"; 
       waiJianSelectStringList.add(selectString);
       return selectPro.substring(1);
	}
	
		/**
		 * 遍历获取所有属性的值 拼接成 属性,属性,、、、
		 * @Methods Name saveAllTypeAliasResultMapSelectProperties
		 * @Create In Aug 3, 2010 By Administrator
		 * @param fields
		 * @return String
		 */
		private static String saveAllTypeAliasResultMapSelectProperties( Field field,String classNameString,String selectNameString,String nameSpace) {
			//先拼出typeAlias
			String typeString = field.getType().getName();
			String typeAlias = " <typeAlias alias=\"" + classNameString + "\" type=\"" + typeString + "\" />";
			//同时拼出该引用实体的resultMap
			String resultMapNameString = classNameString + "Map";
			String selectPro = "";
//			 <result property="userinfoid" column="userinfoid" />  
			String resultMap = "<resultMap id=\""+resultMapNameString+"\" class=\""+classNameString+"\">  ";
			for (Field fieldTemp : field.getType().getDeclaredFields()) {
				selectPro += ","+fieldTemp.getName().toUpperCase();
				//如果不是BaseObject类型
				String superClassName = "";
				if (fieldTemp.getType().getGenericSuperclass() != null) {
					superClassName = fieldTemp.getType().getGenericSuperclass().toString();
				}
				boolean isFromBaseObejct = isFromBaseObejct(superClassName);
				if (!isFromBaseObejct) {
					 resultMap += "<result property=\""+fieldTemp.getName()+"\" column=\""+fieldTemp.getName().toUpperCase()+"\" jdbcType=\""+fieldTemp.getType().getName()+"\"/>";
				}
		    }
			resultMap += "</resultMap> ";
			//外键的<typeAlias>和<resultMap>
		   String back = typeAlias + resultMap;
		   waiJianTypeAliasAndResultMapList.add(back);
	       String selectString = "SELECT " + selectPro.substring(1) + " FROM " + PropertiesUtil.getProperties("tablepre", "cs").toUpperCase()+ "_" + classNameString.toUpperCase() + " WHERE ID=#"+field.getName().toUpperCase()+"#";
	       selectString =  "<select id=\""+nameSpace+"."+selectNameString+"\" parameterClass=\"Long\" resultMap=\""+resultMapNameString+"\">" + selectString + "</select>"; 
	       waiJianSelectStringList.add(selectString);
	       return selectPro.substring(1);
		}
	
		/**
		 * 是否是BaseObject 类型的属性
		 * @Methods Name isFromBaseObejct
		 * @Create In Aug 3, 2010 By likang
		 * @param superClassName
		 * @return boolean
		 */
		private static boolean isFromBaseObejct(String superClassName) {
		// TODO Auto-generated method stub
			if (superClassName.equals("")) {
				return false;
			}
			if (superClassName.substring(superClassName.indexOf("class ")+"class ".length()).equals(BaseObject.class.getName())) {
				return true;
			}
			return false;
		}

		/**
		 * 根据文件名加载document对象
		 * @Methods Name load
		 * @Create In Jul 22, 2010 By debby
		 * @param filename
		 * @return Document
		 */
		private static Document load(String filename){ 
	      Document document = null; 
	      try  
	      {  
	          SAXReader saxReader = new SAXReader(); 
	          document = saxReader.read(new File(filename)); 
	      } 
	      catch (Exception ex){ 
	          ex.printStackTrace(); 
	      }   
	      return document; 
	   }
		
		/**
		 * 把document中的内容写入文件中 
		 * @Methods Name doc2XmlFile
		 * @Create In Jul 22, 2010 By debby
		 * @param document
		 * @param filename void
		 */
		private static void doc2XmlFile(Document document,String filename){ 
	      try{ 
	            OutputFormat format = OutputFormat.createPrettyPrint(); 
	            format.setEncoding("UTF-8");//默认编码为UTF-8 
	            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),format); 
	            writer.setEscapeText(false);//防止自动转码
	            writer.write(document); 
	            writer.close();             
	       }catch(Exception ex){ 
	            ex.printStackTrace(); 
	       } 
	   }
		
		
		/**
		 * 生成Oracle下自动建表和sequence语句
		 * @Methods Name getCreateSqlForOracle
		 * @Create In Aug 2, 2010 By likang
		 * @param clazz
		 * @param tableName
		 * @return
		 * @throws Exception String
		 */
		private static List<String> getCreateSqlForOracle(Class clazz,String tableName) throws Exception{
			List sqlList = new ArrayList<String>();
			String sequenceSql = "";
			Field[] fields = clazz.getDeclaredFields();
			//表前缀名
			String tablePrefix = PropertiesUtil.getProperties("tablepre", "cs");
			String tableNameString =  tablePrefix.toUpperCase() + "_" + tableName.toUpperCase();
			String insertSql = "create table " + tableNameString + "(";
			for (Field field : fields) {
				if ("java.lang.Long".equals(field.getType().getName())) {
					if (field.getName().equalsIgnoreCase("id")) {
						//默认id是主键
						insertSql += field.getName() + " NUMBER primary key,";
						sequenceSql = "create sequence " + tableNameString + "_sequence".toUpperCase();
					} else {
						insertSql += field.getName() + " NUMBER,";
					}
				}
				if ("java.lang.String".equals(field.getType().getName())) {
					insertSql += field.getName() + " varchar2(500),";
				}
				if ("java.util.Date".equals(field.getType().getName())) {
					insertSql += field.getName() + " DATE,";
				}
				if ("java.lang.Integer".equals(field.getType().getName())) {
					insertSql += field.getName() + " NUMBER(10),";
				}
				if ("java.lang.Float".equals(field.getType().getName())) {
					insertSql += field.getName() + " NUMBER(10,2),";
				}
				if ("java.lang.Double".equals(field.getType().getName())) {
					insertSql += field.getName() + " NUMBER(10,2),";
				}
				//如果不是BaseObject类型
				String superClassName = "";
				if (field.getType().getGenericSuperclass() != null) {
					superClassName = field.getType().getGenericSuperclass().toString();
				}
				if (isFromBaseObejct(superClassName)) {
					insertSql += field.getName() + " NUMBER,";
				}
			}
			insertSql = insertSql.substring(0, insertSql.length() - 1) + ")";
			sqlList.add(insertSql);
			sqlList.add(sequenceSql);
			return sqlList;
		
		}
		
		/**
		 * 拿到数据库连接
		 * @Methods Name getConn
		 * @Create In Jul 22, 2010 By debby
		 * @return Connection
		 * @throws IOException 
		 */
		private static Connection getConn() throws IOException {
			Connection conn = null;
			try {
				Class.forName(DBDRIVER);
				conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			} catch (Exception e) {
				return null;
			}
			return conn;
		}
		
		/**
		 * 创建表和Sequence
		 * @Methods Name excuteCreatTableAndSequenceByTableName
		 * @Create In Aug 2, 2010 By likang
		 * @param clazz
		 * @param tableName
		 * @throws Exception void
		 */
		public static void excuteCreatTableAndSequenceByTableName(Class clazz,String tableName) throws Exception {
			List<String> list = getCreateSqlForOracle(clazz,tableName);
			Connection conn = null;
			Statement statement = null;
			try {
				conn = getConn();
				conn.setAutoCommit(true);
				for (String sql : list) {
					statement = conn.createStatement();
					statement.execute(sql);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally{
				colse(conn,statement);
			}
		}
		
		/**
		 * 关闭数据库连接
		 * @Methods Name colse
		 * @Create In Aug 2, 2010 By likang
		 * @param conn
		 * @param statement void
		 */
		private static void colse(Connection conn, Statement statement) {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * Oracle 根据实体生成表 默认表名为实体名的大写 
		 * @Methods Name excuteCreatTableForOracle
		 * @Create In Aug 2, 2010 By likang
		 * @param clazz
		 * @throws Exception void
		 */
		public static void excuteCreatTableForOracle(Class clazz) throws Exception {
			String className = clazz.getName();
			excuteCreatTableAndSequenceByTableName(clazz,className.substring(className.lastIndexOf(".")+1,className.length()).toUpperCase());
		}
		
		/**
		 * 取出list类型的<>泛型类型名称
		 * @Methods Name getListFanXingType
		 * @Create In Aug 5, 2010 By Administrator
		 * @param field
		 * @return String
		 */
		public static String getListFanXingType(Field field) {
			String wholeTypeString = field.getGenericType().toString();
			wholeTypeString = wholeTypeString.substring(wholeTypeString.indexOf("<")+1, wholeTypeString.length()-1);
			return wholeTypeString;
		}

}
