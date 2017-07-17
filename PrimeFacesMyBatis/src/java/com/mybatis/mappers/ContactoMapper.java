/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mybatis.mappers;

import com.mybatis.pojos.Contacto;
import java.util.List;

/**
 *
 * @author Cesar Huanes
 */
public interface ContactoMapper {
   void insertarContacto(Contacto contacto);
   List<Contacto> getContactos();
}
