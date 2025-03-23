
package Control;

import Model.Hilos;
import View.OpcionVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControladorOrdenar implements ActionListener{
    Hilos model;
    OpcionVista view;

    public ControladorOrdenar(Hilos model, OpcionVista view) {
        this.model = model;
        this.view = view;
        this.view.btnCancelar.addActionListener(this);
    }
    
    public void IniciarOrdenamientoVista(){
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        view.setTitle("Opciones de Ordenamiento");
    }
    public void actionPerformed(ActionEvent e){
        
        String opcion = e.getActionCommand();
    
        switch (opcion){
            case ("Cancelar"):
                view.setVisible(false);
                break;
        }
    };
    
}
