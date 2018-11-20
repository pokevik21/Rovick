package rovick.Cubo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
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
    
    private ArrayList<float[]> centros;
    private int[][] cube = null;
            
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

    /**
     * Contructor del proceso, el cual se encarga de resolver el cubo.
     * @param vistaPrincipal Clase de la vista para modificar la acrivacion de botones
     * @param camara Controlador de la camara
     * @param cubo Controlador del cubo
     */
    public ProcesoResolverCubo(MainFrame vistaPrincipal, WebCamController camara, CubeController cubo) {
        this.vistaPrincipal = vistaPrincipal;
        this.camara = camara;
        this.cubo = cubo;

    }

/**
 * 
 * @param move Movimiento ha realizar
 * @param foto Nombre de la foto que se pone.
 * @param seg Segundos a esperar, se añaden 300 milis
 * @throws InterruptedException Excepcion de esperar
 */
    public void hacerPaso(String move,String foto,long seg) throws InterruptedException{
            cubo.doMove(move, false, true);
            sleep(seg + 300);
            if(!foto.isEmpty())camara.takePhoto(foto);
    }
    
    
    /**
     * Metodo que calcula la distanci de colores entre el centro y el color pasado
     * @param c1 color del centro a comprar
     * @param c2 color que estamos clasificando
     * @return La diferencia entre los dos.
     */
    private float distancia(float[] c1, float[] c2) {
        float dR = (float) Math.abs(c1[0]-c2[0]);
        float dG = (float) Math.abs(c1[1]-c2[1]);
        float dB = (float) Math.abs(c1[2]-c2[2]);
        float resultado = (float) Math.sqrt(2*Math.pow(dR, 2) + 4*Math.pow(dG, 2) + 3*Math.pow(dB, 2)); 
        return resultado;
    }
    
    /**
     * Clasifica el color comprarndolo con los centros.
     * @param image Imagen a analizar.
     * @param pos_x Posición en X de donde queremos buscar el color.
     * @param pos_y Posición en X de donde queremos buscar el color.
     * @return 
     */
    private int sacarColor(BufferedImage image, int pos_x, int pos_y){
        float[] color = avg(image, pos_x, pos_y, 10);
        
        //System.out.println("\t"+color[0] +"\t"+color[1]+"\t"+color[2]);
        
        int mejor_color = 0;
        float mejor_distancia = 99999;
        for (int i = 0; i < 6; i++) {
            
            float d = distancia(centros.get(i), color);
            if (d < mejor_distancia) {
                mejor_distancia = d;
                mejor_color = i;
            }
        }
       //System.out.println("intesidad:"+Arrays.toString(color) +"  ; mejor_distancia:"+mejor_distancia  +"  mejor:"+mejor_color);
    return mejor_color;    
    }   
    
    /**
     * Metodo para imprimir todo el cubo, esto se es un metodo de Debug,
     * hay que pasrle los nombres de los colores en el orden de las imágenes.
     * @param c1 centro 1
     * @param c2 centro 2
     * @param c3 centro 3
     * @param c4 centro 4
     * @param c5 centro 5
     * @param c6 centro 6
     */
    private void imprimirCubo(String c1,String c2,String c3,String c4,String c5,String c6){
        for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 9; j++) {
                       String color = "";
                    switch (cube[i][j]) {
                        case 0:
                            color = c1;
                            break;
                        case 1:
                            color = c2;
                            break;
                        case 2:
                            color = c3;
                            break;
                        case 3:
                            color = c4;
                            break;
                        case 4:
                            color = c5;
                            break;
                        case 5:
                            color = c6;
                            break;
                        default:
                            throw new AssertionError();
                    }
                    System.out.print(" ["+color+"] "); 
                    int lin = j+1;
                    if(lin!=0 && lin!=1 && lin%3 == 0)System.out.println("");

                }
                System.out.println("*********************************************");
            }
    }
    
    /**
     * Metodo encargado de analizar todas las caras de una imagen,
     * con determinadas posiciones.
     * @param image Imagen a analizar
     * @return Array que contiene el codigo de color en orden
     */
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
    
    /**
     * Metodo encargado de analizar todas las caras de una imagen,
     * con determinadas posiciones, y la segunda imagen muestra las
     * posiciones tapadas por el gancho.
     * @param image Imagen a analizar
     * @return Array que contiene el codigo de color en orden
     */
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
    
    /**
     * Calcula el promedio de RGB de la imagen en las coordenadas especificadas 
     * y en un cubo de tamaño especificado en el parametro tam.
     * @param image Imagen a analizar.
     * @param coord_x Coordendas en X.
     * @param coord_y Coordendas en Y.
     * @param tam Tamaño del cubo del cual sacará el promedio.
     * @return Array con los promedios de RGB en las posiciones 0,1,2 en orden.
     */
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
        boolean old_luzEstado = vistaPrincipal.isLuz_encendida();
        vistaPrincipal.encenderLuz();

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
            
            int rango_avg = 10;
            centros.add(avg(image_1,237 ,250,rango_avg));
            centros.add(avg(image_2,237 ,250,rango_avg));
            centros.add(avg(image_3_1,237 ,250,rango_avg));
            centros.add(avg(image_4,237 ,250,rango_avg));
            centros.add(avg(image_5_1,237 ,250,rango_avg));
            centros.add(avg(image_6,237 ,250,rango_avg));

            cube = new int[6][9];
            cube[0]=analizarCara(image_1);
            cube[1]=analizarCara(image_2);
            cube[2]=analizarCara(image_3_1,image_3_2);
            cube[3]=analizarCara(image_4);
            cube[4]=analizarCara(image_5_1,image_5_2);
            cube[5]=analizarCara(image_6);
            
            //imprimir cubo:
            imprimirCubo("Am", "Ve", "Na", "Az", "Ro", "Bl");
            
            //sacar numero de colores repetidos:
            int[] colores = new int[6];
            for (int[] is : cube) {
                for (int i = 0; i < is.length; i++) {
                    colores[is[i]]++;
                }
            }
            
            //imprimir repetidos:
            boolean fallo = false;
            String txt_repetidos = "";
            for (int i = 0; i < colores.length; i++) {
                int color = colores[i];
                if(color != 9) fallo = true;
                System.out.println("Color num "+i+", está repetido: "+color);
                txt_repetidos += "Color num "+i+", está repetido: "+color +"\n";
            }
            
            if (fallo){
                JOptionPane.showMessageDialog(vistaPrincipal, "La lectura de colores ha fallado.\n Asegurate de tener una buena iluminacion y vuelve a intentarlo.\nResultados:\n"+txt_repetidos, "Fallo lectura de colores", JOptionPane.ERROR_MESSAGE);
            }else{
                
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcesoResolverCubo.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        cubo.doMove("E", false);
        vistaPrincipal.desableButtons(true);
        if(!old_luzEstado)vistaPrincipal.apagarLuz(); //si no estaba encendido, la apagamos.
    }

}
