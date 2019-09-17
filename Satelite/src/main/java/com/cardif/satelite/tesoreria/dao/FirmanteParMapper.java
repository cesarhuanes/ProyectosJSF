package com.cardif.satelite.tesoreria.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.model.FirmantePar;

public interface FirmanteParMapper {

  final String SELECT_PARES_ACTIVOS_POR_FIRMANTE = "SELECT COD_PAR, f1.correo_firmante as CORREO1, f2.correo_firmante as CORREO2, f1.nom_Firmante + ' ' + f1.ape_firmante as FIRMANTE1,  f2.nom_Firmante + ' ' + f2.ape_firmante as FIRMANTE2, EST_PAR FROM FIRMANTE_PAR "
      + "inner join Firmante f1 on f1.cod_firmante=cod_firmante1 inner join firmante f2 on f2.cod_firmante=cod_firmante2 "
      + "where (cod_firmante1=ISNULL(#{codigo,jdbcType=BIGINT},cod_firmante1) or cod_firmante2=ISNULL(#{codigo,jdbcType=BIGINT},cod_firmante2)) and est_par='A'";

  @Select(SELECT_PARES_ACTIVOS_POR_FIRMANTE)
  @Results(value = { @Result(property = "codigo", column = "COD_PAR"), @Result(property = "firmante1", column = "FIRMANTE1"), @Result(property = "firmante2", column = "FIRMANTE2"),
      @Result(property = "estado", column = "EST_PAR"), @Result(property = "correo1", column = "CORREO1"), @Result(property = "correo2", column = "CORREO2") })
  List<FirmantePar> buscarPares(@Param("codigo") Long codigo);

  final String BUSCAR_PAR_EN_ACTIVOS = "SELECT COD_PAR, EST_PAR FROM FIRMANTE_PAR where ( (cod_firmante1=ISNULL(#{codigo1,jdbcType=BIGINT},cod_firmante1) AND "
      + "cod_firmante2=ISNULL(#{codigo2,jdbcType=BIGINT},cod_firmante2)) or (cod_firmante1=ISNULL(#{codigo2,jdbcType=BIGINT},cod_firmante1) AND "
      + "cod_firmante2=ISNULL(#{codigo1,jdbcType=BIGINT},cod_firmante2)) ) AND EST_PAR='A'";

  @Select(BUSCAR_PAR_EN_ACTIVOS)
  @Results(value = { @Result(property = "codigo", column = "COD_PAR"), @Result(property = "estado", column = "EST_PAR") })
  List<FirmantePar> buscarPar(@Param("codigo1") Long codigo1, @Param("codigo2") Long codigo2);

  final String OBTENER_PAR = "SELECT COD_PAR, COD_FIRMANTE1,COD_FIRMANTE2, f1.nom_Firmante + ' ' + f1.ape_firmante as FIRMANTE1,  f2.nom_Firmante + ' ' + f2.ape_firmante as FIRMANTE2, EST_PAR, f1.CORREO_FIRMANTE as CORREO1, f2.CORREO_FIRMANTE as CORREO2 FROM FIRMANTE_PAR "
      + "inner join Firmante f1 on f1.cod_firmante=cod_firmante1 inner join firmante f2 on f2.cod_firmante=cod_firmante2 where COD_PAR=#{codigo,jdbcType=BIGINT} and est_par='A'";

  @Select(OBTENER_PAR)
  @Results(value = { @Result(property = "codigo", column = "COD_PAR"), @Result(property = "codigoFirmante1", column = "COD_FIRMANTE1"), @Result(property = "codigoFirmante2", column = "COD_FIRMANTE2"),
      @Result(property = "firmante1", column = "FIRMANTE1"), @Result(property = "firmante2", column = "FIRMANTE2"), @Result(property = "estado", column = "EST_PAR"),
      @Result(property = "correo1", column = "CORREO1"), @Result(property = "correo2", column = "CORREO2") })
  FirmantePar obtenerPar(@Param("codigo") Long codigo);

  final String OBTENER_PAR_CON_INACTIVOS = "SELECT COD_PAR, COD_FIRMANTE1,COD_FIRMANTE2, f1.nom_Firmante + ' ' + f1.ape_firmante as FIRMANTE1,  f2.nom_Firmante + ' ' + f2.ape_firmante as FIRMANTE2, EST_PAR, f1.CORREO_FIRMANTE as CORREO1, f2.CORREO_FIRMANTE as CORREO2 FROM FIRMANTE_PAR "
      + "inner join Firmante f1 on f1.cod_firmante=cod_firmante1 inner join firmante f2 on f2.cod_firmante=cod_firmante2 where COD_PAR=#{codigo,jdbcType=BIGINT}";

  @Select(OBTENER_PAR_CON_INACTIVOS)
  @Results(value = { @Result(property = "codigo", column = "COD_PAR"), @Result(property = "codigoFirmante1", column = "COD_FIRMANTE1"), @Result(property = "codigoFirmante2", column = "COD_FIRMANTE2"),
      @Result(property = "firmante1", column = "FIRMANTE1"), @Result(property = "firmante2", column = "FIRMANTE2"), @Result(property = "estado", column = "EST_PAR"),
      @Result(property = "correo1", column = "CORREO1"), @Result(property = "correo2", column = "CORREO2") })
  FirmantePar obtenerParConInactivos(@Param("codigo") Long codigo);

  /**
   * Registrar firmantes par
   */

  final String INSERTAR_PAR = "insert into dbo.FIRMANTE_PAR (COD_FIRMANTE1, COD_FIRMANTE2, EST_PAR) values (#{codigoFirmante1,jdbcType=BIGINT}, #{codigoFirmante2,jdbcType=BIGINT}, #{estado,jdbcType=CHAR}) ";

  @Insert(INSERTAR_PAR)
  int insertarPar(FirmantePar firmantePar);

  final String ELIMINAR_PAR = " update dbo.FIRMANTE_PAR set EST_PAR=#{estado,jdbcType=CHAR} where COD_PAR=#{codigo,jdbcType=BIGINT} ";

  @Update(ELIMINAR_PAR)
  int eliminarPar(FirmantePar firmantePar);
}