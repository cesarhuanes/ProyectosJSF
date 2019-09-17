package com.cardif.satelite.suscripcion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.model.Layout;
import com.cardif.satelite.suscripcion.bean.TramaDiariaBean;
import com.cardif.satelite.util.SateliteConstants;

public interface LayoutMapper {

final String INSERT ="INSERT INTO DETALLE_TRAMA_LAYOUT DETALLE_TRAMA_LAYOUT (ID_PRODUCTO, COLUMNA_EXCEL, TIPO_DATA,OBLIGATORIO, ESTADO,DESCRIPCION_COLUMNA) VALUES (#{idcanal},#{columnaExcel},#{tipodata} ,#{obligatorio},"+ SateliteConstants.ESTADO_REGISTRO_ACTIVO +",#{descripcion})";
final String INSERT_EXCEL = " INSERT INTO DETALLE_TRAMA_LAYOUT (ID_PRODUCTO,COLUMNA_EXCEL,COLUMNA_TABLA,ESTADO,TIPO_DATA,OBLIGATORIO,FECHA_CREACION,USUARIO_CREACION,POSICION,DESCRIPCION_COLUMNA,FORMATO) "
						+ " VALUES (#{idcanal}, #{columnaExcel}, #{columnaTabla}, "+ SateliteConstants.ESTADO_REGISTRO_ACTIVO +", #{tipodata}, #{obligatorio}, #{fechaCreacion}, #{usuarioCreacion},#{posicion},#{descripcion},#{formato}) ";
//final String SELECT_TRAMA_DIARIA ="SELECT CNAME,WIDTH,NULLS,COLTYPE,COMMENTS FROM col WHERE tname = 'DETALLE_TRAMA_DIARIA'";

final String SELECT_TRAMA_DIARIA =" SELECT columna.CNAME, columna.WIDTH, columna.NULLS, columna.COLTYPE, ucc.COMMENTS FROM "
								+ " col  columna  inner join user_col_comments ucc ON(columna.CNAME = ucc.COLUMN_NAME ) "
								+ " WHERE columna.tname = 'DETALLE_TRAMA_DIARIA' and ucc.table_name = 'DETALLE_TRAMA_DIARIA' and ucc.COMMENTS NOT IN('"+SateliteConstants.FLAG_DE_MOSTRAR+"')";
final String SELECT_TRAMA_DIARIA_TMP = "SELECT columna.CNAME FROM col columna where columna.tname='DETALLE_TRAMA_DIARIA_TEMPORAL'";

final String LISTA_LAYOUT_BY_PRODUCTO ="SELECT ID, ID_PRODUCTO, COLUMNA_EXCEL, COLUMNA_TABLA, ESTADO, TIPO_DATA, "+
		  "(CASE OBLIGATORIO    WHEN "+ SateliteConstants.ESTADO_REGISTRO_INACTIVO+" THEN 'NO' "+ 
		                    "  WHEN "+ SateliteConstants.ESTADO_REGISTRO_ACTIVO+"   THEN 'SI' END) OBLIGATORIO_DESC,"+
		                    		 "POSICION,DESCRIPCION_COLUMNA,FORMATO FROM DETALLE_TRAMA_LAYOUT WHERE "+
		                    		 " ID_PRODUCTO = #{idproducto,jdbcType=NUMERIC} AND ESTADO ="+ SateliteConstants.ESTADO_REGISTRO_ACTIVO + "ORDER BY ID ASC"; 


final String UPDATE_LAYOUT ="UPDATE DETALLE_TRAMA_LAYOUT SET  ESTADO  = "+ SateliteConstants.ESTADO_REGISTRO_INACTIVO+" WHERE ID = #{id} ";
final String UPDATE_COLUMNAS = "UPDATE DETALLE_TRAMA_LAYOUT  SET COLUMNA_TABLA = #{columnaTabla} WHERE COLUMNA_EXCEL = #{columnaExcel}  AND ID_PRODUCTO =  #{idcanal}  ";

public List<Layout> selectAllLayout();

@Select(SELECT_TRAMA_DIARIA)
@ResultMap(value = "tramaDiaria")
public List<TramaDiariaBean> SelectTramaDiaria();

@Select(SELECT_TRAMA_DIARIA_TMP)
@ResultMap(value = "tramaDiaria")
public List<TramaDiariaBean> SelectTramaDiariaTemp();

@Select(LISTA_LAYOUT_BY_PRODUCTO)
@ResultMap(value = "BaseResultMap")
public List<Layout> listaLayoutByIdproducto(@Param("idproducto") Integer idproducto);

public int insertSelective(Layout layout);

int updateLayout(Layout layout);

public int insert(Layout objleyout);

@Insert(INSERT)
public int insertLayout(Layout objleyout);

@Insert(INSERT_EXCEL)
public int insertLayoutExcel(Layout layout);

@Update(UPDATE_LAYOUT)
public void Eliminar(@Param("id") Long pk);

@Update(UPDATE_COLUMNAS)
public void UpdateByLayoutNombreColumna(Layout layout);

}
