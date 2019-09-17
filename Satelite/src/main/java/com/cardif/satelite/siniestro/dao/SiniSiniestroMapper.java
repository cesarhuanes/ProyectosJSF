package com.cardif.satelite.siniestro.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cardif.satelite.model.SiniSiniestro;
import com.cardif.satelite.model.SiniSiniestroExample;
import com.cardif.satelite.siniestro.bean.ConsultaSiniestro;
import com.cardif.satelite.siniestro.bean.ReporteSiniestro;

public interface SiniSiniestroMapper {
  // @Select(value =
  // "{CALL CONSULTASINIESTRO(#{SOCIO, mode=IN, jdbcType=VARCHAR},#{PRODUCTO, mode=IN, jdbcType=VARCHAR},#{NROSINI, mode=IN, jdbcType=VARCHAR},
  // #{NROPZ, mode=IN, jdbcType=VARCHAR},#{TIPDOC, mode=IN, jdbcType=VARCHAR},#{NRODOC, mode=IN, jdbcType=VARCHAR},#{COBEAFEC, mode=IN,
  // jdbcType=VARCHAR},#{NOMBRE, mode=IN, jdbcType=VARCHAR},#{APE_PA, mode=IN, jdbcType=VARCHAR},#{APE_MA, mode=IN, jdbcType=VARCHAR},#{EST_C_P,
  // mode=IN, jdbcType=VARCHAR}, #{ESTLEGCP, mode=IN, jdbcType=VARCHAR},#{TIPCAF, mode=IN, jdbcType=VARCHAR},#{EJE_CAFAE, mode=IN,
  // jdbcType=VARCHAR},#{FEC_ULT_NOTI, mode=IN, jdbcType=VARCHAR},#{FE_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_HASTA, mode=IN,
  // jdbcType=VARCHAR},#{TIPO_SEGURO, mode=IN, jdbcType=VARCHAR})}")
  @Select(
      value = "{CALL CONSULTASINIESTRO(#{SOCIO, mode=IN, jdbcType=VARCHAR},#{PRODUCTO, mode=IN, jdbcType=VARCHAR},#{NROSINI, mode=IN, jdbcType=VARCHAR},#{NROPZ, mode=IN, jdbcType=VARCHAR},#{TIPDOC, mode=IN, jdbcType=VARCHAR},#{NRODOC, mode=IN, jdbcType=VARCHAR},#{COBEAFEC, mode=IN, jdbcType=VARCHAR},#{NOMBRE, mode=IN, jdbcType=VARCHAR},#{APE_PA, mode=IN, jdbcType=VARCHAR},#{APE_MA, mode=IN, jdbcType=VARCHAR},#{EST_C_P, mode=IN, jdbcType=VARCHAR},#{ESTLEGCP, mode=IN, jdbcType=VARCHAR},"
          + "#{FEC_ULT_NOTI, mode=IN, jdbcType=VARCHAR},#{FEC_ULT_NOTI_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_ULT_NOTI_HASTA, mode=IN, jdbcType=VARCHAR},#{FEC_APROB_RECH_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_APROB_RECH_HASTA, mode=IN, jdbcType=VARCHAR},#{FEC_ENTREGA_OPC_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_ENTREGA_OPC_HASTA, mode=IN, jdbcType=VARCHAR},#{PAGO_DESDE, mode=IN, jdbcType=NUMERIC},#{PAGO_HASTA, mode=IN, jdbcType=NUMERIC},#{NRO_PLANILLA, mode=IN, jdbcType=VARCHAR},#{EJE_CAFAE, mode=IN, jdbcType=VARCHAR},#{FE_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_HASTA, mode=IN, jdbcType=VARCHAR},#{TIPO_SEGURO, mode=IN, jdbcType=VARCHAR})}")
  @Options(statementType = StatementType.CALLABLE)
  @ResultMap(value = "ConsultaSiniResultMap")
  List<ConsultaSiniestro> buscar(@Param("NROSINI") String nroSiniestro, @Param("SOCIO") String codSocio, @Param("PRODUCTO") String nroProducto, @Param("NROPZ") String nroPoliza,
      @Param("TIPDOC") String tipDocum, @Param("NRODOC") String nroDocumento, @Param("COBEAFEC") String cobAfectada, @Param("NOMBRE") String nombres, @Param("APE_PA") String apePaterno,
      @Param("APE_MA") String apeMaterno, @Param("EST_C_P") String estado, @Param("ESTLEGCP") String estadoLegajo, @Param("FEC_ULT_NOTI") String fecUltDocum,
      @Param("FEC_ULT_NOTI_DESDE") String fecUltDocumDesde, @Param("FEC_ULT_NOTI_HASTA") String fecUltDocumHasta, @Param("FEC_APROB_RECH_DESDE") String fecAprobRechDesde,
      @Param("FEC_APROB_RECH_HASTA") String fecAprobRechHasta, @Param("FEC_ENTREGA_OPC_DESDE") String fecEntrgaOpcDesde, @Param("FEC_ENTREGA_OPC_HASTA") String fecEntrgaOpcHasta,
      @Param("PAGO_DESDE") String pagoDesde, @Param("PAGO_HASTA") String pagoHasta, @Param("NRO_PLANILLA") String nroPlanilla, @Param("EJE_CAFAE") String ejeCafae,
      @Param("FE_DESDE") String fecNotifDesde, @Param("FEC_HASTA") String fecNotifHasta, @Param("TIPO_SEGURO") String tip_seguro);

