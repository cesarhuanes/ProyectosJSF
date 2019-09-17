package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Socio;

public interface SocioMapper {
	final String SELECT_SOCIO = "SELECT COD_SOCIO,DESCRIPCION FROM  SOCIO WHERE ESTADO=1 order by descripcion asc";

	@Select(SELECT_SOCIO)
	@ResultMap(value = "BaseResultMapSocio")
	List<Socio> getListaSocio();

	
}
