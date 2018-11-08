package rovick.Utils;

import com.sun.awt.AWTUtilities;
import java.awt.Toolkit;
import javax.swing.JLabel;

/**
 * Pantalla de carga que aparece durante la configuración de la ventana principal.
 * @author Victor Pastor Urueña
 */
public class PantallaDeCarga extends javax.swing.JFrame {
    
    private String texto_carga = "";
    
    public PantallaDeCarga() {
        initComponents();
        confPantalla();
    }

    private void confPantalla(){
        setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(this, false);
        this.setIconImage(Toolkit.getDefaultToolkit().
        getImage(ClassLoader.getSystemResource("images/cuboIco.png")));
    }

    /**
     * Metodo que se utiliza para cambiar el texto informativo
     * @param texto El texto informativo
     */
    public void cambiarTexto(String texto){
        this.lb_texto.setText(texto);
    }
    
    /**
     * Cierra esta pantalla
     */
    public void close(){
        this.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_texto = new javax.swing.JLabel();
        image_fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_texto.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lb_texto.setForeground(new java.awt.Color(0, 0, 0));
        lb_texto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_texto.setText("Conf. vista");
        lb_texto.setToolTipText("");
        lb_texto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(lb_texto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, -1, -1));

        image_fondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        image_fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Img_pantallaCarga.png"))); // NOI18N
        getContentPane().add(image_fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 380, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(PantallaDeCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaDeCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaDeCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaDeCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaDeCarga().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel image_fondo;
    private javax.swing.JLabel lb_texto;
    // End of variables declaration//GEN-END:variables

    public String getTexto_carga() {
        return texto_carga;
    }

    public void setTexto_carga(String texto_carga) {
        this.texto_carga = texto_carga;
    }  

    public JLabel getLb_texto() {
        return lb_texto;
    }
  
    
    
}