  // CONSULTA REPORTE
  @Select(
      value = "{CALL SP_CONSULTAR_REPORTE_SINI(#{SOCIO, mode=IN, jdbcType=VARCHAR},#{PRODUCTO, mode=IN, jdbcType=VARCHAR},#{NROSINI, mode=IN, jdbcType=VARCHAR},#{NROPZ, mode=IN, jdbcType=VARCHAR},#{TIPDOC, mode=IN, jdbcType=VARCHAR},#{NRODOC, mode=IN, jdbcType=VARCHAR},#{COBEAFEC, mode=IN, jdbcType=VARCHAR},#{NOMBRE, mode=IN, jdbcType=VARCHAR},#{APE_PA, mode=IN, jdbcType=VARCHAR},#{APE_MA, mode=IN, jdbcType=VARCHAR},#{EST_C_P, mode=IN, jdbcType=VARCHAR},#{ESTLEGCP, mode=IN, jdbcType=VARCHAR},"
          + "#{FEC_ULT_NOTI, mode=IN, jdbcType=VARCHAR},#{FEC_ULT_NOTI_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_ULT_NOTI_HASTA, mode=IN, jdbcType=VARCHAR},#{FEC_APROB_RECH_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_APROB_RECH_HASTA, mode=IN, jdbcType=VARCHAR},#{FEC_ENTREGA_OPC_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_ENTREGA_OPC_HASTA, mode=IN, jdbcType=VARCHAR},#{PAGO_DESDE, mode=IN, jdbcType=NUMERIC},#{PAGO_HASTA, mode=IN, jdbcType=NUMERIC},#{NRO_PLANILLA, mode=IN, jdbcType=VARCHAR},#{EJE_CAFAE, mode=IN, jdbcType=VARCHAR},#{FE_DESDE, mode=IN, jdbcType=VARCHAR},#{FEC_HASTA, mode=IN, jdbcType=VARCHAR},#{TIPO_SEGURO, mode=IN, jdbcType=VARCHAR})}")
  @Options(statementType = StatementType.CALLABLE)
  @ResultMap(value = "ConsultaReporteSiniResultMap")
  List<ReporteSiniestro> buscarReporte(@Param("NROSINI") String nroSiniestro, @Param("SOCIO") String codSocio, @Param("PRODUCTO") String nroProducto, @Param("NROPZ") String nroPoliza,
      @Param("TIPDOC") String tipDocum, @Param("NRODOC") String nroDocumento, @Param("COBEAFEC") String cobAfectada, @Param("NOMBRE") String nombres, @Param("APE_PA") String apePaterno,
      @Param("APE_MA") String apeMaterno, @Param("EST_C_P") String estado, @Param("ESTLEGCP") String estadoLegajo, @Param("FEC_ULT_NOTI") String fecUltDocum,
      @Param("FEC_ULT_NOTI_DESDE") String fecUltDocumDesde, @Param("FEC_ULT_NOTI_HASTA") String fecUltDocumHasta, @Param("FEC_APROB_RECH_DESDE") String fecAprobRechDesde,
      @Param("FEC_APROB_RECH_HASTA") String fecAprobRechHasta, @Param("FEC_ENTREGA_OPC_DESDE") String fecEntrgaOpcDesde, @Param("FEC_ENTREGA_OPC_HASTA") String fecEntrgaOpcHasta,
      @Param("PAGO_DESDE") String pagoDesde, @Param("PAGO_HASTA") String pagoHasta, @Param("NRO_PLANILLA") String nroPlanilla, @Param("EJE_CAFAE") String ejeCafae,
      @Param("FE_DESDE") String fecNotifDesde, @Param("FEC_HASTA") String fecNotifHasta, @Param("TIPO_SEGURO") String tip_seguro);

