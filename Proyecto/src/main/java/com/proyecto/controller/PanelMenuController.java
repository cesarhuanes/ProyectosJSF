
package com.proyecto.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name="panelMenu")
@SessionScoped
public class PanelMenuController  implements Serializable{
private String url="/comun/cuerpo.xhtml";

    public void obtenerUrl(ActionEvent ae){
        setUrl((String)ae.getComponent().getAttributes().get("url"));
        
    }
    
    public String redirecionar(){
        return url;
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
    
}
