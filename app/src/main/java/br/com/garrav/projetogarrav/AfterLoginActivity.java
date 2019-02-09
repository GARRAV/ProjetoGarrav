package br.com.garrav.projetogarrav;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.api.Event_UserServerService;
import br.com.garrav.projetogarrav.util.pref.PrefUserUtil;

public class AfterLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

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

        //Get Static Presence List Event_User from Server
        Event_UserServerService.getEventUserPresenceFromServer(
                this,
                User.getUniqueUser().getId()
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(User.getUniqueUser() != null) User.setUniqueUser(null);
            super.onBackPressed();
        }
    }

    /**
     * Método responsável por fazer a interação com a barra lateral
     * com os itens correspondentes.
     *
     * @param item Item selecionado da barra lateral
     * @return Resultado da seleção da barra lateral
     * @author Felipe Savaris
     * @since 23/01/2019
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Id da Opção Selecionada
        int id = item.getItemId();
        //Generic Intent
        Intent it;

        switch (id) {
            //Lista de Presença de Eventos
            case R.id.nav_event_presence :
                //Mudança de Activity -> EventPresenceListActivity
                it = new Intent(
                        this,
                        EventPresenceListActivity.class
                );
                startActivity(it);
                break;

            //Configurações do App
            case R.id.nav_options :
                //Mudança de Activity -> OptionsActivity
                it = new Intent(
                        this,
                        OptionsActivity.class
                );
                startActivity(it);
                break;

            //Reportar Problemas com o App
            case R.id.nav_report_problem :
                //Mudança de Activity -> ReportProblemActivity
                it = new Intent(
                        this,
                        ReportProblemActivity.class
                );
                startActivity(it);
                break;

            //Sobre
            case R.id.nav_about :
                //Mudança de Avtivity -> AboutActivity
                it = new Intent(
                        this,
                        AboutActivity.class
                );
                startActivity(it);
                break;

            //Sair
            case R.id.nav_logout :
                //Mudança de Activity -> MainActivity
                it = new Intent(
                        this,
                        MainActivity.class
                );
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);

                //Erase User From SharedPreferences and Static Attribute
                User.setUniqueUser(null);
                PrefUserUtil.clearUserSharedPreferences(this);

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Método responsável por fazer a interação com o botão Challenge
     * e abrir uma nova activity
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 29/11/2018
     */
    public void btChallenge(View view) {

        //Mudança de Activity -> ChallengeActivity
        Intent it = new Intent(
                this,
                ChallengeActivity.class
        );
        startActivity(it);

    }

    /**
     * Método responsável por fazer a interação com o botão SupportiveFriend
     * e abrir uma nova activity
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 01/12/2018
     */
    public void btSupportiveFriend(View view) {

        //Mudança de Activity -> SupportiveFriendActivity
        Intent it = new Intent(
                this,
                SupportiveFriendActivity.class
        );
        startActivity(it);
    }

    /**
     * Método responsável por fazer a interação com o botão MapsEvents
     * e abrir uma nova activity
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 05/12/2018
     */
    public void btMapsEvents(View view) {

        //Mudança de Activity -> MapsEventsActivity
        Intent it = new Intent(
                this,
                MapsEventsActivity.class
        );
        startActivity(it);
    }
}
