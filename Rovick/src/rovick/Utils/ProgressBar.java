
package rovick.Utils;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Proceso que se encarga de aumentar los valores la ProgresBar de la vista principal.
 * @author Victor Pastor Urueña
 */
public class ProgressBar extends Thread{
    
    private JProgressBar pb = null;
    private int equiv = 0;
    private long segs = 0;
    private JLabel lb_estado = null;
    private String estado = "";
    
    /**
     * Contructor del proceso.
     * @param pb JProgressBar a la que añade.
     * @param segs Segundos.
     * @param equiv Cuanto vale cada segundo. Ej,10 => 1s=1/10s.
     * @param lb_estado JLabel a la que pone en que estado se encuetra.
     * @param estado String del estado.
     */
    public ProgressBar(JProgressBar pb,long segs,int equiv,JLabel lb_estado, String estado) {
        this.pb=pb;
        this.equiv = equiv;
        this.segs = segs;
        this.lb_estado = lb_estado;
        this.estado = estado;
    }


    /**
     * Metodo para iniciar el proceso de la ProgressBar
     */
    @Override
    public void run() {
        this.lb_estado.setText(estado);
        int pb_tics =(int) segs * equiv;
        pb.setValue(0);
        pb.setMaximum(pb_tics);
        for (int i = 1; i <= pb_tics; i++) {
            this.pb.setValue(i);
            try {
                this.sleep(1000/equiv);
            } catch (InterruptedException ex) {
                break;
            }
        }
        this.lb_estado.setText("");
    }
    
}
