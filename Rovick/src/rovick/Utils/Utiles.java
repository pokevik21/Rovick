
package rovick.Utils;

/**
 * Clase que contiene metodos estaticos auximires.
 * @author Victor Pastor Urueña
 */
public class Utiles {
    
    /**
     * Metodo que te devuelve true si la cadena pasada es numérica.
     * @param cadena Cadena a analizar
     * @return true, si la cadena es numerica. false, en el caso contrario.
     */
    public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    
    }
    
    /**
     * Metodo que te devuelve true si la cadena pasada contiene algún numero.
     * @param cadena Cadena a analizar
     * @return true, si la cadena contiene algún numérico. false, en el caso contrario.
     */
     public static boolean tieneNumero(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            String caracter = String.valueOf(cadena.charAt(i));
            if (isNumeric(caracter)) return true;   
        }
        return false;
    }
    
    /**
     * Metodo que te devuelve el entero que contine la cadena.
     * @param cadena Cadena a analizar
     * @return El entero que contiene la cadena.
     */
    public static int extraerNumero(String cadena){
        String numero = "";
        for (int i = 0; i < cadena.length(); i++) {
            String caracter = String.valueOf(cadena.charAt(i));
            if (isNumeric(caracter)) numero+=caracter;   
        }
        
        if (numero.equals(""))return 0;
        return Integer.parseInt(numero);
    }
    
    
    /**
     * Metdo que devuelve la posicion del pimier numero numérico de la cadena.
     * @param cadena Cadena a analizar
     * @return la primiera poscion en la que se encuetra un numérico.
     */
    public static int primerNum(String cadena){
        int pos = 0;
        for (int i = 0; i < cadena.length(); i++) {
            String caracter = String.valueOf(cadena.charAt(i));
            if (isNumeric(caracter)) return i;   
        }
        return -1;
    }
    
}
