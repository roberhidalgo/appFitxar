package src.fichajes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import src.BD.ConexionMySQL;

/**
 * Created by Rober on 18/06/2016.
 */
public class FichajeService {

    private int codigoProfe;
    private List<Fichaje> fichajesProfe;

    public FichajeService(int codigoProfe) {

        this.codigoProfe = codigoProfe;
    }

    public List<Fichaje> getFichajesProfe() {

        return this.fichajesProfe;
    }

    public void leeTodosFichajesProfeHoy(ConexionMySQL conexionMySQL) throws ClassNotFoundException, SQLException {

        List<Fichaje> fichajesProfeHoy = new ArrayList<>();

        String sentenciaSQL = "SELECT id,entrada,eixida FROM faltes_profes WHERE profesor=" + codigoProfe + " AND dia=curdate()";
        ResultSet resultSet = conexionMySQL.ejecutaConsulta(sentenciaSQL);

        while(resultSet.next()) {

            int id = resultSet.getInt("id");
            Time entrada = resultSet.getTime("entrada");
            Time salida = resultSet.getTime("eixida");

            Fichaje fichaje = new Fichaje(entrada, salida);
            fichaje.setId(id);
            fichajesProfeHoy.add(fichaje);
        }

        resultSet.close();

        this.fichajesProfe = fichajesProfeHoy;
    }

    public void anyadeOCierraFichaje(ConexionMySQL conexionMySQL) throws SQLException{

        int numFichajesHoy = fichajesProfe.size();
        Time ahora = getHoraActualDelSistema(conexionMySQL);

        if (numFichajesHoy > 0) {

            Fichaje ultimoFichaje = fichajesProfe.get(numFichajesHoy - 1);

            if (ultimoFichaje.getSalida() == null) {

                Fichaje copiaUltimoFichaje = new Fichaje(ultimoFichaje.getId(), ultimoFichaje.getEntrada());
                copiaUltimoFichaje.setSalida(ahora);
                copiaUltimoFichaje.modificaEnBD(conexionMySQL);

                ultimoFichaje.setSalida(ahora);

                return;
            }
        }

        Fichaje fichaje = new Fichaje(ahora);
        fichaje.insertaEnBD(codigoProfe, conexionMySQL);
        int id = getIdUltimoFichajeInsertado(conexionMySQL);
        fichaje.setId(id);
        this.fichajesProfe.add(fichaje);
    }


    private int getIdUltimoFichajeInsertado(ConexionMySQL conexionMySQL) throws SQLException{

        int ultimoId = 0;

        String sentenciaSQL = "SELECT MAX(last_insert_id()) as proxId FROM faltes_profes";
        ResultSet resultSet = conexionMySQL.ejecutaConsulta(sentenciaSQL);

        while(resultSet.next()) {

            ultimoId = resultSet.getInt("proxId");
            break;
        }

        resultSet.close();

        return ultimoId;
    }

    private Time getHoraActualDelSistema(ConexionMySQL conexionMySQL) throws SQLException{

        Time tiempoActual = null;

        String sentenciaSQL = "SELECT curtime() as horaactual";
        ResultSet resultSet = conexionMySQL.ejecutaConsulta(sentenciaSQL);

        while(resultSet.next()) {

            tiempoActual = resultSet.getTime("horaactual");
            break;
        }

        resultSet.close();

        return tiempoActual;
    }

}
