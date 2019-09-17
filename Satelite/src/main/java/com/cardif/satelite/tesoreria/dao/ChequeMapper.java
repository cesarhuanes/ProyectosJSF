package com.cardif.satelite.tesoreria.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.tesoreria.model.Cheque;

public interface ChequeMapper {

  final String SELECT_CHEQUES = "SELECT ch.* " + ",(select f.nom_firmante+' '+ape_firmante from FIRMANTE_PAR p, FIRMANTE f "
      + "where f.cod_firmante=p.cod_firmante1 AND p.cod_par=ch.cod_par) as NOM_FIRMANTE1 " + ",(select f.nom_firmante+' '+ape_firmante from FIRMANTE_PAR p, FIRMANTE f  "
      + "where f.cod_firmante=p.cod_firmante2 AND p.cod_par=ch.cod_par) as NOM_FIRMANTE2 " + "FROM CHEQUE ch " + "WHERE (est_cheque='I' OR (est_cheque='E' AND FEC_IMPRESION is not null )) "
      + "AND fec_impresion>=DATEADD(dd, 0, DATEDIFF(dd, 0, ISNULL(#{fec1,jdbcType=TIMESTAMP},fec_impresion)))  "
      + "AND fec_impresion< DATEADD(dd, 1, DATEDIFF(dd, 0, ISNULL(#{fec2,jdbcType=TIMESTAMP},fec_impresion))) " + "AND ch.est_cheque=ISNULL(#{estado,jdbcType=CHAR},ch.est_cheque) "
      + "AND ch.ban_cheque=ISNULL(#{banco,,jdbcType=VARCHAR},ch.ban_cheque) " + "AND ch.tip_moneda=ISNULL(#{moneda,jdbcType=VARCHAR},ch.tip_moneda)";

  @Select(SELECT_CHEQUES)
  @ResultMap("BaseResultMap")
  List<Cheque> selectCheques(@Param("fec1") Date fecImpresion1, @Param("fec2") Date fecImpresion2, @Param("estado") String estado, @Param("banco") String banco, @Param("moneda") String moneda);

  final String SELECT_BY_NRO_CHEQUE = "SELECT *,'' as NOM_FIRMANTE1,'' as NOM_FIRMANTE2 FROM CHEQUE WHERE FEC_IMPRESION is not null AND EST_CHEQUE='I' "
      + "AND NRO_CHEQUE=ISNULL(#{nro_cheque,jdbcType=VARCHAR},NRO_CHEQUE) order by FEC_IMPRESION desc";

  @Select(SELECT_BY_NRO_CHEQUE)
  @ResultMap("BaseResultMap")
  List<Cheque> selectChequeByNro(String nro_cheque);

  final String COLUMNAS_CHEQUES = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'CHEQUE' AND TABLE_SCHEMA='DBO'";

  @Select(COLUMNAS_CHEQUES)
  @ResultMap("StringResultMap")
  List<String> selectNomColumnas();

  final String UPDATE = "UPDATE CHEQUE SET EST_CHEQUE='E', USU_ANULACION=#{usuario,jdbcType=VARCHAR}, FEC_ANULACION=#{fecha,jdbcType=TIMESTAMP}, MOT_ANULACION=#{motivo,jdbcType=VARCHAR}, "
      + "USU_MODIFICACION=#{usuario,jdbcType=VARCHAR}, FEC_MODIFICACION=#{fecha,jdbcType=TIMESTAMP} WHERE COD_CHEQUE=#{codigo,jdbcType=BIGINT} ";

  @Update(UPDATE)
  void anularCheque(@Param("codigo") Long cod_cheque, @Param("usuario") String usuario, @Param("motivo") String motivo, @Param("fecha") Date fecha);

  final String INSERT = "INSERT INTO CHEQUE (COD_PAR,BAN_CHEQUE,NRO_CHEQUE,NOM_BENEFICIARIO,TIP_MONEDA,"
      + "IMP_CHEQUE, EST_CHEQUE,FEC_REGISTRO,USU_ASIGNACION, FEC_ASIGNACION, EST_FIRMANTE1, EST_FIRMANTE2, USU_CREACION,FEC_CREACION) "
      + "VALUES (#{codigoPar,jdbcType=BIGINT},#{banco,jdbcType=VARCHAR},#{numero,jdbcType=VARCHAR},#{nombreBeneficiario,jdbcType=VARCHAR},"
      + "#{tipoMoneda,jdbcType=VARCHAR},#{importe,jdbcType=NUMERIC},#{estadoCheque,jdbcType=CHAR},#{fechaRegistro,jdbcType=TIMESTAMP},"
      + "#{usuarioAsignacion,jdbcType=VARCHAR},#{fechaAsignacion,jdbcType=TIMESTAMP},#{estadoFirmante1,jdbcType=CHAR},#{estadoFirmante2,jdbcType=CHAR},"
      + "#{usuarioCreacion,jdbcType=VARCHAR},#{fechaCreacion,jdbcType=TIMESTAMP})";

  @Insert(INSERT)
  void insertar(Cheque cheque);

  final String CHEQUES_PENDIENTES = "select c.*, p.*, (f1.nom_firmante+ ' '+ f1.ape_firmante) as NOM_FIRMANTE1,  (f2.nom_firmante+ ' '+ f2.ape_firmante) as NOM_FIRMANTE2, "
      + "f1.cod_firmante AS COD_FIRMANTE1, f2.cod_firmante AS COD_FIRMANTE2 " + "from cheque c left join firmante_par p on p.cod_par=c.cod_par left join firmante f1 on "
      + "f1.cod_firmante=p.cod_firmante1 left join firmante f2 on f2.cod_firmante=p.cod_firmante2 where p.est_par='A' AND (c.est_cheque='P') "
      + "AND ((p.cod_firmante1=#{codigo,jdbcType=BIGINT} and c.est_FIRMANTE1='P')  OR (p.cod_firmante2=#{codigo,jdbcType=BIGINT} AND c.est_FIRMANTE2='P'))";

