
package Model;

import View.GraficaOrdenadaVista;
import View.OpcionVista;
import static java.lang.Thread.sleep;

public class HiloDatosControl extends Thread{
    private OpcionVista view;
    private GraficaOrdenadaVista view2;
    private String tipoOrdenamiento;
    private static boolean running = true;
    
    public HiloDatosControl(OpcionVista view,GraficaOrdenadaVista view2,String tipoOrdenamiento) {
        this.view=view;
        this.view2=view2;
        this.tipoOrdenamiento=tipoOrdenamiento;
    }
    
    public static void detener() {
        running = false;
    }
    
    @Override
    public void run(){
        running=true;
        
        
        int milisegundo=0;
        int segundero=0;
        int minutero=0;
        
        view2.txtAlgirtmo.setText(tipoOrdenamiento);
        if(view.BtnAscendente.isSelected()){
            view2.txtOrden.setText((String) view.BtnAscendente.getText());
        }else if(view.btnDescendente.isSelected()){
            view2.txtOrden.setText((String) view.btnDescendente.getText());
        }
        if (view.btnLento.isSelected()) {
            view2.txtVelocidad.setText("Lento");
        }else if(view.btnNormal.isSelected()){
            view2.txtVelocidad.setText("Normal");
        }else if(view.btnRapido.isSelected()){
            view2.txtVelocidad.setText("Rapido");
        }
        
        
        //contador de milisegundos
        try{
            long startTime = System.nanoTime(); // Guardar el tiempo de inicio
            while(running){
            long elapsedTime = (System.nanoTime() - startTime) / 1_000_000; // Convertir a milisegundos
            milisegundo = (int) (elapsedTime % 1000);
            segundero = (int) ((elapsedTime / 1000) % 60);
            minutero = (int) ((elapsedTime / 60000));
            view2.txtMilisegundos1.setText(String.valueOf(milisegundo));
            view2.txtSegundos1.setText(String.valueOf(segundero));
            view2.txtMinutos2.setText(String.valueOf(minutero));
            milisegundo++;
            }
            sleep(1);
        }catch(InterruptedException e){
        
        }
    
    
    
    
    }
}
