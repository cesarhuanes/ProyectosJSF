package com.cardif.satelite.soat.cencosud.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.OrdenCompraPe;

public interface OrdenCompraPeMapper
{
	final String SELECT_BY_PK_POLIZA_PE = "SELECT OCP.ORDEN_COMPRA, OCP.CODIGO_SOCIO, OCP.ESTADO_PAGO, OCP.FECHA, OCP.GLOSA_ESTADO_PAGO, OCP.HORA, OCP.MONTO, OCP.NUMERO_TRANSACCION, OCP.VERSION " +
			"FROM ORDEN_COMPRA_PE OCP LEFT JOIN POLIZA_PE POP ON OCP.ORDEN_COMPRA = POP.ORDEN_COMPRA WHERE POP.ID = #{pkPolizaPe,jdbcType=NUMERIC}";
	
	
	public OrdenCompraPe selectByPrimaryKey(Long id);
	
	public int updateByPrimaryKeySelective(OrdenCompraPe record);
	
	@Select(SELECT_BY_PK_POLIZA_PE)
	@ResultMap("BaseResultMap")
	public OrdenCompraPe selectByPkPolizaPe(@Param("pkPolizaPe") Long pkPolizaPe);
	
} //OrdenCompraPeMapper
