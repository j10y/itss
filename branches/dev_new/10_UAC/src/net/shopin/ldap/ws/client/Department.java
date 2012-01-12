package net.shopin.ldap.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for department complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="department">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deptDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deptNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayOrder" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="erpId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "department", propOrder = { "deptDesc", "deptName", "deptNo",
		"displayOrder", "dn", "erpId", "parentNo", "status" })
public class Department {

	protected String deptDesc;
	protected String deptName;
	protected String deptNo;
	protected Integer displayOrder;
	protected String dn;
	protected String erpId;
	protected String parentNo;
	protected Integer status;

	/**
	 * Gets the value of the deptDesc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeptDesc() {
		return deptDesc;
	}

	/**
	 * Sets the value of the deptDesc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeptDesc(String value) {
		this.deptDesc = value;
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
	 * Gets the value of the deptNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeptNo() {
		return deptNo;
	}

	/**
	 * Sets the value of the deptNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeptNo(String value) {
		this.deptNo = value;
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
	 * Gets the value of the erpId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getErpId() {
		return erpId;
	}

	/**
	 * Sets the value of the erpId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setErpId(String value) {
		this.erpId = value;
	}

	/**
	 * Gets the value of the parentNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParentNo() {
		return parentNo;
	}

	/**
	 * Sets the value of the parentNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParentNo(String value) {
		this.parentNo = value;
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

}
