/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.mybatis.MybatisUtil;
import com.mybatis.pojos.Contacto;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Cesar Huanes
 */
@ManagedBean
@RequestScoped
public class ContactoBean {
    private Contacto contacto;
    List<Contacto> listaContactos;
    public ContactoBean() {
        contacto=new Contacto();
    }
    
    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    
   
   
    
    public String guardar(){
        contacto.setId(-1);
        SqlSession session=new MybatisUtil().getSession();
        if(session!=null){
                try{
                  session.insert("Contacto.insertarContacto", contacto);
                  session.commit();
                }finally{
                   session.close();
                }
           }else{
            System.out.println("ERROR");
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","contacto error"));
        return "index";
    }
}
