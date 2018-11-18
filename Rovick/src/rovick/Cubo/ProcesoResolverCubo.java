package rovick.Cubo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import rovick.MainFrame;
import rovick.Utils.WebCamController;

/**
 *Proceso que se encarga de resolver el cubo.
 * @author Victor Pastor Urueña
 */
public class ProcesoResolverCubo extends Thread{
    
    static int POS_X_LEFT = 85;
    static int POS_X_CENTER = 269;
    static int POS_X_RIGHT = 514;
    
    static int POS_Y_UP = 45;
    static int POS_Y_MID = 240;
    static int POS_Y_BOT = 450;
    
    private Color c1 = null;
    private Color c2 = null;
    private Color c3 = null;
    private Color c4 = null;
    private Color c5 = null;
    
    
    private MainFrame vistaPrincipal = null;
    private WebCamController camara = null;
    private CubeController cubo = null;

    
    public ProcesoResolverCubo(MainFrame vistaPrincipal, WebCamController camara, CubeController cubo) {
        this.vistaPrincipal = vistaPrincipal;
        this.camara = camara;
        this.cubo = cubo;

    }

    
    public void hacerPaso(String move,String foto,long seg) throws InterruptedException{
            cubo.doMove(move, false, true);
            sleep(seg + 300);
            if(!foto.isEmpty())camara.takePhoto(foto);
    }
    
    private boolean inColor(Color comp, Color c){
        int R = c.getRed();
        int G = c.getGreen();
        int B = c.getRed();
        int cR = comp.getRed();
        int cG = comp.getGreen();
        int cB = comp.getRed();
        
        int range = 15;
        
        if(   (R >= (cR-range) && R<=(cR+range))
           && (G >= (cG-range) && G<=(cG+range))  
           && (B >= (cB-range) && B<=(cB+range))){
            return true;
        } 
        return false;
    }
    
    private int sacarColor(int RGB){
        Color c = new Color(RGB);
        if(inColor(c1, c)){
            return 1;
        }else if(inColor(c2, c)){
            return 2;
        }else if(inColor(c3, c)){
            return 3;
        }else if(inColor(c4, c)){
            return 4;
        }else if(inColor(c5, c)){
            return 5;
        }
    return 0;    
    }   
    
    private int[] analizarCara(BufferedImage image){
        int[] colores = new int[9]; 
        colores[0] = sacarColor(image.getRGB(POS_X_LEFT  ,POS_Y_UP  ));
        colores[1] = sacarColor(image.getRGB(POS_X_CENTER  ,POS_Y_UP ));
        colores[2] = sacarColor(image.getRGB(POS_X_RIGHT  ,POS_Y_UP ));
        colores[3] = sacarColor(image.getRGB(POS_X_LEFT,POS_Y_UP  ));
        colores[4] = sacarColor(image.getRGB(POS_X_CENTER,POS_Y_MID ));
        colores[5] = sacarColor(image.getRGB(POS_X_RIGHT,POS_Y_BOT ));
        colores[6] = sacarColor(image.getRGB(POS_X_LEFT ,POS_Y_UP  ));
        colores[7] = sacarColor(image.getRGB(POS_X_CENTER ,POS_Y_MID ));
        colores[8] = sacarColor(image.getRGB(POS_X_RIGHT ,POS_Y_BOT ));
        return colores;
    }
    
    private int[] analizarCara(BufferedImage image1, BufferedImage image2){
        int[] colores = new int[9]; 
        colores[0] = sacarColor(image1.getRGB(POS_X_LEFT  ,POS_Y_UP  ));
        colores[1] = sacarColor(image1.getRGB(POS_X_CENTER  ,POS_Y_UP ));
        colores[2] = sacarColor(image1.getRGB(POS_X_RIGHT  ,POS_Y_UP ));
        colores[3] = sacarColor(image2.getRGB(POS_X_LEFT,POS_Y_UP  ));
        colores[4] = sacarColor(image1.getRGB(POS_X_CENTER,POS_Y_MID ));
        colores[5] = sacarColor(image2.getRGB(POS_X_RIGHT,POS_Y_BOT ));
        colores[6] = sacarColor(image1.getRGB(POS_X_LEFT ,POS_Y_UP  ));
        colores[7] = sacarColor(image1.getRGB(POS_X_CENTER ,POS_Y_MID ));
        colores[8] = sacarColor(image1.getRGB(POS_X_RIGHT ,POS_Y_BOT ));
        return colores;
    }
    
    @Override
    public void run() {
        vistaPrincipal.desableButtons(false);
        vistaPrincipal.getBt_parar().setEnabled(true);
        camara.cleeanPhotos();
        
        //Hacer movimientos y fotos
       /* try {
            hacerPaso("1", "5_1", 2500);
            hacerPaso("2", "5_2", 1000);
            hacerPaso("3", "4", 1000);
            hacerPaso("4", "2", 1000);
            hacerPaso("5", "1", 2500);
            hacerPaso("6", "6", 1000);
            hacerPaso("7", "3_1", 6500);
            hacerPaso("8", "3_2", 1000);
            hacerPaso("9", "", 6000);
        } catch (Exception e) {
            System.err.println("Interrumpido");
        }*/
        
        ImageInputStream iis = null;

        try {
            BufferedImage image_1 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/1.png")));
            BufferedImage image_2 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/2.png")));
            BufferedImage image_3_1 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/3_1.png")));
            BufferedImage image_3_2 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/3_2.png")));
            BufferedImage image_4 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/4.png")));
            BufferedImage image_5_1 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/5_1.png")));
            BufferedImage image_5_2 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/5_2.png")));
            BufferedImage image_6 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/6.png")));
            
            c1 = new Color(image_2.getRGB(POS_X_CENTER, POS_Y_MID));
            c2 = new Color(image_3_1.getRGB(POS_X_CENTER, POS_Y_MID));
            c3 = new Color(image_4.getRGB(POS_X_CENTER, POS_Y_MID));
            c4 = new Color(image_5_1.getRGB(POS_X_CENTER, POS_Y_MID));
            c5 = new Color(image_6.getRGB(POS_X_CENTER, POS_Y_MID));
            
            
            int[] top = analizarCara(image_3_1,image_3_2);
            
            System.out.println(Arrays.toString(top));
            
        } catch (IOException ex) {
            Logger.getLogger(ProcesoResolverCubo.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        //cubo.doMove("E", false);
        vistaPrincipal.desableButtons(true);
    }
    
    
    
}
