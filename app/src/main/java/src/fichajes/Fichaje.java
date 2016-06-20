package src.fichajes;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

import src.BD.ConexionMySQL;

/**
 * Created by Rober on 18/06/2016.
 */
public class Fichaje {

    private int id;
    private final Time entrada;
    private Time salida;

    public Fichaje(Time entrada, Time salida) {
        this.entrada = entrada;
        this.salida = salida;
    }

    public Fichaje(Time entrada) {
        this.entrada = entrada;
        this.salida = null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Time getSalida() {
        return salida;
    }

    public void setSalida(Time salida) {
        this.salida = salida;
    }

    public void modificaEnBD(ConexionMySQL conexionMySQL) throws SQLException{

        String sentenciaSQL = "UPDATE faltes_profes SET eixida='" + this.salida.toString() + "' WHERE id=" + this.id;
        conexionMySQL.ejecutaTransaccion(sentenciaSQL);
    }

    public void insertaEnBD(int codigoProfe, ConexionMySQL conexionMySQL) throws SQLException{

        String sentenciaSQL = "INSERT INTO faltes_profes (profesor,dia,entrada,eixida) VALUES (" + codigoProfe + ",curdate(),'" +  entrada + "',null)";
        conexionMySQL.ejecutaTransaccion(sentenciaSQL);
    }

    @Override
    public String toString() {

        String texto = "Entrada: " +entrada.toString();// + ":" + String.valueOf(Calendar.MINUTE));

        if (salida != null) {
            texto += "\n" + "Salida: " + salida.toString();
        }

        return texto;

    }
}
