
package practica2;

import Control.ControladorGrafica;
import Model.Datos;
import View.GraficaVista;


public class Practica2 {

 
    public static void main(String[] args) {
        Datos model = new Datos();
        GraficaVista view = new GraficaVista();
        
        ControladorGrafica control = new ControladorGrafica (model,view);
        
        control.IniciarVista();
        
    }
    
}
