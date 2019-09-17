package com.cardif.satelite.siniestro.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReporteSiniestro implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // SiniSiniestro
  private String nroSiniestro;
  private Date fecOcurrencia;
  private String mesOcurrencia;
  private Date fecNotificacion;
  private String mesNotificacion;
  private String codCobertura;
  private String codUbicacion;
  private String codEstado;
  private String codEstLegajo;
  private String codMotRechazoAgr;
  private String codMotRechazo;
  private String codResumen;
  private String cie10;
  private String diagnostico;
  private String nroCarta;
  private Date fecAprobRechazo;
  private String mesAprobRechazo;
  private Date fecRecepSocio;
  private Date fecUltDocum;
  private Integer canDiasLiquiRecha;
  private Integer canDiasPendientes;
  private String valEdadIngreso;
  private String valEdadPermanencia;
  private String valCarencia;
  private Short canSiniestro;
  private String observaciones;
  private String flagCargoCarta;
  private Date fecEntrega;
  private String codMotNoEnt;
  private Date fecSolicNuevaDirec;
  private Date fecReenvio;
  private Short nroEnvios;
  private String nroCaja;
  private String telReferencia;
  private String contacto;
  private String correo;
  private String dirDomicilio;
  private String codDepartamento;
  private String codProvincia;
  private String codDistrito;
  private String nomDepartamento;
  private String nomProvincia;
  private String nomDistrito;
  private String codTipSeguro;
  private String rangos;
  private String leyReservaPendiente;
  private String leyReservaPendiente1;
  private String nroDocumAsegurado;
  private String tipDocumAsegurado;
  private String nacionalidad;
  private String codGenero;
  private String nomAsegurado;
  private String apePatAsegurado;
  private String apeMatAsegurado;
  private String nombreCompleto;
  private Date fecNacimiento;
  private Short edaFecOcurrencia;
  private String codParAsegurado;
  private Date fecUltDocumSocio;
  private Date fecNotificacionMenor;
  private String mesNotificacionSocio;
  private Integer diasNotificacion;
  private Integer diasDocumentacion;
  
  // cambio para
  private Double impPagos;
  private Double impReserva;
  private Double reservaInicial;
  private String codMoneda;
  private String nroPlanilla;
  private Date fecEntregaOpCont;
  private Date fecEmiCheque;
  private Date fecEntCheque;
  private String usuCreacion;
  private Date fecCreacion;
  private String usuModificacion;
  private Date fecModificacion;
  // SiniDatosCafae
  private String ejeCafae;
  private String tipRefCafae;
  private String refCafae;
  private String tipDocBeneficiario;
  private String nroDocBeneficiario;
  private String nomBeneficiario;
  private String parentescoBenef;
  private String fondoCafae;
  private String flagMuNa;
  private String flagMuAc;
  private String flagIhp;
  private String flagSaDe;
  private String flagUtEs;
  private String flagReRe;
  private String flagTrRe;
  private String flagInToPe;
  private String flagDc;
  private String flagDs;
  private String nombTipRefCafae;
  // SiniRobo
  private String codSucRetiro;
  private BigDecimal impPreLiquid;
  private BigDecimal impGasMedicos;
  private String flagCelular;
  private String flagCartera;
  private String flagMaletin;
  private String flagBilletera;
  private String flagPortadocumentos;
  private String flagLentesOpticos;
  private String flagLentesDeSol;
  private String flagCosmeticos;
  private String flagLapicero;
  private String flagDni;
  private String flagMochila;
  private String flagReloj;
  private String flagDiscIpodMp3;
  private String flagPalmTabled;
  private String flagBolso;
  private String flagSillaAutoBb;
  private String flagCocheBb;
  private String flagDisco;
  private String flagLlanta;
  private String flagGastoMedico;
  private String flagMuerteAccid;
  private String flagLlaves;
  private String flagLibreDisp;
  // SiniDesempleo
  private BigDecimal impMaxCuota;
  private Short nroCuota;
  private String nombTipoTrabajador;
  // SiniRentaHosp
  private Short canDiasCn;
  private Short canDiasUci;
  private BigDecimal impCalcLiquid;
  private Short canDiasHospital;
  private String excepcion;
  // SiniDesgravamen
  private String nroTarjeta;
  private String tipTarjeta;
  private String autoseguro;
  
  // SiniPoliza
  private String codSocio;
  private String nroProducto;
  private String planPoliza;
  private String nroPoliza;
  private String nroCertificado;
  private Date fecIniVigencia;
  private Date fecFinVigencia;
  // SiniProducto
  private String nomProducto;
  private String codRamo;
  private String nomRamo;
  private Short codProductoAcsele;
  private Short codProductoSini;

  // Pais
  private String nomPais;

  public String getNroSiniestro() {
    return nroSiniestro;
  }

  public void setNroSiniestro(String nroSiniestro) {
    this.nroSiniestro = nroSiniestro;
  }

  public Date getFecOcurrencia() {
    return fecOcurrencia;
  }

  public void setFecOcurrencia(Date fecOcurrencia) {
    this.fecOcurrencia = fecOcurrencia;
  }

  public String getMesOcurrencia() {
    return mesOcurrencia;
  }

  public void setMesOcurrencia(String mesOcurrencia) {
    this.mesOcurrencia = mesOcurrencia;
  }

  public Date getFecNotificacion() {
    return fecNotificacion;
  }

  public void setFecNotificacion(Date fecNotificacion) {
    this.fecNotificacion = fecNotificacion;
  }

  public String getMesNotificacion() {
    return mesNotificacion;
  }

  public void setMesNotificacion(String mesNotificacion) {
    this.mesNotificacion = mesNotificacion;
  }

  public String getCodCobertura() {
    return codCobertura;
  }

  public void setCodCobertura(String codCobertura) {
    this.codCobertura = codCobertura;
  }

  public String getCodUbicacion() {
    return codUbicacion;
  }

  public void setCodUbicacion(String codUbicacion) {
    this.codUbicacion = codUbicacion;
  }

  public String getCodEstado() {
    return codEstado;
  }

  public void setCodEstado(String codEstado) {
    this.codEstado = codEstado;
  }

  public String getCodEstLegajo() {
    return codEstLegajo;
  }

  public void setCodEstLegajo(String codEstLegajo) {
    this.codEstLegajo = codEstLegajo;
  }

  public String getCodMotRechazoAgr() {
    return codMotRechazoAgr;
  }

  public void setCodMotRechazoAgr(String codMotRechazoAgr) {
    this.codMotRechazoAgr = codMotRechazoAgr;
  }

  public String getCodMotRechazo() {
    return codMotRechazo;
  }

  public void setCodMotRechazo(String codMotRechazo) {
    this.codMotRechazo = codMotRechazo;
  }

  public String getCodResumen() {
    return codResumen;
  }

  public void setCodResumen(String codResumen) {
    this.codResumen = codResumen;
  }

  public String getCie10() {
    return cie10;
  }

  public void setCie10(String cie10) {
    this.cie10 = cie10;
  }

  public String getDiagnostico() {
    return diagnostico;
  }

  public void setDiagnostico(String diagnostico) {
    this.diagnostico = diagnostico;
  }

  public String getNroCarta() {
    return nroCarta;
  }

  public void setNroCarta(String nroCarta) {
    this.nroCarta = nroCarta;
  }

  public Date getFecAprobRechazo() {
    return fecAprobRechazo;
  }

  public void setFecAprobRechazo(Date fecAprobRechazo) {
    this.fecAprobRechazo = fecAprobRechazo;
  }

  public String getMesAprobRechazo() {
    return mesAprobRechazo;
  }

  public void setMesAprobRechazo(String mesAprobRechazo) {
    this.mesAprobRechazo = mesAprobRechazo;
  }

  public Date getFecRecepSocio() {
    return fecRecepSocio;
  }

  public void setFecRecepSocio(Date fecRecepSocio) {
    this.fecRecepSocio = fecRecepSocio;
  }

  public Date getFecUltDocum() {
    return fecUltDocum;
  }

  public void setFecUltDocum(Date fecUltDocum) {
    this.fecUltDocum = fecUltDocum;
  }

  public Integer getCanDiasLiquiRecha() {
    return canDiasLiquiRecha;
  }

  public void setCanDiasLiquiRecha(Integer canDiasLiquiRecha) {
    this.canDiasLiquiRecha = canDiasLiquiRecha;
  }

  public Integer getCanDiasPendientes() {
    return canDiasPendientes;
  }

  public void setCanDiasPendientes(Integer canDiasPendientes) {
    this.canDiasPendientes = canDiasPendientes;
  }

  public String getValEdadIngreso() {
    return valEdadIngreso;
  }

  public void setValEdadIngreso(String valEdadIngreso) {
    this.valEdadIngreso = valEdadIngreso;
  }

  public String getValEdadPermanencia() {
    return valEdadPermanencia;
  }

  public void setValEdadPermanencia(String valEdadPermanencia) {
    this.valEdadPermanencia = valEdadPermanencia;
  }

  public String getValCarencia() {
    return valCarencia;
  }

  public void setValCarencia(String valCarencia) {
    this.valCarencia = valCarencia;
  }

  public Short getCanSiniestro() {
    return canSiniestro;
  }

  public void setCanSiniestro(Short canSiniestro) {
    this.canSiniestro = canSiniestro;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public String getFlagCargoCarta() {
    return flagCargoCarta;
  }

  public void setFlagCargoCarta(String flagCargoCarta) {
    this.flagCargoCarta = flagCargoCarta;
  }

  public Date getFecEntrega() {
    return fecEntrega;
  }

  public void setFecEntrega(Date fecEntrega) {
    this.fecEntrega = fecEntrega;
  }

  public String getCodMotNoEnt() {
    return codMotNoEnt;
  }

  public void setCodMotNoEnt(String codMotNoEnt) {
    this.codMotNoEnt = codMotNoEnt;
  }

  public Date getFecSolicNuevaDirec() {
    return fecSolicNuevaDirec;
  }

  public void setFecSolicNuevaDirec(Date fecSolicNuevaDirec) {
    this.fecSolicNuevaDirec = fecSolicNuevaDirec;
  }

  public Date getFecReenvio() {
    return fecReenvio;
  }

  public void setFecReenvio(Date fecReenvio) {
    this.fecReenvio = fecReenvio;
  }

  public Short getNroEnvios() {
    return nroEnvios;
  }

  public void setNroEnvios(Short nroEnvios) {
    this.nroEnvios = nroEnvios;
  }

  public String getNroCaja() {
    return nroCaja;
  }

  public void setNroCaja(String nroCaja) {
    this.nroCaja = nroCaja;
  }

  public String getTelReferencia() {
    return telReferencia;
  }

  public void setTelReferencia(String telReferencia) {
    this.telReferencia = telReferencia;
  }

  public String getContacto() {
    return contacto;
  }

  public void setContacto(String contacto) {
    this.contacto = contacto;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getDirDomicilio() {
    return dirDomicilio;
  }

  public void setDirDomicilio(String dirDomicilio) {
    this.dirDomicilio = dirDomicilio;
  }

  public String getCodDepartamento() {
    return codDepartamento;
  }

  public void setCodDepartamento(String codDepartamento) {
    this.codDepartamento = codDepartamento;
  }

  public String getCodProvincia() {
    return codProvincia;
  }

  public void setCodProvincia(String codProvincia) {
    this.codProvincia = codProvincia;
  }

  public String getCodDistrito() {
    return codDistrito;
  }

  public void setCodDistrito(String codDistrito) {
    this.codDistrito = codDistrito;
  }

  public String getCodTipSeguro() {
    return codTipSeguro;
  }

  public void setCodTipSeguro(String codTipSeguro) {
    this.codTipSeguro = codTipSeguro;
  }

  public String getRangos() {
    return rangos;
  }

  public void setRangos(String rangos) {
    this.rangos = rangos;
  }

  public String getLeyReservaPendiente() {
    return leyReservaPendiente;
  }

  public void setLeyReservaPendiente(String leyReservaPendiente) {
    this.leyReservaPendiente = leyReservaPendiente;
  }

  public String getLeyReservaPendiente1() {
    return leyReservaPendiente1;
  }

  public void setLeyReservaPendiente1(String leyReservaPendiente1) {
    this.leyReservaPendiente1 = leyReservaPendiente1;
  }

  public String getNroDocumAsegurado() {
    return nroDocumAsegurado;
  }

  public void setNroDocumAsegurado(String nroDocumAsegurado) {
    this.nroDocumAsegurado = nroDocumAsegurado;
  }

  public String getTipDocumAsegurado() {
    return tipDocumAsegurado;
  }

  public void setTipDocumAsegurado(String tipDocumAsegurado) {
    this.tipDocumAsegurado = tipDocumAsegurado;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }

  public String getCodGenero() {
    return codGenero;
  }

  public void setCodGenero(String codGenero) {
    this.codGenero = codGenero;
  }

  public String getNomAsegurado() {
    return nomAsegurado;
  }

  public void setNomAsegurado(String nomAsegurado) {
    this.nomAsegurado = nomAsegurado;
  }

  public String getApePatAsegurado() {
    return apePatAsegurado;
  }

  public void setApePatAsegurado(String apePatAsegurado) {
    this.apePatAsegurado = apePatAsegurado;
  }

  public String getApeMatAsegurado() {
    return apeMatAsegurado;
  }

  public void setApeMatAsegurado(String apeMatAsegurado) {
    this.apeMatAsegurado = apeMatAsegurado;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public Date getFecNacimiento() {
    return fecNacimiento;
  }

  public void setFecNacimiento(Date fecNacimiento) {
    this.fecNacimiento = fecNacimiento;
  }

  public Short getEdaFecOcurrencia() {
    return edaFecOcurrencia;
  }

  public void setEdaFecOcurrencia(Short edaFecOcurrencia) {
    this.edaFecOcurrencia = edaFecOcurrencia;
  }

  public String getCodParAsegurado() {
    return codParAsegurado;
  }

  public void setCodParAsegurado(String codParAsegurado) {
    this.codParAsegurado = codParAsegurado;
  }

  public String getCodMoneda() {
    return codMoneda;
  }

  public void setCodMoneda(String codMoneda) {
    this.codMoneda = codMoneda;
  }

  public String getNroPlanilla() {
    return nroPlanilla;
  }

  public void setNroPlanilla(String nroPlanilla) {
    this.nroPlanilla = nroPlanilla;
  }

  public Date getFecEntregaOpCont() {
    return fecEntregaOpCont;
  }

  public void setFecEntregaOpCont(Date fecEntregaOpCont) {
    this.fecEntregaOpCont = fecEntregaOpCont;
  }

  public Date getFecEmiCheque() {
    return fecEmiCheque;
  }

  public void setFecEmiCheque(Date fecEmiCheque) {
    this.fecEmiCheque = fecEmiCheque;
  }

  public Date getFecEntCheque() {
    return fecEntCheque;
  }

  public void setFecEntCheque(Date fecEntCheque) {
    this.fecEntCheque = fecEntCheque;
  }

  public String getUsuCreacion() {
    return usuCreacion;
  }

  public void setUsuCreacion(String usuCreacion) {
    this.usuCreacion = usuCreacion;
  }

  public Date getFecCreacion() {
    return fecCreacion;
  }

  public void setFecCreacion(Date fecCreacion) {
    this.fecCreacion = fecCreacion;
  }

  public String getUsuModificacion() {
    return usuModificacion;
  }

  public void setUsuModificacion(String usuModificacion) {
    this.usuModificacion = usuModificacion;
  }

  public Date getFecModificacion() {
    return fecModificacion;
  }

  public void setFecModificacion(Date fecModificacion) {
    this.fecModificacion = fecModificacion;
  }

  public String getEjeCafae() {
    return ejeCafae;
  }

  public void setEjeCafae(String ejeCafae) {
    this.ejeCafae = ejeCafae;
  }

  public String getTipRefCafae() {
    return tipRefCafae;
  }

  public void setTipRefCafae(String tipRefCafae) {
    this.tipRefCafae = tipRefCafae;
  }

  public String getRefCafae() {
    return refCafae;
  }

  public void setRefCafae(String refCafae) {
    this.refCafae = refCafae;
  }

  public String getTipDocBeneficiario() {
    return tipDocBeneficiario;
  }

  public void setTipDocBeneficiario(String tipDocBeneficiario) {
    this.tipDocBeneficiario = tipDocBeneficiario;
  }

  public String getNroDocBeneficiario() {
    return nroDocBeneficiario;
  }

  public void setNroDocBeneficiario(String nroDocBeneficiario) {
    this.nroDocBeneficiario = nroDocBeneficiario;
  }

  public String getNomBeneficiario() {
    return nomBeneficiario;
  }

  public void setNomBeneficiario(String nomBeneficiario) {
    this.nomBeneficiario = nomBeneficiario;
  }

  public String getParentescoBenef() {
    return parentescoBenef;
  }

  public void setParentescoBenef(String parentescoBenef) {
    this.parentescoBenef = parentescoBenef;
  }

  public String getFondoCafae() {
    return fondoCafae;
  }

  public void setFondoCafae(String fondoCafae) {
    this.fondoCafae = fondoCafae;
  }

  public String getFlagMuNa() {
    return flagMuNa;
  }

  public void setFlagMuNa(String flagMuNa) {
    this.flagMuNa = flagMuNa;
  }

  public String getFlagMuAc() {
    return flagMuAc;
  }

  public void setFlagMuAc(String flagMuAc) {
    this.flagMuAc = flagMuAc;
  }

  public String getFlagIhp() {
    return flagIhp;
  }

  public void setFlagIhp(String flagIhp) {
    this.flagIhp = flagIhp;
  }

  public String getFlagSaDe() {
    return flagSaDe;
  }

  public void setFlagSaDe(String flagSaDe) {
    this.flagSaDe = flagSaDe;
  }

  public String getFlagUtEs() {
    return flagUtEs;
  }

  public void setFlagUtEs(String flagUtEs) {
    this.flagUtEs = flagUtEs;
  }

  public String getFlagReRe() {
    return flagReRe;
  }

  public void setFlagReRe(String flagReRe) {
    this.flagReRe = flagReRe;
  }

  public String getFlagTrRe() {
    return flagTrRe;
  }

  public void setFlagTrRe(String flagTrRe) {
    this.flagTrRe = flagTrRe;
  }

  public String getFlagInToPe() {
    return flagInToPe;
  }

  public void setFlagInToPe(String flagInToPe) {
    this.flagInToPe = flagInToPe;
  }

  public String getFlagDc() {
    return flagDc;
  }

  public void setFlagDc(String flagDc) {
    this.flagDc = flagDc;
  }

  public String getFlagDs() {
    return flagDs;
  }

  public void setFlagDs(String flagDs) {
    this.flagDs = flagDs;
  }

  public String getCodSucRetiro() {
    return codSucRetiro;
  }

  public void setCodSucRetiro(String codSucRetiro) {
    this.codSucRetiro = codSucRetiro;
  }

  public BigDecimal getImpPreLiquid() {
    return impPreLiquid;
  }

  public void setImpPreLiquid(BigDecimal impPreLiquid) {
    this.impPreLiquid = impPreLiquid;
  }

  public BigDecimal getImpGasMedicos() {
    return impGasMedicos;
  }

  public void setImpGasMedicos(BigDecimal impGasMedicos) {
    this.impGasMedicos = impGasMedicos;
  }

  public String getFlagCelular() {
    return flagCelular;
  }

  public void setFlagCelular(String flagCelular) {
    this.flagCelular = flagCelular;
  }

  public String getFlagCartera() {
    return flagCartera;
  }

  public void setFlagCartera(String flagCartera) {
    this.flagCartera = flagCartera;
  }

  public String getFlagMaletin() {
    return flagMaletin;
  }

  public void setFlagMaletin(String flagMaletin) {
    this.flagMaletin = flagMaletin;
  }

  public String getFlagBilletera() {
    return flagBilletera;
  }

  public void setFlagBilletera(String flagBilletera) {
    this.flagBilletera = flagBilletera;
  }

  public String getFlagPortadocumentos() {
    return flagPortadocumentos;
  }

  public void setFlagPortadocumentos(String flagPortadocumentos) {
    this.flagPortadocumentos = flagPortadocumentos;
  }

  public String getFlagLentesOpticos() {
    return flagLentesOpticos;
  }

  public void setFlagLentesOpticos(String flagLentesOpticos) {
    this.flagLentesOpticos = flagLentesOpticos;
  }

  public String getFlagLentesDeSol() {
    return flagLentesDeSol;
  }

  public void setFlagLentesDeSol(String flagLentesDeSol) {
    this.flagLentesDeSol = flagLentesDeSol;
  }

  public String getFlagCosmeticos() {
    return flagCosmeticos;
  }

  public void setFlagCosmeticos(String flagCosmeticos) {
    this.flagCosmeticos = flagCosmeticos;
  }

  public String getFlagLapicero() {
    return flagLapicero;
  }

  public void setFlagLapicero(String flagLapicero) {
    this.flagLapicero = flagLapicero;
  }

  public String getFlagDni() {
    return flagDni;
  }

  public void setFlagDni(String flagDni) {
    this.flagDni = flagDni;
  }

  public String getFlagMochila() {
    return flagMochila;
  }

  public void setFlagMochila(String flagMochila) {
    this.flagMochila = flagMochila;
  }

  public String getFlagReloj() {
    return flagReloj;
  }

  public void setFlagReloj(String flagReloj) {
    this.flagReloj = flagReloj;
  }

  public String getFlagDiscIpodMp3() {
    return flagDiscIpodMp3;
  }

  public void setFlagDiscIpodMp3(String flagDiscIpodMp3) {
    this.flagDiscIpodMp3 = flagDiscIpodMp3;
  }

  public String getFlagPalmTabled() {
    return flagPalmTabled;
  }

  public void setFlagPalmTabled(String flagPalmTabled) {
    this.flagPalmTabled = flagPalmTabled;
  }

  public String getFlagBolso() {
    return flagBolso;
  }

  public void setFlagBolso(String flagBolso) {
    this.flagBolso = flagBolso;
  }

  public String getFlagSillaAutoBb() {
    return flagSillaAutoBb;
  }

  public void setFlagSillaAutoBb(String flagSillaAutoBb) {
    this.flagSillaAutoBb = flagSillaAutoBb;
  }

  public String getFlagCocheBb() {
    return flagCocheBb;
  }

  public void setFlagCocheBb(String flagCocheBb) {
    this.flagCocheBb = flagCocheBb;
  }

  public String getFlagDisco() {
    return flagDisco;
  }

  public void setFlagDisco(String flagDisco) {
    this.flagDisco = flagDisco;
  }

  public String getFlagLlanta() {
    return flagLlanta;
  }

  public void setFlagLlanta(String flagLlanta) {
    this.flagLlanta = flagLlanta;
  }

  public String getFlagGastoMedico() {
    return flagGastoMedico;
  }

  public void setFlagGastoMedico(String flagGastoMedico) {
    this.flagGastoMedico = flagGastoMedico;
  }

  public String getFlagMuerteAccid() {
    return flagMuerteAccid;
  }

  public void setFlagMuerteAccid(String flagMuerteAccid) {
    this.flagMuerteAccid = flagMuerteAccid;
  }

  public String getFlagLlaves() {
    return flagLlaves;
  }

  public void setFlagLlaves(String flagLlaves) {
    this.flagLlaves = flagLlaves;
  }

  public String getFlagLibreDisp() {
    return flagLibreDisp;
  }

  public void setFlagLibreDisp(String flagLibreDisp) {
    this.flagLibreDisp = flagLibreDisp;
  }

  public BigDecimal getImpMaxCuota() {
    return impMaxCuota;
  }

  public void setImpMaxCuota(BigDecimal impMaxCuota) {
    this.impMaxCuota = impMaxCuota;
  }

  public Short getNroCuota() {
    return nroCuota;
  }

  public void setNroCuota(Short nroCuota) {
    this.nroCuota = nroCuota;
  }

  public Short getCanDiasCn() {
    return canDiasCn;
  }

  public void setCanDiasCn(Short canDiasCn) {
    this.canDiasCn = canDiasCn;
  }

  public Short getCanDiasUci() {
    return canDiasUci;
  }

  public void setCanDiasUci(Short canDiasUci) {
    this.canDiasUci = canDiasUci;
  }

  public BigDecimal getImpCalcLiquid() {
    return impCalcLiquid;
  }

  public void setImpCalcLiquid(BigDecimal impCalcLiquid) {
    this.impCalcLiquid = impCalcLiquid;
  }

  public Short getCanDiasHospital() {
    return canDiasHospital;
  }

  public void setCanDiasHospital(Short canDiasHospital) {
    this.canDiasHospital = canDiasHospital;
  }

  public String getExcepcion() {
    return excepcion;
  }

  public void setExcepcion(String excepcion) {
    this.excepcion = excepcion;
  }

  public String getNroTarjeta() {
    return nroTarjeta;
  }

  public void setNroTarjeta(String nroTarjeta) {
    this.nroTarjeta = nroTarjeta;
  }

  public String getTipTarjeta() {
    return tipTarjeta;
  }

  public void setTipTarjeta(String tipTarjeta) {
    this.tipTarjeta = tipTarjeta;
  }

  public String getCodSocio() {
    return codSocio;
  }

  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio;
  }

  public String getNroProducto() {
    return nroProducto;
  }

  public void setNroProducto(String nroProducto) {
    this.nroProducto = nroProducto;
  }

  public String getPlanPoliza() {
    return planPoliza;
  }

  public void setPlanPoliza(String planPoliza) {
    this.planPoliza = planPoliza;
  }

  public String getNroPoliza() {
    return nroPoliza;
  }

  public void setNroPoliza(String nroPoliza) {
    this.nroPoliza = nroPoliza;
  }

  public String getNroCertificado() {
    return nroCertificado;
  }

  public void setNroCertificado(String nroCertificado) {
    this.nroCertificado = nroCertificado;
  }

  public Date getFecIniVigencia() {
    return fecIniVigencia;
  }

  public void setFecIniVigencia(Date fecIniVigencia) {
    this.fecIniVigencia = fecIniVigencia;
  }

  public Date getFecFinVigencia() {
    return fecFinVigencia;
  }

  public void setFecFinVigencia(Date fecFinVigencia) {
    this.fecFinVigencia = fecFinVigencia;
  }

  public String getNomProducto() {
    return nomProducto;
  }

  public void setNomProducto(String nomProducto) {
    this.nomProducto = nomProducto;
  }

  public String getCodRamo() {
    return codRamo;
  }

  public void setCodRamo(String codRamo) {
    this.codRamo = codRamo;
  }

  public Short getCodProductoAcsele() {
    return codProductoAcsele;
  }

  public void setCodProductoAcsele(Short codProductoAcsele) {
    this.codProductoAcsele = codProductoAcsele;
  }

  public Short getCodProductoSini() {
    return codProductoSini;
  }

  public void setCodProductoSini(Short codProductoSini) {
    this.codProductoSini = codProductoSini;
  }

  public String getNomRamo() {
    return nomRamo;
  }

  public void setNomRamo(String nomRamo) {
    this.nomRamo = nomRamo;
  }

  public String getNomDepartamento() {
    return nomDepartamento;
  }

  public String getNomProvincia() {
    return nomProvincia;
  }

  public String getNomDistrito() {
    return nomDistrito;
  }

  public void setNomDepartamento(String nomDepartamento) {
    this.nomDepartamento = nomDepartamento;
  }

  public void setNomProvincia(String nomProvincia) {
    this.nomProvincia = nomProvincia;
  }

  public void setNomDistrito(String nomDistrito) {
    this.nomDistrito = nomDistrito;
  }

  public Double getImpPagos() {
    return impPagos;
  }

  public void setImpPagos(Double impPagos) {
    this.impPagos = impPagos;
  }

  public Double getImpReserva() {
    return impReserva;
  }

  public void setImpReserva(Double impReserva) {
    this.impReserva = impReserva;
  }

  public Double getReservaInicial() {
    return reservaInicial;
  }

  public void setReservaInicial(Double reservaInicial) {
    this.reservaInicial = reservaInicial;
  }

  public String getNomPais() {
    return nomPais;
  }

  public void setNomPais(String nomPais) {
    this.nomPais = nomPais;
  }

  public String getNombTipRefCafae() {
    return nombTipRefCafae;
  }

  public void setNombTipRefCafae(String nombTipRefCafae) {
    this.nombTipRefCafae = nombTipRefCafae;
  }

  public String getAutoseguro() {
    return autoseguro;
  }

  public void setAutoseguro(String autoseguro) {
    this.autoseguro = autoseguro;
  }

  public String getNombTipoTrabajador() {
    return nombTipoTrabajador;
  }

  public void setNombTipoTrabajador(String nombTipoTrabajador) {
    this.nombTipoTrabajador = nombTipoTrabajador;
  }

public Date getFecUltDocumSocio() {
	return fecUltDocumSocio;
}

public void setFecUltDocumSocio(Date fecUltDocumSocio) {
	this.fecUltDocumSocio = fecUltDocumSocio;
}

public String getMesNotificacionSocio() {
	return mesNotificacionSocio;
}

public void setMesNotificacionSocio(String mesNotificacionSocio) {
	this.mesNotificacionSocio = mesNotificacionSocio;
}

public Integer getDiasNotificacion() {
	return diasNotificacion;
}

public void setDiasNotificacion(Integer diasNotificacion) {
	this.diasNotificacion = diasNotificacion;
}


public Date getFecNotificacionMenor() {
	return fecNotificacionMenor;
}

public void setFecNotificacionMenor(Date fecNotificacionMenor) {
	this.fecNotificacionMenor = fecNotificacionMenor;
}

public Integer getDiasDocumentacion() {
	return diasDocumentacion;
}

public void setDiasDocumentacion(Integer diasDocumentacion) {
	this.diasDocumentacion = diasDocumentacion;
}

 /* public Date getFecUltDocumSocio() {
	return fecUltDocumSocio;
  }

  public void setFecUltDocumSocio(Date fecUltDocumSocio) {
	this.fecUltDocumSocio = fecUltDocumSocio;
  }*/
  
  

}
