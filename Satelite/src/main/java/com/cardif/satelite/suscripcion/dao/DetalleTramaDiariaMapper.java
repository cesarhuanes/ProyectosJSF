package com.cardif.satelite.suscripcion.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.satelite.DetalleTramaDiaria;
import com.cardif.satelite.moduloimpresion.bean.ConsultaArmarLote;
import com.cardif.satelite.moduloimpresion.bean.ConsultaConfirmacionImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaDocumentoCurier;
import com.cardif.satelite.reportes.bean.RepConsultaAPESEGBean;
import com.cardif.satelite.reportes.bean.RepFalabellaBean;
import com.cardif.satelite.reportes.bean.RepRegistroVentaBean;
import com.cardif.satelite.reportes.bean.RepSunatBean;
import com.cardif.satelite.reportes.bean.RepSunatCabeceraBean;
import com.cardif.satelite.suscripcion.bean.ConsultaPostVentaSOAT;
import com.cardif.satelite.suscripcion.bean.RepPolizasSOATBean;
import com.cardif.satelite.suscripcion.bean.ReportePostVentaSOAT;

public interface DetalleTramaDiariaMapper
{
	final String SELECT_POLIZAS_PARA_ARMAR_LOTE = "SELECT DTD.ID, DTD.NRO_POLIZA_PE, DTD.PLACA, DTD.FEC_VENTA, DTD.FEC_DESPACHO, DTD.COD_VEHICULO, DTD.USO_VEHICULO, " +
			"DTD.DEPARTAMENTO_DESP, DTD.PROVINCIA_DESP, DTD.DISTRITO_DESP, DTD.FRANJA_HORARIA_DESP, PR.ID as ID_PRODUCTO, PR.NOMBRE_PRODUCTO, DTD.ESTADO_IMPRESION " +
			"FROM DETALLE_TRAMA_DIARIA DTD LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO LEFT JOIN PRODUCTO PR ON TD.PRODUCTO = PR.ID " +			
			"WHERE (to_char(DTD.FEC_VENTA ,'YYYYMMDD' ) >= #{fecCompraDesde} AND to_char(DTD.FEC_VENTA ,'YYYYMMDD' ) <= #{fecCompraHasta} ) " +		
			"AND (DTD.USO_VEHICULO LIKE '%' || NVL(#{codUsoVehiculo,jdbcType=VARCHAR}, DTD.USO_VEHICULO) || '%') " +
			"AND (DTD.COD_DEPARTAMENTO_DESP LIKE '%' || NVL(#{codDepartamentoDesp,jdbcType=VARCHAR}, DTD.COD_DEPARTAMENTO_DESP) || '%') " +
			"AND (DTD.COD_PROVINCIA_DESP LIKE '%' || NVL(#{codProvinciaDesp,jdbcType=VARCHAR}, DTD.COD_PROVINCIA_DESP) || '%') " +
			"AND (DTD.COD_DISTRITO_DESP LIKE '%' || NVL(#{codDistritoDesp,jdbcType=VARCHAR}, DTD.COD_DISTRITO_DESP) || '%') " +
			"AND (DTD.FRANJA_HORARIA_DESP LIKE '%' || NVL(#{franjaHorariaDesp,jdbcType=VARCHAR}, DTD.FRANJA_HORARIA_DESP) || '%') " +
			"AND (to_char(DTD.FEC_DESPACHO,'YYYYMMDD') <= NVL(#{fecEntregaHasta},to_char(DTD.FEC_DESPACHO,'YYYYMMDD'))) "+
			"AND (PR.ID = NVL(#{codProducto,jdbcType=NUMERIC}, PR.ID)) " +
			"AND (DTD.PLACA LIKE '%' || NVL(#{numPlaca,jdbcType=VARCHAR}, DTD.PLACA) || '%') " +
			"AND (NVL(DTD.ESTADO_IMPRESION, ' ') <> '" + Constantes.MOD_IMPRESION_ESTADO_IMPRESO + "' AND NVL(DTD.ESTADO_IMPRESION, ' ') <> '" + Constantes.MOD_IMPRESION_ESTADO_EN_ESPERA + "' AND  NVL(DTD.ESTADO_IMPRESION, ' ') <> '" + Constantes.MOD_IMPRESION_ESTADO_REIMPRESO + "') " + 
			"AND DTD.ESTADO = '" + Constantes.POLIZA_ESTADO_VIGENTE + "' " +
			"AND UPPER(DTD.CANAL_VENTA) = 'DIGITAL' ORDER BY DTD.NRO_POLIZA_PE ASC";
	
	final String SELECT_POLIZAS_PARA_CONFIRMAR_IMPRESION = "SELECT DTD.ID, DTD.NRO_POLIZA_PE, DTD.PLACA, DTD.FEC_INI_VIG_POLIZA, DTD.FEC_FIN_VIG_POLIZA, DTD.NRO_CERTIFICADO, LI.NUM_LOTE, TD.PRODUCTO AS ID_PRODUCTO, DLI.IMPRESO_CORRECTAMENTE " +
			"FROM DETALLE_TRAMA_DIARIA DTD LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO " +
			"LEFT JOIN DETALLE_LOTE_IMPRESION DLI ON DTD.ID = DLI.DETALLE_TRAMA_DIARIA " +
			"LEFT JOIN LOTE_IMPRESION LI ON DLI.LOTE_IMPRESION = LI.NUM_LOTE " +
			"WHERE LI.NUM_LOTE = #{numLote,jdbcType=NUMERIC} ORDER BY DTD.NRO_POLIZA_PE";
	
