package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.suscripcion.bean.SocioProductoBean;
import com.cardif.satelite.suscripcion.bean.TipoArchivoBean;

public interface ProductoMapper
{
	final String SELECT_PRODUCTOS_BY_ESTADO = "SELECT ID, ID_SOCIO, NOMBRE_PRODUCTO, TRAMA_DIARIA, TRAMA_MENSUAL, TIPO_ARCHIVO, MOD_SUSCRIPCION, MOD_IMPRESION, ESTADO , SEPARADOR " +
			"FROM PRODUCTO WHERE ESTADO=#{estadoProducto,jdbcType=NUMERIC} ORDER BY NOMBRE_PRODUCTO ASC";
	
	final String LISTA_PRODUCTO_BY_IDSOCIO ="SELECT ID, ID_SOCIO, NOMBRE_PRODUCTO, TRAMA_DIARIA, TRAMA_MENSUAL, TIPO_ARCHIVO, MOD_SUSCRIPCION, MOD_IMPRESION, ESTADO, SEPARADOR,"
			+ "  (SELECT NOMBRE_CANAL FROM CANAL_PRODUCTO WHERE ID = ID_CANAL) AS NOMBRE_CANAL, ID_CANAL FROM PRODUCTO WHERE ID_SOCIO = #{idsocio,jdbcType=NUMERIC} " 
			+ " AND  ( UPPER(NOMBRE_PRODUCTO) LIKE UPPER( '%' || #{producto,jdbcType=VARCHAR} || '%'))  ORDER BY ID DESC ";
	
	final String SELECT_SOCIOyPRODUCTO ="SELECT ID_SOCIO,NOMBRE_SOCIO,p.ID,NOMBRE_PRODUCTO,TRAMA_DIARIA,TRAMA_MENSUAL,TIPO_ARCHIVO ,SEPARADOR FROM SOCIO s "
			+ "inner join PRODUCTO p on(s.ID = p.ID_SOCIO) WHERE P.ID_CANAL = (NVL(#{idcanal,jdbcType=NUMERIC}, P.ID_CANAL))";
	

	final String SELECT_SOCIOyPRODUCTO_DIARIAS ="SELECT DISTINCT ID_SOCIO,NOMBRE_SOCIO,p.ID,NOMBRE_PRODUCTO,TRAMA_DIARIA,TRAMA_MENSUAL,TIPO_ARCHIVO ,SEPARADOR FROM SOCIO s "
			+ "inner join PRODUCTO p on(s.ID = p.ID_SOCIO) inner join DETALLE_TRAMA_LAYOUT l on (p.id= l.id_producto) WHERE P.ID_CANAL = (NVL(#{idcanal,jdbcType=NUMERIC}, P.ID_CANAL)) AND p.TRAMA_DIARIA = " + Constantes.PRODUCTO_MOD_TRAMA_DIARIA_SI
			+ " AND p.estado = "+ Constantes.PRODUCTO_ESTADO_ACTIVO;
	
	final String SELECT_SOCIOyPRODUCTO_MENSUAL ="SELECT DISTINCT ID_SOCIO,NOMBRE_SOCIO,p.ID,NOMBRE_PRODUCTO,TRAMA_DIARIA,TRAMA_MENSUAL,TIPO_ARCHIVO ,SEPARADOR FROM SOCIO s "
			+ "inner join PRODUCTO p on(s.ID = p.ID_SOCIO) inner join DETALLE_TRAMA_LAYOUT l on (p.id= l.id_producto) WHERE P.ID_CANAL = (NVL(#{idcanal,jdbcType=NUMERIC}, P.ID_CANAL))  AND p.TRAMA_MENSUAL =" + Constantes.PRODUCTO_MOD_TRAMA_MENSUAL_SI
			+ " AND p.estado = "+ Constantes.PRODUCTO_ESTADO_ACTIVO;
	
	
	final String SELECT_FOR_MOD_IMPRESION = "SELECT PRO.ID, PRO.ID_SOCIO, PRO.NOMBRE_PRODUCTO, SOC.NOMBRE_SOCIO, PRO.TRAMA_DIARIA, PRO.TRAMA_MENSUAL, PRO.TIPO_ARCHIVO, PRO.MOD_SUSCRIPCION, PRO.MOD_IMPRESION, PRO.ESTADO " +
			"FROM PRODUCTO PRO LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID WHERE PRO.MOD_IMPRESION=#{opcionModImpresion,jdbcType=NUMERIC} AND PRO.ESTADO=" + Constantes.PRODUCTO_ESTADO_ACTIVO + " ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO ASC";
	
