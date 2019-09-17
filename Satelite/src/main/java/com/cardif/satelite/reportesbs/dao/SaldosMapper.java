package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Saldos;

public interface SaldosMapper {

	public final String SELECT_SALDOS_BY_PARAM = "SELECT  * FROM  SALDOS WHERE TIPO_REASEGURO=#{tipoReaseguro} AND MODELO_MONEDA=#{monedaModeloValor} AND MONEDA=#{monedaCuenta} AND COD_EMPRESA=#{codigoEmpresa} ";

	int insert(Saldos record);

	int insertSelective(Saldos record);

	@Select(SELECT_SALDOS_BY_PARAM)
	@ResultMap(value = "BaseResultMap")
	List<Saldos> selectSaldosByParam(
			@Param("monedaCuenta") String monedaCuenta,
			@Param("monedaModeloValor") String monedaModeloValor,
			@Param("codigoEmpresa") Long codigoEmpresa,
			@Param("tipoReaseguro") String tipoReaseguro);
}