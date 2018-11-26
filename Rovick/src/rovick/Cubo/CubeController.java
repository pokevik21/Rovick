package rovick.Cubo;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import rovick.MainFrame;
import rovick.Utils.WebCamController;

/**
 * Clase que se encarga de los movimientos del cubo de rubick.
 * @author Victor Pastor Urueña
 */
public class CubeController{
    
    private MainFrame vistaPrincipal = null;
    private PanamaHitek_Arduino arduino;
    private SelectPort selPort = null;
    private boolean agarrado = false;
    private DoAllMoves doAllMoves = null;
    private boolean respuesta = false;
    private ProcesoResolverCubo prc = null;
    
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
       boolean encontrado = false;
       
        SerialPortEventListener lisener = new SerialPortEventListener() {
           @Override
           public void serialEvent(SerialPortEvent spe) {
               try {
                   if(arduino.isMessageAvailable()){
                       //System.out.println("Desde Arduino: "+arduino.printMessage());
                       respuesta=true;
                   }
               } catch (SerialPortException ex) {
                   Logger.getLogger(CubeController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (ArduinoException ex) {
                   Logger.getLogger(CubeController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
       
        List<String> ports = arduino.getSerialPorts();
        for (String port : ports) {
           try {
               arduino.arduinoRX(port, 9600, lisener);
               Thread.sleep(2000);
               System.out.println("arduino "+respuesta);
               if(respuesta){
                  connection = port;
                  encontrado = true;
                  break;
               }
              arduino.killArduinoConnection();
           } catch (InterruptedException ex) {
               Logger.getLogger(CubeController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SerialPortException ex) {
               Logger.getLogger(CubeController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ArduinoException ex) {
               Logger.getLogger(CubeController.class.getName()).log(Level.SEVERE, null, ex);
               JOptionPane.showMessageDialog(vistaPrincipal, "Nos se ha podido conectar al puerto", "Error", JOptionPane.ERROR_MESSAGE);
               System.exit(0);
           }
        }
        
       if (!encontrado){
           System.out.println("No se ha encontrado puetos automaticamente");
           try {
               arduino.killArduinoConnection();
           } catch (ArduinoException ex) {
               System.err.println("No hay conexion, por lo que no se le puede desconectar..."); 
           }
           
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
       }

           try {
               arduino.killArduinoConnection();
               arduino.arduinoTX(connection, 9600);
           } catch (ArduinoException ex) {
               System.err.println("No hay conexion, por lo que no se le puede desconectar..."); 
           }

        vistaPrincipal.getLb_port().setText(connection);
        System.out.println("Conexion realizada en el puerto "+connection);
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
        vistaPrincipal.desableButtons(false);
        DoMove hacerMovimiento = new DoMove(move,arduino,vistaPrincipal);
        hacerMovimiento.start();
        if(borrar)vistaPrincipal.finisMove(move);
        if(move.equals("E"))agarrado = false;
    }
    
    public void doMove(String move,boolean borrar, boolean desdeResolver){
        agarrado = true;
        vistaPrincipal.desableButtons(false);
        DoMove hacerMovimiento = new DoMove(move,arduino,vistaPrincipal,desdeResolver);
        hacerMovimiento.start();
        if(borrar)vistaPrincipal.finisMove(move);
        if(move.equals("E"))agarrado = false;
    }
    
    /**
     * Metodo que se encarga de realizar todas los movimientos.
     * Llama al proceso DoAllMoves para ello.
     * @see DoAllMoves
     */
    public void doAllMovs(){
        this.doAllMoves = new DoAllMoves(vistaPrincipal, arduino);
        doAllMoves.start();
    }

    public String resolverCubo(WebCamController camara){
        prc= null;
        camara.cleeanPhotos();
        this.prc = new ProcesoResolverCubo(vistaPrincipal,camara,this);
        prc.start();
        return "";
    }
    
    /**
     * Devuelve el arduino del controlador
     * @return Devuelve el arduino del controlador
     */
    public PanamaHitek_Arduino getArduino() {
        return arduino;
    }
    
    public boolean isAgarrado() {
        return agarrado;
    }

    public void setAgarrado(boolean agarrado) {
        this.agarrado = agarrado;
    }
    
    public void endAllMovs(){
        if (doAllMoves != null && doAllMoves.isAlive())this.doAllMoves.interrupt();
        if (prc != null && prc.isAlive())this.prc.interrupt();
    }
    
}
