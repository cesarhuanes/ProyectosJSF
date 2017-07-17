/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mybatis.pojos;

/**
 *
 * @author Cesar Huanes
 */
public class Contacto {
   private int id;
   private String nombre;
   private String apellido;
   private int numero;
   
   
   public Contacto(){
       
   }

    public Contacto(int id, String nombre, String apellido, int numero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
    }
     public Contacto(String nombre, String apellido, int numero) {
         this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
   
   @Override
   public String toString(){
     return nombre+"--"+numero;  
   }
}
