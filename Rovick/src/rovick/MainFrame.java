package rovick;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainFrame extends javax.swing.JFrame {
    
    
//        __     __                 _           _       _              
//        \ \   / /   __ _   _ __  (_)   __ _  | |__   | |   ___   ___ 
//         \ \ / /   / _` | | '__| | |  / _` | | '_ \  | |  / _ \ / __|
//          \ V /   | (_| | | |    | | | (_| | | |_) | | | |  __/ \__ \
//           \_/     \__,_| |_|    |_|  \__,_| |_.__/  |_|  \___| |___/
    
    private ArrayList<String> Movimientos = new ArrayList();
    private int numMovimientos = 0;
    private Date date = null;
    private GregorianCalendar tiempo = null;
    private SimpleDateFormat sdf = null;
    //*********************************** FIN VARIABLES *********************************************
    
    
//        __  __          _                 _               
//       |  \/  |   ___  | |_    ___     __| |   ___    ___ 
//       | |\/| |  / _ \ | __|  / _ \   / _` |  / _ \  / __|
//       | |  | | |  __/ | |_  | (_) | | (_| | | (_) | \__ \
//       |_|  |_|  \___|  \__|  \___/   \__,_|  \___/  |___/

    public MainFrame() {
        initComponents();
        confInicial();
    }
    
    private void confInicial(){
        setLocationRelativeTo(null);
        this.setTitle("Rovick - resolvedor de cubos de rubick");
        setResizable(false);
        this.tf_movimientos.setEditable(false);
        this.date = new Date(0);
        this.tiempo = new GregorianCalendar();
        tiempo.setTime(date);
        this.sdf = new SimpleDateFormat("mm'min' ss'seg'");
    }
    
    private void resetMoves(){
        this.tf_movimientos.setText("");
        Movimientos.clear();
    }
    
    private String sumarNumeroMove(String Move){
        String resultado="";
        if(Utiles.tieneNumero(Move)){
            int numMove = Utiles.extraerNumero(Move)+1;
            resultado = Move.substring(0,Utiles.primerNum(Move)) + numMove;
        }else{
            resultado = Move+"2";
        }
        return resultado;
    }
    
    private void imprimirMovimientos(){
       this.tf_movimientos.setText("");
       for (int i = 0; i < Movimientos.size(); i++) {
            String Movimiento = Movimientos.get(i);
            if(Movimiento.length()>1 && Movimiento.charAt(1) == 'D'){
                String cadena = "";
                for (int j = 0; j < Movimiento.length(); j++) {
                    char caracter = Movimiento.charAt(j);
                    if(j == 1) caracter='\'';
                    cadena += caracter;
                }
                Movimiento = cadena;
            }
            this.tf_movimientos.setText(this.tf_movimientos.getText()+Movimiento);
            if (i != Movimientos.size()-1) this.tf_movimientos.setText(this.tf_movimientos.getText()+" - ");
        }
    }
    
    private void addTime(String tipoMove){
        int seg = 0;
        switch (tipoMove) {
            case "F":
            case "FD":
            case "B":
            case "BD":
                seg = 7;
                break;
            default:
                seg = 2;
        }
        tiempo.add(GregorianCalendar.SECOND, seg);
       /// this.lb_tiempo
    }
    
    private void addMove(String Move){
        if (Movimientos.isEmpty()){
            Movimientos.add(Move);
        }else{
            int ultPos = Movimientos.size()-1;
            String ultimoMove = Movimientos.get(ultPos);
            
            String ultimoMoveSinNum = ultimoMove;
            if (Utiles.tieneNumero(ultimoMove)){
                ultimoMoveSinNum=ultimoMove.substring(0,Utiles.primerNum(ultimoMove));
            }
            
            if (ultimoMoveSinNum.equals(Move)){
                Movimientos.set(ultPos, sumarNumeroMove(ultimoMove));
            }else{
                Movimientos.add(Move);
            }
        }
        imprimirMovimientos();
        this.numMovimientos++;
        this.lb_numMovs.setText(String.valueOf(this.numMovimientos));
        addTime(Move);
    }
    
    private void botonMovimiento(String mov){
        if(!this.cb_hacerSegunPulsas.isSelected()){
            addMove(mov);
            
        }else{
            
        }
    }
    
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
        tf_movimientos = new javax.swing.JTextField();
        cb_hacerSegunPulsas = new javax.swing.JCheckBox();
        lb_movimientos = new javax.swing.JLabel();
        bt_resolver = new javax.swing.JButton();
        bt_limpiarMovs = new javax.swing.JButton();
        bt_realizarMovs = new javax.swing.JButton();
        lb_estado = new javax.swing.JLabel();
        bt_deshacer = new javax.swing.JButton();
        bt_soltar = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        lb_movsAlDeshacer = new javax.swing.JLabel();
        lb_numMovs = new javax.swing.JLabel();
        lb_movs = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        lb_movimientos.setText("Movimientos:");

        bt_resolver.setText("Resolver cubo");

        bt_limpiarMovs.setText("Borrar ordenes");
        bt_limpiarMovs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_limpiarMovsActionPerformed(evt);
            }
        });

        bt_realizarMovs.setText("Realizar movimientos");

        bt_deshacer.setText("Deshacer Cubo");

        bt_soltar.setText("Soltar cubo");
        bt_soltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_soltarActionPerformed(evt);
            }
        });

        jSpinner1.setValue(25);

        lb_movsAlDeshacer.setText(":Movimientos al deshacer");

        lb_numMovs.setText("Num.movimientos:");

        lb_movs.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sep_bajo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_estado))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_R, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_L, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_U, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_D, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_F, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_B, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_RD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_LD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_UD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_DD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_FD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cb_hacerSegunPulsas))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_resolver)
                        .addGap(12, 12, 12)
                        .addComponent(bt_deshacer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_movsAlDeshacer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_realizarMovs))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lb_movimientos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_movimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_limpiarMovs))
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
                        .addComponent(bt_soltar)))
                .addContainerGap())
            .addComponent(sep_arriba)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(424, 424, 424)
                        .addComponent(lb_estado))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_L)
                            .addComponent(lb_R)
                            .addComponent(lb_U)
                            .addComponent(lb_D)
                            .addComponent(lb_F)
                            .addComponent(lb_B))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_movimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_hacerSegunPulsas)
                            .addComponent(lb_movimientos)
                            .addComponent(bt_limpiarMovs))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt_realizarMovs)
                            .addComponent(bt_resolver)
                            .addComponent(bt_deshacer)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_movsAlDeshacer))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
