package com.zsgj.dcit.util;


import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ˵��: ��WebApplicationContext��װ��һ��singleton����, ʹ��ԭ�д�����Է���ĵ���.
 * ��Ȼû��ʹ��IoCģʽ, ����ȴ��������޶ȵı���ԭ������Ľṹ.
 * ����ʱ��: 2005-4-2 3:39:34
 *
 * @author awen
 */
public class SpringContextUtils implements ApplicationContextAware{
    private static final Log logger = LogFactory.getLog(SpringContextUtils.class);

    public static ApplicationContext ctx;

    /** ����ģʽ��ʵ��.
     *  ��ȻapplicationContext�Ѿ��Ǿ�̬ȫ��Ψһ����, ��������singleton�ı�׼ʵ��,
     *  ����Ҫʵ��Map�ӿ�, �ﵽJSTL���õ�Ŀ��, Ҳ������ô��.
     */
    private static final SpringContextUtils instance = new SpringContextUtils();

    /** �õ�ȫ��Ψһ��ʵ��.
     * ����������ᱻspringͨ��factory-method�����Զ�����,
     * ���Ӷ�ʵ��spring�����singleton����ͨsingleton�Ĺ���.
     */
    public static SpringContextUtils instance() {
        return instance;
    }

    /** ��ֹ��ʼ�� **/
    private SpringContextUtils() {}

    /** ApplicationContextAware�Ľӿ�, ��spring���� **/
    public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException {
        ctx = _applicationContext;
    }

    /** �õ�spring��context���� **/
    public ApplicationContext getContext() {
        return ctx;
    }

    /** ��ݷ���, ���Եõ�spring�����bean **/
    public static Object getBean(String beanName) {
        if(ctx ==null) return null;
        return ctx.getBean(beanName);
    }
}
