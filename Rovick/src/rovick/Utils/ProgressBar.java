
package rovick.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/**
 * Proceso que se encarga de aumentar los valores la ProgresBar de la vista principal.
 * @author Victor Pastor Urueña
 */
public class ProgressBar extends Thread{
    
    private JProgressBar pb = null;
    private int equiv = 0;
    private long segs = 0;
    
    public ProgressBar(JProgressBar pb,long segs,int equiv) {
        this.pb=pb;
        this.equiv = equiv;
        this.segs = segs;
    }

    @Override
    public void run() {
        int pb_tics =(int) segs * equiv;
        pb.setValue(0);
        pb.setMaximum(pb_tics);
        for (int i = 1; i <= pb_tics; i++) {
            this.pb.setValue(i);
            try {
                this.sleep(1000/equiv);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
