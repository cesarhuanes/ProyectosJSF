package com.cardif.satelite.soat.falabella.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.ModeloVehiculo;

public interface ModeloVehiculoMapper {

	final String SELECT_MODELO = "SELECT * FROM MODELO_VEHICULO WHERE MARCA_VEHICULO = #{codMarca} ORDER BY NOMBRE_MODELOVEHICULO";
	
	final String SELECT_VALIDA = " SELECT COUNT(*) AS NUM FROM  MODELO_VEHICULO WHERE NOMBRE_MODELOVEHICULO = '#{nombremodelo}' "
			+   " AND MARCA_VEHICULO = (SELECT ID FROM MARCA_VEHICULO WHERE MARCA_VEHICULO.NOMBRE_MARCAVEHICULO = '#{nombreMarca}' AND  ROWNUM < 2 ) ";

	@Select(SELECT_VALIDA)
	public int validateModelo(@Param("nombremodelo") String nombreModelo, @Param("nombreMarca") String nombreMarca); 


	@Select(SELECT_MODELO)
	@ResultMap(value = "BaseResultMap")
	List<ModeloVehiculo> selectModelo(@Param("codMarca") BigDecimal codMarca);

	ModeloVehiculo selectByPrimaryKey(BigDecimal bigDecimal);

	public int insertSelective(ModeloVehiculo record);
}