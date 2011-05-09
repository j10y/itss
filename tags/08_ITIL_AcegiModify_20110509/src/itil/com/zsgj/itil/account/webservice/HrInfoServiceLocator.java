/**
 * HrInfoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.zsgj.itil.account.webservice;

import com.zsgj.info.framework.util.PropertiesUtil;

public class HrInfoServiceLocator extends org.apache.axis.client.Service implements com.zsgj.itil.account.webservice.HrInfoService {
	private java.lang.String HrInfoServiceSoap_address; //= "http://10.1.180.81/HrInfoUpdatePhone/HrInfoService.asmx";
    public HrInfoServiceLocator() {
    	HrInfoServiceSoap_address = PropertiesUtil.getProperties("soap.hrinfo.address","http://10.1.180.81/HrInfoUpdatePhone/HrInfoService.asmx");
    }
    public HrInfoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }
    
    public HrInfoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HrInfoServiceSoap
    

    public java.lang.String getHrInfoServiceSoapAddress() {
        return HrInfoServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HrInfoServiceSoapWSDDServiceName = "HrInfoServiceSoap";

    public java.lang.String getHrInfoServiceSoapWSDDServiceName() {
        return HrInfoServiceSoapWSDDServiceName;
    }

    public void setHrInfoServiceSoapWSDDServiceName(java.lang.String name) {
        HrInfoServiceSoapWSDDServiceName = name;
    }

    public com.zsgj.itil.account.webservice.HrInfoServiceSoap_PortType getHrInfoServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HrInfoServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHrInfoServiceSoap(endpoint);
    }

    public com.zsgj.itil.account.webservice.HrInfoServiceSoap_PortType getHrInfoServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.zsgj.itil.account.webservice.HrInfoServiceSoap_BindingStub _stub = new com.zsgj.itil.account.webservice.HrInfoServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getHrInfoServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHrInfoServiceSoapEndpointAddress(java.lang.String address) {
        HrInfoServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.zsgj.itil.account.webservice.HrInfoServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.zsgj.itil.account.webservice.HrInfoServiceSoap_BindingStub _stub = new com.zsgj.itil.account.webservice.HrInfoServiceSoap_BindingStub(new java.net.URL(HrInfoServiceSoap_address), this);
                _stub.setPortName(getHrInfoServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("HrInfoServiceSoap".equals(inputPortName)) {
            return getHrInfoServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "HrInfoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "HrInfoServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HrInfoServiceSoap".equals(portName)) {
            setHrInfoServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
