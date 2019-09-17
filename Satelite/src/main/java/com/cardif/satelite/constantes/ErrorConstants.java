package com.cardif.satelite.constantes;

public interface ErrorConstants {

  public static final String ERROR_SYNCCON = "ERROR SYNCCON: ";
  public static final String MSJ_ERROR_GENERAL = "Lo sentimos, ha ocurrido un error";
  public static final String MSJ_ERROR = "error";
  public static final String COD_ERROR_GENERAL = "10000";

  /* Errores Generales */
  public static final String COD_ERROR_LOGIN_INVALIDO = "10001";
  public static final String COD_ERROR_USUARIO_INHABILITADO = "10002";
  public static final String COD_ERROR_CONTRASENA_EQUIVOCADA = "10003";
  public static final String COD_ERROR_CONTRASENAS_DIFERENTES = "10004";
  public static final String COD_ERROR_SELECCION = "10005";
  public static final String COD_ERROR_CAMPOS_OBLIGATORIOS = "10006";
  public static final String COD_ERROR_AUTENTICACION = "10007";
  public static final String COD_ERROR_PERFIL = "10008";

  public static final String COD_ERROR_BASE_DATOS = "11000";

  public static final String COD_ERROR_BD_INSERTAR = "11001";
  public static final String COD_ERROR_BD_ACTUALIZAR = "11002";
  public static final String COD_ERROR_BD_ELIMINAR = "11003";
  public static final String COD_ERROR_BD_BUSCAR = "11004";
  public static final String COD_ERROR_BD_OBTENER = "11005";
  public static final String COD_ERROR_BD_INTEGRIDAD = "11006";

  public static final String COD_ERROR_CARGAR_EXCEL = "12001";
  public static final String COD_ERROR_TRANSFORMAR_EXCEL = "12001";
  public static final String COD_ERROR_JOB = "12006";
  public static final String COD_ERROR_ARCHIVO = "12007";
  public static final String COD_ERROR_PERIODO = "12008";

  public static final String COD_ERROR_EXCLUIDO = "12009";
  public static final String COD_ERROR_NO_PRIMA = "12010";
  public static final String COD_ERROR_PLACA = "12011";
  public static final String COD_ERROR_FEC_VIG_ANTERIOR = "12012";
  public static final String COD_ERROR_FEC_VIG_POSTERIOR = "12013";
  public static final String COD_ERROR_CARGA_NULO = "12014";
  public static final String COD_ERROR_FEC_VIG_FIN = "12015";
  public static final String COD_ERROR_FEC_CIERRE_INI = "12016";
  public static final String COD_ERROR_FEC_CIERRE_FIN = "12017";
  public static final String COD_ERROR_REPORTE_EJECUCION = "12018";
  public static final String COD_ERROR_RANGO_FEC = "12019";

  public static final String COD_ERROR_ELIMINAR_LISTA = "12020";
  public static final String COD_ERROR_FEC_VIG_FIN_EMPTY = "12021";
  public static final String COD_ERROR_CUOTAS = "12022";
  public static final String COD_ERROR_CANT_PROD = "12023";
  public static final String COD_ERROR_ARCHIVO_INPUT = "12024";
  public static final String COD_ERROR_ARCHIVO_FEEDBACK = "12025";
  public static final String COD_ERROR_CANT_CERO = "12026";
  public static final String COD_ERROR_LISTA_VACIA = "12027";
  public static final String COD_ERROR_RANGO_FECHAS = "12028";
  public static final String COD_ERROR_RANGO_FECHAS_CONTABLES = "12029";
  public static final String COD_ERROR_COMPROBANTE_FECHA_DESDE_HASTA = "12030";
  public static final String DATOS_GUARDADOS_CORRECTAMENTE = "12200";

  public static final String COD_ERROR_CONSUL_PLACA = "12100";
  public static final String COD_ERROR_CONSUL_PROPIETARIO = "12101";
  public static final String COD_ERROR_CONSUL_DNI = "12102";
  public static final String COD_ERROR_CONSUL_PLACA_EXISTE = "12103";
  public static final String COD_ERROR_VALIDA_EDAD = "12104";
  public static final String COD_ERROR_ACCESO_PROPIETARIO = "12105";
  public static final String COD_ERROR_ACCESO_VEHICULAR = "12106";

