package com.cardif.sunsystems.util;

public class ConstantesSun {

  public static final String AUTH_USR_SUN_LOGIN = "usr";
  public static final String AUTH_USR_SUN_PASS = "pass";
  public static final String COD_BANCO_BBVA = "0011";
  public static final String COD_PARAM_CARDIF_RAZON_SOCIAL = "036";
  public static final String COD_PARAM_CARDIF_DIRECCION = "086";
  public static final String COD_PARAM_FACTURACION_ELECTRONICA = "086";
  public static final String COD_PARAM_CAUSA_ANULACION = "087";
  public static final String COD_PARAM_PORCENT_CALCULO = "090";
  public static final String COD_PARAM_URL_COMPRO_DIGITAL = "091";
  public static final String COD_PARAM_COPIA_CORREO_DIGITAL = "092";
  public static final String COD_VALOR_TASA_RETENCION = "RETENCION";
  public static final String COD_VALOR_URL_COMPRO_SEGUR = "COMPROBANTE_SEGUROS";
  public static final String COD_VALOR_URL_COMPRO_SERVI = "COMPROBANTE_SERVICIOS";
  public static final String COD_VALOR_COPIA_CORREO_DIGITAL = "CORREO_COPIA";

  public static final String CODIGO_BUSQUEDA_DIRECCIONES = "P20513328819100";

  public static final String FEL_SERIE_RETENCION_DIGITAL = "R001-";
  public static final String FEL_TIP_COMP_FACTURA = "01";
  public static final String FEL_TIP_COMP_NOTA_CREDITO = "07";
  public static final String FEL_TIP_COMP_RETENCION = "20";
  public static final String FEL_TIP_COMP_PERCEPCION = "40";
  public static final String FEL_TIP_DOC_IDENT_DNI = "1";
  public static final String FEL_TIP_DOC_IDENT_RUC = "6";
  public static final String FEL_COD_REG_RETENCION = "01";
  public static final String FEL_COD_CTA_RETENCION_E01_SOL = "2012010003";
  public static final String FEL_COD_CTA_RETENCION_E01_DOL = "2022010003";
  public static final String FEL_COD_CTA_RETENCION_E02_BI = "401142";
  public static final String FEL_CDP_LONG__SERIE = "3";
  public static final String FEL_CDP_LONG_CORR = "8";
  
  public static final String COMIS_MEDIO_PAGO = "CARGO A CUENTA";
  public static final String COMIS_MEDIO_PAGO_IBK = "001";
  public static final String COMIS_MEDIO_PAGO_OTROS_BANCOS = "999";
  public static final String COMIS_TIPO_PAGO_COMIS = "COMISION";
  public static final String COMIS_TIPO_PAGO_ITF = "ITF";
  public static final String COMIS_TRANSFER_CENTRO_COSTO = "30000";
  public static final String COMIS_TRANSFER_COMPROB_SUNAT = "99";
  public static final String COMIS_TRANSFER_PROV_EMPLEADO = "P20999999999101";
  public static final int CTA_ReportingAccount = 47141910;
  public static final String CUENTA_PROV_ORDEN_PAGO = "00000000000000000000";

  // Estado de los asientos en SUN
  public static final String EST_ASIENTO_ANULADO = "ANULADO";
  public static final String EST_ASIENTO_PAGADO = "PAGADO";
  public static final String EST_ASIENTO_RECHAZADO_FEP = "RECHAZADO_FEP";
  public static final String EST_ASIENTO_REVERSADO = "REVERSADO";
  public static final String EST_ASIENTO_VALIDADO_FEP = "VALIDADO_FEP";
  public static final String EST_ASIENTO_VENCIDO = "VENCIDO";

  public static final String GLOSA_TRANSFER_COMIS = "Comision por transferencia";
  public static final String MEDIO_PAGO_TRANSFER = "003";

  // Opcione de Busqueda MDP
  public static final String MDP_BUSCAR_RETENCION = "BUSCAR_RETENCION";
  public static final String MDP_FEL_CORRELATIVO_GENERADO = "CORRELATIVO_RETENCION";
  
  // Tipo de Pago
  public static final String PAGOS_COMISION = "PAGOS COMISION";
  public static final String PAGOS_DETRACCION = "PAGOS DETRACCIONES";
  public static final String PAGOS_DETRACCION_MASIVA = "PAGOS_DETRACCION_MASIVA";
  public static final String PAGOS_ITF = "PAGOS ITF";
  public static final String PAGOS_RETENCION = "PAGOS RETENCIONES";
  public static final String PAGOS_SINIESTROS = "PAGOS SINIESTROS";
  public static final String PAGOS_TRANSFERENCIA = "PAGOS TRANSFERENCIA";
  public static final String PAGOS_VARIOS = "PAGOS VARIOS";

  public static final int PPL_FOLIACION_AUTOMATICA = 1;
  public static final int PPL_FOLIACION_MANUAL = 2;
  public static final int PPL_RESP_ID_ASIGNADO = 0;
  public static final int PPL_RESP_URL_XML = 1;
  public static final int PPL_RESP_URL_PDF = 2;
  public static final int PPL_RESP_EST_SUNAT = 3;
  public static final int PPL_RESP_FOL_ASIG = 4; //(Serie-Correlativo)
  public static final int PPL_RESP_BYT_PDF_B64 = 5;
  public static final int PPL_RESP_PDF417 = 6; //(Cadena de texto a imprimir en el PDF 417)
  public static final int PPL_RESP_HASH = 7; //(Cadena de texto)
  
  
  /*
   * La UNIDAD DE NEGOCIO se guarda en la tabla PARAMETRO con un formato diferente, por eso es necesario formatearla, teniendo en cuenta que el valor
   * de la tabla PARAMETRO es del 0-9
   */
  public static final String SSC_BUSINESS_UNIT_PATTERN = "E0{0}";

