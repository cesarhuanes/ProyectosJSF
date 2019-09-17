package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.Socio;

public interface SocioMapper
{
	final String SELECT_BY_PK_DETALLE_TRAMA_DIARIA = "SELECT SO.ID, SO.RUC_SOCIO, SO.NOMBRE_SOCIO, SO.ABREVIATURA, SO.FECHA_CONTRATO, SO.GRUPO_COMERCIO, SO.ESTADO, SO.FECHA_CREACION, SO.FECHA_MODIFICACION, SO.USUARIO_CREACION, SO.USUARIO_MODIFICACION " + 
			"FROM SOCIO SO LEFT JOIN PRODUCTO PRO ON SO.ID = PRO.ID_SOCIO LEFT JOIN TRAMA_DIARIA TD ON PRO.ID = TD.PRODUCTO " +
			"LEFT JOIN DETALLE_TRAMA_DIARIA DTD ON TD.SEC_ARCHIVO = DTD.SEC_ARCHIVO " +
			"WHERE DTD.ID=#{pkDetalleTramaDiaria,jdbcType=NUMERIC} ORDER BY SO.ID ASC";
	
	final String SELECT_BY_MODSUSCRIPCION_ESTADOPROD_ESTADOSOCIO = "SELECT DISTINCT SOC.ID, SOC.NOMBRE_SOCIO, SOC.ABREVIATURA, SOC.FECHA_CONTRATO, SOC.GRUPO_COMERCIO, SOC.ESTADO, SOC.RUC_SOCIO, SOC.FECHA_CREACION, SOC.FECHA_MODIFICACION, SOC.USUARIO_CREACION, SOC.USUARIO_MODIFICACION " +
			"FROM SOCIO SOC LEFT JOIN PRODUCTO PROD ON SOC.ID = PROD.ID_SOCIO " +
			"WHERE (PROD.MOD_SUSCRIPCION = NVL(#{modSuscripcionOption,jdbcType=NUMERIC}, PROD.MOD_SUSCRIPCION)) AND (PROD.ESTADO = NVL(#{estadoProducto,jdbcType=NUMERIC}, PROD.ESTADO)) " +
			"AND (SOC.ESTADO = NVL(#{estadoSocio,jdbcType=NUMERIC}, SOC.ESTADO)) ORDER BY SOC.NOMBRE_SOCIO ASC";
	
	final String SELECT_BY__ESTADOPROD_ESTADOSOCIO = "SELECT DISTINCT SOC.ID, SOC.NOMBRE_SOCIO, SOC.ABREVIATURA, SOC.FECHA_CONTRATO, SOC.GRUPO_COMERCIO, SOC.ESTADO, SOC.RUC_SOCIO, SOC.FECHA_CREACION, SOC.FECHA_MODIFICACION, SOC.USUARIO_CREACION, SOC.USUARIO_MODIFICACION " +
			"FROM SOCIO SOC LEFT JOIN PRODUCTO PROD ON SOC.ID = PROD.ID_SOCIO " +
			"WHERE (SOC.ESTADO = NVL(#{estadoSocio,jdbcType=NUMERIC}, SOC.ESTADO)) ORDER BY SOC.NOMBRE_SOCIO ASC";

	
	final String SELECT_ALL_SOCIOS = "SELECT ID, RUC_SOCIO, NOMBRE_SOCIO, ABREVIATURA, FECHA_CONTRATO, GRUPO_COMERCIO, ESTADO, FECHA_CREACION, FECHA_MODIFICACION, USUARIO_CREACION, USUARIO_MODIFICACION " +
			"FROM SOCIO ORDER BY NOMBRE_SOCIO ASC";
	
	final String SELECT_SOCIOS_BY_GRUPO_COMERCIO = "SELECT ID, RUC_SOCIO, NOMBRE_SOCIO, ABREVIATURA, FECHA_CONTRATO, GRUPO_COMERCIO, ESTADO, FECHA_CREACION, FECHA_MODIFICACION, USUARIO_CREACION, USUARIO_MODIFICACION " +
			"FROM SOCIO WHERE GRUPO_COMERCIO = #{grupoComercio,jdbcType=NUMERIC} ORDER BY NOMBRE_SOCIO, FECHA_CONTRATO ASC";
	
	
	public Socio selectByPrimaryKey(Long id);
	
	public Socio selectByRuc(String rucSocio);
	
	public int deleteByPrimaryKey(Long id);
	
	public int insert(Socio record);
	
	public int insertSelective(Socio record);
	
	public int updateByPrimaryKeySelective(Socio record);
	
	public int updateByPrimaryKey(Socio record);
	
	@Select(SELECT_BY_PK_DETALLE_TRAMA_DIARIA)
	@ResultMap("BaseResultMap")
	public Socio selectByPkDetalleTramaDiaria(@Param("pkDetalleTramaDiaria") Long pkDetalleTramaDiaria);
	
	@Select(SELECT_BY_MODSUSCRIPCION_ESTADOPROD_ESTADOSOCIO)
	@ResultMap("BaseResultMap")
	public List<Socio> selectByModSuscripcionAndEstadoProdAndEstadoSocio(@Param("modSuscripcionOption") Integer modSuscripcionOpcion, @Param("estadoProducto") Integer estadoProducto, @Param("estadoSocio") Integer estadoSocio);
	
	@Select(SELECT_BY__ESTADOPROD_ESTADOSOCIO)
	@ResultMap("BaseResultMap")
	public List<Socio> selectBySocioEstadoProdAndEstadoSocio( @Param("estadoSocio") Integer estadoSocio);
	
	
	@Select(SELECT_ALL_SOCIOS)
	@ResultMap("BaseResultMap")
	public List<Socio> selectAllSocios();
	
	@Select(SELECT_SOCIOS_BY_GRUPO_COMERCIO)
	@ResultMap("BaseResultMap")
	public List<Socio> selectSociosByGrupoComercio(@Param("grupoComercio") String grupoComercio);
	
} //SocioMapper
