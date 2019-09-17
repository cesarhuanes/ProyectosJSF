
package com.cardif.sunsystems.mapeo.supplier;

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
 *                   &lt;element name="Supplier" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Carrier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CompanyAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CreditCheckWarningLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DataAccessGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DaysToleranceOverride" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DefaultBankSubcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DefaultBuyer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DefaultBuyerRoleCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DefaultCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DefaultReceivingWarehouse" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DefaultSupplierReceivingLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DefaultSupplierReturnLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DirectDebit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EMailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EarliestLatestCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EarliestServiceLatestCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EarliestServiceWeightedAverageCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EarliestWeightedAverageCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ImminentSettlement" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="LinkAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="MaintainStatistics" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PayToAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PaymentMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PaymentTermsGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PrimaryOrderAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PurchaseTransactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="RemittanceSupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ShortHeading" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscDate1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscDate2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscDescription1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscDescription2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscNumber1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscNumber2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscReference1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierMiscReference2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SupplierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ToleranceDaysToApply" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="UpdateCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="UserIdLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="WebPageAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BankDetails" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="BankAccountName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="BankAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="BankAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="BankBranch" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="BankDetailsCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="BankSortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="BankSubcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="DataAccessGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="DirectDebitFirstPayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="OwnCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="SupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="TransactionLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="TransactionReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="UpdateCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="UserIdLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="SupplierAddress" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="AddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AddressLine1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AddressLine2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AddressLine3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AddressLine4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="AddressLine5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Area" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="FaxNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ShortHeading" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="StateCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="TelephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="TemporaryAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="TownCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="WebPageAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
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
public class SSC
{
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
	public static class User
	{
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
	public static class SunSystemsContext
	{
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
	 *         &lt;element name="Supplier" maxOccurs="unbounded" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="Carrier" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="CompanyAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="CreditCheckWarningLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DataAccessGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DaysToleranceOverride" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DefaultBankSubcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DefaultBuyer" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DefaultBuyerRoleCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DefaultCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DefaultReceivingWarehouse" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DefaultSupplierReceivingLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DefaultSupplierReturnLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="DirectDebit" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="EMailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="EarliestLatestCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="EarliestServiceLatestCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="EarliestServiceWeightedAverageCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="EarliestWeightedAverageCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="ImminentSettlement" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="LinkAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="MaintainStatistics" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="PayToAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="PaymentMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="PaymentTermsGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="PrimaryOrderAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="PurchaseTransactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="RemittanceSupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="ShortHeading" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscDate1" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscDate2" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscDescription1" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscDescription2" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscNumber1" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscNumber2" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscReference1" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierMiscReference2" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="SupplierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="ToleranceDaysToApply" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="UpdateCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="UserIdLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="WebPageAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="BankDetails" maxOccurs="unbounded" minOccurs="0">
	 *                     &lt;complexType>
	 *                       &lt;complexContent>
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                           &lt;sequence>
	 *							   &lt;element name="BankAccountName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="BankAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="BankAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="BankBranch" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="BankDetailsCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="BankSortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="BankSubcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="DataAccessGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="DirectDebitFirstPayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="OwnCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="SupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="TransactionLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="TransactionReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="UpdateCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="UserIdLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                           &lt;/sequence>
	 *                         &lt;/restriction>
	 *                       &lt;/complexContent>
	 *                     &lt;/complexType>
	 *                   &lt;/element>
	 *                   &lt;element name="SupplierAddress" maxOccurs="unbounded" minOccurs="0">
	 *                     &lt;complexType>
	 *                       &lt;complexContent>
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                           &lt;sequence>
	 *                             &lt;element name="AddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="AddressLine1" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="AddressLine2" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="AddressLine3" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="AddressLine4" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="AddressLine5" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="Area" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="FaxNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="ShortHeading" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="StateCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="TelephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="TemporaryAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="TownCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="WebPageAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
	@XmlType(name = "", propOrder = { "supplier" })
	public static class Payload
	{
		@XmlElement(name = "Supplier", required = true)
		protected List<SSC.Payload.Supplier> supplier;
		
		
		/**
		 * Gets the value of the supplier property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be
		 * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the supplier property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getSupplier().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link SSC.Payload.Supplier }
		 * 
		 * 
		 */
		public List<SSC.Payload.Supplier> getSupplier()
		{
			if (supplier == null) {
				supplier = new ArrayList<SSC.Payload.Supplier>();
			}
			return this.supplier;
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
		 *         &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="Carrier" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="CompanyAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="CreditCheckWarningLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DataAccessGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DaysToleranceOverride" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DefaultBankSubcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DefaultBuyer" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DefaultBuyerRoleCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DefaultCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DefaultReceivingWarehouse" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DefaultSupplierReceivingLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DefaultSupplierReturnLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="DirectDebit" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="EMailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="EarliestLatestCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="EarliestServiceLatestCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="EarliestServiceWeightedAverageCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="EarliestWeightedAverageCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="ImminentSettlement" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="LinkAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="MaintainStatistics" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="PayToAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="PaymentMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="PaymentTermsGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="PrimaryOrderAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="PurchaseTransactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="RemittanceSupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="ShortHeading" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscDate1" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscDate2" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscDescription1" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscDescription2" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscNumber1" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscNumber2" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscReference1" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierMiscReference2" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="SupplierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="ToleranceDaysToApply" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="UpdateCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="UserIdLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="WebPageAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
	     *         &lt;element name="BankDetails" maxOccurs="unbounded" minOccurs="0">
	     *           &lt;complexType>
	     *             &lt;complexContent>
	     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	     *                 &lt;sequence>
		 *					 &lt;element name="BankAccountName" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="BankAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="BankAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="BankBranch" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="BankDetailsCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="BankSortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="BankSubcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="DataAccessGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="DirectDebitFirstPayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="OwnCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="SupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="TransactionLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="TransactionReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="UpdateCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="UserIdLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
	     *                 &lt;/sequence>
	     *               &lt;/restriction>
	     *             &lt;/complexContent>
	     *           &lt;/complexType>
	     *         &lt;/element>
		 *         &lt;element name="SupplierAddress" maxOccurs="unbounded" minOccurs="0">
		 *           &lt;complexType>
		 *             &lt;complexContent>
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *                 &lt;sequence>
		 *                   &lt;element name="AddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="AddressLine1" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="AddressLine2" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="AddressLine3" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="AddressLine4" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="AddressLine5" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="Area" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="FaxNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="ShortHeading" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="StateCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="TelephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="TemporaryAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="TownCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="WebPageAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
	    @XmlType(name = "", propOrder = { 
	    	"accountCode",
	        "carrier",
	        "comment",
	        "companyAddressCode",
	        "creditCheckWarningLimit",
	        "dataAccessGroupCode",
	        "dateTimeLastUpdated",
	        "daysToleranceOverride",
	        "defaultBankSubcode",
	        "defaultBuyer",
	        "defaultBuyerRoleCode",
	        "defaultCurrencyCode",
	        "defaultReceivingWarehouse",
	        "defaultSupplierReceivingLocation",
	        "defaultSupplierReturnLocation",
	        "description",
	        "directDebit",
	        "eMailAddress",
	        "earliestLatestCost",
	        "earliestServiceLatestCost",
	        "earliestServiceWeightedAverageCost",
	        "earliestWeightedAverageCost",
	        "imminentSettlement",
	        "linkAddressCode",
	        "lookupCode",
	        "maintainStatistics",
	        "payToAddressCode",
	        "paymentMethod",
	        "paymentTermsGroupCode",
	        "primaryOrderAddressCode",
	        "priority",
	        "purchaseTransactionType",
	        "remittanceSupplierCode",
	        "shortHeading",
	        "status",
	        "supplierCode",
	        "supplierMiscDate1",
	        "supplierMiscDate2",
	        "supplierMiscDescription1",
	        "supplierMiscDescription2",
	        "supplierMiscNumber1",
	        "supplierMiscNumber2",
	        "supplierMiscReference1",
	        "supplierMiscReference2",
	        "supplierName",
	        "toleranceDaysToApply",
	        "updateCount",
	        "userIdLastUpdated",
	        "webPageAddress",
	        "bankDetails",
	        "supplierAddress"
	    })
	    public static class Supplier
	    {
	    	@XmlElement(name = "AccountCode", required = true)
            protected String accountCode;
            @XmlElement(name = "Carrier", required = true)
            protected String carrier;
            @XmlElement(name = "Comment", required = true)
            protected String comment;
            @XmlElement(name = "CompanyAddressCode", required = true)
            protected String companyAddressCode;
            @XmlElement(name = "CreditCheckWarningLimit", required = true)
            protected String creditCheckWarningLimit;
            @XmlElement(name = "DataAccessGroupCode", required = true)
            protected String dataAccessGroupCode;
            @XmlElement(name = "DateTimeLastUpdated", required = true)
            protected String dateTimeLastUpdated;
            @XmlElement(name = "DaysToleranceOverride", required = true)
            protected String daysToleranceOverride;
            @XmlElement(name = "DefaultBankSubcode", required = true)
            protected String defaultBankSubcode;
            @XmlElement(name = "DefaultBuyer", required = true)
            protected String defaultBuyer;
            @XmlElement(name = "DefaultBuyerRoleCode", required = true)
            protected String defaultBuyerRoleCode;
            @XmlElement(name = "DefaultCurrencyCode", required = true)
            protected String defaultCurrencyCode;
            @XmlElement(name = "DefaultReceivingWarehouse", required = true)
            protected String defaultReceivingWarehouse;
            @XmlElement(name = "DefaultSupplierReceivingLocation", required = true)
            protected String defaultSupplierReceivingLocation;
            @XmlElement(name = "DefaultSupplierReturnLocation", required = true)
            protected String defaultSupplierReturnLocation;
            @XmlElement(name = "Description", required = true)
            protected String description;
            @XmlElement(name = "DirectDebit", required = true)
            protected String directDebit;
            @XmlElement(name = "EMailAddress", required = true)
            protected String eMailAddress;
            @XmlElement(name = "EarliestLatestCost", required = true)
            protected String earliestLatestCost;
            @XmlElement(name = "EarliestServiceLatestCost", required = true)
            protected String earliestServiceLatestCost;
            @XmlElement(name = "EarliestServiceWeightedAverageCost", required = true)
            protected String earliestServiceWeightedAverageCost;
            @XmlElement(name = "EarliestWeightedAverageCost", required = true)
            protected String earliestWeightedAverageCost;
            @XmlElement(name = "ImminentSettlement", required = true)
            protected String imminentSettlement;
            @XmlElement(name = "LinkAddressCode", required = true)
            protected String linkAddressCode;
            @XmlElement(name = "LookupCode", required = true)
            protected String lookupCode;
            @XmlElement(name = "MaintainStatistics", required = true)
            protected String maintainStatistics;
            @XmlElement(name = "PayToAddressCode", required = true)
            protected String payToAddressCode;
            @XmlElement(name = "PaymentMethod", required = true)
            protected String paymentMethod;
            @XmlElement(name = "PaymentTermsGroupCode", required = true)
            protected String paymentTermsGroupCode;
            @XmlElement(name = "PrimaryOrderAddressCode", required = true)
            protected String primaryOrderAddressCode;
            @XmlElement(name = "Priority", required = true)
            protected String priority;
            @XmlElement(name = "PurchaseTransactionType", required = true)
            protected String purchaseTransactionType;
            @XmlElement(name = "RemittanceSupplierCode", required = true)
            protected String remittanceSupplierCode;
            @XmlElement(name = "ShortHeading", required = true)
            protected String shortHeading;
            @XmlElement(name = "Status", required = true)
            protected String status;
            @XmlElement(name = "SupplierCode", required = true)
            protected String supplierCode;
            @XmlElement(name = "SupplierMiscDate1", required = true)
            protected String supplierMiscDate1;
            @XmlElement(name = "SupplierMiscDate2", required = true)
            protected String supplierMiscDate2;
            @XmlElement(name = "SupplierMiscDescription1", required = true)
            protected String supplierMiscDescription1;
            @XmlElement(name = "SupplierMiscDescription2", required = true)
            protected String supplierMiscDescription2;
            @XmlElement(name = "SupplierMiscNumber1", required = true)
            protected String supplierMiscNumber1;
            @XmlElement(name = "SupplierMiscNumber2", required = true)
            protected String supplierMiscNumber2;
            @XmlElement(name = "SupplierMiscReference1", required = true)
            protected String supplierMiscReference1;
            @XmlElement(name = "SupplierMiscReference2", required = true)
            protected String supplierMiscReference2;
            @XmlElement(name = "SupplierName", required = true)
            protected String supplierName;
            @XmlElement(name = "ToleranceDaysToApply", required = true)
            protected String toleranceDaysToApply;
            @XmlElement(name = "UpdateCount", required = true)
            protected String updateCount;
            @XmlElement(name = "UserIdLastUpdated", required = true)
            protected String userIdLastUpdated;
            @XmlElement(name = "WebPageAddress", required = true)
            protected String webPageAddress;
            @XmlElement(name = "BankDetails", required = true)
            protected List<SSC.Payload.Supplier.BankDetails> bankDetails;
            @XmlElement(name = "SupplierAddress", required = true)
            protected List<SSC.Payload.Supplier.SupplierAddress> supplierAddress;
            
            
            /**
             * Gets the value of the accountCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAccountCode() {
                return accountCode;
            }
            
            /**
             * Sets the value of the accountCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAccountCode(String value) {
                this.accountCode = value;
            }
            
            /**
             * Gets the value of the carrier property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCarrier() {
                return carrier;
            }
            
            /**
             * Sets the value of the carrier property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCarrier(String value) {
                this.carrier = value;
            }
            
            /**
             * Gets the value of the comment property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComment() {
                return comment;
            }
            
            /**
             * Sets the value of the comment property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComment(String value) {
                this.comment = value;
            }
            
            /**
             * Gets the value of the companyAddressCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCompanyAddressCode() {
                return companyAddressCode;
            }
            
            /**
             * Sets the value of the companyAddressCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCompanyAddressCode(String value) {
                this.companyAddressCode = value;
            }
            
            /**
             * Gets the value of the creditCheckWarningLimit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCreditCheckWarningLimit() {
                return creditCheckWarningLimit;
            }
            
            /**
             * Sets the value of the creditCheckWarningLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCreditCheckWarningLimit(String value) {
                this.creditCheckWarningLimit = value;
            }
            
            /**
             * Gets the value of the dataAccessGroupCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDataAccessGroupCode() {
                return dataAccessGroupCode;
            }
            
            /**
             * Sets the value of the dataAccessGroupCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDataAccessGroupCode(String value) {
                this.dataAccessGroupCode = value;
            }
            
            /**
             * Gets the value of the dateTimeLastUpdated property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDateTimeLastUpdated() {
                return dateTimeLastUpdated;
            }
            
            /**
             * Sets the value of the dateTimeLastUpdated property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDateTimeLastUpdated(String value) {
                this.dateTimeLastUpdated = value;
            }
            
            /**
             * Gets the value of the daysToleranceOverride property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDaysToleranceOverride() {
                return daysToleranceOverride;
            }
            
            /**
             * Sets the value of the daysToleranceOverride property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDaysToleranceOverride(String value) {
                this.daysToleranceOverride = value;
            }
            
            /**
             * Gets the value of the defaultBankSubcode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDefaultBankSubcode() {
                return defaultBankSubcode;
            }
            
            /**
             * Sets the value of the defaultBankSubcode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDefaultBankSubcode(String value) {
                this.defaultBankSubcode = value;
            }
            
            /**
             * Gets the value of the defaultBuyer property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDefaultBuyer() {
                return defaultBuyer;
            }
            
            /**
             * Sets the value of the defaultBuyer property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDefaultBuyer(String value) {
                this.defaultBuyer = value;
            }
            
            /**
             * Gets the value of the defaultBuyerRoleCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDefaultBuyerRoleCode() {
                return defaultBuyerRoleCode;
            }
            
            /**
             * Sets the value of the defaultBuyerRoleCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDefaultBuyerRoleCode(String value) {
                this.defaultBuyerRoleCode = value;
            }
            
            /**
             * Gets the value of the defaultCurrencyCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDefaultCurrencyCode() {
                return defaultCurrencyCode;
            }
            
            /**
             * Sets the value of the defaultCurrencyCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDefaultCurrencyCode(String value) {
                this.defaultCurrencyCode = value;
            }
            
            /**
             * Gets the value of the defaultReceivingWarehouse property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDefaultReceivingWarehouse() {
                return defaultReceivingWarehouse;
            }
            
            /**
             * Sets the value of the defaultReceivingWarehouse property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDefaultReceivingWarehouse(String value) {
                this.defaultReceivingWarehouse = value;
            }
            
            /**
             * Gets the value of the defaultSupplierReceivingLocation property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDefaultSupplierReceivingLocation() {
                return defaultSupplierReceivingLocation;
            }
            
            /**
             * Sets the value of the defaultSupplierReceivingLocation property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDefaultSupplierReceivingLocation(String value) {
                this.defaultSupplierReceivingLocation = value;
            }
            
            /**
             * Gets the value of the defaultSupplierReturnLocation property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDefaultSupplierReturnLocation() {
                return defaultSupplierReturnLocation;
            }
            
            /**
             * Sets the value of the defaultSupplierReturnLocation property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDefaultSupplierReturnLocation(String value) {
                this.defaultSupplierReturnLocation = value;
            }
            
            /**
             * Gets the value of the description property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }
            
            /**
             * Sets the value of the description property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }
            
            /**
             * Gets the value of the directDebit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDirectDebit() {
                return directDebit;
            }
            
            /**
             * Sets the value of the directDebit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDirectDebit(String value) {
                this.directDebit = value;
            }
            
            /**
             * Gets the value of the eMailAddress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEMailAddress() {
                return eMailAddress;
            }
            
            /**
             * Sets the value of the eMailAddress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEMailAddress(String value) {
                this.eMailAddress = value;
            }
            
            /**
             * Gets the value of the earliestLatestCost property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEarliestLatestCost() {
                return earliestLatestCost;
            }
            
            /**
             * Sets the value of the earliestLatestCost property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEarliestLatestCost(String value) {
                this.earliestLatestCost = value;
            }
            
            /**
             * Gets the value of the earliestServiceLatestCost property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEarliestServiceLatestCost() {
                return earliestServiceLatestCost;
            }
            
            /**
             * Sets the value of the earliestServiceLatestCost property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEarliestServiceLatestCost(String value) {
                this.earliestServiceLatestCost = value;
            }
            
            /**
             * Gets the value of the earliestServiceWeightedAverageCost property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEarliestServiceWeightedAverageCost() {
                return earliestServiceWeightedAverageCost;
            }
            
            /**
             * Sets the value of the earliestServiceWeightedAverageCost property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEarliestServiceWeightedAverageCost(String value) {
                this.earliestServiceWeightedAverageCost = value;
            }
            
            /**
             * Gets the value of the earliestWeightedAverageCost property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEarliestWeightedAverageCost() {
                return earliestWeightedAverageCost;
            }
            
            /**
             * Sets the value of the earliestWeightedAverageCost property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEarliestWeightedAverageCost(String value) {
                this.earliestWeightedAverageCost = value;
            }
            
            /**
             * Gets the value of the imminentSettlement property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getImminentSettlement() {
                return imminentSettlement;
            }
            
            /**
             * Sets the value of the imminentSettlement property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setImminentSettlement(String value) {
                this.imminentSettlement = value;
            }
            
            /**
             * Gets the value of the linkAddressCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLinkAddressCode() {
                return linkAddressCode;
            }
            
            /**
             * Sets the value of the linkAddressCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLinkAddressCode(String value) {
                this.linkAddressCode = value;
            }
            
            /**
             * Gets the value of the lookupCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLookupCode() {
                return lookupCode;
            }
            
            /**
             * Sets the value of the lookupCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLookupCode(String value) {
                this.lookupCode = value;
            }
            
            /**
             * Gets the value of the maintainStatistics property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMaintainStatistics() {
                return maintainStatistics;
            }
            
            /**
             * Sets the value of the maintainStatistics property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMaintainStatistics(String value) {
                this.maintainStatistics = value;
            }
            
            /**
             * Gets the value of the payToAddressCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPayToAddressCode() {
                return payToAddressCode;
            }
            
            /**
             * Sets the value of the payToAddressCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPayToAddressCode(String value) {
                this.payToAddressCode = value;
            }
            
            /**
             * Gets the value of the paymentMethod property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPaymentMethod() {
                return paymentMethod;
            }
            
            /**
             * Sets the value of the paymentMethod property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPaymentMethod(String value) {
                this.paymentMethod = value;
            }
            
            /**
             * Gets the value of the paymentTermsGroupCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPaymentTermsGroupCode() {
                return paymentTermsGroupCode;
            }
            
            /**
             * Sets the value of the paymentTermsGroupCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPaymentTermsGroupCode(String value) {
                this.paymentTermsGroupCode = value;
            }
            
            /**
             * Gets the value of the primaryOrderAddressCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPrimaryOrderAddressCode() {
                return primaryOrderAddressCode;
            }
            
            /**
             * Sets the value of the primaryOrderAddressCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPrimaryOrderAddressCode(String value) {
                this.primaryOrderAddressCode = value;
            }
            
            /**
             * Gets the value of the priority property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPriority() {
                return priority;
            }
            
            /**
             * Sets the value of the priority property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPriority(String value) {
                this.priority = value;
            }
            
            /**
             * Gets the value of the purchaseTransactionType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPurchaseTransactionType() {
                return purchaseTransactionType;
            }
            
            /**
             * Sets the value of the purchaseTransactionType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPurchaseTransactionType(String value) {
                this.purchaseTransactionType = value;
            }
            
            /**
             * Gets the value of the remittanceSupplierCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRemittanceSupplierCode() {
                return remittanceSupplierCode;
            }
            
            /**
             * Sets the value of the remittanceSupplierCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRemittanceSupplierCode(String value) {
                this.remittanceSupplierCode = value;
            }
            
            /**
             * Gets the value of the shortHeading property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getShortHeading() {
                return shortHeading;
            }
            
            /**
             * Sets the value of the shortHeading property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setShortHeading(String value) {
                this.shortHeading = value;
            }
            
            /**
             * Gets the value of the status property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatus() {
                return status;
            }
            
            /**
             * Sets the value of the status property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatus(String value) {
                this.status = value;
            }
            
            /**
             * Gets the value of the supplierCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierCode() {
                return supplierCode;
            }
            
            /**
             * Sets the value of the supplierCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierCode(String value) {
                this.supplierCode = value;
            }
            
            /**
             * Gets the value of the supplierMiscDate1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscDate1() {
                return supplierMiscDate1;
            }
            
            /**
             * Sets the value of the supplierMiscDate1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscDate1(String value) {
                this.supplierMiscDate1 = value;
            }
            
            /**
             * Gets the value of the supplierMiscDate2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscDate2() {
                return supplierMiscDate2;
            }
            
            /**
             * Sets the value of the supplierMiscDate2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscDate2(String value) {
                this.supplierMiscDate2 = value;
            }
            
            /**
             * Gets the value of the supplierMiscDescription1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscDescription1() {
                return supplierMiscDescription1;
            }
            
            /**
             * Sets the value of the supplierMiscDescription1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscDescription1(String value) {
                this.supplierMiscDescription1 = value;
            }
            
            /**
             * Gets the value of the supplierMiscDescription2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscDescription2() {
                return supplierMiscDescription2;
            }
            
            /**
             * Sets the value of the supplierMiscDescription2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscDescription2(String value) {
                this.supplierMiscDescription2 = value;
            }
            
            /**
             * Gets the value of the supplierMiscNumber1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscNumber1() {
                return supplierMiscNumber1;
            }
            
            /**
             * Sets the value of the supplierMiscNumber1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscNumber1(String value) {
                this.supplierMiscNumber1 = value;
            }
            
            /**
             * Gets the value of the supplierMiscNumber2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscNumber2() {
                return supplierMiscNumber2;
            }
            
            /**
             * Sets the value of the supplierMiscNumber2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscNumber2(String value) {
                this.supplierMiscNumber2 = value;
            }
            
            /**
             * Gets the value of the supplierMiscReference1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscReference1() {
                return supplierMiscReference1;
            }
            
            /**
             * Sets the value of the supplierMiscReference1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscReference1(String value) {
                this.supplierMiscReference1 = value;
            }
            
            /**
             * Gets the value of the supplierMiscReference2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierMiscReference2() {
                return supplierMiscReference2;
            }
            
            /**
             * Sets the value of the supplierMiscReference2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierMiscReference2(String value) {
                this.supplierMiscReference2 = value;
            }
            
            /**
             * Gets the value of the supplierName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierName() {
                return supplierName;
            }
            
            /**
             * Sets the value of the supplierName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierName(String value) {
                this.supplierName = value;
            }
            
            /**
             * Gets the value of the toleranceDaysToApply property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getToleranceDaysToApply() {
                return toleranceDaysToApply;
            }
            
            /**
             * Sets the value of the toleranceDaysToApply property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setToleranceDaysToApply(String value) {
                this.toleranceDaysToApply = value;
            }
            
            /**
             * Gets the value of the updateCount property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUpdateCount() {
                return updateCount;
            }
            
            /**
             * Sets the value of the updateCount property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUpdateCount(String value) {
                this.updateCount = value;
            }
            
            /**
             * Gets the value of the userIdLastUpdated property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUserIdLastUpdated() {
                return userIdLastUpdated;
            }
            
            /**
             * Sets the value of the userIdLastUpdated property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUserIdLastUpdated(String value) {
                this.userIdLastUpdated = value;
            }
            
            /**
             * Gets the value of the webPageAddress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getWebPageAddress() {
                return webPageAddress;
            }
            
            /**
             * Sets the value of the webPageAddress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setWebPageAddress(String value) {
                this.webPageAddress = value;
            }
            
            /**
             * Gets the value of the bankDetails property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the bankDetails property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBankDetails().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link SSC.Payload.Supplier.BankDetails }
             * 
             * 
             */
            public List<SSC.Payload.Supplier.BankDetails> getBankDetails() {
                if (bankDetails == null) {
                    bankDetails = new ArrayList<SSC.Payload.Supplier.BankDetails>();
                }
                return this.bankDetails;
            }
            
            /**
             * Gets the value of the supplierAddress property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the supplierAddress property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSupplierAddress().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link SSC.Payload.Supplier.SupplierAddress }
             * 
             * 
             */
            public List<SSC.Payload.Supplier.SupplierAddress> getSupplierAddress() {
                if (supplierAddress == null) {
                    supplierAddress = new ArrayList<SSC.Payload.Supplier.SupplierAddress>();
                }
                return this.supplierAddress;
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
			 *		   &lt;element name="BankAccountName" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="BankAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="BankAddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="BankBranch" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="BankDetailsCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="BankSortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="BankSubcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="DataAccessGroupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="DateTimeLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="DirectDebitFirstPayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="OwnCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="SupplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="TransactionLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="TransactionReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="UpdateCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="UserIdLastUpdated" type="{http://www.w3.org/2001/XMLSchema}string"/>
	       	 *       &lt;/sequence>
	       	 *     &lt;/restriction>
	       	 *   &lt;/complexContent>
	       	 * &lt;/complexType>
	       	 * </pre>
	       	 * 
	       	 * 
	       	 */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { 
            	"bankAccountName",
                "bankAccountNumber",
                "bankAddressCode",
                "bankBranch",
                "bankDetailsCode",
                "bankName",
                "bankSortCode",
                "bankSubcode",
                "comment",
                "customerCode",
                "dataAccessGroupCode",
                "dateTimeLastUpdated",
                "directDebitFirstPayment",
                "lookupCode",
                "ownCompanyCode",
                "status",
                "supplierCode",
                "transactionLimit",
                "transactionReference",
                "updateCount",
                "userIdLastUpdated"
            })
            public static class BankDetails
            {
            	@XmlElement(name = "BankAccountName", required = true)
                protected String bankAccountName;
                @XmlElement(name = "BankAccountNumber", required = true)
                protected String bankAccountNumber;
                @XmlElement(name = "BankAddressCode", required = true)
                protected String bankAddressCode;
                @XmlElement(name = "BankBranch", required = true)
                protected String bankBranch;
                @XmlElement(name = "BankDetailsCode", required = true)
                protected String bankDetailsCode;
                @XmlElement(name = "BankName", required = true)
                protected String bankName;
                @XmlElement(name = "BankSortCode", required = true)
                protected String bankSortCode;
                @XmlElement(name = "BankSubcode", required = true)
                protected String bankSubcode;
                @XmlElement(name = "Comment", required = true)
                protected String comment;
                @XmlElement(name = "CustomerCode", required = true)
                protected String customerCode;
                @XmlElement(name = "DataAccessGroupCode", required = true)
                protected String dataAccessGroupCode;
                @XmlElement(name = "DateTimeLastUpdated", required = true)
                protected String dateTimeLastUpdated;
                @XmlElement(name = "DirectDebitFirstPayment", required = true)
                protected String directDebitFirstPayment;
                @XmlElement(name = "LookupCode", required = true)
                protected String lookupCode;
                @XmlElement(name = "OwnCompanyCode", required = true)
                protected String ownCompanyCode;
                @XmlElement(name = "Status", required = true)
                protected String status;
                @XmlElement(name = "SupplierCode", required = true)
                protected String supplierCode;
                @XmlElement(name = "TransactionLimit", required = true)
                protected String transactionLimit;
                @XmlElement(name = "TransactionReference", required = true)
                protected String transactionReference;
                @XmlElement(name = "UpdateCount", required = true)
                protected String updateCount;
                @XmlElement(name = "UserIdLastUpdated", required = true)
                protected String userIdLastUpdated;
            	
                
                /**
                 * Gets the value of the bankAccountName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankAccountName() {
                    return bankAccountName;
                }
                
                /**
                 * Sets the value of the bankAccountName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankAccountName(String value) {
                    this.bankAccountName = value;
                }

                /**
                 * Gets the value of the bankAccountNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankAccountNumber() {
                    return bankAccountNumber;
                }

                /**
                 * Sets the value of the bankAccountNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankAccountNumber(String value) {
                    this.bankAccountNumber = value;
                }

                /**
                 * Gets the value of the bankAddressCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankAddressCode() {
                    return bankAddressCode;
                }

                /**
                 * Sets the value of the bankAddressCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankAddressCode(String value) {
                    this.bankAddressCode = value;
                }

                /**
                 * Gets the value of the bankBranch property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankBranch() {
                    return bankBranch;
                }

                /**
                 * Sets the value of the bankBranch property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankBranch(String value) {
                    this.bankBranch = value;
                }
                
                /**
                 * Gets the value of the bankDetailsCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankDetailsCode() {
                    return bankDetailsCode;
                }

                /**
                 * Sets the value of the bankDetailsCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankDetailsCode(String value) {
                    this.bankDetailsCode = value;
                }
                
                /**
                 * Gets the value of the bankName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankName() {
                    return bankName;
                }

                /**
                 * Sets the value of the bankName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankName(String value) {
                    this.bankName = value;
                }

                /**
                 * Gets the value of the bankSortCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankSortCode() {
                    return bankSortCode;
                }

                /**
                 * Sets the value of the bankSortCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankSortCode(String value) {
                    this.bankSortCode = value;
                }

                /**
                 * Gets the value of the bankSubcode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBankSubcode() {
                    return bankSubcode;
                }

                /**
                 * Sets the value of the bankSubcode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBankSubcode(String value) {
                    this.bankSubcode = value;
                }

                /**
                 * Gets the value of the comment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getComment() {
                    return comment;
                }

                /**
                 * Sets the value of the comment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setComment(String value) {
                    this.comment = value;
                }

                /**
                 * Gets the value of the customerCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCustomerCode() {
                    return customerCode;
                }

                /**
                 * Sets the value of the customerCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCustomerCode(String value) {
                    this.customerCode = value;
                }

                /**
                 * Gets the value of the dataAccessGroupCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDataAccessGroupCode() {
                    return dataAccessGroupCode;
                }

                /**
                 * Sets the value of the dataAccessGroupCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDataAccessGroupCode(String value) {
                    this.dataAccessGroupCode = value;
                }

                /**
                 * Gets the value of the dateTimeLastUpdated property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDateTimeLastUpdated() {
                    return dateTimeLastUpdated;
                }

                /**
                 * Sets the value of the dateTimeLastUpdated property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDateTimeLastUpdated(String value) {
                    this.dateTimeLastUpdated = value;
                }

                /**
                 * Gets the value of the directDebitFirstPayment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDirectDebitFirstPayment() {
                    return directDebitFirstPayment;
                }

                /**
                 * Sets the value of the directDebitFirstPayment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDirectDebitFirstPayment(String value) {
                    this.directDebitFirstPayment = value;
                }

                /**
                 * Gets the value of the lookupCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLookupCode() {
                    return lookupCode;
                }

                /**
                 * Sets the value of the lookupCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLookupCode(String value) {
                    this.lookupCode = value;
                }

                /**
                 * Gets the value of the ownCompanyCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOwnCompanyCode() {
                    return ownCompanyCode;
                }

                /**
                 * Sets the value of the ownCompanyCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOwnCompanyCode(String value) {
                    this.ownCompanyCode = value;
                }

                /**
                 * Gets the value of the status property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStatus() {
                    return status;
                }

                /**
                 * Sets the value of the status property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStatus(String value) {
                    this.status = value;
                }

                /**
                 * Gets the value of the supplierCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSupplierCode() {
                    return supplierCode;
                }

                /**
                 * Sets the value of the supplierCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSupplierCode(String value) {
                    this.supplierCode = value;
                }

                /**
                 * Gets the value of the transactionLimit property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransactionLimit() {
                    return transactionLimit;
                }

                /**
                 * Sets the value of the transactionLimit property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransactionLimit(String value) {
                    this.transactionLimit = value;
                }

                /**
                 * Gets the value of the transactionReference property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransactionReference() {
                    return transactionReference;
                }

                /**
                 * Sets the value of the transactionReference property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransactionReference(String value) {
                    this.transactionReference = value;
                }

                /**
                 * Gets the value of the updateCount property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUpdateCount() {
                    return updateCount;
                }

                /**
                 * Sets the value of the updateCount property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUpdateCount(String value) {
                    this.updateCount = value;
                }

                /**
                 * Gets the value of the userIdLastUpdated property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUserIdLastUpdated() {
                    return userIdLastUpdated;
                }

                /**
                 * Sets the value of the userIdLastUpdated property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUserIdLastUpdated(String value) {
                    this.userIdLastUpdated = value;
                }
            
            } //BankDetails
            
            
            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="AddressCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AddressLine1" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AddressLine2" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AddressLine3" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AddressLine4" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AddressLine5" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Area" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="FaxNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="LookupCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ShortHeading" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="StateCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="TelephoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="TemporaryAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="TownCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="WebPageAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "addressCode",
                "addressLine1",
                "addressLine2",
                "addressLine3",
                "addressLine4",
                "addressLine5",
                "area",
                "country",
                "countryCode",
                "faxNumber",
                "languageCode",
                "lookupCode",
                "postalCode",
                "shortHeading",
                "state",
                "stateCode",
                "status",
                "telephoneNumber",
                "temporaryAddress",
                "townCity",
                "webPageAddress"
            })
            public static class SupplierAddress
            {
                @XmlElement(name = "AddressCode", required = true)
                protected String addressCode;
                @XmlElement(name = "AddressLine1", required = true)
                protected String addressLine1;
                @XmlElement(name = "AddressLine2", required = true)
                protected String addressLine2;
                @XmlElement(name = "AddressLine3", required = true)
                protected String addressLine3;
                @XmlElement(name = "AddressLine4", required = true)
                protected String addressLine4;
                @XmlElement(name = "AddressLine5", required = true)
                protected String addressLine5;
                @XmlElement(name = "Area", required = true)
                protected String area;
                @XmlElement(name = "Country", required = true)
                protected String country;
                @XmlElement(name = "CountryCode", required = true)
                protected String countryCode;
                @XmlElement(name = "FaxNumber", required = true)
                protected String faxNumber;
                @XmlElement(name = "LanguageCode", required = true)
                protected String languageCode;
                @XmlElement(name = "LookupCode", required = true)
                protected String lookupCode;
                @XmlElement(name = "PostalCode", required = true)
                protected String postalCode;
                @XmlElement(name = "ShortHeading", required = true)
                protected String shortHeading;
                @XmlElement(name = "State", required = true)
                protected String state;
                @XmlElement(name = "StateCode", required = true)
                protected String stateCode;
                @XmlElement(name = "Status", required = true)
                protected String status;
                @XmlElement(name = "TelephoneNumber", required = true)
                protected String telephoneNumber;
                @XmlElement(name = "TemporaryAddress", required = true)
                protected String temporaryAddress;
                @XmlElement(name = "TownCity", required = true)
                protected String townCity;
                @XmlElement(name = "WebPageAddress", required = true)
                protected String webPageAddress;
                
                
                /**
                 * Gets the value of the addressCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressCode() {
                    return addressCode;
                }
                
                /**
                 * Sets the value of the addressCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressCode(String value) {
                    this.addressCode = value;
                }
                
                /**
                 * Gets the value of the addressLine1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressLine1() {
                    return addressLine1;
                }
                
                /**
                 * Sets the value of the addressLine1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressLine1(String value) {
                    this.addressLine1 = value;
                }
                
                /**
                 * Gets the value of the addressLine2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressLine2() {
                    return addressLine2;
                }
                
                /**
                 * Sets the value of the addressLine2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressLine2(String value) {
                    this.addressLine2 = value;
                }
                
                /**
                 * Gets the value of the addressLine3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressLine3() {
                    return addressLine3;
                }
                
                /**
                 * Sets the value of the addressLine3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressLine3(String value) {
                    this.addressLine3 = value;
                }
                
                /**
                 * Gets the value of the addressLine4 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressLine4() {
                    return addressLine4;
                }
                
                /**
                 * Sets the value of the addressLine4 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressLine4(String value) {
                    this.addressLine4 = value;
                }
                
                /**
                 * Gets the value of the addressLine5 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressLine5() {
                    return addressLine5;
                }
                
                /**
                 * Sets the value of the addressLine5 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressLine5(String value) {
                    this.addressLine5 = value;
                }
                
                /**
                 * Gets the value of the area property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getArea() {
                    return area;
                }
                
                /**
                 * Sets the value of the area property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setArea(String value) {
                    this.area = value;
                }
                
                /**
                 * Gets the value of the country property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCountry() {
                    return country;
                }
                
                /**
                 * Sets the value of the country property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCountry(String value) {
                    this.country = value;
                }
                
                /**
                 * Gets the value of the countryCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCountryCode() {
                    return countryCode;
                }
                
                /**
                 * Sets the value of the countryCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCountryCode(String value) {
                    this.countryCode = value;
                }
                
                /**
                 * Gets the value of the faxNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFaxNumber() {
                    return faxNumber;
                }
                
                /**
                 * Sets the value of the faxNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFaxNumber(String value) {
                    this.faxNumber = value;
                }
                
                /**
                 * Gets the value of the languageCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLanguageCode() {
                    return languageCode;
                }
                
                /**
                 * Sets the value of the languageCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLanguageCode(String value) {
                    this.languageCode = value;
                }
                
                /**
                 * Gets the value of the lookupCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLookupCode() {
                    return lookupCode;
                }
                
                /**
                 * Sets the value of the lookupCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLookupCode(String value) {
                    this.lookupCode = value;
                }
                
                /**
                 * Gets the value of the postalCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPostalCode() {
                    return postalCode;
                }
                
                /**
                 * Sets the value of the postalCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPostalCode(String value) {
                    this.postalCode = value;
                }
                
                /**
                 * Gets the value of the shortHeading property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getShortHeading() {
                    return shortHeading;
                }
                
                /**
                 * Sets the value of the shortHeading property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setShortHeading(String value) {
                    this.shortHeading = value;
                }
                
                /**
                 * Gets the value of the state property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getState() {
                    return state;
                }
                
                /**
                 * Sets the value of the state property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setState(String value) {
                    this.state = value;
                }
                
                /**
                 * Gets the value of the stateCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStateCode() {
                    return stateCode;
                }
                
                /**
                 * Sets the value of the stateCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStateCode(String value) {
                    this.stateCode = value;
                }
                
                /**
                 * Gets the value of the status property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStatus() {
                    return status;
                }
                
                /**
                 * Sets the value of the status property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStatus(String value) {
                    this.status = value;
                }
                
                /**
                 * Gets the value of the telephoneNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTelephoneNumber() {
                    return telephoneNumber;
                }
                
                /**
                 * Sets the value of the telephoneNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTelephoneNumber(String value) {
                    this.telephoneNumber = value;
                }
                
                /**
                 * Gets the value of the temporaryAddress property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTemporaryAddress() {
                    return temporaryAddress;
                }
                
                /**
                 * Sets the value of the temporaryAddress property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTemporaryAddress(String value) {
                    this.temporaryAddress = value;
                }
                
                /**
                 * Gets the value of the townCity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTownCity() {
                    return townCity;
                }
                
                /**
                 * Sets the value of the townCity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTownCity(String value) {
                    this.townCity = value;
                }
                
                /**
                 * Gets the value of the webPageAddress property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWebPageAddress() {
                    return webPageAddress;
                }
                
                /**
                 * Sets the value of the webPageAddress property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWebPageAddress(String value) {
                    this.webPageAddress = value;
                }
                
            } //SupplierAddress
            
	    } //Supplier
	    
  	} //Payload
	
} //SSC
