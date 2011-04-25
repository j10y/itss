/**
 * HrInfoServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.digitalchina.itil.account.webservice;

public class HrInfoServiceTestCase extends junit.framework.TestCase {
    public HrInfoServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testHrInfoServiceSoapWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.digitalchina.itil.account.webservice.HrInfoServiceLocator().getHrInfoServiceSoapAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.digitalchina.itil.account.webservice.HrInfoServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1HrInfoServiceSoapUpdatePhone() throws Exception {
        com.digitalchina.itil.account.webservice.HrInfoServiceSoap_BindingStub binding;
        try {
            binding = (com.digitalchina.itil.account.webservice.HrInfoServiceSoap_BindingStub)
                          new com.digitalchina.itil.account.webservice.HrInfoServiceLocator().getHrInfoServiceSoap();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.updatePhone(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), false);
        // TBD - validate results
    }

}
