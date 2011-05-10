package com.zsgj.info.appframework.pagemodel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.ExtData;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.MetaDataService;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainAndExtColumnService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;

/**
 * 考虑将此实体置入集合，发送到页面
 * @Class Name ColumnData
 * @Author peixf
 * @Create In 2008-5-28
 */
public class PageDataWrapper {
	private static Service bs = (Service)ContextHolder.getBean("baseService");
	private static MetaDataService ms = (MetaDataService)ContextHolder.getBean("metaDataService");
//	private static SystemExtColumnServcie secs=(SystemExtColumnServcie)ContextHolder.getBean("systemExtColumnService");
	private static SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
	private static SystemMainAndExtColumnService systemMainAndExtColumnService = (SystemMainAndExtColumnService) ContextHolder.getBean("systemMainAndExtColumnService");
	private Column column; //包装的字段
	//private BaseObject object;

	/**
	 * 对于文本：value=text
	 * 对于列表：value=关联对象，text=关联对象的名字字段
	 * 对于附件：value=关联对象，text=逻辑文件名称，link=上传文件路径+系统文件名
	 */
	private Object value; //属性值
	
	private String text; //属性值的文本表现形式
	private Long key; //属性值的Long表现形式
	private String link; //文件链接
	
	private List list = new ArrayList(0);
	private List parentList = new ArrayList(0);
	private List allList = new ArrayList(0);
	private List sourceList = new ArrayList(0);
	private Map map = new HashMap();

	/**
	 * 如果元数据类型是多选列表，此方法返回源列表数据
	 * @Methods Name getSourceList
	 * @Create In 2008-8-21 By sa
	 * @return List
	 */
	public List getSourceList() {
		return sourceList;
	}
	
	/**
	 * 此方法设置源列表数据
	 * @Methods Name getSourceList
	 * @Create In 2008-8-21 By sa
	 * @return List
	 */
	public void setSourceList(List sourceList) {
		this.sourceList = sourceList;
	}

	public PageDataWrapper(){}
	
