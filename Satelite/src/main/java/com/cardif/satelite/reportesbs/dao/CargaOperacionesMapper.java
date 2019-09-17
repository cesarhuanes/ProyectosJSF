package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.CargaOperaciones;

public interface CargaOperacionesMapper {

	public final String SELECT_DETALLE = " SELECT "
			+ " NOM_EMPRESA, "
			+ " NOM_SOCIO, "
			+ " PRODUCTO, "
			+ " DECODE(BO,NULL,PLANILLA,BO) AS PLANILLA, "
			+ " SUM(NVL(PRIMA_PAGAR_REACEDIDOS,0)) AS PRIMA_PAGAR_REACEDIDOS, "
			+ " SUM(NVL(PRIMA_PAGAR_REACEPTADOS,0)) AS PRIMA_PAGAR_REACEPTADOS, "
			+ " SUM(NVL(SINIESTRO_PAGAR_REASEDIDOS,0)) AS SINIESTRO_PAGAR_REASEDIDOS,"
			+ " SUM(NVL(SINIESTRO_PAGAR_REAACEPTADOS,0)) AS SINIESTRO_PAGAR_REAACEPTADOS, "
			+ " SUM(NVL(CUENTAS_COBRAR,0)) AS CUENTAS_COBRAR, "
			+ " SUM(NVL(CUENTAS_PAGAR,0)) AS CUENTAS_PAGAR, "
			+ " SUM(NVL(DESCUENTOS_COMISIONES_REA,0)) AS DESCUENTOS_COMISIONES_REA,"
			+ " SUM(NVL(SALDO_DEUDOR,0)) AS SALDO_DEUDOR,"
			+ " SUM(NVL(SALDO_ACREEEDOR,0)) AS SALDO_ACREEEDOR "
			+ " FROM CARGA_OPERACIONES "
			+ " WHERE COD_PROCESO_ARCHIVO=#{codProceso} "
			+ " group by DECODE(BO,NULL,PLANILLA,BO),NOM_EMPRESA,NOM_SOCIO,PRODUCTO";

	@Select(SELECT_DETALLE)
	@ResultMap(value = "BaseResultMap2")
	List<CargaOperaciones> getListaDetalle(@Param("codProceso") Long codProceso);

	
	String SELECT_TRAERDETALLECUENTAS =  "  SELECT	"	
			+"	NOMBRE_ASEGURADO, "				
			+"	NUM_POLIZA, "
			+"	NUM_SINIESTRO, "
			+"  PRIMA_PAGAR_REACEDIDOS, "
			+"  PRIMA_PAGAR_REACEPTADOS, "
			+"	SINIESTRO_PAGAR_REASEDIDOS, "
			+"	SINIESTRO_PAGAR_REAACEPTADOS, "
			+"	CUENTAS_COBRAR, "
			+"	CUENTAS_PAGAR, "
			+"	DESCUENTOS_COMISIONES_REA, "
			+"	SALDO_DEUDOR, "
			+"	SALDO_ACREEEDOR "	
			+"	FROM CARGA_OPERACIONES_2 "
			+"	WHERE BO = #{bo} AND TIPO_SEGURO = 'REA' "; 
	//AND MONEDA = 'USD' ";


	String SELECT_TRAERDETALLECUENTASVALIDAR =   "  SELECT	"			
					    +"  SUM(PRIMA_PAGAR_REACEDIDOS) AS  PRIMA_PAGAR_REACEDIDOS , "
					    +"  SUM(PRIMA_PAGAR_REACEPTADOS) AS PRIMA_PAGAR_REACEPTADOS , "
						+"	SUM(SINIESTRO_PAGAR_REASEDIDOS) AS SINIESTRO_PAGAR_REASEDIDOS , "
						+"	SUM(SINIESTRO_PAGAR_REAACEPTADOS) AS SINIESTRO_PAGAR_REAACEPTADOS , "
						+"	SUM(CUENTAS_COBRAR) AS CUENTAS_COBRAR , "
						+"	SUM(CUENTAS_PAGAR) AS CUENTAS_PAGAR , "
						+"	SUM(DESCUENTOS_COMISIONES_REA) AS DESCUENTOS_COMISIONES_REA , "
						+"	SUM(SALDO_DEUDOR) AS SALDO_DEUDOR , "
						+"	SUM(SALDO_ACREEEDOR) AS SALDO_ACREEEDOR "	
						+"	FROM CARGA_OPERACIONES_2 "
						+"	WHERE BO = #{bo} AND TIPO_SEGURO = 'REA' ";
	
	//AND MONEDA = 'USD' ";
	
	int deleteByPrimaryKey(Long codProceso);
	
	@Select(SELECT_TRAERDETALLECUENTAS)
	@ResultMap(value = "BaseResultMap3")
	List<CargaOperaciones> TraerDetalleCuentas(@Param("bo") String bo);
	
	@Select(SELECT_TRAERDETALLECUENTASVALIDAR)
	@ResultMap(value = "BaseResultMap4")
	List<CargaOperaciones> TraerDetalleCuentasValidar(@Param("bo") String bo);

}
