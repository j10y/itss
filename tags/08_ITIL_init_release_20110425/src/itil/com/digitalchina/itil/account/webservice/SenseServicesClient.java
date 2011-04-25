
package com.digitalchina.itil.account.webservice;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.digitalchina.info.framework.util.PropertiesUtil;

public class SenseServicesClient {
    private SenseServicesPortType service;

    public SenseServicesClient() {

    	Service serviceModel = new ObjectServiceFactory().create(SenseServicesPortType.class);
		try {
			service = (SenseServicesPortType) new XFireProxyFactory()
					.create(serviceModel,PropertiesUtil.getProperties("soap.tivoli.address","http://im.digitalchina.com:9081/Sense_ITSS_WS/services/SenseServices"));
			XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
			Client client = proxy.getClient();
			// 添加header信息
			SenseServicesClientAuthHandler handler = new SenseServicesClientAuthHandler();
			handler.setUsername(PropertiesUtil.getProperties("soap.tivoli.user","uuipws4itss"));
			handler.setPassword(PropertiesUtil.getProperties("soap.tivoli.pwd","jaKvsdhjYq3hGdsy74KndTy"));
			// 发送授权信息
			client.addOutHandler(handler);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	public SenseServicesPortType getService() {
		return service;
	}


	public void setService(SenseServicesPortType service) {
		this.service = service;
	}

    public static void main(String[] args) {	
        SenseServicesClient client = new SenseServicesClient();
        SenseServicesPortType service = client.getService();
        System.out.println(service.employeeInfo("yexl"));
		System.out.println("test client completed");
        System.exit(0);
    }


}
