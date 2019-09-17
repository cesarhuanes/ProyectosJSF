package com.cardif.satelite.suscripcion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.ControlPaquete;

public interface ControlPaqueteMapper {
	
	final String SELECT_CONTROL = "	SELECT CP.FECHA_INI_EJECUCION, CP.FECHA_FIN_EJECUCION, CP.FLAG_EJECUCION, CP.ESTADO, CP.USUARIO_CREACION, CP.FECHA_CREACION, CP.TOTAL_REGISTROS,"
								+ " CP.REGISTROS_CORRECTOS, CP.REGISTROS_OBSERVADOS,CP.PROCESO , TD.ESTADO_CONSOLIDADO as ESTADO_PROCESO, CP.MENSAJE FROM CONTROL_PAQUETE CP INNER JOIN TRAMA_DIARIA TD ON "
								+ " CP.ID_TRAMA = TD.SEC_ARCHIVO WHERE CP.ID = (SELECT MAX(ID)FROM CONTROL_PAQUETE)";
	
		
	final String SELECT_CONTROL_MASTER = "SELECT CP.FECHA_INI_EJECUCION, CP.FECHA_FIN_EJECUCION, CP.FLAG_EJECUCION, CP.ESTADO, CP.USUARIO_CREACION, CP.FECHA_CREACION, CP.TOTAL_REGISTROS,"
								+"CP.REGISTROS_CORRECTOS, CP.REGISTROS_OBSERVADOS,CP.PROCESO , TD.estado_master as ESTADO_PROCESO, CP.MENSAJE FROM CONTROL_PAQUETE CP INNER JOIN TRAMA_MASTER TD ON  "
								+"CP.ID_TRAMA = TD.id_master WHERE CP.ID = (SELECT MAX(ID)FROM CONTROL_PAQUETE)";
	
	/**
	 * Insertar Control de paquete 
	 * @param controlPaquete
	 */
	
	public void insert(ControlPaquete controlPaquete );
	
	/**
	 * Lista todo los paquetes registrados
	 * @return Lista total de paquetes
	 */
	
	public List<ControlPaquete> selectAll();
	
	/**
	 * Lista los paquete seleccionados por el id paquete
	 * @param id : codigo de paquete
	 * @return datos del paquete
	 */
	
	@Select(SELECT_CONTROL)
	@ResultMap(value ="BaseMapperControl")
	public ControlPaquete selectByEstadoProceso(@Param("id") Long id );
	
	
	@Select(SELECT_CONTROL_MASTER)
	@ResultMap(value ="BaseMapperControl")
	public ControlPaquete selectByEstadoProcesoMaster(@Param("id") Long id );
	
	/**
	 * Actualiza el paquete 
	 * @param controlPaquete 
	 */
	public void update(ControlPaquete controlPaquete);
	
}
