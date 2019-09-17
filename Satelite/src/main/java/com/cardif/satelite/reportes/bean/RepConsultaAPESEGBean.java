package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepConsultaAPESEGBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long idReporteApeseg;
	
	/* Codigo de CIA. de Seguros*/
	private String codCia;
	
	/* # Poliza-Certificado */
	private String polCer;
	
	/* Tipo de transaccion */
	private String tipTra;
	
	/* Fecha de fin de vigencia del Certificado (dd/mm/yyyy) */
	private Date fchCeF;
	
	/* Fecha de inicio de vigencia del Certificado (dd/mm/yyyy) */
	private Date fchCeI;
	
	/* Tipo de persona */
	private String tipPer;
	
	/* Nombre del contratante */
	private String nomCon;
	
	/* Tipo del documento de identidad del contratante */
	private String tipDoc;
	
	/* # documento de identidad del contratante */
	private String numDoc;
	
	/* Placa del vehiculo asegurado */
	private String placa;
	
	/* Codigo de uso del vehiculo */
	private String uso;
	
	/* Codigo de clase del vehiculo */
	private String clase;
	
	/* Pais de la placa */
	private String pais;
	
	/* Tipo de anulacion */
	private String tipAnu;
	
	/* Fecha de ingreso */
	private Date fecPro;
	
	/* Fecha de actualizacion/anulacion */
	private Date fecAct;
	
	/* Codigo de ubigeo (INEI) */
	private String ubigeo;
	
	/* Numero de serie del motor */
	private String nroMot;
	
	/* Numero de serie del chasis */
	private String nroCha;
	
	/* Nombre del tipo de CANAL */
	private String nomCanal;
	
	/* Nombre del SOCIO */
	private String nomSocio;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getIdReporteApeseg()
	{
		return idReporteApeseg;
	}
	
	public void setIdReporteApeseg(Long idReporteApeseg)
	{
		this.idReporteApeseg = idReporteApeseg;
	}
	
	public String getCodCia()
	{
		return codCia;
	}
	
	public void setCodCia(String codCia)
	{
		this.codCia = codCia;
	}
	
	public String getPolCer()
	{
		return polCer;
	}
	
	public void setPolCer(String polCer)
	{
		this.polCer = polCer;
	}
	
	public String getTipTra()
	{
		return tipTra;
	}
	
	public void setTipTra(String tipTra)
	{
		this.tipTra = tipTra;
	}
	
	public Date getFchCeF()
	{
		return fchCeF;
	}
	
	public void setFchCeF(Date fchCeF)
	{
		this.fchCeF = fchCeF;
	}
	
	public Date getFchCeI()
	{
		return fchCeI;
	}
	
	public void setFchCeI(Date fchCeI)
	{
		this.fchCeI = fchCeI;
	}
	
	public String getTipPer()
	{
		return tipPer;
	}
	
	public void setTipPer(String tipPer)
	{
		this.tipPer = tipPer;
	}
	
	public String getNomCon()
	{
		return nomCon;
	}
	
	public void setNomCon(String nomCon)
	{
		this.nomCon = nomCon;
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
	
	public String getPlaca()
	{
		return placa;
	}
	
	public void setPlaca(String placa)
	{
		this.placa = placa;
	}
	
	public String getUso()
	{
		return uso;
	}
	
	public void setUso(String uso)
	{
		this.uso = uso;
	}
	
	public String getClase()
	{
		return clase;
	}
	
	public void setClase(String clase)
	{
		this.clase = clase;
	}
	
	public String getPais()
	{
		return pais;
	}
	
	public void setPais(String pais)
	{
		this.pais = pais;
	}
	
	public String getTipAnu()
	{
		return tipAnu;
	}
	
	public void setTipAnu(String tipAnu)
	{
		this.tipAnu = tipAnu;
	}
	
	public Date getFecPro()
	{
		return fecPro;
	}
	
	public void setFecPro(Date fecPro)
	{
		this.fecPro = fecPro;
	}
	
	public Date getFecAct()
	{
		return fecAct;
	}
	
	public void setFecAct(Date fecAct)
	{
		this.fecAct = fecAct;
	}
	
	public String getUbigeo()
	{
		return ubigeo;
	}
	
	public void setUbigeo(String ubigeo)
	{
		this.ubigeo = ubigeo;
	}
	
	public String getNroMot()
	{
		return nroMot;
	}
	
	public void setNroMot(String nroMot)
	{
		this.nroMot = nroMot;
	}
	
	public String getNroCha()
	{
		return nroCha;
	}
	
	public void setNroCha(String nroCha)
	{
		this.nroCha = nroCha;
	}
	
	public String getNomCanal()
	{
		return nomCanal;
	}
	
	public void setNomCanal(String nomCanal)
	{
		this.nomCanal = nomCanal;
	}
	
	public String getNomSocio()
	{
		return nomSocio;
	}
	
	public void setNomSocio(String nomSocio)
	{
		this.nomSocio = nomSocio;
	}
	
} //RepConsultaAPESEGBean
