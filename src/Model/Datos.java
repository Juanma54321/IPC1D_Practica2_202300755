
package Model;

import View.GraficaVista;
import java.io.BufferedReader;
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
        
        //verificando la extencion del archivo
        if (ruta.contains(".ipcd1")) {
            //leyendo el archivo
            try(BufferedReader lector = new BufferedReader(new FileReader(ruta))){
                try{
                    //registrando los datos del archivo
                    String fila;
                    int contador=0;

                    //guardando el encabezado
                    encabezado= lector.readLine().split(",");

                    //leyendo el encabezado
                    if (encabezado.length!=2) {
                        //si el archivo no cumple con tener un encabezado
                        JOptionPane.showMessageDialog(view,"El archivo no cuenta con un encabezado","Error", JOptionPane.ERROR_MESSAGE);
                        view.txtRuta.setText("");
                        return;
                    }else{

                    //recorriendo el resto del archivo
                    while((fila=lector.readLine())!=null){
                        //guardando los datos en una lista temporal
                        String [] ListaTemporal = fila.split(",");

                        //analizandon si los datos cumplen con ser una lista
                        if (ListaTemporal.length==2) {
                            Datos dato = new Datos();

                            //registrando los datos
                            dato.setCategoria(ListaTemporal[0]);
                            dato.setDato(Integer.parseInt(ListaTemporal[1]));

                            //guardando los datos dentro de la libreria
                            libreria[contador]=null;
                            libreria[contador]=dato;
                            contador++;
                        }
                        else{

                            //mensaje de error
                            JOptionPane.showMessageDialog(view,"Error al leer el archivo","ERROR", JOptionPane.ERROR_MESSAGE);
                            view.txtRuta.setText("");
                            break;
                        }
                    }}
                }
                catch(IOException f){
                    //mensaje de error
                    JOptionPane.showMessageDialog(view,"Error al leer el archivo","ERROR", JOptionPane.ERROR_MESSAGE);
                    view.txtRuta.setText("");
                }
            }catch(IOException e){
            
                //mensaje de error
                JOptionPane.showMessageDialog(view,"Error al leer el archivo","ERROR", JOptionPane.ERROR_MESSAGE);
                view.txtRuta.setText("");
            }  
        }else{
            //mensaje de error
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