	final String SELECT_TIPO_ARCHIVOS = "SELECT DISTINCT TIPO_ARCHIVO FROM PRODUCTO WHERE TIPO_ARCHIVO<>'" + Constantes.PRODUCTO_TIPO_ARCHIVO_BD + "' ORDER BY TIPO_ARCHIVO ASC";
	
	final String SELECT_BY_MODSUSCRIPCION_ESTADO_CANAL = "SELECT PRO.ID, PRO.ID_SOCIO, PRO.NOMBRE_PRODUCTO, SOC.NOMBRE_SOCIO, PRO.TRAMA_DIARIA, PRO.TRAMA_MENSUAL, PRO.TIPO_ARCHIVO, PRO.MOD_SUSCRIPCION, PRO.MOD_IMPRESION, PRO.ESTADO " +
			"FROM PRODUCTO PRO LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID	LEFT JOIN CANAL_PRODUCTO CAN ON PRO.ID_CANAL = CAN.ID " +
			"WHERE (PRO.MOD_SUSCRIPCION = NVL(#{modSuscripcion,jdbcType=NUMERIC}, PRO.MOD_SUSCRIPCION)) AND (PRO.ID_CANAL = NVL(#{idCanal,jdbcType=NUMERIC}, PRO.ID_CANAL)) " +
			"AND (PRO.ESTADO = NVL(#{estado,jdbcType=NUMERIC}, PRO.ESTADO)) ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO ASC";
	
	final String SELECT_BY_MODIMPRESION_ESTADO_CANAL = "SELECT PRO.ID, PRO.ID_SOCIO, PRO.NOMBRE_PRODUCTO, SOC.NOMBRE_SOCIO, PRO.TRAMA_DIARIA, PRO.TRAMA_MENSUAL, PRO.TIPO_ARCHIVO, PRO.MOD_SUSCRIPCION, PRO.MOD_IMPRESION, PRO.ESTADO " +
			"FROM PRODUCTO PRO LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID	LEFT JOIN CANAL_PRODUCTO CAN ON PRO.ID_CANAL = CAN.ID " +
			"WHERE (PRO.MOD_IMPRESION = NVL(#{modImpresion,jdbcType=NUMERIC}, PRO.MOD_IMPRESION)) AND (PRO.ID_CANAL = NVL(#{idCanal,jdbcType=NUMERIC}, PRO.ID_CANAL)) " +
			"AND (PRO.ESTADO = NVL(#{estado,jdbcType=NUMERIC}, PRO.ESTADO)) ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO ASC";
	

	final String SELECT_PRODUCTO_BY_ID ="SELECT ID, ID_SOCIO, NOMBRE_PRODUCTO,TRAMA_DIARIA, TRAMA_MENSUAL,  TIPO_ARCHIVO,  MOD_SUSCRIPCION,  MOD_IMPRESION,  ESTADO,  ID_CANAL,  SEPARADOR,  FECHA_CREACION, FECHA_MODIFICACION,  USUARIO_CREACION, USUARIO_MODIFICACION FROM PRODUCTO  WHERE ID = #{producto}";

