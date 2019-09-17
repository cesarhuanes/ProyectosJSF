package com.cardif.satelite.suscripcion.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.model.TramaMaster;
import com.cardif.satelite.suscripcion.bean.ConsultaMasterPrecio;
import com.cardif.satelite.util.SateliteConstants;

public interface TramaMasterMapper {
	 
final String SELECT_TRAMA_MASTER =	  " SELECT ID_MASTER, ID_SOCIO, FECHA_INICIO, FECHA_FIN, RUTA_ARCHIVO, ESTADO_MASTER, USUARIO_CREADOR, USUARIO_MODIFICACION,"
				  					+ " FECHA_CREACION, FECHA_MODIFICACION, ESTADO,NUM_REGISTROS FROM TRAMA_MASTER WHERE ESTADO = '" +SateliteConstants.ESTADO_REGISTRO_ACTIVO +"'";

final String SELECT_TRAMA_MASTER_BY_ESTADO =  " SELECT ID_MASTER, ID_SOCIO, FECHA_INICIO, FECHA_FIN, RUTA_ARCHIVO, ESTADO_MASTER, USUARIO_CREADOR, USUARIO_MODIFICACION,"
											+ " FECHA_CREACION, FECHA_MODIFICACION, ESTADO,NUM_REGISTROS FROM TRAMA_MASTER "
											+ " WHERE ID_SOCIO = NVL(#{idsocio,jdbcType=NVARCHAR}, ID_SOCIO) "
											+ " AND   ESTADO = '" +SateliteConstants.ESTADO_REGISTRO_ACTIVO +"'"
											+ " AND   ESTADO_MASTER = '"+ SateliteConstants.TRAMA_MASTER_VIGENTE +"'";
											
//anterior como estaba funcionando
//final String SELECT_MASTER_SOCIO =  " SELECT TM.ID_MASTER, (SELECT NOMBRE_SOCIO FROM SOCIO WHERE ID = TM.ID_SOCIO) AS LABEL_SOCIO, TM.FECHA_INICIO, TM.FECHA_FIN, TM.RUTA_ARCHIVO, TM.ESTADO_MASTER, TM.USUARIO_CREADOR, TM.USUARIO_MODIFICACION, "
//								  + " TM.FECHA_CREACION, TM.FECHA_MODIFICACION, TM.ESTADO,TM.NUM_REGISTROS FROM TRAMA_MASTER  TM "
//								  + " WHERE ( TM.ID_SOCIO = NVL(#{idsocial,jdbcType=NVARCHAR}, TM.ID_SOCIO)) "
//								  + " AND   ( to_char(TM.FECHA_INICIO,'YYYYMMDD') = to_char(NVL(#{fechaValidez,jdbcType=TIMESTAMP},TM.FECHA_INICIO),'YYYYMMDD') ) "
//								  + " AND   ( to_char(TM.FECHA_CREACION,'YYYYMMDD')  = to_char(NVL(#{fechaCarga,jdbcType=TIMESTAMP},TM.FECHA_CREACION) ,'YYYYMMDD') ) "
//								  + " AND     TM.ESTADO NOT IN ('" +SateliteConstants.ESTADO_REGISTRO_ACTIVO +"') ";

final String SELECT_MASTER_SOCIO =  " SELECT TM.ID_MASTER, (SELECT NOMBRE_SOCIO FROM SOCIO WHERE ID = TM.ID_SOCIO) AS LABEL_SOCIO, TM.FECHA_INICIO, TM.FECHA_FIN, TM.RUTA_ARCHIVO, TM.ESTADO_MASTER, TM.USUARIO_CREADOR, TM.USUARIO_MODIFICACION, "
		  + " TM.FECHA_CREACION, TM.FECHA_MODIFICACION, TM.ESTADO,TM.NUM_REGISTROS FROM TRAMA_MASTER  TM "
		  + " WHERE ( TM.ID_SOCIO = NVL(#{socio,jdbcType=NVARCHAR}, TM.ID_SOCIO)) "
		  + " AND   ( to_char(TM.FECHA_INICIO,'YYYYMMDD') = to_char(NVL(#{fechaVigencia,jdbcType=TIMESTAMP},TM.FECHA_INICIO),'YYYYMMDD') ) "
		  + " AND   ( to_char(TM.FECHA_CREACION,'YYYYMMDD')  = to_char(NVL(#{fechaCarga,jdbcType=TIMESTAMP},TM.FECHA_CREACION) ,'YYYYMMDD') ) "
		  + " AND     TM.ESTADO NOT IN ('" +SateliteConstants.ESTADO_REGISTRO_INACTIVO +"') ";

final String UPDATE_ESTADO = " UPDATE TRAMA_MASTER SET ESTADO = '"+ SateliteConstants.ESTADO_REGISTRO_INACTIVO +"' WHERE ID_MASTER = #{idMaster,jdbcType=VARCHAR}";

final String CONSULTA_DETALLE_MASTER =" SELECT COD_SOCIO,COD_MASTER,CANAL,MEDIO_PAGO,USO,TIPO AS CATEGORIA,DEPARTAMENTO,MARCA,MODELO,CASO,NRO_ASIENTOS,PRIMA_TECNICA,PRIMA_PUBLICA,"
									+ " IGV,DE,FND_COMP,COM_COBRANZA,COM_SOCIO,COM_BROKER_PORCENTAJE,FDO_TLMK,FDO_TLMK_PORCENTAJE,COM_BROKER,COM_BROKER_PORCENTAJE,"
									+ " PRIMA_IGV FROM CONCI_MASTER_PRECIOS_SOAT ";
	
