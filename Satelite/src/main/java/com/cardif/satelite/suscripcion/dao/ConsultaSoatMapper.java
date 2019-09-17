package com.cardif.satelite.suscripcion.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.ConsultaSoat;

public interface ConsultaSoatMapper {
	
	final String CONSULTA_POLIZA = "SELECT CODIGO_VENTA FROM VENTA WHERE TIPO_VENTA = 2 and NRO_PLACA = #{nroPlaca,jdbcType=VARCHAR} ";
	
	final String CONSULTA_COBERTURA_GASTOS_MEDICOS = "SELECT COUNT(*) AS CANT_COBERTURAS FROM ORDEN_ATENCIONSALUD WHERE CODIGO_VENTA = #{codVenta,jdbcType=NUMERIC} ";
	
	final String CONSULTA_OTRAS_COBERTURAS = "SELECT COUNT(*) AS CANT_OTRAS_COBERTURAS FROM EVENTO WHERE CODIGO_VENTA= #{codVenta,jdbcType=NUMERIC} and confirmar_evento='SI' ";
	
	@Select(CONSULTA_POLIZA)
    @ResultMap(value = "BaseResultMap")
    public ConsultaSoat consultaPoliza(@Param("nroPlaca") String nroPlaca);
	
	@Select(CONSULTA_COBERTURA_GASTOS_MEDICOS)
    @ResultMap(value = "BaseResultMap")
    public ConsultaSoat consultaCoberturaGastosMedicos(@Param("codVenta") Long codVenta);
	
	@Select(CONSULTA_OTRAS_COBERTURAS)
    @ResultMap(value = "BaseResultMap")
    public ConsultaSoat consultaOtrasCoberturas(@Param("codVenta") Long codVenta);

}
