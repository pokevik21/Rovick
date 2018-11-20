package rovick;

/**
 * \defgroup VistaPrincipal
 * @{
 * @}
 */

import rovick.Utils.WebCamController;
import rovick.Utils.Utiles;
import com.panamahitek.ArduinoException;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import jssc.SerialPortException;
import rovick.Utils.ProcesoCarga;
import rovick.Cubo.CubeController;

/**
 * Vista pincipal del resolvedor de cubos de rubick
 * @author Victor Pastor Urueña
 */
public class MainFrame extends javax.swing.JFrame {
    
   
    
//        __     __                 _           _       _              
//        \ \   / /   __ _   _ __  (_)   __ _  | |__   | |   ___   ___ 
//         \ \ / /   / _` | | '__| | |  / _` | | '_ \  | |  / _ \ / __|
//          \ V /   | (_| | | |    | | | (_| | | |_) | | | |  __/ \__ \
//           \_/     \__,_| |_|    |_|  \__,_| |_.__/  |_|  \___| |___/

    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
    /** \defgroup Variable
     *  \ingroup VistaPrincipal
     * @{
     */
    /**ArrayList de movimientos acumulados */
    private ArrayList<String> movimientos = null;
    /**Numero de movimientos */
    private int numMovimientos = 0;
    /** Calendario que contiene el tiempo estimado en realizar los movimientos o resolver*/
    private GregorianCalendar tiempo = null;
    /** Encargado de fromatear la fecha a "mm min ss seg"*/
    private SimpleDateFormat sdf = null;
    /** Array con todos los movimientos posibles, utilizado para generar los movimientos aleatorios*/
    private String[] posiblesMovs = {"R","RD","L","LD","U","UD","D","DD","F","FD","B","BD"};
    /** Objeto que se encarga de generar randoms */
    private Random rand = null;
    /** Objeto que se comunica con el cubo, realizando los movimientos y demás */
    private CubeController cuboController = null;
    /** Objeto que controla la camara, haciendo fotos y demás*/
    private WebCamController wc = null;
    /** Bandera para saber si hay que añadir el tiempo de agarre y soltar o no*/
    private boolean flagFinish = false;
    /** Controla si la luz esta encendida o apagada*/
    private boolean luz_encendida = false;
    
    /**
     * @}
     */
//</editor-fold>
    
//*********************************** FIN VARIABLES *********************************************
    
    
//        __  __          _                 _               
//       |  \/  |   ___  | |_    ___     __| |   ___    ___ 
//       | |\/| |  / _ \ | __|  / _ \   / _` |  / _ \  / __|
//       | |  | | |  __/ | |_  | (_) | | (_| | | (_) | \__ \
//       |_|  |_|  \___|  \__|  \___/   \__,_|  \___/  |___/
/**
* \defgroup Metodos
* \ingroup VistaPrincipal
* @{
*/
    //<editor-fold defaultstate="collapsed" desc="AUXILIARES">

    /**
     * \defgroup Auxiliares
     * \ingroup Metodos
     * @{
     * Grupo de Metodos utilizados por los metodos principales
     */
    
    /**
     *Limpia la interface y duvuelve las variables a su estado por defecto.
     */
    public void resetMoves(){
        movimientos.clear();
        tiempo.setTime(new Date(0));
        this.numMovimientos = 0;
        this.lb_movs.setText("0");
        imprimirMovimientos();
        this.pb_progreso.setValue(0);
        flagFinish = false;
    }
    
    /**
     *Suma al string move un movimiento. Ej:"F"->"F2"
     * @param move String(move) al que le sumar un movimiento. 
     * @return Devuelve el numero con numero ya sumado.
     */
    public String sumarNumeroMove(String move){
        String resultado="";
        if(Utiles.tieneNumero(move)){
            int numMove = Utiles.extraerNumero(move)+1;
            resultado = move.substring(0,Utiles.primerNum(move)) + numMove;
        }else{
            resultado = move+"2";
        }
        return resultado;
    }
    
