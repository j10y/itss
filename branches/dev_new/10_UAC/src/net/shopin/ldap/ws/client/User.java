package net.shopin.ldap.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for user complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="user">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="belongDeptDN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="belongTitleDN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departmentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayOrder" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="employeeNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="employeeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="facsimileTelephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="givenName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="photo" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="sn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="telephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = { "belongDeptDN", "belongTitleDN", "cn",
		"departmentNumber", "deptName", "description", "displayName",
		"displayOrder", "dn", "employeeNumber", "employeeType",
		"facsimileTelephoneNumber", "givenName", "mail", "mobile", "o",
		"password", "photo", "sn", "status", "telephoneNumber", "title", "uid",
		"userType" })
public class User {

	protected String belongDeptDN;
	protected String belongTitleDN;
	protected String cn;
	protected String departmentNumber;
	protected String deptName;
	protected String description;
	protected String displayName;
	protected Integer displayOrder;
	protected String dn;
	protected String employeeNumber;
	protected String employeeType;
	protected String facsimileTelephoneNumber;
	protected String givenName;
	protected String mail;
	protected String mobile;
	protected String o;
	protected String password;
	protected byte[] photo;
	protected String sn;
	protected Integer status;
	protected String telephoneNumber;
	protected String title;
	protected String uid;
	protected String userType;

	/**
	 * Gets the value of the belongDeptDN property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBelongDeptDN() {
		return belongDeptDN;
	}

	/**
	 * Sets the value of the belongDeptDN property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBelongDeptDN(String value) {
		this.belongDeptDN = value;
	}

	/**
	 * Gets the value of the belongTitleDN property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBelongTitleDN() {
		return belongTitleDN;
	}

	/**
	 * Sets the value of the belongTitleDN property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBelongTitleDN(String value) {
		this.belongTitleDN = value;
	}

	/**
	 * Gets the value of the cn property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * Sets the value of the cn property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCn(String value) {
		this.cn = value;
	}

	/**
	 * Gets the value of the departmentNumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDepartmentNumber() {
		return departmentNumber;
	}

	/**
	 * Sets the value of the departmentNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDepartmentNumber(String value) {
		this.departmentNumber = value;
	}

	/**
	 * Gets the value of the deptName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the value of the deptName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeptName(String value) {
		this.deptName = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the displayName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the value of the displayName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDisplayName(String value) {
		this.displayName = value;
	}

	/**
	 * Gets the value of the displayOrder property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * Sets the value of the displayOrder property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setDisplayOrder(Integer value) {
		this.displayOrder = value;
	}

	/**
	 * Gets the value of the dn property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDn() {
		return dn;
	}

	/**
	 * Sets the value of the dn property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDn(String value) {
		this.dn = value;
	}

	/**
	 * Gets the value of the employeeNumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * Sets the value of the employeeNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEmployeeNumber(String value) {
		this.employeeNumber = value;
	}

	/**
	 * Gets the value of the employeeType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * Sets the value of the employeeType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEmployeeType(String value) {
		this.employeeType = value;
	}

	/**
	 * Gets the value of the facsimileTelephoneNumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFacsimileTelephoneNumber() {
		return facsimileTelephoneNumber;
	}

	/**
	 * Sets the value of the facsimileTelephoneNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFacsimileTelephoneNumber(String value) {
		this.facsimileTelephoneNumber = value;
	}

	/**
	 * Gets the value of the givenName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * Sets the value of the givenName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setGivenName(String value) {
		this.givenName = value;
	}

	/**
	 * Gets the value of the mail property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the value of the mail property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMail(String value) {
		this.mail = value;
	}

	/**
	 * Gets the value of the mobile property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the value of the mobile property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMobile(String value) {
		this.mobile = value;
	}

	/**
	 * Gets the value of the o property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getO() {
		return o;
	}

	/**
	 * Sets the value of the o property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setO(String value) {
		this.o = value;
	}

	/**
	 * Gets the value of the password property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the password property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPassword(String value) {
		this.password = value;
	}

	/**
	 * Gets the value of the photo property.
	 * 
	 * @return possible object is byte[]
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/**
	 * Sets the value of the photo property.
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setPhoto(byte[] value) {
		this.photo = ((byte[]) value);
	}

	/**
	 * Gets the value of the sn property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * Sets the value of the sn property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSn(String value) {
		this.sn = value;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setStatus(Integer value) {
		this.status = value;
	}

	/**
	 * Gets the value of the telephoneNumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * Sets the value of the telephoneNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTelephoneNumber(String value) {
		this.telephoneNumber = value;
	}

	/**
	 * Gets the value of the title property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the title property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTitle(String value) {
		this.title = value;
	}

	/**
	 * Gets the value of the uid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the value of the uid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUid(String value) {
		this.uid = value;
	}

	/**
	 * Gets the value of the userType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the value of the userType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserType(String value) {
		this.userType = value;
	}

}
