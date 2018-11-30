package rovick.Utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import rovick.MainFrame;

/**
 * Proceso que se encarga de llevar la cuenta atrás del tiempo,
 * este llama al proceso de PrograssBar a su vez.
 * Cuando termina muesta un mensaje de Terminado.
 */
public class CuentaAtras extends Thread{
    
    private MainFrame vistaPrincipal = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("mm' min' ss' seg'");
    private String estado = "";
    private boolean impMng = true; 
    
    public CuentaAtras(MainFrame vista,String estado) {
        this.vistaPrincipal = vista;
        this.estado = estado;
    }

    public CuentaAtras(MainFrame vista,String estado,boolean mensaje) {
        this.vistaPrincipal = vista;
        this.estado = estado;
        this.impMng = mensaje;
    }
    
    @Override
    public void run() {
        vistaPrincipal.getPb_progreso().setValue(0);
        int segs = (int) (vistaPrincipal.getTiempo().getTimeInMillis()/1000);
        ProgressBar p_pb = new ProgressBar(vistaPrincipal.getPb_progreso(), vistaPrincipal.getTiempo().getTimeInMillis()/1000 -1, 10,vistaPrincipal.getLb_estadoPrograssBar(),estado);
        p_pb.start();
        for (int i = 0; i < segs; i++) {
            try {
                vistaPrincipal.getTiempo().add(GregorianCalendar.SECOND, -1);
                vistaPrincipal.getLb_tiempo().setText(sdf.format(vistaPrincipal.getTiempo().getTime()));
                this.sleep(1000);
            } catch (InterruptedException ex) {
                p_pb.interrupt();
                break;
            }
        }
        try {
            p_pb.join();
        } catch (InterruptedException ex) {
        }
        if(impMng){
            JOptionPane.showMessageDialog(vistaPrincipal, "TERMINADO :)");
            vistaPrincipal.desableButtons(true);
        }
        vistaPrincipal.resetMoves();
    }
     
}