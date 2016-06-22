package src.BD;


import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;

import net.sgoliver.android.toolbar3.R;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * Clase responsable de la conéxion, realización de consultas y transacciones y desconexión de la base de datos.
 * @author Rober
 */
public class ConexionMySQL
{
    /*DATOS DE CONFIGURACIÓN DE CONEXIÓN CON LA BASE DE DATOS*/
    private static final String CONTROLLER = "com.mysql.jdbc.Driver";
    private static final String NOMBRE_BD = "intranet";
    private static final String ENLACE_CONEXCION_BD = "jdbc:mysql://192.168.1.102/";
    private static final String USUARIO = "dam";
    private static final String PASSWORD = "1234";

    private final Connection connection;
    private final Statement statement;

    /**
     * El constructor creará la conexión y el interfaz de instrucciones.
     * @throws SQLException En caso de ocurrir algún error al conectar con la base de datos
     * @throws ClassNotFoundException En caso de ocurrir algún error al crear el interfaz de instrucciones
     */
    public ConexionMySQL(Resources resources) throws SQLException, ClassNotFoundException, IOException
    {
        InputStream inputStream = resources.openRawResource(R.raw.conexionmysql);
        Properties propiedades = new Properties();
        propiedades.load(inputStream);

        String controllerBD = propiedades.getProperty("controller");
        String nombreBD = propiedades.getProperty("nombre");
        String urlBD = propiedades.getProperty("direccion");
        String usuarioBD = propiedades.getProperty("usuario");
        String passwordBD = propiedades.getProperty("contrasenya");

        Class.forName(controllerBD);
        this.connection = DriverManager.getConnection(urlBD + nombreBD, usuarioBD, passwordBD);
        this.statement = this.connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        System.out.println("Conectado correctamente a la base de datos " + nombreBD);
    }

    /**
     * Ejecuta la consulta dada
     * @param sentenciaSQL
     * @return devuelve el ResultSet indicado con los datos referentes a la sentencia sql proporcionada
     * @throws SQLException En caso de ocurrir algún error a la hora de realizar la consulta.
     */

    public ResultSet ejecutaConsulta(String sentenciaSQL) throws SQLException{

        return this.statement.executeQuery(sentenciaSQL);
    }

    /**
     * Ejecuta la transacción (insert, update o delete) dada.
     * @param sentenciaSQL
     * @throws SQLException En caso de ocurrir algún error a la hora de realizar la transacción.
     */
    public void ejecutaTransaccion(String sentenciaSQL) throws SQLException
    {
        if (this.statement.executeUpdate(sentenciaSQL) == 0) {
            throw new SQLException("Error de transcacción sobre la base de datos: No hay filas afectadas");
        }
    }

    /**
     * Cierrra la conexión
     * @throws SQLException
     */
    public void close() throws SQLException {

        this.statement.close();
        this.connection.close();
    }
}
