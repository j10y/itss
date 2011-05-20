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
 * ���ǽ���ʵ�����뼯�ϣ����͵�ҳ��
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
	private Column column; //��װ���ֶ�
	//private BaseObject object;

	/**
	 * �����ı���value=text
	 * �����б�value=��������text=��������������ֶ�
	 * ���ڸ�����value=��������text=�߼��ļ����ƣ�link=�ϴ��ļ�·��+ϵͳ�ļ���
	 */
	private Object value; //����ֵ
	
	private String text; //����ֵ���ı�������ʽ
	private Long key; //����ֵ��Long������ʽ
	private String link; //�ļ�����
	
	private List list = new ArrayList(0);
	private List parentList = new ArrayList(0);
	private List allList = new ArrayList(0);
	private List sourceList = new ArrayList(0);
	private Map map = new HashMap();

	/**
	 * ���Ԫ���������Ƕ�ѡ�б��˷�������Դ�б�����
	 * @Methods Name getSourceList
	 * @Create In 2008-8-21 By sa
	 * @return List
	 */
	public List getSourceList() {
		return sourceList;
	}
	
	/**
	 * �˷�������Դ�б�����
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
			System.out.print("������" + className + "����ȷ��");
			e.printStackTrace();
		}
		return clazz;
	}
	
	/**
	 * ��ʼ���б�����
	 * @Methods Name initList
	 * @Create In 2008-8-22 By sa void
	 */
	public void initList(){
		SystemMainTableColumn mc = (SystemMainTableColumn) column;
		if(mc.getIsExtColumn()== SystemMainTableColumn.isMain){
			if(mc.getSystemMainTableColumnType()==null){
				throw new ServiceException(mc.getPropertyName()+"����ѡ��ҳ��������ֶΣ�����");
			}
			String columnTypenName = mc.getSystemMainTableColumnType().getColumnTypeName();
			
			if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "��");
				map.put(0, "��");
				this.map = map; 
				
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "��");
				map.put(0, "��");
				this.map = map; 
				
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "��");
				map.put(0, "Ů");
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
				
				//�Ƿ����������ֶ�
				Integer abstractFlag = mc.getAbstractFlag();
				
				if(abstractFlag!=null && abstractFlag.intValue()==1){
					SystemMainTableColumn discColumn = mc.getDiscColumn();
					SystemMainTable foreignDiscTable = mc.getForeignDiscTable();
					String foreignDiscTableClassname = foreignDiscTable.getClassName();
					Class foreignDiscTableClass = this.getClass(foreignDiscTableClassname);
					
					
				}else{ //�����������б������������ֶ�
					
					SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
					if(fValueColumn==null){
						SystemMainTable smt = mc.getSystemMainTable();
						String tableName = smt.getTableName();
						throw new ServiceException(tableName+"��"+mc.getPropertyName()+"������ı��ֶβ�����Ϊnull");
					}
					SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
					String fClassName = column.getForeignTable().getClassName();
					Class clazz = null;
					try {
						clazz = Class.forName(fClassName);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					//��ȡ�����������͵��������ݣ�����ҳ����ʾ�����������Ե������б����ڸ����б�����ʾ2���б�
					if(fParentColumn==null) { //��ͨ�б�ֻ��ʾ�������һ���ֶ�
						
						Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
						List fObjects = new ArrayList();
						if(fColumnOrder==null){//2�ڿ����ʱע��
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
						
					}else { //�����б���ʾ������������ֶκ͸��ֶ�
						
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
					throw new ServiceException(tableName+"��"+mc.getPropertyName()+"��������ñ����ѡ��");
				}
				SystemMainTableColumn fKeyColumn = mc.getForeignTableKeyColumn(); //role.id
				SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn(); //role.name
				
				//Set<Role>
				if(fValueColumn==null){
					SystemMainTable smt = mc.getSystemMainTable();
					String tableName = smt.getTableName();
					throw new ServiceException(tableName+"��"+mc.getPropertyName()+"������ı��ֶβ�����Ϊnull");
				}
				SystemMainTableColumn fParentColumn = mc.getForeignTableParentColumn();
				String fClassName = fTable.getClassName();
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				if(fParentColumn==null) { //��ͨ�б�ֻ��ʾ�������һ���ֶ�
					
					Integer fColumnOrder = mc.getForeignTableValueColumnOrder();
					List fObjects = new ArrayList();
					if(fColumnOrder==null){//2�ڿ����ʱע��
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
				
				//������
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
				//��ȡ�����������͵��������ݣ�����ҳ����ʾ�����������Ե������б����ڸ����б�����ʾ2���б�
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
				map.put(1, "��");
				map.put(0, "��");
				this.map = map; 
				
			}else if(extColumnTypenName.equalsIgnoreCase("yesNoRadio")){
				Map<Integer,String> map = new HashMap<Integer,String>();
				map.put(1, "��");
				map.put(0, "��");
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
					throw new ServiceException("��չ�ֶ�"+mc.getPropertyName()+"����ѡ��ҳ��������ֶΣ�����");
				}
				String fClassName = column.getForeignTable().getClassName();
				Class clazz = null;
				try {
					clazz = Class.forName(fClassName);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				//��ȡ�����������͵��������ݣ�����ҳ����ʾ�����������Ե������б����ڸ����б�����ʾ2���б�
		        //��ͨ�б�ֻ��ʾ�������һ���ֶ�
					
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
	 * �����ֶγ�ʼ�����й�������ļ����������ݣ�������ѯʱ����ѯ�ֶ�Ҫ��ʾ�б������
	 * @Methods Name initDataList
	 * @Create In 2008-5-28 By peixf
	 * @param column void
	 */
	public void initList(Column column){
		this.column = column;
		this.initList();
	}
	
	/**
	 * �����ֶγ�ʼ��"����������"��"���й�������ļ�����������"����VIEW��EDITʱ��Ҫ��ʾ�������
	 * ���ݣ���Ҫ��ʼ�������б�����
	 * @Methods Name initDataAndList
	 * @Create In 2008-5-28 By peixf
	 * @param object void
	 */
	public void initDataAndList(BaseObject object){
		//��ʼ����������
		this.initList(column);
		this.initData(object);
	}

	/**
	 * �����ֶγ�ʼ��"����������"��"���й�������ļ�����������"����VIEW��EDITʱ��Ҫ��ʾ�������
	 * ���ݣ���Ҫ��ʼ�������б�����
	 * @Methods Name initDataAndList
	 * @Create In 2008-5-28 By peixf
	 * @param object void
	 */
	@SuppressWarnings("unchecked")
	public void initData(BaseObject object){
		String propertyName = column.getPropertyName();
		//��ʼ������������
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
//						this.text = this.text.replaceAll("\\\\", "\\\\\\\\");//�����ݿ��еķ�б�߶���������ԭ����ʾ
						//SystemMainTable fTable = column.getSystemMainTable();
					    //String fClassName = fTable.getClassName();
					    //��ҳ�������б�������ı�����
						if(propertyName.equalsIgnoreCase("userName")){//|| propertyName.equalsIgnoreCase("realName")
							//this.text = foreignValue.toString();
							//�û���������Ϣ
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
							
							Object fObject = bw.getPropertyValue(propertyName); //��ȡ�������������������
							if(fObject!=null){ //��������null
								//��װ��������
								bw.setWrappedInstance(fObject);
//								��ȡ���������id
								if(fKeyColumn!=null){
									String fKeyPropertyName = fKeyColumn.getPropertyName();
									Object fObjectKey = bw.getPropertyValue(fKeyPropertyName);
									this.value = fObjectKey.toString();//���id
									this.key = Long.valueOf(this.value.toString()); //2�ڿ������
								}
								//��ȡ����������ı�
								if(fValueColumn!=null){
									String fTextPropertyName = fValueColumn.getPropertyName();
									Object fObjectText = bw.getPropertyValue(fTextPropertyName);
									//2�ڿ���޸ģ��������˲���Ҫ�ı�����id��Ŀǰ������grid���������ֶζ����ַ�����
									//������formҳ��Ӧ��ͬ�����ڣ������˾Ͳ���Ҫ�ı���ʾ
									if(fObjectText!=null){
										this.text = fObjectText.toString();//����ı�   
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
					//�����ݿ��еķ�б�߶���������ԭ����ʾ
					this.text = this.text.replaceAll("\\\\", "\\\\\\\\");
					this.text = this.text.replaceAll("\r", "\\\\r");
					this.text = this.text.replaceAll("\n", "\\\\n");	
					/*
					 * �����ʱ��Ͳ��ð��û�����Щ�س������滻��, �û�ԭ����ʲô�͸�������ʲô
					 */
					this.value = this.text;
				}
			}else if(columnTypenName.equalsIgnoreCase("htmlEditor")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object foreignValue = bw.getPropertyValue(column.getPropertyName()); 
				if(foreignValue!=null){
					this.text = foreignValue.toString();
					//�����ݿ��еķ�б�߶���������ԭ����ʾ
					this.text = this.text.replaceAll("\\\\", "\\\\\\\\");
					this.text = this.text.replaceAll("\r", "\\\\r");
					this.text = this.text.replaceAll("\n", "\\\\n");	
					/*
					 * �����ʱ��Ͳ��ð��û�����Щ�س������滻��, �û�ԭ����ʲô�͸�������ʲô
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
					propertyValue = intValue.intValue()==1?"��":"��";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //����Ƿ��б������ֵΪnull����Ϊ��ʾ��
					this.value = "";
					this.text = "";
				}
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object propertyValue  = bw.getPropertyValue(propertyName);
				if(propertyValue!=null){
					Integer intValue = Integer.valueOf(propertyValue.toString());
					propertyValue = intValue.intValue()==1?"��":"Ů";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //����Ƿ��б������ֵΪnull����Ϊ��ʾ��
					this.value = "";
					this.text = "";
				}
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				BeanWrapper bw = new BeanWrapperImpl(object);
				Object propertyValue  = bw.getPropertyValue(propertyName);
				if(propertyValue!=null){
					Integer intValue = Integer.valueOf(propertyValue.toString());
					propertyValue = intValue.intValue()==1?"��":"��";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //����Ƿ��б������ֵΪnull����Ϊ��ʾ��
					this.value = "";
					this.text = "";
				}
			}else if(columnTypenName.equalsIgnoreCase("foreiText")){
				//�ͻ��������Ӵ���
				SystemMainTable foreiTable = column.getForeignTable();
				if(foreiTable!=null){

					Integer isVirtualColumn = mc.getIsNullForeignColumn();//�Ƿ�ʱ�����ֶ�
					if(isVirtualColumn!=null&& isVirtualColumn.intValue()==1){
						SystemMainTable smt = mc.getSystemMainTable();
						//�ͻ���ַ-���ͻ��ֶ�
						SystemMainTableColumn mainForeiColumn = ms.findMainForeiColumn(smt, foreiTable);
						//�ͻ���ַ-���ͻ��ֶε��������ƣ���customer
						String mainForeiColumnProp = mainForeiColumn.getPropertyName();
						BeanWrapper mainForeiColumnBw = new BeanWrapperImpl(object);
						//���ؿͻ�����
						Object mainForeiObject = mainForeiColumnBw.getPropertyValue(mainForeiColumnProp); 

						Object virtualColumnValue = null; //�����б����ݵ�id					
						try {
							BeanWrapper mainForeiObjectWrapper = new BeanWrapperImpl(mainForeiObject); //����entityΪ����ʹ�ö������������,customer.address
							//�ͻ���ַ
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
				//�Ӹ������ȡ����ֵ
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //��ȡ�������������������
				//begin
				Integer abstractFlag = mc.getAbstractFlag(); //�Ƿ������
				if(abstractFlag!=null && abstractFlag.intValue()==1){
					//�����ֶ�
					SystemMainTableColumn discColumn = mc.getDiscColumn();
					//�����ֶ������õı�
					SystemMainTable foreignDiscTable = mc.getForeignDiscTable();
					//�����ֶεı�������ͻ����ͱ�
					String fdisctTableName = foreignDiscTable.getTableName();
					//�����ֶ������ñ��������
					String foreignDiscTableClassname = foreignDiscTable.getClassName();
					//�����ֶ������ñ����
					@SuppressWarnings("unused")
					Class foreignDiscTableClass = this.getClass(foreignDiscTableClassname);
					//systemColumnService
					if(fObject!=null){
						this.value = fObject.toString();
						this.key = Long.valueOf(fObject.toString());
						//�����ֶ���������
						String discPropName = discColumn.getPropertyName();
						//�����ֶ����ԵĶ���ֵ
						Object discPropValue = baseObjectWrapper.getPropertyValue(discPropName);
						//�����ֶ����ԵĶ���ֵid
						Long discPropIdValue = ((BaseObject)discPropValue).getId();
						//���ڲ��ͻ�
						String pClassName = systemColumnService.findClassNameByDisc(discPropIdValue.toString(), fdisctTableName);
						Class pClass = this.getClass(pClassName);
						BaseObject absObject = (BaseObject) bs.find(pClass, fObject.toString(), true);
						String name = absObject.getName();
						
						this.text = name; //this.key.toString();
						
					}
					
					
				}else{//end
					//begin old
//					�������ʹ˶���ҳ������������б����ʱ���ݹ�������������ж��Զ�ѡ���ĸ���Ŀ
					this.value=fObject;
					if(fObject!=null){ //��������null
						//��װ��������
						baseObjectWrapper.setWrappedInstance(fObject);
						//��ȡ���������id
						Object fObjectKey = baseObjectWrapper.getPropertyValue(fTable.getPrimaryKeyColumn().getPropertyName());
						this.key = Long.valueOf(fObjectKey.toString());
					}
					
					//��ȡ�����������͵��������ݣ�����ҳ����ʾ�����������Ե������б����ڸ����б�����ʾ2���б�
					if(fParentColumn==null) { //��ͨ�б�ֻ��ʾ�������һ���ֶ�
						if(fObject!=null){ //��������null
							//��װ��������
							baseObjectWrapper.setWrappedInstance(fObject);
							//��ȡ���������id
							if(mc.getForeignTableValueColumn()!=null){
								String fClassName = fTable.getClassName();
								
								Object fObjectText = null;
								//�˴������û��б�ҳ����ʾ�û��û�������ʵ����
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
					}else { //�����б���ʾ������������ֶκ͸��ֶ�
						if(fObject!=null){ //��������null
							//��װ��������
							baseObjectWrapper.setWrappedInstance(fObject);
							//��ȡ���������id
							Object fObjectText = baseObjectWrapper.getPropertyValue(mc.getForeignTableValueColumn().getPropertyName());
							this.text = fObjectText.toString();
							//��Ź�������ĸ�����ֵ
							Object pObjectText = null;
							//������
							Object pObject = baseObjectWrapper.getPropertyValue(fParentColumn.getPropertyName());
							if(pObject!=null){ //�и�����
								baseObjectWrapper.setWrappedInstance(pObject); //��װ������
								pObjectText = baseObjectWrapper.getPropertyValue(fValueColumn.getPropertyName());
								if(pObjectText!=null){
									this.text = pObjectText+ "��" + this.text;
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
				
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //��ȡ�������������������
				//�������ʹ˶���ҳ������������б����ʱ���ݹ�������������ж��Զ�ѡ���ĸ���Ŀ
				this.value=fObject;
				if(fObject!=null){ //��������null
					//��װ��������
					baseObjectWrapper.setWrappedInstance(fObject);
					//��ȡ���������id
					Object fObjectKey = baseObjectWrapper.getPropertyValue(fTable.getPrimaryKeyColumn().getPropertyName());
					this.key = Long.valueOf(fObjectKey.toString());
				}
				
				//��ȡ�����������͵��������ݣ�����ҳ����ʾ�����������Ե������б����ڸ����б�����ʾ2���б�
				if(fParentColumn==null) { //��ͨ�б�ֻ��ʾ�������һ���ֶ�
					if(fObject!=null){ //��������null
						//��װ��������
						baseObjectWrapper.setWrappedInstance(fObject);
						//��ȡ���������id
						if(mc.getForeignTableValueColumn()!=null){
							Object fObjectText = baseObjectWrapper.getPropertyValue(mc.getForeignTableValueColumn().getPropertyName());
							if(fObjectText==null){
								this.text="";
							}else{
								this.text = fObjectText.toString();
							}
						}
					}
				}else { //�����б���ʾ������������ֶκ͸��ֶ�
					if(fObject!=null){ //��������null
						//��װ��������
						baseObjectWrapper.setWrappedInstance(fObject);
						//��ȡ���������id
						Object fObjectText = baseObjectWrapper.getPropertyValue(mc.getForeignTableValueColumn().getPropertyName());
						this.text = fObjectText.toString();
						//��Ź�������ĸ�����ֵ
						Object pObjectText = null;
						//������
						Object pObject = baseObjectWrapper.getPropertyValue(fParentColumn.getPropertyName());
						if(pObject!=null){ //�и�����
							baseObjectWrapper.setWrappedInstance(pObject); //��װ������
							pObjectText = baseObjectWrapper.getPropertyValue(fValueColumn.getPropertyName());
							if(pObjectText!=null){
								this.text = pObjectText+ "��" + this.text;
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
				Object rObject = baseObjectWrapper.getPropertyValue(propertyName); //��ȡ�����ö���ļ���
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
				
				
				//fObject��һ��Set<Role>
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //��ȡ�������������������
				Set roles = (Set) fObject;
				this.list.addAll(roles);
				
				//������
//				SystemMainTable foreignTable = column.getReferencedTable(); //userRole
//				SystemMainTableColumn foreignTableKeyColumn = column.getReferencedTableKeyColumn();//userRole.id
//				SystemMainTableColumn foreignTableValueColumn = column.getReferencedTableValueColumn();//userRole.roleid
//				SystemMainTableColumn referencedTableParentColumn = column.getReferencedTableParentColumn();//userRole.userid
//				Integer referencedTableValueColumnOrder = column.getReferencedTableValueColumnOrder();
	
				
			}else if(columnTypenName.equalsIgnoreCase("file")){
				SystemMainTable fTable = mc.getReferencedTable();
				SystemMainTableColumn ftableKeyColumn = mc.getForeignTableKeyColumn();
				//�߼��ļ����ƶ�Ӧ���ֶ�fileName
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
				
				Object fObject = baseObjectWrapper.getPropertyValue(propertyName); //��ȡ�������������������
				//�������ʹ˶���ҳ������������б����ʱ���ݹ�������������ж��Զ�ѡ���ĸ���Ŀ
				this.value=fObject;
				if(fObject!=null){ //��������null
					if(fObject instanceof BaseObject){
//						��װ��������
						baseObjectWrapper.setWrappedInstance(fObject);
						//��ȡ���������id
						Object fObjectKey = baseObjectWrapper.getPropertyValue(ftableKeyColumn.getPropertyName());
						this.key = Long.valueOf(fObjectKey.toString());
						//��ȡ�������������
						Object fObjectText = baseObjectWrapper.getPropertyValue(ftableValueColumn.getPropertyName());
						this.text = String.valueOf(fObjectText.toString());
						//��ȡ�������󸽼���ϵͳ�ļ�������
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
						this.text = "����";
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
//					throw new ServiceException("�฽���ֶα���ѡ������ĸ�������");
//				}
//				String fClassName = column.getForeignTable().getClassName();
//				Class clazz = null;
//				try {
//					clazz = Class.forName(fClassName);
//				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
//				}
			
//				String multiFileIds = (String) baseObjectWrapper.getPropertyValue(propertyName); //��ȡ���������id
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
					propertyValue = intValue.intValue()==1?"��":"��";
					this.value = propertyValue;
					this.text = this.value.toString();
					this.key = new Long(intValue.intValue());
				}else{ //����Ƿ��б������ֵΪnull����Ϊ��ʾ��
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
	 * �����ֶγ�ʼ��"����������"��"���й�������ļ�����������"����VIEW��EDITʱ��Ҫ��ʾ�������
	 * ���ݣ���Ҫ��ʼ�������б�����
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
	 * ����Map��ʽ���ݣ����Ƿ��б���Ա��б�����
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
	 * ��ȡ�б��������ݣ������ָ������͵��б�ʹ�ô˷���������������
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
