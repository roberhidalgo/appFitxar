package net.sgoliver.android.toolbar3;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;

import src.fichajes.FichajeService;
import src.fichajes.ProcesoNuevoFichaje;
import src.fichajes.ProcesoLecturaFichajes;


public class MainActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener{

    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private FichajeService fichajeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int codigoProfe = getIntent().getExtras().getInt("codigoProfe");
        this.fichajeService = new FichajeService(codigoProfe);
        new ProcesoLecturaFichajes(getSupportFragmentManager(), getResources()).execute(introduceFichajeServicesEnArray());

        //Appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Appbar page filter
        toolbar.setTitle(getResources().getString(R.string.controlES));
        toolbar.setLogo(R.drawable.batoi);

        //Tabs + ViewPager

        //Establecer el PageAdapter del componente ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MiFragmentPagerAdapter(
                getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        preparaNavigationView();
    }

    private FichajeService[] introduceFichajeServicesEnArray() {

        FichajeService[] fichajeServices = new FichajeService[1];
        fichajeServices[0] = this.fichajeService;
        return fichajeServices;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        /*if (id == R.id.add) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "AGREGADO UNA NUEVA CITA", Toast.LENGTH_SHORT);

            toast1.show();
            return true;

        }*/
        return super.onOptionsItemSelected(item);
    }

    private void preparaNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.menu_Movies:
                                tabLayout.getTabAt(0).select();
                                break;
                            case R.id.menu_TvShows:
                                tabLayout.getTabAt(1).select();
                                break;
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    public void onFragmentInteraction() {

        new ProcesoNuevoFichaje(getSupportFragmentManager(), getResources()).execute(introduceFichajeServicesEnArray());
    }

}
