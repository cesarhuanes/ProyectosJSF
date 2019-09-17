package com.cardif.satelite.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cardif.framework.excepcion.SyncconException;

public class Tools {

  public final static Logger LOGGER = Logger.getLogger(Tools.class);

  public static synchronized boolean getLength(String cell) {
    try {
      boolean res = false;
      int sum = cell.replaceAll("(?i)[^AEIOU]", "").length() + cell.replaceAll("(?i)[^BCDFGHJKLMNPQRSTVWXYZ]", "").length();
      if (sum == 2) {
        return true;
      }
    } catch (Exception e) {
      LOGGER.error(" [ " + e.getMessage() + " ]");
    }
    return false;
  }

  public static BigDecimal getBigDecimal(String format, int numDecimals) throws SyncconException {
    BigDecimal bg1 = null;
    NumberFormat nf = NumberFormat.getInstance(Locale.JAPAN); // FIXME:
    // CAMBIAR A
    // JAPAN EN
    // PROD
    DecimalFormat df = null;
    try {
      format = format.replace(",", "");
      switch (numDecimals) {
        case 2:
          df = new DecimalFormat("0.00");
          bg1 = new BigDecimal(df.format(nf.parse(format)).replace(",", "."))/*
                                                                              * . divide ( new BigDecimal ( "100" ) ) ;
                                                                              */; // FIXME
                                                                                 // Se
          // divide
          // entre 100
          // cuando es
          // GERMAN
          break;
        case 3:
          df = new DecimalFormat("0.000");
          bg1 = new BigDecimal(df.format(nf.parse(format)).replace(",", "."));
          break;
        default:
          bg1 = new BigDecimal("0");
          break;
      }
      return bg1;
    } catch (Exception e) {
      LOGGER.error(" [ " + e.getMessage() + " ]");
    }
    return bg1;
  }

  public static BigDecimal getBigDecimal1(String format, int numDecimals) throws SyncconException {
    BigDecimal bg1 = null;
    NumberFormat nf = NumberFormat.getInstance(Locale.JAPAN); // FIXME:
    // CAMBIAR A
    // JAPAN EN
    // PROD
    DecimalFormat df = null;
    try {
      // format = format.replace(",", "");
      LOGGER.info(format);
      switch (numDecimals) {
        case 2:
          df = new DecimalFormat("0.00");

          bg1 = new BigDecimal(df.format(nf.parse(format)).replace(",", "."))/*
                                                                              * . divide ( new BigDecimal ( "100" ) ) ;
                                                                              */; // FIXME
                                                                                 // Se
          // divide
          // entre 100
          // cuando es
          // GERMAN
          break;
        case 3:
          df = new DecimalFormat("0.000");
          bg1 = new BigDecimal(df.format(nf.parse(format)).replace(",", "."));
          break;
        default:
          bg1 = new BigDecimal("0");
          break;
      }
      return bg1;
    } catch (Exception e) {
      LOGGER.error(" [ " + e.getMessage() + " ]");
    }
    return bg1;
  }
}
