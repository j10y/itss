package net.shopin.util;


import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 说明: 将WebApplicationContext封装在一个singleton类中, 使得原有代码可以方便的调用.
 * 虽然没有使用IoC模式, 但是却可以最大限度的保留原来代码的结构.
 * 建立时间: 2005-4-2 3:39:34
 *
 * @author awen
 */
public class SpringContextUtils implements ApplicationContextAware{
    private static final Log logger = LogFactory.getLog(SpringContextUtils.class);

    public static ApplicationContext ctx;

    /** 单例模式的实现.
     *  虽然applicationContext已经是静态全局唯一的了, 但是这是singleton的标准实现,
     *  此外要实现Map接口, 达到JSTL调用的目的, 也必须这么做.
     */
    private static final SpringContextUtils instance = new SpringContextUtils();

    /** 得到全局唯一的实例.
     * 　这个方法会被spring通过factory-method设置自动调用,
     * 　从而实现spring管理的singleton和普通singleton的共用.
     */
    public static SpringContextUtils instance() {
        return instance;
    }

    /** 防止初始化 **/
    private SpringContextUtils() {}

    /** ApplicationContextAware的接口, 由spring调用 **/
    public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException {
        ctx = _applicationContext;
    }

    /** 得到spring的context对象 **/
    public ApplicationContext getContext() {
        return ctx;
    }

    /** 便捷方法, 可以得到spring管理的bean **/
    public static Object getBean(String beanName) {
        if(ctx ==null) return null;
        return ctx.getBean(beanName);
    }
}
