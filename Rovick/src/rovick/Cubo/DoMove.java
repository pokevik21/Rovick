package rovick.Cubo;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import rovick.MainFrame;

/**
 * Proceso que se encoraga de realizar un movimiento,
 * el que se le pasa en el contructor.
 */
public class DoMove extends Thread{
    
    private String move;
    private PanamaHitek_Arduino arduino;
    private MainFrame vistaPrincipal;
    private boolean desdeAllMovs =  false;

    public DoMove(String move,PanamaHitek_Arduino arduino,MainFrame vistaPrincipal) {
        this.move = move;
        this.arduino = arduino;
        this.vistaPrincipal = vistaPrincipal;
    }

    public DoMove(String move,PanamaHitek_Arduino arduino,MainFrame vistaPrincipal,boolean desdeAllMovs) {
        this.move = move;
        this.arduino = arduino;
        this.vistaPrincipal = vistaPrincipal;
        this.desdeAllMovs = desdeAllMovs;
    }
    
    @Override
    public void run() {
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
            arduino.sendData(move+";");
            Thread.sleep(espera * 1000);
            
        } catch (ArduinoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    if(!desdeAllMovs) vistaPrincipal.desableButtons(true);
    System.out.println("Terminado: "+move);
    }
    
    
    
    
}