	final String SELECT_POLIZAS_CURIER_BY_PKLOTE = "SELECT DTD.NRO_CERTIFICADO AS NRO_DOC_CLIENTE, (DTD.NOM_CONTRAT || ', ' || DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT) AS NOMBRE_DESTINATARIO_GUIA, " +
			"DTD.DIRECCION_DESP AS DIR_DESTINATARIO_GUIA, DTD.DISTRITO_DESP AS DIST_DESTINATARIO_GUIA, DTD.PROVINCIA_DESP AS CIUDAD_DESTINATARIO_GUIA, '1' AS PIEZAS, '' AS TIPO_ENVIO, '' AS VALOR_DECLARADO_GUIA, " +
			"DTD.NUM_DOC AS ID_DESTINATARIO_GUIA, DTD.TEL_FIJO AS TEL_DESTINATARIO_GUIA,       (DTD.DIR_REFERENCIA_DESP || ' - ' || TO_CHAR(DTD.FEC_DESPACHO, 'dd/mm/yyyy') || ' - ' || DTD.FRANJA_HORARIA_DESP) AS OBSERVA_GUIA, " +
			"PR.NOMBRE_PRODUCTO AS NOMBRE_REMITENTE_GUIA, DTD.NRO_POLIZA_PE AS POLIZA, DTD.PLACA, DTD.FEC_VENTA AS FECHA_VENTA " +
			"FROM DETALLE_TRAMA_DIARIA DTD LEFT JOIN DETALLE_LOTE_IMPRESION DLI ON DTD.ID = DLI.DETALLE_TRAMA_DIARIA " +
			"LEFT JOIN LOTE_IMPRESION LI ON DLI.LOTE_IMPRESION = LI.NUM_LOTE " +
			"LEFT JOIN PRODUCTO PR ON LI.PRODUCTO = PR.ID " + 
			"WHERE (LI.NUM_LOTE = #{pkLoteImpresion,jdbcType=NUMERIC}) ORDER BY LI.PRODUCTO, DTD.NRO_CERTIFICADO ASC";
	
	final String SELECT_POLIZAS_POST_VENTA_SOAT = "SELECT DTD.ID, DTD.ID_POLIZA, DTD.NRO_POLIZA_PE AS NRO_POLIZA, DTD.PLACA, DTD.NRO_CERTIFICADO, (DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ', ' || DTD.NOM_CONTRAT) AS CONTRATANTE, " +
			"DTD.NUM_DOC, DTD.FEC_VENTA, UPPER(CAN.NOMBRE_CANAL) AS CANAL, UPPER(NVL(SOC.NOMBRE_SOCIO, '') || ' - ' || NVL(PRO.NOMBRE_PRODUCTO, '')) AS SOCIO_PRODUCTO, DTD.ESTADO " +
			"FROM DETALLE_TRAMA_DIARIA DTD LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO LEFT JOIN PRODUCTO PRO ON TD.PRODUCTO = PRO.ID LEFT JOIN CANAL_PRODUCTO CAN ON PRO.ID_CANAL = CAN.ID LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID " +
			"WHERE (SOC.ID = NVL(#{codSocio,jdbcType=NUMERIC}, SOC.ID)) AND (CAN.ID = NVL(#{codCanal,jdbcType=NUMERIC}, CAN.ID)) AND (NVL(DTD.PLACA, ' ') LIKE '%' || NVL(#{numPlaca,jdbcType=VARCHAR}, DTD.PLACA)  || '%') " +
			"AND (NVL(DTD.NRO_CERTIFICADO, ' ') LIKE '%' || NVL(#{numCertificado,jdbcType=VARCHAR}, DTD.NRO_CERTIFICADO) || '%') AND (NVL(DTD.NUM_DOC, ' ') LIKE '%' || NVL(#{numDocumentoID,jdbcType=VARCHAR}, DTD.NUM_DOC) || '%') " +
			"AND (NVL((DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ' ' || DTD.NOM_CONTRAT), ' ') LIKE '%' || NVL(#{nomContratante,jdbcType=VARCHAR}, (DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ' ' || DTD.NOM_CONTRAT)) || '%') " +
			"AND (TRUNC(DTD.FEC_VENTA) <= #{fecVentaHasta}) AND (TRUNC(DTD.FEC_VENTA) >= #{fecVentaDesde}) " +
			"AND (NVL(DTD.ESTADO_IMPRESION, ' ') = '" + Constantes.MOD_IMPRESION_ESTADO_IMPRESO+ "' OR NVL(DTD.ESTADO_IMPRESION, ' ') = '" + Constantes.MOD_IMPRESION_ESTADO_REIMPRESO + "') " +
			"ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO, DTD.CANAL_VENTA, DTD.NRO_POLIZA_PE ASC";
	