//        _____                          _                 
//       | ____| __   __   ___   _ __   | |_    ___    ___ 
//       |  _|   \ \ / /  / _ \ | '_ \  | __|  / _ \  / __|
//       | |___   \ V /  |  __/ | | | | | |_  | (_) | \__ \
//       |_____|   \_/    \___| |_| |_|  \__|  \___/  |___/
    
    //<editor-fold defaultstate="collapsed" desc="BOTONES">
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
        botonMovimiento("E");
    }//GEN-LAST:event_bt_soltarActionPerformed
//</editor-fold>
    
    private void bt_limpiarMovsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_limpiarMovsActionPerformed
        resetMoves();
    }//GEN-LAST:event_bt_limpiarMovsActionPerformed

    
    
    
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
    private javax.swing.JButton bt_deshacer;
    private javax.swing.JButton bt_limpiarMovs;
    private javax.swing.JButton bt_realizarMovs;
    private javax.swing.JButton bt_resolver;
    private javax.swing.JButton bt_soltar;
    private javax.swing.JCheckBox cb_hacerSegunPulsas;
    private javax.swing.JSpinner jSpinner1;
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
    private javax.swing.JLabel lb_estado;
    private javax.swing.JLabel lb_movimientos;
    private javax.swing.JLabel lb_movs;
    private javax.swing.JLabel lb_movsAlDeshacer;
    private javax.swing.JLabel lb_numMovs;
    private javax.swing.JLabel lb_tiempo;
    private javax.swing.JLabel lb_txt_tiempo;
    private javax.swing.JProgressBar pb_progreso;
    private javax.swing.JSeparator sep_arriba;
    private javax.swing.JSeparator sep_bajo;
    private javax.swing.JTextField tf_movimientos;
    // End of variables declaration//GEN-END:variables
//</editor-fold>

}
