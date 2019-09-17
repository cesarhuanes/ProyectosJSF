package com.cardif.satelite.tesoreria.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.tesoreria.bean.ComprobanteElectronicoBean;
import com.cardif.satelite.tesoreria.bean.ConsultaCertRetencionReversa;
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;
import com.cardif.sunsystems.util.ConstantesSun;

public interface ComprobanteElectronicoMapper {

  final String INSERT_COMPROBANTE_ELE = "INSERT INTO TES_COMPROBANTE_ELECTRONICO (NRO_COMPROBANTE_ELE, TIPO_COMPROBANTE, FECHA_EMISION, PRO_TIPO_DOCUMENTO, PRO_NRO_DOCUMENTO,"
      + "PRO_RAZON_SOCIAL, MONEDA_PAGO, TASA_RETPER, IMPORTE_RETPER, MONEDA_RETPER, IMPORTE_PAGCOB, PLANTILLA, CORREO, TIPO_CAMBIO, MONTO_LETRAS, ESTADO, FECHA_CREACION,"
      + "CREADO_POR, FECHA_MODIFICACION, MODIFICADO_POR, CAUSA_ANULACION, MOTIVO_ANULACION, RESPUESTA_DTERECOVERY) "
      + "VALUES (#{nroComprobanteElectronico,jdbcType=NVARCHAR}, #{tipoComprobante,jdbcType=NVARCHAR}, #{fechaEmision,jdbcType=TIMESTAMP}, #{proTipoDocumento,jdbcType=NVARCHAR},"
      + " #{proNroDocumento,jdbcType=NVARCHAR}, #{proRazonSocial,jdbcType=NUMERIC}, #{monedaPago,jdbcType=NVARCHAR}, #{tasaRetPer,jdbcType=NVARCHAR}, #{importeRetPer,jdbcType=NUMERIC},"
      + " #{monedaRetPer,jdbcType=NVARCHAR}, #{importePagCob,jdbcType=NUMERIC}, #{plantilla,jdbcType=NVARCHAR}, #{correo,jdbcType=NVARCHAR}, #{tipoCambio,jdbcType=NUMERIC},"
      + " #{montoLetras,jdbcType=NVARCHAR}, #{estado,jdbcType=NVARCHAR}, #{fechaCreacion,jdbcType=TIMESTAMP}, #{creadoPor,jdbcType=NVARCHAR}, #{fechaModificacion,jdbcType=TIMESTAMP},"
      + " #{modificadoPor,jdbcType=NVARCHAR}, #{causaAnulacion,jdbcType=NVARCHAR}, #{motivoAnulacion,jdbcType=NVARCHAR}, #{respuestaDteRecovery,jdbcType=NVARCHAR})";
  	
  	final String SELECT_CERTIFICADO_RETENCION_REVERSA = "SELECT NRO_COMPROBANTE_ELE, TIPO_COMPROBANTE, NRO_DIARIO, FECHA_EMISION, MONEDA_PAGO, IMPORTE_RETPER, PRO_NRO_DOCUMENTO, PRO_TIPO_DOCUMENTO, PRO_RAZON_SOCIAL, MOTIVO_ANULACION, UNIDAD_NEGOCIO " +
  			"FROM COMPROBANTE_ELECTRONICO WHERE (UNIDAD_NEGOCIO = isnull(#{codUnidadNegocio,jdbcType=NVARCHAR}, UNIDAD_NEGOCIO)) " +
 			"AND (CONVERT(NVARCHAR, FECHA_EMISION, 103) = #{fechaEmision,jdbcType=NVARCHAR}) " + 
  			"AND (NRO_COMPROBANTE_ELE LIKE '%' + isnull(#{nroCertificado,jdbcType=NVARCHAR}, NRO_COMPROBANTE_ELE) + '%') AND (PRO_NRO_DOCUMENTO LIKE '%' + isnull(#{rucProveedor,jdbcType=NVARCHAR}, PRO_NRO_DOCUMENTO) + '%') " +
 			"AND (ESTADO = '"+ ConstantesSun.EST_ASIENTO_ANULADO + "') AND (PLAZO_FECHA_SUNAT = " + Constantes.PLAZO_FECHA_SUNAT_OK + ") " +
  			"ORDER BY FECHA_EMISION, PRO_NRO_DOCUMENTO DESC";
  	
