package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.ProcesoArchivo;
import com.cardif.satelite.model.reportesbs.ProcesoArchivoTransaccional;

public interface ProcesoArchivoMapper {
	public String SELECT_PROCESO_ARCHIVO = "SELECT "
			+ " PRO.COD_PROCESO_ARCHIVO	,"
			+ " PRO.NOM_ARCHIVO_PROCESADO	,"
			+ " PRO.NOM_ARCHIVO_ORIGINAL ,"
			+ " PRO.FEC_CREACION ,"
			+ " PRO.ESTADO,"
			+ " PAR.DESCRIPCION_VALOR,"
			+ " CASE WHEN "
			+ " PRO.FEC_PROCESO<=to_date('01-01-1990','dd-MM-yyyy') THEN "
			+ " ''"
			+ " ELSE"
			+ " to_char(PRO.FEC_PROCESO,'dd-MM-yyyy')"
			+ " END AS FEC_PROCESO,"
			+ " PRO.USU_PROCESO,"
			+ " PRO.NUM_REGISTROS,"
			+ " PRO.ESTADO_SOLICITUD "
			+ " FROM   PROCESO_ARCHIVO PRO INNER JOIN"
			+ " PARAMETRO PAR  ON PRO.ESTADO=PAR.COD_PARAMETRO  " 
			+ " WHERE PAR.COD_PARAMETRO IN('0901','0902','0903')"
			+ " AND PRO.COD_PROCESO_ARCHIVO=DECODE(#{codProceso},0,PRO.COD_PROCESO_ARCHIVO,#{codProceso}) "
			+ " AND PRO.ESTADO=DECODE(#{codEstado},'T',PRO.ESTADO,#{codEstado})"
			+ " AND PRO.ESTADO IN('0901','0902','0903')"
			+ " AND UPPER(PRO.NOM_ARCHIVO_ORIGINAL) LIKE CONCAT('%',CONCAT(DECODE(#{nomArchivo},'T',UPPER(PRO.NOM_ARCHIVO_ORIGINAL), #{nomArchivo} ),'%'))"
			+ " AND NVL(UPPER(PRO.USU_PROCESO),'-') LIKE CONCAT('%',CONCAT(DECODE(#{usuarioProceso},'T',NVL(UPPER(PRO.USU_PROCESO),'-'), #{usuarioProceso} ),'%'))"
			+ " AND PRO.FEC_CREACION BETWEEN to_date(#{fechaCargaDesde}||' 00:00:00','yyyy-MM-dd HH24:MI:SS ') AND to_date(#{fechaCargaHasta}||' 23:59:59','yyyy-MM-dd HH24:MI:SS')"
			+ " AND PRO.FEC_PROCESO BETWEEN to_date(#{fechaProcesoDesde}||' 00:00:00','yyyy-MM-dd HH24:MI:SS') AND to_date(#{fechaProcesoHasta}||' 23:59:59','yyyy-MM-dd HH24:MI:SS')"
			+ " ORDER BY PRO.COD_PROCESO_ARCHIVO DESC";

	@Select(SELECT_PROCESO_ARCHIVO)
	@ResultMap(value = "BaseResultMapArchivo")
	List<ProcesoArchivo> getListaProcesoArchivo(
			@Param("codProceso") Long codProceso,
			@Param("codEstado") String codEstado,
			@Param("nomArchivo") String nomArchivo,
			@Param("usuarioProceso") String usuarioProceso,
			@Param("fechaCargaDesde") String fechaCargaDesde,
			@Param("fechaCargaHasta") String fechaCargaHasta,
			@Param("fechaProcesoDesde") String fechaProcesoDesde,
			@Param("fechaProcesoHasta") String fechaProcesoHasta);

	ProcesoArchivoTransaccional selectByPrimaryKey(Long pk);

	int updateByPrimaryKey(ProcesoArchivoTransaccional record);

}
