package com.cardif.satelite.tesoreria.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.tesoreria.model.Firmante;

/**
 * 
 * @author paredesdi
 *
 */
public interface FirmanteMapper {

  final String BUSCAR_FIRMANTE = "SELECT COD_FIRMANTE,USU_FIRMANTE, NOM_FIRMANTE, APE_FIRMANTE, CORREO_FIRMANTE, EST_REGISTRO, FIR_FIRMANTE FROM FIRMANTE  "
      + "WHERE COD_FIRMANTE=#{codigo,jdbcType=BIGINT} AND EST_REGISTRO='A' ORDER BY COD_FIRMANTE DESC";

  @Select(BUSCAR_FIRMANTE)
  @Results(value = { @Result(property = "codigo", column = "COD_FIRMANTE"), @Result(property = "usuario", column = "USU_FIRMANTE"), @Result(property = "nombres", column = "NOM_FIRMANTE"),
      @Result(property = "apellidos", column = "APE_FIRMANTE"), @Result(property = "correo", column = "CORREO_FIRMANTE"), @Result(property = "estado", column = "EST_REGISTRO"),
      @Result(property = "firma", column = "FIR_FIRMANTE") })
  Firmante buscarFirmante(@Param("codigo") Long codigo);

  /**
   * Listar firmantes para la interfaz de MANTENIMIENTO DE FIRMANTES, Se listan los firmantes independientemente del estado en que se encuentran
   * 
   * Filtrar firmantes por usuario y estado para la interfaz de MANTENIMIENTO DE FIRMANTES
   */

  final String LISTAR_FIRMANTES_ACTIVOS = "SELECT COD_FIRMANTE,USU_FIRMANTE, NOM_FIRMANTE, APE_FIRMANTE, CORREO_FIRMANTE, EST_REGISTRO, FIR_FIRMANTE FROM FIRMANTE  "
      + "WHERE USU_FIRMANTE LIKE '%'+ISNULL(#{usuario,jdbcType=VARCHAR} ,USU_FIRMANTE)+'%'  AND EST_REGISTRO='A' ORDER BY COD_FIRMANTE DESC";

  @Select(LISTAR_FIRMANTES_ACTIVOS)
  @Results(value = { @Result(property = "codigo", column = "COD_FIRMANTE"), @Result(property = "usuario", column = "USU_FIRMANTE"), @Result(property = "nombres", column = "NOM_FIRMANTE"),
      @Result(property = "apellidos", column = "APE_FIRMANTE"), @Result(property = "correo", column = "CORREO_FIRMANTE"), @Result(property = "estado", column = "EST_REGISTRO"),
      @Result(property = "firma", column = "FIR_FIRMANTE") })
  List<Firmante> buscarFirmantesActivos(@Param("usuario") String usuario);

  final String LISTAR_FIRMANTES_TEMP_POR_FIRMANTE = "SELECT COD_TEMP, COD_FIRMANTE,USU_FIRMANTE, NOM_FIRMANTE, APE_FIRMANTE, CORREO_FIRMANTE, EST_REGISTRO, FIR_FIRMANTE, "
      + "NOM_ACCION, MOT_ACCION, EST_ACCION, MOT_RECHAZO FROM FIRMANTE_TEMP WHERE COD_FIRMANTE=#{codigo,jdbcType=BIGINT} AND EST_ACCION!='A' ORDER BY COD_TEMP DESC";

  @Select(LISTAR_FIRMANTES_TEMP_POR_FIRMANTE)
  @Results(value = { @Result(property = "temporal", column = "COD_TEMP"), @Result(property = "codigo", column = "COD_FIRMANTE"), @Result(property = "usuario", column = "USU_FIRMANTE"),
      @Result(property = "nombres", column = "NOM_FIRMANTE"), @Result(property = "apellidos", column = "APE_FIRMANTE"), @Result(property = "correo", column = "CORREO_FIRMANTE"),
      @Result(property = "estado", column = "EST_REGISTRO"), @Result(property = "firma", column = "FIR_FIRMANTE"), @Result(property = "nombreAccion", column = "NOM_ACCION"),
      @Result(property = "estadoAccion", column = "EST_ACCION"), @Result(property = "motivoAccion", column = "MOT_ACCION"), @Result(property = "motivoRechazo", column = "MOT_RECHAZO") })
  List<Firmante> buscarFirmantesTemporalesPorFirmante(@Param("codigo") Long codigo);

  final String LISTAR_FIRMANTES_TEMP = "SELECT COD_TEMP, COD_FIRMANTE,USU_FIRMANTE, NOM_FIRMANTE, APE_FIRMANTE, CORREO_FIRMANTE, EST_REGISTRO, FIR_FIRMANTE, "
      + "NOM_ACCION, MOT_ACCION, EST_ACCION, MOT_RECHAZO FROM FIRMANTE_TEMP WHERE EST_ACCION!='A' ORDER BY COD_TEMP DESC";

