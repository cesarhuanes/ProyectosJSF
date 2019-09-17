package com.cardif.satelite.constantes;

public interface Constantes {

//  Constantes Satelite
  public static final String TIP_PARAM_CABECERA = "C";
  public static final String TIP_PARAM_DETALLE = "D";

  public static final String COD_PARAM_COMPANIA = "001";
  public static final String COD_PARAM_TIPOS_LIBROS ="002";
  public static final String COD_PARAM_VIA_COBRO = "003";
  public static final String COD_PARAM_TIPO_PROCESO = "004";
  public static final String COD_PARAM_TIPOS_COMPROBANTE = "005";
  public static final String COD_PARAM_TIPOS_DOCUMENTO = "006";
  public static final String COD_PARAM_TIPO_VENTA = "007";
  public static final String COD_PARAM_TIPO_REPORTE = "008";
  public static final String COD_PARAM_ESTADO_VENTA = "009";
  public static final String COD_PARAM_TIPO_SISTEMA = "010";
  public static final String COD_PARAM_SOCIO = "011";
  public static final String COD_PARAM_SOCIO_TLMK = "012";
  public static final String COD_PARAM_MONEDA = "013";
  public static final String COD_PARAM_INDICADOR_IGV = "014";
  public static final String COD_PARAM_SOCIO_SOAT = "025";
  public static final String COD_PARAM_CANAL_VENTA_SOAT = "032";
  public static final String COD_PARAM_E_COMPROBANTE = "033";
  public static final String COD_PARAM_TIPO_SISTEMA_CC = "034";
  public static final String COD_PARAM_PROPIEDADES_SATELITE = "063";

  public static final Integer OPC_NIVEL_CERO = 0;
  public static final Integer OPC_NIVEL_UNO = 1;
  public static final Integer OPC_NIVEL_DOS = 2;

  public static final String FECHA_DEFAULT = "01/01/0001";
  public static final String MONTO_DEFAULT = "0.00";
  public static final String TIP_CAMBIO_DEFAULT = "0.000";
  public static final String CANTIDAD_DEFAULT = "0";
  public static final String TIP_COMP_DEFAULT = "00";
  public static final String TEXTO_DEFAULT = "";// "-"

  public static final String PREFIJO_ARCHIVO = "LE";
  public static final String RUC_CARDIF_SEGUROS = "20513328819";
  public static final String RUC_CARDIF_SERVICIOS = "20512830316";

  public static final String COD_REG_COMPRAS = "080100";
  public static final String COD_REG_VENTAS = "140100";
  public static final String COD_LIBRO_DIARIO = "050100";
  public static final String COD_LIBRO_MAYOR = "060100";

  public static final String COD_MONEDA_SOLES = "1";
  public static final String COD_MONEDA_DOLAR = "2";

  public static final String COD_CARDIF_SEGUROS = "1";
  public static final String COD_CARDIF_SERVICIOS = "2";

  public static final String REG_COMPRAS = "1";
  public static final String REG_VENTAS = "2";
  public static final String LIBRO_MAYOR = "3";
  public static final String LIBRO_DIARIO = "4";

  // Nombre de JOBs COBRANZA
  public static final String COBRANZA_CS_JOB_ENVIO = "JOB_ENVIO_CS";
  public static final String COBRANZA_CS_JOB_RESPUESTA = "JOB_RESPUESTA_CS";
  public static final String COBRANZA_VISA_JOB_ENVIO = "JOB_ENVIO_VISA";
  public static final String COBRANZA_VISA_JOB_RESPUESTA = "JOB_RESPUESTA_VISA";
  public static final String COBRANZA_MC_JOB_ENVIO = "JOB_ENVIO_MC";
  public static final String COBRANZA_MC_JOB_RESPUESTA = "JOB_RESPUESTA_MC";
  public static final String COBRANZA_IB_JOB_ENVIO = "JOB_ENVIO_IB";
  public static final String COBRANZA_IB_JOB_RESPUESTA = "JOB_RESPUESTA_IB";

  // Nombre de JOBs de SUSCRIPCION
  public static final String SUSCRIPCION_CARG_REG_VENTAS = "JOB_CARG_REG_VENTAS";
  public static final String SUSCRIPCION_LISTA_RAZON_SOCIAL = "036";

