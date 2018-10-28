package rovick.cube;

import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rovick.MainFrame;
import rovick.Utils.CuentaAtras;
import rovick.Utils.Utiles;


public class DoAllMoves extends Thread{
    
    private MainFrame vistaPrincipal= null;
    private PanamaHitek_Arduino arduino;
    CuentaAtras cuentsAtas = null;
    
    public DoAllMoves(MainFrame vista,PanamaHitek_Arduino arduino) {
        this.arduino = arduino;
        this.vistaPrincipal = vista;   
    }

    public void doMove(String move,boolean borrar){
        DoMove hacerMovimiento = new DoMove(move,arduino);
        hacerMovimiento.start();
        try {
            hacerMovimiento.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CubeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(borrar)vistaPrincipal.finisMove(move);
        System.out.println("Terminado: "+move);
    }
    
    @Override
    public void run() {
        if(!vistaPrincipal.getMovimientos().isEmpty()){
            vistaPrincipal.desableButtons(false);
            cuentsAtas = new CuentaAtras(vistaPrincipal);
            cuentsAtas.start();
            int movs =vistaPrincipal.getMovimientos().size();
            for (int k=0; k < movs; k++) {
                int repetir = 1;
                String movARealizar = vistaPrincipal.getMovimientos().get(0);
                if(Utiles.tieneNumero(vistaPrincipal.getMovimientos().get(0))){
                    repetir = Utiles.extraerNumero(vistaPrincipal.getMovimientos().get(0));
                    movARealizar = vistaPrincipal.getMovimientos().get(0).substring(0,Utiles.primerNum(vistaPrincipal.getMovimientos().get(0)));
                    boolean conVuelta = false;
                    
                    switch (String.valueOf(movARealizar.charAt(0))) {
                        case "F":
                        case "B":
                            conVuelta = true;
                            break;
                    }
                    if(conVuelta)doMove("I", false);
                    for (int i = 0; i < repetir; i++) {
                        switch (movARealizar) {
                            case "F":
                                doMove("R", true);
                                break;
                            case "FD":
                                doMove("RD", true);
                                break;
                            case "B":
                                doMove("L", true);
                                break;
                            case "BD":
                                doMove("LD", true);
                                break;
                            default:
                                doMove(movARealizar, true);
                        }
                    }
                    if (conVuelta)doMove("V", false);
                }else{
                    doMove(movARealizar, true);
                }
                vistaPrincipal.getMovimientos().remove(0);
                vistaPrincipal.imprimirMovimientos();
            }
        }else{
            JOptionPane.showMessageDialog(vistaPrincipal,"No hay movimiento que hacer","Sin movimientos",JOptionPane.INFORMATION_MESSAGE);
        }
        doMove("E", true);
    }
    
    
}
    
    
    



