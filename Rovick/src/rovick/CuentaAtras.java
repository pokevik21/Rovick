package rovick;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author A692371
 */
public class CuentaAtras extends Thread{
    
    private MainFrame vistaPrincipal = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("mm' min' ss' seg'");
    
    public CuentaAtras(MainFrame vista) {
        this.vistaPrincipal = vista;
    }

    @Override
    public synchronized void start() {
        super.start();
        vistaPrincipal.getPb_progreso().setValue(0);
        int segs = (int) (vistaPrincipal.getTiempo().getTimeInMillis()/1000);
        vistaPrincipal.getPb_progreso().setMaximum(segs);
        for (int i = 0; i < segs; i++) {
            try {
                vistaPrincipal.getTiempo().add(GregorianCalendar.SECOND, -1);
                System.out.println("process:  "+(i+1));
                vistaPrincipal.getPb_progreso().setValue(i+1);
                vistaPrincipal.getLb_tiempo().setText(sdf.format(vistaPrincipal.getTiempo().getTime()));
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CuentaAtras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