  // Nombre de JOBs de ACTUARIAL
  public static final String REPORTE_CALCULO_RESERVAS_ACSELE = "JOB_CAL_RESERVAS_ACSELE";
  public static final String REPORTE_CALCULO_RESERVAS_SEGYA = "JOB_CAL_RESERVAS_SEGYA";

  // Nombre de JOBs de Telemarketing
  public static final String TLMK_JOB_BASE_IB = "JOB_TLMK_IB";
  public static final String TLMK_JOB_BASE_SB = "JOB_TLMK_SB";
  public static final String TLMK_JOB_BASE_BC = "JOB_TLMK_BC";
  public static final String TLMK_JOB_BASE_RP = "JOB_TLMK_RP";
  public static final String TLMK_JOB_VIGILANCE = "JOB_TLMK_VIGILANCE";

  public static final String ESTADO_REGISTRADO = "REGISTRADO";
  public static final String ESTADO_ERROR = "ERROR";

  // ACTUALIZAR CODIGOS
  public static final Long SOCIO_ID_SB = 9L;
  public static final Long SOCIO_ID_RP = 3L;
  public static final Long SOCIO_ID_BC = 2L;
  public static final Long SOCIO_ID_IB = 19L;

  public static final String CONTABILIDAD_JOB_REG_VENTAS = "JOB_REG_VENTAS_SEGUROS";
  public static final String SUSCRIPCION_JOB_MIG_REG_VENTAS = "JOB_MIG_REG_VENTAS";

  // NOMBRES DE JOBS DE SINIESTROS
  public static final String COD_PARAM_LISTA_GENERO = "015";
  public static final String COD_PARAM_LISTA_EJECUTIVOS = "016";
  public static final String COD_PARAM_LISTA_RAMOS = "017";
  public static final String COD_PARAM_LISTA_COBERTURA = "018";
  public static final String COD_PARAM_LISTA_ESTADOS = "019";
  public static final String COD_PARAM_LISTA_RECHAZOS_AGRUPADOS = "020";
  public static final String COD_PARAM_LISTA_ESTADO_LEGAJO = "021";
  public static final String COD_PARAM_LISTA_RECHAZADOS = "022";
  public static final String COD_PARAM_LISTA_RESUMEN = "023";
  public static final String COD_PARAM_LISTA_SEGUROS = "024";
  public static final String COD_PARAM_SOCIOS_SINI = "026";
  public static final String COD_PARAM_TIPOS_DOCUMENTO_SINI = "027";
  public static final String COD_PARAM_LISTA_MONEDA = "028";
  public static final String COD_PARAM_LISTA_GENEROS_SINI = "029";
  public static final String COD_PARAM_LISTA_PARENTESCO_SINI = "030";
  public static final String COD_PARAM_LISTA_MOTIVOS_NO_ENTREGA = "031";
  public static final String COD_PARAM_LISTA_TIPO_REF_CAFAE = "035";
  public static final String COD_PARAM_SOCIOS_PLACAS = "083";

  /* INICIO SYNCCON JPALOMINO 28/10/2014 */
  // Modulo de Pagos (Prefijo MDP)
  public static final String MDP_TIPO_MARCADOR_PAGOS = "037";
  public static final String MDP_TIPO_PAGOS = "038";
  public static final String MDP_MEDIO_PAGOS = "039";

  public static final String MDP_CTAS_BANCO = "040";

  public static final String MDP_DIF_CAMBIO_WIN_PEN = "041";
  public static final String MDP_DIF_CAMBIO_LOSE_PEN = "042";
  public static final String MDP_DIF_CAMBIO_WIN_EUR = "043";
  public static final String MDP_DIF_CAMBIO_LOSE_EUR = "044";

  public static final String MDP_PAG_VARIOS_CTA_PROPINA = "045";
  public static final String MDP_PAG_VARIOS_T_DIARIO_PAGO = "046";

