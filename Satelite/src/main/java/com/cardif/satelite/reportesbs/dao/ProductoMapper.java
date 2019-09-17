package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Producto;

public interface ProductoMapper {
	final String SELECT_PRODUCTO = "SELECT COD_PRODUCTO,DESCRIPCION FROM  PRODUCTO WHERE ESTADO=1 order by descripcion asc";

	@Select(SELECT_PRODUCTO)
	@ResultMap(value = "BaseResultMapProducto")
	List<Producto> getListaProducto();

	
}
