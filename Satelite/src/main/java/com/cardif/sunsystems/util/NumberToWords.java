package com.cardif.sunsystems.util;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class NumberToWords {

  public static final Logger LOGGER = Logger.getLogger(NumberToWords.class);

  private static final String[] tensNames = { "", " DIEZ", " VEINTE", " TREINTA", " CUARENTA", " CINCUENTA", " SESENTA", " SETENTA", " OCHENTA", " NOVENTA" };

  private static final String[] numNames = { "", " UNO", " DOS", " TRES", " CUATRO", " CINCO", " SEIS", " SIETE", " OCHO", " NUEVE", " DIEZ", " ONCE", " DOCE", " TRECE", " CATORCE", " QUINCE",
      " DIECISEIS", " DIECISIETE", " DIECIOCHO", " DIECINUEVE" };

  private static final String[] centenas = { "", " CIEN", " DOSCIENTOS", " TRECIENTOS", " CUATROCIENTOS", " QUINIENTOS", " SEISCIENTOS", " SETECIENTOS", " OCHOCIENTOS", " NOVECIENTOS" };

  private static String convertLessThanOneThousand(int number) {
    String soFar;

    if (number % 100 < 20) {
      soFar = numNames[number % 100];
      number /= 100;
    } else {
      soFar = numNames[number % 10];
      number /= 10;

      // soFar = tensNames[number % 10] + soFar;
      if (soFar.equals("")) {
        soFar = tensNames[number % 10] + soFar;
      } else {
        soFar = tensNames[number % 10] + " Y " + soFar;
      }

      number /= 10;
    }
    if (number == 0)
      return soFar;

    else if (soFar.equals("") && number == 1)
      return "CIEN";

    else if (number == 1)
      // return numNames[number] + " CIENTOS" + soFar;
      return "CIENTO" + soFar;

    // return numNames[number] + " CIENTOS" + soFar;
    return centenas[number] + " " + soFar;

  }

  private static boolean hasDecimals(String number) {
    if (number.indexOf(".") != -1 || number.indexOf(",") != -1)
      return true;
    return false;
  }

  private static void serpararDecimales(double numberComplete) {

    DecimalFormat df = new DecimalFormat("#.00");
    String numeroString = df.format(numberComplete);

    numeroString = numeroString.replace(".", ",");

    LOGGER.info("Num string: " + numeroString);
    if (hasDecimals(numeroString) && !numeroString.equals(",00")) {
      // String numeroString = new BigDecimal(numberComplete)+"";

      String[] letras = numeroString.split(",");
      LOGGER.info("Num string: " + numeroString);
      LOGGER.info("Tamaño Letras: " + letras.length);

      number = Long.parseLong(letras[0]);
      decimal = Integer.parseInt(letras[1]);

      LOGGER.info("Entero: " + number);
      LOGGER.info("Decimal: " + decimal);
    } else {
      if (numeroString.equals(",00")) {
        number = Long.parseLong("00");
      } else {
        number = Long.parseLong(numeroString);
      }

    }

  }

  private static long number = 0;
  private static int decimal = 00;

  private static boolean isNegativo = false;

  public static String convert(double numberComplete) {
    // 0 to 999 999 999 999

    number = 0;
    decimal = 00;

    isNegativo = false;

    if (numberComplete < 0) {
      isNegativo = true;
      numberComplete *= -1;
      System.out.println("Negativo cambiado a: " + numberComplete);
    }

    if (numberComplete < 1 && numberComplete > -1 && numberComplete != 0) {
      return "Cero con  " + ((numberComplete + "").substring(2, (numberComplete + "").length())) + "/100";
    }

    serpararDecimales(numberComplete);

    if (number == 0 && decimal == 0) {
      return "Cero con  00/100";
    }

    String snumber = Long.toString(number);

    // pad with "0"
    String mask = "000000000000";
    DecimalFormat df = new DecimalFormat(mask);
    snumber = df.format(number);

    // XXXnnnnnnnnn
    int billions = Integer.parseInt(snumber.substring(0, 3));
    // nnnXXXnnnnnn
    int millions = Integer.parseInt(snumber.substring(3, 6));
    // nnnnnnXXXnnn
    int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
    // nnnnnnnnnXXX
    int thousands = Integer.parseInt(snumber.substring(9, 12));

    String tradBillions;
    switch (billions) {
      case 0:
        tradBillions = "";
        break;
      case 1:
        tradBillions = convertLessThanOneThousand(billions) + " BILLON ";
        break;
      default:
        tradBillions = convertLessThanOneThousand(billions) + " BILLON ";
    }
    String result = tradBillions;

    String tradMillions;
    switch (millions) {
      case 0:
        tradMillions = "";
        break;
      case 1:
        tradMillions = convertLessThanOneThousand(millions) + " MILLON ";
        break;
      default:
        tradMillions = convertLessThanOneThousand(millions) + " MILLON ";
    }
    result = result + tradMillions;

    String tradHundredThousands;
    switch (hundredThousands) {
      case 0:
        tradHundredThousands = "";
        break;
      case 1:
        tradHundredThousands = "MIL ";
        break;
      default:
        tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " MIL ";
    }
    result = result + tradHundredThousands;

    String tradThousand;
    tradThousand = convertLessThanOneThousand(thousands);
    result = result + tradThousand;

    // remove extra spaces!

    if (decimal == 0) {
      result += "  con 0" + decimal + "/100";
    } else {
      result += "  con  " + decimal + "/100";
    }

    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");

  }

  /**
   * testing
   * 
   * @param args
   */
  public static void main(String[] args) {
    /*
     * System.out.println("*** " + NumberToWords.convert(0)); System.out.println("*** " + NumberToWords.convert(1)); System.out.println("*** " +
     * NumberToWords.convert(16)); System.out.println("*** " + NumberToWords.convert(100));
     */

    System.out.println("*** " + NumberToWords.convert(1640));
    System.out.println("*** " + NumberToWords.convert(1643));
    System.out.println("*** " + NumberToWords.convert(25));

    /*
     * ** zero** one** sixteen** one hundred** one hundred eighteen** two hundred** two hundred nineteen** eight hundred** eight hundred one** one
     * thousand three hundred sixteen** one million** two millions** three millions two hundred** seven hundred thousand** nine millions** nine
     * millions one thousand** one hundred twenty three millions four hundred* fifty six thousand seven hundred eighty nine** two billion one hundred
     * forty seven millions* four hundred eighty three thousand six hundred forty seven** three billion ten
     */
  }
}