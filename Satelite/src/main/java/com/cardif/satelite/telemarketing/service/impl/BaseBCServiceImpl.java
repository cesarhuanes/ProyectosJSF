package com.cardif.satelite.telemarketing.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.beanutils.BeanUtils;
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
import com.cardif.satelite.model.TlmkBaseBC;
import com.cardif.satelite.model.TlmkBaseBCExample;
import com.cardif.satelite.telemarketing.dao.TlmkBaseBCMapper;
import com.cardif.satelite.telemarketing.service.BaseBCService;

@Service("baseBCService")
public class BaseBCServiceImpl implements BaseBCService {

  public static final Logger log = Logger.getLogger(BaseBCServiceImpl.class);
  public static Long codBase;

  @Autowired
  private TlmkBaseBCMapper tlmkBaseBCMapper;

  @Override
  public void insertar(TlmkBaseBC tlmkBaseBC) throws SyncconException {

    log.info("Inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(tlmkBaseBC) + "]");

      tlmkBaseBCMapper.insert(tlmkBaseBC);

      log.debug("Output: [Ok]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("Fin");
  }

  @Override
  public void actualizar(TlmkBaseBC tlmkBaseBC, TlmkBaseBCExample example) throws SyncconException {

    log.info("Inicio");
    int result = 0;
    try {
      log.debug("Input: [" + BeanUtils.describe(tlmkBaseBC) + "]");

      result = tlmkBaseBCMapper.updateByExampleSelective(tlmkBaseBC, example);

      log.debug("Output: [Registros actualizados: " + result + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }

    log.info("Fin");
  }

  @Override
  public List<TlmkBaseBC> buscar(Long codBase, String estado) throws SyncconException {

    log.info("Inicio");

    List<TlmkBaseBC> lista = null;
    try {
      lista = tlmkBaseBCMapper.selectBase(codBase, estado);
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

      BaseBCServiceImpl.codBase = codBase;

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
  public int codBaseMaxBC() throws SyncconException {

    log.info("Inicio");
    int codBaseBC = 0;

    try {

      codBaseBC = tlmkBaseBCMapper.selectMaxCodBaseBC();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");

    return codBaseBC;
  }

  @Override
  public int cantClientesProcesados(Long codBase, String estado) throws SyncconException {

    int cantCli = 0;

    try {

      cantCli = tlmkBaseBCMapper.selectClientesProcesados(codBase, estado);

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
      final SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");

      // final NumberFormat nf = NumberFormat.getInstance(Locale.JAPAN);
      // final DecimalFormat df = new DecimalFormat("0.00");

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        TlmkBaseBC tlmkBaseBC;
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
            tlmkBaseBC.setCodBase(codBase);
            tlmkBaseBCMapper.insert(tlmkBaseBC);
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

              if (column.equals(mapExcel.get("COD_CAMP"))) {
                tlmkBaseBC.setCodCamp(formattedValue);
              } else if (column.equals(mapExcel.get("FEC_INIC"))) {
                tlmkBaseBC.setFecInic(formattedValue);
              } else if (column.equals(mapExcel.get("FEC_FINAL"))) {
                tlmkBaseBC.setFecFinal(formattedValue);
              } else if (column.equals(mapExcel.get("CLIENTE"))) {
                tlmkBaseBC.setCliente(formattedValue);
              } else if (column.equals(mapExcel.get("TDOCUM"))) {
                tlmkBaseBC.setTdocum(formattedValue);
              } else if (column.equals(mapExcel.get("NDOC_IDENT"))) {
                tlmkBaseBC.setNdocIdent(formattedValue);
              } else if (column.equals(mapExcel.get("COD_TRATA"))) {
                tlmkBaseBC.setCodTrata(formattedValue);
              } else if (column.equals(mapExcel.get("COD_PROVEEDOR"))) {
                tlmkBaseBC.setCodProveedor(formattedValue);
              } else if (column.equals(mapExcel.get("COD_RPTA"))) {
                tlmkBaseBC.setCodRpta(formattedValue);
              } else if (column.equals(mapExcel.get("COD_OBS"))) {
                tlmkBaseBC.setCodObs(formattedValue);
              } else if (column.equals(mapExcel.get("FENACIMI"))) {
                tlmkBaseBC.setFenacimi(formatFecha.parse(formattedValue));
              } else if (column.equals(mapExcel.get("EDAD"))) {
                tlmkBaseBC.setEdad(formattedValue);
              } else if (column.equals(mapExcel.get("SEXO"))) {
                tlmkBaseBC.setSexo(formattedValue);
              } else if (column.equals(mapExcel.get("EST_CIVIL"))) {
                tlmkBaseBC.setEstCivil(formattedValue);
              } else if (column.equals(mapExcel.get("PRIAPE"))) {
                tlmkBaseBC.setPriape(formattedValue);
              } else if (column.equals(mapExcel.get("SEGAPE"))) {
                tlmkBaseBC.setSegape(formattedValue);
              } else if (column.equals(mapExcel.get("NOMBRES"))) {
                tlmkBaseBC.setNombres(formattedValue);
              } else if (column.equals(mapExcel.get("DIRECCION"))) {
                tlmkBaseBC.setDireccion(formattedValue);
              } else if (column.equals(mapExcel.get("UBIGEO"))) {
                tlmkBaseBC.setUbigeo(formattedValue);
              } else if (column.equals(mapExcel.get("DISTRITO"))) {
                tlmkBaseBC.setDistrito(formattedValue);
              } else if (column.equals(mapExcel.get("PROVINCIA"))) {
                tlmkBaseBC.setProvincia(formattedValue);
              } else if (column.equals(mapExcel.get("DEPARTAMENTO"))) {
                tlmkBaseBC.setDepartamento(formattedValue);
              } else if (column.equals(mapExcel.get("TIPTEL1"))) {
                tlmkBaseBC.setTiptel1(formattedValue);
              } else if (column.equals(mapExcel.get("TIPTEL2"))) {
                tlmkBaseBC.setTiptel2(formattedValue);
              } else if (column.equals(mapExcel.get("TIPTEL3"))) {
                tlmkBaseBC.setTiptel3(formattedValue);
              } else if (column.equals(mapExcel.get("CODPOST"))) {
                tlmkBaseBC.setCodpost(formattedValue);
              } else if (column.equals(mapExcel.get("COD_C01"))) {
                tlmkBaseBC.setCodC01(formattedValue);
              } else if (column.equals(mapExcel.get("COD_C02"))) {
                tlmkBaseBC.setCodC02(formattedValue);
              } else if (column.equals(mapExcel.get("COD_C03"))) {
                tlmkBaseBC.setCodC03(formattedValue);
              } else if (column.equals(mapExcel.get("TELEFO1"))) {
                tlmkBaseBC.setTelefo1(formattedValue);
              } else if (column.equals(mapExcel.get("TELEFO2"))) {
                tlmkBaseBC.setTelefo2(formattedValue);
              } else if (column.equals(mapExcel.get("TELEFO3"))) {
                tlmkBaseBC.setTelefo3(formattedValue);
              } else if (column.equals(mapExcel.get("CONTRATO_AHORRO"))) {
                tlmkBaseBC.setContratoAhorro(formattedValue);
              } else if (column.equals(mapExcel.get("N_TARJETA"))) {
                tlmkBaseBC.setnTarjeta(formattedValue);
              } else if (column.equals(mapExcel.get("FECHA_VENCIMIENTO"))) {
                tlmkBaseBC.setFechaVencimiento(formatFecha.parse(formattedValue));
              } else if (column.equals(mapExcel.get("BIN"))) {
                tlmkBaseBC.setBin(formattedValue);
              } else if (column.equals(mapExcel.get("TARJETA"))) {
                tlmkBaseBC.setTarjeta(formattedValue);
              } else if (column.equals(mapExcel.get("RSCORE"))) {
                tlmkBaseBC.setRscore(formattedValue);
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
