package com.zsgj.info.appframework.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.ExtData;
import com.zsgj.info.appframework.metadata.entity.Operator;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemTableColumnCondition;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.ValidateType;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.util.DateUtil;

/**
 * 
 * �ֶβ�ѯ����,ʵ�ֻ��ڶ��ƵĲ�ѯ����ӿڡ�
 * ����ҵ��仯�����ںܶ�������������ܿ���޷����Ǵ������
 * ��ʱ���Ե���ʵ��ColumnQueryService��������Ӧ�ķ��������
 * ���ƿ��֮��������ѯ�������á�
 * 
 * ֮���������ѯ����ӿ���Ϊ��ʹ��spring��ע�빦�ܡ���Ҫ�������ڽӿڣ��ſ�����ɴ���
 * 
 * @Class Name ColumnQueryService
 * @Author peixf
 * @Create In 2008-5-30
 */
public abstract class ColumnQueryService extends BaseDao implements QueryService{
	private DepartmentService deptService;
	/**
	 * �Բ�ѯ���������ƣ���ǰ�˲�ѯ�������������า�Ǵ˷�����
	 * @param criteria
	 * @param extParams
	 */
	public void middle(Criteria criteria, Map extParams) {
		
		this.middle(criteria);
	}

	/**
	 * �Բ�ѯ���������ƣ�����ǰ�˲�ѯ�������������า�Ǵ˷�����
	 * ���������з����������
	 * @param criteria
	 */
	public void middle(Criteria criteria) {}

