/**
 * @Probject Name: 09_Flame_2
 * @Path: com.zsgj.info.framework.utilEntityBeanUtils.java
 * @Create By Jack
 * @Create In 2011-5-10 ����02:19:21
 * TODO
 */
package com.zsgj.info.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;


/**
 * ��չApache Commons BeanUtils, �ṩһЩ���䷽��ȱʧ���ܵķ�װ.
 * @Class Name EntityBeanUtils
 * @Author Jack
 * @Create In 2011-5-10
 */
public class EntityBeanUtils extends BeanUtils{
	protected static final Log logger = LogFactory.getLog(BeanUtils.class);

	private EntityBeanUtils() {
	}

	/**
	 * ѭ������ת��,��ȡ�����DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             ���û�и�Fieldʱ�׳�.
	 */
	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * ѭ������ת��,��ȡ�����DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             ���û�и�Fieldʱ�׳�.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field���ڵ�ǰ�ඨ��,��������ת��
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}

	/**
	 * ������ȡ�������ֵ,����private,protected���η�������.
	 * 
	 * @throws NoSuchFieldException
	 *             ���û�и�Fieldʱ�׳�.
	 */
	public static Object forceGetProperty(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * �������ö������ֵ,����private,protected���η�������.
	 * 
	 * @throws NoSuchFieldException
	 *             ���û�и�Fieldʱ�׳�.
	 */
	public static void forceSetProperty(Object object, String propertyName,
			Object newValue) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * �������ö�����,����private,protected���η�������.
	 * 
	 * @throws NoSuchMethodException
	 *             ���û�и�Methodʱ�׳�.
	 */
	public static Object invokePrivateMethod(Object object, String methodName,
			Object... params) throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				// �������ڵ�ǰ�ඨ��,��������ת��
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:"
					+ clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * ��Filed������ȡ��Field�б�.
	 */
	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * ��FiledName���Field������.
	 */
	public static Class getPropertyType(Class type, String name)
			throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * ���field��getter��������.
	 */
	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * ���field��getter����,����Ҳ����÷���,����null.
	 */
	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/***************************************************************************
	 * bean���ƺ��������Ӻ����б���<br>
	 * ��ע����֪Ϊ��spring��BeanUtils�޷�����lazyform
	 * 
	 * @param dest
	 * @param orig
	 * @param ignoreProperties
	 *            �����б�
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static void copyProperties(Object dest, Object orig,
			String[] ignoreProperties) throws IllegalAccessException,
			InvocationTargetException {
		Map m = new HashMap();
		for (String s : ignoreProperties) {
			try {
				m.put(s, getProperty(dest, s));
			} catch (Exception e) {
				// �����б��п����в����ֶβ���bean���ֶ�
			}
		}
		copyProperties(dest, orig);
		for (Entry e : (Set<Entry>) m.entrySet()) {
			setProperty(dest, e.getKey().toString(), e.getValue());
		}

	}

}