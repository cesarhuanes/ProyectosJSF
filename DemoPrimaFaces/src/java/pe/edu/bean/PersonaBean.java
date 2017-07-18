
package pe.edu.bean;
import java.util.List;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import pe.edu.dao.Persona;

@ManagedBean(name = "personaBean")
@RequestScoped
public class PersonaBean {
    
    private BarChartModel barChart;
    private  BarChart bar;
    
 private List<Persona> listaPersona=new ArrayList<Persona>();
 
 
 public PersonaBean(){
  
 }
 
 public void graficar(){
  llenarPersona();
  generarGrafico(getListaPersona());
  
  
 }
 private void llenarPersona(){
Persona per=new Persona();
 per.setEdad(10);
 per.setNombre("Carlitos");
 Persona per1=new Persona();
 per1.setEdad(25);
 per1.setNombre("Alfredo");
getListaPersona().add(per);
getListaPersona().add(per1);
 }
 private void generarGrafico(List<Persona> lista){
      barChart=new BarChartModel();
        ChartSeries series=new ChartSeries();
        setBar(new BarChart());
   for(Persona per:lista){
            getBar().setYaxisAngle(per.getEdad());
            getBar().setXaxisLabel(per.getNombre());
   //  series.set(per.getNombre(), per.getEdad());
   
   }
   
  /* barChart.setShowDatatip(true);
   barChart.addSeries(series);
   barChart.setShadow(true);
   barChart.setAnimate(true);
   barChart.setTitle("Grafico Medico");
  */
   //barChart.setSeriesColors("eaa229, 4bb2c5, 0E54CD, FF6F6F, FFEF00, 8295f3, 4fb234, 8C989A, FE6E14");
 }

   

    /**
     * @return the listaPersona
     */
    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    /**
     * @param listaPersona the listaPersona to set
     */
    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    /**
     * @return the barChart
     */
    public BarChartModel getBarChart() {
        return barChart;
    }

    /**
     * @param barChart the barChart to set
     */
    public void setBarChart(BarChartModel barChart) {
        this.barChart = barChart;
    }

    /**
     * @return the bar
     */
    public BarChart getBar() {
        return bar;
    }

    /**
     * @param bar the bar to set
     */
    public void setBar(BarChart bar) {
        this.bar = bar;
    }
 
}
