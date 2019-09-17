package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.UsoVehiculo;

public interface UsoVehiculoMapper
{
	public final String SELECT_ALL_USO_VEHICULOS = "SELECT COD_USO, DESCRIPCION_USO FROM USO_VEHICULO ORDER BY COD_USO ASC";
	
	
	public UsoVehiculo selectByPrimaryKey(String codUso);
	
	@Select(SELECT_ALL_USO_VEHICULOS)
	@ResultMap("BaseResultMap")
	public List<UsoVehiculo> selectAllUsoVehiculos();
	
} //SocioMapper
