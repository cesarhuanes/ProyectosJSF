package com.cardif.satelite.suscripcion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.CanalSubProducto;

public interface CanalSubProductoMapper {

  final String SELECT_CANAL = "SELECT ID, ID_PRODUCTO, NOMBRE_CANAL, CONCILIAR, ESTADO FROM CANAL_PRODUCTO WHERE ID_PRODUCTO = #{subProducto,jdbcType=VARCHAR}";

  final String SELECT_LISTA_CANALES = "SELECT DISTINCT CANAL FROM CANAL_SUBPRODUCTO";

  final String SELECT_LISTA_CANALES_PRODUCTO ="SELECT ID, ID_PRODUCTO, NOMBRE_CANAL, CONCILIAR, ESTADO FROM CANAL_PRODUCTO WHERE ID_PRODUCTO = #{Producto,jdbcType=DECIMAL}";
  
  @Select(SELECT_CANAL)
  String selectCanal(@Param("subProducto") String subProducto);

  int insert(CanalSubProducto record);

  @Select(SELECT_LISTA_CANALES)
  List<String> selectListaCanales();
  
  @Select(SELECT_LISTA_CANALES_PRODUCTO)
  List<CanalProducto> selectListaCanalesProducto(@Param("Producto") Long Producto);

}