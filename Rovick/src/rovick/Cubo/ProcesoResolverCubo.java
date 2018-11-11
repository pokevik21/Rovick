package rovick.Cubo;

import java.util.logging.Level;
import java.util.logging.Logger;
import rovick.MainFrame;
import rovick.Utils.WebCamController;

/**
 *Proceso que se encarga de resolver el cubo.
 * @author Victor Pastor Urueña
 */
public class ProcesoResolverCubo extends Thread{
    
    private MainFrame vistaPrincipal = null;
    private WebCamController camara = null;
    private CubeController cubo = null;

    
    public ProcesoResolverCubo(MainFrame vistaPrincipal, WebCamController camara, CubeController cubo) {
        this.vistaPrincipal = vistaPrincipal;
        this.camara = camara;
        this.cubo = cubo;

    }

    
    public void hacerPaso(String move,String foto,int seg){
        try {
            cubo.doMove(move, false, true);
            sleep(seg*1000 + 300);
            if(!foto.isEmpty())camara.takePhoto(foto);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcesoResolverCubo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run() {
        vistaPrincipal.desableButtons(false);
        vistaPrincipal.getBt_parar().setEnabled(true);
        camara.cleeanPhotos();
        
        //Hacer movimientos y fotos
        hacerPaso("S", "5", 3);
        hacerPaso("1", "4", 2);
        hacerPaso("2", "2", 2);
        hacerPaso("3", "1", 2);
        hacerPaso("4", "6", 1);
        hacerPaso("5", "3", 7);
        hacerPaso("6", "", 6);
           
        
        cubo.doMove("E", false);
        vistaPrincipal.desableButtons(true);
    }
    
    
    
}
