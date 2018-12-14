package rovick.Utils;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Proceso que se encarga de hacer la animación de poner puntos suspensivos.
 * @author Victor Pastor Urueña
 */
public class ProcesoCarga extends Thread{
    
    private PantallaDeCarga pdc = null;
    private int sleep = 500;
    private boolean cerrar = false;//true cuando quieres que se cierre.
    
    public ProcesoCarga() {
        pdc = new PantallaDeCarga();
    }

     /**
     * Metodo para iniciar el proceso de la ProgressBar
     */
    @Override
    public void run() {
        pdc.setVisible(true);
        puntos();
    }

    /**
     * Se encarga de poner o quitar puntos.
     */
    private void puntos(){
        try {
        sleep(sleep);
        while (!cerrar && !isInterrupted()) {
            String texto = this.pdc.getTexto_carga();
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
    
    /**
     * Cambiar el texto de la pantalla de carga
     * @param text al texto que cambia
     */
    public void cambiarTexto(String text){
        //pdc.cambiarTexto(text);
        this.pdc.setTexto_carga(text);
    }
    
    /**
     * Termina el proceso
     */
    public void close(){
        pdc.close();
        cerrar = true;
    }
    
}