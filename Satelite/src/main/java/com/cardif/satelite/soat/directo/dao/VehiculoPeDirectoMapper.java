package com.cardif.satelite.soat.directo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.callcenter.bean.ConsultaRegVehicular;
import com.cardif.satelite.soat.model.VehiculoPe;

public interface VehiculoPeDirectoMapper {

  final String SELECT_VEHICULO = "SELECT A.* " + "FROM vehiculo_pe A,modelo_vehiculo b, marca_vehiculo c " + "WHERE A.placa = #{placa} "
      + "and a.modelo_vehiculo=b.id and b.marca_vehiculo = c.id and a.marca_vehiculo = c.id ";

  //EPINEDA 29/12/2015 INICIO
  // SELECT_REG_VEHICULAR_OLD ES LA VARIABLE ANTIGUA ANTES COMO REALIZA LA CONSULTA, 
  //SE AGREGA UNA NUEVA SELECT_REG_VEHICULAR PERO NO COSULT ALA TALBA INTERMEDIA DE "DIRECCION"
  final String SELECT_REG_VEHICULAR_OLD = "SELECT " + "P.RUT_PROPIETARIO AS PROPIETARIO, VP.ANIO_VEHICULO, " + "VP.NUMERO_ASIENTO, VP.NUMERO_SERIE, " + "VP.PLACA, VP.CATEGORIA_CLASE_VEHICULO, "
      + "MOV.nombre_modelovehiculo AS MODELO, MAV.NOMBRE_MARCAVEHICULO AS MARCA, " + "VP.USO_VEHICULO, DEP.NOMBRE_DEPARTAMENTO AS DEPARTAMENTO " + "FROM VEHICULO_PE VP "
      + "LEFT JOIN DIRECCION_PROPIETARIO_VEHICULO DPV ON DPV.PLACA = VP.PLACA " + "LEFT JOIN PROPIETARIO P ON DPV.PROPIETARIO = P.ID " + "LEFT JOIN DIRECCION D ON DPV.DIRECCION = D.ID_DIRECCION "
      + "LEFT JOIN MARCA_VEHICULO MAV ON VP.MARCA_VEHICULO = MAV.ID " + "LEFT JOIN MODELO_VEHICULO MOV ON VP.MODELO_VEHICULO = MOV.ID AND MOV.MARCA_VEHICULO = MAV.ID "
      + "LEFT JOIN DEPARTAMENTO DEP ON D.DEPARTAMENTO = DEP.COD_DEPARTAMENTO " + "WHERE VP.PLACA = #{placa} ";

  final String SELECT_REG_VEHICULAR = "SELECT " + 
		  "P.RUT_PROPIETARIO AS PROPIETARIO, VP.ANIO_VEHICULO, " + 
		  "VP.NUMERO_ASIENTO, VP.NUMERO_SERIE, " + 
		  "VP.PLACA, VP.CATEGORIA_CLASE_VEHICULO, " +
	      "MOV.nombre_modelovehiculo AS MODELO, MAV.NOMBRE_MARCAVEHICULO AS MARCA, " + 
		  "VP.USO_VEHICULO, DEP.NOMBRE_DEPARTAMENTO AS DEPARTAMENTO " + 
		  "FROM VEHICULO_PE VP " +
	      "LEFT JOIN DIRECCION_PROPIETARIO_VEHICULO DPV ON DPV.PLACA = VP.PLACA " + 
		  "LEFT JOIN PROPIETARIO P ON DPV.PROPIETARIO = P.ID " +
		  "LEFT JOIN MARCA_VEHICULO MAV ON VP.MARCA_VEHICULO = MAV.ID " + 
		  "LEFT JOIN MODELO_VEHICULO MOV ON VP.MODELO_VEHICULO = MOV.ID AND MOV.MARCA_VEHICULO = MAV.ID " +
		  "LEFT JOIN DEPARTAMENTO DEP ON DPV.DIRECCION = DEP.COD_DEPARTAMENTO " + 
		  "WHERE VP.PLACA = #{placa} ";
  //EPINEDA 29/12/2015 FIN  
  
  
  final String SELECT_VEHI_PROPIETARIO_X_DNI = "SELECT a.placa, b.nombre_modelovehiculo as modelo, " + "c.nombre_marcavehiculo as marca, A.numero_serie,a.anio_vehiculo, "
      + "a.uso_vehiculo, A.CATEGORIA_CLASE_VEHICULO, a.numero_asiento, " + "(select nombre_departamento  FROM departamento " + "where cod_departamento = (select a.departamento from direccion a, "
      + "direccion_propietario_vehiculo  b where a.id_direccion= b.direccion " + "and b.placa=(select d.placa from direccion_propietario_vehiculo d, propietario f "
      + "where d.propietario= f.id and f.rut_propietario=#{dni}))) as departamento " + "FROM vehiculo_pe A, modelo_vehiculo b, marca_vehiculo c "
      + "WHERE a.modelo_vehiculo=b.id and b.marca_vehiculo = c.id and a.marca_vehiculo = c.id " + "and placa=(select d.placa from direccion_propietario_vehiculo d, propietario f "
      + "where d.propietario= f.id and f.rut_propietario=#{dni})";

  final String SELECT_VEHICULO_BY_PLACA = "SELECT * FROM VEHICULO_PE  where PLACA = #{placa}";

  @Select(SELECT_VEHICULO)
  @ResultMap(value = "BaseResultMap")
  VehiculoPe selectVehiculo(@Param("placa") String placa);

  @Select(SELECT_REG_VEHICULAR)
  @ResultMap(value = "BaseResultMapRegVehicular")
  List<ConsultaRegVehicular> selectVehicular(@Param("placa") String placa);

  @Select(SELECT_VEHI_PROPIETARIO_X_DNI)
  @ResultMap(value = "BaseResultMapRegVehicular")
  ConsultaRegVehicular selectRegistro(@Param("dni") String dni);

  @Select(SELECT_VEHICULO_BY_PLACA)
  @ResultMap(value = "BaseResultMap")
  VehiculoPe selectVehiculoByPlaca(@Param("placa") String placa);

  int insert(VehiculoPe record);

  int updateByPrimaryKey(VehiculoPe record);
}