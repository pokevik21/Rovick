package rovick;

import javax.swing.ImageIcon;


public class MainFrame extends javax.swing.JFrame {


    public MainFrame() {
        initComponents();
        confImages();
    }

    private void confImages() {
        lb_RD.setIcon(new ImageIcon("./images/RD.png"));
    }
    
    
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
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lb_RD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/RD.png"))); // NOI18N

        lb_R.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/R.png"))); // NOI18N

        lb_U.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/U.png"))); // NOI18N

        lb_L.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/L.png"))); // NOI18N

        lb_F.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/F.png"))); // NOI18N

        lb_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/D.png"))); // NOI18N

        lb_B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/B.png"))); // NOI18N

        lb_UD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/UD.png"))); // NOI18N

        lb_LD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LD.png"))); // NOI18N

        lb_FD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/FD.png"))); // NOI18N

        lb_BD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/BD.png"))); // NOI18N

        lb_DD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DD.png"))); // NOI18N

        lb_txt_tiempo.setText("Tiempo aproximado:");

        lb_tiempo.setText("00:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sep_bajo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb_RD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_LD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_UD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_DD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_FD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_BD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(lb_B, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lb_txt_tiempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_tiempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(sep_arriba, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_txt_tiempo)
                        .addComponent(lb_tiempo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sep_arriba, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_L)
                    .addComponent(lb_R)
                    .addComponent(lb_U)
                    .addComponent(lb_D)
                    .addComponent(lb_F)
                    .addComponent(lb_B))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_RD)
                            .addComponent(lb_BD)
                            .addComponent(lb_FD)
                            .addComponent(lb_DD)
                            .addComponent(lb_UD)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_LD)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sep_bajo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
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
    private javax.swing.JLabel lb_tiempo;
    private javax.swing.JLabel lb_txt_tiempo;
    private javax.swing.JSeparator sep_arriba;
    private javax.swing.JSeparator sep_bajo;
    // End of variables declaration//GEN-END:variables


}
