package com.cardif.satelite.model;

import java.io.Serializable;
import java.util.Date;

public class TlmkBaseCabecera implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.COD_BASE
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Long codBase;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.COD_SOCIO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private String codSocio;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.NUM_REGISTROS
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Long numRegistros;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.SEQUENCE_FILE_NUMBER
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Long sequenceFileNumber;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.PRODUCTOS
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private String productos;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.NUM_MES_DESCANSO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Integer numMesDescanso;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.NUM_MES_VENCIMIENTO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Integer numMesVencimiento;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.ESTADO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private String estado;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.USU_CREACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private String usuCreacion;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.FEC_CREACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Date fecCreacion;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.USU_MODIFICACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private String usuModificacion;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.FEC_MODIFICACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Date fecModificacion;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column dbo.TLMK_BASE_CABECERA.MAX_EDAD
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  private Integer maxEdad;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.COD_BASE
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.COD_BASE
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Long getCodBase() {
    return codBase;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.COD_BASE
   *
   * @param codBase
   *          the value for dbo.TLMK_BASE_CABECERA.COD_BASE
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setCodBase(Long codBase) {
    this.codBase = codBase;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.COD_SOCIO
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.COD_SOCIO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public String getCodSocio() {
    return codSocio;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.COD_SOCIO
   *
   * @param codSocio
   *          the value for dbo.TLMK_BASE_CABECERA.COD_SOCIO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio == null ? null : codSocio.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.NUM_REGISTROS
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.NUM_REGISTROS
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Long getNumRegistros() {
    return numRegistros;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.NUM_REGISTROS
   *
   * @param numRegistros
   *          the value for dbo.TLMK_BASE_CABECERA.NUM_REGISTROS
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setNumRegistros(Long numRegistros) {
    this.numRegistros = numRegistros;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.SEQUENCE_FILE_NUMBER
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.SEQUENCE_FILE_NUMBER
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Long getSequenceFileNumber() {
    return sequenceFileNumber;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.SEQUENCE_FILE_NUMBER
   *
   * @param sequenceFileNumber
   *          the value for dbo.TLMK_BASE_CABECERA.SEQUENCE_FILE_NUMBER
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setSequenceFileNumber(Long sequenceFileNumber) {
    this.sequenceFileNumber = sequenceFileNumber;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.PRODUCTOS
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.PRODUCTOS
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public String getProductos() {
    return productos;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.PRODUCTOS
   *
   * @param productos
   *          the value for dbo.TLMK_BASE_CABECERA.PRODUCTOS
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setProductos(String productos) {
    this.productos = productos == null ? null : productos.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.NUM_MES_DESCANSO
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.NUM_MES_DESCANSO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Integer getNumMesDescanso() {
    return numMesDescanso;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.NUM_MES_DESCANSO
   *
   * @param numMesDescanso
   *          the value for dbo.TLMK_BASE_CABECERA.NUM_MES_DESCANSO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setNumMesDescanso(Integer numMesDescanso) {
    this.numMesDescanso = numMesDescanso;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.NUM_MES_VENCIMIENTO
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.NUM_MES_VENCIMIENTO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Integer getNumMesVencimiento() {
    return numMesVencimiento;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.NUM_MES_VENCIMIENTO
   *
   * @param numMesVencimiento
   *          the value for dbo.TLMK_BASE_CABECERA.NUM_MES_VENCIMIENTO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setNumMesVencimiento(Integer numMesVencimiento) {
    this.numMesVencimiento = numMesVencimiento;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.ESTADO
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.ESTADO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public String getEstado() {
    return estado;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.ESTADO
   *
   * @param estado
   *          the value for dbo.TLMK_BASE_CABECERA.ESTADO
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setEstado(String estado) {
    this.estado = estado == null ? null : estado.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.USU_CREACION
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.USU_CREACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public String getUsuCreacion() {
    return usuCreacion;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.USU_CREACION
   *
   * @param usuCreacion
   *          the value for dbo.TLMK_BASE_CABECERA.USU_CREACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setUsuCreacion(String usuCreacion) {
    this.usuCreacion = usuCreacion == null ? null : usuCreacion.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.FEC_CREACION
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.FEC_CREACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Date getFecCreacion() {
    return fecCreacion;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.FEC_CREACION
   *
   * @param fecCreacion
   *          the value for dbo.TLMK_BASE_CABECERA.FEC_CREACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setFecCreacion(Date fecCreacion) {
    this.fecCreacion = fecCreacion;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.USU_MODIFICACION
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.USU_MODIFICACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public String getUsuModificacion() {
    return usuModificacion;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.USU_MODIFICACION
   *
   * @param usuModificacion
   *          the value for dbo.TLMK_BASE_CABECERA.USU_MODIFICACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setUsuModificacion(String usuModificacion) {
    this.usuModificacion = usuModificacion == null ? null : usuModificacion.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.FEC_MODIFICACION
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.FEC_MODIFICACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Date getFecModificacion() {
    return fecModificacion;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.FEC_MODIFICACION
   *
   * @param fecModificacion
   *          the value for dbo.TLMK_BASE_CABECERA.FEC_MODIFICACION
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setFecModificacion(Date fecModificacion) {
    this.fecModificacion = fecModificacion;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column dbo.TLMK_BASE_CABECERA.MAX_EDAD
   *
   * @return the value of dbo.TLMK_BASE_CABECERA.MAX_EDAD
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public Integer getMaxEdad() {
    return maxEdad;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column dbo.TLMK_BASE_CABECERA.MAX_EDAD
   *
   * @param maxEdad
   *          the value for dbo.TLMK_BASE_CABECERA.MAX_EDAD
   *
   * @mbggenerated Thu May 29 11:28:39 COT 2014
   */
  public void setMaxEdad(Integer maxEdad) {
    this.maxEdad = maxEdad;
  }
}