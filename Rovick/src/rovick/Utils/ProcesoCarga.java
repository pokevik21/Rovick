package rovick.Utils;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Pastor Urueña
 */
public class ProcesoCarga extends Thread{
    
    private PantallaDeCarga pdc = null;
    private boolean cerrar = false;
    private int sleep = 500;
    
    public ProcesoCarga() {
        pdc = new PantallaDeCarga();
    }

    @Override
    public void run() {
        pdc.setVisible(true);
        puntos();
    }

    private void puntos(){
        try {
        sleep(sleep);
        while (!cerrar && !isInterrupted()) {
            String texto = this.pdc.getLb_cargando().getText();
            String texto_limpio = texto;
            for (int i = 0; i < 3; i++) {
                texto += ".";
                pdc.cambiarTexto(texto);
                sleep(sleep);
            }
            pdc.cambiarTexto(texto_limpio);
            sleep(sleep);
        }
       } catch (InterruptedException ex) {
                Logger.getLogger(ProcesoCarga.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex){
            
        }
    }
    
    public void cambiarTexto(String text){
        //pdc.cambiarTexto(text);
        this.pdc.getLb_cargando().setText(text);
    }
    
    public void close(){
        pdc.close();
        cerrar = true;
    }
    
}
