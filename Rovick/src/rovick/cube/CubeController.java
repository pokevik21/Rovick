package rovick.cube;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPortException;
import rovick.MainFrame;
import rovick.Utils.CuentaAtras;
import rovick.Utils.Utiles;


public class CubeController{
    
    private MainFrame vistaPrincipal = null;
    private PanamaHitek_Arduino arduino;
    private SelectPort selPort = null;

    public CubeController(MainFrame vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        arduino=new PanamaHitek_Arduino();
        selPort=new SelectPort(vistaPrincipal, false);
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
            System.out.println("Conexion realizada en el puerto "+connection);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void doMove(String move,boolean borrar){
        int espera = 0;
        switch (move) {
            case "R":
            case "L":
            case "U":
            case "D":
            case "RD":
            case "LD":
            case "UD":
            case "DD":
                espera = 2;
                break;
            case "F":
            case "B":
            case "FD":
            case "BD":
                espera = 8;
                break;
            case "V":
            case "I":
            case "FDI":
            case "BDI":
                espera = 3;
                break;
            case "E":
            case "S":
                espera = 1;
                break;
        }
        try {
            arduino.sendData(move);
            Thread.sleep(espera * 1000);
            if(borrar)vistaPrincipal.finisMove(move);
            System.out.println("Terminado: "+move);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doAllMovs(){
        if(!vistaPrincipal.getMovimientos().isEmpty()){
            CuentaAtras cuentsAtas = new CuentaAtras(vistaPrincipal);
            cuentsAtas.start();
            for (int k=0; k < vistaPrincipal.getMovimientos().size(); k++) {
                int repetir = 1;
                String movARealizar = vistaPrincipal.getMovimientos().get(k);
                if(Utiles.tieneNumero(vistaPrincipal.getMovimientos().get(k))){
                    repetir = Utiles.extraerNumero(vistaPrincipal.getMovimientos().get(k));
                    movARealizar = vistaPrincipal.getMovimientos().get(k).substring(0,Utiles.primerNum(vistaPrincipal.getMovimientos().get(k)));
                    boolean conVuelta = false;
                    
                    switch (String.valueOf(movARealizar.charAt(0))) {
                        case "F":
                        case "B":
                            conVuelta = true;
                            break;
                    }
                    for (int i = 0; i < repetir; i++) {
                        if(conVuelta && i==0)doMove("I", false);
                        switch (movARealizar) {
                            case "F":
                                doMove("R", true);
                                if (i==repetir-1)doMove("V", false);
                                break;
                            case "FD":
                                doMove("RD", true);
                                if (i==repetir-1)doMove("V", false);
                                break;
                            case "B":
                                doMove("L", true);
                                if (i==repetir-1)doMove("V", false);
                                break;
                            case "BD":
                                doMove("LD", true);
                                if (i==repetir-1)doMove("V", false);
                                break;
                            default:
                                doMove(movARealizar, true);
                        }
                    }
                }else{
                    doMove(movARealizar, true);
                }
                vistaPrincipal.getMovimientos().remove(k);
                vistaPrincipal.imprimirMovimientos();
            }
        }else{
            JOptionPane.showMessageDialog(vistaPrincipal,"No hay movimiento que hacer","Sin movimientos",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public PanamaHitek_Arduino getArduino() {
        return arduino;
    }
    
    
    
    
    
    
}
