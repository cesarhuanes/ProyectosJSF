package com.cardif.satelite.tesoreria.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.cardif.satelite.model.FirmantePar;
import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.model.Firmante;

public interface ChequeService {

  boolean sendMail(String recipients, String subject, String body) throws AddressException, MessagingException;

  List<String> obtenerBancos();

  List<Cheque> buscar(String banco, String numeroCheque, String fechaDesde, String fechaHasta);

  String asignar(List<Cheque> cheques, String usuario, Long par);

  String generarPrevisualizacion(List<Cheque> cheques, Long par, Boolean impresion);

  List<Cheque> buscarChequesPendientes(Firmante firmante);

  List<Cheque> buscarChequesRechazados(Firmante firmante);

  List<Cheque> filtrarSeleccionado(List<Cheque> cheques);

  String aprobarCheques(List<Cheque> chequesAprobar, Firmante firmante);

  String rechazarCheques(List<Cheque> chequesRechazar, Firmante firmante, String motivoRechazo);

  Firmante buscarFirmante(String usuario);

  boolean anularCheque(Cheque cheque, String usuario);

  List<Cheque> buscarCheques(String estado);

  List<FirmantePar> buscarPares(Cheque cheque);

  boolean reactivarCheque(Cheque chequeSeleccionado, String usuario);

  boolean reasignarCheque(Cheque chequeSeleccionado, String usuario);

  boolean imprimirCheques(List<Cheque> seleccionados, String usuario);

}
