package com.cardif.satelite.tesoreria.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.tesoreria.model.PagoComisionItf;
import com.cardif.satelite.tesoreria.model.PagoComisionItfExample;

public interface PagoComisionItfMapper {

  final String INSERT = "INSERT INTO PAGO_COMISION_ITF VALUES (#{nom_excel}, #{usu_creacion}, #{fec_creacion}, #{usu_modificacion}, #{fec_modificacion})";
  final String COUNT_EXCEL = "SELECT COUNT(1) FROM PAGO_COMISION_ITF WHERE nom_excel=#{nom_excel}";

  @Insert(INSERT)
  void insertarPagoComisionItf(PagoComisionItf pagoComisionItf);

  @Select(COUNT_EXCEL)
  int countExcel(String nom_excel);

  int countByExample(PagoComisionItfExample example);

  int deleteByExample(PagoComisionItfExample example);

  int deleteByPrimaryKey(String nom_excel);

  int insert(PagoComisionItf record);

  int insertSelective(PagoComisionItf record);

  List<PagoComisionItf> selectByExample(PagoComisionItfExample example);

  PagoComisionItf selectByPrimaryKey(String nom_excel);

  int updateByExampleSelective(@Param("record") PagoComisionItf record, @Param("example") PagoComisionItfExample example);

  int updateByExample(@Param("record") PagoComisionItf record, @Param("example") PagoComisionItfExample example);

  int updateByPrimaryKeySelective(PagoComisionItf record);

  int updateByPrimaryKey(PagoComisionItf record);
}