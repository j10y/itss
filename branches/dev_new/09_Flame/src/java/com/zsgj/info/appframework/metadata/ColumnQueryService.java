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
 * 字段查询服务,实现基于定制的查询服务接口。
 * 考虑业务变化，存在很多特殊情况，可能框架无法覆盖此情况，
 * 此时可以单独实现ColumnQueryService，覆盖相应的方法，完成
 * 定制框架之外的特殊查询条件设置。
 * 
 * 之所以引入查询服务接口是为了使用spring的注入功能。其要求必须存在接口，才可以完成代理。
 * 
 * @Class Name ColumnQueryService
 * @Author peixf
 * @Create In 2008-5-30
 */
public abstract class ColumnQueryService extends BaseDao implements QueryService{
	private DepartmentService deptService;
	/**
	 * 对查询的特殊限制，由前端查询参数决定，子类覆盖此方法。
	 * @param criteria
	 * @param extParams
	 */
	public void middle(Criteria criteria, Map extParams) {
		
		this.middle(criteria);
	}

	/**
	 * 对查询的特殊限制，不由前端查询参数决定，子类覆盖此方法。
	 * 如解决方案有法人体的限制
	 * @param criteria
	 */
	public void middle(Criteria criteria) {}

	/**
	 * 使用参数[查询参数，分页参数，排序参数]，返回查询分页数据
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
		//调用子类的middle，注意此方法内部先调用无参的middle
		this.middle(criteria, extParams);
		//end
		return this.end(criteria,params, pageNo, pageSize);
	}
	
	public Page queryByParams(Class clazz, Map<String, Object> params, Map<String, Object> extParams, 
				int pageNo, int pageSize, String orderProp, boolean isAsc) {

		Criteria  criteria = this.beforeByParams(clazz, params, pageNo, pageSize, orderProp, isAsc);
		//调用子类的middle，注意此方法内部先调用无参的middle
		this.middle(criteria, extParams);
		//end
		return this.endByParams(clazz, criteria, params, pageNo, pageSize);
		//return this.end(criteria, pageNo, pageSize);
	}
	
	public Page queryByParamsForUser(Class clazz, Map<String, Object> queryParams,
			Object object, int pageNo, int pageSize, String orderProp,
			boolean isAsc, String propertyName){
		Criteria  criteria = this.beforeByParams(clazz, queryParams, pageNo, pageSize, orderProp, isAsc);

		//过滤用户角色查看数据
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
			if(role.getDataViewFlag().equals(Role.VIEW_FLAG_ALL)){//是否可查看所有数据权限
				Department roleDept = role.getDepartment();
				if(roleDept.getParentDepartment()==null){
					return null;	//如果角色部门为最大部门，则返回null
				}else{
					Department dept = userInfo.getDepartment();
					List depts = deptService.findDeptByParentCode(dept.getDepartCode().toString());//获取部门的所有子部门
					depts.add(dept);
					Criteria criteria = super.getCriteria(UserInfo.class);
					criteria.add(Restrictions.in("department", depts));
					List deptUsers = criteria.list();
					list.addAll(deptUsers);//将部门内所有人加入查看用户范围
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
		//此出使用2个分页参数其实无意义，只是不想多定义一个beforeByParams方法
		Criteria  criteria = this.beforeByParams(clazz, params, 0, 0, orderProp, isAsc);
		//调用子类的middle，注意此方法内部先调用无参的middle
		this.middle(criteria, extParams);
		return this.end(criteria);//为返回list重载的方法
	}

	/**
	 * 传入类，查询参数，分页参数，排序参数，返回查询Criteria
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
			if(keyObj instanceof java.lang.String){ //如果key为字符串类型，则为搜索区间的条件
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
						//指定关联对象的查询条件
		
						//取关联对象, new codes
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
					else if(colType.getColumnTypeName().equalsIgnoreCase("dateText")){ //日期控件
						String matchModeStr = sysTableQueryColumn.getMatchModeStr();
						//日期区间搜索
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
						}else{ //日期准确搜索
							columnValue = DateUtil.convertStringToDate(propertyValue.toString());
							critea.add(Restrictions.eq("this."+propertyName, columnValue));
						}	
					}
				}
				
			}/*else if(sysTableQueryColumn.isExtendColumn()){//记录有哪些扩展字段的查询
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
			System.out.print("类名：" + className + "不正确！");
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
		
		//获取一个实体的默认的查询条件，如自动过滤status=-1的记录，即不显示已删除的
		/*
		 * 这里我们使用SystemTableColumnCondition过滤了不应出现的数据，但对于应该正确显示的历史数据我们也同时过滤了，
		 * 因此增加判断，如果传入参数为id的话不再使用SystemTableColumnCondition过滤数据
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
						critea.add(disjunction); //未判定null情况
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
					throw new ServiceException("请选择字段类型");
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
										throw new ServiceException("string类型字段，单个查询参数值不可以使用区域查询");
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
				}else{//区域查询
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
						
						//新修改，为测试
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
				
			}else{//扩展字段
				
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
	 * 返回分页结果
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
/* 一期已作废	
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
				//日期区间搜索
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
				}else{ //日期准确搜索
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
//					//把在主表中查询出来的结果每条记录都遍历一遍,通过每条记录的主表id来查扩展表的判断是否符合查询条件
//					//这样做的效率低是由于查询的次数为主表的条数n*扩展表的条件数m
//						Object[] object = srs.get();
//						BeanWrapper bw = new BeanWrapperImpl(object[0]);
//						Object foreignValue = bw.getPropertyValue("id");
//						Long tableId=Long.parseLong(foreignValue.toString());
//						boolean flag=this.findExt(params,sysTableQueryColumn, tableId);
//						if(flag==false){
//							criteria.add(Restrictions.ne("id", tableId));
//						}
//					}
					//通过传入扩展表的查询条件,和扩展表类型,把符合条件的扩展表对应的主表的id放如Restrictions.in中
					//做为查询主表的附加条件,每个扩展字段需查询一次,最后把所有扩展表的条件和起来查出主表,总的查讯次数是
					//扩展字段的个数+1(最后条件查主表),理论上来说可以提高查询效率
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
			//日期区间搜索
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
			}else{ //日期准确搜索
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
