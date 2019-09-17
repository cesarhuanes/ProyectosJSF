package com.cardif.satelite.moduloimpresion.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.moduloimpresion.DetalleLoteImpresion;

public interface DetalleLoteImpresionMapper
{
	final String SELECT_BY_LOTE_IMPRESION_AND_DET_TRAMA_DIARIA = "SELECT DLI.ID, DLI.LOTE_IMPRESION, DLI.DETALLE_TRAMA_DIARIA, DLI.IMPRESO_CORRECTAMENTE FROM DETALLE_LOTE_IMPRESION DLI " +
			"WHERE (DLI.LOTE_IMPRESION = #{pkLoteImpresion,jdbcType=NUMERIC}) AND (DLI.DETALLE_TRAMA_DIARIA = #{pkDetalleTramaDiaria,jdbcType=NUMERIC})";
	
	
	public DetalleLoteImpresion selectByPrimaryKey(Long pk);
	
	public int deleteByPrimaryKey(Long pk);
	
	public int insert(DetalleLoteImpresion record);
	
	public int updateByPrimaryKeySelective(DetalleLoteImpresion record);
	
	public int updateByPrimaryKey(DetalleLoteImpresion record);
	
	@Select(SELECT_BY_LOTE_IMPRESION_AND_DET_TRAMA_DIARIA)
	@ResultMap("BaseResultMap")
	public DetalleLoteImpresion selectByLoteImpresionAndDetalleTramaDiaria(@Param("pkLoteImpresion") Long pkLoteImpresion, @Param("pkDetalleTramaDiaria") Long pkDetalleTramaDiaria);
	
} //DetalleLoteImpresionMapper
