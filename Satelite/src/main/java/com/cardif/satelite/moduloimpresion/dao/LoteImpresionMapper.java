package com.cardif.satelite.moduloimpresion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.moduloimpresion.LoteImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaLoteImpresion;

public interface LoteImpresionMapper
{
	final String SELECT_LOTE_IMPRESION_BY_PARAMETERS = "SELECT LI.NUM_LOTE, LI.ESTADO, LI.FEC_CREACION, LI.USUARIO, "+
			"(CASE LI.ESTADO WHEN 'PENDIENTE' THEN 'Confirmar Impresión' "+
			"                WHEN 'IMPRESO'   THEN 'Generar Archivo de Despacho' ELSE '-' END)  DESC_LINK, to_char(LI.FEC_CREACION,'YYYY-MM-DD' ) as FECHA_CREACION "+
            "FROM LOTE_IMPRESION LI " + 
			"WHERE (LI.PRODUCTO LIKE '%' || NVL(#{codProducto,jdbcType=NUMERIC}, LI.PRODUCTO) || '%') " + 
			"AND (LI.ESTADO LIKE '%' || NVL(#{estadoLote,jdbcType=VARCHAR}, LI.ESTADO) || '%') " + 
			"AND (LI.NUM_LOTE LIKE '%' || NVL(#{numLote,jdbcType=VARCHAR}, LI.NUM_LOTE) || '%') " + 
			"AND (to_char(LI.FEC_CREACION,'YYYYMMDD' ) >= #{fecCreacionDesde} AND to_char(LI.FEC_CREACION,'YYYYMMDD' ) <=  #{fecCreacionHasta} ) " + 
			"ORDER BY LI.NUM_LOTE ASC";
	
	LoteImpresion selectByPrimaryKey(Long pk);
	
	int deleteByPrimaryKey(Long pk);
	
	int insert(LoteImpresion record);
	
	int updateByPrimaryKeySelective(LoteImpresion record);
	
	int updateByPrimaryKey(LoteImpresion record);
	
	@Select(SELECT_LOTE_IMPRESION_BY_PARAMETERS)
	@ResultMap("ConsultaLoteImpresionResultMap")
	List<ConsultaLoteImpresion> selectLoteImpresionByParameters(@Param("codProducto") Long codProducto, @Param("estadoLote") String estadoLote, @Param("numLote") String numLote, @Param("fecCreacionDesde") String fecCreacionDesde, @Param("fecCreacionHasta") String fecCreacionHasta);
	
} //LoteImpresionMapper
