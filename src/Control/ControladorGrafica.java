
package Control;

import Model.Datos;
import static Model.Datos.encabezado;
import static Model.Datos.libreria;

import View.GraficaVista;
import View.OpcionVista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class ControladorGrafica implements ActionListener{
    Datos model;
    GraficaVista view;
    
    
    //generando cosntructor
    public ControladorGrafica(Datos model, GraficaVista view) {
        this.model = model;
        this.view = view;
        this.view.btnBuscar.addActionListener(this);
        this.view.btnGenerar.addActionListener(this);
        this.view.btnOrdenar.addActionListener(this);
    }
    
    //metodo para inicializar la vista
    public void IniciarVista(){
        view.setVisible(true);
        view.setTitle("Graficador de Datos");
        view.setLocationRelativeTo(null);
    }
    
    //metodo para buscar un archivo
    public String Buscador(){
    JFileChooser buscador = new JFileChooser();
    int seleccion = buscador.showOpenDialog(null);
    
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File ruta = buscador.getSelectedFile();
            
            return ruta.getAbsolutePath();
        }else{
            return ("****Archivo no encontrado");
        
        }    
    }
    //metodo para generar la grafica
    public void GenerarGrafica(Datos[] libreria, String titulo){
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        
        //recorriendo la libreria
        for (int i = 0; i < model.ContadorDatos(); i++) {
            datos.setValue(
                    libreria[i].getDato(), //obteniendo los datos numericos
                    libreria[i].getCategoria(), // titulo de la tabla
                    libreria[i].getCategoria() //obteniendo nombre de la columna
            );
        }
        //incertardo datos a la grafica
        JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                titulo,                         //titulo de la grafica
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
        
        view.grafica.removeAll();
        view.grafica.setLayout(new BorderLayout());
        view.grafica.add(panel,BorderLayout.NORTH);
        view.grafica.revalidate();
        view.pack();
        view.repaint();
        
    }
    
    //asignando las acciones a cada boton
    public void actionPerformed(ActionEvent e){
        
        //diferenciando que boton fue precionado
        String opcion = e.getActionCommand();
        String ruta=view.txtRuta.getText();
        
        switch (opcion){
            //accion del boton buscar
            case ("Buscar"):
                //obteniendo la ruta del archivo
                ruta=Buscador();
                view.txtRuta.setText(ruta);
                break;
                
            //accion del boton generar
            case ("Generar"):
                
                if (!view.txtNombre.getText().equals("") && !view.txtNombre.getText().equals("Ingrese algun nombre")) {
                   //guardando los datos del archivo
                    model.GuardarDatos(ruta,view);
                
                    if (model.ContadorDatos()!=0) {
                        //obteniendo el nombre de la grafica
                        String titulo= view.txtNombre.getText();
                        GenerarGrafica(libreria,titulo);
                    } 
                }else{
                    JOptionPane.showMessageDialog(view,"Ingrese un nombre para la tabla","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                break;
            //accion del boton ordenar
            case ("Ordenar"):
                if (model.ContadorDatos()!=0) {
                    OpcionVista view2 = new OpcionVista();
                    
                    ControladorOrdenar control = new ControladorOrdenar(view2,view);
                    control.MetodosdeOrdenamiento();
                    control.IniciarOrdenamientoVista();
                    
                }else{
                    JOptionPane.showMessageDialog(view,"No existe ninguna grafica para ordenar","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
        
        
    }
}
