package com.cardif.satelite.soat.comparabien.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.Propietario;

/***
 * @Usuario_Creacion: lmaron
 * @fecha 09/02/2014
 * @Metodo: metodo obtener ConsultaRegPropietario
 * @fecha_Modificacion: 18/02/2014
 * @Modificacion se restablecio el metodo obtener al estado inicial y se renombra al metodo del Propietario
 */
/***
 * @author jhurtado
 * @fecha 14/02/2014
 * @descripcion metodos insert actualizar
 */
public interface PropietarioServiceComparaBien {

  public Propietario obtener(String dni) throws SyncconException;

  public int insertar(Propietario propietario) throws SyncconException;

  public boolean actualizar(Propietario propietario) throws SyncconException;
}
