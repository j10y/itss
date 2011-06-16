
package net.shopin.ldap.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.shopin.ldap.ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetDeptListResponse_QNAME = new QName("http://service.ws.ldap.shopin.net/", "getDeptListResponse");
    private final static QName _GetUserDetailByUid_QNAME = new QName("http://service.ws.ldap.shopin.net/", "getUserDetailByUid");
    private final static QName _UpdateUser_QNAME = new QName("http://service.ws.ldap.shopin.net/", "updateUser");
    private final static QName _GetUserDetailByUidResponse_QNAME = new QName("http://service.ws.ldap.shopin.net/", "getUserDetailByUidResponse");
    private final static QName _GetDeptList_QNAME = new QName("http://service.ws.ldap.shopin.net/", "getDeptList");
    private final static QName _FindUserListByParam_QNAME = new QName("http://service.ws.ldap.shopin.net/", "findUserListByParam");
    private final static QName _UpdateUserResponse_QNAME = new QName("http://service.ws.ldap.shopin.net/", "updateUserResponse");
    private final static QName _FindUserListResponse_QNAME = new QName("http://service.ws.ldap.shopin.net/", "findUserListResponse");
    private final static QName _FindUserList_QNAME = new QName("http://service.ws.ldap.shopin.net/", "findUserList");
    private final static QName _FindUserListByParamResponse_QNAME = new QName("http://service.ws.ldap.shopin.net/", "findUserListByParamResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.shopin.ldap.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Department }
     * 
     */
    public Department createDepartment() {
        return new Department();
    }

    /**
     * Create an instance of {@link FindUserListResponse }
     * 
     */
    public FindUserListResponse createFindUserListResponse() {
        return new FindUserListResponse();
    }

    /**
     * Create an instance of {@link GetDeptList }
     * 
     */
    public GetDeptList createGetDeptList() {
        return new GetDeptList();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link UpdateUser }
     * 
     */
    public UpdateUser createUpdateUser() {
        return new UpdateUser();
    }

    /**
     * Create an instance of {@link GetUserDetailByUidResponse }
     * 
     */
    public GetUserDetailByUidResponse createGetUserDetailByUidResponse() {
        return new GetUserDetailByUidResponse();
    }

    /**
     * Create an instance of {@link FindUserListByParamResponse }
     * 
     */
    public FindUserListByParamResponse createFindUserListByParamResponse() {
        return new FindUserListByParamResponse();
    }

    /**
     * Create an instance of {@link UpdateUserResponse }
     * 
     */
    public UpdateUserResponse createUpdateUserResponse() {
        return new UpdateUserResponse();
    }

    /**
     * Create an instance of {@link FindUserListByParam }
     * 
     */
    public FindUserListByParam createFindUserListByParam() {
        return new FindUserListByParam();
    }

    /**
     * Create an instance of {@link FindUserList }
     * 
     */
    public FindUserList createFindUserList() {
        return new FindUserList();
    }

    /**
     * Create an instance of {@link GetUserDetailByUid }
     * 
     */
    public GetUserDetailByUid createGetUserDetailByUid() {
        return new GetUserDetailByUid();
    }

    /**
     * Create an instance of {@link GetDeptListResponse }
     * 
     */
    public GetDeptListResponse createGetDeptListResponse() {
        return new GetDeptListResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeptListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getDeptListResponse")
    public JAXBElement<GetDeptListResponse> createGetDeptListResponse(GetDeptListResponse value) {
        return new JAXBElement<GetDeptListResponse>(_GetDeptListResponse_QNAME, GetDeptListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserDetailByUid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getUserDetailByUid")
    public JAXBElement<GetUserDetailByUid> createGetUserDetailByUid(GetUserDetailByUid value) {
        return new JAXBElement<GetUserDetailByUid>(_GetUserDetailByUid_QNAME, GetUserDetailByUid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "updateUser")
    public JAXBElement<UpdateUser> createUpdateUser(UpdateUser value) {
        return new JAXBElement<UpdateUser>(_UpdateUser_QNAME, UpdateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserDetailByUidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getUserDetailByUidResponse")
    public JAXBElement<GetUserDetailByUidResponse> createGetUserDetailByUidResponse(GetUserDetailByUidResponse value) {
        return new JAXBElement<GetUserDetailByUidResponse>(_GetUserDetailByUidResponse_QNAME, GetUserDetailByUidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeptList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getDeptList")
    public JAXBElement<GetDeptList> createGetDeptList(GetDeptList value) {
        return new JAXBElement<GetDeptList>(_GetDeptList_QNAME, GetDeptList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserListByParam }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByParam")
    public JAXBElement<FindUserListByParam> createFindUserListByParam(FindUserListByParam value) {
        return new JAXBElement<FindUserListByParam>(_FindUserListByParam_QNAME, FindUserListByParam.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "updateUserResponse")
    public JAXBElement<UpdateUserResponse> createUpdateUserResponse(UpdateUserResponse value) {
        return new JAXBElement<UpdateUserResponse>(_UpdateUserResponse_QNAME, UpdateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListResponse")
    public JAXBElement<FindUserListResponse> createFindUserListResponse(FindUserListResponse value) {
        return new JAXBElement<FindUserListResponse>(_FindUserListResponse_QNAME, FindUserListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserList")
    public JAXBElement<FindUserList> createFindUserList(FindUserList value) {
        return new JAXBElement<FindUserList>(_FindUserList_QNAME, FindUserList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserListByParamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByParamResponse")
    public JAXBElement<FindUserListByParamResponse> createFindUserListByParamResponse(FindUserListByParamResponse value) {
        return new JAXBElement<FindUserListByParamResponse>(_FindUserListByParamResponse_QNAME, FindUserListByParamResponse.class, null, value);
    }

}
