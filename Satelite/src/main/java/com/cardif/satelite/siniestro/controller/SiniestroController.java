package com.cardif.satelite.siniestro.controller;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.GenDepartamentoService;
import com.cardif.satelite.configuracion.service.GenDistritoService;
import com.cardif.satelite.configuracion.service.GenPaisService;
import com.cardif.satelite.configuracion.service.GenProvinciaService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.configuracion.service.TipoTrabajadorService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.SiniDatosCafae;
import com.cardif.satelite.model.SiniDesempleo;
import com.cardif.satelite.model.SiniDesgravamen;
import com.cardif.satelite.model.SiniObjetos;
import com.cardif.satelite.model.SiniPoliza;
import com.cardif.satelite.model.SiniProducto;
import com.cardif.satelite.model.SiniRentaHosp;
import com.cardif.satelite.model.SiniRobo;
import com.cardif.satelite.model.SiniSiniestro;
import com.cardif.satelite.model.SiniValidaciones;
import com.cardif.satelite.model.satelite.GenDepartamento;
import com.cardif.satelite.model.satelite.GenDistrito;
import com.cardif.satelite.model.satelite.GenPais;
import com.cardif.satelite.model.satelite.GenProvincia;
import com.cardif.satelite.model.satelite.TipoTrabajador;
import com.cardif.satelite.siniestro.bean.ConsultaSiniestro;
import com.cardif.satelite.siniestro.bean.ReporteSiniestro;
import com.cardif.satelite.siniestro.service.SiniDatosCafaeService;
import com.cardif.satelite.siniestro.service.SiniDesempleoService;
import com.cardif.satelite.siniestro.service.SiniDesgravamenService;
import com.cardif.satelite.siniestro.service.SiniObjetosService;
import com.cardif.satelite.siniestro.service.SiniPolizaService;
import com.cardif.satelite.siniestro.service.SiniProductoService;
import com.cardif.satelite.siniestro.service.SiniRentaHospService;
import com.cardif.satelite.siniestro.service.SiniRoboService;
import com.cardif.satelite.siniestro.service.SiniSiniestroService;
import com.cardif.satelite.siniestro.service.SiniValidacionesService;
import com.cardif.satelite.siniestro.service.SiniestrosManager;
import com.cardif.satelite.siniestro.service.impl.SiniestroDataSourceService;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.satelite.util.Utilitarios;
import com.sun.mail.imap.Utility;

@Controller("siniestroController")
@Scope("request")
public class SiniestroController extends BaseController {
	public static final Logger logger = Logger
			.getLogger(SiniestroController.class);
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private SiniSiniestroService siniSiniestroService;
	@Autowired
	private SiniPolizaService siniPolizaService;
	@Autowired
	private SiniDatosCafaeService siniDatosCafaeService;
	@Autowired
	private SiniDesgravamenService siniDesgravamenService;
	@Autowired
	private SiniDesempleoService siniDesempleoService;
	@Autowired
	private SiniRentaHospService siniRentaHospService;
	@Autowired
	private SiniRoboService siniRoboService;
	@Autowired
	private GenProvinciaService genProvinciaService;
	@Autowired
	private GenDistritoService genDistritoService;
	@Autowired
	private GenDepartamentoService genDepartamentoService;
	@Autowired
	private GenPaisService genPaisService;
	@Autowired
	private SiniProductoService siniProductoService;
	@Autowired
	private SiniValidacionesService siniValidacionesService;
	@Autowired
	private SiniObjetosService siniObjetosService;
	@Autowired
	private SiniestrosManager siniestrosManager;
	@Autowired
	private TipoTrabajadorService tipoTrabajadorService;

	private List<GenProvincia> listaProvincia;
	private List<GenDepartamento> listaDepartamento;
	private List<GenDistrito> listaDistrito;
	private List<TipoTrabajador> listaTipoTrabajador;

	private List<ConsultaSiniestro> listaSiniSiniestro;
	private List<ConsultaSiniestro> varlista;
	private List<SiniDatosCafae> listaSiniDatosCafae;
	private List<SiniPoliza> listaSiniPoliza;
	private List<SiniDesgravamen> listaSiniDesgravamen;

	private List<SiniRentaHosp> listaRentaHosp;
	private List<SiniRobo> listaSiniRobo;
	private List<SiniDesempleo> listaSiniDesempleo;

	private SimpleSelection selection;

	private List<SelectItem> listaTipoSeguroItem;
	private List<SelectItem> listaSocioItem;
	private List<SelectItem> listaProductoItem1;
	private List<SelectItem> listaProductoItem2;
	private List<SelectItem> listaTipoDocItem;
	private List<SelectItem> listaCoberturaAfecItem;
	private List<SelectItem> listaEstadoItem;
	private List<SelectItem> listaEstadoLegajoItem;
	private List<SelectItem> listaEjeCafaeItem;
	private List<SelectItem> listaRamosItems;

	private List<SelectItem> listaPaisItem;
	private List<SelectItem> listaDepartamentoItem;
	private List<SelectItem> listaDepartamentoPaisItem;
	private List<SelectItem> listaProvinciaItem;
	private List<SelectItem> listaDistritoItem;
	private List<SelectItem> listaGenerosItem;
	private List<SelectItem> listaRechazosItem;
	private List<SelectItem> listaRechazosAgItem;
	private List<SelectItem> listaResumenItem;
	private List<SelectItem> listaParentescoItem;
	private List<SelectItem> listaParentescoItem2;
	private List<SelectItem> listaMonedaItem;
	private List<SelectItem> listaMotivoNoEntregaItem;
	private List<SelectItem> listaTipRefCafaeItem;
	private List<SelectItem> listaTipoTrabajItem;

	private List<ReporteSiniestro> listaReporteSiniestro;
	private BigDecimal total;
	private List<ReporteSiniestro> varlistaReporteSiniestro;

	private SiniSiniestro siniSiniestro;
	private SiniDatosCafae siniDatosCafae;
	private SiniRobo siniRobo;
	private SiniDesempleo siniDesempleo;

	private SiniDesgravamen siniDesgravamen;
	private SiniRentaHosp siniRentaHosp;

	private SiniPoliza siniPoliza;

	private String nroSiniestro_b;
	private Date fecNotiDesde_b;
	private Date fecNotiHasta_b;
	private Date fecUltDocumen_b;
	private Date fecUltDocumenDesde_b;
	private Date fecUltDocumenHasta_b;
	private Date fecAprobRechDesde_b;
	private Date fecAprobRechHasta_b;
	private Date fecEntrgaOpcDesde_b;
	private Date fecEntrgaOpcHasta_b;
	private String pagoDesde_b;
	private String pagoHasta_b;
	private String nroPlanilla_b;
	private String findEjeCafae_b;
	private String vTipoTarjeta_b;
	private String tipoSeguro_b;
	private String socio_b;
	private String producto_b;
	private String estado_b;
	private String estadoLegajo_b;
	private String nroDocumento_b;
	private String cobAfectada_b;
	private String nroPoliza_b;
	private String nombres_b;
	private String apePaterno_b;
	private String apeMaterno_b;
	private String tipoDoc_b;
	private String tipoRefCafae_b;
	private String ejeCafae_b;
	private String codRamo_m1;

	private String variasPrueba;
	private boolean esNuevo;

	private boolean flagMuNa;
	private boolean flagMuAc;
	private boolean flagIHP;
	private boolean flagSaDe;
	private boolean flagUtEs;
	private boolean flagReRe;
	private boolean flagTrRe;
	private boolean flagInToPe;
	private boolean flagDc;
	private boolean flagDs;

	// parametro de robo
	private String sucursalRetiro_m3;
	private double impgastoMedico_m3;
	private double impPreLiquidacion_m3;

	private boolean celular_m3;
	private boolean cartera_m3;
	private boolean maletetin_m3;
	private boolean billetera_m3;
	private boolean portadocumentos_m3;
	private boolean lentesOpticos_m3;
	private boolean lentesDeSol_m3;
	private boolean cosmeticos_m3;
	private boolean lapicero_m3;
	private boolean dni_m3;
	private boolean mochila_m3;
	private boolean reloj_m3;
	private boolean discIpodMP3Table_m3;
	private boolean palmTabled_m3;
	private boolean bolso_m3;
	private boolean sillaAutoBB_m3;
	private boolean cocheBB_m3;
	private boolean disco_m3;
	private boolean llanta_m3;
	private boolean flagMedicos_m3;
	private boolean libreDisp_m3;
	private boolean muerteAccidente_m3;
	private boolean llaves_m3;

	private boolean requiredCafae;
	private boolean requiredRentaHosp;
	private boolean requiredDesemp;
	private boolean requiredFecAproRechazo;
	private boolean seleccionado = true;

	private boolean verSiniestro = false;
	private String styleVerSiniestro;

	private boolean readonlyProducto;
	private boolean readonlyMostrar;
	private boolean readonlyFecha;
	boolean flagcargoTargeEntregada_m5;

	private Date fecOcurrencia_contenedor;
	private Date fecNotificacion_contenedor;
	private Date nroSiniestro;
	private String edaFecOcurrenciaCal;

	private JasperReport report;
	private Map<String, Object> params;
	private ServletOutputStream out;
	private String nombreArchivo;
	private File tempFile;
	private boolean upload;

	private boolean botonExtra;
	ConsultaSiniestro consultaSiniestro;

	private String emisorConsulta = "";

	private Boolean selected;
	private Selection siniestroSelection;

	private Boolean obligatorio;
	private int numero;
	private Boolean obligatorioEstado;

	private String mensaje;

	private String strDiasNotificacion = "";
	private String strDiasDocumentacion = "";

	public String verSiniestroCall() {
		emisorConsulta = "CALLCENTER";
		return verSiniestro();
	}

	public String verSiniestroSini() {
		emisorConsulta = "";
		return verSiniestro();
	}

