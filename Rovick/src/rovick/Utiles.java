
package rovick;


public class Utiles {
    
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
    
     public static boolean tieneNumero(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            String caracter = String.valueOf(cadena.charAt(i));
            if (isNumeric(caracter)) return true;   
        }
        return false;
    }
    
    public static int extraerNumero(String cadena){
        String numero = "";
        for (int i = 0; i < cadena.length(); i++) {
            String caracter = String.valueOf(cadena.charAt(i));
            if (isNumeric(caracter)) numero+=caracter;   
        }
        
        if (numero.equals(""))return 0;
        return Integer.parseInt(numero);
    }
    
    
    
    public static int primerNum(String cadena){
        int pos = 0;
        for (int i = 0; i < cadena.length(); i++) {
            String caracter = String.valueOf(cadena.charAt(i));
            if (isNumeric(caracter)) return i;   
        }
        return -1;
    }
    
}
