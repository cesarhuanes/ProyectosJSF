package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Proceso;
import com.cardif.satelite.model.reportesbs.ProcesoParametro;
import com.cardif.satelite.model.reportesbs.ProductoSocio;

public interface ProcesoParametroMapper {
	final String SELECT_PROCESO_PARAMETRO = " SELECT TABLA.COD_PROCESO_PARAMETRO,TABLA.COD_SOCIO,TABLA.SOCIO,TABLA.COD_TIPO_CONTRATO,TABLA.TIPO_CONTRATO,"
			+ " TABLA.COD_TIPO_SEGURO,TABLA.TIPO_SEGURO,TABLA.COD_TIPO_REASEGURO,TABLA.TIPO_REASEGURO,TABLA.COD_EMPRESA,TABLA.EMPRESA,"
			+ " TABLA.COD_PRODUCTO,TABLA.PRODUCTO,TABLA.CODIGO_ESTADO,TABLA.ESTADO,TABLA.COD_TIPO_MOVIMIENTO,TABLA.TIPO_MOVIMIENTO,"
			+ " TABLA.COD_TIPO_CUENTA,TABLA.TIPO_CUENTA,TABLA.SBS,TABLA.RIESGO,TABLA.DETALLE_CUENTA,TABLA.CUENTA_SALDO_SOLES,"
			+ " TABLA.CUENTA_PRIMA_SOLES,TABLA.CUENTA_DESCUENTO_SOLES,TABLA.CUENTA_SALDO_DOLARES,TABLA.CUENTA_PRIMA_DOLARES,"
			+ " TABLA.CUENTA_DESCUENTO_DOLARES FROM (SELECT PAR.COD_PROCESO_PARAMETRO,"
			+ " PAR.COD_SOCIO,SOS.DESCRIPCION AS SOCIO,"
			+ " PAR.COD_TIPO_CONTRATO,"
			+ " (SELECT X.DESCRIPCION_VALOR FROM PARAMETRO X  WHERE X.COD_PARAMETRO=PAR.COD_TIPO_CONTRATO) AS TIPO_CONTRATO,"
			+ " PAR.COD_TIPO_SEGURO,"
			+ " (SELECT X.DESCRIPCION_VALOR FROM PARAMETRO X  WHERE X.COD_PARAMETRO=PAR.COD_TIPO_SEGURO) AS TIPO_SEGURO,"
			+ " PAR.COD_TIPO_REASEGURO,"
			+ " (SELECT X.DESCRIPCION_VALOR FROM PARAMETRO X  WHERE X.COD_PARAMETRO=PAR.COD_TIPO_REASEGURO) AS TIPO_REASEGURO,"
			+ " PAR.COD_EMPRESA,"
			+ " EMP.DESCRIPCION AS EMPRESA,"
			+ " PAR.COD_PRODUCTO,"
			+ " PROD.DESCRIPCION AS PRODUCTO,"
			+ " PAR.COD_ESTADO   AS CODIGO_ESTADO,"
			+ " (SELECT X.DESCRIPCION_VALOR FROM PARAMETRO X  WHERE X.COD_PARAMETRO=PAR.COD_ESTADO)  AS ESTADO,"
			+ " PAR.COD_TIPO_MOVIMIENTO,"
			+ " (SELECT X.DESCRIPCION_VALOR FROM PARAMETRO X  WHERE X.COD_PARAMETRO=PAR.COD_TIPO_MOVIMIENTO) AS TIPO_MOVIMIENTO,"
			+ " PAR.COD_TIPO_CUENTA,"
			+ " CASE WHEN PAR.COD_TIPO_CUENTA <>'0803' THEN"
			+ " (SELECT X.DESCRIPCION_VALOR FROM PARAMETRO X  WHERE X.COD_PARAMETRO=PAR.COD_TIPO_CUENTA)"
			+ " ELSE "
			+ " PAR.DETALLE_CUENTA"
			+ " END "
			+ " AS TIPO_CUENTA,"
			+ " PAR.COD_SBS AS SBS,"
			+ " PAR.RIESGO AS RIESGO,"
			+ " DETALLE_CUENTA AS DETALLE_CUENTA,"
			+ " PAR.CUENTA_SALDO_SOLES AS CUENTA_SALDO_SOLES,"
			+ " PAR.CUENTA_PRIMA_SOLES AS CUENTA_PRIMA_SOLES,"
			+ " PAR.CUENTA_DESCUENTO_SOLES AS CUENTA_DESCUENTO_SOLES,"
			+ " PAR.CUENTA_SALDO_DOLARES AS CUENTA_SALDO_DOLARES,"
			+ " PAR.CUENTA_PRIMA_DOLARES AS CUENTA_PRIMA_DOLARES,"
			+ " PAR.CUENTA_DESCUENTO_DOLARES AS CUENTA_DESCUENTO_DOLARES"
			+ " FROM  PROCESO_PARAMETRO PAR "
			+ " INNER JOIN SOCIO SOS ON PAR.COD_SOCIO=SOS.COD_SOCIO"
			+ " INNER JOIN EMPRESA EMP ON PAR.COD_EMPRESA=EMP.COD_EMPRESA"
			+ " INNER JOIN PRODUCTO PROD ON PAR.COD_PRODUCTO=PROD.COD_PRODUCTO) TABLA"
			+ " WHERE "
			+ " (TABLA.COD_TIPO_CONTRATO=DECODE(#{codTipoContrato},'T',TABLA.COD_TIPO_CONTRATO,#{codTipoContrato})   ) AND"
			+ " (TABLA.COD_TIPO_SEGURO=DECODE(#{codTipoSeguro},'T',TABLA.COD_TIPO_SEGURO,#{codTipoSeguro})  ) AND"
			+ " (TABLA.COD_TIPO_REASEGURO=DECODE(#{codTipoReaseguro},'T',TABLA.COD_TIPO_REASEGURO,#{codTipoReaseguro})   ) AND"
			+ " (TABLA.COD_TIPO_MOVIMIENTO=DECODE(#{codTipoMovimiento},'T',TABLA.COD_TIPO_MOVIMIENTO,#{codTipoMovimiento})  ) AND"
			+ " (TABLA.CODIGO_ESTADO=DECODE(#{codEstado},'T',TABLA.CODIGO_ESTADO,#{codEstado})   ) AND"
			+ " TABLA.COD_EMPRESA =DECODE(#{codEmpresa},0,TABLA.COD_EMPRESA,#{codEmpresa}) AND"
			+ " TABLA.COD_SOCIO=DECODE(#{codSocio},0,TABLA.COD_SOCIO,#{codSocio}) AND"
			+ " TABLA.COD_PRODUCTO=DECODE(#{codProducto},0,TABLA.COD_PRODUCTO,#{codProducto}) AND"
			+ " TABLA.CUENTA_SALDO_SOLES=DECODE(#{cuentaSaldoSoles},0,TABLA.CUENTA_SALDO_SOLES,#{cuentaSaldoSoles}) AND"
			+ " TABLA.CUENTA_PRIMA_SOLES=DECODE(#{cuentaPrimaSoles},0,TABLA.CUENTA_PRIMA_SOLES,#{cuentaPrimaSoles}) AND"
			+ " TABLA.CUENTA_DESCUENTO_SOLES=DECODE(#{cuentaDescuentoSoles},0,TABLA.CUENTA_DESCUENTO_SOLES,#{cuentaDescuentoSoles}) AND"
			+ " TABLA.CUENTA_SALDO_DOLARES=DECODE(#{cuentaSaldoDolares},0,TABLA.CUENTA_SALDO_DOLARES,#{cuentaSaldoDolares}) AND "
			+ " TABLA.CUENTA_PRIMA_DOLARES=DECODE(#{cuentaPrimaDolares},0,TABLA.CUENTA_PRIMA_DOLARES,#{cuentaPrimaDolares}) AND "
			+ " TABLA.CUENTA_DESCUENTO_DOLARES=DECODE(#{cuentaDescuentoDolares},0,TABLA.CUENTA_DESCUENTO_DOLARES,#{cuentaDescuentoDolares}) AND"
			+ " TABLA.TIPO_CUENTA LIKE CONCAT('%',CONCAT(DECODE(#{detalleCuenta},'T',TABLA.TIPO_CUENTA,#{detalleCuenta}),'%')) ";

