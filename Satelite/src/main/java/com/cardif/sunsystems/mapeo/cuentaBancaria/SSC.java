package com.cardif.sunsystems.mapeo.cuentaBancaria;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="User">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SunSystemsContext">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BusinessUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Payload">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Accounts" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="AccountType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "user", "sunSystemsContext", "payload" })
@XmlRootElement(name = "SSC")
public class SSC {

  @XmlElement(name = "User", required = true)
  protected SSC.User user;
  @XmlElement(name = "SunSystemsContext", required = true)
  protected SSC.SunSystemsContext sunSystemsContext;
  @XmlElement(name = "Payload", required = true)
  protected SSC.Payload payload;

  /**
   * Gets the value of the user property.
   * 
   * @return possible object is {@link SSC.User }
   * 
   */
  public SSC.User getUser() {
    return user;
  }

  /**
   * Sets the value of the user property.
   * 
   * @param value
   *          allowed object is {@link SSC.User }
   * 
   */
  public void setUser(SSC.User value) {
    this.user = value;
  }

  /**
   * Gets the value of the sunSystemsContext property.
   * 
   * @return possible object is {@link SSC.SunSystemsContext }
   * 
   */
  public SSC.SunSystemsContext getSunSystemsContext() {
    return sunSystemsContext;
  }

  /**
   * Sets the value of the sunSystemsContext property.
   * 
   * @param value
   *          allowed object is {@link SSC.SunSystemsContext }
   * 
   */
  public void setSunSystemsContext(SSC.SunSystemsContext value) {
    this.sunSystemsContext = value;
  }

  /**
   * Gets the value of the payload property.
   * 
   * @return possible object is {@link SSC.Payload }
   * 
   */
  public SSC.Payload getPayload() {
    return payload;
  }

  /**
   * Sets the value of the payload property.
   * 
   * @param value
   *          allowed object is {@link SSC.Payload }
   * 
   */
  public void setPayload(SSC.Payload value) {
    this.payload = value;
  }

  /**
   * <p>
   * Java class for anonymous complex type.
   * 
   * <p>
   * The following schema fragment specifies the expected content contained within this class.
   * 
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="Accounts" maxOccurs="unbounded" minOccurs="0">
   *           &lt;complexType>
   *             &lt;complexContent>
   *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *                 &lt;sequence>
   *                   &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
   *                   &lt;element name="AccountType" type="{http://www.w3.org/2001/XMLSchema}int"/>
   *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/>
   *                 &lt;/sequence>
   *               &lt;/restriction>
   *             &lt;/complexContent>
   *           &lt;/complexType>
   *         &lt;/element>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "accounts" })
  public static class Payload {

    @XmlElement(name = "Accounts")
    protected List<SSC.Payload.Accounts> accounts;

    /**
     * Gets the value of the accounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be
     * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the accounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAccounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SSC.Payload.Accounts }
     * 
     * 
     */
    public List<SSC.Payload.Accounts> getAccounts() {
      if (accounts == null) {
        accounts = new ArrayList<SSC.Payload.Accounts>();
      }
      return this.accounts;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="AccountType" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "accountCode", "accountType", "description", "status" })
    public static class Accounts {

      @XmlElement(name = "AccountCode")
      protected int accountCode;
      @XmlElement(name = "AccountType")
      protected int accountType;
      @XmlElement(name = "Description", required = true)
      protected String description;
      @XmlElement(name = "Status")
      protected int status;

      /**
       * Gets the value of the accountCode property.
       * 
       */
      public int getAccountCode() {
        return accountCode;
      }

      /**
       * Sets the value of the accountCode property.
       * 
       */
      public void setAccountCode(int value) {
        this.accountCode = value;
      }

      /**
       * Gets the value of the accountType property.
       * 
       */
      public int getAccountType() {
        return accountType;
      }

      /**
       * Sets the value of the accountType property.
       * 
       */
      public void setAccountType(int value) {
        this.accountType = value;
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
       *          allowed object is {@link String }
       * 
       */
      public void setDescription(String value) {
        this.description = value;
      }

      /**
       * Gets the value of the status property.
       * 
       */
      public int getStatus() {
        return status;
      }

      /**
       * Sets the value of the status property.
       * 
       */
      public void setStatus(int value) {
        this.status = value;
      }

    }

  }

  /**
   * <p>
   * Java class for anonymous complex type.
   * 
   * <p>
   * The following schema fragment specifies the expected content contained within this class.
   * 
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="BusinessUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "businessUnit" })
  public static class SunSystemsContext {

    @XmlElement(name = "BusinessUnit", required = true)
    protected String businessUnit;

    /**
     * Gets the value of the businessUnit property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBusinessUnit() {
      return businessUnit;
    }

    /**
     * Sets the value of the businessUnit property.
     * 
     * @param value
     *          allowed object is {@link String }
     * 
     */
    public void setBusinessUnit(String value) {
      this.businessUnit = value;
    }

  }

  /**
   * <p>
   * Java class for anonymous complex type.
   * 
   * <p>
   * The following schema fragment specifies the expected content contained within this class.
   * 
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "name" })
  public static class User {

    @XmlElement(name = "Name", required = true)
    protected String name;

    /**
     * Gets the value of the name property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getName() {
      return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *          allowed object is {@link String }
     * 
     */
    public void setName(String value) {
      this.name = value;
    }

  }

}
