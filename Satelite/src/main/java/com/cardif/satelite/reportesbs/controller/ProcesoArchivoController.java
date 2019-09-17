package com.cardif.satelite.reportesbs.controller;

import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.reportesbs.CargaOperaciones;
import com.cardif.satelite.model.reportesbs.LogError;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.ProcesoArchivo;
import com.cardif.satelite.model.reportesbs.ProcesoArchivoTransaccional;
import com.cardif.satelite.reportesbs.service.ProcesoArchivoService;
import com.cardif.satelite.reportesbs.service.RepParametroService;
import com.cardif.satelite.util.SateliteUtil;

@Controller("procesoArchivoController")
@Scope("request")
public class ProcesoArchivoController extends BaseController implements
		Runnable {

	public static final Logger logger = Logger
			.getLogger(ProcesoArchivoController.class);

	@Autowired
	private ProcesoArchivoService procesoArchivoService;

	@Autowired
	private RepParametroService repParametroService;

	private Long codProceso = null;
	private String nomArchivo;
	private String codEstado;
	private String usuarioProceso;
	private Date fechaCargaDesde;
	private Date fechaCargaHasta;
	private Date fechaProcesoDesde;
	private Date fechaProcesoHasta;
	private Long codArchivo;
	private String desArchivo;
	private String estadoArchivo;

	private List<ProcesoArchivo> listaProcesoArchivo;
	private List<CargaOperaciones> listaProcesoCarga;
	private List<LogError> listaLogError;
	private List<SelectItem> itemsEstados;

	private ProcesoArchivo procesoArchivo;
	private CargaOperaciones cargaOperaciones;
	private ProcesoArchivoTransaccional procesoTransaccional;
	private SimpleSelection selection;
	boolean flagEjecuccion = false;

	@Override
	@PostConstruct
	public String inicio() {
		String respuesta = "";
		selection = new SimpleSelection();
		listaProcesoArchivo = new ArrayList<ProcesoArchivo>();
		procesoArchivo = new ProcesoArchivo();
		itemsEstados = new ArrayList<SelectItem>();

		try {
			itemsEstados = llenarListaEstados();
		} catch (SyncconException e) {
			log.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		}
		return respuesta;
	}

	private List<SelectItem> llenarListaEstados() throws SyncconException {
		List<Parametro> listaEstados = repParametroService
				.listarTipoEstado(Constantes.TIPO_PROCESO);
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("T", "TODOS"));

		for (Parametro p : listaEstados) {
			items.add(new SelectItem(p.getCodParametro(), p.getDescripcion()));
		}
		return items;
	}

	public String buscaProcesoArchivo() {
		log.info("Buscando Archivos");
		String respuesta = null;
		selection = new SimpleSelection();
		listaProcesoArchivo = new ArrayList<ProcesoArchivo>();
		try {

			listaProcesoArchivo = procesoArchivoService.listaProcesoArchivo(
					codProceso, codEstado, nomArchivo, usuarioProceso,
					fechaCargaDesde, fechaCargaHasta, fechaProcesoDesde,
					fechaProcesoHasta);

		} catch (SyncconException e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		return respuesta;
	}

	public void actionPasarDatos(ActionEvent event) {
		Long codigoArchivo = (Long) event.getComponent().getAttributes()
				.get("codigoArchivo");
		String nombreArchivo = (String) event.getComponent().getAttributes()
				.get("nombreArchivo");
		String codEstado = (String) event.getComponent().getAttributes()
				.get("codEstado");

		this.setCodArchivo(codigoArchivo);
		this.setDesArchivo(nombreArchivo);
		this.setEstadoArchivo(codEstado);
		log.info("Codigo a procesar" + codigoArchivo);
		log.info("Nombre Archivo" + nombreArchivo);
		log.info("Codigo Estado" + codEstado);

	}

	public void actionCodigo(ActionEvent event) {
		Long codigoArchivo = (Long) event.getComponent().getAttributes()
				.get("codigoArchivo");
		this.setCodArchivo(codigoArchivo);
		log.info("Codigo seleccionado : " + codigoArchivo);
	}

	public String procesar() {

		String respuesta = null;
		if (log.isDebugEnabled()) {
			log.info("Inicio Procesar");
		}
		try {
			log.info("Codigo de Archivo :" + this.getCodArchivo());
			log.info("Nombre de proceso :" + this.getDesArchivo());
			log.info("Estado de proceso :" + this.getEstadoArchivo());

			procesoTransaccional = procesoArchivoService
					.buscaProcesoArhivo(this.getCodArchivo());// buscamos los
																// estados de
																// proceso
			if (procesoTransaccional.getEstado().equals(Constantes.PROCESADO)) {
				SateliteUtil
						.mostrarMensaje("El archivo seleccionado ya ha sido procesado.");
			} else if (procesoTransaccional.getEstado().equals(
					Constantes.PROCESADO_ERROR)) {
				SateliteUtil
						.mostrarMensaje("El archivo seleccionado no se puede procesar\n ya que presenta errores.");
			} else if (procesoTransaccional.getEstado().equals(
					Constantes.INACTIVADO)) {
				SateliteUtil
						.mostrarMensaje("No se puede procesar un archivo que esta inactivo.");
			} else {

				if (procesoTransaccional.getCodEstadoSolicitud().equals(
						Constantes.PROCESO_PENDIENTE)) {
					SateliteUtil
							.mostrarMensaje("Se envio a procesar su solicitud,por favor espere.");
					procesoTransaccional
							.setCodEstadoSolicitud(Constantes.PROCESO_SOLICITUD);
					procesoTransaccional
							.setUsuModificacion(SecurityContextHolder
									.getContext().getAuthentication().getName());
					procesoTransaccional.setFecModificacion(new Date(System
							.currentTimeMillis()));

					boolean flagEstadoSolicitud = procesoArchivoService
							.actualizaProcesoArchivo(procesoTransaccional);// actualizamos
																			// a
																			// estado
																			// S
					log.info("Estado de Solicitud :"
							+ procesoTransaccional.getCodEstadoSolicitud());
					log.info("Cantidad de Registros :"
							+ procesoTransaccional.getNumRegistros());
					if (flagEstadoSolicitud) {

						procesoTransaccional = procesoArchivoService
								.buscaProcesoArhivo(this.getCodArchivo());// buscamos
																			// los
																			// estados
																			// S
																			// de
																			// solicitud

						listaProcesoArchivo = procesoArchivoService
								.listaProcesoArchivo(codProceso, codEstado,
										nomArchivo, usuarioProceso,
										fechaCargaDesde, fechaCargaHasta,
										fechaProcesoDesde, fechaProcesoHasta);
						if (procesoTransaccional.getCodEstadoSolicitud()
								.equals(Constantes.PROCESO_SOLICITUD)) {
							new Thread(this, "ProcesoArchivoController")
									.start();

							procesoTransaccional = procesoArchivoService
									.buscaProcesoArhivo(this.getCodArchivo());// buscamos
																				// los
																				// estados
																				// de
																				// proceso

						}
					} else {
						SateliteUtil
								.mostrarMensaje("Error no se puedo actualizar el \n estado de solicitud de proceso");
					}

				} else {
					SateliteUtil
							.mostrarMensaje("Su solicitud de proceso se esta generando");
				}

			}

		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getStackTrace());
			}
		}

		if (log.isDebugEnabled()) {
			log.info("Final");
		}

		return respuesta;
	}

	public String descargarLog() {
		String respuesta = null;
		try {

			if (this.getEstadoArchivo().equals(Constantes.PROCESADO)) {
				SateliteUtil
						.mostrarMensaje("El archivo procesado no contiene log de errores");
			} else if (this.getEstadoArchivo().equals(Constantes.NOPROCESADO)) {
				SateliteUtil
						.mostrarMensaje("No se puede que generar log \n de un arhivo que no a sido procesado");
			} else if (this.getEstadoArchivo().equals(Constantes.INACTIVADO)) {
				SateliteUtil
						.mostrarMensaje("No se puede puede descargar log \n de un archivo que esta esta inactivo. ");
			} else {
				listaLogError = procesoArchivoService.listaLogError(this
						.getCodArchivo());
				for (LogError x : listaLogError) {
					log.info("Socio" + x.getSocio());
				}
				downloadFile(this.getCodArchivo(), listaLogError);
			}
		} catch (SyncconException e) {
			e.printStackTrace();
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String verDetalle() {
		String respuesta = null;
		cargaOperaciones = new CargaOperaciones();
		listaProcesoCarga = new ArrayList<CargaOperaciones>();
		try {
			listaProcesoCarga = procesoArchivoService
					.listaDetalleOperaciones(this.getCodArchivo());
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String eliminar() {
		String respuesta = null;
		boolean flagActualizar = false;
		boolean flagEliminar = false;
		procesoTransaccional = new ProcesoArchivoTransaccional();
		try {

			log.info("Codigo a eliminar" + this.getCodArchivo());
			procesoTransaccional = procesoArchivoService
					.buscaProcesoArhivo(this.getCodArchivo());
			if (procesoTransaccional.getEstado().equals(Constantes.NOPROCESADO)) {
				eliminarFichero(procesoTransaccional.getNomArchivoProcesado());

			}
			if (!procesoTransaccional.getEstado().equals(Constantes.INACTIVADO)) {

				procesoTransaccional
						.setEstado(Constantes.TIPO_PROCESO_ELIMINADO);
				procesoTransaccional.setUsuModificacion(SecurityContextHolder
						.getContext().getAuthentication().getName());
				procesoTransaccional.setFecModificacion(new Date(System
						.currentTimeMillis()));

				flagActualizar = procesoArchivoService
						.actualizaProcesoArchivo(procesoTransaccional);

				if (flagActualizar) {
					log.info("Cabecera Actualizada");
					flagEliminar = procesoArchivoService.eliminarDetalle(this
							.getCodArchivo());
					if (flagEliminar) {
						SateliteUtil.mostrarMensaje("Registro Eliminado");
						listaProcesoArchivo = procesoArchivoService
								.listaProcesoArchivo(codProceso, codEstado,
										nomArchivo, usuarioProceso,
										fechaCargaDesde, fechaCargaHasta,
										fechaProcesoDesde, fechaProcesoHasta);
					} else {
						SateliteUtil
								.mostrarMensaje("No se pudo eliminar el registro");
					}

				} else {
					SateliteUtil
							.mostrarMensaje("No se pudo inactivar el registro");
				}
			} else {
				SateliteUtil.mostrarMensaje("Ya se elimino el  registro");
			}

		} catch (SyncconException e) {
			e.printStackTrace();
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		return respuesta;
	}

	public String downloadFile(Long codArchivo, List<LogError> listaError) {

		try {

			String filename = codArchivo + "LOG_ERROR.csv";
			ExternalContext contexto = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) contexto
					.getResponse();

			response.reset();
			response.setContentType("text/comma-separated-values");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");
			ServletOutputStream output = response.getOutputStream();

			List<String> strings = new ArrayList<String>();

			strings.add("COD_ERROR" + "," + "COD_PROCESO_ARCHIVO" + ","
					+ "SOCIO" + "," + "ASEGURADO" + "," + "PRODUCTO" + ","
					+ "BO-PLANILLA" + "," + "NUM_POLIZA" + "," + "USU_CREADOR"
					+ "," + "FEC_CREACION" + "," + "DESCRIPCION ERROR" + "\n");
			for (LogError x : listaError) {
				strings.add(x.getCodLogError() + "," + x.getCodProcesoArchivo()
						+ "," + x.getSocio() + "," + x.getAsegurado() + ","
						+ x.getProducto() + "," + x.getBo_planilla() + ","
						+ x.getNumPolizaSiniestro() + "," + x.getUsuCreacion()
						+ "," + x.getFecCreacion() + ","
						+ x.getDescripcionError() + "\n");
			}
			for (String s : strings) {
				output.write(s.getBytes());
			}

			output.flush();
			output.close();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String limpiar() {
		String respuesta = "";
		try {
			codProceso = null;
			nomArchivo = "";
			codEstado = "";
			usuarioProceso = "";
			fechaCargaDesde = null;
			fechaCargaHasta = null;
			fechaProcesoDesde = null;
			fechaProcesoHasta = null;

			listaProcesoArchivo = new ArrayList<ProcesoArchivo>();
			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			ViewHandler viewHandler = application.getViewHandler();
			UIViewRoot viewRoot = viewHandler.createView(context, context
					.getViewRoot().getViewId());
			context.setViewRoot(viewRoot);
			context.renderResponse();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public boolean eliminarFichero(String nomFichero) {
		boolean eliminarFichero = false;
		try {
			String ruta = procesoArchivoService
					.getRutaPKG(Constantes.RUTA_EXCEL_DESTINO)
					+ "\\"
					+ nomFichero;
			log.info("Ruta del archivo " + ruta);
			File file = new File(ruta);
			if (file.canRead()) {
				if (file.exists()) {
					file.delete();
					eliminarFichero = true;
					log.info("Archivo " + nomFichero + " eliminado");
				}
			}
		} catch (SyncconException e) {
			e.printStackTrace();
		}

		return eliminarFichero;
	}
	@Override
	public void run() {
		
		try {
			while (procesoArchivoService.encontroJobRunnable()) {
				Thread.sleep(15000);
				log.info("Ingreso a espera");
			}
			flagEjecuccion = procesoArchivoService.ejecutarPaquete();
			notify();
			
		} catch (SyncconException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}

	public Long getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(Long codProceso) {
		this.codProceso = codProceso;
	}

	public String getUsuarioProceso() {
		return usuarioProceso;
	}

	public void setUsuarioProceso(String usuarioProceso) {
		this.usuarioProceso = usuarioProceso;
	}

	public Date getFechaCargaDesde() {
		return fechaCargaDesde;
	}

	public void setFechaCargaDesde(Date fechaCargaDesde) {
		this.fechaCargaDesde = fechaCargaDesde;
	}

	public Date getFechaCargaHasta() {
		return fechaCargaHasta;
	}

	public void setFechaCargaHasta(Date fechaCargaHasta) {
		this.fechaCargaHasta = fechaCargaHasta;
	}

	public Date getFechaProcesoDesde() {
		return fechaProcesoDesde;
	}

	public void setFechaProcesoDesde(Date fechaProcesoDesde) {
		this.fechaProcesoDesde = fechaProcesoDesde;
	}

	public Date getFechaProcesoHasta() {
		return fechaProcesoHasta;
	}

	public void setFechaProcesoHasta(Date fechaProcesoHasta) {
		this.fechaProcesoHasta = fechaProcesoHasta;
	}

	public List<ProcesoArchivo> getListaProcesoArchivo() {
		return listaProcesoArchivo;
	}

	public void setListaProcesoArchivo(List<ProcesoArchivo> listaProcesoArchivo) {
		this.listaProcesoArchivo = listaProcesoArchivo;
	}

	public List<SelectItem> getItemsEstados() {
		return itemsEstados;
	}

	public void setItemsEstados(List<SelectItem> itemsEstados) {
		this.itemsEstados = itemsEstados;
	}

	public ProcesoArchivo getProcesoArchivo() {
		return procesoArchivo;
	}

	public void setProcesoArchivo(ProcesoArchivo procesoArchivo) {
		this.procesoArchivo = procesoArchivo;
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public ProcesoArchivoService getProcesoArchivoService() {
		return procesoArchivoService;
	}

	public void setProcesoArchivoService(
			ProcesoArchivoService procesoArchivoService) {
		this.procesoArchivoService = procesoArchivoService;
	}

	public String getNomArchivo() {
		return nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public List<CargaOperaciones> getListaProcesoCarga() {
		return listaProcesoCarga;
	}

	public void setListaProcesoCarga(List<CargaOperaciones> listaProcesoCarga) {
		this.listaProcesoCarga = listaProcesoCarga;
	}

	public CargaOperaciones getCargaOperaciones() {
		return cargaOperaciones;
	}

	public void setCargaOperaciones(CargaOperaciones cargaOperaciones) {
		this.cargaOperaciones = cargaOperaciones;
	}

	public RepParametroService getRepParametroService() {
		return repParametroService;
	}

	public void setRepParametroService(RepParametroService repParametroService) {
		this.repParametroService = repParametroService;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public ProcesoArchivoTransaccional getProcesoTransaccional() {
		return procesoTransaccional;
	}

	public void setProcesoTransaccional(
			ProcesoArchivoTransaccional procesoTransaccional) {
		this.procesoTransaccional = procesoTransaccional;
	}

	public List<LogError> getListaLogError() {
		return listaLogError;
	}

	public void setListaLogError(List<LogError> listaLogError) {
		this.listaLogError = listaLogError;
	}

	public Long getCodArchivo() {
		return codArchivo;
	}

	public void setCodArchivo(Long codArchivo) {
		this.codArchivo = codArchivo;
	}

	public String getDesArchivo() {
		return desArchivo;
	}

	public void setDesArchivo(String desArchivo) {
		this.desArchivo = desArchivo;
	}

	public String getEstadoArchivo() {
		return estadoArchivo;
	}

	public void setEstadoArchivo(String estadoArchivo) {
		this.estadoArchivo = estadoArchivo;
	}


}