  // fin-reporte

  @Select("SELECT SI.NRO_SINIESTRO ,SI.FEC_OCURRENCIA,CONVERT(VARCHAR(10),YEAR(FEC_OCURRENCIA))+'.'+CONVERT(VARCHAR(10),MONTH(FEC_OCURRENCIA)) AS MES_OCURRENCIA,SI.FEC_NOTIFICACION,CONVERT(VARCHAR(10),YEAR(FEC_NOTIFICACION))+'.'+CONVERT(VARCHAR(10),MONTH(FEC_NOTIFICACION)) AS MES_NOTIFICACION,P5.NOM_VALOR AS COD_COBERTURA,(SELECT NOMBRE_DEPARTAMENTO FROM GEN_DEPARTAMENTO WHERE COD_DEPARTAMENTO = SI.COD_UBICACION) AS COD_UBICACION,P7.NOM_VALOR AS COD_ESTADO,P8.NOM_VALOR AS COD_EST_LEGAJO,P10.NOM_VALOR AS COD_MOT_RECHAZO_AGR,P9.NOM_VALOR AS COD_MOT_RECHAZO,P11.NOM_VALOR AS COD_RESUMEN,SI.CIE_10,SI.DIAGNOSTICO,SI.NRO_CARTA,SI.FEC_APROB_RECHAZO,CONVERT(VARCHAR(10),YEAR(FEC_APROB_RECHAZO))+'.'+CONVERT(VARCHAR(10),MONTH(FEC_APROB_RECHAZO)) AS MES_APROB_RECHAZO,SI.FEC_RECEP_SOCIO,SI.FEC_ULT_DOCUM,SI.CAN_DIAS_LIQUI_RECHA, SI.CAN_DIAS_PENDIENTES,SI.VAL_EDAD_INGRESO,SI.VAL_EDAD_PERMANENCIA, SI.VAL_CARENCIA, SI.CAN_SINIESTRO, SI.OBSERVACIONES,CASE SI.FLAG_CARGO_CARTA WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_CARGO_CARTA,SI.FEC_ENTREGA,P16.NOM_VALOR AS COD_MOT_NO_ENT,SI.FEC_SOLIC_NUEVA_DIREC,SI.FEC_REENVIO,SI.NRO_ENVIOS,SI.NRO_CAJA,SI.TEL_REFERENCIA,SI.CONTACTO,SI.CORREO,SI.DIR_DOMICILIO,SI.COD_DEPARTAMENTO,SI.COD_PROVINCIA,SI.COD_DISTRITO,P13.NOM_VALOR AS COD_TIP_SEGURO,SI.RANGOS,SI.LEY_RESERVA_PENDIENTE,SI.LEY_RESERVA_PENDIENTE_1,SI.NRO_DOCUM_ASEGURADO,SI.TIP_DOCUM_ASEGURADO,SI.NACIONALIDAD,P4.NOM_VALOR AS COD_GENERO,SI.NOM_ASEGURADO as NOM_ASEGURADO,SI.APE_PAT_ASEGURADO,SI.APE_MAT_ASEGURADO,SI.APE_PAT_ASEGURADO+' '+SI.APE_MAT_ASEGURADO+', '+SI.NOM_ASEGURADO AS NOMBRE_COMPLETO,SI.FEC_NACIMIENTO,SI.EDA_FEC_OCURRENCIA,P12.NOM_VALOR AS COD_PAR_ASEGURADO,CASE WHEN SI.COD_ESTADO ='8' OR SI.COD_ESTADO ='9' OR SI.COD_ESTADO ='10' THEN '0' ELSE SI.IMP_PAGOS END AS IMP_PAGOS,SI.IMP_RESERVA,CASE WHEN SI.COD_ESTADO ='8' OR SI.COD_ESTADO ='9' OR SI.COD_ESTADO ='10' THEN SI.IMP_PAGOS ELSE '0' END AS RESERVA_INICIAL,P6.NOM_VALOR AS COD_MONEDA,SI.NRO_PLANILLA,SI.FEC_ENTREGA_OP_CONT,SI.FEC_EMI_CHEQUE,SI.FEC_ENT_CHEQUE,SI.USU_CREACION,SI.FEC_CREACION,SI.USU_MODIFICACION,SI.FEC_MODIFICACION, SI.FEC_ULT_DOCUM_SOCIO,CASE WHEN SI.FEC_RECEP_SOCIO IS NULL THEN SI.FEC_NOTIFICACION ELSE SI.FEC_RECEP_SOCIO END AS FEC_NOTIFICACION_MENOR,CONVERT(VARCHAR(10),YEAR(SI.FEC_RECEP_SOCIO))+'.'+CONVERT(VARCHAR(10),MONTH(SI.FEC_RECEP_SOCIO)) AS MES_NOTIFICACION_SOCIO,CASE WHEN FEC_RECEP_SOCIO IS NOT NULL AND FEC_NOTIFICACION IS NOT NULL THEN DATEDIFF(day, FEC_RECEP_SOCIO, FEC_NOTIFICACION) ELSE 0 END DIAS_NOTIFICACION,CASE WHEN FEC_ULT_DOCUM IS NOT NULL AND FEC_ULT_DOCUM_SOCIO IS NOT NULL THEN DATEDIFF(day, FEC_ULT_DOCUM_SOCIO, FEC_ULT_DOCUM) ELSE 0 END DIAS_ULT_DOCUMENTACION,P15.NOM_VALOR AS EJE_CAFAE,DC.TIP_REF_CAFAE,DC.REF_CAFAE,DC.TIP_DOC_BENEFICIARIO,DC.NRO_DOC_BENEFICIARIO,DC.NOM_BENEFICIARIO,P17.NOM_VALOR AS PARENTESCO_BENEF,DC.FONDO_CAFAE,CASE DC.FLAG_MU_NA WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_MU_NA,CASE DC.FLAG_MU_AC WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_MU_AC,CASE DC.FLAG_IHP WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_IHP,CASE DC.FLAG_SA_DE WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_SA_DE,CASE DC.FLAG_UT_ES WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_UT_ES,CASE DC.FLAG_RE_RE WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_RE_RE,CASE DC.FLAG_TR_RE WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_TR_RE,CASE DC.FLAG_IN_TO_PE WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_IN_TO_PE,CASE DC.FLAG_DC WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_DC,CASE DC.FLAG_DS WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_DS,SR.COD_SUC_RETIRO,SR.IMP_PRE_LIQUID,SR.IMP_GAS_MEDICOS,CASE SR.FLAG_CELULAR WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_CELULAR,CASE SR.FLAG_CARTERA WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_CARTERA,CASE SR.FLAG_MALETIN WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_MALETIN,CASE SR.FLAG_BILLETERA WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_BILLETERA,CASE SR.FLAG_PORTADOCUMENTOS WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_PORTADOCUMENTOS,CASE SR.FLAG_LENTES_OPTICOS WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_LENTES_OPTICOS,CASE SR.FLAG_LENTES_DE_SOL WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_LENTES_DE_SOL,CASE SR.FLAG_COSMETICOS WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_COSMETICOS,CASE SR.FLAG_LAPICERO WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_LAPICERO,CASE SR.FLAG_DNI WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_DNI,CASE SR.FLAG_MOCHILA WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_MOCHILA,CASE SR.FLAG_RELOJ WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_RELOJ ,CASE SR.FLAG_DISC_IPOD_MP3 WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_DISC_IPOD_MP3,CASE SR.FLAG_PALM_TABLED WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_PALM_TABLED,CASE SR.FLAG_BOLSO WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_BOLSO,CASE SR.FLAG_SILLA_AUTO_BB WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_SILLA_AUTO_BB,CASE SR.FLAG_COCHE_BB WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_COCHE_BB,CASE SR.FLAG_DISCO WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_DISCO,CASE SR.FLAG_LLANTA WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_LLANTA,CASE SR.FLAG_MUERTE_ACCID WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_MUERTE_ACCID,CASE SR.FLAG_LLAVES WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_LLAVES,CASE SR.FLAG_LIBRE_DISP WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_LIBRE_DISP,CASE SR.FLAG_GASTO_MEDICO WHEN 1 THEN 'SI' ELSE 'NO' END AS FLAG_GASTO_MEDICO,SDP.IMP_MAX_CUOTA,SDP.NRO_CUOTA,RH.CAN_DIAS_CN,RH.CAN_DIAS_UCI,RH.IMP_CALC_LIQUID,RH.CAN_DIAS_HOSPITAL,RH.EXCEPCION,SDS.NRO_TARJETA,SDS.TIP_TARJETA,P14.NOM_VALOR AS COD_SOCIO,PO.NRO_PRODUCTO,PO.PLAN_POLIZA,PO.NRO_POLIZA,PO.NRO_CERTIFICADO,FEC_INI_VIGENCIA,FEC_FIN_VIGENCIA,PR.NOM_PRODUCTO,PR.COD_RAMO,P3.NOM_VALOR AS NOM_RAMO,PR.COD_PRODUCTO_ACSELE ,PR.COD_PRODUCTO_SINI, DEP.NOMBRE_DEPARTAMENTO, PRO.NOMBRE_PROVINCIA, DIS.NOMBRE_DISTRITO ,PA.NOMBRE_PAIS , TTI.DESCRIPCION AS NOMB_TIP_TRAB_INDEP, TRC.NOM_VALOR AS NOM_TIP_REF_CAFAE ,CASE SDS.AUTOSEGURO WHEN   1 THEN 'SI' WHEN   0 THEN 'NO' ELSE 'NO' END AS AUTOSEGURO FROM SINI_SINIESTRO SI LEFT JOIN GEN_DEPARTAMENTO DEP ON SI.COD_DEPARTAMENTO = DEP.COD_DEPARTAMENTO LEFT JOIN GEN_PROVINCIA PRO ON SI.COD_PROVINCIA = PRO.COD_PROVINCIA LEFT JOIN GEN_DISTRITO DIS ON SI.COD_DISTRITO = DIS.COD_DISTRITO LEFT JOIN SINI_POLIZA PO ON SI.NRO_SINIESTRO = PO.NRO_SINIESTRO LEFT JOIN PARAMETRO P1 ON P1.COD_VALOR = PO.COD_SOCIO AND P1.COD_PARAM = '026' AND P1.TIP_PARAM = 'D' LEFT JOIN SINI_PRODUCTO PR ON PO.NRO_PRODUCTO = PR.NRO_PRODUCTO LEFT JOIN PARAMETRO P2 ON P2.COD_VALOR = SI.TIP_DOCUM_ASEGURADO AND P2.COD_PARAM = '027' AND P2.TIP_PARAM = 'D' JOIN SINI_DATOS_CAFAE DC ON SI.NRO_SINIESTRO = DC.NRO_SINIESTRO JOIN SINI_ROBO SR  ON SI.NRO_SINIESTRO = SR.NRO_SINIESTRO JOIN SINI_DESEMPLEO SDP  ON SI.NRO_SINIESTRO = SDP.NRO_SINIESTRO JOIN SINI_RENTA_HOSP RH  ON SI.NRO_SINIESTRO = RH.NRO_SINIESTRO JOIN SINI_DESGRAVAMEN SDS  ON SI.NRO_SINIESTRO = SDS.NRO_SINIESTRO LEFT JOIN PARAMETRO P3 ON P3.COD_VALOR = PR.COD_RAMO AND P3.COD_PARAM = '017' AND P3.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P4 ON P4.COD_VALOR = SI.COD_GENERO AND P4.COD_PARAM = '029' AND P4.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P5 ON P5.COD_VALOR = SI.COD_COBERTURA AND P5.COD_PARAM = '018' AND P5.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P6 ON P6.COD_VALOR = SI.COD_MONEDA AND P6.COD_PARAM = '028' AND P6.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P7 ON P7.COD_VALOR = SI.COD_ESTADO AND P7.COD_PARAM = '019' AND P7.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P8 ON P8.COD_VALOR = SI.COD_EST_LEGAJO AND P8.COD_PARAM = '021' AND P8.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P9 ON P9.COD_VALOR = SI.COD_MOT_RECHAZO AND P9.COD_PARAM = '022' AND P9.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P10 ON P10.COD_VALOR = SI.COD_MOT_RECHAZO_AGR AND P10.COD_PARAM = '020' AND P10.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P11 ON P11.COD_VALOR = SI.COD_RESUMEN AND P11.COD_PARAM = '023' AND P11.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P12 ON P12.COD_VALOR = SI.COD_PAR_ASEGURADO AND P12.COD_PARAM = '030' AND P12.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P13 ON P13.COD_VALOR = SI.COD_TIP_SEGURO AND P13.COD_PARAM = '024' AND P13.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P14 ON P14.COD_VALOR = PO.COD_SOCIO AND P14.COD_PARAM = '026' AND P14.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P15 ON P15.COD_VALOR = DC.EJE_CAFAE AND P15.COD_PARAM = '016' AND P15.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P16 ON P16.COD_VALOR = SI.COD_MOT_NO_ENT AND P16.COD_PARAM = '031' AND P16.TIP_PARAM = 'D' LEFT JOIN PARAMETRO P17 ON P17.COD_VALOR = DC.PARENTESCO_BENEF AND P17.COD_PARAM = '030' AND P17.TIP_PARAM = 'D' LEFT JOIN GEN_PAIS PA			ON SI.COD_PAIS = PA.COD_PAIS LEFT JOIN TIPO_TRABAJADOR TTI  ON TTI.COD_TIPO_TRABAJADOR = SDP.COD_TIPO_TRABAJADOR LEFT JOIN PARAMETRO TRC ON TRC.COD_PARAM = '035' AND TRC.TIP_PARAM = 'D'  AND  TRC.COD_VALOR = DC.TIP_REF_CAFAE  WHERE SI.NRO_SINIESTRO = #{id,jdbcType=VARCHAR}")
  @ResultMap(value = "ConsultaReporteSiniResultMap")
  ReporteSiniestro getReporteSiniestro(@Param("id") String id);