  public static final String MDP_CTA_GASTO_DETRAC_TRANS_COMISION = "047";
  public static final String MDP_PAG_DETRACCIONES_IMP_COMISION = "048";
  public static final String MDP_CENT_COST_DETRAC_TRANS_COMISION = "049";
  public static final String MDP_PAG_DETRACCIONES_T_DIARIO_PAGO = "050";
  public static final String MDP_PAG_DETRACCIONES_CTA_COMISION = "047";
  public static final String MDP_PAG_DETRACCIONES_CEN_COSTO = "049";

  public static final String MDP_PAG_RETENCION_CTA_PEN = "051";
  public static final String MDP_PAG_RETENCION_IMP = "052";
  public static final String MDP_PAG_RETENCION_MONTO_MIN = "053";
  public static final String MDP_PAG_RETENCION_CTA_PROPINA = "054";
  public static final String MDP_PAG_RETENCION_T_DIARIO_PAGO = "055";
  public static final String MDP_PAG_RETENCION_CTA_USD = "056";
  /* FIN SYNCCON JPALOMINO 28/10/2014 */

  /* INICIO SYNCCON JARIASS 19/01/2015 */
  public static final String MDP_DIF_CAMBIO_WIN_USD = "058";
  public static final String MDP_DIF_CAMBIO_LOSE_USD = "059";
  public static final String MDP_DIF_CAMBIO_WIN_EUR_USD = "060";
  public static final String MDP_DIF_CAMBIO_LOSE_EUR_USD = "061";
  public static final String MDP_PAG_SINIESTROS_T_DIARIO_PAGO = "062";
  public static final String MDP_PAG_COMIS_T_DIARIO_PAGO = "065";
  public static final String MDP_CTA_GASTO_ITF = "066";
  public static final String MDP_TRANSF_COMIS_PROV_EMPL = "067";
  public static final String MDP_ESTADOS = "068";
  public static final String MDP_MONEDAS = "069";
  public static final String MDP_DOC_PROVEEDOR = "070";
  public static final String MDP_PAG_TRASNF_T_DIARIO_PAGO = "071";
  public static final String MDP_RUTA_TRAMA = "072";
  /* FIN SYNCCON JARIASS 19/01/2015 */

  public static final String MDP_AUTH_SUNSYSTEMS_TOKEN = "TOKEN";

  /* INICIO SYNCCON JARIASS 15/04/2015 */
  public static final String COD_PARAM_MED_PAGO_SOAT = "079";
  public static final String COD_PARAM_CATEGORIA_SOAT = "080";
  public static final String COD_PARAM_USO_SOAT = "081";
  public static final String COD_PARAM_PRODUCTO_SOAT = "082";

  public static final String COD_SOCIO_SOAT_CENCOSUD = "01";
  public static final String COD_SOCIO_SOAT_COMPARA_BIEN = "02";
  public static final String COD_SOCIO_SOAT_DIRECTO = "03";
  public static final String COD_SOCIO_SOAT_FALABELLA = "04";
  public static final String COD_SOCIO_SOAT_RIPLEY = "05";
  public static final String COD_SOCIO_SOAT_RPP = "06";
  public static final String COD_SOCIO_SOAT_SUCURSAL = "07";
  /* INICIO SYNCCON JARIASS 15/04/2015 */
  	
  	public static final int PLAZO_DIAS_HABILES_SUNAT = 7;
  	public static final String COD_PARAM_ESTADO_CERTIFICADO = "088";
  	public static final int PLAZO_FECHA_SUNAT_OK = 1;
  	public static final int PLAZO_FECHA_SUNAT_NO_OK = 0;
  	
  	
  	/*
  	 * Codigos de respuesta de PPL
  	 */
  	public static final int RESPUESTA_PPL_VALIDADO = 0;
  	public static final int RESPUESTA_PPL_RECHAZADO = -1;
  	
  	
  	public static final String COD_PARAM_SUNAT_DATOS = "089";
  	public static final String USU_SUNAT_SEGUROS_RUC = "SEGUROS_RUC";
  	public static final String USU_SUNAT_SEGUROS_USUARIO = "SEGUROS_USUARIO";
  	public static final String USU_SUNAT_SEGUROS_CLAVE = "SEGUROS_CLAVE";
  	public static final String USU_SUNAT_SERVICIOS_RUC = "SERVICIOS_RUC";
  	public static final String USU_SUNAT_SERVICIOS_USUARIO = "SERVICIOS_USUARIO";
  	public static final String USU_SUNAT_SERVICIOS_CLAVE = "SERVICIOS_CLAVE";
  	
