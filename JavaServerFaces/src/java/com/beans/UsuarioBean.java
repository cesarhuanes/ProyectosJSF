/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Cesar Huanes
 */
@ManagedBean(name = "usuario")
@RequestScoped
public class UsuarioBean {
    private String nombre; 
    private String password;  
    public UsuarioBean(){
        
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
