package net.shopin.ldap.ws;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;

/**
 * 所有webservice需要实现此基类
 * @author wchao
 *
 */
@javax.jws.WebService
//@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class BaseWebService {
	
	/**
	 * 默认Spring的代理机制是得不到wsctx的，如有需要的话,<br>
	 * 可以编写一个切面通过反射填充wsctx
	 */
//	@Resource
//	protected WebServiceContext wsctx;
}