	/**
	 * ʹ�ò���[��ѯ��������ҳ�������������]�����ز�ѯ��ҳ����
	 * @Methods Name query
	 * @Create In 2008-5-29 By peixf
	 * @param clazz
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	public Page query(Class clazz, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc){
		//before
		Criteria  criteria = this.before(clazz, params, pageNo, pageSize, orderProp, isAsc);
		this.middle(criteria);
		//end
		return this.end(criteria,params, pageNo, pageSize);
	}
	
	public Page query(Class clazz, Map params, Map extParams, int pageNo, int pageSize, String orderProp, boolean isAsc){
		//before
		Criteria  criteria = this.before(clazz, params, pageNo, pageSize, orderProp, isAsc);
		//���������middle��ע��˷����ڲ��ȵ����޲ε�middle
		this.middle(criteria, extParams);
		//end
		return this.end(criteria,params, pageNo, pageSize);
	}
	
	public Page queryByParams(Class clazz, Map<String, Object> params, Map<String, Object> extParams, 
				int pageNo, int pageSize, String orderProp, boolean isAsc) {

		Criteria  criteria = this.beforeByParams(clazz, params, pageNo, pageSize, orderProp, isAsc);
		//���������middle��ע��˷����ڲ��ȵ����޲ε�middle
		this.middle(criteria, extParams);
		//end
		return this.endByParams(clazz, criteria, params, pageNo, pageSize);
		//return this.end(criteria, pageNo, pageSize);
	}
	
	public Page queryByParamsForUser(Class clazz, Map<String, Object> queryParams,
			Object object, int pageNo, int pageSize, String orderProp,
			boolean isAsc, String propertyName){
		Criteria  criteria = this.beforeByParams(clazz, queryParams, pageNo, pageSize, orderProp, isAsc);

		//�����û���ɫ�鿴����
		List list = this.getDataScopeUsers();
		if(list!=null){
			criteria.add(Restrictions.in(propertyName,list));
		}
		//end
		return this.endByParams(clazz,criteria, queryParams, pageNo, pageSize);
	}
	private List getDataScopeUsers(){
		UserInfo userInfo = UserContext.getUserInfo();
		List<UserInfo> list = new ArrayList();
		list.add(userInfo);
		Set<Role> curRole = userInfo.getRoles();
		for(Role role : curRole){
			if(role.getDataViewFlag().equals(Role.VIEW_FLAG_ALL)){//�Ƿ�ɲ鿴��������Ȩ��
				Department roleDept = role.getDepartment();
				if(roleDept.getParentDepartment()==null){
					return null;	//�����ɫ����Ϊ����ţ��򷵻�null
				}else{
					Department dept = userInfo.getDepartment();
					List depts = deptService.findDeptByParentCode(dept.getDepartCode().toString());//��ȡ���ŵ������Ӳ���
					depts.add(dept);
					Criteria criteria = super.getCriteria(UserInfo.class);
					criteria.add(Restrictions.in("department", depts));
					List deptUsers = criteria.list();
					list.addAll(deptUsers);//�������������˼���鿴�û���Χ
				}
				
			}
		}
		return list;
	}
	public Page queryForTree(Class clazz, Map<String, Object> params, Map<String, Object> extParams, int pageNo, int pageSize, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List queryByParams(Class clazz, Map<String, Object> params, Map<String, Object> extParams, 
			String orderProp, boolean isAsc) {
		//�˳�ʹ��2����ҳ������ʵ�����壬ֻ�ǲ���ඨ��һ��beforeByParams����
		Criteria  criteria = this.beforeByParams(clazz, params, 0, 0, orderProp, isAsc);
		//���������middle��ע��˷����ڲ��ȵ����޲ε�middle
		this.middle(criteria, extParams);
		return this.end(criteria);//Ϊ����list���صķ���
	}

	/**
	 * �����࣬��ѯ��������ҳ������������������ز�ѯCriteria
	 * @Methods Name before
	 * @Create In 2008-5-29 By peixf
	 * @param entityClazz
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Criteria
	 */
	public Criteria before(Class entityClazz, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc){
		Criteria critea = createCriteria(entityClazz);
		critea.add(Restrictions.isNotNull("this.id"));
		
		Set keySet = params.keySet();
		Iterator keyItera = keySet.iterator();
		while(keyItera.hasNext()){
			Object columnValue = null;
			Object keyObj = keyItera.next();
			if(keyObj instanceof java.lang.String){ //���keyΪ�ַ������ͣ���Ϊ�������������
				continue;
			}
			SystemTableQueryColumn sysTableQueryColumn = (SystemTableQueryColumn) keyObj;
			Integer matchMode = sysTableQueryColumn.getMatchModeAsInt();
			
			if(sysTableQueryColumn.isSystemColumn()){
				SystemMainTableColumn smtc = sysTableQueryColumn.getMainTableColumn();
				SystemMainTableColumnType colType = smtc.getSystemMainTableColumnType();
				
				String propertyName = smtc.getPropertyName();
				Object propertyValue = params.get(sysTableQueryColumn);
				
				SystemMainTable foreiTable = smtc.getForeignTable();
				sysTableQueryColumn.getColumn().getPropertyType();
				Integer isHiddenItem = sysTableQueryColumn.getIsHiddenItem();
				if(isHiddenItem!=null&& isHiddenItem.intValue()==1){
					Column column = sysTableQueryColumn.getColumn();
					String type=null;
					if(column.getPropertyType()!=null){
						type=column.getPropertyType().getPropertyTypeName();
						if(type.equals("Integer")){
							if(propertyValue!=null){
								if(propertyValue.toString().indexOf("_")!=-1){
									String propertyValues = propertyValue.toString();
									String[] propValues = propertyValues.split("_");
									List list = new ArrayList();
									for(String propValue : propValues){
										list.add(Integer.valueOf(propValue));
									}
									critea.add(Restrictions.in("this."+propertyName, list));
								}else{
									critea.add(Restrictions.eq("this."+propertyName, Integer.parseInt(propertyValue.toString())));
								}
								
							}
						}else if(type.equals("String")){
							if(propertyValue!=null){
								critea.add(Restrictions.eq("this."+propertyName, propertyValue.toString()));
							}
						}else if(type.equals("Long")){
							if(propertyValue!=null){
								critea.add(Restrictions.eq("this."+propertyName, Long.parseLong(propertyValue.toString())));
							}
						}else if(type.equals("Double")){
							if(propertyValue!=null){
								critea.add(Restrictions.eq("this."+propertyName, Double.parseDouble(propertyValue.toString())));
							}
						}else if(type.equals("Date")){
							if(propertyValue!=null){
								critea.add(Restrictions.eq("this."+propertyName, DateUtil.convertStringToDate(propertyValue.toString())));
							}
						}else if(type.equals("BaseObject")){
							if(propertyValue!=null){
								if(foreiTable!=null){
									String foreiClassname = foreiTable.getClassName();
									
									if(propertyValue instanceof BaseObject){
										columnValue = propertyValue;
									}else if(propertyValue instanceof java.lang.String){
										Long queryParamValueLong = Long.valueOf(propertyValue.toString());
										columnValue = super.get(foreiClassname, queryParamValueLong);
									}else if(propertyValue instanceof java.lang.Integer){
										Long queryParamValueLong = Long.valueOf(propertyValue.toString());
										columnValue = super.get(foreiClassname, queryParamValueLong);
									}else if(propertyValue instanceof java.lang.Long){
										Long queryParamValueLong = Long.valueOf(propertyValue.toString());
										columnValue = super.get(foreiClassname, queryParamValueLong);
									}
									
									critea.add(Restrictions.eq("this."+propertyName, columnValue));
								}else{
									critea.add(Restrictions.eq("this."+propertyName, propertyValue));
								}
							}
						}
					}
					
				}else{
					if(colType.getColumnTypeName().equalsIgnoreCase("text")
							||colType.getColumnTypeName().equalsIgnoreCase("textArea")
							){
						columnValue = propertyValue;
						if(matchMode==null){
							critea.add(Restrictions.eq("this."+propertyName, propertyValue));
						}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
							if(propertyValue!=null){
								critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.ANYWHERE));
							}
							
						}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
							if(propertyValue!=null){
								critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.EXACT));
							}
						}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
							if(propertyValue!=null){
								critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.START));
							}
						}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
							if(propertyValue!=null){
								critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.END));
							}
						}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_BETWEEN)){ 
							Object begin = params.get(propertyName+"Begin");
							Object end = params.get(propertyName+"End");
							ValidateType validateType = smtc.getValidateType();
							String validateTypeName = validateType.getValidateTypeName();
							if(begin!=null&& !begin.equals("")){
								if(validateTypeName.equalsIgnoreCase("Currency")
										||validateTypeName.equalsIgnoreCase("Double")
										||validateTypeName.equalsIgnoreCase("Number") ){
									begin = new Double(begin.toString());
								}else if(validateTypeName.equalsIgnoreCase("Integer")){
									begin = new Integer(begin.toString());
								}
								
								//critea.add(Restrictions.ge("this."+propertyName, begin));
								Disjunction disj = Restrictions.disjunction();
								disj.add(Restrictions.gt("this."+propertyName, begin));
								disj.add(Restrictions.eq("this."+propertyName, begin));
								critea.add(disj);
							}
							if(end!=null && !end.equals("")){
								if(validateTypeName.equalsIgnoreCase("Currency")
										||validateTypeName.equalsIgnoreCase("Double")
										||validateTypeName.equalsIgnoreCase("Number") ){
									end = new Double(end.toString());
								}else if(validateTypeName.equalsIgnoreCase("Integer")){
									end = new Integer(end.toString());
								}
								//critea.add(Restrictions.le("this."+propertyName, end));
								Disjunction disj = Restrictions.disjunction();
								disj.add(Restrictions.lt("this."+propertyName, end));
								disj.add(Restrictions.eq("this."+propertyName, end));
								critea.add(disj);
							}
						}
						
					}else if(colType.getColumnTypeName().equalsIgnoreCase("select")){
						String foreiClassname = foreiTable.getClassName();
						String foreiTableName = foreiTable.getTableName();
						//old code 
						//columnValue = super.get(foreiClassname, Long.valueOf(propertyValue.toString()));
						//ָ����������Ĳ�ѯ����
		
						//ȡ��������, new codes
						if(propertyValue instanceof BaseObject){
							columnValue = propertyValue;
						}else if(propertyValue instanceof java.lang.String){
							Long queryParamValueLong = Long.valueOf(propertyValue.toString());
							columnValue = super.get(foreiClassname, queryParamValueLong);
						}else if(propertyValue instanceof java.lang.Integer){
							Long queryParamValueLong = Long.valueOf(propertyValue.toString());
							columnValue = super.get(foreiClassname, queryParamValueLong);
						}else if(propertyValue instanceof java.lang.Long){
							Long queryParamValueLong = Long.valueOf(propertyValue.toString());
							columnValue = super.get(foreiClassname, queryParamValueLong);
						}
						
						if(sysTableQueryColumn.isParentType()==true){
							String childSetName = "child"+foreiTableName+"s";
											Object parentObj =  get(foreiClassname, (Serializable) Long.valueOf(propertyValue.toString()));
							BeanWrapper bwp = new BeanWrapperImpl(parentObj);
							Set childSet = (Set) bwp.getPropertyValue(childSetName);

							if(childSet!=null&& !childSet.isEmpty()){
								critea.add(Restrictions.in("this."+propertyName, childSet));
							}
						}else{
							critea.add(Restrictions.eq("this."+propertyName, columnValue));
						}
					}
					else if(colType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")){
						columnValue = new Integer(propertyValue.toString());
						critea.add(Restrictions.eq("this."+propertyName, columnValue));
					}
					else if(colType.getColumnTypeName().equalsIgnoreCase("hidden")){
						Column column = sysTableQueryColumn.getColumn();
						String type=null;
						if(column.getPropertyType()!=null){
							type=column.getPropertyType().getPropertyTypeName();
							if(type.equals("Integer")){
								if(propertyValue!=null){
									if(propertyValue.toString().indexOf("_")!=-1){
										String propertyValues = propertyValue.toString();
										String[] propValues = propertyValues.split("_");
										List list = new ArrayList();
										for(String propValue : propValues){
											list.add(Integer.valueOf(propValue));
										}
										critea.add(Restrictions.in("this."+propertyName, list));
									}else{
										critea.add(Restrictions.eq("this."+propertyName, Integer.parseInt(propertyValue.toString())));
									}
									
								}
								
							}else if(type.equals("String")){
								if(propertyValue!=null){
									critea.add(Restrictions.eq("this."+propertyName, propertyValue.toString()));
								}
								
							}else if(type.equals("Long")){
								if(propertyValue!=null){
									critea.add(Restrictions.eq("this."+propertyName, Long.parseLong(propertyValue.toString())));
								}
								
							}else if(type.equals("Double")){
								if(propertyValue!=null){
									critea.add(Restrictions.eq("this."+propertyName, Double.parseDouble(propertyValue.toString())));
								}
								
							}else if(type.equals("Date")){
								if(propertyValue!=null){
									critea.add(Restrictions.eq("this."+propertyName, DateUtil.convertStringToDate(propertyValue.toString())));
								}
							
							}else if(type.equals("BaseObject")){
								if(propertyValue!=null){
									critea.add(Restrictions.eq("this."+propertyName, propertyValue));
								}
							
							}
						}
//						columnValue = new Long(propertyValue.toString());
//						critea.add(Restrictions.eq("this."+propertyName, columnValue));
//						critea.list();
					}
					else if(colType.getColumnTypeName().equalsIgnoreCase("dateText")){ //���ڿؼ�
						String matchModeStr = sysTableQueryColumn.getMatchModeStr();
						//������������
						if(matchModeStr!=null&& matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
							Object begin = params.get(propertyName+"Begin");
							Object end = params.get(propertyName+"End");
							if(begin!=null&& !begin.equals("")){
								Date dateBegin = DateUtil.convertStringToDate(begin.toString());
								critea.add(Restrictions.ge("this."+propertyName, dateBegin));
							}
							if(end!=null&& !end.equals("")){
								Date dateEnd = DateUtil.convertStringToDate(end.toString());
								critea.add(Restrictions.le("this."+propertyName, dateEnd));
							}
						}else{ //����׼ȷ����
							columnValue = DateUtil.convertStringToDate(propertyValue.toString());
							critea.add(Restrictions.eq("this."+propertyName, columnValue));
						}	
					}
				}
				
			}/*else if(sysTableQueryColumn.isExtendColumn()){//��¼����Щ��չ�ֶεĲ�ѯ
				extSystemTableQueryColumns.add(sysTableQueryColumn);
			}*/

		}
		if(orderProp!=null&& !orderProp.equals("")){
			if(isAsc==true){
				critea.addOrder(Order.asc(orderProp));
			}else {
				critea.addOrder(Order.desc(orderProp));
			}
		}
		return critea;
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
	
	Object getValue(Column smtc, PropertyType pt, String value){
		Object result = null;
		String ptName = pt.getPropertyTypeName();
		
		if(ptName.equalsIgnoreCase("BaseObject")){
			SystemMainTable ftable = smtc.getForeignTable();
//			SystemMainTableColumn fvc = smtc.getForeignTableValueColumn();
			String ftableClass = ftable.getClassName();
			Class fclass = this.getClass(ftableClass);
			result = super.getObject(fclass, value, true);
			
		}else if(ptName.equalsIgnoreCase("String")){
			result = value;
			
		}else if(ptName.equalsIgnoreCase("Long")){
			result = Long.valueOf(value);
			
		}else if(ptName.equalsIgnoreCase("Integer")){
			result = Integer.valueOf(value);
			
		}else if(ptName.equalsIgnoreCase("Double")){
			result = Double.valueOf(value);
			
		}else if(ptName.equalsIgnoreCase("Date")){
			result = Double.valueOf(value); DateUtil.convertStringToDate(value);
			
		}     
		
		return result;
	}
	
	public Criteria beforeByParams(Class entityClazz, Map<String, Object> params, int pageNo, int pageSize, String orderProp, boolean isAsc){
		Criteria critea = createCriteria(entityClazz);
		critea.add(Restrictions.isNotNull("this.id"));
		
		SystemColumnService scs = (SystemColumnService)ContextHolder.getBean("systemColumnService");
		SystemMainTableService smts = (SystemMainTableService)ContextHolder.getBean("systemMainTableService");
		SystemMainTable smt = smts.findSystemMainTableByClazz(entityClazz);
		
		//��ȡһ��ʵ���Ĭ�ϵĲ�ѯ���������Զ�����status=-1�ļ�¼��������ʾ��ɾ����
		/*
		 * ��������ʹ��SystemTableColumnCondition�����˲�Ӧ���ֵ����ݣ�������Ӧ����ȷ��ʾ����ʷ��������Ҳͬʱ�����ˣ�
		 * ��������жϣ�����������Ϊid�Ļ�����ʹ��SystemTableColumnCondition��������
		 */
		if(params.get("id")==null){//add by lee for show the histroy record in 20100419 
			Criteria cBeforeParams = super.getCriteria(SystemTableColumnCondition.class);
			cBeforeParams.add(Restrictions.eq("systemMainTable", smt));
			cBeforeParams.add(Restrictions.eq("tableDefaultFlag", Integer.valueOf(1)));
			List<SystemTableColumnCondition> stccs = cBeforeParams.list();
			for(SystemTableColumnCondition condit : stccs){
				SystemMainTableColumn mainTableColumn = condit.getMainTableColumn();
				Operator operator = condit.getOperator();
				PropertyType pt = mainTableColumn.getPropertyType();
				
				String value = condit.getValue();
//				Integer logicType = condit.getLogicType();
				if(value!=null){
					if(operator.getName().equalsIgnoreCase(Operator.OPERATOR_EQ)){
						critea.add(Restrictions.eq(mainTableColumn.getPropertyName(), getValue(mainTableColumn, pt, value)));
					}else if(operator.getName().equalsIgnoreCase(Operator.OPERATOR_NE)){
						Disjunction disjunction = Restrictions.disjunction();
						disjunction.add(Restrictions.isNull(mainTableColumn.getPropertyName()));
						disjunction.add(Restrictions.ne(mainTableColumn.getPropertyName(), getValue(mainTableColumn, pt, value)));
						critea.add(disjunction); //δ�ж�null���
						//critea.add(Restrictions.ne(mainTableColumn.getPropertyName(), getValue(mainTableColumn, pt, value)));
					}
				}else{
					critea.add(Restrictions.isNull(mainTableColumn.getPropertyName()));
				}
			}
		}//add by lee for show the histroy record in 20100419 
		
		List columns = scs.findSystemTableColumns(smt);
		Iterator iter = columns.iterator();
		while(iter.hasNext()){
			SystemMainTableColumn column = (SystemMainTableColumn) iter.next();
			String propertyName = column.getPropertyName();
			Object propertyValue = params.get(propertyName);
			//SystemMainTableColumn column=(SystemMainTableColumn)column;
			if(column.getIsExtColumn()==SystemMainTableColumn.isMain){
				PropertyType propType = column.getPropertyType();
				if(propType==null){
					throw new ServiceException("��ѡ���ֶ�����");
				}
				String type = propType.getPropertyTypeName();
				if(propertyValue!=null&& !propertyValue.toString().equals("")){
					
					if(type.equals("Integer")){
						if(propertyValue instanceof java.util.Set){
							Set set = (Set)propertyValue;
							critea.add(Restrictions.in("this."+propertyName, set));
						}else{
							critea.add(Restrictions.eq("this."+propertyName, Integer.valueOf(propertyValue.toString())));
						}
						
					}else if(type.equals("String")){
						if(propertyValue instanceof java.util.Set){
							Set set = (Set)propertyValue;
							critea.add(Restrictions.in("this."+propertyName, set));
						}else{
							Criteria c = super.getCriteria(SystemTableQueryColumn.class);
							c.add(Restrictions.eq("systemMainTable", smt));
							c.add(Restrictions.eq("mainTableColumn", column));
							List list = c.list();
							if(!list.isEmpty()){
								SystemTableQueryColumn stqc = (SystemTableQueryColumn) list.iterator().next();
								Integer matchMode = stqc.getMatchModeAsInt();
								if(matchMode!=null){
									if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
										critea.add(Restrictions.like("this."+propertyName, String.valueOf(propertyValue), MatchMode.ANYWHERE));
									}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
										critea.add(Restrictions.like("this."+propertyName, String.valueOf(propertyValue), MatchMode.START));
									}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
										critea.add(Restrictions.like("this."+propertyName, String.valueOf(propertyValue), MatchMode.EXACT));
									}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
										critea.add(Restrictions.like("this."+propertyName, String.valueOf(propertyValue), MatchMode.END));
									}else{
										throw new ServiceException("string�����ֶΣ�������ѯ����ֵ������ʹ�������ѯ");
									}
								}
								super.evict(stqc);
							}
							//critea.add(Restrictions.like("this."+propertyName, String.valueOf(propertyValue)));
						}
					}else if(type.equals("Long")){
						critea.add(Restrictions.eq("this."+propertyName, Long.valueOf(propertyValue.toString())));
					}else if(type.equals("Double")){
						if(propertyValue!=null){
							critea.add(Restrictions.eq("this."+propertyName, Double.valueOf(propertyValue.toString())));
						}
					}else if(type.equals("Date")){
						if(propertyValue!=null){
							critea.add(Restrictions.eq("this."+propertyName, propertyValue));
						}
					}else if(type.equals("BaseObject")){
						if(propertyValue!=null){
							if(propertyValue instanceof java.util.Set){
								Set<Long> set = (Set<Long>)propertyValue;
								critea.add(Restrictions.in("this."+propertyName+".id", set));
							}else{
								critea.add(Restrictions.eq("this."+propertyName, propertyValue));
							}
							
						}
					}/*else{
						critea.add(Restrictions.eq("this."+propertyName, propertyValue));
					}*/
				}else{//�����ѯ
					if(type.equals("Double")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						Object queryParamValueBegin = params.get(propertyNameBegin);
						Object queryParamValueEnd = params.get(propertyNameEnd);
						
						if(queryParamValueBegin!=null&& !queryParamValueBegin.toString().equals("")){
							critea.add(Restrictions.ge("this."+propertyName, queryParamValueBegin));
						}
						
						if(queryParamValueEnd!=null&& !queryParamValueEnd.toString().equals("")){
							critea.add(Restrictions.le("this."+propertyName, queryParamValueEnd));
						}
					}else if(type.equals("Date")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						Object queryParamValueBegin = params.get(propertyNameBegin);
						Object queryParamValueEnd = params.get(propertyNameEnd);
						
						//���޸ģ�Ϊ����
						Object queryParamValue = params.get(propertyName);
						if(queryParamValue!=null&& !queryParamValue.toString().equals("")){
							critea.add(Restrictions.eq("this."+propertyName, queryParamValue));
						}else{
							if(queryParamValueBegin!=null&& !queryParamValueBegin.toString().equals("")){
								critea.add(Restrictions.ge("this."+propertyName, queryParamValueEnd));
							}
							
							if(queryParamValueEnd!=null&& !queryParamValueEnd.toString().equals("")){
								critea.add(Restrictions.le("this."+propertyName, queryParamValueEnd));
							}
						}
						
						
					}
					
				}
				
			}else{//��չ�ֶ�
				
			}
		}
		
		if(orderProp!=null&& !orderProp.equals("")){
			if(isAsc==true){
				critea.addOrder(Order.asc(orderProp));
			}else {
				critea.addOrder(Order.desc(orderProp));
			}
		}
		return critea;
	}
	
	/**
	 * ���ط�ҳ���
	 * @Methods Name end
	 * @Create In 2008-5-29 By peixf
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public final Page end(Criteria criteria, int pageNo, int pageSize){
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
	
		Page page = this.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	
	public final List end(Criteria criteria){
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		return criteria.list();
	}
/* һ��������	
	private List findExt1(Map params,SystemTableQueryColumn sysTableQueryColumn){
		    Integer matchMode = sysTableQueryColumn.getMatchModeAsInt();
			SystemMainTableExtColumn smtec = sysTableQueryColumn.getExtendTableColumn();
			SystemMainTableColumnType colType = smtec.getSystemMainTableColumnType();
			String colTypeName=colType.getColumnTypeName();
			Long extColuId=smtec.getId();
			//Integer extTableColumnNum=smtec.getExtendTableColumnNum();
			String propertyName = smtec.getPropertyName();
			Object propertyValue = params.get(sysTableQueryColumn);	
			List tableIds=new ArrayList();
			if(colTypeName.equals("text")||colTypeName.equals("textArea")||colTypeName.equals("htmlEditor")){
				Criteria c=this.createCriteria(ExtData.class);
				c.add(Restrictions.eq("extendTableId",Integer.parseInt(extColuId.toString())));
				if(matchMode==null){
				//c.add(Restrictions.eq("mainTableRowID", tableId));
				c.add(Restrictions.eq("extendTableData", propertyValue));
				}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
					if(propertyValue!=null){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.ANYWHERE));	
					}
					
				}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
					if(propertyValue!=null){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.EXACT));	
					}
				}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
					if(propertyValue!=null){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.START));	
					}
				}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
					if(propertyValue!=null){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.END));	
					}
				}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_BETWEEN)){ 
					Object begin = params.get(propertyName+"Begin");
					Object end = params.get(propertyName+"End");
					ValidateType validateType = smtec.getValidateType();
					String validateTypeName = validateType.getValidateTypeName();
					if(begin!=null&& !begin.equals("")){
						if(validateTypeName.equalsIgnoreCase("Currency")
								||validateTypeName.equalsIgnoreCase("Double")
								||validateTypeName.equalsIgnoreCase("Number") ){
							begin = new Double(begin.toString());
						}else if(validateTypeName.equalsIgnoreCase("Integer")){
							begin = new Integer(begin.toString());
						}
						if(begin!=null){
							//c.add(Restrictions.eq("mainTableRowID", tableId));
							c.add(Restrictions.ge("extendTableData", begin));	
						}
					}
					if(end!=null && !end.equals("")){
						if(validateTypeName.equalsIgnoreCase("Currency")
								||validateTypeName.equalsIgnoreCase("Double")
								||validateTypeName.equalsIgnoreCase("Number") ){
							end = new Double(end.toString());
						}else if(validateTypeName.equalsIgnoreCase("Integer")){
							end = new Integer(end.toString());
						}if(end!=null){
							//c.add(Restrictions.eq("mainTableRowID", tableId));
							c.add(Restrictions.le("extendTableData", end));	
						}
					}
				}
				List<ExtData> list= c.list();
				for(ExtData et:list){
					tableIds.add(et.getMainTableRowID());
				}
				return tableIds;
			}else if(colTypeName.equals("dateText")){
				String matchModeStr = sysTableQueryColumn.getMatchModeStr();
				Criteria c=this.createCriteria(ExtData.class);
				c.add(Restrictions.eq("extendTableId",Integer.parseInt(extColuId.toString())));
				//������������
				if(matchModeStr!=null&& matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
					Object begin = params.get(propertyName+"Begin");
					Object end = params.get(propertyName+"End");
					if(begin!=null&& !begin.equals("")){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						String dateBegin = begin.toString();
						c.add(Restrictions.ge("extendTableData", dateBegin));
					}
					if(end!=null&& !end.equals("")){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						String  dateEnd = end.toString();							
						c.add(Restrictions.le("extendTableData", dateEnd));
					}
				}else{ //����׼ȷ����
					String  date = propertyValue.toString();
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					c.add(Restrictions.eq("extendTableData", date));
				}
				List<ExtData> list= c.list();
				for(ExtData et:list){
					tableIds.add(et.getMainTableRowID());
				}
				return tableIds;
			}else if(colTypeName.equals("select")||colTypeName.equals("yesNoSelect")||colTypeName.equals("sexSelect")
					||colTypeName.equals("extSelect")){
				String matchModeStr = sysTableQueryColumn.getMatchModeStr();
				Criteria c=this.createCriteria(ExtData.class);
				if(matchModeStr!=null){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					c.add(Restrictions.eq("extendTableData", propertyValue.toString()));
				}
				List<ExtData> list= c.list();
				for(ExtData et:list){
					tableIds.add(et.getMainTableRowID());
				}
				return tableIds;
			}else if(colTypeName.equals("checkbox")||colTypeName.equals("checkboxGroup")||colTypeName.equals("multiSelect")){
				String matchModeStr = sysTableQueryColumn.getMatchModeStr();
				Criteria c=this.createCriteria(ExtData.class);
				List<ExtData> extCheckBoxs=null;
				if(matchModeStr!=null){
					c.add(Restrictions.isNotNull("extendTableData"));
					extCheckBoxs=c.list();
					
				}
				if(extCheckBoxs!=null){
					for(ExtData extCheckBox:extCheckBoxs){
						BeanWrapper bw = new BeanWrapperImpl(extCheckBox);
						String columnValue=(String) bw.getPropertyValue("extendTableData");
						String[] pvs=propertyValue.toString().split(",");
						boolean flag=true;
						for(String pv:pvs){
							int i=columnValue.indexOf(pv);
							if(i<0){
								flag= false;
							}
						}
						if(flag==true){
							tableIds.add(extCheckBox.getMainTableRowID());
						}
					}
				}
				return tableIds;
			}else if(colTypeName.equals("radio")||colTypeName.equals("yesNoRadio")){
				String matchModeStr = sysTableQueryColumn.getMatchModeStr();
				Criteria c=this.createCriteria(ExtData.class);
				if(matchModeStr!=null){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					c.add(Restrictions.eq("extendTableData", propertyValue.toString()));
				}
				List<ExtData> list= c.list();

				for(ExtData et:list){
					tableIds.add(et.getMainTableRowID());
				}
				return tableIds;	
			}
		return tableIds;
}
*/	
	public final Page end(Criteria criteria, Map params, int pageNo, int pageSize){
		Page page =new Page();
/*		
		Set keySet = params.keySet();
		Iterator keyItera = keySet.iterator();
		while(keyItera.hasNext()){
			Object keyObj = keyItera.next();
			if(keyObj instanceof SystemTableQueryColumn){
				SystemTableQueryColumn sysTableQueryColumn = (SystemTableQueryColumn) keyObj;
				if(sysTableQueryColumn.isExtendColumn()&&sysTableQueryColumn.getExtendTableColumn()!=null){
//					ScrollableResults srs=null;
//					srs=criteria.scroll();
//					while(srs.next()){
//					//���������в�ѯ�����Ľ��ÿ����¼������һ��,ͨ��ÿ����¼������id������չ����ж��Ƿ���ϲ�ѯ����
//					//��������Ч�ʵ������ڲ�ѯ�Ĵ���Ϊ���������n*��չ���������m
//						Object[] object = srs.get();
//						BeanWrapper bw = new BeanWrapperImpl(object[0]);
//						Object foreignValue = bw.getPropertyValue("id");
//						Long tableId=Long.parseLong(foreignValue.toString());
//						boolean flag=this.findExt(params,sysTableQueryColumn, tableId);
//						if(flag==false){
//							criteria.add(Restrictions.ne("id", tableId));
//						}
//					}
					//ͨ��������չ��Ĳ�ѯ����,����չ������,�ѷ�����������չ���Ӧ�������id����Restrictions.in��
					//��Ϊ��ѯ����ĸ�������,ÿ����չ�ֶ����ѯһ��,����������չ��������������������,�ܵĲ�Ѷ������
					//��չ�ֶεĸ���+1(�������������),��������˵������߲�ѯЧ��
					List tableIds=this.findExt1(params,sysTableQueryColumn);
					if(tableIds!=null&&tableIds.size()>0){
					criteria.add(Restrictions.in("id", tableIds));
					}else{
						return page;
					}
				}
			}
		} 
		*/
		page = this.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}

	public final Page endByParams(Class clazz,Criteria criteria, Map params, int pageNo, int pageSize){
		Page page =new Page();
		String className=clazz.getName();
		Criteria critea = createCriteria(SystemMainTable.class);
		critea.add(Restrictions.eq("className", className));
		SystemMainTable systemMainTable=(SystemMainTable) critea.uniqueResult();	
		Criteria c1 = createCriteria(SystemTableQueryColumn.class);
		c1.add(Restrictions.eq("systemMainTable", systemMainTable));
		//c1.add(Restrictions.isNotNull("extendTableColumn"));
		List<SystemTableQueryColumn> stqcList=c1.list();
		for(SystemTableQueryColumn stqc:stqcList){
			if(stqc.getMainTableColumn().getIsExtColumn()==1){
				if(params.get(stqc.getMainTableColumn().getPropertyName())!=null){
					List tableIds=this.findExt(params,stqc);
					if(tableIds!=null&&tableIds.size()>0){
						criteria.add(Restrictions.in("id", tableIds));
						}else{
							return page;
						}
				}
			}	
		}
		page = this.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	
	private List findExt(Map params,SystemTableQueryColumn sysTableQueryColumn){
	    Integer matchMode = sysTableQueryColumn.getMatchModeAsInt();
		SystemMainTableColumn smtc = sysTableQueryColumn.getMainTableColumn();
		SystemMainTableColumnType colType = smtc.getSystemMainTableColumnType();
		String colTypeName=colType.getColumnTypeName();
		Long extColuId=smtc.getId();
		//Integer extTableColumnNum=smtec.getExtendTableColumnNum();
		String propertyName = smtc.getPropertyName();
		Object propertyValue = params.get(propertyName);	
		List tableIds=new ArrayList();
		if(colTypeName.equals("text")||colTypeName.equals("textArea")){
			Criteria c=this.createCriteria(ExtData.class);
			c.add(Restrictions.eq("extendTableId",Integer.parseInt(extColuId.toString())));
			if(matchMode==null){
			//c.add(Restrictions.eq("mainTableRowID", tableId));
			c.add(Restrictions.eq("extendTableData", propertyValue));
			}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
				if(propertyValue!=null){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.ANYWHERE));	
				}
				
			}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
				if(propertyValue!=null){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.EXACT));	
				}
			}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
				if(propertyValue!=null){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.START));	
				}
			}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
				if(propertyValue!=null){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					c.add(Restrictions.like("extendTableData", propertyValue.toString(),MatchMode.END));	
				}
			}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_BETWEEN)){ 
				Object begin = params.get(propertyName+"Begin");
				Object end = params.get(propertyName+"End");
				ValidateType validateType = smtc.getValidateType();
				String validateTypeName = validateType.getValidateTypeName();
				if(begin!=null&& !begin.equals("")){
					if(validateTypeName.equalsIgnoreCase("Currency")
							||validateTypeName.equalsIgnoreCase("Double")
							||validateTypeName.equalsIgnoreCase("Number") ){
						begin = new Double(begin.toString());
					}else if(validateTypeName.equalsIgnoreCase("Integer")){
						begin = new Integer(begin.toString());
					}
					if(begin!=null){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						c.add(Restrictions.ge("extendTableData", begin));	
					}
				}
				if(end!=null && !end.equals("")){
					if(validateTypeName.equalsIgnoreCase("Currency")
							||validateTypeName.equalsIgnoreCase("Double")
							||validateTypeName.equalsIgnoreCase("Number") ){
						end = new Double(end.toString());
					}else if(validateTypeName.equalsIgnoreCase("Integer")){
						end = new Integer(end.toString());
					}if(end!=null){
						//c.add(Restrictions.eq("mainTableRowID", tableId));
						c.add(Restrictions.le("extendTableData", end));	
					}
				}
			}
			List<ExtData> list= c.list();
			for(ExtData et:list){
				tableIds.add(Long.parseLong(et.getMainTableRowID().toString()));
			}
			return tableIds;
		}else if(colTypeName.equals("dateText")){
			String matchModeStr = sysTableQueryColumn.getMatchModeStr();
			Criteria c=this.createCriteria(ExtData.class);
			c.add(Restrictions.eq("extendTableId",Integer.parseInt(extColuId.toString())));
			//������������
			if(matchModeStr!=null&& matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
				Object begin = params.get(propertyName+"Begin");
				Object end = params.get(propertyName+"End");
				if(begin!=null&& !begin.equals("")){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					String dateBegin = begin.toString();
					c.add(Restrictions.ge("extendTableData", dateBegin));
				}
				if(end!=null&& !end.equals("")){
					//c.add(Restrictions.eq("mainTableRowID", tableId));
					String  dateEnd = end.toString();							
					c.add(Restrictions.le("extendTableData", dateEnd));
				}
			}else{ //����׼ȷ����
				String  date = propertyValue.toString();
				//c.add(Restrictions.eq("mainTableRowID", tableId));
				c.add(Restrictions.eq("extendTableData", date));
			}
			List<ExtData> list= c.list();
			for(ExtData et:list){
				tableIds.add(Long.parseLong(et.getMainTableRowID().toString()));
			}
			return tableIds;
		}else if(colTypeName.equals("select")||colTypeName.equals("yesNoSelect")
				||colTypeName.equals("extSelect")||colTypeName.equals("sexSelect")){
			String matchModeStr = sysTableQueryColumn.getMatchModeStr();
			Criteria c=this.createCriteria(ExtData.class);
			c.add(Restrictions.eq("extendTableId",Integer.parseInt(extColuId.toString())));
			if(matchModeStr!=null){
				//c.add(Restrictions.eq("mainTableRowID", tableId));
				c.add(Restrictions.eq("extendTableData", propertyValue.toString()));
			}
			List<ExtData> list= c.list();
			for(ExtData et:list){
				tableIds.add(Long.parseLong(et.getMainTableRowID().toString()));
			}
			return tableIds;
		}else if(colTypeName.equals("checkbox")||colTypeName.equals("checkboxGroup")
				||colTypeName.equals("multiSelect")){
			String matchModeStr = sysTableQueryColumn.getMatchModeStr();
			Criteria c=this.createCriteria(ExtData.class);
			c.add(Restrictions.eq("extendTableId",Integer.parseInt(extColuId.toString())));
			List<ExtData> extCheckBoxs=null;
			if(matchModeStr!=null){
				c.add(Restrictions.isNotNull("extendTableData"));
				extCheckBoxs=c.list();
				
			}
			if(extCheckBoxs!=null){
				for(ExtData extCheckBox:extCheckBoxs){
					BeanWrapper bw = new BeanWrapperImpl(extCheckBox);
					String columnValue=(String) bw.getPropertyValue("extendTableData");
					String[] pvs=propertyValue.toString().split(",");
					boolean flag=true;
					for(String pv:pvs){
						int i=columnValue.indexOf(pv);
						if(i<0){
							flag= false;
						}
					}
					if(flag==true){
						tableIds.add(Long.parseLong(extCheckBox.getMainTableRowID().toString()));
					}
				}
			}
			return tableIds;
		}else if(colTypeName.equals("radio")||colTypeName.equals("yesNoRadio")){
			String matchModeStr = sysTableQueryColumn.getMatchModeStr();
			Criteria c=this.createCriteria(ExtData.class);
			c.add(Restrictions.eq("extendTableId",Integer.parseInt(extColuId.toString())));
			if(matchModeStr!=null){
				//c.add(Restrictions.eq("mainTableRowID", tableId));
				c.add(Restrictions.eq("extendTableData", propertyValue.toString()));
			}
			List<ExtData> list= c.list();

			for(ExtData et:list){
				tableIds.add(Long.parseLong(et.getMainTableRowID().toString()));
			}
			return tableIds;	
		}
	return tableIds;
}

	public DepartmentService getDeptService() {
		return deptService;
	}

	public void setDeptService(DepartmentService deptService) {
		this.deptService = deptService;
	}

}
