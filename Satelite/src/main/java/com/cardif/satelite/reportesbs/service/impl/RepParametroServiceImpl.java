package com.cardif.satelite.reportesbs.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.reportesbs.Empresa;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.Proceso;
import com.cardif.satelite.model.reportesbs.ProcesoParametro;
import com.cardif.satelite.model.reportesbs.Producto;
import com.cardif.satelite.model.reportesbs.ProductoSocio;
import com.cardif.satelite.model.reportesbs.Socio;
import com.cardif.satelite.reportesbs.dao.EmpresaMapper;
import com.cardif.satelite.reportesbs.dao.ProcesoParametroMapper;
import com.cardif.satelite.reportesbs.dao.ProductoMapper;
import com.cardif.satelite.reportesbs.dao.RepParametroMapper;
import com.cardif.satelite.reportesbs.dao.SocioMapper;
import com.cardif.satelite.reportesbs.service.RepParametroService;

@Service("repParametroService")
public class RepParametroServiceImpl implements RepParametroService {
	public static final Logger log = Logger
			.getLogger(RepParametroServiceImpl.class);

	@Autowired
	private RepParametroMapper parametrosMapper;
	@Autowired
	private EmpresaMapper empresaMapper;
	@Autowired
	private SocioMapper socioSbsMapper;
	@Autowired
	private ProductoMapper productoSbsMapper;
	@Autowired
	private ProcesoParametroMapper procesoParametroMapper;