	public PageDataWrapper(Column column){
		this.column = column;
	}
	
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}
	
	/**
	 * 初始化列表数据
	 * @Methods Name initList
	 * @Create In 2008-8-22 By sa void
	 */
	public void initList(){
		SystemMainTableColumn mc = (SystemMainTableColumn) column;
		if(mc.getIsExtColumn()== SystemMainTableColumn.isMain){
			if(mc.getSystemMainTableColumnType()==null){
				throw new ServiceException(mc.getPropertyName()+"必须选择页面组件（字段）类型");
			}
			String columnTypenName = mc.getSystemMainTableColumnType().getColumnTypeName();
			
			if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "是");
				map.put(0, "否");
				this.map = map; 
				
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "是");
				map.put(0, "否");
				this.map = map; 
				
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "男");
				map.put(0, "女");
				this.map = map; 
				
			}else if(columnTypenName.equalsIgnoreCase("radio")){
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
				String fClassName = column.getForeignTable().getClassName();
				
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
				List fObjects = null;
				if(fColumnOrder==null){
					fObjects = bs.findAll(clazz);
				}else{
					boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
					fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
				}
				this.list = fObjects;
			
			} else if(columnTypenName.equalsIgnoreCase("select")){
				
				//是否引用区分字段
				Integer abstractFlag = mc.getAbstractFlag();
				
				if(abstractFlag!=null && abstractFlag.intValue()==1){
					SystemMainTableColumn discColumn = mc.getDiscColumn();
					SystemMainTable foreignDiscTable = mc.getForeignDiscTable();
					String foreignDiscTableClassname = foreignDiscTable.getClassName();
					Class foreignDiscTableClass = this.getClass(foreignDiscTableClassname);
					
					
				}else{ //正常的下来列表，不引用区分字段
					
					SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
					if(fValueColumn==null){
						SystemMainTable smt = mc.getSystemMainTable();
						String tableName = smt.getTableName();
						throw new ServiceException(tableName+"的"+mc.getPropertyName()+"的外键文本字段不允许为null");
					}
					SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
					String fClassName = column.getForeignTable().getClassName();
					Class clazz = null;
					try {
						clazz = Class.forName(fClassName);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					//获取关联对象类型的所有数据，用于页面显示对象类型属性的下拉列表，对于父子列表需显示2个列表
					if(fParentColumn==null) { //普通列表，只显示关联表的一个字段
						
						Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
						List fObjects = new ArrayList();
						if(fColumnOrder==null){//2期框架暂时注释
							fObjects = bs.findAll(clazz);
						}else{
							boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
							String propName =mc.getPropertyName();
							String columnName = mc.getColumnCnName();
							try {
								if(!fClassName.equalsIgnoreCase("com.zsgj.info.framework.security.entity.UserInfo")
										&& !fClassName.equals("com.zsgj.info.framework.security.entity.Department")){
									fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
								}
										
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						this.list = fObjects;
						
					}else { //父子列表，显示关联表的名字字段和父字段
						
						Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
						List fChildObjects = null;
						if(fColumnOrder==null){
							fChildObjects = bs.findAll(clazz);
						}else{
							boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
							fChildObjects = bs.findAllChildBy(clazz, fParentColumn.getPropertyName(), fValueColumn.getPropertyName(), isAsc);
						}
						this.list = fChildObjects; //such as createUsers, tradeWays

						List fParentObjects = null;
						if(fColumnOrder==null){
							fParentObjects = bs.findAll(clazz);
						}else{
							boolean isAsc = fColumnOrder.intValue()==1 ? true : false; //parentTradeWay
							fParentObjects = bs.findAllTopBy(clazz, fParentColumn.getPropertyName(), fValueColumn.getPropertyName(), isAsc);
						}
						this.parentList = fParentObjects; //parentTradeWays
						
						List fObjects = null;
						if(fColumnOrder==null){
							fObjects = bs.findAll(clazz);
						}else{
							boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
							fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
						}
						this.allList = fObjects; //alltradeWays
					}
				}
				
				
			} else if(columnTypenName.equalsIgnoreCase("checkboxGroup")){
				
				SystemMainTable fTable = column.getForeignTable(); //role
				if(fTable==null){
					SystemMainTable smt = mc.getSystemMainTable();
					String tableName = smt.getTableName();
					throw new ServiceException(tableName+"的"+mc.getPropertyName()+"的外键引用表必须选择");
				}
				SystemMainTableColumn fKeyColumn = mc.getForeignTableKeyColumn(); //role.id
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn(); //role.name
				
				//Set<Role>
				if(fValueColumn==null){
					SystemMainTable smt = mc.getSystemMainTable();
					String tableName = smt.getTableName();
					throw new ServiceException(tableName+"的"+mc.getPropertyName()+"的外键文本字段不允许为null");
				}
				SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
				String fClassName = fTable.getClassName();
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				if(fParentColumn==null) { //普通列表，只显示关联表的一个字段
					
					Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
					List fObjects = new ArrayList();
					if(fColumnOrder==null){//2期框架暂时注释
						fObjects = bs.findAll(clazz);
					}else{
						boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
						String propName =mc.getPropertyName();
						String columnName = mc.getColumnCnName();
						try {
							fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
									
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					this.sourceList = fObjects;
					
				}
				
				//关联表
//				SystemMainTable foreignTable = column.getReferencedTable(); //userRole
//				SystemMainTableColumn foreignTableKeyColumn = column.getReferencedTableKeyColumn();//userRole.id
//				SystemMainTableColumn foreignTableValueColumn = column.getReferencedTableValueColumn();//userRole.roleid
//				SystemMainTableColumn referencedTableParentColumn = column.getReferencedTableParentColumn();//userRole.userid
//				Integer referencedTableValueColumnOrder = column.getReferencedTableValueColumnOrder();
	
				
			} else if(columnTypenName.equalsIgnoreCase("multiSelect")){

				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
				SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
				String fClassName = column.getForeignTable().getClassName();
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				//获取关联对象类型的所有数据，用于页面显示对象类型属性的下拉列表，对于父子列表需显示2个列表
					Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
					List fObjects = null;
					if(fColumnOrder==null){
						fObjects = bs.findAll(clazz);
					}else{
						boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
						fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
					}
					this.sourceList = fObjects;
			}
		}else if(mc.getIsExtColumn()== SystemMainTableColumn.isExt){
			//String columnTypenName = mec.getSystemMainTableColumnType().getColumnTypeName();
			String extColumnTypenName=mc.getSystemMainTableColumnType().getColumnTypeName();
			if(extColumnTypenName.equalsIgnoreCase("yesNoSelect")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "是");
				map.put(0, "否");
				this.map = map; 
				
			}else if(extColumnTypenName.equalsIgnoreCase("yesNoRadio")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "是");
				map.put(0, "否");
				this.map = map; 				
			}else if(extColumnTypenName.equalsIgnoreCase("radio")){
				if(mc.getExtSelectType()==0){
					SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
					String fClassName = column.getForeignTable().getClassName();
					
					Class clazz = null;
					try {
						clazz = Class.forName(fClassName);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
					List fObjects = null;
					if(fColumnOrder==null){
						fObjects = bs.findAll(clazz);
					}else{
						boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
						fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
					}
					this.list = fObjects;
				}else if(mc.getExtSelectType()==2){
					List fObjects = null;
					//Integer extTCN=mec.getExtendTableColumnNum();
					Long extColumnId=mc.getId();
					fObjects=systemMainAndExtColumnService.findExtOptionDataByExtColId(extColumnId.toString());
					this.list = fObjects;
				}
			
			} else if(extColumnTypenName.equalsIgnoreCase("checkbox")){
				if(mc.getExtSelectType()==0){
					SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
					String fClassName = column.getForeignTable().getClassName();
					
					Class clazz = null;
					try {
						clazz = Class.forName(fClassName);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
					List fObjects = null;
					if(fColumnOrder==null){
						fObjects = bs.findAll(clazz);
					}else{
						boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
						fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
					}
					this.sourceList = fObjects;
				}else if(mc.getExtSelectType()==2){
					List fObjects = null;
					Long extColumnId=mc.getId();
					fObjects=systemMainAndExtColumnService.findExtOptionDataByExtColId(extColumnId.toString());
					this.sourceList = fObjects;
				}
			
			} else if(extColumnTypenName.equalsIgnoreCase("select")){
				if(mc.getExtSelectType()==0){
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
				if(fValueColumn == null){
					throw new ServiceException("扩展字段"+mc.getPropertyName()+"必须选择页面组件（字段）类型");
				}
				String fClassName = column.getForeignTable().getClassName();
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				//获取关联对象类型的所有数据，用于页面显示对象类型属性的下拉列表，对于父子列表需显示2个列表
		        //普通列表，只显示关联表的一个字段
					
					Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
					List fObjects = null;
					if(fColumnOrder==null){
						fObjects = bs.findAll(clazz);
					}else{
						boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
						fObjects = bs.findAllBy(clazz, fValueColumn.getPropertyName(), isAsc);
					}
					this.list = fObjects;
				}else if(mc.getExtSelectType()==2){
					List fObjects = null;
					Long extColumnId=mc.getId();
					fObjects=systemMainAndExtColumnService.findExtOptionDataByExtColId(extColumnId.toString());
					this.list = fObjects;
				}
			}else if(extColumnTypenName.equalsIgnoreCase("multiSelect")){
				SystemMainTable rtable = mc.getReferencedTable();
				SystemMainTableColumn rtableKeyColumn = mc.getReferencedTableKeyColumn();
				SystemMainTableColumn rtableValueColumn = mc.getReferencedTableValueColumn();
				Integer rtableValueColumnOrder = mc.getOrder();
				String propertyName = mc.getPropertyName();
				String rtableClassName = rtable.getClassName();
				Class rtableClass = null;
				try {
					rtableClass = Class.forName(rtableClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				List rObjects = null;
				if(rtableValueColumnOrder==null){
					rObjects = bs.findAll(rtableClass);
				}else{
					boolean isAsc = rtableValueColumnOrder.intValue()==1 ? true : false;
					rObjects = bs.findAllBy(rtableClass, rtableValueColumn.getPropertyName(), isAsc);
				}
				//this.list = rObjects;
				this.sourceList = rObjects;
			}
		}
	}
	
	/**
	 * 根据字段初始化所有关联对象的集合类型数据，如做查询时，查询字段要显示列表等数据
	 * @Methods Name initDataList
	 * @Create In 2008-5-28 By peixf
	 * @param column void
	 */
	public void initList(Column column){
		this.column = column;
		this.initList();
	}
	
	/**
	 * 根据字段初始化"根对象数据"和"所有关联对象的集合类型数据"，如VIEW和EDIT时既要显示本对象的
	 * 数据，有要初始化下拉列表数据
	 * @Methods Name initDataAndList
	 * @Create In 2008-5-28 By peixf
	 * @param object void
	 */
	public void initDataAndList(BaseObject object){
		//初始化集合数据
		this.initList(column);
		this.initData(object);
	}

	/**
	 * 根据字段初始化"根对象数据"和"所有关联对象的集合类型数据"，如VIEW和EDIT时既要显示本对象的
	 * 数据，有要初始化下拉列表数据
	 * @Methods Name initDataAndList
	 * @Create In 2008-5-28 By peixf
	 * @param object void
	 */
	@SuppressWarnings("unchecked")
	public void initData(BaseObject object){
		String propertyName = column.getPropertyName();
		//初始化根对象数据
		BeanWrapperImpl baseObjectWrapper = new BeanWrapperImpl(object);
		SystemMainTableColumn mc = (SystemMainTableColumn) column;
		if(mc.getIsExtColumn()==SystemMainTableColumn.isMain){
			
			String columnTypenName = mc.getSystemMainTableColumnType().getColumnTypeName();

			if(columnTypenName.equalsIgnoreCase("radio")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					this.text = foreignValue.toString();
					this.value = this.text;
				}
			}else if(columnTypenName.equalsIgnoreCase("text")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					if(foreignValue instanceof Double){
						DecimalFormat nf = new DecimalFormat("###.00");
						double doubleValue = Double.valueOf(foreignValue.toString());
						this.text = nf.format(doubleValue);
					}else{
//						this.text = this.text.replaceAll("\\\\", "\\\\\\\\");//将数据库中的反斜线读出到界面原样显示
						//SystemMainTable fTable = column.getSystemMainTable();
					    //String fClassName = fTable.getClassName();
					    //表单页面下拉列表里面的文本内容
						if(propertyName.equalsIgnoreCase("userName")){//|| propertyName.equalsIgnoreCase("realName")
							//this.text = foreignValue.toString();
							//用户名完整信息
							Object realNameAndDept = bw.getPropertyValue("realNameAndDept");
							this.text = realNameAndDept.toString();
						}else{
							this.text = foreignValue.toString();
						}
							
						
					}
					
					this.value = this.text;
				}
				
			}else if(columnTypenName.equalsIgnoreCase("hidden")){
				BeanWrapperImpl bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					if(foreignValue instanceof BaseObject){
						SystemMainTable foreiTable = column.getForeignTable();
						if(foreiTable!=null){
							SystemMainTableColumn fKeyColumn = mc.getForeignTableKeyColumn();
							SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
							//SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
							
							Object fObject = bw.getPropertyValue(propertyName); //获取外键关联对象类型属性
							if(fObject!=null){ //关联对象不null
								//包装关联对象
								bw.setWrappedInstance(fObject);
//								获取关联对象的id
								if(fKeyColumn!=null){
									String fKeyPropertyName = fKeyColumn.getPropertyName();
									Object fObjectKey = bw.getPropertyValue(fKeyPropertyName);
									this.value = fObjectKey.toString();//外键id
									this.key = Long.valueOf(this.value.toString()); //2期框架新增
								}
								//获取关联对象的文本
								if(fValueColumn!=null){
									String fTextPropertyName = fValueColumn.getPropertyName();
									Object fObjectText = bw.getPropertyValue(fTextPropertyName);
									//2期框架修改，都隐藏了不需要文本而是id，目前问题是grid的隐藏域字段都是字符串，
									//问题在form页面应该同样存在，隐藏了就不需要文本显示
									if(fObjectText!=null){
										this.text = fObjectText.toString();//外键文本   
									}
									
								}
								
							}
						}
					}else{
						this.text = foreignValue.toString();
						this.value = this.text;
					}
					
				}
			}else if(columnTypenName.equalsIgnoreCase("textArea")){
				BeanWrapperImpl bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					this.text = foreignValue.toString();
					//将数据库中的反斜线读出到界面原样显示
					this.text = this.text.replaceAll("\\\\", "\\\\\\\\");
					this.text = this.text.replaceAll("\r", "\\\\r");
					this.text = this.text.replaceAll("\n", "\\\\n");	
					/*
					 * 保存的时候就不用把用户的那些回车换行替换了, 用户原来输什么就给他保存什么
					 */
					this.value = this.text;
				}
			}else if(columnTypenName.equalsIgnoreCase("htmlEditor")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					this.text = foreignValue.toString();
					//将数据库中的反斜线读出到界面原样显示
					this.text = this.text.replaceAll("\\\\", "\\\\\\\\");
					this.text = this.text.replaceAll("\r", "\\\\r");
					this.text = this.text.replaceAll("\n", "\\\\n");	
					/*
					 * 保存的时候就不用把用户的那些回车换行替换了, 用户原来输什么就给他保存什么
					 */
					this.value = this.text;
				}
			}
			//add by lee for add fckediter in 20090928 begin
			else if(columnTypenName.equalsIgnoreCase("fckEditor")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					this.text = foreignValue.toString();
					this.value = this.text;
				}
			}
			//add by lee for add fckediter in 20090928 end
			else if(columnTypenName.equalsIgnoreCase("dateText")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					String dateString = DateUtil.convertDateTimeToString((Date)foreignValue);
					this.text = dateString; //foreignValue.toString(); //DateUtil.getDateTime(DateUtil.timePattern,(Date)foreignValue); //
					this.value = this.text;
				}
			}else if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object propertyValue  = bw.getPropertyValue(propertyName);
				if(propertyValue!=null){
					Integer intValue = Integer.valueOf(propertyValue.toString());
					propertyValue = intValue.intValue()==1?"是":"否";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //如果是否列表的属性值为null，则为显示否
					this.value = "";
					this.text = "";
				}
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object propertyValue  = bw.getPropertyValue(propertyName);
				if(propertyValue!=null){
					Integer intValue = Integer.valueOf(propertyValue.toString());
					propertyValue = intValue.intValue()==1?"男":"女";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //如果是否列表的属性值为null，则为显示否
					this.value = "";
					this.text = "";
				}
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object propertyValue  = bw.getPropertyValue(propertyName);
				if(propertyValue!=null){
					Integer intValue = Integer.valueOf(propertyValue.toString());
					propertyValue = intValue.intValue()==1?"是":"否";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //如果是否列表的属性值为null，则为显示否
					this.value = "";
					this.text = "";
				}
			}else if(columnTypenName.equalsIgnoreCase("foreiText")){
				//客户案例增加代码
				SystemMainTable foreiTable = column.getForeignTable();
				if(foreiTable!=null){

					Integer isVirtualColumn = mc.getIsNullForeignColumn();//是否时虚拟字段
					if(isVirtualColumn!=null&& isVirtualColumn.intValue()==1){
						SystemMainTable smt = mc.getSystemMainTable();
						//客户地址-》客户字段
						SystemMainTableColumn mainForeiColumn = ms.findMainForeiColumn(smt, foreiTable);
						//客户地址-》客户字段的属性名称，如customer
						String mainForeiColumnProp = mainForeiColumn.getPropertyName();
						BeanWrapper mainForeiColumnBw = new BeanWrapperImpl(object);
						//返回客户对象
						Object mainForeiObject = mainForeiColumnBw.getPropertyValue(mainForeiColumnProp); 

						Object virtualColumnValue = null; //下拉列表数据的id					
						try {
							BeanWrapper mainForeiObjectWrapper = new BeanWrapperImpl(mainForeiObject); //传递entity为参数使用对象点属性名称,customer.address
							//客户地址
							virtualColumnValue = mainForeiObjectWrapper.getPropertyValue(propertyName);
							if(virtualColumnValue!=null){
								this.text = virtualColumnValue.toString();
								this.value = this.text;
							}
							
						} catch (Exception e) {}						
						if(virtualColumnValue instanceof BaseObject){
							try {
								BeanWrapper virtualColumnObjectWrapper = new BeanWrapperImpl(virtualColumnValue);
								Object virtualColumnObject = virtualColumnObjectWrapper.getPropertyValue(propertyName + "Name");		
								if(virtualColumnObject!=null){
									this.text = virtualColumnObject.toString();		
									this.value = this.text;
								}														
							} catch (Exception e) {}							 
						}
					}
				}
			}else if(columnTypenName.equalsIgnoreCase("select")){
				SystemMainTable fTable = column.getForeignTable();
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
				SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
				//从跟对象获取属性值
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //获取外键关联对象类型属性
				//begin
				Integer abstractFlag = mc.getAbstractFlag(); //是否抽象标记
				if(abstractFlag!=null && abstractFlag.intValue()==1){
					//区分字段
					SystemMainTableColumn discColumn = mc.getDiscColumn();
					//区分字段所引用的表
					SystemMainTable foreignDiscTable = mc.getForeignDiscTable();
					//区分字段的表名，如客户类型表
					String fdisctTableName = foreignDiscTable.getTableName();
					//区分字段所引用表的类名称
					String foreignDiscTableClassname = foreignDiscTable.getClassName();
					//区分字段所引用表的类
					@SuppressWarnings("unused")
					Class foreignDiscTableClass = this.getClass(foreignDiscTableClassname);
					//systemColumnService
					if(fObject!=null){
						this.value = fObject.toString();
						this.key = Long.valueOf(fObject.toString());
						//区分字段属性名称
						String discPropName = discColumn.getPropertyName();
						//区分字段属性的对象值
						Object discPropValue = baseObjectWrapper.getPropertyValue(discPropName);
						//区分字段属性的对象值id
						Long discPropIdValue = ((BaseObject)discPropValue).getId();
						//如内部客户
						String pClassName = systemColumnService.findClassNameByDisc(discPropIdValue.toString(), fdisctTableName);
						Class pClass = this.getClass(pClassName);
						BaseObject absObject = (BaseObject) bs.find(pClass, fObject.toString(), true);
						String name = absObject.getName();
						
						this.text = name; //this.key.toString();
						
					}
					
					
				}else{//end
					//begin old
//					向请求发送此对象，页面可以在下拉列表遍历时根据关联对象的属性判断自动选择哪个条目
					this.value=fObject;
					if(fObject!=null){ //关联对象不null
						//包装关联对象
						baseObjectWrapper.setWrappedInstance(fObject);
						//获取关联对象的id
						Object fObjectKey = baseObjectWrapper.getPropertyValue(fTable.getPrimaryKeyColumn().getPropertyName());
						this.key = Long.valueOf(fObjectKey.toString());
					}
					
					//获取关联对象类型的所有数据，用于页面显示对象类型属性的下拉列表，对于父子列表需显示2个列表
					if(fParentColumn==null) { //普通列表，只显示关联表的一个字段
						if(fObject!=null){ //关联对象不null
							//包装关联对象
							baseObjectWrapper.setWrappedInstance(fObject);
							//获取关联对象的id
							if(mc.getForeignTableValueColumn()!=null){
								String fClassName = fTable.getClassName();
								
								Object fObjectText = null;
								//此处功能用户列表页面显示用户用户名或真实姓名
								if(fClassName.equalsIgnoreCase("com.zsgj.info.framework.security.entity.UserInfo")){
									fObjectText = baseObjectWrapper.getPropertyValue("realNameAndDept");
								}else{
									fObjectText = baseObjectWrapper.getPropertyValue(mc.getForeignTableValueColumn().getPropertyName());
								}
								
								if(fObjectText==null){
									this.text="";
								}else{
									this.text = fObjectText.toString();
								}
							}
						}
					}else { //父子列表，显示关联表的名字字段和父字段
						if(fObject!=null){ //关联对象不null
							//包装关联对象
							baseObjectWrapper.setWrappedInstance(fObject);
							//获取关联对象的id
							Object fObjectText = baseObjectWrapper.getPropertyValue(mc.getForeignTableValueColumn().getPropertyName());
							this.text = fObjectText.toString();
							//存放关联对象的父对象值
							Object pObjectText = null;
							//父对象
							Object pObject = baseObjectWrapper.getPropertyValue(fParentColumn.getPropertyName());
							if(pObject!=null){ //有父对象
								baseObjectWrapper.setWrappedInstance(pObject); //包装父对象
								pObjectText = baseObjectWrapper.getPropertyValue(fValueColumn.getPropertyName());
								if(pObjectText!=null){
									this.text = pObjectText+ "→" + this.text;
								}	
							}
						}

					}
					//end old
				}
				
				
				
				
			}else if(columnTypenName.equalsIgnoreCase("extSelect")){
				SystemMainTable fTable = column.getForeignTable();
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
				SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
				
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //获取外键关联对象类型属性
				//向请求发送此对象，页面可以在下拉列表遍历时根据关联对象的属性判断自动选择哪个条目
				this.value=fObject;
				if(fObject!=null){ //关联对象不null
					//包装关联对象
					baseObjectWrapper.setWrappedInstance(fObject);
					//获取关联对象的id
					Object fObjectKey = baseObjectWrapper.getPropertyValue(fTable.getPrimaryKeyColumn().getPropertyName());
					this.key = Long.valueOf(fObjectKey.toString());
				}
				
				//获取关联对象类型的所有数据，用于页面显示对象类型属性的下拉列表，对于父子列表需显示2个列表
				if(fParentColumn==null) { //普通列表，只显示关联表的一个字段
					if(fObject!=null){ //关联对象不null
						//包装关联对象
						baseObjectWrapper.setWrappedInstance(fObject);
						//获取关联对象的id
						if(mc.getForeignTableValueColumn()!=null){
							Object fObjectText = baseObjectWrapper.getPropertyValue(mc.getForeignTableValueColumn().getPropertyName());
							if(fObjectText==null){
								this.text="";
							}else{
								this.text = fObjectText.toString();
							}
						}
					}
				}else { //父子列表，显示关联表的名字字段和父字段
					if(fObject!=null){ //关联对象不null
						//包装关联对象
						baseObjectWrapper.setWrappedInstance(fObject);
						//获取关联对象的id
						Object fObjectText = baseObjectWrapper.getPropertyValue(mc.getForeignTableValueColumn().getPropertyName());
						this.text = fObjectText.toString();
						//存放关联对象的父对象值
						Object pObjectText = null;
						//父对象
						Object pObject = baseObjectWrapper.getPropertyValue(fParentColumn.getPropertyName());
						if(pObject!=null){ //有父对象
							baseObjectWrapper.setWrappedInstance(pObject); //包装父对象
							pObjectText = baseObjectWrapper.getPropertyValue(fValueColumn.getPropertyName());
							if(pObjectText!=null){
								this.text = pObjectText+ "→" + this.text;
							}	
						}
					}

				}
				
			}else if(columnTypenName.equalsIgnoreCase("multiSelect")){
				/*SystemMainTable rtable = mc.getReferencedTable();
				SystemMainTableColumn rtableKeyColumn = mc.getReferencedTableKeyColumn();
				SystemMainTableColumn rtableValueColumn = mc.getReferencedTableValueColumn();
				Integer rtableValueColumnOrder = mc.getReferencedTableValueColumnOrder();*/
				//String propertyName = mc.getPropertyName();
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
				SystemMainTableColumn fKeyColumn = mc.getForeignTableKeyColumn();
				Object rObject = baseObjectWrapper.getPropertyValue(propertyName); //获取被引用对象的集合
				if(rObject instanceof java.util.Collection){
					Set sets=(Set) rObject;
					for(Object ob:sets){
						baseObjectWrapper.setWrappedInstance(ob);
						String te= (String) baseObjectWrapper.getPropertyValue(fValueColumn.getPropertyName());
						if(this.text==null){
							this.text=te;
						}else{
							this.text=this.text+","+te;
						}
						this.list.add(ob);
					}
				}
				
			}else if(columnTypenName.equalsIgnoreCase("checkboxGroup")){
				
				SystemMainTable fTable = column.getForeignTable(); //role
				SystemMainTableColumn fKeyColumn = mc.getForeignTableKeyColumn(); //role.id
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn(); //role.name
				//SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn(); 
				//Integer foreignTableValueColumnOrder = mc.getForeignTableValueColumnOrder();
				
				String fClassName = fTable.getClassName();
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
				
				//fObject是一个Set<Role>
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //获取外键关联对象类型属性
				Set roles = (Set) fObject;
				this.list.addAll(roles);
				
				//关联表
//				SystemMainTable foreignTable = column.getReferencedTable(); //userRole
//				SystemMainTableColumn foreignTableKeyColumn = column.getReferencedTableKeyColumn();//userRole.id
//				SystemMainTableColumn foreignTableValueColumn = column.getReferencedTableValueColumn();//userRole.roleid
//				SystemMainTableColumn referencedTableParentColumn = column.getReferencedTableParentColumn();//userRole.userid
//				Integer referencedTableValueColumnOrder = column.getReferencedTableValueColumnOrder();
	
				
			}else if(columnTypenName.equalsIgnoreCase("file")){
				SystemMainTable fTable = mc.getReferencedTable();
				SystemMainTableColumn ftableKeyColumn = mc.getForeignTableKeyColumn();
				//逻辑文件名称对应的字段fileName
				SystemMainTableColumn ftableValueColumn = mc.getForeignTableValueColumn();
				/*String uploadUrl = mc.getUploadUrl();
				String fileNamePrefix = mc.getFileNamePrefix();
				SystemMainTableColumn fileNameColumn = mc.getFileNameColumn();
				SystemMainTableColumn systemFileNameColumn = mc.getSystemFileNameColumn();*/
				
				String fClassName = column.getForeignTable().getClassName();
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //获取外键关联对象类型属性
				//向请求发送此对象，页面可以在下拉列表遍历时根据关联对象的属性判断自动选择哪个条目
				this.value=fObject;
				if(fObject!=null){ //关联对象不null
					if(fObject instanceof BaseObject){
//						包装关联对象
						baseObjectWrapper.setWrappedInstance(fObject);
						//获取关联对象的id
						Object fObjectKey = baseObjectWrapper.getPropertyValue(ftableKeyColumn.getPropertyName());
						this.key = Long.valueOf(fObjectKey.toString());
						//获取关联对象的名字
						Object fObjectText = baseObjectWrapper.getPropertyValue(ftableValueColumn.getPropertyName());
						this.text = String.valueOf(fObjectText.toString());
						//获取关联对象附件的系统文件名链接
						String uploadUrl = mc.getUploadUrl();
						String fileNamePrefix = mc.getFileNamePrefix();
						SystemMainTableColumn fileNameColumn = mc.getFileNameColumn();
						SystemMainTableColumn systemFileNameColumn = mc.getSystemFileNameColumn();
						
						Object fObjectLink = baseObjectWrapper.getPropertyValue(systemFileNameColumn.getPropertyName());
						String systemFileName = String.valueOf(fObjectLink.toString());
						
						String FSP = System.getProperty("file.separator");
						String LSP = System.getProperty("line.separator");
						
						this.link = systemFileName;
					}else if(fObject instanceof String){
						this.text = "下载";
						this.link = fObject.toString();
					}
					
					
					
				}
				
			}else if(columnTypenName.equalsIgnoreCase("multiFile")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object propertyValue  = bw.getPropertyValue(propertyName);
				if(propertyValue!=null&& propertyValue instanceof String){
					this.value = propertyValue.toString();
					this.text = propertyValue.toString();
				}
				
//				SystemMainTable ftable = column.getForeignTable();
//				if(ftable==null){
//					throw new ServiceException("多附件字段必须选择关联哪个附件表");
//				}
//				String fClassName = column.getForeignTable().getClassName();
//				Class clazz = null;
//				try {
//					clazz = Class.forName(fClassName);
//				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
//				}
			
//				String multiFileIds = (String) baseObjectWrapper.getPropertyValue(propertyName); //获取多个附件的id
//				String[] fileIds = multiFileIds.split(",");
//				for(String fileId : fileIds){
//					Object fileObject = bs.find(clazz, fileId, true);
//					this.map.put(fileId, fileObject);
//				}
				
			}else{
				Object textObject = baseObjectWrapper.getPropertyValue(mc.getPropertyName());
				if(textObject!=null){
					if(textObject instanceof Number){
						this.text = ((Number)textObject).toString();
					}
					this.text = textObject.toString();
					this.value = this.text;
				}	
			}
			
		}else if(mc.getIsExtColumn()== SystemMainTableColumn.isExt){
			//SystemMainTableExtColumn mec=(SystemMainTableExtColumn)column;
			//String columnTypenName = mec.getSystemMainTableColumnType().getColumnTypeName();
			String extColumnTypenName=mc.getSystemMainTableColumnType().getColumnTypeName();
			String extKey=null;
			if(mc.getForeignTableKeyColumn()!=null){
				extKey=mc.getForeignTableKeyColumn().getPropertyName();
			}
			String extValue=null;
			if(mc.getForeignTableValueColumn()!=null){
				extValue=mc.getForeignTableValueColumn().getPropertyName();
			}
			SystemMainTable fTable = column.getForeignTable();
			Integer selectType=mc.getExtSelectType();
			Long mainRowId=object.getId();
			//Integer extTableColumnNum=mec.getExtendTableColumnNum();
			Long extColumnId=mc.getId();
			if(extColumnTypenName.equalsIgnoreCase("radio")){
				ExtData extData=(ExtData) systemMainAndExtColumnService.findObjectByMainRowIdAndExtColId(Integer.parseInt(mainRowId.toString()),Integer.parseInt(extColumnId.toString()));
				if(extData==null){
					extData=new ExtData();
				}
				Object foreignValue =extData.getExtendTableData();
				if(foreignValue!=null){
					Object ob=null;
					Long id=null;
					String fValue=null;
					if(selectType==0){
						Class clazz = null;
						try {
							clazz = Class.forName(fTable.getClassName());
						} catch (ClassNotFoundException e) {}
						ob=bs.find(clazz, foreignValue.toString());
						BeanWrapper bwo = new BeanWrapperImpl(ob);
						id=(Long) bwo.getPropertyValue(extKey);
						fValue=(String) bwo.getPropertyValue(extValue);
					}else if(selectType==2){
						ob=systemMainAndExtColumnService.findOptionById(Long.parseLong(foreignValue.toString()));
						BeanWrapper bwo = new BeanWrapperImpl(ob);
						id=(Long) bwo.getPropertyValue("id");
						fValue=(String) bwo.getPropertyValue("extOptionValue");
					}
						this.key=id;
						this.text = fValue;
						this.value = ob;
				}
			}else if(extColumnTypenName.equalsIgnoreCase("text")||extColumnTypenName.equalsIgnoreCase("dateText")
					||extColumnTypenName.equalsIgnoreCase("textArea")){
				ExtData extData=(ExtData) systemMainAndExtColumnService.findObjectByMainRowIdAndExtColId(Integer.parseInt(mainRowId.toString()),Integer.parseInt(extColumnId.toString()));
				if(extData==null){
					extData=new ExtData();
				}
				Object foreignValue =extData.getExtendTableData();
				if(foreignValue!=null){
					this.text = foreignValue.toString();
					this.value = this.text;
				}
			}else if(extColumnTypenName.equalsIgnoreCase("yesNoSelect")||extColumnTypenName.equalsIgnoreCase("yesNoRadio")){
				ExtData extData=(ExtData) systemMainAndExtColumnService.findObjectByMainRowIdAndExtColId(Integer.parseInt(mainRowId.toString()),Integer.parseInt(extColumnId.toString()));
				if(extData==null){
					extData=new ExtData();
				}
				Object propertyValue =extData.getExtendTableData();
				if(propertyValue!=null){
					Integer intValue = Integer.valueOf(propertyValue.toString());
					propertyValue = intValue.intValue()==1?"是":"否";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //如果是否列表的属性值为null，则为显示否
					this.value = "";
					this.text = "";
				}
			}else if(extColumnTypenName.equalsIgnoreCase("select")){
				ExtData extData=(ExtData) systemMainAndExtColumnService.findObjectByMainRowIdAndExtColId(Integer.parseInt(mainRowId.toString()),Integer.parseInt(extColumnId.toString()));
				if(extData==null){
					extData=new ExtData();
				}
				Object foreignValue =extData.getExtendTableData();
					if(foreignValue!=null){
						Object ob=null;
						Long id=null;
						String fValue=null;
						if(selectType==0){
							Class clazz = null;
							try {
								clazz = Class.forName(fTable.getClassName());
							} catch (ClassNotFoundException e) {}
							ob=bs.find(clazz, foreignValue.toString());
							BeanWrapper bwo = new BeanWrapperImpl(ob);
							id=(Long) bwo.getPropertyValue(extKey);
							fValue=(String) bwo.getPropertyValue(extValue);
						}else if(selectType==2){
							ob=systemMainAndExtColumnService.findOptionById(Long.parseLong(foreignValue.toString()));
							BeanWrapper bwo = new BeanWrapperImpl(ob);
							id=(Long) bwo.getPropertyValue("id");
							fValue=(String) bwo.getPropertyValue("extOptionValue");
						}
							this.key=id;
							this.text = fValue;
							this.value = ob;
					}			
			}else if(extColumnTypenName.equalsIgnoreCase("checkbox")){
				ExtData extData=(ExtData) systemMainAndExtColumnService.findObjectByMainRowIdAndExtColId(Integer.parseInt(mainRowId.toString()),Integer.parseInt(extColumnId.toString()));
				if(extData==null){
					extData=new ExtData();
				}
				Object foreignValue =extData.getExtendTableData(); 
				if(foreignValue!=null){
					Object ob=null;
					Long id=null;
					String fValue=null;
					List fValues=new ArrayList();
					if(selectType==0){
						String[] intValues = foreignValue.toString().split(",");
						if(intValues.length>0){
							for(String intvalue:intValues){
								Class clazz = null;
								try {
									clazz = Class.forName(fTable.getClassName());
								} catch (ClassNotFoundException e) {}
								
								ob=bs.find(clazz, intvalue);
								BeanWrapper bwo = new BeanWrapperImpl(ob);
								id=(Long) bwo.getPropertyValue(extKey);
								fValue=(String) bwo.getPropertyValue(extValue);
								fValues.add(fValue);
								this.list.add(id);
								if(this.text==null){
									this.text=fValue;
								}else{
									this.text=this.text+","+fValue;
								}
							}
						}
					}else if(selectType==2){
						String[] intValues = foreignValue.toString().split(",");
						if(intValues.length>0){
							for(String intvalue:intValues){
							ob=systemMainAndExtColumnService.findOptionById(Long.parseLong(intvalue));
							BeanWrapper bwo = new BeanWrapperImpl(ob);
							id=(Long) bwo.getPropertyValue("id");
							fValue=(String) bwo.getPropertyValue("extOptionValue");
							fValues.add(fValue);
							this.list.add(id);
							if(this.text==null){
								this.text=fValue;
							}else{
								this.text=this.text+","+fValue;
							}
							}
						}
					}
				}		
		    }
		}
	}
	
	/**
	 * 根据字段初始化"根对象数据"和"所有关联对象的集合类型数据"，如VIEW和EDIT时既要显示本对象的
	 * 数据，有要初始化下拉列表数据
	 * @Methods Name initDataAndList
	 * @Create In 2008-5-28 By peixf
	 * @param column
	 * @param object 
	 * @param object void
	 */
	public void initDataAndList(Column column, BaseObject object){
		this.column = column;
		this.initDataAndList(object);
	}

	/**
	 * 返回Map形式数据，如是否列表和性别列表类型
	 * @Methods Name getMap
	 * @Create In 2008-8-22 By sa
	 * @return Map
	 */
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * 获取列表所有数据，如区分父子类型的列表，使用此方法返回所有数据
	 * @Methods Name getAllList
	 * @Create In 2008-8-22 By sa
	 * @return List
	 */
	public List getAllList() {
		return allList;
	}

	public void setAllList(List allList) {
		this.allList = allList;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List getParentList() {
		return parentList;
	}

	public void setParentList(List parentList) {
		this.parentList = parentList;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public String getPropertyName() {
		return getColumn().getPropertyName();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