  final String LISTA_SINIDATOS_CAFAE = "SELECT * FROM dbo.SINI_SINIESTRO" + " WHERE NRO_SINIESTRO=#{nroSiniestro} ";

  @Select(LISTA_SINIDATOS_CAFAE)
  @ResultMap(value = "BaseResultMap")
  SiniSiniestro listar(@Param("nroSiniestro") String nroSiniestro);

  final String LISTA_SINIDATOS_CAFAE_TODOS = "SELECT * FROM dbo.SINI_SINIESTRO";

  @Select(LISTA_SINIDATOS_CAFAE_TODOS)
  @ResultMap(value = "BaseResultMap")
  List<SiniSiniestro> listarT();

  /*
   * final String SQL_MAX_NRO_PLANILLA = "SELECT MAX(CONVERT(int,SUBSTRING(NRO_PLANILLA, 8, 4))) FROM SINI_SINIESTRO S " +
   * " INNER JOIN SINI_POLIZA P ON S.NRO_SINIESTRO = P.NRO_SINIESTRO " +
   * " WHERE P.COD_SOCIO = #{codSocio} AND SUBSTRING(NRO_PLANILLA, 3,4) = YEAR(GETDATE())" ;
   */

  final String SQL_MAX_NRO_PLANILLA = "SELECT MAX(CONVERT(int,SUBSTRING(S.NRO_PLANILLA,CHARINDEX('-', S.NRO_PLANILLA)+1 , 4)))  FROM SINI_SINIESTRO S "
      + "INNER JOIN SINI_POLIZA P ON S.NRO_SINIESTRO = P.NRO_SINIESTRO " + "WHERE P.COD_SOCIO = #{codSocio} AND SUBSTRING( S.NRO_PLANILLA, CHARINDEX('-', S.NRO_PLANILLA) -4,4) = YEAR(GETDATE())";

