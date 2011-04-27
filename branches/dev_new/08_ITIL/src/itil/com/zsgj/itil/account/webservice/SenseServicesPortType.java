
package com.zsgj.itil.account.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "SenseServicesPortType", targetNamespace = "http://itss.sense.com")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SenseServicesPortType {

	@WebMethod(operationName = "deleteTempPerson", action = "")
	@WebResult(name = "out", targetNamespace = "http://itss.sense.com")
	public String deleteTempPerson(
			@WebParam(name = "in0", targetNamespace = "http://itss.sense.com")
			String in0);

	@WebMethod(operationName = "employeeInfo", action = "")
	@WebResult(name = "out", targetNamespace = "http://itss.sense.com")
	public String employeeInfo(
			@WebParam(name = "in0", targetNamespace = "http://itss.sense.com")
			String in0);

	@WebMethod(operationName = "addTempPerson", action = "")
	@WebResult(name = "out", targetNamespace = "http://itss.sense.com")
	public String addTempPerson(
			@WebParam(name = "in0", targetNamespace = "http://itss.sense.com")
			String in0);

	@WebMethod(operationName = "deleteWWWAccount", action = "")
	@WebResult(name = "out", targetNamespace = "http://itss.sense.com")
	public String deleteWWWAccount(
			@WebParam(name = "in0", targetNamespace = "http://itss.sense.com")
			String in0);

	@WebMethod(operationName = "addWWWAccount", action = "")
	@WebResult(name = "out", targetNamespace = "http://itss.sense.com")
	public String addWWWAccount(
			@WebParam(name = "in0", targetNamespace = "http://itss.sense.com")
			String in0);

	@WebMethod(operationName = "modifyTempPersonITCodeManager", action = "")
	@WebResult(name = "out", targetNamespace = "http://itss.sense.com")
	public String modifyTempPersonITCodeManager(
			@WebParam(name = "in0", targetNamespace = "http://itss.sense.com")
			String in0,
			@WebParam(name = "in1", targetNamespace = "http://itss.sense.com")
			String in1);

}