	// ------------------------ PRIMER PASE
	public static final String COD_DOC_VALORADO_PRIVADO = "01";
  	public static final String COD_DOC_VALORADO_PUBLICO = "02";
  	
  	public static final String PRODUCTO_TIPO_ARCHIVO_EXCEL = "EXCEL";
  	public static final String PRODUCTO_TIPO_ARCHIVO_TXT = "TXT";
  	public static final String PRODUCTO_TIPO_ARCHIVO_BD = "BD";
	public static final String MOD_IMPRESION_ESTADO_IMPRESO = "IMPRESO";
	public static final String MOD_IMPRESION_ESTADO_IMPRESA_DIG = "IMPRESA";
  	public static final String MOD_IMPRESION_ESTADO_EN_ESPERA = "EN_ESPERA";
  	public static final String MOD_IMPRESION_ESTADO_PENDIENTE = "PENDIENTE";
  	public static final String MOD_IMPRESION_ESTADO_REIMPRESO = "REIMPRESO";
  	
  	public static final int COD_NUM_DOC_VALORADO_DISPONIBLE = 0;
  	public static final int COD_NUM_DOC_VALORADO_USADO = 1;
  	
  	public static final int COD_NUM_DOC_VALORADO_EST_ACTIVO = 0;
  	public static final int COD_NUM_DOC_VALORADO_EST_ANULADO = 1;
  	
	public static final String MOD_IMP_VALIDACION_CERTIFICADO ="> Validación de Certificado";
  	public static final String MOD_IMP_CONFIRMACION_IMPRESION ="> Confirmación de impresión";
  	public static final String MOD_SUSC_VER_DETALLE = "> Ver Detalle";
  	
//  Cambio de valores por cambio de flag en base de datos
//  	public static final int PRODUCTO_ESTADO_ACTIVO = 0;
//  	public static final int PRODUCTO_ESTADO_INACTIVO = 1;
  	public static final int PRODUCTO_ESTADO_ACTIVO = 1;
  	public static final int PRODUCTO_ESTADO_INACTIVO = 0;
  	
  	public static final int SOCIO_ESTADO_ACTIVO = 0;
  	public static final int SOCIO_ESTADO_INACTIVO = 1;
  	
  	public static final int PRODUCTO_MOD_IMPRESION_SI = 1;
  	public static final int PRODUCTO_MOD_IMPRESION_NO = 0;
  	public static final int PRODUCTO_MOD_SUSCRIPCION_SI = 1;
  	public static final int PRODUCTO_MOD_SUSCRIPCION_NO = 0;
  	

