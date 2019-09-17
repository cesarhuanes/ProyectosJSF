package com.cardif.satelite.reportesbs.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface EjecutaArchivoMapper {

	final String SP_JOB = "{ CALL SP_EJECUTAR_JOB( #{nombreJob} ) } ";

	@Select(SP_JOB)
	@Options(statementType = StatementType.CALLABLE)
	void ejecutarJob(@Param("nombreJob") String nombreJob);
	
	final String EVALUA_EJECUCCION_JOB="select  j.name "
			+" from  msdb.dbo.sysjobs  j, msdb.dbo.sysjobactivity  ja"
			+" where j.job_id = ja.job_id "
			+" and ja.start_execution_date IS NOT NULL " 
			+" AND ja.stop_execution_date IS NULL "
			+" and j.name =#{nombreJob} ";
 
	@Select(EVALUA_EJECUCCION_JOB)
	String resultadoJobRunnable(@Param("nombreJob") String nombreJob);

}