  @Select(CHEQUES_PENDIENTES)
  @ResultMap("BaseResultMap")
  List<Cheque> buscarChequesPendientes(@Param("codigo") Long codigoFirmante);

  final String CHEQUES_RECHAZADOS = "select c.*, p.*, (f1.nom_firmante+ ' '+ f1.ape_firmante) as NOM_FIRMANTE1,  (f2.nom_firmante+ ' '+ f2.ape_firmante) as NOM_FIRMANTE2, "
      + "f1.cod_firmante AS COD_FIRMANTE1, f2.cod_firmante AS COD_FIRMANTE2 "
      + "from cheque c left join firmante_par p on p.cod_par=c.cod_par left join firmante f1 on f1.cod_firmante=p.cod_firmante1 "
      + "left join firmante f2 on f2.cod_firmante=p.cod_firmante2 where p.est_par='A' AND (c.est_cheque='R')  AND ( (p.cod_firmante1=#{codigo,jdbcType=BIGINT} )OR (p.cod_firmante2=#{codigo,jdbcType=BIGINT}))";

  @Select(CHEQUES_RECHAZADOS)
  @ResultMap("BaseResultMap")
  List<Cheque> buscarChequesRechazados(@Param("codigo") Long codigoFirmante);

  final String LISTAR_CHEQUES = "SELECT c.*, f1.nom_firmante+' '+ f1.ape_firmante+'('+f1.est_registro+')' as NOM_FIRMANTE1, f2.nom_firmante+' '+ f2.ape_firmante+'('+f2.est_registro+')' as NOM_FIRMANTE2 FROM CHEQUE c "
      + "inner join firmante_par p on p.cod_par=c.cod_par inner join firmante f1 on f1.cod_firmante=p.cod_firmante1 inner join firmante f2 on f2.cod_firmante=p.cod_firmante2 "
      + "WHERE (EST_CHEQUE='P' OR EST_CHEQUE='A' OR EST_CHEQUE='R') AND EST_CHEQUE=ISNULL(#{estado,jdbcType=CHAR},EST_CHEQUE)";

  @Select(LISTAR_CHEQUES)
  @ResultMap("BaseResultMap")
  List<Cheque> listarCheques(@Param("estado") String estado);

  int actualizar(Cheque cheque);

  final String REACTIVAR_FIRMANTE1 = "update cheque set est_firmante1='P',est_cheque='P',USU_REACTIVACION=#{usuario,jdbcType=VARCHAR}, FEC_REACTIVACION=#{fecha,jdbcType=TIMESTAMP},"
      + "USU_MODIFICACION=#{usuario,jdbcType=VARCHAR}, FEC_MODIFICACION=#{fecha,jdbcType=TIMESTAMP} where cod_cheque=#{codigo,jdbcType=BIGINT} and est_cheque='R' and est_firmante1='R' ";

  @Update(REACTIVAR_FIRMANTE1)
  int reactivarFirmante1(@Param("codigo") Long codigo, @Param("usuario") String usuario, @Param("fecha") Date fecha);

  final String REACTIVAR_FIRMANTE2 = "update cheque set est_firmante2='P',est_cheque='P',USU_REACTIVACION=#{usuario,jdbcType=VARCHAR}, FEC_REACTIVACION=#{fecha,jdbcType=TIMESTAMP},"
      + "USU_MODIFICACION=#{usuario,jdbcType=VARCHAR}, FEC_MODIFICACION=#{fecha,jdbcType=TIMESTAMP} where cod_cheque=#{codigo,jdbcType=BIGINT} and est_cheque='R' and est_firmante2='R' ";

  @Update(REACTIVAR_FIRMANTE2)
  int reactivarFirmante2(@Param("codigo") Long codigo, @Param("usuario") String usuario, @Param("fecha") Date fecha);

  final String REASIGNAR = "update cheque set cod_par=#{par,jdbcType=BIGINT} ,est_cheque='P', mot_reasignacion=#{motivo,jdbcType=VARCHAR},"
      + "USU_REASIGNACION=#{usuario,jdbcType=VARCHAR}, FEC_REASIGNACION=#{fecha,jdbcType=VARCHAR},"
      + "USU_MODIFICACION=#{usuario,jdbcType=VARCHAR}, FEC_MODIFICACION=#{fecha,jdbcType=VARCHAR} where cod_cheque=#{codigo,jdbcType=BIGINT} and est_cheque='P' ";

  @Update(REASIGNAR)
  int reasignarCheque(@Param("codigo") Long codigo, @Param("par") Long par, @Param("usuario") String usuario, @Param("fecha") Date fecha, @Param("motivo") String motivo);

  final String IMPRIMIR = "update cheque set est_cheque='I',usu_impresion=#{usuario,jdbcType=VARCHAR},fec_impresion=#{fecha,jdbcType=VARCHAR},"
      + "USU_MODIFICACION=#{usuario,jdbcType=VARCHAR}, FEC_MODIFICACION=#{fecha,jdbcType=VARCHAR} where  est_cheque='A' and cod_cheque=#{codigo,jdbcType=BIGINT}";

  @Update(IMPRIMIR)
  int imprimirCheque(@Param("codigo") Long codigo, @Param("usuario") String usuario, @Param("fecha") Date fecha);
}