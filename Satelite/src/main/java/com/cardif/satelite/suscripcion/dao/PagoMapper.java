package com.cardif.satelite.suscripcion.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.soatsucursal.Pago;
import com.cardif.satelite.model.soatsucursal.PagoExample;
import com.cardif.satelite.suscripcion.bean.RepVentaSoatSucursalBean;

public interface PagoMapper {
  final String LISTAR_REPORTA_SOAT_VENTA = "select k.fecha, k.monto, b.dni_propietario, j.nombres, j.paterno, j.materno,c.direccion , "
      + " d.nombre_distrito as distrito, c.telefono, j.fecha_nacimiento , g.placa, i.nombre_marcavehiculo, h.nombre_modelovehiculo, g.anio_vehiculo, "
      + " g.uso_vehiculo, g.categoria_clase_vehiculo, f.nombre_departamento as departamento, g.numero_serie, g.numero_asiento,c.email, a.FECHA_VIGENCIA_DESDE, a.FECHA_VIGENCIA_HASTA ,"
      + " k.monto, a.nro_poliza_pe, a.NUMERO_DOCUMENTO_VALORADO, a.cotizacion_pe, a.orden_compra, l.rut, k.numero_transaccion, k.estado_pago, m.nombre , n.nombre_tipo ,o.dsc_socio  ,p.nombre as VENDEDOR   , q.nombre as CAJERO"
      + "  from " + " user_ventas_sucursal.poliza_pe a, " + " user_ventas_sucursal.cotizacion_pe b, user_ventas_sucursal.direccion c,user_ventas_sucursal.distrito d,"
      + " user_ventas_sucursal.provincia e,user_ventas_sucursal.departamento f,user_ventas_sucursal.vehiculo_pe g,user_ventas_sucursal.modelo_vehiculo h,user_ventas_sucursal.marca_vehiculo i,"
      + " user_ventas_sucursal.propietario j,user_ventas_sucursal.orden_compra_pe k,user_ventas_sucursal.usuario l,user_ventas_sucursal.sucursal m , user_ventas_sucursal.tipo_documento n ,user_ventas_sucursal.socio o ,user_ventas_sucursal.usuario p  ,user_ventas_sucursal.usuario q   where "
      + " a.cotizacion_pe=b.id and b.direccion_propietario=c.id_direccion and c.distrito=d.cod_distrito and c.provincia=e.cod_provincia and c.departamento=f.cod_departamento "
      + " and b.vehiculo=g.id  and g.marca_vehiculo=i.id and g.modelo_vehiculo=h.id and b.propietario=j.id and a.orden_compra=k.orden_compra and b.ejecutivo_venta=l.id "
      + " and l.sucursal=m.id and k.fecha >=TO_DATE(#{desde},'DD/MM/RR HH24:MI:SS')  and k.fecha <TO_DATE(#{hasta},'DD/MM/RR HH24:MI:SS')  and  n.id = j.id_tipo_dcto  and o.cod_socio=k.codigo_socio  and o.cod_socio=#{socio}  and  m.id = nvl(#{sucursal}, m.id)  AND p.id=b.ejecutivo_venta   AND  q.id (+)= a.documento  order by a.NRO_POLIZA_PE ";

  @Select(LISTAR_REPORTA_SOAT_VENTA)
  @ResultMap(value = "BaseResultMapReportaSoatVentaSucursal")
  List<RepVentaSoatSucursalBean> listaReporte(@Param("desde") String desde, @Param("hasta") String hasta, @Param("socio") String socio, @Param("sucursal") String sucursal);

  final String LISTAR_REPORTA_SOAT_VENTA2 = "select k.fecha, k.monto, b.dni_propietario, j.nombres, j.paterno, j.materno,c.direccion , "
      + " d.nombre_distrito as distrito, c.telefono, j.fecha_nacimiento , g.placa, i.nombre_marcavehiculo, h.nombre_modelovehiculo, g.anio_vehiculo, "
      + " g.uso_vehiculo, g.categoria_clase_vehiculo, f.nombre_departamento as departamento, g.numero_serie, g.numero_asiento,c.email, a.FECHA_VIGENCIA_DESDE, a.FECHA_VIGENCIA_HASTA ,"
      + " k.monto, a.nro_poliza_pe, a.NUMERO_DOCUMENTO_VALORADO, a.cotizacion_pe, a.orden_compra, l.rut, k.numero_transaccion, k.estado_pago, m.nombre , n.nombre_tipo ,o.dsc_socio  ,p.nombre as VENDEDOR   , q.nombre as CAJERO"
      + "  from " + " user_ventas_sucursal.poliza_pe a, " + " user_ventas_sucursal.cotizacion_pe b, user_ventas_sucursal.direccion c,user_ventas_sucursal.distrito d,"
      + " user_ventas_sucursal.provincia e,user_ventas_sucursal.departamento f,user_ventas_sucursal.vehiculo_pe g,user_ventas_sucursal.modelo_vehiculo h,user_ventas_sucursal.marca_vehiculo i,"
      + " user_ventas_sucursal.propietario j,user_ventas_sucursal.orden_compra_pe k,user_ventas_sucursal.usuario l,user_ventas_sucursal.sucursal m , user_ventas_sucursal.tipo_documento n ,user_ventas_sucursal.socio o ,user_ventas_sucursal.usuario p  ,user_ventas_sucursal.usuario q   where "
      + " a.cotizacion_pe=b.id and b.direccion_propietario=c.id_direccion and c.distrito=d.cod_distrito and c.provincia=e.cod_provincia and c.departamento=f.cod_departamento "
      + " and b.vehiculo=g.id  and g.marca_vehiculo=i.id and g.modelo_vehiculo=h.id and b.propietario=j.id and a.orden_compra=k.orden_compra and b.ejecutivo_venta=l.id "
      + " and l.sucursal=m.id and k.fecha >=TO_DATE(#{desde},'DD/MM/RR HH24:MI:SS')  and k.fecha <TO_DATE(#{hasta},'DD/MM/RR HH24:MI:SS')  and  n.id = j.id_tipo_dcto  and o.cod_socio=k.codigo_socio  and o.cod_socio=#{socio}  AND p.id=b.ejecutivo_venta   AND  q.id (+)= a.documento  order by a.NRO_POLIZA_PE ";

  @Select(LISTAR_REPORTA_SOAT_VENTA2)
  @ResultMap(value = "BaseResultMapReportaSoatVentaSucursal")
  List<RepVentaSoatSucursalBean> listaReporte2(@Param("desde") String desde, @Param("hasta") String hasta, @Param("socio") String socio);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int countByExample(PagoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int deleteByExample(PagoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int deleteByPrimaryKey(BigDecimal id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int insert(Pago record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int insertSelective(Pago record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  List<Pago> selectByExample(PagoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  Pago selectByPrimaryKey(BigDecimal id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int updateByExampleSelective(@Param("record") Pago record, @Param("example") PagoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int updateByExample(@Param("record") Pago record, @Param("example") PagoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int updateByPrimaryKeySelective(Pago record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_VENTAS_SUCURSAL.PAGO
   *
   * @mbggenerated Thu Sep 04 11:10:14 COT 2014
   */
  int updateByPrimaryKey(Pago record);
}