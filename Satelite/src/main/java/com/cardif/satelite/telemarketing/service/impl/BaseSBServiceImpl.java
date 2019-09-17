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
import com.cardif.satelite.model.TlmkBaseSB;
import com.cardif.satelite.telemarketing.dao.TlmkBaseSBMapper;
import com.cardif.satelite.telemarketing.service.BaseSBService;

@Service("baseSBService")
public class BaseSBServiceImpl implements BaseSBService {

  public static final Logger log = Logger.getLogger(BaseSBServiceImpl.class);
  public static Long codBase;

  @Autowired
  private TlmkBaseSBMapper tlmkBaseSBMapper;

  @Override
  public void insertar(TlmkBaseSB tlmkBaseSB) throws SyncconException {

    log.info("Inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(tlmkBaseSB) + "]");

      tlmkBaseSBMapper.insert(tlmkBaseSB);
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

      BaseSBServiceImpl.codBase = codBase;

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
  public int codBaseMaxSB() throws SyncconException {

    log.info("Inicio");
    int codBaseIB = 0;

    try {

      codBaseIB = tlmkBaseSBMapper.selectMaxCodBaseSB();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");

    return codBaseIB;
  }

  @Override
  public List<TlmkBaseSB> buscar(Long codBase, String estado) throws SyncconException {

    log.info("Inicio");
    // String estado = null;
    List<TlmkBaseSB> lista = null;
    try {
      lista = tlmkBaseSBMapper.selectBase(codBase, estado);
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

      cantCli = tlmkBaseSBMapper.selectClientesProcesados(codBase, estado);

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

      final SimpleDateFormat formatFecha = new SimpleDateFormat("d/M/yy");

      // final NumberFormat nf = NumberFormat.getInstance(Locale.JAPAN);
      // final DecimalFormat df = new DecimalFormat("0.00");

      ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        TlmkBaseSB tlmkBaseSB;
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
              tlmkBaseSB = new TlmkBaseSB();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        // AGREGAR AL FINAL
        @Override
        public void endRow() {
          if (procesar) {
            tlmkBaseSB.setCodBase(codBase);
            tlmkBaseSBMapper.insert(tlmkBaseSB);
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

              if (column.equals(mapExcel.get("CLIENTE_ID"))) {
                tlmkBaseSB.setClienteId(formattedValue);
              } else if (column.equals(mapExcel.get("NRO_DOC"))) {
                tlmkBaseSB.setNroDoc(formattedValue);
              } else if (column.equals(mapExcel.get("TIPO_DOCUMENTO"))) {
                tlmkBaseSB.setTipoDocumento(formattedValue);
              } else if (column.equals(mapExcel.get("APELLIDO_PATERNO"))) {
                tlmkBaseSB.setApellidoPaterno(formattedValue);
              } else if (column.equals(mapExcel.get("APELLIDO_MATERNO"))) {
                tlmkBaseSB.setApellidoMaterno(formattedValue);
              } else if (column.equals(mapExcel.get("PRIMER_NOMBRE"))) {
                tlmkBaseSB.setPrimerNombre(formattedValue);
              } else if (column.equals(mapExcel.get("SEGUNDO_NOMBRE"))) {
                tlmkBaseSB.setSegundoNombre(formattedValue);
              } else if (column.equals(mapExcel.get("FECHA_NACIMIENTO"))) {
                tlmkBaseSB.setFechaNacimiento(formatFecha.parse(formattedValue));
              } else if (column.equals(mapExcel.get("EDAD"))) {
                tlmkBaseSB.setEdad(formattedValue);
              } else if (column.equals(mapExcel.get("SEXO"))) {
                tlmkBaseSB.setSexo(formattedValue);
              } else if (column.equals(mapExcel.get("ESTADO_CIVIL"))) {
                tlmkBaseSB.setEstadoCivil(formattedValue);
              } else if (column.equals(mapExcel.get("NACIONALIDAD"))) {
                tlmkBaseSB.setNacionalidad(formattedValue);
              } else if (column.equals(mapExcel.get("DIRECCION"))) {
                tlmkBaseSB.setDireccion(formattedValue);
              } else if (column.equals(mapExcel.get("DEPARTAMENTO"))) {
                tlmkBaseSB.setDepartamento(formattedValue);
              } else if (column.equals(mapExcel.get("PROVINCIA"))) {
                tlmkBaseSB.setProvincia(formattedValue);
              } else if (column.equals(mapExcel.get("DISTRITO"))) {
                tlmkBaseSB.setDistrito(formattedValue);
              } else if (column.equals(mapExcel.get("UBIGEO"))) {
                tlmkBaseSB.setUbigeo(formattedValue);
              } else if (column.equals(mapExcel.get("TELF_DOMICILIO"))) {
                tlmkBaseSB.setTelfDomicilio(formattedValue);
              } else if (column.equals(mapExcel.get("TELEF_CORRESPONDENCIA"))) {
                tlmkBaseSB.setTelefCorrespondencia(formattedValue);
              } else if (column.equals(mapExcel.get("TELF_VISA"))) {
                tlmkBaseSB.setTelfVisa(formattedValue);
              } else if (column.equals(mapExcel.get("TELF_CASA"))) {
                tlmkBaseSB.setTelfCasa(formattedValue);
              } else if (column.equals(mapExcel.get("TELF_RETAIL"))) {
                tlmkBaseSB.setTelfRetail(formattedValue);
              } else if (column.equals(mapExcel.get("TELF_LIMA"))) {
                tlmkBaseSB.setTelfLima(formattedValue);
              } else if (column.equals(mapExcel.get("TELF_PROVINCIA"))) {
                tlmkBaseSB.setTelfProvincia(formattedValue);
              } else if (column.equals(mapExcel.get("TELF_TRABAJO"))) {
                tlmkBaseSB.setTelfTrabajo(formattedValue);
              } else if (column.equals(mapExcel.get("NRO_CUENTA"))) {
                tlmkBaseSB.setNroCuenta(formattedValue);
              } else if (column.equals(mapExcel.get("NRO_TARJETA"))) {
                tlmkBaseSB.setNroTarjeta(formattedValue);
              } else if (column.equals(mapExcel.get("TIPO_TARJETA"))) {
                tlmkBaseSB.setTipoTarjeta(formattedValue);
              } else if (column.equals(mapExcel.get("LIMITE_CREDITO_PEN"))) {
                tlmkBaseSB.setLimiteCreditoPen(formattedValue);
              } else if (column.equals(mapExcel.get("LIMITE_CREDITO_DOLARES"))) {
                tlmkBaseSB.setLimiteCreditoDolares(formattedValue);
              } else if (column.equals(mapExcel.get("COD_CICLO"))) {
                tlmkBaseSB.setCodCiclo(formattedValue);
              } else if (column.equals(mapExcel.get("FECHA_VENCIMIENTO"))) {
                tlmkBaseSB.setFechaVencimiento(formatFecha.parse(formattedValue));
              } else if (column.equals(mapExcel.get("CLIENTETH"))) {
                tlmkBaseSB.setClienteth(formattedValue);
              } else if (column.equals(mapExcel.get("COMENTARIO1_CARDIF"))) {
                tlmkBaseSB.setComentario1Cardif(formattedValue);
              } else if (column.equals(mapExcel.get("COMENTARIO2_CARDIF"))) {
                tlmkBaseSB.setComentario2Cardif(formattedValue);
              } else if (column.equals(mapExcel.get("COMENTARIO3_CARDIF"))) {
                tlmkBaseSB.setComentario3Cardif(formattedValue);
              } else if (column.equals(mapExcel.get("COMENTARIO4_CARDIF"))) {
                tlmkBaseSB.setComentario4Cardif(formattedValue);
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
