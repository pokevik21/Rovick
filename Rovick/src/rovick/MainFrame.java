package rovick;

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
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import rovick.cube.CubeController;

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

    private ArrayList<String> movimientos = null;
    private int numMovimientos = 0;
    private Date date = null;
    private GregorianCalendar tiempo = null;
    private SimpleDateFormat sdf = null;
    private String[] posiblesMovs = {"R","RD","L","LD","U","UD","D","DD","F","FD","B","BD"};
    private Random rand = null;
    private CubeController cuboController = null;
    private WebCamController wc = null;
    private boolean flagFinish = false;
    
    
//*********************************** FIN VARIABLES *********************************************
    
    
//        __  __          _                 _               
//       |  \/  |   ___  | |_    ___     __| |   ___    ___ 
//       | |\/| |  / _ \ | __|  / _ \   / _` |  / _ \  / __|
//       | |  | | |  __/ | |_  | (_) | | (_| | | (_) | \__ \
//       |_|  |_|  \___|  \__|  \___/   \__,_|  \___/  |___/

    //<editor-fold defaultstate="collapsed" desc="AUXILIARES">

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
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="IMPORTANTES">
    
    public MainFrame() {
        initComponents();
        confInicial();
    }
    
    private void confInicial(){
        setLocationRelativeTo(null);
        this.setTitle("Rovick - resolvedor de cubos de rubick hecho por Victor Pastor Urueña");
        setResizable(false);
        movimientos = new ArrayList();
        rand = new Random();
        this.ta_movimientos.setEditable(false);
        this.date = new Date(0);
        this.tiempo = new GregorianCalendar();
        tiempo.setTime(date);
        this.sdf = new SimpleDateFormat("mm' min' ss' seg'");
        cuboController = new CubeController(this);
        this.wc = new WebCamController();
        this.setIconImage(Toolkit.getDefaultToolkit().
         getImage(ClassLoader.getSystemResource("images/cuboIco.png")));
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
            cuboController.doMove(mov,false);
        }
    }
    
    /**
     * Metodo que genera los movimientos necesarios para resolver el cubo.
     */
    private void resolverCubo(){
        
    }
    
//</editor-fold>

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
        lb_movimientos = new javax.swing.JLabel();
        bt_resolver = new javax.swing.JButton();
        bt_limpiarMovs = new javax.swing.JButton();
        bt_realizarMovs = new javax.swing.JButton();
        bt_deshacer = new javax.swing.JButton();
        bt_soltar = new javax.swing.JButton();
        sp_deshacer = new javax.swing.JSpinner();
        lb_movsAlDeshacer = new javax.swing.JLabel();
        lb_numMovs = new javax.swing.JLabel();
        lb_movs = new javax.swing.JLabel();
        bl_borrarUltimoMove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_movimientos = new javax.swing.JTextArea();
        lb_txtPort = new javax.swing.JLabel();
        lb_port = new javax.swing.JLabel();

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

        cb_hacerSegunPulsas.setText("Hacer según pulsas");
        cb_hacerSegunPulsas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_hacerSegunPulsasActionPerformed(evt);
            }
        });

        lb_movimientos.setText("Movimientos:");

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

        bt_deshacer.setText("Movimientos aleatorios");
        bt_deshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_deshacerActionPerformed(evt);
            }
        });

        bt_soltar.setText("Soltar cubo");
        bt_soltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_soltarActionPerformed(evt);
            }
        });

        sp_deshacer.setValue(25);

        lb_movsAlDeshacer.setText(":Numero de aletorios");

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
        jScrollPane1.setViewportView(ta_movimientos);

        lb_txtPort.setText("Puerto:");

        lb_port.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lb_port.setForeground(new java.awt.Color(0, 102, 255));
        lb_port.setText("port");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sep_bajo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bt_resolver)
                                .addGap(12, 12, 12)
                                .addComponent(bt_deshacer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp_deshacer, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_movsAlDeshacer)
                                .addGap(55, 55, 55)
                                .addComponent(lb_txtPort)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_port)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bt_realizarMovs)))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lb_txt_tiempo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lb_tiempo))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lb_numMovs)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lb_movs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(pb_progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_soltar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bl_borrarUltimoMove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_limpiarMovs))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cb_hacerSegunPulsas)
                                .addGap(18, 18, 18)
                                .addComponent(lb_movimientos))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_UD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lb_DD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_FD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lb_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(sep_arriba)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(bt_soltar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pb_progreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_txt_tiempo)
                            .addComponent(lb_tiempo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_numMovs)
                            .addComponent(lb_movs))))
                .addGap(18, 18, 18)
                .addComponent(sep_arriba, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_L)
                    .addComponent(lb_R)
                    .addComponent(lb_U)
                    .addComponent(lb_D)
                    .addComponent(lb_B)
                    .addComponent(lb_F))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_RD)
                    .addComponent(lb_BD)
                    .addComponent(lb_FD)
                    .addComponent(lb_DD)
                    .addComponent(lb_UD)
                    .addComponent(lb_LD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep_bajo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_hacerSegunPulsas)
                            .addComponent(lb_movimientos)
                            .addComponent(bt_limpiarMovs)
                            .addComponent(bl_borrarUltimoMove)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_realizarMovs)
                    .addComponent(bt_resolver)
                    .addComponent(bt_deshacer)
                    .addComponent(sp_deshacer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_movsAlDeshacer)
                    .addComponent(lb_txtPort)
                    .addComponent(lb_port))
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
    private void lb_RMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_RMouseClicked
        botonMovimiento("R");
    }//GEN-LAST:event_lb_RMouseClicked

    private void lb_LMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_LMouseClicked
        botonMovimiento("L");
    }//GEN-LAST:event_lb_LMouseClicked

    private void lb_UMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_UMouseClicked
        botonMovimiento("U");
    }//GEN-LAST:event_lb_UMouseClicked

    private void lb_DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_DMouseClicked
        botonMovimiento("D");
    }//GEN-LAST:event_lb_DMouseClicked

    private void lb_FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_FMouseClicked
        botonMovimiento("F");
    }//GEN-LAST:event_lb_FMouseClicked

    private void lb_BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_BMouseClicked
        botonMovimiento("B");
    }//GEN-LAST:event_lb_BMouseClicked

    private void lb_RDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_RDMouseClicked
        botonMovimiento("RD");
    }//GEN-LAST:event_lb_RDMouseClicked

    private void lb_LDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_LDMouseClicked
        botonMovimiento("LD");
    }//GEN-LAST:event_lb_LDMouseClicked

    private void lb_UDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_UDMouseClicked
        botonMovimiento("UD");
    }//GEN-LAST:event_lb_UDMouseClicked

    private void lb_DDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_DDMouseClicked
        botonMovimiento("DD");
    }//GEN-LAST:event_lb_DDMouseClicked

    private void lb_FDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_FDMouseClicked
        botonMovimiento("FD");
    }//GEN-LAST:event_lb_FDMouseClicked

    private void lb_BDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_BDMouseClicked
        botonMovimiento("BD");
    }//GEN-LAST:event_lb_BDMouseClicked

    private void bt_soltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_soltarActionPerformed
        if(cuboController.isAgarrado())cuboController.doMove("E",false);
        cuboController.setAgarrado(false);
    }//GEN-LAST:event_bt_soltarActionPerformed