    /**
     *Encorgado de rellenar el TextArea de los movimientos a partir del ArrayList movimientos
     */
    public void imprimirMovimientos(){
        this.ta_movimientos.setText("");
        for (int i = 0; i < movimientos.size(); i++) {
            String movimiento = movimientos.get(i);
            if(movimiento.length()>1 && movimiento.charAt(1) == 'D'){
                String cadena = "";
                for (int j = 0; j < movimiento.length(); j++) {
                    char caracter = movimiento.charAt(j);
                    if(j == 1) caracter='\'';
                    cadena += caracter;
                }
                movimiento = cadena;
            }
            this.ta_movimientos.setText(this.ta_movimientos.getText()+movimiento);
            if (i != movimientos.size()-1) this.ta_movimientos.setText(this.ta_movimientos.getText()+" - ");
        }
        this.lb_tiempo.setText(sdf.format(tiempo.getTime()));
    }
    
    /**
     *Devuelve true si el ultimo movimiento del ArrayList movimientos contiene el String move
     * @param move Al que busca en el utimo del Arraylist de movimientos
     * @return Devuelve true si el ultimo movimiento del ArrayList movimientos contiene el String move
     */
    public boolean ultimoContiene (String move){
        String lastMove = movimientos.get(movimientos.size()-1);
        if(Utiles.tieneNumero(lastMove)){
            String lastMoveSinNum = lastMove.substring(0, Utiles.primerNum(lastMove));
            if(lastMoveSinNum.equals(move)) return true;
        }
        return false;
    }
    
    /**
     *Resta el String finMove al numero de movimientos,Jlabel lb_movs.
     * @param finMove Es el movimiento que termina
     */
    public void finisMove(String finMove){
        int movimientos = Integer.parseInt(this.lb_movs.getText());
        if (movimientos > 0) --movimientos;
        this.lb_movs.setText(String.valueOf(movimientos));
    }
    
    /**
     *Encargado de añadir el tiempo correspondiente el GregorianCalendar tiempo.
     * @param tipoMove El movimiento el cual tarda X segundos.
     */
    public void addTime(String tipoMove){
        int seg = 0;
        switch (tipoMove) {
            case "F":
            case "FD":
            case "B":
            case "BD":
                if(ultimoContiene(tipoMove)){
                    seg = 2;
                }else{
                    seg = 8;
                }
                break;
            default:
                seg = 2;
        }
        if (!flagFinish){
            seg+=4;//Por las acciones de aggarar el cubo y soltarlo.
            flagFinish=true;
        }
        tiempo.add(GregorianCalendar.SECOND, seg);
    }
    
    /**
     *Añade el movimiento al ArrayLista movimientos dependiendo de que movimiento sea.
     * @param move El movimiento ha añadir.
     */
    public void addMove(String move){
        if (movimientos.isEmpty()){
            movimientos.add(move);
        }else{
            int ultPos = movimientos.size()-1;
            String ultimoMove = movimientos.get(ultPos);
            
            String ultimoMoveSinNum = ultimoMove;
            if (Utiles.tieneNumero(ultimoMove)){
                ultimoMoveSinNum=ultimoMove.substring(0,Utiles.primerNum(ultimoMove));
            }
            
            if (ultimoMoveSinNum.equals(move)){
                movimientos.set(ultPos, sumarNumeroMove(ultimoMove));
            }else{
                movimientos.add(move);
            }
        }
        addTime(move);
        this.numMovimientos++;
        this.lb_movs.setText(String.valueOf(this.numMovimientos));
        imprimirMovimientos();
    }
    
    /**
     *Resta tiempo al GregorianCalendar tiempo dependiendo de que movimiento sea.
     * @param move El movimiento que tiene que restar.
     */
    public void removeTime(String move){
        int seg = 0;
        if(Utiles.tieneNumero(move)){
            String lastMoveSinNum = move.substring(0, Utiles.primerNum(move));
            int numerMovs = Utiles.extraerNumero(move);
            switch (lastMoveSinNum) {
                case "F":
                case "FD":
                case "B":
                case "BD":
                    seg = 6;
                    break;
            }
            this.numMovimientos -= numerMovs;
            seg += 2*numerMovs;
        }else{
            switch (move) {
                case "F":
                case "FD":
                case "B":
                case "BD":
                    seg = 8;
                    break;
                default:
                    seg = 2;
            }
            this.numMovimientos --;
        }
        tiempo.add(GregorianCalendar.SECOND, -1*seg);
    }
    
