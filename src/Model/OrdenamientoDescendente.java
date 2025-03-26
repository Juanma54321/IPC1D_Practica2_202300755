
package Model;
import Control.ControladorReportes;
import static Model.Datos.ContadorDatos;
import static Model.Datos.encabezado;
import static Model.Datos.libreria;
import static Model.Datos.libreria_ordenada;
import static Model.OrdenamientoAscendente.contador_recursivo;
import View.GraficaOrdenadaVista;
import View.GraficaVista;

import View.OpcionVista;
import View.ReporteVista;
import java.awt.BorderLayout;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class OrdenamientoDescendente implements Runnable {
    private String tipoOrdenamiento;
    private long velocidad;
    private OpcionVista view;
    private GraficaVista view3;
    public static int contador_recursivo=0;
    
    // Constructor para diferenciar cada método
    public OrdenamientoDescendente(String tipoOrdenamiento, long velocidad,OpcionVista view,GraficaVista view3) {
        this.tipoOrdenamiento = tipoOrdenamiento;
        this.velocidad = velocidad;
        this.view=view;
        this.view3=view3;
    }
    
    //verificando que hilos se ejecutaran 
    @Override
    public void run() {
        GraficaOrdenadaVista view2 = new GraficaOrdenadaVista();
        
        //metodo de ordenamiento bubble sort
        if (tipoOrdenamiento.equals("Bubble Sort")) {
            Bubble_Sort(libreria_ordenada,velocidad,view2);
        
        //metodo de ordenamiento Insert Sort
        }else if(tipoOrdenamiento.equals("Insert Sort")){
            Insert_Sort(libreria_ordenada,velocidad,view2);
            
        //metodo de ordenamiento Select Sort
        }else if(tipoOrdenamiento.equals("Select Sort")){
            Select_Sort(libreria_ordenada,velocidad,view2);
        
        //metodo de ordenamiento Merge Sort
        }else if(tipoOrdenamiento.equals("Merge Sort")){
            HiloDatosControl hilo2 =new HiloDatosControl(view,view2,"Merge Sort");
            
            //creando los hilos para ordenar y graficar
            hilo2.start();
            Merge_Sort(libreria_ordenada,velocidad,0,libreria_ordenada.length-1,view2,hilo2);
            hilo2.interrupt();
        
        
        }else if(tipoOrdenamiento.equals("Quicksort")){
            HiloDatosControl hilo2 =new HiloDatosControl(view,view2,"Quicksort");
            
            //creando los hilos para ordenar y graficar
            hilo2.start();
            contador_recursivo=0;
            QuickSort(libreria_ordenada,velocidad,0,libreria_ordenada.length-1,view2,hilo2);
            hilo2.interrupt();
        //metodo de ordenamiento Shellsort
        }else if (tipoOrdenamiento.equals("Shellsort")){
            shellSort(libreria_ordenada,velocidad,view2);
        }
        
        //llamando a la vista reporte
        view.dispose();
        ReporteVista view3 = new ReporteVista();
        ControladorReportes control;
        control = new ControladorReportes(view3);
        
        control.IniciarReporte();
        control.RegistrarDatos(libreria, libreria_ordenada, view2);
        
    }
    
    
    
    
    
    
    //Bubble sort
    public void Bubble_Sort(Datos[] lista_a_Ordenar, long velocidad,GraficaOrdenadaVista view2){
        int n = lista_a_Ordenar.length;
        boolean intercambiado;
        int contador =0;
        
        //iniciamos la vista donde se mostrara la grafica ordenada
        HiloDatosControl hilo2 =new HiloDatosControl(view,view2,"Bubble Sort");
        //iniciando el hilo que controlara los detalles de la grafica
        try{
            view2.setVisible(true);
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
            
            Thread.sleep(3000);
           
            //creando los hilos para ordenar y graficar
            hilo2.start();
            
        }catch(InterruptedException e){
        }
        //inicio del algoritmo de ordenamiento
        for (int i = 0; i < n - 1; i++) {
            
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Hilo interrumpido. Terminando...");
                return; // Sale del método run() y finaliza el hilo
            }    
            intercambiado = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (lista_a_Ordenar[j].getDato() < lista_a_Ordenar[j + 1].getDato()) { // Si el actual es mayor que el siguiente, intercambiar
                    Datos temp = lista_a_Ordenar[j];
                    lista_a_Ordenar[j]=lista_a_Ordenar[j+1];
                    lista_a_Ordenar[j + 1]=temp;
                    intercambiado = true;
                    
                }
                //ajustando la velocidad de ordenamiento
                try{
                    contador++;
                    view2.txtPasos.setText(String.valueOf(contador));
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
    
    //Insert_Sort
    public void Insert_Sort(Datos[] lista_a_Ordenar, long velocidad,GraficaOrdenadaVista view2){
        int n = lista_a_Ordenar.length;
        int contador =0;
        HiloDatosControl hilo2 =new HiloDatosControl(view,view2,"Insert Sort");
        
        //iniciando el hilo que controlara los detalles de la grafica
        try{
            view2.setVisible(true);
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
            
            Thread.sleep(3000);
           
            //creando los hilos para ordenar y graficar
            hilo2.start();
            
        }catch(InterruptedException e){
        }
        
        //inicio del algoritmo de ordenamiento
        for (int i = 0; i < n; i++) {
            Datos key=lista_a_Ordenar[i]; // Elemento a insertar
            int j =i-1;
            // Mueve los elementos mayores que key una posición adelante
            while (j >= 0 && lista_a_Ordenar[j].getDato() < key.getDato()) {
                lista_a_Ordenar[j + 1] = lista_a_Ordenar[j];
                j--;
            }
            lista_a_Ordenar[j + 1] = key; // Inserta el elemento en su posición correcta
            
            
            //ajustando la velocidad de ordenamiento
            try{
                contador++;
                view2.txtPasos.setText(String.valueOf(contador));
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
        hilo2.detener();
    }
     
    //Select Sort
    public void Select_Sort(Datos[] lista_a_Ordenar, long velocidad,GraficaOrdenadaVista view2){
        int n = lista_a_Ordenar.length;
        int contador =0;
        
        HiloDatosControl hilo2 =new HiloDatosControl(view,view2,"Select Sort");
        
        //iniciando el hilo que controlara los detalles de la grafica
        try{
            view2.setVisible(true);
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
            
            Thread.sleep(3000);
           
            //creando los hilos para ordenar y graficar
            hilo2.start();
            
        }catch(InterruptedException e){
        }
        
        //inicio del algoritmo de ordenamiento
        for (int i = 0; i < n-1; i++) {
            int datomenor=i;// Índice del menor valor
  
            // Busca el menor elemento en el resto del array
            for (int j = i + 1; j < n; j++) {
                if (lista_a_Ordenar[j].getDato() > lista_a_Ordenar[datomenor].getDato()) {
                    datomenor = j;
                }
            }
            // Intercambia el menor encontrado con el primer elemento no ordenado
            Datos temp = lista_a_Ordenar[datomenor];
            lista_a_Ordenar[datomenor] = lista_a_Ordenar[i];
            lista_a_Ordenar[i] = temp;
            
            //ajustando la velocidad de ordenamiento
            try{
                contador++;
                view2.txtPasos.setText(String.valueOf(contador));
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
        hilo2.detener();
    }   
    
    //Merge Sort
    public void Merge_Sort(Datos[] lista_a_Ordenar, long velocidad,int inicio,int fin,GraficaOrdenadaVista view2,HiloDatosControl hilo2){
        
        //iniciando el hilo que controlara los detalles de la grafica
        try{
            view2.setVisible(true);
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
            
            Thread.sleep(1);
           
            
            
        }catch(InterruptedException e){
        }
        
        //inicio del algoritmo de ordenamiento
        if(inicio<fin){
            int mitad = (inicio+fin)/2;
            
            // Dividir el array en dos mitades y ordenarlas recursivamente
            Merge_Sort(lista_a_Ordenar,velocidad, inicio, mitad,view2,hilo2);
            Merge_Sort(lista_a_Ordenar,velocidad, mitad + 1, fin,view2,hilo2);
            
            //ajustando la velocidad de ordenamiento
            try{
                contador_recursivo++;
                view2.txtPasos.setText(String.valueOf(contador_recursivo));
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
            
            merge(lista_a_Ordenar,inicio,mitad,fin,view2,velocidad,view3);
        }
    }
    // Método para mezclar las dos mitades ordenadas
    private static void merge(
            Datos[] lista_a_Ordenar,
            int inicio, 
            int medio,
            int fin,
            GraficaOrdenadaVista view2, 
            Long velocidad,
            GraficaVista view3){
        
        int n1 = medio-inicio+1;
        int n2 = fin - medio;

        // Crear arreglos temporales
        Datos[] izquierda= new Datos[n1];
        Datos[] derecha= new Datos[n2];
        
        // Copiar los datos en los arreglos temporales
        for (int i = 0; i < n1; i++) {
            izquierda[i] = lista_a_Ordenar[inicio + i];
        }
        for (int j = 0; j < n2; j++) {
            derecha[j] = lista_a_Ordenar[medio + 1 + j];
        }
        
         // Mezclar los arreglos temporales de vuelta en el original
        int i = 0, j = 0, k = inicio;
        while (i < n1 && j < n2) {
            
            if (izquierda[i].getDato() >= derecha[j].getDato()) {
                lista_a_Ordenar[k] = izquierda[i];
                i++;
            } else {
                lista_a_Ordenar[k] = derecha[j];
                j++;
            }
            k++;
            
            //ajustando la velocidad de ordenamiento
            try{
                contador_recursivo++;
                view2.txtPasos.setText(String.valueOf(contador_recursivo));
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
         // Copiar los elementos restantes, si hay alguno
        while (i < n1) {
            
            //ajustando la velocidad de ordenamiento
            try{
                contador_recursivo++;
                view2.txtPasos.setText(String.valueOf(contador_recursivo));
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
            
            
            lista_a_Ordenar[k] = izquierda[i];
            i++;
            k++;
        }
        while (j < n2) {
            
            //ajustando la velocidad de ordenamiento
            try{
                contador_recursivo++;
                view2.txtPasos.setText(String.valueOf(contador_recursivo));
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
            
            
            lista_a_Ordenar[k] = derecha[j];
            j++;
            k++;
        }
    }

    //Quicksort
    public void QuickSort(Datos[] lista_a_Ordenar, long velocidad,int inicio,int fin,GraficaOrdenadaVista view2,HiloDatosControl hilo2){
        
        //iniciando el hilo que controlara los detalles de la grafica
        try{
            view2.setVisible(true);
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
                    view3.txtNombre.getText(),      //titulo de la grafica
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
            
            Thread.sleep(1);
        }catch(InterruptedException e){
        }
        
        if (inicio < fin) {
            
             //ajustando la velocidad de ordenamiento
            try{
                contador_recursivo++;
                view2.txtPasos.setText(String.valueOf(contador_recursivo));
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
            
            int indicePivote = particion(lista_a_Ordenar, inicio, fin,view2,velocidad,view3);
            QuickSort(lista_a_Ordenar,velocidad, inicio, indicePivote - 1,view2,hilo2); // Ordenar la izquierda
            QuickSort(lista_a_Ordenar,velocidad, indicePivote + 1, fin,view2,hilo2);    // Ordenar la derecha
        }
    }
    private static int particion(Datos[] lista_a_Ordenar, int inicio, int fin,GraficaOrdenadaVista view2,Long velocidad,GraficaVista view3) {
        Datos pivote = lista_a_Ordenar[fin]; // Se elige el último elemento como pivote
        int i = inicio - 1; // Índice para los elementos menores que el pivote

        for (int j = inicio; j < fin; j++) {
            
            //ajustando la velocidad de ordenamiento
            try{
                contador_recursivo++;
                view2.txtPasos.setText(String.valueOf(contador_recursivo));
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
            
            if (lista_a_Ordenar[j].getDato() > pivote.getDato()) { // Cambio de "<" a ">" para orden descendente
                i++;
                intercambiar(lista_a_Ordenar, i, j,view2,velocidad,view3);
            }
        }

        intercambiar(lista_a_Ordenar, i + 1, fin,view2,velocidad,view3);
        return i + 1; // Retorna la posición final del pivote
    }
    private static void intercambiar(Datos[] lista_a_Ordenar, int i, int j,GraficaOrdenadaVista view2,Long velocidad,GraficaVista view3) {
            //ajustando la velocidad de ordenamiento
            try{
                contador_recursivo++;
                view2.txtPasos.setText(String.valueOf(contador_recursivo));
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
        Datos temp = lista_a_Ordenar[i];
        lista_a_Ordenar[i] = lista_a_Ordenar[j];
        lista_a_Ordenar[j] = temp;
    }

    //shellsort
    public void shellSort(Datos[] lista_a_Ordenar, long velocidad,GraficaOrdenadaVista view2){
        int n = lista_a_Ordenar.length;
        int contador =0;
        
        HiloDatosControl hilo2 =new HiloDatosControl(view,view2,"Shellsort");
        
        //iniciando el hilo que controlara los detalles de la grafica
        try{
            view2.setVisible(true);
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
            
            Thread.sleep(3000);
           
            //creando los hilos para ordenar y graficar
            hilo2.start();
        }catch(InterruptedException e){
        }
        
        
        //Se inicia definiendo el espacio entre los elementos
        for (int x = n / 2; x > 0; x /= 2) {
            //Se realiza el ordenamiento por inserción para cada gap
            for (int i = x; i < n; i++) {
                Datos temp = lista_a_Ordenar[i];
                int j;
                //Se mueven los elementos que ya han sido ordenados hasta encontrar
                //la posición correcta de arr[i]
                for (j = i; j >= x && lista_a_Ordenar[j - x].getDato() < temp.getDato(); j -= x) {
                    lista_a_Ordenar[j] = lista_a_Ordenar[j - x];
                    
                    //ajustando la velocidad de ordenamiento
                    try{
                        contador++;
                        view2.txtPasos.setText(String.valueOf(contador));
                        Thread.sleep(velocidad);

                    }catch(InterruptedException e){
                        System.out.println("error");
                    }

                    //actualizamos la grafica donde se muestra el proceso de ordenamiento
                    DefaultCategoryDataset datos = new DefaultCategoryDataset();
                    //recorriendo la libreria
                    for (int y = 0; y <ContadorDatos(); y++) {
                        datos.setValue(
                                lista_a_Ordenar[y].getDato(), //obteniendo los datos numericos
                                lista_a_Ordenar[y].getCategoria(), // titulo de la tabla
                                lista_a_Ordenar[y].getCategoria() //obteniendo nombre de la columna
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
                lista_a_Ordenar[j] = temp;
                
                //ajustando la velocidad de ordenamiento
                try{
                    contador++;
                    view2.txtPasos.setText(String.valueOf(contador));
                    Thread.sleep(velocidad);

                }catch(InterruptedException e){
                    System.out.println("error");
                }

                //actualizamos la grafica donde se muestra el proceso de ordenamiento
                DefaultCategoryDataset datos = new DefaultCategoryDataset();
                //recorriendo la libreria
                for (int y = 0; y <ContadorDatos(); y++) {
                    datos.setValue(
                            lista_a_Ordenar[y].getDato(), //obteniendo los datos numericos
                            lista_a_Ordenar[y].getCategoria(), // titulo de la tabla
                            lista_a_Ordenar[y].getCategoria() //obteniendo nombre de la columna
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
        }
        hilo2.detener();
    }

}
