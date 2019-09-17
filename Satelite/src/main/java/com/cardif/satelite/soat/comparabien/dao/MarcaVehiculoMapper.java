package com.cardif.satelite.soat.comparabien.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.MarcaVehiculo;

public interface MarcaVehiculoMapper
{
	final String SELECT_MARCA = "SELECT * FROM MARCA_VEHICULO ORDER BY NOMBRE_MARCAVEHICULO ";
	
	final String SELECT_VALIDATION = " SELECT COUNT(*) FROM MARCA_VEHICULO WHERE NOMBRE_MARCAVEHICULO = #{nombremarca} ORDER BY NOMBRE_MARCAVEHICULO  ";	
	@Select(SELECT_VALIDATION)
	public int validateMarcar(@Param("nombremarca") String marca);
	
	public MarcaVehiculo selectByPrimaryKey(BigDecimal bigDecimal);
	
	@Select(SELECT_MARCA)
	@ResultMap(value = "BaseResultMap")
	public List<MarcaVehiculo> selectMarca();
	
	public int insertSelective(MarcaVehiculo record);

} //MarcaVehiculoMapper
