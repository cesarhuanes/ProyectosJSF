package com.cardif.satelite.telemarketing.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.telemarketing.bean.BaseVigilance;

public interface VigilanceMapper {

  final String COUNT_VIGILANCE = " SELECT COUNT(1) as Total FROM VIGILANCE";

  @Select(COUNT_VIGILANCE)
  Long countVigilance();

  final String SELECT_BASE_VIGILANCE = "SELECT TOP 1 * FROM TLMK_BASE_VIGILANCE ORDER BY COD_BASE DESC";

  @Select(SELECT_BASE_VIGILANCE)
  @Results(value = { @Result(property = "numRegistros", column = "NUM_REGISTROS"), @Result(property = "estado", column = "ESTADO"), @Result(property = "usuCreacion", column = "USU_CREACION"),
      @Result(property = "fecCreacion", column = "FEC_CREACION") })
  BaseVigilance getBaseVigilance();

  final String COUNT_TLMK_BASE_VIGILANCE = "SELECT COUNT(1) AS TOTAL FROM TLMK_BASE_VIGILANCE WHERE ESTADO='PENDIENTE'";

  @Select(COUNT_TLMK_BASE_VIGILANCE)
  int countBaseVigilance();

  final String SELECT_ESTADO = "SELECT ESTADO FROM TLMK_BASE_VIGILANCE WHERE COD_BASE=#{codigoBase}  ";

  @Select(SELECT_ESTADO)
  @Results(value = { @Result(property = "estado", column = "ESTADO") })
  String selectEstado(@Param("codigoBase") String codigoBase);

  final String INSERTAR_BASE_VIGILANCE = "insert into TLMK_BASE_VIGILANCE (ESTADO, USU_CREACION, FEC_CREACION) values (#{estado,jdbcType=VARCHAR}, #{usuCreacion,jdbcType=VARCHAR}, #{fecCreacion,jdbcType=TIMESTAMP}) ";

  @Insert(INSERTAR_BASE_VIGILANCE)
  void insert(BaseVigilance baseVigilance);

  final String CODIGO_BASE = "SELECT TOP 1 COD_BASE FROM TLMK_BASE_VIGILANCE WHERE ESTADO='PENDIENTE' ORDER BY COD_BASE DESC";

  @Select(CODIGO_BASE)
  String getCodigoBase();

  final String UPDATE_BASE_VIGILANCE = "update TLMK_BASE_VIGILANCE set NUM_REGISTROS = #{numRegistros,jdbcType=NUMERIC} WHERE COD_BASE = #{codigoBase}";

  @Select(UPDATE_BASE_VIGILANCE)
  void update(@Param("codigoBase") String codigoBase, @Param("numRegistros") Long numRegistros);
}