  	public static final int PRODUCTO_MOD_TRAMA_DIARIA_SI=1;
  	public static final int PRODUCTO_MOD_TRAMA_DIARIA_NO=0;
  	public static final int PRODUCTO_MOD_TRAMA_MENSUAL_SI=1;
  	public static final int PRODUCTO_MOD_TRAMA_MENSUAL_NO=1;
  	
  	

//	public static final int COD_NUM_DOC_VALORADO_NO_USADO = 0;  //Para el Reemplazar del step 3 de modulo impresion
  	
  	
  	//---------------------------------------------------------------------------------
    public static final String COD_PARAM_ESTADO_LOTE_IMPRESION = "000001";
    public static final String COD_PARAM_RANGO_HORARIO = "000002";
    public static final String COD_PARAM_MOTIVO_REIMPRESION = "000003";
    public static final String COD_PARAM_TIPO_DOCUMENTO_ID = "000004";
    public static final String COD_PARAM_SEXO = "000005";
    public static final String COD_PARAM_MOTIVO_ANULACION = "000006";
    public static final String COD_PARAM_SOCIOS_MULTISOCIO = "000007";
    public static final String COD_PARAM_CATEGORIA_CLASE = "000008";
    public static final String COD_PARAM_CODIGO_CIA_SEGUROS = "000014";
    public static final String COD_PARAM_CODIGO_CIA_SEGUROS_VALOR = "COD_CIA";
    public static final String COD_PARAM_APESEG_COUNT = "000015";
    public static final String COD_PARAM_APESEG_COUNT_VALOR = "APESEG_COUNT";
    public static final String COD_PARAM_RUTA_APESEG = "000016";
    public static final String COD_PARAM_RUTA_APESEG_VALOR = "RUTA_ARCHIVO_APESEG";
    public static final String COD_PARAM_SBS_TIPO_ANEXO = "000017";
    public static final String COD_PARAM_SBS_ANIO = "000018";
    public static final String COD_PARAM_RUTA_SBS = "000019";
    public static final String COD_PARAM_RUTA_SBS_VALOR = "RUTA_ARCHIVO_SBS";
    public static final String COD_PARAM_FLB_TIPO_TRAMA = "000020";
    public static final String COD_PARAM_NRO_ULT_PERIODOS = "000021";
    public static final String COD_PARAM_NRO_ULT_PERIODOS_VALOR = "NRO_ULT_PERIODOS";
    public static final String COD_PARAM_DOC_IDENTIDAD_SUNAT = "000022";
    public static final String COD_PARAM_RUTA_SUNAT = "000023";
    public static final String COD_PARAM_RUTA_SUNAT_VALOR = "RUTA_ARCHIVO_SUNAT";
    public static final String COD_PARAM_IGV_IMPUESTO = "000024";
    public static final String COD_PARAM_IGV_IMPUESTO_VALOR = "IGV_IMPUESTO";
    public static final String COD_PARAM_PRODUCTO_SERIE = "000025";
    public static final String COD_PARAM_PRODUCTO_SERIE_ALTA = "ALTA";
    public static final String COD_PARAM_PRODUCTO_SERIE_BAJA = "BAJA";
    public static final String COD_PARAM_TIP_DOC_REG_VTA = "000026";
    public static final String COD_PARAM_TIPO_FORMATO_FECHA= "000029";
    
    
    public static final String REIMPRESION_SIN_MODIF_DATOS = "01";
    public static final String REIMPRESION_CON_MODIF_DATOS = "02";
    
    public static final String COD_CATEGORIA_CLASE_MOTO = "MOTO";
    public static final String COD_CATEGORIA_CLASE_AUTOMOVIL= "AUTOMOVIL";
    
    
    public static final String BTN_ARMAR_LOTE_SELECT_TODOS = "Seleccionar Todos";
    public static final String BTN_ARMAR_LOTE_DESELECT_TODOS = "Deseleccionar Todos";
    
    public static final String NUM_LOTE_FORMAT = "0000000000";
    
    
    /*
     * Opciones de confirmacion de impresion
     */
    public static final String MOD_IMP_IMPRESO_CORRECTAMENTE_SI = "SI";
    public static final String MOD_IMP_IMPRESO_CORRECTAMENTE_NO = "NO";
    public static final String MOD_IMP_IMPRESO_CORRECTAMENTE_REEMPLAZAR = "REEMPLAZAR";
    
    /*
     * Validacion del certificado diferenciandola por
     * color
     */
    public static final String MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_OK = "background-color: #D9EDF7;";
    public static final String MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_FAIED = "background-color: #F2DEDE;";
    public static final String MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_CLEAN = "background-color: white;";
    
    public static final int CANAL_PROD_ESTADO_ACTIVO = 0;
    public static final int CANAL_PROD_ESTADO_INACTIVO = 1;
    
    public static final String PRODUCTO_MOD_SUSCRIPCION = "SUSCRIPCION";
    public static final String PRODUCTO_MOD_IMPRESION = "IMPRESION";
    
    public static final int DET_TRAMA_DIA_MOTIVO_REIMPRESION = 1;
    public static final int DET_TRAMA_DIA_MOTIVO_ANULACION = 2;
    
    public static final String POLIZA_ESTADO_VIGENTE = "VIGENTE";
    public static final String POLIZA_ESTADO_ANULADO = "ANULADO";
    
    /* Canal de venta SOAT Digital */
    public static final String SOAT_CANAL_DIGITAL = "DIGITAL";
    