  public static final String COD_ERROR_PERIODO_CERRADO = "13001";
  public static final String COD_ERROR_PERIODO_NO_PROCESADO = "13002";
  public static final String COD_ERROR_PERIODO_PENDIENTE = "13003";
  public static final String COD_ERROR_PERIODO_COD_NOEXISTE = "13004";
  public static final String COD_ERROR_PERIODO_TIPO_ARCHIVO_TXT = "13005";
  public static final String COD_ERROR_PERIODO_TIPO_ARCHIVO_XLSX = "13006";
  public static final String COD_ERROR_PERIODO_CERRADO_NO_EXPORTAR = "13007";
  public static final String COD_ERROR_PERIODO_CERRADO_NO_PROCESAR = "13008";
  public static final String COD_ERROR_LEER_ARCHIVO_EXCEL = "13009";

  public static final String COD_ERROR_SUSCRIP_FECHA_DESDE_HASTA = "13010";
  public static final String COD_ERROR_SUSCRIP_LISTA_VACIA_AL_EXPORTAR = "13011";

  public static final String COD_ERROR_WS_ACSELE = "14000";
  public static final String COD_ERROR_DOCU_WS_ACSELE = "14001";
  public static final String COD_ERROR_POLIZA_WS_ACSELE = "14002";

  public static final String COD_ERROR_FEC_VALIDEZ = "15000";

  public static final String COD_ERROR_REGISTRO_NO_SELECCIONADO = "16000";
  public static final String COD_ERROR_BUSQUEDA_PENDIENTE = "16001";
  public static final String COD_ERROR_SINIESTROS_NO_SELECCIONADOS = "16002";
  public static final String COD_ERROR_SINIESTROS_FECHA_APROBO_RECHAZA = "16003";
  public static final String COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_SOCIO = "16004";
  public static final String COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_SOCIO_CON_FECHA_INI_VIGENCIA = "16005";
  public static final String COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_SOCIO_CON_FECHA_ACTUAL = "16006";
  public static final String COD_ERROR_SINIESTROS_NRO_PLANILLAS = "16007";
  public static final String COD_ERROR_SINIESTROS_NRO_PLANILLAS_CERO = "16027";

  public static final String COD_ERROR_SINIESTROS_AL_MENOS_UNA_COBERTURA_CAFAE = "16008";
  public static final String COD_ERROR_SINIESTROS_FECHA_OCURRENCIA_CON_FECHA_ACTUAL = "16009";
  public static final String COD_ERROR_SINIESTROS_FECHA_NOTIFICACION_CARDIF_CON_FECHA_ACTUAL = "16010";
  public static final String COD_ERROR_SINIESTROS_FECHA_ULT_DOCUMEN_CARDIF_CON_FECHA_ACTUAL = "16011";
  public static final String COD_ERROR_SINIESTROS_FECHA_INICIO_VIGENCIA_CON_FECHA_ACTUAL = "16012";
  public static final String COD_ERROR_SINIESTROS_FECHA_NACIMIENTO_CON_FECHA_ACTUAL = "16013";
  public static final String COD_ERROR_SINIESTROS_FECHA_FIN_VIGENCIA_CON_FECHA_ACTUAL = "16014";
  public static final String COD_ERROR_SINIESTROS_FECHA_ULT_DOCUM_CARDIF_Y_NOT_CON_FECHA_ACTUAL = "16015";
  public static final String COD_ERROR_SINIESTROS_FECHA_EMI_Y_FECHA_APROBACION = "16016";
  public static final String COD_ERROR_SINIESTROS_FECHA_ENTRE_Y_FECHA_EMI_CHECE = "16017";
  public static final String COD_ERROR_SINIESTROS_FECHA_EMI_CHEQUE_FECHA_ACTUAL = "16018";
  public static final String COD_ERROR_SINIESTROS_FECHA_APROB_RECHA_Y_FECHA_INICIO_VIGENCIA = "16019";
  public static final String COD_ERROR_SINIESTROS_FECHA_ENTRE_Y_FECHA_ACTUAL = "16020";
  public static final String COD_ERROR_SINIESTROS_FECHA_PROBACION_RECHA_Y_FECHA_ACTUAL = "16021";
  public static final String COD_ERROR_SINIESTROS_ESTADO_APROBADO_REQUIERE_LLENAR_PAGOS = "16022";
  public static final String COD_ERROR_SINIESTROS_FECHA_OCURRENCIA_CON_FECHA_NOTIFICACION_CARDIF = "16023";
  public static final String COD_ERROR_SINIESTROS_FECHA_FIN_VIGENCIA_CON_FECHA_INI_VIGENCIA = "16024";
  public static final String COD_ERROR_SINIESTROS_FECHA_NACIMIENTO_CON_FECHA_OCURRENCIA = "16025";
  public static final String COD_ERROR_SINIESTROS_FECHA_PROBACION_RECHA_Y_FECHA_ULTIMA_DOCUMENT_CARDIF = "16026";
  public static final String COD_ERROR_SINIESTROS_FECHA_ULT_DOCUMEN_SOCIO_CON_FECHA_ACTUAL = "16028";
  public static final String COD_ERROR_SINIESTROS_FECHA_ULT_DOC_SOCIO_Y_NOT_CON_FECHA_ULT_DOC_CARDIF = "16029";
  public static final String COD_ERROR_SINIESTROS_FECHA_NOT_SOCIO_Y_NOT_CON_FECHA_NOT_CARDIF = "16030";
  public static final String COD_ERROR_SINIESTROS_FECHA_PROBACION_RECHA_Y_FECHA_NOTIF_CARDIF = "16031";

