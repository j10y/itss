/**
 * @Probject Name: 09_Flame_2
 * @Path: com.zsgj.info.framework.utilGenericsUtils.java
 * @Create By Jack
 * @Create In 2011-5-10 ����02:07:14
 * TODO
 */
package com.zsgj.info.framework.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * ���Ͳ�������
 * @Class Name GenericsUtils
 * @Author Jack
 * @Create In 2011-5-10
 */
public class GenericsUtils {
	private static final Log log = LogFactory.getLog(GenericsUtils.class);

	private GenericsUtils() {
	}

	/**
	 *  ͨ�����䣬��ö���Classʱ�����ĸ���ĵڸ����Ͳ���������
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	 /** 
     * ͨ�����䣬��ö���Classʱ�����ĸ���ķ��Ͳ���������
     * ��û���ҵ�����Ҫ��ķ��Ͳ�������ݹ�����ֱ��
     *
     * @param clazz Ҫ���в�ѯ����
     * @param index ���ж�����������������ӵ�0����ʼ
     * @return ��indexλ�õķ��Ͳ��������ͣ�����޷��ж��򷵻�<code>Object.class</code>
     */
    @SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}
}