  @Select(LISTAR_FIRMANTES_TEMP)
  @Results(value = { @Result(property = "temporal", column = "COD_TEMP"), @Result(property = "codigo", column = "COD_FIRMANTE"), @Result(property = "usuario", column = "USU_FIRMANTE"),
      @Result(property = "nombres", column = "NOM_FIRMANTE"), @Result(property = "apellidos", column = "APE_FIRMANTE"), @Result(property = "correo", column = "CORREO_FIRMANTE"),
      @Result(property = "estado", column = "EST_REGISTRO"), @Result(property = "firma", column = "FIR_FIRMANTE"), @Result(property = "nombreAccion", column = "NOM_ACCION"),
      @Result(property = "estadoAccion", column = "EST_ACCION"), @Result(property = "motivoAccion", column = "MOT_ACCION"), @Result(property = "motivoRechazo", column = "MOT_RECHAZO") })
  List<Firmante> buscarFirmantesTemporales();

  final String BUSCAR_NUM_USUARIO_EN_FIRMANTE_TEMP = "select count(1) as NUM from FIRMANTE_TEMP where usu_firmante=#{usuario,jdbcType=VARCHAR}";

  @Select(BUSCAR_NUM_USUARIO_EN_FIRMANTE_TEMP)
  int buscarFirmanteTemporal(@Param("usuario") String usuario);

  final String BUSCAR_NUM_USUARIO_EN_FIRMANTE_ACTIVO = "select count(1) as NUM from FIRMANTE where usu_firmante=#{usuario,jdbcType=VARCHAR} and est_registro='A'";

  @Select(BUSCAR_NUM_USUARIO_EN_FIRMANTE_ACTIVO)
  int buscarFirmanteActivo(@Param("usuario") String usuario);

  int insertarFirmanteTemporal(Firmante firmante);

  int eliminarFirmanteTemporal(Firmante firmante);

  int rechazarFirmanteTemporal(Firmante firmante);

  int aprobarFirmanteTemporal(Firmante firmante);

  int insertarFirmante(Firmante firmante);

  int actualizarFirmante(Firmante firmante);

  int eliminarFirmante(Firmante firmante);

  final String BUSCAR_FIRMANTE_POR_USUARIO = "SELECT COD_FIRMANTE,USU_FIRMANTE, CLAVE_FIRMANTE, NOM_FIRMANTE, APE_FIRMANTE, CORREO_FIRMANTE, EST_REGISTRO, FIR_FIRMANTE FROM FIRMANTE  "
      + "WHERE USU_FIRMANTE=#{usuario,jdbcType=VARCHAR} AND EST_REGISTRO='A' ORDER BY COD_FIRMANTE DESC";

  @Select(BUSCAR_FIRMANTE_POR_USUARIO)
  @Results(value = { @Result(property = "codigo", column = "COD_FIRMANTE"), @Result(property = "usuario", column = "USU_FIRMANTE"), @Result(property = "nombres", column = "NOM_FIRMANTE"),
      @Result(property = "apellidos", column = "APE_FIRMANTE"), @Result(property = "correo", column = "CORREO_FIRMANTE"), @Result(property = "estado", column = "EST_REGISTRO"),
      @Result(property = "firma", column = "FIR_FIRMANTE"), @Result(property = "clave", column = "CLAVE_FIRMANTE") })
  Firmante buscarFirmantePorUsuario(@Param("usuario") String usuario);

  final String BUSCAR_FIRMANTE_POR_USUARIO_CLAVE = "SELECT COD_FIRMANTE,USU_FIRMANTE, NOM_FIRMANTE, APE_FIRMANTE, CORREO_FIRMANTE, EST_REGISTRO, FIR_FIRMANTE FROM FIRMANTE  "
      + "WHERE USU_FIRMANTE=#{usuario,jdbcType=VARCHAR} AND CLAVE_FIRMANTE=#{clave,jdbcType=LONGVARBINARY} AND EST_REGISTRO='A' ORDER BY COD_FIRMANTE DESC";

  @Select(BUSCAR_FIRMANTE_POR_USUARIO_CLAVE)
  @Results(value = { @Result(property = "codigo", column = "COD_FIRMANTE"), @Result(property = "usuario", column = "USU_FIRMANTE"), @Result(property = "nombres", column = "NOM_FIRMANTE"),
      @Result(property = "apellidos", column = "APE_FIRMANTE"), @Result(property = "correo", column = "CORREO_FIRMANTE"), @Result(property = "estado", column = "EST_REGISTRO"),
      @Result(property = "firma", column = "FIR_FIRMANTE") })
  List<Firmante> validarFirmante(@Param("usuario") String usuario, @Param("clave") byte[] clave);

  final String SET_CLAVE = "UPDATE FIRMANTE SET CLAVE_FIRMANTE=#{clave,jdbcType=LONGVARBINARY} WHERE COD_FIRMANTE=#{codigo,jdbcType=BIGINT}";

  @Update(SET_CLAVE)
  int configurarClave(@Param("codigo") Long codigo, @Param("clave") byte[] enc);

}