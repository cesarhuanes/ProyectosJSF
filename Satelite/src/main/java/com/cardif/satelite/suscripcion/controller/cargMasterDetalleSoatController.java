package com.cardif.satelite.suscripcion.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.DepartamentoService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.CategoriaClase;
import com.cardif.satelite.model.Departamento;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.suscripcion.bean.ConsultaMasterPrecio;
import com.cardif.satelite.suscripcion.service.CargMasterSoatService;

@Controller("cargMasterDetalleSoatController")
@Scope("request")
public class cargMasterDetalleSoatController  extends BaseController   {

	public static final Logger logger = Logger.getLogger(cargMasterDetalleSoatController.class);

	private List<ConsultaMasterPrecio> listaMasterPrecio;
	private List<SelectItem> tipoSocioItems;
	private List<SelectItem> tipoLocalidadItems;
	private List<SelectItem> tipoUsoItems;
	private List<SelectItem> tipoVehiculoItems;
	private List<SelectItem> tipoCanalItems;
	private String socio;
	private String departamento;
	private String uso;
	private String categoria;
	private String canalVenta;
	
	@Autowired
	CargMasterSoatService cargMasterSoatService = null;
	
	@Autowired
	DepartamentoService departamentoService = null;
	
	@Autowired
	ParametroService parametroService = null;
	
	
	@PostConstruct
	@Override
	public String inicio() {
		
		if(logger.isInfoEnabled()){logger.info("Inicio");}
		
		String respuesta = null;
		
		try {					
			if(!tieneAcceso())
			{
				if(logger.isDebugEnabled()){logger.debug("No cuenta con los accesos necesarios.");}
				return "accesoDenegado";
			}
			
			 listaMasterPrecio = new ArrayList<ConsultaMasterPrecio>();			 
			 
			 tipoSocioItems = new ArrayList<SelectItem>();
			 tipoSocioItems.add(new SelectItem("", "- Seleccione -"));
			 List<Socio> lstSocio = new ArrayList<Socio>();
			 lstSocio = cargMasterSoatService.getListaSocios();
				 for (Socio socio : lstSocio) {
					 tipoSocioItems.add(new  SelectItem(socio.getId(),socio.getNombreSocio()));
				 }
				 
			 tipoLocalidadItems = new ArrayList<SelectItem>();
			 tipoLocalidadItems.add(new SelectItem("","- Seleccione -"));
			 List<Departamento> lstDepartamento = departamentoService.buscarDepartamentos();
			 	for (Departamento departamento : lstDepartamento) {
			 		tipoLocalidadItems.add(new SelectItem(departamento.getCodDepartamento(), departamento.getNombreDepartamento()));
				}
			 	
			 tipoUsoItems = new ArrayList<SelectItem>();
			 tipoUsoItems.add(new SelectItem("","- Seleccione -"));
			  List<Parametro> listaUso = parametroService.buscar(Constantes.COD_PARAM_USO_SOAT, Constantes.TIP_PARAM_DETALLE);
			  	for (Parametro parametro : listaUso) {
			  		tipoUsoItems.add(new SelectItem(parametro.getCodValor() , parametro.getNomValor()));
				}	
			  	
			 tipoVehiculoItems = new ArrayList<SelectItem>();
			 tipoVehiculoItems.add(new SelectItem("","- Seleccione -"));
			 List<CategoriaClase> lstCategoria = cargMasterSoatService.getListCategoriaClase();
			 	for (CategoriaClase categoriaClase : lstCategoria) {
			 		tipoVehiculoItems.add(new SelectItem(categoriaClase.getCodCategoriaClase(),categoriaClase.getDescripcionCategoriaClase()));					 
				}
			 	
			 tipoCanalItems = new ArrayList<SelectItem>();
			 tipoCanalItems.add(new SelectItem("","- Seleccione -"));
			 List<CanalProducto> lstCanalProducto	= cargMasterSoatService.getListCanales();
			 	for (CanalProducto canalProducto : lstCanalProducto) {
			 		tipoCanalItems.add(new SelectItem(canalProducto.getId(), canalProducto.getNombreCanal()));
				}
			 
						
		} catch (Exception e) {

			logger.error("Exception(" + e.getClass().getName()+ ") - ERROR"+ ErrorConstants.ERROR_SYNCCON,null);
			logger.error("Exception(" + e.getStackTrace() + ")"); 			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.ERROR_SYNCCON, null);
			FacesContext.getCurrentInstance().addMessage(null,facesMsg);
			
		}
		
		if(logger.isInfoEnabled()){logger.info("Fin");}		
		// TODO Auto-generated method stub
		return respuesta;
	}
	
	public void buscarMaster(){
		if(logger.isInfoEnabled()){logger.info("Inicio");}		
		try {
		
			if(logger.isDebugEnabled()){logger.info("Input [ 1.-"+socio+"  2.-"+departamento+"  3.-"+uso+" 4.-"+categoria+" 5.-"+canalVenta+" ]");} 
			listaMasterPrecio = new ArrayList<ConsultaMasterPrecio>();						
			listaMasterPrecio = cargMasterSoatService.listar(socio, departamento, uso, categoria, canalVenta);
			if(logger.isDebugEnabled()){logger.info("Output [ registro "+ listaMasterPrecio.size() +"]");}
		
		} catch (SyncconException e) {
			
		}
		
		if(logger.isInfoEnabled()){logger.info("Fin");}
	}
	
	public void exportar(){
		if(logger.isInfoEnabled()){logger.info("Inicio");}

			try {
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		if(logger.isInfoEnabled()){logger.info("Fin");}		
	}
	



	public List<ConsultaMasterPrecio> getListaMasterPrecio() {
		return listaMasterPrecio;
	}

	public void setListaMasterPrecio(List<ConsultaMasterPrecio> listaMasterPrecio) {
		this.listaMasterPrecio = listaMasterPrecio;
	}

	public List<SelectItem> getTipoSocioItems() {
		return tipoSocioItems;
	}

	public void setTipoSocioItems(List<SelectItem> tipoSocioItems) {
		this.tipoSocioItems = tipoSocioItems;
	}

	public List<SelectItem> getTipoLocalidadItems() {
		return tipoLocalidadItems;
	}

	public void setTipoLocalidadItems(List<SelectItem> tipoLocalidadItems) {
		this.tipoLocalidadItems = tipoLocalidadItems;
	}

	public List<SelectItem> getTipoUsoItems() {
		return tipoUsoItems;
	}

	public void setTipoUsoItems(List<SelectItem> tipoUsoItems) {
		this.tipoUsoItems = tipoUsoItems;
	}

	public List<SelectItem> getTipoVehiculoItems() {
		return tipoVehiculoItems;
	}

	public void setTipoVehiculoItems(List<SelectItem> tipoVehiculoItems) {
		this.tipoVehiculoItems = tipoVehiculoItems;
	}

	public List<SelectItem> getTipoCanalItems() {
		return tipoCanalItems;
	}

	public void setTipoCanalItems(List<SelectItem> tipoCanalItems) {
		this.tipoCanalItems = tipoCanalItems;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(String canalVenta) {
		this.canalVenta = canalVenta;
	}
	
	
	
	
}
