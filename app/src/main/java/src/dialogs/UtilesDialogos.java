package src.dialogs;

import android.support.v4.app.FragmentManager;

/**
 * Created by Rober on 19/06/2016.
 */
public class UtilesDialogos {

    public static void muestraDialogo(FragmentManager fragmentManager, String mensajeDialogo) {

        DialogoAlerta dialogo = new DialogoAlerta();
        dialogo.setMensaje("Error al conectar con la BD. Contacte con el administrador.");
        dialogo.show(fragmentManager, "tagAlerta");
    }
}
