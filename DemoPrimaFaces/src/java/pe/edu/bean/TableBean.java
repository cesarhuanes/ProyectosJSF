
package pe.edu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import pe.edu.dao.Datos;

@ManagedBean(name = "tableBean")
@RequestScoped
public class TableBean implements Serializable {
 private List<Datos> lstDatos;
 private Datos datos;

 public TableBean(){
  lstDatos=new ArrayList<Datos>();   
 Datos datos1=new Datos("001","Cesar  ALfredo","Huanes Bautista");
 Datos datos2=new Datos("002","Carlos Alfredo","Huanes Pinedo");
 Datos datos3=new Datos("003","Gloria Bautista","Bautista Inga");
 Datos datos4=new Datos("004","Belen Pinedo ","Pinedo Diaz");
 Datos datos5=new Datos("005","Jose Alejandro","Rodriguez Perchotinta");
 Datos datos6=new Datos("006","Almendra Yasmin","Gimenez Comeca");
 Datos datos7=new Datos("007","Juan Jose","Humbolth Llontop");
 Datos datos8=new Datos("008","Alejandro Fernandez","Dolores Cabrera");
 lstDatos.add(datos1);
 lstDatos.add(datos2);
 lstDatos.add(datos3);
 lstDatos.add(datos4);
 lstDatos.add(datos5);
 lstDatos.add(datos6);
 lstDatos.add(datos7);
 lstDatos.add(datos8);
 }
 
 
    /**
     * @return the lstDatos
     */
    public List<Datos> getLstDatos() {
        return lstDatos;
    }

    /**
     * @param lstDatos the lstDatos to set
     */
    public void setLstDatos(List<Datos> lstDatos) {
        this.lstDatos = lstDatos;
    }

    /**
     * @return the datos
     */
    public Datos getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(Datos datos) {
        this.datos = datos;
    }
 
}
