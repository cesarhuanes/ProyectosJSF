package com.cardif.satelite.soat.cencosud.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.ModeloVehiculo;

public interface ModeloVehiculoMapper
{
	final String SELECT_MODELO = "SELECT * FROM MODELO_VEHICULO WHERE MARCA_VEHICULO = #{codMarca} ORDER BY NOMBRE_MODELOVEHICULO";
	
  	
	public ModeloVehiculo selectByPrimaryKey(BigDecimal bigDecimal);
	
	@Select(SELECT_MODELO)
	@ResultMap(value = "BaseResultMap")
	public List<ModeloVehiculo> selectModelo(@Param("codMarca") BigDecimal codMarca);
	
	public int insertSelective(ModeloVehiculo record);

} //ModeloVehiculoMapper