//</editor-fold>
    
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
        wc.cleeanPhotos(true);
        wc.takePhoto("prueba");
//desableButtons(false);
    }//GEN-LAST:event_bt_resolverActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            cuboController.getArduino().killArduinoConnection();
            this.wc.cleeanPhotos(false);
            this.wc.close();
        } catch (ArduinoException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void bt_realizarMovsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_realizarMovsActionPerformed
        cuboController.doAllMovs();
    }//GEN-LAST:event_bt_realizarMovsActionPerformed

    private void cb_hacerSegunPulsasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_hacerSegunPulsasActionPerformed
        if (this.cuboController.isAgarrado()){
            cuboController.doMove("E", false);
        }else{
            
        }
        resetMoves();
    }//GEN-LAST:event_cb_hacerSegunPulsasActionPerformed

    //*********************************** FIN EVENTOS ********************************************
    
    
    
    
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


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    
    //<editor-fold defaultstate="collapsed" desc="Componentes">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bl_borrarUltimoMove;
    private javax.swing.JButton bt_deshacer;
    private javax.swing.JButton bt_limpiarMovs;
    private javax.swing.JButton bt_realizarMovs;
    private javax.swing.JButton bt_resolver;
    private javax.swing.JButton bt_soltar;
    private javax.swing.JCheckBox cb_hacerSegunPulsas;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JLabel lb_movimientos;
    private javax.swing.JLabel lb_movs;
    private javax.swing.JLabel lb_movsAlDeshacer;
    private javax.swing.JLabel lb_numMovs;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_tiempo;
    private javax.swing.JLabel lb_txtPort;
    private javax.swing.JLabel lb_txt_tiempo;
    private javax.swing.JProgressBar pb_progreso;
    private javax.swing.JSeparator sep_arriba;
    private javax.swing.JSeparator sep_bajo;
    private javax.swing.JSpinner sp_deshacer;
    private javax.swing.JTextArea ta_movimientos;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc="Get y set">
   
    public ArrayList<String> getMovimientos() {
        return movimientos;
    }
    
    public int getNumMovimientos() {
        return numMovimientos;
    }
    
    public GregorianCalendar getTiempo() {
        return tiempo;
    }
    
    public JLabel getLb_movimientos() {
        return lb_movimientos;
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
//</editor-fold>
    
//</editor-fold>

       
}
