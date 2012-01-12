package net.shopin.ldap.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the net.shopin.ldap.ws.client package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _FindUserListByGroupCN_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findUserListByGroupCN");
	private final static QName _FindUserListByRoleCNResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/",
			"findUserListByRoleCNResponse");
	private final static QName _FindRolesByUserIdResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findRolesByUserIdResponse");
	private final static QName _FindUserListByParamResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findUserListByParamResponse");
	private final static QName _GetDeptListResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "getDeptListResponse");
	private final static QName _DeleteUserByUserId_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "deleteUserByUserId");
	private final static QName _FindUserListByRoleCN_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findUserListByRoleCN");
	private final static QName _UpdateUser_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "updateUser");
	private final static QName _GetUserDetailByUid_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "getUserDetailByUid");
	private final static QName _GetUserDetailByUidResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "getUserDetailByUidResponse");
	private final static QName _CreateUser_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "createUser");
	private final static QName _UpdateUserResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "updateUserResponse");
	private final static QName _FindRolesByUserId_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findRolesByUserId");
	private final static QName _FindRoleListBySystemCN_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findRoleListBySystemCN");
	private final static QName _FindRoleListBySystemCNResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/",
			"findRoleListBySystemCNResponse");
	private final static QName _CreateUserResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "createUserResponse");
	private final static QName _FindGroupsByUserId_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findGroupsByUserId");
	private final static QName _DeleteUserByUserIdResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "deleteUserByUserIdResponse");
	private final static QName _GetDeptList_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "getDeptList");
	private final static QName _FindUserListByGroupCNResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/",
			"findUserListByGroupCNResponse");
	private final static QName _FindGroupsByUserIdResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findGroupsByUserIdResponse");
	private final static QName _FindUserListResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findUserListResponse");
	private final static QName _FindUserList_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findUserList");
	private final static QName _FindSystemsByRoleCNResponse_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findSystemsByRoleCNResponse");
	private final static QName _FindUserListByParam_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findUserListByParam");
	private final static QName _FindSystemsByRoleCN_QNAME = new QName(
			"http://service.ws.ldap.shopin.net/", "findSystemsByRoleCN");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: net.shopin.ldap.ws.client
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Role }
	 * 
	 */
	public Role createRole() {
		return new Role();
	}

	/**
	 * Create an instance of {@link GetDeptList }
	 * 
	 */
	public GetDeptList createGetDeptList() {
		return new GetDeptList();
	}

	/**
	 * Create an instance of {@link GetUserDetailByUidResponse }
	 * 
	 */
	public GetUserDetailByUidResponse createGetUserDetailByUidResponse() {
		return new GetUserDetailByUidResponse();
	}

	/**
	 * Create an instance of {@link FindRoleListBySystemCN }
	 * 
	 */
	public FindRoleListBySystemCN createFindRoleListBySystemCN() {
		return new FindRoleListBySystemCN();
	}

	/**
	 * Create an instance of {@link FindUserListByRoleCN }
	 * 
	 */
	public FindUserListByRoleCN createFindUserListByRoleCN() {
		return new FindUserListByRoleCN();
	}

	/**
	 * Create an instance of {@link DeleteUserByUserId }
	 * 
	 */
	public DeleteUserByUserId createDeleteUserByUserId() {
		return new DeleteUserByUserId();
	}

	/**
	 * Create an instance of {@link FindSystemsByRoleCNResponse }
	 * 
	 */
	public FindSystemsByRoleCNResponse createFindSystemsByRoleCNResponse() {
		return new FindSystemsByRoleCNResponse();
	}

	/**
	 * Create an instance of {@link FindRoleListBySystemCNResponse }
	 * 
	 */
	public FindRoleListBySystemCNResponse createFindRoleListBySystemCNResponse() {
		return new FindRoleListBySystemCNResponse();
	}

	/**
	 * Create an instance of {@link CreateUserResponse }
	 * 
	 */
	public CreateUserResponse createCreateUserResponse() {
		return new CreateUserResponse();
	}

	/**
	 * Create an instance of {@link System }
	 * 
	 */
	public System createSystem() {
		return new System();
	}

	/**
	 * Create an instance of {@link FindGroupsByUserIdResponse }
	 * 
	 */
	public FindGroupsByUserIdResponse createFindGroupsByUserIdResponse() {
		return new FindGroupsByUserIdResponse();
	}

	/**
	 * Create an instance of {@link GetDeptListResponse }
	 * 
	 */
	public GetDeptListResponse createGetDeptListResponse() {
		return new GetDeptListResponse();
	}

	/**
	 * Create an instance of {@link UserGroup }
	 * 
	 */
	public UserGroup createUserGroup() {
		return new UserGroup();
	}

	/**
	 * Create an instance of {@link UpdateUserResponse }
	 * 
	 */
	public UpdateUserResponse createUpdateUserResponse() {
		return new UpdateUserResponse();
	}

	/**
	 * Create an instance of {@link FindGroupsByUserId }
	 * 
	 */
	public FindGroupsByUserId createFindGroupsByUserId() {
		return new FindGroupsByUserId();
	}

	/**
	 * Create an instance of {@link FindUserListByParamResponse }
	 * 
	 */
	public FindUserListByParamResponse createFindUserListByParamResponse() {
		return new FindUserListByParamResponse();
	}

	/**
	 * Create an instance of {@link FindUserListResponse }
	 * 
	 */
	public FindUserListResponse createFindUserListResponse() {
		return new FindUserListResponse();
	}

	/**
	 * Create an instance of {@link CreateUser }
	 * 
	 */
	public CreateUser createCreateUser() {
		return new CreateUser();
	}

	/**
	 * Create an instance of {@link FindUserListByRoleCNResponse }
	 * 
	 */
	public FindUserListByRoleCNResponse createFindUserListByRoleCNResponse() {
		return new FindUserListByRoleCNResponse();
	}

	/**
	 * Create an instance of {@link FindUserListByGroupCN }
	 * 
	 */
	public FindUserListByGroupCN createFindUserListByGroupCN() {
		return new FindUserListByGroupCN();
	}

	/**
	 * Create an instance of {@link GetUserDetailByUid }
	 * 
	 */
	public GetUserDetailByUid createGetUserDetailByUid() {
		return new GetUserDetailByUid();
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
	 * Create an instance of {@link FindUserListByGroupCNResponse }
	 * 
	 */
	public FindUserListByGroupCNResponse createFindUserListByGroupCNResponse() {
		return new FindUserListByGroupCNResponse();
	}

	/**
	 * Create an instance of {@link DeleteUserByUserIdResponse }
	 * 
	 */
	public DeleteUserByUserIdResponse createDeleteUserByUserIdResponse() {
		return new DeleteUserByUserIdResponse();
	}

	/**
	 * Create an instance of {@link FindRolesByUserIdResponse }
	 * 
	 */
	public FindRolesByUserIdResponse createFindRolesByUserIdResponse() {
		return new FindRolesByUserIdResponse();
	}

	/**
	 * Create an instance of {@link User }
	 * 
	 */
	public User createUser() {
		return new User();
	}

	/**
	 * Create an instance of {@link FindRolesByUserId }
	 * 
	 */
	public FindRolesByUserId createFindRolesByUserId() {
		return new FindRolesByUserId();
	}

	/**
	 * Create an instance of {@link Department }
	 * 
	 */
	public Department createDepartment() {
		return new Department();
	}

	/**
	 * Create an instance of {@link UpdateUser }
	 * 
	 */
	public UpdateUser createUpdateUser() {
		return new UpdateUser();
	}

	/**
	 * Create an instance of {@link FindSystemsByRoleCN }
	 * 
	 */
	public FindSystemsByRoleCN createFindSystemsByRoleCN() {
		return new FindSystemsByRoleCN();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserListByGroupCN }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByGroupCN")
	public JAXBElement<FindUserListByGroupCN> createFindUserListByGroupCN(
			FindUserListByGroupCN value) {
		return new JAXBElement<FindUserListByGroupCN>(
				_FindUserListByGroupCN_QNAME, FindUserListByGroupCN.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserListByRoleCNResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByRoleCNResponse")
	public JAXBElement<FindUserListByRoleCNResponse> createFindUserListByRoleCNResponse(
			FindUserListByRoleCNResponse value) {
		return new JAXBElement<FindUserListByRoleCNResponse>(
				_FindUserListByRoleCNResponse_QNAME,
				FindUserListByRoleCNResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindRolesByUserIdResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findRolesByUserIdResponse")
	public JAXBElement<FindRolesByUserIdResponse> createFindRolesByUserIdResponse(
			FindRolesByUserIdResponse value) {
		return new JAXBElement<FindRolesByUserIdResponse>(
				_FindRolesByUserIdResponse_QNAME,
				FindRolesByUserIdResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserListByParamResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByParamResponse")
	public JAXBElement<FindUserListByParamResponse> createFindUserListByParamResponse(
			FindUserListByParamResponse value) {
		return new JAXBElement<FindUserListByParamResponse>(
				_FindUserListByParamResponse_QNAME,
				FindUserListByParamResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link GetDeptListResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getDeptListResponse")
	public JAXBElement<GetDeptListResponse> createGetDeptListResponse(
			GetDeptListResponse value) {
		return new JAXBElement<GetDeptListResponse>(_GetDeptListResponse_QNAME,
				GetDeptListResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link DeleteUserByUserId }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "deleteUserByUserId")
	public JAXBElement<DeleteUserByUserId> createDeleteUserByUserId(
			DeleteUserByUserId value) {
		return new JAXBElement<DeleteUserByUserId>(_DeleteUserByUserId_QNAME,
				DeleteUserByUserId.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserListByRoleCN }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByRoleCN")
	public JAXBElement<FindUserListByRoleCN> createFindUserListByRoleCN(
			FindUserListByRoleCN value) {
		return new JAXBElement<FindUserListByRoleCN>(
				_FindUserListByRoleCN_QNAME, FindUserListByRoleCN.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUser }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "updateUser")
	public JAXBElement<UpdateUser> createUpdateUser(UpdateUser value) {
		return new JAXBElement<UpdateUser>(_UpdateUser_QNAME, UpdateUser.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link GetUserDetailByUid }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getUserDetailByUid")
	public JAXBElement<GetUserDetailByUid> createGetUserDetailByUid(
			GetUserDetailByUid value) {
		return new JAXBElement<GetUserDetailByUid>(_GetUserDetailByUid_QNAME,
				GetUserDetailByUid.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link GetUserDetailByUidResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getUserDetailByUidResponse")
	public JAXBElement<GetUserDetailByUidResponse> createGetUserDetailByUidResponse(
			GetUserDetailByUidResponse value) {
		return new JAXBElement<GetUserDetailByUidResponse>(
				_GetUserDetailByUidResponse_QNAME,
				GetUserDetailByUidResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "createUser")
	public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
		return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link UpdateUserResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "updateUserResponse")
	public JAXBElement<UpdateUserResponse> createUpdateUserResponse(
			UpdateUserResponse value) {
		return new JAXBElement<UpdateUserResponse>(_UpdateUserResponse_QNAME,
				UpdateUserResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindRolesByUserId }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findRolesByUserId")
	public JAXBElement<FindRolesByUserId> createFindRolesByUserId(
			FindRolesByUserId value) {
		return new JAXBElement<FindRolesByUserId>(_FindRolesByUserId_QNAME,
				FindRolesByUserId.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindRoleListBySystemCN }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findRoleListBySystemCN")
	public JAXBElement<FindRoleListBySystemCN> createFindRoleListBySystemCN(
			FindRoleListBySystemCN value) {
		return new JAXBElement<FindRoleListBySystemCN>(
				_FindRoleListBySystemCN_QNAME, FindRoleListBySystemCN.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindRoleListBySystemCNResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findRoleListBySystemCNResponse")
	public JAXBElement<FindRoleListBySystemCNResponse> createFindRoleListBySystemCNResponse(
			FindRoleListBySystemCNResponse value) {
		return new JAXBElement<FindRoleListBySystemCNResponse>(
				_FindRoleListBySystemCNResponse_QNAME,
				FindRoleListBySystemCNResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link CreateUserResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "createUserResponse")
	public JAXBElement<CreateUserResponse> createCreateUserResponse(
			CreateUserResponse value) {
		return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME,
				CreateUserResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindGroupsByUserId }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findGroupsByUserId")
	public JAXBElement<FindGroupsByUserId> createFindGroupsByUserId(
			FindGroupsByUserId value) {
		return new JAXBElement<FindGroupsByUserId>(_FindGroupsByUserId_QNAME,
				FindGroupsByUserId.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link DeleteUserByUserIdResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "deleteUserByUserIdResponse")
	public JAXBElement<DeleteUserByUserIdResponse> createDeleteUserByUserIdResponse(
			DeleteUserByUserIdResponse value) {
		return new JAXBElement<DeleteUserByUserIdResponse>(
				_DeleteUserByUserIdResponse_QNAME,
				DeleteUserByUserIdResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link GetDeptList }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "getDeptList")
	public JAXBElement<GetDeptList> createGetDeptList(GetDeptList value) {
		return new JAXBElement<GetDeptList>(_GetDeptList_QNAME,
				GetDeptList.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserListByGroupCNResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByGroupCNResponse")
	public JAXBElement<FindUserListByGroupCNResponse> createFindUserListByGroupCNResponse(
			FindUserListByGroupCNResponse value) {
		return new JAXBElement<FindUserListByGroupCNResponse>(
				_FindUserListByGroupCNResponse_QNAME,
				FindUserListByGroupCNResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindGroupsByUserIdResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findGroupsByUserIdResponse")
	public JAXBElement<FindGroupsByUserIdResponse> createFindGroupsByUserIdResponse(
			FindGroupsByUserIdResponse value) {
		return new JAXBElement<FindGroupsByUserIdResponse>(
				_FindGroupsByUserIdResponse_QNAME,
				FindGroupsByUserIdResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserListResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListResponse")
	public JAXBElement<FindUserListResponse> createFindUserListResponse(
			FindUserListResponse value) {
		return new JAXBElement<FindUserListResponse>(
				_FindUserListResponse_QNAME, FindUserListResponse.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link FindUserList }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserList")
	public JAXBElement<FindUserList> createFindUserList(FindUserList value) {
		return new JAXBElement<FindUserList>(_FindUserList_QNAME,
				FindUserList.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindSystemsByRoleCNResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findSystemsByRoleCNResponse")
	public JAXBElement<FindSystemsByRoleCNResponse> createFindSystemsByRoleCNResponse(
			FindSystemsByRoleCNResponse value) {
		return new JAXBElement<FindSystemsByRoleCNResponse>(
				_FindSystemsByRoleCNResponse_QNAME,
				FindSystemsByRoleCNResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindUserListByParam }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findUserListByParam")
	public JAXBElement<FindUserListByParam> createFindUserListByParam(
			FindUserListByParam value) {
		return new JAXBElement<FindUserListByParam>(_FindUserListByParam_QNAME,
				FindUserListByParam.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link FindSystemsByRoleCN }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.ws.ldap.shopin.net/", name = "findSystemsByRoleCN")
	public JAXBElement<FindSystemsByRoleCN> createFindSystemsByRoleCN(
			FindSystemsByRoleCN value) {
		return new JAXBElement<FindSystemsByRoleCN>(_FindSystemsByRoleCN_QNAME,
				FindSystemsByRoleCN.class, null, value);
	}

}