	final String SELECT_CONSULTA_VENTA_SOAT = "SELECT DTD.ID, DTD.NRO_POLIZA_PE, DTD.MARCA , DTD.MODELO , DTD.CATEGORIA_CLASE ,DTD.IMPORTE_COBRO, DTD.NRO_POLIZA_PE AS NRO_POLIZA, DTD.PLACA, DTD.NRO_CERTIFICADO, (DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ', ' || DTD.NOM_CONTRAT) AS CONTRATANTE, " +
			"DTD.NUM_DOC, DTD.FEC_VENTA, UPPER(CAN.NOMBRE_CANAL) AS CANAL, UPPER(NVL(SOC.NOMBRE_SOCIO, '') || ' - ' || NVL(PRO.NOMBRE_PRODUCTO, '')) AS SOCIO_PRODUCTO, DTD.ESTADO " +
			"FROM DETALLE_TRAMA_DIARIA DTD LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO LEFT JOIN PRODUCTO PRO ON TD.PRODUCTO = PRO.ID LEFT JOIN CANAL_PRODUCTO CAN ON PRO.ID_CANAL = CAN.ID LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID " +
			"WHERE (SOC.ID = NVL(#{codSocio,jdbcType=NUMERIC}, SOC.ID)) AND (CAN.ID = NVL(#{codCanal,jdbcType=NUMERIC}, CAN.ID)) AND (NVL(DTD.PLACA, ' ') LIKE '%' || NVL(#{numPlaca,jdbcType=VARCHAR}, DTD.PLACA)  || '%') " +
			"AND (NVL(DTD.NRO_CERTIFICADO, ' ') LIKE '%' || NVL(#{numCertificado,jdbcType=VARCHAR}, DTD.NRO_CERTIFICADO) || '%') AND (NVL(DTD.NUM_DOC, ' ') LIKE '%' || NVL(#{numDocumentoID,jdbcType=VARCHAR}, DTD.NUM_DOC) || '%') " +
			"AND (NVL((DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ' ' || DTD.NOM_CONTRAT), ' ') LIKE '%' || NVL(#{nomContratante,jdbcType=VARCHAR}, (DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ' ' || DTD.NOM_CONTRAT)) || '%') " +
			"AND (TRUNC(DTD.FEC_VENTA) <= #{fecVentaHasta}) AND (TRUNC(DTD.FEC_VENTA) >= #{fecVentaDesde}) " +
			"AND (NVL(DTD.ESTADO_IMPRESION, ' ') = '" + Constantes.MOD_IMPRESION_ESTADO_IMPRESO+ "' OR NVL(DTD.ESTADO_IMPRESION, ' ') = '" + Constantes.MOD_IMPRESION_ESTADO_REIMPRESO + "') " +
			"ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO, DTD.CANAL_VENTA, DTD.NRO_POLIZA_PE ASC";
	
