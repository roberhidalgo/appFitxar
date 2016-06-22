package net.sgoliver.android.toolbar3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import src.login.EnumResultadoLogin;
import src.login.LoginService;
import src.dialogs.DialogoAlerta;

public class InicioSesion extends AppCompatActivity {

    private TabLayout tabLayout;

    class ProcesoLogin extends AsyncTask<String, Void, Void> {

        private LoginService loginService;

        @Override
        protected Void doInBackground(String... credenciales) {

            String mensajeDialogo  = null;

            try {
                String codigoProfe = credenciales[0];
                String password = credenciales[1];
                this.loginService = new LoginService(codigoProfe, password, getResources());
                EnumResultadoLogin enumResultadoLogin = this.loginService.verificaDatosLogin();

                switch (enumResultadoLogin) {

                    case USUARIO_VACIO:

                        mensajeDialogo = "Debes introducir un usuario";
                        break;
                    case USUARIO_INCORRECTO:

                        mensajeDialogo = "El usuario es incorrecto o no existe";
                        break;
                    case PASSWORD_INCORRECTO:

                        mensajeDialogo = "La contraseña introducida es incorrecta";
                        break;
                    case CORRECTO:

                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.putExtra("codigoProfe",Integer.parseInt(codigoProfe));
                        startActivity(intent);
                        break;
                    default:

                        mensajeDialogo = "Error inesperado. Contacte con el administrador.";
                }
            }
            catch (SQLException ex) {

                ex.printStackTrace();
                mensajeDialogo = "Imposible conectar con la BD. Contacte con el administrador.";
            }
            catch (ClassNotFoundException | IOException ex) {

                ex.printStackTrace();
                mensajeDialogo = "Error en la configuración de la BD. Contacte con el administrador";
            }

            if (mensajeDialogo != null) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoAlerta dialogo = new DialogoAlerta();
                dialogo.setMensaje(mensajeDialogo);
                dialogo.show(fragmentManager, "tagAlerta");
            }

            return (null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Appbar page filter
        toolbar.setTitle(getResources().getString(R.string.controlES));
        toolbar.setLogo(R.drawable.batoi);

        final Button button = (Button) findViewById(R.id.bLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText editTextUsuario = (EditText) findViewById(R.id.etUsername);
                final EditText editTextPassword = (EditText) findViewById(R.id.etPassword);

                String credenciales[] = new String[2];
                credenciales[0] = editTextUsuario.getText().toString();
                credenciales[1] = editTextPassword.getText().toString();

                new ProcesoLogin().execute(credenciales);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }
}
