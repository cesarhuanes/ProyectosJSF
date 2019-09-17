package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Empresa;
import com.cardif.satelite.model.reportesbs.FirmanteCargo;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.ParametrosSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.Producto;
import com.cardif.satelite.model.reportesbs.ReporteSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.Socio;

public interface ReporteSBSModeloUnoDetalleMapper {

	int insertSelective(ReporteSBSModeloUnoDetalle record);

	List<ParametrosSBSModeloUnoDetalle> selectParametros();

	Parametro selectParametroByPrimaryKey(String codParametro);

	Socio selectSocioByPrimaryKey(Long codSocio);

	Producto selectProductoByPrimaryKey(Long codProducto);

	Empresa selectEmpresaByPrimaryKey(Long codEmpresa);

	List<Parametro> selectListParametroByPrimaryKey(String codParametro);

	final String SELECT_FIRMANTE_CARGO = "SELECT  FIR.CODIGO_FIRMANTE ,"
			+ " (SELECT  DESCRIPCION_VALOR FROM  PARAMETRO WHERE COD_PARAMETRO=FIR.CODIGO_FIRMANTE ) AS FIRMANTE,"
			+ " FIR.COD_CARGO ,"
			+ " (SELECT  DESCRIPCION_VALOR FROM  PARAMETRO WHERE COD_PARAMETRO=FIR.COD_CARGO) AS CARGO "
			+ " FROM  FIRMANTE  FIR"
			+ " INNER JOIN PARAMETRO PAR ON FIR.COD_REPORTE=PAR.COD_PARAMETRO"
			+ " AND PAR.COD_PARAMETRO=#{codReporte}";

	@Select(SELECT_FIRMANTE_CARGO)
	@ResultMap(value = "firmanteCargo")
	List<FirmanteCargo> listaFirmanteCargo(
			@Param("codReporte") String codReporte);

}