  public static final String COD_ERROR_PERIODO_INVALIDO = "17000";
  public static final String COD_ERROR_NO_MASTER = "17001";
  public static final String COD_ERROR_FORMATO_INCORRECTO = "17002";
  public static final String COD_ERROR_SINIESTROS_CON_NRO_PLANILLA = "17003";
  public static final String COD_ERROR_SINIESTROS_NO_APROBADOS = "17004";
  public static final String COD_ERROR_NO_COINCIDEN_SOCIOS = "17005";
  public static final String COD_ERROR_NO_COINCIDEN_POLIZAS = "17006";
  public static final String COD_ERROR_NO_COINCIDEN_PRODUCTOS = "17007";
  public static final String COD_ERROR_PERIODO_VACIO = "17008";

  public static final String COD_ERROR_FEC_NO_INGRESADA = "18000";
  public static final String COD_ERROR_SOCIO_MASTER = "18001";

  public static final String COD_ERROR_FILA_NO_SELECCIONADA = "19000";
  public static final String COD_ERROR_RANGO_INVALIDO = "19001";

  public static final String COD_ERROR_REG_VEHICULAR_SERIE_FORMAT = "19100";
  
  
  	public static final String COD_ERROR_GEN_COMPROBANTE_NO_CHECK = "24001";
  	public static final String COD_ERROR_LISTA_TRAMA_REVERSION_VACIA = "24002";
  	public static final String COD_ERROR_PLAZO_MAXIMO_SUNAT = "24003";
  	public static final String COD_ERROR_EXPORTAR_CERT_RETENCION = "24004";
  	public static final String COD_ERROR_SUNSYSTEMS_LEDGERTRANSACTION_UPDATE = "24005";
  	public static final String COD_ERROR_LISTA_CERT_RETENCION_VACIA = "24006";
  	public static final String COD_ERROR_UND_NEGOCIO_NO_EXISTE = "24007";
  	public static final String COD_ERROR_TRAMA_RETENCION_ERROR = "24008";
  	
  	// PRIMER PASE 
  	
	public static final String COD_ERROR_ARMAR_LOTE_FECHA_COMPRA_DESDE = "20001";
  	public static final String COD_ERROR_ARMAR_LOTE_FECHA_COMPRA_HASTA = "20002";
  	public static final String COD_ERROR_ARMAR_LOTE_SOCIO_NULO = "20003";
  	public static final String COD_ERROR_ARMAR_LOTE_NO_CHECK_SELECT = "20004";
  	public static final String COD_ERROR_ARMAR_LOTE_ACT_POLIZA_IMP = "20005";
  	public static final String COD_ERROR_ARMAR_LOTE_ACT_DOC_VALORADO = "20006";
  	public static final String COD_ERROR_ARMAR_LOTE_DET_LOTE_IMPRESION = "20007";
  	public static final String COD_ERROR_ARMAR_LOTE_FORMATO_CERTIFICADOS = "20008";
  	public static final String COD_ERROR_ARMAR_LOTE_IMP_NULA = "20009";
  	public static final String COD_ERROR_ARMAR_LOTE_CONF_IMPRESION_POLIZA = "20010";
  	public static final String COD_ERROR_ARMAR_LOTE_ACTUA_LOTE_IMPRESION = "20011";
  	public static final String COD_ERROR_ARMAR_LOTE_NRO_CERTIFICADO_VACIO = "20012";
  	public static final String COD_ERROR_ARMAR_LOTE_DUPLICIDAD_CERTIFICADOS = "20013";
    public static final String COD_ERROR_ARMAR_LOTE_NRO_CERT_NO_DISPONIBLE = "20014";
    public static final String COD_ERROR_ARMAR_LOTE_VALIDAR_FILTRO_MINIMO = "20015";
  	
