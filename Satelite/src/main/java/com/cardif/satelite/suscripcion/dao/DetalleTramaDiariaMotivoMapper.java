package com.cardif.satelite.suscripcion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.satelite.DetalleTramaDiariaMotivo;

public interface DetalleTramaDiariaMotivoMapper
{
	final String SELECT_ROWLAST_BY_PK_DET_TRAMA_DIARIA_AND_TIP_MOTIVO = "SELECT DTDM.ID, DTDM.MOTIVO_DSC, DTDM.OBSERVACION_DSC, DTDM.TIPO_MOTIVO, DTDM.ID_DETALLE_TRAMA_DIARIA, DTDM.FECHA_CREACION, DTDM.USU_CREACION " +
			"FROM DETALLE_TRAMA_DIARIA_MOTIVO DTDM LEFT JOIN DETALLE_TRAMA_DIARIA DTD ON DTDM.ID_DETALLE_TRAMA_DIARIA = DTD.ID " +
			"WHERE (DTD.ID = NVL(#{idDetalleTramaDiaria,jdbcType=NUMERIC}, DTD.ID)) AND (DTDM.TIPO_MOTIVO = NVL(#{tipoMotivo,jdbcType=NUMERIC}, DTDM.TIPO_MOTIVO)) " +
			"ORDER BY DTDM.FECHA_CREACION DESC";
	
	
	public DetalleTramaDiariaMotivo selectByPrimaryKey(Long id);
	
	public int insert(DetalleTramaDiariaMotivo record);
	
	public int insertSelective(DetalleTramaDiariaMotivo record);
	
	@Select(SELECT_ROWLAST_BY_PK_DET_TRAMA_DIARIA_AND_TIP_MOTIVO)
	@ResultMap("BaseResultMap")
	public List<DetalleTramaDiariaMotivo> selectRowLastByPkDetalleTramaDiariaAndTipoMotivo(@Param("idDetalleTramaDiaria") Long idDetalleTramaDiaria, @Param("tipoMotivo") Integer tipoMotivo);
	
} //DetalleTramaDiariaMapper
