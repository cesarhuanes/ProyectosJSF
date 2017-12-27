
package com.proyecto.pojos;

import java.io.Serializable;
import java.util.Date;


public class Menu implements Serializable {
	private static final long serialVersionUID = 4078312041526670409L;
	private int codigoOpcion;
    private int codigoPadre;
    private String descripcion;
    private String url;
    private int nivel;
    private int orden;
    private int habilitado;
    private String usuarioCreacion;
    private Date fechaCreacion;
    public Menu(){
        
    }
    /**
     * @return the codigoOpcion
     */
    public int getCodigoOpcion() {
        return codigoOpcion;
    }

    /**
     * @param codigoOpcion the codigoOpcion to set
     */
    public void setCodigoOpcion(int codigoOpcion) {
        this.codigoOpcion = codigoOpcion;
    }

    /**
     * @return the codigoPadre
     */
    public int getCodigoPadre() {
        return codigoPadre;
    }

    /**
     * @param codigoPadre the codigoPadre to set
     */
    public void setCodigoPadre(int codigoPadre) {
        this.codigoPadre = codigoPadre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     * @return the habilitado
     */
    public int getHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

   
}