    public static final int ID_CANAL_DIGITAL = 1;
    
    
    /*
     * Nombre de los PRODUCTOS DIGITALES
     * 
   		SCS_DIGITAL
		BCS_DIGITAL
		CPB_DIGITAL
		DTO_DIGITAL
		FLB_DIGITAL
		RPY_DIGITAL
     * 
     * 
     */
    public static final String PRODUCTO_NOMBRE_FALABELLA = "FLB_DIGITAL";
    public static final String PRODUCTO_NOMBRE_RIPLEY = "RPY_DIGITAL";
    public static final String PRODUCTO_NOMBRE_CENCOSUD = "BCS_DIGITAL";
    public static final String PRODUCTO_NOMBRE_COMPARA_BIEN = "CPB_DIGITAL";
    public static final String PRODUCTO_NOMBRE_DIRECTO = "DTO_DIGITAL";
    public static final String PRODUCTO_NOMBRE_SUCURSAL = "SCS_DIGITAL";
    
    public static final String CANAL_PROD_NOMBRE_DIGITAL = "DIGITAL";
	public static final String POLIZA_ESTADO_ANULADA = "";
	public static final Integer COD_NUM_DOC_VALORADO_ACTIVO = null;
	public static final Integer COD_NUM_DOC_VALORADO_ANULADO = null;
	
	public static final String TIP_PERS_NATURAL = "N";
	public static final String TIP_PERS_JURIDICA = "J";
	
	public static final String TIP_DOC_DNI = "1";
	public static final String TIP_DOC_DNI_REG_VTA = "01";
	public static final String TIP_DOC_CE = "2";
	public static final String TIP_DOC_CE_REG_VTA = "03";
	public static final String TIP_DOC_RUC = "3";
	public static final String TIP_DOC_RUC_REG_VTA = "06";
	
	/* Necesario para APESEG */
	public static final String PAIS_PLACA_PERU = "0001";
	public static final String PAIS_PLACA_EXT = "9999";
    
	public static final String APESEG_REPORTADO_NO = "NO";
	public static final String APESEG_REPORTADO_SI = "SI";
	
	
	public static final String SBS_TIPO_ANEXO_PROD_PRIMAS = "01";
	public static final String SBS_TIPO_ANEXO_ANUL_PRIMAS = "03";
	public static final String SBS_TIPO_ANEXO_CONTRATO_SOAT = "05";
	
	public static final String SBS_DET_REPORTE_ESTADO_PROD = "PRODUCCION";
	public static final String SBS_DET_REPORTE_ESTADO_ANUL = "ANULACION";
	
	public static final String SBS_REPORTADO_SI = "SI";
	public static final String SBS_REPORTADO_NO = "NO";
	
	public static final String SBS_ESTADO_REPORTADO_PROD = "PRODUCCION";
	public static final String SBS_ESTADO_REPORTADO_ANUL = "ANULACION";
	
	public static final String FLB_TRAMA_DESPACHO = "TD";
	public static final String FLB_TRAMA_ANULACION = "TA";
	
	public static final String FLB_MOTIVO_DESPACHO = "619";
	public static final String FLB_MOTIVO_ANULACION = "782";
	
	public static final String REP_SUNAT_COD_AAA = "001";
	public static final String REP_SUNAT_PERIODO_ANUAL = "00";
	public static final String REP_SUNAT_ESTADO_INICIAL = "GENERADO";
	
	public static final String REP_REGISTRO_VENTA_TIPO_ALTA = "13";
	public static final String REP_REGISTRO_VENTA_TIPO_BAJA = "87";
	
	public static final String DESC_REP_REGISTRO_VENTA_TIPO_ALTA = "ALTA";
	public static final String DESC_REP_REGISTRO_VENTA_TIPO_BAJA = "BAJA";
	
	public static final String TIPO_CAMBIO_REG_VTA = "1";
	
	public static final String NRO_CORRELATIVO_DEFAULT = "999";
	
	
	public static final String SOC_GRUPO_COMERCIO_ACTIVO = "1";
	public static final String SOC_GRUPO_COMERCIO_INACTIVO = "0";
	
