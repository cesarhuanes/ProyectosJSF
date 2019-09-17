package com.cardif.satelite.reportes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.reportes.DetalleReporteSBS;
import com.cardif.satelite.reportes.bean.RepAnulacionPrimaSBSBean;
import com.cardif.satelite.reportes.bean.RepContratoSoatSBSBean;
import com.cardif.satelite.reportes.bean.RepProduccionPrimaSBSBean;

public interface DetalleReporteSBSMapper
{
	
	final String SELECT_ROWLAST_BY_PK_DETALLE_TRAMA_DIARIA = "SELECT DRS.ID, DRS.ID_DETALLE_TRAMA_MENSUAL, DRS.CLASE, DRS.USO, DRS.REPORTADO, DRS.FECHA_REPORTADO, DRS.FEC_CREACION, DRS.USU_CREACION " +
			"FROM DETALLE_REPORTE_SBS DRS LEFT JOIN DETALLE_TRAMA_DIARIA DTD ON DRS.ID_DETALLE_TRAMA_DIARIA = DTD.ID " +
			"WHERE (DTD.ID = NVL(#{pkDetalleTramaDiaria,jdbcType=NUMERIC}, DTD.ID)) ORDER BY DRS.FECHA_REPORTADO DESC";
	
	/*
	 * Reporte: ES – 11A
	 * 		Busca las polizas con estado VIGENTE segun el anio y mes, ademas verifica que dichas polizas no se encuentren reportadas a la SBS
	 * 		como PRODUCCION DE PRIMAS.
	 * 
	 * 		- Polizas con estado VIGENTE: 						DTD.ESTADO='VIGENTE'
	 * 		- Polizas segun el anio: 							EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anio,jdbcType=NUMERIC}
	 * 		- Polizas segun el mes: 							EXTRACT(MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{mes,jdbcType=NUMERIC}
	 * 		- Polizas que no se encuentren reportadas a la SBS: DRS.REPORTADO = 'NO'
	 * 		- Polizas no reportadas como PRODUCCION DE PRIMAS:	DRS.ESTADO_REPORTADO IS NULL
	 */

