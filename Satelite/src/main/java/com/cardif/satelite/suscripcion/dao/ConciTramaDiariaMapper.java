package com.cardif.satelite.suscripcion.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.satelite.ConciTramaDiaria;
import com.cardif.satelite.model.satelite.ConciTramaDiariaExample;
import com.cardif.satelite.moduloimpresion.bean.ConsultaArmarLote;
import com.cardif.satelite.moduloimpresion.bean.ConsultaConfirmacionImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaDocumentoCurier;
import com.cardif.satelite.reportes.bean.RepConsultaAPESEGBean;
import com.cardif.satelite.suscripcion.bean.RepPolizasSOATBean;

public interface ConciTramaDiariaMapper
{
	final String DELETE_X_ARCHIVO = "DELETE FROM DETALLE_TRAMA_DIARIA where sec_archivo=#{codSecuencia}";
	final String CONSULTA_NRO_REGITROS = "select count(*)  from dbo.CONCI_TRAMA_DIARIA " + "where sec_archivo =#{codSecuencia}";
	
	final String SELECT_POLIZAS_PARA_IMPRIMIR = "SELECT TM.PK, TM.NRO_POLIZA_PE AS ID_POLIZA, TM.ESTADO_IMPRESION, TM.FEC_VENTA AS FECHA_VENTA, TM.FECHA_DESPACHO, TM.COD_VEHICULO, TM.USO_VEHI AS USO_VEHICULO, " +
			"TM.COD_DEPARTAMENTO AS COD_DEPARTAMENTO_PROP, TM.COD_PROVINCIA AS COD_PROVINCIA_PROP, TM.COD_DISTRITO AS COD_DISTRITO_PROP, TM.FRANJA_HORARIA AS FRANJA_HORARIA_PROP, " +
			"TM.COD_DEPARTAMENTO_DESP, TM.DEPARTAMENTO_DESP, TM.COD_PROVINCIA_DESP, TM.PROVINCIA_DESP, TM.COD_DISTRITO_DESP,	TM.DISTRITO_DESP, TM.FRANJA_HORARIA_DESP, " +
			"CTM.PRODUCTO AS COD_SOCIO, TM.PLACA " +
			"FROM dbo.CONCI_TRAMA_DIARIA TM LEFT JOIN dbo.CONCI_CAB_TRAMA_DIARIA CTM ON CTM.SEC_ARCHIVO = TM.SEC_ARCHIVO " +
			"WHERE (TM.FEC_VENTA <= #{fecCompraHasta}) AND (TM.FEC_VENTA >= #{fecCompraDesde}) " + 
			"AND (TM.USO_VEHI LIKE '%' + isnull(#{codUso,jdbcType=VARCHAR}, TM.USO_VEHI) + '%') " + 
			"AND (TM.COD_DEPARTAMENTO_DESP LIKE '%' + isnull(#{codDepartamentoDesp,jdbcType=VARCHAR}, TM.COD_DEPARTAMENTO_DESP) + '%') " + 
			"AND (TM.COD_PROVINCIA_DESP LIKE '%' + isnull(#{codProvinciaDesp,jdbcType=VARCHAR}, TM.COD_PROVINCIA_DESP) + '%') " + 
			"AND (TM.COD_DISTRITO_DESP LIKE '%' + isnull(#{codDistritoDesp,jdbcType=VARCHAR}, TM.COD_DISTRITO_DESP) + '%') " + 
			"AND (TM.FRANJA_HORARIA_DESP LIKE '%' + isnull(#{franjaHorariaDesp,jdbcType=VARCHAR}, TM.FRANJA_HORARIA_DESP) + '%') " + 
			"AND (TM.FECHA_DESPACHO <= #{fecEntregaHasta}) " + 
			"AND (CTM.PRODUCTO LIKE '%' + isnull(#{codSocio,jdbcType=VARCHAR}, CTM.PRODUCTO) + '%') " + 
			"AND (TM.PLACA LIKE '%' + isnull(#{numPlaca,jdbcType=VARCHAR}, TM.PLACA) + '%') " + 
			"AND (isnull(TM.ESTADO_IMPRESION, '') <> '" + Constantes.MOD_IMPRESION_ESTADO_IMPRESO + "' AND isnull(TM.ESTADO_IMPRESION, '') <> '" + Constantes.MOD_IMPRESION_ESTADO_EN_ESPERA + "') " +
			"AND TM.POLIZA_ANULADA <> 1" + 
			"AND UPPER(TM.CANAL_VENTA) = 'DIGITAL' ORDER BY TM.NRO_POLIZA_PE ASC";

	
	final String SELECT_CONSULTA_POLIZAS_SOAT = "SELECT TM.PK, TM.NRO_POLIZA_PE, UPPER(COALESCE(TM.PLACA, '')) AS PLACA, UPPER(COALESCE(TM.MARCA, '')) AS MARCA, UPPER(COALESCE(TM.MODELO_IMPRES, '')) AS MODELO, " +
			"UPPER(COALESCE(TM.TIP_VEHI, '')) AS TIP_VEHI, UPPER(COALESCE(TM.USO_VEHI, '')) AS USO_VEHI, TM.NRO_ASIENTOS, TM.IMPORTE_COBRO_DSC, (TM.APEL_PATE + ' '+ TM.APEL_MATE + ' ' + TM.NOM_CONTRAT) as CONTRATANTE, TM.NUM_DOC, REPLACE(COALESCE(TM.NUM_CERT, ''), ' ', '') AS NUM_CERTIFICADO, TM.FEC_VENTA " +
			"FROM dbo.CONCI_TRAMA_DIARIA TM LEFT JOIN dbo.CONCI_CAB_TRAMA_DIARIA CTM ON CTM.SEC_ARCHIVO = TM.SEC_ARCHIVO " +
			"WHERE (CTM.PRODUCTO LIKE '%' + isnull(#{codSocio,jdbcType=VARCHAR}, CTM.PRODUCTO) + '%') " + 
			"AND (TM.PLACA LIKE '%' + isnull(#{numPlaca,jdbcType=VARCHAR}, TM.PLACA) + '%') " + 
			"AND (TM.NUM_CERT LIKE '%' + isnull(#{numCertificado,jdbcType=VARCHAR}, TM.NUM_CERT) + '%') " + 
			"AND (TM.NUM_DOC LIKE '%' + isnull(#{numDocumentoID,jdbcType=VARCHAR}, TM.NUM_DOC) + '%') " + 
			"AND ((TM.APEL_PATE + ' ' + TM.APEL_MATE + ' ' + TM.NOM_CONTRAT) LIKE '%' + isnull(#{nombreContratante,jdbcType=VARCHAR},(TM.APEL_PATE + ' ' + TM.APEL_MATE + ' ' + TM.NOM_CONTRAT)) + '%') " + 
			"AND (TM.FEC_VENTA <= #{fechaVentaHasta}) AND (TM.FEC_VENTA >= #{fechaVentaDesde}) ORDER BY TM.NRO_POLIZA_PE ASC";
	
