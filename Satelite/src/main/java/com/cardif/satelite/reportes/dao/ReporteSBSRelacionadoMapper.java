package com.cardif.satelite.reportes.dao;

import com.cardif.satelite.model.reportes.ReporteSBSRelacionado;

public interface ReporteSBSRelacionadoMapper
{
	
	public ReporteSBSRelacionado selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int insertSelective(ReporteSBSRelacionado record);
	
} //ReporteSBSRelacionadoMapper
