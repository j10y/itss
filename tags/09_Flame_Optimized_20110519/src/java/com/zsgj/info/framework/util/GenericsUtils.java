/**
 * @Probject Name: 09_Flame_2
 * @Path: com.zsgj.info.framework.utilGenericsUtils.java
 * @Create By Jack
 * @Create In 2011-5-10 下午02:07:14
 * TODO
 */
package com.zsgj.info.framework.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 泛型参数辅助
 * @Class Name GenericsUtils
 * @Author Jack
 * @Create In 2011-5-10
 */
public class GenericsUtils {
	private static final Log log = LogFactory.getLog(GenericsUtils.class);

	private GenericsUtils() {
	}

	/**
	 *  通过反射，获得定义Class时声明的父类的第个范型参数的类型
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	 /** 
     * 通过反射，获得定义Class时声明的父类的范型参数的类型
     * 如没有找到符合要求的范型参数，则递归向上直到
     *
     * @param clazz 要进行查询的类
     * @param index 如有多个范型声明该索引从第0个开始
     * @return 在index位置的范型参数的类型，如果无法判断则返回<code>Object.class</code>
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