	@Override
	@PostConstruct
	public String inicio() {
		pagoDesde_b = null;
		pagoHasta_b = null;
		logger.info("Inicio carga de datos: ");
		listaSiniSiniestro = new ArrayList<ConsultaSiniestro>();
		selection = new SimpleSelection();
		String respuesta = null;
		if (!tieneAcceso()) {
			logger.debug("No cuenta con los accesos necesarios");
			return "accesoDenegado";
		}
		try {

			listaPaisItem = new ArrayList<SelectItem>();
			listaDepartamentoItem = new ArrayList<SelectItem>();
			listaDepartamentoPaisItem = new ArrayList<SelectItem>();
			listaProvinciaItem = new ArrayList<SelectItem>();
			listaDistritoItem = new ArrayList<SelectItem>();

			listaTipoTrabajItem = new ArrayList<SelectItem>();

			/*** lista de Tipos de Seguros ***************/
			List<Parametro> listaTipoSeguro = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_SEGUROS, TIP_PARAM_DETALLE);
			listaTipoSeguroItem = new ArrayList<SelectItem>();
			listaTipoSeguroItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaTipoSeguro) {
				listaTipoSeguroItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista de socios ***************/
			List<Parametro> listaSocio = parametroService.buscar(
					Constantes.COD_PARAM_SOCIOS_SINI, TIP_PARAM_DETALLE);
			listaSocioItem = new ArrayList<SelectItem>();
			listaSocioItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaSocio) {
				listaSocioItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista de productos ***************/
			List<SiniProducto> listaProducto = siniProductoService.listar();
			listaProductoItem1 = new ArrayList<SelectItem>();
			listaProductoItem1.add(new SelectItem("", "- Seleccionar -"));
			for (SiniProducto p : listaProducto) {
				listaProductoItem1.add(new SelectItem(p.getNroProducto(), p
						.getNomProducto()));
			}

			/*** lista de tipo de Documento ***************/
			List<Parametro> listaTipoDoc = parametroService.buscar(
					Constantes.COD_PARAM_TIPOS_DOCUMENTO_SINI,
					TIP_PARAM_DETALLE);
			listaTipoDocItem = new ArrayList<SelectItem>();
			listaTipoDocItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaTipoDoc) {
				listaTipoDocItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista de coberturas afectadas ***************/
			List<Parametro> listaCoberturasAfec = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_COBERTURA, TIP_PARAM_DETALLE);
			listaCoberturaAfecItem = new ArrayList<SelectItem>();
			listaCoberturaAfecItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaCoberturasAfec) {
				listaCoberturaAfecItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista estados ***************/
			List<Parametro> listaEstados = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_ESTADOS, TIP_PARAM_DETALLE);
			listaEstadoItem = new ArrayList<SelectItem>();
			listaEstadoItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaEstados) {
				listaEstadoItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista estados legajo ***************/
			List<Parametro> listaEstadosLegajo = parametroService
					.buscar(Constantes.COD_PARAM_LISTA_ESTADO_LEGAJO,
							TIP_PARAM_DETALLE);
			listaEstadoLegajoItem = new ArrayList<SelectItem>();
			listaEstadoLegajoItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaEstadosLegajo) {
				listaEstadoLegajoItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista generos ***************/
			List<Parametro> listaGeneros = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_GENEROS_SINI, TIP_PARAM_DETALLE);
			listaGenerosItem = new ArrayList<SelectItem>();
			listaGenerosItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaGeneros) {
				listaGenerosItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista rechazos ***************/
			List<Parametro> listaRechazos = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_RECHAZADOS, TIP_PARAM_DETALLE);
			listaRechazosItem = new ArrayList<SelectItem>();
			listaRechazosItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaRechazos) {
				listaRechazosItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista rechazos agrupados ***************/
			List<Parametro> listaRechazosAg = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_RECHAZOS_AGRUPADOS,
					TIP_PARAM_DETALLE);
			listaRechazosAgItem = new ArrayList<SelectItem>();
			listaRechazosAgItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaRechazosAg) {
				listaRechazosAgItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista rechazos resumen ***************/
			List<Parametro> listaResumen = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_RESUMEN, TIP_PARAM_DETALLE);
			listaResumenItem = new ArrayList<SelectItem>();
			listaResumenItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaResumen) {
				listaResumenItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista parentesco ***************/
			List<Parametro> listaParentesco = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_PARENTESCO_SINI,
					TIP_PARAM_DETALLE);
			listaParentescoItem = new ArrayList<SelectItem>();
			listaParentescoItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaParentesco) {
				listaParentescoItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista parentesco cafae ***************/
			List<Parametro> listaParentesco2 = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_PARENTESCO_SINI,
					TIP_PARAM_DETALLE);
			listaParentescoItem2 = new ArrayList<SelectItem>();
			listaParentescoItem2.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaParentesco2) {
				listaParentescoItem2.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista monedas ***************/
			List<Parametro> listaMonedas = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_MONEDA, TIP_PARAM_DETALLE);
			listaMonedaItem = new ArrayList<SelectItem>();
			listaMonedaItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaMonedas) {
				listaMonedaItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista motivo no entrega ***************/
			List<Parametro> listaMotivosNoEntrega = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_MOTIVOS_NO_ENTREGA,
					TIP_PARAM_DETALLE);
			listaMotivoNoEntregaItem = new ArrayList<SelectItem>();
			listaMotivoNoEntregaItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaMotivosNoEntrega) {
				listaMotivoNoEntregaItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista ramos ***************/
			List<Parametro> listaRamos = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_RAMOS, TIP_PARAM_DETALLE);
			listaRamosItems = new ArrayList<SelectItem>();
			listaRamosItems.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaRamos) {
				listaRamosItems.add(new SelectItem(p.getCodValor(), p
						.getCodValor() + " - " + p.getNomValor()));
			}

			/*** lista paises ***************/
			List<GenPais> listaPaises = genPaisService.buscar();
			listaPaisItem = new ArrayList<SelectItem>();
			listaPaisItem.add(new SelectItem("", "- Seleccionar -"));
			for (GenPais d : listaPaises) {
				listaPaisItem.add(new SelectItem(d.getCodPais(), d
						.getNombrePais()));
			}
			/*** lista departamentos ***************/
			listaDepartamentoItem = llenarDepartamentos();
			listaDepartamentoPaisItem = llenarDepartamentos();

			/*** lista ejecutivo cafae ***************/
			List<Parametro> listaEjecutivo = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_EJECUTIVOS, TIP_PARAM_DETALLE);
			listaEjeCafaeItem = new ArrayList<SelectItem>();
			listaEjeCafaeItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaEjecutivo) {
				listaEjeCafaeItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

			/*** lista de tipo de trabajador ***************/
			List<TipoTrabajador> listaTipoTrab = tipoTrabajadorService.buscar();
			listaTipoTrabajItem = new ArrayList<SelectItem>();
			listaTipoTrabajItem.add(new SelectItem("", "- Seleccionar -"));
			for (TipoTrabajador t : listaTipoTrab) {
				listaTipoTrabajItem.add(new SelectItem(
						t.getCodTipoTrabajador(), t.getDescripcion()));
			}

			/*** lista de tipo de ref Cafae ***************/
			List<Parametro> listaTipRefCafae = parametroService.buscar(
					Constantes.COD_PARAM_LISTA_TIPO_REF_CAFAE,
					TIP_PARAM_DETALLE);
			listaTipRefCafaeItem = new ArrayList<SelectItem>();
			listaTipRefCafaeItem.add(new SelectItem("", "- Seleccionar -"));
			for (Parametro p : listaTipRefCafae) {
				listaTipRefCafaeItem.add(new SelectItem(p.getCodValor(), p
						.getNomValor()));
			}

		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		logger.info("Final");

		return respuesta;
	}

	private List<SelectItem> llenarDepartamentos() throws SyncconException {
		List<GenDepartamento> listaDepartamentos = genDepartamentoService
				.buscar();
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", "- Seleccionar -"));
		for (GenDepartamento d : listaDepartamentos) {
			items.add(new SelectItem(d.getCodDepartamento(), d
					.getNombreDepartamento()));
		}
		return items;
	}

	public String calcularDiasNotificacion() {
		
		if (siniSiniestro.getFecNotificacion() != null && siniSiniestro.getFecRecepSocio() != null) {
			strDiasNotificacion = String.valueOf(TimeUnit.DAYS.convert(
					siniSiniestro.getFecNotificacion().getTime()
							- siniSiniestro.getFecRecepSocio().getTime(),
					TimeUnit.MILLISECONDS));
		}else{
			strDiasNotificacion = String.valueOf(0);
		}

		return strDiasNotificacion;
	}

	public String calcularDiasDocumentacion() {
		if (siniSiniestro.getFecUltDocum() != null && siniSiniestro.getFecUltDocumSocio() != null) {
			strDiasDocumentacion = String.valueOf(TimeUnit.DAYS.convert(
					siniSiniestro.getFecUltDocum().getTime()
							- siniSiniestro.getFecUltDocumSocio().getTime(),
					TimeUnit.MILLISECONDS));
		}else{
			strDiasDocumentacion = String.valueOf(0);
		}

		return strDiasDocumentacion;
	}

	public String registrarSini() {
		logger.info("Inicio");
		String respuesta = null;
		vTipoTarjeta_b = "";

		try {

			esNuevo = true;
			readonlyProducto = false;
			setReadonlyFecha(false);

			siniSiniestro = new SiniSiniestro();
			siniSiniestro.setNacionalidad("PERUANA");
			siniSiniestro.setCodPais("173");
			listaDepartamentoItem = llenarDepartamentos();
			listaDepartamentoPaisItem = llenarDepartamentos();
			siniDesgravamen = new SiniDesgravamen();
			siniRentaHosp = new SiniRentaHosp();
			siniPoliza = new SiniPoliza();
			siniDatosCafae = new SiniDatosCafae();
			siniRobo = new SiniRobo();
			siniDesempleo = new SiniDesempleo();
			listaProductoItem2 = new ArrayList<SelectItem>();
			listaProductoItem2.add(new SelectItem("", "- Seleccionar -"));
			limpiarFlagsRobo();
			strDiasDocumentacion = "";
			strDiasNotificacion = "";

			flagcargoTargeEntregada_m5 = false;
			codRamo_m1 = null;
			
		   

			respuesta = "registrar";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;

	}

	private void limpiarFlagsRobo() {

		setCartera_m3(false);
		setBilletera_m3(false);
		setCelular_m3(false);
		setCocheBB_m3(false);
		setCosmeticos_m3(false);
		setDiscIpodMP3Table_m3(false);
		setDisco_m3(false);
		setLlanta_m3(false);
		setLapicero_m3(false);
		setLentesDeSol_m3(false);

		setLibreDisp_m3(false);
		setLlaves_m3(false);
		setSillaAutoBB_m3(false);
		setReloj_m3(false);
		setLentesOpticos_m3(false);

		setMaletetin_m3(false);
		setPortadocumentos_m3(false);
		setMochila_m3(false);
		setCocheBB_m3(false);
		setFlagMedicos_m3(false);
		setMuerteAccidente_m3(false);
		setDni_m3(false);
		setPalmTabled_m3(false);
		setBolso_m3(false);

		setFlagMuNa(false);
		setFlagMuAc(false);
		setFlagIHP(false);
		setFlagSaDe(false);
		setFlagUtEs(false);

		setFlagReRe(false);
		setFlagTrRe(false);
		setFlagInToPe(false);
		setFlagDc(false);
		setFlagDs(false);

	}

	public String cancelar() {

		pagoDesde_b = null;
		pagoHasta_b = null;
		logger.info("Inicio");
		String respuesta = null;

		try {
			if (verSiniestro) {
				readonlyProducto = false;
				setReadonlyFecha(false);
				readonlyMostrar = false;
				styleVerSiniestro = "";
			}

			respuesta = "cancelar";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");

		if (emisorConsulta == "CALLCENTER") {
			emisorConsulta = "";
			return "cancelarCallCenter";
		}
		return respuesta;

	}

	private String guardar() throws Exception {
		Integer canSiniestros;
		String resp = "";
		String nroSiniestro = null;
		int diastras = 0;

		logger.debug("NRO SINIESTRO  : " + siniSiniestro.getNroSiniestro());
		if (isFlagcargoTargeEntregada_m5()) {
			siniSiniestro.setFlagCargoCarta(Short.parseShort("1"));
		} else {
			siniSiniestro.setFlagCargoCarta(Short.parseShort("0"));
		}

		readonlyProducto = true;
		setReadonlyFecha(true);
		int listaGrilla;
		if (listaSiniSiniestro.size() == 0) {
			listaGrilla = 0;
		} else {
			listaGrilla = 1;
		}

		if (esNuevo) {
			listaSiniSiniestro = siniSiniestroService.buscar(null, null, null,
					null, siniSiniestro.getTipDocumAsegurado(),
					siniSiniestro.getNroDocumAsegurado(), null, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null);

			if (CollectionUtils.isNotEmpty(listaSiniSiniestro)) {
				canSiniestros = listaSiniSiniestro.size() + 1;
				SiniSiniestro sini = null;
				for (ConsultaSiniestro siniConsul : listaSiniSiniestro) {
					sini = new SiniSiniestro();
					sini.setNroSiniestro(siniConsul.getNroSiniestro());
					sini.setCanSiniestro(canSiniestros.shortValue());
					siniSiniestroService.actualizarSelective(sini);
				}
			} else {
				canSiniestros = 1;
			}
			
			// /////////////////////////////////////////////////////////
	        /*
	         *  Cuando la fecha de ultima documentacion se encuentre en blanco 
	         *  lo que hace es setear y repetir la fecha de notificacion
	         *  
	         */
						Date FecUltDocum = siniSiniestro.getFecNotificacion();

						if (siniSiniestro.getFecUltDocum() == null) {
							siniSiniestro.setFecUltDocum(FecUltDocum);
						}
						
						Date FecUltDocumSocio = siniSiniestro.getFecRecepSocio();
					      
					      if (siniSiniestro.getFecUltDocumSocio() == null){
					    	  siniSiniestro.setFecUltDocumSocio(FecUltDocumSocio);
					      }
			

			diastras = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
									siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(),
									siniSiniestro.getFecAprobRechazo(),
									siniSiniestro.getFecEntregaOpCont());
							
							
			siniSiniestro.setCanDiasPendientes(diastras);
							
			System.out.println("diatras: " + diastras);
			System.out.println("DocumSocio: " + siniSiniestro.getFecUltDocumSocio());
			System.out.println("DocumCardif: " + siniSiniestro.getFecUltDocumSocio());
			


			siniSiniestro.setCanSiniestro(canSiniestros.shortValue());
			siniSiniestro.setNroPlanilla(siniSiniestro.getNroPlanilla());
			siniSiniestro.setUsuCreacion(SecurityContextHolder.getContext()
					.getAuthentication().getName());
			siniestrosManager
					.insertarSiniestroPoliza(siniSiniestro, siniPoliza);
			nroSiniestro = siniSiniestro.getNroSiniestro();
			siniDatosCafae.setNroSiniestro(nroSiniestro);
			siniRobo.setNroSiniestro(nroSiniestro);
			siniRentaHosp.setNroSiniestro(nroSiniestro);
			siniDesgravamen.setNroSiniestro(nroSiniestro);
			siniDesempleo.setNroSiniestro(nroSiniestro);
			gudardarFlasCafae(siniDatosCafae);
			siniDatosCafaeService.insertarSiniDatosCafae(siniDatosCafae);

			guardarFlasRobo(siniRobo);
			siniRoboService.insertarSiniRobo(siniRobo);

			siniRentaHospService.insertarSiniRentaHosp(siniRentaHosp);
			siniDesgravamenService.insertarSiniDesgravamen(siniDesgravamen);
			siniDesempleoService.insertarSiniDesempleo(siniDesempleo);
			esNuevo = false;
		} else {
			gudardarFlasCafae(siniDatosCafae);
			guardarFlasRobo(siniRobo);
			siniSiniestro.setUsuModificacion(SecurityContextHolder.getContext()
					.getAuthentication().getName());
			siniSiniestroService.actualizarSiniSiniestro(siniSiniestro);
			siniDatosCafaeService.actualizarSiniDatosCafae(siniDatosCafae);
			siniRoboService.actualizarSiniRobo(siniRobo);
			siniDesempleoService.actualizarSiniDesempleo(siniDesempleo);
			siniRentaHospService.actualizarRentaHosp(siniRentaHosp);
			siniDesgravamenService.actualizarSiniDesgravamen(siniDesgravamen);

			vTipoTarjeta_b = siniDesgravamen.getTipTarjeta();
			siniDesgravamen.setTipTarjeta(siniDesgravamen.getTipTarjeta());
			siniPolizaService.actualizarSiniPoliza(siniPoliza);
		}

		listaSiniSiniestro = siniSiniestroService.buscar(nroSiniestro_b,
				socio_b, producto_b, nroPoliza_b, tipoDoc_b, nroDocumento_b,
				cobAfectada_b, nombres_b, apePaterno_b, apeMaterno_b, estado_b,
				estadoLegajo_b, fecUltDocumen_b, fecUltDocumenDesde_b,
				fecUltDocumenHasta_b, fecAprobRechDesde_b, fecAprobRechHasta_b,
				fecEntrgaOpcDesde_b, fecEntrgaOpcHasta_b, pagoDesde_b,
				pagoHasta_b, nroPlanilla_b, findEjeCafae_b, fecNotiDesde_b,
				fecNotiHasta_b, tipoSeguro_b);
		
	

		calcularDiasNotificacion();
		calcularDiasDocumentacion();
		resp = "guardar";

		return resp;

	}

	public String metodoValidaBtnGuardar() {

		logger.info("llega ........");
		return "";
	}

	public String guardarSiniestro() {
		logger.info("Inicio");
		String respuesta = "";
		Date fecActual;
		fecActual = new Date(System.currentTimeMillis());
		Utilitarios.convertirFechaACadena(fecActual, "dd/MM/yyyy");

		int vEstadoPago;
		vEstadoPago = validaEstadoPago();
		obligatorioEstado = Boolean.FALSE;
		if (vEstadoPago == 1) {

			obligatorioEstado = Boolean.TRUE;
			SateliteUtil
					.mostrarMensaje("De acuerdo con el estado seleccionado, el monto de pago debe ser mayor a 0.00.");
			return null;
		}

		try {
			if (siniSiniestro.getFecOcurrencia().after(fecActual)) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_OCURRENCIA_CON_FECHA_ACTUAL,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecNotificacion().after(fecActual)) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_CARDIF_CON_FECHA_ACTUAL,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecOcurrencia().after(
					siniSiniestro.getFecNotificacion())) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_OCURRENCIA_CON_FECHA_NOTIFICACION_CARDIF,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecUltDocum() != null
					&& siniSiniestro.getFecUltDocum().after(fecActual)) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_ULT_DOCUMEN_CARDIF_CON_FECHA_ACTUAL,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecUltDocumSocio() != null
					&& siniSiniestro.getFecUltDocumSocio().after(fecActual)) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_ULT_DOCUMEN_SOCIO_CON_FECHA_ACTUAL,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniPoliza.getFecIniVigencia().after(fecActual)) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_INICIO_VIGENCIA_CON_FECHA_ACTUAL,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecNacimiento() != null
					&& siniSiniestro.getFecNacimiento().after(fecActual)) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_NACIMIENTO_CON_FECHA_ACTUAL,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecUltDocum() != null
					&& siniSiniestro.getFecUltDocum() != null
					&& siniSiniestro.getFecUltDocum().before(
							siniSiniestro.getFecNotificacion())) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_ULT_DOCUM_CARDIF_Y_NOT_CON_FECHA_ACTUAL,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecUltDocumSocio() != null
					&& siniSiniestro.getFecUltDocumSocio().after(
							siniSiniestro.getFecUltDocum())) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_ULT_DOC_SOCIO_Y_NOT_CON_FECHA_ULT_DOC_CARDIF,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecRecepSocio() != null
					&& siniSiniestro.getFecRecepSocio().after(
							siniSiniestro.getFecNotificacion())) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_NOT_SOCIO_Y_NOT_CON_FECHA_NOT_CARDIF,
						FacesMessage.SEVERITY_INFO);
			}
			if (siniSiniestro.getFecEmiCheque() != null)
				if (siniSiniestro.getFecEmiCheque().after(fecActual)) {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_EMI_CHEQUE_FECHA_ACTUAL,
							FacesMessage.SEVERITY_INFO);
				}
			if (siniSiniestro.getFecEmiCheque() != null)
				if (siniSiniestro.getFecAprobRechazo() != null
						&& siniSiniestro.getFecEmiCheque().before(
								siniSiniestro.getFecAprobRechazo())) {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_EMI_Y_FECHA_APROBACION,
							FacesMessage.SEVERITY_INFO);
				}
			if (siniSiniestro.getFecEntCheque() != null)
				if (siniSiniestro.getFecEmiCheque() != null
						&& siniSiniestro.getFecEntCheque().before(
								siniSiniestro.getFecEmiCheque())) {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_ENTRE_Y_FECHA_EMI_CHECE,
							FacesMessage.SEVERITY_INFO);
				}
			if (siniSiniestro.getFecEntCheque() != null)
				if (siniSiniestro.getFecEntCheque().after(fecActual)) {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_ENTRE_Y_FECHA_ACTUAL,
							FacesMessage.SEVERITY_INFO);
				}
			if (siniSiniestro.getFecAprobRechazo() != null)
				if (siniSiniestro.getFecAprobRechazo().before(
						siniPoliza.getFecIniVigencia())) {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_APROB_RECHA_Y_FECHA_INICIO_VIGENCIA,
							FacesMessage.SEVERITY_INFO);
				}
			if (siniSiniestro.getFecAprobRechazo() != null)
				if (siniSiniestro.getFecAprobRechazo().after(fecActual)) {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_PROBACION_RECHA_Y_FECHA_ACTUAL,
							FacesMessage.SEVERITY_INFO);
				}
			if (siniSiniestro.getFecAprobRechazo() != null) {
				if (siniSiniestro.getFecUltDocum() == null) {
					if (siniSiniestro.getFecNotificacion().after(
							siniSiniestro.getFecAprobRechazo())) {
						throw new SyncconException(
								ErrorConstants.COD_ERROR_SINIESTROS_FECHA_PROBACION_RECHA_Y_FECHA_NOTIF_CARDIF,
								FacesMessage.SEVERITY_INFO);
					}

				} else {
					if (siniSiniestro.getFecUltDocum().after(
							siniSiniestro.getFecAprobRechazo())) {
						throw new SyncconException(
								ErrorConstants.COD_ERROR_SINIESTROS_FECHA_PROBACION_RECHA_Y_FECHA_ULTIMA_DOCUMENT_CARDIF,
								FacesMessage.SEVERITY_INFO);
					}
				}
			}
			if (siniPoliza.getFecFinVigencia() != null)
				if (siniPoliza.getFecIniVigencia().after(
						siniPoliza.getFecFinVigencia())) {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_FIN_VIGENCIA_CON_FECHA_INI_VIGENCIA,
							FacesMessage.SEVERITY_INFO);
				}
			if (siniSiniestro.getFecNacimiento() != null
					&& siniSiniestro.getFecNacimiento().after(
							siniSiniestro.getFecOcurrencia())) {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_FECHA_NACIMIENTO_CON_FECHA_OCURRENCIA,
						FacesMessage.SEVERITY_INFO);
			}
			

			if (siniSiniestro.getFecRecepSocio() != null) {
				if (siniSiniestro.getFecOcurrencia().before(
						siniSiniestro.getFecRecepSocio())
						|| siniSiniestro.getFecOcurrencia().compareTo(
								siniSiniestro.getFecRecepSocio()) == 0) {
					if (siniPoliza.getFecIniVigencia().before(
							siniSiniestro.getFecRecepSocio())
							|| siniPoliza.getFecIniVigencia().compareTo(
									siniSiniestro.getFecRecepSocio()) == 0) {
						if (siniSiniestro.getFecRecepSocio().before(fecActual)
								|| siniSiniestro.getFecRecepSocio().compareTo(
										fecActual) == 0) {
							if (isRequiredFecAproRechazo() == true) {
								if (siniPoliza.getFecIniVigencia().before(
										siniSiniestro.getFecAprobRechazo())
										|| siniPoliza
												.getFecIniVigencia()
												.compareTo(
														siniSiniestro
																.getFecAprobRechazo()) == 0) {
									if (!siniPoliza.getCodSocio()
											.equalsIgnoreCase("CF")) {
										respuesta = guardar();
									} else if (siniPoliza.getCodSocio()
											.equalsIgnoreCase("CF")
											&& (isFlagDc() || isFlagDs()
													|| isFlagIHP()
													|| isFlagInToPe()
													|| isFlagMuAc()
													|| isFlagMuNa()
													|| isFlagReRe()
													|| isFlagSaDe()
													|| isFlagTrRe() || isFlagUtEs())) {
										respuesta = guardar();
									} else {
										throw new SyncconException(
												ErrorConstants.COD_ERROR_SINIESTROS_AL_MENOS_UNA_COBERTURA_CAFAE,
												FacesMessage.SEVERITY_INFO);
									}
								} else {
									throw new SyncconException(
											ErrorConstants.COD_ERROR_SINIESTROS_FECHA_APROBO_RECHAZA,
											FacesMessage.SEVERITY_INFO);
								}
							} else if (isRequiredFecAproRechazo() == false) {
								if (!siniPoliza.getCodSocio().equalsIgnoreCase(
										"CF")) {
									respuesta = guardar();
								} else if (siniPoliza.getCodSocio()
										.equalsIgnoreCase("CF")
										&& (isFlagDc() || isFlagDs()
												|| isFlagIHP()
												|| isFlagInToPe()
												|| isFlagMuAc() || isFlagMuNa()
												|| isFlagReRe() || isFlagSaDe()
												|| isFlagTrRe() || isFlagUtEs())) {
									respuesta = guardar();
								} else {
									throw new SyncconException(
											ErrorConstants.COD_ERROR_SINIESTROS_AL_MENOS_UNA_COBERTURA_CAFAE,
											FacesMessage.SEVERITY_INFO);
								}
							}
						} else {
							throw new SyncconException(
									ErrorConstants.COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_SOCIO_CON_FECHA_ACTUAL,
									FacesMessage.SEVERITY_INFO);
						}
					} else {
						throw new SyncconException(
								ErrorConstants.COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_SOCIO_CON_FECHA_INI_VIGENCIA,
								FacesMessage.SEVERITY_INFO);
					}
				} else {
					throw new SyncconException(
							ErrorConstants.COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_SOCIO,
							FacesMessage.SEVERITY_INFO);
				}
			} else {
				if (isRequiredFecAproRechazo() == true) {
					if (siniPoliza.getFecIniVigencia().before(
							siniSiniestro.getFecAprobRechazo())
							|| siniPoliza.getFecIniVigencia().compareTo(
									siniSiniestro.getFecAprobRechazo()) == 0) {
						if (!siniPoliza.getCodSocio().equalsIgnoreCase("CF")) {
							respuesta = guardar();
						} else if (siniPoliza.getCodSocio().equalsIgnoreCase(
								"CF")
								&& (isFlagDc() || isFlagDs() || isFlagIHP()
										|| isFlagInToPe() || isFlagMuAc()
										|| isFlagMuNa() || isFlagReRe()
										|| isFlagSaDe() || isFlagTrRe() || isFlagUtEs())) {
							respuesta = guardar();
						} else {
							throw new SyncconException(
									ErrorConstants.COD_ERROR_SINIESTROS_AL_MENOS_UNA_COBERTURA_CAFAE,
									FacesMessage.SEVERITY_INFO);
						}
					} else {
						throw new SyncconException(
								ErrorConstants.COD_ERROR_SINIESTROS_FECHA_APROBO_RECHAZA,
								FacesMessage.SEVERITY_INFO);
					}
				} else if (isRequiredFecAproRechazo() == false) {
					if (!siniPoliza.getCodSocio().equalsIgnoreCase("CF")) {
						respuesta = guardar();
					} else if (siniPoliza.getCodSocio().equalsIgnoreCase("CF")
							&& (isFlagDc() || isFlagDs() || isFlagIHP()
									|| isFlagInToPe() || isFlagMuAc()
									|| isFlagMuNa() || isFlagReRe()
									|| isFlagSaDe() || isFlagTrRe() || isFlagUtEs())) {
						respuesta = guardar();
					} else {
						throw new SyncconException(
								ErrorConstants.COD_ERROR_SINIESTROS_AL_MENOS_UNA_COBERTURA_CAFAE,
								FacesMessage.SEVERITY_INFO);
					}
				}

			}

		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		
		
	
        
        logger.info("Fin: ");
		logger.info(respuesta);
		logger.info("Fin: ");
		return respuesta;
		
	}

	/*
	 * 
	 * 2014-07-17 19:40:43,104 DEBUG [java.sql.PreparedStatement] [debug] - ==>
	 * Executing: delete from dbo.SINI_ROBO where NRO_SINIESTRO = ? 2014-07-17
	 * 19:40:43,198 DEBUG [java.sql.PreparedStatement] [debug] - ==> Executing:
	 * delete from dbo.SINI_DESEMPLEO where NRO_SINIESTRO = ? 2014-07-17
	 * 19:40:43,307 DEBUG [java.sql.PreparedStatement] [debug] - ==> Executing:
	 * delete from dbo.SINI_RENTA_HOSP where NRO_SINIESTRO = ? 2014-07-17
	 * 19:40:43,416 DEBUG [java.sql.PreparedStatement] [debug] - ==> Executing:
	 * delete from dbo.SINI_DESGRAVAMEN where NRO_SINIESTRO = ? 2014-07-17
	 * 19:40:43,526 DEBUG [java.sql.PreparedStatement] [debug] - ==> Executing:
	 * delete from dbo.SINI_POLIZA where NRO_SINIESTRO = ?
	 */

	public String validaTipoTarjeta() throws SyncconException {
		if (siniDesgravamen.getNroTarjeta() != null
				&& siniDesgravamen.getNroTarjeta().length() >= 8) {

			logger.info("Inicio method validaTipoTarjeta: " + vTipoTarjeta_b);

			int valida;
			valida = siniDesgravamenService.validaTipoTarjeta(siniDesgravamen
					.getNroTarjeta());

			if (valida == 1) {
				vTipoTarjeta_b = "Linea Paralela";
			} else if (valida == 0) {
				vTipoTarjeta_b = "Linea No Paralela";
			}

			logger.info("upadate valida: " + valida);
			logger.info("upadate vTipoTarjeta_b: " + vTipoTarjeta_b);
		} else {
			vTipoTarjeta_b = "";
		}
		return vTipoTarjeta_b;
	}

	public int validaEstadoPago() {
		try {

			logger.info("Inicio method validaEstadoPago: ");
			String vCodEstado = "";
			vCodEstado = siniSiniestro.getCodEstado();
			if (vCodEstado.equals("2") || vCodEstado.equals("3")
					|| vCodEstado.equals("7") || vCodEstado.equals("13")) {
				if (siniSiniestro.getImpPagos() == null
						|| Double.parseDouble(siniSiniestro.getImpPagos()
								.toString()) <= 0) {
					logger.info("Ingrese un monto mayor a 0");
					return 1;
				}
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return 0;
	}

	public void setObligatorioEstado() {
		try {
			logger.info("Inicio metodo setObligatorioEstado: ");

			obligatorioEstado = Boolean.FALSE;
			String vCodEstado = siniSiniestro.getCodEstado();
			if (vCodEstado.equals("2") || vCodEstado.equals("3")
					|| vCodEstado.equals("7") || vCodEstado.equals("13")) {
				obligatorioEstado = Boolean.TRUE;
			}

		} catch (Exception e) {
			logger.error(e);
		}
		logger.info("Fin metodo setObligatorioEstado: ");

	}

	public String mostrarSiniestro() {
    logger.info("Inicio");
    String respuesta = "";
    String pk = null;
    vTipoTarjeta_b = "";
    int diastras;

    try {
      readonlyProducto = true;
      setReadonlyFecha(true);
      esNuevo = false;
      setRequiredCafae(false);
      setRequiredDesemp(false);
      setRequiredDesemp(false);
      setFlagcargoTargeEntregada_m5(false);
      Iterator<Object> iterator = null;
      iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        pk = listaSiniSiniestro.get(key).getNroSiniestro();
      }
      if (pk != null) {
        siniSiniestro = siniSiniestroService.listar(pk);
        siniDatosCafae = siniDatosCafaeService.listar(pk);
        siniRobo = siniRoboService.listar(pk);
        siniDesempleo = siniDesempleoService.listar(pk);
        siniRentaHosp = siniRentaHospService.listar(pk);
        siniDesgravamen = siniDesgravamenService.listar(pk);
        siniPoliza = siniPolizaService.listar(pk);

        if (siniPoliza.getCodSocio().equalsIgnoreCase("CF")) {
          setRequiredCafae(true);
        } else {
          setRequiredCafae(false);
        }
        if (siniPoliza.getNroProducto().equalsIgnoreCase("590103") || siniPoliza.getNroProducto().equalsIgnoreCase("590104") || siniPoliza.getNroProducto().equalsIgnoreCase("590105")
            || siniPoliza.getNroProducto().equalsIgnoreCase("590106") || siniPoliza.getNroProducto().equalsIgnoreCase("590107") || siniPoliza.getNroProducto().equalsIgnoreCase("590108")
            || siniPoliza.getNroProducto().equalsIgnoreCase("590109")) {
          setRequiredDesemp(true);
        } else {
          setRequiredDesemp(false);
        }

        if (siniPoliza.getNroProducto().equalsIgnoreCase("610101")) {

          setRequiredRentaHosp(true);
        } else {
          setRequiredRentaHosp(false);
        }

        if (siniSiniestro.getFlagCargoCarta() == 1) {

          setFlagcargoTargeEntregada_m5(true);
        } else {
          setFlagcargoTargeEntregada_m5(false);
        }

        llenarFlagsCafae(siniDatosCafae);
        llenarFlagsRobo(siniRobo);

        codRamo_m1 = siniProductoService.obtener(siniPoliza.getNroProducto()).getCodRamo();

        List<SiniProducto> listaProducto = siniProductoService.listar(codRamo_m1);
        listaProductoItem2 = new ArrayList<SelectItem>();
        listaProductoItem2.add(new SelectItem("", "- Seleccionar -"));
        for (SiniProducto p : listaProducto) {
          listaProductoItem2.add(new SelectItem(p.getNroProducto(), p.getNomProducto()));
        }

        if (StringUtils.isNotBlank(siniSiniestro.getCodPais())) {
          /*** lista ubicacion ***************/
          List<GenDepartamento> listaUbicacion = genDepartamentoService.buscar(siniSiniestro.getCodPais());
          listaDepartamentoPaisItem = new ArrayList<SelectItem>();
          listaDepartamentoPaisItem.add(new SelectItem("", "- Seleccionar -"));
          for (GenDepartamento p : listaUbicacion) {
            listaDepartamentoPaisItem.add(new SelectItem(p.getCodDepartamento(), p.getNombreDepartamento()));
          }
        } else {
          listaDepartamentoPaisItem = new ArrayList<SelectItem>();
          listaDepartamentoPaisItem.add(new SelectItem("", "- Seleccionar -"));
        }

        if (StringUtils.isNotBlank(siniSiniestro.getCodDepartamento())) {
          /*** lista provincias ***************/
          List<GenProvincia> listaProvincia = genProvinciaService.buscar(siniSiniestro.getCodDepartamento());
          listaProvinciaItem = new ArrayList<SelectItem>();
          listaProvinciaItem.add(new SelectItem("", "- Seleccionar -"));
          for (GenProvincia p : listaProvincia) {
            listaProvinciaItem.add(new SelectItem(p.getCodProvincia(), p.getNombreProvincia()));
          }
        } else {
          listaProvinciaItem = new ArrayList<SelectItem>();
        }

        if (StringUtils.isNotBlank(siniSiniestro.getCodProvincia())) {
          /*** lista distritos ***************/
          List<GenDistrito> listaDistrito = genDistritoService.buscar(siniSiniestro.getCodProvincia());
          listaDistritoItem = new ArrayList<SelectItem>();
          listaDistritoItem.add(new SelectItem("", "- Seleccionar -"));
          for (GenDistrito d : listaDistrito) {
            listaDistritoItem.add(new SelectItem(d.getCodDistrito(), d.getNombreDistrito()));
          }
        } else {
          listaDistritoItem = new ArrayList<SelectItem>();
        }

        siniSiniestro = siniSiniestroService.listar(pk);
        logger.info(" FECHA APROBACION RECHAZO: " + siniSiniestro.getFecAprobRechazo());

        setObligatorioEstado();
        if (siniDesgravamen != null) {
          vTipoTarjeta_b = siniDesgravamen.getTipTarjeta();
        }
        
        
        calcularDiasNotificacion();
        calcularDiasDocumentacion();
        diastras = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(), siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(), siniSiniestro.getFecAprobRechazo(), siniSiniestro.getFecEntregaOpCont());
        siniSiniestro.setCanDiasPendientes(diastras);
        
        respuesta = "mostrar";

      } else {
        throw new SyncconException(ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO, FacesMessage.SEVERITY_INFO);
      }
    } catch (SyncconException ex) {
      logger.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    logger.info("Fin");
    return respuesta;
  }

	public String buscarSiniestro() {
		logger.info("Inicio");
		selection = new SimpleSelection();
		String respuesta = null;
		SiniSiniestro sinisiniestro = new SiniSiniestro();
		sinisiniestro.setNroSiniestro(getNroSiniestro_b());
		sinisiniestro.setTipDocumAsegurado(getTipoDoc_b());
		sinisiniestro.setNroDocumAsegurado(getNroDocumento_b());
		sinisiniestro.setCodCobertura(getCobAfectada_b());
		sinisiniestro.setNomAsegurado(getNombres_b());
		sinisiniestro.setApePatAsegurado(getApePaterno_b());
		sinisiniestro.setApeMatAsegurado(getApeMaterno_b());
		sinisiniestro.setCodEstado(getEstado_b());
		sinisiniestro.setCodEstLegajo(getEstadoLegajo_b());
		sinisiniestro.setFecUltDocum(getFecUltDocumen_b());
		sinisiniestro.setCodTipSeguro(getTipoSeguro_b());
		varlista = new ArrayList<ConsultaSiniestro>();

		try {

			listaSiniSiniestro = siniSiniestroService.buscar(nroSiniestro_b,
					socio_b, producto_b, nroPoliza_b, tipoDoc_b,
					nroDocumento_b, cobAfectada_b, nombres_b, apePaterno_b,
					apeMaterno_b, estado_b, estadoLegajo_b, fecUltDocumen_b,
					fecUltDocumenDesde_b, fecUltDocumenHasta_b,
					fecAprobRechDesde_b, fecAprobRechHasta_b,
					fecEntrgaOpcDesde_b, fecEntrgaOpcHasta_b, pagoDesde_b,
					pagoHasta_b, nroPlanilla_b, findEjeCafae_b, fecNotiDesde_b,
					fecNotiHasta_b, tipoSeguro_b);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String limpiar() {
		logger.info("Inicio");
		String respuesta = null;
		selection = new SimpleSelection();

		try {
			nroSiniestro_b = null;
			fecNotiDesde_b = null;
			fecNotiHasta_b = null;
			fecUltDocumen_b = null;
			fecUltDocumenDesde_b = null;
			fecUltDocumenHasta_b = null;
			fecAprobRechDesde_b = null;
			fecAprobRechHasta_b = null;
			fecEntrgaOpcDesde_b = null;
			fecEntrgaOpcHasta_b = null;
			pagoDesde_b = null;
			pagoHasta_b = null;
			nroPlanilla_b = null;
			vTipoTarjeta_b = null;
			findEjeCafae_b = null;
			nroPoliza_b = null;
			nroDocumento_b = null;
			nombres_b = null;
			apePaterno_b = null;
			apeMaterno_b = null;
			tipoSeguro_b = null;
			socio_b = null;
			producto_b = null;
			tipoDoc_b = null;
			cobAfectada_b = null;
			estado_b = null;
			estadoLegajo_b = null;
			pagoDesde_b = null;
			pagoHasta_b = null;
			listaSiniSiniestro = new ArrayList<ConsultaSiniestro>();
			listaReporteSiniestro = new ArrayList<ReporteSiniestro>();
			varlistaReporteSiniestro = new ArrayList<ReporteSiniestro>();
			varlista = new ArrayList<ConsultaSiniestro>();

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

		logger.info("Fin");
		return respuesta;
	}

	public String calcularMontoRobo(ActionEvent event) {
		logger.info("Inicio");
		String respuesta = null;

		try {

			siniRobo.setCodSucRetiro("");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String buscarProductos(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = null;
		List<SiniProducto> lista = null;
		listaProductoItem2 = new ArrayList<SelectItem>();
		listaProductoItem2.add(new SelectItem("", "- Seleccionar -"));
		try {
			logger.debug("test: " + (String) event.getNewValue());
			lista = siniProductoService.listar((String) event.getNewValue());
			for (SiniProducto pro : lista) {
				listaProductoItem2.add(new SelectItem(pro.getNroProducto(), pro
						.getNomProducto()));
			}

			siniSiniestro.setValCarencia(null);
			siniSiniestro.setValEdadIngreso(null);
			siniSiniestro.setValEdadPermanencia(null);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String calcularEdadFecOcurrencia(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		SiniValidaciones siniValidaciones = null;
		try {
			Date fecOcurrencia = (Date) event.getNewValue();
			siniSiniestro.setFecOcurrencia(fecOcurrencia);
			if (siniSiniestro.getFecNacimiento() != null
					&& fecOcurrencia != null) {

				LocalDate fechaIni = LocalDate.fromDateFields(siniSiniestro
						.getFecNacimiento());
				LocalDate fechaFin = LocalDate.fromDateFields(fecOcurrencia);
				Years anios = Years.yearsBetween(fechaIni, fechaFin);

				siniSiniestro.setEdaFecOcurrencia(Short.parseShort(String
						.valueOf(anios.getYears())));

			} else {
				siniSiniestro.setEdaFecOcurrencia(null);

			}

			if (siniPoliza.getCodSocio() != null
					&& siniPoliza.getNroProducto() != null) {
				siniValidaciones = siniValidacionesService.obtener(
						siniPoliza.getNroProducto(), siniPoliza.getCodSocio());
				if (siniValidaciones != null) {
					// Validacion Edad de Ingreso
					logger.debug("siniSiniestro.getFecNacimiento(): "
							+ siniSiniestro.getFecNacimiento());
					logger.debug("siniPoliza.getFecIniVigencia(): "
							+ siniPoliza.getFecIniVigencia());
					if (siniSiniestro.getFecNacimiento() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniSiniestro
										.getFecNacimiento());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						Years anios = Years.yearsBetween(fechaIni, fechaFin);
						if (siniValidaciones.getEdaIngMin() <= anios.getYears()
								&& anios.getYears() <= siniValidaciones
										.getEdaIngMax()) {
							siniSiniestro.setValEdadIngreso("OK");
						} else {
							siniSiniestro.setValEdadIngreso("ERROR");
						}
					} else {
						siniSiniestro.setValEdadIngreso(null);
					}
					// Validacion Edad de Permanencia
					if (siniSiniestro.getEdaFecOcurrencia() != null) {
						if (siniSiniestro.getEdaFecOcurrencia() <= siniValidaciones
								.getEdaPermanencia()) {
							siniSiniestro.setValEdadPermanencia("OK");
						} else {
							siniSiniestro.setValEdadPermanencia("ERROR");
						}
					} else {
						siniSiniestro.setValEdadPermanencia(null);
					}
					// Validación Carencia
					if (siniSiniestro.getFecOcurrencia() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						LocalDate fechaFin = LocalDate
								.fromDateFields(fecOcurrencia);
						Days dias = Days.daysBetween(fechaIni, fechaFin);
						if (siniValidaciones.getNroDiasCarencia() == 0) {
							siniSiniestro.setValCarencia("OK");
						} else if (siniValidaciones.getNroDiasCarencia() > 0
								&& dias.getDays() > siniValidaciones
										.getNroDiasCarencia()) {
							siniSiniestro.setValCarencia("OK");
						} else {
							siniSiniestro.setValCarencia("ERROR");
						}
					} else {
						siniSiniestro.setValCarencia(null);
					}
				} else {
					siniSiniestro.setValEdadIngreso("OK");
					siniSiniestro.setValCarencia("OK");
					siniSiniestro.setValEdadPermanencia("OK");
				}
			} else {
				siniSiniestro.setValEdadIngreso(null);
				siniSiniestro.setValCarencia(null);
				siniSiniestro.setValEdadPermanencia(null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String validarFecNacimiento(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		SiniValidaciones siniValidaciones = null;
		try {
			Date fecNacimiento = (Date) event.getNewValue();
			siniSiniestro.setFecNacimiento(fecNacimiento);
			if (siniSiniestro.getFecNacimiento() != null
					&& siniSiniestro.getFecOcurrencia() != null) {
				LocalDate fechaIni = LocalDate.fromDateFields(siniSiniestro
						.getFecNacimiento());
				LocalDate fechaFin = LocalDate.fromDateFields(siniSiniestro
						.getFecOcurrencia());
				Years anios = Years.yearsBetween(fechaIni, fechaFin);

				siniSiniestro.setEdaFecOcurrencia(Short.parseShort(String
						.valueOf(anios.getYears())));

			} else {
				siniSiniestro.setEdaFecOcurrencia(null);

			}

			if (siniPoliza.getCodSocio() != null
					&& siniPoliza.getNroProducto() != null) {
				siniValidaciones = siniValidacionesService.obtener(
						siniPoliza.getNroProducto(), siniPoliza.getCodSocio());
				if (siniValidaciones != null) {
					// Validacion Edad de Ingreso
					logger.debug("siniSiniestro.getFecNacimiento(): "
							+ siniSiniestro.getFecNacimiento());
					logger.debug("siniPoliza.getFecIniVigencia(): "
							+ siniPoliza.getFecIniVigencia());
					if (siniSiniestro.getFecNacimiento() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniSiniestro
										.getFecNacimiento());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						Years anios = Years.yearsBetween(fechaIni, fechaFin);
						if (siniValidaciones.getEdaIngMin() <= anios.getYears()
								&& anios.getYears() <= siniValidaciones
										.getEdaIngMax()) {
							siniSiniestro.setValEdadIngreso("OK");
						} else {
							siniSiniestro.setValEdadIngreso("ERROR");
						}
					} else {
						siniSiniestro.setValEdadIngreso(null);
					}
					// Validacion Edad de Permanencia
					if (siniSiniestro.getEdaFecOcurrencia() != null) {
						if (siniSiniestro.getEdaFecOcurrencia() <= siniValidaciones
								.getEdaPermanencia()) {
							siniSiniestro.setValEdadPermanencia("OK");
						} else {
							siniSiniestro.setValEdadPermanencia("ERROR");
						}
					} else {
						siniSiniestro.setValEdadPermanencia(null);
					}
				} else {
					siniSiniestro.setValEdadIngreso("OK");
					siniSiniestro.setValEdadPermanencia("OK");
				}
			} else {
				siniSiniestro.setValEdadIngreso(null);
				siniSiniestro.setValEdadPermanencia(null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String validarCarencia(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = null;
		SiniValidaciones siniValidaciones = null;
		try {
			Date fecIniVigencia = (Date) event.getNewValue();
			siniPoliza.setFecIniVigencia(fecIniVigencia);
			if (siniPoliza.getCodSocio() != null
					&& siniPoliza.getNroProducto() != null) {
				siniValidaciones = siniValidacionesService.obtener(
						siniPoliza.getNroProducto(), siniPoliza.getCodSocio());
				if (siniValidaciones != null) {
					// Validacion Edad de Ingreso
					logger.debug("siniSiniestro.getFecNacimiento(): "
							+ siniSiniestro.getFecNacimiento());
					logger.debug("siniPoliza.getFecIniVigencia(): "
							+ siniPoliza.getFecIniVigencia());
					if (siniSiniestro.getFecNacimiento() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniSiniestro
										.getFecNacimiento());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						Years anios = Years.yearsBetween(fechaIni, fechaFin);
						if (siniValidaciones.getEdaIngMin() <= anios.getYears()
								&& anios.getYears() <= siniValidaciones
										.getEdaIngMax()) {
							siniSiniestro.setValEdadIngreso("OK");
						} else {
							siniSiniestro.setValEdadIngreso("ERROR");
						}
					} else {
						siniSiniestro.setValEdadIngreso(null);
					}
					// Validacion Carencia
					logger.debug("siniSiniestro.getFecOcurrencia(): "
							+ siniSiniestro.getFecOcurrencia());
					logger.debug("siniPoliza.getFecIniVigencia(): "
							+ siniPoliza.getFecIniVigencia());
					if (siniSiniestro.getFecOcurrencia() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniSiniestro
										.getFecOcurrencia());
						Days dias = Days.daysBetween(fechaIni, fechaFin);
						if (siniValidaciones.getNroDiasCarencia() == 0) {
							siniSiniestro.setValCarencia("OK");
						} else if (siniValidaciones.getNroDiasCarencia() > 0
								&& dias.getDays() > siniValidaciones
										.getNroDiasCarencia()) {
							siniSiniestro.setValCarencia("OK");
						} else {
							siniSiniestro.setValCarencia("ERROR");
						}
					} else {
						siniSiniestro.setValCarencia(null);
					}
				} else {
					siniSiniestro.setValEdadIngreso("OK");
					siniSiniestro.setValCarencia("OK");
				}
			} else {
				siniSiniestro.setValEdadIngreso(null);
				siniSiniestro.setValCarencia(null);
			}
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String buscarDepartamento(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";

		List<GenDepartamento> lista = null;
		listaDepartamentoPaisItem = new ArrayList<SelectItem>();
		listaDepartamentoPaisItem.add(new SelectItem("", "- Seleccionar -"));
		try {
			// seleccion de combo anidado en destiempo
			if (event.getNewValue() != null) {
				lista = genDepartamentoService.buscar((String) event
						.getNewValue());

				for (GenDepartamento departamento : lista) {
					listaDepartamentoPaisItem.add(new SelectItem(departamento
							.getCodDepartamento(), departamento
							.getNombreDepartamento()));
				}
			} else {
				listaDepartamentoPaisItem = new ArrayList<SelectItem>();
				listaDepartamentoPaisItem.add(new SelectItem("",
						"- Seleccionar -"));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String buscarProvincia(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";

		List<GenProvincia> lista = null;
		listaProvinciaItem = new ArrayList<SelectItem>();
		listaProvinciaItem.add(new SelectItem("", "- Seleccionar -"));
		try {
			// seleccion de combo anidado en destiempo
			if (event.getNewValue() != null) {
				lista = genProvinciaService
						.buscar((String) event.getNewValue());

				for (GenProvincia provincia : lista) {
					listaProvinciaItem
							.add(new SelectItem(provincia.getCodProvincia(),
									provincia.getNombreProvincia()));
				}
			} else {
				listaDistritoItem = new ArrayList<SelectItem>();
				listaDistritoItem.add(new SelectItem("", "- Seleccionar -"));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String buscarDistrito(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = null;
		List<GenDistrito> lista = null;
		listaDistritoItem = new ArrayList<SelectItem>();
		listaDistritoItem.add(new SelectItem("", "- Seleccionar -"));
		try {

			if (event.getNewValue() != null) {
				lista = genDistritoService.buscar((String) event.getNewValue());
				for (GenDistrito dist : lista) {
					listaDistritoItem.add(new SelectItem(dist.getCodDistrito(),
							dist.getNombreDistrito()));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String generarPlantilla1() {
		logger.debug("Inicio");
		String respuesta = null;
		try {
			iniciaGeneraPlantilla("PlantillaAnalisis.jasper");

			params.put("nroSiniestro", siniSiniestro.getNroSiniestro());
			params.put("fRecepcion",
					formatearFecha((siniSiniestro.getFecUltDocum())));
			params.put("socio", parametroService.obtenerDescripcion(
					siniPoliza.getCodSocio(), Constantes.COD_PARAM_SOCIOS_SINI,
					"D"));

			params.put("producto",
					siniProductoService.obtener(siniPoliza.getNroProducto())
							.getNomProducto());

			params.put("coberturas", parametroService.obtenerDescripcion(
					siniSiniestro.getCodCobertura(),
					Constantes.COD_PARAM_LISTA_COBERTURA,
					Constantes.TIP_PARAM_DETALLE));
			params.put("plan", siniPoliza.getPlanPoliza());

			params.put("inicioVigencia",
					formatearFecha(siniPoliza.getFecIniVigencia()));

			params.put("ejecutivo", parametroService.obtenerDescripcion(
					siniDatosCafae.getEjeCafae(),
					Constantes.COD_PARAM_LISTA_EJECUTIVOS, "D"));

			if (siniSiniestro.getImpReserva() != null) {
				params.put("impReserva", formatearMonto(String
						.valueOf(siniSiniestro.getImpReserva().doubleValue())));
			} else {
				params.put("impReserva", "0.00");
			}

			String tipRefCafae = StringUtils.isBlank(siniDatosCafae
					.getTipRefCafae()) ? "" : siniDatosCafae.getTipRefCafae();
			tipRefCafae = "CON".equals(siniDatosCafae.getTipRefCafae()) ? "C -"
					: siniDatosCafae.getTipRefCafae();

			String ubicacion = siniSiniestro.getCodUbicacion();
			if (ubicacion != null && ubicacion.length() > 0) {
				params.put(
						"ubicacion",
						genDepartamentoService.obtener(
								siniSiniestro.getCodUbicacion())
								.getNombreDepartamento());
			} else {
				params.put("ubicacion", "");
			}

			params.put("ref", tipRefCafae + " " + siniDatosCafae.getRefCafae());
			params.put(
					"asegurado",
					siniSiniestro.getApePatAsegurado() + " "
							+ siniSiniestro.getApeMatAsegurado() + ", "
							+ siniSiniestro.getNomAsegurado() + " ");

			params.put("parentesco", parametroService.obtenerDescripcion(
					siniSiniestro.getCodParAsegurado(),
					Constantes.COD_PARAM_LISTA_PARENTESCO_SINI, "D"));
			params.put("fNacimiento",
					formatearFecha(siniSiniestro.getFecNacimiento()));
			params.put("fOcurrencia",
					formatearFecha(siniSiniestro.getFecOcurrencia()));

			params.put(
					"edadAfiliacion",
					calcularEdad(siniSiniestro.getFecNacimiento(),
							siniPoliza.getFecIniVigencia()));
			params.put(
					"edadOcurrencia",
					calcularEdad(siniSiniestro.getFecNacimiento(),
							siniSiniestro.getFecOcurrencia()));
			params.put("fNotCardif",
					formatearFecha(siniSiniestro.getFecNotificacion()));
			params.put("fNotSocio",
					formatearFecha(siniSiniestro.getFecRecepSocio()));
			params.put("diasFNot", getStrDiasNotificacion());
			// params.put("fUltDocCardif", siniSiniestro.getFecUltDocum());
			params.put("fUltDocSocio",
					formatearFecha(siniSiniestro.getFecUltDocumSocio()));
			params.put("diasUltDoc", getStrDiasDocumentacion());
			params.put("candiasp",
					String.valueOf(siniSiniestro.getCanDiasPendientes()));

			String nombrePlantilla = "PLANTILLA_ANALISIS_"
					+ siniSiniestro.getNroSiniestro();
			terminaGeneraPlantilla(nombrePlantilla);

			/*
			 * } catch (SyncconException ex) { log.error(ERROR_SYNCCON +
			 * ex.getMessageComplete()); FacesMessage facesMsg = new
			 * FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
			 * FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			 */
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		logger.info("Fin");

		return respuesta;
	}

	private String calcularEdad(Date fechaInicial, Date fechaTope)
			throws Exception {
		String edad = "";

		LocalDate fechaInicials = LocalDate.fromDateFields(fechaInicial);
		LocalDate fechaTopes = LocalDate.fromDateFields(fechaTope);
		Months age = Months.monthsBetween(fechaInicials, fechaTopes);

		edad = age.getMonths() / 12 + "." + age.getMonths() % 12;
		int anios = age.getMonths() / 12;
		int meses = age.getMonths() % 12;
		if (meses != 0) {
			edad = (meses < 10) ? anios + ".0" + meses : anios + "." + meses;
		} else {
			edad = anios + ".00";
		}

		return edad;
	}

	public String generarPlantilla2() {
		logger.debug("Inicio");
		String respuesta = null;
		try {
			iniciaGeneraPlantilla("PlantillaLiquidacion.jasper");

			params.put("producto",
					siniProductoService.obtener(siniPoliza.getNroProducto())
							.getNomProducto());
			params.put("nSiniestro", siniSiniestro.getNroSiniestro());
			params.put("nPlantilla", siniSiniestro.getNroPlanilla());
			params.put("fLiquidacion",
					formatearFecha(siniSiniestro.getFecAprobRechazo()));
			params.put("fUltimaNotificacion",
					formatearFecha(siniSiniestro.getFecUltDocum()));
			params.put("socio", parametroService.obtenerDescripcion(
					siniPoliza.getCodSocio(), Constantes.COD_PARAM_SOCIOS_SINI,
					"D"));
			params.put("nroPoliza", siniPoliza.getNroPoliza());
			String tipRefCafae = StringUtils.isBlank(siniDatosCafae
					.getTipRefCafae()) ? "" : siniDatosCafae.getTipRefCafae();
			tipRefCafae = "CON".equals(siniDatosCafae.getTipRefCafae()) ? "C -"
					: siniDatosCafae.getTipRefCafae();
			params.put("contExp",
					tipRefCafae + " " + siniDatosCafae.getRefCafae());
			params.put("cobertura", parametroService.obtenerDescripcion(
					siniSiniestro.getCodCobertura(),
					Constantes.COD_PARAM_LISTA_COBERTURA, "D"));
			params.put("plan", siniPoliza.getPlanPoliza());
			params.put("inicioVig",
					formatearFecha(siniPoliza.getFecIniVigencia()));
			params.put("nombre", siniSiniestro.getApePatAsegurado() + "  "
					+ siniSiniestro.getApeMatAsegurado() + ",  "
					+ siniSiniestro.getNomAsegurado() + " ");
			params.put("dni", siniSiniestro.getNroDocumAsegurado());
			// params.put("fOcurrencia",
			// formatearFecha(listaSiniSiniestro.get(0).getFecOcurrencia()));
			params.put("nacionalidad", siniSiniestro.getNacionalidad());
			params.put("fOcurrencia",
					formatearFecha(siniSiniestro.getFecOcurrencia()));
			params.put("parentesco", parametroService.obtenerDescripcion(
					siniSiniestro.getCodParAsegurado(),
					Constantes.COD_PARAM_LISTA_PARENTESCO_SINI, "D"));
			params.put("diagnostico", siniSiniestro.getDiagnostico());
			params.put("cie10", siniSiniestro.getCie10());
			params.put("beneficiario", siniDatosCafae.getNomBeneficiario());
			params.put("parentescoBeneficiario", parametroService
					.obtenerDescripcion(siniDatosCafae.getParentescoBenef(),
							Constantes.COD_PARAM_LISTA_PARENTESCO_SINI, "D"));
			if (siniSiniestro.getImpPagos() != null) {
				params.put("importeIndemnizar", formatearMonto(siniSiniestro
						.getImpPagos().toString()));
			} else
				params.put("importeIndemnizar", "0.00");

			if (siniSiniestro.getImpPagos() != null) {
				params.put("totalIndemnizar", formatearMonto(siniSiniestro
						.getImpPagos().toString()));
			} else
				params.put("totalIndemnizar", "0.00");

			params.put("moneda", parametroService.obtenerDescripcion(
					siniSiniestro.getCodMoneda(),
					Constantes.COD_PARAM_LISTA_MONEDA, "D"));
			params.put("socio", parametroService.obtenerDescripcion(
					siniPoliza.getCodSocio(), Constantes.COD_PARAM_SOCIOS_SINI,
					"D"));
			params.put("fSiniestro",
					formatearFechaTexto(siniSiniestro.getFecOcurrencia()));

			String nombrePlantilla = "PLANTILLA_LIQUIDACION_"
					+ siniSiniestro.getNroSiniestro();
			terminaGeneraPlantilla(nombrePlantilla);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		logger.info("Fin");

		return respuesta;
	}

	public String validarSocio(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		String codSocio = "";
		SiniValidaciones siniValidaciones = null;
		siniSiniestro.setNacionalidad("ECUADOR");
		try {
			codSocio = (String) event.getNewValue();

			if (codSocio.equalsIgnoreCase("CF")) {
				setRequiredCafae(true);
			} else {
				setRequiredCafae(false);
			}
			siniPoliza.setCodSocio(codSocio);
			if (codSocio != null && siniPoliza.getNroProducto() != null) {
				siniValidaciones = siniValidacionesService.obtener(
						siniPoliza.getNroProducto(), siniPoliza.getCodSocio());
				if (siniValidaciones != null) {

					if (siniSiniestro.getFecNacimiento() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniSiniestro
										.getFecNacimiento());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						Years anios = Years.yearsBetween(fechaIni, fechaFin);
						if (siniValidaciones.getEdaIngMin() <= anios.getYears()
								&& anios.getYears() <= siniValidaciones
										.getEdaIngMax()) {
							siniSiniestro.setValEdadIngreso("OK");
						} else {
							siniSiniestro.setValEdadIngreso("ERROR");
						}
					}

					if (siniSiniestro.getEdaFecOcurrencia() != null) {

						if (siniSiniestro.getEdaFecOcurrencia() <= siniValidaciones
								.getEdaPermanencia()) {
							siniSiniestro.setValEdadPermanencia("OK");
						} else {
							siniSiniestro.setValEdadPermanencia("ERROR");
						}
					}

					if (siniSiniestro.getFecOcurrencia() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniSiniestro
										.getFecOcurrencia());
						Days dias = Days.daysBetween(fechaIni, fechaFin);

						if (siniValidaciones.getNroDiasCarencia() == 0) {
							siniSiniestro.setValCarencia("OK");
						} else if (siniValidaciones.getNroDiasCarencia() > 0
								&& dias.getDays() > siniValidaciones
										.getNroDiasCarencia()) {
							siniSiniestro.setValCarencia("OK");
						} else {
							siniSiniestro.setValCarencia("ERROR");
						}
					}
				} else {
					siniSiniestro.setValEdadIngreso("OK");
					siniSiniestro.setValCarencia("OK");
					siniSiniestro.setValEdadPermanencia("OK");
				}
			} else {
				siniSiniestro.setValEdadIngreso(null);
				siniSiniestro.setValCarencia(null);
				siniSiniestro.setValEdadPermanencia(null);
			}
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String validarProducto(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		String nroProducto = "";
		SiniValidaciones siniValidaciones = null;
		try {
			nroProducto = (String) event.getNewValue();
			siniPoliza.setNroProducto(nroProducto);
			// FIXME: JCAT ELIMINAR CODIGO DURO
			if (nroProducto.equalsIgnoreCase("610101")) {
				setRequiredRentaHosp(true);
			} else {
				setRequiredRentaHosp(false);
			}

			// FIXME: JCAT ELIMINAR CODIGO DURO
			if (nroProducto.equalsIgnoreCase("590103")
					|| nroProducto.equalsIgnoreCase("590104")
					|| nroProducto.equalsIgnoreCase("590105")
					|| nroProducto.equalsIgnoreCase("590106")
					|| nroProducto.equalsIgnoreCase("590107")
					|| nroProducto.equalsIgnoreCase("590108")
					|| nroProducto.equalsIgnoreCase("590109")) {
				setRequiredDesemp(true);
			} else {
				setRequiredDesemp(false);
			}
			siniPoliza.setNroProducto(nroProducto);
			if (nroProducto != null && siniPoliza.getCodSocio() != null) {
				siniValidaciones = siniValidacionesService.obtener(
						siniPoliza.getNroProducto(), siniPoliza.getCodSocio());
				if (siniValidaciones != null) {

					if (siniSiniestro.getFecNacimiento() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniSiniestro
										.getFecNacimiento());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						Years anios = Years.yearsBetween(fechaIni, fechaFin);
						if (siniValidaciones.getEdaIngMin() <= anios.getYears()
								&& anios.getYears() <= siniValidaciones
										.getEdaIngMax()) {
							siniSiniestro.setValEdadIngreso("OK");
						} else {
							siniSiniestro.setValEdadIngreso("ERROR");
						}
					}
					// Validacion Edad de Permanencia
					if (siniSiniestro.getEdaFecOcurrencia() != null) {
						if (siniSiniestro.getEdaFecOcurrencia() <= siniValidaciones
								.getEdaPermanencia()) {
							siniSiniestro.setValEdadPermanencia("OK");
						} else {
							siniSiniestro.setValEdadPermanencia("ERROR");
						}
					}
					// Validacion Carencia
					if (siniSiniestro.getFecOcurrencia() != null
							&& siniPoliza.getFecIniVigencia() != null) {
						LocalDate fechaIni = LocalDate
								.fromDateFields(siniPoliza.getFecIniVigencia());
						LocalDate fechaFin = LocalDate
								.fromDateFields(siniSiniestro
										.getFecOcurrencia());
						Days dias = Days.daysBetween(fechaIni, fechaFin);
						if (siniValidaciones.getNroDiasCarencia() == 0) {
							siniSiniestro.setValCarencia("OK");
						} else if (siniValidaciones.getNroDiasCarencia() > 0
								&& dias.getDays() > siniValidaciones
										.getNroDiasCarencia()) {
							siniSiniestro.setValCarencia("OK");
						} else {
							siniSiniestro.setValCarencia("ERROR");
						}
					}
				} else {
					siniSiniestro.setValEdadIngreso("OK");
					siniSiniestro.setValCarencia("OK");
					siniSiniestro.setValEdadPermanencia("OK");
				}
			} else {
				siniSiniestro.setValEdadIngreso(null);
				siniSiniestro.setValCarencia(null);
				siniSiniestro.setValEdadPermanencia(null);

			}
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String copiarSiniestro() {
		logger.info("Inicio");
		String respuesta = "";
		String pk = null;
		try {
			Iterator<Object> iterator = null;
			iterator = getSelection().getKeys();

			if (iterator.hasNext()) {
				int key = (Integer) iterator.next();
				pk = listaSiniSiniestro.get(key).getNroSiniestro();
			}
			if (pk != null) {

				mostrarSiniestro();
				respuesta = "mostrar";
				siniSiniestro.setNroSiniestro("");
				siniSiniestro.setNroPlanilla(null);
				esNuevo = true;
				readonlyProducto = false;
				setReadonlyFecha(false);

			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO,
						FacesMessage.SEVERITY_INFO);
			}
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;

	}

	public String verSiniestro() {
		logger.info("Inicio");
		String respuesta = "";
		String pk = null;
		try {

			verSiniestro = true;
			Iterator<Object> iterator = null;
			iterator = getSelection().getKeys();
			if (iterator.hasNext()) {
				int key = (Integer) iterator.next();
				pk = listaSiniSiniestro.get(key).getNroSiniestro();
			}
			if (pk != null) {
				mostrarSiniestro();
				respuesta = "mostrar";
				readonlyProducto = true;
				readonlyMostrar = true;
				setReadonlyFecha(true);
				styleVerSiniestro = "background:#ddd; color:black;";
			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO,
						FacesMessage.SEVERITY_INFO);
			}
		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		calcularDiasDocumentacion();
		calcularDiasNotificacion();

		logger.info("Fin");
		return respuesta;

	}

	public int getDiasHabiles(Calendar fechaInicial, Calendar fechaFinal) {

		int diffDays = 0;
		while (fechaInicial.before(fechaFinal)
				|| fechaInicial.equals(fechaFinal)) {
			if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
					&& fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				diffDays++;
			}
			fechaInicial.add(Calendar.DATE, 1);
		}
		return diffDays;
	}

	public void selectionListener(ActionEvent event) {
		Iterator<Object> iterator = null;
		String pk = null;
		iterator = getSelection().getKeys(); // getSiniestroSelection().getKeys();//
		String valorCheck = (String) event.getComponent().getAttributes()
				.get("selectedSiniestro");

		List<ConsultaSiniestro> siniestroSeleccionados = new ArrayList<ConsultaSiniestro>();
		for (ConsultaSiniestro cheque : listaSiniSiniestro) {
			if (cheque.isIdCheck()) {
				siniestroSeleccionados.add(cheque);
			}
		}
		varlista = siniestroSeleccionados;

	}

	public String generarPlantilla3() {
		logger.debug("Inicio");
		String respuesta = "";
		String delete = "delete";
		mensaje = "";

		List<ConsultaSiniestro> listaSiniPlantilla = null;
		ArrayList<ConsultaSiniestro> selectedItems = new ArrayList<ConsultaSiniestro>();
		try {

			logger.info("selected: " + selected);

			// 1. VALIDA CANTIDAD DE SELECCIONADOS
			boolean existe_planilla = false;
			int n = 0;
			// Iterator<Object> iterator1 = getSelection().getKeys();
			/*
			 * Iterator<Object> iterator1 = null; iterator1
			 * =getSelection().getKeys();
			 * 
			 * if (!iterator1.hasNext()) { throw new
			 * SyncconException(ErrorConstants
			 * .COD_ERROR_SINIESTROS_NO_SELECCIONADOS,
			 * FacesMessage.SEVERITY_INFO); }
			 */

			if (varlista == null || varlista.size() == 0) {
				// throw new
				// SyncconException(ErrorConstants.COD_ERROR_SINIESTROS_NRO_PLANILLAS_CERO,
				// FacesMessage.SEVERITY_INFO);
				mensaje = ErrorConstants.COD_ERROR_SINIESTROS_NRO_PLANILLAS_CERO;
				return respuesta;
			}

			for (int x = 0; x < varlista.size(); x++) {

				if (varlista.size() == 0) {
					mensaje = ErrorConstants.COD_ERROR_SINIESTROS_NO_SELECCIONADOS;
					return respuesta;
					// throw new
					// SyncconException(ErrorConstants.COD_ERROR_SINIESTROS_NO_SELECCIONADOS,
					// FacesMessage.SEVERITY_INFO);
				}

				if (StringUtils.isNotBlank(varlista.get(x).getNroPlanilla())) {
					existe_planilla = true;
					logger.info(StringUtils.isNotBlank(varlista.get(x)
							.getNroPlanilla()));
					// existe_planilla = false;
				}
				n++;

			}

			if (n > 10) {
				mensaje = ErrorConstants.COD_ERROR_SINIESTROS_NRO_PLANILLAS;
				return respuesta;
				// throw new
				// SyncconException(ErrorConstants.COD_ERROR_SINIESTROS_NRO_PLANILLAS,
				// FacesMessage.SEVERITY_INFO);
			}
			// 2. VALIDA NRO_PLANILLA
			if (existe_planilla) {

				mensaje = ErrorConstants.COD_ERROR_SINIESTROS_CON_NRO_PLANILLA;
				return respuesta;
				// throw new
				// SyncconException(ErrorConstants.COD_ERROR_SINIESTROS_CON_NRO_PLANILLA,
				// FacesMessage.SEVERITY_INFO);
			}

			// Iterator<Object> iterator2 = getSelection().getKeys();
			listaSiniPlantilla = new ArrayList<ConsultaSiniestro>();
			ConsultaSiniestro siniestro1 = new ConsultaSiniestro();
			String estado = null;
			String nroPlanilla = null;
			String nro_siniestro = null;
			n = 0;

			/*
			 * while (iterator2.hasNext()) { int key = (Integer)
			 * iterator2.next();
			 */
			for (int x = 0; x < varlista.size(); x++) {

				// nro_siniestro =
				// listaSiniSiniestro.get(key).getNroSiniestro();
				nro_siniestro = varlista.get(x).getNroSiniestro();
				siniSiniestro = siniSiniestroService.listar(nro_siniestro);
				// estado =
				// siniSiniestroService.listar(listaSiniSiniestro.get(key).getNroSiniestro()).getCodEstado();
				estado = siniSiniestroService.listar(
						varlista.get(x).getNroSiniestro()).getCodEstado();

				if (n == 0) {// para el 1er elemento

					// 3. VALIDA ESTADO 1
					if (!(estado.equals("2") || estado.equals("3")
							|| estado.equals("7") || estado.equals("13"))) {
						mensaje = ErrorConstants.COD_ERROR_SINIESTROS_NO_APROBADOS;
						return respuesta;

						// throw new
						// SyncconException(ErrorConstants.COD_ERROR_SINIESTROS_NO_APROBADOS,
						// FacesMessage.SEVERITY_INFO);
					}
					// Para comparar cuando se tenga varios siniestros
					// siniestro1 = listaSiniSiniestro.get(key);
					siniestro1 = varlista.get(x);

					siniPoliza = siniPolizaService.listar(nro_siniestro);
					nroPlanilla = siniSiniestroService
							.obtenerNroPlanilla(siniPoliza.getCodSocio());

				} else {
					// 4. VALIDA NOMBRE DE SOCIO
					// if
					// (!siniestro1.getNomSocio().equals(listaSiniSiniestro.get(key).getNomSocio()))
					// {
					if (!siniestro1.getNomSocio().equals(
							varlista.get(x).getNomSocio())) {
						mensaje = ErrorConstants.COD_ERROR_NO_COINCIDEN_SOCIOS;
						return respuesta;
						// throw new
						// SyncconException(ErrorConstants.COD_ERROR_NO_COINCIDEN_SOCIOS,
						// FacesMessage.SEVERITY_INFO);
					}
					// 5. VALIDA PRODUCTO
					// if
					// (!siniestro1.getNomProducto().equals(listaSiniSiniestro.get(key).getNomProducto()))
					// {
					if (!siniestro1.getNomProducto().equals(
							varlista.get(x).getNomProducto())) {
						mensaje = ErrorConstants.COD_ERROR_NO_COINCIDEN_PRODUCTOS;
						return respuesta;
						// throw new
						// SyncconException(ErrorConstants.COD_ERROR_NO_COINCIDEN_PRODUCTOS,
						// FacesMessage.SEVERITY_INFO);
					}
					// 6. VALIDA POLIZA
					// if
					// (!siniestro1.getNroPoliza().equals(listaSiniSiniestro.get(key).getNroPoliza()))
					// {
					if (!siniestro1.getNroPoliza().equals(
							varlista.get(x).getNroPoliza())) {
						mensaje = ErrorConstants.COD_ERROR_NO_COINCIDEN_POLIZAS;
						return respuesta;
						// throw new
						// SyncconException(ErrorConstants.COD_ERROR_NO_COINCIDEN_POLIZAS,
						// FacesMessage.SEVERITY_INFO);
					}
					// 3. VALIDA ESTADO 2
					if (!(estado.equals("2") || estado.equals("3")
							|| estado.equals("7") || estado.equals("13"))) {
						mensaje = ErrorConstants.COD_ERROR_SINIESTROS_NO_APROBADOS;
						return respuesta;
						// throw new
						// SyncconException(ErrorConstants.COD_ERROR_SINIESTROS_NO_APROBADOS,
						// FacesMessage.SEVERITY_INFO);
					}
				}
				n++;
			}

			// SI HA PASADO TODAS LAS VALIDACIONES RECIEN GENERA PLANILLA CON
			// NUMERO DEL 1RO
			// Iterator<Object> iterator3 = getSelection().getKeys();
			listaSiniPlantilla = new ArrayList<ConsultaSiniestro>();

			/*
			 * while (iterator3.hasNext()) { int key = (Integer)
			 * iterator3.next();
			 */
			for (int x = 0; x < varlista.size(); x++) {

				/*
				 * listaSiniPlantilla.add(listaSiniSiniestro.get(key));
				 * nro_siniestro =
				 * listaSiniSiniestro.get(key).getNroSiniestro();
				 */
				listaSiniPlantilla.add(varlista.get(x));
				nro_siniestro = varlista.get(x).getNroSiniestro();
				siniSiniestro = siniSiniestroService.listar(nro_siniestro);
				siniSiniestro.setNroPlanilla(nroPlanilla);
				siniSiniestroService.actualizarSiniSiniestro(siniSiniestro);
			}

			siniSiniestro = siniSiniestroService.listar(listaSiniPlantilla.get(
					0).getNroSiniestro());
			siniDatosCafae = siniDatosCafaeService.listar(listaSiniPlantilla
					.get(0).getNroSiniestro());
			siniPoliza = siniPolizaService.listar(listaSiniPlantilla.get(0)
					.getNroSiniestro());

			SiniestroDataSourceService siniestroDataSourceService = new SiniestroDataSourceService(
					listaSiniPlantilla);
			siniestroDataSourceService.setSiniDatosCafae(siniDatosCafae);

			total = new BigDecimal("0.00");

			for (ConsultaSiniestro csini : listaSiniPlantilla) {
				SiniSiniestro sini = siniSiniestroService.listar(csini
						.getNroSiniestro());
				sini.setUsuModificacion(SecurityContextHolder.getContext()
						.getAuthentication().getName());
				sini.setNroPlanilla(nroPlanilla);
				siniSiniestroService.actualizarSiniSiniestro(sini);
				if (sini.getImpPagos() != null) {
					total = total.add(sini.getImpPagos());
				}
			}

			listaSiniSiniestro = siniSiniestroService.buscar(nroSiniestro_b,
					socio_b, producto_b, nroPoliza_b, tipoDoc_b,
					nroDocumento_b, cobAfectada_b, nombres_b, apePaterno_b,
					apeMaterno_b, estado_b, estadoLegajo_b, fecUltDocumen_b,
					fecUltDocumenDesde_b, fecUltDocumenHasta_b,
					fecAprobRechDesde_b, fecAprobRechHasta_b,
					fecEntrgaOpcDesde_b, fecEntrgaOpcHasta_b, pagoDesde_b,
					pagoHasta_b, nroPlanilla_b, findEjeCafae_b, fecNotiDesde_b,
					fecNotiHasta_b, tipoSeguro_b);

			// terminaGeneraPlantilla(nombrePlantilla);

		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}

		logger.info("Fin");

		return respuesta;
	}

	private static String formatearMonto(String monto) {
		double valor = Double.valueOf(monto).doubleValue();
		DecimalFormat formato = new DecimalFormat("#,###,###,##0.00");
		String valorFormateado = formato.format(valor);
		String miles = "";
		if (valor >= 1000000) {
			miles = StringUtils.right(valorFormateado, 10);
			String resto = valorFormateado.substring(0,
					valorFormateado.length() - 10);
			resto = resto.replace(
					valorFormateado.charAt(valorFormateado.length() - 7), '\'');
			valorFormateado = resto + miles;
		}
		return valorFormateado;
	}

	private void iniciaGeneraPlantilla(String planilla) throws Exception {

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fc.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();
		ServletContext sc = (ServletContext) externalContext.getContext();
		InputStream jasperStream = externalContext
				.getResourceAsStream("/jasper/" + planilla);
		out = response.getOutputStream();
		report = (JasperReport) JRLoader.loadObject(jasperStream);
		params = new HashMap<String, Object>();
		params.put("img",
				sc.getRealPath(File.separator + "imagenes\\logoCardif.jpg"));

	}

	public String exportarTodo() {
		logger.info("Inicio");
		String respuesta = "";

		Map<String, Object> beans = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		ExternalContext contexto = FacesContext.getCurrentInstance()
				.getExternalContext();
		String FechaActual = sdf.format(new Date(System.currentTimeMillis()));

		try {

			if (listaSiniSiniestro.size() > 0) {
				listaReporteSiniestro = siniSiniestroService.buscarReporte(
						nroSiniestro_b, socio_b, producto_b, nroPoliza_b,
						tipoDoc_b, nroDocumento_b, cobAfectada_b, nombres_b,
						apePaterno_b, apeMaterno_b, estado_b, estadoLegajo_b,
						fecUltDocumen_b, fecUltDocumenDesde_b,
						fecUltDocumenHasta_b, fecAprobRechDesde_b,
						fecAprobRechHasta_b, fecEntrgaOpcDesde_b,
						fecEntrgaOpcHasta_b, pagoDesde_b, pagoHasta_b,
						nroPlanilla_b, findEjeCafae_b, fecNotiDesde_b,
						fecNotiHasta_b, tipoSeguro_b);

				String rutaTemp = System.getProperty("java.io.tmpdir")
						+ "Reporte_Clientes_" + System.currentTimeMillis()
						+ ".xls";
				logger.debug("Ruta Archivo: " + rutaTemp);
				FacesContext fc = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) fc.getExternalContext()
						.getContext();
				String rutaTemplate = sc.getRealPath(File.separator + "excel"
						+ File.separator + "template_exportar_siniestros.xls");
				beans.put("exportar", listaReporteSiniestro);
				XLSTransformer transformer = new XLSTransformer();
				transformer.transformXLS(rutaTemplate, beans, rutaTemp);
				File archivoResp = new File(rutaTemp);
				FileInputStream in = new FileInputStream(archivoResp);
				HttpServletResponse response = (HttpServletResponse) contexto
						.getResponse();
				byte[] loader = new byte[(int) archivoResp.length()];
				response.addHeader("Content-Disposition",
						"attachment;filename=" + "Reporte_Cliente_"
								+ FechaActual + ".xls");
				response.setContentType("application/vnd.ms-excel");

				ServletOutputStream out = response.getOutputStream();

				while ((in.read(loader)) > 0) {
					out.write(loader, 0, loader.length);
				}
				in.close();
				out.close();

				FacesContext.getCurrentInstance().responseComplete();

			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_BUSQUEDA_PENDIENTE,
						FacesMessage.SEVERITY_INFO);
			}

		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		logger.info("Fin");
		return respuesta;
	}

	public String descargarPlantilla() {

		String respuesta = "";

		try {

			if (mensaje.length() == 0) {
				String nombrePlantilla = "PLANILLA_"
						+ siniSiniestro.getNroPlanilla();
				iniciaGeneraPlantilla("PlantillaIndemniza.jasper");
				List<ConsultaSiniestro> listaSiniPlantilla = new ArrayList<ConsultaSiniestro>();
				for (int x = 0; x < varlista.size(); x++) {
					listaSiniPlantilla.add(varlista.get(x));
				}
				SiniestroDataSourceService siniestroDataSourceService = new SiniestroDataSourceService(
						listaSiniPlantilla);
				siniestroDataSourceService.setSiniDatosCafae(siniDatosCafae);
				params.put("siniestrosDataSource", siniestroDataSourceService);
				params.put("nroPlanilla", siniSiniestro.getNroPlanilla());
				params.put(
						"poliza",
						siniPolizaService.obtener(
								siniSiniestro.getNroSiniestro()).getNroPoliza());
				params.put("tipoRefCafae", parametroService.obtenerDescripcion(
						siniDatosCafae.getTipRefCafae(),
						Constantes.COD_PARAM_LISTA_TIPO_REF_CAFAE, "D"));
				params.put("ejecutivo", parametroService.obtenerDescripcion(
						siniDatosCafae.getEjeCafae(),
						Constantes.COD_PARAM_LISTA_EJECUTIVOS, "D"));
				params.put("producto",
						siniProductoService
								.obtener(siniPoliza.getNroProducto())
								.getNomProducto());
				params.put("fechaPlanilla",
						formatearFecha(siniSiniestro.getFecAprobRechazo()));
				params.put("socio", parametroService.obtenerDescripcion(
						siniPoliza.getCodSocio(),
						Constantes.COD_PARAM_SOCIOS_SINI, "D"));
				params.put("moneda", parametroService.obtenerDescripcion(
						siniSiniestro.getCodMoneda(),
						Constantes.COD_PARAM_LISTA_MONEDA, "D"));
				params.put("observaciones", siniSiniestro.getObservaciones());
				params.put("totalIndemniza", formatearMonto(total.toString()));
				terminaGeneraPlantilla(nombrePlantilla);
				respuesta = "";

			} else {
				throw new SyncconException(mensaje, FacesMessage.SEVERITY_INFO);
			}

		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	private void terminaGeneraPlantilla(String namePlantilla) throws Exception {

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fc.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		out = response.getOutputStream();
		File pdfFile = new File(System.getProperty("java.io.tmpdir")
				+ namePlantilla + ".pdf");
		if (!pdfFile.exists()) {
			pdfFile.createNewFile();
		}
		FileInputStream pdfIn = new FileInputStream(pdfFile);
		IOUtils.copy(pdfIn, out);
		response.addHeader("Content-Disposition", "attachment;filename="
				+ pdfFile.getName());
		response.setContentType("application/pdf");
		pdfIn.close();

		JasperPrint print = JasperFillManager.fillReport(report, params,
				new JREmptyDataSource());
		JasperExportManager.exportReportToPdfStream(print, out);
		FacesContext.getCurrentInstance().responseComplete();
	}

	private String formatearFecha(Date fecha) throws Exception {
		String fechaFormato = null;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

		if (fecha != null) {
			fechaFormato = DATE_FORMAT.format(fecha);
		}
		return fechaFormato;
	}

	private String formatearFechaTexto(Date fecha) throws Exception {
		String fechaFormato = null;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
				"dd '  ' MMMM '  ' yyyy", new Locale("ES"));// new
		// Locale("es_ES"):
		// ingles
		if (fecha != null) {
			fechaFormato = DATE_FORMAT.format(fecha);
		}
		return fechaFormato;
	}

	public String validarDiasUltDocum(ValueChangeEvent event) {
		logger.info("validarDiasUltDocum");
		String respuesta = null;
		int diastras = 0;
		try {

			if (event.getNewValue() != null
					&& siniSiniestro.getFecAprobRechazo() != null) {

				LocalDate fechaIni = LocalDate.fromDateFields((Date) event
						.getNewValue());
				LocalDate fechaFin = LocalDate.fromDateFields(siniSiniestro
						.getFecAprobRechazo());
				Days dias = Days.daysBetween(fechaIni, fechaFin);
				siniSiniestro.setCanDiasLiquiRecha(dias.getDays());

			}

			/*
			 * if (event.getNewValue() != null) { Date fechaIni = (Date)
			 * event.getNewValue(); Date fechaFin = new
			 * Date(System.currentTimeMillis()); int can =
			 * siniSiniestroService.canDiasHabiles(fechaIni, fechaFin);
			 * siniSiniestro.setCanDiasPendientes(can - 1); }
			 */
			System.out.println("fecha null: " + event.getNewValue());   
			
			if (event.getNewValue() != null){
				
			diastras = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
					(Date) event.getNewValue(), siniSiniestro.getCodEstado(),
					siniSiniestro.getFecAprobRechazo(),
					siniSiniestro.getFecEntregaOpCont());
			
			}else{
				System.out.println("fecha Documento Socio: " + siniSiniestro.getFecUltDocumSocio());
				
				diastras = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
						null, siniSiniestro.getCodEstado(),
						siniSiniestro.getFecAprobRechazo(),
						siniSiniestro.getFecEntregaOpCont());
				
			}
			calcularDiasNotificacion();
			siniSiniestro.setCanDiasPendientes(diastras);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String validarDiasUltDocumSocio(ValueChangeEvent event) {
		logger.info("validarDiasUltDocumSocio");
		String respuesta = null;
		int diastras;
		try {

			if (event.getNewValue() != null
					&& siniSiniestro.getFecAprobRechazo() != null) {

				LocalDate fechaIni = LocalDate.fromDateFields((Date) event
						.getNewValue());
				LocalDate fechaFin = LocalDate.fromDateFields(siniSiniestro
						.getFecAprobRechazo());
				Days dias = Days.daysBetween(fechaIni, fechaFin);
				siniSiniestro.setCanDiasLiquiRecha(dias.getDays());

			}

			/*
			 * if (event.getNewValue() != null) { Date fechaIni = (Date)
			 * event.getNewValue(); Date fechaFin = new
			 * Date(System.currentTimeMillis()); int can =
			 * siniSiniestroService.canDiasHabiles(fechaIni, fechaFin);
			 * siniSiniestro.setCanDiasPendientes(can - 1); }
			 */
            
		   
			if (event.getNewValue() != null){
				
				diastras = diasTranscurridos((Date) event.getNewValue(),
						siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(),
						siniSiniestro.getFecAprobRechazo(),
						siniSiniestro.getFecEntregaOpCont());
				
				}else{
					
					diastras = diasTranscurridos(null,
							siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(),
							siniSiniestro.getFecAprobRechazo(),
							siniSiniestro.getFecEntregaOpCont());
					
				}
				
				siniSiniestro.setCanDiasPendientes(diastras);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public String validarFecOperCont(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = null;
		int diastras;
		try {

			if (event.getNewValue() != null){
				
				diastras = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
						siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(),
						siniSiniestro.getFecAprobRechazo(),
						(Date) event.getNewValue());
				
				}else{
					
					diastras = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
							siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(),
							siniSiniestro.getFecAprobRechazo(),
							null);
					
				}
				
				siniSiniestro.setCanDiasPendientes(diastras);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");
		return respuesta;
	}

	public int diasTranscurridos(Date fechaUltDocumenS, Date fechaUltDocumenC,
			String estado, Date FechaAprobacion, Date FechaOContrato) {
		logger.info("Inicio");
		int can = 0;
		
        System.out.println("fecha O contratp" + FechaOContrato); 
		
        if(estado != null){
        
        if (estado.equals("8")) {

			if (fechaUltDocumenS != null && fechaUltDocumenC != null) {

				Date fechaFin = new Date(System.currentTimeMillis());
				can = fechasDiferenciaEnDias(fechaUltDocumenS, fechaFin);
				
			} else {

				if (fechaUltDocumenC != null && fechaUltDocumenS == null) {

					Date fechaFin = new Date(System.currentTimeMillis());
					can = fechasDiferenciaEnDias(fechaUltDocumenC, fechaFin);
				} else {

					if ((fechaUltDocumenS == null && fechaUltDocumenC == null)) {

						can = 0;
						System.out.println(can);
						
					}else{
						
					Date fechaFin = new Date(System.currentTimeMillis());
					can = fechasDiferenciaEnDias(fechaUltDocumenS, fechaFin);
					
					}
					
				}

			}
			
			

		} else {

			if (estado.equals("2") || estado.equals("3") || estado.equals("13")	|| estado.equals("7")) {

				if (FechaOContrato != null) {

					if ((fechaUltDocumenS != null && fechaUltDocumenC != null)) {

						can = fechasDiferenciaEnDias(fechaUltDocumenS,
								FechaOContrato);
					} else {

						if (fechaUltDocumenC != null && fechaUltDocumenS == null) {

							can = fechasDiferenciaEnDias(fechaUltDocumenC,FechaOContrato);
						} else {
							
							if ((fechaUltDocumenS == null && fechaUltDocumenC == null)) {

								can = 0;
								
								
							}else{

							can = fechasDiferenciaEnDias(fechaUltDocumenS,
									FechaOContrato);
							}
							
						}

					}
					
					

				} else {

					if ((fechaUltDocumenS != null && fechaUltDocumenC != null)) {

						Date fechaFin = new Date(System.currentTimeMillis());
						can = fechasDiferenciaEnDias(fechaUltDocumenS, fechaFin);
						

					} else {

						if (fechaUltDocumenC != null && fechaUltDocumenS == null) {

							Date fechaFin = new Date(System.currentTimeMillis());
							can = fechasDiferenciaEnDias(fechaUltDocumenC,fechaFin);
						} else {
							
							if ((fechaUltDocumenS == null && fechaUltDocumenC == null)) {

								can = 0;
							
								
							}else{
									
								Date fechaFin = new Date(System.currentTimeMillis());
								can = fechasDiferenciaEnDias(fechaUltDocumenS, fechaFin);
								
								}
							
							}
						}

					
				}

			} else {

				if (FechaAprobacion != null) {

					if ((fechaUltDocumenS != null && fechaUltDocumenC != null)) {

						can = fechasDiferenciaEnDias(fechaUltDocumenS,FechaAprobacion);
						

					} else {

						if (fechaUltDocumenC != null && fechaUltDocumenS == null) {

							can = fechasDiferenciaEnDias(fechaUltDocumenC,
									FechaAprobacion);
						} else {
							
							if ((fechaUltDocumenS == null && fechaUltDocumenC == null)) {

								can = 0;
								
								
							}else{

							can = fechasDiferenciaEnDias(fechaUltDocumenS,FechaAprobacion);
							}
							
						}

					}
					

				} else {

					if ((fechaUltDocumenS != null && fechaUltDocumenC != null)) {

						Date fechaFin = new Date(System.currentTimeMillis());
						can = fechasDiferenciaEnDias(fechaUltDocumenS, fechaFin);
						

					} else {

						if (fechaUltDocumenC != null && fechaUltDocumenS == null) {

							Date fechaFin = new Date(System.currentTimeMillis());
							can = fechasDiferenciaEnDias(fechaUltDocumenC,fechaFin);
						} else {

							if ((fechaUltDocumenS == null && fechaUltDocumenC == null)) {

								can = 0;
								
								
							}else{
								
							Date fechaFin = new Date(System.currentTimeMillis());
							can = fechasDiferenciaEnDias(fechaUltDocumenS, fechaFin);
							
							}
						}

					}
					

				}

			}

		}
        
        }else{
        	can = 0;
        }

		return can;
	}

	public String validarDiasAprobRechazo(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = null;
		int diastrans;
		try {
			if (siniSiniestro.getFecUltDocum() != null
					&& event.getNewValue() != null) {
				LocalDate fechaIni = LocalDate.fromDateFields(siniSiniestro
						.getFecUltDocum());
				LocalDate fechaFin = LocalDate.fromDateFields((Date) event
						.getNewValue());
				Days dias = Days.daysBetween(fechaIni, fechaFin);
				siniSiniestro.setCanDiasLiquiRecha(dias.getDays());
				if (siniSiniestro.getCodEstado().contentEquals("8")) {
					setRequiredFecAproRechazo(true);
				} else {
					setRequiredFecAproRechazo(false);
				}
			}
			
			if (event.getNewValue() != null){
				
				diastrans = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
						siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(),
						(Date) event.getNewValue(),
						siniSiniestro.getFecEntregaOpCont());
				
				}else{
					
					diastrans = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
							siniSiniestro.getFecUltDocum(), siniSiniestro.getCodEstado(),
							null,
							siniSiniestro.getFecEntregaOpCont());
					
				}
				
				siniSiniestro.setCanDiasPendientes(diastrans);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.info("Fin");

		return respuesta;
	}

	private void guardarFlasRobo(SiniRobo siniRobo) throws Exception {
		logger.debug("inicio");
		if (isCelular_m3()) {
			siniRobo.setFlagCelular(Short.parseShort("1"));
		} else {
			siniRobo.setFlagCelular(Short.parseShort("0"));
		}
		if (isBilletera_m3()) {
			siniRobo.setFlagBilletera(Short.parseShort("1"));
		} else {
			siniRobo.setFlagBilletera(Short.parseShort("0"));
		}
		if (isBolso_m3()) {
			siniRobo.setFlagBolso(Short.parseShort("1"));
		} else {
			siniRobo.setFlagBolso(Short.parseShort("0"));
		}
		if (isCartera_m3()) {
			siniRobo.setFlagCartera(Short.parseShort("1"));
		} else {
			siniRobo.setFlagCartera(Short.parseShort("0"));
		}
		if (isCocheBB_m3()) {
			siniRobo.setFlagCocheBb(Short.parseShort("1"));
		} else {
			siniRobo.setFlagCocheBb(Short.parseShort("0"));
		}
		if (isCosmeticos_m3()) {
			siniRobo.setFlagCosmeticos(Short.parseShort("1"));
		} else {
			siniRobo.setFlagCosmeticos(Short.parseShort("0"));
		}
		if (isDiscIpodMP3Table_m3()) {
			siniRobo.setFlagDiscIpodMp3(Short.parseShort("1"));
		} else {
			siniRobo.setFlagDiscIpodMp3(Short.parseShort("0"));
		}
		if (isDisco_m3()) {
			siniRobo.setFlagDisco(Short.parseShort("1"));
		} else {
			siniRobo.setFlagDisco(Short.parseShort("0"));
		}
		if (isDni_m3()) {
			siniRobo.setFlagDni(Short.parseShort("1"));
		} else {
			siniRobo.setFlagDni(Short.parseShort("0"));
		}
		if (isFlagMedicos_m3()) {
			siniRobo.setFlagGastoMedico(Short.parseShort("1"));
		} else {
			siniRobo.setFlagGastoMedico(Short.parseShort("0"));
		}
		if (isMaletetin_m3()) {
			siniRobo.setFlagMaletin(Short.parseShort("1"));
		} else {
			siniRobo.setFlagMaletin(Short.parseShort("0"));
		}
		if (isPortadocumentos_m3()) {
			siniRobo.setFlagPortadocumentos(Short.parseShort("1"));
		} else {
			siniRobo.setFlagPortadocumentos(Short.parseShort("0"));
		}
		if (isLentesOpticos_m3()) {
			siniRobo.setFlagLentesOpticos(Short.parseShort("1"));
		} else {
			siniRobo.setFlagLentesOpticos(Short.parseShort("0"));
		}
		if (isLentesDeSol_m3()) {
			siniRobo.setFlagLentesDeSol(Short.parseShort("1"));
		} else {
			siniRobo.setFlagLentesDeSol(Short.parseShort("0"));
		}
		if (isLapicero_m3()) {
			siniRobo.setFlagLapicero(Short.parseShort("1"));
		} else {
			siniRobo.setFlagLapicero(Short.parseShort("0"));
		}
		if (isDni_m3()) {
			siniRobo.setFlagDni(Short.parseShort("1"));
		} else {
			siniRobo.setFlagDni(Short.parseShort("0"));
		}
		if (isMochila_m3()) {
			siniRobo.setFlagMochila(Short.parseShort("1"));
		} else {
			siniRobo.setFlagMochila(Short.parseShort("0"));
		}
		if (isReloj_m3()) {
			siniRobo.setFlagReloj(Short.parseShort("1"));
		} else {
			siniRobo.setFlagReloj(Short.parseShort("0"));
		}
		if (isPalmTabled_m3()) {
			siniRobo.setFlagPalmTabled(Short.parseShort("1"));
		} else {
			siniRobo.setFlagPalmTabled(Short.parseShort("0"));
		}
		if (isSillaAutoBB_m3()) {
			siniRobo.setFlagSillaAutoBb(Short.parseShort("1"));
		} else {
			siniRobo.setFlagSillaAutoBb(Short.parseShort("0"));
		}
		if (isLlanta_m3()) {
			siniRobo.setFlagLlanta(Short.parseShort("1"));
		} else {
			siniRobo.setFlagLlanta(Short.parseShort("0"));
		}
		if (isMuerteAccidente_m3()) {
			siniRobo.setFlagMuerteAccid(Short.parseShort("1"));
		} else {
			siniRobo.setFlagMuerteAccid(Short.parseShort("0"));
		}
		if (isLlaves_m3()) {
			siniRobo.setFlagLlaves(Short.parseShort("1"));
		} else {
			siniRobo.setFlagLlaves(Short.parseShort("0"));
		}
		if (isLibreDisp_m3()) {
			siniRobo.setFlagLibreDisp(Short.parseShort("1"));
		} else {
			siniRobo.setFlagLibreDisp(Short.parseShort("0"));
		}
		logger.debug("fin");
	}

	private void gudardarFlasCafae(SiniDatosCafae siniDatosCafae)
			throws Exception {
		logger.debug("inicio");

		if (isFlagDc()) {
			siniDatosCafae.setFlagDc(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagDc(Short.parseShort("0"));
		}

		if (isFlagDs()) {
			siniDatosCafae.setFlagDs(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagDs(Short.parseShort("0"));
		}

		if (isFlagIHP()) {
			siniDatosCafae.setFlagIhp(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagIhp(Short.parseShort("0"));
		}

		if (isFlagInToPe()) {
			siniDatosCafae.setFlagInToPe(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagInToPe(Short.parseShort("0"));
		}

		if (isFlagMuAc()) {
			siniDatosCafae.setFlagMuAc((short) 1);
		} else {
			siniDatosCafae.setFlagMuAc(Short.parseShort("0"));
		}

		if (isFlagMuNa()) {
			siniDatosCafae.setFlagMuNa(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagMuNa(Short.parseShort("0"));
		}

		if (isFlagReRe()) {
			siniDatosCafae.setFlagReRe(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagReRe(Short.parseShort("0"));
		}

		if (isFlagSaDe()) {
			siniDatosCafae.setFlagSaDe(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagSaDe(Short.parseShort("0"));
		}

		if (isFlagTrRe()) {
			siniDatosCafae.setFlagTrRe(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagTrRe(Short.parseShort("0"));
		}

		if (isFlagUtEs()) {
			siniDatosCafae.setFlagUtEs(Short.parseShort("1"));
		} else {
			siniDatosCafae.setFlagUtEs(Short.parseShort("0"));
		}

		logger.debug("fin");
	}

	private void llenarFlagsCafae(SiniDatosCafae siniDatosCafae)
			throws Exception {
		logger.debug("inicio");

		// if(siniDatosCafae.getFlagDc()==1)
		// { setFlagDc(true);
		// }
		// if(siniDatosCafae.getFlagDs()==1)
		// { setFlagDs(true);
		// }
		// if(siniDatosCafae.getFlagIhp()==1)
		// { setFlagIHP(true);
		// }
		// if(siniDatosCafae.getFlagInToPe()==1)
		// { setFlagInToPe(true);
		// }
		// if(siniDatosCafae.getFlagMuAc()==1)
		// { setFlagMuAc(true);
		// }
		//
		// if(siniDatosCafae.getFlagMuNa()==1)
		// { setFlagMuNa(true);
		// }
		// if(siniDatosCafae.getFlagReRe()==1)
		// { setFlagReRe(true);
		// }
		// if(siniDatosCafae.getFlagSaDe()==1)
		// { setFlagSaDe(true);
		// }
		// if(siniDatosCafae.getFlagTrRe()==1)
		// { setFlagTrRe(true);
		// }
		// if(siniDatosCafae.getFlagUtEs()==1)
		// { setFlagUtEs(true);
		// }

		setFlagDc(siniDatosCafae.getFlagDc() == 1);
		setFlagDs(siniDatosCafae.getFlagDs() == 1);
		setFlagIHP(siniDatosCafae.getFlagIhp() == 1);

		setFlagInToPe(siniDatosCafae.getFlagInToPe() == 1);
		setFlagMuAc(siniDatosCafae.getFlagMuAc() == 1);
		setFlagMuNa(siniDatosCafae.getFlagMuNa() == 1);
		setFlagReRe(siniDatosCafae.getFlagReRe() == 1);
		setFlagSaDe(siniDatosCafae.getFlagSaDe() == 1);
		setFlagTrRe(siniDatosCafae.getFlagTrRe() == 1);
		setFlagUtEs(siniDatosCafae.getFlagUtEs() == 1);

		logger.debug("fin");
	}

	private void llenarFlagsRobo(SiniRobo siniRobo) throws Exception {
		logger.debug("inicio");

		if (siniRobo.getFlagBilletera() == 1) {
			setBilletera_m3(true);
		} else {
			setBilletera_m3(false);
		}
		if (siniRobo.getFlagCelular() == 1) {
			setCelular_m3(true);
		} else {
			setCelular_m3(false);
		}
		if (siniRobo.getFlagCartera() == 1) {
			setCartera_m3(true);
		} else {
			setCartera_m3(false);
		}
		if (siniRobo.getFlagMaletin() == 1) {
			setMaletetin_m3(true);
		} else {
			setMaletetin_m3(false);
		}
		if (siniRobo.getFlagPortadocumentos() == 1) {
			setPortadocumentos_m3(true);
		} else {
			setPortadocumentos_m3(false);
		}

		if (siniRobo.getFlagLentesOpticos() == 1) {
			setLentesOpticos_m3(true);
		} else {
			setLentesOpticos_m3(false);
		}

		if (siniRobo.getFlagLentesDeSol() == 1) {
			setLentesDeSol_m3(true);
		} else {
			setLentesDeSol_m3(false);
		}

		if (siniRobo.getFlagCosmeticos() == 1) {
			setCosmeticos_m3(true);
		} else {
			setCosmeticos_m3(false);
		}

		if (siniRobo.getFlagLapicero() == 1) {
			setLapicero_m3(true);
		} else {
			setLapicero_m3(false);
		}

		if (siniRobo.getFlagDni() == 1) {
			setDni_m3(true);
		} else {
			setDni_m3(false);
		}

		if (siniRobo.getFlagMochila() == 1) {
			setMochila_m3(true);
		} else {
			setMochila_m3(false);
		}

		if (siniRobo.getFlagReloj() == 1) {
			setReloj_m3(true);
		} else {
			setReloj_m3(false);
		}

		if (siniRobo.getFlagDiscIpodMp3() == 1) {
			setDiscIpodMP3Table_m3(true);
		} else {
			setDiscIpodMP3Table_m3(false);
		}

		if (siniRobo.getFlagPalmTabled() == 1) {
			setPalmTabled_m3(true);
		} else {
			setPalmTabled_m3(false);
		}

		if (siniRobo.getFlagBolso() == 1) {
			setBolso_m3(true);
		} else {
			setBolso_m3(false);
		}

		if (siniRobo.getFlagSillaAutoBb() == 1) {
			setSillaAutoBB_m3(true);
		} else {
			setSillaAutoBB_m3(false);
		}

		if (siniRobo.getFlagCocheBb() == 1) {
			setCocheBB_m3(true);
		} else {
			setCocheBB_m3(false);
		}

		if (siniRobo.getFlagDisco() == 1) {
			setDisco_m3(true);
		} else {
			setDisco_m3(false);
		}

		if (siniRobo.getFlagLlanta() == 1) {
			setLlanta_m3(true);
		} else {
			setLlanta_m3(false);
		}

		if (siniRobo.getFlagGastoMedico() == 1) {
			setFlagMedicos_m3(true);
		} else {
			setFlagMedicos_m3(false);
		}

		if (siniRobo.getFlagMuerteAccid() == 1) {
			setMuerteAccidente_m3(true);
		} else {
			setMuerteAccidente_m3(false);
		}

		if (siniRobo.getFlagLlaves() == 1) {
			setLlaves_m3(true);
		} else {
			setLlaves_m3(false);
		}

		if (siniRobo.getFlagLibreDisp() == 1) {
			setLibreDisp_m3(true);
		} else {
			setLibreDisp_m3(false);
		}
		logger.debug("fin");
	}

	public int getDiasHabiles() {
		Calendar fechaInicial1 = new GregorianCalendar(2011, 1 - 1, 01);
		Calendar fechaFinal1 = new GregorianCalendar(2011, 1 - 1, 31);
		int diffDays = 0;

		while (fechaInicial1.before(fechaFinal1)
				|| fechaInicial1.equals(fechaFinal1)) {

			if (fechaInicial1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
					&& fechaInicial1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {

				diffDays++;
			}

			fechaInicial1.add(Calendar.DATE, 1);
		}

		return diffDays;
	}

	public List<SelectItem> getListaSocioItem() {
		return listaSocioItem;
	}

	public void setListaSocioItem(List<SelectItem> listaSocioItem) {
		this.listaSocioItem = listaSocioItem;
	}

	public List<SelectItem> getListaTipoSeguroItem() {
		return listaTipoSeguroItem;
	}

	public void setListaTipoSeguroItem(List<SelectItem> listaTipoSeguroItem) {
		this.listaTipoSeguroItem = listaTipoSeguroItem;
	}

	public List<SelectItem> getListaTipoDocItem() {
		return listaTipoDocItem;
	}

	public List<SelectItem> getListaCoberturaAfecItem() {
		return listaCoberturaAfecItem;
	}

	public List<SelectItem> getListaEstadoItem() {
		return listaEstadoItem;
	}

	public List<SelectItem> getListaEstadoLegajoItem() {
		return listaEstadoLegajoItem;
	}

	public void setListaTipoDocItem(List<SelectItem> listaTipoDocItem) {
		this.listaTipoDocItem = listaTipoDocItem;
	}

	public void setListaCoberturaAfecItem(
			List<SelectItem> listaCoberturaAfecItem) {
		this.listaCoberturaAfecItem = listaCoberturaAfecItem;
	}

	public void setListaEstadoItem(List<SelectItem> listaEstadoItem) {
		this.listaEstadoItem = listaEstadoItem;
	}

	public void setListaEstadoLegajoItem(List<SelectItem> listaEstadoLegajoItem) {
		this.listaEstadoLegajoItem = listaEstadoLegajoItem;
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public String getTipoSeguro_b() {
		return tipoSeguro_b;
	}

	public String getSocio_b() {
		return socio_b;
	}

	public String getProducto_b() {
		return producto_b;
	}

	public String getTipoDoc_b() {
		return tipoDoc_b;
	}

	public String getNroDocumento_b() {
		return nroDocumento_b;
	}

	public String getEstado_b() {
		return estado_b;
	}

	public String getEstadoLegajo_b() {
		return estadoLegajo_b;
	}

	public String getNombres_b() {
		return nombres_b;
	}

	public String getApePaterno_b() {
		return apePaterno_b;
	}

	public String getApeMaterno_b() {
		return apeMaterno_b;
	}

	public void setTipoSeguro_b(String tipoSeguro_b) {
		this.tipoSeguro_b = tipoSeguro_b;
	}

	public void setSocio_b(String socio_b) {
		this.socio_b = socio_b;
	}

	public void setProducto_b(String producto_b) {
		this.producto_b = producto_b;
	}

	public void setTipoDoc_b(String tipoDoc_b) {
		this.tipoDoc_b = tipoDoc_b;
	}

	public void setNroDocumento_b(String nroDocumento_b) {
		this.nroDocumento_b = nroDocumento_b;
	}

	public void setEstado_b(String estado_b) {
		this.estado_b = estado_b;
	}

	public void setEstadoLegajo_b(String estadoLegajo_b) {
		this.estadoLegajo_b = estadoLegajo_b;
	}

	public void setNombres_b(String nombres_b) {
		this.nombres_b = nombres_b;
	}

	public void setApePaterno_b(String apePaterno_b) {
		this.apePaterno_b = apePaterno_b;
	}

	public void setApeMaterno_b(String apeMaterno_b) {
		this.apeMaterno_b = apeMaterno_b;
	}

	public Date getFecUltDocumen_b() {
		return fecUltDocumen_b;
	}

	public Date getFecUltDocumenDesde_b() {
		return fecUltDocumenDesde_b;
	}

	public void setFecUltDocumenDesde_b(Date fecUltDocumenDesde_b) {
		this.fecUltDocumenDesde_b = fecUltDocumenDesde_b;
	}

	public Date getFecUltDocumenHasta_b() {
		return fecUltDocumenHasta_b;
	}

	public void setFecUltDocumenHasta_b(Date fecUltDocumenHasta_b) {
		this.fecUltDocumenHasta_b = fecUltDocumenHasta_b;
	}

	public Date getFecAprobRechDesde_b() {
		return fecAprobRechDesde_b;
	}

	public void setFecAprobRechDesde_b(Date fecAprobRechDesde_b) {
		this.fecAprobRechDesde_b = fecAprobRechDesde_b;
	}

	public Date getFecAprobRechHasta_b() {
		return fecAprobRechHasta_b;
	}

	public void setFecAprobRechHasta_b(Date fecAprobRechHasta_b) {
		this.fecAprobRechHasta_b = fecAprobRechHasta_b;
	}

	public Date getFecEntrgaOpcDesde_b() {
		return fecEntrgaOpcDesde_b;
	}

	public void setFecEntrgaOpcDesde_b(Date fecEntrgaOpcDesde_b) {
		this.fecEntrgaOpcDesde_b = fecEntrgaOpcDesde_b;
	}

	public Date getFecEntrgaOpcHasta_b() {
		return fecEntrgaOpcHasta_b;
	}

	public void setFecEntrgaOpcHasta_b(Date fecEntrgaOpcHasta_b) {
		this.fecEntrgaOpcHasta_b = fecEntrgaOpcHasta_b;
	}

	public String getPagoDesde_b() {
		return pagoDesde_b;
	}

	public void setPagoDesde_b(String pagoDesde_b) {
		this.pagoDesde_b = pagoDesde_b;
	}

	public String getPagoHasta_b() {
		return pagoHasta_b;
	}

	public void setPagoHasta_b(String pagoHasta_b) {
		this.pagoHasta_b = pagoHasta_b;
	}

	public String getNroPlanilla_b() {
		return nroPlanilla_b;
	}

	public void setNroPlanilla_b(String nroPlanilla_b) {
		this.nroPlanilla_b = nroPlanilla_b;
	}

	public String getFindEjeCafae_b() {
		return findEjeCafae_b;
	}

	public void setFindEjeCafae_b(String findEjeCafae_b) {
		this.findEjeCafae_b = findEjeCafae_b;
	}

	public String getvTipoTarjeta_b() {
		return vTipoTarjeta_b;
	}

	public void setvTipoTarjeta_b(String vTipoTarjeta_b) {
		this.vTipoTarjeta_b = vTipoTarjeta_b;
	}

	public String getNroPoliza_b() {
		return nroPoliza_b;
	}

	public String getCobAfectada_b() {
		return cobAfectada_b;
	}

	public void setFecUltDocumen_b(Date fecUltDocumen_b) {
		this.fecUltDocumen_b = fecUltDocumen_b;
	}

	public void setNroPoliza_b(String nroPoliza_b) {
		this.nroPoliza_b = nroPoliza_b;
	}

	public void setCobAfectada_b(String cobAfectada_b) {
		this.cobAfectada_b = cobAfectada_b;
	}

	public List<SelectItem> getListaEjeCafaeItem() {
		return listaEjeCafaeItem;
	}

	public void setListaEjeCafaeItem(List<SelectItem> listaEjeCafaeItem) {
		this.listaEjeCafaeItem = listaEjeCafaeItem;
	}

	public boolean isCelular_m3() {
		return celular_m3;
	}

	public boolean isCartera_m3() {
		return cartera_m3;
	}

	public boolean isMaletetin_m3() {
		return maletetin_m3;
	}

	public boolean isBilletera_m3() {
		return billetera_m3;
	}

	public boolean isPortadocumentos_m3() {
		return portadocumentos_m3;
	}

	public boolean isLentesOpticos_m3() {
		return lentesOpticos_m3;
	}

	public boolean isLentesDeSol_m3() {
		return lentesDeSol_m3;
	}

	public boolean isCosmeticos_m3() {
		return cosmeticos_m3;
	}

	public boolean isLapicero_m3() {
		return lapicero_m3;
	}

	public boolean isDni_m3() {
		return dni_m3;
	}

	public boolean isMochila_m3() {
		return mochila_m3;
	}

	public boolean isReloj_m3() {
		return reloj_m3;
	}

	public boolean isDiscIpodMP3Table_m3() {
		return discIpodMP3Table_m3;
	}

	public boolean isPalmTabled_m3() {
		return palmTabled_m3;
	}

	public boolean isBolso_m3() {
		return bolso_m3;
	}

	public boolean isSillaAutoBB_m3() {
		return sillaAutoBB_m3;
	}

	public boolean isCocheBB_m3() {
		return cocheBB_m3;
	}

	public boolean isDisco_m3() {
		return disco_m3;
	}

	public boolean isLlanta_m3() {
		return llanta_m3;
	}

	public boolean isLibreDisp_m3() {
		return libreDisp_m3;
	}

	public boolean isMuerteAccidente_m3() {
		return muerteAccidente_m3;
	}

	public boolean isLlaves_m3() {
		return llaves_m3;
	}

	public void setCelular_m3(boolean celular_m3) {
		this.celular_m3 = celular_m3;
	}

	public void setCartera_m3(boolean cartera_m3) {
		this.cartera_m3 = cartera_m3;
	}

	public void setMaletetin_m3(boolean maletetin_m3) {
		this.maletetin_m3 = maletetin_m3;
	}

	public void setBilletera_m3(boolean billetera_m3) {
		this.billetera_m3 = billetera_m3;
	}

	public void setPortadocumentos_m3(boolean portadocumentos_m3) {
		this.portadocumentos_m3 = portadocumentos_m3;
	}

	public void setLentesOpticos_m3(boolean lentesOpticos_m3) {
		this.lentesOpticos_m3 = lentesOpticos_m3;
	}

	public void setLentesDeSol_m3(boolean lentesDeSol_m3) {
		this.lentesDeSol_m3 = lentesDeSol_m3;
	}

	public void setCosmeticos_m3(boolean cosmeticos_m3) {
		this.cosmeticos_m3 = cosmeticos_m3;
	}

	public void setLapicero_m3(boolean lapicero_m3) {
		this.lapicero_m3 = lapicero_m3;
	}

	public void setDni_m3(boolean dni_m3) {
		this.dni_m3 = dni_m3;
	}

	public void setMochila_m3(boolean mochila_m3) {
		this.mochila_m3 = mochila_m3;
	}

	public void setReloj_m3(boolean reloj_m3) {
		this.reloj_m3 = reloj_m3;
	}

	public void setDiscIpodMP3Table_m3(boolean discIpodMP3Table_m3) {
		this.discIpodMP3Table_m3 = discIpodMP3Table_m3;
	}

	public void setPalmTabled_m3(boolean palmTabled_m3) {
		this.palmTabled_m3 = palmTabled_m3;
	}

	public void setBolso_m3(boolean bolso_m3) {
		this.bolso_m3 = bolso_m3;
	}

	public void setSillaAutoBB_m3(boolean sillaAutoBB_m3) {
		this.sillaAutoBB_m3 = sillaAutoBB_m3;
	}

	public void setCocheBB_m3(boolean cocheBB_m3) {
		this.cocheBB_m3 = cocheBB_m3;
	}

	public void setDisco_m3(boolean disco_m3) {
		this.disco_m3 = disco_m3;
	}

	public void setLlanta_m3(boolean llanta_m3) {
		this.llanta_m3 = llanta_m3;
	}

	public void setLibreDisp_m3(boolean libreDisp_m3) {
		this.libreDisp_m3 = libreDisp_m3;
	}

	public void setMuerteAccidente_m3(boolean muerteAccidente_m3) {
		this.muerteAccidente_m3 = muerteAccidente_m3;
	}

	public void setLlaves_m3(boolean llaves_m3) {
		this.llaves_m3 = llaves_m3;
	}

	public boolean isFlagcargoTargeEntregada_m5() {
		return flagcargoTargeEntregada_m5;
	}

	public void setFlagcargoTargeEntregada_m5(boolean flagcargoTargeEntregada_m5) {
		this.flagcargoTargeEntregada_m5 = flagcargoTargeEntregada_m5;
	}

	public boolean isFlagMuNa() {
		return flagMuNa;
	}

	public boolean isFlagMuAc() {
		return flagMuAc;
	}

	public boolean isFlagIHP() {
		return flagIHP;
	}

	public boolean isFlagSaDe() {
		return flagSaDe;
	}

	public boolean isFlagUtEs() {
		return flagUtEs;
	}

	public boolean isFlagReRe() {
		return flagReRe;
	}

	public boolean isFlagTrRe() {
		return flagTrRe;
	}

	public boolean isFlagInToPe() {
		return flagInToPe;
	}

	public boolean isFlagDc() {
		return flagDc;
	}

	public boolean isFlagDs() {
		return flagDs;
	}

	public void setFlagMuNa(boolean flagMuNa) {
		this.flagMuNa = flagMuNa;
	}

	public void setFlagMuAc(boolean flagMuAc) {
		this.flagMuAc = flagMuAc;
	}

	public void setFlagIHP(boolean flagIHP) {
		this.flagIHP = flagIHP;
	}

	public void setFlagSaDe(boolean flagSaDe) {
		this.flagSaDe = flagSaDe;
	}

	public void setFlagUtEs(boolean flagUtEs) {
		this.flagUtEs = flagUtEs;
	}

	public void setFlagReRe(boolean flagReRe) {
		this.flagReRe = flagReRe;
	}

	public void setFlagTrRe(boolean flagTrRe) {
		this.flagTrRe = flagTrRe;
	}

	public void setFlagInToPe(boolean flagInToPe) {
		this.flagInToPe = flagInToPe;
	}

	public void setFlagDc(boolean flagDc) {
		this.flagDc = flagDc;
	}

	public String getSucursalRetiro_m3() {
		return sucursalRetiro_m3;
	}

	public double getImpPreLiquidacion_m3() {
		return impPreLiquidacion_m3;
	}

	public void setSucursalRetiro_m3(String sucursalRetiro_m3) {
		this.sucursalRetiro_m3 = sucursalRetiro_m3;
	}

	public void setImpPreLiquidacion_m3(double impPreLiquidacion_m3) {
		this.impPreLiquidacion_m3 = impPreLiquidacion_m3;
	}

	public String getTipoRefCafae_b() {
		return tipoRefCafae_b;
	}

	public void setTipoRefCafae_b(String tipoRefCafae_b) {
		this.tipoRefCafae_b = tipoRefCafae_b;
	}

	public String getEjeCafae_b() {
		return ejeCafae_b;
	}

	public void setEjeCafae_b(String ejeCafae_b) {
		this.ejeCafae_b = ejeCafae_b;
	}

	public Date getFecOcurrencia_contenedor() {
		return fecOcurrencia_contenedor;
	}

	public void setFecOcurrencia_contenedor(Date fecOcurrencia_contenedor) {
		this.fecOcurrencia_contenedor = fecOcurrencia_contenedor;
	}

	public Date getFecNotificacion_contenedor() {
		return fecNotificacion_contenedor;
	}

	public void setFecNotificacion_contenedor(Date fecNotificacion_contenedor) {
		this.fecNotificacion_contenedor = fecNotificacion_contenedor;
	}

	public Date getNroSiniestro() {
		return nroSiniestro;
	}

	public void setNroSiniestro(Date nroSiniestro) {
		this.nroSiniestro = nroSiniestro;
	}

	public boolean isFlagMedicos_m3() {
		return flagMedicos_m3;
	}

	public void setFlagMedicos_m3(boolean flagMedicos_m3) {
		this.flagMedicos_m3 = flagMedicos_m3;
	}

	public double getImpgastoMedico_m3() {
		return impgastoMedico_m3;
	}

	public void setImpgastoMedico_m3(double impgastoMedico_m3) {
		this.impgastoMedico_m3 = impgastoMedico_m3;
	}

	public SiniSiniestro getSiniSiniestro() {
		return siniSiniestro;
	}

	public SiniDatosCafae getSiniDatosCafae() {
		return siniDatosCafae;
	}

	public void setSiniSiniestro(SiniSiniestro siniSiniestro) {
		this.siniSiniestro = siniSiniestro;
	}

	public void setSiniDatosCafae(SiniDatosCafae siniDatosCafae) {
		this.siniDatosCafae = siniDatosCafae;
	}

	public void setFlagDs(boolean flagDs) {
		this.flagDs = flagDs;
	}

	public SiniRobo getSiniRobo() {
		return siniRobo;
	}

	public SiniDesempleo getSiniDesempleo() {
		return siniDesempleo;
	}

	public SiniDesgravamen getSiniDesgravamen() {
		return siniDesgravamen;
	}

	public SiniRentaHosp getSiniRentaHosp() {
		return siniRentaHosp;
	}

	public SiniPoliza getSiniPoliza() {
		return siniPoliza;
	}

	public void setSiniRobo(SiniRobo siniRobo) {
		this.siniRobo = siniRobo;
	}

	public void setSiniDesempleo(SiniDesempleo siniDesempleo) {
		this.siniDesempleo = siniDesempleo;
	}

	public void setSiniDesgravamen(SiniDesgravamen siniDesgravamen) {
		this.siniDesgravamen = siniDesgravamen;
	}

	public void setSiniRentaHosp(SiniRentaHosp siniRentaHosp) {
		this.siniRentaHosp = siniRentaHosp;
	}

	public void setSiniPoliza(SiniPoliza siniPoliza) {
		this.siniPoliza = siniPoliza;
	}

	public String getNroSiniestro_b() {
		return nroSiniestro_b;
	}

	public void setNroSiniestro_b(String nroSiniestro_b) {
		this.nroSiniestro_b = nroSiniestro_b;
	}

	public List<SiniPoliza> getListaSiniPoliza() {
		return listaSiniPoliza;
	}

	public void setListaSiniPoliza(List<SiniPoliza> listaSiniPoliza) {
		this.listaSiniPoliza = listaSiniPoliza;
	}

	public List<SiniDesgravamen> getListaSiniDesgravamen() {
		return listaSiniDesgravamen;
	}

	public void setListaSiniDesgravamen(
			List<SiniDesgravamen> listaSiniDesgravamen) {
		this.listaSiniDesgravamen = listaSiniDesgravamen;
	}

	public List<SiniRobo> getListaSiniRobo() {
		return listaSiniRobo;
	}

	public void setListaSiniRobo(List<SiniRobo> listaSiniRobo) {
		this.listaSiniRobo = listaSiniRobo;
	}

	public List<SiniDesempleo> getListaSiniDesempleo() {
		return listaSiniDesempleo;
	}

	public void setListaSiniDesempleo(List<SiniDesempleo> listaSiniDesempleo) {
		this.listaSiniDesempleo = listaSiniDesempleo;
	}

	public List<SiniDatosCafae> getListaSiniDatosCafae() {
		return listaSiniDatosCafae;
	}

	public void setListaSiniDatosCafae(List<SiniDatosCafae> listaSiniDatosCafae) {
		this.listaSiniDatosCafae = listaSiniDatosCafae;
	}

	public List<SiniRentaHosp> getListaRentaHosp() {
		return listaRentaHosp;
	}

	public void setListaRentaHosp(List<SiniRentaHosp> listaRentaHosp) {
		this.listaRentaHosp = listaRentaHosp;
	}

	public List<SelectItem> getListaPaisItem() {
		return listaPaisItem;
	}

	public void setListaPaisItem(List<SelectItem> listaPaisItem) {
		this.listaPaisItem = listaPaisItem;
	}

	public List<SelectItem> getListaDepartamentoItem() {
		return listaDepartamentoItem;
	}

	public void setListaDepartamentoItem(List<SelectItem> listaDepartamentoItem) {
		this.listaDepartamentoItem = listaDepartamentoItem;
	}

	public List<SelectItem> getListaProvinciaItem() {
		return listaProvinciaItem;
	}

	public void setListaProvinciaItem(List<SelectItem> listaProvinciaItem) {
		this.listaProvinciaItem = listaProvinciaItem;
	}

	public List<SelectItem> getListaDistritoItem() {
		return listaDistritoItem;
	}

	public void setListaDistritoItem(List<SelectItem> listaDistritoItem) {
		this.listaDistritoItem = listaDistritoItem;
	}

	public List<SelectItem> getListaRamosItems() {
		return listaRamosItems;
	}

	public void setListaRamosItems(List<SelectItem> listaRamosItems) {
		this.listaRamosItems = listaRamosItems;
	}

	public String getCodRamo_m1() {
		return codRamo_m1;
	}

	public void setCodRamo_m1(String codRamo_m1) {
		this.codRamo_m1 = codRamo_m1;
	}

	public List<SelectItem> getListaGenerosItem() {
		return listaGenerosItem;
	}

	public void setListaGenerosItem(List<SelectItem> listaGenerosItem) {
		this.listaGenerosItem = listaGenerosItem;
	}

	public List<SelectItem> getListaRechazosItem() {
		return listaRechazosItem;
	}

	public void setListaRechazosItem(List<SelectItem> listaRechazosItem) {
		this.listaRechazosItem = listaRechazosItem;
	}

	public List<SelectItem> getListaRechazosAgItem() {
		return listaRechazosAgItem;
	}

	public void setListaRechazosAgItem(List<SelectItem> listaRechazosAgItem) {
		this.listaRechazosAgItem = listaRechazosAgItem;
	}

	public List<SelectItem> getListaResumenItem() {
		return listaResumenItem;
	}

	public void setListaResumenItem(List<SelectItem> listaResumenItem) {
		this.listaResumenItem = listaResumenItem;
	}

	public List<SelectItem> getListaParentescoItem() {
		return listaParentescoItem;
	}

	public void setListaParentescoItem(List<SelectItem> listaParentescoItem) {
		this.listaParentescoItem = listaParentescoItem;
	}

	public List<SelectItem> getListaMonedaItem() {
		return listaMonedaItem;
	}

	public void setListaMonedaItem(List<SelectItem> listaMonedaItem) {
		this.listaMonedaItem = listaMonedaItem;
	}

	public List<SelectItem> getListaMotivoNoEntregaItem() {
		return listaMotivoNoEntregaItem;
	}

	public void setListaMotivoNoEntregaItem(
			List<SelectItem> listaMotivoNoEntregaItem) {
		this.listaMotivoNoEntregaItem = listaMotivoNoEntregaItem;
	}

	public List<SelectItem> getListaProductoItem1() {
		return listaProductoItem1;
	}

	public List<SelectItem> getListaProductoItem2() {
		return listaProductoItem2;
	}

	public void setListaProductoItem1(List<SelectItem> listaProductoItem1) {
		this.listaProductoItem1 = listaProductoItem1;
	}

	public void setListaProductoItem2(List<SelectItem> listaProductoItem2) {
		this.listaProductoItem2 = listaProductoItem2;
	}

	public List<ConsultaSiniestro> getListaSiniSiniestro() {
		return listaSiniSiniestro;
	}

	public void setListaSiniSiniestro(List<ConsultaSiniestro> listaSiniSiniestro) {
		this.listaSiniSiniestro = listaSiniSiniestro;
	}

	public String listener(UploadEvent event) {
		logger.debug("[ Inicio Upload files ]");
		String respuesta = null;
		try {
			UploadItem item = event.getUploadItem();
			File archivo = item.getFile();
			nombreArchivo = item.getFileName().substring(
					item.getFileName().lastIndexOf("\\") + 1);
			tempFile = File.createTempFile("INTERBANK_", nombreArchivo);

			FileUtils.copyFile(archivo, tempFile);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		logger.debug("[ Fin Upload files ]");
		return respuesta;
	}

	public void clear() {
		logger.info("[ Inicio Clear ]");
		nombreArchivo = new String();

		logger.info("[ Fin Clear ]");
	}

	public String exportar() {
		logger.info("Inicio");
		String respuesta = "";

		Map<String, Object> beans = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		ExternalContext contexto = FacesContext.getCurrentInstance()
				.getExternalContext();
		String FechaActual = sdf.format(new Date(System.currentTimeMillis()));

		try {

			if (varlista.size() > 0) {

				varlistaReporteSiniestro = new ArrayList<ReporteSiniestro>();

				for (int x = 0; x < varlista.size(); x++) {
					String vrlSini = varlista.get(x).getNroSiniestro();
					ReporteSiniestro reporteSiniestro = siniSiniestroService
							.getReporteSiniestro(vrlSini);
					if (reporteSiniestro != null) {
						varlistaReporteSiniestro.add(reporteSiniestro);

					}
				}

				String rutaTemp = System.getProperty("java.io.tmpdir")
						+ "Reporte_Clientes_" + System.currentTimeMillis()
						+ ".xls";
				logger.debug("Ruta Archivo: " + rutaTemp);
				FacesContext fc = FacesContext.getCurrentInstance();
				ServletContext sc = (ServletContext) fc.getExternalContext()
						.getContext();
				String rutaTemplate = sc.getRealPath(File.separator + "excel"
						+ File.separator + "template_exportar_siniestros.xls");
				beans.put("exportar", varlistaReporteSiniestro);
				XLSTransformer transformer = new XLSTransformer();
				transformer.transformXLS(rutaTemplate, beans, rutaTemp);
				File archivoResp = new File(rutaTemp);
				FileInputStream in = new FileInputStream(archivoResp);
				HttpServletResponse response = (HttpServletResponse) contexto
						.getResponse();
				byte[] loader = new byte[(int) archivoResp.length()];
				response.addHeader("Content-Disposition",
						"attachment;filename=" + "Reporte_Cliente_"
								+ FechaActual + ".xls");
				response.setContentType("application/vnd.ms-excel");

				ServletOutputStream out = response.getOutputStream();

				while ((in.read(loader)) > 0) {
					out.write(loader, 0, loader.length);
				}
				in.close();
				out.close();

				FacesContext.getCurrentInstance().responseComplete();

			} else {
				throw new SyncconException(
						ErrorConstants.COD_ERROR_SINIESTROS_NRO_PLANILLAS_CERO,
						FacesMessage.SEVERITY_INFO);
			}

		} catch (SyncconException ex) {
			logger.error(ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(),
					ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		logger.info("Fin");
		return respuesta;
	}

	public String getEdaFecOcurrenciaCal() {
		return edaFecOcurrenciaCal;
	}

	public void setEdaFecOcurrenciaCal(String edaFecOcurrenciaCal) {
		this.edaFecOcurrenciaCal = edaFecOcurrenciaCal;
	}

	public Date getFecNotiDesde_b() {
		return fecNotiDesde_b;
	}

	public Date getFecNotiHasta_b() {
		return fecNotiHasta_b;
	}

	public void setFecNotiDesde_b(Date fecNotiDesde_b) {
		this.fecNotiDesde_b = fecNotiDesde_b;
	}

	public void setFecNotiHasta_b(Date fecNotiHasta_b) {
		this.fecNotiHasta_b = fecNotiHasta_b;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public static int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {
		logger.info("Inicio");
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		try {
			fechaInicial = df.parse(fechaInicioString);
			String fechaFinalString = df.format(fechaFinal);
			fechaFinal = df.parse(fechaFinalString);
		} catch (Exception e) {

			logger.info(e.getMessage());
		}

		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		logger.info("Fin");
		return ((int) dias);

	}

	public String cambiarEstadoFecha(ValueChangeEvent event) {
		logger.info("Inicio");

		String respuesta = "";
		int diastrasn;
		String cod = (String) event.getNewValue();
		siniSiniestro.setCodEstado(cod);

		setObligatorioEstado();

		try {

			logger.debug("test: " + (String) event.getNewValue());

			logger.debug(" boolean 1 : " + isRequiredFecAproRechazo());
			cod = (String) event.getNewValue();

			obligatorioEstado = Boolean.FALSE;
			String vCodEstado = cod;
			if (vCodEstado.equals("2") || vCodEstado.equals("3")
					|| vCodEstado.equals("7") || vCodEstado.equals("13")) {
				obligatorioEstado = Boolean.TRUE;
			}
			logger.debug("test: " + (String) event.getNewValue());

			if (!cod.equalsIgnoreCase("8")) {
				setRequiredFecAproRechazo(true);
			} else {

				setRequiredFecAproRechazo(false);
			}
			logger.debug(" boolean 2 : " + isRequiredFecAproRechazo());

			diastrasn = diasTranscurridos(siniSiniestro.getFecUltDocumSocio(),
					siniSiniestro.getFecUltDocum(),
					(String) event.getNewValue(),
					siniSiniestro.getFecAprobRechazo(),
					siniSiniestro.getFecEntregaOpCont());
			siniSiniestro.setCanDiasPendientes(diastrasn);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	private synchronized void check(int cod, ValueChangeEvent event)
			throws Exception {
		SiniObjetos siniObjetos = null;
		BigDecimal var1 = null;
		siniObjetos = siniObjetosService.obtener(cod);
		var1 = siniRobo.getImpPreLiquid();
		// siniRobo.setImpPreLiquid(siniRobo.getImpPreLiquid().add(siniObjetos.getValObjeto())
		// );

		boolean flag = ((Boolean) event.getNewValue()).booleanValue();
		if (flag == true) {

			if (var1 == null) {
				siniRobo.setImpPreLiquid(new BigDecimal("0"));
			}
			siniRobo.setImpPreLiquid(siniRobo.getImpPreLiquid().add(
					siniObjetos.getValObjeto()));
		} else {
			if (var1 == null) {
				siniRobo.setImpPreLiquid(new BigDecimal("0"));
			}
			if (var1 != null && var1.compareTo(BigDecimal.ZERO) > 0) {
				siniRobo.setImpPreLiquid(siniRobo.getImpPreLiquid().subtract(
						siniObjetos.getValObjeto()));
			}

		}
	}

	public String cambiarCheckCelular(ValueChangeEvent event) {
		logger.info("Inicio");

		String respuesta = "";
		int cod = 8;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckLentesSol(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 5;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckDiscIpod(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 7;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckLlanta(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckCartera(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckCosmeticos(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckPalmTabled(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 9;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckGMedicos(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckMaletin(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 4;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckLapicero(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckBolso(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckLibreDisp(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckBilletera(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 3;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckDni(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckSillaBB(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckPortadocumentos(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckMochila(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckCochiBB(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckLlaves(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarLentesOpticos(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckReloj(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public String cambiarCheckDisco(ValueChangeEvent event) {
		logger.info("Inicio");
		String respuesta = "";
		int cod = 1;
		try {
			check(cod, event);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		return respuesta;
	}

	public List<SelectItem> getListaTipoTrabajItem() {
		return listaTipoTrabajItem;
	}

	public void setListaTipoTrabajItem(List<SelectItem> listaTipoTrabajItem) {
		this.listaTipoTrabajItem = listaTipoTrabajItem;
	}

	public List<SelectItem> getListaTipRefCafaeItem() {
		return listaTipRefCafaeItem;
	}

	public void setListaTipRefCafaeItem(List<SelectItem> listaTipRefCafaeItem) {
		this.listaTipRefCafaeItem = listaTipRefCafaeItem;
	}

	public boolean isRequiredCafae() {
		return requiredCafae;
	}

	public void setRequiredCafae(boolean requiredCafae) {
		this.requiredCafae = requiredCafae;
	}

	public boolean isRequiredRentaHosp() {
		return requiredRentaHosp;
	}

	public void setRequiredRentaHosp(boolean requiredRentaHosp) {
		this.requiredRentaHosp = requiredRentaHosp;
	}

	public boolean isRequiredDesemp() {
		return requiredDesemp;
	}

	public void setRequiredDesemp(boolean requiredDesemp) {
		this.requiredDesemp = requiredDesemp;
	}

	public boolean isRequiredFecAproRechazo() {
		return requiredFecAproRechazo;
	}

	public void setRequiredFecAproRechazo(boolean requiredFecAproRechazo) {
		this.requiredFecAproRechazo = requiredFecAproRechazo;
	}

	public List<SelectItem> getListaParentescoItem2() {
		return listaParentescoItem2;
	}

	public void setListaParentescoItem2(List<SelectItem> listaParentescoItem2) {
		this.listaParentescoItem2 = listaParentescoItem2;
	}

	public boolean isReadonlyProducto() {
		return readonlyProducto;
	}

	public void setReadonlyProducto(boolean readonlyProducto) {
		this.readonlyProducto = readonlyProducto;
	}

	public String getVariasPrueba() {
		return variasPrueba;
	}

	public void setVariasPrueba(String variasPrueba) {
		this.variasPrueba = variasPrueba;
	}

	public List<GenProvincia> getListaProvincia() {
		return listaProvincia;
	}

	public void setListaProvincia(List<GenProvincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public List<GenDepartamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<GenDepartamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public List<GenDistrito> getListaDistrito() {
		return listaDistrito;
	}

	public void setListaDistrito(List<GenDistrito> listaDistrito) {
		this.listaDistrito = listaDistrito;
	}

	public List<TipoTrabajador> getListaTipoTrabajador() {
		return listaTipoTrabajador;
	}

	public void setListaTipoTrabajador(List<TipoTrabajador> listaTipoTrabajador) {
		this.listaTipoTrabajador = listaTipoTrabajador;
	}

	public boolean isReadonlyMostrar() {
		return readonlyMostrar;
	}

	public void setReadonlyMostrar(boolean readonlyMostrar) {
		this.readonlyMostrar = readonlyMostrar;
	}

	public boolean isVerSiniestro() {
		return verSiniestro;
	}

	public void setVerSiniestro(boolean verSiniestro) {
		this.verSiniestro = verSiniestro;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public File getTempFile() {
		return tempFile;
	}

	public void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public String getStyleVerSiniestro() {
		return styleVerSiniestro;
	}

	public void setStyleVerSiniestro(String styleVerSiniestro) {
		this.styleVerSiniestro = styleVerSiniestro;
	}

	public boolean isBotonExtra() {
		return botonExtra;
	}

	public void setBotonExtra(boolean botonExtra) {
		this.botonExtra = botonExtra;
	}

	public List<SelectItem> getListaDepartamentoPaisItem() {
		return listaDepartamentoPaisItem;
	}

	public void setListaDepartamentoPaisItem(
			List<SelectItem> listaDepartamentoPaisItem) {
		this.listaDepartamentoPaisItem = listaDepartamentoPaisItem;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Selection getSiniestroSelection() {
		return siniestroSelection;
	}

	public void setSiniestroSelection(Selection siniestroSelection) {
		this.siniestroSelection = siniestroSelection;
	}

	public List<ConsultaSiniestro> getVarlista() {
		return varlista;
	}

	public void setVarlista(List<ConsultaSiniestro> varlista) {
		this.varlista = varlista;
	}

	public List<ReporteSiniestro> getVarlistaReporteSiniestro() {
		return varlistaReporteSiniestro;
	}

	public void setVarlistaReporteSiniestro(
			List<ReporteSiniestro> varlistaReporteSiniestro) {
		this.varlistaReporteSiniestro = varlistaReporteSiniestro;
	}

	public Boolean getObligatorio() {
		obligatorio = Boolean.FALSE;
		if (listaDepartamentoPaisItem != null
				&& listaDepartamentoPaisItem.size() > 1) {
			obligatorio = Boolean.TRUE;
		}
		return obligatorio;
	}

	public int getNumero() {
		if (listaSiniSiniestro != null)
			numero = listaSiniSiniestro.size();
		else
			numero = 0;
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Boolean getObligatorioEstado() {
		return obligatorioEstado;
	}

	public void setObligatorioEstado(Boolean obligatorioEstado) {
		this.obligatorioEstado = obligatorioEstado;
	}

	public boolean isReadonlyFecha() {
		return readonlyFecha;
	}

	public void setReadonlyFecha(boolean readonlyFecha) {
		this.readonlyFecha = readonlyFecha;
	}

	public String getStrDiasNotificacion() {
		return strDiasNotificacion;
	}

	public void setStrDiasNotificacion(String strDiasNotificacion) {
		this.strDiasNotificacion = strDiasNotificacion;
	}

	public String getStrDiasDocumentacion() {
		return strDiasDocumentacion;
	}

	public void setStrDiasDocumentacion(String strDiasDocumentacion) {
		this.strDiasDocumentacion = strDiasDocumentacion;
	}

}
