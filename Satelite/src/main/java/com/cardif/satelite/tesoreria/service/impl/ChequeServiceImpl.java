package com.cardif.satelite.tesoreria.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cardif.satelite.configuracion.dao.ParametroMapper;
import com.cardif.satelite.model.FirmantePar;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.dao.ChequeMapper;
import com.cardif.satelite.tesoreria.dao.FirmanteMapper;
import com.cardif.satelite.tesoreria.dao.FirmanteParMapper;
import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.model.Firmante;
import com.cardif.satelite.tesoreria.service.ChequeService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.sunsystems.controller.SunsystemsController;
import com.cardif.sunsystems.util.NumberToWords;
import com.cardif.sunsystems.util.Utilidades;

@Service("chequeService")
public class ChequeServiceImpl implements ChequeService, Serializable {

  private static final long serialVersionUID = 1L;
  public static final Logger logger = Logger.getLogger(ChequeServiceImpl.class);

  @Autowired
  private ParametroMapper parametroMapper;

  @Autowired
  private ChequeMapper chequeMapper;

  @Autowired
  private FirmanteParMapper firmanteParMapper;

  @Autowired
  private FirmanteMapper firmanteMapper;

  @Override
  public boolean sendMail(String recipients, String subject, String body) throws AddressException, MessagingException {
    boolean result = false;
    final String username = "pe-alerts@cardif.com.pe";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "false");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp-app.cardifnet.com");
    props.put("mail.smtp.port", "25");