  final String SELECT_COMPROBANTE_ELE_ANULADO = "SELECT NRO_COMPROBANTE_ELE, TIPO_COMPROBANTE, FECHA_EMISION, PRO_TIPO_DOCUMENTO, PRO_NRO_DOCUMENTO,"
      + "PRO_RAZON_SOCIAL, MONEDA_PAGO, TASA_RETPER, IMPORTE_RETPER, MONEDA_RETPER, IMPORTE_PAGCOB, PLANTILLA, CORREO, TIPO_CAMBIO, MONTO_LETRAS, ESTADO, FECHA_CREACION,"
      + "CREADO_POR, FECHA_MODIFICACION, MODIFICADO_POR, CAUSA_ANULACION, MOTIVO_ANULACION, RESPUESTA_DTERECOVERY FROM TES_COMPROBANTE_ELECTRONICO "
      + "WHERE "
      + "NRO_COMPROBANTE_ELE "
      + "VALUES (#{nroComprobanteElectronico,jdbcType=NVARCHAR}, #{tipoComprobante,jdbcType=NVARCHAR}, #{fechaEmision,jdbcType=TIMESTAMP}, #{proTipoDocumento,jdbcType=NVARCHAR},"
      + " #{proNroDocumento,jdbcType=NVARCHAR}, #{proRazonSocial,jdbcType=NUMERIC}, #{monedaPago,jdbcType=NVARCHAR}, #{tasaRetPer,jdbcType=NVARCHAR}, #{importeRetPer,jdbcType=NUMERIC},"
      + " #{monedaRetPer,jdbcType=NVARCHAR}, #{importePagCob,jdbcType=NUMERIC}, #{plantilla,jdbcType=NVARCHAR}, #{correo,jdbcType=NVARCHAR}, #{tipoCambio,jdbcType=NUMERIC},"
      + " #{montoLetras,jdbcType=NVARCHAR}, #{estado,jdbcType=NVARCHAR}, #{fechaCreacion,jdbcType=TIMESTAMP}, #{creadoPor,jdbcType=NVARCHAR}, #{fechaModificacion,jdbcType=TIMESTAMP},"
      + " #{modificadoPor,jdbcType=NVARCHAR}, #{causaAnulacion,jdbcType=NVARCHAR}, #{motivoAnulacion,jdbcType=NVARCHAR}, #{respuestaDteRecovery,jdbcType=NVARCHAR})";
  	
  	final String SELECT_CERT_RETENCION_BY_PARAMETROS = "SELECT NRO_COMPROBANTE_ELE, TIPO_COMPROBANTE, FECHA_EMISION, PRO_TIPO_DOCUMENTO, PRO_NRO_DOCUMENTO, PRO_RAZON_SOCIAL, MONEDA_PAGO, " +
  			"TASA_RETPER, IMPORTE_RETPER, MONEDA_RETPER, IMPORTE_PAGCOB, PLANTILLA, CORREO, TIPO_CAMBIO, MONTO_LETRAS, ESTADO, FECHA_CREACION, CREADO_POR, FECHA_MODIFICACION, MODIFICADO_POR, " +
  			"CAUSA_ANULACION, MOTIVO_ANULACION, RESPUESTA_DTERECOVERY, NRO_DIARIO, FECHA_DIGITALIZACION, UNIDAD_NEGOCIO " + 
  			"FROM COMPROBANTE_ELECTRONICO WHERE (UNIDAD_NEGOCIO = isnull(#{codUnidadNegocio,jdbcType=NVARCHAR}, UNIDAD_NEGOCIO)) " +
  			"AND (FECHA_EMISION <= #{fecEmisionHasta}) AND (FECHA_EMISION >= #{fecEmisionDesde}) " +
  			"AND (NRO_COMPROBANTE_ELE BETWEEN isnull(#{nroCertificadoDesde,jdbcType=NVARCHAR}, NRO_COMPROBANTE_ELE) AND isnull(#{nroCertificadoHasta,jdbcType=NVARCHAR}, NRO_COMPROBANTE_ELE)) " +
  			"AND (ESTADO = isnull(#{codEstadoCertificado,jdbcType=NVARCHAR}, ESTADO)) " +
  			"ORDER BY FECHA_EMISION, PRO_NRO_DOCUMENTO DESC";
  	
