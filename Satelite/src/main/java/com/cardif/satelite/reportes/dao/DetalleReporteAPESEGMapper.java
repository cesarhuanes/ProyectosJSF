package com.cardif.satelite.reportes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportes.DetalleReporteAPESEG;

public interface DetalleReporteAPESEGMapper
{
	final String SELECT_ROWLAST_BY_PK_DETALLE_TRAMA_DIARIA = "SELECT DRA.ID, DRA.ID_DETALLE_TRAMA_DIARIA, DRA.CLASE, DRA.USO, DRA.TIPO_TRX, DRA.TIPO_ANULACION, DRA.FEC_ACT_ANUL, DRA.REPORTADO, DRA.FECHA_REPORTADO, DRA.FEC_CREACION, DRA.USU_CREACION " +
			"FROM DETALLE_REPORTE_APESEG DRA LEFT JOIN DETALLE_TRAMA_DIARIA DTD ON DRA.ID_DETALLE_TRAMA_DIARIA = DTD.ID " +
			"WHERE (DTD.ID = NVL(#{pkDetalleTramaDiaria,jdbcType=NUMERIC}, DTD.ID)) ORDER BY DRA.FECHA_REPORTADO DESC";
	
	
	public DetalleReporteAPESEG selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int updateByPrimaryKeySelective(DetalleReporteAPESEG record);
	
	@Select(SELECT_ROWLAST_BY_PK_DETALLE_TRAMA_DIARIA)
	@ResultMap("BaseResultMap")
	public List<DetalleReporteAPESEG> selectRowLastByPkDetalleTramaDiaria(@Param("pkDetalleTramaDiaria") Long pkDetalleTramaDiaria);
	
} //DetalleReporteAPESEGMapper
