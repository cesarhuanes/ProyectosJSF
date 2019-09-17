package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.CanalProducto;

public interface CanalProductoMapper
{	
	final String SELECT_BY_ESTADO = "SELECT ID, "
			+ "NOMBRE_CANAL,  "
			+ "ESTADO "
			+ "FROM CANAL_PRODUCTO "
			+ "WHERE ESTADO = #{estado,jdbcType=NUMERIC} "
			+ "AND ID != "+ Constantes.ID_CANAL_DIGITAL 
			+ "ORDER BY NOMBRE_CANAL ASC";
	
	final String SELECT_LISTA_CANALES_PRODUCTO ="SELECT ID,  NOMBRE_CANAL,  ESTADO FROM CANAL_PRODUCTO WHERE ID in (#{Producto,jdbcType=VARCHAR})";
	final String SELECT_CANAL =" SELECT ID,  NOMBRE_CANAL,  ESTADO FROM CANAL_PRODUCTO WHERE ID<>1";

	final String SELECT_BY_COD_SOCIO = "SELECT DISTINCT CAP.ID, CAP.NOMBRE_CANAL,  CAP.ESTADO, CAP.CANAL_MASTER " +
			"FROM CANAL_PRODUCTO CAP INNER JOIN PRODUCTO PROD ON CAP.ID = PROD.ID_CANAL INNER JOIN SOCIO SOC ON PROD.ID_SOCIO = SOC.ID " +
			"WHERE (PROD.MOD_SUSCRIPCION = NVL(#{modSuscripcionOpcion,jdbcType=NUMERIC}, PROD.MOD_SUSCRIPCION)) " +
			"AND (CAP.ESTADO = NVL(#{estadoCanal,jdbcType=NUMERIC}, CAP.ESTADO)) " +
			"AND (PROD.ESTADO = NVL(#{estadoProducto,jdbcType=NUMERIC}, PROD.ESTADO)) " +
			"AND (SOC.ESTADO = NVL(#{estadoSocio,jdbcType=NUMERIC}, SOC.ESTADO)) " +
			"AND (SOC.ID = NVL(#{codSocio,jdbcType=NUMERIC}, SOC.ID)) ORDER BY CAP.NOMBRE_CANAL ASC";
	
	
	public CanalProducto selectByPrimaryKey(Long id);
	
	@Select(SELECT_BY_ESTADO)
	@ResultMap("BaseResultMap")
	public List<CanalProducto> selectByEstado(int estado);
	
	@Select(SELECT_LISTA_CANALES_PRODUCTO)
	@ResultMap("BaseResultMap")
	public List<CanalProducto> selectListaCanalesProducto(@Param("Producto") String Producto);
	
//	public List<CanalProducto> selectByPrimaryKey(Long idProducto);
	 
	@Select(SELECT_CANAL)
	@ResultMap("BaseResultMap")
	public List<CanalProducto> selectAll(); 
	
	@Select(SELECT_BY_COD_SOCIO)
	@ResultMap("BaseResultMap")
	public List<CanalProducto> selectByCodSocio(@Param("modSuscripcionOpcion") Integer modSuscripcionOpcion, @Param("estadoSocio") Integer estadoSocio, @Param("estadoProducto") Integer estadoProducto, @Param("estadoCanal") Integer estadoCanal, @Param("codSocio") Long codSocio);
	
} //CanalProductoMapper