	public static final String ESTADO_REPORTE_SBS_PENDIENTE = "PENDIENTE";
	public static final String ESTADO_REPORTE_SBS_CONFIRMADO = "CONFIRMADO";	
	
	public static final String SUSCRIPCION_JOB_MARCA_MODELO = "JOB_SOAT_MarcaModelo";
	
	public static final String COD_CIA_SEGUROS = "021";
	
	public static final int COD_PRODUCTO_FLB_DIGITAL = 5;
	
	public static final String SUSCRIPCION_JOB_CARGA_MASTER = "JOB_SOAT_Carga_Master";
	public static final String SUSCRIPCION_JOB_CARGA_DIARIA = "JOB_SOAT_Trama_Diaria";
	public static final String SUSCRIPCION_JOB_CARGA_MENSUAL = "JOB_SOAT_Trama_Mensual";
	public static final String SUSCRIPCION_JOB_CARGA_CONCILIACION_TRAMA = "JOB_SOAT_Conciliacion_Trama";
	public static final String SUSCRIPCION_JOB_CARGA_CONCILIACION = "JOB_SOAT_Conciliacion";
	
	public static final String DIGITOS_RUC_PERSONA_NATURAL = "10";
	public static final String DIGITOS_RUC_PERSONA_JURIDICAS = "20";
	
	public static final String INDICADOR_REGVENTA_OBLIGATORIO_POR_PRODUCTO = "1";
	public static final String INDICADOR_REGVENTA_NO_OBLIGATORIO_POR_PRODUCTO = "0";

	
	public static final String SUSCRIPCION_RUTA_SERVIDOR_SSIS= "000030";
	public static final String SUSCRIPCION_RUTA_SERVIDOR_SSIS_DIARIA ="TRAMA_DIARIA\\";
	public static final String SUSCRIPCION_RUTA_SERVIDOR_SSIS_MENSUAL ="TRAMA_MENSUAL\\";
	public static final String SUSCRIPCION_RUTA_SERVIDOR_SSIS_MASTER ="TRAMA_MASTER\\";
		
	public static final String REPORTE_SBS_PRODUCCION_PRIMAS = "01";
	
	public static final String INDEX_INICIAL_GRILLA = "1";
	
	public static final String MENSAJE_VALIDACION_CAMPOS_VACIOS = "No se permiten campos vacios.";
	
	public static final String MENSAJE_VALIDACION_CONF_SOCIO_CANAL_REPETIDO = "El tipo de canal ya existe.";
	
	public static final String  TIPO_SEGURO="01";

	public static final String  TIPO_REASEGURO="03";

	public static final String  TIPO_MOVIMIENTO="05";

	public static final String  TIPO_ESTADO="06";
	
	public static final String  TIPO_CONTRATO="07";
	
	public static final String  DETALLE_CUENTA="08";
	
	public static final String  TIPO_PROCESO="09";
	
	public static final String  TIPO_PROCESO_ELIMINADO="0904";
	
	public static final String  TIPO_ESTADO_ACTIVO="0601";
	
	public static final String  TIPO_ESTADO_INACTIVO="0602";
	
	public static final String RUTA_SQL="1602";
	
	public static final String RUTA_EXCEL_DESTINO="1605";
	
	public static final String PROCESADO="0901";
	
	public static final String NOPROCESADO="0902";
	
	public static final String PROCESADO_ERROR="0903";
	
	public static final String INACTIVADO="0904";
	
	public static final String MSJ_ACTIVO="El parámetro se activará y por lo tanto,será ";
	public static final String MSJ_ACTIVO2="incluido en los reportes.¿Desea continuar?";
	
	public static final String MSJ_INACTIVO="El parámetro se desactivará y por lo tanto,no será";
	public static final String MSJ_INACTIVO2="incluido en los reportes.¿Desea continuar?";
	
	public static final String PROCESO_PENDIENTE="P";
	public static final String PROCESO_SOLICITUD="S";
	public static final String PROCESO_COMPLETADO="C";
	public static final String JOB_EJECUTAR_ARCHIVO="JOB_SBS_PROCESA_ARCHIVOS";
	
	public static final int COD_ESTADO_ACTIVO=1;
	public static final int COD_ESTADO_INACTIVO=0;
	
	

}