	final String SELECT_REPORTE_POST_VENTA_SOAT = "SELECT DTD.ID, DTD.NRO_POLIZA_PE, DTD.FEC_VENTA, DTD.HORA_VENTA, DTD.FEC_INI_VIG_POLIZA, DTD.FEC_FIN_VIG_POLIZA,	DTD.PLACA, NVL(DTD.SERIE, '-') AS SERIE, NVL(DTD.MOTOR, '-') AS MOTOR, NVL(DTD.COLOR, '-') AS COLOR, DTD.MARCA, DTD.MODELO, DTD.CATEGORIA_CLASE, DTD.USO_VEHICULO, " +
			"DTD.ANIO_FAB, DTD.NRO_ASIENTOS, DTD.NRO_CERTIFICADO, NVL(DTD.NRO_CERTIFICADO_ANT, '-') AS NRO_CERTIFICADO_ANT, DTD.TIPO_PERSONA, NVL((SELECT NOMBRE_VALOR FROM PARAMETRO_AUTOMAT WHERE COD_PARAM='" + Constantes.COD_PARAM_TIPO_DOCUMENTO_ID + "' AND COD_VALOR=DTD.TIP_DOC), '-') AS TIP_DOC, " +
			"DTD.NUM_DOC, UPPER((CASE WHEN DTD.APEL_PATE_CONTRAT <> ' ' THEN (DTD.APEL_PATE_CONTRAT || ' ') ELSE '' END) || (CASE WHEN DTD.APEL_MATE_CONTRAT <> ' ' THEN (DTD.APEL_MATE_CONTRAT || ', ') ELSE '' END) || DTD.NOM_CONTRAT) AS NOM_CONTRATANTE, " +
			"NVL((SELECT NOMBRE_VALOR FROM PARAMETRO_AUTOMAT WHERE COD_PARAM='" + Constantes.COD_PARAM_SEXO + "' AND DTD.SEXO LIKE '%' || COD_VALOR || '%'), '-') AS SEXO, NVL(TO_CHAR(DTD.FEC_NAC, 'dd/MM/yyyy'), '-') AS FEC_NAC, NVL(DTD.ESTADO_CIVIL, '-') AS ESTADO_CIVIL, " +
			"DTD.DEPARTAMENTO, DTD.PROVINCIA, DTD.DISTRITO, UPPER(DTD.DIRECCION) AS DIRECCION, NVL(DTD.TEL_FIJO, '-') AS TEL_FIJO, NVL(DTD.TEL_MOVIL, '-') AS TEL_MOVIL, NVL(DTD.EMAIL, '-') AS EMAIL, DTD.DEPARTAMENTO_DESP, DTD.PROVINCIA_DESP, DTD.DISTRITO_DESP, UPPER(DTD.DIRECCION_DESP) AS DIRECCION_DESP, UPPER(NVL(DTD.DIR_REFERENCIA_DESP, '-')) AS DIR_REFERENCIA_DESP, " +
			"UPPER(NVL(DTD.FRANJA_HORARIA_DESP, '-')) AS FRANJA_HORARIA_DESP, NVL(TO_CHAR(DTD.FEC_DESPACHO, 'dd/MM/yyyy'), '-') AS FEC_DESPACHO, NVL(DTD.MEDIO_PAGO, '-') AS MEDIO_PAGO, DTD.MONEDA, DTD.PRIMA_BRUTA, DTD.PRIMA_TECNICA, DTD.IMPORTE_COBRO, DTD.IMPORTE_COBRO_DSC, NVL(DTD.NUM_TARJ, '-') AS NUM_TARJ, NVL(TO_CHAR(DTD.FEC_VENCI_TARJ, 'dd/MM/yyyy'), '-') AS FEC_VENCI_TARJ, UPPER(NVL(DTD.TIPO_TARJ, '-')) AS TIPO_TARJ, " +
			"UPPER(NVL(DTD.COD_MASTER, '-')) AS COD_MASTER, UPPER(NVL(DTD.CANAL_VENTA, '-')) AS CANAL_VENTA, UPPER(NVL(DTD.PUNTO_VENTA, '-')) AS PUNTO_VENTA, UPPER(NVL(DTD.NOM_VEND, '-')) AS NOM_VEND, NVL(TO_CHAR(DTD.FEC_COBRO, 'dd/MM/yyyy'), '-') AS FEC_COBRO, UPPER(NVL(DTD.SUB_PROD, '-')) AS SUB_PROD, UPPER(NVL(DTD.PROPUESTA, '-')) AS PROPUESTA, UPPER(NVL(DTD.TRX, '-')) AS TRX, UPPER(NVL(DTD.VOUCHER, '-')) AS VOUCHER, " +
			"UPPER(NVL(DTD.COD_TIENDA, '-')) AS COD_TIENDA, UPPER(NVL(DTD.TARIF_DPTO, '-')) AS TARIF_DPTO, UPPER(NVL(DTD.PROP_ANTE, '-')) AS PROP_ANTE, UPPER(NVL(DTD.NU_AP, '-')) AS NU_AP, UPPER(NVL(DTD.CD_REF, '-')) AS CD_REF, UPPER(NVL(DTD.POS, '-')) AS POS, UPPER(NVL(DTD.LOTE_POS, '-')) AS LOTE_POS, UPPER(NVL(DTD.CD_RECT, '-')) AS CD_RECT, UPPER(NVL(DTD.EST_PROP, '-')) AS EST_PROP, " +
			"UPPER(NVL(DTD.DES_MEDPAG, '-')) AS DES_MEDPAG, UPPER(NVL(DTD.DONDE_PAGO, '-')) AS DONDE_PAGO, (CASE WHEN (SELECT COUNT(*) FROM DETALLE_REPORTE_APESEG WHERE ID_DETALLE_TRAMA_DIARIA=DTD.ID) > 0 THEN 'SI' ELSE 'NO' END) AS REPORTADO_APESEG, NVL(TO_CHAR((SELECT MAX(FECHA_REPORTADO) FROM DETALLE_REPORTE_APESEG WHERE ID_DETALLE_TRAMA_DIARIA=DTD.ID), 'dd/MM/yyyy hh:mm:ss'), '-') AS FEC_REPORTADO_APESEG, " +		
			"(CASE WHEN (SELECT COUNT(*) FROM DETALLE_REPORTE_SBS WHERE ID_DETALLE_TRAMA_MENSUAL=DTD.ID) > 0 THEN 'SI' ELSE 'NO' END) AS REPORTADO_SBS, NVL(TO_CHAR((SELECT MAX(FECHA_REPORTADO) FROM DETALLE_REPORTE_SBS WHERE ID_DETALLE_TRAMA_MENSUAL=DTD.ID), 'dd/MM/yyyy hh:mm:ss'), '-') AS FEC_REPORTADO_SBS, " +		
			"UPPER(SOC.NOMBRE_SOCIO) AS NOMBRE_SOCIO, UPPER(CAN.NOMBRE_CANAL) AS NOMBRE_CANAL, DTD.ESTADO, DTD.ESTADO_IMPRESION " +
			"FROM DETALLE_TRAMA_DIARIA DTD LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO LEFT JOIN PRODUCTO PRO ON TD.PRODUCTO = PRO.ID LEFT JOIN CANAL_PRODUCTO CAN ON PRO.ID_CANAL = CAN.ID LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID " +
			"WHERE (SOC.ID = NVL(#{codSocio,jdbcType=NUMERIC}, SOC.ID)) AND (CAN.ID = NVL(#{codCanal,jdbcType=NUMERIC}, CAN.ID)) AND (NVL(DTD.PLACA, ' ') LIKE '%' || NVL(#{numPlaca,jdbcType=VARCHAR}, DTD.PLACA)  || '%') " +
			"AND (NVL(DTD.NRO_CERTIFICADO, ' ') LIKE '%' || NVL(#{numCertificado,jdbcType=VARCHAR}, DTD.NRO_CERTIFICADO) || '%') AND (NVL(DTD.NUM_DOC, ' ') LIKE '%' || NVL(#{numDocumentoID,jdbcType=VARCHAR}, DTD.NUM_DOC) || '%') " +
			"AND (NVL((DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ' ' || DTD.NOM_CONTRAT), ' ') LIKE '%' || NVL(#{nomContratante,jdbcType=VARCHAR}, (DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT || ' ' || DTD.NOM_CONTRAT)) || '%') " +
			"AND (TRUNC(DTD.FEC_VENTA) <= #{fecVentaHasta}) AND (TRUNC(DTD.FEC_VENTA) >= #{fecVentaDesde}) " +
			"AND (NVL(DTD.ESTADO_IMPRESION, ' ') = '" + Constantes.MOD_IMPRESION_ESTADO_IMPRESO+ "' OR NVL(DTD.ESTADO_IMPRESION, ' ') = '" + Constantes.MOD_IMPRESION_ESTADO_REIMPRESO + "') " +
			"ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO, DTD.CANAL_VENTA, DTD.NRO_POLIZA_PE ASC";
	
