package com.cardif.satelite.moduloimpresion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;

public interface NumeroDocValoradoMapper
{
	final String SELECT_DOC_VALORADO_BY_PRODUCTO_AND_TIPO = "SELECT ID, ANIO, NUM_CERTIFICADO, TIPO_CERTIFICADO, PRODUCTO, DISPONIBLE, NRO_POLIZA_REL, ANULADO FROM NUMERO_DOC_VALORADO " +
			"WHERE DISPONIBLE=" + Constantes.COD_NUM_DOC_VALORADO_DISPONIBLE + " AND (TIPO_CERTIFICADO LIKE '%' || #{codTipoCertificado,jdbcType=VARCHAR} || '%') " + 
			"AND (PRODUCTO LIKE '%' || #{codProducto,jdbcType=NUMERIC} || '%') ORDER BY NUM_CERTIFICADO ASC";
	
	final String SELECT_CERTIFICADO_BY_PARAMETROS = "SELECT ID, ANIO, NUM_CERTIFICADO, TIPO_CERTIFICADO, PRODUCTO, DISPONIBLE, NRO_POLIZA_REL, ANULADO FROM NUMERO_DOC_VALORADO " + 
			"WHERE (NUM_CERTIFICADO LIKE '%' || #{numeroCertificado,jdbcType=VARCHAR} || '%') AND (ANIO LIKE '%' || #{anioCertificado,jdbcType=NUMERIC} || '%') " + 
			"AND (TIPO_CERTIFICADO LIKE '%' || #{tipoCertificado,jdbcType=VARCHAR} || '%')";
	
	final String SELECT_CERTIFICADO_BY_PARAMETROS_AND_PRODUCTO = "SELECT ID, ANIO, NUM_CERTIFICADO, TIPO_CERTIFICADO, PRODUCTO, DISPONIBLE, NRO_POLIZA_REL, ANULADO FROM NUMERO_DOC_VALORADO " + 
			"WHERE (NUM_CERTIFICADO LIKE '%' || #{numeroCertificado,jdbcType=VARCHAR} || '%') AND (ANIO LIKE '%' || #{anioCertificado,jdbcType=NUMERIC} || '%') " + 
			"AND (TIPO_CERTIFICADO LIKE '%' || #{tipoCertificado,jdbcType=VARCHAR} || '%') AND (PRODUCTO LIKE '%' || #{codProducto,jdbcType=NUMERIC} || '%')";
	
	
	public NumeroDocValorado selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int insert(NumeroDocValorado record);
	
	public int insertSelective(NumeroDocValorado record);
	
	public int updateByPrimaryKeySelective(NumeroDocValorado record);
	
	public int updateByPrimaryKey(NumeroDocValorado record);
	
	@Select(SELECT_DOC_VALORADO_BY_PRODUCTO_AND_TIPO)
	@ResultMap("BaseResultMap")
	public List<NumeroDocValorado> selectNumeroDocValoradoByProductoAndTipo(@Param("codProducto") Long codProducto, @Param("codTipoCertificado") String codTipoCertificado);
	
	@Select(SELECT_CERTIFICADO_BY_PARAMETROS)
	@ResultMap("BaseResultMap")
	public NumeroDocValorado selectCertificadoByParametros(@Param("numeroCertificado") String numeroCertificado, @Param("anioCertificado") String anioCertificado, 
			@Param("tipoCertificado") String tipoCertificado);
	
	@Select(SELECT_CERTIFICADO_BY_PARAMETROS_AND_PRODUCTO)
	@ResultMap("BaseResultMap")
	public NumeroDocValorado selectCertificadoByParametrosAndProducto(@Param("numeroCertificado") String numeroCertificado, @Param("anioCertificado") String anioCertificado, 
			@Param("tipoCertificado") String tipoCertificado, @Param("codProducto") Long codProducto);
	
} //NumeroDocValoradoMapper
