package com.cardif.satelite.telemarketing.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.RowBounds;
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
import com.cardif.satelite.model.TlmkBaseIB;
import com.cardif.satelite.telemarketing.dao.TlmkBaseIBMapper;
import com.cardif.satelite.telemarketing.service.BaseIBService;

@Service("baseIBService")
public class BaseIBServiceImpl implements BaseIBService {

  public static final Logger log = Logger.getLogger(BaseIBServiceImpl.class);
  public static Long codBase;

  @Autowired
  private TlmkBaseIBMapper tlmkBaseIBMapper;

  @Override
  public void insertar(TlmkBaseIB tlmkBaseIB) throws SyncconException {

    log.info("Inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(tlmkBaseIB) + "]");

      tlmkBaseIBMapper.insert(tlmkBaseIB);

      log.debug("Output: [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("Fin");
  }

  @Override
  public List<TlmkBaseIB> buscar(Long codBase, String estado) throws SyncconException {
    log.info("Inicio");
    // String estado1 = null;
    List<TlmkBaseIB> lista = null;
    try {
      lista = tlmkBaseIBMapper.selectBase(codBase, estado);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return lista;
  }

  @Override
  public List<TlmkBaseIB> buscarParcial(Long codBase, String estado, int inicio, int cantidad) throws SyncconException {
    log.info("Inicio");
    // String estado1 = null;
    List<TlmkBaseIB> lista = null;
    try {
      lista = tlmkBaseIBMapper.selectBaseParcial(codBase, estado, new RowBounds(inicio, cantidad));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return lista;
  }

  @Override
  public void insertarDatosExcel(File excelFile, Long codBase, LinkedHashMap<String, String> mapExcel) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("input [" + excelFile + "]");

      OPCPackage container;
      container = OPCPackage.open(excelFile);
      ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(container);
      XSSFReader xssfReader = new XSSFReader(container);
      StylesTable styles = xssfReader.getStylesTable();
      InputStream stream = xssfReader.getSheet("rId1");

      BaseIBServiceImpl.codBase = codBase;

      procesarExcel(styles, strings, stream, mapExcel);

      container.close();
      stream.close();
      stream = null;

      if (log.isDebugEnabled())
        log.debug("Output [Ok]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
  }

  @Override
  public int codBaseMax() throws SyncconException {

    log.info("Inicio");
    int codBaseIB = 0;

    try {

      codBaseIB = tlmkBaseIBMapper.selectMaxCodBase();

    } catch (Exception e) {

      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");

    return codBaseIB;
  }

  @Override
  public int cantClientesProcesados(Long codBase, String estado) throws SyncconException {

    int cantCli = 0;

    try {

      cantCli = tlmkBaseIBMapper.selectClientesProcesados(codBase, estado);

    } catch (Exception e) {

      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    return cantCli;
  }

  private void procesarExcel(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream, final LinkedHashMap<String, String> mapExcel) throws IOException, SAXException {

    log.info("[ Inicio proceso carga Excel ]");
    InputSource sheetSource = new InputSource(sheetInputStream);
    SAXParserFactory saxFactory = SAXParserFactory.newInstance();

    try {
      SAXParser saxParser = saxFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        TlmkBaseIB tlmkBaseIB;
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
              tlmkBaseIB = new TlmkBaseIB();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        // AGREGAR AL FINAL
        @Override
        public void endRow() {
          if (procesar) {
            tlmkBaseIB.setCodBase(codBase);
            tlmkBaseIBMapper.insert(tlmkBaseIB);
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

              if (column.equals(mapExcel.get("TC_VEA"))) {
                tlmkBaseIB.setTcVea(formattedValue);
              } else if (column.equals(mapExcel.get("TC_VISA"))) {
                tlmkBaseIB.setTcVisa(formattedValue);
              } else if (column.equals(mapExcel.get("TC_AMEX"))) {
                tlmkBaseIB.setTcAmex(formattedValue);
              } else if (column.equals(mapExcel.get("TC_MASTERCARD"))) {
                tlmkBaseIB.setTcMastercard(formattedValue);
              } else if (column.equals(mapExcel.get("TD"))) {
                tlmkBaseIB.setTd(formattedValue);
              } else if (column.equals(mapExcel.get("TIPDOC"))) {
                tlmkBaseIB.setTipdoc(formattedValue);
              } else if (column.equals(mapExcel.get("CODDOC"))) {
                tlmkBaseIB.setCoddoc(formattedValue);
              } else if (column.equals(mapExcel.get("NBRPRIMERPER"))) {
                tlmkBaseIB.setNbrprimerper(formattedValue);
              } else if (column.equals(mapExcel.get("NBRSEGUNDOPER"))) {
                tlmkBaseIB.setNbrsegundoper(formattedValue);
              } else if (column.equals(mapExcel.get("APEPATPER"))) {
                tlmkBaseIB.setApepatper(formattedValue);
              } else if (column.equals(mapExcel.get("APEMATPER"))) {
                tlmkBaseIB.setApematper(formattedValue);
              } else if (column.equals(mapExcel.get("SEXO"))) {
                tlmkBaseIB.setSexo(formattedValue);
              } else if (column.equals(mapExcel.get("EDAD"))) {
                tlmkBaseIB.setEdad(formattedValue);
              } else if (column.equals(mapExcel.get("CODTELDEPARTAMENTO_TEL_DOM"))) {
                tlmkBaseIB.setCodteldepartamentoTelDom(formattedValue);
              } else if (column.equals(mapExcel.get("NUMTELEFONO_TEL_DOM"))) {
                tlmkBaseIB.setNumtelefonoTelDom(formattedValue);
              } else if (column.equals(mapExcel.get("CODTELDEPARTAMENTO_TEL_CEL"))) {
                tlmkBaseIB.setCodteldepartamentoTelCel(formattedValue);
              } else if (column.equals(mapExcel.get("NUMTELEFONO_TEL_CEL"))) {
                tlmkBaseIB.setNumtelefonoTelCel(formattedValue);
              } else if (column.equals(mapExcel.get("CODTELDEPARTAMENTO_TEL_TRABAJO"))) {
                tlmkBaseIB.setCodteldepartamentoTelTrabajo(formattedValue);
              } else if (column.equals(mapExcel.get("NUMTELEFONO_TEL_TRABAJO"))) {
                tlmkBaseIB.setNumtelefonoTelTrabajo(formattedValue);
              } else if (column.equals(mapExcel.get("DESDIRECCION"))) {
                tlmkBaseIB.setDesdireccion(formattedValue);
              } else if (column.equals(mapExcel.get("CODUBIGEO"))) {
                tlmkBaseIB.setCodubigeo(formattedValue);
              } else if (column.equals(mapExcel.get("FLG_PROCEDENCIA"))) {
                tlmkBaseIB.setFlgProcedencia(formattedValue);
              } else if (column.equals(mapExcel.get("DESC_SEGUROS"))) {
                tlmkBaseIB.setDescSeguros(formattedValue);
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