	@Select(SELECT_PROCESO_PARAMETRO)
	@ResultMap(value = "BaseResultMapProceso")
	List<ProcesoParametro> getListaProcesosParametros(
			@Param("codTipoContrato") String codTipoContrato,
			@Param("codTipoSeguro") String codTipoSeguro,
			@Param("codTipoReaseguro") String codTipoReaseguro,
			@Param("codTipoMovimiento") String codTipoMovimiento,
			@Param("codEstado") String codEstado,
			@Param("codEmpresa") Integer codEmpresa,
			@Param("codSocio") Integer codSocio,
			@Param("codProducto") Integer codProducto,
			@Param("cuentaSaldoSoles") Long cuentaSaldoSoles,
			@Param("cuentaPrimaSoles") Long cuentaPrimaSoles,
			@Param("cuentaDescuentoSoles") Long cuentaDescuentoSoles,
			@Param("cuentaSaldoDolares") Long cuentaSaldoDolares,
			@Param("cuentaPrimaDolares") Long cuentaPrimaDolares,
			@Param("cuentaDescuentoDolares") Long cuentaDescuentoDolares,
			@Param("detalleCuenta") String detalleCuenta);

	public int insertSelective(Proceso record);

	
	Proceso selectByPrimaryKey(Long pk);

	int updateByPrimaryKey(Proceso record);

	final String CANTIDAD_CUENTAS = "SELECT  COUNT(1) from  PROCESO_PARAMETRO where (CUENTA_SALDO_SOLES=#{numCuenta} "
			+ " OR  CUENTA_PRIMA_SOLES=#{numCuenta} "
			+ " OR  CUENTA_DESCUENTO_SOLES=#{numCuenta} "
			+ " OR  CUENTA_SALDO_DOLARES=#{numCuenta} "
			+ " OR  CUENTA_PRIMA_DOLARES=#{numCuenta} "
			+ " OR  CUENTA_DESCUENTO_DOLARES=#{numCuenta})"
			+ " AND COD_PROCESO_PARAMETRO <> #{codParametro}"
			+ " AND COD_ESTADO='0601' ";

	@Select(CANTIDAD_CUENTAS)
	int cantidadCuentas(@Param("codParametro") Long codParametro,
			@Param("numCuenta") Long numCuenta);
	
	final String CANTIDAD_PROD_SOCIO = "SELECT COUNT(1) FROM PRODUCTO_SOCIO WHERE COD_PRODUCTO=#{codProducto}"
			+ " AND COD_SOCIO=#{codSocio}" + " AND ESTADO=1";

	@Select(CANTIDAD_PROD_SOCIO)
	int cantProductoSocio(@Param("codProducto") Long codProducto,
			@Param("codSocio") Long codSocio);

	public int insertProductoSocio(ProductoSocio productoSocio);
	
	final String CANTIDAD_RIESGO="select  COUNT(1) from  parametro where COD_PARAMETRO like'%17%' AND COD_PARAMETRO<>'17' AND  UPPER(COD_VALOR)=#{codRiesgo}";
	
	@Select(CANTIDAD_RIESGO)
	int cantRiesgo(@Param("codRiesgo") String codRiesgo);

}
