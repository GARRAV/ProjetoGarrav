package br.com.garrav.projetogarrav;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.garrav.projetogarrav.util.MessageActionUtil;

public class MapsEventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_events);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Chamado barra lateral
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Referência aos itens da barra lateral
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Instância do Fragment Google Maps
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.add(R.id.flMapsContainer, new MapsFragment(), "MapsFragment");
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        if(MapsFragment.EVENT_REGISTER) MapsFragment.EVENT_REGISTER = false;

        //Fecha a barra lateral se aberta pelo botão retornar
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *
     * @param menu
     * @author Felipe Savaris
     * @since 05/12/2018
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps_events, menu);
        return true;
    }

    /**
     *
     * @param item
     * @author Felipe Savaris
     * @since 05/12/2018
     * @return
     */
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

        return super.onOptionsItemSelected(item);
    }

    /**.
     *
     * @param item
     * @author Felipe Savaris
     * @since 05/12/2018
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Id da opção selecionada
        int id = item.getItemId();

        //Opções de seleção da barra lateral
        switch (id) {
            //Registro de eventos
            case R.id.nav_register_event_my_location :

                MapsFragment.EVENT_REGISTER = true;

                //Mensagem teste
                MessageActionUtil.makeText(
                        this,
                        "Selecione o local do evento"
                );
        }

        //Fecha a barra lateral após item selecionado
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