	final String SELECT_POLIZAS_FOR_APESEG = "SELECT DTD.ID, DRA.ID AS ID_REPORTE_APESEG, (SELECT NOMBRE_VALOR FROM PARAMETRO_AUTOMAT WHERE COD_PARAM='" + Constantes.COD_PARAM_CODIGO_CIA_SEGUROS + "' AND COD_VALOR='" + Constantes.COD_PARAM_CODIGO_CIA_SEGUROS_VALOR + "') AS COD_CIA, " +
			"(CASE WHEN DTD.NRO_CERTIFICADO IS NOT NULL THEN (SUBSTR(DTD.NRO_CERTIFICADO, GREATEST(-12, -LENGTH(DTD.NRO_CERTIFICADO)), 6) || ' ' || SUBSTR(DTD.NRO_CERTIFICADO, GREATEST(-6, -LENGTH(DTD.NRO_CERTIFICADO)), 2) || ' ' || SUBSTR(DTD.NRO_CERTIFICADO, GREATEST(-4, -LENGTH(DTD.NRO_CERTIFICADO)), 4)) ELSE ' ' END) AS POL_CER, " +
			"DRA.TIPO_TRX AS TIP_TRA, DTD.FEC_FIN_VIG_POLIZA AS FCH_CE_F, DTD.FEC_INI_VIG_POLIZA AS FCH_CE_I, (CASE UPPER(DTD.TIPO_PERSONA) WHEN '" + Constantes.TIP_PERS_NATURAL + "' THEN '1' WHEN '" + Constantes.TIP_PERS_JURIDICA + "' THEN '2' ELSE '3' END) AS TIP_PER, " +
			"(CASE WHEN DTD.TIP_DOC='" + Constantes.TIP_DOC_RUC + "' THEN DTD.NOM_CONTRAT ELSE (DTD.NOM_CONTRAT || ' ' || DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT) END) AS NOM_CON, DTD.TIP_DOC, DTD.NUM_DOC, DTD.PLACA, DRA.USO, DRA.CLASE, '" + Constantes.PAIS_PLACA_PERU + "' AS PAIS, DRA.TIPO_ANULACION AS TIP_ANU, " +
			"DTD.FEC_VENTA AS FEC_PRO, DRA.FEC_ACT_ANUL AS FEC_ACT, DTD.COD_DISTRITO AS UBIGEO, DTD.MOTOR AS NRO_MOT, DTD.SERIE AS NRO_CHA, CAP.NOMBRE_CANAL AS NOM_CANAL, SOC.NOMBRE_SOCIO AS NOM_SOCIO " +
			"FROM DETALLE_TRAMA_DIARIA DTD " + 
			"LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO " +
			"LEFT JOIN PRODUCTO PRO ON TD.PRODUCTO = PRO.ID " +
			"LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID " +
			"LEFT JOIN CANAL_PRODUCTO CAP ON PRO.ID_CANAL = CAP.ID " +
			"LEFT JOIN DETALLE_REPORTE_APESEG DRA ON DTD.ID = DRA.ID_DETALLE_TRAMA_DIARIA " + 
			"WHERE (DRA.REPORTADO = '" + Constantes.APESEG_REPORTADO_NO + "') AND (TRUNC(DTD.FEC_VENTA) <= #{fecRegistroHasta}) AND (TRUNC(DTD.FEC_VENTA) >= #{fecRegistroDesde}) " +
			"ORDER BY DTD.PLACA, DTD.NRO_CERTIFICADO, DTD.FEC_VENTA, DRA.TIPO_TRX ASC";
	
	final String SELECT_POLIZAS_FALABELLA_ALTAS =
												"SELECT " +
												"DTD.ID, " +
												"DTD.PROPUESTA AS PROPUESTA, " +
												"DTD.FEC_DESPACHO AS FECHA, " +
												"'" + Constantes.FLB_MOTIVO_DESPACHO + "' AS MOTIVO, " + 
												"DTD.NRO_CERTIFICADO AS VALOR " +
												"FROM DETALLE_TRAMA_DIARIA DTD " +
												"LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO " + 
												"WHERE " +
												"(TRUNC(DTD.FEC_VENTA) <= #{fecVentaHasta}) AND " +
												"(TRUNC(DTD.FEC_VENTA) >= #{fecVentaDesde}) AND " +
												"TD.PRODUCTO = '"+ Constantes.COD_PRODUCTO_FLB_DIGITAL + "' AND " +
												"DTD.ESTADO = '" + Constantes.POLIZA_ESTADO_VIGENTE + "' AND " +
												"DTD.ESTADO_IMPRESION = '" + Constantes.MOD_IMPRESION_ESTADO_IMPRESO + "' " + 
												"ORDER BY DTD.ID ASC ";
	
