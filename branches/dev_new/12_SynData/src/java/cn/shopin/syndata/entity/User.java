//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.31 at 02:10:38 PM CST 
//


package cn.shopin.syndata.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for User complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="User">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="realname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="belongdept" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="belongName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="passWord" type="{http://syndata.shopin.cn}passWord"/>
 *         &lt;element name="deleteFlag" type="{http://syndata.shopin.cn}deleteFlag"/>
 *         &lt;element name="customizeSQL" type="{http://syndata.shopin.cn}customizeSQL" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isSyn" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="tablename" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", propOrder = {
    "uid",
    "email",
    "realname",
    "belongdept",
    "belongName",
    "phone",
    "mobile",
    "fax",
    "position",
    "passWord",
    "deleteFlag",
    "customizeSQL"
})
public class User {

    @XmlElement(required = true)
    protected String uid;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String realname;
    @XmlElement(required = true)
    protected String belongdept;
    @XmlElement(required = true)
    protected String belongName;
    @XmlElement(required = true)
    protected String phone;
    @XmlElement(required = true)
    protected String mobile;
    @XmlElement(required = true)
    protected String fax;
    @XmlElement(required = true)
    protected String position;
    @XmlElement(required = true)
    protected PassWord passWord;
    @XmlElement(required = true)
    protected DeleteFlag deleteFlag;
    protected CustomizeSQL customizeSQL;
    @XmlAttribute(name = "isSyn")
    protected Boolean isSyn;
    @XmlAttribute(name = "tablename")
    protected String tablename;

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the realname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealname() {
        return realname;
    }

    /**
     * Sets the value of the realname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealname(String value) {
        this.realname = value;
    }

    /**
     * Gets the value of the belongdept property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBelongdept() {
        return belongdept;
    }

    /**
     * Sets the value of the belongdept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBelongdept(String value) {
        this.belongdept = value;
    }

    /**
     * Gets the value of the belongName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBelongName() {
        return belongName;
    }

    /**
     * Sets the value of the belongName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBelongName(String value) {
        this.belongName = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the mobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the passWord property.
     * 
     * @return
     *     possible object is
     *     {@link PassWord }
     *     
     */
    public PassWord getPassWord() {
        return passWord;
    }

    /**
     * Sets the value of the passWord property.
     * 
     * @param value
     *     allowed object is
     *     {@link PassWord }
     *     
     */
    public void setPassWord(PassWord value) {
        this.passWord = value;
    }

    /**
     * Gets the value of the deleteFlag property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteFlag }
     *     
     */
    public DeleteFlag getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the value of the deleteFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteFlag }
     *     
     */
    public void setDeleteFlag(DeleteFlag value) {
        this.deleteFlag = value;
    }

    /**
     * Gets the value of the customizeSQL property.
     * 
     * @return
     *     possible object is
     *     {@link CustomizeSQL }
     *     
     */
    public CustomizeSQL getCustomizeSQL() {
        return customizeSQL;
    }

    /**
     * Sets the value of the customizeSQL property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomizeSQL }
     *     
     */
    public void setCustomizeSQL(CustomizeSQL value) {
        this.customizeSQL = value;
    }

    /**
     * Gets the value of the isSyn property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsSyn() {
        if (isSyn == null) {
            return false;
        } else {
            return isSyn;
        }
    }

    /**
     * Sets the value of the isSyn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSyn(Boolean value) {
        this.isSyn = value;
    }

    /**
     * Gets the value of the tablename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * Sets the value of the tablename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTablename(String value) {
        this.tablename = value;
    }

}
