
package Model;

import View.ReporteVista;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

public class Impresor extends Thread {
    private ReporteVista view;
    private static int contador=0;

    public Impresor(ReporteVista view) {
        this.view = view;
    }
    
    @Override
    public void run(){
        //generando el nombre del pdf con los reportes de transacciones
        String NombreArchivo="GraficaOrdenada"+contador;
        contador++;
        //ruta donde se guardara el reporte generado en pdf
         String rutaProyecto = System.getProperty("user.dir");
         
         //guardando el pdf
         String rutaArchivo = rutaProyecto+File.separator+NombreArchivo+".pdf";
         
         try{
            // Esperar 2 segundos para que el JFrame termine de renderizarse
            Thread.sleep(2000); 
             
            // Obtener el contenido del JScrollPane
            JViewport viewport = view.Scroll.getViewport();
            Component contenido = viewport.getView(); // Obtiene el panel completo
            
            // Capturar el contenido del JFrame como imagen
            BufferedImage imagen = new BufferedImage(contenido.getWidth(), contenido.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imagen.createGraphics();
            // Renderizar el contenido completo
            contenido.paint(g2d);
            g2d.dispose();
            
            // Guardar la imagen temporalmente en disco
            ImageIO.write(imagen, "png", new java.io.File("temp.png"));
            
            //creando el pdf nuevo
            Document document = new Document(new Rectangle(contenido.getWidth(), contenido.getHeight()));
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            // Convertir la imagen a un formato compatible con iText
            Image pdfImage = Image.getInstance("temp.png");
            pdfImage.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfImage);
            
            document.close();
            JOptionPane.showMessageDialog(view,"Reporte generado con exito","INF", JOptionPane.INFORMATION_MESSAGE);
         }catch(Exception e){
             JOptionPane.showMessageDialog(view,"Error al guardar el reporte", "ERROR", JOptionPane.ERROR_MESSAGE);
         }
    }
}