	final String SELECT_BY_PK_DETALLE_TRAMA_DIARIA = "SELECT PRO.ID, PRO.ID_SOCIO, UPPER(PRO.NOMBRE_PRODUCTO) AS NOMBRE_PRODUCTO, UPPER(SOC.NOMBRE_SOCIO) AS NOMBRE_SOCIO, " +
			"PRO.TRAMA_DIARIA, PRO.TRAMA_MENSUAL, PRO.TIPO_ARCHIVO, PRO.MOD_SUSCRIPCION, PRO.MOD_IMPRESION, PRO.ESTADO " +
			"FROM PRODUCTO PRO LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID LEFT JOIN TRAMA_DIARIA TD ON PRO.ID = TD.PRODUCTO LEFT JOIN DETALLE_TRAMA_DIARIA DTD ON TD.SEC_ARCHIVO = DTD.SEC_ARCHIVO " +
			"WHERE DTD.ID = #{pkDetalleTramaDiaria, jdbcType=NUMERIC} AND (ROWNUM = 1) ORDER BY PRO.ID ASC";

	final String SELECT_BY_MODIMPRESION_CANAL = "SELECT PRO.ID, PRO.ID_SOCIO, PRO.NOMBRE_PRODUCTO, SOC.NOMBRE_SOCIO, PRO.TRAMA_DIARIA, PRO.TRAMA_MENSUAL, PRO.TIPO_ARCHIVO, PRO.MOD_SUSCRIPCION, PRO.MOD_IMPRESION, PRO.ESTADO " +
			"FROM PRODUCTO PRO LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID LEFT JOIN CANAL_PRODUCTO CAN ON PRO.ID_CANAL = CAN.ID " +
			"WHERE (PRO.MOD_IMPRESION = NVL(#{modImpresion,jdbcType=NUMERIC}, PRO.MOD_IMPRESION)) AND (CAN.NOMBRE_CANAL = NVL(#{nombreCanal,jdbcType=VARCHAR}, CAN.NOMBRE_CANAL)) " +
			"AND PRO.ESTADO=" + Constantes.PRODUCTO_ESTADO_ACTIVO + " ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO ASC";
	
	
	final String SELECT_FOR_ESTADO = "SELECT PRO.ID, PRO.ID_SOCIO, PRO.NOMBRE_PRODUCTO, SOC.NOMBRE_SOCIO, PRO.TRAMA_DIARIA, PRO.TRAMA_MENSUAL, PRO.TIPO_ARCHIVO, PRO.MOD_SUSCRIPCION, PRO.MOD_IMPRESION, PRO.ESTADO " + 
			"FROM PRODUCTO PRO LEFT JOIN SOCIO SOC ON PRO.ID_SOCIO = SOC.ID WHERE (PRO.ESTADO = #{estado,jdbcType=VARCHAR}) ORDER BY SOC.NOMBRE_SOCIO, PRO.NOMBRE_PRODUCTO ASC";
	
	final String SELECT_VALIDA_OBLIGACION_REG_FECHA_VENTA_BY_PRODUCTO = "SELECT ID, INDICADOR_REGVENTA, NOMBRE_PRODUCTO FROM PRODUCTO " +
																	    "WHERE ID = #{idProducto,jdbcType=NUMERIC} ";	
	
	final String SELECT_ARCHIVO_PRODUCTO_URL ="SELECT * FROM TRAMA_DIARIA WHERE SEC_ARCHIVO =(SELECT MAX(sec_archivo) FROM trama_diaria WHERE producto =NVL(#{codProducto,jdbcType=VARCHAR}, producto) AND estado NOT IN(0) AND ruta_archivo IS NULL) AND flag_trama = #{flag_canal,jdbcType=VARCHAR}";
	
	public Producto selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int insert(Producto record);
	
	public int insertSelective(Producto record);
	
	public int updateByPrimaryKeySelective(Producto record);
	
	public int updateByPrimaryKey(Producto record);
	
