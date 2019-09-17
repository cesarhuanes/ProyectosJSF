package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.ModeloVehiculo;

public interface ModeloVehiculoMapper
{
	final String SELECT_BY_MARCA_VEHICULO = "SELECT MODV.ID, MODV.NOMBRE_MODELOVEHICULO, MODV.MARCA_VEHICULO, MODV.CATEGORIA_CLASE_VEHICULO FROM MODELO_VEHICULO MODV " +
			"LEFT JOIN MARCA_VEHICULO MARV ON MODV.MARCA_VEHICULO = MARV.ID LEFT JOIN CATEGORIA_CLASE CATC ON MODV.CATEGORIA_CLASE_VEHICULO = CATC.COD_CATEGORIA_CLASE " +
			"WHERE (MARV.ID = #{idMarcaVehiculo,jdbcType=NUMERIC})  " + 
			"ORDER BY MODV.NOMBRE_MODELOVEHICULO ASC";
	
	final String SELECT_BY_NOMBRE_MODELO_VEHICULO = "SELECT ID, NOMBRE_MODELOVEHICULO, MARCA_VEHICULO, CATEGORIA_CLASE_VEHICULO FROM MODELO_VEHICULO " +
			"WHERE UPPER(NOMBRE_MODELOVEHICULO) LIKE '%' || #{nombreModeloVehiculo,jdbcType=VARCHAR} || '%' ORDER BY NOMBRE_MODELOVEHICULO ASC";
	
	final String SELECT_BY_PK_MARCA_AND_NOMBRE_MODELO = "SELECT MOV.ID, MOV.NOMBRE_MODELOVEHICULO, MOV.MARCA_VEHICULO, MOV.CATEGORIA_CLASE_VEHICULO " +
			"FROM MODELO_VEHICULO MOV LEFT JOIN MARCA_VEHICULO MAV ON MOV.MARCA_VEHICULO = MAV.ID " +
			"WHERE MAV.ID = #{pkMarcaVehiculo,jdbcType=NUMERIC} AND MOV.NOMBRE_MODELOVEHICULO = #{nombreModeloVehiculo,jdbcType=VARCHAR} AND ROWNUM = 1 " +
			"ORDER BY MOV.NOMBRE_MODELOVEHICULO ASC";
	
	final String SELECT_BY_NOMBRE_MARCA_AND_NOMBRE_MODELO = "SELECT MOV.ID, MOV.NOMBRE_MODELOVEHICULO, MOV.MARCA_VEHICULO, MOV.CATEGORIA_CLASE_VEHICULO " +
			"FROM MODELO_VEHICULO MOV LEFT JOIN MARCA_VEHICULO MAV ON MOV.MARCA_VEHICULO = MAV.ID " +
			"WHERE MAV.NOMBRE_MARCAVEHICULO = #{nombreMarcaVehiculo,jdbcType=VARCHAR} AND MOV.NOMBRE_MODELOVEHICULO = #{nombreModeloVehiculo,jdbcType=VARCHAR} " +
			"AND ROWNUM = 1 ORDER BY MOV.NOMBRE_MODELOVEHICULO ASC";
	
	
	public ModeloVehiculo selectByPrimaryKey(Long id);
	
	public int insertSelective(ModeloVehiculo record);
	
	@Select(SELECT_BY_MARCA_VEHICULO)
	@ResultMap("BaseResultMap")
	public List<ModeloVehiculo> selectByPkMarcaVehiculo(@Param("idMarcaVehiculo") Long idMarcaVehiculo);
	
	@Select(SELECT_BY_PK_MARCA_AND_NOMBRE_MODELO)
	@ResultMap("BaseResultMap")
	public ModeloVehiculo selectByPkMarcaAndNombreModelo(@Param("pkMarcaVehiculo") Long pkMarcaVehiculo, @Param("nombreModeloVehiculo") String nombreModeloVehiculo);
	
	@Select(SELECT_BY_NOMBRE_MARCA_AND_NOMBRE_MODELO)
	@ResultMap("BaseResultMap")
	public ModeloVehiculo selectByNombreMarcaAndNombreModelo(@Param("nombreMarcaVehiculo") String nombreMarcaVehiculo, @Param("nombreModeloVehiculo") String nombreModeloVehiculo);
	
} //ModeloVehiculoMapper