  	final String SELECT_CERTIFICADO_RETENCION_ANULADO=" SELECT NRO_COMPROBANTE_ELE, TIPO_COMPROBANTE, FECHA_EMISION,PRO_TIPO_DOCUMENTO, PRO_NRO_DOCUMENTO, PRO_RAZON_SOCIAL, MONEDA_PAGO,"
  			+ " TASA_RETPER, IMPORTE_RETPER,MONEDA_RETPER, IMPORTE_PAGCOB, PLANTILLA, CORREO, TIPO_CAMBIO, MONTO_LETRAS, ESTADO,	FECHA_CREACION, CREADO_POR, FECHA_MODIFICACION,"
  			+ " MODIFICADO_POR,CAUSA_ANULACION, MOTIVO_ANULACION, RESPUESTA_DTERECOVERY, NRO_DIARIO,FECHA_DIGITALIZACION, UNIDAD_NEGOCIO, PLAZO_FECHA_SUNAT"
  			+ " FROM COMPROBANTE_ELECTRONICO WHERE ESTADO = '"+ConstantesSun.EST_ASIENTO_VALIDADO_FEP+"' AND (UNIDAD_NEGOCIO = isnull(#{unidadNegocio,jdbcType=NVARCHAR}, UNIDAD_NEGOCIO)) " 
  			+ " AND (NRO_COMPROBANTE_ELE LIKE '%' + isnull(#{nroComprobanteRetencion,jdbcType=NVARCHAR}, NRO_COMPROBANTE_ELE) + '%') AND (PRO_NRO_DOCUMENTO LIKE '%' + isnull(#{rucProveedor,jdbcType=NVARCHAR}, PRO_NRO_DOCUMENTO) + '%') "
  			+ " AND (CONVERT(NVARCHAR, FECHA_EMISION, 112) BETWEEN isnull(#{fechaDesde,jdbcType=NVARCHAR}, FECHA_EMISION) AND isnull(#{fechaHasta,jdbcType=NVARCHAR}, FECHA_EMISION)) "  	  			
  			+ " ORDER BY FECHA_EMISION, PRO_NRO_DOCUMENTO DESC";
  	
  @Insert(INSERT_COMPROBANTE_ELE)
  void insertar(ComprobanteElectronicoBean comprobante);
    
  public int insertSelective(ComprobanteElectronico record);
  	
  public ComprobanteElectronico selectByPrimaryKey(String nroComprobanteElectronico);
	
  public int updateByPrimaryKeySelective(ComprobanteElectronico record);
  	
  @Select(INSERT_COMPROBANTE_ELE)
  @ResultMap("BaseResultMap")
  List<ComprobanteElectronicoBean> select(ComprobanteElectronicoBean comprobante);
  	
  @Select(SELECT_CERTIFICADO_RETENCION_REVERSA)
  @ResultMap("CertRetencionReversaResultMap")
  public List<ConsultaCertRetencionReversa> selectCertificadoRetencionReversar(@Param("codUnidadNegocio") String codUnidadNegocio, @Param("fechaEmision") String fechaEmision, 
		  	@Param("nroCertificado") String nroCertificado, @Param("rucProveedor") String rucProveedor);
  	
  @Select(SELECT_CERT_RETENCION_BY_PARAMETROS)
  @ResultMap("BaseResultMap")
  public List<ComprobanteElectronico> selectCertificadoRetencion(@Param("codUnidadNegocio") String codUnidadNegocio, @Param("fecEmisionDesde") Date fecEmisionDesde, @Param("fecEmisionHasta") Date fecEmisionHasta,
  			@Param("nroCertificadoDesde") String nroCertificadoDesde, @Param("nroCertificadoHasta") String nroCertificadoHasta, @Param("codEstadoCertificado") String codEstadoCertificado);
  
  void updateByNroComprobanteEstado(ComprobanteElectronico comprobate);
  
  public List<ComprobanteElectronico>  selectByCertificadosAnulados(ComprobanteElectronico comprobate);
  
  @Select(SELECT_CERTIFICADO_RETENCION_ANULADO)
  @ResultMap("BaseResultMap")
  public List<ComprobanteElectronico> selectCertificadoAnulados(@Param("unidadNegocio") String unidadNegocio,@Param("nroDiario") String nroDiario, @Param("nroComprobanteRetencion") String nroComprobanteRetencion,@Param("fechaDesde") String fechaDesde, @Param("fechaHasta") String fechaHasta, @Param("rucProveedor") String rucProveedor);
}