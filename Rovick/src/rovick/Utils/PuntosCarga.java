package rovick.Utils;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *Proceso que añade 3 puntos y los quita esperando 0,5 segs entre uno y otro.
 * @author Victor Pastor Urueña
 */
public class PuntosCarga extends SwingWorker <Void, String >{
    
    private PantallaDeCarga pantalla = null;
    private boolean cerrar = false;
    
    
    public PuntosCarga(PantallaDeCarga pantalla) {
        this.pantalla = pantalla;
        
    }

    
    public void close(){
        this.cerrar = true;
    }

    @Override
    protected void process(List<String> chunks) {
        this.pantalla.getLb_cargando().setText(chunks.get(chunks.size()-1));
    }

  
    
    @Override
    protected Void doInBackground() throws Exception {
        try {
        sleep(500);
        while (!cerrar) {
            String texto = this.pantalla.getLb_cargando().getText();
            String texto_limpio = texto;
            for (int i = 0; i < 3; i++) {
                texto += ".";
                publish(texto);
                System.out.println("texto:"+texto);
                sleep(500);
            }
            publish(texto_limpio);
       
            System.out.println("texto:"+texto);
            sleep(500);
        }
       } catch (InterruptedException ex) {
                Logger.getLogger(PuntosCarga.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null; 
    }
    
    
}
