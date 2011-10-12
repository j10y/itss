
package net.shopin.ldap.ws.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "SystemWSImpl", targetNamespace = "http://service.ws.ldap.shopin.net/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SystemWSImpl {


    /**
     * 
     * @param arg0
     * @return
     *     returns net.shopin.ldap.ws.client.User
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserDetailByUid", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.GetUserDetailByUid")
    @ResponseWrapper(localName = "getUserDetailByUidResponse", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.GetUserDetailByUidResponse")
    public User getUserDetailByUid(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<net.shopin.ldap.ws.client.User>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findUserListByParam", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.FindUserListByParam")
    @ResponseWrapper(localName = "findUserListByParamResponse", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.FindUserListByParamResponse")
    public List<User> findUserListByParam(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<net.shopin.ldap.ws.client.User>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findUserList", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.FindUserList")
    @ResponseWrapper(localName = "findUserListResponse", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.FindUserListResponse")
    public List<User> findUserList();

    /**
     * 
     * @return
     *     returns java.util.List<net.shopin.ldap.ws.client.Department>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDeptList", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.GetDeptList")
    @ResponseWrapper(localName = "getDeptListResponse", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.GetDeptListResponse")
    public List<Department> getDeptList();

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "updateUser", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.UpdateUser")
    @ResponseWrapper(localName = "updateUserResponse", targetNamespace = "http://service.ws.ldap.shopin.net/", className = "net.shopin.ldap.ws.client.UpdateUserResponse")
    public void updateUser(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0);

}