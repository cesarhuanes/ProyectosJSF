
package com.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="cuestionario")
@RequestScoped
public class Cuestionario {
  
private String nombre;
private String correo;
private String[] aficiones;
private boolean quieroOpinar;
private String fumador;
private String[] sistema;
private String[] comidas;
private String estado;
private String[] lenguajes;

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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the aficiones
     */
    public String[] getAficiones() {
        return aficiones;
    }

    /**
     * @param aficiones the aficiones to set
     */
    public void setAficiones(String[] aficiones) {
        this.aficiones = aficiones;
    }

    /**
     * @return the quieroOpinar
     */
    public boolean isQuieroOpinar() {
        return quieroOpinar;
    }

    /**
     * @param quieroOpinar the quieroOpinar to set
     */
    public void setQuieroOpinar(boolean quieroOpinar) {
        this.quieroOpinar = quieroOpinar;
    }

    /**
     * @return the fumador
     */
    public String getFumador() {
        return fumador;
    }

    /**
     * @param fumador the fumador to set
     */
    public void setFumador(String fumador) {
        this.fumador = fumador;
    }

    /**
     * @return the sistema
     */
    public String[] getSistema() {
        return sistema;
    }

    /**
     * @param sistema the sistema to set
     */
    public void setSistema(String[] sistema) {
        this.sistema = sistema;
    }

    /**
     * @return the comidas
     */
    public String[] getComidas() {
        return comidas;
    }

    /**
     * @param comidas the comidas to set
     */
    public void setComidas(String[] comidas) {
        this.comidas = comidas;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the lenguajes
     */
    public String[] getLenguajes() {
        return lenguajes;
    }

    /**
     * @param lenguajes the lenguajes to set
     */
    public void setLenguajes(String[] lenguajes) {
        this.lenguajes = lenguajes;
    }

}