	final String SELECT_TRAMA_MASTER_CABECERA = "SELECT TM.ID_MASTER, SOC.NOMBRE_SOCIO AS LABEL_SOCIO, TM.FECHA_INICIO, TM.FECHA_FIN, TM.RUTA_ARCHIVO, TM.ESTADO_MASTER, " +
			"TM.USUARIO_CREADOR, TM.USUARIO_MODIFICACION, TM.FECHA_CREACION, TM.FECHA_MODIFICACION, TM.ESTADO,TM.NUM_REGISTROS " +
			"FROM TRAMA_MASTER TM LEFT JOIN SOCIO SOC ON TM.ID_SOCIO = SOC.ID " +
			"WHERE (TM.ID_SOCIO = NVL(#{codSocio,jdbcType=NUMERIC}, TM.ID_SOCIO)) AND (TM.ESTADO='" + SateliteConstants.ESTADO_REGISTRO_ACTIVO + "') " +
			"AND (TRUNC(TM.FECHA_INICIO) >= #{fechaIniVigencia}) AND (TRUNC(TM.FECHA_CREACION) >= #{fechaCarga}) " +
			"ORDER BY SOC.NOMBRE_SOCIO, TM.FECHA_INICIO, TM.FECHA_CREACION ASC";
	
	
	final String SELECT_Id_TRAMA =" SELECT MAX(id_master) FROM trama_master WHERE estado_master = 'VIGENTE' AND (ID_SOCIO = NVL(#{codSocio,jdbcType=NUMERIC}, ID_SOCIO)) AND ESTADO ='" + SateliteConstants.ESTADO_REGISTRO_ACTIVO + "'";
			
	@Select(SELECT_TRAMA_MASTER_BY_ESTADO)
	@ResultMap( value = "BaseResultMap")
	public TramaMaster selectValidarMaster(@Param("idsocio") String idsocio);
	
	@Select(SELECT_TRAMA_MASTER)
	@ResultMap( value = "BaseResultMap")
	public List<TramaMaster> selectTramaMasterAll();
	
	 @Select(SELECT_Id_TRAMA)
	 public Long selectIdTrama(@Param("flag_trama") String flagTrama, @Param("codSocio") Integer codSocio);
	
	
	void insert(TramaMaster tramaMaster);
	
	void updateBySelective(TramaMaster tramaMaster);
	
	@Select(SELECT_MASTER_SOCIO)
	@ResultMap( value = "BaseResultConsulta")
	//public List<TramaMaster> selectTramaMasterBySocioFecha(@Param("idsocial") String idsocial,@Param("fechaValidez")  Date fechaValidez, @Param("fechaCarga") Date fechaCarga);//anterior funcionando
	public List<TramaMaster> selectTramaMasterBySocioFecha(@Param("socio") String socio,@Param("fechaVigencia")  Date fechaValidez, @Param("fechaCarga") Date fechaCarga);
	
	@Update(UPDATE_ESTADO)
	public void eliminarMaster(@Param("idMaster") String idMaster);
	
	@Select(CONSULTA_DETALLE_MASTER)
	@ResultMap(value="BaseDetalleMaster")
	public List<ConsultaMasterPrecio> consultaMasterDetalle(@Param("idMaster") String idMaster);
	
	@Select(SELECT_TRAMA_MASTER_CABECERA)
	@ResultMap("BaseResultConsulta")
	public List<TramaMaster> selectTramaMasterCabecera(@Param("codSocio") Long codSocio, @Param("fechaIniVigencia") Date fechaIniVigencia, @Param("fechaCarga") Date fechaCarga);
	
}
