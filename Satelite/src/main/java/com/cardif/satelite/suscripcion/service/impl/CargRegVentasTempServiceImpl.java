package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CargRegVentasTemp;
import com.cardif.satelite.suscripcion.dao.CargRegVentasTempMapper;
import com.cardif.satelite.suscripcion.service.CargRegVentasTempService;

/**
 * 
 * @author JOANHUVE
 * 
 */
@Service("cargRegVentasTempService")
public class CargRegVentasTempServiceImpl implements CargRegVentasTempService {

  public static final Logger logger = Logger.getLogger(CargRegVentasTempServiceImpl.class);

  @Autowired
  private CargRegVentasTempMapper cargRegVentasTempMapper;

  @Override
  public void insertarDatosExcel(File excelFile) throws SyncconException {
    logger.info("Inicio");
    try {
      if (logger.isDebugEnabled())
        logger.debug("input [" + excelFile + "]");

      OPCPackage container;
      container = OPCPackage.open(excelFile.getAbsolutePath());
      ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(container);
      XSSFReader xssfReader = new XSSFReader(container);
      StylesTable styles = xssfReader.getStylesTable();
      InputStream stream = xssfReader.getSheet("rId1");
      procesarCargRegVentasTemp(styles, strings, stream);
      stream.close();

      if (logger.isDebugEnabled())
        logger.debug("Output [Ok]");

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    logger.info("[ Fin ]");
  }

  private void procesarCargRegVentasTemp(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws IOException, SAXException {
    logger.info("[ Inicio proceso carga reg ventas ]");
    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();

    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      // final NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

      // FIXME:
      // CAMBIAR
      // A
      // JAPAN
      // EN
      // PROD
      // final DecimalFormat df = new DecimalFormat("0.00");
      final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        CargRegVentasTemp cargRegVentasTemp;
        boolean procesar = false;

        // ESPEZAR DESDE LA FILA
        @Override
        public void startRow(int rowNum) {
          // logger.info("[numero de fila : " + rowNum +
          // " ]");
          try {
            if (rowNum == 1) {
              procesar = true;
            }
            if (procesar) {
              cargRegVentasTemp = new CargRegVentasTemp();
            }
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
        }

        // AGREGAR AL FINAL
        @Override
        public void endRow() {
          if (procesar && cargRegVentasTemp.getFechaemision() != null) {
            cargRegVentasTempMapper.insert(cargRegVentasTemp);
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            String column = "";
            if (procesar) {
              if (!getLength(cellReference)) {
                column = cellReference.substring(0, 1);
                if (column.equals("A")) {
                  cargRegVentasTemp.setCorrelativoRegistro(Long.parseLong(formattedValue));
                } else if (column.equals("B")) {
                  cargRegVentasTemp.setFechaemision(sdf.parse(formattedValue));
                } else if (column.equals("C")) {
                  cargRegVentasTemp.setFechavencimiento(sdf.parse(formattedValue));
                } else if (column.equals("D")) {
                  cargRegVentasTemp.setTipocomprobante(Long.parseLong(formattedValue));
                } else if (column.equals("E")) {
                  cargRegVentasTemp.setNroSerie(Long.parseLong(formattedValue));
                } else if (column.equals("F")) {
                  cargRegVentasTemp.setCorrelativoSerie(Long.parseLong(formattedValue));
                } else if (column.equals("G")) {
                  cargRegVentasTemp.setTipodoccliente(formattedValue);
                } else if (column.equals("H")) {
                  cargRegVentasTemp.setNumerodoccliente(formattedValue);
                } else if (column.equals("I")) {
                  cargRegVentasTemp.setNombresRazonSocial(formattedValue);
                }
                /********************************************/
                else if (column.equals("J")) {
                  cargRegVentasTemp.setValorFactExport(getBigDecimal(formattedValue, 2));
                } else if (column.equals("K")) {
                  cargRegVentasTemp.setBaseImponible(getBigDecimal(formattedValue, 2));
                } else if (column.equals("L")) {
                  cargRegVentasTemp.setImporteExonerado(getBigDecimal(formattedValue, 2));
                } else if (column.equals("M")) {
                  cargRegVentasTemp.setImporteInafecto(getBigDecimal(formattedValue, 2));
                } else if (column.equals("N")) {
                  cargRegVentasTemp.setIsc(getBigDecimal(formattedValue, 2));
                } else if (column.equals("O")) {
                  cargRegVentasTemp.setIgv(getBigDecimal(formattedValue, 2));
                } else if (column.equals("P")) {
                  cargRegVentasTemp.setOtrosImportes(getBigDecimal(formattedValue, 2));
                } else if (column.equals("Q")) {
                  cargRegVentasTemp.setImporteTotal(getBigDecimal(formattedValue, 2));
                } else if (column.equals("R")) {
                  cargRegVentasTemp.setTipoCambio(getBigDecimal(formattedValue, 3));
                }
                /**********************************************/
                else if (column.equals("S")) {
                  cargRegVentasTemp.setFechaEmisionRef(sdf.parse(formattedValue));
                } else if (column.equals("T")) {
                  cargRegVentasTemp.setTipoComprobanteRef(Long.parseLong(formattedValue));
                } else if (column.equals("U")) {
                  cargRegVentasTemp.setSerieComprobanteRef(Long.parseLong(formattedValue));
                } else if (column.equals("V")) {
                  cargRegVentasTemp.setCorrelativoSerieOrig(Long.parseLong(formattedValue));
                } else if (column.equals("W")) {
                  cargRegVentasTemp.setModificable(formattedValue);
                } else if (column.equals("X")) {
                  cargRegVentasTemp.setOpenitem(formattedValue);
                } else if (column.equals("Y")) {
                  cargRegVentasTemp.setOpenitemRef(formattedValue);
                } else if (column.equals("Z")) {
                  cargRegVentasTemp.setNumeroPoliza((formattedValue));
                }
              } else {
                column = cellReference.substring(0, 2);
                if (column.equals("AA")) {
                  cargRegVentasTemp.setProducto(formattedValue);
                } else if (column.equals("AB")) {
                  cargRegVentasTemp.setEstado(formattedValue);
                } else if (column.equals("AC")) {
                  cargRegVentasTemp.setObservaciones(formattedValue);
                } else if (column.equals("AD")) {
                  cargRegVentasTemp.setCorelativoFinal(Long.parseLong(formattedValue));
                } else if (column.equals("AE")) {
                  cargRegVentasTemp.setDireccion(formattedValue);
                } else if (column.equals("AF")) {
                  cargRegVentasTemp.setDistrito(formattedValue);
                } else if (column.equals("AG")) {
                  cargRegVentasTemp.setProvincia(formattedValue);
                } else if (column.equals("AH")) {
                  cargRegVentasTemp.setDepartamento(formattedValue);
                }
              }
            }
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }
      }, false);

      sheetParser.setContentHandler(handler);
      sheetParser.parse(sheetSource);

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("[ SAX parser appears to be broken - " + e.getMessage() + " ]");
    }
    logger.info("[ Fin ]");
  }

  /**
   * @JHURTADO
   * @param format
   *          * decimal
   * @return
   * @throws SyncconException
   */
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
      logger.error(" [ " + e.getMessage() + " ]");
    }
    return bg1;
  }

  /**
   * @JHURTADO
   * @param cell
   * @return
   */
  private static synchronized boolean getLength(String cell) {
    boolean res = false;
    int sum = cell.replaceAll("(?i)[^AEIOU]", "").length() + cell.replaceAll("(?i)[^BCDFGHJKLMNPQRSTVWXYZ]", "").length();
    if (sum == 2) {
      res = true;
    }
    return res;
  }

  @Override
  public void deleteRows() throws SyncconException {
    logger.info("[ Inicio ]");
    try {
      int mens = cargRegVentasTempMapper.deleteRows();
      logger.info("Cantidad de Registros eliminados : [ " + mens + " ]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    logger.info("[ Fin ]");
  }

}