	final String SELECT_CONSULTA_REPORTE_APESEG = "SELECT '" + Constantes.COD_CIA_SEGUROS + "' AS COD_CIA, REPLACE(COALESCE(TM.NUM_CERT, ''), ' ', '') AS POL_CER, TM.TIPO_TRX_APESEG AS TIP_TRA, TM.FIN_VIGEN AS FCH_CE_F, TM.INI_VIGEN AS FCH_CE_I, " +
			"TM.TIPO_PERSONA_APESEG AS TIP_PER, UPPER(TM.APEL_PATE + ' ' + TM.APEL_MATE + ' ' + TM.NOM_CONTRAT) AS NOM_CON, TM.TIP_DOC, TM.NUM_DOC, TM.PLACA, TM.USOS_APESEG AS USO, TM.CLASES_APESEG AS CLASE, '001' AS PAIS, " +
			"TM.TIPO_ANUL_APESEG AS TIP_ANU, TM.FEC_VENTA AS FEC_PRO, TM.FECHA_ACTU_ANUL AS FEC_ACT, TM.COD_DISTRITO AS UBIGEO, TM.MOTOR AS NRO_MOT, TM.SERIE AS NRO_CHA " +
			"FROM dbo.CONCI_TRAMA_DIARIA TM " + 
			"WHERE (TM.FEC_VENTA <= #{fecFinRegistro}) AND (TM.FEC_VENTA >= #{fecInicioRegistro}) ORDER BY TM.FEC_VENTA ASC";
	