    /**
     *Borra el ultimo movimiento del ArrayList movimientos, y ajusta los tiempos y los movimientos.
     */
    public void removeLastMove(){
        if(!movimientos.isEmpty()){
            removeTime(movimientos.get(movimientos.size()-1));
            if(Utiles.tieneNumero(movimientos.get(movimientos.size()-1))){
                int movs = Utiles.extraerNumero(movimientos.get(movimientos.size()-1));
                this.lb_movs.setText(String.valueOf(Integer.parseInt(this.lb_movs.getText())-movs));
            }else{
               this.lb_movs.setText(String.valueOf(Integer.parseInt(this.lb_movs.getText())-1));
            }
            movimientos.remove(movimientos.size()-1);
            imprimirMovimientos();
            if(movimientos.isEmpty())resetMoves();
      }
    }
    
    /**
     *Activa o desactiva los botones para que no se pueda hacer nada 
     * mientas que se haga el proceso de hacer todos los movimientos.
     * @param estado Al estado al que los cambias.
     */
    public void desableButtons(boolean estado){
        this.bt_deshacer.setEnabled(estado);
        this.bt_limpiarMovs.setEnabled(estado);
        this.bt_realizarMovs.setEnabled(estado);
        this.bt_resolver.setEnabled(estado);
        this.bt_soltar.setEnabled(estado);
        this.bl_borrarUltimoMove.setEnabled(estado);
        this.lb_B.setEnabled(estado);
        this.lb_BD.setEnabled(estado);
        this.lb_D.setEnabled(estado);
        this.lb_DD.setEnabled(estado);
        this.lb_F.setEnabled(estado);
        this.lb_FD.setEnabled(estado);
        this.lb_L.setEnabled(estado);
        this.lb_LD.setEnabled(estado);
        this.lb_R.setEnabled(estado);
        this.lb_RD.setEnabled(estado);
        this.lb_U.setEnabled(estado);
        this.lb_UD.setEnabled(estado);
        this.cb_hacerSegunPulsas.setEnabled(estado);
        this.sp_deshacer.setEnabled(estado);
        this.cb_soloAlg.setEnabled(estado);
    }
    