  	public static final String COD_ERROR_CONF_CERTIFICADO_PUB_LISTA_NULA = "20101";
  	public static final String COD_ERROR_CONF_CERTIFICADO_PRI_LISTA_NULA = "20102";
  	public static final String COD_ERROR_CONF_CERTIFICADO_PUB_ELIM_NULA = "20103";
  	public static final String COD_ERROR_CONF_CERTIFICADO_PRI_ELIM_NULA = "20104";
  	
  	
  	public static final String COD_ERROR_REP_POLIZAS_SOAT_BUSQUEDA_PENDIENTE = "21001";
  	
  	public static final String COD_ERROR_REP_APESEG_BUSQUEDA_PENDIENTE = "22001";
  	
  	public static final String COD_ERROR_LOTE_IMPRESION_PK_VACIO = "22010";
  	public static final String COD_ERROR_DESCARGAR_PDF_VACIO = "22011";

  	public static final String COD_ERROR_POST_VENTA_SOAT_FORMATO_CERT = "22100";
  	public static final String COD_ERROR_POST_VENTA_SOAT_CERT_NO_DISPONIBLE = "22101";
  	public static final String COD_ERROR_POST_VENTA_SOAT_REASIGNAR_CERT = "22102";
  	
    public static final String COD_ERROR_ALTA_DE_REIMPRESION = "22103";
    public static final String COD_ERROR_CONFIRMAR_CERT_POST_VENTA = "22104";
    public static final String COD_ERROR_POST_VENTA_ACTUALIZAR_VACIOS = "22105";
    public static final String COD_ERROR_GENERAR_PDF_POLIZA = "22106";
    public static final String COD_ERROR_NUEVA_MARCA_MODELO = "22107";
    
    public static final String COD_ERROR_MARCA_VEHICULO_EXISTE = "22108";
    public static final String COD_ERROR_MODELO_VEHICULO_EXISTE = "22109";
    public static final String COD_ERROR_SBS_ANEXO_EXISTE = "22111";
    
    public static final String COD_ERROR_CORRELATIVO_APESEG = "20110";
    
    public static final String COD_ERROR_REP_FALABELLA_ITEMS_VACIO = "22112";
    public static final String COD_ERROR_REP_FALABELLA_TIPO_TRAMA = "22113";
    
    public static final String COD_ERROR_REPORTE_RV_FILA_NULA = "24009";
    
    public static final String COD_ERROR_REP_SBS_ES_11A = "24010";
    public static final String COD_ERROR_REP_SBS_ES_11A_SIN_FILAS = "24011";
    public static final String COD_ERROR_REP_SBS_ES_11C = "24012";
    public static final String COD_ERROR_REP_SBS_ES_11C_SIN_FILAS = "24013";
    public static final String COD_ERROR_REP_SBS_S_18 = "24016";
    public static final String COD_ERROR_REP_SBS_S_18_SIN_FILAS = "24017";
    
    public static final String COD_ERROR_REP_SBS_TIP_ANEXO_INDEFINIDO = "24014";
    public static final String COD_ERROR_REP_SBS_REGISTRAR = "24015";    
    public static final String COD_ERROR_DET_MASTER_PRECIOS_NULO = "24018";
    
    public static final String COD_INFO_MENSAJE_CARACTERES_ESPECIALES = "24019";
    public static final String COD_INFO_MENSAJE_VALIDACION_CERTIFIVCADO ="24020";
	public static final String COD_VALIDACION_ARMAR_LOTE = "24021";
	public static final String COD_INFO_MENSAJE_ERROR_WEBSERVICE = "24022";
	public static final String COD_ERROR_POST_VENTA_VALIDACION_SAS = "24023";
	public static final String COD_ERROR_POST_VENTA_SOAT_NULL = "24024";

	public static final String COD_INFO_ELIMINAR_TRAMAS = "24025";
	public static final String COD_SOCIODIGITAL_ERROR_UPDATE = "24026";

}