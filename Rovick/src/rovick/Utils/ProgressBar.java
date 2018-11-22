
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
    
    public ProgressBar(JProgressBar pb,long segs,int equiv,JLabel lb_estado, String estado) {
        this.pb=pb;
        this.equiv = equiv;
        this.segs = segs;
        this.lb_estado = lb_estado;
        this.estado = estado;
    }

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