    /**
     * Envia orden al arduino para encender los LEDs del robot
     */
    public void encenderLuz(){
        if(luz_encendida){
            try {
                cuboController.getArduino().sendData("Z;");
                luz_encendida=true;
            } catch (ArduinoException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Envia orden al arduino para apagar los LEDs del robot
     */
    public void apagarLuz(){
        if(luz_encendida){
            try {
                cuboController.getArduino().sendData("X;");
                luz_encendida=false;
            } catch (ArduinoException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Comunica a la camara que borre las fotos temporales.
     */
    public void clearPhotos(){
        this.wc.cleeanPhotos();
    }
    
    /**
     * @}
     */
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="IMPORTANTES">
    
    /**
     * \defgroup Principales
     * \ingroup Metodos
     * @{
     */
    
    /**
     * Contrucotr de la victa principal
     */
    public MainFrame() {
        initComponents();
        confInicial();
    }
    
    /**
     * Metodo que se encarga de configurar la vista y las conexiones utilizadas (arduino, camara...)
     */
    private void confInicial(){
        ProcesoCarga pc = new ProcesoCarga();
        pc.start();
        
        //VARIABLES
        pc.cambiarTexto("Conf. Variables");
        movimientos = new ArrayList();
        rand = new Random();
        this.ta_movimientos.setEditable(false);
        Date date = new Date(0);
        this.tiempo = new GregorianCalendar();
        tiempo.setTime(date);
        
        //VISTA:
        pc.cambiarTexto("Conf. Vista");
        setLocationRelativeTo(null);
        this.setTitle("Rovick");
        setResizable(false);
        this.setIconImage(Toolkit.getDefaultToolkit().
        getImage(ClassLoader.getSystemResource("images/cuboIco.png")));
        this.sdf = new SimpleDateFormat("mm' min' ss' seg'");
        
        //ARDUINO
        pc.cambiarTexto("Conf. Arduino");
        cuboController = new CubeController(this);
        //CAMARA WEB
        pc.cambiarTexto("Conf. Camara");
        this.wc = new WebCamController();
        pc.close();

    }

    /**
     * Metodo al que llaman los Botones de los movimientos,
     * Si el checkBox cb_hacerSegunPulsas esta activado, hace el movimiento,
     * si no lo añade al ArrayList llamando al metodo addMove.
     * @param mov El botón que se ha pulsado.
     */
    private void botonMovimiento(String mov){
        if(!this.cb_hacerSegunPulsas.isSelected()){
            addMove(mov);
        }else{
            desableButtons(false);
            cuboController.doMove(mov,false);
        }
    }
    
    /**
     * Metodo que genera los movimientos necesarios para resolver el cubo.
     */
    private void resolverCubo(){
       this.cuboController.resolverCubo(wc);
    }
    /**@} */
//</editor-fold>

 /** @} */
//*********************************** FIN METODOS ********************************************
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sep_arriba = new javax.swing.JSeparator();
        sep_bajo = new javax.swing.JSeparator();
        lb_RD = new javax.swing.JLabel();
        lb_R = new javax.swing.JLabel();
        lb_U = new javax.swing.JLabel();
        lb_L = new javax.swing.JLabel();
        lb_F = new javax.swing.JLabel();
        lb_D = new javax.swing.JLabel();
        lb_B = new javax.swing.JLabel();
        lb_UD = new javax.swing.JLabel();
        lb_LD = new javax.swing.JLabel();
        lb_FD = new javax.swing.JLabel();
        lb_BD = new javax.swing.JLabel();
        lb_DD = new javax.swing.JLabel();
        lb_txt_tiempo = new javax.swing.JLabel();
        lb_tiempo = new javax.swing.JLabel();
        pb_progreso = new javax.swing.JProgressBar();
        cb_hacerSegunPulsas = new javax.swing.JCheckBox();
        bt_resolver = new javax.swing.JButton();
        bt_limpiarMovs = new javax.swing.JButton();
        bt_realizarMovs = new javax.swing.JButton();
        bt_deshacer = new javax.swing.JButton();
        bt_soltar = new javax.swing.JButton();
        sp_deshacer = new javax.swing.JSpinner();
        lb_numMovs = new javax.swing.JLabel();
        lb_movs = new javax.swing.JLabel();
        bl_borrarUltimoMove = new javax.swing.JButton();
        sp_movimientos = new javax.swing.JScrollPane();
        ta_movimientos = new javax.swing.JTextArea();
        lb_txtPort = new javax.swing.JLabel();
        lb_port = new javax.swing.JLabel();
        cb_soloAlg = new javax.swing.JCheckBox();
        bt_parar = new javax.swing.JButton();
        cb_luz = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lb_RD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/RD.png"))); // NOI18N
        lb_RD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_RDMouseClicked(evt);
            }
        });

        lb_R.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/R.png"))); // NOI18N
        lb_R.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_RMouseClicked(evt);
            }
        });

