package com.cardif.satelite.soat.comparabien.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.TarifaExcepcion;
import com.cardif.satelite.suscripcion.bean.TarifaModeloBean;

public interface TarifaExcepcionMapper
{
	final String SELECT_BY_PK_MARCA_AND_NOMBRE_MODELO = "SELECT DISTINCT MOV.ID, MOV.NOMBRE_MODELOVEHICULO AS NOMBRE_MODELO FROM TARIFA_EXCEPCION TE " +
			"LEFT JOIN MODELO_VEHICULO MOV ON TE.MODELO = MOV.ID LEFT JOIN MARCA_VEHICULO MAV ON MOV.MARCA_VEHICULO = MAV.ID " +
			"WHERE MAV.ID = #{pkMarca,jdbcType=NUMERIC} AND #{nombreModelo,jdbcType=VARCHAR} LIKE '%' || UPPER(MOV.NOMBRE_MODELOVEHICULO) || '%' " +
			"ORDER BY MOV.NOMBRE_MODELOVEHICULO ASC";
	
	final String SELECT_BY_CODIGO_MODELO = "SELECT ID, PLAN, COBERTURA, VALOR, DEPARTAMENTO, USO, CATEGORIA, MODELO, SOCIO, VERSION FROM TARIFA_EXCEPCION " +
			"WHERE MODELO = #{codigoModelo,jdbcType=VARCHAR} ORDER BY ID ASC";
	
	
	public List<TarifaExcepcion> selectByPrimaryKey(Long id);
	
	public int insertSelective(TarifaExcepcion record);
	
	@Select(SELECT_BY_PK_MARCA_AND_NOMBRE_MODELO)
	@ResultMap("TarifaModeloResultMap")
	public List<TarifaModeloBean> selectByPkMarcaAndNombreModelo(@Param("pkMarca") Long pkMarca, @Param("nombreModelo") String nombreModelo);
	
	@Select(SELECT_BY_CODIGO_MODELO)
	@ResultMap("BaseResultMap")
	public List<TarifaExcepcion> selectByCodigoModelo(@Param("codigoModelo") Long codigoModelo);
	
} //TarifaExcepcionMapper
