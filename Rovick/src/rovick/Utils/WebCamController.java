package rovick.Utils;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;


/**
 * Clase encargada de el control de la Cśmara Web.
 */
public class WebCamController {
    
    private Webcam wc = null;
    private File imagesPath = null;
    
    /**
     * Contructor del controlador de la webcam.
     */
    public WebCamController() {
        wc = Webcam.getDefault();
        imagesPath = new File("tmp_images");    
        Dimension imga_size = new Dimension(640,480);
        wc.setViewSize(imga_size);
        wc.open();
    }
    
    /**
     * Tama un foto en formato jpg y la pone con el nombre pasado 
     * @param titulo String del titulo de la foto en formato jpg
     */
    public void takePhoto(String titulo){
        if (!imagesPath.isDirectory())imagesPath.mkdir();
        BufferedImage image = wc.getImage();

        try {
            // save image to PNG file
            ImageIO.write(image, "PNG", new File(imagesPath,titulo+".png"));
        } catch (IOException ex) {
            Logger.getLogger(WebCamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *Borra todas los archivos en la carpeta webCam_images
     */
    public void cleeanPhotos(){
        try {
            File[] files = imagesPath.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    file.delete();
                }
            }
            FileUtils.deleteDirectory(imagesPath);
        } catch (IOException ex) {
            System.err.println("No hay ficheros");
        }
    }
    

    
    /**
     * Cierra la WebCam
     */
    public void close(){
        this.wc.close();
    }
    
}