	final String SELECT_POLIZAS_FALABELLA_BAJAS =
												"SELECT " +
												"DTD.ID, " +
												"DTD.PROPUESTA AS PROPUESTA, " +
												"DTD.FEC_DESPACHO AS FECHA, " + 
												"'"+ Constantes.FLB_MOTIVO_ANULACION+ "' AS MOTIVO " +
												"FROM DETALLE_TRAMA_DIARIA DTD " +
												"LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO " +
												"LEFT JOIN DETALLE_TRAMA_DIARIA_MOTIVO DTDM ON DTD.ID = DTDM.ID_DETALLE_TRAMA_DIARIA " +
												"WHERE TD.PRODUCTO = '"+ Constantes.COD_PRODUCTO_FLB_DIGITAL + "' AND " +
												"DTD.ESTADO = '" + Constantes.POLIZA_ESTADO_ANULADO + "' AND " +
												"DTD.ESTADO_IMPRESION = '" + Constantes.MOD_IMPRESION_ESTADO_IMPRESO + "' AND " +
												"(TRUNC(DTD.FEC_VENTA) <= #{fecVentaHasta}) AND " + 
												"(TRUNC(DTD.FEC_VENTA) >= #{fecVentaDesde}) " +
												"ORDER BY DTD.ID ASC ";
	
//	final String SELECT_REPORTE_SUNAT_CAB = "SELECT ('" + Constantes.RUC_CARDIF_SEGUROS + Constantes.REP_SUNAT_COD_AAA + "' || (SELECT EXTRACT(YEAR FROM TO_DATE(SYSDATE, 'DD-MM-YY')) FROM DUAL) || '" + Constantes.REP_SUNAT_PERIODO_ANUAL + "' || '.txt') AS NOMBRE_ARCHIVO, COUNT(*) AS NRO_REGISTROS, SYSDATE AS FECHA_PROCESO, '" + Constantes.REP_SUNAT_ESTADO_INICIAL + "' AS ESTADO_PROCESO " +
//			"FROM DETALLE_TRAMA_DIARIA DTD WHERE (EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anioPeriodo,jdbcType=NUMERIC}) GROUP BY 1, 3, 4";
	
	final String SELECT_REPORTE_SUNAT_CAB = "SELECT ('" + Constantes.RUC_CARDIF_SEGUROS + Constantes.REP_SUNAT_COD_AAA + "' || #{anioPeriodo,jdbcType=NUMERIC} || '" + Constantes.REP_SUNAT_PERIODO_ANUAL + "' || '.txt') AS NOMBRE_ARCHIVO, COUNT(*) AS NRO_REGISTROS, SYSDATE AS FECHA_PROCESO, '" + Constantes.REP_SUNAT_ESTADO_INICIAL + "' AS ESTADO_PROCESO " +
			"FROM DETALLE_TRAMA_DIARIA DTD WHERE (EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anioPeriodo,jdbcType=NUMERIC}) GROUP BY 1, 3, 4";
	
	final String SELECT_POLIZAS_REPORTE_SUNAT = "SELECT DTD.ID, '" + Constantes.RUC_CARDIF_SEGUROS + "' AS RUC_INFORMANTE, (SELECT NOMBRE_VALOR FROM PARAMETRO_AUTOMAT WHERE COD_PARAM='" + Constantes.COD_PARAM_DOC_IDENTIDAD_SUNAT + "' AND COD_VALOR=TIP_DOC) AS TIP_DOC_ID, DTD.NUM_DOC AS NUM_DOC_ID, " +
			"(CASE DTD.TIP_DOC WHEN '3' THEN '' ELSE UPPER(DTD.APEL_PATE_CONTRAT) END) AS APE_PATE_PROP, (CASE DTD.TIP_DOC WHEN '3' THEN '' ELSE UPPER(DTD.APEL_MATE_CONTRAT) END) AS APE_MATE_PROP, " +
			"(CASE DTD.TIP_DOC WHEN '3' THEN '' ELSE UPPER(DTD.NOM_CONTRAT) END) AS NOM_PROP, (CASE DTD.TIP_DOC WHEN '3' THEN UPPER(DTD.NOM_CONTRAT) ELSE '' END) AS RAZON_SOC_PROP, UPPER(DTD.MARCA) AS MARCA_VEHICULO, " +
			"DTD.ANIO_FAB, UPPER(DTD.COLOR) AS COLOR_VEHICULO, UPPER(DTD.PLACA) AS NRO_PLACA, UPPER(DTD.USO_VEHICULO) AS USO_VEHICULO, UPPER(DTD.CATEGORIA_CLASE) AS CLASE_VEHICULO, DTD.NRO_ASIENTOS, UPPER(DTD.MODELO) AS MODELO_VEHICULO, " +
			"UPPER(DTD.SERIE) AS NRO_SERIE, DTD.PROVINCIA, DTD.DEPARTAMENTO, UPPER(DTD.DIRECCION) AS DIRECCION, (CASE NVL(DTD.TEL_FIJO, 'NULL') WHEN 'NULL' THEN DTD.TEL_MOVIL ELSE DTD.TEL_FIJO END) AS TELEFONO, " + 
			"TO_CHAR(DTD.FEC_VENTA, 'MM/DD/YYYY') AS FECHA_POLIZA, DTD.NRO_POLIZA_PE AS NRO_POLIZA, DTD.PRIMA_TECNICA AS MONTO_PRIMA FROM DETALLE_TRAMA_DIARIA DTD " +
			"WHERE (EXTRACT(YEAR FROM TO_DATE(DTD.FEC_VENTA, 'DD-MM-YY')) = #{anioPeriodo,jdbcType=NUMERIC}) ORDER BY RUC_INFORMANTE, FECHA_POLIZA, NRO_POLIZA ASC";
	
