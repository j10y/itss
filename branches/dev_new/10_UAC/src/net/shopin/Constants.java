package net.shopin;


//import org.apache.log4j.helpers.ISO8601DateFormat;
import javax.servlet.ServletContext;
import javax.management.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.lang.management.ManagementFactory;

/**
 * 说明:系统级别的常量定义.
 * 将在系统中约定的键值,常用的字符串等放在这里,方便使用并且可以统一修改
 * 建立时间: 2005-3-1 10:40:35
 *
 * @author awen
 */
public class Constants implements ConstantsMBean {
    public static boolean DEBUG = false;
    public static String SESSION_USER_KEY = "USER_KEY";
    public static final String SESSION_USER_ID_KEY = "USER_ID_KEY";
    public static final String DEFAULT_CHARSET = "UTF-8";

    //public static final DateFormat DATE_ISO8601 = new ISO8601DateFormat();
    public static final String LOGOUT_URL = "/logout.jsp";
    public static final String CAS_LOGOUT_URL = "http://localhost:8080/cas3/logout";
    
    public static final String USER_PHOTO_UPLOADPATH = "D:/data/userphoto/";//用户图片上传路径
    public static final String USER_IMP_UPLOADPATH = "D:/data/upload/";//用户信息导入路径
    

    static {
        //得到平台默认MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        //MBeanServer mbs = MBeanServerFactory.createMBeanServer();

        //设置域名, 通过jconsole可以看到按层次划分的MBean
        Constants constants = new Constants();
        try {
            ObjectName objectName = new ObjectName("net.shopin"+":type=Constants"+",name=系统参数");
            mbs.registerMBean(constants, objectName);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        }
    }

    public boolean isDEBUG() {
        return DEBUG;
    }

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }
}
