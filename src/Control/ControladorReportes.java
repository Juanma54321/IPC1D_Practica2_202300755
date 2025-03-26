
package Control;

import Model.Datos;
import static Model.Datos.encabezado;
import View.GraficaOrdenadaVista;
import View.ReporteVista;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ControladorReportes {
    private ReporteVista view;

    public ControladorReportes(ReporteVista view) {
        this.view = view;
    }
    
    //metodo para iniciar la vista de reporte
    public void IniciarReporte(){
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        view.setTitle("Reporte");
    
    };
    
    //metodo para poder graficar los datos
    //metodo para generar la grafica
    public void GenerarGrafica(Datos[] libreria, String titulo){
        
        
    }
    
    //metodo para insertar los datos del array
    public void RegistrarDatos(Datos[] libreria,Datos[] libreria_ordenada, GraficaOrdenadaVista view2){
        int n=libreria_ordenada.length;
        
        //capturando los datos de la ventana anterior
        view.txtAlgoritmo.setText(view2.txtAlgirtmo.getText());
        view.txtVelocidad.setText(view2.txtVelocidad.getText());
        view.txtOrden1.setText(view2.txtOrden.getText());
        view.txtPasos.setText(view2.txtPasos.getText());
        view.txtMilisegundos1.setText(view2.txtMilisegundos1.getText());
        view.txtSegundos1.setText(view2.txtSegundos1.getText());
        view.txtMinutos2.setText(view2.txtMinutos2.getText());
        
        
        Datos p1;
        Datos p2;
        
        if (view2.txtOrden.getText().equals("Ascendente")) {
            //capturando dato mayor y dato menor
            p1 = libreria_ordenada[0];
            p2 = libreria_ordenada[n-1];
        }else{
            p1 = libreria_ordenada[n-1];
            p2 = libreria_ordenada[0];
        }
        view.tablaDesordenada.setValueAt(encabezado[0], 0, 0);
        view.tablaDesordenada.setValueAt(encabezado[1], 1, 0);
        view.TablaDatosOrdenados.setValueAt(encabezado[0], 0, 0);
        view.TablaDatosOrdenados.setValueAt(encabezado[1], 1, 0);
        //llenando las tablas de datos desordenados y ordenados
        DefaultTableModel modelo = (DefaultTableModel) view.tablaDesordenada.getModel();
        DefaultTableModel modelo1 = (DefaultTableModel) view.TablaDatosOrdenados.getModel();
        
        for (int i = 0; i < n; i++) {
            modelo.addColumn(""); 
            modelo1.addColumn("");
            modelo.setValueAt(libreria[i].getCategoria(), 0, i+1);
            modelo.setValueAt(libreria[i].getDato(), 1, i+1);
            modelo1.setValueAt(libreria_ordenada[i].getCategoria(), 0, i+1);
            modelo1.setValueAt(libreria_ordenada[i].getDato(), 1, i+1);
            
        }
        
        view.txtCategoriaMenor.setText(p1.getCategoria());
        view.txtDatoMenor.setText(String.valueOf(p1.getDato()));
        view.txtCategoriaMayor.setText(p2.getCategoria());
        view.txtDatoMayor.setText(String.valueOf(p2.getDato()));
        
        
        //generando grafica de datos desordenados
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        
        //recorriendo la libreria
        for (int i = 0; i < n; i++) {
            datos.setValue(
                    libreria[i].getDato(), //obteniendo los datos numericos
                    libreria[i].getCategoria(), // titulo de la tabla
                    libreria[i].getCategoria() //obteniendo nombre de la columna
            );
        }
        //incertardo datos a la grafica
        JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                "",                         //titulo de la grafica
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
        panel.setPreferredSize(new Dimension(340, 210));
        
        view.GraficaDesordenada.removeAll();
        view.GraficaDesordenada.setLayout(new BorderLayout());
        view.GraficaDesordenada.add(panel,BorderLayout.NORTH);
        view.GraficaDesordenada.revalidate();
        
        
        //generando grafica de datos Ordenanos
        DefaultCategoryDataset datos1 = new DefaultCategoryDataset();
        
        //recorriendo la libreria
        for (int i = 0; i < n; i++) {
            datos1.setValue(
                    libreria_ordenada[i].getDato(), //obteniendo los datos numericos
                    libreria_ordenada[i].getCategoria(), // titulo de la tabla
                    libreria_ordenada[i].getCategoria() //obteniendo nombre de la columna
            );
        }
        //incertardo datos a la grafica
        JFreeChart grafico_barras1 = ChartFactory.createBarChart3D(
                "",                         //titulo de la grafica
                encabezado[0],                  //titulo de las barras
                encabezado[1],                  //titulo de la numeracion
                datos1,                          //datos del grafico
                PlotOrientation.VERTICAL,       //orientacion
                true,                           //leyenda de barras individuales por color
                true,                           //herramientas
                false                           //url del grafico
        );
        
        //generando un jpanel a la grafica
        ChartPanel panel1 = new ChartPanel(grafico_barras1);
        panel1.setMouseWheelEnabled(true);
        panel1.setPreferredSize(new Dimension(340, 210));
        
        view.GraficaOrdenada.removeAll();
        view.GraficaOrdenada.setLayout(new BorderLayout());
        view.GraficaOrdenada.add(panel1,BorderLayout.NORTH);
        view.GraficaOrdenada.revalidate();
        view.pack();
        view.repaint();
        
        
    }
}