  // Componentes, Metodos y parametros para SSC
  public static final String SSC_AllocationMarker_P = "P";
  public static final String SSC_AllocationMarker_W = "W";
  public static final String SSC_AllocationMarkerUpdate = "AllocationMarkerUpdate";
  public static final String SSC_AmendMarker = "AmendMarker";
  public static final String SSC_AnalysisCode9 = "01";
  public static final String SSC_BudgetCode = "A";
  public static final String SSC_BusinessUnit_E01 = "E01";
  public static final String SSC_BusinessUnit_E02 = "E02";
  public static final String SSC_ComponenteAccounts = "Accounts";
  public static final String SSC_ComponenteAddress = "Address";
  public static final String SSC_ComponenteBankDetails = "BankDetails";
  public static final String SSC_ComponenteCurrencyDailyRates = "CurrencyDailyRates";
  public static final String SSC_ComponenteJournal = "Journal";
  public static final String SSC_ComponenteLedgerTransaction = "LedgerTransaction";
  public static final String SSC_ComponenteSupplier = "Supplier";
  public static final String SSC_Debit_Credit = "B";
  public static final String SSC_filter_operador_and = "AND";
  public static final String SSC_filter_operador_or = "OR";
  public static final String SSC_filter_operador_Equals = "EQU";
  public static final String SSC_filter_operador_Not_Equal = "NEQU";
  public static final String SSC_filter_operador_Greater_Than = "GT";
  public static final String SSC_filter_operador_Greater_Than_Or_Equals = "GTE";
  public static final String SSC_filter_operador_Less_Than = "LT";
  public static final String SSC_filter_operador_Less_Than_Or_Equals = "LTE";
  public static final String SSC_filter_operador_List = "IN";
  public static final String SSC_filter_operador_Like = "Like";
  public static final String SSC_filter_operador_Range = "BETWEEN";

  public static final String SSC_MetodoImport = "Import";
  public static final String SSC_MetodoLedgerAnalysisUpdate = "LedgerAnalysisUpdate";
  public static final String SSC_MetodoLoadAndPost = "LoadAndPost";
  public static final String SSC_MetodoQuery = "Query";
  public static final int SSC_postingtype_contabilizar = 1;
  public static final int SSC_postingtype_contabilizar_sinerrores = 2;
  public static final int SSC_postingtype_solovalidar = 0;

  public static final String SSC_SupplementaryExtension_YES = "1";
  public static final String SSC_SupplementaryExtension_NO = "0";

  // Utilidades
  public static final String UTL_CHR_SEPARADOR = ";";
  public static final String UTL_CHR_VACIO = "";
  public static final String UTL_NUM_VACIO = "0";
  public static final String UTL_FORMATO_FECHA_SUNSYSTEMS = "ddMMyyyy";
  public static final String UTL_FORMATO_FECHA_PAPERLESS = "yyyy-MM-dd";
  public static final String UTL_FORMATO_FECHA_PAPERLESS2 = "yyyyMMdd";
  public static final String UTL_MONEDA_SOLES = "PEN";
  public static final String UTL_MONEDA_DOLARES = "USD";
  public static final String UTL_MONEDA_EURO = "EUR";
  public static final String UTL_RELLENAR_DERECHA = "RELLENAR_DERECHA";
  public static final String UTL_RELLENAR_IZQUIERDA = "RELLENAR_IZQUIERDA";
  public static final String UTL_UBIGEO_CARDIF_COD = "150131";
  public static final String UTL_UBIGEO_CARDIF_DEPARTAMENTO = "LIMA";
  public static final String UTL_UBIGEO_CARDIF_DISTRITO = "SAN ISIDRO";
  public static final String UTL_UBIGEO_CARDIF_PAIS = "PE";
  public static final String UTL_UBIGEO_CARDIF_PROVINCIA = "LIMA";
  public static final String UTL_UBIGEO_CARDIF_URB = "DESCONOCIDO";

  public static final String SSC_MetodoLedgerTransaction_StatusSUCCESS = "success";
  public static final String SSC_MetodoLedgerTransaction_StatusFAIL = "fail";

//  Codigos de la tabla OPCION_MENU para filtrar autenticacion con SunSystems
  public static final String TES_OPCION_MENU_ASIGNACION_FIRMANTES = "413003";
  public static final String TES_OPCION_MENU_MODULO_PAGO = "413008";
  public static final String TES_OPCION_MENU_PAGO_BANCARIO = "413009";
  public static final String TES_OPCION_MENU_COMISION_E_ITF = "413010";
  public static final String TES_OPCION_MENU_TRANSFER_ENTRE_CUENTAS = "413011";
  public static final String TES_OPCION_MENU_GENERAR_CERT_RETENCION = "413014";
  public static final String TES_OPCION_MENU_ANULAR_CERT_RETENCION = "413015";
  public static final String TES_OPCION_MENU_REVERSAR_CERT_RETENCION = "413016";
  public static final String TES_OPCION_MENU_GENERAR_REPORTE_SBS_MODELO_UNO = "888003";
  public static final String TES_OPCION_MENU_GENERAR_REPORTE_SBS_MODELO_UNO_LISTADO = "888004";
  
}