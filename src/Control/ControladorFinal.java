
package Control;

import View.GraficaOrdenadaVista;




public class ControladorFinal {
    private GraficaOrdenadaVista view;

    public ControladorFinal(GraficaOrdenadaVista view) {
        this.view = view;
    }
    
    public void IniciarVistaFinal(){
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        view.setTitle("Proceso de Ordenamiento");
    
    }
    
    
}