	final String SELECT_POLIZAS_CURIER_BY_PKLOTE = "SELECT CTM.NUM_CERT AS NRO_DOC_CLIENTE,	(CTM.NOM_CONTRAT + ', ' + CTM.APEL_PATE + ' ' + CTM.APEL_MATE) AS NOMBRE_DESTINATARIO_GUIA, CTM.DIRECCION_DESP AS DIR_DESTINATARIO_GUIA, CTM.DISTRITO_DESP	AS DIST_DESTINATARIO_GUIA, CTM.PROVINCIA_DESP AS CIUDAD_DESTINATARIO_GUIA, '1' AS PIEZAS, " + 
			"'' AS TIPO_ENVIO, '' AS VALOR_DECLARADO_GUIA, CTM.NUM_DOC AS ID_DESTINATARIO_GUIA, CTM.TEL_FIJO AS TEL_DESTINATARIO_GUIA, (CTM.DIR_REFERENCIA_DESP + ' - ' + CONVERT(NVARCHAR, CTM.FECHA_DESPACHO, 103) + ' - ' + CTM.FRANJA_HORARIA_DESP) AS OBSERVA_GUIA, " +
			"LI.SOCIO AS NOMBRE_REMITENTE_GUIA, CTM.NRO_POLIZA_PE AS POLIZA, CTM.PLACA, CTM.FEC_VENTA AS FECHA_VENTA " + 
			"FROM CONCI_TRAMA_DIARIA CTM LEFT JOIN DETALLE_LOTE_IMPRESION DLI ON DLI.CONCI_TRAMA_DIARIA = CTM.PK " + 
			"LEFT JOIN LOTE_IMPRESION LI ON LI.NUM_LOTE = DLI.LOTE_IMPRESION " + 
			"WHERE (LI.NUM_LOTE = #{pkLoteImpresion,jdbcType=NUMERIC}) ORDER BY LI.SOCIO, CTM.NUM_CERT ASC";
	
	final String SELECT_POLIZAS_PARA_CONFIRMAR_IMPRESION = "SELECT CTM.PK, CTM.NRO_POLIZA_PE, CTM.PLACA, CTM.NUM_CERT AS NUM_CERTIFICADO, LI.NUM_LOTE, CCTM.PRODUCTO AS COD_SOCIO, (CASE WHEN CTM.NUM_CERT <> 'NULL' THEN 'SI' ELSE 'NO' END) AS IMPRESO_CORRECTAMENTE " +
			"FROM CONCI_TRAMA_DIARIA CTM LEFT JOIN CONCI_CAB_TRAMA_DIARIA CCTM ON CCTM.SEC_ARCHIVO = CTM.SEC_ARCHIVO " + 
			"LEFT JOIN DETALLE_LOTE_IMPRESION DLI ON DLI.CONCI_TRAMA_DIARIA = CTM.PK " +
			"LEFT JOIN LOTE_IMPRESION LI ON LI.NUM_LOTE = DLI.LOTE_IMPRESION " +
			"WHERE LI.NUM_LOTE = #{numLote,jdbcType=NUMERIC} ORDER BY CTM.NRO_POLIZA_PE ASC";
	
	
	@Select(CONSULTA_NRO_REGITROS)
	Long consultaRegistros(@Param("codSecuencia") Long codSecuencia);
	
