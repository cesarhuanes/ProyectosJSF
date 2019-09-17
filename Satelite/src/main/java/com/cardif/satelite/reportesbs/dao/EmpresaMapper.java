package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Empresa;

public interface EmpresaMapper {
	final String SELECT_EMPRESA = "SELECT COD_EMPRESA,DESCRIPCION FROM  EMPRESA WHERE ESTADO=1 order by descripcion asc";

	@Select(SELECT_EMPRESA)
	@ResultMap(value = "BaseResultMapEmpresa")
	List<Empresa> getListaEmpresa();

	final String SELECT_CODSBS_BUSCA = "SELECT COD_SBS FROM EMPRESA WHERE COD_EMPRESA = #{codEmpresa} ";

	@Select(SELECT_CODSBS_BUSCA)
	@ResultMap(value = "BaseResultMapEmpresa")
	List<Empresa> selectCodSbs(@Param("codEmpresa") Long codEmpresa);

}
