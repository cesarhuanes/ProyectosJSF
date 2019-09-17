package com.cardif.satelite.model.satelite;

import java.util.Date;

public class DetalleTramaDiaria implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long idPoliza;
	private String nroPolizaPe;
	private String nroCorrelativo;
	private String codCorredor;
	private Date fecVenta;
	private String horaVenta;
	private Date fecCarga;
	private Date fecIniVigencia;
	private Date fecFinVigencia;
	private String placa;
	private String serie;
	private String motor;
	private String color;
	private Long codMarca;
	private String marca;
	private Long codModelo;
	private String modelo;
	private String codCategoriaClase;
	private String categoriaClase;
	private String codUsoVehiculo;
	private String usoVehiculo;
	private Integer anioFab;
	private Integer nroAsientos;
	private Long codVehiculo;
	private String nroCertificado;
	private String nroCertificadoAnt;
	private String pdfCodificadoSinFirmar;
	private String tipoPersona;
	private String tipDoc;
	private String numDoc;
	private String nomContrat;
	private String apelPateContrat;
	private String apelMateContrat;
	private String sexo;
	private Date fecNac;
	private String estadoCivil;
	private String codDepartamento;
	private String departamento;
	private String codProvincia;
	private String provincia;
	private String codDistrito;
	private String distrito;
	private String direccion;
	private String telFijo;
	private String telMovil;
	private String email;
	private String codDepartamentoDesp;
	private String departamentoDesp;
	private String codProvinciaDesp;
	private String provinciaDesp;
	private String codDistritoDesp;
	private String distritoDesp;
	private String direccionDesp;
	private String dirReferenciaDesp;
	private String franjaHorariaDesp;
	private Date fecDespacho;
	private String medioPago;
	private String moneda;
	private Double primaBruta;
	private Double primaTecnica;
	private Double importeCobro;
	private Double importeCobroDsc;
	private String numTarj;
	private Date fecVenciTarj;
	private String tipoTarj;
	private String codMaster;
	private String canalVenta;
	private String puntoVenta;
	private String nomVend;
	private Date fecCobro;
	private String subProd;
	private String propuesta;
	private String trx;
	private String voucher;
	private String codTienda;
	private String tarifDpto;
	private String propAnte;
	private String nuAp;
	private String cdRef;
	private String pos;
	private String lotePos;
	private String cdRect;
	private String estProp;
	private String desMedpag;
	private String dondePago;
	private Date fecCargaRef;
	private String nroCorrelativoRef;
	private String estado;
	private String estadoImpresion;
	private Long secArchivo;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getIdPoliza()
	{
		return idPoliza;
	}
	
	public void setIdPoliza(Long idPoliza)
	{
		this.idPoliza = idPoliza;
	}
	
	public String getNroPolizaPe()
	{
		return nroPolizaPe;
	}
	
	public void setNroPolizaPe(String nroPolizaPe)
	{
		this.nroPolizaPe = nroPolizaPe;
	}
	
	public String getNroCorrelativo()
	{
		return nroCorrelativo;
	}
	
	public void setNroCorrelativo(String nroCorrelativo)
	{
		this.nroCorrelativo = nroCorrelativo;
	}
	
	public String getCodCorredor()
	{
		return codCorredor;
	}
	
	public void setCodCorredor(String codCorredor)
	{
		this.codCorredor = codCorredor;
	}
	
	public Date getFecVenta()
	{
		return fecVenta;
	}
	
	public void setFecVenta(Date fecVenta)
	{
		this.fecVenta = fecVenta;
	}
	
	public String getHoraVenta()
	{
		return horaVenta;
	}
	
	public void setHoraVenta(String horaVenta)
	{
		this.horaVenta = horaVenta;
	}
	
	public Date getFecCarga()
	{
		return fecCarga;
	}
	
	public void setFecCarga(Date fecCarga)
	{
		this.fecCarga = fecCarga;
	}
	
	public Date getFecIniVigencia()
	{
		return fecIniVigencia;
	}
	
	public void setFecIniVigencia(Date fecIniVigencia)
	{
		this.fecIniVigencia = fecIniVigencia;
	}
	
	public Date getFecFinVigencia()
	{
		return fecFinVigencia;
	}
	
	public void setFecFinVigencia(Date fecFinVigencia)
	{
		this.fecFinVigencia = fecFinVigencia;
	}
	
	public String getPlaca()
	{
		return placa;
	}
	
	public void setPlaca(String placa)
	{
		this.placa = placa;
	}
	
	public String getSerie()
	{
		return serie;
	}
	
	public void setSerie(String serie)
	{
		this.serie = serie;
	}
	
	public String getMotor()
	{
		return motor;
	}
	
	public void setMotor(String motor)
	{
		this.motor = motor;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public Long getCodMarca()
	{
		return codMarca;
	}
	
	public void setCodMarca(Long codMarca)
	{
		this.codMarca = codMarca;
	}
	
	public String getMarca()
	{
		return marca;
	}
	
	public void setMarca(String marca)
	{
		this.marca = marca;
	}
	
	public Long getCodModelo()
	{
		return codModelo;
	}
	
	public void setCodModelo(Long codModelo)
	{
		this.codModelo = codModelo;
	}
	
	public String getModelo()
	{
		return modelo;
	}
	
	public void setModelo(String modelo)
	{
		this.modelo = modelo;
	}
	
	public String getCodCategoriaClase()
	{
		return codCategoriaClase;
	}
	
	public void setCodCategoriaClase(String codCategoriaClase)
	{
		this.codCategoriaClase = codCategoriaClase;
	}
	
	public String getCategoriaClase()
	{
		return categoriaClase;
	}
	
	public void setCategoriaClase(String categoriaClase)
	{
		this.categoriaClase = categoriaClase;
	}
	
	public String getCodUsoVehiculo()
	{
		return codUsoVehiculo;
	}
	
	public void setCodUsoVehiculo(String codUsoVehiculo)
	{
		this.codUsoVehiculo = codUsoVehiculo;
	}
	
	public String getUsoVehiculo()
	{
		return usoVehiculo;
	}
	
	public void setUsoVehiculo(String usoVehiculo)
	{
		this.usoVehiculo = usoVehiculo;
	}
	
	public Integer getAnioFab()
	{
		return anioFab;
	}
	
	public void setAnioFab(Integer anioFab)
	{
		this.anioFab = anioFab;
	}
	
	public Integer getNroAsientos()
	{
		return nroAsientos;
	}
	
	public void setNroAsientos(Integer nroAsientos)
	{
		this.nroAsientos = nroAsientos;
	}
	
	public Long getCodVehiculo()
	{
		return codVehiculo;
	}
	
	public void setCodVehiculo(Long codVehiculo)
	{
		this.codVehiculo = codVehiculo;
	}
	
	public String getNroCertificado()
	{
		return nroCertificado;
	}
	
	public void setNroCertificado(String nroCertificado)
	{
		this.nroCertificado = nroCertificado;
	}
	
	public String getNroCertificadoAnt()
	{
		return nroCertificadoAnt;
	}
	
	public void setNroCertificadoAnt(String nroCertificadoAnt)
	{
		this.nroCertificadoAnt = nroCertificadoAnt;
	}
	
	public String getPdfCodificadoSinFirmar()
	{
		return pdfCodificadoSinFirmar;
	}
	
	public void setPdfCodificadoSinFirmar(String pdfCodificadoSinFirmar)
	{
		this.pdfCodificadoSinFirmar = pdfCodificadoSinFirmar;
	}
	
	public String getTipoPersona()
	{
		return tipoPersona;
	}
	
	public void setTipoPersona(String tipoPersona)
	{
		this.tipoPersona = tipoPersona;
	}
	
	public String getTipDoc()
	{
		return tipDoc;
	}
	
	public void setTipDoc(String tipDoc)
	{
		this.tipDoc = tipDoc;
	}
	
	public String getNumDoc()
	{
		return numDoc;
	}
	
	public void setNumDoc(String numDoc)
	{
		this.numDoc = numDoc;
	}
	
	public String getNomContrat()
	{
		return nomContrat;
	}
	
	public void setNomContrat(String nomContrat)
	{
		this.nomContrat = nomContrat;
	}
	
	public String getApelPateContrat()
	{
		return apelPateContrat;
	}
	
	public void setApelPateContrat(String apelPateContrat)
	{
		this.apelPateContrat = apelPateContrat;
	}
	
	public String getApelMateContrat()
	{
		return apelMateContrat;
	}
	
	public void setApelMateContrat(String apelMateContrat)
	{
		this.apelMateContrat = apelMateContrat;
	}
	
	public String getSexo()
	{
		return sexo;
	}
	
	public void setSexo(String sexo)
	{
		this.sexo = sexo;
	}
	
	public Date getFecNac()
	{
		return fecNac;
	}
	
	public void setFecNac(Date fecNac)
	{
		this.fecNac = fecNac;
	}
	
	public String getEstadoCivil()
	{
		return estadoCivil;
	}
	
	public void setEstadoCivil(String estadoCivil)
	{
		this.estadoCivil = estadoCivil;
	}
	
	public String getCodDepartamento()
	{
		return codDepartamento;
	}
	
	public void setCodDepartamento(String codDepartamento)
	{
		this.codDepartamento = codDepartamento;
	}
	
	public String getDepartamento()
	{
		return departamento;
	}
	
	public void setDepartamento(String departamento)
	{
		this.departamento = departamento;
	}
	
	public String getCodProvincia()
	{
		return codProvincia;
	}
	
	public void setCodProvincia(String codProvincia)
	{
		this.codProvincia = codProvincia;
	}
	
	public String getProvincia()
	{
		return provincia;
	}
	
	public void setProvincia(String provincia)
	{
		this.provincia = provincia;
	}
	
	public String getCodDistrito()
	{
		return codDistrito;
	}
	
	public void setCodDistrito(String codDistrito)
	{
		this.codDistrito = codDistrito;
	}
	
	public String getDistrito()
	{
		return distrito;
	}
	
	public void setDistrito(String distrito)
	{
		this.distrito = distrito;
	}
	
	public String getDireccion()
	{
		return direccion;
	}
	
	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}
	
	public String getTelFijo()
	{
		return telFijo;
	}
	
	public void setTelFijo(String telFijo)
	{
		this.telFijo = telFijo;
	}
	
	public String getTelMovil()
	{
		return telMovil;
	}
	
	public void setTelMovil(String telMovil)
	{
		this.telMovil = telMovil;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getCodDepartamentoDesp()
	{
		return codDepartamentoDesp;
	}
	
	public void setCodDepartamentoDesp(String codDepartamentoDesp)
	{
		this.codDepartamentoDesp = codDepartamentoDesp;
	}
	
	public String getDepartamentoDesp()
	{
		return departamentoDesp;
	}
	
	public void setDepartamentoDesp(String departamentoDesp)
	{
		this.departamentoDesp = departamentoDesp;
	}
	
	public String getCodProvinciaDesp()
	{
		return codProvinciaDesp;
	}
	
	public void setCodProvinciaDesp(String codProvinciaDesp)
	{
		this.codProvinciaDesp = codProvinciaDesp;
	}
	
	public String getProvinciaDesp()
	{
		return provinciaDesp;
	}
	
	public void setProvinciaDesp(String provinciaDesp)
	{
		this.provinciaDesp = provinciaDesp;
	}
	
	public String getCodDistritoDesp()
	{
		return codDistritoDesp;
	}
	
	public void setCodDistritoDesp(String codDistritoDesp)
	{
		this.codDistritoDesp = codDistritoDesp;
	}
	
	public String getDistritoDesp()
	{
		return distritoDesp;
	}
	
	public void setDistritoDesp(String distritoDesp)
	{
		this.distritoDesp = distritoDesp;
	}
	
	public String getDireccionDesp()
	{
		return direccionDesp;
	}
	
	public void setDireccionDesp(String direccionDesp)
	{
		this.direccionDesp = direccionDesp;
	}
	
	public String getDirReferenciaDesp()
	{
		return dirReferenciaDesp;
	}
	
	public void setDirReferenciaDesp(String dirReferenciaDesp)
	{
		this.dirReferenciaDesp = dirReferenciaDesp;
	}
	
	public String getFranjaHorariaDesp()
	{
		return franjaHorariaDesp;
	}
	
	public void setFranjaHorariaDesp(String franjaHorariaDesp)
	{
		this.franjaHorariaDesp = franjaHorariaDesp;
	}
	
	public Date getFecDespacho()
	{
		return fecDespacho;
	}
	
	public void setFecDespacho(Date fecDespacho)
	{
		this.fecDespacho = fecDespacho;
	}
	
	public String getMedioPago()
	{
		return medioPago;
	}
	
	public void setMedioPago(String medioPago)
	{
		this.medioPago = medioPago;
	}
	
	public String getMoneda()
	{
		return moneda;
	}
	
	public void setMoneda(String moneda)
	{
		this.moneda = moneda;
	}
	
	public Double getPrimaBruta()
	{
		return primaBruta;
	}
	
	public void setPrimaBruta(Double primaBruta)
	{
		this.primaBruta = primaBruta;
	}
	
	public Double getPrimaTecnica()
	{
		return primaTecnica;
	}
	
	public void setPrimaTecnica(Double primaTecnica)
	{
		this.primaTecnica = primaTecnica;
	}
	
	public Double getImporteCobro()
	{
		return importeCobro;
	}
	
	public void setImporteCobro(Double importeCobro)
	{
		this.importeCobro = importeCobro;
	}
	
	public Double getImporteCobroDsc()
	{
		return importeCobroDsc;
	}
	
	public void setImporteCobroDsc(Double importeCobroDsc)
	{
		this.importeCobroDsc = importeCobroDsc;
	}
	
	public String getNumTarj()
	{
		return numTarj;
	}
	
	public void setNumTarj(String numTarj)
	{
		this.numTarj = numTarj;
	}
	
	public Date getFecVenciTarj()
	{
		return fecVenciTarj;
	}
	
	public void setFecVenciTarj(Date fecVenciTarj)
	{
		this.fecVenciTarj = fecVenciTarj;
	}
	
	public String getTipoTarj()
	{
		return tipoTarj;
	}
	
	public void setTipoTarj(String tipoTarj)
	{
		this.tipoTarj = tipoTarj;
	}
	
	public String getCodMaster()
	{
		return codMaster;
	}
	
	public void setCodMaster(String codMaster)
	{
		this.codMaster = codMaster;
	}
	
	public String getCanalVenta()
	{
		return canalVenta;
	}
	
	public void setCanalVenta(String canalVenta)
	{
		this.canalVenta = canalVenta;
	}
	
	public String getPuntoVenta()
	{
		return puntoVenta;
	}
	
	public void setPuntoVenta(String puntoVenta)
	{
		this.puntoVenta = puntoVenta;
	}
	
	public String getNomVend()
	{
		return nomVend;
	}
	
	public void setNomVend(String nomVend)
	{
		this.nomVend = nomVend;
	}
	
	public Date getFecCobro()
	{
		return fecCobro;
	}
	
	public void setFecCobro(Date fecCobro)
	{
		this.fecCobro = fecCobro;
	}
	
	public String getSubProd()
	{
		return subProd;
	}
	
	public void setSubProd(String subProd)
	{
		this.subProd = subProd;
	}
	
	public String getPropuesta()
	{
		return propuesta;
	}
	
	public void setPropuesta(String propuesta)
	{
		this.propuesta = propuesta;
	}
	
	public String getTrx()
	{
		return trx;
	}
	
	public void setTrx(String trx)
	{
		this.trx = trx;
	}
	
	public String getVoucher()
	{
		return voucher;
	}
	
	public void setVoucher(String voucher)
	{
		this.voucher = voucher;
	}
	
	public String getCodTienda()
	{
		return codTienda;
	}
	
	public void setCodTienda(String codTienda)
	{
		this.codTienda = codTienda;
	}
	
	public String getTarifDpto()
	{
		return tarifDpto;
	}
	
	public void setTarifDpto(String tarifDpto)
	{
		this.tarifDpto = tarifDpto;
	}
	
	public String getPropAnte()
	{
		return propAnte;
	}
	
	public void setPropAnte(String propAnte)
	{
		this.propAnte = propAnte;
	}
	
	public String getNuAp()
	{
		return nuAp;
	}
	
	public void setNuAp(String nuAp)
	{
		this.nuAp = nuAp;
	}
	
	public String getCdRef()
	{
		return cdRef;
	}
	
	public void setCdRef(String cdRef)
	{
		this.cdRef = cdRef;
	}
	
	public String getPos()
	{
		return pos;
	}
	
	public void setPos(String pos)
	{
		this.pos = pos;
	}
	
	public String getLotePos()
	{
		return lotePos;
	}
	
	public void setLotePos(String lotePos)
	{
		this.lotePos = lotePos;
	}
	
	public String getCdRect()
	{
		return cdRect;
	}
	
	public void setCdRect(String cdRect)
	{
		this.cdRect = cdRect;
	}
	
	public String getEstProp()
	{
		return estProp;
	}
	
	public void setEstProp(String estProp)
	{
		this.estProp = estProp;
	}
	
	public String getDesMedpag()
	{
		return desMedpag;
	}
	
	public void setDesMedpag(String desMedpag)
	{
		this.desMedpag = desMedpag;
	}
	
	public String getDondePago()
	{
		return dondePago;
	}
	
	public void setDondePago(String dondePago)
	{
		this.dondePago = dondePago;
	}
	
	public Date getFecCargaRef()
	{
		return fecCargaRef;
	}
	
	public void setFecCargaRef(Date fecCargaRef)
	{
		this.fecCargaRef = fecCargaRef;
	}
	
	public String getNroCorrelativoRef()
	{
		return nroCorrelativoRef;
	}
	
	public void setNroCorrelativoRef(String nroCorrelativoRef)
	{
		this.nroCorrelativoRef = nroCorrelativoRef;
	}
	
	public String getEstado()
	{
		return estado;
	}
	
	public void setEstado(String estado)
	{
		this.estado = estado;
	}
	
	public String getEstadoImpresion()
	{
		return estadoImpresion;
	}
	
	public void setEstadoImpresion(String estadoImpresion)
	{
		this.estadoImpresion = estadoImpresion;
	}
	
	public Long getSecArchivo()
	{
		return secArchivo;
	}
	
	public void setSecArchivo(Long secArchivo)
	{
		this.secArchivo = secArchivo;
	}
	
} // DetalleTramaDiaria
