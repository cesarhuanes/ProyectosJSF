package com.cardif.satelite.telemarketing.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.xml.sax.XMLReader;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.TlmkBaseBC;
import com.cardif.satelite.model.TlmkBaseBCExample;
import com.cardif.satelite.telemarketing.service.BaseBCService;
import com.cardif.satelite.telemarketing.service.FeedBackService;

/**
 * @author 2ariasju
 *
 */
@Service("feedBackService")
public class FeedBackServiceImpl implements FeedBackService {

  public static final Logger log = Logger.getLogger(FeedBackServiceImpl.class);

  @Autowired
  private BaseBCService baseBCService;

  private static Long codBase;

  @Override
  public void generarArchivo(Long codBase, String codSocio, File archivo) throws SyncconException {

    log.info("Inicio");
    try {
      log.debug("Input: [" + codBase + "," + codSocio + "," + archivo + "]");

      List<TlmkBaseBC> listaBC = null;

      if ("BC".equals(codSocio)) {

        FileWriter fw = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(fw);

        pw.println("ASCod_camp\tFec_inic\tasFec_Final\tcliente\tTdocum\tNdoc_ident\tCod_trata\tCod_proveedor\tCod_Rpta\tCod_Obs");

        listaBC = baseBCService.buscar(codBase, null);

        String codRpta = null;
        String codObs = null;

        for (TlmkBaseBC tlmkBaseBC : listaBC) {

          codRpta = ("ERROR".equals(tlmkBaseBC.getEstado())) ? "0002" : tlmkBaseBC.getCodRpta();
          codObs = ("ERROR".equals(tlmkBaseBC.getEstado())) ? "000009" : tlmkBaseBC.getCodObs();

          pw.println(tlmkBaseBC.getCodCamp() + "\t" + tlmkBaseBC.getFecInic() + "\t" + tlmkBaseBC.getFecFinal() + "\t" + tlmkBaseBC.getCliente() + "\t" + tlmkBaseBC.getTdocum() + "\t"
              + tlmkBaseBC.getNdocIdent() + "\t" + tlmkBaseBC.getCodTrata() + "\t" + tlmkBaseBC.getCodProveedor() + "\t" + codRpta + "\t" + codObs);
        }
        pw.flush();
        pw.close();
        fw.close();

      }

      log.debug("Output: [Ok]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("Fin");
  }

  @Override
  public void cargarArchivo(Long codBase, String codSocio, File excelFile) throws SyncconException {

    log.info("Inicio");
    FeedBackServiceImpl.codBase = codBase;
    try {
      log.debug("Input: [" + codBase + "," + codSocio + "," + excelFile + "]");

      if ("BC".equals(codSocio)) {
        insertarDatosExcel(excelFile);
      }

      log.debug("Output: [Ok]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("Fin");
  }

  private void insertarDatosExcel(File excelFile) throws Exception {

    log.info("Inicio");

    if (log.isDebugEnabled())
      log.debug("input [" + excelFile + "]");

    OPCPackage container;
    container = OPCPackage.open(excelFile);
    ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(container);
    XSSFReader xssfReader = new XSSFReader(container);
    StylesTable styles = xssfReader.getStylesTable();
    InputStream stream = xssfReader.getSheet("rId1");

    procesarExcel(styles, strings, stream);

    container.close();
    stream.close();
    stream = null;

    log.info("[ Fin ]");

  }

  private void procesarExcel(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws Exception {

    log.info("[ Inicio proceso carga Excel ]");
    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();

    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        TlmkBaseBC tlmkBaseBC;
        TlmkBaseBCExample tlmkBaseBCExample;
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
              tlmkBaseBC = new TlmkBaseBC();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        // AGREGAR AL FINAL
        @Override
        public void endRow() {
          if (procesar) {

            try {// Actualizamos registros
              tlmkBaseBCExample = new TlmkBaseBCExample();
              tlmkBaseBCExample.createCriteria().andCodBaseEqualTo(codBase).andTdocumEqualTo(tlmkBaseBC.getTdocum()).andNdocIdentEqualTo(tlmkBaseBC.getNdocIdent());

              baseBCService.actualizar(tlmkBaseBC, tlmkBaseBCExample);
            } catch (Exception e) {
              log.error(e.getMessage(), e);
            }
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            String column = "";
            if (procesar) {
              if (!getLength(cellReference)) {
                column = cellReference.substring(0, 1);
              } else {
                column = cellReference.substring(0, 2);
              }

              if (column.equals("A")) {
                tlmkBaseBC.setCodCamp(formattedValue);
              } else if (column.equals("B")) {
                tlmkBaseBC.setFecInic(formattedValue);
              } else if (column.equals("C")) {
                tlmkBaseBC.setFecFinal(formattedValue);
              } else if (column.equals("D")) {
                // No se actualiza cliente
                // tlmkBaseBC.setCliente(formattedValue);
              } else if (column.equals("E")) {
                if (formattedValue.equals("D.N.I.") || formattedValue.equals("DNI")) {
                  tlmkBaseBC.setTdocum("L");
                } else {
                  tlmkBaseBC.setTdocum(formattedValue);
                }
              } else if (column.equals("F")) {
                log.info("Nro Doc: " + formattedValue);
                tlmkBaseBC.setNdocIdent(formattedValue);
              } else if (column.equals("G")) {
                tlmkBaseBC.setCodTrata(formattedValue);
              } else if (column.equals("H")) {
                tlmkBaseBC.setCodProveedor(formattedValue);
              } else if (column.equals("I")) {
                tlmkBaseBC.setCodRpta(formattedValue);
              } else if (column.equals("J")) {
                tlmkBaseBC.setCodObs(formattedValue);
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

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("[ SAX parser appears to be broken - " + e.getMessage() + " ]");
    }
    log.info("Fin");
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
}
