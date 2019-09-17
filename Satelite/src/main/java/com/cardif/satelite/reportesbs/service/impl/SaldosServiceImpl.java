package com.cardif.satelite.reportesbs.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.satelite.model.reportesbs.Saldos;
import com.cardif.satelite.reportesbs.dao.SaldosMapper;
import com.cardif.satelite.reportesbs.service.SaldosService;

@Service("saldosService")
public class SaldosServiceImpl implements SaldosService{

	public static final Logger log = Logger.getLogger(SaldosServiceImpl.class);
	
	@Autowired
	private SaldosMapper saldosMapper;
	
	@Override
	public Saldos listaSaldosByParam(String monedaCuenta,
			String monedaModeloValor, Long codigoEmpresa, String tipoReaseguro) {
		  log.info("Inicio");
		  
		  Saldos saldosBean = null;		    		    
		  List<Saldos> objListSaldos=null;
		    
		    try {
		    	
		      if (log.isDebugEnabled())
		      log.debug("Input [" + monedaCuenta + "]");
		      log.debug("Input [" + monedaModeloValor + "]");
		      log.debug("Input [" + codigoEmpresa + "]");
		      log.debug("Input [" + tipoReaseguro + "]");
		      
		      objListSaldos = saldosMapper.selectSaldosByParam(monedaCuenta,monedaModeloValor,codigoEmpresa,tipoReaseguro.toUpperCase());
		     
		      for (int i = 0; i < objListSaldos.size(); i++) {
				
		    	  saldosBean = new Saldos();		 
		    	  
		    	  saldosBean.setPrimaCobrarReaAceptado(objListSaldos.get(0).getPrimaCobrarReaAceptado());
		    	  saldosBean.setPrimaPagarReaCedido(objListSaldos.get(0).getPrimaPagarReaCedido());
		    	  saldosBean.setSiniestroCobrarReaAceptado(objListSaldos.get(0).getSiniestroCobrarReaAceptado());
		    	  saldosBean.setSiniestroPagarReaCedido(objListSaldos.get(0).getSiniestroPagarReaCedido());
		    	  saldosBean.setOtrasCobrarReaAceptado(objListSaldos.get(0).getOtrasCobrarReaAceptado());
		    	  saldosBean.setOtrasCobrarReaCedidos(objListSaldos.get(0).getOtrasCobrarReaCedidos());
		    	  saldosBean.setDescuentosComisiones(objListSaldos.get(0).getDescuentosComisiones());
		      
		      }
		      
		      if (log.isDebugEnabled())
		        log.debug("Output [" + BeanUtils.describe(saldosBean) + "]");
		      
		    } catch (Exception e) {
		    	
		    	saldosBean = null;
		    	log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
				log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
				
		    }
		    log.info("Fin");
		    return saldosBean;
	}

}
