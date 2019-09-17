package com.cardif.sunsystems.mapeo.currencyDailyRates;

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
 *                   &lt;element name="DailyConversionRate" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ConversionRate" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                             &lt;element name="CurrencyCodeFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CurrencyCodeTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
   *         &lt;element name="DailyConversionRate" maxOccurs="unbounded" minOccurs="0">
   *           &lt;complexType>
   *             &lt;complexContent>
   *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *                 &lt;sequence>
   *                   &lt;element name="ConversionRate" type="{http://www.w3.org/2001/XMLSchema}float"/>
   *                   &lt;element name="CurrencyCodeFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="CurrencyCodeTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
   *                   &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
  @XmlType(name = "", propOrder = { "dailyConversionRate" })
  public static class Payload {

    @XmlElement(name = "DailyConversionRate")
    protected List<SSC.Payload.DailyConversionRate> dailyConversionRate;

    /**
     * Gets the value of the dailyConversionRate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be
     * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the dailyConversionRate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDailyConversionRate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SSC.Payload.DailyConversionRate }
     * 
     * 
     */
    public List<SSC.Payload.DailyConversionRate> getDailyConversionRate() {
      if (dailyConversionRate == null) {
        dailyConversionRate = new ArrayList<SSC.Payload.DailyConversionRate>();
      }
      return this.dailyConversionRate;
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
     *         &lt;element name="ConversionRate" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *         &lt;element name="CurrencyCodeFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CurrencyCodeTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "conversionRate", "currencyCodeFrom", "currencyCodeTo", "dateTimeLastUpdated", "effectiveDate" })
    public static class DailyConversionRate {

      @XmlElement(name = "ConversionRate")
      protected float conversionRate;
      @XmlElement(name = "CurrencyCodeFrom", required = true)
      protected String currencyCodeFrom;
      @XmlElement(name = "CurrencyCodeTo", required = true)
      protected String currencyCodeTo;
      @XmlElement(name = "DateTimeLastUpdated", required = true)
      protected String dateTimeLastUpdated;
      @XmlElement(name = "EffectiveDate", required = true)
      protected String effectiveDate;

      /**
       * Gets the value of the conversionRate property.
       * 
       */
      public float getConversionRate() {
        return conversionRate;
      }

      /**
       * Sets the value of the conversionRate property.
       * 
       */
      public void setConversionRate(float value) {
        this.conversionRate = value;
      }

      /**
       * Gets the value of the currencyCodeFrom property.
       * 
       * @return possible object is {@link String }
       * 
       */
      public String getCurrencyCodeFrom() {
        return currencyCodeFrom;
      }

      /**
       * Sets the value of the currencyCodeFrom property.
       * 
       * @param value
       *          allowed object is {@link String }
       * 
       */
      public void setCurrencyCodeFrom(String value) {
        this.currencyCodeFrom = value;
      }

      /**
       * Gets the value of the currencyCodeTo property.
       * 
       * @return possible object is {@link String }
       * 
       */
      public String getCurrencyCodeTo() {
        return currencyCodeTo;
      }

      /**
       * Sets the value of the currencyCodeTo property.
       * 
       * @param value
       *          allowed object is {@link String }
       * 
       */
      public void setCurrencyCodeTo(String value) {
        this.currencyCodeTo = value;
      }

      /**
       * Gets the value of the dateTimeLastUpdated property.
       * 
       * @return possible object is {@link String }
       * 
       */
      public String getDateTimeLastUpdated() {
        return dateTimeLastUpdated;
      }

      /**
       * Sets the value of the dateTimeLastUpdated property.
       * 
       * @param value
       *          allowed object is {@link String }
       * 
       */
      public void setDateTimeLastUpdated(String value) {
        this.dateTimeLastUpdated = value;
      }

      /**
       * Gets the value of the effectiveDate property.
       * 
       * @return possible object is {@link String }
       * 
       */
      public String getEffectiveDate() {
        return effectiveDate;
      }

      /**
       * Sets the value of the effectiveDate property.
       * 
       * @param value
       *          allowed object is {@link String }
       * 
       */
      public void setEffectiveDate(String value) {
        this.effectiveDate = value;
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