	@Select(SELECT_PRODUCTOS_BY_ESTADO)
	@ResultMap("BaseResultMap")
	public List<Producto> selectProductosByEstado(@Param("estadoProducto") Integer estadoProducto);
	
	@Select(SELECT_FOR_MOD_IMPRESION)
	@ResultMap("ConsultaProductoSocioResultMap")
	public List<ConsultaProductoSocio> selectForModImpresion(@Param("opcionModImpresion") Integer opcionModImpresion);
	
	@Select(SELECT_TIPO_ARCHIVOS)
	@ResultMap("TipoArchivoResultMap")
	public List<TipoArchivoBean> selectTipoArchivos();
	
	@Select(SELECT_BY_MODSUSCRIPCION_ESTADO_CANAL)
	@ResultMap("ConsultaProductoSocioResultMap")
	public List<ConsultaProductoSocio> selectByModSuscripcionAndEstadoAndCanal(@Param("modSuscripcion") Integer modSuscripcion, @Param("idCanal") Long idCanal, @Param("estado") Integer estado);
	
	@Select(SELECT_BY_MODIMPRESION_ESTADO_CANAL)
	@ResultMap("ConsultaProductoSocioResultMap")
	public List<ConsultaProductoSocio> selectByModImpresionAndEstadoAndCanal(@Param("modImpresion") Integer modImpresion, @Param("idCanal") Long idCanal, @Param("estado") Integer estado);
	
	@Select(SELECT_SOCIOyPRODUCTO)
	@ResultMap(value = "JoinResultMap")
	public List<SocioProductoBean> listaSocioProducto(@Param("idcanal") Integer idcanal);
	
	@Select(SELECT_SOCIOyPRODUCTO_DIARIAS)
	@ResultMap(value = "JoinResultMap")
	public List<SocioProductoBean> listaSocioProductoDiarias(@Param("idcanal") Integer idcanal);
	
	
	@Select(SELECT_SOCIOyPRODUCTO_MENSUAL)
	@ResultMap(value = "JoinResultMap")
	public List<SocioProductoBean> listaSocioProductoMensuales(@Param("idcanal") Integer idcanal);
	
	@Select(SELECT_PRODUCTO_BY_ID)
	@ResultMap(value = "BaseResultMap")
	public Producto selectProductoByID(@Param("producto") int producto);
	
	
	@Select(LISTA_PRODUCTO_BY_IDSOCIO)
	@ResultMap(value = "BaseResultMap")
	public List<Producto> listaProductoByIdsocio(@Param("idsocio") Integer idsocio, @Param("producto") String producto);
	
	@Select(SELECT_BY_PK_DETALLE_TRAMA_DIARIA)
	@ResultMap("ConsultaProductoSocioResultMap")
	public ConsultaProductoSocio selectByPkDetalleTramaDiaria(@Param("pkDetalleTramaDiaria") Long pkDetalleTramaDiaria);
	
	@Select(SELECT_BY_MODIMPRESION_CANAL)
	@ResultMap("ConsultaProductoSocioResultMap")
	public List<ConsultaProductoSocio> selectForModImpresionAndCanal(@Param("modImpresion") Integer modImpresion, @Param("nombreCanal") String nombreCanal);
	
	@Select(SELECT_FOR_ESTADO)
	@ResultMap("ConsultaProductoSocioResultMap")
	public List<ConsultaProductoSocio> selectForEstado(@Param("estado") Integer estado);
	
	@Select(SELECT_VALIDA_OBLIGACION_REG_FECHA_VENTA_BY_PRODUCTO)
	@ResultMap("ConsultaProductoSocioResultMap")
	public ConsultaProductoSocio selectIndicadorRegFechaVentaByProducto(@Param("idProducto") Long idProducto);

	@Select(SELECT_ARCHIVO_PRODUCTO_URL)
	public String selectArchivoUrl(@Param("codProducto") String codProducto,@Param("flag_canal") String flag_canal);
	
} //ProductoMapper
