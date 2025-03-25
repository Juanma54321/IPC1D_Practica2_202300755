
package Control;

import Model.Datos;
import static Model.Datos.ContadorDatos;
import static Model.Datos.libreria;
import static Model.Datos.libreria_ordenada;
import Model.HiloDatosControl;
import Model.Hilos;
import View.GraficaOrdenadaVista;
import View.GraficaVista;
import View.OpcionVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControladorOrdenar implements ActionListener{
    private OpcionVista view;
    private GraficaVista view2;
    //generando constructor
    public ControladorOrdenar(OpcionVista view, GraficaVista view2) {
        this.view = view;
        this.view.btnCancelar.addActionListener(this);
        this.view.btnOrdenar.addActionListener(this);
        this.view2=view2;
    }
    
    //metodo para iniciar la vista
    public void IniciarOrdenamientoVista(){
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        view.setTitle("Opciones de Ordenamiento");
    }
    
    //metodo para obtener un valor numerico de los radio button
    public long Velocidad(){
        long velocidad=0;
        if (view.btnLento.isSelected()) {
            velocidad=2000;
        }else if(view.btnNormal.isSelected()){
            velocidad=1000;
        }else if(view.btnRapido.isSelected()){
            velocidad=500;
        }
        return velocidad;
    }
    
    //metodo para agregar las opciones al combobox
    public void MetodosdeOrdenamiento(){
        view.btnOrdenamiento.removeAllItems();
        view.btnOrdenamiento.addItem("Bubble Sort");
        view.btnOrdenamiento.addItem("Insert Sort");
        view.btnOrdenamiento.addItem("Select Sort");
        view.btnOrdenamiento.addItem("Merge Sort");
        view.btnOrdenamiento.addItem("Quicksort");
        view.btnOrdenamiento.addItem("Shellsort");
    
    }
    //generando acciones a los botones
    public void actionPerformed(ActionEvent e){
        
        String opcion = e.getActionCommand();
        //capturando las condiciones para ordenar
        String TipoOrdenamiento;
        Long velocidad=Velocidad();
        TipoOrdenamiento= (String) view.btnOrdenamiento.getSelectedItem();
        
        //generando el hilo que graficara los datos
        Thread hilo1 = new Thread(new Hilos(TipoOrdenamiento,velocidad,view,view2));    
    
        switch (opcion){
            //accion del boton cancelar
            case ("Cancelar"):
                view.setVisible(false);
                hilo1.interrupt();
                break;
                
            //accion del boton ordenar
            case ("Ordenar"):
                
                //ordenando acendente
                if (view.BtnAscendente.isSelected()) {
                    //copiando el array
                    libreria_ordenada=new Datos[ContadorDatos()];
                    for (int i = 0; i < ContadorDatos() ; i++) {
                        libreria_ordenada[i]=libreria[i];
                    }
                    hilo1.start();
                }
                
                break;
        }
    };
    
}
