package net.shopin.ldap.ws.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for system complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="system">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="systemOccupant" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "system", propOrder = { "cn", "description", "displayName",
		"dn", "o", "status", "systemOccupant" })
public class System {

	protected String cn;
	protected String description;
	protected String displayName;
	protected String dn;
	protected String o;
	protected Integer status;
	@XmlElement(nillable = true)
	protected List<String> systemOccupant;

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
	 * Gets the value of the systemOccupant property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the systemOccupant property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getSystemOccupant().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getSystemOccupant() {
		if (systemOccupant == null) {
			systemOccupant = new ArrayList<String>();
		}
		return this.systemOccupant;
	}

}
