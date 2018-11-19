package rovick.Cubo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    
    /*private int[] c1 = null;
    private int[] c2 = null;
    private int[] c3 = null;
    private int[] c4 = null;
    private int[] c5 = null;*/
    
    private ArrayList<float[]> centros;

    private BufferedImage image_1 = null;
    private BufferedImage image_2 = null;
    private BufferedImage image_3_1 = null;
    private BufferedImage image_3_2 = null;
    private BufferedImage image_4 = null;
    private BufferedImage image_5_1 = null;
    private BufferedImage image_5_2 = null;
    private BufferedImage image_6 = null;
    
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
    
    /*private boolean inColor(float h, Color c){
        int R = c.getRed();
        int G = c.getGreen();
        int B = c.getRed();
        float[] hsv = new float[3];
        Color.RGBtoHSB(R, G, B, hsv);
        System.out.println("**************************************");
        System.out.println("R:"+R+" ;G:"+G + "  ;B:"+B);
        System.out.println("**************************************");*/
        
       /* if(   (R >= (r[0]) && R<=(r[3]))
           && (G >= (r[1]) && G<=(r[4]))  
           && (B >= (r[2]) && B<=(r[5]))){
            return true;
        } */
        /*return false;
    }*/
    
    /*private boolean intensidadParecidas(float h1, float h2 ,int rango){
        return (h1>=h2-rango) && (h1<=h2+rango);
    }*/
    
    private float distancia(float[] c1, float[] c2) {
        float dR = (float) (c1[0]-c2[0]);
        //System.out.println("red:"+dR);
        float dG = (float) (c1[1]-c2[1]);
        //System.out.println("green:"+dG);
        float dB = (float) (c1[2]-c2[2]);
        //System.out.println("blue:"+dB);
        float resultado = (float) Math.sqrt(2*Math.pow(dR, 2) + 4*Math.pow(dG, 2) + 3*Math.pow(dB, 2)); 
        //System.out.println("resultado:"+ resultado);
        return resultado;
    }
    
    //2*dR*dR + 4*dG*dG + 3*dB*
    private int sacarColor(BufferedImage image, int pos_x, int pos_y){
        float[] color = avg(image, pos_x, pos_y, 10);
        
        int mejor_color = 0;
        float mejor_distancia = 99999;
        for (int i = 0; i < 6; i++) {
            
            float d = distancia(centros.get(i), color);
            if (d < mejor_distancia) {
                mejor_distancia = d;
                mejor_color = i;
            }
        }
        
       // System.out.println("intesidad:"+Arrays.toString(color) +"  ; mejor_distancia:"+mejor_distancia  +"  mejor:"+mejor_color);
        
    return mejor_color;    
    }   
    
    private int[] analizarCara(BufferedImage image){
        int[] colores = new int[9]; 
        colores[0] = sacarColor(image,POS_X_LEFT,POS_Y_UP);
        colores[1] = sacarColor(image,POS_X_CENTER,POS_Y_UP );
        colores[2] = sacarColor(image,POS_X_RIGHT,POS_Y_UP );
        
        colores[3] = sacarColor(image,POS_X_LEFT,POS_Y_MID  );
        colores[4] = sacarColor(image,POS_X_CENTER,POS_Y_MID );
        colores[5] = sacarColor(image,POS_X_RIGHT,POS_Y_MID);
        
        colores[6] = sacarColor(image,POS_X_LEFT,POS_Y_BOT  );
        colores[7] = sacarColor(image,POS_X_CENTER,POS_Y_BOT);
        colores[8] = sacarColor(image,POS_X_RIGHT,POS_Y_BOT );
        return colores;
    }
    
    private int[] analizarCara(BufferedImage image1, BufferedImage image2){
        int[] colores = new int[9]; 
        colores[0] = sacarColor(image1,POS_X_LEFT,POS_Y_UP);
        colores[1] = sacarColor(image1,POS_X_CENTER,POS_Y_UP );
        colores[2] = sacarColor(image1,POS_X_RIGHT,POS_Y_UP );
        
        colores[3] = sacarColor(image2,POS_X_LEFT,POS_Y_MID  );
        colores[4] = sacarColor(image1,POS_X_CENTER,POS_Y_MID );
        colores[5] = sacarColor(image2,POS_X_RIGHT,POS_Y_MID );
        
        colores[6] = sacarColor(image1,POS_X_LEFT,POS_Y_BOT  );
        colores[7] = sacarColor(image1,POS_X_CENTER,POS_Y_BOT);
        colores[8] = sacarColor(image1,POS_X_RIGHT,POS_Y_BOT );
        return colores;
    }
    
    /*private int[] getRangos(BufferedImage image, int coord_x , int coord_y){
        int[] rangos = new int[6];

        int maxR=0;
        int maxG=0;
        int maxB=0;

        int minR=0;
        int minG=0;
        int minB=0;

        boolean primero = false;
        int tam=50;
        
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                Color c_pos = new Color(image.getRGB(coord_x + j, coord_y +i));
                if (maxR < c_pos.getRed()) maxR = c_pos.getRed();
                if (maxG < c_pos.getGreen()) maxG = c_pos.getGreen();
                if (maxB < c_pos.getBlue()) maxB = c_pos.getBlue();

                if (!primero){
                    minR = c_pos.getRed();
                    minG = c_pos.getGreen();
                    minB = c_pos.getBlue();
                    primero = true;
                }else{
                    if (minR > c_pos.getRed()) minR = c_pos.getRed();
                    if (minG > c_pos.getGreen()) minG = c_pos.getGreen();
                    if (minB > c_pos.getBlue()) minB = c_pos.getBlue();    
                }

            }
        }
        //System.out.println("maximos--> R:"+maxR+" ; G:"+maxG+" ; B:"+maxB);
        //System.out.println("mínimos--> R:"+minR+" ; G:"+minG+" ; B:"+minB);
        rangos[0] = maxR;
        rangos[1] = maxG;
        rangos[2] = maxB;
        rangos[3] = minR;
        rangos[4] = minG;
        rangos[5] = minB;
     return rangos;   
    }*/
    
    
    private float[] avg(BufferedImage image, int coord_x , int coord_y, int tam){
        
        float[] RGB = new float[3];
        
        float totalR = 0;
        float totalG = 0;
        float totalB = 0;
        
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                Color c_pos = new Color(image.getRGB(coord_x + j, coord_y +i));
                
                totalR += c_pos.getRed();
                totalG += c_pos.getGreen();
                totalB += c_pos.getBlue();
            }
        }
        
        RGB[0] = (float)(totalR/Math.pow(tam, 2));
        RGB[1] = (float)(totalG/Math.pow(tam, 2));
        RGB[2] = (float)(totalB/Math.pow(tam, 2));

     return RGB;
    }
    
    @Override
    public void run() {
        vistaPrincipal.desableButtons(false);
        vistaPrincipal.getBt_parar().setEnabled(true);
        camara.cleeanPhotos();
        
        
        ImageInputStream iis = null;

        try {
            image_1 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/1.png")));
            image_2 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/2.png")));
            image_3_1 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/3_1.png")));
            image_3_2 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/3_2.png")));
            image_4 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/4.png")));
            image_5_1 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/5_1.png")));
            image_5_2 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/5_2.png")));
            image_6 = ImageIO.read(ImageIO.createImageInputStream(new FileInputStream("./test_images/6.png")));
            
            centros = new ArrayList<>();
            
            centros.add(avg(image_1,237 ,250,10));
            centros.add(avg(image_2,237 ,250,10));
            centros.add(avg(image_3_1,237 ,250,10));
            centros.add(avg(image_4,237 ,250,10));
            centros.add(avg(image_5_1,237 ,250,10));
            centros.add(avg(image_6,237 ,250,10));

            int[][] cube = new int[6][9];
            cube[0]=analizarCara(image_1);
            cube[1]=analizarCara(image_2);
            cube[2]=analizarCara(image_3_1,image_3_2);
            cube[3]=analizarCara(image_4);
            cube[4]=analizarCara(image_5_1,image_5_2);
            cube[5]=analizarCara(image_6);
            
            for (int[] is : cube) {
                System.out.println(Arrays.toString(is));
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProcesoResolverCubo.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        //cubo.doMove("E", false);
        vistaPrincipal.desableButtons(true);
    }

    
    
    
    
}
