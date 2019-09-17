package com.cardif.satelite.reportesbs.service;

import com.cardif.satelite.model.reportesbs.Saldos;

public interface SaldosService {

	Saldos listaSaldosByParam(String monedaCuenta, String monedaModeloValor,
			Long codigoEmpresa, String tipoReaseguro);

}
