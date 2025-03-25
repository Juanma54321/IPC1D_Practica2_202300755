
package Model;

import static Model.Datos.ContadorDatos;
import static Model.Datos.encabezado;
import static Model.Datos.libreria_ordenada;
import View.GraficaOrdenadaVista;
import View.GraficaVista;

import View.OpcionVista;
import java.awt.BorderLayout;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Hilos  implements Runnable{
    private String tipoOrdenamiento;
    private long velocidad;
    private OpcionVista view;
    private GraficaVista view3;
    
    // Constructor para diferenciar cada método
    public Hilos(String tipoOrdenamiento, long velocidad,OpcionVista view,GraficaVista view3) {
        this.tipoOrdenamiento = tipoOrdenamiento;
        this.velocidad = velocidad;
        this.view=view;
        this.view3=view3;
    }
    
    //verificando que hilos se ejecutaran 
    @Override
    public void run() {
        //metodo de ordenamiento bubble sort
        if (tipoOrdenamiento.equals("Bubble Sort")) {
            Bubble_Sort(libreria_ordenada,velocidad);
        }
    }
    
    
    
    
    
    
    //Bubble sort
    public void Bubble_Sort(Datos[] lista_a_Ordenar, long velocidad){
        int n = lista_a_Ordenar.length;
        boolean intercambiado;
        
        //iniciamos la vista donde se mostrara la grafica ordenada
        GraficaOrdenadaVista view2 = new GraficaOrdenadaVista();
       
        HiloDatosControl hilo2 =new HiloDatosControl(view,view2,"Bubble Sort");
        try{
            view2.setVisible(true);
            Thread.sleep(3000);
           
            //creando los hilos para ordenar y graficar
            hilo2.start();
            
        }catch(InterruptedException e){
        }
        
        for (int i = 0; i < n - 1; i++) {
            
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Hilo interrumpido. Terminando...");
                return; // Sale del método run() y finaliza el hilo
            }    
            intercambiado = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (lista_a_Ordenar[j].getDato() > lista_a_Ordenar[j + 1].getDato()) { // Si el actual es mayor que el siguiente, intercambiar
                    Datos temp = lista_a_Ordenar[j];
                    lista_a_Ordenar[j]=lista_a_Ordenar[j+1];
                    lista_a_Ordenar[j + 1]=temp;
                    intercambiado = true;
                    
                }
                //ajustando la velocidad de ordenamiento
                try{
                    Thread.sleep(velocidad);
                    
                }catch(InterruptedException e){
                    System.out.println("error");
                    
                }
                
            //actualizamos la grafica donde se muestra el proceso de ordenamiento
            DefaultCategoryDataset datos = new DefaultCategoryDataset();
            //recorriendo la libreria
            for (int x = 0; x <ContadorDatos(); x++) {
                datos.setValue(
                        lista_a_Ordenar[x].getDato(), //obteniendo los datos numericos
                        lista_a_Ordenar[x].getCategoria(), // titulo de la tabla
                        lista_a_Ordenar[x].getCategoria() //obteniendo nombre de la columna
                );
            }
            //incertardo datos a la grafica
            JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                    view3.txtNombre.getText(),                         //titulo de la grafica
                    encabezado[0],                  //titulo de las barras
                    encabezado[1],                  //titulo de la numeracion
                    datos,                          //datos del grafico
                    PlotOrientation.VERTICAL,       //orientacion
                    true,                           //leyenda de barras individuales por color
                    true,                           //herramientas
                    false                           //url del grafico
            );

            //generando un jpanel a la grafica
            ChartPanel panel = new ChartPanel(grafico_barras);
            panel.setMouseWheelEnabled(true);
            panel.setPreferredSize(new Dimension(510,310));

            view2.grafica.removeAll();
            view2.grafica.setLayout(new BorderLayout());
            view2.grafica.add(panel,BorderLayout.NORTH);
            view2.grafica.revalidate();
            view2.grafica.repaint();
            view2.pack();

            }
            
            
            // Si no hubo intercambios, la lista ya está ordenada y terminamos
            if (!intercambiado) break;
        
       }
     
       hilo2.detener();
    }
}
