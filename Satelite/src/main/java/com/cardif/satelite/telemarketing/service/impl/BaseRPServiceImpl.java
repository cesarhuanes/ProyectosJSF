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
import com.cardif.satelite.model.TlmkBaseRP;
import com.cardif.satelite.telemarketing.dao.TlmkBaseRPMapper;
import com.cardif.satelite.telemarketing.service.BaseRPService;

@Service("baseRPService")
public class BaseRPServiceImpl implements BaseRPService {

  public static final Logger log = Logger.getLogger(BaseRPServiceImpl.class);
  public static Long codBase;

  @Autowired
  private TlmkBaseRPMapper tlmkBaseRPMapper;

  @Override
  public void insertar(TlmkBaseRP tlmkBaseRP) throws SyncconException {

    log.info("Inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(tlmkBaseRP) + "]");

      tlmkBaseRPMapper.insert(tlmkBaseRP);

      log.debug("Output: [Ok]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }

    log.info("Fin");
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
      BaseRPServiceImpl.codBase = codBase;

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
  public int codBaseMaxRP() throws SyncconException {

    log.info("Inicio");
    int codBaseRP = 0;

    try {

      codBaseRP = tlmkBaseRPMapper.selectMaxCodBaseRP();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");

    return codBaseRP;
  }

  @Override
  public List<TlmkBaseRP> buscar(Long codBase, String estado) throws SyncconException {

    log.info("Inicio");
    // String estado = null;
    List<TlmkBaseRP> lista = null;
    try {
      lista = tlmkBaseRPMapper.selectBase(codBase, estado);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return lista;

  }

  @Override
  public int cantClientesProcesados(Long codBase, String estado) throws SyncconException {

    int cantCli = 0;

    try {

      cantCli = tlmkBaseRPMapper.selectClientesProcesados(codBase, estado);

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

      final SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        TlmkBaseRP tlmkBaseRP;
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
              tlmkBaseRP = new TlmkBaseRP();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        // AGREGAR AL FINAL
        @Override
        public void endRow() {
          if (procesar) {
            tlmkBaseRP.setCodBase(codBase);
            tlmkBaseRPMapper.insert(tlmkBaseRP);
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

              if (column.equals(mapExcel.get("SECUENCIA_TLMK"))) {// mapExcel.get("")
                tlmkBaseRP.setSecuenciaTlmk(formattedValue);
              } else if (column.equals(mapExcel.get("TARGET"))) {
                tlmkBaseRP.setTarget(formattedValue);
              } else if (column.equals(mapExcel.get("FUE_MAX"))) {
                tlmkBaseRP.setFueMax(formattedValue);
              } else if (column.equals(mapExcel.get("MARCA1"))) {
                tlmkBaseRP.setMarca1(formattedValue);
              } else if (column.equals(mapExcel.get("DOCIDE_ENL"))) {
                tlmkBaseRP.setDocideEnl(formattedValue);
              } else if (column.equals(mapExcel.get("TIPDOC"))) {
                tlmkBaseRP.setTipdoc(formattedValue);
              } else if (column.equals(mapExcel.get("TIP_TARJETA"))) {
                tlmkBaseRP.setTipTarjeta(formattedValue);
              } else if (column.equals(mapExcel.get("RANGO_UTILIZACION"))) {
                tlmkBaseRP.setRangoUtilizacion(formattedValue);
              } else if (column.equals(mapExcel.get("RANGO_LINEA_RIPLEY"))) {
                tlmkBaseRP.setRangoLineaRipley(formattedValue);
              } else if (column.equals(mapExcel.get("RANGO_SALDOS_RIPLEY"))) {
                tlmkBaseRP.setRangoSaldosRipley(formattedValue);
              } else if (column.equals(mapExcel.get("RANGO_ANTIGUEDAD"))) {
                tlmkBaseRP.setRangoAntiguedad(formattedValue);
              } else if (column.equals(mapExcel.get("RECENCIA_TO"))) {
                tlmkBaseRP.setRecenciaTo(formattedValue);
              } else if (column.equals(mapExcel.get("FECHA_ACT"))) {
                tlmkBaseRP.setFechaAct(formatFecha.parse(formattedValue));
              } else if (column.equals(mapExcel.get("FECHA_VENCIMIENTO"))) {
                tlmkBaseRP.setFechaVencimiento(formatFecha.parse(formattedValue));
              } else if (column.equals(mapExcel.get("FECHA_EMBOZADO"))) {
                tlmkBaseRP.setFechaEmbozado(formatFecha.parse(formattedValue));
              } else if (column.equals(mapExcel.get("SEXO"))) {
                tlmkBaseRP.setSexo(formattedValue);
              } else if (column.equals(mapExcel.get("EDAD"))) {
                tlmkBaseRP.setEdad(formattedValue);
              } else if (column.equals(mapExcel.get("NSE"))) {
                tlmkBaseRP.setNse(formattedValue);
              } else if (column.equals(mapExcel.get("NOMBRESYAPELLIDOS"))) {
                tlmkBaseRP.setNombresyapellidos(formattedValue);
              } else if (column.equals(mapExcel.get("OCUPACION"))) {
                tlmkBaseRP.setOcupacion(formattedValue);
              } else if (column.equals(mapExcel.get("DIRECCION"))) {
                tlmkBaseRP.setDireccion(formattedValue);
              } else if (column.equals(mapExcel.get("DISTRITO"))) {
                tlmkBaseRP.setDistrito(formattedValue);
              } else if (column.equals(mapExcel.get("DEPARTAMENTO"))) {
                tlmkBaseRP.setDepartamento(formattedValue);
              } else if (column.equals(mapExcel.get("FONO_EMPLEADOR_1"))) {
                tlmkBaseRP.setFonoEmpleador1(formattedValue);
              } else if (column.equals(mapExcel.get("FONO_CLIENTE_1"))) {
                tlmkBaseRP.setFonoCliente1(formattedValue);
              } else if (column.equals(mapExcel.get("FONO_CLIENTE_2"))) {
                tlmkBaseRP.setFonoCliente2(formattedValue);
              } else if (column.equals(mapExcel.get("CELULAR_0"))) {
                tlmkBaseRP.setCelular0(formattedValue);
              } else if (column.equals(mapExcel.get("CELULAR_1"))) {
                tlmkBaseRP.setCelular1(formattedValue);
              } else if (column.equals(mapExcel.get("OFICINA"))) {
                tlmkBaseRP.setOficina(formattedValue);
              } else if (column.equals(mapExcel.get("CASA"))) {
                tlmkBaseRP.setCasa(formattedValue);
              } else if (column.equals(mapExcel.get("CEL1"))) {
                tlmkBaseRP.setCel1(formattedValue);
              } else if (column.equals(mapExcel.get("CEL2"))) {
                tlmkBaseRP.setCel2(formattedValue);
              } else if (column.equals(mapExcel.get("CEL3"))) {
                tlmkBaseRP.setCel3(formattedValue);
              } else if (column.equals(mapExcel.get("CEL4"))) {
                tlmkBaseRP.setCel4(formattedValue);
              } else if (column.equals(mapExcel.get("CEL5"))) {
                tlmkBaseRP.setCel5(formattedValue);
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