    Session session = Session.getInstance(props);

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));

      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

      message.setSubject(subject);
      message.setText(body);
      Transport.send(message);
      result = true;
    } catch (MessagingException e) {
      logger.error(e);
      result = false;

    }
    return result;
  }

  @Override
  public List<String> obtenerBancos() {
    try {

      List<String> bancos = parametroMapper.selectBancos();
      logger.info("Número de registros encontrados: " + bancos.size());
      return bancos;

    } catch (Exception e) {
      logger.error(e);
      return new ArrayList<String>();
    }
  }

  @Override
  public List<Cheque> buscar(String banco, String numeroCheque, String fechaDesde, String fechaHasta) {

    try {
      List<Cheque> cheques = new ArrayList<Cheque>();

      List<com.cardif.sunsystems.mapeo.impresionCheques.SSC.Payload.Ledger.Line> lineas = null;

      String payload = generarPayloadTipoCambio(fechaDesde, fechaHasta, banco, numeroCheque);
      logger.info(payload);

      SunsystemsController sun = SunsystemsController.getInstance();
      String resultadoXml = sun.extraerChequesSun(payload);
      logger.info(resultadoXml);

      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.impresionCheques.ObjectFactory.class);

      Unmarshaller unmarshaller = jc.createUnmarshaller();

      StringReader reader = new StringReader(resultadoXml);

      logger.info("Antes de Unmarshall");
      com.cardif.sunsystems.mapeo.impresionCheques.SSC expenseObj = (com.cardif.sunsystems.mapeo.impresionCheques.SSC) unmarshaller.unmarshal(reader);
      logger.info("Luego de Unmarshall");

      if (expenseObj.getPayload().getLedger() instanceof com.cardif.sunsystems.mapeo.impresionCheques.SSC.Payload.Ledger) {
        lineas = expenseObj.getPayload().getLedger().getLine();
        logger.info("Recuperando Lista");
        logger.info("ELEMENTOS DEL SELECT (Registros: " + lineas.size() + ")");
        int x = 0;
        for (com.cardif.sunsystems.mapeo.impresionCheques.SSC.Payload.Ledger.Line line : lineas) {
          x++;
          logger.info(x + "---Nombre Benef: " + line.getDescription());
          logger.info(x + "---Nombre Banco: " + line.getLinkReference3());
          logger.info(x + "---Importe: " + line.getTransactionAmount());
          logger.info(x + "---Cheque: " + line.getTransactionReference());
          logger.info(x + "---Moneda: " + line.getCurrencyCode());
          logger.info(x + "---Diario: " + line.getJournalNumber());
          logger.info(x + "---Linea diario: " + line.getJournalLineNumber());
          Cheque cheque = new Cheque();
          cheque.setNombreBeneficiario(line.getDescription());
          cheque.setBanco(line.getLinkReference3());
          cheque.setImporte(Utilidades.redondear(2, BigDecimal.valueOf(line.getTransactionAmount())));
          cheque.setNumero(line.getTransactionReference());
          cheque.setTipoMoneda(line.getCurrencyCode());
          cheque.setFechaRegistro(SateliteUtil.getDateFromString(line.getTransactionDate()));
          cheque.setDiario(line.getJournalNumber());
          cheque.setLineaDiario(line.getJournalLineNumber());
          cheques.add(cheque);
        }
      }
      return cheques;
    } catch (Exception e) {
      logger.error(e);
      return new ArrayList<Cheque>();
    }

  }

  private String generarPayloadTipoCambio(String fechaDesde, String fechaHasta, String banco, String numCheque) throws Exception {
    Document document = null;
    document = loadXMLFromResource("/com/cardif/sunsystems/plantillas/impresionCheques.xml");
    NodeList nodes = document.getElementsByTagName("Item");
    logger.info("Nodes.getLength(): " + nodes.getLength());
    List<Node> lista = new ArrayList<Node>();
    for (int j = 0; j < nodes.getLength(); j++) {
      Node node = nodes.item(j);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap atributos = node.getAttributes();
        if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/TransactionReference")) {
          if (numCheque == null || numCheque.equals("")) {
            lista.add(node);
          } else {
            atributos.getNamedItem("value").setNodeValue(numCheque);
          }
        } else if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/LinkReference3")) {
          if (banco == null || banco.equals("")) {
            lista.add(node);
          } else {
            atributos.getNamedItem("value").setNodeValue(banco);
          }
        } else if (atributos.getNamedItem("name").getNodeValue().equals("/Ledger/Line/TransactionDate")) {
          if (fechaDesde.equals("") || fechaHasta.equals("")) {
            lista.add(node);
            // node.getParentNode().removeChild(node);
          } else {
            atributos.getNamedItem("minvalue").setNodeValue(fechaDesde);
            atributos.getNamedItem("maxvalue").setNodeValue(fechaHasta);
          }
        }
      }

    }

    for (Node node : lista) {
      node.getParentNode().removeChild(node);
    }

    return documenttoString(document);
  }

  private Document loadXMLFromResource(String xml) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputStream in = getClass().getResourceAsStream(xml);
    return builder.parse(in);
  }

  private String documenttoString(Document document) throws Exception {
    DOMSource domSource = new DOMSource(document);
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    StringWriter sw = new StringWriter();
    StreamResult sr = new StreamResult(sw);
    transformer.transform(domSource, sr);
    return sw.toString();
  }

  @Override
  public List<Cheque> filtrarSeleccionado(List<Cheque> cheques) {
    List<Cheque> chequesSeleccionados = new ArrayList<Cheque>();
    for (Cheque cheque : cheques) {
      if (cheque.getSeleccionado() != null && cheque.getSeleccionado().equals(Boolean.TRUE)) {
        chequesSeleccionados.add(cheque);
      }
    }
    return chequesSeleccionados;
  }

  @Override
  public String asignar(List<Cheque> cheques, String usuario, Long par) {
    logger.info("Asignando");

    List<Cheque> chequesSeleccionados = filtrarSeleccionado(cheques);

    logger.info("Número de cheques seleccionados: " + chequesSeleccionados.size());

    if (null == par || par.longValue() == 0) {
      return "Debe seleccionar un par de firmantes";
    }
    try {

      BigDecimal totalSoles = new BigDecimal(0);
      BigDecimal totalDolares = new BigDecimal(0);
      if (chequesSeleccionados.size() == 0) {
        return "No ha seleccionado ningun registro";
      } else {

        SunsystemsController sun = SunsystemsController.getInstance();

        for (Cheque cheque : chequesSeleccionados) {

          cheque.setCodigoPar(par);
          cheque.setEstadoCheque(SateliteConstants.ESTADO_PENDIENTE);
          cheque.setUsuarioAsignacion(usuario);

          cheque.setFechaAsignacion(new Date());
          cheque.setEstadoFirmante1(SateliteConstants.ESTADO_PENDIENTE);
          cheque.setEstadoFirmante2(SateliteConstants.ESTADO_PENDIENTE);
          cheque.setUsuarioCreacion(usuario);

          cheque.setFechaCreacion(new Date());

          chequeMapper.insertar(cheque);
          sun.marcarLineasPagados(cheque.getDiario(), cheque.getLineaDiario(), "0");
          if (cheque.getTipoMoneda().equalsIgnoreCase("PEN")) {
            totalSoles = totalSoles.add(cheque.getImporte());
          } else if (cheque.getTipoMoneda().equalsIgnoreCase("USD")) {
            totalDolares = totalDolares.add(cheque.getImporte());

          }

        }

        FirmantePar firmantePar = firmanteParMapper.obtenerPar(par);

        Parametro parametro1 = parametroMapper.obtenerParametro("073", "D", "1");
        Parametro parametro2 = parametroMapper.obtenerParametro("073", "D", "2");

        String asunto = parametro1.getNomValor();
        String mensaje = parametro2.getNomValor();
        logger.info("Asunto: " + asunto);
        logger.info("Mensaje: " + mensaje);

        mensaje = mensaje.replace("CAN", String.valueOf(chequesSeleccionados.size()));
        totalSoles = Utilidades.redondear(2, totalSoles);
        totalDolares = Utilidades.redondear(2, totalDolares);

        mensaje = mensaje.replace("SOL", String.valueOf(totalSoles));
        mensaje = mensaje.replace("DOL", String.valueOf(totalDolares));

        boolean resultado = sendMail(firmantePar.getCorreo1() + "," + firmantePar.getCorreo2(), asunto, mensaje);
        logger.info(resultado);

        return "Los cheques han sido registrados";
      }
    } catch (Exception e) {
      logger.error(e);
      return "Error al registrar los cheques";
    }

  }

  @Override
  public String generarPrevisualizacion(List<Cheque> cheques, Long par, Boolean impresion) {

    List<Cheque> chequesSeleccionados = filtrarSeleccionado(cheques);

    logger.info("Número de cheques seleccionados: " + chequesSeleccionados.size());

    String path = SateliteUtil.getPath("/templates");
    String rutaPDF = path + File.separator + "PDF_CHEQUE_MAQUETA.pdf";
    if (impresion)
      rutaPDF = path + File.separator + "PDF_CHEQUE_IMPRESION.pdf";
    logger.info("Ruta: " + rutaPDF);

    if (null == par && !impresion) {
      return "Debe seleccionar un par de firmantes";
    }

    try {
      FirmantePar firmantePar = null;

      if (!impresion) {

        firmantePar = firmanteParMapper.obtenerPar(par);

        logger.info(firmantePar);
      }

      File archivo = new File(rutaPDF);
      if (archivo.exists()) {
        archivo.delete();
      }
      SateliteUtil.clearCookies();
      String formatoCheque = "";

      if (chequesSeleccionados.size() == 0) {
        return "No ha seleccionado ningun registro";
      }
      List<JasperPrint> listaPrint = new ArrayList<JasperPrint>();

      for (Cheque registro : chequesSeleccionados) {
        String banco = registro.getBanco();
        if (banco.equals("CHEQUE BBVA")) {

          if (registro.getTipoMoneda().equals("PEN")) {
            formatoCheque = "BBVA_PEN_A4.jrxml";
          } else if (registro.getTipoMoneda().equals("USD")) {
            formatoCheque = "BBVA_USD_A4.jrxml";
          }

        } else if (banco.equals("CHEQUE SBK")) {
          formatoCheque = "Scotia_A4.jrxml";

        } else if (banco.equals("CHEQUE IBK")) {
          formatoCheque = "INTER_A4.jrxml";

        } else if (banco.equals("CHEQUE BN")) {
          formatoCheque = "ChequeBancoNacion_A4.jrxml";
        }

        logger.info("formatoCheque: " + formatoCheque);

        JasperReport jasperReport = JasperCompileManager.compileReport(path + File.separator + formatoCheque);

        logger.info("La ruta del PDF sera: " + rutaPDF);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("benef", stringConAteriscos(registro.getNombreBeneficiario()));
        parameters.put("importeLetra", stringConAteriscos(NumberToWords.convert(Utilidades.redondear(2, registro.getImporte()).doubleValue())));
        parameters.put("dia", "dd");
        parameters.put("mes", "mm");
        parameters.put("anho", "yyyy");

        if (impresion) {
          String fecha = SateliteUtil.getFormatDate(new Date());
          logger.info("Fecha de impresion: " + fecha);
          parameters.put("dia", fecha.substring(0, 2));
          parameters.put("mes", fecha.substring(2, 4));
          parameters.put("anho", fecha.substring(4));
        }

        BigDecimal importe = Utilidades.redondear(2, registro.getImporte());
        String pattern = "###,###.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        double valor = importe.doubleValue();
        decimalFormat.applyPattern("###,###.00");
        String format = decimalFormat.format(valor); // registro.getImporte()
        format = format.replace('.', ' ');
        format = format.replace(',', '.');
        format = format.replace(' ', ',');
        parameters.put("importe", format);

        InputStream imagen1 = null;
        InputStream imagen2 = null;

        if (impresion) {
          firmantePar = firmanteParMapper.obtenerPar(registro.getCodigoPar());

          Firmante firmante1 = firmanteMapper.buscarFirmante(firmantePar.getCodigoFirmante1());
          imagen1 = new ByteArrayInputStream(firmante1.getFirma());

          Firmante firmante2 = firmanteMapper.buscarFirmante(firmantePar.getCodigoFirmante2());
          imagen2 = new ByteArrayInputStream(firmante2.getFirma());
        }
        parameters.put("firma1", firmantePar.getFirmante1());
        parameters.put("firma2", firmantePar.getFirmante2());

        parameters.put("imagen1", imagen1);
        parameters.put("imagen2", imagen2);

        logger.info("MAP: " + parameters);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        logger.info("jasperPrint: " + jasperPrint);
        listaPrint.add(jasperPrint);

        logger.info("La hoja PDF ha sido generada");

      }
      JRPdfExporter exp = new JRPdfExporter();
      logger.info("listaPrint: " + listaPrint);
      exp.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, listaPrint);
      logger.info("rutaPDF: " + rutaPDF);
      exp.setParameter(JRPdfExporterParameter.OUTPUT_FILE, new File(rutaPDF));
      logger.info("antes de Exportar (exportReport): " + rutaPDF);
      exp.exportReport();

      if (impresion) {
        logger.info("antes de Imprimir (imprimir(rutaPDF)): " + rutaPDF);
        imprimir(rutaPDF);
      }

      logger.info("Fin");

      return SateliteConstants.MENSAJE_PDF_EXITO;

    } catch (Exception e) {

      logger.error(e.getMessage(), e);
      return "Ocurrio un error al generar el PDF";

    }

  }

  private void imprimir(String rutaPDF) {

    try {
      Parametro parametro = parametroMapper.obtenerParametro("076", "D", "1");
      logger.info("parametro: " + parametro.getNomValor());
      String[] cmd = { "print", "/D:" + parametro.getNomValor(), rutaPDF }; // Comando de impresion

      for (int i = 0; i < cmd.length; i++) {
        logger.info("cmd[" + i + "]: " + cmd[i]);
      }

      Runtime.getRuntime().exec(cmd);
    } catch (IOException ioe) {
      logger.info(ioe);
    }
  }

  private String stringConAteriscos(String x) {
    String result = x;
    int size = x.length();
    int falta = 40 - size;

    if (!(falta % 2 == 0)) {
      falta++;
    }

    falta /= 2;
    for (int i = 0; i < falta; i++) {
      result = "*" + result + "*";
    }

    logger.info("Tamaño Asteriscos: " + result.length());
    return result;
  }

  @Override
  public Firmante buscarFirmante(String usuario) {
    try {
      Firmante firmante = firmanteMapper.buscarFirmantePorUsuario(usuario);
      return firmante;
    } catch (Exception e) {
      logger.info(e);
      return null;
    }
  }

  @Override
  public List<Cheque> buscarChequesPendientes(Firmante firmante) {
    try {

      Long codigoFirmante = firmante.getCodigo();
      List<Cheque> cheques = chequeMapper.buscarChequesPendientes(codigoFirmante);
      return cheques;
    } catch (Exception e) {
      logger.error(e);
      return new ArrayList<Cheque>();
    }
  }

  @Override
  public List<Cheque> buscarChequesRechazados(Firmante firmante) {
    try {

      Long codigoFirmante = firmante.getCodigo();
      List<Cheque> cheques = chequeMapper.buscarChequesRechazados(codigoFirmante);
      return cheques;
    } catch (Exception e) {
      logger.error(e);
      return new ArrayList<Cheque>();
    }
  }

  @Override
  public String aprobarCheques(List<Cheque> chequesAprobar, Firmante firmante) {
    try {

      for (Cheque cheque : chequesAprobar) {

        cheque.setUsuarioModificacion(firmante.getUsuario());
        cheque.setFechaModificacion(new Date());

        if (cheque.getCodigoFirmante1().equals(firmante.getCodigo())) {

          cheque.setUsuarioAprobacion1(firmante.getUsuario());
          cheque.setFechaAprobacion1(new Date());
          cheque.setEstadoFirmante1(SateliteConstants.ESTADO_APROBADO);

        } else {
          cheque.setUsuarioAprobacion2(firmante.getUsuario());
          cheque.setFechaAprobacion2(new Date());
          cheque.setEstadoFirmante2(SateliteConstants.ESTADO_APROBADO);

        }

        if (cheque.getEstadoFirmante1().equalsIgnoreCase(SateliteConstants.ESTADO_APROBADO) && cheque.getEstadoFirmante2().equalsIgnoreCase(SateliteConstants.ESTADO_APROBADO)) {
          cheque.setEstadoCheque(SateliteConstants.ESTADO_APROBADO);
        }

        int rows = chequeMapper.actualizar(cheque);
        logger.info("Número de registros actualizados: " + rows);
      }

      if (chequesAprobar.size() > 1) {
        return "Los cheques fueron aprobados con éxito";
      } else {
        return "El cheque se aprobó con éxito";
      }

    } catch (Exception e) {
      logger.error(e);
      return "Ocurrió un error al aprobar";

    }
  }

  @Override
  public String rechazarCheques(List<Cheque> chequesRechazar, Firmante firmante, String motivoRechazo) {
    try {
      for (Cheque cheque : chequesRechazar) {
        cheque.setUsuarioModificacion(firmante.getUsuario());
        cheque.setFechaModificacion(new Date());
        cheque.setEstadoCheque(SateliteConstants.ESTADO_RECHAZADO);
        cheque.setMotivoRechazo(motivoRechazo);
        if (cheque.getCodigoFirmante1().equals(firmante.getCodigo())) {
          cheque.setEstadoFirmante1(SateliteConstants.ESTADO_RECHAZADO);
        } else {
          cheque.setEstadoFirmante2(SateliteConstants.ESTADO_RECHAZADO);
        }
        int rows = chequeMapper.actualizar(cheque);
        logger.info("Número de registros actualizados: " + rows);
      }
      return "El cheque se rechazó con éxito";
    } catch (Exception e) {
      logger.error(e);
      return "Ocurrió un error al rechazar";
    }
  }

  @Override
  public boolean anularCheque(Cheque cheque, String usuario) {
    try {
      logger.info("Anulando cheque de codigo: " + cheque.getCodigo());
      logger.info("El usuario es: " + usuario);
      logger.info("El motivo es:" + cheque.getMotivoAnulacion());
      chequeMapper.anularCheque(cheque.getCodigo(), usuario, cheque.getMotivoAnulacion(), new Date());

      FirmantePar firmantePar = firmanteParMapper.obtenerPar(cheque.getCodigoPar());
      Parametro parametroAsunto = null;
      Parametro parametroMensaje = null;

      logger.info("El estado es: " + cheque.getEstadoCheque());

      if (cheque.getEstadoCheque().equalsIgnoreCase(SateliteConstants.ESTADO_IMPRESO)) {
        parametroMensaje = parametroMapper.obtenerParametro("078", "D", "2");
        parametroAsunto = parametroMapper.obtenerParametro("078", "D", "1");

      } else {
        parametroMensaje = parametroMapper.obtenerParametro("074", "D", "2");
        parametroAsunto = parametroMapper.obtenerParametro("074", "D", "1");

      }

      String asunto = parametroAsunto.getNomValor();

      StringBuilder sb = new StringBuilder();
      sb.append(parametroMensaje.getNomValor());
      sb.append("\n\nNº Cheque: ");
      sb.append(cheque.getNumero());
      sb.append("\nBanco: ");
      sb.append(cheque.getBanco());
      sb.append("\nNombre del Beneficiario: ");
      sb.append(cheque.getNombreBeneficiario());
      sb.append("\nMoneda: ");
      sb.append(cheque.getTipoMoneda());
      sb.append("\nImporte: ");
      sb.append(cheque.getImporte());
      sb.append("\nEstado: ");
      sb.append(SateliteUtil.getEstado(cheque.getEstadoCheque()));
      sb.append("\nMotivo de anulación: ");
      sb.append(cheque.getMotivoAnulacion());

      String mensaje = sb.toString();
      logger.info("Asunto: " + asunto);
      logger.info("Mensaje: " + mensaje);

      sendMail(firmantePar.getCorreo1() + "," + firmantePar.getCorreo2(), asunto, mensaje);

      return true;
    } catch (AddressException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    } catch (MessagingException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return false;

  }

  @Override
  public List<Cheque> buscarCheques(String estado) {
    try {
      estado = SateliteUtil.resetString(estado);
      List<Cheque> cheques = chequeMapper.listarCheques(estado);
      logger.info("Número de registros encontrados: " + cheques.size());
      return cheques;
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return new ArrayList<Cheque>();
  }

  @Override
  public List<FirmantePar> buscarPares(Cheque cheque) {
    logger.info("Inicio");

    logger.info("Estado 1: " + cheque.getEstadoFirmante1());
    logger.info("Estado 2: " + cheque.getEstadoFirmante2());

    // Pendiente Pendiente

    List<FirmantePar> pares = new ArrayList<FirmantePar>();

    try {

      // FirmantePar par = firmanteParMapper.obtenerPar(cheque.getCodigoPar());
      FirmantePar par = firmanteParMapper.obtenerParConInactivos(cheque.getCodigoPar());
      if (esPendiente(cheque.getEstadoFirmante1()) && esPendiente(cheque.getEstadoFirmante2())) {

        pares = firmanteParMapper.buscarPares(null);
        cheque.setIndicador(SateliteConstants.AMBOS_FIRMANTES);
        // Aprobado Pendiente
      } else if (esAprobado(cheque.getEstadoFirmante1()) && esPendiente(cheque.getEstadoFirmante2())) {
        pares = firmanteParMapper.buscarPares(par.getCodigoFirmante1());
        cheque.setIndicador(SateliteConstants.FIRMANTE_2);
        // Pendiente Aprobado

      } else if (esPendiente(cheque.getEstadoFirmante1()) && esAprobado(cheque.getEstadoFirmante2())) {
        pares = firmanteParMapper.buscarPares(par.getCodigoFirmante2());
        cheque.setIndicador(SateliteConstants.FIRMANTE_1);
      }

    } catch (Exception e) {
      logger.error(e);
    }

    logger.info("Fin");

    return pares;
  }

  private boolean esPendiente(String estado) {
    return SateliteConstants.ESTADO_PENDIENTE.equalsIgnoreCase(estado);
  }

  private boolean esAprobado(String estado) {
    return SateliteConstants.ESTADO_APROBADO.equalsIgnoreCase(estado);
  }

  @Override
  public boolean reactivarCheque(Cheque cheque, String usuario) {
    try {

      if (cheque.getEstadoFirmante1().equalsIgnoreCase(SateliteConstants.ESTADO_RECHAZADO)) {
        int cont = chequeMapper.reactivarFirmante1(cheque.getCodigo(), usuario, new Date());
        logger.info("Número de registros actualizados: " + cont);
      }
      if (cheque.getEstadoFirmante2().equalsIgnoreCase(SateliteConstants.ESTADO_RECHAZADO)) {
        int cont = chequeMapper.reactivarFirmante2(cheque.getCodigo(), usuario, new Date());
        logger.info("Número de registros actualizados: " + cont);
      }
      return true;
    } catch (Exception e) {
      logger.error(e);
    }

    return false;
  }

  @Override
  public boolean reasignarCheque(Cheque cheque, String usuario) {
    try {

      logger.info("Indicador: " + cheque.getIndicador());
      int cont = chequeMapper.reasignarCheque(cheque.getCodigo(), cheque.getCodigoPar(), usuario, new Date(), cheque.getMotivoReasignacion());
      logger.info("Número de registros actualizados: " + cont);

      Parametro parametroMensaje = parametroMapper.obtenerParametro("075", "D", "2");
      Parametro parametroAsunto = parametroMapper.obtenerParametro("075", "D", "1");

      String asunto = parametroAsunto.getNomValor();
      String mensaje = parametroMensaje.getNomValor();

      mensaje = mensaje.replace("CAN", "1");

      BigDecimal totalSoles = new BigDecimal(0);
      BigDecimal totalDolares = new BigDecimal(0);

      if (cheque.getTipoMoneda().equalsIgnoreCase("PEN")) {
        totalSoles = totalSoles.add(cheque.getImporte());
      } else if (cheque.getTipoMoneda().equalsIgnoreCase("USD")) {
        totalDolares = totalDolares.add(cheque.getImporte());
      }

      totalSoles = Utilidades.redondear(2, totalSoles);
      totalDolares = Utilidades.redondear(2, totalDolares);

      mensaje = mensaje.replace("SOL", String.valueOf(totalSoles));
      mensaje = mensaje.replace("DOL", String.valueOf(totalDolares));
      FirmantePar firmantePar = firmanteParMapper.obtenerPar(cheque.getCodigoPar());

      switch (cheque.getIndicador()) {
        case SateliteConstants.AMBOS_FIRMANTES:
          logger.info("Enviar correo a ambos firmantes");
          mensaje = mensaje.replace("MOT", "los apoderados FIRMANTE1 y FIRMANTE2 no se encuentran disponibles");
          mensaje = mensaje.replace("FIRMANTE1", cheque.getNombreFirmante1());
          mensaje = mensaje.replace("FIRMANTE2", cheque.getNombreFirmante2());
          sendMail(firmantePar.getCorreo1() + "," + firmantePar.getCorreo2(), asunto, mensaje);

          break;
        case SateliteConstants.FIRMANTE_1:

          logger.info("Enviar correo al firmante 1");

          mensaje = mensaje.replace("MOT", "el apoderado FIRMANTE no se encuentra disponible");
          mensaje = mensaje.replace("FIRMANTE", cheque.getNombreFirmante1());
          sendMail(firmantePar.getCorreo1(), asunto, mensaje);
          break;
        case SateliteConstants.FIRMANTE_2:
          logger.info("Enviar correo al firmante 2");
          mensaje = mensaje.replace("MOT", "el apoderado FIRMANTE no se encuentra disponible");
          mensaje = mensaje.replace("FIRMANTE", cheque.getNombreFirmante2());
          sendMail(firmantePar.getCorreo2(), asunto, mensaje);
          break;
        default:
          break;
      }

      return true;
    } catch (Exception e) {
      logger.error(e);
    }

    return false;
  }

  @Override
  public boolean imprimirCheques(List<Cheque> seleccionados, String usuario) {
    try {

      for (Cheque cheque : seleccionados) {
        int cont = chequeMapper.imprimirCheque(cheque.getCodigo(), usuario, new Date());
        logger.info("Número de registros actualizados: " + cont);
      }

      return true;
    } catch (Exception e) {
      logger.error(e);
    }

    return false;
  }
}