        lb_U.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/U.png"))); // NOI18N
        lb_U.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_UMouseClicked(evt);
            }
        });

        lb_L.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/L.png"))); // NOI18N
        lb_L.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_LMouseClicked(evt);
            }
        });

        lb_F.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/F.png"))); // NOI18N
        lb_F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_FMouseClicked(evt);
            }
        });

        lb_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/D.png"))); // NOI18N
        lb_D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_DMouseClicked(evt);
            }
        });

        lb_B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/B.png"))); // NOI18N
        lb_B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_BMouseClicked(evt);
            }
        });

        lb_UD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/UD.png"))); // NOI18N
        lb_UD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_UDMouseClicked(evt);
            }
        });

        lb_LD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LD.png"))); // NOI18N
        lb_LD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_LDMouseClicked(evt);
            }
        });

        lb_FD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/FD.png"))); // NOI18N
        lb_FD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_FDMouseClicked(evt);
            }
        });

        lb_BD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BD.png"))); // NOI18N
        lb_BD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_BDMouseClicked(evt);
            }
        });

        lb_DD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DD.png"))); // NOI18N
        lb_DD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_DDMouseClicked(evt);
            }
        });

        lb_txt_tiempo.setText("Tiempo:");

        lb_tiempo.setText("00 min 00 seg");

        cb_hacerSegunPulsas.setText("Según pulsas");
        cb_hacerSegunPulsas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_hacerSegunPulsasActionPerformed(evt);
            }
        });

        bt_resolver.setText("Resolver cubo");
        bt_resolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_resolverActionPerformed(evt);
            }
        });

        bt_limpiarMovs.setText("Borrar todo");
        bt_limpiarMovs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_limpiarMovsActionPerformed(evt);
            }
        });

        bt_realizarMovs.setText("Realizar movimientos");
        bt_realizarMovs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_realizarMovsActionPerformed(evt);
            }
        });

        bt_deshacer.setText("Gen. Random");
        bt_deshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_deshacerActionPerformed(evt);
            }
        });

        bt_soltar.setText("Soltar ");
        bt_soltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_soltarActionPerformed(evt);
            }
        });

        sp_deshacer.setValue(25);

        lb_numMovs.setText("Num.movimientos:");

        lb_movs.setText("0");

        bl_borrarUltimoMove.setText("-1");
        bl_borrarUltimoMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bl_borrarUltimoMoveActionPerformed(evt);
            }
        });

        ta_movimientos.setColumns(20);
        ta_movimientos.setRows(5);
        sp_movimientos.setViewportView(ta_movimientos);

        lb_txtPort.setText("Puerto:");

        lb_port.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lb_port.setForeground(new java.awt.Color(0, 102, 255));
        lb_port.setText("port");

        cb_soloAlg.setText("Solo algoritmo");

        bt_parar.setText("Parar");
        bt_parar.setEnabled(false);
        bt_parar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_pararActionPerformed(evt);
            }
        });

        cb_luz.setText("Luz");
        cb_luz.setToolTipText("");
        cb_luz.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cb_luz.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cb_luz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_luzActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sep_bajo)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_resolver)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lb_R, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_L, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_U, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(lb_D, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lb_F, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_B, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lb_RD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_LD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_UD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(lb_DD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lb_FD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cb_hacerSegunPulsas)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(sp_movimientos)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(bl_borrarUltimoMove)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(bt_limpiarMovs))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(cb_soloAlg)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                                                .addComponent(sp_deshacer, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(bt_deshacer)
                                                .addGap(84, 84, 84)
                                                .addComponent(lb_txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lb_port)
                                                .addGap(83, 83, 83)
                                                .addComponent(bt_realizarMovs)))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lb_txt_tiempo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lb_tiempo))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lb_numMovs)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_movs, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pb_progreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_soltar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_luz)
                                    .addComponent(bt_parar)))
                            .addComponent(sep_arriba, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(bt_soltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bt_parar)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pb_progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_luz, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_tiempo)
                            .addComponent(lb_txt_tiempo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_numMovs)
                            .addComponent(lb_movs))
                        .addGap(12, 12, 12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep_arriba, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_L)
                    .addComponent(lb_R)
                    .addComponent(lb_U)
                    .addComponent(lb_D)
                    .addComponent(lb_B)
                    .addComponent(lb_F))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lb_RD)
                        .addComponent(lb_BD)
                        .addComponent(lb_FD)
                        .addComponent(lb_UD)
                        .addComponent(lb_LD))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb_DD)
                        .addGap(5, 5, 5)))
                .addComponent(sep_bajo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_hacerSegunPulsas)
                            .addComponent(bt_limpiarMovs)
                            .addComponent(bl_borrarUltimoMove)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sp_movimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bt_resolver)
                        .addComponent(cb_soloAlg))
                    .addComponent(bt_realizarMovs)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_port)
                        .addComponent(lb_txtPort))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sp_deshacer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_deshacer)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
