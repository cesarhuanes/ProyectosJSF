package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsultaMasterPrecio implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String codSocio;
  private String codMaster;
  private String canal;
  private String medioPago;
  private String uso;
  private String categoria;
  private String departamento;
  private String marca;
  private String modelo;
  private BigDecimal caso;
  private Integer nroAsientos;
  private BigDecimal primaTecnica;
  private BigDecimal primaPublica;
  private BigDecimal igv;
  private BigDecimal de;
  private BigDecimal fndComp;
  private BigDecimal comisionCobranza;
  private BigDecimal comisionSocioNeto;
  private BigDecimal comSocioPorcentaje;
  private BigDecimal fondoMKT;
  private BigDecimal fondoMktPorcentaje;
  private BigDecimal comBrokerNeto;
  private BigDecimal comBrokerPorcentaje;
  private BigDecimal prima;

  public String getCodSocio() {
    return codSocio;
  }

  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio;
  }

  public String getDepartamento() {
    return departamento;
  }

  public void setDepartamento(String departamento) {
    this.departamento = departamento;
  }

  public String getUso() {
    return uso;
  }

  public void setUso(String uso) {
    this.uso = uso;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getCanal() {
    return canal;
  }

  public void setCanal(String canal) {
    this.canal = canal;
  }

public String getCodMaster() {
	return codMaster;
}

public void setCodMaster(String codMaster) {
	this.codMaster = codMaster;
}

public String getMedioPago() {
	return medioPago;
}

public void setMedioPago(String medioPago) {
	this.medioPago = medioPago;
}

public Integer getNroAsientos() {
	return nroAsientos;
}

public void setNroAsientos(Integer nroAsientos) {
	this.nroAsientos = nroAsientos;
}

public BigDecimal getCaso() {
	return caso;
}

public void setCaso(BigDecimal caso) {
	this.caso = caso;
}

public BigDecimal getPrimaTecnica() {
	return primaTecnica;
}

public void setPrimaTecnica(BigDecimal primaTecnica) {
	this.primaTecnica = primaTecnica;
}

public BigDecimal getPrimaPublica() {
	return primaPublica;
}

public void setPrimaPublica(BigDecimal primaPublica) {
	this.primaPublica = primaPublica;
}

public BigDecimal getIgv() {
	return igv;
}

public void setIgv(BigDecimal igv) {
	this.igv = igv;
}

public BigDecimal getDe() {
	return de;
}

public void setDe(BigDecimal de) {
	this.de = de;
}

public BigDecimal getFndComp() {
	return fndComp;
}

public void setFndComp(BigDecimal fndComp) {
	this.fndComp = fndComp;
}

public BigDecimal getComisionCobranza() {
	return comisionCobranza;
}

public void setComisionCobranza(BigDecimal comisionCobranza) {
	this.comisionCobranza = comisionCobranza;
}

public BigDecimal getComisionSocioNeto() {
	return comisionSocioNeto;
}

public void setComisionSocioNeto(BigDecimal comisionSocioNeto) {
	this.comisionSocioNeto = comisionSocioNeto;
}

public BigDecimal getComSocioPorcentaje() {
	return comSocioPorcentaje;
}

public void setComSocioPorcentaje(BigDecimal comSocioPorcentaje) {
	this.comSocioPorcentaje = comSocioPorcentaje;
}

public BigDecimal getFondoMKT() {
	return fondoMKT;
}

public void setFondoMKT(BigDecimal fondoMKT) {
	this.fondoMKT = fondoMKT;
}

public BigDecimal getFondoMktPorcentaje() {
	return fondoMktPorcentaje;
}

public void setFondoMktPorcentaje(BigDecimal fondoMktPorcentaje) {
	this.fondoMktPorcentaje = fondoMktPorcentaje;
}

public BigDecimal getComBrokerNeto() {
	return comBrokerNeto;
}

public void setComBrokerNeto(BigDecimal comBrokerNeto) {
	this.comBrokerNeto = comBrokerNeto;
}

public BigDecimal getComBrokerPorcentaje() {
	return comBrokerPorcentaje;
}

public void setComBrokerPorcentaje(BigDecimal comBrokerPorcentaje) {
	this.comBrokerPorcentaje = comBrokerPorcentaje;
}

public BigDecimal getPrima() {
	return prima;
}

public void setPrima(BigDecimal prima) {
	this.prima = prima;
}




}
