package com.cardif.satelite.suscripcion.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

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
import com.cardif.satelite.acsele.service.OpenItemService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SusTramaInterbank;
import com.cardif.satelite.model.SusTramaInterbankExample;
import com.cardif.satelite.model.acsele.OpenItem;
import com.cardif.satelite.suscripcion.dao.SusTramaCabeceraIBMapper;
import com.cardif.satelite.suscripcion.service.SusTramaAnulacionMensualService;
import com.cardif.satelite.suscripcion.service.SusTramaInterbankService;
import com.cardif.satelite.util.Tools;

@Service("susTramaAnulacionMensualService")
public class SusTramaAnulacionMensualImpl implements SusTramaAnulacionMensualService {

  public static final Logger logger = Logger.getLogger(SusTramaInterbankServiceImpl.class);

  @Autowired
  SusTramaCabeceraIBMapper susTramaCabeceraIBMapper;
  @Autowired
  SusTramaInterbankService susTramaInterbankService;
  @Autowired
  OpenItemService openItemService;

  @Override
  public void actEstadosRegistros(File excelFile, Long codTrama) throws SyncconException {

    int i = 0;
    logger.info("Inicio");

    try {
      logger.debug("input archivo excel:   [" + excelFile + "]");
      logger.info("el valor de i es : " + i);
      OPCPackage container;
      container = OPCPackage.open(excelFile.getAbsolutePath());
      ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(container);
      XSSFReader xssfReader = new XSSFReader(container);
      StylesTable styles = xssfReader.getStylesTable();
      InputStream stream = xssfReader.getSheet("rId1");

      final Long codigoTrama = codTrama;
      procesarArchivoMensual(styles, strings, stream, codigoTrama);

      stream.close();
      if (logger.isDebugEnabled())
        logger.debug("Output [Ok] ");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    logger.info("[ Fin ]");
  }

  private void procesarArchivoMensual(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream, final Long codTrama) throws IOException, SAXException {
    logger.info("[ Inicio procesar archivo ]");

    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();

    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();
      DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
      simbolo.setDecimalSeparator('.');
      // simbolo.setGroupingSeparator(',');

      // final NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN); //
      // FIXME:
      // CAMBIAR
      // A
      // JAPAN
      // EN
      // PROD
      final DecimalFormat df = new DecimalFormat("0.00");
      // final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        SusTramaInterbank susTramaInterbank;
        SusTramaInterbankExample susTramaInterbankExample;
        boolean procesar = false;

        @Override
        public void startRow(int rowNum) {
          logger.info("[numero de fila : " + rowNum + " ]");
          try {
            if (rowNum == 1) {
              procesar = true;
            }
            if (procesar) {
              susTramaInterbank = new SusTramaInterbank();
            }
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
        }

        // AGREGAR AL FINAL
        @Override
        public void endRow() {

          if (procesar) {
            try {

              if (susTramaInterbank.getEstadoDocumento().equalsIgnoreCase("PROCEDE")) {
                List<OpenItem> listaOpenItem = openItemService.listarOpenItem(susTramaInterbank.getNroCertificado());

                OpenItem o;
                logger.error("ANTES DE LA FECHA DE SOLICITUD ---> ");
                logger.error("listaOpenItem.size()---> " + listaOpenItem.size());
                logger.error("susTramaInterbank.getNroCuotas() ---> " + susTramaInterbank.getNroCuotas());

                if (listaOpenItem != null && listaOpenItem.size() >= susTramaInterbank.getNroCuotas()) {
                  o = listaOpenItem.get(susTramaInterbank.getNroCuotas().intValue() - 1);
                  logger.error("LA FECHA DE SOLICITUD DE ANULACION : FECHA  " + o.getDocdate());
                  susTramaInterbank.setFecSolAnulacion(o.getDocdate());
                }
                susTramaInterbankExample = new SusTramaInterbankExample();

                susTramaInterbank.setEstadoDocumento("CON DEVOLUCION"); // EstadoDocumento
                // es
                // la
                // variable
                // que
                // define
                // el
                // estado
                // del
                // registro
                // (CON
                // DEVOLUCION/SIN
                // DEVOLUCION)
                susTramaInterbankExample.createCriteria().andNroCertificadoEqualTo(susTramaInterbank.getNroCertificado()).andCodTramaEqualTo(codTrama);
                susTramaInterbankService.actualizar(susTramaInterbank, susTramaInterbankExample);
                // segun el numero de cuotas, obtener la
                // fecha del openitem mas antiguo.
              }

            } catch (Exception e) {
              logger.error(e.getMessage(), e);
            }
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {

          try {
            String column = "";
            if (procesar) {
              if (!Tools.getLength(cellReference)) {
                column = cellReference.substring(0, 1);
                /*
                 * if (column.equals("A")) { susTramaInterbank .setNroTicket(formattedValue); } else if (column.equals("B")) { susTramaInterbank
                 * .setFecIn(sdf.parse(formattedValue)); } else if (column.equals("C")) { susTramaInterbank .setFecReclamo(sdf.parse
                 * (formattedValue)); } else if (column.equals("D")) { susTramaInterbank .setNroReclamo(Long.parseLong (formattedValue)); } else if
                 * (column.equals("E")) { susTramaInterbank .setFecAtencion(sdf.parse (formattedValue)); } else if (column.equals("F")) {
                 * susTramaInterbank .setFecModificacion(sdf .parse(formattedValue)); } else if (column.equals("G")) { susTramaInterbank
                 * .setProducto(formattedValue); } else if (column.equals("H")) { susTramaInterbank .setEntidad(formattedValue); } else if
                 * (column.equals("I")) { susTramaInterbank .setImagen(formattedValue); } else if (column.equals("J")) { susTramaInterbank
                 * .setAutonomia(formattedValue); } else
                 */if (column.equals("K")) {
                  susTramaInterbank.setNroCertificado(formattedValue);
                  /*
                   * } else if (column.equals("L")) { susTramaInterbank .setNroCerticadoBanco (formattedValue); } else if (column.equals("M")) {
                   * susTramaInterbank .setNomApeCliente (formattedValue); } else if (column.equals("N")) { susTramaInterbank .setDni(Long.parseLong
                   * (formattedValue)); } else if (column.equals("O")) { susTramaInterbank .setNroCuenta(formattedValue); } else if
                   * (column.equals("P")) { susTramaInterbank .setMotReclamo(formattedValue);
                   */
                } else if (column.equals("Q")) {
                  susTramaInterbank.setEstadoDocumento(formattedValue);
                  /*
                   * } else if (column.equals("R")) { susTramaInterbank .setPerInicioVigencia (formattedValue); } else if (column.equals("S")) {
                   * susTramaInterbank .setPerFinVigencia (formattedValue);
                   */
                } else if (column.equals("T")) {
                  susTramaInterbank.setNroCuotas(Long.parseLong(formattedValue));

                } else if (column.equals("U")) {
                  logger.debug("monto es--------------------- :   " + formattedValue);

                  formattedValue.replace(',', '.');
                  susTramaInterbank.setTotDevolver(new BigDecimal(df.parse(formattedValue) + ""));

                  /*
                   * } else if (column.equals("V")) { susTramaInterbank .setMoneda(formattedValue); } else if (column.equals("W")) { susTramaInterbank
                   * .setFecExtomo(sdf .parse(formattedValue)); } else if (column.equals("X")) { susTramaInterbank .setOpeExtomo(Long
                   * .parseLong(formattedValue)); } else if (column.equals("Y")) { susTramaInterbank .setCodRf(Long.parseLong (formattedValue)); }
                   * else if (column.equals("Z")) { susTramaInterbank .setNomRf(formattedValue); }
                   */

                } /*
                   * else { column = cellReference.substring(0, 2); if (column.equals("AA")) { susTramaInterbank .setCodOficina(Long.parseLong
                   * (formattedValue)); } else if (column.equals("AB")) { susTramaInterbank .setNomOficina(formattedValue); } else if
                   * (column.equals("AC")) { susTramaInterbank .setNomTerritorio(formattedValue); } else if (column.equals("AD")) { susTramaInterbank
                   * .setFecDigitacion(formattedValue); }
                   */
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

}