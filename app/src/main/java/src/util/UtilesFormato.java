package src.util;

/**
 * Created by Rober on 18/06/2016.
 */
public class UtilesFormato {

    public static boolean esUnNumero(String cadena) {

        try {
            Integer.parseInt(cadena);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }

    }
}