	@Override
	public List<Parametro> listarTipoReaseguro(String codParametro)
			throws SyncconException {
		log.info("Inicio Tipo Reaseguro");
		List<Parametro> lista = null;
		int size = 0;
		lista = parametrosMapper.getListaParametros(codParametro);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Reaseguros = " + size + "]");
		return lista;
	}

	@Override
	public List<Parametro> listarTipo(String codParametro)
			throws SyncconException {
		log.info("Inicio Tipo Seguro");
		List<Parametro> lista = null;
		int size = 0;
		lista = parametrosMapper.getListaParametros(codParametro);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Seguros = " + size + "]");
		return lista;
	}

	@Override
	public List<Parametro> listarTipoMovimiento(String codParametro)
			throws SyncconException {
		log.info("Inicio Tipo Movimiento");
		List<Parametro> lista = null;
		int size = 0;
		lista = parametrosMapper.getListaParametros(codParametro);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Tipo Movimiento = " + size + "]");
		return lista;
	}

	@Override
	public List<Parametro> listarTipoEstado(String codParametro)
			throws SyncconException {
		log.info("Inicio Tipo Estados");
		List<Parametro> lista = null;
		int size = 0;
		lista = parametrosMapper.getListaParametros(codParametro);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Estados = " + size + "]");
		return lista;
	}

	@Override
	public List<Parametro> listarTipoContrato(String codParametro)
			throws SyncconException {
		log.info("Inicio Tipo Contrato");
		List<Parametro> lista = null;
		int size = 0;
		lista = parametrosMapper.getListaParametros(codParametro);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Tipo Constrato = " + size + "]");
		return lista;
	}

	@Override
	public List<Empresa> listarEmpresa() throws SyncconException {
		log.info("Inicio Lista Empresa");
		List<Empresa> lista = null;
		int size = 0;
		lista = empresaMapper.getListaEmpresa();
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Empresas = " + size + "]");
		return lista;
	}

	@Override
	public List<Socio> listarSocio() throws SyncconException {
		log.info("Inicio Lista Socio");
		List<Socio> lista = null;
		int size = 0;
		lista = socioSbsMapper.getListaSocio();
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Socios = " + size + "]");
		return lista;
	}

	@Override
	public List<Producto> listarProducto() throws SyncconException {
		log.info("Inicio Producto");
		List<Producto> lista = null;
		int size = 0;
		lista = productoSbsMapper.getListaProducto();
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Producto = " + size + "]");
		return lista;
	}

	@Override
	public List<Parametro> listarDetalleCuenta(String codParametro)
			throws SyncconException {
		log.info("Inicio Detalle Cuenta");
		List<Parametro> lista = null;
		int size = 0;
		lista = parametrosMapper.getListaParametros(codParametro);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Detalle Cuenta = " + size + "]");
		return lista;
	}

	@Override
	public List<ProcesoParametro> buscarListaProcesos(String codTipoContrato,
			String codTipoSeguro, String codTipoReaseguro,
			String codTipoMovimiento, String codEstado, Integer codEmpresa,
			Integer codSocio, Integer codProducto, String cuentaSaldoSoles,
			String cuentaPrimaSoles, String cuentaDescuentoSoles,
			String cuentaSaldoDolares, String cuentaPrimaDolares,
			String cuentaDescuentoDolares, String detalleCuenta) {
		log.info("Inicio buscar parametros");

		Long cSaldoSoles = 0L;
		Long cPrimaSoles = 0L;
		Long cDesSoles = 0L;
		Long cSaldoDolar = 0L;
		Long cPrimaDolar = 0L;
		Long cDesDolar = 0L;

		List<ProcesoParametro> lista = null;
		int size = 0;

		if (detalleCuenta.trim().equals("") || detalleCuenta == null) {
			detalleCuenta = "T";
		}
		if (cuentaSaldoSoles.trim().equals("")) {
			cSaldoSoles = 0L;

		} else {
			cSaldoSoles = Long.valueOf(cuentaSaldoSoles);
		}
		if (cuentaPrimaSoles.trim().equals("")) {
			cPrimaSoles = 0L;

		} else {
			cPrimaSoles = Long.valueOf(cuentaPrimaSoles);
		}
		if (cuentaDescuentoSoles.trim().equals("")) {
			cDesSoles = 0L;

		} else {
			cDesSoles = Long.valueOf(cuentaDescuentoSoles);
		}
		if (cuentaSaldoDolares.trim().equals("")) {
			cSaldoDolar = 0L;

		} else {
			cSaldoDolar = Long.valueOf(cuentaSaldoDolares);
		}
		if (cuentaPrimaDolares.trim().equals("")) {
			cPrimaDolar = 0L;

		} else {
			cPrimaDolar = Long.valueOf(cuentaPrimaDolares);
		}
		if (cuentaDescuentoDolares.trim().equals("")) {
			cDesDolar = 0L;
		} else {
			cDesDolar = Long.valueOf(cuentaDescuentoDolares);
		}

		lista = procesoParametroMapper.getListaProcesosParametros(
				codTipoContrato, codTipoSeguro, codTipoReaseguro,
				codTipoMovimiento, codEstado, codEmpresa, codSocio,
				codProducto, cSaldoSoles, cPrimaSoles, cDesSoles, cSaldoDolar,
				cPrimaDolar, cDesDolar, detalleCuenta);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros procesos = " + size + "]");
		return lista;

	}

	@Override
	public boolean insertarProcesoParametro(Proceso bean)
			throws SyncconException {
		boolean isGrabar = false;

		try {

			procesoParametroMapper.insertSelective(bean);
			isGrabar = true;
		} catch (Exception e) {
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}

		return isGrabar;
	}

	@Override
	public Proceso buscarProceso(Long codProcesoParametro)
			throws SyncconException {
		log.info("Inicio");

		Proceso proceso = null;
		try {
			if (log.isDebugEnabled())
				log.debug("Input [ " + codProcesoParametro + " ]");

			proceso = procesoParametroMapper
					.selectByPrimaryKey(codProcesoParametro);
			if (proceso.getCuentaSaldoSoles() == "0") {
				proceso.setCuentaSaldoSoles(null);
			}
			if (proceso.getCuentaDescuentoSoles() == "0") {
				proceso.setCuentaDescuentoSoles(null);
			}
			if (proceso.getCuentaPrimaSoles() == "0") {
				proceso.setCuentaPrimaSoles(null);
			}
			if (proceso.getCuentaDescuentoDolares() == "0") {
				proceso.setCuentaDescuentoDolares(null);
			}
			if (proceso.getCuentaPrimaDolares() == "0") {
				proceso.setCuentaPrimaDolares(null);
			}
			if (proceso.getCuentaSaldoDolares() == "0") {
				proceso.setCuentaSaldoDolares(null);
			}
			if (log.isDebugEnabled())
				log.debug("Output [ " + BeanUtils.describe(codProcesoParametro)
						+ " ]");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SyncconException(COD_ERROR_BD_INSERTAR);
		}
		log.info("Fin");
		return proceso;

	}

	@Override
	public boolean actualizarProcesoParametro(Proceso bean)
			throws SyncconException {
		boolean isActualizar = false;

		try {

			procesoParametroMapper.updateByPrimaryKey(bean);
			isActualizar = true;
		} catch (Exception e) {
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}

		return isActualizar;
	}

	@Override
	public List<Empresa> listaSBS(Long codEmpresa) throws SyncconException {
		log.info("Inicio cos sbs");
		List<Empresa> lista = null;
		int size = 0;
		lista = empresaMapper.selectCodSbs(codEmpresa);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Socio = " + size + "]");
		return lista;
	}

	@Override
	public int cantidadCuentas(Long codParametro, Long numCuenta)
			throws SyncconException {
		int cantCuentas = 0;

		if (codParametro == null) {
			codParametro = 0L;
		}
		if (numCuenta == null) {
			numCuenta = 0L;
		}
		cantCuentas = procesoParametroMapper.cantidadCuentas(codParametro,
				numCuenta);
		log.info("Cuentas repetidas del numero" + numCuenta + "= "
				+ cantCuentas);
		return cantCuentas;
	}

	@Override
	public void insertaProductoSocio(ProductoSocio productoSocio)
			throws SyncconException {
		int cantProductoSocio = 0;

		cantProductoSocio = procesoParametroMapper.cantProductoSocio(
				productoSocio.getCodProducto(), productoSocio.getCodSocio());
		if (cantProductoSocio == 0) {
			procesoParametroMapper.insertProductoSocio(productoSocio);
		}
	}

	@Override
	public boolean existeRiesgo(String codRiesgo) throws SyncconException {
		int cantRiesgos = 0;
		boolean flagExisteRiesgo = false;
		cantRiesgos = procesoParametroMapper.cantRiesgo(codRiesgo);
		if (cantRiesgos > 0) {
			flagExisteRiesgo = true;
		}
		return flagExisteRiesgo;
	}

}