	@Delete(DELETE_X_ARCHIVO)
	int deletePorSecuencia(@Param("codSecuencia") Long codSecuencia);
	
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int countByExample(ConciTramaDiariaExample example);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int deleteByExample(ConciTramaDiariaExample example);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int deleteByPrimaryKey(Long pk);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
   	* @mbggenerated Tue Jul 22 17:31:19 COT 2014
   	*/
	int insert(ConciTramaDiaria record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int insertSelective(ConciTramaDiaria record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	List<ConciTramaDiaria> selectByExample(ConciTramaDiariaExample example);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	ConciTramaDiaria selectByPrimaryKey(Long pk);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int updateByExampleSelective(@Param("record") ConciTramaDiaria record, @Param("example") ConciTramaDiariaExample example);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int updateByExample(@Param("record") ConciTramaDiaria record, @Param("example") ConciTramaDiariaExample example);
	
	
	int updateCertificadoAndEstadoImpresion(@Param("record") ConciTramaDiaria record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int updateByPrimaryKeySelective(ConciTramaDiaria record);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_TRAMA_DIARIA
	 *
	 * @mbggenerated Tue Jul 22 17:31:19 COT 2014
	 */
	int updateByPrimaryKey(ConciTramaDiaria record);
  	
  	@Select(SELECT_POLIZAS_PARA_IMPRIMIR)
  	@ResultMap("BaseResultMapPolizasParaImprimir")
  	List<ConsultaArmarLote> selectPolizasParaImprimir(@Param("codSocio") String codSocio, @Param("numPlaca") String numPlaca, @Param("codUso") String codUso, 
  			@Param("codDepartamentoDesp") String codDepartamentoDesp, @Param("codProvinciaDesp") String codProvinciaDesp, @Param("codDistritoDesp") String codDistritoDesp, 
  			@Param("fecCompraDesde") Date fecCompraDesde, @Param("fecCompraHasta") Date fecCompraHasta, @Param("fecEntregaHasta") Date fecEntregaHasta, @Param("franjaHorariaDesp") String franjaHorariaDesp);
  	
  	@Select(SELECT_CONSULTA_POLIZAS_SOAT)
  	@ResultMap("BaseResultConsultaPolizasSOAT")
	List<RepPolizasSOATBean> selectConsultaPolizasSOAT(@Param("codSocio") String codSocio, @Param("numPlaca") String numPlaca, @Param("numCertificado") String numCertificado,
			@Param("numDocumentoID") String numDocumentoID, @Param("nombreContratante") String nombreContratante, @Param("fechaVentaDesde")	Date fechaVentaDesde, @Param("fechaVentaHasta") Date fechaVentaHasta);
  	
  	@Select(SELECT_CONSULTA_REPORTE_APESEG)
  	@ResultMap("BaseResultConsultaReporteAPESEG")
	List<RepConsultaAPESEGBean> selectConsultaReporteAPESEG(@Param("fecInicioRegistro") Date fecInicioRegistro, @Param("fecFinRegistro") Date fecFinRegistro);
  	
  	@Select(SELECT_POLIZAS_CURIER_BY_PKLOTE)
  	@ResultMap("ResultDocumentoCurier")
	List<ConsultaDocumentoCurier> selectPolizasCurierByPkLote(@Param("pkLoteImpresion") Long pkLoteImpresion);
  	
  	@Select(SELECT_POLIZAS_PARA_CONFIRMAR_IMPRESION)
  	@ResultMap("ResultPolizasConfirmarImpresion")
	List<ConsultaConfirmacionImpresion> selectPolizasParaConfirmarImpresion(@Param("numLote") Long numLote);
	
}