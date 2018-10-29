package rovick.Utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rovick.MainFrame;

/**
 * Proceso que se encarga de llevar la cuenta atrás del tiempo,
 * este llama al proceso de PrograssBar a su vez.
 * Cuando termina muesta un mensaje de Terminado.
 * @author Victor Pastor Urueña
 */
public class CuentaAtras extends Thread{
    
    private MainFrame vistaPrincipal = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("mm' min' ss' seg'");
    
    public CuentaAtras(MainFrame vista) {
        this.vistaPrincipal = vista;
    }

    @Override
    public void run() {
        vistaPrincipal.getPb_progreso().setValue(0);
        int segs = (int) (vistaPrincipal.getTiempo().getTimeInMillis()/1000);
        ProgressBar p_pb = new ProgressBar(vistaPrincipal.getPb_progreso(), vistaPrincipal.getTiempo().getTimeInMillis()/1000 -1, 10);
        p_pb.start();
        for (int i = 0; i < segs; i++) {
            try {
                vistaPrincipal.getTiempo().add(GregorianCalendar.SECOND, -1);
                vistaPrincipal.getLb_tiempo().setText(sdf.format(vistaPrincipal.getTiempo().getTime()));
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CuentaAtras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            p_pb.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CuentaAtras.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(vistaPrincipal, "TERMINADO :)");
        vistaPrincipal.resetMoves();
        vistaPrincipal.desableButtons(true);
    }
    
}
