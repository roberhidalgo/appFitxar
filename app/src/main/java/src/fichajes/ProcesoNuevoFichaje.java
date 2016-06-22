package src.fichajes;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import net.sgoliver.android.toolbar3.Fragment2;
import net.sgoliver.android.toolbar3.R;

import java.io.IOException;
import java.sql.SQLException;

import src.BD.ConexionMySQL;
import src.dialogs.UtilesDialogos;

/**
 * Created by Rober on 19/06/2016.
 */
public class ProcesoNuevoFichaje extends AsyncTask<FichajeService, Void, Void> {

    private FichajeService fichajeService;

    private final FragmentManager fragmentManager;
    private final Resources resources;

    public ProcesoNuevoFichaje(FragmentManager fragmentManager, Resources resources) {
        this.fragmentManager = fragmentManager;
        this.resources = resources;
    }

    @Override
    protected Void doInBackground(FichajeService... fichajeServices) {

        try {
            this.fichajeService = fichajeServices[0];
            ConexionMySQL conexionMySQL = new ConexionMySQL(resources);
            fichajeService.anyadeOCierraFichaje(conexionMySQL);
            conexionMySQL.close();
        }
        catch (ClassNotFoundException | SQLException | IOException ex) {

            ex.printStackTrace();
            UtilesDialogos.muestraDialogo(this.fragmentManager, "Error al conectar con la BD. Contacte con el administrador.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Fragment2 fragment2 = (Fragment2) this.fragmentManager.findFragmentById(R.id.viewpager);
        fragment2.updateList(this.fichajeService.getFichajesProfe());
    }
}
