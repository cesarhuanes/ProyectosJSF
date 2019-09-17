package com.cardif.satelite.contabilidad.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.contabilidad.controller.CargarLibrosController;
import com.cardif.satelite.contabilidad.dao.CabLibroMapper;
import com.cardif.satelite.contabilidad.service.LibroElectronicoService;
import com.cardif.satelite.model.CabLibro;
import com.cardif.satelite.tesoreria.bean.DetRegComprasBean;
import com.cardif.satelite.tesoreria.bean.DetRegVentasBean;
import com.cardif.satelite.tesoreria.bean.LibroDiarioBean;
import com.cardif.satelite.tesoreria.bean.LibroMayorBean;

@Service("libroElectronicoService")
public class LibroElectronicoServiceImpl implements LibroElectronicoService {

  public static final Logger log = Logger.getLogger(CargarLibrosController.class);
  private static final Locale LANGUAGE = Locale.JAPAN;// para Produccion
  // cambiar a JAPAN y
  // local cambiar German
  @Autowired
  private CabLibroMapper cabLibroMapper;

  @Override
  public void insertar(CabLibro cabLibro) throws SyncconException {
    log.info("inicio");

    try {

      if (log.isDebugEnabled())
        log.debug("input [" + BeanUtils.describe(cabLibro).toString() + "]");
      cabLibro.setFecCreacion(new Date(System.currentTimeMillis()));
      cabLibroMapper.insert(cabLibro);

      if (log.isDebugEnabled())
        log.debug("Output [ok]");
    }

    catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);

    }
    log.info("Fin");
  }

  @Override
  public void transformarExcel(CabLibro cabLibro, File txtFile, File excelFile) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("input [" + excelFile + "]");

      OPCPackage container;
      container = OPCPackage.open(excelFile.getAbsolutePath());
      ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(container);
      XSSFReader xssfReader = new XSSFReader(container);
      StylesTable styles = xssfReader.getStylesTable();
      InputStream stream = xssfReader.getSheet("rId1");
      // InputStream name= xssfReader.getWorkbookData();

      if (cabLibro.getTipLibro().equals(Constantes.LIBRO_DIARIO)) {
        procesarLibroDiario(txtFile, cabLibro, styles, strings, stream);
      } else if (cabLibro.getTipLibro().equals(Constantes.LIBRO_MAYOR)) {
        procesarLibroMayor(txtFile, cabLibro, styles, strings, stream);
      } else if (cabLibro.getTipLibro().equals(Constantes.REG_COMPRAS)) {
        stream = xssfReader.getSheet("rId2");
        // stream = xssfReader.getSheet("rId3");
        procesarRegistroCompras(txtFile, cabLibro, styles, strings, stream);
      } else if (cabLibro.getTipLibro().equals(Constantes.REG_VENTAS)) {
        if (cabLibro.getCodCia().equals(Constantes.COD_CARDIF_SEGUROS)) {
          procesarRegVentasSeguros(txtFile, cabLibro, styles, strings, stream);
        } else {
          // stream = xssfReader.getSheet("rId4");
          stream = xssfReader.getSheet("rId3");
          procesarRegVentasServicios(txtFile, cabLibro, styles, strings, stream);
        }
      }
      stream.close();

      if (log.isDebugEnabled())
        log.debug("Output [Ok]");
    }

    catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
  }

  private void procesarLibroDiario(File file, CabLibro cabLibro, StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws IOException, SAXException {

    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();
      // Creo el archivo de salida
      final FileWriter pw = new FileWriter(file, true);
      final BufferedWriter bw = new BufferedWriter(pw);
      final int per = cabLibro.getPeriodo();
      final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
      final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      final NumberFormat nf = NumberFormat.getInstance(LANGUAGE); // JCAT
      // CAMBIAR
      // A
      // JAPAN
      // EN
      // PROD
      DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
      simbolo.setDecimalSeparator('.');
      simbolo.setGroupingSeparator(',');
      // final DecimalFormat df = new DecimalFormat("###,###.00",simbolo);
      // final DecimalFormat df = new DecimalFormat("0.00");
      final DecimalFormat df = new DecimalFormat("0.00", simbolo);

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        boolean procesar = false;
        LibroDiarioBean detalle;

        @Override
        public void startRow(int rowNum) {
          try {
            if (rowNum == 11) {// 13

              procesar = true;
            }

            if (procesar) {
              detalle = new LibroDiarioBean();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void endRow() {
          try {
            if (procesar) {
              if (detalle.getCodOperacion() != null && detalle.getFecOperacion() != null) {
                bw.append(/* 1 */per + "00|" + // 20121100
                /* 2 */StringUtils.left(detalle.getCodOperacion(), 40) + "|" + // 106383
                /* 3 */((detalle.getNumCorrAsientoCtble() == null) ? "M" + detalle.getCodOperacion() : StringUtils.left(detalle.getNumCorrAsientoCtble(), 10)) + "|" + // Nuevo
                /* 4 */StringUtils.left(((detalle.getCodPlanCtas().length() == 1) ? "0" + detalle.getCodPlanCtas() : detalle.getCodPlanCtas()), 2) + "|" + // Nuevo
                                                                                                                                                           // cod
                                                                                                                                                           // plan
                                                                                                                                                           // de
                                                                                                                                                           // cuentas
                /* 5 */StringUtils.left(detalle.getCodCuentaContable(), 24) + "|" + // cod
                                                                                    // cuenta
                                                                                    // contable
                                                                                    // 4211720102
                /* 6 */sdf1.format(detalle.getFecOperacion()) + "|" + // 02/11/2012
                /* 7 */StringUtils.left(detalle.getDesOperacion(), 100) + "|" + // glosa
                                                                                // de
                                                                                // descripcion
                                                                                // STRO
                                                                                // VIDA
                                                                                // SEPELIO...
                /* 8 */((detalle.getDebe() == null) ? Constantes.MONTO_DEFAULT : detalle.getDebe()) + "|" + // 23,00
                /* 9 */((detalle.getHaber() == null) ? Constantes.MONTO_DEFAULT : detalle.getHaber()) + "|" + // 0.00
                /* 10 */((detalle.getNumCorrelativoRegVentas() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCorrelativoRegVentas(), 40)) + "|" + // nuevo
                /* 11 */((detalle.getNumCorrelativoRegCompras() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCorrelativoRegCompras(), 40)) + "|" + // nuevo
                /* 12 */((detalle.getNumCorrelativoRegConsig() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCorrelativoRegConsig(), 40)) + "|" + // nuevo
                /* 13 */((detalle.getEstadoOperacion() == null) ? "1" : detalle.getEstadoOperacion()) + "|" // 1
                                                                                                            // -
                                                                                                            // indica
                                                                                                            // estado
                );
                bw.newLine();
              }
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            if (procesar) {

              String fila1 = cellReference.substring(0, 1);

              if (fila1.equals("E")) {
                detalle.setCodOperacion(formattedValue);
              } else if (fila1.equals("F")) {
                detalle.setNumCorrAsientoCtble(formattedValue);
              } else if (fila1.equals("G")) {
                detalle.setCodPlanCtas(formattedValue);
              } // plan de cuentas
              else if (fila1.equals("H")) {
                detalle.setCodCuentaContable(formattedValue);
              } else if (fila1.equals("I")) {
                detalle.setFecOperacion(sdf.parse(formattedValue));
              } else if (fila1.equals("J")) {
                detalle.setDesOperacion(formattedValue);
              } else if (fila1.equals("K")) {
                detalle.setDebe(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("L")) {
                detalle.setHaber(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("M")) {
                detalle.setNumCorrelativoRegVentas(formattedValue);
              } else if (fila1.equals("N")) {
                detalle.setNumCorrelativoRegCompras(formattedValue);
              } else if (fila1.equals("O")) {
                detalle.setNumCorrelativoRegConsig(formattedValue);
              } else if (fila1.equals("P")) {
                detalle.setEstadoOperacion(formattedValue);
              }
              /*
               * else if (fila1.equals("F")) { detalle.setFecOperacion (sdf.parse(formattedValue)); //log.info("cell: " + cellReference+
               * " detalle: -->" + sdf.parse(formattedValue) + "sin format: " + formattedValue); } else if (fila1.equals("G")) {
               * detalle.setDesOperacion(formattedValue); } else if (fila1.equals("H")) { detalle.setCodLibro(formattedValue); } else if
               * (fila1.equals("I")) { detalle.setNumCorrelativo (formattedValue); } else if (fila1.equals("J")) { detalle.setNumDocSustentatorio
               * (formattedValue); } else if (fila1.equals("K")) { detalle.setCodCuentaContable (formattedValue); } else if (fila1.equals("L")) {
               * detalle.setDesCuentaContable (formattedValue); } else if (fila1.equals("M")) { detalle.setDebe(df.format (nf.parse(formattedValue)));
               * } else if (fila1.equals("N")) { detalle.setHaber(df. format(nf.parse(formattedValue))); } else if (fila1.equals("Q")) {
               * detalle.setEstadoOperacion (formattedValue); }
               */

              // log.info("detalle: FechaOperacion -->" );
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

      }, false);

      sheetParser.setContentHandler(handler);
      sheetParser.parse(sheetSource);

      bw.flush();
      bw.close();

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
    }
  }

  private void procesarLibroMayor(File file, CabLibro cabLibro, StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws IOException, SAXException {

    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      // Creo el archivo de salida
      final FileWriter pw = new FileWriter(file, true);
      final BufferedWriter bw = new BufferedWriter(pw);

      final int per = cabLibro.getPeriodo();
      final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
      final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      final NumberFormat nf = NumberFormat.getInstance(LANGUAGE); // JCAT
      // CAMBIAR
      // A
      // JAPAN
      // EN
      // PROD
      DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
      simbolo.setDecimalSeparator('.');
      simbolo.setGroupingSeparator(',');
      // final DecimalFormat df = new DecimalFormat("###,###.00",simbolo);
      final DecimalFormat df = new DecimalFormat("0.00", simbolo);

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        boolean procesar = false;
        LibroMayorBean detalle;

        @Override
        public void startRow(int rowNum) {
          try {
            if (rowNum == 11) {
              procesar = true;
            }

            if (procesar) {

              /*
               * if (detalle == null) { detalle = new LibroMayorBean(); } else { if (detalle.getDesOperacion() != null &&
               * detalle.getDesOperacion().trim().equals( "Total Cuenta")) { detalle = new LibroMayorBean(); } }
               */
              detalle = new LibroMayorBean();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void endRow() {
          try {
            if (procesar) {
              if (detalle.getCodOperacion() != null) {
                if (detalle.getFecOperacion() != null && !detalle.getDesOperacion().trim().equals("Total Cuenta")) {
                  bw.append(/* 1 */per + "00|" + // periodo
                  /* 2 */StringUtils.left(detalle.getNumCorrelativo(), 40) + "|" + // cod
                                                                                   // unico
                                                                                   // de
                                                                                   // operacion
                  /* 3 */((detalle.getNumCorrAsientoCtble() == null) ? "M" + detalle.getNumCorrelativo() : StringUtils.left(detalle.getNumCorrAsientoCtble(), 8)) + "|" + // Nuevo-->
                                                                                                                                                                          // nro
                                                                                                                                                                          // correlativo
                                                                                                                                                                          // de
                                                                                                                                                                          // asiento
                                                                                                                                                                          // contable
                  /* 4 */StringUtils.left(((detalle.getCodPlanCtas().length() == 1) ? "0" + detalle.getCodPlanCtas() : detalle.getCodPlanCtas()), 2) + "|" + // Nuevo
                                                                                                                                                             // cod
                                                                                                                                                             // plan
                                                                                                                                                             // de
                                                                                                                                                             // cuentas
                  /* 5 */StringUtils.left(detalle.getCodOperacion(), 24) + "|" + // codigo
                                                                                 // de
                                                                                 // la
                                                                                 // cuenta
                                                                                 // contable???
                  /* 6 */sdf1.format(detalle.getFecOperacion()) + "|" + //
                  /* 7 */StringUtils.left(detalle.getDesOperacion(), 100) + "|" + // glosa
                                                                                  // de
                                                                                  // descripcion
                  /* 8 */((detalle.getDebe() == null) ? Constantes.MONTO_DEFAULT : detalle.getDebe()) + "|" +
                  /* 9 */((detalle.getHaber() == null) ? Constantes.MONTO_DEFAULT : detalle.getHaber()) + "|" +
                  // Nuevo: nro correlativo ventas
                  /* 10 */((detalle.getNumCorrelativoVentas() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCorrelativoVentas(), 40)) + "|" +
                  /* 11 */((detalle.getNumCorrelativoCompras() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCorrelativoCompras(), 40)) + "|" + // Nuevo
                  /* 12 */((detalle.getNumCorrelativoConsig() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCorrelativoConsig(), 40)) + "|" + // Nuevo
                  /* 13 */((detalle.getEstadoOperacion() == null) ? "1" : detalle.getEstadoOperacion()) + "|"//
                  );
                  bw.newLine();

                  // Se limpia el debe y el haber
                  detalle.setDebe(null);
                  detalle.setHaber(null);
                }
              }
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            if (procesar) {

              String fila1 = cellReference.substring(0, 1);
              /*
               * if (fila1.equals("E")) { if(formattedValue.indexOf("/") > 0) { detalle .setFecOperacion(sdf.parse(formattedValue )); } else {
               * detalle.setCodOperacion(formattedValue); detalle.setFecOperacion(null); } } else if (fila1.equals("F")) { detalle.setNumCorrelativo
               * (formattedValue); } else if (fila1.equals("G")) { detalle.setDesOperacion(formattedValue); } else if (fila1.equals("H")) {
               * detalle.setDebe (df.format(nf.parse(formattedValue))); } else if (fila1.equals("I")) { detalle.setHaber
               * (df.format(nf.parse(formattedValue))); } else if (fila1.equals("M")) { detalle.setEstadoOperacion (formattedValue); }
               */

              if (fila1.equals("E")) {
                detalle.setNumCorrelativo(formattedValue);
              } // o codigo unico de operacion
              else if (fila1.equals("F")) {
                detalle.setNumCorrAsientoCtble(formattedValue);
              } else if (fila1.equals("G")) {
                detalle.setCodPlanCtas(formattedValue);
              } else if (fila1.equals("H")) {
                detalle.setCodOperacion(formattedValue);
              } // codigo de la cuenta contable
              else if (fila1.equals("I")) {
                detalle.setFecOperacion(sdf.parse(formattedValue));
              } //
              else if (fila1.equals("J")) {
                detalle.setDesOperacion(formattedValue);
              } else if (fila1.equals("K")) {
                detalle.setDebe(df.format(nf.parseObject(formattedValue)));
              } else if (fila1.equals("L")) {
                detalle.setHaber(df.format(nf.parseObject(formattedValue)));
              } else if (fila1.equals("M")) {
                detalle.setNumCorrelativoVentas(formattedValue);
              } else if (fila1.equals("N")) {
                detalle.setNumCorrelativoCompras(formattedValue);
              } else if (fila1.equals("O")) {
                detalle.setNumCorrelativoConsig(formattedValue);
              } // nro correlativo consignaciones
              else if (fila1.equals("P")) {
                detalle.setEstadoOperacion(formattedValue);
              }
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

      }, false// means result instead of formula
      );

      sheetParser.setContentHandler(handler);
      sheetParser.parse(sheetSource);

      bw.flush();
      bw.close();

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
    }
  }

  private void procesarRegistroCompras(File file, CabLibro cabLibro, StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws IOException, SAXException {
    log.info("Inicio Compras");
    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      // Creo el archivo de salida
      final FileWriter pw = new FileWriter(file, true);
      final BufferedWriter bw = new BufferedWriter(pw);

      final int per = cabLibro.getPeriodo();
      final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
      final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      final NumberFormat nf = NumberFormat.getInstance(LANGUAGE);
      DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
      simbolo.setDecimalSeparator('.');
      simbolo.setGroupingSeparator(',');
      // final DecimalFormat df = new DecimalFormat("###,###.00",simbolo);
      final DecimalFormat df = new DecimalFormat("0.00", simbolo);
      final DecimalFormat dft = new DecimalFormat("0.000");

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        boolean procesar = false;
        DetRegComprasBean detalle;

        @Override
        public void startRow(int rowNum) {
          try {
            if (rowNum == 10) {// if (rowNum == 15 )//16
              procesar = true;
            }
            if (procesar) {
              detalle = new DetRegComprasBean();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void endRow() {
          try {
            if (procesar) {
              if (detalle.getCodOperacion() != null) {
                bw.append(/* 1 */per + "00|" +
                /* 2 */StringUtils.left(detalle.getCodOperacion(), 40) + "|" +
                /* 3 */((detalle.getNumCorrAsientoCtble() == null) ? "M" + detalle.getCodOperacion() : StringUtils.left(detalle.getNumCorrAsientoCtble(), 10)) + "|" + // Nuevo
                /* 4 */((detalle.getFecEmision() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecEmision())) + "|" +
                /* 5 */((detalle.getFecVencimiento() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecVencimiento())) + "|" +
                /* 6 */((detalle.getTipCompPago() == null) ? Constantes.TIP_COMP_DEFAULT : detalle.getTipCompPago() + "|" +
                /* 7 */((detalle.getCodDepPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(StringUtils.leftPad(detalle.getCodDepPago(), 4, '0'), 20))) + "|" +
                /* 8 */((detalle.getAnioEmiPago() == null) ? Constantes.CANTIDAD_DEFAULT : detalle.getAnioEmiPago()) + "|" +
                /* 9 */StringUtils.left(detalle.getNumCompPago1(), 24) + "|" + // 20
                // Este valor no se tiene -- cambiar este valor??????????? long 24
                /* 10 */((detalle.getNumCompFinal() == null) ? "0" : detalle.getNumCompFinal()) + "|" +
                /* 10 */// "0" + "|" +
                /* 11 */((detalle.getTipDocProveedor() == null) ? Constantes.TEXTO_DEFAULT : Integer.parseInt(detalle.getTipDocProveedor())) + "|" +
                /* 12 */((detalle.getNumDocProveedor() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumDocProveedor(), 15)) + "|" +
                /* 13 */((detalle.getNomProveedor() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNomProveedor(), 100)) + "|" + // 60
                /* 14 */((detalle.getBaseImponible1() == null) ? Constantes.MONTO_DEFAULT : detalle.getBaseImponible1()) + "|" +
                /* 15 */((detalle.getIgv1() == null) ? Constantes.MONTO_DEFAULT : detalle.getIgv1()) + "|" +
                /* 16 */((detalle.getBaseImponible2() == null) ? Constantes.MONTO_DEFAULT : detalle.getBaseImponible2()) + "|" +
                /* 17 */((detalle.getIgv2() == null) ? Constantes.MONTO_DEFAULT : detalle.getIgv2()) + "|" +
                /* 18 */((detalle.getBaseImponible3() == null) ? Constantes.MONTO_DEFAULT : detalle.getBaseImponible3()) + "|" +
                /* 19 */((detalle.getIgv3() == null) ? Constantes.MONTO_DEFAULT : detalle.getIgv3()) + "|" +
                /* 20 */((detalle.getValAdqNogravadas() == null) ? Constantes.MONTO_DEFAULT : detalle.getValAdqNogravadas()) + "|" +
                /* 21 */((detalle.getIsc() == null) ? Constantes.MONTO_DEFAULT : detalle.getIsc()) + "|" +
                /* 22 */((detalle.getOtrosTributos() == null) ? Constantes.MONTO_DEFAULT : detalle.getOtrosTributos()) + "|" +
                /* 23 */((detalle.getImpTotal() == null) ? Constantes.MONTO_DEFAULT : detalle.getImpTotal()) + "|" +
                /* 24 */((detalle.getTipCambio() == null) ? Constantes.TIP_CAMBIO_DEFAULT : detalle.getTipCambio()) + "|" +
                /* 25 */((detalle.getFecRefPago() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecRefPago())) + "|" +
                /* 26 */((detalle.getTipRefPago() == null) ? Constantes.TIP_COMP_DEFAULT : detalle.getTipRefPago()) + "|" +
                /* 27 */((detalle.getSerRefPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(StringUtils.leftPad(detalle.getSerRefPago(), 4, '0'), 20)) + "|" +
                /* 28 */((detalle.getCodDepAduaneraDUA() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getCodDepAduaneraDUA(), 3)) + "|" + // Nuevo
                /* 29 */((detalle.getNumCompRefPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCompRefPago(), 24)) + "|" + // 20
                /* 30 */((detalle.getNumCompPago2() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCompPago2(), 24)) + "|" + // 20
                /* 31 */((detalle.getFecEmiDeposito() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecEmiDeposito())) + "|" +
                /* 32 */((detalle.getNumConstDeposito() == null) ? Constantes.CANTIDAD_DEFAULT : StringUtils.left(detalle.getNumConstDeposito(), 24)) + "|" + // 20
                /* 33 */((detalle.getMcaCompRetencion() == null) ? Constantes.CANTIDAD_DEFAULT : detalle.getMcaCompRetencion()) + "|" +
                /* 34 */((detalle.getEstadoOportunidad() == null) ? "1" : detalle.getEstadoOportunidad()) + "|");
                bw.newLine();
              }
            }

          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            if (procesar) {

              String fila1 = cellReference.substring(0, 1);
              /*
               * if (fila1.equals("B")) { detalle.setCodOperacion(formattedValue); }//campo2
               * 
               * else if (fila1.equals("C")) { detalle.setFecEmision (sdf.parse(formattedValue)); } else if (fila1.equals("D")) {
               * detalle.setFecVencimiento (sdf.parse(formattedValue)); } else if (fila1.equals("E")) { detalle.setTipCompPago(formattedValue); } else
               * if (fila1.equals("F")) { detalle.setCodDepPago(formattedValue); } else if (fila1.equals("G")) { detalle.setAnioEmiPago
               * (Short.parseShort(formattedValue)); } else if (fila1.equals("H")) { detalle.setNumCompPago1(formattedValue); } else if
               * (fila1.equals("I")) { detalle.setTipDocProveedor (formattedValue); } else if (fila1.equals("J")) { detalle.setNumDocProveedor
               * (formattedValue); } else if (fila1.equals("K")) { detalle.setNomProveedor(formattedValue); } else if (fila1.equals("L")) {
               * detalle.setBaseImponible1 (df.format(nf.parse(formattedValue))); } else if (fila1.equals("M")) { detalle.setIgv1
               * (df.format(nf.parse(formattedValue))); } else if (fila1.equals("N")) { detalle.setBaseImponible2
               * (df.format(nf.parse(formattedValue))); } else if (fila1.equals("O")) { detalle.setIgv2 (df.format(nf.parse(formattedValue))); } else
               * if (fila1.equals("P")) { detalle.setBaseImponible3 (df.format(nf.parse(formattedValue))); } else if (fila1.equals("Q")) {
               * detalle.setIgv3 (df.format(nf.parse(formattedValue))); } else if (fila1.equals("R")) { detalle.setValAdqNogravadas
               * (df.format(nf.parse(formattedValue))); } else if (fila1.equals("S")) { detalle.setIsc (df.format(nf.parse(formattedValue))); } else
               * if (fila1.equals("T")) { detalle.setOtrosTributos (df.format(nf.parse(formattedValue))); } else if (fila1.equals("U")) {
               * detalle.setImpTotal (df.format(nf.parse(formattedValue))); } else if (fila1.equals("V")) { detalle.setNumCompPago2
               * (df.format(nf.parse(formattedValue)));} else if (fila1.equals("W")) { detalle.setNumConstDeposito
               * (df.format(nf.parse(formattedValue))); } else if (fila1.equals("X")) { detalle.setFecEmiDeposito (sdf.parse(formattedValue)); } else
               * if (fila1.equals("Y")) { detalle.setTipCambio (dft.format(nf.parse(formattedValue))); } else if (fila1.equals("Z")) {
               * detalle.setFecRefPago (sdf.parse(formattedValue)); } //else if (fila1.equals("AF")) {detalle.setNumCorrAstoCtble (formattedValue);}
               * else { String fila2 = cellReference.substring(0, 2); if (fila2.equals("AA")) { detalle.setTipRefPago(formattedValue); } else if
               * (fila2.equals("AB")) { detalle.setSerRefPago(formattedValue); } else if (fila2.equals("AC")) { detalle.setNumCompRefPago
               * (formattedValue); } else if (fila2.equals("AD")) { detalle.setMcaCompRetencion (formattedValue); } else if (fila2.equals("AE")) {
               * detalle.setEstadoOportunidad (formattedValue); } else if (fila2.equals("AF")) {detalle.setNumCorrAsientoCtble (formattedValue);} }
               */
              if (fila1.equals("B")) {
                detalle.setCodOperacion(formattedValue);
              } else if (fila1.equals("C")) {
                detalle.setNumCorrAsientoCtble(formattedValue);
              } // c3
              else if (fila1.equals("D")) {
                detalle.setFecEmision(sdf.parse(formattedValue));
              } // c4
              else if (fila1.equals("E")) {
                detalle.setFecVencimiento(sdf.parse(formattedValue));
              } // c5
              else if (fila1.equals("F")) {
                detalle.setTipCompPago(formattedValue);
              } // c6
              else if (fila1.equals("G")) {
                detalle.setCodDepPago(formattedValue);
              } // c7
              else if (fila1.equals("H")) {
                detalle.setAnioEmiPago(Short.parseShort(formattedValue));
              } // c8
              else if (fila1.equals("I")) {
                detalle.setNumCompPago1(formattedValue);
              } // c9
              else if (fila1.equals("J")) {
              } // c10
              else if (fila1.equals("K")) {
                detalle.setTipDocProveedor(formattedValue);
              } // c11
              else if (fila1.equals("L")) {
                detalle.setNumDocProveedor(formattedValue);
              } // c12
              else if (fila1.equals("M")) {
                detalle.setNomProveedor(formattedValue);
              } // c13 Informacion del Proveedor
              else if (fila1.equals("N")) {
                detalle.setBaseImponible1(df.format(nf.parse(formattedValue)));
              } // c14
              else if (fila1.equals("O")) {
                detalle.setIgv1(df.format(nf.parse(formattedValue)));
              } // c15
              else if (fila1.equals("P")) {
                detalle.setBaseImponible2(df.format(nf.parse(formattedValue)));
              } // c16
              else if (fila1.equals("Q")) {
                detalle.setIgv2(df.format(nf.parse(formattedValue)));
              } // c17
              else if (fila1.equals("R")) {
                detalle.setBaseImponible3(df.format(nf.parse(formattedValue)));
              } // c18
              else if (fila1.equals("S")) {
                detalle.setIgv3(df.format(nf.parse(formattedValue)));
              } // c19
              else if (fila1.equals("T")) {
                detalle.setValAdqNogravadas(df.format(nf.parse(formattedValue)));
              } // c20
              else if (fila1.equals("U")) {
                detalle.setIsc(df.format(nf.parse(formattedValue)));
              } // c21
              else if (fila1.equals("V")) {
                detalle.setOtrosTributos(df.format(nf.parse(formattedValue)));
              } // c22
              else if (fila1.equals("W")) {
                detalle.setImpTotal(df.format(nf.parse(formattedValue)));
              } // c23
              else if (fila1.equals("X")) {
                detalle.setTipCambio(dft.format(nf.parse(formattedValue)));
              } // c24
              else if (fila1.equals("Y")) {
                detalle.setFecRefPago(sdf.parse(formattedValue));
              } // c25
              else if (fila1.equals("Z")) {
                detalle.setTipRefPago(formattedValue);
              } // c26
              else {
                String fila2 = cellReference.substring(0, 2);
                if (fila2.equals("AA")) {
                  detalle.setSerRefPago(formattedValue);
                } // c27
                else if (fila2.equals("AB")) {
                  detalle.setCodDepAduaneraDUA(formattedValue);
                } // c28
                else if (fila2.equals("AC")) {
                  detalle.setNumCompRefPago(formattedValue);
                } // c29
                else if (fila2.equals("AD")) {
                  detalle.setNumCompPago2(formattedValue);
                } // c30
                else if (fila2.equals("AE")) {
                  detalle.setFecEmiDeposito(sdf.parse(formattedValue));
                } // c31
                else if (fila2.equals("AF")) {
                  detalle.setNumConstDeposito(df.format(nf.parse(formattedValue)));
                } // c32
                else if (fila2.equals("AG")) {
                  detalle.setMcaCompRetencion(formattedValue);
                } // c33
                else if (fila2.equals("AH")) {
                  detalle.setEstadoOportunidad(formattedValue);
                } // c34
              }
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }

        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

      }, false);

      sheetParser.setContentHandler(handler);
      sheetParser.parse(sheetSource);

      bw.flush();
      bw.close();

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
    }
  }

  private void procesarRegVentasSeguros(File file, CabLibro cabLibro, StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws IOException, SAXException {

    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      final FileWriter pw = new FileWriter(file, true);
      final BufferedWriter bw = new BufferedWriter(pw);

      final int per = cabLibro.getPeriodo();
      final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      final NumberFormat nf = NumberFormat.getInstance(LANGUAGE);
      final DecimalFormat df = new DecimalFormat("0.00");
      final DecimalFormat dft = new DecimalFormat("0.000");

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        boolean procesar = false;
        DetRegVentasBean detalle;

        @Override
        public void startRow(int rowNum) {
          try {

            if (rowNum == 17) {
              procesar = true;
            }

            if (procesar) {
              detalle = new DetRegVentasBean();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void endRow() {
          try {
            if (procesar) {
              if (detalle.getCodOperacion() != null && detalle.getFecEmision() != null) {
                bw.append(/* 1 */per + "00|" +
                /* 2 */StringUtils.left(detalle.getCodOperacion(), 40) + "|" +
                /* 3 */((detalle.getFecEmision() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecEmision())) + "|" +
                /* 4 */((detalle.getFecVencimiento() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecVencimiento())) + "|" +
                /* 5 */((detalle.getTipCompPago() == null) ? Constantes.TIP_COMP_DEFAULT : detalle.getTipCompPago()) + "|" +
                /* 6 */((detalle.getSerCompPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(StringUtils.leftPad(detalle.getSerCompPago(), 4, '0'), 20)) + "|" +
                /* 7 */("00".equals(detalle.getSerCompPago()) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCompPago(), 20)) + "|" +
                /* 8 */"0" + "|" + // Campo 8 no corresponde
                /* 9 */((detalle.getTipDocCliente() == null) ? Constantes.TEXTO_DEFAULT : Integer.parseInt(detalle.getTipDocCliente())) + "|" +
                /* 10 */((detalle.getNumDocCliente() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumDocCliente().replace(".", Constantes.TEXTO_DEFAULT), 15)) + "|" +
                /* 11 */((detalle.getNomCliente() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNomCliente(), 60)) + "|" +
                /* 12 */((detalle.getValFacExportacion() == null) ? Constantes.MONTO_DEFAULT : detalle.getValFacExportacion()) + "|" +
                /* 13 */((detalle.getBaseImponible1() == null) ? Constantes.MONTO_DEFAULT : detalle.getBaseImponible1()) + "|" +
                /* 14 */((detalle.getImpTotOperacion1() == null) ? Constantes.MONTO_DEFAULT : detalle.getImpTotOperacion1()) + "|" +
                /* 15 */((detalle.getImpTotOperacion2() == null) ? Constantes.MONTO_DEFAULT : detalle.getImpTotOperacion2()) + "|" +
                /* 16 */((detalle.getIsc() == null) ? Constantes.MONTO_DEFAULT : detalle.getIsc()) + "|" +
                /* 17 */((detalle.getIgv1() == null) ? Constantes.MONTO_DEFAULT : detalle.getIgv1()) + "|" +
                // No aplica para Cardif
                /* 18 */Constantes.MONTO_DEFAULT + "|" +
                // No aplica para Cardif
                /* 19 */Constantes.MONTO_DEFAULT + "|" +
                /* 20 */((detalle.getOtrosTributos() == null) ? Constantes.MONTO_DEFAULT : detalle.getOtrosTributos()) + "|" +
                /* 21 */((detalle.getImpTotal() == null) ? Constantes.MONTO_DEFAULT : detalle.getImpTotal()) + "|" +
                /* 22 */((detalle.getTipCambio() == null) ? Constantes.TIP_CAMBIO_DEFAULT : detalle.getTipCambio()) + "|" +
                /* 23 */((detalle.getFecRefPago() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecRefPago())) + "|" +
                /* 24 */((detalle.getTipRefPago() == null) ? Constantes.TIP_COMP_DEFAULT : detalle.getTipRefPago()) + "|" +
                /* 25 */((detalle.getSerRefPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getSerRefPago(), 20)) + "|" +
                /* 26 */((detalle.getNumCompRefPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCompRefPago(), 20)) + "|" +
                /* 27 */((detalle.getEstadoCompPago() == null) ? "1" : detalle.getEstadoCompPago()) + "|"

                );

                bw.newLine();
              }
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            if (procesar) {

              String fila1 = cellReference.substring(0, 1);

              if (fila1.equals("B")) {
                detalle.setCodOperacion(formattedValue);
              } else if (fila1.equals("C")) {
                detalle.setFecEmision(sdf.parse(formattedValue));
              } else if (fila1.equals("F")) {
                detalle.setFecVencimiento(sdf.parse(formattedValue));
              } else if (fila1.equals("G")) {
                detalle.setTipCompPago(formattedValue);
              } else if (fila1.equals("H")) {
                detalle.setSerCompPago(formattedValue);
              } else if (fila1.equals("J")) {
                detalle.setNumCompPago(formattedValue);
              } else if (fila1.equals("M")) {
                detalle.setTipDocCliente(formattedValue);
              } else if (fila1.equals("N")) {
                detalle.setNumDocCliente(formattedValue);
              } else if (fila1.equals("O")) {
                detalle.setNomCliente(formattedValue);
              } else if (fila1.equals("S")) {
                detalle.setValFacExportacion(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("T")) {
                detalle.setBaseImponible1(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("U")) {
                detalle.setImpTotOperacion1(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("V")) {
                detalle.setImpTotOperacion2(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("W")) {
                detalle.setIsc(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("X")) {
                detalle.setIgv1(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("Y")) {
                detalle.setOtrosTributos(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("Z")) {
                detalle.setImpTotal(df.format(nf.parse(formattedValue)));
              } else {
                String fila2 = cellReference.substring(0, 2);
                if (fila2.equals("AA")) {
                  detalle.setTipCambio(dft.format(nf.parse(formattedValue)));
                } else if (fila2.equals("AB")) {
                  detalle.setFecRefPago(sdf.parse(formattedValue));
                } else if (fila2.equals("AC")) {
                  detalle.setTipRefPago(formattedValue);
                } else if (fila2.equals("AD")) {
                  detalle.setSerRefPago(formattedValue);
                } else if (fila2.equals("AE")) {
                  detalle.setNumCompRefPago(formattedValue);
                } else if (fila2.equals("AF")) {
                  detalle.setEstadoCompPago(formattedValue);
                }
              }

            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

      }, false// means result instead of formula
      );

      sheetParser.setContentHandler(handler);
      sheetParser.parse(sheetSource);

      bw.flush();
      bw.close();

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
    }
  }

  private void procesarRegVentasServicios(File file, CabLibro cabLibro, StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws IOException, SAXException {

    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      final FileWriter pw = new FileWriter(file);
      final BufferedWriter bw = new BufferedWriter(pw);

      final int per = cabLibro.getPeriodo();
      final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      final NumberFormat nf = NumberFormat.getInstance(LANGUAGE); // JapanX
      DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
      simbolo.setDecimalSeparator('.');
      simbolo.setGroupingSeparator(',');
      // final DecimalFormat df = new DecimalFormat("###,###.00",simbolo);
      final DecimalFormat df = new DecimalFormat("0.00", simbolo);
      final DecimalFormat dft = new DecimalFormat("0.000");

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        boolean procesar = false;
        DetRegVentasBean detalle;

        @Override
        public void startRow(int rowNum) {
          try {

            if (rowNum == 10) {// row 14
              procesar = true;
            }

            if (procesar) {
              detalle = new DetRegVentasBean();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void endRow() {
          try {
            if (procesar) {
              if (detalle.getCodOperacion() != null) {
                bw.append(/* 1 */per + "00|" +
                /* 2 */StringUtils.left(detalle.getCodOperacion(), 40) + "|" +
                /* 3 */((detalle.getNumCorrAsientoCtble() == null) ? "M" + detalle.getCodOperacion() : StringUtils.left(detalle.getNumCorrAsientoCtble(), 10)) + "|" + // Nuevo
                /* 4 */((detalle.getFecEmision() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecEmision())) + "|" +
                /* 5 */((detalle.getFecVencimiento() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecEmision())) + "|" +
                /* 6 */((detalle.getTipCompPago() == null) ? Constantes.TIP_COMP_DEFAULT : detalle.getTipCompPago()) + "|" +
                /* 7 */((detalle.getSerCompPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(StringUtils.leftPad(detalle.getSerCompPago(), 4, '0'), 20)) + "|" +
                // Estaba en 20, se cambio a 24
                /* 8 */("00".equals(detalle.getSerCompPago()) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCompPago(), 24)) + "|" +
                /* 9 */((detalle.getNumCompFinal() == null) ? "0" : detalle.getNumCompFinal()) + "|" +
                /* 10 */((detalle.getTipDocCliente() == null) ? Constantes.TEXTO_DEFAULT : Integer.parseInt(detalle.getTipDocCliente())) + "|" +
                /* 11 */((detalle.getNumDocCliente() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumDocCliente(), 15)) + "|" +
                // Se cambio longitud de 60 a 100
                /* 12 */((detalle.getNomCliente() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNomCliente(), 100)) + "|" +
                /* 13 */((detalle.getValFacExportacion() == null) ? Constantes.MONTO_DEFAULT : detalle.getValFacExportacion()) + "|" +
                /* 14 */((detalle.getBaseImponible1() == null) ? Constantes.MONTO_DEFAULT : detalle.getBaseImponible1()) + "|" +
                /* 15 */((detalle.getImpTotOperacion1() == null) ? Constantes.MONTO_DEFAULT : detalle.getImpTotOperacion1()) + "|" +
                /* 16 */((detalle.getImpTotOperacion2() == null) ? Constantes.MONTO_DEFAULT : detalle.getImpTotOperacion2()) + "|" +
                /* 17 */((detalle.getIsc() == null) ? Constantes.MONTO_DEFAULT : detalle.getIsc()) + "|" +
                /* 18 */((detalle.getIgv1() == null) ? Constantes.MONTO_DEFAULT : detalle.getIgv1()) + "|" +
                // No aplica para Cardif
                /* 19 */Constantes.MONTO_DEFAULT + "|" +
                // No aplica para Cardif
                /* 20 */Constantes.MONTO_DEFAULT + "|" +
                /* 21 */((detalle.getOtrosTributos() == null) ? Constantes.MONTO_DEFAULT : detalle.getOtrosTributos()) + "|" +
                /* 22 */((detalle.getImpTotal() == null) ? Constantes.MONTO_DEFAULT : detalle.getImpTotal()) + "|" +
                /* 23 */((detalle.getTipCambio() == null) ? Constantes.TIP_CAMBIO_DEFAULT : detalle.getTipCambio()) + "|" +
                /* 24 */((detalle.getFecRefPago() == null) ? Constantes.FECHA_DEFAULT : sdf1.format(detalle.getFecRefPago())) + "|" +
                /* 25 */((detalle.getTipRefPago() == null) ? Constantes.TIP_COMP_DEFAULT : detalle.getTipRefPago()) + "|" +
                /* 26 */((detalle.getSerRefPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getSerRefPago(), 20)) + "|" +
                /* 27 */((detalle.getNumCompRefPago() == null) ? Constantes.TEXTO_DEFAULT : StringUtils.left(detalle.getNumCompRefPago(), 24)) + "|" + // 20
                /* 28 */((detalle.getValEmbarExport() == null) ? Constantes.MONTO_DEFAULT : detalle.getValEmbarExport()) + "|" + // cambio
                /* 29 */((detalle.getEstadoCompPago() == null) ? "1" : detalle.getEstadoCompPago()) + "|");

                bw.newLine();
              }
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            if (procesar) {
              String fila1 = cellReference.substring(0, 1);
              /*
               * if (fila1.equals("B")) { detalle.setCodOperacion(formattedValue); } else if (fila1.equals("C")) { log.debug("Fecha: " +
               * formattedValue); detalle .setFecEmision(sdf.parse(formattedValue )); } else if (fila1.equals("D")) { detalle
               * .setFecVencimiento(sdf.parse(formattedValue )); } else if (fila1.equals("E")) { detalle.setTipCompPago(formattedValue); } else if
               * (fila1.equals("F")) { detalle.setSerCompPago(formattedValue); } else if (fila1.equals("G")) { detalle.setNumCompPago(formattedValue);
               * } else if (fila1.equals("H")) { detalle.setTipDocCliente(formattedValue); } else if (fila1.equals("I")) {
               * detalle.setNumDocCliente(formattedValue); } else if (fila1.equals("J")) { detalle.setNomCliente(formattedValue); } else if
               * (fila1.equals("K")) { detalle.setValFacExportacion (df.format(nf.parse(formattedValue))); } //else if (fila1.equals("L")) {
               * detalle.setBaseImponible1 (df.format(nf.parse(formattedValue))); } else if (fila1.equals("L")) { detalle.setBaseImponible1
               * (formattedValue); } else if (fila1.equals("M")) { detalle.setImpTotOperacion1 (df.format(nf.parse(formattedValue))); } else if
               * (fila1.equals("N")) { detalle.setImpTotOperacion2 (df.format(nf.parse(formattedValue))); } else if (fila1.equals("O")) {
               * detalle.setIsc (df.format(nf.parse(formattedValue))); } else if (fila1.equals("P")) { detalle.setIgv1
               * (df.format(nf.parse(formattedValue))); } else if (fila1.equals("Q")) { detalle.setOtrosTributos
               * (df.format(nf.parse(formattedValue))); } else if (fila1.equals("R")) { detalle.setImpTotal (df.format(nf.parse(formattedValue))); }
               * else if (fila1.equals("S")) { detalle.setTipCambio (dft.format(nf.parse(formattedValue))); } else if (fila1.equals("T")) {
               * detalle.setFecRefPago (sdf.parse(formattedValue)); } else if (fila1.equals("U")) { detalle.setTipRefPago(formattedValue); } else if
               * (fila1.equals("V")) { detalle.setSerRefPago(formattedValue);} else if (fila1.equals("W")) { detalle.setNumCompRefPago
               * (formattedValue); } else if (fila1.equals("Y")) { detalle.setEstadoCompPago (formattedValue); } if(fila1.equals("B")){
               * detalle.setCodOperacion(formattedValue);}
               */

              if (fila1.equals("B")) {
                detalle.setCodOperacion(formattedValue);
              } else if (fila1.equals("C")) {
                detalle.setNumCorrAsientoCtble(formattedValue);
              } //
              else if (fila1.equals("D")) {
                log.debug("Fecha: " + formattedValue);
                detalle.setFecEmision(sdf.parse(formattedValue));
              } else if (fila1.equals("E")) {
                detalle.setFecVencimiento(sdf.parse(formattedValue));
              } else if (fila1.equals("F")) {
                detalle.setTipCompPago(formattedValue);
              } else if (fila1.equals("G")) {
                detalle.setSerCompPago(formattedValue);
              } else if (fila1.equals("H")) {
                detalle.setNumCompPago(formattedValue);
              } else if (fila1.equals("I")) {
                detalle.setNumCompFinal(formattedValue);
              } //
              else if (fila1.equals("J")) {
                detalle.setTipDocCliente(formattedValue);
              } else if (fila1.equals("K")) {
                detalle.setNumDocCliente(formattedValue);
              } else if (fila1.equals("L")) {
                detalle.setNomCliente(formattedValue);
              } else if (fila1.equals("M")) {
                detalle.setValFacExportacion(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("N")) {
                detalle.setBaseImponible1(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("O")) {
                detalle.setImpTotOperacion1(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("P")) {
                detalle.setImpTotOperacion2(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("Q")) {
                detalle.setIsc(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("R")) {
                detalle.setIgv1(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("U")) {
                detalle.setOtrosTributos(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("V")) {
                detalle.setImpTotal(df.format(nf.parse(formattedValue)));
              } else if (fila1.equals("W")) {
                detalle.setTipCambio(dft.format(nf.parse(formattedValue)));
              } else if (fila1.equals("X")) {
                detalle.setFecRefPago(sdf.parse(formattedValue));
              } else if (fila1.equals("Y")) {
                detalle.setTipRefPago(formattedValue);
              } else if (fila1.equals("Z")) {
                detalle.setSerRefPago(formattedValue);
              }
              // if(fila1.equals("B")){detalle.setCodOperacion(formattedValue);}
              else {
                String fila2 = cellReference.substring(0, 2);
                if (fila2.equals("AA")) {
                  detalle.setNumCompRefPago(formattedValue);
                } else if (fila2.equals("AB")) {
                  detalle.setValEmbarExport(formattedValue);
                } else if (fila2.equals("AC")) {
                  detalle.setEstadoCompPago(formattedValue);
                }
              }
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

      }, false// means result instead of formula
      );

      sheetParser.setContentHandler(handler);
      sheetParser.parse(sheetSource);
      bw.flush();
      bw.close();

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());

    }
  }

}
