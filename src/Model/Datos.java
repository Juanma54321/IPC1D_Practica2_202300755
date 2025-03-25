
package Model;

import View.GraficaVista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Datos {
    private int dato;
    private String categoria;
    public static String [] encabezado;
    
    //libreria donde estaran todos los datos leidos
    public static Datos[] libreria = new Datos[20];
    public static Datos[] libreria_ordenada;

    //set y get de Datos
    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    //metodo para guardar los datos en una libreria
    public void GuardarDatos(String ruta,GraficaVista view){
        
        try(BufferedReader lector = new BufferedReader(new FileReader(ruta))){
            try{
                String fila;
                int contador=0;
                encabezado= lector.readLine().split(",");
                while((fila=lector.readLine())!=null){
                    String [] ListaTemporal = fila.split(",");

                    if (ListaTemporal.length==2) {
                        Datos dato = new Datos();

                        dato.setCategoria(ListaTemporal[0]);
                        dato.setDato(Integer.parseInt(ListaTemporal[1]));
                        
                        libreria[contador]=null;
                        libreria[contador]=dato;
                        contador++;
                    }
                    else{
                        JOptionPane.showMessageDialog(view,"Error al leer el archivo","ERROR", JOptionPane.ERROR_MESSAGE);
                        view.txtRuta.setText("");
                        break;
                    }
                }
            }
            catch(IOException f){
                JOptionPane.showMessageDialog(view,"Error al leer el archivo","ERROR", JOptionPane.ERROR_MESSAGE);
                view.txtRuta.setText("");
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(view,"Error al leer el archivo","ERROR", JOptionPane.ERROR_MESSAGE);
            view.txtRuta.setText("");
        }
    }
    
    //metodo para contar cuantos datos existentes hay
    public static byte ContadorDatos(){
        byte contador=0;
        
        for (int i = 0; i < libreria.length; i++) {
            //verificando que celdas contienen datos
            if (libreria[i]!=null) {
                contador++;
            }
        }
        return contador;
    }
}
