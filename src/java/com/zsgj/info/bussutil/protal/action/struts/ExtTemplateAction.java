package com.zsgj.info.bussutil.protal.action.struts;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;


import com.zsgj.info.bussutil.protal.util.Http;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.util.json.CollectionUtil;
import com.zsgj.info.framework.util.json.JSONUtil;
import com.zsgj.info.framework.util.json.StringPool;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * ExtJs ��Action������
 * @Class Name ExtTemplateAction
 * @Author ����
 * @Create In Oct 23, 2008
 */
public abstract class ExtTemplateAction extends BaseDispatchAction {

	protected final static String SAVE = StringPool.ENTITY_SAVE;
	protected final static String LIST = StringPool.ENTITY_LIST;
	protected final static String DETAIL = StringPool.ENTITY_DETAIL;

	protected Http getHttp() {
		return Http.getHttp();
	}
	
	protected abstract void onInitEntity(HttpServletRequest request,Object o,ActionForm form);
	
	/**
	 * ȡ�ñ�Aciotn�����ʵ�� ����ͨ������ȡ��
	 */
	protected Class clasz;

	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return this.clasz;
	}

	protected void setEntityClass(Class clasz) {
		this.clasz = clasz;
	}

	protected void setEntityClass(Object o) {
		this.clasz = o.getClass();
	}

	
	protected Object getEntity(String entityClassName, Serializable id) {
		Object o = null;
		Class classz = null;
		try {
			if (id != null && StringUtils.isNotEmpty(entityClassName)) {
				classz = Class.forName(entityClassName);
				o = getService().find(classz, id.toString());
			}
		} catch (Exception e) {
			log.error(e.toString());
		}
		return o;
	}
	
	protected Object getEntity(Class entityClass, Serializable id) {
		Object o = null;
		try {
			o = getService().find(entityClass, id.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		return o;
	}
	
	protected Object getEntity(Class entityClass, HttpServletRequest request) {
		Object o = null;
		Serializable id = this.getHttp().getEntityId(request);
		if (id != null) {
			o = getEntity(entityClass, id);
		}
		return o;
	}
	
	protected Object getEntity(HttpServletRequest request) {
		Object o = null;
		Map params = this.getRequestParameterMap(request);
		if (params.containsKey(StringPool.ENTITY_CLASS)
				&& params.containsKey(StringPool.ENTITY_ID)) {
			if (StringUtils.isNotEmpty(params.get(StringPool.ENTITY_CLASS)
					.toString())
					&& StringUtils.isNotEmpty(params.get(StringPool.ENTITY_ID)
							.toString())) {
				String entityClassName = params.get(StringPool.ENTITY_CLASS)
						.toString();
				Serializable id = params.get(StringPool.ENTITY_ID).toString();
				try {
					o = this.getEntity(entityClassName, id);
				} catch (LinkageError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return o;
	}
	
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map params = getRequestParameterMap(request);
		String json = "";
		try {
			Class clazz = Class.forName(params.get(StringPool.ENTITY_CLASS)
					.toString());
			Object o = getEntity(clazz, request);
			json = this.object2Json(o);
			this.jsonPrint(response, JSONUtil.rowJson(json));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String json = this.getPageJson(request);
		this.jsonPrint(response, json);
		return null;
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 删除数据
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map params = getRequestParameterMap(request);
		String entityClass = "";
		String ids = "";
		String entityId = "";
		try {
			// �?次�?�删除多条记�?
			if (params.containsKey(StringPool.ENTITY_IDS)) {
				// 判断id 及entityClass 满足情况
				ids = params.get(StringPool.ENTITY_IDS).toString();
				if (params.containsKey(StringPool.ENTITY_CLASS)) {
					if (StringUtils.isNotEmpty(params.get(
							StringPool.ENTITY_CLASS).toString())) {
						entityClass = params.get(StringPool.ENTITY_CLASS)
								.toString();
					} else {
						entityClass = this.getEntityClass().getName();
					}
				} else {
					entityClass = this.getEntityClass().getName();
				}
				// 如果满足以上条件，则执行删除动作
				if (StringUtils.isNotEmpty(ids)
						&& StringUtils.isNotEmpty(entityClass)) {
					String id[] = ids.split(StringPool.ENTITY_IDS_SPLIT_WITH);
					Class clazz = Class.forName(entityClass);
					if (id != null) {
						for (int i = 0; i < id.length; i++) {
							if (id[i] != null) {
								this.getService().remove(clazz, id[i]);
							}
						}
					}
					
				}
				// �?次删除一条数�?
			} else if (params.containsKey(StringPool.ENTITY_ID)) {
				// 判断id,entityClass满足情况
				if (params.containsKey(StringPool.ENTITY_CLASS)) {
					if (StringUtils.isNotEmpty(params.get(
							StringPool.ENTITY_CLASS).toString())) {
						entityClass = params.get(StringPool.ENTITY_CLASS)
								.toString();
					} else {
						entityClass = this.getEntityClass().getName();
					}
				} else {
					entityClass = this.getEntityClass().getName();
				}
				entityId = params.get(StringPool.ENTITY_ID).toString();
				// 如果满足以上条件，则执行删除动作
				if (StringUtils.isNotEmpty(entityId)
						&& StringUtils.isNotEmpty(entityClass)) {

					this.getService().remove(
									ClassUtils.forName(entityClass), entityId);

				}
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected Object initEntity(HttpServletRequest request, ActionForm form) {
		Object o = null;
		try {
			Map params = getRequestParameterMap(request);
			if (params.containsKey(StringPool.ENTITY_CLASS)
					&& StringUtils.isNotEmpty(params.get(
							StringPool.ENTITY_CLASS).toString())) {
				if (params.containsKey(StringPool.ENTITY_ID)
						&& StringUtils.isNotEmpty(params.get(
								StringPool.ENTITY_ID).toString())) {
					o = getEntity(request);
				} else {

					o = ClassUtils.forName(
							params.get(StringPool.ENTITY_CLASS).toString())
							.newInstance();

				}
				/*
				 * ���û�д�Request��ȡ��Class���ͣ���ô��Ҫ�������и��ݷ����� protected Class
				 * getEntityClass()ȡ��Class���͡�
				 * 
				 */
			} else if (this.getEntityClass() != null) {
				if (params.containsKey(StringPool.ENTITY_ID)
						&& StringUtils.isNotEmpty(params.get(
								StringPool.ENTITY_ID).toString())) {
					o = getEntity(this.getEntityClass(), params.get(
							StringPool.ENTITY_ID).toString());

				} else {

					o = ClassUtils.forName(this.getEntityClass().getName())
							.newInstance();

				}
			}
			if (o != null) {
				BeanUtils.copyProperties(o, params);
				this.onInitEntity(request, o, form);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LinkageError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			Map params = getRequestParameterMap(request);
			Object o = this.initEntity(request, form);
			this.getService().save(o);
			return null;
		} catch (Exception e) {
			log.info("faceye exception:" + e.toString() + " in method save()");
			return null;
		}
	}

	/**
	 * 更新数据
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map params = getRequestParameterMap(request);
		Class clazz = null;
		Serializable entityId = null;
		Object o = null;
		String json = "";
		try {
			// 判断是否满足对像加载条件
			if (params.containsKey(StringPool.ENTITY_CLASS)) {
				if (StringUtils.isNotEmpty(params.get(StringPool.ENTITY_CLASS)
						.toString())) {
					clazz = ClassUtils.forName(params.get(
							StringPool.ENTITY_CLASS).toString());
				}
			} else {
				if (this.getEntityClass() != null) {
					clazz = this.getEntityClass();
				}
			}
			if (params.containsKey(StringPool.ENTITY_ID)) {
				if (StringUtils.isNotEmpty(params.get(StringPool.ENTITY_ID)
						.toString())) {
					entityId = params.get(StringPool.ENTITY_ID).toString();
				}
			}
			// 如果满足加载对像的条�?
			if (null != clazz && entityId != null) {
				o = getEntity(clazz, entityId);
				// 将对像转化为json结构
				json = this.object2Json(o);
			}
			// 当对像已经成功加载，并已将对像的数据结构转化为json结构
			this.jsonPrint(response, JSONUtil.rowJson(json));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LinkageError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * JSON
	 * 
	 * @param response
	 * @param json
	 */
	protected void jsonPrint(HttpServletResponse response, String json) {
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		try {
			//System.out.println("JSON IS: " + json);
			if(json!=null){
				response.getWriter().write(json);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 取得分页使用的JSON数据
	 * 
	 * @param request
	 * @return
	 */
	protected String getPageJson(HttpServletRequest request) {
		return null;
	}

	protected String object2Json(Object o) {
		String json = null;
		// 将对像转化为json结构
		if (null != o) {
			Class clazz = o.getClass();
			if (ClassUtils.hasMethod(clazz, StringPool.REFLECTION_METHOD_MAP,
					null)) {
				json = JSONArray
						.fromObject(
								ReflectionUtils
										.invokeMethod(
												ClassUtils
														.getMethodIfAvailable(
																clazz,
																StringPool.REFLECTION_METHOD_MAP,
																null), o))
						.toString();
			} else if (ClassUtils.hasMethod(clazz,
					StringPool.REFLECTION_METHOD_JSON, null)) {
				json = "["
						+ ReflectionUtils
								.invokeMethod(
										ClassUtils
												.getMethodIfAvailable(
														clazz,
														StringPool.REFLECTION_METHOD_JSON,
														null), o).toString()
						+ "]";
			} else {
				json = JSONArray.fromObject(o).toString();
			}
		}
		return json;
	}
	

	

	/**
	 * ȡ��request parameters map
	 * 
	 * @param request
	 * @return
	 */
	protected Map getRequestParameterMap(HttpServletRequest request) {
		return CollectionUtil.getCollectionUtil().transRequestParametersMap(
				request.getParameterMap());
	}

	/**
	 * ����KEYֵ��ȡ��request parameter map �е�һ��ֵ
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	protected String get(Object key, HttpServletRequest request) {
		Map params = this.getRequestParameterMap(request);
		if (params.containsKey(key)) {
			if (null != params.get(key)) {
				return params.get(key).toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
