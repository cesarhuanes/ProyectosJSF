package com.cardif.satelite.reportesbs.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.reportesbs.Empresa;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.Proceso;
import com.cardif.satelite.model.reportesbs.ProcesoParametro;
import com.cardif.satelite.model.reportesbs.Producto;
import com.cardif.satelite.model.reportesbs.ProductoSocio;
import com.cardif.satelite.model.reportesbs.Socio;

public interface RepParametroService {

	public List<Parametro> listarTipoReaseguro(String codParametro)
			throws SyncconException;

	public List<Parametro> listarTipo(String codParametro)
			throws SyncconException;

	public List<Parametro> listarTipoMovimiento(String codParametro)
			throws SyncconException;

	public List<Parametro> listarTipoEstado(String codParametro)
			throws SyncconException;

	public List<Parametro> listarTipoContrato(String codParametro)
			throws SyncconException;

	public List<Empresa> listarEmpresa() throws SyncconException;

	public List<Socio> listarSocio() throws SyncconException;

	public List<Producto> listarProducto() throws SyncconException;

	public List<Parametro> listarDetalleCuenta(String codParametro)
			throws SyncconException;

	public List<ProcesoParametro> buscarListaProcesos(String codTipoContrato,
			String codTipoSeguro, String codTipoReaseguro,
			String codTipoMovimiento, String codEstado, Integer codEmpresa,
			Integer codSocio, Integer codProducto, String cuentaSaldoSoles,
			String cuentaPrimaSoles, String cuentaDescuentoSoles,
			String cuentaSaldoDolares, String cuentaPrimaDolares,
			String cuentaDescuentoDolares, String detalleCuenta);

	public boolean insertarProcesoParametro(Proceso bean)
			throws SyncconException;

	public Proceso buscarProceso(Long codProcesoParametro)
			throws SyncconException;

	public boolean actualizarProcesoParametro(Proceso bean)
			throws SyncconException;

	public List<Empresa> listaSBS(Long codEmpresa) throws SyncconException;

	public int cantidadCuentas(Long codParametro, Long numCuenta)
			throws SyncconException;

	public void insertaProductoSocio(ProductoSocio productoSocio)
			throws SyncconException;

	public boolean existeRiesgo(String codRiesgo) throws SyncconException;

}