//        _____                          _                 
//       | ____| __   __   ___   _ __   | |_    ___    ___ 
//       |  _|   \ \ / /  / _ \ | '_ \  | __|  / _ \  / __|
//       | |___   \ V /  |  __/ | | | | | |_  | (_) | \__ \
//       |_____|   \_/    \___| |_| |_|  \__|  \___/  |___/
    
    //<editor-fold defaultstate="collapsed" desc="BOTONES DE MOVIMIENTO">
    /**
     * \defgroup Eventos
     * \ingroup VistaPrincipal
     *  @{
     */
    
    /**
     * \defgroup Botones
     * \ingroup Eventos
     * Botones de los movimientos.
     * ![Ejemplo boton de movimiento](DoxyRecursos/images/R.png)
     *@{
     */
    private void lb_RMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_RMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("R");
    }//GEN-LAST:event_lb_RMouseClicked

    private void lb_LMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_LMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("L");
    }//GEN-LAST:event_lb_LMouseClicked

    private void lb_UMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_UMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("U");
    }//GEN-LAST:event_lb_UMouseClicked

    private void lb_DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_DMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("D");
    }//GEN-LAST:event_lb_DMouseClicked

    private void lb_FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_FMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("F");
    }//GEN-LAST:event_lb_FMouseClicked

    private void lb_BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_BMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("B");
    }//GEN-LAST:event_lb_BMouseClicked

    private void lb_RDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_RDMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("RD");
    }//GEN-LAST:event_lb_RDMouseClicked

    private void lb_LDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_LDMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("LD");
    }//GEN-LAST:event_lb_LDMouseClicked

    private void lb_UDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_UDMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("UD");
    }//GEN-LAST:event_lb_UDMouseClicked

    private void lb_DDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_DDMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("DD");
    }//GEN-LAST:event_lb_DDMouseClicked

    private void lb_FDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_FDMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("FD");
    }//GEN-LAST:event_lb_FDMouseClicked

    private void lb_BDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_BDMouseClicked
        if(((JLabel)evt.getSource()).isEnabled())botonMovimiento("BD");
    }//GEN-LAST:event_lb_BDMouseClicked

    private void bt_soltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_soltarActionPerformed
        if(cuboController.isAgarrado())cuboController.doMove("E",false);
        cuboController.setAgarrado(false);
    }//GEN-LAST:event_bt_soltarActionPerformed
    /**@} */
//</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="OTROS EVENTOS">
    /**
     * \defgroup Otros
     * \ingroup Eventos
     * @{
     */
    private void bt_limpiarMovsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_limpiarMovsActionPerformed
        resetMoves();
    }//GEN-LAST:event_bt_limpiarMovsActionPerformed

    private void bl_borrarUltimoMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bl_borrarUltimoMoveActionPerformed
      removeLastMove();
    }//GEN-LAST:event_bl_borrarUltimoMoveActionPerformed

    private void bt_deshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_deshacerActionPerformed
        int movsDeshacer = (int)this.sp_deshacer.getValue();
        for (int i = 0; i < movsDeshacer; i++) {
            addMove(posiblesMovs[rand.nextInt(posiblesMovs.length -1)]);
        }
    }//GEN-LAST:event_bt_deshacerActionPerformed

    private void bt_resolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_resolverActionPerformed
        resetMoves();
        resolverCubo();
