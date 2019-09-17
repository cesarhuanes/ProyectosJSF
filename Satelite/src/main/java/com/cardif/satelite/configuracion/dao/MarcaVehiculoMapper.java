package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.MarcaVehiculo;

public interface MarcaVehiculoMapper
{
	final String SELECT_BY_CATEGORIA_CLASE = "SELECT DISTINCT MARV.ID AS ID, MARV.NOMBRE_MARCAVEHICULO AS NOMBRE_MARCAVEHICULO FROM MARCA_VEHICULO MARV " +
			"LEFT JOIN MODELO_VEHICULO MODV ON MODV.MARCA_VEHICULO = MARV.ID LEFT JOIN CATEGORIA_CLASE CATC ON MODV.CATEGORIA_CLASE_VEHICULO = CATC.COD_CATEGORIA_CLASE " +
			"WHERE NVL(CATC.COD_CATEGORIA_CLASE, ' ') = NVL(#{codCategoriaClase,jdbcType=VARCHAR}, ' ') ORDER BY MARV.NOMBRE_MARCAVEHICULO ASC";
	
	final String SELECT_BY_NOMBRE_MARCA_VEHICULO = "SELECT ID, NOMBRE_MARCAVEHICULO FROM MARCA_VEHICULO WHERE UPPER(NOMBRE_MARCAVEHICULO) = #{nombreMarcaVehiculo,jdbcType=VARCHAR} " +
			"AND ROWNUM = 1 ORDER BY NOMBRE_MARCAVEHICULO ASC";
	
	
	public MarcaVehiculo selectByPrimaryKey(Long id);
	
	public int insertSelective(MarcaVehiculo record);
	
	@Select(SELECT_BY_CATEGORIA_CLASE)
	@ResultMap("BaseResultMap")
	public List<MarcaVehiculo> selectByPkCategoriaClase(@Param("codCategoriaClase") String codCategoriaClase);
	
	@Select(SELECT_BY_NOMBRE_MARCA_VEHICULO)
	@ResultMap("BaseResultMap")
	public MarcaVehiculo selectByNombreMarcaVehiculo(@Param("nombreMarcaVehiculo") String nombreMarcaVehiculo);
	
	
	
	
	
} //MarcaVehiculoMapper
