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
 * ExtJs 的Action基础类
 * @Class Name ExtTemplateAction
 * @Author 张鹏
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
	 * 取得本Aciotn管理的实体 可以通过反射取得
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
	 * 鍒犻櫎鏁版嵁
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map params = getRequestParameterMap(request);
		String entityClass = "";
		String ids = "";
		String entityId = "";
		try {
			// 涓?娆℃?у垹闄ゅ鏉¤褰?
			if (params.containsKey(StringPool.ENTITY_IDS)) {
				// 鍒ゆ柇id 鍙奺ntityClass 婊¤冻鎯呭喌
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
				// 濡傛灉婊¤冻浠ヤ笂鏉′欢锛屽垯鎵ц鍒犻櫎鍔ㄤ綔
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
				// 涓?娆″垹闄や竴鏉℃暟鎹?
			} else if (params.containsKey(StringPool.ENTITY_ID)) {
				// 鍒ゆ柇id,entityClass婊¤冻鎯呭喌
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
				// 濡傛灉婊¤冻浠ヤ笂鏉′欢锛屽垯鎵ц鍒犻櫎鍔ㄤ綔
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
				 * 如果没有从Request中取得Class类型，那么将要多子类中根据方法： protected Class
				 * getEntityClass()取得Class类型。
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
	 * 鏇存柊鏁版嵁
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
			// 鍒ゆ柇鏄惁婊¤冻瀵瑰儚鍔犺浇鏉′欢
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
			// 濡傛灉婊¤冻鍔犺浇瀵瑰儚鐨勬潯浠?
			if (null != clazz && entityId != null) {
				o = getEntity(clazz, entityId);
				// 灏嗗鍍忚浆鍖栦负json缁撴瀯
				json = this.object2Json(o);
			}
			// 褰撳鍍忓凡缁忔垚鍔熷姞杞斤紝骞跺凡灏嗗鍍忕殑鏁版嵁缁撴瀯杞寲涓簀son缁撴瀯
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
	 * 鍙栧緱鍒嗛〉浣跨敤鐨凧SON鏁版嵁
	 * 
	 * @param request
	 * @return
	 */
	protected String getPageJson(HttpServletRequest request) {
		return null;
	}

	protected String object2Json(Object o) {
		String json = null;
		// 灏嗗鍍忚浆鍖栦负json缁撴瀯
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
	 * 取得request parameters map
	 * 
	 * @param request
	 * @return
	 */
	protected Map getRequestParameterMap(HttpServletRequest request) {
		return CollectionUtil.getCollectionUtil().transRequestParametersMap(
				request.getParameterMap());
	}

	/**
	 * 根据KEY值，取得request parameter map 中的一个值
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
