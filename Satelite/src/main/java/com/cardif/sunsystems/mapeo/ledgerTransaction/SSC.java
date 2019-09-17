package com.cardif.sunsystems.mapeo.ledgerTransaction;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
 *                   &lt;element name="BudgetCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *                   &lt;element name="LedgerUpdate" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *                             &lt;element name="AllocationMarker" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AnalysisCode9" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BaseAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                             &lt;element name="DebitCredit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="JournalLineNumber" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="JournalNumber" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                             &lt;element name="JournalType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Actions" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="GeneralDescription1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="GeneralDescription2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Messages" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Message" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="UserText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="Application">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="Component" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                               &lt;attribute name="Level" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                           &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "", propOrder = {
    "user",
    "sunSystemsContext",
    "payload"
})
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
     * @return
     *     possible object is
     *     {@link SSC.User }
     *     
     */
    public SSC.User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link SSC.User }
     *     
     */
    public void setUser(SSC.User value) {
        this.user = value;
    }

    /**
     * Gets the value of the sunSystemsContext property.
     * 
     * @return
     *     possible object is
     *     {@link SSC.SunSystemsContext }
     *     
     */
    public SSC.SunSystemsContext getSunSystemsContext() {
        return sunSystemsContext;
    }

    /**
     * Sets the value of the sunSystemsContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link SSC.SunSystemsContext }
     *     
     */
    public void setSunSystemsContext(SSC.SunSystemsContext value) {
        this.sunSystemsContext = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link SSC.Payload }
     *     
     */
    public SSC.Payload getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link SSC.Payload }
     *     
     */
    public void setPayload(SSC.Payload value) {
        this.payload = value;
    }


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
     *         &lt;element name="LedgerUpdate" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}long"/>
     *                   &lt;element name="AllocationMarker" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode10" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode3" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode4" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode5" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode6" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode7" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode8" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="AnalysisCode9" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BaseAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="DebitCredit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="JournalLineNumber" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="JournalNumber" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *                   &lt;element name="JournalType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Actions" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="GeneralDescription1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="GeneralDescription2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Messages" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Message" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="UserText" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="Application">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="Component" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                 &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                 &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                     &lt;attribute name="Level" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                     &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
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
     *                 &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                 &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "ledgerUpdate"
    })
    public static class Payload {

        @XmlElement(name = "LedgerUpdate")
        protected List<SSC.Payload.LedgerUpdate> ledgerUpdate;

        /**
         * Gets the value of the ledgerUpdate property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ledgerUpdate property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLedgerUpdate().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SSC.Payload.LedgerUpdate }
         * 
         * 
         */
        public List<SSC.Payload.LedgerUpdate> getLedgerUpdate() {
            if (ledgerUpdate == null) {
                ledgerUpdate = new ArrayList<SSC.Payload.LedgerUpdate>();
            }
            return this.ledgerUpdate;
        }


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
         *         &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}long"/>
         *         &lt;element name="AllocationMarker" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode10" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode2" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode3" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode4" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode5" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode6" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode7" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode8" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AnalysisCode9" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BaseAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *         &lt;element name="DebitCredit" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="JournalLineNumber" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *         &lt;element name="JournalNumber" type="{http://www.w3.org/2001/XMLSchema}short"/>
         *         &lt;element name="JournalType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Actions" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="GeneralDescription1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="GeneralDescription2" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Messages" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Message" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="UserText" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="Application">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="Component" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                       &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                       &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                           &lt;attribute name="Level" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                           &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
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
         *       &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            "allocationMarker",
            "analysisCode1",
            "analysisCode10",
            "analysisCode2",
            "analysisCode3",
            "analysisCode4",
            "analysisCode5",
            "analysisCode6",
            "analysisCode7",
            "analysisCode8",
            "analysisCode9",
            "baseAmount",
            "debitCredit",
            "journalLineNumber",
            "journalNumber",
            "journalType",
            "actions",
            "messages"
        })
        public static class LedgerUpdate {

            @XmlElement(name = "AccountCode")
            protected long accountCode;
            @XmlElement(name = "AllocationMarker", required = true)
            protected String allocationMarker;
            @XmlElement(name = "AnalysisCode1", required = true)
            protected String analysisCode1;
            @XmlElement(name = "AnalysisCode10", required = true)
            protected String analysisCode10;
            @XmlElement(name = "AnalysisCode2", required = true)
            protected String analysisCode2;
            @XmlElement(name = "AnalysisCode3", required = true)
            protected String analysisCode3;
            @XmlElement(name = "AnalysisCode4", required = true)
            protected String analysisCode4;
            @XmlElement(name = "AnalysisCode5", required = true)
            protected String analysisCode5;
            @XmlElement(name = "AnalysisCode6", required = true)
            protected String analysisCode6;
            @XmlElement(name = "AnalysisCode7", required = true)
            protected String analysisCode7;
            @XmlElement(name = "AnalysisCode8", required = true)
            protected String analysisCode8;
            @XmlElement(name = "AnalysisCode9", required = true)
            protected String analysisCode9;
            @XmlElement(name = "BaseAmount")
            protected float baseAmount;
            @XmlElement(name = "DebitCredit", required = true)
            protected String debitCredit;
            @XmlElement(name = "JournalLineNumber")
            protected byte journalLineNumber;
            @XmlElement(name = "JournalNumber")
            protected short journalNumber;
            @XmlElement(name = "JournalType", required = true)
            protected String journalType;
            @XmlElement(name = "Actions")
            protected List<SSC.Payload.LedgerUpdate.Actions> actions;
            @XmlElement(name = "Messages")
            protected SSC.Payload.LedgerUpdate.Messages messages;
            @XmlAttribute(name = "Reference")
            protected Byte reference;
            @XmlAttribute
            protected String status;

            /**
             * Gets the value of the accountCode property.
             * 
             */
            public long getAccountCode() {
                return accountCode;
            }

            /**
             * Sets the value of the accountCode property.
             * 
             */
            public void setAccountCode(long value) {
                this.accountCode = value;
            }

            /**
             * Gets the value of the allocationMarker property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAllocationMarker() {
                return allocationMarker;
            }

            /**
             * Sets the value of the allocationMarker property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAllocationMarker(String value) {
                this.allocationMarker = value;
            }

            /**
             * Gets the value of the analysisCode1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode1() {
                return analysisCode1;
            }

            /**
             * Sets the value of the analysisCode1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode1(String value) {
                this.analysisCode1 = value;
            }

            /**
             * Gets the value of the analysisCode10 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode10() {
                return analysisCode10;
            }

            /**
             * Sets the value of the analysisCode10 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode10(String value) {
                this.analysisCode10 = value;
            }

            /**
             * Gets the value of the analysisCode2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode2() {
                return analysisCode2;
            }

            /**
             * Sets the value of the analysisCode2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode2(String value) {
                this.analysisCode2 = value;
            }

            /**
             * Gets the value of the analysisCode3 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode3() {
                return analysisCode3;
            }

            /**
             * Sets the value of the analysisCode3 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode3(String value) {
                this.analysisCode3 = value;
            }

            /**
             * Gets the value of the analysisCode4 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode4() {
                return analysisCode4;
            }

            /**
             * Sets the value of the analysisCode4 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode4(String value) {
                this.analysisCode4 = value;
            }

            /**
             * Gets the value of the analysisCode5 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode5() {
                return analysisCode5;
            }

            /**
             * Sets the value of the analysisCode5 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode5(String value) {
                this.analysisCode5 = value;
            }

            /**
             * Gets the value of the analysisCode6 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode6() {
                return analysisCode6;
            }

            /**
             * Sets the value of the analysisCode6 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode6(String value) {
                this.analysisCode6 = value;
            }

            /**
             * Gets the value of the analysisCode7 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode7() {
                return analysisCode7;
            }

            /**
             * Sets the value of the analysisCode7 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode7(String value) {
                this.analysisCode7 = value;
            }

            /**
             * Gets the value of the analysisCode8 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode8() {
                return analysisCode8;
            }

            /**
             * Sets the value of the analysisCode8 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode8(String value) {
                this.analysisCode8 = value;
            }

            /**
             * Gets the value of the analysisCode9 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnalysisCode9() {
                return analysisCode9;
            }

            /**
             * Sets the value of the analysisCode9 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnalysisCode9(String value) {
                this.analysisCode9 = value;
            }

            /**
             * Gets the value of the baseAmount property.
             * 
             */
            public float getBaseAmount() {
                return baseAmount;
            }

            /**
             * Sets the value of the baseAmount property.
             * 
             */
            public void setBaseAmount(float value) {
                this.baseAmount = value;
            }

            /**
             * Gets the value of the debitCredit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDebitCredit() {
                return debitCredit;
            }

            /**
             * Sets the value of the debitCredit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDebitCredit(String value) {
                this.debitCredit = value;
            }

            /**
             * Gets the value of the journalLineNumber property.
             * 
             */
            public byte getJournalLineNumber() {
                return journalLineNumber;
            }

            /**
             * Sets the value of the journalLineNumber property.
             * 
             */
            public void setJournalLineNumber(byte value) {
                this.journalLineNumber = value;
            }

            /**
             * Gets the value of the journalNumber property.
             * 
             */
            public short getJournalNumber() {
                return journalNumber;
            }

            /**
             * Sets the value of the journalNumber property.
             * 
             */
            public void setJournalNumber(short value) {
                this.journalNumber = value;
            }

            /**
             * Gets the value of the journalType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getJournalType() {
                return journalType;
            }

            /**
             * Sets the value of the journalType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setJournalType(String value) {
                this.journalType = value;
            }

            /**
             * Gets the value of the actions property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the actions property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getActions().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link SSC.Payload.LedgerUpdate.Actions }
             * 
             * 
             */
            public List<SSC.Payload.LedgerUpdate.Actions> getActions() {
                if (actions == null) {
                    actions = new ArrayList<SSC.Payload.LedgerUpdate.Actions>();
                }
                return this.actions;
            }

            /**
             * Gets the value of the messages property.
             * 
             * @return
             *     possible object is
             *     {@link SSC.Payload.LedgerUpdate.Messages }
             *     
             */
            public SSC.Payload.LedgerUpdate.Messages getMessages() {
                return messages;
            }

            /**
             * Sets the value of the messages property.
             * 
             * @param value
             *     allowed object is
             *     {@link SSC.Payload.LedgerUpdate.Messages }
             *     
             */
            public void setMessages(SSC.Payload.LedgerUpdate.Messages value) {
                this.messages = value;
            }

            /**
             * Gets the value of the reference property.
             * 
             * @return
             *     possible object is
             *     {@link Byte }
             *     
             */
            public Byte getReference() {
                return reference;
            }

            /**
             * Sets the value of the reference property.
             * 
             * @param value
             *     allowed object is
             *     {@link Byte }
             *     
             */
            public void setReference(Byte value) {
                this.reference = value;
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
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="GeneralDescription1" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="GeneralDescription2" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "generalDescription1",
                "generalDescription2"
            })
            public static class Actions {

                @XmlElement(name = "GeneralDescription1", required = true)
                protected String generalDescription1;
                @XmlElement(name = "GeneralDescription2", required = true)
                protected String generalDescription2;

                /**
                 * Gets the value of the generalDescription1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getGeneralDescription1() {
                    return generalDescription1;
                }

                /**
                 * Sets the value of the generalDescription1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setGeneralDescription1(String value) {
                    this.generalDescription1 = value;
                }

                /**
                 * Gets the value of the generalDescription2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getGeneralDescription2() {
                    return generalDescription2;
                }

                /**
                 * Sets the value of the generalDescription2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setGeneralDescription2(String value) {
                    this.generalDescription2 = value;
                }

            }


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
             *         &lt;element name="Message" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="UserText" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="Application">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="Component" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                             &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                             &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}byte"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *                 &lt;attribute name="Level" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                 &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
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
                "message"
            })
            public static class Messages {

                @XmlElement(name = "Message")
                protected List<SSC.Payload.LedgerUpdate.Messages.Message> message;

                /**
                 * Gets the value of the message property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the message property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getMessage().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link SSC.Payload.LedgerUpdate.Messages.Message }
                 * 
                 * 
                 */
                public List<SSC.Payload.LedgerUpdate.Messages.Message> getMessage() {
                    if (message == null) {
                        message = new ArrayList<SSC.Payload.LedgerUpdate.Messages.Message>();
                    }
                    return this.message;
                }


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
                 *         &lt;element name="UserText" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="Application">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="Component" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                   &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                   &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}byte"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *       &lt;attribute name="Level" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *       &lt;attribute name="Reference" type="{http://www.w3.org/2001/XMLSchema}byte" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "userText",
                    "application"
                })
                public static class Message {

                    @XmlElement(name = "UserText", required = true)
                    protected String userText;
                    @XmlElement(name = "Application", required = true)
                    protected SSC.Payload.LedgerUpdate.Messages.Message.Application application;
                    @XmlAttribute(name = "Level")
                    protected String level;
                    @XmlAttribute(name = "Reference")
                    protected Byte reference;

                    /**
                     * Gets the value of the userText property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getUserText() {
                        return userText;
                    }

                    /**
                     * Sets the value of the userText property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setUserText(String value) {
                        this.userText = value;
                    }

                    /**
                     * Gets the value of the application property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link SSC.Payload.LedgerUpdate.Messages.Message.Application }
                     *     
                     */
                    public SSC.Payload.LedgerUpdate.Messages.Message.Application getApplication() {
                        return application;
                    }

                    /**
                     * Sets the value of the application property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link SSC.Payload.LedgerUpdate.Messages.Message.Application }
                     *     
                     */
                    public void setApplication(SSC.Payload.LedgerUpdate.Messages.Message.Application value) {
                        this.application = value;
                    }

                    /**
                     * Gets the value of the level property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getLevel() {
                        return level;
                    }

                    /**
                     * Sets the value of the level property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setLevel(String value) {
                        this.level = value;
                    }

                    /**
                     * Gets the value of the reference property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Byte }
                     *     
                     */
                    public Byte getReference() {
                        return reference;
                    }

                    /**
                     * Sets the value of the reference property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Byte }
                     *     
                     */
                    public void setReference(Byte value) {
                        this.reference = value;
                    }


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
                     *         &lt;element name="Component" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *         &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}byte"/>
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
                        "component",
                        "method",
                        "version"
                    })
                    public static class Application {

                        @XmlElement(name = "Component", required = true)
                        protected String component;
                        @XmlElement(name = "Method", required = true)
                        protected String method;
                        @XmlElement(name = "Version")
                        protected byte version;

                        /**
                         * Gets the value of the component property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getComponent() {
                            return component;
                        }

                        /**
                         * Sets the value of the component property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setComponent(String value) {
                            this.component = value;
                        }

                        /**
                         * Gets the value of the method property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getMethod() {
                            return method;
                        }

                        /**
                         * Sets the value of the method property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setMethod(String value) {
                            this.method = value;
                        }

                        /**
                         * Gets the value of the version property.
                         * 
                         */
                        public byte getVersion() {
                            return version;
                        }

                        /**
                         * Sets the value of the version property.
                         * 
                         */
                        public void setVersion(byte value) {
                            this.version = value;
                        }

                    }

                }

            }

        }

    }


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
     *         &lt;element name="BusinessUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="BudgetCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "businessUnit",
        "budgetCode"
    })
    public static class SunSystemsContext {

        @XmlElement(name = "BusinessUnit", required = true)
        protected String businessUnit;
        @XmlElement(name = "BudgetCode", required = true)
        protected String budgetCode;

        /**
         * Gets the value of the businessUnit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBusinessUnit() {
            return businessUnit;
        }

        /**
         * Sets the value of the businessUnit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBusinessUnit(String value) {
            this.businessUnit = value;
        }

        /**
         * Gets the value of the budgetCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBudgetCode() {
            return budgetCode;
        }

        /**
         * Sets the value of the budgetCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBudgetCode(String value) {
            this.budgetCode = value;
        }

    }


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
    @XmlType(name = "", propOrder = {
        "name"
    })
    public static class User {

        @XmlElement(name = "Name", required = true)
        protected String name;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

    }

}