	final String SELECT_PRODUCCION_PRIMAS_SBS = 
					"SELECT ROWNUM AS CODIGO_FILA, " 
				  + "DRS.ID, "
				  + "DRS.ID_DETALLE_TRAMA_MENSUAL, "
				  + "DTD.FEC_VENTA AS FECHA_EMISION, "
				  + "DTD.NRO_CERTIFICADO AS NRO_POLIZA, " 
				  + "DTD.NRO_CERTIFICADO, "
				  + "DTD.PLACA, "
				  + "DTD.COD_PROVINCIA AS UBIGEO, "
				  + "DRS.CLASE AS CLASE_VEHICULO, "
				  + "DRS.USO AS USO_VEHICULO, "
				  + "DTD.NRO_ASIENTOS, "
				  + "DTD.IMPORTE_COBRO AS PRIMA_COMERCIAL_BRUTA, " 
				  + "DRL.DE, "
				  + "(DTD.IMPORTE_COBRO - DRL.DE - " 
								+ "(DTD.IMPORTE_COBRO * ((SELECT TO_NUMBER(NOMBRE_VALOR, '999.99') " 
												+ " FROM PARAMETRO_AUTOMAT "
													+ " WHERE COD_PARAM = '" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00))) AS PRIMA_COMERCIAL, " 
					+ Constantes.MONTO_DEFAULT + " AS PRIMA_COMERCIAL_COBRADA, "
					+ " (DTD.IMPORTE_COBRO - DRL.DE - "
							+ " (DTD.IMPORTE_COBRO * ((SELECT TO_NUMBER(NOMBRE_VALOR, '999.99') " 
					                               + " FROM PARAMETRO_AUTOMAT "
					                                       + " WHERE COD_PARAM='" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00))) AS PRIMA_COMERCIAL_COBRAR, " 
					+ "DRL.COM_SOCIO AS COMISIONES, "
					+ Constantes.MONTO_DEFAULT + " AS GASTO_ADMINISTRACION, " 
					+ Constantes.MONTO_DEFAULT + " AS RECARGO_COMERCIAL, "
					+ Constantes.MONTO_DEFAULT + " AS OTROS_RECARGOS, "
					+ "DTD.FEC_INI_VIG_POLIZA AS FECHA_INI_VIGENCIA, "
					+ "DTD.FEC_FIN_VIG_POLIZA AS FECHA_FIN_VIGENCIA, "
					+ "DRL.FND_COMP "
					+ "FROM DETALLE_REPORTE_SBS DRS " 
					+ "LEFT JOIN DETALLE_TRAMA_MENSUAL DTD ON DRS.ID_DETALLE_TRAMA_MENSUAL = DTD.ID " 
					+ "LEFT JOIN DETALLE_REPORTE_LIQUIDACION DRL ON DTD.ID = DRL.ID_DETALLE_TRAMA_MENSUAL " 
					+ "WHERE (DTD.ESTADO = '" + Constantes.POLIZA_ESTADO_VIGENTE + "') AND "
					+ "(DTD.NRO_CERTIFICADO IS NOT NULL) AND " 
					+ "(EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anio,jdbcType=NUMERIC}) " 
					+ "AND (EXTRACT(MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{mes,jdbcType=NUMERIC}) "
					+ "AND (DRS.REPORTADO = 'NO') AND "
					+ "(DRS.ESTADO_REPORTADO IS NULL OR DRS.ESTADO_REPORTADO = '0') "
					+ "ORDER BY DTD.FEC_VENTA, DTD.PLACA ASC "; 
	
	/*
	 * Reporte: ES – 11C
	 * 		Busca las polizas con estado ANULADO segun el anio y mes, ademas verifica que dichas polizas no se encuentren reportadas a la SBS
	 *		como ANULACION DE PRIMAS.
	 * 
	 * 		- Polizas con estado ANULADO: 						DTD.ESTADO='ANULADO'
	 * 		- Polizas segun el anio: 							EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anio,jdbcType=NUMERIC}
	 * 		- Polizas segun el mes: 							EXTRACT(MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{mes,jdbcType=NUMERIC}
	 * 		- Polizas no reportadas como ANULACION DE PRIMAS:	DRS.ESTADO_REPORTADO IS NULL OR DRS.ESTADO_REPORTADO = 'PRODUCCION'
	 * 															No importa si el valor REPORTADO se encuentra con 'SI' o 'NO', porque puede que esten
	 * 															con valor 'SI' de reportado como PRODUCCION DE PRIMAS.
	 */
	final String SELECT_ANULACION_PRIMAS_SBS1111 = 
										"SELECT ROWNUM AS CODIGO_FILA, "
										+ "DRS.ID, "
										+ "DRS.ID_DETALLE_TRAMA_DIARIA, "
										+ "DTM.FECHA_CREACION AS FEC_REGISTRO_ANULACION, "
										+ "DTM.FEC_ANULACION, "
										+ "DTD.NRO_CERTIFICADO AS NRO_POLIZA, "
										+ "DTD.NRO_CERTIFICADO, "
										+ "DTD.PLACA, " 
										+"DTD.IMPORTE_COBRO AS PRIMA_COMERCIAL_BRUTA, "
										+ "(DTD.IMPORTE_COBRO - DRL.DERECHO_EMISION - "
										+ "(DTD.IMPORTE_COBRO * ((SELECT TO_NUMBER(NOMBRE_VALOR, '999.99') "
										+ "FROM PARAMETRO_AUTOMAT WHERE "
										+ "COD_PARAM='" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND "
										+ "COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00))) AS PRIMA_COMERCIAL, " 
										+ "DRL.FONDO_COMPENSACION AS MONTO_FONDO_SOAT, "
										+ "DTD.FEC_INI_VIGENCIA AS FECHA_INI_VIGENCIA " 
										+ "FROM DETALLE_REPORTE_SBS DRS "
										+ "LEFT JOIN DETALLE_TRAMA_DIARIA DTD ON DRS.ID_DETALLE_TRAMA_DIARIA = DTD.ID " 
										+ "LEFT JOIN DETALLE_TRAMA_DIARIA_MOTIVO DTM ON DTD.ID = DTM.ID_DETALLE_TRAMA_DIARIA " 
										+ "LEFT JOIN DETALLE_REPORTE_LIQUIDACION DRL ON DTD.ID = DRL.ID_DETALLE_TRAMA_DIARIA " 
										+ "WHERE (DTD.ESTADO = '" + Constantes.POLIZA_ESTADO_ANULADO + "') AND "
										+ "(DTD.NRO_CERTIFICADO IS NOT NULL) AND "
										+ "(EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anio,jdbcType=NUMERIC}) AND "
										+ "(EXTRACT(MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{mes,jdbcType=NUMERIC}) AND" 
										+ "(DRS.ESTADO_REPORTADO IS NULL OR DRS.ESTADO_REPORTADO = '" + Constantes.SBS_ESTADO_REPORTADO_PROD + "') " 
										+ " ORDER BY DTM.FEC_ANULACION, DTD.PLACA ASC";
	
	final String SELECT_ANULACION_PRIMAS_SBS = 
										"SELECT ROWNUM AS CODIGO_FILA, " 
									  + "DRS.ID, "
									  + "DRS.ID_DETALLE_TRAMA_MENSUAL, "
									  + "DTD.FEC_REGISTRO AS FEC_REGISTRO_ANULACION, "
									  + "DTD.FEC_REGISTRO, "
									  + "DTD.NRO_CERTIFICADO AS NRO_POLIZA, " 
									  + "DTD.NRO_CERTIFICADO, " 
									  + "DTD.PLACA, "
									  + "DTD.IMPORTE_COBRO AS PRIMA_COMERCIAL_BRUTA, " 
									  + "(DTD.IMPORTE_COBRO - DRL.DE - "
									  + "		(DTD.IMPORTE_COBRO * ((SELECT TO_NUMBER(NOMBRE_VALOR, '999.99') " 
									  + "			FROM PARAMETRO_AUTOMAT WHERE COD_PARAM='" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND "
									  + "								COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00))) " 
									  + "								AS PRIMA_COMERCIAL, " 
									  + "DRL.FND_COMP AS MONTO_FONDO_SOAT, "
									  + "DTD.FEC_INI_VIG_POLIZA AS FECHA_INI_VIGENCIA " 
									  + "FROM DETALLE_REPORTE_SBS DRS "
									  + "LEFT JOIN DETALLE_TRAMA_MENSUAL DTD ON DRS.ID_DETALLE_TRAMA_MENSUAL = DTD.ID "
									  + "LEFT JOIN DETALLE_REPORTE_LIQUIDACION DRL ON DTD.ID = DRL.ID_DETALLE_TRAMA_MENSUAL " 
									  + "WHERE (DTD.ESTADO = '" + Constantes.POLIZA_ESTADO_ANULADO + "') AND "
									  + "(DTD.NRO_CERTIFICADO IS NOT NULL) AND "
									  + "(EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anio,jdbcType=NUMERIC}) AND " 
									  + "(EXTRACT(MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{mes,jdbcType=NUMERIC}) AND "
									  + "(DRS.ESTADO_REPORTADO IS NULL OR DRS.ESTADO_REPORTADO = '" + Constantes.SBS_ESTADO_REPORTADO_PROD + "') " 
									  + "ORDER BY DTD.FEC_REGISTRO, DTD.PLACA ASC";
	
	/*
	 * Reporte: S – 18
	 * 		Busca la sumatoria de las polizas con estado VIGENTE agrupandolas por nombre del socio y fecha de contrato, el socio debe de pertenecer
	 * 		al grupo de comercio, se busca segun el anio y un periodo (inicio a fin).
	 * 
	 * 		- Polizas con estado VIGENTE:							DTD.ESTADO='VIGENTE'
	 * 		- Polizas cuyo socio pertenece al Grupo de Comercio:	SOC.GRUPO_COMERCIO='1'
	 * 		- Polizas segun el anio: 								EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anio,jdbcType=NUMERIC}
	 * 		- Polizas segun el periodo (inicio - fin):				EXTRACT (MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) <= #{periodoFin,jdbcType=NUMERIC} 
	 * 																EXTRACT (MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) >= #{periodoIni,jdbcType=NUMERIC}
	 */
	final String SELECT_CONTRATOS_COMERCIO_SBS =
									"SELECT ROWNUM AS CODIGO_FILA, " + 
									"DRS.ID, " +
									"DRS.ID_DETALLE_TRAMA_MENSUAL, " +
									"SOC.ID AS ID_SOCIO, " +
									"SOC.NOMBRE_SOCIO AS NOMBRE_EMPRESA, " +
									"SOC.FECHA_CONTRATO AS FEC_FIRMA_CONTRATO, " +
									"DTD.IMPORTE_COBRO AS MONTO_PRIMAS " +
									"FROM DETALLE_REPORTE_SBS DRS " +
									"LEFT JOIN DETALLE_TRAMA_MENSUAL DTD ON DRS.ID_DETALLE_TRAMA_MENSUAL = DTD.ID " +
									"LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO " +
									"LEFT JOIN PRODUCTO PRO ON TD.PRODUCTO = PRO.ID " +
									"LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID " +
									"WHERE (DTD.ESTADO = '" + Constantes.POLIZA_ESTADO_VIGENTE + "') AND " +
									"(SOC.GRUPO_COMERCIO='" + Constantes.SOC_GRUPO_COMERCIO_ACTIVO + "') AND " +
									"(EXTRACT (YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anio,jdbcType=NUMERIC}) AND " +
									"(EXTRACT (MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) <= #{periodoFin,jdbcType=NUMERIC}) AND " +
									"(EXTRACT (MONTH FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) >= #{periodoIni,jdbcType=NUMERIC}) " +
									"ORDER BY SOC.NOMBRE_SOCIO ASC ";
	
	
	public DetalleReporteSBS selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int updateByPrimaryKeySelective(DetalleReporteSBS record);
	
	@Select(SELECT_ROWLAST_BY_PK_DETALLE_TRAMA_DIARIA)
	@ResultMap("BaseResultMap")
	public List<DetalleReporteSBS> selectRowLastByPkDetalleTramaDiaria(@Param("pkDetalleTramaDiaria") Long pkDetalleTramaDiaria);
	
	@Select(SELECT_PRODUCCION_PRIMAS_SBS)
	@ResultMap("RepProduccionPrimaResultMap")
	public List<RepProduccionPrimaSBSBean> selectSBSProduccionPrimas(@Param("anio") Integer anio, @Param("mes") Integer mes);
	
	@Select(SELECT_ANULACION_PRIMAS_SBS)
	@ResultMap("RepAnulacionPrimaResultMap")
	public List<RepAnulacionPrimaSBSBean> selectSBSAnulacionPrimas(@Param("anio") Integer anio, @Param("mes") Integer valueOf);
	
	@Select(SELECT_CONTRATOS_COMERCIO_SBS)
	@ResultMap("RepContratoComercioResultMap")
	public List<RepContratoSoatSBSBean> selectSBSContratosComercio(@Param("anio") Integer anio, @Param("periodoIni") Integer periodoIni, @Param("periodoFin") Integer periodoFin);
	
} //DetalleReporteSBSMapper
