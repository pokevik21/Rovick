package rovick.cube;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rovick.MainFrame;

/**
 * Clase que se encarga de los movimientos del cubo de rubick.
 * @author Victor Pastor Urueña
 */
public class CubeController{
    
    private MainFrame vistaPrincipal = null;
    private PanamaHitek_Arduino arduino;
    private SelectPort selPort = null;
    private boolean agarrado = false;
    
    public CubeController(MainFrame vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        arduino=new PanamaHitek_Arduino();
        selPort=new SelectPort(vistaPrincipal, true);
        confArduinoConnection();
    }

//        __  __          _                 _               
//       |  \/  |   ___  | |_    ___     __| |   ___    ___ 
//       | |\/| |  / _ \ | __|  / _ \   / _` |  / _ \  / __|
//       | |  | | |  __/ | |_  | (_) | | (_| | | (_) | \__ \
//       |_|  |_|  \___|  \__|  \___/   \__,_|  \___/  |___/

    private void confArduinoConnection(){
       String connection = "";
        if (arduino.getPortsAvailable()>1){ 
           selPort.setVisible(true);
           int result = selPort.getReturnStatus();
           if (result == SelectPort.RET_OK){
                connection= selPort.getPort();
            }else{
                JOptionPane.showMessageDialog(null, "Nose ha podido conectar a nungun puerto", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
           }
        }else if (arduino.getPortsAvailable()==1){
              connection = arduino.getSerialPorts().get(0).toString();
       }else{
            JOptionPane.showMessageDialog(null, "Nose ha podido conectar a nungun puerto", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        try {
            arduino.arduinoTX(connection, 9600);
            vistaPrincipal.getLb_port().setText(connection);
            System.out.println("Conexion realizada en el puerto "+connection);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    /**
     * Metodo que envia al arduino la orden de hacer un movimiento.
     * Llama al proceso DoMove pora ello.
     * @param move El movimiento ha realizar
     * @param borrar si se quiere que se borre del contador de movimientos o no.
     * @see DoMove
     */
    public void doMove(String move,boolean borrar){
        agarrado = true;
        DoMove hacerMovimiento = new DoMove(move,arduino);
        hacerMovimiento.start();
        try {
            hacerMovimiento.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CubeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(borrar)vistaPrincipal.finisMove(move);
        System.out.println("Terminado: "+move);
        if(move.equals("E"))agarrado = false;
    }
    
    /**
     * Metodo que se encarga de realizar todas los movimientos.
     * Llama al proceso DoAllMoves para ello.
     * @see DoAllMoves
     */
    public void doAllMovs(){
        DoAllMoves doAllMoves = new DoAllMoves(vistaPrincipal, arduino);
        doAllMoves.start();
        
    }

    public PanamaHitek_Arduino getArduino() {
        return arduino;
    }

    public boolean isAgarrado() {
        return agarrado;
    }

    public void setAgarrado(boolean agarrado) {
        this.agarrado = agarrado;
    }
    
    
    
    
    
    
}