//desableButtons(false);
    }//GEN-LAST:event_bt_resolverActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            cuboController.getArduino().killArduinoConnection();
            clearPhotos();
            this.wc.close();
        } catch (ArduinoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void bt_realizarMovsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_realizarMovsActionPerformed
        bt_parar.setEnabled(true);
        cuboController.doAllMovs();
    }//GEN-LAST:event_bt_realizarMovsActionPerformed

    private void cb_hacerSegunPulsasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_hacerSegunPulsasActionPerformed
        if (this.cuboController.isAgarrado()){
            cuboController.doMove("E", false);
        }else{
            cuboController.doMove("S", false);
        }
        resetMoves();
    }//GEN-LAST:event_cb_hacerSegunPulsasActionPerformed

    private void bt_pararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_pararActionPerformed
        bt_parar.setEnabled(false);
        cuboController.endAllMovs();
    }//GEN-LAST:event_bt_pararActionPerformed

    private void cb_luzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_luzActionPerformed
        if(this.cb_luz.isSelected()){
            encenderLuz();
        }else{
            apagarLuz();
        }
    }//GEN-LAST:event_cb_luzActionPerformed
    /**@} */
    /**@} */
    //</editor-fold>
    
//*********************************** FIN EVENTOS ********************************************
    
    
    
    //<editor-fold defaultstate="collapsed" desc="MAIN">
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        new MainFrame().setVisible(true);

    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="COMPONENTES">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bl_borrarUltimoMove;
    private javax.swing.JButton bt_deshacer;
    private javax.swing.JButton bt_limpiarMovs;
    private javax.swing.JButton bt_parar;
    private javax.swing.JButton bt_realizarMovs;
    private javax.swing.JButton bt_resolver;
    private javax.swing.JButton bt_soltar;
    private javax.swing.JCheckBox cb_hacerSegunPulsas;
    private javax.swing.JCheckBox cb_luz;
    private javax.swing.JCheckBox cb_soloAlg;
    private javax.swing.JLabel lb_B;
    private javax.swing.JLabel lb_BD;
    private javax.swing.JLabel lb_D;
    private javax.swing.JLabel lb_DD;
    private javax.swing.JLabel lb_F;
    private javax.swing.JLabel lb_FD;
    private javax.swing.JLabel lb_L;
    private javax.swing.JLabel lb_LD;
    private javax.swing.JLabel lb_R;
    private javax.swing.JLabel lb_RD;
    private javax.swing.JLabel lb_U;
    private javax.swing.JLabel lb_UD;
    private javax.swing.JLabel lb_movs;
    private javax.swing.JLabel lb_numMovs;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_tiempo;
    private javax.swing.JLabel lb_txtPort;
    private javax.swing.JLabel lb_txt_tiempo;
    private javax.swing.JProgressBar pb_progreso;
    private javax.swing.JSeparator sep_arriba;
    private javax.swing.JSeparator sep_bajo;
    private javax.swing.JSpinner sp_deshacer;
    private javax.swing.JScrollPane sp_movimientos;
    private javax.swing.JTextArea ta_movimientos;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GETTERS Y SETTERS">
   /**
    * \defgroup Getters_Y_Setters
    * \ingroup Metodos
    * @{
    */
    
    public ArrayList<String> getMovimientos() {
        return movimientos;
    }
    
    public int getNumMovimientos() {
        return numMovimientos;
    }
    
    public GregorianCalendar getTiempo() {
        return tiempo;
    }
    
    public JLabel getLb_movs() {
        return lb_movs;
    }
    
    public JLabel getLb_numMovs() {
        return lb_numMovs;
    }
    
    public JLabel getLb_tiempo() {
        return lb_tiempo;
    }
    
    public JProgressBar getPb_progreso() {
        return pb_progreso;
    }
    
    public JTextArea getTa_movimientos() {
        return ta_movimientos;
    }
    
    public JLabel getLb_port() {
        return lb_port;
    }

    public JCheckBox getCb_soloAlg() {
        return cb_soloAlg;
    }

    public JButton getBt_parar() {
        return bt_parar;
}

    public boolean isLuz_encendida() {
        return luz_encendida;
    }
    
    
    /**
     * @}
     */
    //</editor-fold>
 

}


/**
 * \defgroup 
 */