	final String SELECT_POLIZAS_REGISTRO_VENTAS = "SELECT DTD.ID, ROWNUM AS CORRELATIVO_REGISTRO, DTD.FEC_CARGA AS FECHA_EMISION, NULL AS FECHA_VENCIMIENTO, (CASE DTD.ESTADO WHEN '" + Constantes.POLIZA_ESTADO_VIGENTE+ "' THEN '" + Constantes.REP_REGISTRO_VENTA_TIPO_ALTA + "' ELSE '" + Constantes.REP_REGISTRO_VENTA_TIPO_BAJA + "' END) AS TIPO_COMPROBANTE, " +
			"(CASE DTD.ESTADO WHEN '" +  Constantes.POLIZA_ESTADO_VIGENTE + "' THEN (SELECT PAR.NOMBRE_VALOR FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_PRODUCTO_SERIE +"' AND PAR.COD_VALOR LIKE '%' || PRO.NOMBRE_PRODUCTO || '%' || '-' || '%' || '" + Constantes.COD_PARAM_PRODUCTO_SERIE_ALTA + "' || '%') ELSE (SELECT PAR.NOMBRE_VALOR FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_PRODUCTO_SERIE +"' AND PAR.COD_VALOR LIKE '%' || PRO.NOMBRE_PRODUCTO || '%' || '-' || '%' || '" + Constantes.COD_PARAM_PRODUCTO_SERIE_BAJA + "' || '%') END) AS NRO_SERIE, " +
			"DTD.NRO_CORRELATIVO AS CORRELATIVO_SERIE, (CASE DTD.TIP_DOC WHEN '" + Constantes.TIP_DOC_DNI + "' THEN '" + Constantes.TIP_DOC_DNI_REG_VTA + "' WHEN '" + Constantes.TIP_DOC_CE + "' THEN '" + Constantes.TIP_DOC_CE_REG_VTA + "' WHEN '" + Constantes.TIP_DOC_RUC + "' THEN '" + Constantes.TIP_DOC_RUC_REG_VTA + "' END) AS TIPO_DOC_CLIENTE, DTD.NUM_DOC AS NRO_DOC_CLIENTE, " +
			"(CASE DTD.TIP_DOC WHEN '" + Constantes.TIP_DOC_RUC + "' THEN DTD.NOM_CONTRAT ELSE (DTD.NOM_CONTRAT || ' ' || DTD.APEL_PATE_CONTRAT || ' ' || DTD.APEL_MATE_CONTRAT) END) AS NOMBRE_RAZON_SOCIAL, " + Constantes.MONTO_DEFAULT + " AS VALOR_FACT_EXPORT, (DTD.IMPORTE_COBRO_DSC - (DTD.IMPORTE_COBRO_DSC * ((SELECT TO_NUMBER(PAR.NOMBRE_VALOR, '999.99') FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00))) AS BASE_IMPONIBLE, " +
			Constantes.MONTO_DEFAULT + " AS IMPORTE_EXONERADO, " + Constantes.MONTO_DEFAULT + " AS IMPORTE_INAFECTO, " + Constantes.MONTO_DEFAULT + " AS IMPUESTO_ISC, (DTD.IMPORTE_COBRO_DSC *  ((SELECT TO_NUMBER(PAR.NOMBRE_VALOR, '999.99') FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_IGV_IMPUESTO + "' AND COD_VALOR='" + Constantes.COD_PARAM_IGV_IMPUESTO_VALOR + "') / 100.00)) AS IMPUESTO_IGV, " + Constantes.MONTO_DEFAULT + " AS OTROS_IMPORTES, DTD.IMPORTE_COBRO_DSC AS IMPORTE_TOTAL, " + Constantes.TIPO_CAMBIO_REG_VTA + " AS TIPO_CAMBIO, " +
			"DTD.FEC_CARGA_REF AS FECHA_EMISION_REF, (CASE DTD.ESTADO WHEN '" + Constantes.POLIZA_ESTADO_ANULADO + "' THEN '" + Constantes.REP_REGISTRO_VENTA_TIPO_ALTA + "' ELSE NULL END) AS TIPO_COMPROBANTE_REF, " +
			"(CASE DTD.ESTADO WHEN '" +  Constantes.POLIZA_ESTADO_VIGENTE + "' THEN '' ELSE (SELECT PAR.NOMBRE_VALOR FROM PARAMETRO_AUTOMAT PAR WHERE PAR.COD_PARAM='" + Constantes.COD_PARAM_PRODUCTO_SERIE +"' AND PAR.COD_VALOR LIKE '%' || PRO.NOMBRE_PRODUCTO || '%' || '-' || '%' || '" + Constantes.COD_PARAM_PRODUCTO_SERIE_BAJA + "' || '%') END) AS SERIE_COMPROBANTE_REF, " +
			"DTD.NRO_CORRELATIVO_REF AS CORRELATIVO_SERIE_ORIG, NULL AS MODIFICABLE, NULL AS OPEN_ITEM, NULL AS OPEN_ITEM_REF, DTD.NRO_CERTIFICADO AS NRO_POLIZA, (SOC.NOMBRE_SOCIO || ' - ' || PRO.NOMBRE_PRODUCTO) AS PRODUCTO, '' AS ESTADO, DTD.PLACA AS OBSERVACION, " +
			"TD.FEC_CARGA, DTD.FEC_VENTA " +
			"FROM DETALLE_TRAMA_DIARIA DTD LEFT JOIN TRAMA_DIARIA TD ON DTD.SEC_ARCHIVO = TD.SEC_ARCHIVO LEFT JOIN PRODUCTO PRO ON TD.PRODUCTO = PRO.ID LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID " +
			"WHERE (TRUNC(DTD.FEC_CARGA) <= #{fecEmisionHasta}) AND (TRUNC(DTD.FEC_CARGA) >= #{fecEmisionDesde}) AND (PRO.ID = #{codProducto,jdbcType=NUMERIC}) " + 
			"ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO ASC";
	
	
	public DetalleTramaDiaria selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int insert(DetalleTramaDiaria record);
	
	public int updateByPrimaryKeySelective(DetalleTramaDiaria record);
	
	public int updateCertificadoAndEstadoImpresion(@Param("record") DetalleTramaDiaria record);
	
	public int updateAltaDeImpresion(@Param("record") DetalleTramaDiaria record);
	
	public int updateEstadoImpresion(@Param("record") DetalleTramaDiaria record);
	
	@Select(SELECT_POLIZAS_PARA_ARMAR_LOTE)
	@ResultMap("PolizasParaArmarLoteResultMap")
	public List<ConsultaArmarLote> selectPolizasParaArmarLote(@Param("codProducto") Long codProducto, @Param("numPlaca") String numPlaca, @Param("codUsoVehiculo") String codUsoVehiculo, 
			@Param("codDepartamentoDesp") String codDepartamentoDesp, @Param("codProvinciaDesp") String codProvinciaDesp, @Param("codDistritoDesp") String codDistritoDesp, 
			@Param("fecCompraDesde") String fecCompraDesde, @Param("fecCompraHasta") String fecCompraHasta, @Param("fecEntregaHasta") String fecEntregaHasta, @Param("franjaHorariaDesp") String franjaHorariaDesp);
	
	@Select(SELECT_POLIZAS_PARA_CONFIRMAR_IMPRESION)
  	@ResultMap("ConfirmarImpresionLoteResultMap")
	public List<ConsultaConfirmacionImpresion> selectPolizasParaConfirmarImpresion(@Param("numLote") Long numLote);
	
	@Select(SELECT_POLIZAS_CURIER_BY_PKLOTE)
  	@ResultMap("DocumentoCurierResultMap")
	public List<ConsultaDocumentoCurier> selectPolizasCurierByPkLote(@Param("pkLoteImpresion") Long pkLoteImpresion);
	
	@Select(SELECT_POLIZAS_POST_VENTA_SOAT)
	@ResultMap("ConsultaPostVentaSOATResultMap")
	public List<ConsultaPostVentaSOAT> selectPolizasPostVentaSOAT(@Param("codSocio") Long codSocio,	@Param("codCanal") Long codCanal, @Param("numPlaca") String numPlaca, @Param("numCertificado") String numCertificado, 
			@Param("numDocumentoID") String numDocumentoID, @Param("nomContratante") String nomContratante, @Param("fecVentaDesde") Date fecVentaDesde, @Param("fecVentaHasta") Date fecVentaHasta);
	
	@Select(SELECT_REPORTE_POST_VENTA_SOAT)
	@ResultMap("ReportePostVentaSOATResultMap")
	public List<ReportePostVentaSOAT> selectReportePostVentaSOAT(@Param("codSocio") Long codSocio,	@Param("codCanal") Long codCanal, @Param("numPlaca") String numPlaca, @Param("numCertificado") String numCertificado, 
			@Param("numDocumentoID") String numDocumentoID, @Param("nomContratante") String nomContratante, @Param("fecVentaDesde") Date fecVentaDesde, @Param("fecVentaHasta") Date fecVentaHasta);
	
	@Select(SELECT_POLIZAS_FOR_APESEG)
	@ResultMap("ReporteAPESEGResultMap")
	public List<RepConsultaAPESEGBean> selectPolizasForAPESEG(@Param("fecRegistroDesde") Date fecRegistroDesde, @Param("fecRegistroHasta") Date fecRegistroHasta);
	
	@Select(SELECT_POLIZAS_FALABELLA_ALTAS)
	@ResultMap("RepFalabellaResultMap")
	public List<RepFalabellaBean> selectPolizasFalabellaAltas(@Param("fecVentaDesde") Date fecVentaDesde, @Param("fecVentaHasta") Date fecVentaHasta);
	
	@Select(SELECT_POLIZAS_FALABELLA_BAJAS)
	@ResultMap("RepFalabellaResultMap")
	public List<RepFalabellaBean> selectPolizasFalabellaBajas(@Param("fecVentaDesde") Date fecVentaDesde, @Param("fecVentaHasta") Date fecVentaHasta);
	
	@Select(SELECT_REPORTE_SUNAT_CAB)
	@ResultMap("RepSunatCabResultMap")
	public List<RepSunatCabeceraBean> selectReporteSunatCabecera(@Param("anioPeriodo") Integer anioPeriodo);
	
	@Select(SELECT_POLIZAS_REPORTE_SUNAT)
	@ResultMap("RepSunatDetalleResultMap")
	public List<RepSunatBean> selectPolizasReporteSunat(@Param("anioPeriodo") Integer anioPeriodo);
	
	@Select(SELECT_POLIZAS_REGISTRO_VENTAS)
	@ResultMap("RepRegistroVentaResultMap")
	public List<RepRegistroVentaBean> selectPolizasRegistroVentas(@Param("codProducto") Long codProducto, @Param("fecEmisionDesde") Date fecEmisionDesde, @Param("fecEmisionHasta") Date fecEmisionHasta);

	@Select(SELECT_CONSULTA_VENTA_SOAT)
	@ResultMap("ConsultaVentaSOATResultMap")
	public List<RepPolizasSOATBean> selectConsultaVentaSOAT(@Param("codSocio") Long codSocio,	@Param("codCanal") Long codCanal, @Param("numPlaca") String numPlaca, @Param("numCertificado") String numCertificado, 
			@Param("numDocumentoID") String numDocumentoID, @Param("nomContratante") String nomContratante, @Param("fecVentaDesde") Date fecVentaDesde, @Param("fecVentaHasta") Date fecVentaHasta);
	
} //DetalleTramaDiariaMapper