  @Select(SQL_MAX_NRO_PLANILLA)
  Integer getMaxNroPlanilla(@Param("codSocio") String codSocio);

  final String SQL_MAX_NRO_SINIESTRO = "SELECT MAX(CONVERT(int,SUBSTRING(S.NRO_SINIESTRO, 9, 4))) FROM SINI_SINIESTRO S "
      + "WHERE S.NRO_SINIESTRO like CONVERT(VARCHAR,#{codProductoSini,jdbcType=VARCHAR})+'-'+SUBSTRING(CONVERT(VARCHAR,YEAR(GETDATE())),3,2)+'%'";

  @Select(SQL_MAX_NRO_SINIESTRO)
  Integer getMaxNroSiniestro(@Param("codProductoSini") Short codProductoSini);

  final String CANTIDAD_DE_SINIESTRO_POR_DNI = "SELECT count(*) AS  CANTIDAD FROM SINI_SINIESTRO WHERE NRO_DOCUM_ASEGURADO=#{codDni} AND TIP_DOCUM_ASEGURADO=#{tipDoc} ";

  @Select(CANTIDAD_DE_SINIESTRO_POR_DNI)
  String cantSiniestro(@Param("codDni") String codDni, @Param("tipDoc") String tipDoc);

  final String CANTIDAD_DIAS_HABILES = "SELECT" + "(DATEDIFF(dd,  #{fechainicial}, #{fechaFinal}) + 1)" + " -(DATEDIFF(wk,  #{fechainicial},#{fechaFinal}) * 2)"
      + "-(CASE WHEN DATENAME(dw, #{fechainicial}) = 'Sunday' THEN 1 ELSE 0 END)" + "-(CASE WHEN DATENAME(dw, #{fechaFinal}) = 'Saturday' THEN 1 ELSE 0 END)";

  @Select(CANTIDAD_DIAS_HABILES)
  Integer cantDiasHabiles(@Param("fechainicial") Date fechainicial, @Param("fechaFinal") Date fechaFinal);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int countByExample(SiniSiniestroExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int deleteByExample(SiniSiniestroExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int deleteByPrimaryKey(String nroSiniestro);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int insert(SiniSiniestro record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int insertSelective(SiniSiniestro record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  List<SiniSiniestro> selectByExample(SiniSiniestroExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  SiniSiniestro selectByPrimaryKey(String nroSiniestro);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int updateByExampleSelective(@Param("record") SiniSiniestro record, @Param("example") SiniSiniestroExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int updateByExample(@Param("record") SiniSiniestro record, @Param("example") SiniSiniestroExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int updateByPrimaryKeySelective(SiniSiniestro record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_SINIESTRO
   *
   * @mbggenerated Thu Jul 31 14:38:07 COT 2014
   */
  int updateByPrimaryKey(SiniSiniestro record);
}