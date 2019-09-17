package com.cardif.satelite.ssis.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface SsisMapper {

  final String SP_JOB = "{ CALL SP_EJECUTAR_JOB( #{nombreJob} ) } ";
  final String tst = "select COUNT(*) as num from ERROR";
  @Select(SP_JOB)
  @Options(statementType = StatementType.CALLABLE)
  void ejecutarJob(@Param("nombreJob") String nombreJob);
  
  @Select(tst)
  public int count();
}