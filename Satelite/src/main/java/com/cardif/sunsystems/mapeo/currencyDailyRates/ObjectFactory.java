package com.cardif.sunsystems.mapeo.currencyDailyRates;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the tipoCambio package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML
 * content can consist of schema derived interfaces and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: tipoCambio
   * 
   */
  public ObjectFactory() {
  }

  /**
   * Create an instance of {@link SSC }
   * 
   */
  public SSC createSSC() {
    return new SSC();
  }

  /**
   * Create an instance of {@link SSC.Payload.DailyConversionRate }
   * 
   */
  public SSC.Payload.DailyConversionRate createSSCPayloadDailyConversionRate() {
    return new SSC.Payload.DailyConversionRate();
  }

  /**
   * Create an instance of {@link SSC.User }
   * 
   */
  public SSC.User createSSCUser() {
    return new SSC.User();
  }

  /**
   * Create an instance of {@link SSC.Payload }
   * 
   */
  public SSC.Payload createSSCPayload() {
    return new SSC.Payload();
  }

  /**
   * Create an instance of {@link SSC.SunSystemsContext }
   * 
   */
  public SSC.SunSystemsContext createSSCSunSystemsContext() {
    return new SSC.SunSystemsContext();
  }

}
