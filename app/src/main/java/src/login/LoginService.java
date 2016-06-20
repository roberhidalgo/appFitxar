package src.login;

import java.sql.ResultSet;
import java.sql.SQLException;

import src.BD.ConexionMySQL;
import src.util.UtilesFormato;

/**
 * Created by Rober on 18/06/2016.
 */
public class LoginService {

    private final String usuario;
    private final String password;

    public LoginService(String usuario, String password) {

        this.usuario = usuario;
        this.password = password;
    }

    public EnumResultadoLogin verificaDatosLogin() throws ClassNotFoundException,SQLException{

        EnumResultadoLogin resultadoLogin;

        if (usuario != null && !usuario.isEmpty()) {

            if (UtilesFormato.esUnNumero(usuario)) {
                ConexionMySQL conexionMySQL;

                conexionMySQL = new ConexionMySQL();

                String sentenciaSQL = "SELECT * FROM profesores WHERE codigo=" + usuario;
                ResultSet resultSet = conexionMySQL.ejecutaConsulta(sentenciaSQL);

                resultadoLogin = EnumResultadoLogin.USUARIO_INCORRECTO;

                while (resultSet.next()) {

                    String passwordBD = resultSet.getString("password");

                    if (password != null && password.equals(passwordBD)) {

                        resultadoLogin = EnumResultadoLogin.CORRECTO;
                        break;
                    }

                    resultadoLogin = EnumResultadoLogin.PASSWORD_INCORRECTO;
                    break;
                }

                resultSet.close();
                conexionMySQL.close();
            }
            else {
                resultadoLogin = EnumResultadoLogin.USUARIO_INCORRECTO;
            }
        }
        else {
            resultadoLogin = EnumResultadoLogin.USUARIO_VACIO;
        }

        return resultadoLogin;

    }
}
