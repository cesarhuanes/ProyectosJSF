package com.cardif.satelite.reportes.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.reportes.DetalleReporteRegVenta;
import com.cardif.satelite.reportes.bean.RepRegistroVentaBean;

public interface DetalleReporteRegVentaMapper
{
	final String SELECT_POLIZAS_NUEVA_VENTA = "SELECT DRV.ID, ROWNUM AS CORRELATIVO_REGISTRO, DRV.FEC_EMISION AS FECHA_EMISION, DRV.TIPO_COMP AS TIPO_COMPROBANTE, " +
			"(SELECT PAR.NOMBRE_VALOR FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_PRODUCTO_SERIE + "' AND PAR.COD_VALOR LIKE '%' || PRO.NOMBRE_PRODUCTO || '%' || '-' || '%' || '" + Constantes.COD_PARAM_PRODUCTO_SERIE_ALTA+ "' || '%') AS NRO_SERIE, " +
			Constantes.NRO_CORRELATIVO_DEFAULT + " AS CORRELATIVO_SERIE, DRV.TIPO_DOCUMENTO AS TIPO_DOC_CLIENTE, DRV.NRO_DOCUMENTO AS NRO_DOC_CLIENTE, DRV.NOMBRE_RAZONSOCIAL AS NOMBRE_RAZON_SOCIAL, " +
			Constantes.MONTO_DEFAULT + " AS VALOR_FACT_EXPORT, (DRV.IMPORTE_TOTAL - (DRV.IMPORTE_TOTAL * ((SELECT TO_NUMBER(PAR.NOMBRE_VALOR, '999.99') FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00))) AS BASE_IMPONIBLE, " +
			Constantes.MONTO_DEFAULT + " AS IMPORTE_EXONERADO, " + Constantes.MONTO_DEFAULT + " AS IMPORTE_INAFECTO, " + Constantes.MONTO_DEFAULT + " AS IMPUESTO_ISC, " +
			"(DRV.IMPORTE_TOTAL *  ((SELECT TO_NUMBER(PAR.NOMBRE_VALOR, '999.99') FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00)) AS IMPUESTO_IGV, " + 
			Constantes.MONTO_DEFAULT + " AS OTROS_IMPORTES, DRV.IMPORTE_TOTAL AS IMPORTE_TOTAL, " + Constantes.TIPO_CAMBIO_REG_VTA + " AS TIPO_CAMBIO, NULL AS FECHA_EMISION_REF, NULL AS TIPO_COMPROBANTE_REF, " + 
			"NULL AS SERIE_COMPROBANTE_REF, NULL AS CORRELATIVO_SERIE_ORIG, NULL AS MODIFICABLE, NULL AS OPEN_ITEM, NULL AS OPEN_ITEM_REF, NULL AS NRO_POLIZA, (SOC.NOMBRE_SOCIO || '-' || PRO.NOMBRE_PRODUCTO) AS PRODUCTO, NULL AS ESTADO, NULL AS OBSERVACION " +
			"FROM DETALLE_REPORTE_REG_VENTA DRV LEFT JOIN PRODUCTO PRO ON DRV.ID_PRODUCTO=PRO.ID LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID " +
			"WHERE (TRUNC(DRV.FEC_EMISION) <= #{fecEmisionHasta}) AND (TRUNC(DRV.FEC_EMISION) >= #{fecEmisionDesde}) AND (PRO.ID = #{codProducto,jdbcType=NUMERIC}) " + 
			"ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO ASC";
		
	
	public DetalleReporteRegVenta selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int insertSelective(DetalleReporteRegVenta record);
	
	@Select(SELECT_POLIZAS_NUEVA_VENTA)
	@ResultMap("RepRegistroVentaResultMap")
	public List<RepRegistroVentaBean> selectPolizasNuevaVenta(@Param("codProducto") Long codProducto, @Param("fecEmisionDesde") Date fecEmisionDesde, @Param("fecEmisionHasta") Date fecEmisionHasta);
	
} //DetalleReporteRegVentaMapper
