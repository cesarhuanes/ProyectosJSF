package com.cardif.satelite.siniestro.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.commons.lang.StringUtils;

import com.cardif.satelite.model.SiniDatosCafae;
import com.cardif.satelite.siniestro.bean.ConsultaSiniestro;

public class SiniestroDataSourceService implements JRDataSource {
  private List<ConsultaSiniestro> lista;
  private int index = -1;
  private SiniDatosCafae siniDatosCafae;

  public SiniestroDataSourceService(List<ConsultaSiniestro> lista) {
    super();
    this.lista = lista;
  }

  public Object getFieldValue(JRField field) throws JRException {
    String fieldName = field.getName();
    ConsultaSiniestro siniestro = lista.get(index);

    if ("item".equals(fieldName)) {
      return String.valueOf(index + 1);
    } else if ("nroSiniestro".equals(fieldName)) {
      return String.valueOf(siniestro.getNroSiniestro());
    } else if ("fechaRecepcion".equals(fieldName)) {
      // antes FecRecepcion
      return formatearFecha(siniestro.getFecUltDocumentacion());
    } else if ("contratoExpediente".equals(fieldName)) {
      // return siniDatosCafae.getRefCafae();
      String tipRefCafae = StringUtils.isBlank(siniestro.getTipRefCafae()) ? "" : siniestro.getTipRefCafae();
      String refCafae = StringUtils.isBlank(siniestro.getRefCafae()) ? "" : siniestro.getRefCafae();
      String contratoExpediente = "CON".equals(tipRefCafae) ? "C-" + refCafae : tipRefCafae + " " + refCafae;
      return contratoExpediente;
    } else if ("asegurado".equals(fieldName)) {
      return siniestro.getNomCompleto();
    } else if ("montoIndemnizar".equals(fieldName)) {

      if (siniestro.getImpPagos() == null) {
        return "0.00";
      } else {
        return formatearMonto(siniestro.getImpPagos().toString());
      }

    }
    return "";
  }

  public boolean next() throws JRException {
    return ++index < lista.size();
  }

  public SiniDatosCafae getSiniDatosCafae() {
    return siniDatosCafae;
  }

  public void setSiniDatosCafae(SiniDatosCafae siniDatosCafae) {
    this.siniDatosCafae = siniDatosCafae;
  }

  private String formatearFecha(Date fecha) {
    String fechaFormato = null;
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    if (fecha != null) {
      fechaFormato = DATE_FORMAT.format(fecha);
    }
    return fechaFormato;
  }

  private static String formatearMonto(String monto) {
    double valor = Double.valueOf(monto).doubleValue();
    DecimalFormat formato = new DecimalFormat("#,###,###,##0.00");
    String valorFormateado = formato.format(valor);
    String miles = "";
    if (valor >= 1000000) {
      miles = StringUtils.right(valorFormateado, 10);
      String resto = valorFormateado.substring(0, valorFormateado.length() - 10);
      resto = resto.replace(valorFormateado.charAt(valorFormateado.length() - 7), '\'');
      valorFormateado = resto + miles;
    }
    return valorFormateado;
  }

}
